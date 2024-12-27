package com.android.server.biometrics.sensors.fingerprint;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Build;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.util.Pair;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.AuthenticationStatsCollector;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationImpl;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintShellCommand extends ShellCommand {
    public final FingerprintService mService;

    public FingerprintShellCommand(FingerprintService fingerprintService) {
        this.mService = fingerprintService;
    }

    public final void doNotify() {
        FingerprintService fingerprintService = this.mService;
        Context context = fingerprintService.getContext();
        boolean z = Utils.DEBUG;
        if (Binder.getCallingUid() != 2000) {
            Utils.checkPermission(context, "android.permission.MANAGE_FINGERPRINT");
        }
        if (Build.IS_DEBUGGABLE) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Pair singleProvider = fingerprintService.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    AuthenticationStatsCollector authenticationStatsCollector =
                            ((FingerprintProvider) singleProvider.second)
                                    .mAuthenticationStatsCollector;
                    BiometricNotificationImpl biometricNotificationImpl =
                            authenticationStatsCollector.mBiometricNotification;
                    Context context2 = authenticationStatsCollector.mContext;
                    biometricNotificationImpl.getClass();
                    BiometricNotificationImpl.sendFpEnrollNotification(context2);
                } else {
                    Slog.w("FingerprintService", "Null provider for notification");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void doSimulateVhalFingerDown() {
        FingerprintService fingerprintService = this.mService;
        if (Utils.isFingerprintVirtualEnabled(fingerprintService.getContext())) {
            Slog.i("FingerprintService", "Simulate virtual HAL finger down event");
            Pair singleProvider = fingerprintService.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                ((FingerprintProvider) ((ServiceProvider) singleProvider.second))
                        .simulateVhalFingerDown(
                                UserHandle.getCallingUserId(),
                                ((Integer) singleProvider.first).intValue());
            }
        }
    }

    public final void doSync() {
        FingerprintService fingerprintService = this.mService;
        Context context = fingerprintService.getContext();
        boolean z = Utils.DEBUG;
        if (Binder.getCallingUid() != 2000) {
            Utils.checkPermission(context, "android.permission.MANAGE_FINGERPRINT");
        }
        if (Utils.isFingerprintVirtualEnabled(fingerprintService.getContext())) {
            Slog.i("FingerprintService", "Sync virtual enrollments");
            int currentUser = ActivityManager.getCurrentUser();
            FingerprintServiceRegistry fingerprintServiceRegistry = fingerprintService.mRegistry;
            final CountDownLatch countDownLatch =
                    new CountDownLatch(fingerprintServiceRegistry.getProviders().size());
            Iterator it = fingerprintServiceRegistry.getProviders().iterator();
            while (it.hasNext()) {
                FingerprintProvider fingerprintProvider =
                        (FingerprintProvider) ((ServiceProvider) it.next());
                Iterator it2 = ((ArrayList) fingerprintProvider.getSensorProperties()).iterator();
                while (it2.hasNext()) {
                    fingerprintProvider.scheduleInternalCleanup(
                            ((FingerprintSensorPropertiesInternal) it2.next()).sensorId,
                            currentUser,
                            new ClientMonitorCallback() { // from class:
                                                          // com.android.server.biometrics.sensors.fingerprint.FingerprintService.3
                                public final /* synthetic */ CountDownLatch val$latch;

                                public AnonymousClass3(final CountDownLatch countDownLatch2) {
                                    r1 = countDownLatch2;
                                }

                                @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                                public final void onClientFinished(
                                        BaseClientMonitor baseClientMonitor, boolean z2) {
                                    r1.countDown();
                                    if (z2) {
                                        return;
                                    }
                                    Slog.e("FingerprintService", "Sync virtual enrollments failed");
                                }
                            });
                }
            }
            try {
                countDownLatch2.await(3L, TimeUnit.SECONDS);
            } catch (Exception e) {
                Slog.e("FingerprintService", "Failed to wait for sync finishing", e);
            }
        }
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            onHelp();
            return 1;
        }
        try {
            switch (str.hashCode()) {
                case -1014576245:
                    if (str.equals("fingerdown")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3545755:
                    if (str.equals("sync")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 595233003:
                    if (str.equals("notification")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            getOutPrintWriter().println("Exception: " + e);
        }
        if (c == 0) {
            onHelp();
            return 0;
        }
        if (c == 1) {
            doSync();
            return 0;
        }
        if (c == 2) {
            doSimulateVhalFingerDown();
            return 0;
        }
        if (c != 3) {
            getOutPrintWriter().println("Unrecognized command: ".concat(str));
            return -1;
        }
        doNotify();
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Fingerprint Service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  sync");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Sync enrollments now (virtualized sensors only).",
                "  fingerdown",
                "      Simulate finger down event (virtualized sensors only).",
                "  notification");
        outPrintWriter.println("     Sends a Fingerprint re-enrollment notification");
    }
}
