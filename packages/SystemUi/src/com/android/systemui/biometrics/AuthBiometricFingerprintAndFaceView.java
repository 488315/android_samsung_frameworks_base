package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthBiometricFingerprintAndFaceView extends AuthBiometricFingerprintView {
    public boolean isFaceClass3;

    public AuthBiometricFingerprintAndFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintView, com.android.systemui.biometrics.AuthBiometricView
    public final AuthIconController createIconController() {
        return new AuthBiometricFingerprintAndFaceIconController(((LinearLayout) this).mContext, this.mIconView, this.mIconViewOverlay);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final boolean forceRequireConfirmation(int i) {
        return i == 8;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final int getConfirmationPrompt() {
        return R.string.biometric_dialog_tap_confirm_with_face;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final boolean ignoreUnsuccessfulEventsFrom(int i, String str) {
        if (i != 8) {
            return false;
        }
        if (this.isFaceClass3) {
            if (Intrinsics.areEqual(str, FaceManager.getErrorString(((LinearLayout) this).mContext, 7, 0)) || Intrinsics.areEqual(str, FaceManager.getErrorString(((LinearLayout) this).mContext, 9, 0))) {
                return false;
            }
        }
        return true;
    }

    public AuthBiometricFingerprintAndFaceView(Context context) {
        this(context, null);
    }
}
