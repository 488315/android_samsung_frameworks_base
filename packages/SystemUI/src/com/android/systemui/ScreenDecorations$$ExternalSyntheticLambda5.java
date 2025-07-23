package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.decor.CutoutDecorProviderFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda5(ScreenDecorations screenDecorations, int i) {
        this.$r8$classId = i;
        this.f$0 = screenDecorations;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScreenDecorations screenDecorations = this.f$0;
        switch (i) {
            case 0:
                ScreenDecorations.m997$r8$lambda$KoNSRBrMuoZsUyWIX90PQvGYvA(screenDecorations);
                break;
            case 1:
                boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                screenDecorations.startOnScreenDecorationsThread();
                break;
            case 2:
                boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                screenDecorations.removeAllOverlays();
                screenDecorations.removeHwcOverlay();
                screenDecorations.setupDecorations();
                break;
            case 3:
                boolean z3 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                screenDecorations.removeAllOverlays();
                screenDecorations.removeHwcOverlay();
                screenDecorations.setupDecorations();
                CutoutDecorProviderFactory cutoutDecorProviderFactory = screenDecorations.mDebugCutoutFactory;
                if (!cutoutDecorProviderFactory.isCameraProtectionEnabled || !cutoutDecorProviderFactory.isCameraProtectionVisible) {
                    screenDecorations.hideCameraProtection();
                    break;
                } else {
                    screenDecorations.showCameraProtection(new Path(), new Rect());
                    break;
                }
                break;
            case 4:
                int i2 = screenDecorations.mFaceScanningViewId;
                if (screenDecorations.getOverlayView(i2) != null) {
                    screenDecorations.updateOverlayProviderViews(new Integer[]{Integer.valueOf(i2)});
                    break;
                } else {
                    screenDecorations.setupDecorations();
                    break;
                }
            default:
                boolean z4 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                screenDecorations.removeAllOverlays();
                screenDecorations.removeHwcOverlay();
                screenDecorations.startOnScreenDecorationsThread();
                ScreenDecorations.AnonymousClass7 anonymousClass7 = screenDecorations.mColorInversionSetting;
                screenDecorations.updateColorInversion(anonymousClass7 != null ? anonymousClass7.getValue() : 0);
                break;
        }
    }
}
