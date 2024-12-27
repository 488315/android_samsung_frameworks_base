package com.android.systemui.pluginlock;

import android.app.SemWallpaperColors;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DisabledPluginWallpaperManager implements PluginWallpaperManager {
    public DisabledPluginWallpaperManager() {
        Log.i("DisabledPluginWallpaperManager", "## DisabledPluginWallpaperManager ##, " + this);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromPath(String str) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromUri(Uri uri) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public SemWallpaperColors getFbeSemWallpaperColors() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getFbeWallpaper(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperIntelligentCrop(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperPath() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getFbeWallpaperRect() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getFbeWallpaperType(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getHomeCurrentScreen() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getHomeWallpaperBitmap(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getHomeWallpaperIntelligentCrop(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getHomeWallpaperPath(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getHomeWallpaperRect(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getHomeWallpaperType(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getSubFbeWallpaperType() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getWallpaperBitmap() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperIndex() {
        return -1;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperIntelligentCrop() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperPath() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperType() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperUpdateStyle() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Uri getWallpaperUri() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean hasBackupWallpaper(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCloneDisplayRequired() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCustomPackApplied() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isDynamicWallpaperEnabled() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeAvailable() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeRequired() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperAvailable() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperVideo() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isHomeWallpaperRequired(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isMultiPackApplied() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isServiceWallpaperApplied() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isVideoWallpaperEnabled() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcBitmap() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcPath() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcUri() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onLockWallpaperChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromPath(String str, boolean z) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromUri(Uri uri, boolean z) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public SemWallpaperColors getFbeSemWallpaperColors(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getFbeWallpaper(int i, boolean z) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperPath(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getFbeWallpaperRect(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getWallpaperBitmap(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperIndex(int i, Bundle bundle) {
        return -1;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperIntelligentCrop(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperPath(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperType(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Uri getWallpaperUri(int i) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCustomPackApplied(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isDynamicWallpaperEnabled(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeAvailable(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeRequired(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperAvailable(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperVideo(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isMultiPackApplied(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isServiceWallpaperApplied(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isVideoWallpaperEnabled(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcBitmap(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcPath(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcUri(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onLockWallpaperChanged(int i, boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onColorAreasChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onHomeWallpaperChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void setHomeWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void setLockWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onWallpaperConsumed(int i, boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void fillWallpaperData(int i, int i2, int i3, String str) {
    }
}
