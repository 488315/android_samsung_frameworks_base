package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.biometrics.SemBiometricDisplayStateMonitor;
import com.android.server.biometrics.SemBiometricFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpEnrollSessionMonitor implements SemBiometricDisplayStateMonitor.DisplayStateCallback {
    public final SemBiometricDisplayStateMonitor mDisplayStateMonitor;
    public final SparseArray mEnrollSessions = new SparseArray();

    public SemFpEnrollSessionMonitor(SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
        this.mDisplayStateMonitor = semBiometricDisplayStateMonitor;
    }

    public final boolean isEnrollSession(int i) {
        boolean z;
        synchronized (this.mEnrollSessions) {
            z = this.mEnrollSessions.get(i) != null;
        }
        return z;
    }

    @Override // com.android.server.biometrics.SemBiometricDisplayStateMonitor.DisplayStateCallback
    public final void onDisplayOff() {
        if (SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD) {
            return;
        }
        synchronized (this.mEnrollSessions) {
            for (int i = 0; i < this.mEnrollSessions.size(); i++) {
                try {
                    Pair pair = (Pair) this.mEnrollSessions.valueAt(i);
                    Slog.i("FingerprintService", "Revoke challenge due to screen off, " + this.mEnrollSessions.keyAt(i));
                    ((Runnable) pair.first).run();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void revokeChallenge(int i) {
        synchronized (this.mEnrollSessions) {
            try {
                Pair pair = (Pair) this.mEnrollSessions.get(i);
                if (pair != null) {
                    ((Runnable) pair.first).run();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
