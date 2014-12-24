package uk.co.codezen.maven.composer.mojo.exception;

/**
 * Failed to install composer
 */
final public class ComposerInstallationException extends Exception
{
    /**
     * Exception message
     */
    protected String message;

    /**
     * Exception cause
     */
    protected Throwable cause = null;

    /**
     * Constructor
     *
     * @param cause Exception cause
     */
    public ComposerInstallationException(Throwable cause)
    {
        this.cause = cause;
    }

    /**
     * Constructor
     *
     * @param commandArgs Exception message
     * @param workingDirectory Working directory
     */
    public ComposerInstallationException(String[] commandArgs, String workingDirectory)
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

        this.message = String.format("Failed to install composer with command: %s, in working directory %s",
                arguments, workingDirectory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage()
    {
        return this.message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Throwable getCause()
    {
        return this.cause;
    }
}
