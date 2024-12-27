package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class KeyguardSecAbsKeyInputView extends KeyguardAbsKeyInputView {
    public KeyguardSecAbsKeyInputView(Context context) {
        this(context, null);
    }

    public final void doHapticKeyClick() {
        performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1), (LsRune.SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isHapticFeedbackEnabled()) ? 3 : 1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        enableTouch();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        enableTouch();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            return false;
        }
        super.onKeyDown(i, keyEvent);
        return false;
    }

    public KeyguardSecAbsKeyInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void disableTouch() {
    }

    public void enableTouch() {
    }
}
