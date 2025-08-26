package com.android.wm.shell;

import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes3.dex */
public class ProtoLogController implements ShellCommandHandler.ShellCommandActionHandler {
    public final ShellCommandHandler mShellCommandHandler;
    public final IProtoLog mShellProtoLog;

    public ProtoLogController(ShellInit shellInit, ShellCommandHandler shellCommandHandler) {
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.ProtoLogController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProtoLogController protoLogController = this.f$0;
                protoLogController.mShellCommandHandler.addCommandCallback("protolog", protoLogController, protoLogController);
            }
        }, this);
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellProtoLog = ProtoLogImpl_1771455215.getSingleInstance();
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(final PrintWriter printWriter, String[] strArr) {
        ILogger iLogger;
        Objects.requireNonNull(printWriter);
        iLogger = new ILogger() { // from class: com.android.wm.shell.ProtoLogController$$ExternalSyntheticLambda1
            public final void log(String str) {
                printWriter.println(str);
            }
        };
        String str = strArr[0];
        str.getClass();
        switch (str) {
            case "enable-text":
                String[] strArr2 = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                if (this.mShellProtoLog.startLoggingToLogcat(strArr2, iLogger) == 0) {
                    CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Starting logging on groups: "), Arrays.toString(strArr2), printWriter);
                    break;
                }
                break;
            case "enable":
                if (this.mShellProtoLog.startLoggingToLogcat((String[]) Arrays.copyOfRange(strArr, 1, strArr.length), iLogger) == 0) {
                }
                break;
            case "disable-text":
                String[] strArr3 = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
                if (this.mShellProtoLog.stopLoggingToLogcat(strArr3, iLogger) == 0) {
                    CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Stopping logging on groups: "), Arrays.toString(strArr3), printWriter);
                    break;
                }
                break;
            case "status":
                printWriter.println("(Deprecated) legacy command. Use Perfetto commands instead.");
                break;
            case "save-for-bugreport":
                printWriter.println("(Deprecated) legacy command");
                break;
            case "stop":
                printWriter.println("(Deprecated) legacy command. Use Perfetto commands instead.");
                break;
            case "start":
                printWriter.println("(Deprecated) legacy command. Use Perfetto commands instead.");
                break;
            case "disable":
                if (this.mShellProtoLog.stopLoggingToLogcat((String[]) Arrays.copyOfRange(strArr, 1, strArr.length), iLogger) == 0) {
                }
                break;
            default:
                CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
                printShellCommandHelp(printWriter, "");
                break;
        }
        return false;
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
