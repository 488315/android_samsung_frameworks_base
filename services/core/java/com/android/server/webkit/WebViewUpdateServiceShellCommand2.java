package com.android.server.webkit;

import android.os.RemoteException;
import android.os.ShellCommand;
import android.text.TextUtils;
import android.webkit.IWebViewUpdateService;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WebViewUpdateServiceShellCommand2 extends ShellCommand {
    public final IWebViewUpdateService mInterface;

    public WebViewUpdateServiceShellCommand2(IWebViewUpdateService iWebViewUpdateService) {
        this.mInterface = iWebViewUpdateService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            if (str.hashCode() == -1381305903 && str.equals("set-webview-implementation")) {
                return setWebViewImplementation();
            }
            return handleDefaultCommands(str);
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, outPrintWriter);
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("WebView updater commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        PrintWriter outPrintWriter2 = getOutPrintWriter();
        outPrintWriter2.println("  set-webview-implementation PACKAGE");
        outPrintWriter2.println("    Set the WebView implementation to the specified package.");
        outPrintWriter.println();
    }

    public final int setWebViewImplementation() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Failed to switch, no PACKAGE provided.");
            outPrintWriter.println("");
            PrintWriter outPrintWriter2 = getOutPrintWriter();
            outPrintWriter2.println("  set-webview-implementation PACKAGE");
            outPrintWriter2.println("    Set the WebView implementation to the specified package.");
            return 1;
        }
        String changeProviderAndSetting = this.mInterface.changeProviderAndSetting(nextArg);
        if (nextArg.equals(changeProviderAndSetting)) {
            outPrintWriter.println("Success");
            return 0;
        }
        outPrintWriter.println(
                TextUtils.formatSimple(
                        "Failed to switch to %s, the WebView implementation is now provided by %s.",
                        new Object[] {nextArg, changeProviderAndSetting}));
        return 1;
    }
}
