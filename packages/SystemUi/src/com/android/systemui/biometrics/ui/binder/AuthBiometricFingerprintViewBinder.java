package com.android.systemui.biometrics.ui.binder;

import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.biometrics.AuthBiometricFingerprintView;
import com.android.systemui.biometrics.ui.viewmodel.AuthBiometricFingerprintViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthBiometricFingerprintViewBinder {
    static {
        new AuthBiometricFingerprintViewBinder();
    }

    private AuthBiometricFingerprintViewBinder() {
    }

    public static final void bind(AuthBiometricFingerprintView authBiometricFingerprintView, AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel) {
        if (authBiometricFingerprintView.isSfps) {
            LottieAnimationView lottieAnimationView = authBiometricFingerprintView.mIconView;
            int i = AuthBiometricFingerprintIconViewBinder.$r8$clinit;
            RepeatWhenAttachedKt.repeatWhenAttached(lottieAnimationView, EmptyCoroutineContext.INSTANCE, new AuthBiometricFingerprintIconViewBinder$bind$1(lottieAnimationView, authBiometricFingerprintViewModel, null));
        }
    }
}
