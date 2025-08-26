package android.app;

import android.app.ILocalWallpaperColorConsumer;
import android.app.IWallpaperManagerCallback;
import android.app.wallpaper.WallpaperDescription;
import android.app.wallpaper.WallpaperInstance;
import android.content.ComponentName;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IWallpaperManager extends IInterface {

    public static class Default implements IWallpaperManager {
        @Override // android.app.IWallpaperManager
        public void addOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void clearWallpaper(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void copyFileToWallpaperFile(int i, String str) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void copyPreloadedFileToWallpaperFile(int i, String str) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void forceRebindWallpaper(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public String getAnimatedPkgName(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Rect getBitmapCrop(Point point, int[] iArr, List<Rect> list) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public List getBitmapCrops(List<Point> list, int i, boolean z, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getCurrentBitmapCrops(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getDeviceColor() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getDisplayId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public List getFutureBitmapCrops(Point point, List<Point> list, int[] iArr, List<Rect> list2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getHeightHint(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int getHighlightFilterState(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public String getLastCallingPackage(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getLastCallingPackageWithPrefix(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getLegacyDeviceColor() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getLidState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getLockWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getLockWallpaperType() throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public String getMotionWallpaperPkgName(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getName() throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getSnapshotCount(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int[] getSnapshotKeys(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoFileName(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoFilePath(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String getVideoPackage(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperAssets(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Region getWallpaperBackgroundRegion(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperComponentExtras(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public float getWallpaperDimAmount() throws RemoteException {
            return 0.0f;
        }

        @Override // android.app.IWallpaperManager
        public Bundle getWallpaperExtras(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWallpaperIdForUser(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperInfo getWallpaperInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperInfoFile(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public WallpaperInstance getWallpaperInstance(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWallpaperOrientation(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor getWallpaperWithFeature(String str, String str2, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, boolean z, boolean z2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWidthHint(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean hasNamedWallpaper(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean hasVideoWallpaper() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDefaultWallpaperState(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDesktopMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isDesktopModeEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSetWallpaperAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSnapshotTestMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isStaticWallpaper(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isStockLiveWallpaper(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSystemAndLockPaired(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isValidSnapshot(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isVideoWallpaper() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isVirtualWallpaperDisplay(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWaitingForUnlockUser(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperBackupAllowed(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperBackupEligible(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperDataExists(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperSupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean lockScreenWallpaperExists() throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public int makeSnapshot(int i, int i2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public void notifyAodVisibilityState(int i) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void notifyGoingToSleep(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void notifyPid(int i, int i2, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void notifyWakingUp(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void removeOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void removeSnapshotByKey(int i) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void removeSnapshotBySource(String str) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void removeSnapshotByWhich(int i) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean restoreSnapshot(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void semClearWallpaperThumbnailCache(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public SemWallpaperColors semGetPrimaryWallpaperColors(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Rect semGetSmartCropRect(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public String semGetUri(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public Rect semGetWallpaperCropHint(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int semGetWallpaperType(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public boolean semIsPreloadedWallpaper(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor semSetWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setDisplayPadding(Rect rect, String str, int i) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setInAmbientMode(boolean z, long j) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean setSnapshotSource(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setSnapshotTestMode(boolean z) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setVideoWallpaper(String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public ParcelFileDescriptor setWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponentChecked(WallpaperDescription wallpaperDescription, String str, int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperDimAmount(float f) throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void settingsRestored() throws RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
        }
    }

    void addOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException;

    void clearWallpaper(String str, int i, int i2) throws RemoteException;

    void copyFileToWallpaperFile(int i, String str) throws RemoteException;

    void copyPreloadedFileToWallpaperFile(int i, String str) throws RemoteException;

    void forceRebindWallpaper(int i, int i2) throws RemoteException;

    String getAnimatedPkgName(int i) throws RemoteException;

    Rect getBitmapCrop(Point point, int[] iArr, List<Rect> list) throws RemoteException;

    List getBitmapCrops(List<Point> list, int i, boolean z, int i2) throws RemoteException;

    Bundle getCurrentBitmapCrops(int i, int i2) throws RemoteException;

    String getDeviceColor() throws RemoteException;

    int getDisplayId(int i) throws RemoteException;

    List getFutureBitmapCrops(Point point, List<Point> list, int[] iArr, List<Rect> list2) throws RemoteException;

    int getHeightHint(int i) throws RemoteException;

    int getHighlightFilterState(int i) throws RemoteException;

    String getLastCallingPackage(int i) throws RemoteException;

    String getLastCallingPackageWithPrefix(int i, boolean z) throws RemoteException;

    String getLegacyDeviceColor() throws RemoteException;

    int getLidState() throws RemoteException;

    ParcelFileDescriptor getLockWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) throws RemoteException;

    int getLockWallpaperType() throws RemoteException;

    String getMotionWallpaperPkgName(int i) throws RemoteException;

    String getName() throws RemoteException;

    ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle) throws RemoteException;

    int getSnapshotCount(int i) throws RemoteException;

    int[] getSnapshotKeys(String str, int i) throws RemoteException;

    String getVideoFileName(int i) throws RemoteException;

    String getVideoFilePath(int i) throws RemoteException;

    String getVideoPackage(int i) throws RemoteException;

    @Deprecated
    ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) throws RemoteException;

    ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) throws RemoteException;

    Bundle getWallpaperAssets(int i, int i2) throws RemoteException;

    Region getWallpaperBackgroundRegion(int i, int i2, int i3) throws RemoteException;

    WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException;

    Bundle getWallpaperComponentExtras(int i, int i2) throws RemoteException;

    float getWallpaperDimAmount() throws RemoteException;

    Bundle getWallpaperExtras(int i, int i2) throws RemoteException;

    int getWallpaperIdForUser(int i, int i2) throws RemoteException;

    WallpaperInfo getWallpaperInfo(int i) throws RemoteException;

    ParcelFileDescriptor getWallpaperInfoFile(int i) throws RemoteException;

    WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws RemoteException;

    WallpaperInstance getWallpaperInstance(int i, int i2) throws RemoteException;

    int getWallpaperOrientation(int i, int i2) throws RemoteException;

    ParcelFileDescriptor getWallpaperWithFeature(String str, String str2, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, boolean z, boolean z2, int i3) throws RemoteException;

    int getWidthHint(int i) throws RemoteException;

    boolean hasNamedWallpaper(String str) throws RemoteException;

    boolean hasVideoWallpaper() throws RemoteException;

    boolean isDefaultWallpaperState(int i) throws RemoteException;

    boolean isDesktopMode() throws RemoteException;

    boolean isDesktopModeEnabled(int i) throws RemoteException;

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

    void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

    void removeOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException;

    void removeSnapshotByKey(int i) throws RemoteException;

    void removeSnapshotBySource(String str) throws RemoteException;

    void removeSnapshotByWhich(int i) throws RemoteException;

    boolean restoreSnapshot(int i, String str) throws RemoteException;

    void semClearWallpaperThumbnailCache(int i, int i2, String str) throws RemoteException;

    SemWallpaperColors semGetPrimaryWallpaperColors(int i) throws RemoteException;

    Rect semGetSmartCropRect(int i) throws RemoteException;

    ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) throws RemoteException;

    String semGetUri(int i, String str) throws RemoteException;

    SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException;

    ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException;

    Rect semGetWallpaperCropHint(int i) throws RemoteException;

    int semGetWallpaperType(int i) throws RemoteException;

    boolean semIsPreloadedWallpaper(int i, int i2) throws RemoteException;

    void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException;

    void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException;

    void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) throws RemoteException;

    void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException;

    void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) throws RemoteException;

    ParcelFileDescriptor semSetWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException;

    void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

    boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException;

    void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException;

    void setDisplayPadding(Rect rect, String str, int i) throws RemoteException;

    void setInAmbientMode(boolean z, long j) throws RemoteException;

    boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException;

    void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

    boolean setSnapshotSource(int i, String str) throws RemoteException;

    void setSnapshotTestMode(boolean z) throws RemoteException;

    void setVideoWallpaper(String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) throws RemoteException;

    ParcelFileDescriptor setWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException;

    void setWallpaperComponent(ComponentName componentName) throws RemoteException;

    void setWallpaperComponentChecked(WallpaperDescription wallpaperDescription, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void setWallpaperDimAmount(float f) throws RemoteException;

    void settingsRestored() throws RemoteException;

    void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IWallpaperManager {
        public static final String DESCRIPTOR = "android.app.IWallpaperManager";
        static final int TRANSACTION_addOnLocalColorsChangedListener = 33;
        static final int TRANSACTION_clearWallpaper = 16;
        static final int TRANSACTION_copyFileToWallpaperFile = 77;
        static final int TRANSACTION_copyPreloadedFileToWallpaperFile = 78;
        static final int TRANSACTION_forceRebindWallpaper = 102;
        static final int TRANSACTION_getAnimatedPkgName = 72;
        static final int TRANSACTION_getBitmapCrop = 10;
        static final int TRANSACTION_getBitmapCrops = 7;
        static final int TRANSACTION_getCurrentBitmapCrops = 8;
        static final int TRANSACTION_getDeviceColor = 73;
        static final int TRANSACTION_getDisplayId = 97;
        static final int TRANSACTION_getFutureBitmapCrops = 9;
        static final int TRANSACTION_getHeightHint = 20;
        static final int TRANSACTION_getHighlightFilterState = 80;
        static final int TRANSACTION_getLastCallingPackage = 75;
        static final int TRANSACTION_getLastCallingPackageWithPrefix = 76;
        static final int TRANSACTION_getLegacyDeviceColor = 74;
        static final int TRANSACTION_getLidState = 96;
        static final int TRANSACTION_getLockWallpaper = 6;
        static final int TRANSACTION_getLockWallpaperType = 45;
        static final int TRANSACTION_getMotionWallpaperPkgName = 59;
        static final int TRANSACTION_getName = 22;
        static final int TRANSACTION_getScreenshotFileDescriptor = 85;
        static final int TRANSACTION_getSnapshotCount = 68;
        static final int TRANSACTION_getSnapshotKeys = 71;
        static final int TRANSACTION_getVideoFileName = 57;
        static final int TRANSACTION_getVideoFilePath = 55;
        static final int TRANSACTION_getVideoPackage = 56;
        static final int TRANSACTION_getWallpaper = 4;
        static final int TRANSACTION_getWallpaperAssetFile = 84;
        static final int TRANSACTION_getWallpaperAssets = 83;
        static final int TRANSACTION_getWallpaperBackgroundRegion = 86;
        static final int TRANSACTION_getWallpaperColors = 31;
        static final int TRANSACTION_getWallpaperComponentExtras = 81;
        static final int TRANSACTION_getWallpaperDimAmount = 40;
        static final int TRANSACTION_getWallpaperExtras = 82;
        static final int TRANSACTION_getWallpaperIdForUser = 11;
        static final int TRANSACTION_getWallpaperInfo = 12;
        static final int TRANSACTION_getWallpaperInfoFile = 15;
        static final int TRANSACTION_getWallpaperInfoWithFlags = 13;
        static final int TRANSACTION_getWallpaperInstance = 14;
        static final int TRANSACTION_getWallpaperOrientation = 87;
        static final int TRANSACTION_getWallpaperWithFeature = 5;
        static final int TRANSACTION_getWidthHint = 19;
        static final int TRANSACTION_hasNamedWallpaper = 17;
        static final int TRANSACTION_hasVideoWallpaper = 53;
        static final int TRANSACTION_isDefaultWallpaperState = 50;
        static final int TRANSACTION_isDesktopMode = 43;
        static final int TRANSACTION_isDesktopModeEnabled = 44;
        static final int TRANSACTION_isSetWallpaperAllowed = 25;
        static final int TRANSACTION_isSnapshotTestMode = 66;
        static final int TRANSACTION_isStaticWallpaper = 42;
        static final int TRANSACTION_isStockLiveWallpaper = 106;
        static final int TRANSACTION_isSystemAndLockPaired = 79;
        static final int TRANSACTION_isValidSnapshot = 70;
        static final int TRANSACTION_isVideoWallpaper = 52;
        static final int TRANSACTION_isVirtualWallpaperDisplay = 98;
        static final int TRANSACTION_isWaitingForUnlockUser = 99;
        static final int TRANSACTION_isWallpaperBackupAllowed = 28;
        static final int TRANSACTION_isWallpaperBackupEligible = 26;
        static final int TRANSACTION_isWallpaperDataExists = 104;
        static final int TRANSACTION_isWallpaperSupported = 24;
        static final int TRANSACTION_lockScreenWallpaperExists = 41;
        static final int TRANSACTION_makeSnapshot = 64;
        static final int TRANSACTION_notifyAodVisibilityState = 105;
        static final int TRANSACTION_notifyGoingToSleep = 38;
        static final int TRANSACTION_notifyPid = 103;
        static final int TRANSACTION_notifyWakingUp = 37;
        static final int TRANSACTION_registerWallpaperColorsCallback = 34;
        static final int TRANSACTION_removeOnLocalColorsChangedListener = 32;
        static final int TRANSACTION_removeSnapshotByKey = 62;
        static final int TRANSACTION_removeSnapshotBySource = 63;
        static final int TRANSACTION_removeSnapshotByWhich = 61;
        static final int TRANSACTION_restoreSnapshot = 65;
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
        static final int TRANSACTION_semSetWallpaper = 27;
        static final int TRANSACTION_setAnimatedWallpaper = 60;
        static final int TRANSACTION_setCoverWallpaperCallback = 30;
        static final int TRANSACTION_setDimensionHints = 18;
        static final int TRANSACTION_setDisplayPadding = 21;
        static final int TRANSACTION_setInAmbientMode = 36;
        static final int TRANSACTION_setLockWallpaperCallback = 29;
        static final int TRANSACTION_setMotionWallpaper = 58;
        static final int TRANSACTION_setSnapshotSource = 69;
        static final int TRANSACTION_setSnapshotTestMode = 67;
        static final int TRANSACTION_setVideoWallpaper = 51;
        static final int TRANSACTION_setWallpaper = 1;
        static final int TRANSACTION_setWallpaperComponent = 3;
        static final int TRANSACTION_setWallpaperComponentChecked = 2;
        static final int TRANSACTION_setWallpaperDimAmount = 39;
        static final int TRANSACTION_settingsRestored = 23;
        static final int TRANSACTION_unregisterWallpaperColorsCallback = 35;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 105;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWallpaperManager)) {
                return (IWallpaperManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "getCurrentBitmapCrops";
                case 9:
                    return "getFutureBitmapCrops";
                case 10:
                    return "getBitmapCrop";
                case 11:
                    return "getWallpaperIdForUser";
                case 12:
                    return "getWallpaperInfo";
                case 13:
                    return "getWallpaperInfoWithFlags";
                case 14:
                    return "getWallpaperInstance";
                case 15:
                    return "getWallpaperInfoFile";
                case 16:
                    return "clearWallpaper";
                case 17:
                    return "hasNamedWallpaper";
                case 18:
                    return "setDimensionHints";
                case 19:
                    return "getWidthHint";
                case 20:
                    return "getHeightHint";
                case 21:
                    return "setDisplayPadding";
                case 22:
                    return "getName";
                case 23:
                    return "settingsRestored";
                case 24:
                    return "isWallpaperSupported";
                case 25:
                    return "isSetWallpaperAllowed";
                case 26:
                    return "isWallpaperBackupEligible";
                case 27:
                    return "semSetWallpaper";
                case 28:
                    return "isWallpaperBackupAllowed";
                case 29:
                    return "setLockWallpaperCallback";
                case 30:
                    return "setCoverWallpaperCallback";
                case 31:
                    return "getWallpaperColors";
                case 32:
                    return "removeOnLocalColorsChangedListener";
                case 33:
                    return "addOnLocalColorsChangedListener";
                case 34:
                    return "registerWallpaperColorsCallback";
                case 35:
                    return "unregisterWallpaperColorsCallback";
                case 36:
                    return "setInAmbientMode";
                case 37:
                    return "notifyWakingUp";
                case 38:
                    return "notifyGoingToSleep";
                case 39:
                    return "setWallpaperDimAmount";
                case 40:
                    return "getWallpaperDimAmount";
                case 41:
                    return "lockScreenWallpaperExists";
                case 42:
                    return "isStaticWallpaper";
                case 43:
                    return "isDesktopMode";
                case 44:
                    return "isDesktopModeEnabled";
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
                    return "getVideoFilePath";
                case 56:
                    return "getVideoPackage";
                case 57:
                    return "getVideoFileName";
                case 58:
                    return "setMotionWallpaper";
                case 59:
                    return "getMotionWallpaperPkgName";
                case 60:
                    return "setAnimatedWallpaper";
                case 61:
                    return "removeSnapshotByWhich";
                case 62:
                    return "removeSnapshotByKey";
                case 63:
                    return "removeSnapshotBySource";
                case 64:
                    return "makeSnapshot";
                case 65:
                    return "restoreSnapshot";
                case 66:
                    return "isSnapshotTestMode";
                case 67:
                    return "setSnapshotTestMode";
                case 68:
                    return "getSnapshotCount";
                case 69:
                    return "setSnapshotSource";
                case 70:
                    return "isValidSnapshot";
                case 71:
                    return "getSnapshotKeys";
                case 72:
                    return "getAnimatedPkgName";
                case 73:
                    return "getDeviceColor";
                case 74:
                    return "getLegacyDeviceColor";
                case 75:
                    return "getLastCallingPackage";
                case 76:
                    return "getLastCallingPackageWithPrefix";
                case 77:
                    return "copyFileToWallpaperFile";
                case 78:
                    return "copyPreloadedFileToWallpaperFile";
                case 79:
                    return "isSystemAndLockPaired";
                case 80:
                    return "getHighlightFilterState";
                case 81:
                    return "getWallpaperComponentExtras";
                case 82:
                    return "getWallpaperExtras";
                case 83:
                    return "getWallpaperAssets";
                case 84:
                    return "getWallpaperAssetFile";
                case 85:
                    return "getScreenshotFileDescriptor";
                case 86:
                    return "getWallpaperBackgroundRegion";
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Rect.CREATOR);
                    boolean z = parcel.readBoolean();
                    Bundle bundle = new Bundle();
                    int i3 = parcel.readInt();
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaper = setWallpaper(string, string2, iArrCreateIntArray, arrayListCreateTypedArrayList, z, bundle, i3, iWallpaperManagerCallbackAsInterface, i4, i5, z2, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper, 1);
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    WallpaperDescription wallpaperDescription = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWallpaperComponentChecked(wallpaperDescription, string3, i6, i7, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWallpaperComponent(componentName);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface2 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    Bundle bundle4 = new Bundle();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaper2 = getWallpaper(string4, iWallpaperManagerCallbackAsInterface2, i8, bundle4, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper2, 1);
                    parcel2.writeTypedObject(bundle4, 1);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface3 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    Bundle bundle5 = new Bundle();
                    int i11 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperWithFeature = getWallpaperWithFeature(string5, string6, iWallpaperManagerCallbackAsInterface3, i10, bundle5, i11, z3, z4, i12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperWithFeature, 1);
                    parcel2.writeTypedObject(bundle5, 1);
                    return true;
                case 6:
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface4 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle6 = new Bundle();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor lockWallpaper = getLockWallpaper(iWallpaperManagerCallbackAsInterface4, bundle6, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lockWallpaper, 1);
                    parcel2.writeTypedObject(bundle6, 1);
                    return true;
                case 7:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Point.CREATOR);
                    int i15 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List bitmapCrops = getBitmapCrops(arrayListCreateTypedArrayList2, i15, z5, i16);
                    parcel2.writeNoException();
                    parcel2.writeList(bitmapCrops);
                    return true;
                case 8:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle currentBitmapCrops = getCurrentBitmapCrops(i17, i18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentBitmapCrops, 1);
                    return true;
                case 9:
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(Point.CREATOR);
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    List futureBitmapCrops = getFutureBitmapCrops(point, arrayListCreateTypedArrayList3, iArrCreateIntArray2, arrayListCreateTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeList(futureBitmapCrops);
                    return true;
                case 10:
                    Point point2 = (Point) parcel.readTypedObject(Point.CREATOR);
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    ArrayList arrayListCreateTypedArrayList5 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect bitmapCrop = getBitmapCrop(point2, iArrCreateIntArray3, arrayListCreateTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapCrop, 1);
                    return true;
                case 11:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wallpaperIdForUser = getWallpaperIdForUser(i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperIdForUser);
                    return true;
                case 12:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInfo wallpaperInfo = getWallpaperInfo(i21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfo, 1);
                    return true;
                case 13:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInfo wallpaperInfoWithFlags = getWallpaperInfoWithFlags(i22, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoWithFlags, 1);
                    return true;
                case 14:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInstance wallpaperInstance = getWallpaperInstance(i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInstance, 1);
                    return true;
                case 15:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperInfoFile = getWallpaperInfoFile(i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoFile, 1);
                    return true;
                case 16:
                    String string7 = parcel.readString();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearWallpaper(string7, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasNamedWallpaper = hasNamedWallpaper(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasNamedWallpaper);
                    return true;
                case 18:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    String string9 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDimensionHints(i29, i30, string9, i31);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int widthHint = getWidthHint(i32);
                    parcel2.writeNoException();
                    parcel2.writeInt(widthHint);
                    return true;
                case 20:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int heightHint = getHeightHint(i33);
                    parcel2.writeNoException();
                    parcel2.writeInt(heightHint);
                    return true;
                case 21:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    String string10 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayPadding(rect, string10, i34);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                case 23:
                    settingsRestored();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsWallpaperSupported = isWallpaperSupported(string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWallpaperSupported);
                    return true;
                case 25:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSetWallpaperAllowed = isSetWallpaperAllowed(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSetWallpaperAllowed);
                    return true;
                case 26:
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWallpaperBackupEligible = isWallpaperBackupEligible(i35, i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWallpaperBackupEligible);
                    return true;
                case 27:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    ArrayList arrayListCreateTypedArrayList6 = parcel.createTypedArrayList(Rect.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    Bundle bundle7 = new Bundle();
                    int i37 = parcel.readInt();
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface5 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorSemSetWallpaper = semSetWallpaper(string13, string14, iArrCreateIntArray4, arrayListCreateTypedArrayList6, z6, bundle7, i37, iWallpaperManagerCallbackAsInterface5, i38, i39, z7, bundle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorSemSetWallpaper, 1);
                    parcel2.writeTypedObject(bundle7, 1);
                    return true;
                case 28:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWallpaperBackupAllowed = isWallpaperBackupAllowed(i40, i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWallpaperBackupAllowed);
                    return true;
                case 29:
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface6 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean lockWallpaperCallback = setLockWallpaperCallback(iWallpaperManagerCallbackAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockWallpaperCallback);
                    return true;
                case 30:
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface7 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean coverWallpaperCallback = setCoverWallpaperCallback(iWallpaperManagerCallbackAsInterface7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(coverWallpaperCallback);
                    return true;
                case 31:
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperColors wallpaperColors = getWallpaperColors(i42, i43, i44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperColors, 1);
                    return true;
                case 32:
                    ILocalWallpaperColorConsumer iLocalWallpaperColorConsumerAsInterface = ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList7 = parcel.createTypedArrayList(RectF.CREATOR);
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnLocalColorsChangedListener(iLocalWallpaperColorConsumerAsInterface, arrayListCreateTypedArrayList7, i45, i46, i47);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ILocalWallpaperColorConsumer iLocalWallpaperColorConsumerAsInterface2 = ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList8 = parcel.createTypedArrayList(RectF.CREATOR);
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnLocalColorsChangedListener(iLocalWallpaperColorConsumerAsInterface2, arrayListCreateTypedArrayList8, i48, i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface8 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerWallpaperColorsCallback(iWallpaperManagerCallbackAsInterface8, i51, i52);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IWallpaperManagerCallback iWallpaperManagerCallbackAsInterface9 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperColorsCallback(iWallpaperManagerCallbackAsInterface9, i53, i54);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean z8 = parcel.readBoolean();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInAmbientMode(z8, j);
                    return true;
                case 37:
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyWakingUp(i55, i56, bundle9);
                    return true;
                case 38:
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyGoingToSleep(i57, i58, bundle10);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperDimAmount(f);
                    return true;
                case 40:
                    float wallpaperDimAmount = getWallpaperDimAmount();
                    parcel2.writeNoException();
                    parcel2.writeFloat(wallpaperDimAmount);
                    return true;
                case 41:
                    boolean zLockScreenWallpaperExists = lockScreenWallpaperExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zLockScreenWallpaperExists);
                    return true;
                case 42:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStaticWallpaper = isStaticWallpaper(i59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStaticWallpaper);
                    return true;
                case 43:
                    boolean zIsDesktopMode = isDesktopMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDesktopMode);
                    return true;
                case 44:
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDesktopModeEnabled = isDesktopModeEnabled(i60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDesktopModeEnabled);
                    return true;
                case 45:
                    int lockWallpaperType = getLockWallpaperType();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockWallpaperType);
                    return true;
                case 46:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSemGetWallpaperType = semGetWallpaperType(i61);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetWallpaperType);
                    return true;
                case 47:
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName componentNameSemGetWallpaperComponent = semGetWallpaperComponent(i62, i63);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(componentNameSemGetWallpaperComponent, 1);
                    return true;
                case 48:
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemIsPreloadedWallpaper = semIsPreloadedWallpaper(i64, i65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsPreloadedWallpaper);
                    return true;
                case 49:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect rectSemGetWallpaperCropHint = semGetWallpaperCropHint(i66);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rectSemGetWallpaperCropHint, 1);
                    return true;
                case 50:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDefaultWallpaperState = isDefaultWallpaperState(i67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDefaultWallpaperState);
                    return true;
                case 51:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoWallpaper(string15, string16, string17, string18, i68, i69, z9, bundle11);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean zIsVideoWallpaper = isVideoWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoWallpaper);
                    return true;
                case 53:
                    boolean zHasVideoWallpaper = hasVideoWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasVideoWallpaper);
                    return true;
                case 54:
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorSemGetThumbnailFileDescriptor = semGetThumbnailFileDescriptor(i70, i71, i72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorSemGetThumbnailFileDescriptor, 1);
                    return true;
                case 55:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoFilePath = getVideoFilePath(i73);
                    parcel2.writeNoException();
                    parcel2.writeString(videoFilePath);
                    return true;
                case 56:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoPackage = getVideoPackage(i74);
                    parcel2.writeNoException();
                    parcel2.writeString(videoPackage);
                    return true;
                case 57:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoFileName = getVideoFileName(i75);
                    parcel2.writeNoException();
                    parcel2.writeString(videoFileName);
                    return true;
                case 58:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    int i76 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMotionWallpaper(string19, string20, i76, z10);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String motionWallpaperPkgName = getMotionWallpaperPkgName(i77);
                    parcel2.writeNoException();
                    parcel2.writeString(motionWallpaperPkgName);
                    return true;
                case 60:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    int i78 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAnimatedWallpaper(string21, string22, i78, z11);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSnapshotByWhich(i79);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSnapshotByKey(i80);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSnapshotBySource(string23);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iMakeSnapshot = makeSnapshot(i81, i82, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeSnapshot);
                    return true;
                case 65:
                    int i83 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRestoreSnapshot = restoreSnapshot(i83, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRestoreSnapshot);
                    return true;
                case 66:
                    boolean zIsSnapshotTestMode = isSnapshotTestMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSnapshotTestMode);
                    return true;
                case 67:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSnapshotTestMode(z12);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int snapshotCount = getSnapshotCount(i84);
                    parcel2.writeNoException();
                    parcel2.writeInt(snapshotCount);
                    return true;
                case 69:
                    int i85 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean snapshotSource = setSnapshotSource(i85, string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(snapshotSource);
                    return true;
                case 70:
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsValidSnapshot = isValidSnapshot(i86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsValidSnapshot);
                    return true;
                case 71:
                    String string26 = parcel.readString();
                    int i87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] snapshotKeys = getSnapshotKeys(string26, i87);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(snapshotKeys);
                    return true;
                case 72:
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String animatedPkgName = getAnimatedPkgName(i88);
                    parcel2.writeNoException();
                    parcel2.writeString(animatedPkgName);
                    return true;
                case 73:
                    String deviceColor = getDeviceColor();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceColor);
                    return true;
                case 74:
                    String legacyDeviceColor = getLegacyDeviceColor();
                    parcel2.writeNoException();
                    parcel2.writeString(legacyDeviceColor);
                    return true;
                case 75:
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lastCallingPackage = getLastCallingPackage(i89);
                    parcel2.writeNoException();
                    parcel2.writeString(lastCallingPackage);
                    return true;
                case 76:
                    int i90 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String lastCallingPackageWithPrefix = getLastCallingPackageWithPrefix(i90, z13);
                    parcel2.writeNoException();
                    parcel2.writeString(lastCallingPackageWithPrefix);
                    return true;
                case 77:
                    int i91 = parcel.readInt();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    copyFileToWallpaperFile(i91, string27);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int i92 = parcel.readInt();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    copyPreloadedFileToWallpaperFile(i92, string28);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemAndLockPaired = isSystemAndLockPaired(i93);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemAndLockPaired);
                    return true;
                case 80:
                    int i94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int highlightFilterState = getHighlightFilterState(i94);
                    parcel2.writeNoException();
                    parcel2.writeInt(highlightFilterState);
                    return true;
                case 81:
                    int i95 = parcel.readInt();
                    int i96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperComponentExtras = getWallpaperComponentExtras(i95, i96);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperComponentExtras, 1);
                    return true;
                case 82:
                    int i97 = parcel.readInt();
                    int i98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperExtras = getWallpaperExtras(i97, i98);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperExtras, 1);
                    return true;
                case 83:
                    int i99 = parcel.readInt();
                    int i100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperAssets = getWallpaperAssets(i99, i100);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperAssets, 1);
                    return true;
                case 84:
                    String string29 = parcel.readString();
                    int i101 = parcel.readInt();
                    int i102 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperAssetFile = getWallpaperAssetFile(string29, i101, i102, string30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperAssetFile, 1);
                    return true;
                case 85:
                    int i103 = parcel.readInt();
                    int i104 = parcel.readInt();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor screenshotFileDescriptor = getScreenshotFileDescriptor(i103, i104, bundle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotFileDescriptor, 1);
                    return true;
                case 86:
                    int i105 = parcel.readInt();
                    int i106 = parcel.readInt();
                    int i107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region wallpaperBackgroundRegion = getWallpaperBackgroundRegion(i105, i106, i107);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperBackgroundRegion, 1);
                    return true;
                case 87:
                    int i108 = parcel.readInt();
                    int i109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wallpaperOrientation = getWallpaperOrientation(i108, i109);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperOrientation);
                    return true;
                case 88:
                    int i110 = parcel.readInt();
                    String string31 = parcel.readString();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSendWallpaperCommand(i110, string31, bundle14);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int i111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = semGetWallpaperColors(i111);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semWallpaperColorsSemGetWallpaperColors, 1);
                    return true;
                case 90:
                    int i112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWallpaperColors semWallpaperColorsSemGetPrimaryWallpaperColors = semGetPrimaryWallpaperColors(i112);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semWallpaperColorsSemGetPrimaryWallpaperColors, 1);
                    return true;
                case 91:
                    int i113 = parcel.readInt();
                    int i114 = parcel.readInt();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semClearWallpaperThumbnailCache(i113, i114, string32);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int i115 = parcel.readInt();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semRequestWallpaperColorsAnalysis(i115, string33);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    SemWallpaperColors semWallpaperColors = (SemWallpaperColors) parcel.readTypedObject(SemWallpaperColors.CREATOR);
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetDLSWallpaperColors(semWallpaperColors, i116);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int i117 = parcel.readInt();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSetSmartCropRect(i117, rect2, rect3);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    int i118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect rectSemGetSmartCropRect = semGetSmartCropRect(i118);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rectSemGetSmartCropRect, 1);
                    return true;
                case 96:
                    int lidState = getLidState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lidState);
                    return true;
                case 97:
                    int i119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayId = getDisplayId(i119);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 98:
                    int i120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVirtualWallpaperDisplay = isVirtualWallpaperDisplay(i120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVirtualWallpaperDisplay);
                    return true;
                case 99:
                    int i121 = parcel.readInt();
                    int i122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWaitingForUnlockUser = isWaitingForUnlockUser(i121, i122);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWaitingForUnlockUser);
                    return true;
                case 100:
                    String string34 = parcel.readString();
                    boolean z14 = parcel.readBoolean();
                    int i123 = parcel.readInt();
                    int i124 = parcel.readInt();
                    String string35 = parcel.readString();
                    int i125 = parcel.readInt();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSetUri(string34, z14, i123, i124, string35, i125, bundle15);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    int i126 = parcel.readInt();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSemGetUri = semGetUri(i126, string36);
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetUri);
                    return true;
                case 102:
                    int i127 = parcel.readInt();
                    int i128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRebindWallpaper(i127, i128);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int i129 = parcel.readInt();
                    int i130 = parcel.readInt();
                    String string37 = parcel.readString();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyPid(i129, i130, string37, z15);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int i131 = parcel.readInt();
                    int i132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWallpaperDataExists = isWallpaperDataExists(i131, i132);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWallpaperDataExists);
                    return true;
                case 105:
                    int i133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAodVisibilityState(i133);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int i134 = parcel.readInt();
                    int i135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStockLiveWallpaper = isStockLiveWallpaper(i134, i135);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStockLiveWallpaper);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWallpaperManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor setWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponentChecked(WallpaperDescription wallpaperDescription, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(wallpaperDescription, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperWithFeature(String str, String str2, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getLockWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getBitmapCrops(List<Point> list, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getCurrentBitmapCrops(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getFutureBitmapCrops(Point point, List<Point> list, int[] iArr, List<Rect> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(point, 0);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect getBitmapCrop(Point point, int[] iArr, List<Rect> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(point, 0);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperIdForUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WallpaperInfo) parcelObtain2.readTypedObject(WallpaperInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WallpaperInfo) parcelObtain2.readTypedObject(WallpaperInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInstance getWallpaperInstance(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WallpaperInstance) parcelObtain2.readTypedObject(WallpaperInstance.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperInfoFile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void clearWallpaper(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasNamedWallpaper(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWidthHint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHeightHint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDisplayPadding(Rect rect, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void settingsRestored() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperSupported(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSetWallpaperAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupEligible(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semSetWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupAllowed(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WallpaperColors) parcelObtain2.readTypedObject(WallpaperColors.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void addOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setInAmbientMode(boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyWakingUp(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyGoingToSleep(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperDimAmount(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public float getWallpaperDimAmount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean lockScreenWallpaperExists() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStaticWallpaper(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopModeEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLockWallpaperType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int semGetWallpaperType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean semIsPreloadedWallpaper(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetWallpaperCropHint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDefaultWallpaperState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setVideoWallpaper(String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVideoWallpaper() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasVideoWallpaper() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFilePath(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoPackage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFileName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getMotionWallpaperPkgName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByWhich(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByKey(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotBySource(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int makeSnapshot(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean restoreSnapshot(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSnapshotTestMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setSnapshotTestMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getSnapshotCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setSnapshotSource(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isValidSnapshot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int[] getSnapshotKeys(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getAnimatedPkgName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getDeviceColor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLegacyDeviceColor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackageWithPrefix(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyFileToWallpaperFile(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyPreloadedFileToWallpaperFile(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSystemAndLockPaired(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHighlightFilterState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperComponentExtras(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperExtras(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperAssets(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Region getWallpaperBackgroundRegion(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Region) parcelObtain2.readTypedObject(Region.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperOrientation(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWallpaperColors) parcelObtain2.readTypedObject(SemWallpaperColors.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetPrimaryWallpaperColors(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWallpaperColors) parcelObtain2.readTypedObject(SemWallpaperColors.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semClearWallpaperThumbnailCache(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semWallpaperColors, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeTypedObject(rect2, 0);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetSmartCropRect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLidState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getDisplayId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVirtualWallpaperDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWaitingForUnlockUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String semGetUri(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void forceRebindWallpaper(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyPid(int i, int i2, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperDataExists(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyAodVisibilityState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStockLiveWallpaper(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
