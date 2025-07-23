package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenshotShelfViewProxy$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenshotShelfViewProxy f$0;

    public /* synthetic */ ScreenshotShelfViewProxy$$ExternalSyntheticLambda1(ScreenshotShelfViewProxy screenshotShelfViewProxy, int i) {
        this.$r8$classId = i;
        this.f$0 = screenshotShelfViewProxy;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ScreenshotShelfViewProxy.ScreenshotViewCallback screenshotViewCallback = this.f$0.callbacks;
                if (screenshotViewCallback != null) {
                    screenshotViewCallback.onUserInteraction();
                }
                break;
            default:
                ScreenshotViewModel screenshotViewModel = this.f$0.viewModel;
                screenshotViewModel._animationState.setValue(AnimationState.ENTRANCE_REVEAL);
                break;
        }
        return Unit.INSTANCE;
    }
}
