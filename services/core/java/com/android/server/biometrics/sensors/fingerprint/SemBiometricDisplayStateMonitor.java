package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemBiometricDisplayStateMonitor extends SemBiometricDisplayMonitor {
    public boolean mIsChangingPhysicalState;
    public int mLogicalDisplayState;
    public int mPhysicalDisplayState;
    public Runnable mRunnableLocalHbmOff;

    public SemBiometricDisplayStateMonitor(Context context, Handler handler) {
        super(context, handler);
        this.mLogicalDisplayState = 2;
        this.mPhysicalDisplayState = 2;
        this.mIsChangingPhysicalState = false;
    }

    public void start() {
        this.mDisplayManagerInternal.registerDisplayStateListener(this);
    }

    public int getLogicalDisplayState() {
        return this.mLogicalDisplayState;
    }

    public int getPhysicalDisplayState() {
        return this.mPhysicalDisplayState;
    }

    public boolean isChangingPhysicalState() {
        return this.mIsChangingPhysicalState;
    }

    public boolean isInteractive() {
        return ((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor
    public void onStart(final int i, final int i2, final int i3) {
        Runnable runnable;
        if (i3 != 1) {
            return;
        }
        if (SemBiometricFeature.FP_FEATURE_SUPPORT_LOCAL_HBM && i2 != 2 && (runnable = this.mRunnableLocalHbmOff) != null) {
            runnable.run();
        }
        if (this.mPhysicalDisplayState != i2) {
            this.mIsChangingPhysicalState = true;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricDisplayStateMonitor.this.lambda$onStart$0(i, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(int i, int i2, int i3) {
        Slog.i("SemBiometricDisplayStateMonitor", "DisplayStateListener#onStart : " + i + ", " + i2);
        this.mLogicalDisplayState = i;
        for (SemBiometricDisplayMonitor.Callback callback : this.mCallbacks) {
            if (i == 2) {
                callback.onDisplayOn();
            } else if (i == 1) {
                callback.onDisplayOff();
            }
            callback.onStartDisplayState(i, i2, i3);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor
    public void onFinish(final int i, final int i2, final int i3) {
        if (i3 != 1) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricDisplayStateMonitor.this.lambda$onFinish$1(i, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinish$1(int i, int i2, int i3) {
        Slog.i("SemBiometricDisplayStateMonitor", "DisplayStateListener#onFinish : " + i + ", " + i2);
        this.mLogicalDisplayState = i;
        if (this.mPhysicalDisplayState != i2 && this.mIsChangingPhysicalState) {
            this.mIsChangingPhysicalState = false;
        }
        this.mPhysicalDisplayState = i2;
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((SemBiometricDisplayMonitor.Callback) it.next()).onFinishDisplayState(i, i2, i3);
        }
    }
}
