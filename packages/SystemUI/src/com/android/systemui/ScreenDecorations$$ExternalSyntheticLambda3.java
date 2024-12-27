package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.decor.CutoutDecorProviderFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ScreenDecorations.m879$r8$lambda$06HZTYiZHuzOTp6D2dnb_0cn6o((ScreenDecorations) obj);
                break;
            case 1:
                ((ScreenDecorations) obj).startOnScreenDecorationsThread();
                break;
            case 2:
                ScreenDecorations screenDecorations = (ScreenDecorations) obj;
                screenDecorations.removeAllOverlays();
                screenDecorations.removeHwcOverlay();
                screenDecorations.setupDecorations();
                break;
            case 3:
                ScreenDecorations screenDecorations2 = (ScreenDecorations) obj;
                screenDecorations2.removeAllOverlays();
                screenDecorations2.removeHwcOverlay();
                screenDecorations2.setupDecorations();
                CutoutDecorProviderFactory cutoutDecorProviderFactory = screenDecorations2.mDebugCutoutFactory;
                if (!cutoutDecorProviderFactory.isCameraProtectionEnabled || !cutoutDecorProviderFactory.isCameraProtectionVisible) {
                    screenDecorations2.hideCameraProtection();
                    break;
                } else {
                    screenDecorations2.showCameraProtection(new Path(), new Rect());
                    break;
                }
                break;
            case 4:
                ScreenDecorations screenDecorations3 = (ScreenDecorations) obj;
                int i2 = screenDecorations3.mFaceScanningViewId;
                if (screenDecorations3.getOverlayView(i2) != null) {
                    screenDecorations3.updateOverlayProviderViews(new Integer[]{Integer.valueOf(i2)});
                    break;
                } else {
                    screenDecorations3.setupDecorations();
                    break;
                }
            case 5:
                ScreenDecorations screenDecorations4 = (ScreenDecorations) obj;
                screenDecorations4.removeAllOverlays();
                screenDecorations4.removeHwcOverlay();
                screenDecorations4.startOnScreenDecorationsThread();
                ScreenDecorations.AnonymousClass6 anonymousClass6 = screenDecorations4.mColorInversionSetting;
                screenDecorations4.updateColorInversion(anonymousClass6 != null ? anonymousClass6.getValue() : 0);
                break;
            default:
                ScreenDecorations.this.mOverlays[1].rootView.invalidate();
                break;
        }
    }
}
