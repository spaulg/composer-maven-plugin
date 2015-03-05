package uk.co.codezen.maven.composer.mojo.exception;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ComposerExecutionExceptionTest
{
    @Test
    public void testException()
    {
        List<String> args = Arrays.asList("arg1", "arg2", "arg3");
        ComposerExecutionException exception = new ComposerExecutionException(args, ".");
        assertEquals("Failed to execute composer with command: arg1 arg2 arg3, in working directory .", exception.getMessage());
    }
}
