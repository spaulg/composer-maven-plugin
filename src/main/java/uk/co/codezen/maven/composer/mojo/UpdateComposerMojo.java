
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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import uk.co.codezen.maven.composer.mojo.exception.ComposerExecutionException;
import uk.co.codezen.maven.composer.mojo.exception.ComposerInstallationException;

import java.io.IOException;

/**
 * Update composer dependencies
 */
@Mojo(name = "update")
final public class UpdateComposerMojo extends AbstractComposerMojo
{
    /**
     * Run composer update
     *
     * @throws MojoExecutionException
     */
    @Override
    public void execute() throws MojoExecutionException
    {
        try {
            this.runComposer("update");
        }
        catch(IOException ex) {
            this.getLog().error("Failed to execute composer install", ex);
            throw new MojoExecutionException("Failed to execute composer install", ex);
        }
        catch(ComposerInstallationException ex) {
            this.getLog().error("Failed to execute composer install", ex);
            throw new MojoExecutionException("Failed to execute composer install", ex);
        }
        catch(ComposerExecutionException ex) {
            this.getLog().error("Failed to execute composer install", ex);
            throw new MojoExecutionException("Failed to execute composer install", ex);
        }
    }
}
