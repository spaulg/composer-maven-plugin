package uk.co.codezen.maven.composer.mojo.exception;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ComposerInstallationExceptionTest
{
    @Test
    public void testException()
    {
        ComposerInstallationException exception;
        Exception ex = new Exception("exception");
        List<String> args = Arrays.asList("arg1", "arg2", "arg3");

        exception = new ComposerInstallationException(args, ".");
        assertEquals("Failed to install composer with command: arg1 arg2 arg3, in working directory .", exception.getMessage());
        assertNull(exception.getCause());

        exception = new ComposerInstallationException(ex);
        assertNull(exception.getMessage());
        assertEquals(ex, exception.getCause());
    }
}
