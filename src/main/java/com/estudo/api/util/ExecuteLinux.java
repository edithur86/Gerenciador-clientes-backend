package com.estudo.api.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExecuteLinux {

    public static List<String> execute(final String command) throws ExecutionFailedException, InterruptedException, IOException {
        try {
            return execute(command, 0, null, false);
        } catch (ExecutionTimeoutException e) { return null; } /* Impossible case! */
    }

    public static List<String> execute(final String command, final long timeout, final TimeUnit timeUnit) throws ExecutionFailedException, ExecutionTimeoutException, InterruptedException, IOException {
        return execute(command, 0, null, true);
    }

    public static List<String> execute(final String command, final long timeout, final TimeUnit timeUnit, boolean destroyOnTimeout) throws ExecutionFailedException, ExecutionTimeoutException, InterruptedException, IOException {
        Process process = new ProcessBuilder().command("bash", "-c", command).start();
        if(timeUnit != null) {
            if(process.waitFor(timeout, timeUnit)) {
                if(process.exitValue() == 0) {
                    return IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8);
                } else {
                    throw new ExecutionFailedException("Execution failed: " + command, process.exitValue(), IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8));
                }
            } else {
                if(destroyOnTimeout) process.destroy();
                throw new ExecutionTimeoutException("Execution timed out: " + command);
            }
        } else {
            if(process.waitFor() == 0) {
                return IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8);
            } else {
                throw new ExecutionFailedException("Execution failed: " + command, process.exitValue(), IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8));
            }
        }
    }

    public static class ExecutionFailedException extends Exception {

        private static final long serialVersionUID = 1951044996696304510L;

        private final int exitCode;
        private final List<String> errorOutput;

        public ExecutionFailedException(final String message, final int exitCode, final List<String> errorOutput) {
            super(message);
            this.exitCode = exitCode;
            this.errorOutput = errorOutput;
        }

        public int getExitCode() {
            return this.exitCode;
        }

        public List<String> getErrorOutput() {
            return this.errorOutput;
        }

    }

    public static class ExecutionTimeoutException extends Exception {

        private static final long serialVersionUID = 4428595769718054862L;

        public ExecutionTimeoutException(final String message) {
            super(message);
        }

    }
}
