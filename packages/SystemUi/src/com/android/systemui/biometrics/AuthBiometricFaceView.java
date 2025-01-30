package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthBiometricFaceView extends AuthBiometricView {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ AuthBiometricFaceView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final AuthIconController createIconController() {
        return new AuthBiometricFaceIconController(((LinearLayout) this).mContext, this.mIconView);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final int getDelayAfterAuthenticatedDurationMs() {
        return 500;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final int getStateForAfterError() {
        return 0;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final void handleResetAfterError() {
        this.mIndicatorView.setTextColor(this.mTextColorHint);
        this.mIndicatorView.setVisibility(4);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final void handleResetAfterHelp() {
        this.mIndicatorView.setTextColor(this.mTextColorHint);
        this.mIndicatorView.setVisibility(4);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView, com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onAuthenticationFailed(int i, String str) {
        if (this.mSize == 2) {
            this.mTryAgainButton.setVisibility(0);
            this.mConfirmButton.setVisibility(8);
        }
        super.onAuthenticationFailed(i, str);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final void updateState(int i) {
        if (i == 1 || (i == 2 && this.mSize == 2)) {
            this.mIndicatorView.setTextColor(this.mTextColorHint);
            this.mIndicatorView.setVisibility(4);
        }
        super.updateState(i);
    }

    public AuthBiometricFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
