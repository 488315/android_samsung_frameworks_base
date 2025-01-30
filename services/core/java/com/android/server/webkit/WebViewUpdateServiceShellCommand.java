package com.android.server.webkit;

import android.os.RemoteException;
import android.os.ShellCommand;
import android.webkit.IWebViewUpdateService;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class WebViewUpdateServiceShellCommand extends ShellCommand {
  public final IWebViewUpdateService mInterface;

  public WebViewUpdateServiceShellCommand(IWebViewUpdateService iWebViewUpdateService) {
    this.mInterface = iWebViewUpdateService;
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x0045  */
  /* JADX WARN: Removed duplicated region for block: B:24:0x0058 A[Catch: RemoteException -> 0x005d, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x005d, blocks: (B:7:0x000c, B:18:0x0049, B:20:0x004e, B:22:0x0053, B:24:0x0058, B:26:0x0023, B:29:0x002d, B:32:0x0038), top: B:6:0x000c }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int onCommand(String str) {
    char c;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    PrintWriter outPrintWriter = getOutPrintWriter();
    try {
      int hashCode = str.hashCode();
      if (hashCode == -1857752288) {
        if (str.equals("enable-multiprocess")) {
          c = 1;
          if (c != 0) {}
        }
        c = 65535;
        if (c != 0) {}
      } else if (hashCode != -1381305903) {
        if (hashCode == 436183515 && str.equals("disable-multiprocess")) {
          c = 2;
          if (c != 0) {
            return setWebViewImplementation();
          }
          if (c == 1) {
            return enableMultiProcess(true);
          }
          if (c == 2) {
            return enableMultiProcess(false);
          }
          return handleDefaultCommands(str);
        }
        c = 65535;
        if (c != 0) {}
      } else {
        if (str.equals("set-webview-implementation")) {
          c = 0;
          if (c != 0) {}
        }
        c = 65535;
        if (c != 0) {}
      }
    } catch (RemoteException e) {
      outPrintWriter.println("Remote exception: " + e);
      return -1;
    }
  }

  public final int setWebViewImplementation() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    String nextArg = getNextArg();
    if (nextArg == null) {
      outPrintWriter.println("Failed to switch, no PACKAGE provided.");
      outPrintWriter.println("");
      helpSetWebViewImplementation();
      return 1;
    }
    String changeProviderAndSetting = this.mInterface.changeProviderAndSetting(nextArg);
    if (nextArg.equals(changeProviderAndSetting)) {
      outPrintWriter.println("Success");
      return 0;
    }
    outPrintWriter.println(
        String.format(
            "Failed to switch to %s, the WebView implementation is now provided by %s.",
            nextArg, changeProviderAndSetting));
    return 1;
  }

  public final int enableMultiProcess(boolean z) {
    PrintWriter outPrintWriter = getOutPrintWriter();
    this.mInterface.enableMultiProcess(z);
    outPrintWriter.println("Success");
    return 0;
  }

  public void helpSetWebViewImplementation() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("  set-webview-implementation PACKAGE");
    outPrintWriter.println("    Set the WebView implementation to the specified package.");
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("WebView updater commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help text.");
    outPrintWriter.println("");
    helpSetWebViewImplementation();
    outPrintWriter.println("  enable-multiprocess");
    outPrintWriter.println("    Enable multi-process mode for WebView");
    outPrintWriter.println("  disable-multiprocess");
    outPrintWriter.println("    Disable multi-process mode for WebView");
    outPrintWriter.println();
  }
}
