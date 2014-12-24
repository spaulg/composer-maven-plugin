package uk.co.codezen.maven.composer.mojo.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComposerExecutionExceptionTest
{
    @Test
    public void testException()
    {
        String[] args = new String[] {"arg1", "arg2", "arg3"};
        ComposerExecutionException exception = new ComposerExecutionException(args, ".");
        assertEquals("Failed to execute composer with command: arg1 arg2 arg3, in working directory .", exception.getMessage());
    }
}
