package com.android.server.aod;

import android.content.Context;
import android.content.Intent;
import android.os.ShellCommand;
import android.os.UserHandle;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AODManagerShellCommand extends ShellCommand {
    public Context mContext;

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048 A[Catch: Exception -> 0x0026, TRY_LEAVE, TryCatch #0 {Exception -> 0x0026, blocks: (B:8:0x000a, B:16:0x0038, B:18:0x0044, B:19:0x0048, B:20:0x001b, B:23:0x0028), top: B:7:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = "Unknown command: "
            if (r7 != 0) goto L9
            int r6 = r6.handleDefaultCommands(r7)
            return r6
        L9:
            r1 = -1
            int r2 = r7.hashCode()     // Catch: java.lang.Exception -> L26
            r3 = 115153(0x1c1d1, float:1.61364E-40)
            r4 = 0
            r5 = 1
            if (r2 == r3) goto L28
            r3 = 3198785(0x30cf41, float:4.482453E-39)
            if (r2 == r3) goto L1b
            goto L33
        L1b:
            java.lang.String r2 = "help"
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Exception -> L26
            if (r2 == 0) goto L33
            r2 = r5
            goto L34
        L26:
            r0 = move-exception
            goto L4c
        L28:
            java.lang.String r2 = "tsp"
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Exception -> L26
            if (r2 == 0) goto L33
            r2 = r4
            goto L34
        L33:
            r2 = r1
        L34:
            if (r2 == 0) goto L48
            if (r2 == r5) goto L44
            java.io.PrintWriter r2 = r6.getErrPrintWriter()     // Catch: java.lang.Exception -> L26
            java.lang.String r0 = r0.concat(r7)     // Catch: java.lang.Exception -> L26
            r2.println(r0)     // Catch: java.lang.Exception -> L26
            goto L4b
        L44:
            r6.onHelp()     // Catch: java.lang.Exception -> L26
            goto L4b
        L48:
            r6.runTspEvent()     // Catch: java.lang.Exception -> L26
        L4b:
            return r4
        L4c:
            java.io.PrintWriter r2 = r6.getErrPrintWriter()
            java.lang.String r3 = "Error while executing command: "
            java.lang.String r7 = r3.concat(r7)
            r2.println(r7)
            java.io.PrintWriter r6 = r6.getErrPrintWriter()
            r0.printStackTrace(r6)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.aod.AODManagerShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("aod service commands:");
            outPrintWriter.println("");
            outPrintWriter.println(
                    "NOTE: when aod is shown, the command should should be executed, and -user"
                        + " USER_ID is optional argument.");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println(
                    "  tsp [ACTION_INFO] [-x X_POSITION] [-y Y_POSITION] [-user USER_ID]");
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
        String str;
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
                    if (nextOption == null) {
                        PrintWriter outPrintWriter = getOutPrintWriter();
                        StringBuilder sb =
                                new StringBuilder("aod service commands: runTspEvent : info: ");
                        switch (parseInt) {
                            case 8:
                                str = "tap to show(8)";
                                break;
                            case 9:
                                str = "press(9)";
                                break;
                            case 10:
                                str = "long pres(10)";
                                break;
                            case 11:
                                str = "double tap(11)";
                                break;
                            default:
                                str = "";
                                break;
                        }
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                i, str, " , x: ", " , y: ", sb);
                        sb.append(i2);
                        sb.append(" , userId: ");
                        sb.append(userHandle.toString());
                        outPrintWriter.println(sb.toString());
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                        intent.putExtra("info", parseInt);
                        intent.putExtra("location", new float[] {i, i2});
                        intent.addFlags(32);
                        this.mContext.sendBroadcastAsUser(
                                intent,
                                userHandle,
                                "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER");
                        return;
                    }
                    if ("-x".equals(nextOption)) {
                        i = Integer.parseInt(getNextArgRequired());
                    } else if ("-y".equals(nextOption)) {
                        i2 = Integer.parseInt(getNextArgRequired());
                    } else {
                        if (!"-user".equals(nextOption)) {
                            getErrPrintWriter().println("Unknown option: ".concat(nextOption));
                            throw new IllegalArgumentException();
                        }
                        userHandle = new UserHandle(Integer.parseInt(getNextArgRequired()));
                    }
                }
            default:
                getErrPrintWriter().println("Unknown ACTION_INFO: " + parseInt);
                throw new IllegalArgumentException();
        }
    }
}
