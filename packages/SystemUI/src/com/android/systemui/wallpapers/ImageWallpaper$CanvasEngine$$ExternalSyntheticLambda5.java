package com.android.systemui.wallpapers;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5(ImageWallpaper.CanvasEngine canvasEngine, int i, Rect rect) {
        this.f$0 = canvasEngine;
        this.f$1 = i;
        this.f$2 = rect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ImageWallpaper.CanvasEngine) this.f$0).drawFrameSynchronized(this.f$1, (Rect) this.f$2);
                break;
            default:
                ImageWallpaper.IntegratedEngine.AnonymousClass1 anonymousClass1 = (ImageWallpaper.IntegratedEngine.AnonymousClass1) this.f$0;
                int i = this.f$1;
                Bundle bundle = (Bundle) this.f$2;
                ImageWallpaper.IntegratedEngine integratedEngine = anonymousClass1.this$1;
                String str = integratedEngine.TAG;
                if (i != 605 && i != 606) {
                    if (i == 724) {
                        integratedEngine.onCommand("samsung.android.wallpaper.bouncervisibilitychanged", 0, 0, 0, bundle, false);
                        break;
                    } else if (i == 732) {
                        Log.d(str, "MSG_DLS_VIEW_MODE_CHANGED");
                        break;
                    }
                } else {
                    integratedEngine.onSurfaceRedrawNeeded(integratedEngine.mSurfaceHolder);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5(ImageWallpaper.IntegratedEngine.AnonymousClass1 anonymousClass1, int i, Bundle bundle) {
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = bundle;
    }
}
