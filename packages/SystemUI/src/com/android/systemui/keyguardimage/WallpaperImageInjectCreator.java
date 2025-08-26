package com.android.systemui.keyguardimage;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* loaded from: classes2.dex */
public class WallpaperImageInjectCreator extends WallpaperImageCreator {
    public final SelectedUserInteractor mSelectedUserInteractor;

    public WallpaperImageInjectCreator(Context context, SettingsHelper settingsHelper, PluginWallpaperManager pluginWallpaperManager, CoverWallpaper coverWallpaper, SelectedUserInteractor selectedUserInteractor, KeyguardWallpaper keyguardWallpaper) {
        super("WallpaperImageInjectCreator", context, settingsHelper, pluginWallpaperManager, coverWallpaper, keyguardWallpaper);
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.keyguardimage.WallpaperImageCreator, com.android.systemui.keyguardimage.ImageCreator
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        int i = WallpaperUtils.sCurrentWhich;
        int iSemGetWallpaperType = WallpaperManager.getInstance(this.mContext).semGetWallpaperType(i);
        Log.i(this.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iSemGetWallpaperType, "createImage, wallpaperViewType = "));
        if (iSemGetWallpaperType == 7) {
            if ((i & 60) == 0) {
                i |= 4;
            }
            if (new SemWallpaperProperties(this.mContext, i, this.mSelectedUserInteractor.getSelectedUserId()).isFixedOrientationLiveWallpaper()) {
                imageOption.rotation = this.mContext.getDisplay().getRotation();
            }
        }
        return super.createImage(imageOption, point);
    }
}
