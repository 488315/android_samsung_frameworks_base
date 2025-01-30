package com.android.systemui.settings.brightness;

import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BrightnessSliderController$$ExternalSyntheticLambda1 {
    public final /* synthetic */ BrightnessSliderController f$0;

    public /* synthetic */ BrightnessSliderController$$ExternalSyntheticLambda1(BrightnessSliderController brightnessSliderController) {
        this.f$0 = brightnessSliderController;
    }

    public final void onDispatchTouchEvent(MotionEvent motionEvent) {
        this.f$0.mirrorTouchEvent(motionEvent);
    }
}
