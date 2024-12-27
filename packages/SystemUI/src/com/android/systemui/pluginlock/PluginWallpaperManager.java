package com.android.systemui.pluginlock;

import android.app.SemWallpaperColors;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface PluginWallpaperManager {
    static int getScreenId(int i) {
        return (i & 16) != 0 ? 1 : 0;
    }

    void fillWallpaperData(int i, int i2, int i3, String str);

    Bitmap getBitmapFromPath(String str);

    Bitmap getBitmapFromPath(String str, boolean z);

    Bitmap getBitmapFromUri(Uri uri);

    Bitmap getBitmapFromUri(Uri uri, boolean z);

    SemWallpaperColors getFbeSemWallpaperColors();

    SemWallpaperColors getFbeSemWallpaperColors(int i);

    Bitmap getFbeWallpaper(int i);

    Bitmap getFbeWallpaper(int i, boolean z);

    String getFbeWallpaperIntelligentCrop(int i);

    String getFbeWallpaperPath();

    String getFbeWallpaperPath(int i);

    Rect getFbeWallpaperRect();

    Rect getFbeWallpaperRect(int i);

    int getFbeWallpaperType(int i);

    int getHomeCurrentScreen();

    Bitmap getHomeWallpaperBitmap(int i);

    String getHomeWallpaperIntelligentCrop(int i);

    String getHomeWallpaperPath(int i);

    Rect getHomeWallpaperRect(int i);

    int getHomeWallpaperType(int i);

    int getSubFbeWallpaperType();

    Bitmap getWallpaperBitmap();

    Bitmap getWallpaperBitmap(int i);

    int getWallpaperIndex();

    int getWallpaperIndex(int i, Bundle bundle);

    String getWallpaperIntelligentCrop();

    String getWallpaperIntelligentCrop(int i);

    String getWallpaperPath();

    String getWallpaperPath(int i);

    int getWallpaperType();

    int getWallpaperType(int i);

    int getWallpaperUpdateStyle();

    Uri getWallpaperUri();

    Uri getWallpaperUri(int i);

    boolean hasBackupWallpaper(int i);

    boolean isCloneDisplayRequired();

    boolean isCustomPackApplied();

    boolean isCustomPackApplied(int i);

    boolean isDynamicWallpaperEnabled();

    boolean isDynamicWallpaperEnabled(int i);

    boolean isFbeAvailable();

    boolean isFbeAvailable(int i);

    boolean isFbeRequired();

    boolean isFbeRequired(int i);

    boolean isFbeWallpaperAvailable();

    boolean isFbeWallpaperAvailable(int i);

    boolean isFbeWallpaperVideo();

    boolean isFbeWallpaperVideo(int i);

    boolean isHomeWallpaperRequired(int i);

    boolean isMultiPackApplied();

    boolean isMultiPackApplied(int i);

    boolean isServiceWallpaperApplied();

    boolean isServiceWallpaperApplied(int i);

    boolean isVideoWallpaperEnabled();

    boolean isVideoWallpaperEnabled(int i);

    boolean isWallpaperSrcBitmap();

    boolean isWallpaperSrcBitmap(int i);

    boolean isWallpaperSrcPath();

    boolean isWallpaperSrcPath(int i);

    boolean isWallpaperSrcUri();

    boolean isWallpaperSrcUri(int i);

    void onColorAreasChanged(int i);

    void onHomeWallpaperChanged(int i);

    void onLockWallpaperChanged(int i);

    void onLockWallpaperChanged(int i, boolean z);

    void onWallpaperConsumed(int i, boolean z);

    void setHomeWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback);

    void setLockWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback);
}
