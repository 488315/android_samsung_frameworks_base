package com.android.server.accessibility.magnification;

import com.android.server.accessibility.magnification.FullScreenMagnificationController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FullScreenMagnificationController.DisplayMagnification f$0;

    public /* synthetic */ FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1(FullScreenMagnificationController.DisplayMagnification displayMagnification, int i) {
        this.$r8$classId = i;
        this.f$0 = displayMagnification;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        FullScreenMagnificationController.DisplayMagnification displayMagnification = this.f$0;
        FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback = (FullScreenMagnificationController.MagnificationInfoChangedCallback) obj;
        switch (i) {
            case 0:
                magnificationInfoChangedCallback.onRequestMagnificationSpec(displayMagnification.mDisplayId);
                break;
            default:
                magnificationInfoChangedCallback.onFullScreenMagnificationActivationState(displayMagnification.mDisplayId, displayMagnification.mMagnificationActivated);
                break;
        }
    }
}
