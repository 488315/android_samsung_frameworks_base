package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.ShellCommand;
import android.util.Pair;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.AuthenticationStatsCollector;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BiometricNotificationImpl;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FaceShellCommand extends ShellCommand {
    public final FaceService mService;

    public FaceShellCommand(FaceService faceService) {
        this.mService = faceService;
    }

    public final void doNotify() {
        FaceService faceService = this.mService;
        Context context = faceService.getContext();
        boolean z = Utils.DEBUG;
        if (Binder.getCallingUid() != 2000) {
            Utils.checkPermission(context, "com.samsung.android.bio.face.permission.MANAGE_FACE");
        }
        if (Build.IS_DEBUGGABLE) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Pair singleProvider = faceService.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    AuthenticationStatsCollector authenticationStatsCollector =
                            ((FaceProvider) singleProvider.second).mAuthenticationStatsCollector;
                    BiometricNotificationImpl biometricNotificationImpl =
                            authenticationStatsCollector.mBiometricNotification;
                    Context context2 = authenticationStatsCollector.mContext;
                    biometricNotificationImpl.getClass();
                    BiometricNotificationImpl.sendFaceEnrollNotification(context2);
                } else {
                    Slog.w("FaceService", "Null provider for notification");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075 A[Catch: Exception -> 0x002b, TRY_LEAVE, TryCatch #0 {Exception -> 0x002b, blocks: (B:8:0x000a, B:19:0x004a, B:21:0x0056, B:23:0x005a, B:26:0x0071, B:28:0x006e, B:29:0x0075, B:31:0x0020, B:34:0x002d, B:37:0x0038), top: B:7:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "Unrecognized command: "
            r1 = 1
            if (r8 != 0) goto L9
            r7.onHelp()
            return r1
        L9:
            r2 = -1
            int r3 = r8.hashCode()     // Catch: java.lang.Exception -> L2b
            r4 = 3198785(0x30cf41, float:4.482453E-39)
            r5 = 2
            r6 = 0
            if (r3 == r4) goto L38
            r4 = 3545755(0x361a9b, float:4.968661E-39)
            if (r3 == r4) goto L2d
            r4 = 595233003(0x237a88eb, float:1.3581521E-17)
            if (r3 == r4) goto L20
            goto L43
        L20:
            java.lang.String r3 = "notification"
            boolean r3 = r8.equals(r3)     // Catch: java.lang.Exception -> L2b
            if (r3 == 0) goto L43
            r3 = r5
            goto L44
        L2b:
            r8 = move-exception
            goto L79
        L2d:
            java.lang.String r3 = "sync"
            boolean r3 = r8.equals(r3)     // Catch: java.lang.Exception -> L2b
            if (r3 == 0) goto L43
            r3 = r1
            goto L44
        L38:
            java.lang.String r3 = "help"
            boolean r3 = r8.equals(r3)     // Catch: java.lang.Exception -> L2b
            if (r3 == 0) goto L43
            r3 = r6
            goto L44
        L43:
            r3 = r2
        L44:
            if (r3 == 0) goto L75
            if (r3 == r1) goto L5a
            if (r3 == r5) goto L56
            java.io.PrintWriter r1 = r7.getOutPrintWriter()     // Catch: java.lang.Exception -> L2b
            java.lang.String r8 = r0.concat(r8)     // Catch: java.lang.Exception -> L2b
            r1.println(r8)     // Catch: java.lang.Exception -> L2b
            goto L8e
        L56:
            r7.doNotify()     // Catch: java.lang.Exception -> L2b
            return r6
        L5a:
            com.android.server.biometrics.sensors.face.FaceService r8 = r7.mService     // Catch: java.lang.Exception -> L2b
            android.content.Context r8 = r8.getContext()     // Catch: java.lang.Exception -> L2b
            java.lang.String r0 = "com.samsung.android.bio.face.permission.MANAGE_FACE"
            boolean r1 = com.android.server.biometrics.Utils.DEBUG     // Catch: java.lang.Exception -> L2b
            int r1 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L2b
            r3 = 2000(0x7d0, float:2.803E-42)
            if (r1 != r3) goto L6e
            goto L71
        L6e:
            com.android.server.biometrics.Utils.checkPermission(r8, r0)     // Catch: java.lang.Exception -> L2b
        L71:
            com.android.server.biometrics.Flags.faceVhalFeature()     // Catch: java.lang.Exception -> L2b
            return r6
        L75:
            r7.onHelp()     // Catch: java.lang.Exception -> L2b
            return r6
        L79:
            java.io.PrintWriter r7 = r7.getOutPrintWriter()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Exception: "
            r0.<init>(r1)
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.println(r8)
        L8e:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.face.FaceShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Face Service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  sync");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Sync enrollments now (virtualized sensors only).",
                "  notification",
                "     Sends a Face re-enrollment notification");
    }
}
