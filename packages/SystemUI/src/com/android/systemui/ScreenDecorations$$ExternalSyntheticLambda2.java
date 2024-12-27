package com.android.systemui;

import android.os.Trace;
import android.view.View;

public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ScreenDecorations f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda2(ScreenDecorations screenDecorations, View view) {
        this.f$0 = screenDecorations;
        this.f$1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = this.f$0;
                FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) this.f$1;
                screenDecorations.getClass();
                Trace.beginSection("ScreenDecorations#hideOverlayRunnable");
                screenDecorations.updateOverlayWindowVisibilityIfViewExists(faceScanningOverlay.findViewById(screenDecorations.mFaceScanningViewId));
                Trace.endSection();
                break;
            default:
                this.f$0.getClass();
                break;
        }
    }

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda2(ScreenDecorations screenDecorations, FaceScanningOverlay faceScanningOverlay) {
        this.f$0 = screenDecorations;
        this.f$1 = faceScanningOverlay;
    }
}
