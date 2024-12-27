package com.android.server.display;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayPowerController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayPowerController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((DisplayPowerController) ((DisplayPowerControllerInterface) obj)).setBrightnessToFollow(Float.NaN, -1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, false);
                break;
            case 1:
                ((DisplayPowerController) ((DisplayPowerControllerInterface) obj)).setBrightnessToFollow(Float.NaN, -1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, false);
                break;
            default:
                DisplayPowerController.SettingsObserver settingsObserver = (DisplayPowerController.SettingsObserver) obj;
                settingsObserver.this$0.handleBrightnessModeChange();
                settingsObserver.this$0.updatePowerState();
                break;
        }
    }
}
