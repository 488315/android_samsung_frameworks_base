package android.app;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.SparseArray;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class DisabledWallpaperManager extends WallpaperManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "DisabledWallpaperManager";
    private static DisabledWallpaperManager sInstance;

    private static <T> T unsupported() {
        return null;
    }

    private static boolean unsupportedBoolean() {
        return false;
    }

    private static int unsupportedInt() {
        return -1;
    }

    @Override // android.app.WallpaperManager
    public boolean isSetWallpaperAllowed() {
        return false;
    }

    @Override // android.app.WallpaperManager
    public boolean isWallpaperSupported() {
        return false;
    }

    static DisabledWallpaperManager getInstance() {
        if (sInstance == null) {
            sInstance = new DisabledWallpaperManager();
        }
        return sInstance;
    }

    private DisabledWallpaperManager() {
    }

    @Override // android.app.WallpaperManager
    public IWallpaperManager getIWallpaperManager() {
        return (IWallpaperManager) unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean isLockscreenLiveWallpaperEnabled() {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean shouldEnableWideColorGamut() {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public Drawable getDrawable() {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable getBuiltInDrawable() {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable getBuiltInDrawable(int i) {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2) {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3) {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable peekDrawable() {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable getFastDrawable() {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Drawable peekFastDrawable() {
        return (Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean wallpaperSupportsWcg(int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public Bitmap getBitmap() {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Bitmap getBitmap(boolean z) {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Bitmap getBitmap(boolean z, int i) {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Bitmap getBitmapAsUser(int i, boolean z) {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Bitmap getBitmapAsUser(int i, boolean z, int i2) {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager, android.app.SemWallpaperManager
    public Bitmap getBitmapAsUser(int i, boolean z, int i2, boolean z2) {
        return (Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Rect peekBitmapDimensions() {
        return (Rect) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Rect peekBitmapDimensions(int i) {
        return (Rect) unsupported();
    }

    @Override // android.app.WallpaperManager
    public Rect peekBitmapDimensions(int i, boolean z) {
        return (Rect) unsupported();
    }

    @Override // android.app.WallpaperManager
    public List<Rect> getBitmapCrops(List<Point> list, int i, boolean z) {
        return (List) unsupported();
    }

    @Override // android.app.WallpaperManager
    public List<Rect> getBitmapCrops(Point point, List<Point> list, Map<Point, Rect> map) {
        return (List) unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperColors getWallpaperColors(Bitmap bitmap, Map<Point, Rect> map) {
        return (WallpaperColors) unsupported();
    }

    @Override // android.app.WallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int i) {
        return (ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void addOnColorsChangedListener(WallpaperManager.OnColorsChangedListener onColorsChangedListener, Handler handler) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void addOnColorsChangedListener(WallpaperManager.OnColorsChangedListener onColorsChangedListener, Handler handler, int i) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void removeOnColorsChangedListener(WallpaperManager.OnColorsChangedListener onColorsChangedListener) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void removeOnColorsChangedListener(WallpaperManager.OnColorsChangedListener onColorsChangedListener, int i) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperColors getWallpaperColors(int i) {
        return (WallpaperColors) unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperColors getWallpaperColors(int i, int i2) {
        return (WallpaperColors) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void addOnColorsChangedListener(WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer, List<RectF> list, int i) throws IllegalArgumentException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void removeOnColorsChangedListener(WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int i, int i2) {
        return (ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int i, boolean z) {
        return (ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void forgetLoadedWallpaper() {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperInfo getWallpaperInfo() {
        return (WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperInfo getWallpaperInfoForUser(int i) {
        return (WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public WallpaperInfo getWallpaperInfo(int i) {
        return (WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager, android.app.SemWallpaperManager
    public WallpaperInfo getWallpaperInfo(int i, int i2) {
        return (WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public ParcelFileDescriptor getWallpaperInfoFile() {
        return (ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public int getWallpaperId(int i) {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public int getWallpaperIdForUser(int i, int i2) {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public Intent getCropAndSetWallpaperIntent(Uri uri) {
        return (Intent) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setResource(int i) throws IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setResource(int i, int i2) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public void setBitmap(Bitmap bitmap) throws IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(Bitmap bitmap, Rect rect, boolean z) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, int i2) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setBitmapWithCrops(Bitmap bitmap, Map<Point, Rect> map, boolean z, int i) throws IOException {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public void setStream(InputStream inputStream) throws IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setStream(InputStream inputStream, Rect rect, boolean z) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setStream(InputStream inputStream, Rect rect, boolean z, int i) throws IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setStreamWithCrops(InputStream inputStream, Map<Point, Rect> map, boolean z, int i) throws IOException {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public int setStreamWithCrops(InputStream inputStream, SparseArray<Rect> sparseArray, boolean z, int i) throws IOException {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public boolean hasResourceWallpaper(int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public int getDesiredMinimumWidth() {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public int getDesiredMinimumHeight() {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public void suggestDesiredDimensions(int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setDisplayPadding(Rect rect) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setDisplayOffset(IBinder iBinder, int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaper() {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaper(int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponent(ComponentName componentName) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperDimAmount(float f) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public float getWallpaperDimAmount() {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public boolean lockScreenWallpaperExists() {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponent(ComponentName componentName, int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponentWithFlags(ComponentName componentName, int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponentWithFlags(ComponentName componentName, int i, int i2) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperOffsets(IBinder iBinder, float f, float f2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperOffsetSteps(float f, float f2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperZoomOut(IBinder iBinder, float f) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaperOffsets(IBinder iBinder) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clear() throws IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clear(int i) throws IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean isWallpaperBackupEligible(int i) {
        return unsupportedBoolean();
    }
}
