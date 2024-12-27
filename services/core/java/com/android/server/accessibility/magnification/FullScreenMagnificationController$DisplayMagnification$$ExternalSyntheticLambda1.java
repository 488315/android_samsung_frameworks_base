package com.android.server.accessibility.magnification;

import java.util.function.Consumer;

public final /* synthetic */
class FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FullScreenMagnificationController.DisplayMagnification f$0;

    public /* synthetic */
    FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1(
            FullScreenMagnificationController.DisplayMagnification displayMagnification, int i) {
        this.$r8$classId = i;
        this.f$0 = displayMagnification;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        FullScreenMagnificationController.DisplayMagnification displayMagnification = this.f$0;
        FullScreenMagnificationController.MagnificationInfoChangedCallback
                magnificationInfoChangedCallback =
                        (FullScreenMagnificationController.MagnificationInfoChangedCallback) obj;
        switch (i) {
            case 0:
                magnificationInfoChangedCallback.onRequestMagnificationSpec(
                        displayMagnification.mDisplayId);
                break;
            default:
                magnificationInfoChangedCallback.onFullScreenMagnificationActivationState(
                        displayMagnification.mDisplayId,
                        displayMagnification.mMagnificationActivated);
                break;
        }
    }
}
