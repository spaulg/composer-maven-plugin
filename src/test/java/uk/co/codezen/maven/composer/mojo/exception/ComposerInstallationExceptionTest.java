package uk.co.codezen.maven.composer.mojo.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class ComposerInstallationExceptionTest
{
    @Test
    public void testException()
    {
        ComposerInstallationException exception;
        Exception ex = new Exception("exception");
        String[] args = new String[] {"arg1", "arg2", "arg3"};

        exception = new ComposerInstallationException(args, ".");
        assertEquals("Failed to install composer with command: arg1 arg2 arg3, in working directory .", exception.getMessage());
        assertNull(exception.getCause());

        exception = new ComposerInstallationException(ex);
        assertNull(exception.getMessage());
        assertEquals(ex, exception.getCause());
    }
}
