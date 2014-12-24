package uk.co.codezen.maven.composer.mocks;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import uk.co.codezen.maven.composer.mojo.AbstractComposerMojo;
import uk.co.codezen.maven.composer.mojo.exception.ComposerInstallationException;

import java.io.IOException;

public class MockMojo extends AbstractComposerMojo
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void installComposer(String composerPhar) throws IOException, ComposerInstallationException
    {
        super.installComposer(composerPhar);
    }

    /**
     * Execute mojo action
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        // do nothing
    }
}
