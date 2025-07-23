package android.hardware.biometrics;

import android.hardware.biometrics.IBiometricStateListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class BiometricStateListener extends IBiometricStateListener.Stub {
    public static final int ACTION_SENSOR_TOUCH = 0;
    public static final int STATE_AUTH_OTHER = 4;
    public static final int STATE_BP_AUTH = 3;
    public static final int STATE_ENROLLING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_KEYGUARD_AUTH = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onBiometricAction(int i) {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onEnrollmentsChanged(int i, int i2, boolean z) {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onStateChanged(int i) {
    }
}
