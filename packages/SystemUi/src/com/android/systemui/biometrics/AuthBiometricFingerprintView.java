package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthDialog;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AuthBiometricFingerprintView extends AuthBiometricView {
    public boolean isSfps;
    public boolean isUdfps;
    public AuthController.ScaleFactorProvider scaleFactorProvider;
    public UdfpsDialogMeasureAdapter udfpsAdapter;

    public /* synthetic */ AuthBiometricFingerprintView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public AuthIconController createIconController() {
        return new AuthBiometricFingerprintIconController(((LinearLayout) this).mContext, this.mIconView, this.mIconViewOverlay);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final int getDelayAfterAuthenticatedDurationMs() {
        return 500;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final int getStateForAfterError() {
        return 2;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final void handleResetAfterError() {
        this.mIndicatorView.setText(R.string.fingerprint_dialog_touch_sensor);
        this.mIndicatorView.setTextColor(this.mTextColorHint);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final void handleResetAfterHelp() {
        this.mIndicatorView.setText(R.string.fingerprint_dialog_touch_sensor);
        this.mIndicatorView.setTextColor(this.mTextColorHint);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIndicatorView.setText(R.string.fingerprint_dialog_touch_sensor);
        this.mIndicatorView.setTextColor(this.mTextColorHint);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        if (udfpsDialogMeasureAdapter != null) {
            int i5 = udfpsDialogMeasureAdapter.mBottomSpacerHeight;
            IconCompat$$ExternalSyntheticOutline0.m30m("bottomSpacerHeight: ", i5, "AuthBiometricFingerprintView");
            if (i5 < 0) {
                View findViewById = findViewById(R.id.biometric_icon_frame);
                Intrinsics.checkNotNull(findViewById);
                float f = -i5;
                ((FrameLayout) findViewById).setTranslationY(f);
                View findViewById2 = findViewById(R.id.indicator);
                Intrinsics.checkNotNull(findViewById2);
                ((TextView) findViewById2).setTranslationY(f);
            }
        }
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        AuthDialog.LayoutParams onMeasureInternal = super.onMeasureInternal(i, i2);
        AuthController.ScaleFactorProvider scaleFactorProvider = this.scaleFactorProvider;
        float provide = scaleFactorProvider != null ? scaleFactorProvider.provide() : 1.0f;
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        AuthDialog.LayoutParams onMeasureInternal2 = udfpsDialogMeasureAdapter != null ? udfpsDialogMeasureAdapter.onMeasureInternal(i, i2, onMeasureInternal, provide) : null;
        return onMeasureInternal2 == null ? onMeasureInternal : onMeasureInternal2;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public final boolean supportsSmallDialog() {
        return false;
    }

    public final void updateOverrideIconLayoutParamsSize() {
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        if (udfpsDialogMeasureAdapter != null) {
            AuthController.ScaleFactorProvider scaleFactorProvider = this.scaleFactorProvider;
            int sensorDiameter = udfpsDialogMeasureAdapter.getSensorDiameter(scaleFactorProvider != null ? scaleFactorProvider.provide() : 1.0f);
            AuthIconController authIconController = this.mIconController;
            AuthBiometricFingerprintIconController authBiometricFingerprintIconController = authIconController instanceof AuthBiometricFingerprintIconController ? (AuthBiometricFingerprintIconController) authIconController : null;
            if (authBiometricFingerprintIconController == null) {
                return;
            }
            authBiometricFingerprintIconController.setIconLayoutParamSize(new Pair(Integer.valueOf(sensorDiameter), Integer.valueOf(sensorDiameter)));
        }
    }

    public AuthBiometricFingerprintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
