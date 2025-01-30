package com.android.p038wm.shell;

import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ProtoLogController implements ShellCommandHandler.ShellCommandActionHandler {
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellProtoLogImpl mShellProtoLog;

    public ProtoLogController(ShellInit shellInit, ShellCommandHandler shellCommandHandler) {
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.ProtoLogController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProtoLogController protoLogController = ProtoLogController.this;
                protoLogController.mShellCommandHandler.addCommandCallback("protolog", protoLogController, protoLogController);
            }
        }, this);
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellProtoLog = ShellProtoLogImpl.getSingleInstance();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        char c;
        String str = strArr[0];
        str.getClass();
        switch (str.hashCode()) {
            case -1475003593:
                if (str.equals("enable-text")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1298848381:
                if (str.equals("enable")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1032071950:
                if (str.equals("disable-text")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -892481550:
                if (str.equals(IMSParameter.CALL.STATUS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -390772652:
                if (str.equals("save-for-bugreport")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (str.equals("stop")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (str.equals(NetworkAnalyticsConstants.DataPoints.OPEN_TIME)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (str.equals("disable")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        ShellProtoLogImpl shellProtoLogImpl = this.mShellProtoLog;
        switch (c) {
            case 0:
                String[] strArr2 = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                if (shellProtoLogImpl.startTextLogging(printWriter, strArr2) != 0) {
                    return false;
                }
                KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Starting logging on groups: "), Arrays.toString(strArr2), printWriter);
                return true;
            case 1:
                return shellProtoLogImpl.startTextLogging(printWriter, (String[]) Arrays.copyOfRange(strArr, 1, strArr.length)) == 0;
            case 2:
                String[] strArr3 = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                if (shellProtoLogImpl.stopTextLogging(printWriter, strArr3) != 0) {
                    return false;
                }
                KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Stopping logging on groups: "), Arrays.toString(strArr3), printWriter);
                return true;
            case 3:
                printWriter.println(shellProtoLogImpl.getStatus());
                return true;
            case 4:
                if (!shellProtoLogImpl.isProtoEnabled()) {
                    printWriter.println("Logging to proto is not enabled for WMShell.");
                    return false;
                }
                shellProtoLogImpl.stopProtoLog(printWriter, true);
                shellProtoLogImpl.startProtoLog(printWriter);
                return true;
            case 5:
                shellProtoLogImpl.stopProtoLog(printWriter, true);
                return true;
            case 6:
                shellProtoLogImpl.startProtoLog(printWriter);
                return true;
            case 7:
                return shellProtoLogImpl.stopTextLogging(printWriter, (String[]) Arrays.copyOfRange(strArr, 1, strArr.length)) == 0;
            default:
                KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
                printShellCommandHelp(printWriter, "");
                return false;
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println(str.concat(IMSParameter.CALL.STATUS));
        printWriter.println(str.concat("  Get current ProtoLog status."));
        printWriter.println(str.concat(NetworkAnalyticsConstants.DataPoints.OPEN_TIME));
        printWriter.println(str.concat("  Start proto logging."));
        printWriter.println(str.concat("stop"));
        printWriter.println(str.concat("  Stop proto logging and flush to file."));
        printWriter.println(str.concat("enable [group...]"));
        printWriter.println(str.concat("  Enable proto logging for given groups."));
        printWriter.println(str.concat("disable [group...]"));
        printWriter.println(str.concat("  Disable proto logging for given groups."));
        printWriter.println(str.concat("enable-text [group...]"));
        printWriter.println(str.concat("  Enable logcat logging for given groups."));
        printWriter.println(str.concat("disable-text [group...]"));
        printWriter.println(str.concat("  Disable logcat logging for given groups."));
        printWriter.println(str.concat("save-for-bugreport"));
        printWriter.println(str.concat("  Flush proto logging to file, only if it's enabled."));
    }
}
