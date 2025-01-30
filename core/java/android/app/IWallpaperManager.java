package android.app;

import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.RectF;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
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

  int getDesktopMode() throws RemoteException;

  String getDeviceColor() throws RemoteException;

  int getDisplayId(int i) throws RemoteException;

  int getHeightHint(int i) throws RemoteException;

  int getHighlightFilterState(int i) throws RemoteException;

  String getLastCallingPackage(int i) throws RemoteException;

  String getLegacyDeviceColor() throws RemoteException;

  int getLidState() throws RemoteException;

  ParcelFileDescriptor getLockWallpaper(
      IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2)
      throws RemoteException;

  int getLockWallpaperType() throws RemoteException;

  String getMotionWallpaperPkgName(int i) throws RemoteException;

  String getName() throws RemoteException;

  int getSnapshotCount(int i) throws RemoteException;

  int[] getSnapshotKeys(String str, int i) throws RemoteException;

  String getVideoFileName(int i) throws RemoteException;

  String getVideoFilePath(int i) throws RemoteException;

  String getVideoPackage(int i) throws RemoteException;

  @Deprecated
  ParcelFileDescriptor getWallpaper(
      String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2)
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

  boolean isLockscreenLiveWallpaperEnabled() throws RemoteException;

  boolean isMultiCropEnabled() throws RemoteException;

  boolean isSetWallpaperAllowed(String str) throws RemoteException;

  boolean isSnapshotTestMode() throws RemoteException;

  boolean isStaticWallpaper(int i) throws RemoteException;

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
      IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

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

  String semGetUri(int i, String str) throws RemoteException;

  SemWallpaperColors semGetWallpaperColors(int i) throws RemoteException;

  ComponentName semGetWallpaperComponent(int i, int i2) throws RemoteException;

  Rect semGetWallpaperCropHint(int i) throws RemoteException;

  int semGetWallpaperType(int i) throws RemoteException;

  void semRequestWallpaperColorsAnalysis(int i, String str) throws RemoteException;

  void semSendWallpaperCommand(int i, String str, Bundle bundle) throws RemoteException;

  void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i)
      throws RemoteException;

  void semSetSmartCropRect(int i, Rect rect, Rect rect2) throws RemoteException;

  void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle)
      throws RemoteException;

  void semSetWallpaperColorOverrideAreas(int i, int i2, String str) throws RemoteException;

  void setAnimatedWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

  boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback)
      throws RemoteException;

  void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException;

  void setDisplayPadding(Rect rect, String str, int i) throws RemoteException;

  void setInAmbientMode(boolean z, long j) throws RemoteException;

  void setKWPTypeLiveWallpaper(int i) throws RemoteException;

  void setKWPTypeLiveWallpaperWithMode(int i, int i2) throws RemoteException;

  boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback)
      throws RemoteException;

  void setMotionWallpaper(String str, String str2, int i, boolean z) throws RemoteException;

  boolean setSnapshotSource(int i, String str) throws RemoteException;

  void setSnapshotTestMode(boolean z) throws RemoteException;

  void setVideoWallpaper(
      String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle)
      throws RemoteException;

  ParcelFileDescriptor setWallpaper(
      String str,
      String str2,
      Rect rect,
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
      ComponentName componentName, String str, int i, int i2, Bundle bundle) throws RemoteException;

  void setWallpaperDimAmount(float f) throws RemoteException;

  void settingsRestored() throws RemoteException;

  void unregisterWallpaperColorsCallback(
      IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

  public static class Default implements IWallpaperManager {
    @Override // android.app.IWallpaperManager
    public ParcelFileDescriptor setWallpaper(
        String name,
        String callingPackage,
        Rect cropHint,
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
        String callingPkg, IWallpaperManagerCallback cb, int which, Bundle outParams, int userId)
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
    public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
      return 0;
    }

    @Override // android.app.IWallpaperManager
    public WallpaperInfo getWallpaperInfo(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IWallpaperManager
    public WallpaperInfo getWallpaperInfoWithFlags(int which, int userId) throws RemoteException {
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
    public boolean isWallpaperBackupAllowed(int which, int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.IWallpaperManager
    public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb) throws RemoteException {
      return false;
    }

    @Override // android.app.IWallpaperManager
    public boolean setCoverWallpaperCallback(IWallpaperManagerCallback cb) throws RemoteException {
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
    public boolean isLockscreenLiveWallpaperEnabled() throws RemoteException {
      return false;
    }

    @Override // android.app.IWallpaperManager
    public boolean isMultiCropEnabled() throws RemoteException {
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
    public ComponentName semGetWallpaperComponent(int which, int userId) throws RemoteException {
      return null;
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
    public void setKWPTypeLiveWallpaper(int value) throws RemoteException {}

    @Override // android.app.IWallpaperManager
    public void setKWPTypeLiveWallpaperWithMode(int mode, int value) throws RemoteException {}

    @Override // android.app.IWallpaperManager
    public void copyFileToWallpaperFile(int which, String callingPackage) throws RemoteException {}

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
        String callingPkg, int which, int userId, String assetFilePath) throws RemoteException {
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
    public void semSetWallpaperColorOverrideAreas(int which, int userId, String colorAreas)
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

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IWallpaperManager {
    public static final String DESCRIPTOR = "android.app.IWallpaperManager";
    static final int TRANSACTION_addOnLocalColorsChangedListener = 27;
    static final int TRANSACTION_clearWallpaper = 11;
    static final int TRANSACTION_copyFileToWallpaperFile = 75;
    static final int TRANSACTION_copyPreloadedFileToWallpaperFile = 76;
    static final int TRANSACTION_forceRebindWallpaper = 99;
    static final int TRANSACTION_getAnimatedPkgName = 69;
    static final int TRANSACTION_getDesktopMode = 40;
    static final int TRANSACTION_getDeviceColor = 70;
    static final int TRANSACTION_getDisplayId = 94;
    static final int TRANSACTION_getHeightHint = 15;
    static final int TRANSACTION_getHighlightFilterState = 78;
    static final int TRANSACTION_getLastCallingPackage = 72;
    static final int TRANSACTION_getLegacyDeviceColor = 71;
    static final int TRANSACTION_getLidState = 93;
    static final int TRANSACTION_getLockWallpaper = 6;
    static final int TRANSACTION_getLockWallpaperType = 43;
    static final int TRANSACTION_getMotionWallpaperPkgName = 56;
    static final int TRANSACTION_getName = 17;
    static final int TRANSACTION_getSnapshotCount = 65;
    static final int TRANSACTION_getSnapshotKeys = 68;
    static final int TRANSACTION_getVideoFileName = 54;
    static final int TRANSACTION_getVideoFilePath = 52;
    static final int TRANSACTION_getVideoPackage = 53;
    static final int TRANSACTION_getWallpaper = 4;
    static final int TRANSACTION_getWallpaperAssetFile = 82;
    static final int TRANSACTION_getWallpaperAssets = 81;
    static final int TRANSACTION_getWallpaperColors = 25;
    static final int TRANSACTION_getWallpaperComponentExtras = 79;
    static final int TRANSACTION_getWallpaperDimAmount = 34;
    static final int TRANSACTION_getWallpaperExtras = 80;
    static final int TRANSACTION_getWallpaperIdForUser = 7;
    static final int TRANSACTION_getWallpaperInfo = 8;
    static final int TRANSACTION_getWallpaperInfoFile = 10;
    static final int TRANSACTION_getWallpaperInfoWithFlags = 9;
    static final int TRANSACTION_getWallpaperOrientation = 83;
    static final int TRANSACTION_getWallpaperThumbnailFileDescriptor = 51;
    static final int TRANSACTION_getWallpaperWithFeature = 5;
    static final int TRANSACTION_getWidthHint = 14;
    static final int TRANSACTION_hasNamedWallpaper = 12;
    static final int TRANSACTION_hasVideoWallpaper = 50;
    static final int TRANSACTION_isDefaultWallpaperState = 47;
    static final int TRANSACTION_isDesktopMode = 39;
    static final int TRANSACTION_isDesktopModeEnabled = 41;
    static final int TRANSACTION_isDesktopStandAloneMode = 42;
    static final int TRANSACTION_isLockscreenLiveWallpaperEnabled = 37;
    static final int TRANSACTION_isMultiCropEnabled = 38;
    static final int TRANSACTION_isSetWallpaperAllowed = 20;
    static final int TRANSACTION_isSnapshotTestMode = 63;
    static final int TRANSACTION_isStaticWallpaper = 36;
    static final int TRANSACTION_isSystemAndLockPaired = 77;
    static final int TRANSACTION_isValidSnapshot = 67;
    static final int TRANSACTION_isVideoWallpaper = 49;
    static final int TRANSACTION_isVirtualWallpaperDisplay = 95;
    static final int TRANSACTION_isWaitingForUnlockUser = 96;
    static final int TRANSACTION_isWallpaperBackupAllowed = 22;
    static final int TRANSACTION_isWallpaperBackupEligible = 21;
    static final int TRANSACTION_isWallpaperDataExists = 101;
    static final int TRANSACTION_isWallpaperSupported = 19;
    static final int TRANSACTION_lockScreenWallpaperExists = 35;
    static final int TRANSACTION_makeSnapshot = 61;
    static final int TRANSACTION_notifyAodVisibilityState = 102;
    static final int TRANSACTION_notifyGoingToSleep = 32;
    static final int TRANSACTION_notifyPid = 100;
    static final int TRANSACTION_notifyWakingUp = 31;
    static final int TRANSACTION_registerWallpaperColorsCallback = 28;
    static final int TRANSACTION_removeOnLocalColorsChangedListener = 26;
    static final int TRANSACTION_removeSnapshotByKey = 59;
    static final int TRANSACTION_removeSnapshotBySource = 60;
    static final int TRANSACTION_removeSnapshotByWhich = 58;
    static final int TRANSACTION_restoreSnapshot = 62;
    static final int TRANSACTION_semClearWallpaperThumbnailCache = 87;
    static final int TRANSACTION_semGetPrimaryWallpaperColors = 86;
    static final int TRANSACTION_semGetSmartCropRect = 92;
    static final int TRANSACTION_semGetUri = 98;
    static final int TRANSACTION_semGetWallpaperColors = 85;
    static final int TRANSACTION_semGetWallpaperComponent = 45;
    static final int TRANSACTION_semGetWallpaperCropHint = 46;
    static final int TRANSACTION_semGetWallpaperType = 44;
    static final int TRANSACTION_semRequestWallpaperColorsAnalysis = 88;
    static final int TRANSACTION_semSendWallpaperCommand = 84;
    static final int TRANSACTION_semSetDLSWallpaperColors = 90;
    static final int TRANSACTION_semSetSmartCropRect = 91;
    static final int TRANSACTION_semSetUri = 97;
    static final int TRANSACTION_semSetWallpaperColorOverrideAreas = 89;
    static final int TRANSACTION_setAnimatedWallpaper = 57;
    static final int TRANSACTION_setCoverWallpaperCallback = 24;
    static final int TRANSACTION_setDimensionHints = 13;
    static final int TRANSACTION_setDisplayPadding = 16;
    static final int TRANSACTION_setInAmbientMode = 30;
    static final int TRANSACTION_setKWPTypeLiveWallpaper = 73;
    static final int TRANSACTION_setKWPTypeLiveWallpaperWithMode = 74;
    static final int TRANSACTION_setLockWallpaperCallback = 23;
    static final int TRANSACTION_setMotionWallpaper = 55;
    static final int TRANSACTION_setSnapshotSource = 66;
    static final int TRANSACTION_setSnapshotTestMode = 64;
    static final int TRANSACTION_setVideoWallpaper = 48;
    static final int TRANSACTION_setWallpaper = 1;
    static final int TRANSACTION_setWallpaperComponent = 3;
    static final int TRANSACTION_setWallpaperComponentChecked = 2;
    static final int TRANSACTION_setWallpaperDimAmount = 33;
    static final int TRANSACTION_settingsRestored = 18;
    static final int TRANSACTION_unregisterWallpaperColorsCallback = 29;

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

    @Override // android.p009os.IInterface
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
          return "getWallpaperIdForUser";
        case 8:
          return "getWallpaperInfo";
        case 9:
          return "getWallpaperInfoWithFlags";
        case 10:
          return "getWallpaperInfoFile";
        case 11:
          return "clearWallpaper";
        case 12:
          return "hasNamedWallpaper";
        case 13:
          return "setDimensionHints";
        case 14:
          return "getWidthHint";
        case 15:
          return "getHeightHint";
        case 16:
          return "setDisplayPadding";
        case 17:
          return "getName";
        case 18:
          return "settingsRestored";
        case 19:
          return "isWallpaperSupported";
        case 20:
          return "isSetWallpaperAllowed";
        case 21:
          return "isWallpaperBackupEligible";
        case 22:
          return "isWallpaperBackupAllowed";
        case 23:
          return "setLockWallpaperCallback";
        case 24:
          return "setCoverWallpaperCallback";
        case 25:
          return "getWallpaperColors";
        case 26:
          return "removeOnLocalColorsChangedListener";
        case 27:
          return "addOnLocalColorsChangedListener";
        case 28:
          return "registerWallpaperColorsCallback";
        case 29:
          return "unregisterWallpaperColorsCallback";
        case 30:
          return "setInAmbientMode";
        case 31:
          return "notifyWakingUp";
        case 32:
          return "notifyGoingToSleep";
        case 33:
          return "setWallpaperDimAmount";
        case 34:
          return "getWallpaperDimAmount";
        case 35:
          return "lockScreenWallpaperExists";
        case 36:
          return "isStaticWallpaper";
        case 37:
          return "isLockscreenLiveWallpaperEnabled";
        case 38:
          return "isMultiCropEnabled";
        case 39:
          return "isDesktopMode";
        case 40:
          return "getDesktopMode";
        case 41:
          return "isDesktopModeEnabled";
        case 42:
          return "isDesktopStandAloneMode";
        case 43:
          return "getLockWallpaperType";
        case 44:
          return "semGetWallpaperType";
        case 45:
          return "semGetWallpaperComponent";
        case 46:
          return "semGetWallpaperCropHint";
        case 47:
          return "isDefaultWallpaperState";
        case 48:
          return "setVideoWallpaper";
        case 49:
          return "isVideoWallpaper";
        case 50:
          return "hasVideoWallpaper";
        case 51:
          return "getWallpaperThumbnailFileDescriptor";
        case 52:
          return "getVideoFilePath";
        case 53:
          return "getVideoPackage";
        case 54:
          return "getVideoFileName";
        case 55:
          return "setMotionWallpaper";
        case 56:
          return "getMotionWallpaperPkgName";
        case 57:
          return "setAnimatedWallpaper";
        case 58:
          return "removeSnapshotByWhich";
        case 59:
          return "removeSnapshotByKey";
        case 60:
          return "removeSnapshotBySource";
        case 61:
          return "makeSnapshot";
        case 62:
          return "restoreSnapshot";
        case 63:
          return "isSnapshotTestMode";
        case 64:
          return "setSnapshotTestMode";
        case 65:
          return "getSnapshotCount";
        case 66:
          return "setSnapshotSource";
        case 67:
          return "isValidSnapshot";
        case 68:
          return "getSnapshotKeys";
        case 69:
          return "getAnimatedPkgName";
        case 70:
          return "getDeviceColor";
        case 71:
          return "getLegacyDeviceColor";
        case 72:
          return "getLastCallingPackage";
        case 73:
          return "setKWPTypeLiveWallpaper";
        case 74:
          return "setKWPTypeLiveWallpaperWithMode";
        case 75:
          return "copyFileToWallpaperFile";
        case 76:
          return "copyPreloadedFileToWallpaperFile";
        case 77:
          return "isSystemAndLockPaired";
        case 78:
          return "getHighlightFilterState";
        case 79:
          return "getWallpaperComponentExtras";
        case 80:
          return "getWallpaperExtras";
        case 81:
          return "getWallpaperAssets";
        case 82:
          return "getWallpaperAssetFile";
        case 83:
          return "getWallpaperOrientation";
        case 84:
          return "semSendWallpaperCommand";
        case 85:
          return "semGetWallpaperColors";
        case 86:
          return "semGetPrimaryWallpaperColors";
        case 87:
          return "semClearWallpaperThumbnailCache";
        case 88:
          return "semRequestWallpaperColorsAnalysis";
        case 89:
          return "semSetWallpaperColorOverrideAreas";
        case 90:
          return "semSetDLSWallpaperColors";
        case 91:
          return "semSetSmartCropRect";
        case 92:
          return "semGetSmartCropRect";
        case 93:
          return "getLidState";
        case 94:
          return "getDisplayId";
        case 95:
          return "isVirtualWallpaperDisplay";
        case 96:
          return "isWaitingForUnlockUser";
        case 97:
          return "semSetUri";
        case 98:
          return "semGetUri";
        case 99:
          return "forceRebindWallpaper";
        case 100:
          return "notifyPid";
        case 101:
          return "isWallpaperDataExists";
        case 102:
          return "notifyAodVisibilityState";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              String _arg1 = data.readString();
              Rect _arg2 = (Rect) data.readTypedObject(Rect.CREATOR);
              boolean _arg3 = data.readBoolean();
              Bundle _arg4 = new Bundle();
              int _arg5 = data.readInt();
              IWallpaperManagerCallback _arg6 =
                  IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
              int _arg7 = data.readInt();
              int _arg8 = data.readInt();
              boolean _arg9 = data.readBoolean();
              Bundle _arg10 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result =
                  setWallpaper(
                      _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              reply.writeTypedObject(_arg4, 1);
              return true;
            case 2:
              ComponentName _arg02 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg12 = data.readString();
              int _arg22 = data.readInt();
              int _arg32 = data.readInt();
              Bundle _arg42 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              setWallpaperComponentChecked(_arg02, _arg12, _arg22, _arg32, _arg42);
              reply.writeNoException();
              return true;
            case 3:
              ComponentName _arg03 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
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
              ParcelFileDescriptor _result2 = getWallpaper(_arg04, _arg13, _arg23, _arg33, _arg43);
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
                      _arg05, _arg14, _arg24, _arg34, _arg44, _arg52, _arg62, _arg72, _arg82);
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
              ParcelFileDescriptor _result4 = getLockWallpaper(_arg06, _arg15, _arg25, _arg35);
              reply.writeNoException();
              reply.writeTypedObject(_result4, 1);
              reply.writeTypedObject(_arg15, 1);
              return true;
            case 7:
              int _arg07 = data.readInt();
              int _arg16 = data.readInt();
              data.enforceNoDataAvail();
              int _result5 = getWallpaperIdForUser(_arg07, _arg16);
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 8:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              WallpaperInfo _result6 = getWallpaperInfo(_arg08);
              reply.writeNoException();
              reply.writeTypedObject(_result6, 1);
              return true;
            case 9:
              int _arg09 = data.readInt();
              int _arg17 = data.readInt();
              data.enforceNoDataAvail();
              WallpaperInfo _result7 = getWallpaperInfoWithFlags(_arg09, _arg17);
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 10:
              int _arg010 = data.readInt();
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result8 = getWallpaperInfoFile(_arg010);
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 11:
              String _arg011 = data.readString();
              int _arg18 = data.readInt();
              int _arg26 = data.readInt();
              data.enforceNoDataAvail();
              clearWallpaper(_arg011, _arg18, _arg26);
              reply.writeNoException();
              return true;
            case 12:
              String _arg012 = data.readString();
              data.enforceNoDataAvail();
              boolean _result9 = hasNamedWallpaper(_arg012);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 13:
              int _arg013 = data.readInt();
              int _arg19 = data.readInt();
              String _arg27 = data.readString();
              int _arg36 = data.readInt();
              data.enforceNoDataAvail();
              setDimensionHints(_arg013, _arg19, _arg27, _arg36);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              int _result10 = getWidthHint(_arg014);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 15:
              int _arg015 = data.readInt();
              data.enforceNoDataAvail();
              int _result11 = getHeightHint(_arg015);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 16:
              Rect _arg016 = (Rect) data.readTypedObject(Rect.CREATOR);
              String _arg110 = data.readString();
              int _arg28 = data.readInt();
              data.enforceNoDataAvail();
              setDisplayPadding(_arg016, _arg110, _arg28);
              reply.writeNoException();
              return true;
            case 17:
              String _result12 = getName();
              reply.writeNoException();
              reply.writeString(_result12);
              return true;
            case 18:
              settingsRestored();
              reply.writeNoException();
              return true;
            case 19:
              String _arg017 = data.readString();
              data.enforceNoDataAvail();
              boolean _result13 = isWallpaperSupported(_arg017);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 20:
              String _arg018 = data.readString();
              data.enforceNoDataAvail();
              boolean _result14 = isSetWallpaperAllowed(_arg018);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 21:
              int _arg019 = data.readInt();
              int _arg111 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result15 = isWallpaperBackupEligible(_arg019, _arg111);
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 22:
              int _arg020 = data.readInt();
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result16 = isWallpaperBackupAllowed(_arg020, _arg112);
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 23:
              IWallpaperManagerCallback _arg021 =
                  IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result17 = setLockWallpaperCallback(_arg021);
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 24:
              IWallpaperManagerCallback _arg022 =
                  IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result18 = setCoverWallpaperCallback(_arg022);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 25:
              int _arg023 = data.readInt();
              int _arg113 = data.readInt();
              int _arg29 = data.readInt();
              data.enforceNoDataAvail();
              WallpaperColors _result19 = getWallpaperColors(_arg023, _arg113, _arg29);
              reply.writeNoException();
              reply.writeTypedObject(_result19, 1);
              return true;
            case 26:
              ILocalWallpaperColorConsumer _arg024 =
                  ILocalWallpaperColorConsumer.Stub.asInterface(data.readStrongBinder());
              List<RectF> _arg114 = data.createTypedArrayList(RectF.CREATOR);
              int _arg210 = data.readInt();
              int _arg37 = data.readInt();
              int _arg45 = data.readInt();
              data.enforceNoDataAvail();
              removeOnLocalColorsChangedListener(_arg024, _arg114, _arg210, _arg37, _arg45);
              reply.writeNoException();
              return true;
            case 27:
              ILocalWallpaperColorConsumer _arg025 =
                  ILocalWallpaperColorConsumer.Stub.asInterface(data.readStrongBinder());
              List<RectF> _arg115 = data.createTypedArrayList(RectF.CREATOR);
              int _arg211 = data.readInt();
              int _arg38 = data.readInt();
              int _arg46 = data.readInt();
              data.enforceNoDataAvail();
              addOnLocalColorsChangedListener(_arg025, _arg115, _arg211, _arg38, _arg46);
              reply.writeNoException();
              return true;
            case 28:
              IWallpaperManagerCallback _arg026 =
                  IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
              int _arg116 = data.readInt();
              int _arg212 = data.readInt();
              data.enforceNoDataAvail();
              registerWallpaperColorsCallback(_arg026, _arg116, _arg212);
              reply.writeNoException();
              return true;
            case 29:
              IWallpaperManagerCallback _arg027 =
                  IWallpaperManagerCallback.Stub.asInterface(data.readStrongBinder());
              int _arg117 = data.readInt();
              int _arg213 = data.readInt();
              data.enforceNoDataAvail();
              unregisterWallpaperColorsCallback(_arg027, _arg117, _arg213);
              reply.writeNoException();
              return true;
            case 30:
              boolean _arg028 = data.readBoolean();
              long _arg118 = data.readLong();
              data.enforceNoDataAvail();
              setInAmbientMode(_arg028, _arg118);
              return true;
            case 31:
              int _arg029 = data.readInt();
              int _arg119 = data.readInt();
              Bundle _arg214 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              notifyWakingUp(_arg029, _arg119, _arg214);
              return true;
            case 32:
              int _arg030 = data.readInt();
              int _arg120 = data.readInt();
              Bundle _arg215 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              notifyGoingToSleep(_arg030, _arg120, _arg215);
              reply.writeNoException();
              return true;
            case 33:
              float _arg031 = data.readFloat();
              data.enforceNoDataAvail();
              setWallpaperDimAmount(_arg031);
              return true;
            case 34:
              float _result20 = getWallpaperDimAmount();
              reply.writeNoException();
              reply.writeFloat(_result20);
              return true;
            case 35:
              boolean _result21 = lockScreenWallpaperExists();
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 36:
              int _arg032 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result22 = isStaticWallpaper(_arg032);
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 37:
              boolean _result23 = isLockscreenLiveWallpaperEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 38:
              boolean _result24 = isMultiCropEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result24);
              return true;
            case 39:
              boolean _result25 = isDesktopMode();
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 40:
              int _result26 = getDesktopMode();
              reply.writeNoException();
              reply.writeInt(_result26);
              return true;
            case 41:
              int _arg033 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result27 = isDesktopModeEnabled(_arg033);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 42:
              boolean _result28 = isDesktopStandAloneMode();
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            case 43:
              int _result29 = getLockWallpaperType();
              reply.writeNoException();
              reply.writeInt(_result29);
              return true;
            case 44:
              int _arg034 = data.readInt();
              data.enforceNoDataAvail();
              int _result30 = semGetWallpaperType(_arg034);
              reply.writeNoException();
              reply.writeInt(_result30);
              return true;
            case 45:
              int _arg035 = data.readInt();
              int _arg121 = data.readInt();
              data.enforceNoDataAvail();
              ComponentName _result31 = semGetWallpaperComponent(_arg035, _arg121);
              reply.writeNoException();
              reply.writeTypedObject(_result31, 1);
              return true;
            case 46:
              int _arg036 = data.readInt();
              data.enforceNoDataAvail();
              Rect _result32 = semGetWallpaperCropHint(_arg036);
              reply.writeNoException();
              reply.writeTypedObject(_result32, 1);
              return true;
            case 47:
              int _arg037 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result33 = isDefaultWallpaperState(_arg037);
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 48:
              String _arg038 = data.readString();
              String _arg122 = data.readString();
              String _arg216 = data.readString();
              String _arg39 = data.readString();
              int _arg47 = data.readInt();
              int _arg53 = data.readInt();
              boolean _arg63 = data.readBoolean();
              Bundle _arg73 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              setVideoWallpaper(_arg038, _arg122, _arg216, _arg39, _arg47, _arg53, _arg63, _arg73);
              reply.writeNoException();
              return true;
            case 49:
              boolean _result34 = isVideoWallpaper();
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 50:
              boolean _result35 = hasVideoWallpaper();
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 51:
              int _arg039 = data.readInt();
              int _arg123 = data.readInt();
              int _arg217 = data.readInt();
              int _arg310 = data.readInt();
              int _arg48 = data.readInt();
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result36 =
                  getWallpaperThumbnailFileDescriptor(_arg039, _arg123, _arg217, _arg310, _arg48);
              reply.writeNoException();
              reply.writeTypedObject(_result36, 1);
              return true;
            case 52:
              int _arg040 = data.readInt();
              data.enforceNoDataAvail();
              String _result37 = getVideoFilePath(_arg040);
              reply.writeNoException();
              reply.writeString(_result37);
              return true;
            case 53:
              int _arg041 = data.readInt();
              data.enforceNoDataAvail();
              String _result38 = getVideoPackage(_arg041);
              reply.writeNoException();
              reply.writeString(_result38);
              return true;
            case 54:
              int _arg042 = data.readInt();
              data.enforceNoDataAvail();
              String _result39 = getVideoFileName(_arg042);
              reply.writeNoException();
              reply.writeString(_result39);
              return true;
            case 55:
              String _arg043 = data.readString();
              String _arg124 = data.readString();
              int _arg218 = data.readInt();
              boolean _arg311 = data.readBoolean();
              data.enforceNoDataAvail();
              setMotionWallpaper(_arg043, _arg124, _arg218, _arg311);
              reply.writeNoException();
              return true;
            case 56:
              int _arg044 = data.readInt();
              data.enforceNoDataAvail();
              String _result40 = getMotionWallpaperPkgName(_arg044);
              reply.writeNoException();
              reply.writeString(_result40);
              return true;
            case 57:
              String _arg045 = data.readString();
              String _arg125 = data.readString();
              int _arg219 = data.readInt();
              boolean _arg312 = data.readBoolean();
              data.enforceNoDataAvail();
              setAnimatedWallpaper(_arg045, _arg125, _arg219, _arg312);
              reply.writeNoException();
              return true;
            case 58:
              int _arg046 = data.readInt();
              data.enforceNoDataAvail();
              removeSnapshotByWhich(_arg046);
              reply.writeNoException();
              return true;
            case 59:
              int _arg047 = data.readInt();
              data.enforceNoDataAvail();
              removeSnapshotByKey(_arg047);
              reply.writeNoException();
              return true;
            case 60:
              String _arg048 = data.readString();
              data.enforceNoDataAvail();
              removeSnapshotBySource(_arg048);
              reply.writeNoException();
              return true;
            case 61:
              int _arg049 = data.readInt();
              int _arg126 = data.readInt();
              Bundle _arg220 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              int _result41 = makeSnapshot(_arg049, _arg126, _arg220);
              reply.writeNoException();
              reply.writeInt(_result41);
              return true;
            case 62:
              int _arg050 = data.readInt();
              String _arg127 = data.readString();
              data.enforceNoDataAvail();
              boolean _result42 = restoreSnapshot(_arg050, _arg127);
              reply.writeNoException();
              reply.writeBoolean(_result42);
              return true;
            case 63:
              boolean _result43 = isSnapshotTestMode();
              reply.writeNoException();
              reply.writeBoolean(_result43);
              return true;
            case 64:
              boolean _arg051 = data.readBoolean();
              data.enforceNoDataAvail();
              setSnapshotTestMode(_arg051);
              reply.writeNoException();
              return true;
            case 65:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              int _result44 = getSnapshotCount(_arg052);
              reply.writeNoException();
              reply.writeInt(_result44);
              return true;
            case 66:
              int _arg053 = data.readInt();
              String _arg128 = data.readString();
              data.enforceNoDataAvail();
              boolean _result45 = setSnapshotSource(_arg053, _arg128);
              reply.writeNoException();
              reply.writeBoolean(_result45);
              return true;
            case 67:
              int _arg054 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result46 = isValidSnapshot(_arg054);
              reply.writeNoException();
              reply.writeBoolean(_result46);
              return true;
            case 68:
              String _arg055 = data.readString();
              int _arg129 = data.readInt();
              data.enforceNoDataAvail();
              int[] _result47 = getSnapshotKeys(_arg055, _arg129);
              reply.writeNoException();
              reply.writeIntArray(_result47);
              return true;
            case 69:
              int _arg056 = data.readInt();
              data.enforceNoDataAvail();
              String _result48 = getAnimatedPkgName(_arg056);
              reply.writeNoException();
              reply.writeString(_result48);
              return true;
            case 70:
              String _result49 = getDeviceColor();
              reply.writeNoException();
              reply.writeString(_result49);
              return true;
            case 71:
              String _result50 = getLegacyDeviceColor();
              reply.writeNoException();
              reply.writeString(_result50);
              return true;
            case 72:
              int _arg057 = data.readInt();
              data.enforceNoDataAvail();
              String _result51 = getLastCallingPackage(_arg057);
              reply.writeNoException();
              reply.writeString(_result51);
              return true;
            case 73:
              int _arg058 = data.readInt();
              data.enforceNoDataAvail();
              setKWPTypeLiveWallpaper(_arg058);
              reply.writeNoException();
              return true;
            case 74:
              int _arg059 = data.readInt();
              int _arg130 = data.readInt();
              data.enforceNoDataAvail();
              setKWPTypeLiveWallpaperWithMode(_arg059, _arg130);
              reply.writeNoException();
              return true;
            case 75:
              int _arg060 = data.readInt();
              String _arg131 = data.readString();
              data.enforceNoDataAvail();
              copyFileToWallpaperFile(_arg060, _arg131);
              reply.writeNoException();
              return true;
            case 76:
              int _arg061 = data.readInt();
              String _arg132 = data.readString();
              data.enforceNoDataAvail();
              copyPreloadedFileToWallpaperFile(_arg061, _arg132);
              reply.writeNoException();
              return true;
            case 77:
              int _arg062 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result52 = isSystemAndLockPaired(_arg062);
              reply.writeNoException();
              reply.writeBoolean(_result52);
              return true;
            case 78:
              int _arg063 = data.readInt();
              data.enforceNoDataAvail();
              int _result53 = getHighlightFilterState(_arg063);
              reply.writeNoException();
              reply.writeInt(_result53);
              return true;
            case 79:
              int _arg064 = data.readInt();
              int _arg133 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result54 = getWallpaperComponentExtras(_arg064, _arg133);
              reply.writeNoException();
              reply.writeTypedObject(_result54, 1);
              return true;
            case 80:
              int _arg065 = data.readInt();
              int _arg134 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result55 = getWallpaperExtras(_arg065, _arg134);
              reply.writeNoException();
              reply.writeTypedObject(_result55, 1);
              return true;
            case 81:
              int _arg066 = data.readInt();
              int _arg135 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result56 = getWallpaperAssets(_arg066, _arg135);
              reply.writeNoException();
              reply.writeTypedObject(_result56, 1);
              return true;
            case 82:
              String _arg067 = data.readString();
              int _arg136 = data.readInt();
              int _arg221 = data.readInt();
              String _arg313 = data.readString();
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result57 =
                  getWallpaperAssetFile(_arg067, _arg136, _arg221, _arg313);
              reply.writeNoException();
              reply.writeTypedObject(_result57, 1);
              return true;
            case 83:
              int _arg068 = data.readInt();
              int _arg137 = data.readInt();
              data.enforceNoDataAvail();
              int _result58 = getWallpaperOrientation(_arg068, _arg137);
              reply.writeNoException();
              reply.writeInt(_result58);
              return true;
            case 84:
              int _arg069 = data.readInt();
              String _arg138 = data.readString();
              Bundle _arg222 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              semSendWallpaperCommand(_arg069, _arg138, _arg222);
              reply.writeNoException();
              return true;
            case 85:
              int _arg070 = data.readInt();
              data.enforceNoDataAvail();
              SemWallpaperColors _result59 = semGetWallpaperColors(_arg070);
              reply.writeNoException();
              reply.writeTypedObject(_result59, 1);
              return true;
            case 86:
              int _arg071 = data.readInt();
              data.enforceNoDataAvail();
              SemWallpaperColors _result60 = semGetPrimaryWallpaperColors(_arg071);
              reply.writeNoException();
              reply.writeTypedObject(_result60, 1);
              return true;
            case 87:
              int _arg072 = data.readInt();
              int _arg139 = data.readInt();
              String _arg223 = data.readString();
              data.enforceNoDataAvail();
              semClearWallpaperThumbnailCache(_arg072, _arg139, _arg223);
              reply.writeNoException();
              return true;
            case 88:
              int _arg073 = data.readInt();
              String _arg140 = data.readString();
              data.enforceNoDataAvail();
              semRequestWallpaperColorsAnalysis(_arg073, _arg140);
              reply.writeNoException();
              return true;
            case 89:
              int _arg074 = data.readInt();
              int _arg141 = data.readInt();
              String _arg224 = data.readString();
              data.enforceNoDataAvail();
              semSetWallpaperColorOverrideAreas(_arg074, _arg141, _arg224);
              reply.writeNoException();
              return true;
            case 90:
              SemWallpaperColors _arg075 =
                  (SemWallpaperColors) data.readTypedObject(SemWallpaperColors.CREATOR);
              int _arg142 = data.readInt();
              data.enforceNoDataAvail();
              semSetDLSWallpaperColors(_arg075, _arg142);
              reply.writeNoException();
              return true;
            case 91:
              int _arg076 = data.readInt();
              Rect _arg143 = (Rect) data.readTypedObject(Rect.CREATOR);
              Rect _arg225 = (Rect) data.readTypedObject(Rect.CREATOR);
              data.enforceNoDataAvail();
              semSetSmartCropRect(_arg076, _arg143, _arg225);
              reply.writeNoException();
              return true;
            case 92:
              int _arg077 = data.readInt();
              data.enforceNoDataAvail();
              Rect _result61 = semGetSmartCropRect(_arg077);
              reply.writeNoException();
              reply.writeTypedObject(_result61, 1);
              return true;
            case 93:
              int _result62 = getLidState();
              reply.writeNoException();
              reply.writeInt(_result62);
              return true;
            case 94:
              int _arg078 = data.readInt();
              data.enforceNoDataAvail();
              int _result63 = getDisplayId(_arg078);
              reply.writeNoException();
              reply.writeInt(_result63);
              return true;
            case 95:
              int _arg079 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result64 = isVirtualWallpaperDisplay(_arg079);
              reply.writeNoException();
              reply.writeBoolean(_result64);
              return true;
            case 96:
              int _arg080 = data.readInt();
              int _arg144 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result65 = isWaitingForUnlockUser(_arg080, _arg144);
              reply.writeNoException();
              reply.writeBoolean(_result65);
              return true;
            case 97:
              String _arg081 = data.readString();
              boolean _arg145 = data.readBoolean();
              int _arg226 = data.readInt();
              int _arg314 = data.readInt();
              String _arg49 = data.readString();
              int _arg54 = data.readInt();
              Bundle _arg64 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              semSetUri(_arg081, _arg145, _arg226, _arg314, _arg49, _arg54, _arg64);
              reply.writeNoException();
              return true;
            case 98:
              int _arg082 = data.readInt();
              String _arg146 = data.readString();
              data.enforceNoDataAvail();
              String _result66 = semGetUri(_arg082, _arg146);
              reply.writeNoException();
              reply.writeString(_result66);
              return true;
            case 99:
              int _arg083 = data.readInt();
              int _arg147 = data.readInt();
              data.enforceNoDataAvail();
              forceRebindWallpaper(_arg083, _arg147);
              reply.writeNoException();
              return true;
            case 100:
              int _arg084 = data.readInt();
              int _arg148 = data.readInt();
              String _arg227 = data.readString();
              boolean _arg315 = data.readBoolean();
              data.enforceNoDataAvail();
              notifyPid(_arg084, _arg148, _arg227, _arg315);
              reply.writeNoException();
              return true;
            case 101:
              int _arg085 = data.readInt();
              int _arg149 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result67 = isWallpaperDataExists(_arg085, _arg149);
              reply.writeNoException();
              reply.writeBoolean(_result67);
              return true;
            case 102:
              int _arg086 = data.readInt();
              data.enforceNoDataAvail();
              notifyAodVisibilityState(_arg086);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IWallpaperManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
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
          Rect cropHint,
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
          try {
            _data.writeString(name);
          } catch (Throwable th) {
            th = th;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
        }
        try {
          _data.writeString(callingPackage);
          try {
            _data.writeTypedObject(cropHint, 0);
            try {
              _data.writeBoolean(allowBackup);
              try {
                _data.writeInt(which);
              } catch (Throwable th3) {
                th = th3;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th4) {
              th = th4;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeStrongInterface(completion);
              try {
                _data.writeInt(userId);
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
                try {
                  this.mRemote.transact(1, _data, _reply, 0);
                  _reply.readException();
                  ParcelFileDescriptor _result =
                      (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                  if (_reply.readInt() != 0) {
                    try {
                      extras.readFromParcel(_reply);
                    } catch (Throwable th8) {
                      th = th8;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  }
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th9) {
                  th = th9;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th10) {
                th = th10;
              }
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
          String callingPkg, IWallpaperManagerCallback cb, int which, Bundle outParams, int userId)
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
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
      public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          this.mRemote.transact(7, _data, _reply, 0);
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
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          WallpaperInfo _result = (WallpaperInfo) _reply.readTypedObject(WallpaperInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public WallpaperInfo getWallpaperInfoWithFlags(int which, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          WallpaperInfo _result = (WallpaperInfo) _reply.readTypedObject(WallpaperInfo.CREATOR);
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
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
          this.mRemote.transact(11, _data, _reply, 0);
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
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void setDimensionHints(int width, int height, String callingPackage, int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(width);
          _data.writeInt(height);
          _data.writeString(callingPackage);
          _data.writeInt(displayId);
          this.mRemote.transact(13, _data, _reply, 0);
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
          this.mRemote.transact(14, _data, _reply, 0);
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
          this.mRemote.transact(15, _data, _reply, 0);
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
          this.mRemote.transact(16, _data, _reply, 0);
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
          this.mRemote.transact(17, _data, _reply, 0);
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
          this.mRemote.transact(18, _data, _reply, 0);
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
          this.mRemote.transact(19, _data, _reply, 0);
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
          this.mRemote.transact(20, _data, _reply, 0);
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
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
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
      public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
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
      public boolean setCoverWallpaperCallback(IWallpaperManagerCallback cb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
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
      public WallpaperColors getWallpaperColors(int which, int userId, int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          _data.writeInt(displayId);
          this.mRemote.transact(25, _data, _reply, 0);
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
          this.mRemote.transact(26, _data, _reply, 0);
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
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void registerWallpaperColorsCallback(
          IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          _data.writeInt(userId);
          _data.writeInt(displayId);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void unregisterWallpaperColorsCallback(
          IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          _data.writeInt(userId);
          _data.writeInt(displayId);
          this.mRemote.transact(29, _data, _reply, 0);
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
          this.mRemote.transact(30, _data, null, 1);
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
          this.mRemote.transact(31, _data, null, 1);
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
          this.mRemote.transact(32, _data, _reply, 0);
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
          this.mRemote.transact(33, _data, null, 1);
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
          this.mRemote.transact(34, _data, _reply, 0);
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
          this.mRemote.transact(35, _data, _reply, 0);
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
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public boolean isLockscreenLiveWallpaperEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public boolean isMultiCropEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(38, _data, _reply, 0);
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
      public int getDesktopMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(40, _data, _reply, 0);
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
      public boolean isDesktopStandAloneMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(42, _data, _reply, 0);
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
          this.mRemote.transact(43, _data, _reply, 0);
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
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public ComponentName semGetWallpaperComponent(int which, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
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
          this.mRemote.transact(46, _data, _reply, 0);
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
          this.mRemote.transact(47, _data, _reply, 0);
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
          this.mRemote.transact(48, _data, _reply, 0);
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
          this.mRemote.transact(49, _data, _reply, 0);
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
      public ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(
          int type, int userId, int which, int orientation, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeInt(userId);
          _data.writeInt(which);
          _data.writeInt(orientation);
          _data.writeInt(mode);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
          this.mRemote.transact(52, _data, _reply, 0);
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
          this.mRemote.transact(53, _data, _reply, 0);
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
          this.mRemote.transact(54, _data, _reply, 0);
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
          this.mRemote.transact(55, _data, _reply, 0);
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
          this.mRemote.transact(57, _data, _reply, 0);
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
          this.mRemote.transact(58, _data, _reply, 0);
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
          this.mRemote.transact(59, _data, _reply, 0);
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
          this.mRemote.transact(60, _data, _reply, 0);
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
          this.mRemote.transact(61, _data, _reply, 0);
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
          this.mRemote.transact(62, _data, _reply, 0);
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
          this.mRemote.transact(63, _data, _reply, 0);
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
          this.mRemote.transact(64, _data, _reply, 0);
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
      public boolean setSnapshotSource(int key, String source) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(key);
          _data.writeString(source);
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
      public boolean isValidSnapshot(int key) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(key);
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
      public int[] getSnapshotKeys(String source, int which) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(source);
          _data.writeInt(which);
          this.mRemote.transact(68, _data, _reply, 0);
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
          this.mRemote.transact(69, _data, _reply, 0);
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
          this.mRemote.transact(70, _data, _reply, 0);
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
          this.mRemote.transact(71, _data, _reply, 0);
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
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void setKWPTypeLiveWallpaper(int value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(value);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void setKWPTypeLiveWallpaperWithMode(int mode, int value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mode);
          _data.writeInt(value);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void copyFileToWallpaperFile(int which, String callingPackage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeString(callingPackage);
          this.mRemote.transact(75, _data, _reply, 0);
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
          this.mRemote.transact(76, _data, _reply, 0);
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
          this.mRemote.transact(77, _data, _reply, 0);
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
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public Bundle getWallpaperComponentExtras(int which, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          this.mRemote.transact(79, _data, _reply, 0);
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
          this.mRemote.transact(80, _data, _reply, 0);
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
          this.mRemote.transact(81, _data, _reply, 0);
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
          String callingPkg, int which, int userId, String assetFilePath) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeInt(which);
          _data.writeInt(userId);
          _data.writeString(assetFilePath);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
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
          this.mRemote.transact(83, _data, _reply, 0);
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
          this.mRemote.transact(84, _data, _reply, 0);
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
          this.mRemote.transact(85, _data, _reply, 0);
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
      public SemWallpaperColors semGetPrimaryWallpaperColors(int which) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          this.mRemote.transact(86, _data, _reply, 0);
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
      public void semClearWallpaperThumbnailCache(int which, int userId, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          _data.writeString(callingPackage);
          this.mRemote.transact(87, _data, _reply, 0);
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
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IWallpaperManager
      public void semSetWallpaperColorOverrideAreas(int which, int userId, String colorAreas)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(which);
          _data.writeInt(userId);
          _data.writeString(colorAreas);
          this.mRemote.transact(89, _data, _reply, 0);
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
          this.mRemote.transact(90, _data, _reply, 0);
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
          this.mRemote.transact(91, _data, _reply, 0);
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
          this.mRemote.transact(92, _data, _reply, 0);
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
          this.mRemote.transact(93, _data, _reply, 0);
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
          this.mRemote.transact(94, _data, _reply, 0);
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
          this.mRemote.transact(95, _data, _reply, 0);
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
          this.mRemote.transact(96, _data, _reply, 0);
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
          this.mRemote.transact(97, _data, _reply, 0);
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
          this.mRemote.transact(98, _data, _reply, 0);
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
          this.mRemote.transact(99, _data, _reply, 0);
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
          this.mRemote.transact(100, _data, _reply, 0);
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
          this.mRemote.transact(101, _data, _reply, 0);
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
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 101;
    }
  }
}
