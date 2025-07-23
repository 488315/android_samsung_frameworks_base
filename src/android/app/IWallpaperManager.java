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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWallpaperManager)) {
                return (IWallpaperManager) queryLocalInterface;
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Rect.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    Bundle bundle = new Bundle();
                    int readInt = parcel.readInt();
                    IWallpaperManagerCallback asInterface = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaper = setWallpaper(readString, readString2, createIntArray, createTypedArrayList, readBoolean, bundle, readInt, asInterface, readInt2, readInt3, readBoolean2, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper, 1);
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    WallpaperDescription wallpaperDescription = (WallpaperDescription) parcel.readTypedObject(WallpaperDescription.CREATOR);
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWallpaperComponentChecked(wallpaperDescription, readString3, readInt4, readInt5, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWallpaperComponent(componentName);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    IWallpaperManagerCallback asInterface2 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    Bundle bundle4 = new Bundle();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaper2 = getWallpaper(readString4, asInterface2, readInt6, bundle4, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper2, 1);
                    parcel2.writeTypedObject(bundle4, 1);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    IWallpaperManagerCallback asInterface3 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    Bundle bundle5 = new Bundle();
                    int readInt9 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperWithFeature = getWallpaperWithFeature(readString5, readString6, asInterface3, readInt8, bundle5, readInt9, readBoolean3, readBoolean4, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperWithFeature, 1);
                    parcel2.writeTypedObject(bundle5, 1);
                    return true;
                case 6:
                    IWallpaperManagerCallback asInterface4 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle6 = new Bundle();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor lockWallpaper = getLockWallpaper(asInterface4, bundle6, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lockWallpaper, 1);
                    parcel2.writeTypedObject(bundle6, 1);
                    return true;
                case 7:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Point.CREATOR);
                    int readInt13 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List bitmapCrops = getBitmapCrops(createTypedArrayList2, readInt13, readBoolean5, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeList(bitmapCrops);
                    return true;
                case 8:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle currentBitmapCrops = getCurrentBitmapCrops(readInt15, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentBitmapCrops, 1);
                    return true;
                case 9:
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(Point.CREATOR);
                    int[] createIntArray2 = parcel.createIntArray();
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    List futureBitmapCrops = getFutureBitmapCrops(point, createTypedArrayList3, createIntArray2, createTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeList(futureBitmapCrops);
                    return true;
                case 10:
                    Point point2 = (Point) parcel.readTypedObject(Point.CREATOR);
                    int[] createIntArray3 = parcel.createIntArray();
                    ArrayList createTypedArrayList5 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect bitmapCrop = getBitmapCrop(point2, createIntArray3, createTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapCrop, 1);
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wallpaperIdForUser = getWallpaperIdForUser(readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperIdForUser);
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInfo wallpaperInfo = getWallpaperInfo(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfo, 1);
                    return true;
                case 13:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInfo wallpaperInfoWithFlags = getWallpaperInfoWithFlags(readInt20, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoWithFlags, 1);
                    return true;
                case 14:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperInstance wallpaperInstance = getWallpaperInstance(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInstance, 1);
                    return true;
                case 15:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperInfoFile = getWallpaperInfoFile(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoFile, 1);
                    return true;
                case 16:
                    String readString7 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearWallpaper(readString7, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasNamedWallpaper = hasNamedWallpaper(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNamedWallpaper);
                    return true;
                case 18:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    String readString9 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDimensionHints(readInt27, readInt28, readString9, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int widthHint = getWidthHint(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(widthHint);
                    return true;
                case 20:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int heightHint = getHeightHint(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeInt(heightHint);
                    return true;
                case 21:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    String readString10 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayPadding(rect, readString10, readInt32);
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
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperSupported = isWallpaperSupported(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperSupported);
                    return true;
                case 25:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSetWallpaperAllowed = isSetWallpaperAllowed(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSetWallpaperAllowed);
                    return true;
                case 26:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperBackupEligible = isWallpaperBackupEligible(readInt33, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperBackupEligible);
                    return true;
                case 27:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int[] createIntArray4 = parcel.createIntArray();
                    ArrayList createTypedArrayList6 = parcel.createTypedArrayList(Rect.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    Bundle bundle7 = new Bundle();
                    int readInt35 = parcel.readInt();
                    IWallpaperManagerCallback asInterface5 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor semSetWallpaper = semSetWallpaper(readString13, readString14, createIntArray4, createTypedArrayList6, readBoolean6, bundle7, readInt35, asInterface5, readInt36, readInt37, readBoolean7, bundle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semSetWallpaper, 1);
                    parcel2.writeTypedObject(bundle7, 1);
                    return true;
                case 28:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperBackupAllowed = isWallpaperBackupAllowed(readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperBackupAllowed);
                    return true;
                case 29:
                    IWallpaperManagerCallback asInterface6 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean lockWallpaperCallback = setLockWallpaperCallback(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockWallpaperCallback);
                    return true;
                case 30:
                    IWallpaperManagerCallback asInterface7 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean coverWallpaperCallback = setCoverWallpaperCallback(asInterface7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(coverWallpaperCallback);
                    return true;
                case 31:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperColors wallpaperColors = getWallpaperColors(readInt40, readInt41, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperColors, 1);
                    return true;
                case 32:
                    ILocalWallpaperColorConsumer asInterface8 = ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList7 = parcel.createTypedArrayList(RectF.CREATOR);
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnLocalColorsChangedListener(asInterface8, createTypedArrayList7, readInt43, readInt44, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ILocalWallpaperColorConsumer asInterface9 = ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList8 = parcel.createTypedArrayList(RectF.CREATOR);
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnLocalColorsChangedListener(asInterface9, createTypedArrayList8, readInt46, readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IWallpaperManagerCallback asInterface10 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerWallpaperColorsCallback(asInterface10, readInt49, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IWallpaperManagerCallback asInterface11 = IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperColorsCallback(asInterface11, readInt51, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean readBoolean8 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInAmbientMode(readBoolean8, readLong);
                    return true;
                case 37:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyWakingUp(readInt53, readInt54, bundle9);
                    return true;
                case 38:
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyGoingToSleep(readInt55, readInt56, bundle10);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperDimAmount(readFloat);
                    return true;
                case 40:
                    float wallpaperDimAmount = getWallpaperDimAmount();
                    parcel2.writeNoException();
                    parcel2.writeFloat(wallpaperDimAmount);
                    return true;
                case 41:
                    boolean lockScreenWallpaperExists = lockScreenWallpaperExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockScreenWallpaperExists);
                    return true;
                case 42:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStaticWallpaper = isStaticWallpaper(readInt57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStaticWallpaper);
                    return true;
                case 43:
                    boolean isDesktopMode = isDesktopMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopMode);
                    return true;
                case 44:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDesktopModeEnabled = isDesktopModeEnabled(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopModeEnabled);
                    return true;
                case 45:
                    int lockWallpaperType = getLockWallpaperType();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockWallpaperType);
                    return true;
                case 46:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int semGetWallpaperType = semGetWallpaperType(readInt59);
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetWallpaperType);
                    return true;
                case 47:
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName semGetWallpaperComponent = semGetWallpaperComponent(readInt60, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetWallpaperComponent, 1);
                    return true;
                case 48:
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semIsPreloadedWallpaper = semIsPreloadedWallpaper(readInt62, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsPreloadedWallpaper);
                    return true;
                case 49:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect semGetWallpaperCropHint = semGetWallpaperCropHint(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetWallpaperCropHint, 1);
                    return true;
                case 50:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDefaultWallpaperState = isDefaultWallpaperState(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDefaultWallpaperState);
                    return true;
                case 51:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoWallpaper(readString15, readString16, readString17, readString18, readInt66, readInt67, readBoolean9, bundle11);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean isVideoWallpaper = isVideoWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoWallpaper);
                    return true;
                case 53:
                    boolean hasVideoWallpaper = hasVideoWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasVideoWallpaper);
                    return true;
                case 54:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor semGetThumbnailFileDescriptor = semGetThumbnailFileDescriptor(readInt68, readInt69, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetThumbnailFileDescriptor, 1);
                    return true;
                case 55:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoFilePath = getVideoFilePath(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeString(videoFilePath);
                    return true;
                case 56:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoPackage = getVideoPackage(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeString(videoPackage);
                    return true;
                case 57:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String videoFileName = getVideoFileName(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeString(videoFileName);
                    return true;
                case 58:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMotionWallpaper(readString19, readString20, readInt74, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String motionWallpaperPkgName = getMotionWallpaperPkgName(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeString(motionWallpaperPkgName);
                    return true;
                case 60:
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAnimatedWallpaper(readString21, readString22, readInt76, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSnapshotByWhich(readInt77);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSnapshotByKey(readInt78);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSnapshotBySource(readString23);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int makeSnapshot = makeSnapshot(readInt79, readInt80, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeSnapshot);
                    return true;
                case 65:
                    int readInt81 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean restoreSnapshot = restoreSnapshot(readInt81, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(restoreSnapshot);
                    return true;
                case 66:
                    boolean isSnapshotTestMode = isSnapshotTestMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSnapshotTestMode);
                    return true;
                case 67:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSnapshotTestMode(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int snapshotCount = getSnapshotCount(readInt82);
                    parcel2.writeNoException();
                    parcel2.writeInt(snapshotCount);
                    return true;
                case 69:
                    int readInt83 = parcel.readInt();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean snapshotSource = setSnapshotSource(readInt83, readString25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(snapshotSource);
                    return true;
                case 70:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isValidSnapshot = isValidSnapshot(readInt84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValidSnapshot);
                    return true;
                case 71:
                    String readString26 = parcel.readString();
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] snapshotKeys = getSnapshotKeys(readString26, readInt85);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(snapshotKeys);
                    return true;
                case 72:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String animatedPkgName = getAnimatedPkgName(readInt86);
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
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lastCallingPackage = getLastCallingPackage(readInt87);
                    parcel2.writeNoException();
                    parcel2.writeString(lastCallingPackage);
                    return true;
                case 76:
                    int readInt88 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String lastCallingPackageWithPrefix = getLastCallingPackageWithPrefix(readInt88, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeString(lastCallingPackageWithPrefix);
                    return true;
                case 77:
                    int readInt89 = parcel.readInt();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    copyFileToWallpaperFile(readInt89, readString27);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt90 = parcel.readInt();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    copyPreloadedFileToWallpaperFile(readInt90, readString28);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSystemAndLockPaired = isSystemAndLockPaired(readInt91);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemAndLockPaired);
                    return true;
                case 80:
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int highlightFilterState = getHighlightFilterState(readInt92);
                    parcel2.writeNoException();
                    parcel2.writeInt(highlightFilterState);
                    return true;
                case 81:
                    int readInt93 = parcel.readInt();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperComponentExtras = getWallpaperComponentExtras(readInt93, readInt94);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperComponentExtras, 1);
                    return true;
                case 82:
                    int readInt95 = parcel.readInt();
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperExtras = getWallpaperExtras(readInt95, readInt96);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperExtras, 1);
                    return true;
                case 83:
                    int readInt97 = parcel.readInt();
                    int readInt98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle wallpaperAssets = getWallpaperAssets(readInt97, readInt98);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperAssets, 1);
                    return true;
                case 84:
                    String readString29 = parcel.readString();
                    int readInt99 = parcel.readInt();
                    int readInt100 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor wallpaperAssetFile = getWallpaperAssetFile(readString29, readInt99, readInt100, readString30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperAssetFile, 1);
                    return true;
                case 85:
                    int readInt101 = parcel.readInt();
                    int readInt102 = parcel.readInt();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor screenshotFileDescriptor = getScreenshotFileDescriptor(readInt101, readInt102, bundle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotFileDescriptor, 1);
                    return true;
                case 86:
                    int readInt103 = parcel.readInt();
                    int readInt104 = parcel.readInt();
                    int readInt105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region wallpaperBackgroundRegion = getWallpaperBackgroundRegion(readInt103, readInt104, readInt105);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperBackgroundRegion, 1);
                    return true;
                case 87:
                    int readInt106 = parcel.readInt();
                    int readInt107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wallpaperOrientation = getWallpaperOrientation(readInt106, readInt107);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperOrientation);
                    return true;
                case 88:
                    int readInt108 = parcel.readInt();
                    String readString31 = parcel.readString();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSendWallpaperCommand(readInt108, readString31, bundle14);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWallpaperColors semGetWallpaperColors = semGetWallpaperColors(readInt109);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetWallpaperColors, 1);
                    return true;
                case 90:
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWallpaperColors semGetPrimaryWallpaperColors = semGetPrimaryWallpaperColors(readInt110);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetPrimaryWallpaperColors, 1);
                    return true;
                case 91:
                    int readInt111 = parcel.readInt();
                    int readInt112 = parcel.readInt();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semClearWallpaperThumbnailCache(readInt111, readInt112, readString32);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt113 = parcel.readInt();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semRequestWallpaperColorsAnalysis(readInt113, readString33);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    SemWallpaperColors semWallpaperColors = (SemWallpaperColors) parcel.readTypedObject(SemWallpaperColors.CREATOR);
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetDLSWallpaperColors(semWallpaperColors, readInt114);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int readInt115 = parcel.readInt();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSetSmartCropRect(readInt115, rect2, rect3);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    int readInt116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect semGetSmartCropRect = semGetSmartCropRect(readInt116);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetSmartCropRect, 1);
                    return true;
                case 96:
                    int lidState = getLidState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lidState);
                    return true;
                case 97:
                    int readInt117 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayId = getDisplayId(readInt117);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 98:
                    int readInt118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVirtualWallpaperDisplay = isVirtualWallpaperDisplay(readInt118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVirtualWallpaperDisplay);
                    return true;
                case 99:
                    int readInt119 = parcel.readInt();
                    int readInt120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWaitingForUnlockUser = isWaitingForUnlockUser(readInt119, readInt120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWaitingForUnlockUser);
                    return true;
                case 100:
                    String readString34 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    int readInt121 = parcel.readInt();
                    int readInt122 = parcel.readInt();
                    String readString35 = parcel.readString();
                    int readInt123 = parcel.readInt();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    semSetUri(readString34, readBoolean14, readInt121, readInt122, readString35, readInt123, bundle15);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    int readInt124 = parcel.readInt();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String semGetUri = semGetUri(readInt124, readString36);
                    parcel2.writeNoException();
                    parcel2.writeString(semGetUri);
                    return true;
                case 102:
                    int readInt125 = parcel.readInt();
                    int readInt126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRebindWallpaper(readInt125, readInt126);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int readInt127 = parcel.readInt();
                    int readInt128 = parcel.readInt();
                    String readString37 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyPid(readInt127, readInt128, readString37, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int readInt129 = parcel.readInt();
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperDataExists = isWallpaperDataExists(readInt129, readInt130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperDataExists);
                    return true;
                case 105:
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAodVisibilityState(readInt131);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int readInt132 = parcel.readInt();
                    int readInt133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStockLiveWallpaper = isStockLiveWallpaper(readInt132, readInt133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStockLiveWallpaper);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle2, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponentChecked(WallpaperDescription wallpaperDescription, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(wallpaperDescription, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperWithFeature(String str, String str2, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getLockWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getBitmapCrops(List<Point> list, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getCurrentBitmapCrops(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public List getFutureBitmapCrops(Point point, List<Point> list, int[] iArr, List<Rect> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(point, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect getBitmapCrop(Point point, int[] iArr, List<Rect> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(point, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperIdForUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WallpaperInfo) obtain2.readTypedObject(WallpaperInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WallpaperInfo) obtain2.readTypedObject(WallpaperInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperInstance getWallpaperInstance(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WallpaperInstance) obtain2.readTypedObject(WallpaperInstance.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperInfoFile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void clearWallpaper(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasNamedWallpaper(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWidthHint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHeightHint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDisplayPadding(Rect rect, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void settingsRestored() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperSupported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSetWallpaperAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupEligible(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semSetWallpaper(String str, String str2, int[] iArr, List<Rect> list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle2, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupAllowed(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WallpaperColors) obtain2.readTypedObject(WallpaperColors.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void addOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setInAmbientMode(boolean z, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyWakingUp(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyGoingToSleep(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperDimAmount(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public float getWallpaperDimAmount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean lockScreenWallpaperExists() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStaticWallpaper(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDesktopModeEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLockWallpaperType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int semGetWallpaperType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean semIsPreloadedWallpaper(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetWallpaperCropHint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isDefaultWallpaperState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setVideoWallpaper(String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVideoWallpaper() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasVideoWallpaper() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFilePath(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoPackage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getVideoFileName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getMotionWallpaperPkgName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByWhich(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotByKey(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeSnapshotBySource(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int makeSnapshot(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean restoreSnapshot(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSnapshotTestMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setSnapshotTestMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getSnapshotCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean setSnapshotSource(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isValidSnapshot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int[] getSnapshotKeys(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getAnimatedPkgName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getDeviceColor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLegacyDeviceColor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String getLastCallingPackageWithPrefix(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyFileToWallpaperFile(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void copyPreloadedFileToWallpaperFile(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSystemAndLockPaired(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHighlightFilterState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperComponentExtras(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperExtras(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Bundle getWallpaperAssets(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Region getWallpaperBackgroundRegion(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Region) obtain2.readTypedObject(Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperOrientation(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWallpaperColors) obtain2.readTypedObject(SemWallpaperColors.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public SemWallpaperColors semGetPrimaryWallpaperColors(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWallpaperColors) obtain2.readTypedObject(SemWallpaperColors.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semClearWallpaperThumbnailCache(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(semWallpaperColors, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(rect2, 0);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public Rect semGetSmartCropRect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getLidState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getDisplayId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isVirtualWallpaperDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWaitingForUnlockUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public String semGetUri(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void forceRebindWallpaper(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyPid(int i, int i2, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperDataExists(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyAodVisibilityState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStockLiveWallpaper(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
