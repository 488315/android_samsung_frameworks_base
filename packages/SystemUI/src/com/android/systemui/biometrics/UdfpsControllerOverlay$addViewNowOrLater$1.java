package com.android.systemui.biometrics;

import android.os.Trace;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UdfpsControllerOverlay$addViewNowOrLater$1 implements Runnable {
    public final /* synthetic */ UdfpsAnimationViewController $animation;
    public final /* synthetic */ View $view;
    public final /* synthetic */ UdfpsControllerOverlay this$0;

    public UdfpsControllerOverlay$addViewNowOrLater$1(UdfpsControllerOverlay udfpsControllerOverlay, View view, UdfpsAnimationViewController udfpsAnimationViewController) {
        this.this$0 = udfpsControllerOverlay;
        this.$view = view;
        this.$animation = udfpsAnimationViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Trace.setCounter("UdfpsAddView", 1L);
        UdfpsControllerOverlay udfpsControllerOverlay = this.this$0;
        WindowManager windowManager = udfpsControllerOverlay.windowManager;
        View view = this.$view;
        WindowManager.LayoutParams layoutParams = udfpsControllerOverlay.coreLayoutParams;
        udfpsControllerOverlay.updateDimensions(layoutParams, this.$animation);
        windowManager.addView(view, layoutParams);
    }
}
