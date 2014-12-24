
/*
    Copyright 2014 Simon Paulger <spaulger@codezen.co.uk>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package uk.co.codezen.maven.composer.mojo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import uk.co.codezen.maven.composer.mojo.exception.ComposerExecutionException;
import uk.co.codezen.maven.composer.mojo.exception.ComposerInstallationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract composer
 * Contains the properties needed for customising
 * the composer run.
 */
abstract public class AbstractComposerMojo extends AbstractMojo
{
    /**
     * Composer installer URL
     */
    final String composerInstallerUrl = "https://getcomposer.org/installer";

    /**
     * Build directory
     */
    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private String buildDirectory = null;

    /**
     * PHP binary path, defaults to 'php'
     */
    @Parameter(defaultValue = "php")
    private String phpPath = "php";

    /**
     * Composer phar path.
     * Downloads from https://getcomposer.org/installer if not set
     */
    @Parameter
    private String composerPharPath = null;

    /**
     * Composer json path. Defaults to target/artifact/composer.json
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}/composer.json")
    private String composerJsonPath = null;



    /**
     * Set PHP path
     *
     * @param phpPath PHP path
     */
    public void setPhpPath(String phpPath)
    {
        this.phpPath = phpPath;
    }

    /**
     * Get PHP path
     *
     * @return PHP path
     */
    public String getPhpPath()
    {
        return this.phpPath;
    }

    /**
     * Set build directory
     *
     * @param buildDirectory Build directory
     */
    public void setBuildDirectory(String buildDirectory)
    {
        this.buildDirectory = buildDirectory;
    }

    /**
     * Get build directory
     *
     * @return Build directory
     */
    public String getBuildDirectory()
    {
        return this.buildDirectory;
    }

    /**
     * Set composer phar path
     *
     * @param composerPharPath Composer phar path
     */
    public void setComposerPharPath(String composerPharPath)
    {
        this.composerPharPath = composerPharPath;
    }

    /**
     * Get composer phar path
     *
     * @return Composer phar path
     */
    public String getComposerPharPath()
    {
        return this.composerPharPath;
    }

    /**
     * Set composer json path
     *
     * @param composerJsonPath composer json path
     */
    public void setComposerJsonPath(String composerJsonPath)
    {
        this.composerJsonPath = composerJsonPath;
    }

    /**
     * Get composer json path
     *
     * @return Composer json path
     */
    public String getComposerJsonPath()
    {
        return this.composerJsonPath;
    }

    /**
     * Get error stream output file
     *
     * @return Error stream output file
     */
    private File getRedirectErrorFile()
    {
        return new File(String.format("%s%scomposer.error", this.getBuildDirectory(), File.separator));
    }

    /**
     * Get output stream output file
     *
     * @return Output stream output file
     */
    private File getRedirectOutputFile()
    {
        return new File(String.format("%s%scomposer.out", this.getBuildDirectory(), File.separator));
    }

    /**
     * Run composer with the specified action
     *
     * @param composerAction Composer action to perform
     * @throws IOException IO problem executing composer
     */
    protected void runComposer(String composerAction) throws IOException, ComposerInstallationException, ComposerExecutionException
    {
        String composerPhar = this.getComposerPharPath();

        if (null == composerPhar) {
            // Composer path not set, download and install from getcomposer.org
            // using the runCommand function against the PHP installer downloaded.
            this.getLog().info(String.format("composer.phar path not specified, downloading from %s", this.composerInstallerUrl));

            composerPhar = String.format("%s%scomposer.phar", this.getBuildDirectory(), File.separator);
            this.installComposer(composerPhar);
        }

        String workingPath =
                new File(this.getComposerJsonPath())
                .getParentFile()
                .getCanonicalPath()
        ;

        String[] commandArgs = new String[] {this.getPhpPath(), composerPhar, "--no-interaction",
                "--working-dir=" + workingPath, composerAction};
        if (0 != this.runCommand(commandArgs, ".")) {
            throw new ComposerExecutionException(commandArgs, ".");
        }
    }

    /**
     * Install composer from the upstream provider
     *
     * @param composerPhar Composer phar path
     */
    protected void installComposer(String composerPhar) throws IOException, ComposerInstallationException
    {
        // Composer installer & phar paths
        File composerPharFile = new File(composerPhar);
        String composerDirectory = composerPharFile.getParent();

        File composerInstallerFile = new File(String.format("%s%scomposerinstaller.php",
                composerDirectory, File.separator));

        // Create an HTTP client and retrieve the installer
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        CloseableHttpResponse response = null;

        try {
            this.getLog().debug("Retrieving installer");
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(this.composerInstallerUrl);
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            if (null != entity) {
                InputStream installerInStream = entity.getContent();
                FileOutputStream installerOutStream = new FileOutputStream(composerInstallerFile);

                try {
                    byte[] buff = new byte[1024];
                    int bytesRead;
                    while (-1 != (bytesRead = installerInStream.read(buff))) {
                        installerOutStream.write(buff, 0, bytesRead);
                    }
                }
                finally {
                    installerInStream.close();
                    installerOutStream.close();
                }
            }
        }
        catch (IOException ex) {
            throw new ComposerInstallationException(ex);
        }
        finally {
            if (null != response) {
                response.close();
            }
        }

        // Execute the installer to create the phar
        String composerInstaller = composerInstallerFile.getCanonicalPath();
        this.getLog().debug(String.format("Running downloaded composer installer %s", composerInstaller));
        String[] commandArgs = new String[] {this.getPhpPath(), composerInstaller};
        if (0 != this.runCommand(commandArgs, composerDirectory)) {
            throw new ComposerInstallationException(commandArgs, composerDirectory);
        }
    }

    /**
     * Execute an arbitrary command, forwarding the process stdout to the Log info level
     * and the process stderr to the Java error level.
     *
     * @param command Command to execute
     * @param workingDirectory Working directory
     * @throws IOException IO problem executing command
     */
    private int runCommand(String[] command, String workingDirectory) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(workingDirectory));
        processBuilder.redirectError(this.getRedirectErrorFile());
        processBuilder.redirectOutput(this.getRedirectOutputFile());

        Process composerProcess = processBuilder.start();

        while (true) {
            try {
                composerProcess.waitFor();
                break;
            } catch (InterruptedException e) {
                // Do nothing, re-run loop
            }
        }

        return composerProcess.exitValue();
    }
}
