package com.android.systemui;

import android.os.Trace;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda0(ScreenDecorations screenDecorations, View view, int i) {
        this.$r8$classId = i;
        this.f$0 = screenDecorations;
        this.f$1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                break;
            default:
                ScreenDecorations screenDecorations = this.f$0;
                FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) this.f$1;
                screenDecorations.getClass();
                Trace.beginSection("ScreenDecorations#hideOverlayRunnable");
                screenDecorations.updateOverlayWindowVisibilityIfViewExists(faceScanningOverlay.findViewById(screenDecorations.mFaceScanningViewId));
                Trace.endSection();
                break;
        }
    }
}
