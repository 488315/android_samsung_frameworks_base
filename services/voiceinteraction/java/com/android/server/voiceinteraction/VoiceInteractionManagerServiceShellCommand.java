package com.android.server.voiceinteraction;

import android.os.Bundle;
import android.os.ShellCommand;
import android.util.Slog;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public final class VoiceInteractionManagerServiceShellCommand extends ShellCommand {
  public final VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mService;

  public VoiceInteractionManagerServiceShellCommand(
      VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
          voiceInteractionManagerServiceStub) {
    this.mService = voiceInteractionManagerServiceStub;
  }

  public int onCommand(String str) {
    if (str == null) {
      return handleDefaultCommands(str);
    }
    PrintWriter outPrintWriter = getOutPrintWriter();
    switch (str) {
    }
    return handleDefaultCommands(str);
  }

  public void onHelp() {
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
      outPrintWriter.println(
          "    The debug logging will be reset after one hour from last enable.");
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

  public final int requestShow(final PrintWriter printWriter) {
    Slog.i("VoiceInteractionManager", "requestShow()");
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final AtomicInteger atomicInteger = new AtomicInteger();
    try {
      if (!this.mService.showSessionForActiveService(
          new Bundle(),
          0,
          null,
          new IVoiceInteractionSessionShowCallback.Stub() { // from class:
            // com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand.1
            public void onFailed() {
              Slog.w("VoiceInteractionManager", "onFailed()");
              printWriter.println("callback failed");
              atomicInteger.set(1);
              countDownLatch.countDown();
            }

            public void onShown() {
              Slog.d("VoiceInteractionManager", "onShown()");
              atomicInteger.set(0);
              countDownLatch.countDown();
            }
          },
          null)) {
        printWriter.println("showSessionForActiveService() returned false");
        return 1;
      }
      if (countDownLatch.await(5000L, TimeUnit.MILLISECONDS)) {
        return 0;
      }
      printWriter.printf("Callback not called in %d ms\n", 5000L);
      return 1;
    } catch (Exception e) {
      return handleError(printWriter, "showSessionForActiveService()", e);
    }
  }

  public final int requestHide(PrintWriter printWriter) {
    Slog.i("VoiceInteractionManager", "requestHide()");
    try {
      this.mService.hideCurrentSession();
      return 0;
    } catch (Exception e) {
      return handleError(printWriter, "requestHide()", e);
    }
  }

  public final int requestDisable(PrintWriter printWriter) {
    boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
    Slog.i("VoiceInteractionManager", "requestDisable(): " + parseBoolean);
    try {
      this.mService.setDisabled(parseBoolean);
      return 0;
    } catch (Exception e) {
      return handleError(printWriter, "requestDisable()", e);
    }
  }

  public final int requestRestartDetection(PrintWriter printWriter) {
    Slog.i("VoiceInteractionManager", "requestRestartDetection()");
    try {
      this.mService.forceRestartHotwordDetector();
      return 0;
    } catch (Exception e) {
      return handleError(printWriter, "requestRestartDetection()", e);
    }
  }

  public final int setDebugHotwordLogging(PrintWriter printWriter) {
    boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
    Slog.i("VoiceInteractionManager", "setDebugHotwordLogging(): " + parseBoolean);
    try {
      this.mService.setDebugHotwordLogging(parseBoolean);
      return 0;
    } catch (Exception e) {
      return handleError(printWriter, "setDebugHotwordLogging()", e);
    }
  }

  public static int handleError(PrintWriter printWriter, String str, Exception exc) {
    Slog.e("VoiceInteractionManager", "error calling " + str, exc);
    printWriter.printf("Error calling %s: %s\n", str, exc);
    return 1;
  }
}
