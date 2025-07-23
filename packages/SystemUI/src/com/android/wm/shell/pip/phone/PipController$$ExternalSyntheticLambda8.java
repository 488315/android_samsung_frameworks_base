package com.android.wm.shell.pip.phone;

import com.android.wm.shell.pip.PipTaskOrganizer;
import java.util.function.IntConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda8 implements IntConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda8(PipController pipController, int i) {
        this.$r8$classId = i;
        this.f$0 = pipController;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        int i2 = this.$r8$classId;
        PipController pipController = this.f$0;
        switch (i2) {
            case 0:
                pipController.mPipDisplayLayoutState.mDisplayId = i;
                pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), false);
                break;
            default:
                PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
                if (i != 0) {
                    pipTaskOrganizer.setStashDimOverlayAlpha(0.65f);
                    break;
                } else {
                    pipTaskOrganizer.clearStashDimOverlay();
                    break;
                }
        }
    }
}
