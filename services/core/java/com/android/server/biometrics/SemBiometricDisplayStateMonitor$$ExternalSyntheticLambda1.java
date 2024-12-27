package com.android.server.biometrics;

import android.util.Slog;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemBiometricDisplayStateMonitor f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda1(
            SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor,
            int i,
            int i2,
            int i3,
            int i4) {
        this.$r8$classId = i4;
        this.f$0 = semBiometricDisplayStateMonitor;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                semBiometricDisplayStateMonitor.getClass();
                Slog.i(
                        "SemBiometricDisplayStateMonitor",
                        "DisplayStateListener#onFinish : " + i + ", " + i2);
                semBiometricDisplayStateMonitor.mLogicalDisplayState = i;
                semBiometricDisplayStateMonitor.mPhysicalDisplayState = i2;
                Iterator it =
                        ((CopyOnWriteArrayList)
                                        semBiometricDisplayStateMonitor.mDisplayStateCallbacks)
                                .iterator();
                while (it.hasNext()) {
                    ((SemBiometricDisplayStateMonitor.DisplayStateCallback) it.next())
                            .onFinishDisplayState(i, i2, i3);
                }
                break;
            default:
                SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor2 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                semBiometricDisplayStateMonitor2.getClass();
                Slog.i(
                        "SemBiometricDisplayStateMonitor",
                        "DisplayStateListener#onStart : " + i4 + ", " + i5);
                semBiometricDisplayStateMonitor2.mLogicalDisplayState = i4;
                Iterator it2 =
                        ((CopyOnWriteArrayList)
                                        semBiometricDisplayStateMonitor2.mDisplayStateCallbacks)
                                .iterator();
                while (it2.hasNext()) {
                    SemBiometricDisplayStateMonitor.DisplayStateCallback displayStateCallback =
                            (SemBiometricDisplayStateMonitor.DisplayStateCallback) it2.next();
                    displayStateCallback.onStartDisplayState(i4, i5, i6);
                    if (i4 == 2) {
                        displayStateCallback.onDisplayOn();
                    } else if (i4 == 1) {
                        displayStateCallback.onDisplayOff();
                    }
                }
                break;
        }
    }
}
