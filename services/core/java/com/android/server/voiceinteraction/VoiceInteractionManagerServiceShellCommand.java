package com.android.server.voiceinteraction;

import android.os.Bundle;
import android.os.ShellCommand;
import android.util.Slog;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.server.voiceinteraction.VoiceInteractionManagerService;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VoiceInteractionManagerServiceShellCommand extends ShellCommand {
    public final VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mService;

    public VoiceInteractionManagerServiceShellCommand(VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub) {
        this.mService = voiceInteractionManagerServiceStub;
    }

    public static void handleError(PrintWriter printWriter, Exception exc, String str) {
        Slog.e("VoiceInteractionManager", "error calling ".concat(str), exc);
        printWriter.printf("Error calling %s: %s\n", str, exc);
    }

    public final int onCommand(String str) {
        final PrintWriter outPrintWriter;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        outPrintWriter = getOutPrintWriter();
        switch (str) {
            case "set-debug-hotword-logging":
                boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
                Slog.i("VoiceInteractionManager", "setDebugHotwordLogging(): " + parseBoolean);
                try {
                    VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this.mService;
                    synchronized (voiceInteractionManagerServiceStub) {
                        try {
                            if (voiceInteractionManagerServiceStub.mImpl == null) {
                                Slog.w("VoiceInteractionManager", "setTemporaryLogging without running voice interaction service");
                            } else {
                                voiceInteractionManagerServiceStub.mImpl.setDebugHotwordLoggingLocked(parseBoolean);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    break;
                } catch (Exception e) {
                    handleError(outPrintWriter, e, "setDebugHotwordLogging()");
                    return 1;
                }
            case "hide":
                Slog.i("VoiceInteractionManager", "requestHide()");
                try {
                    this.mService.hideCurrentSession();
                    break;
                } catch (Exception e2) {
                    handleError(outPrintWriter, e2, "requestHide()");
                    return 1;
                }
            case "show":
                Slog.i("VoiceInteractionManager", "requestShow()");
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                final AtomicInteger atomicInteger = new AtomicInteger();
                try {
                    if (!this.mService.showSessionForActiveService(new Bundle(), 0, null, new IVoiceInteractionSessionShowCallback.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand.1
                        public final void onFailed() {
                            Slog.w("VoiceInteractionManager", "onFailed()");
                            outPrintWriter.println("callback failed");
                            atomicInteger.set(1);
                            countDownLatch.countDown();
                        }

                        public final void onShown() {
                            Slog.d("VoiceInteractionManager", "onShown()");
                            atomicInteger.set(0);
                            countDownLatch.countDown();
                        }
                    }, null)) {
                        outPrintWriter.println("showSessionForActiveService() returned false");
                    } else if (!countDownLatch.await(5000L, TimeUnit.MILLISECONDS)) {
                        outPrintWriter.printf("Callback not called in %d ms\n", 5000L);
                    }
                } catch (Exception e3) {
                    handleError(outPrintWriter, e3, "showSessionForActiveService()");
                }
                break;
            case "disable":
                boolean parseBoolean2 = Boolean.parseBoolean(getNextArgRequired());
                Slog.i("VoiceInteractionManager", "requestDisable(): " + parseBoolean2);
                try {
                    this.mService.setDisabled(parseBoolean2);
                    break;
                } catch (Exception e4) {
                    handleError(outPrintWriter, e4, "requestDisable()");
                    return 1;
                }
            case "restart-detection":
                Slog.i("VoiceInteractionManager", "requestRestartDetection()");
                try {
                    this.mService.forceRestartHotwordDetector();
                    break;
                } catch (Exception e5) {
                    handleError(outPrintWriter, e5, "requestRestartDetection()");
                    return 1;
                }
        }
        return handleDefaultCommands(str);
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("VoiceInteraction Service (voiceinteraction) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  show");
            outPrintWriter.println("    Shows a session for the active service");
            outPrintWriter.println("");
            outPrintWriter.println("  hide");
            outPrintWriter.println("    Hides the current session");
            outPrintWriter.println("");
            outPrintWriter.println("  disable [true|false]");
            outPrintWriter.println("    Temporarily disable (when true) service");
            outPrintWriter.println("");
            outPrintWriter.println("  restart-detection");
            outPrintWriter.println("    Force a restart of a hotword detection service");
            outPrintWriter.println("");
            outPrintWriter.println("  set-debug-hotword-logging [true|false]");
            outPrintWriter.println("    Temporarily enable or disable debug logging for hotword result.");
            outPrintWriter.println("    The debug logging will be reset after one hour from last enable.");
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
