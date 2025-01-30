package com.android.systemui.wallpaper.view;

import android.util.Log;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardTransitionWallpaper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardTransitionWallpaper$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                KeyguardTransitionWallpaper keyguardTransitionWallpaper = (KeyguardTransitionWallpaper) this.f$0;
                synchronized (keyguardTransitionWallpaper) {
                    if (keyguardTransitionWallpaper.mCapturedBitmap == null) {
                        Log.i("KeyguardTransitionWallpaper", "capture start ");
                        keyguardTransitionWallpaper.mCaptureStart = true;
                        keyguardTransitionWallpaper.updateBitmap();
                    } else {
                        PluginWallpaperManager pluginWallpaperManager = keyguardTransitionWallpaper.mPluginWallpaperManager;
                        if (!WallpaperUtils.isSubDisplay()) {
                            i = 0;
                        }
                        ((PluginWallpaperManagerImpl) pluginWallpaperManager).onWallpaperConsumed(i, false);
                    }
                }
                return;
            default:
                KeyguardTransitionWallpaper.C37061 c37061 = (KeyguardTransitionWallpaper.C37061) this.f$0;
                c37061.getClass();
                Log.d("KeyguardTransitionWallpaper", "onAnimationStart: Request dls color.");
                ((PluginWallpaperManagerImpl) KeyguardTransitionWallpaper.this.mPluginWallpaperManager).onWallpaperConsumed(WallpaperUtils.isSubDisplay() ? 1 : 0, true);
                return;
        }
    }
}
