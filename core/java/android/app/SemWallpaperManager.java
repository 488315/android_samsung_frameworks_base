package android.app;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* loaded from: classes.dex */
public interface SemWallpaperManager {
    default Drawable getDrawable(int which) {
        return semGetDrawable(which);
    }

    default Drawable semGetDrawable(int which) {
        return null;
    }

    default boolean wallpaperSupportsWcg(Bitmap bitmap) {
        return false;
    }

    default Bitmap getBitmap(boolean hardware, int which, boolean useCache) {
        return null;
    }

    default Bitmap getBitmapAsUser(int userId, boolean hardware, int which, boolean useCache) {
        return null;
    }

    default Bitmap getBitmapForDex() {
        return null;
    }

    default Bitmap getBitmapForDex(boolean hardware) {
        return null;
    }

    default Bitmap getBitmapForDexAsUser(int userId, boolean hardware) {
        return null;
    }

    default ParcelFileDescriptor getWallpaperFile(int which, boolean orig) {
        return null;
    }

    default SemWallpaperColors semGetWallpaperColors(int which) {
        return null;
    }

    default void semSetSmartCropRect(int which, Rect original, Rect smartCrop) {
    }

    default Rect semGetSmartCropRect(int which) {
        return null;
    }

    default ParcelFileDescriptor getWallpaperFile(int which, int userId, int kwpType) {
        return null;
    }

    default ParcelFileDescriptor getLockWallpaperFile(int userId) {
        return getLockWallpaperFile(userId, 2);
    }

    default ParcelFileDescriptor getLockWallpaperFile(int userId, int which) {
        return null;
    }

    default WallpaperInfo getWallpaperInfo(int which, int userId) {
        return null;
    }

    default void setWallpaperUri(String uriString, boolean allowBackup, int which) throws IOException, PackageManager.NameNotFoundException {
    }

    default Uri semGetUri(int which) {
        return null;
    }

    default void semSetDLSWallpaperColors(SemWallpaperColors colors, int which) {
    }

    default void semSetUri(Uri uri, boolean allowBackup, int which) throws IOException, PackageManager.NameNotFoundException {
    }

    default void semSetUri(Uri uri, boolean allowBackup, int which, int type) throws IOException, PackageManager.NameNotFoundException {
    }

    default void setStream(InputStream data, int simSlot) throws IOException {
    }

    default int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which, int type, boolean isPreloaded, Bundle extras) throws IOException {
        return 0;
    }

    default boolean isSupportCMFFeature() {
        return false;
    }

    default int getDefaultWallpaperType(int which) {
        return 0;
    }

    default String getDefaultMultipackStyle(int which) {
        return null;
    }

    default boolean isSupportDefaultMultipleWallpaper() {
        return false;
    }

    default void resetMultipleWallpaperSettingIfNeeded() {
    }

    default boolean isWallpaperBackupAllowed(int which) {
        return false;
    }

    default void clearAll() throws IOException {
    }

    default void setResourceAll(int resid) throws IOException {
    }

    default int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }

    default int semGetWallpaperType(int which) {
        return -1;
    }

    default boolean isDefaultWallpaperState(int which) {
        return true;
    }

    default Rect semGetWallpaperCropHint(int which) {
        return null;
    }

    default void setVideoLockscreenWallpaper(String videoFilePath) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int which) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int which, boolean allowBackup) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, boolean updateSetting) {
    }

    default void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, boolean updateSetting, boolean allowBackup) {
    }

    default String getVideoFilePath(int which) {
        return null;
    }

    default String getVideoPackage() {
        return null;
    }

    default String getVideoPackage(int which) {
        return null;
    }

    default String getVideoFileName(int which) {
        return null;
    }

    default boolean isVideoWallpaper() {
        return false;
    }

    default boolean hasVideoWallpaper() {
        return false;
    }

    default void setMotionWallpaper(String packageName) {
    }

    default void setMotionWallpaper(String packageName, int which) {
    }

    default void setMotionWallpaper(String packageName, int which, boolean allowBackup) {
    }

    default String getMotionWallpaperPkgName(int which) {
        return null;
    }

    default void setAnimatedLockscreenWallpaper(String packageName) throws IOException {
    }

    default void setAnimatedLockscreenWallpaper(String packageName, int which) throws IOException {
    }

    default void setAnimatedLockscreenWallpaper(String packageName, int which, boolean allowBackup) throws IOException {
    }

    default int semMakeBackupWallpaper() {
        return 1;
    }

    default int semMakeBackupWallpaper(int which) {
        if (which == 0) {
            return -1;
        }
        return 1;
    }

    default int semMakeBackupWallpaper(int which, int key) {
        if (which == 0) {
            return -1;
        }
        return 1;
    }

    default void semClearBackupWallpapers() {
    }

    default void semClearBackupWallpapers(int which) {
    }

    default void clearBackupWallpaperGivenKey(int key) {
    }

    default boolean semRestoreBackupWallpaper(int key) {
        if (key == 1) {
            return true;
        }
        return false;
    }

    default String getAnimatedPkgName(int which) {
        return null;
    }

    default boolean isExternalLiveWallpaper() {
        return false;
    }

    default boolean isExternalLiveWallpaper(int flag) {
        return false;
    }

    default int getLidState() {
        return -1;
    }

    default boolean isSubDisplay() {
        return false;
    }

    default int getAppliedScreen(String pkgName, boolean forSubDisplay) {
        return 0;
    }

    default void addOnSemColorsChangedListener(OnSemColorsChangedListener listener, Handler handler) {
    }

    default void addOnSemColorsChangedListener(OnSemColorsChangedListener listener, Handler handler, int userId) {
    }

    default void removeOnSemColorsChangedListener(OnSemColorsChangedListener callback) {
    }

    default void removeOnSemColorsChangedListener(OnSemColorsChangedListener callback, int userId) {
    }

    default void applyWallpaperColors(List colors, int sNum, int lNum, int which) {
    }

    default List<int[][]> getColorPalettes(int[] seeds) {
        return null;
    }

    default List<int[][]> getColorPalettes(int[] seeds, boolean fromGoogle) {
        return null;
    }

    default List<int[][]> getColorPalettes(Bitmap bitmap) {
        return null;
    }

    default List<int[][]> getColorPalettes(Bitmap bitmap, boolean fromGoogle) {
        return null;
    }

    default int[] getSeedColors(int which) {
        return null;
    }

    default int[] getSeedColors(int which, boolean fromGoogle) {
        return null;
    }

    default int[] getSeedColors(Bitmap bitmap) {
        return null;
    }

    default int[] getSeedColors(Bitmap bitmap, boolean fromGoogle) {
        return null;
    }

    default boolean canBackup() {
        return false;
    }

    default boolean canBackup(int which) {
        return false;
    }

    default Bundle getWallpaperExtras(int which, int userId) {
        return null;
    }

    default Bundle getWallpaperAssets(int which, int userId) {
        return null;
    }

    default boolean isStockLiveWallpaper(int which) {
        return false;
    }

    default ParcelFileDescriptor getWallpaperAssetFile(int which, int userId, String assetFilePath) {
        return null;
    }

    default boolean isSystemAndLockPaired(int mode) {
        return false;
    }

    default boolean isWallpaperDataExists(int which) {
        return false;
    }

    default int getWallpaperOrientation(int which, int userId) {
        return 0;
    }

    default ComponentName semGetWallpaperComponent(int which, int userId) {
        return null;
    }

    default int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, Bundle extras) throws IOException {
        return 0;
    }
}
