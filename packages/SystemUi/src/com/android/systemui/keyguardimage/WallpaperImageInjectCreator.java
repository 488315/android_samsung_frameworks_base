package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperChangeObserver;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WallpaperImageInjectCreator extends WallpaperImageCreator {
    public WallpaperImageInjectCreator(Context context, SettingsHelper settingsHelper, PluginWallpaperManager pluginWallpaperManager, CoverWallpaper coverWallpaper, KeyguardWallpaper keyguardWallpaper) {
        super("WallpaperImageInjectCreator", context, settingsHelper, pluginWallpaperManager, coverWallpaper, keyguardWallpaper, new WallpaperChangeObserver());
    }

    @Override // com.android.systemui.keyguardimage.WallpaperImageCreator, com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        boolean isLiveWallpaperEnabled = WallpaperUtils.isLiveWallpaperEnabled();
        KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) this.mKeyguardWallpaper;
        int wallpaperViewType = keyguardWallpaperController.getWallpaperViewType();
        String str = this.TAG;
        Log.i(str, "createImage, isLiveWallpaperEnabled = " + isLiveWallpaperEnabled + " , wallpaperViewType = " + wallpaperViewType);
        Context context = this.mContext;
        if (isLiveWallpaperEnabled || wallpaperViewType == 7) {
            int i = WallpaperUtils.sCurrentWhich;
            if ((i & 60) == 0) {
                i |= 4;
            }
            if (new SemWallpaperProperties(context, i, KeyguardUpdateMonitor.getCurrentUser()).isFixedOrientationLiveWallpaper()) {
                imageOption.rotation = context.getDisplay().getRotation();
            }
        } else if (wallpaperViewType != 8) {
            Bitmap wallpaperBitmap = keyguardWallpaperController.getWallpaperBitmap();
            boolean z = true;
            if (wallpaperBitmap != null && context != null && context.getResources() != null) {
                int i2 = context.getResources().getConfiguration().orientation;
                int i3 = wallpaperBitmap.getHeight() >= wallpaperBitmap.getWidth() ? 1 : 2;
                Log.i(str, "isBitmapAndScreenOrientationSame, (w = " + wallpaperBitmap.getWidth() + " , h = " + wallpaperBitmap.getHeight() + ") orientation = " + i2);
                if (i2 != i3) {
                    z = false;
                }
            }
            if (z) {
                return wallpaperBitmap;
            }
            Log.w(str, "createImage failed");
            return null;
        }
        return super.createImage(imageOption, point);
    }
}
