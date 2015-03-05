
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

package uk.co.codezen.maven.composer.mojo.exception;

import java.util.List;

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
    public ComposerExecutionException(List<String> commandArgs, String workingDirectory)
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
