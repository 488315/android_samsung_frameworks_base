package android.app;

import android.content.ComponentName;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.util.List;

public interface IWallpaperManager extends IInterface {
    void addOnLocalColorsChangedListener(
            ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer,
            List<RectF> list,
            int i,
            int i2,
            int i3)
            throws RemoteException;

    void clearWallpaper(String str, int i, int i2) throws RemoteException;

    void copyFileToWallpaperFile(int i, String str) throws RemoteException;

    void copyPreloadedFileToWallpaperFile(int i, String str) throws RemoteException;

    void forceRebindWallpaper(int i, int i2) throws RemoteException;

    String getAnimatedPkgName(int i) throws RemoteException;

    Rect getBitmapCrop(Point point, int[] iArr, List<Rect> list) throws RemoteException;

    List getBitmapCrops(List<Point> list, int i, boolean z, int i2) throws RemoteException;

    int getDesktopMode() throws RemoteException;

    String getDeviceColor() throws RemoteException;

    int getDisplayId(int i) throws RemoteException;

    List getFutureBitmapCrops(Point point, List<Point> list, int[] iArr, List<Rect> list2)
            throws RemoteException;

    int getHeightHint(int i) throws RemoteException;

    int getHighlightFilterState(int i) throws RemoteException;

    String getLastCallingPackage(int i) throws RemoteException;

    String getLastCallingPackageWithPrefix(int i, boolean z) throws RemoteException;

    String getLegacyDeviceColor() throws RemoteException;

    int getLidState() throws RemoteException;

    ParcelFileDescriptor getLockWallpaper(
            IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2)
            throws RemoteException;

    int getLockWallpaperType() throws RemoteException;

    String getMotionWallpaperPkgName(int i) throws RemoteException;

    String getName() throws RemoteException;

    ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle)
            throws RemoteException;

    int getSnapshotCount(int i) throws RemoteException;

    int[] getSnapshotKeys(String str, int i) throws RemoteException;

    String getVideoFileName(int i) throws RemoteException;

    String getVideoFilePath(int i) throws RemoteException;

    String getVideoPackage(int i) throws RemoteException;

    @Deprecated
    ParcelFileDescriptor getWallpaper(
            String str,
            IWallpaperManagerCallback iWallpaperManagerCallback,
            int i,
            Bundle bundle,
            int i2)
            throws RemoteException;

    ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2)
            throws RemoteException;

    Bundle getWallpaperAssets(int i, int i2) throws RemoteException;

    WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException;

    Bundle getWallpaperComponentExtras(int i, int i2) throws RemoteException;

    float getWallpaperDimAmount() throws RemoteException;

    Bundle getWallpaperExtras(int i, int i2) throws RemoteException;

    int getWallpaperIdForUser(int i, int i2) throws RemoteException;

    WallpaperInfo getWallpaperInfo(int i) throws RemoteException;

    ParcelFileDescriptor getWallpaperInfoFile(int i) throws RemoteException;

    WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws RemoteException;

    int getWallpaperOrientation(int i, int i2) throws RemoteException;

    ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(int i, int i2, int i3, int i4, int i5)
            throws RemoteException;

    ParcelFileDescriptor getWallpaperWithFeature(
            String str,
            String str2,
            IWallpaperManagerCallback iWallpaperManagerCallback,
            int i,
            Bundle bundle,
            int i2,
            boolean z,
            boolean z2,
            int i3)
            throws RemoteException;

    int getWidthHint(int i) throws RemoteException;

    boolean hasNamedWallpaper(String str) throws RemoteException;

    boolean hasVideoWallpaper() throws RemoteException;

    boolean isDefaultWallpaperState(int i) throws RemoteException;

    boolean isDesktopMode() throws RemoteException;

    boolean isDesktopModeEnabled(int i) throws RemoteException;

    boolean isDesktopStandAloneMode() throws RemoteException;

    boolean isSetWallpaperAllowed(String str) throws RemoteException;

    boolean isSnapshotTestMode() throws RemoteException;

    boolean isStaticWallpaper(int i) throws RemoteException;

    boolean isStockLiveWallpaper(int i, int i2) throws RemoteException;

    boolean isSystemAndLockPaired(int i) throws RemoteException;

    boolean isValidSnapshot(int i) throws RemoteException;

    boolean isVideoWallpaper() throws RemoteException;

    boolean isVirtualWallpaperDisplay(int i) throws RemoteException;

    boolean isWaitingForUnlockUser(int i, int i2) throws RemoteException;

    boolean isWallpaperBackupAllowed(int i, int i2) throws RemoteException;

    boolean isWallpaperBackupEligible(int i, int i2) throws RemoteException;

    boolean isWallpaperDataExists(int i, int i2) throws RemoteException;

    boolean isWallpaperSupported(String str) throws RemoteException;

    boolean lockScreenWallpaperExists() throws RemoteException;

    int makeSnapshot(int i, int i2, Bundle bundle) throws RemoteException;

    void notifyAodVisibilityState(int i) throws RemoteException;

    void notifyGoingToSleep(int i, int i2, Bundle bundle) throws RemoteException;

    void notifyPid(int i, int i2, String str, boolean z) throws RemoteException;

    void notifyWakingUp(int i, int i2, Bundle bundle) throws RemoteException;

    void registerWallpaperColorsCallback(
            IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2)
            throws RemoteException;

    void removeOnLocalColorsChangedListener(
            ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer,
            List<RectF> list,
            int i,
            int i2,
            int i3)
            throws RemoteException;

    void removeSnapshotByKey(int i) throws RemoteException;

    void removeSnapshotBySource(String str) throws RemoteException;

    void removeSnapshotByWhich(int i) throws RemoteException;

    boolean restoreSnapshot(int i, String str) throws RemoteException;

    void semClearWallpaperThumbnailCache(int i, int i2, String str) throws RemoteException;

    SemWallpaperColors semGetPrimaryWallpaperColors(int i) throws RemoteException;

    Rect semGetSmartCropRect(int i) throws RemoteException;

    ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3)
            throws RemoteException;

    String semGetUri(int i, String str) throws RemoteException;

    SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException;

    ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException;

    Rect semGetWallpaperCropHint(int i) throws RemoteException;

    int semGetWallpaperType(int i) throws RemoteException;

    boolean semIsPreloadedWallpaper(int i, int i2) throws RemoteException;

    void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException;

    void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException;

    void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i)
            throws RemoteException;

    void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException;

    void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle)
            throws RemoteException;

    ParcelFileDescriptor semSetWallpaper(
            String str,
            String str2,
            int[] iArr,
            List<Rect> list,
            boolean z,
            Bundle bundle,
            int i,
            IWallpaperManagerCallback iWallpaperManagerCallback,
            int i2,
            int i3,
            boolean z2,
            Bundle bundle2)
            throws RemoteException;

    void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

    boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback)
            throws RemoteException;

    void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException;

    void setDisplayPadding(Rect rect, String str, int i) throws RemoteException;

    void setInAmbientMode(boolean z, long j) throws RemoteException;

    boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback)
            throws RemoteException;

    void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

    boolean setSnapshotSource(int i, String str) throws RemoteException;

    void setSnapshotTestMode(boolean z) throws RemoteException;

    void setVideoWallpaper(
            String str,
            String str2,
            String str3,
            String str4,
            int i,
            int i2,
            boolean z,
            Bundle bundle)
            throws RemoteException;

    ParcelFileDescriptor setWallpaper(
            String str,
            String str2,
            int[] iArr,
            List<Rect> list,
            boolean z,
            Bundle bundle,
            int i,
            IWallpaperManagerCallback iWallpaperManagerCallback,
            int i2,
            int i3,
            boolean z2,
            Bundle bundle2)
            throws RemoteException;

    void setWallpaperComponent(ComponentName componentName) throws RemoteException;

    void setWallpaperComponentChecked(
            ComponentName componentName, String str, int i, int i2, Bundle bundle)
            throws RemoteException;

    void setWallpaperDimAmount(float f) throws RemoteException;

    void settingsRestored() throws RemoteException;

    void unregisterWallpaperColorsCallback(
            IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2)
            throws RemoteException;

    public static class Default implements IWallpaperManager {
        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor setWallpaper(
                String name,
                String callingPackage,
                int[] screenOrientations,
                List<Rect> crops,
                boolean allowBackup,
                Bundle extras,
                int which,
                IWallpaperManagerCallback completion,
                int userId,
                int type,
                boolean isPreloaded,
                Bundle inputExtras)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponentChecked(
                ComponentName name, String callingPackage, int which, int userId, Bundle extras)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponent(ComponentName name) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaper(
                String callingPkg,
                IWallpaperManagerCallback cb,
                int which,
                Bundle outParams,
                int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperWithFeature(
                String callingPkg,
                String callingFeatureId,
                IWallpaperManagerCallback cb,
                int which,
                Bundle outParams,
                int userId,
                boolean getCropped,
                boolean includeCopied,
                int wpType)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getLockWallpaper(
                IWallpaperManagerCallback cb, Bundle outParams, int userId, int which)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public List getBitmapCrops(
                List<Point> displaySizes, int which, boolean originalBitmap, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public List getFutureBitmapCrops(
                Point bitmapSize,
                List<Point> displaySizes,
                int[] screenOrientations,
                List<Rect> crops)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Rect getBitmapCrop(Point bitmapSize, int[] screenOrientations, List<Rect> crops)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperInfo getWallpaperInfo(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperInfo getWallpaperInfoWithFlags(int which, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperInfoFile(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void clearWallpaper(String callingPackage, int which, int userId)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean hasNamedWallpaper(String name) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setDimensionHints(int width, int height, String callingPackage, int displayId)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public int getWidthHint(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int getHeightHint(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public void setDisplayPadding(Rect padding, String callingPackage, int displayId)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public String getName() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void settingsRestored() throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperSupported(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSetWallpaperAllowed(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperBackupEligible(int which, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor semSetWallpaper(
                String name,
                String callingPackage,
                int[] screenOrientations,
                List<Rect> crops,
                boolean allowBackup,
                Bundle extras,
                int which,
                IWallpaperManagerCallback completion,
                int userId,
                int wallpaperType,
                boolean isPreloaded,
                Bundle inputExtras)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperBackupAllowed(int which, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean setCoverWallpaperCallback(IWallpaperManagerCallback cb)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperColors getWallpaperColors(int which, int userId, int displayId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void removeOnLocalColorsChangedListener(
                ILocalWallpaperColorConsumer callback,
                List<RectF> area,
                int which,
                int userId,
                int displayId)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void addOnLocalColorsChangedListener(
                ILocalWallpaperColorConsumer callback,
                List<RectF> regions,
                int which,
                int userId,
                int displayId)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void registerWallpaperColorsCallback(
                IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void unregisterWallpaperColorsCallback(
                IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void setInAmbientMode(boolean inAmbientMode, long animationDuration)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void notifyWakingUp(int x, int y, Bundle extras) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void notifyGoingToSleep(int x, int y, Bundle extras) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void setWallpaperDimAmount(float dimAmount) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public float getWallpaperDimAmount() throws RemoteException {
            return 0.0f;
        }

        @Override // android.app.IWallpaperManager
        public boolean lockScreenWallpaperExists() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isStaticWallpaper(int which) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDesktopMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public int getDesktopMode() throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDesktopModeEnabled(int which) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDesktopStandAloneMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public int getLockWallpaperType() throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int semGetWallpaperType(int which) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public ComponentName semGetWallpaperComponent(int which, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public boolean semIsPreloadedWallpaper(int which, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public Rect semGetWallpaperCropHint(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDefaultWallpaperState(int which) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setVideoWallpaper(
                String videoFilePath,
                String themePackage,
                String videoColor,
                String callingPackage,
                int userId,
                int which,
                boolean allowBackup,
                Bundle extras)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean isVideoWallpaper() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean hasVideoWallpaper() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor semGetThumbnailFileDescriptor(
                int which, int userId, int rotation) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(
                int type, int userId, int which, int orientation, int mode) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoFilePath(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoPackage(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoFileName(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setMotionWallpaper(
                String pkgName, String callingPackage, int which, boolean allowBackup)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public String getMotionWallpaperPkgName(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setAnimatedWallpaper(
                String pkgName, String callingPackage, int which, boolean allowBackup)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void removeSnapshotByWhich(int which) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void removeSnapshotByKey(int key) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void removeSnapshotBySource(String source) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public int makeSnapshot(int which, int key, Bundle extras) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean restoreSnapshot(int key, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSnapshotTestMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setSnapshotTestMode(boolean enable) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public int getSnapshotCount(int which) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean setSnapshotSource(int key, String source) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isValidSnapshot(int key) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public int[] getSnapshotKeys(String source, int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getAnimatedPkgName(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getDeviceColor() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getLegacyDeviceColor() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getLastCallingPackage(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getLastCallingPackageWithPrefix(int which, boolean includePrefix)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void copyFileToWallpaperFile(int which, String callingPackage)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void copyPreloadedFileToWallpaperFile(int which, String callingPackage)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean isSystemAndLockPaired(int mode) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public int getHighlightFilterState(int which) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperComponentExtras(int which, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperExtras(int which, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperAssets(int which, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperAssetFile(
                String callingPkg, int which, int userId, String assetFilePath)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getScreenshotFileDescriptor(
                int which, int userId, Bundle extras) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWallpaperOrientation(int which, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public void semSendWallpaperCommand(int which, String action, Bundle extras)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public SemWallpaperColors semGetWallpaperColors(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public SemWallpaperColors semGetPrimaryWallpaperColors(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void semClearWallpaperThumbnailCache(int which, int userId, String callingPackage)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void semRequestWallpaperColorsAnalysis(int which, String callingPackage)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void semSetDLSWallpaperColors(SemWallpaperColors colors, int which)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void semSetSmartCropRect(int which, Rect original, Rect smartCrop)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public Rect semGetSmartCropRect(int which) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getLidState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int getDisplayId(int which) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean isVirtualWallpaperDisplay(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWaitingForUnlockUser(int which, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void semSetUri(
                String uri,
                boolean allowBackup,
                int which,
                int type,
                String callerPackage,
                int userId,
                Bundle extras)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public String semGetUri(int which, String callerPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void forceRebindWallpaper(int which, int userId) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public void notifyPid(int uid, int pid, String packageName, boolean enable)
                throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperDataExists(int userId, int which) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void notifyAodVisibilityState(int state) throws RemoteException {}

        @Override // android.app.IWallpaperManager
        public boolean isStockLiveWallpaper(int which, int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWallpaperManager {
        public static final String DESCRIPTOR = "android.app.IWallpaperManager";
        static final int TRANSACTION_addOnLocalColorsChangedListener = 31;
        static final int TRANSACTION_clearWallpaper = 14;
        static final int TRANSACTION_copyFileToWallpaperFile = 78;
        static final int TRANSACTION_copyPreloadedFileToWallpaperFile = 79;
        static final int TRANSACTION_forceRebindWallpaper = 102;
        static final int TRANSACTION_getAnimatedPkgName = 73;
        static final int TRANSACTION_getBitmapCrop = 9;
        static final int TRANSACTION_getBitmapCrops = 7;
        static final int TRANSACTION_getDesktopMode = 42;
        static final int TRANSACTION_getDeviceColor = 74;
        static final int TRANSACTION_getDisplayId = 97;
        static final int TRANSACTION_getFutureBitmapCrops = 8;
        static final int TRANSACTION_getHeightHint = 18;
        static final int TRANSACTION_getHighlightFilterState = 81;
        static final int TRANSACTION_getLastCallingPackage = 76;
        static final int TRANSACTION_getLastCallingPackageWithPrefix = 77;
        static final int TRANSACTION_getLegacyDeviceColor = 75;
        static final int TRANSACTION_getLidState = 96;
        static final int TRANSACTION_getLockWallpaper = 6;
        static final int TRANSACTION_getLockWallpaperType = 45;
        static final int TRANSACTION_getMotionWallpaperPkgName = 60;
        static final int TRANSACTION_getName = 20;
        static final int TRANSACTION_getScreenshotFileDescriptor = 86;
        static final int TRANSACTION_getSnapshotCount = 69;
        static final int TRANSACTION_getSnapshotKeys = 72;
        static final int TRANSACTION_getVideoFileName = 58;
        static final int TRANSACTION_getVideoFilePath = 56;
        static final int TRANSACTION_getVideoPackage = 57;
        static final int TRANSACTION_getWallpaper = 4;
        static final int TRANSACTION_getWallpaperAssetFile = 85;
        static final int TRANSACTION_getWallpaperAssets = 84;
        static final int TRANSACTION_getWallpaperColors = 29;
        static final int TRANSACTION_getWallpaperComponentExtras = 82;
        static final int TRANSACTION_getWallpaperDimAmount = 38;
        static final int TRANSACTION_getWallpaperExtras = 83;
        static final int TRANSACTION_getWallpaperIdForUser = 10;
        static final int TRANSACTION_getWallpaperInfo = 11;
        static final int TRANSACTION_getWallpaperInfoFile = 13;
        static final int TRANSACTION_getWallpaperInfoWithFlags = 12;
        static final int TRANSACTION_getWallpaperOrientation = 87;
        static final int TRANSACTION_getWallpaperThumbnailFileDescriptor = 55;
        static final int TRANSACTION_getWallpaperWithFeature = 5;
        static final int TRANSACTION_getWidthHint = 17;
        static final int TRANSACTION_hasNamedWallpaper = 15;
        static final int TRANSACTION_hasVideoWallpaper = 53;
        static final int TRANSACTION_isDefaultWallpaperState = 50;
        static final int TRANSACTION_isDesktopMode = 41;
        static final int TRANSACTION_isDesktopModeEnabled = 43;
        static final int TRANSACTION_isDesktopStandAloneMode = 44;
        static final int TRANSACTION_isSetWallpaperAllowed = 23;
        static final int TRANSACTION_isSnapshotTestMode = 67;
        static final int TRANSACTION_isStaticWallpaper = 40;
        static final int TRANSACTION_isStockLiveWallpaper = 106;
        static final int TRANSACTION_isSystemAndLockPaired = 80;
        static final int TRANSACTION_isValidSnapshot = 71;
        static final int TRANSACTION_isVideoWallpaper = 52;
        static final int TRANSACTION_isVirtualWallpaperDisplay = 98;
        static final int TRANSACTION_isWaitingForUnlockUser = 99;
        static final int TRANSACTION_isWallpaperBackupAllowed = 26;
        static final int TRANSACTION_isWallpaperBackupEligible = 24;
        static final int TRANSACTION_isWallpaperDataExists = 104;
        static final int TRANSACTION_isWallpaperSupported = 22;
        static final int TRANSACTION_lockScreenWallpaperExists = 39;
        static final int TRANSACTION_makeSnapshot = 65;
        static final int TRANSACTION_notifyAodVisibilityState = 105;
        static final int TRANSACTION_notifyGoingToSleep = 36;
        static final int TRANSACTION_notifyPid = 103;
        static final int TRANSACTION_notifyWakingUp = 35;
        static final int TRANSACTION_registerWallpaperColorsCallback = 32;
        static final int TRANSACTION_removeOnLocalColorsChangedListener = 30;
        static final int TRANSACTION_removeSnapshotByKey = 63;
        static final int TRANSACTION_removeSnapshotBySource = 64;
        static final int TRANSACTION_removeSnapshotByWhich = 62;
        static final int TRANSACTION_restoreSnapshot = 66;
        static final int TRANSACTION_semClearWallpaperThumbnailCache = 91;
        static final int TRANSACTION_semGetPrimaryWallpaperColors = 90;
        static final int TRANSACTION_semGetSmartCropRect = 95;
        static final int TRANSACTION_semGetThumbnailFileDescriptor = 54;
        static final int TRANSACTION_semGetUri = 101;
        static final int TRANSACTION_semGetWallpaperColors = 89;
        static final int TRANSACTION_semGetWallpaperComponent = 47;
        static final int TRANSACTION_semGetWallpaperCropHint = 49;
        static final int TRANSACTION_semGetWallpaperType = 46;
        static final int TRANSACTION_semIsPreloadedWallpaper = 48;
        static final int TRANSACTION_semRequestWallpaperColorsAnalysis = 92;
        static final int TRANSACTION_semSendWallpaperCommand = 88;
        static final int TRANSACTION_semSetDLSWallpaperColors = 93;
        static final int TRANSACTION_semSetSmartCropRect = 94;
        static final int TRANSACTION_semSetUri = 100;
        static final int TRANSACTION_semSetWallpaper = 25;
        static final int TRANSACTION_setAnimatedWallpaper = 61;
        static final int TRANSACTION_setCoverWallpaperCallback = 28;
        static final int TRANSACTION_setDimensionHints = 16;
        static final int TRANSACTION_setDisplayPadding = 19;
        static final int TRANSACTION_setInAmbientMode = 34;
        static final int TRANSACTION_setLockWallpaperCallback = 27;
        static final int TRANSACTION_setMotionWallpaper = 59;
        static final int TRANSACTION_setSnapshotSource = 70;
        static final int TRANSACTION_setSnapshotTestMode = 68;
        static final int TRANSACTION_setVideoWallpaper = 51;
        static final int TRANSACTION_setWallpaper = 1;
        static final int TRANSACTION_setWallpaperComponent = 3;
        static final int TRANSACTION_setWallpaperComponentChecked = 2;
        static final int TRANSACTION_setWallpaperDimAmount = 37;
        static final int TRANSACTION_settingsRestored = 21;
        static final int TRANSACTION_unregisterWallpaperColorsCallback = 33;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperManager)) {
                return (IWallpaperManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setWallpaper";
                case 2:
                    return "setWallpaperComponentChecked";
                case 3:
                    return "setWallpaperComponent";
                case 4:
                    return "getWallpaper";
                case 5:
                    return "getWallpaperWithFeature";
                case 6:
                    return "getLockWallpaper";
                case 7:
                    return "getBitmapCrops";
                case 8:
                    return "getFutureBitmapCrops";
                case 9:
                    return "getBitmapCrop";
                case 10:
                    return "getWallpaperIdForUser";
                case 11:
                    return "getWallpaperInfo";
                case 12:
                    return "getWallpaperInfoWithFlags";
                case 13:
                    return "getWallpaperInfoFile";
                case 14:
                    return "clearWallpaper";
                case 15:
                    return "hasNamedWallpaper";
                case 16:
                    return "setDimensionHints";
                case 17:
                    return "getWidthHint";
                case 18:
                    return "getHeightHint";
                case 19:
                    return "setDisplayPadding";
                case 20:
                    return "getName";
                case 21:
                    return "settingsRestored";
                case 22:
                    return "isWallpaperSupported";
                case 23:
                    return "isSetWallpaperAllowed";
                case 24:
                    return "isWallpaperBackupEligible";
                case 25:
                    return "semSetWallpaper";
                case 26:
                    return "isWallpaperBackupAllowed";
                case 27:
                    return "setLockWallpaperCallback";
                case 28:
                    return "setCoverWallpaperCallback";
                case 29:
                    return "getWallpaperColors";
                case 30:
                    return "removeOnLocalColorsChangedListener";
                case 31:
                    return "addOnLocalColorsChangedListener";
                case 32:
                    return "registerWallpaperColorsCallback";
                case 33:
                    return "unregisterWallpaperColorsCallback";
                case 34:
                    return "setInAmbientMode";
                case 35:
                    return "notifyWakingUp";
                case 36:
                    return "notifyGoingToSleep";
                case 37:
                    return "setWallpaperDimAmount";
                case 38:
                    return "getWallpaperDimAmount";
                case 39:
                    return "lockScreenWallpaperExists";
                case 40:
                    return "isStaticWallpaper";
                case 41:
                    return "isDesktopMode";
                case 42:
                    return "getDesktopMode";
                case 43:
                    return "isDesktopModeEnabled";
                case 44:
                    return "isDesktopStandAloneMode";
                case 45:
                    return "getLockWallpaperType";
                case 46:
                    return "semGetWallpaperType";
                case 47:
                    return "semGetWallpaperComponent";
                case 48:
                    return "semIsPreloadedWallpaper";
                case 49:
                    return "semGetWallpaperCropHint";
                case 50:
                    return "isDefaultWallpaperState";
                case 51:
                    return "setVideoWallpaper";
                case 52:
                    return "isVideoWallpaper";
                case 53:
                    return "hasVideoWallpaper";
                case 54:
                    return "semGetThumbnailFileDescriptor";
                case 55:
                    return "getWallpaperThumbnailFileDescriptor";
                case 56:
                    return "getVideoFilePath";
                case 57:
                    return "getVideoPackage";
                case 58:
                    return "getVideoFileName";
                case 59:
                    return "setMotionWallpaper";
                case 60:
                    return "getMotionWallpaperPkgName";
                case 61:
                    return "setAnimatedWallpaper";
                case 62:
                    return "removeSnapshotByWhich";
                case 63:
                    return "removeSnapshotByKey";
                case 64:
                    return "removeSnapshotBySource";
                case 65:
                    return "makeSnapshot";
                case 66:
                    return "restoreSnapshot";
                case 67:
                    return "isSnapshotTestMode";
                case 68:
                    return "setSnapshotTestMode";
                case 69:
                    return "getSnapshotCount";
                case 70:
                    return "setSnapshotSource";
                case 71:
                    return "isValidSnapshot";
                case 72:
                    return "getSnapshotKeys";
                case 73:
                    return "getAnimatedPkgName";
                case 74:
                    return "getDeviceColor";
                case 75:
                    return "getLegacyDeviceColor";
                case 76:
                    return "getLastCallingPackage";
                case 77:
                    return "getLastCallingPackageWithPrefix";
                case 78:
                    return "copyFileToWallpaperFile";
                case 79:
                    return "copyPreloadedFileToWallpaperFile";
                case 80:
                    return "isSystemAndLockPaired";
                case 81:
                    return "getHighlightFilterState";
                case 82:
                    return "getWallpaperComponentExtras";
                case 83:
                    return "getWallpaperExtras";
                case 84:
                    return "getWallpaperAssets";
                case 85:
                    return "getWallpaperAssetFile";
                case 86:
                    return "getScreenshotFileDescriptor";
                case 87:
                    return "getWallpaperOrientation";
                case 88:
                    return "semSendWallpaperCommand";
                case 89:
                    return "semGetWallpaperColors";
                case 90:
                    return "semGetPrimaryWallpaperColors";
                case 91:
                    return "semClearWallpaperThumbnailCache";
                case 92:
                    return "semRequestWallpaperColorsAnalysis";
                case 93:
                    return "semSetDLSWallpaperColors";
                case 94:
                    return "semSetSmartCropRect";
                case 95:
                    return "semGetSmartCropRect";
                case 96:
                    return "getLidState";
                case 97:
                    return "getDisplayId";
                case 98:
                    return "isVirtualWallpaperDisplay";
                case 99:
                    return "isWaitingForUnlockUser";
                case 100:
                    return "semSetUri";
                case 101:
                    return "semGetUri";
                case 102:
                    return "forceRebindWallpaper";
                case 103:
                    return "notifyPid";
                case 104:
                    return "isWallpaperDataExists";
                case 105:
                    return "notifyAodVisibilityState";
                case 106:
                    return "isStockLiveWallpaper";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int[] _arg2 = data.createIntArray();
                    List<Rect> _arg3 = data.createTypedArrayList(Rect.CREATOR);
                    boolean _arg4 = data.readBoolean();
                    Bundle _arg5 = new Bundle();
                    int _arg6 = data.readInt();
                    IWallpaperManagerCallback _arg7 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg8 = data.readInt();
                    int _arg9 = data.readInt();
                    boolean _arg10 = data.readBoolean();
                    Bundle _arg11 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result =
                            setWallpaper(
                                    _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8,
                                    _arg9, _arg10, _arg11);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    reply.writeTypedObject(_arg5, 1);
                    return true;
                case 2:
                    ComponentName _arg02 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    Bundle _arg42 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    setWallpaperComponentChecked(_arg02, _arg12, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 3:
                    ComponentName _arg03 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setWallpaperComponent(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    IWallpaperManagerCallback _arg13 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg23 = data.readInt();
                    Bundle _arg33 = new Bundle();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result2 =
                            getWallpaper(_arg04, _arg13, _arg23, _arg33, _arg43);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    reply.writeTypedObject(_arg33, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    String _arg14 = data.readString();
                    IWallpaperManagerCallback _arg24 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg34 = data.readInt();
                    Bundle _arg44 = new Bundle();
                    int _arg52 = data.readInt();
                    boolean _arg62 = data.readBoolean();
                    boolean _arg72 = data.readBoolean();
                    int _arg82 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result3 =
                            getWallpaperWithFeature(
                                    _arg05, _arg14, _arg24, _arg34, _arg44, _arg52, _arg62, _arg72,
                                    _arg82);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    reply.writeTypedObject(_arg44, 1);
                    return true;
                case 6:
                    IWallpaperManagerCallback _arg06 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    Bundle _arg15 = new Bundle();
                    int _arg25 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result4 =
                            getLockWallpaper(_arg06, _arg15, _arg25, _arg35);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    reply.writeTypedObject(_arg15, 1);
                    return true;
                case 7:
                    List<Point> _arg07 = data.createTypedArrayList(Point.CREATOR);
                    int _arg16 = data.readInt();
                    boolean _arg26 = data.readBoolean();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    List _result5 = getBitmapCrops(_arg07, _arg16, _arg26, _arg36);
                    reply.writeNoException();
                    reply.writeList(_result5);
                    return true;
                case 8:
                    Point _arg08 = (Point) data.readTypedObject(Point.CREATOR);
                    List<Point> _arg17 = data.createTypedArrayList(Point.CREATOR);
                    int[] _arg27 = data.createIntArray();
                    List<Rect> _arg37 = data.createTypedArrayList(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    List _result6 = getFutureBitmapCrops(_arg08, _arg17, _arg27, _arg37);
                    reply.writeNoException();
                    reply.writeList(_result6);
                    return true;
                case 9:
                    Point _arg09 = (Point) data.readTypedObject(Point.CREATOR);
                    int[] _arg18 = data.createIntArray();
                    List<Rect> _arg28 = data.createTypedArrayList(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    Rect _result7 = getBitmapCrop(_arg09, _arg18, _arg28);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result8 = getWallpaperIdForUser(_arg010, _arg19);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    WallpaperInfo _result9 = getWallpaperInfo(_arg011);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    WallpaperInfo _result10 = getWallpaperInfoWithFlags(_arg012, _arg110);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result11 = getWallpaperInfoFile(_arg013);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    int _arg111 = data.readInt();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    clearWallpaper(_arg014, _arg111, _arg29);
                    reply.writeNoException();
                    return true;
                case 15:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result12 = hasNamedWallpaper(_arg015);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    int _arg112 = data.readInt();
                    String _arg210 = data.readString();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    setDimensionHints(_arg016, _arg112, _arg210, _arg38);
                    reply.writeNoException();
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result13 = getWidthHint(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result14 = getHeightHint(_arg018);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 19:
                    Rect _arg019 = (Rect) data.readTypedObject(Rect.CREATOR);
                    String _arg113 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    setDisplayPadding(_arg019, _arg113, _arg211);
                    reply.writeNoException();
                    return true;
                case 20:
                    String _result15 = getName();
                    reply.writeNoException();
                    reply.writeString(_result15);
                    return true;
                case 21:
                    settingsRestored();
                    reply.writeNoException();
                    return true;
                case 22:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result16 = isWallpaperSupported(_arg020);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 23:
                    String _arg021 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result17 = isSetWallpaperAllowed(_arg021);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 24:
                    int _arg022 = data.readInt();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = isWallpaperBackupEligible(_arg022, _arg114);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 25:
                    String _arg023 = data.readString();
                    String _arg115 = data.readString();
                    int[] _arg212 = data.createIntArray();
                    List<Rect> _arg39 = data.createTypedArrayList(Rect.CREATOR);
                    boolean _arg45 = data.readBoolean();
                    Bundle _arg53 = new Bundle();
                    int _arg63 = data.readInt();
                    IWallpaperManagerCallback _arg73 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg83 = data.readInt();
                    int _arg92 = data.readInt();
                    boolean _arg102 = data.readBoolean();
                    Bundle _arg116 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result19 =
                            semSetWallpaper(
                                    _arg023, _arg115, _arg212, _arg39, _arg45, _arg53, _arg63,
                                    _arg73, _arg83, _arg92, _arg102, _arg116);
                    reply.writeNoException();
                    reply.writeTypedObject(_result19, 1);
                    reply.writeTypedObject(_arg53, 1);
                    return true;
                case 26:
                    int _arg024 = data.readInt();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = isWallpaperBackupAllowed(_arg024, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 27:
                    IWallpaperManagerCallback _arg025 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result21 = setLockWallpaperCallback(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 28:
                    IWallpaperManagerCallback _arg026 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result22 = setCoverWallpaperCallback(_arg026);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 29:
                    int _arg027 = data.readInt();
                    int _arg118 = data.readInt();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    WallpaperColors _result23 = getWallpaperColors(_arg027, _arg118, _arg213);
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 30:
                    ILocalWallpaperColorConsumer _arg028 =
                            ILocalWallpaperColorConsumer.Stub.asInterface(data.readStrongBinder());
                    List<RectF> _arg119 = data.createTypedArrayList(RectF.CREATOR);
                    int _arg214 = data.readInt();
                    int _arg310 = data.readInt();
                    int _arg46 = data.readInt();
                    data.enforceNoDataAvail();
                    removeOnLocalColorsChangedListener(_arg028, _arg119, _arg214, _arg310, _arg46);
                    reply.writeNoException();
                    return true;
                case 31:
                    ILocalWallpaperColorConsumer _arg029 =
                            ILocalWallpaperColorConsumer.Stub.asInterface(data.readStrongBinder());
                    List<RectF> _arg120 = data.createTypedArrayList(RectF.CREATOR);
                    int _arg215 = data.readInt();
                    int _arg311 = data.readInt();
                    int _arg47 = data.readInt();
                    data.enforceNoDataAvail();
                    addOnLocalColorsChangedListener(_arg029, _arg120, _arg215, _arg311, _arg47);
                    reply.writeNoException();
                    return true;
                case 32:
                    IWallpaperManagerCallback _arg030 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg121 = data.readInt();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    registerWallpaperColorsCallback(_arg030, _arg121, _arg216);
                    reply.writeNoException();
                    return true;
                case 33:
                    IWallpaperManagerCallback _arg031 =
                            IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg122 = data.readInt();
                    int _arg217 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterWallpaperColorsCallback(_arg031, _arg122, _arg217);
                    reply.writeNoException();
                    return true;
                case 34:
                    boolean _arg032 = data.readBoolean();
                    long _arg123 = data.readLong();
                    data.enforceNoDataAvail();
                    setInAmbientMode(_arg032, _arg123);
                    return true;
                case 35:
                    int _arg033 = data.readInt();
                    int _arg124 = data.readInt();
                    Bundle _arg218 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyWakingUp(_arg033, _arg124, _arg218);
                    return true;
                case 36:
                    int _arg034 = data.readInt();
                    int _arg125 = data.readInt();
                    Bundle _arg219 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyGoingToSleep(_arg034, _arg125, _arg219);
                    reply.writeNoException();
                    return true;
                case 37:
                    float _arg035 = data.readFloat();
                    data.enforceNoDataAvail();
                    setWallpaperDimAmount(_arg035);
                    return true;
                case 38:
                    float _result24 = getWallpaperDimAmount();
                    reply.writeNoException();
                    reply.writeFloat(_result24);
                    return true;
                case 39:
                    boolean _result25 = lockScreenWallpaperExists();
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 40:
                    int _arg036 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result26 = isStaticWallpaper(_arg036);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 41:
                    boolean _result27 = isDesktopMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 42:
                    int _result28 = getDesktopMode();
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case 43:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = isDesktopModeEnabled(_arg037);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 44:
                    boolean _result30 = isDesktopStandAloneMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 45:
                    int _result31 = getLockWallpaperType();
                    reply.writeNoException();
                    reply.writeInt(_result31);
                    return true;
                case 46:
                    int _arg038 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result32 = semGetWallpaperType(_arg038);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 47:
                    int _arg039 = data.readInt();
                    int _arg126 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result33 = semGetWallpaperComponent(_arg039, _arg126);
                    reply.writeNoException();
                    reply.writeTypedObject(_result33, 1);
                    return true;
                case 48:
                    int _arg040 = data.readInt();
                    int _arg127 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result34 = semIsPreloadedWallpaper(_arg040, _arg127);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 49:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    Rect _result35 = semGetWallpaperCropHint(_arg041);
                    reply.writeNoException();
                    reply.writeTypedObject(_result35, 1);
                    return true;
                case 50:
                    int _arg042 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result36 = isDefaultWallpaperState(_arg042);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 51:
                    String _arg043 = data.readString();
                    String _arg128 = data.readString();
                    String _arg220 = data.readString();
                    String _arg312 = data.readString();
                    int _arg48 = data.readInt();
                    int _arg54 = data.readInt();
                    boolean _arg64 = data.readBoolean();
                    Bundle _arg74 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    setVideoWallpaper(
                            _arg043, _arg128, _arg220, _arg312, _arg48, _arg54, _arg64, _arg74);
                    reply.writeNoException();
                    return true;
                case 52:
                    boolean _result37 = isVideoWallpaper();
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 53:
                    boolean _result38 = hasVideoWallpaper();
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 54:
                    int _arg044 = data.readInt();
                    int _arg129 = data.readInt();
                    int _arg221 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result39 =
                            semGetThumbnailFileDescriptor(_arg044, _arg129, _arg221);
                    reply.writeNoException();
                    reply.writeTypedObject(_result39, 1);
                    return true;
                case 55:
                    int _arg045 = data.readInt();
                    int _arg130 = data.readInt();
                    int _arg222 = data.readInt();
                    int _arg313 = data.readInt();
                    int _arg49 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result40 =
                            getWallpaperThumbnailFileDescriptor(
                                    _arg045, _arg130, _arg222, _arg313, _arg49);
                    reply.writeNoException();
                    reply.writeTypedObject(_result40, 1);
                    return true;
                case 56:
                    int _arg046 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result41 = getVideoFilePath(_arg046);
                    reply.writeNoException();
                    reply.writeString(_result41);
                    return true;
                case 57:
                    int _arg047 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result42 = getVideoPackage(_arg047);
                    reply.writeNoException();
                    reply.writeString(_result42);
                    return true;
                case 58:
                    int _arg048 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result43 = getVideoFileName(_arg048);
                    reply.writeNoException();
                    reply.writeString(_result43);
                    return true;
                case 59:
                    String _arg049 = data.readString();
                    String _arg131 = data.readString();
                    int _arg223 = data.readInt();
                    boolean _arg314 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMotionWallpaper(_arg049, _arg131, _arg223, _arg314);
                    reply.writeNoException();
                    return true;
                case 60:
                    int _arg050 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result44 = getMotionWallpaperPkgName(_arg050);
                    reply.writeNoException();
                    reply.writeString(_result44);
                    return true;
                case 61:
                    String _arg051 = data.readString();
                    String _arg132 = data.readString();
                    int _arg224 = data.readInt();
                    boolean _arg315 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAnimatedWallpaper(_arg051, _arg132, _arg224, _arg315);
                    reply.writeNoException();
                    return true;
                case 62:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    removeSnapshotByWhich(_arg052);
                    reply.writeNoException();
                    return true;
                case 63:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    removeSnapshotByKey(_arg053);
                    reply.writeNoException();
                    return true;
                case 64:
                    String _arg054 = data.readString();
                    data.enforceNoDataAvail();
                    removeSnapshotBySource(_arg054);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _arg055 = data.readInt();
                    int _arg133 = data.readInt();
                    Bundle _arg225 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result45 = makeSnapshot(_arg055, _arg133, _arg225);
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    return true;
                case 66:
                    int _arg056 = data.readInt();
                    String _arg134 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result46 = restoreSnapshot(_arg056, _arg134);
                    reply.writeNoException();
                    reply.writeBoolean(_result46);
                    return true;
                case 67:
                    boolean _result47 = isSnapshotTestMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 68:
                    boolean _arg057 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSnapshotTestMode(_arg057);
                    reply.writeNoException();
                    return true;
                case 69:
                    int _arg058 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result48 = getSnapshotCount(_arg058);
                    reply.writeNoException();
                    reply.writeInt(_result48);
                    return true;
                case 70:
                    int _arg059 = data.readInt();
                    String _arg135 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result49 = setSnapshotSource(_arg059, _arg135);
                    reply.writeNoException();
                    reply.writeBoolean(_result49);
                    return true;
                case 71:
                    int _arg060 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result50 = isValidSnapshot(_arg060);
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 72:
                    String _arg061 = data.readString();
                    int _arg136 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result51 = getSnapshotKeys(_arg061, _arg136);
                    reply.writeNoException();
                    reply.writeIntArray(_result51);
                    return true;
                case 73:
                    int _arg062 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result52 = getAnimatedPkgName(_arg062);
                    reply.writeNoException();
                    reply.writeString(_result52);
                    return true;
                case 74:
                    String _result53 = getDeviceColor();
                    reply.writeNoException();
                    reply.writeString(_result53);
                    return true;
                case 75:
                    String _result54 = getLegacyDeviceColor();
                    reply.writeNoException();
                    reply.writeString(_result54);
                    return true;
                case 76:
                    int _arg063 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result55 = getLastCallingPackage(_arg063);
                    reply.writeNoException();
                    reply.writeString(_result55);
                    return true;
                case 77:
                    int _arg064 = data.readInt();
                    boolean _arg137 = data.readBoolean();
                    data.enforceNoDataAvail();
                    String _result56 = getLastCallingPackageWithPrefix(_arg064, _arg137);
                    reply.writeNoException();
                    reply.writeString(_result56);
                    return true;
                case 78:
                    int _arg065 = data.readInt();
                    String _arg138 = data.readString();
                    data.enforceNoDataAvail();
                    copyFileToWallpaperFile(_arg065, _arg138);
                    reply.writeNoException();
                    return true;
                case 79:
                    int _arg066 = data.readInt();
                    String _arg139 = data.readString();
                    data.enforceNoDataAvail();
                    copyPreloadedFileToWallpaperFile(_arg066, _arg139);
                    reply.writeNoException();
                    return true;
                case 80:
                    int _arg067 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result57 = isSystemAndLockPaired(_arg067);
                    reply.writeNoException();
                    reply.writeBoolean(_result57);
                    return true;
                case 81:
                    int _arg068 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result58 = getHighlightFilterState(_arg068);
                    reply.writeNoException();
                    reply.writeInt(_result58);
                    return true;
                case 82:
                    int _arg069 = data.readInt();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result59 = getWallpaperComponentExtras(_arg069, _arg140);
                    reply.writeNoException();
                    reply.writeTypedObject(_result59, 1);
                    return true;
                case 83:
                    int _arg070 = data.readInt();
                    int _arg141 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result60 = getWallpaperExtras(_arg070, _arg141);
                    reply.writeNoException();
                    reply.writeTypedObject(_result60, 1);
                    return true;
                case 84:
                    int _arg071 = data.readInt();
                    int _arg142 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result61 = getWallpaperAssets(_arg071, _arg142);
                    reply.writeNoException();
                    reply.writeTypedObject(_result61, 1);
                    return true;
                case 85:
                    String _arg072 = data.readString();
                    int _arg143 = data.readInt();
                    int _arg226 = data.readInt();
                    String _arg316 = data.readString();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result62 =
                            getWallpaperAssetFile(_arg072, _arg143, _arg226, _arg316);
                    reply.writeNoException();
                    reply.writeTypedObject(_result62, 1);
                    return true;
                case 86:
                    int _arg073 = data.readInt();
                    int _arg144 = data.readInt();
                    Bundle _arg227 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result63 =
                            getScreenshotFileDescriptor(_arg073, _arg144, _arg227);
                    reply.writeNoException();
                    reply.writeTypedObject(_result63, 1);
                    return true;
                case 87:
                    int _arg074 = data.readInt();
                    int _arg145 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result64 = getWallpaperOrientation(_arg074, _arg145);
                    reply.writeNoException();
                    reply.writeInt(_result64);
                    return true;
                case 88:
                    int _arg075 = data.readInt();
                    String _arg146 = data.readString();
                    Bundle _arg228 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    semSendWallpaperCommand(_arg075, _arg146, _arg228);
                    reply.writeNoException();
                    return true;
                case 89:
                    int _arg076 = data.readInt();
                    data.enforceNoDataAvail();
                    SemWallpaperColors _result65 = semGetWallpaperColors(_arg076);
                    reply.writeNoException();
                    reply.writeTypedObject(_result65, 1);
                    return true;
                case 90:
                    int _arg077 = data.readInt();
                    data.enforceNoDataAvail();
                    SemWallpaperColors _result66 = semGetPrimaryWallpaperColors(_arg077);
                    reply.writeNoException();
                    reply.writeTypedObject(_result66, 1);
                    return true;
                case 91:
                    int _arg078 = data.readInt();
                    int _arg147 = data.readInt();
                    String _arg229 = data.readString();
                    data.enforceNoDataAvail();
                    semClearWallpaperThumbnailCache(_arg078, _arg147, _arg229);
                    reply.writeNoException();
                    return true;
                case 92:
                    int _arg079 = data.readInt();
                    String _arg148 = data.readString();
                    data.enforceNoDataAvail();
                    semRequestWallpaperColorsAnalysis(_arg079, _arg148);
                    reply.writeNoException();
                    return true;
                case 93:
                    SemWallpaperColors _arg080 =
                            (SemWallpaperColors) data.readTypedObject(SemWallpaperColors.CREATOR);
                    int _arg149 = data.readInt();
                    data.enforceNoDataAvail();
                    semSetDLSWallpaperColors(_arg080, _arg149);
                    reply.writeNoException();
                    return true;
                case 94:
                    int _arg081 = data.readInt();
                    Rect _arg150 = (Rect) data.readTypedObject(Rect.CREATOR);
                    Rect _arg230 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    semSetSmartCropRect(_arg081, _arg150, _arg230);
                    reply.writeNoException();
                    return true;
                case 95:
                    int _arg082 = data.readInt();
                    data.enforceNoDataAvail();
                    Rect _result67 = semGetSmartCropRect(_arg082);
                    reply.writeNoException();
                    reply.writeTypedObject(_result67, 1);
                    return true;
                case 96:
                    int _result68 = getLidState();
                    reply.writeNoException();
                    reply.writeInt(_result68);
                    return true;
                case 97:
                    int _arg083 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result69 = getDisplayId(_arg083);
                    reply.writeNoException();
                    reply.writeInt(_result69);
                    return true;
                case 98:
                    int _arg084 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result70 = isVirtualWallpaperDisplay(_arg084);
                    reply.writeNoException();
                    reply.writeBoolean(_result70);
                    return true;
                case 99:
                    int _arg085 = data.readInt();
                    int _arg151 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result71 = isWaitingForUnlockUser(_arg085, _arg151);
                    reply.writeNoException();
                    reply.writeBoolean(_result71);
                    return true;
                case 100:
                    String _arg086 = data.readString();
                    boolean _arg152 = data.readBoolean();
                    int _arg231 = data.readInt();
                    int _arg317 = data.readInt();
                    String _arg410 = data.readString();
                    int _arg55 = data.readInt();
                    Bundle _arg65 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    semSetUri(_arg086, _arg152, _arg231, _arg317, _arg410, _arg55, _arg65);
                    reply.writeNoException();
                    return true;
                case 101:
                    int _arg087 = data.readInt();
                    String _arg153 = data.readString();
                    data.enforceNoDataAvail();
                    String _result72 = semGetUri(_arg087, _arg153);
                    reply.writeNoException();
                    reply.writeString(_result72);
                    return true;
                case 102:
                    int _arg088 = data.readInt();
                    int _arg154 = data.readInt();
                    data.enforceNoDataAvail();
                    forceRebindWallpaper(_arg088, _arg154);
                    reply.writeNoException();
                    return true;
                case 103:
                    int _arg089 = data.readInt();
                    int _arg155 = data.readInt();
                    String _arg232 = data.readString();
                    boolean _arg318 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyPid(_arg089, _arg155, _arg232, _arg318);
                    reply.writeNoException();
                    return true;
                case 104:
                    int _arg090 = data.readInt();
                    int _arg156 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result73 = isWallpaperDataExists(_arg090, _arg156);
                    reply.writeNoException();
                    reply.writeBoolean(_result73);
                    return true;
                case 105:
                    int _arg091 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAodVisibilityState(_arg091);
                    reply.writeNoException();
                    return true;
                case 106:
                    int _arg092 = data.readInt();
                    int _arg157 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result74 = isStockLiveWallpaper(_arg092, _arg157);
                    reply.writeNoException();
                    reply.writeBoolean(_result74);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWallpaperManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor setWallpaper(
                    String name,
                    String callingPackage,
                    int[] screenOrientations,
                    List<Rect> crops,
                    boolean allowBackup,
                    Bundle extras,
                    int which,
                    IWallpaperManagerCallback completion,
                    int userId,
                    int type,
                    boolean isPreloaded,
                    Bundle inputExtras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeIntArray(screenOrientations);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedList(crops, 0);
                    try {
                        _data.writeBoolean(allowBackup);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(which);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongInterface(completion);
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(type);
                        try {
                            _data.writeBoolean(isPreloaded);
                            try {
                                _data.writeTypedObject(inputExtras, 0);
                            } catch (Throwable th8) {
                                th = th8;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            this.mRemote.transact(1, _data, _reply, 0);
                            _reply.readException();
                            ParcelFileDescriptor _result =
                                    (ParcelFileDescriptor)
                                            _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                            if (_reply.readInt() != 0) {
                                try {
                                    extras.readFromParcel(_reply);
                                } catch (Throwable th10) {
                                    th = th10;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        } catch (Throwable th11) {
                            th = th11;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th12) {
                        th = th12;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th13) {
                    th = th13;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponentChecked(
                    ComponentName name, String callingPackage, int which, int userId, Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(name, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponent(ComponentName name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(name, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaper(
                    String callingPkg,
                    IWallpaperManagerCallback cb,
                    int which,
                    Bundle outParams,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeStrongInterface(cb);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (_reply.readInt() != 0) {
                        outParams.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperWithFeature(
                    String callingPkg,
                    String callingFeatureId,
                    IWallpaperManagerCallback cb,
                    int which,
                    Bundle outParams,
                    int userId,
                    boolean getCropped,
                    boolean includeCopied,
                    int wpType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(callingFeatureId);
                    _data.writeStrongInterface(cb);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeBoolean(getCropped);
                    _data.writeBoolean(includeCopied);
                    _data.writeInt(wpType);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (_reply.readInt() != 0) {
                        outParams.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getLockWallpaper(
                    IWallpaperManagerCallback cb, Bundle outParams, int userId, int which)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    _data.writeInt(userId);
                    _data.writeInt(which);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (_reply.readInt() != 0) {
                        outParams.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getBitmapCrops(
                    List<Point> displaySizes, int which, boolean originalBitmap, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(displaySizes, 0);
                    _data.writeInt(which);
                    _data.writeBoolean(originalBitmap);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getFutureBitmapCrops(
                    Point bitmapSize,
                    List<Point> displaySizes,
                    int[] screenOrientations,
                    List<Rect> crops)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(bitmapSize, 0);
                    _data.writeTypedList(displaySizes, 0);
                    _data.writeIntArray(screenOrientations);
                    _data.writeTypedList(crops, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect getBitmapCrop(Point bitmapSize, int[] screenOrientations, List<Rect> crops)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(bitmapSize, 0);
                    _data.writeIntArray(screenOrientations);
                    _data.writeTypedList(crops, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfo(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    WallpaperInfo _result =
                            (WallpaperInfo) _reply.readTypedObject(WallpaperInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfoWithFlags(int which, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    WallpaperInfo _result =
                            (WallpaperInfo) _reply.readTypedObject(WallpaperInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperInfoFile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void clearWallpaper(String callingPackage, int which, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasNamedWallpaper(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDimensionHints(
                    int width, int height, String callingPackage, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeString(callingPackage);
                    _data.writeInt(displayId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWidthHint(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHeightHint(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDisplayPadding(Rect padding, String callingPackage, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(padding, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(displayId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void settingsRestored() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperSupported(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSetWallpaperAllowed(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupEligible(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semSetWallpaper(
                    String name,
                    String callingPackage,
                    int[] screenOrientations,
                    List<Rect> crops,
                    boolean allowBackup,
                    Bundle extras,
                    int which,
                    IWallpaperManagerCallback completion,
                    int userId,
                    int wallpaperType,
                    boolean isPreloaded,
                    Bundle inputExtras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeIntArray(screenOrientations);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedList(crops, 0);
                    try {
                        _data.writeBoolean(allowBackup);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(which);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongInterface(completion);
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(wallpaperType);
                        try {
                            _data.writeBoolean(isPreloaded);
                            try {
                                _data.writeTypedObject(inputExtras, 0);
                            } catch (Throwable th8) {
                                th = th8;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            this.mRemote.transact(25, _data, _reply, 0);
                            _reply.readException();
                            ParcelFileDescriptor _result =
                                    (ParcelFileDescriptor)
                                            _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                            if (_reply.readInt() != 0) {
                                try {
                                    extras.readFromParcel(_reply);
                                } catch (Throwable th10) {
                                    th = th10;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        } catch (Throwable th11) {
                            th = th11;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th12) {
                        th = th12;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th13) {
                    th = th13;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupAllowed(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setCoverWallpaperCallback(IWallpaperManagerCallback cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperColors getWallpaperColors(int which, int userId, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    WallpaperColors _result =
                            (WallpaperColors) _reply.readTypedObject(WallpaperColors.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeOnLocalColorsChangedListener(
                    ILocalWallpaperColorConsumer callback,
                    List<RectF> area,
                    int which,
                    int userId,
                    int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedList(area, 0);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void addOnLocalColorsChangedListener(
                    ILocalWallpaperColorConsumer callback,
                    List<RectF> regions,
                    int which,
                    int userId,
                    int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedList(regions, 0);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void registerWallpaperColorsCallback(
                    IWallpaperManagerCallback cb, int userId, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void unregisterWallpaperColorsCallback(
                    IWallpaperManagerCallback cb, int userId, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setInAmbientMode(boolean inAmbientMode, long animationDuration)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(inAmbientMode);
                    _data.writeLong(animationDuration);
                    this.mRemote.transact(34, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyWakingUp(int x, int y, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(35, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyGoingToSleep(int x, int y, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperDimAmount(float dimAmount) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(dimAmount);
                    this.mRemote.transact(37, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public float getWallpaperDimAmount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean lockScreenWallpaperExists() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStaticWallpaper(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getDesktopMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopModeEnabled(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopStandAloneMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLockWallpaperType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int semGetWallpaperType(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ComponentName semGetWallpaperComponent(int which, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean semIsPreloadedWallpaper(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetWallpaperCropHint(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDefaultWallpaperState(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setVideoWallpaper(
                    String videoFilePath,
                    String themePackage,
                    String videoColor,
                    String callingPackage,
                    int userId,
                    int which,
                    boolean allowBackup,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(videoFilePath);
                    _data.writeString(themePackage);
                    _data.writeString(videoColor);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeInt(which);
                    _data.writeBoolean(allowBackup);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVideoWallpaper() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasVideoWallpaper() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semGetThumbnailFileDescriptor(
                    int which, int userId, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeInt(rotation);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(
                    int type, int userId, int which, int orientation, int mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(userId);
                    _data.writeInt(which);
                    _data.writeInt(orientation);
                    _data.writeInt(mode);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFilePath(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoPackage(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFileName(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setMotionWallpaper(
                    String pkgName, String callingPackage, int which, boolean allowBackup)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeString(callingPackage);
                    _data.writeInt(which);
                    _data.writeBoolean(allowBackup);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getMotionWallpaperPkgName(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setAnimatedWallpaper(
                    String pkgName, String callingPackage, int which, boolean allowBackup)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeString(callingPackage);
                    _data.writeInt(which);
                    _data.writeBoolean(allowBackup);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByWhich(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByKey(int key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotBySource(String source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(source);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int makeSnapshot(int which, int key, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(key);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean restoreSnapshot(int key, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSnapshotTestMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setSnapshotTestMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getSnapshotCount(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setSnapshotSource(int key, String source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeString(source);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isValidSnapshot(int key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int[] getSnapshotKeys(String source, int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(source);
                    _data.writeInt(which);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getAnimatedPkgName(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getDeviceColor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLegacyDeviceColor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackage(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackageWithPrefix(int which, boolean includePrefix)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeBoolean(includePrefix);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyFileToWallpaperFile(int which, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyPreloadedFileToWallpaperFile(int which, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSystemAndLockPaired(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHighlightFilterState(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperComponentExtras(int which, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperExtras(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperAssets(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperAssetFile(
                    String callingPkg, int which, int userId, String assetFilePath)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeString(assetFilePath);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getScreenshotFileDescriptor(
                    int which, int userId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperOrientation(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSendWallpaperCommand(int which, String action, Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeString(action);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetWallpaperColors(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    SemWallpaperColors _result =
                            (SemWallpaperColors) _reply.readTypedObject(SemWallpaperColors.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetPrimaryWallpaperColors(int which)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    SemWallpaperColors _result =
                            (SemWallpaperColors) _reply.readTypedObject(SemWallpaperColors.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semClearWallpaperThumbnailCache(
                    int which, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semRequestWallpaperColorsAnalysis(int which, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetDLSWallpaperColors(SemWallpaperColors colors, int which)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(colors, 0);
                    _data.writeInt(which);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetSmartCropRect(int which, Rect original, Rect smartCrop)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeTypedObject(original, 0);
                    _data.writeTypedObject(smartCrop, 0);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetSmartCropRect(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLidState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getDisplayId(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVirtualWallpaperDisplay(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWaitingForUnlockUser(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetUri(
                    String uri,
                    boolean allowBackup,
                    int which,
                    int type,
                    String callerPackage,
                    int userId,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeBoolean(allowBackup);
                    _data.writeInt(which);
                    _data.writeInt(type);
                    _data.writeString(callerPackage);
                    _data.writeInt(userId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String semGetUri(int which, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void forceRebindWallpaper(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyPid(int uid, int pid, String packageName, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeString(packageName);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperDataExists(int userId, int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(which);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyAodVisibilityState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStockLiveWallpaper(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 105;
        }
    }
}
