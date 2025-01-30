package com.android.server.aod;

import android.content.Context;
import android.content.Intent;
import android.os.ShellCommand;
import android.os.UserHandle;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class AODManagerShellCommand extends ShellCommand {
    public Context mContext;

    public final String infoToString(int i) {
        switch (i) {
            case 8:
                return "tap to show(8)";
            case 9:
                return "press(9)";
            case 10:
                return "long pres(10)";
            case 11:
                return "double tap(11)";
            default:
                return "";
        }
    }

    public AODManagerShellCommand(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0050 A[Catch: Exception -> 0x0054, TRY_LEAVE, TryCatch #0 {Exception -> 0x0054, blocks: (B:7:0x0008, B:15:0x0033, B:17:0x004c, B:18:0x0050, B:19:0x0019, B:22:0x0023), top: B:6:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onCommand(String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            int hashCode = str.hashCode();
            if (hashCode == 115153) {
                if (str.equals("tsp")) {
                    z = false;
                    if (z) {
                    }
                    return 0;
                }
                z = -1;
                if (z) {
                }
                return 0;
            }
            if (hashCode == 3198785 && str.equals("help")) {
                z = true;
                if (z) {
                    runTspEvent();
                } else if (z) {
                    onHelp();
                } else {
                    getErrPrintWriter().println("Unknown command: " + str);
                }
                return 0;
            }
            z = -1;
            if (z) {
            }
            return 0;
        } catch (Exception e) {
            getErrPrintWriter().println("Error while executing command: " + str);
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("aod service commands:");
            outPrintWriter.println("");
            outPrintWriter.println("NOTE: when aod is shown, the command should should be executed, and -user USER_ID is optional argument.");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  tsp [ACTION_INFO] [-x X_POSITION] [-y Y_POSITION] [-user USER_ID]");
            outPrintWriter.println("    Send tsp event with the below arguments.");
            outPrintWriter.println("      ACTION_INFO");
            outPrintWriter.println("          8  : tap to show");
            outPrintWriter.println("          9  : press");
            outPrintWriter.println("          10 : long press");
            outPrintWriter.println("          11 : double tap");
            outPrintWriter.println("      X_POSITION : x position with integer value");
            outPrintWriter.println("      Y_POSITION : x position with integer value");
            outPrintWriter.println("      USER_ID : user id (optional argument");
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

    public final void runTspEvent() {
        int parseInt = Integer.parseInt(getNextArgRequired());
        UserHandle userHandle = UserHandle.CURRENT;
        switch (parseInt) {
            case 8:
            case 9:
            case 10:
            case 11:
                int i = 0;
                int i2 = 0;
                while (true) {
                    String nextOption = getNextOption();
                    if (nextOption != null) {
                        if ("-x".equals(nextOption)) {
                            i = Integer.parseInt(getNextArgRequired());
                        } else if ("-y".equals(nextOption)) {
                            i2 = Integer.parseInt(getNextArgRequired());
                        } else if ("-user".equals(nextOption)) {
                            userHandle = new UserHandle(Integer.parseInt(getNextArgRequired()));
                        } else {
                            getErrPrintWriter().println("Unknown option: " + nextOption);
                            throw new IllegalArgumentException();
                        }
                    } else {
                        getOutPrintWriter().println("aod service commands: runTspEvent : info: " + infoToString(parseInt) + " , x: " + i + " , y: " + i2 + " , userId: " + userHandle.toString());
                        Intent intent = new Intent("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                        intent.putExtra("info", parseInt);
                        intent.putExtra("location", new float[]{(float) i, (float) i2});
                        intent.addFlags(32);
                        this.mContext.sendBroadcastAsUser(intent, userHandle, "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER");
                        return;
                    }
                }
            default:
                getErrPrintWriter().println("Unknown ACTION_INFO: " + parseInt);
                throw new IllegalArgumentException();
        }
    }
}
