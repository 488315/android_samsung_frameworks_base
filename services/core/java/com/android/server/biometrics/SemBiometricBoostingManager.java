package com.android.server.biometrics;

import android.content.Context;
import android.content.Intent;
import android.os.CustomFrequencyManager;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

import com.samsung.android.os.SemDvfsManager;

public final class SemBiometricBoostingManager {
    public static SemBiometricBoostingManager sInstance;
    public boolean mIsEnabledSsrm;
    public final SparseArray mDvfsMgrs = new SparseArray();
    public final SparseArray mReleasers = new SparseArray();

    public final class Releaser implements Runnable {
        public final int mBiometricType;
        public final Context mContext;

        public Releaser(Context context, int i) {
            this.mContext = context;
            this.mBiometricType = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            SemBiometricBoostingManager.getInstance().release(this.mContext, this.mBiometricType);
        }
    }

    public static synchronized SemBiometricBoostingManager getInstance() {
        SemBiometricBoostingManager semBiometricBoostingManager;
        synchronized (SemBiometricBoostingManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SemBiometricBoostingManager();
                }
                semBiometricBoostingManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return semBiometricBoostingManager;
    }

    public static void sendBroadcastForBoosting(Context context, String str) {
        try {
            context.sendBroadcastAsUser(new Intent(str), UserHandle.ALL);
            Slog.i("BiometricsBoosting", str);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("sendBroadcastForBoosting: "), "BiometricsBoosting");
        }
    }

    public final synchronized void acquireDvfs(Context context, int i, int i2, String str, int i3) {
        SemDvfsManager semDvfsManager = (SemDvfsManager) this.mDvfsMgrs.get(i2);
        if (semDvfsManager == null) {
            semDvfsManager = SemDvfsManager.createInstance(context, str);
            if (semDvfsManager == null) {
                Slog.i("BiometricsBoosting", "acquireDvfs: can't get SemDvfsManager");
                return;
            } else {
                semDvfsManager.setProcName("biometrics_service");
                semDvfsManager.setHint(i);
                this.mDvfsMgrs.put(i2, semDvfsManager);
            }
        }
        if (Utils.DEBUG) {
            Slog.i("BiometricsBoosting", "acquireDvfs: " + i3);
        }
        semDvfsManager.acquire();
        Releaser releaser = (Releaser) this.mReleasers.get(i2);
        if (releaser == null) {
            releaser = new Releaser(context, i2);
            this.mReleasers.put(i2, releaser);
        } else {
            context.getMainThreadHandler().removeCallbacks(releaser);
        }
        context.getMainThreadHandler().postDelayed(releaser, i3);
        CustomFrequencyManager customFrequencyManager =
                (CustomFrequencyManager) context.getSystemService("CustomFrequencyManagerService");
        if (customFrequencyManager != null) {
            try {
                customFrequencyManager.disableGpisHint();
            } catch (Exception e) {
                Slog.e("BiometricsBoosting", "acquireDvfs: failed to disableGipsHint", e);
            }
        }
        if (!this.mIsEnabledSsrm) {
            sendBroadcastForBoosting(
                    context, "com.samsung.android.intent.action.BIOMETRIC_AUTHENTICATION_START");
            this.mIsEnabledSsrm = true;
        }
        if (Utils.DEBUG) {
            Slog.i(
                    "BiometricsBoosting",
                    "acquireDvfs: "
                            + this.mDvfsMgrs.size()
                            + ", "
                            + this.mReleasers.size()
                            + ", "
                            + this.mIsEnabledSsrm);
        }
    }

    public final synchronized void release(Context context, int i) {
        try {
            SemDvfsManager semDvfsManager = (SemDvfsManager) this.mDvfsMgrs.get(i);
            if (semDvfsManager != null) {
                semDvfsManager.release();
                this.mDvfsMgrs.delete(i);
            }
            if (this.mIsEnabledSsrm && this.mDvfsMgrs.size() == 0) {
                sendBroadcastForBoosting(
                        context, "com.samsung.android.intent.action.BIOMETRIC_AUTHENTICATION_STOP");
                this.mIsEnabledSsrm = false;
            }
            Releaser releaser = (Releaser) this.mReleasers.get(i);
            if (releaser != null) {
                context.getMainThreadHandler().removeCallbacks(releaser);
            }
            this.mReleasers.delete(i);
            if (Utils.DEBUG) {
                Slog.i(
                        "BiometricsBoosting",
                        "release: "
                                + i
                                + ", "
                                + this.mDvfsMgrs.size()
                                + ", "
                                + this.mReleasers.size());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
