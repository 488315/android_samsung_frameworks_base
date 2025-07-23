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
    default void addOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, Handler handler) {
    }

    default void addOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, Handler handler, int i) {
    }

    default void applyWallpaperColors(List list, int i, int i2, int i3) {
    }

    default boolean canBackup() {
        return false;
    }

    default boolean canBackup(int i) {
        return false;
    }

    default void clearAll() throws IOException {
    }

    default void clearBackupWallpaperGivenKey(int i) {
    }

    default String getAnimatedPkgName(int i) {
        return null;
    }

    default int getAppliedScreen(String str, boolean z) {
        return 0;
    }

    default Bitmap getBitmap(boolean z, int i, boolean z2) {
        return null;
    }

    default Bitmap getBitmapAsUser(int i, boolean z, int i2, boolean z2) {
        return null;
    }

    default Bitmap getBitmapForDex() {
        return null;
    }

    default Bitmap getBitmapForDex(boolean z) {
        return null;
    }

    default Bitmap getBitmapForDexAsUser(int i, boolean z) {
        return null;
    }

    default List<int[][]> getColorPalettes(Bitmap bitmap) {
        return null;
    }

    default List<int[][]> getColorPalettes(Bitmap bitmap, boolean z) {
        return null;
    }

    default List<int[][]> getColorPalettes(int[] iArr) {
        return null;
    }

    default List<int[][]> getColorPalettes(int[] iArr, boolean z) {
        return null;
    }

    default String getDefaultMultipackStyle(int i) {
        return null;
    }

    default int getDefaultWallpaperType(int i) {
        return 0;
    }

    default int getLidState() {
        return -1;
    }

    default ParcelFileDescriptor getLockWallpaperFile(int i, int i2) {
        return null;
    }

    default String getMotionWallpaperPkgName(int i) {
        return null;
    }

    default int[] getSeedColors(int i) {
        return null;
    }

    default int[] getSeedColors(int i, boolean z) {
        return null;
    }

    default int[] getSeedColors(Bitmap bitmap) {
        return null;
    }

    default int[] getSeedColors(Bitmap bitmap, boolean z) {
        return null;
    }

    default String getVideoFileName(int i) {
        return null;
    }

    default String getVideoFilePath(int i) {
        return null;
    }

    default String getVideoPackage() {
        return null;
    }

    default String getVideoPackage(int i) {
        return null;
    }

    default ParcelFileDescriptor getWallpaperAssetFile(int i, int i2, String str) {
        return null;
    }

    default Bundle getWallpaperAssets(int i, int i2) {
        return null;
    }

    default Bundle getWallpaperExtras(int i, int i2) {
        return null;
    }

    default ParcelFileDescriptor getWallpaperFile(int i, int i2, int i3) {
        return null;
    }

    default WallpaperInfo getWallpaperInfo(int i, int i2) {
        return null;
    }

    default int getWallpaperOrientation(int i, int i2) {
        return 0;
    }

    default boolean hasVideoWallpaper() {
        return false;
    }

    default boolean isDefaultWallpaperState(int i) {
        return true;
    }

    default boolean isExternalLiveWallpaper() {
        return false;
    }

    default boolean isExternalLiveWallpaper(int i) {
        return false;
    }

    default boolean isStockLiveWallpaper(int i) {
        return false;
    }

    default boolean isSubDisplay() {
        return false;
    }

    default boolean isSupportCMFFeature() {
        return false;
    }

    default boolean isSupportDefaultMultipleWallpaper() {
        return false;
    }

    default boolean isSystemAndLockPaired(int i) {
        return false;
    }

    default boolean isVideoWallpaper() {
        return false;
    }

    default boolean isWallpaperBackupAllowed(int i) {
        return false;
    }

    default boolean isWallpaperDataExists(int i) {
        return false;
    }

    default void removeOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener) {
    }

    default void removeOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, int i) {
    }

    default void resetMultipleWallpaperSettingIfNeeded() {
    }

    default void semClearBackupWallpapers() {
    }

    default void semClearBackupWallpapers(int i) {
    }

    default Drawable semGetDrawable(int i) {
        return null;
    }

    default Rect semGetSmartCropRect(int i) {
        return null;
    }

    default Uri semGetUri(int i) {
        return null;
    }

    default SemWallpaperColors semGetWallpaperColors(int i) {
        return null;
    }

    default ComponentName semGetWallpaperComponent(int i, int i2) {
        return null;
    }

    default Rect semGetWallpaperCropHint(int i) {
        return null;
    }

    default int semGetWallpaperType(int i) {
        return -1;
    }

    default int semMakeBackupWallpaper() {
        return 1;
    }

    default int semMakeBackupWallpaper(int i) {
        return i == 0 ? -1 : 1;
    }

    default int semMakeBackupWallpaper(int i, int i2) {
        return i == 0 ? -1 : 1;
    }

    default boolean semRestoreBackupWallpaper(int i) {
        return i == 1;
    }

    default void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) {
    }

    default void semSetSmartCropRect(int i, Rect rect, Rect rect2) {
    }

    default void semSetUri(Uri uri, boolean z, int i) throws IOException, PackageManager.NameNotFoundException {
    }

    default void semSetUri(Uri uri, boolean z, int i, int i2) throws IOException, PackageManager.NameNotFoundException {
    }

    default void setAnimatedLockscreenWallpaper(String str) throws IOException {
    }

    default void setAnimatedLockscreenWallpaper(String str, int i) throws IOException {
    }

    default void setAnimatedLockscreenWallpaper(String str, int i, boolean z) throws IOException {
    }

    default int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, Bundle bundle) throws IOException {
        return 0;
    }

    default void setMotionWallpaper(String str) {
    }

    default void setMotionWallpaper(String str, int i) {
    }

    default void setMotionWallpaper(String str, int i, boolean z) {
    }

    default void setResourceAll(int i) throws IOException {
    }

    default int setStream(InputStream inputStream, Rect rect, boolean z, int i, int i2, boolean z2, Bundle bundle) throws IOException {
        return 0;
    }

    default void setStream(InputStream inputStream, int i) throws IOException {
    }

    default void setVideoLockscreenWallpaper(String str) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2, String str3, int i) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, int i2) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, int i2, boolean z) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, int i2, boolean z, boolean z2) {
    }

    default void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, boolean z) {
    }

    default void setWallpaperUri(String str, boolean z, int i) throws IOException, PackageManager.NameNotFoundException {
    }

    default boolean wallpaperSupportsWcg(Bitmap bitmap) {
        return false;
    }

    default Drawable getDrawable(int i) {
        return semGetDrawable(i);
    }

    default ParcelFileDescriptor getLockWallpaperFile(int i) {
        return getLockWallpaperFile(i, 2);
    }

    default int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }
}
