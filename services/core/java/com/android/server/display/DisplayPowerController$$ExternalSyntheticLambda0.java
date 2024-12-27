package com.android.server.display;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda0
        implements Runnable {
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
                ((DisplayPowerController) ((DisplayPowerControllerInterface) obj))
                        .setBrightnessToFollow(
                                Float.NaN,
                                -1.0f,
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                false);
                break;
            case 1:
                ((DisplayPowerController) ((DisplayPowerControllerInterface) obj))
                        .setBrightnessToFollow(
                                Float.NaN,
                                -1.0f,
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                false);
                break;
            default:
                DisplayPowerController.SettingsObserver settingsObserver =
                        (DisplayPowerController.SettingsObserver) obj;
                settingsObserver.this$0.handleBrightnessModeChange();
                settingsObserver.this$0.updatePowerState();
                break;
        }
    }
}
