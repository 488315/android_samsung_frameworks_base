package com.android.systemui.biometrics;

import android.os.Trace;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UdfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1 implements Runnable {
    public final /* synthetic */ UdfpsAnimationViewController $animation$inlined;
    public final /* synthetic */ View $view$inlined;
    public final /* synthetic */ UdfpsControllerOverlay this$0;

    public UdfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1(UdfpsControllerOverlay udfpsControllerOverlay, View view, UdfpsAnimationViewController udfpsAnimationViewController) {
        this.this$0 = udfpsControllerOverlay;
        this.$view$inlined = view;
        this.$animation$inlined = udfpsAnimationViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Trace.setCounter("UdfpsAddView", 1L);
        UdfpsControllerOverlay udfpsControllerOverlay = this.this$0;
        WindowManager windowManager = udfpsControllerOverlay.windowManager;
        View view = this.$view$inlined;
        WindowManager.LayoutParams layoutParams = udfpsControllerOverlay.coreLayoutParams;
        udfpsControllerOverlay.updateDimensions(layoutParams, this.$animation$inlined);
        windowManager.addView(view, layoutParams);
    }
}
