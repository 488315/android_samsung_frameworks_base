package com.android.systemui.wallpaper;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.view.View;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardWallpaperController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardWallpaperController$$ExternalSyntheticLambda4(View view, int i) {
        this.$r8$classId = 2;
        this.f$1 = i;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardWallpaperController) this.f$0).applyBlurInternalOnUiThread(this.f$1);
                break;
            case 1:
                KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) this.f$0;
                int i = this.f$1;
                SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView;
                if (systemUIWallpaperBase != null) {
                    systemUIWallpaperBase.setThumbnailVisibility(i);
                    break;
                }
                break;
            default:
                int i2 = this.f$1;
                View view = (View) this.f$0;
                if (i2 != 0) {
                    float f = i2;
                    if (!Float.isNaN(f)) {
                        view.setRenderEffect(RenderEffect.createBlurEffect(f, f, null, Shader.TileMode.CLAMP));
                        break;
                    }
                }
                view.setRenderEffect(null);
                break;
        }
    }

    public /* synthetic */ KeyguardWallpaperController$$ExternalSyntheticLambda4(KeyguardWallpaperController keyguardWallpaperController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = keyguardWallpaperController;
        this.f$1 = i;
    }
}
