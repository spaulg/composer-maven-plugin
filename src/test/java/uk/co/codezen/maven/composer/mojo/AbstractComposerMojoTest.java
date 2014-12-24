package uk.co.codezen.maven.composer.mojo;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import uk.co.codezen.maven.composer.mocks.MockMojo;
import uk.co.codezen.maven.composer.mojo.exception.ComposerInstallationException;

import java.io.File;
import java.io.IOException;

public class AbstractComposerMojoTest
{
    private String testOutputPath;
    private MockMojo mojo;

    @Before
    public void setUp()
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");

        this.mojo = new MockMojo();
    }

    @Test
    public void phpPathAccessors()
    {
        assertEquals("php", this.mojo.getPhpPath());
        this.mojo.setPhpPath("/usr/bin/php");
        assertEquals("/usr/bin/php", this.mojo.getPhpPath());
    }

    @Test
    public void buildDirectoryAccessors()
    {
        assertNull(this.mojo.getBuildDirectory());
        this.mojo.setBuildDirectory("path/to/build/directory");
        assertEquals("path/to/build/directory", this.mojo.getBuildDirectory());
    }

    @Test
    public void composerPharPathAccessors()
    {
        assertNull(this.mojo.getComposerPharPath());
        this.mojo.setComposerPharPath("path/to/phar");
        assertEquals("path/to/phar", this.mojo.getComposerPharPath());
    }

    @Test
    public void composerJsonPathAccessors()
    {
        assertNull(this.mojo.getComposerJsonPath());
        this.mojo.setComposerJsonPath("path/to/json");
        assertEquals("path/to/json", this.mojo.getComposerJsonPath());
    }

    @Test
    public void installComposer() throws IOException, ComposerInstallationException
    {
        String composerPhar = String.format("%s%scomposer.phar", this.testOutputPath, File.separator);
        File composerPharFile = new File(composerPhar);

        assertFalse(composerPharFile.exists());
        this.mojo.setBuildDirectory(this.testOutputPath);
        this.mojo.installComposer(composerPhar);
        assertTrue(composerPharFile.exists());
    }
}
