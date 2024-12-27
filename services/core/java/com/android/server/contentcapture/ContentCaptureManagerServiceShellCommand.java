package com.android.server.contentcapture;

import android.os.ShellCommand;

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class ContentCaptureManagerServiceShellCommand extends ShellCommand {
    public final ContentCaptureManagerService mService;

    public ContentCaptureManagerServiceShellCommand(
            ContentCaptureManagerService contentCaptureManagerService) {
        this.mService = contentCaptureManagerService;
    }

    public static int requestSessionCommon(
            PrintWriter printWriter, CountDownLatch countDownLatch, Runnable runnable) {
        runnable.run();
        try {
            if (countDownLatch.await(5L, TimeUnit.SECONDS)) {
                return 0;
            }
            printWriter.println("Timed out after 5 seconds");
            return -1;
        } catch (InterruptedException unused) {
            printWriter.println("System call interrupted");
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00de, code lost:

       if (r8.equals("temporary-service") == false) goto L46;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("ContentCapture Service (content_capture) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get bind-instant-service-allowed");
            outPrintWriter.println(
                    "    Gets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set bind-instant-service-allowed [true | false]");
            outPrintWriter.println(
                    "    Sets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println(
                    "    Temporarily (for DURATION ms) changes the service implemtation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-service-enabled USER_ID");
            outPrintWriter.println(
                    "    Checks whether the default service is enabled for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  list sessions [--user USER_ID]");
            outPrintWriter.println("    Lists all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  destroy sessions [--user USER_ID]");
            outPrintWriter.println("    Destroys all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
