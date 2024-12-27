package com.android.server.webkit;

import android.os.ShellCommand;
import android.webkit.IWebViewUpdateService;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WebViewUpdateServiceShellCommand extends ShellCommand {
    public final IWebViewUpdateService mInterface;

    public WebViewUpdateServiceShellCommand(IWebViewUpdateService iWebViewUpdateService) {
        this.mInterface = iWebViewUpdateService;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006c A[Catch: RemoteException -> 0x002d, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x002d, blocks: (B:7:0x000c, B:19:0x004d, B:21:0x0052, B:23:0x005f, B:25:0x006c, B:27:0x0023, B:30:0x002f, B:33:0x003a), top: B:6:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L7
            int r7 = r7.handleDefaultCommands(r8)
            return r7
        L7:
            java.io.PrintWriter r0 = r7.getOutPrintWriter()
            r1 = -1
            int r2 = r8.hashCode()     // Catch: android.os.RemoteException -> L2d
            r3 = -1857752288(0xffffffff9144f320, float:-1.5536592E-28)
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == r3) goto L3a
            r3 = -1381305903(0xffffffffadaaf1d1, float:-1.943415E-11)
            if (r2 == r3) goto L2f
            r3 = 436183515(0x19ffa1db, float:2.6431755E-23)
            if (r2 == r3) goto L23
            goto L44
        L23:
            java.lang.String r2 = "disable-multiprocess"
            boolean r2 = r8.equals(r2)     // Catch: android.os.RemoteException -> L2d
            if (r2 == 0) goto L44
            r2 = r4
            goto L45
        L2d:
            r7 = move-exception
            goto L71
        L2f:
            java.lang.String r2 = "set-webview-implementation"
            boolean r2 = r8.equals(r2)     // Catch: android.os.RemoteException -> L2d
            if (r2 == 0) goto L44
            r2 = r6
            goto L45
        L3a:
            java.lang.String r2 = "enable-multiprocess"
            boolean r2 = r8.equals(r2)     // Catch: android.os.RemoteException -> L2d
            if (r2 == 0) goto L44
            r2 = r5
            goto L45
        L44:
            r2 = r1
        L45:
            if (r2 == 0) goto L6c
            java.lang.String r3 = "Success"
            if (r2 == r5) goto L5f
            if (r2 == r4) goto L52
            int r7 = r7.handleDefaultCommands(r8)     // Catch: android.os.RemoteException -> L2d
            return r7
        L52:
            java.io.PrintWriter r8 = r7.getOutPrintWriter()     // Catch: android.os.RemoteException -> L2d
            android.webkit.IWebViewUpdateService r7 = r7.mInterface     // Catch: android.os.RemoteException -> L2d
            r7.enableMultiProcess(r6)     // Catch: android.os.RemoteException -> L2d
            r8.println(r3)     // Catch: android.os.RemoteException -> L2d
            return r6
        L5f:
            java.io.PrintWriter r8 = r7.getOutPrintWriter()     // Catch: android.os.RemoteException -> L2d
            android.webkit.IWebViewUpdateService r7 = r7.mInterface     // Catch: android.os.RemoteException -> L2d
            r7.enableMultiProcess(r5)     // Catch: android.os.RemoteException -> L2d
            r8.println(r3)     // Catch: android.os.RemoteException -> L2d
            return r6
        L6c:
            int r7 = r7.setWebViewImplementation()     // Catch: android.os.RemoteException -> L2d
            return r7
        L71:
            java.lang.String r8 = "Remote exception: "
            com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0.m(r8, r7, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.webkit.WebViewUpdateServiceShellCommand.onCommand(java.lang.String):int");
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
        outPrintWriter.println("  enable-multiprocess");
        outPrintWriter.println("    Enable multi-process mode for WebView");
        outPrintWriter.println("  disable-multiprocess");
        outPrintWriter.println("    Disable multi-process mode for WebView");
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
        outPrintWriter.println(XmlUtils$$ExternalSyntheticOutline0.m("Failed to switch to ", nextArg, ", the WebView implementation is now provided by ", changeProviderAndSetting, "."));
        return 1;
    }
}
