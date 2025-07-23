package com.android.systemui.settings.brightness;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ToggleSeekBar extends SeekBar {
    public String mAccessibilityLabel;
    public BrightnessSliderController$$ExternalSyntheticLambda1 mAdminBlocker;

    public ToggleSeekBar(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9) {
            setHovered(true);
        } else if (motionEvent.getAction() == 10) {
            setHovered(false);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        String str = this.mAccessibilityLabel;
        if (str != null) {
            accessibilityNodeInfo.setText(str);
        }
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        BrightnessSliderController$$ExternalSyntheticLambda1 brightnessSliderController$$ExternalSyntheticLambda1 = this.mAdminBlocker;
        if (brightnessSliderController$$ExternalSyntheticLambda1 == null) {
            if (!isEnabled()) {
                setEnabled(true);
            }
            return super.onTouchEvent(motionEvent);
        }
        BrightnessSliderController brightnessSliderController = brightnessSliderController$$ExternalSyntheticLambda1.f$0;
        brightnessSliderController.getClass();
        brightnessSliderController.mActivityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(brightnessSliderController$$ExternalSyntheticLambda1.f$1), 0);
        return true;
    }

    public ToggleSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ToggleSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
