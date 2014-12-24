package uk.co.codezen.maven.composer.mojo.exception;

/**
 * Failure to execute composer
 */
final public class ComposerExecutionException extends Exception
{
    /**
     * Exception message
     */
    protected String message;

    /**
     * Constructor
     *
     * @param commandArgs Exception message
     * @param workingDirectory Working directory
     */
    public ComposerExecutionException(String[] commandArgs, String workingDirectory)
    {
        String arguments = null;

        for (String arg : commandArgs) {
            if (null == arguments) {
                arguments = arg;
            }
            else {
                arguments += " " + arg;
            }
        }

        this.message = String.format("Failed to execute composer with command: %s, in working directory %s",
                arguments, workingDirectory);
    }

    /**
     * {@inheritDoc}
     */
    public String getMessage()
    {
        return this.message;
    }
}
