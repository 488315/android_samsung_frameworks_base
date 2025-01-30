package com.android.systemui.biometrics;

import android.content.Context;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AuthBiometricFingerprintAndFaceIconController extends AuthBiometricFingerprintIconController {
    public final boolean actsAsConfirmButton;

    public AuthBiometricFingerprintAndFaceIconController(Context context, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        super(context, lottieAnimationView, lottieAnimationView2);
        this.actsAsConfirmButton = true;
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public final boolean getActsAsConfirmButton() {
        return this.actsAsConfirmButton;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintIconController
    public final Integer getAnimationForTransition(int i, int i2) {
        if (i2 != 5) {
            return i2 != 6 ? super.getAnimationForTransition(i, i2) : i == 5 ? Integer.valueOf(R.raw.fingerprint_dialogue_unlocked_to_checkmark_success_lottie) : super.getAnimationForTransition(i, i2);
        }
        return Integer.valueOf((i == 3 || i == 4) ? R.raw.fingerprint_dialogue_error_to_unlock_lottie : R.raw.fingerprint_dialogue_fingerprint_to_unlock_lottie);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintIconController
    public final boolean shouldAnimateIconViewForTransition(int i, int i2) {
        if (i2 == 5) {
            return true;
        }
        return super.shouldAnimateIconViewForTransition(i, i2);
    }
}
