package com.android.server.wallpaper;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.ILocalWallpaperColorConsumer;
import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.app.SemWallpaperResourcesInfo;
import android.app.UserSwitchObserver;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManagerInternal;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.service.wallpaper.IWallpaperConnection;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wm.ActivityManagerPerformance;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WallpaperExtraBundleHelper;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.AssetFileManager;
import com.samsung.server.wallpaper.LegibilityColor;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper;
import com.samsung.server.wallpaper.SemWallpaperData;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import com.samsung.server.wallpaper.snapshot.SnapshotCallback;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class WallpaperManagerService extends IWallpaperManager.Stub
    implements IWallpaperManagerService, SnapshotCallback {
  public static final RectF LOCAL_COLOR_BOUNDS =
      new RectF(
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          1.0f,
          1.0f);
  public static final boolean SHIPPED = !Debug.semIsProductDev();
  public static final Map sWallpaperType = Map.of(5, "decode_record", 6, "decode_lock_record");
  public int mActiveVirtualDisplayId;
  public final ActivityManager mActivityManager;
  public final AppOpsManager mAppOpsManager;
  public AssetFileManager mAssetFileManager;
  public WallpaperColors mCacheDefaultImageWallpaperColors;
  public final SparseArray mColorsChangedListeners;
  public final Context mContext;
  public ArrayList mCoverWallpaperListenerClientList;
  public ArrayList mCoverWallpaperListenerList;
  public int mCurrentUserId;
  public final ComponentName mDefaultWallpaperComponent;
  public final DisplayManager.DisplayListener mDisplayListener;
  public WallpaperData mFallbackWallpaper;
  public boolean mHomeWallpaperWaitingForUnlock;
  public final IPackageManager mIPackageManager;
  public final ComponentName mImageWallpaper;
  public boolean mInAmbientMode;
  public final boolean mIsLockscreenLiveWallpaperEnabled;
  public final boolean mIsMultiCropEnabled;
  public final boolean mIsPreviewLockLiveWallpaperEnabled;
  public SparseArray mIsWallpaperInitialized;
  public ArrayList mKeyguardListenerClientList;
  public ArrayList mKeyguardListenerList;
  public WallpaperData mLastDexWallpaper;
  public WallpaperData mLastLockWallpaper;
  public WallpaperData mLastVirtualWallpaper;
  public WallpaperData mLastWallpaper;
  public WallpaperData mLastWatchFace;
  public PreloadedLiveWallpaperHelper mLiveWallpaperHelper;
  public LocalColorRepository mLocalColorRepo;
  public final Object mLock;
  public WallpaperDataManager mLockWallpaperMap;
  public boolean mLockWallpaperWaitingForUnlock;
  public final MyPackageMonitor mMonitor;
  public final PackageManagerInternal mPackageManagerInternal;
  public WallpaperDestinationChangeHandler mPendingMigrationViaStatic;
  public SemWallpaperManagerService mSemService;
  public SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
  public boolean mShuttingDown;
  public final IStatusBarService mStatusBarService;
  public final SparseBooleanArray mUserRestorecon;
  public final WallpaperCropper mWallpaperCropper;
  final WallpaperDataParser mWallpaperDataParser;
  final WallpaperDisplayHelper mWallpaperDisplayHelper;
  public final HandlerThread mWallpaperHandlerThread;
  public final Handler mWallpaperHanlder;
  public WallpaperDataManager mWallpaperMap;
  public final WindowManagerInternal mWindowManagerInternal;
  public WallpaperObserver mWallpaperObserver = null;
  public boolean mIsInitialLoadSucceed = true;

  public class Lifecycle extends SystemService {
    public IWallpaperManagerService mService;

    public Lifecycle(Context context) {
      super(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
      try {
        IWallpaperManagerService iWallpaperManagerService =
            (IWallpaperManagerService)
                Class.forName(
                        getContext()
                            .getResources()
                            .getString(R.string.ext_media_unsupported_notification_message))
                    .getConstructor(Context.class)
                    .newInstance(getContext());
        this.mService = iWallpaperManagerService;
        publishBinderService("wallpaper", iWallpaperManagerService);
      } catch (Exception e) {
        Slog.wtf("WallpaperManagerService", "Failed to instantiate WallpaperManagerService", e);
      }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
      IWallpaperManagerService iWallpaperManagerService = this.mService;
      if (iWallpaperManagerService != null) {
        iWallpaperManagerService.onBootPhase(i);
      }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
      IWallpaperManagerService iWallpaperManagerService = this.mService;
      if (iWallpaperManagerService != null) {
        iWallpaperManagerService.onUnlockUser(targetUser.getUserIdentifier());
      }
    }
  }

  public class WallpaperObserver {
    public final SemWallpaperManagerService.SemWallpaperObserver mSemObserver;
    public final int mUserId;
    public final WallpaperData mWallpaper;
    public final File mWallpaperDir;
    public final File mWallpaperFile;
    public final File mWallpaperLockFile;

    public WallpaperObserver(WallpaperData wallpaperData) {
      int i = wallpaperData.userId;
      this.mUserId = i;
      File wallpaperDir = WallpaperUtils.getWallpaperDir(i);
      this.mWallpaperDir = wallpaperDir;
      this.mWallpaper = wallpaperData;
      this.mWallpaperFile = new File(wallpaperDir, "wallpaper_orig");
      this.mWallpaperLockFile = new File(wallpaperDir, "wallpaper_lock_orig");
      this.mSemObserver =
          new SemWallpaperManagerService.SemWallpaperObserver(
              wallpaperDir,
              WallpaperUtils.getWallpaperLockDir(wallpaperData.userId),
              WallpaperManagerService.this.new SemCallback());
    }

    public WallpaperData dataForEvent(boolean z, boolean z2, int i) {
      WallpaperData wallpaperData;
      synchronized (WallpaperManagerService.this.mLock) {
        if (z2) {
          try {
            wallpaperData = WallpaperManagerService.this.mLockWallpaperMap.get(this.mUserId, i);
          } catch (Throwable th) {
            throw th;
          }
        } else {
          wallpaperData = null;
        }
        if (wallpaperData == null) {
          wallpaperData = WallpaperManagerService.this.mWallpaperMap.get(this.mUserId, i);
        }
      }
      return wallpaperData != null ? wallpaperData : this.mWallpaper;
    }

    public final void updateWallpapers(
        int i, String str, File file, boolean z, boolean z2, int i2) {
      final WallpaperData dataForEvent = dataForEvent(z, z2, i2);
      int i3 = 0;
      boolean z3 = i == 128;
      boolean z4 = i == 8 || z3;
      boolean z5 = z3 && z2;
      boolean z6 = z3 && !z5;
      boolean z7 = (dataForEvent.mWhich & 2) != 0;
      boolean z8 =
          dataForEvent.wallpaperComponent == null || i != 8 || dataForEvent.imageWallpaperPending;
      if (z5) {
        return;
      }
      if (z || z2) {
        synchronized (WallpaperManagerService.this.mLock) {
          WallpaperManagerService.this.notifyCallbacksLocked(dataForEvent);
          if (z4 && z8) {
            if (dataForEvent.primaryColors != null) {
              dataForEvent.primaryColors = null;
            }
            if (dataForEvent.mSemWallpaperData.getPrimarySemColors() != null) {
              dataForEvent.mSemWallpaperData.setPrimarySemColors(null);
            }
            if (Rune.SUPPORT_SUB_DISPLAY_MODE
                && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                && z
                && WhichChecker.isWatchFaceDisplay(dataForEvent.mWhich)) {
              WallpaperManagerService.this.notifyCoverWallpaperChanged(
                  dataForEvent.mSemWallpaperData.getWpType(), dataForEvent.mWhich);
            }
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            final WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler =
                wallpaperManagerService.mPendingMigrationViaStatic;
            wallpaperManagerService.mPendingMigrationViaStatic = null;
            SELinux.restorecon(file);
            if (z6) {
              WallpaperManagerService.this.loadSettingsLocked(dataForEvent.userId, true, 3, i2);
            }
            WallpaperManagerService.this.mWallpaperCropper.generateCrop(dataForEvent);
            dataForEvent.imageWallpaperPending = false;
            WallpaperManagerService.this.mSemService.generateResizedBitmap(
                dataForEvent.cropFile, dataForEvent.mSemWallpaperData);
            if (z) {
              IRemoteCallback.Stub stub =
                  new IRemoteCallback
                      .Stub() { // from class:
                                // com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.1
                    public void sendResult(Bundle bundle) {
                      WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler2 =
                          wallpaperDestinationChangeHandler;
                      if (wallpaperDestinationChangeHandler2 != null) {
                        wallpaperDestinationChangeHandler2.complete();
                      }
                      WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                    }
                  };
              if (WallpaperManagerService.this.canBindComponentNow(dataForEvent.mWhich | i2)) {
                WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                wallpaperManagerService2.bindWallpaperComponentLocked(
                    wallpaperManagerService2.mImageWallpaper, true, false, dataForEvent, stub);
              } else if (Rune.SUPPORT_SUB_DISPLAY_MODE
                  && WhichChecker.isSystem(dataForEvent.mWhich)) {
                sendRefreshCacheCommandLocked(dataForEvent, i2 | 1);
              }
              i3 = 0 | i2 | 1;
            }
            if (z2) {
              IRemoteCallback.Stub stub2 =
                  new IRemoteCallback
                      .Stub() { // from class:
                                // com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.2
                    public void sendResult(Bundle bundle) {
                      WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler2 =
                          wallpaperDestinationChangeHandler;
                      if (wallpaperDestinationChangeHandler2 != null) {
                        wallpaperDestinationChangeHandler2.complete();
                      }
                      WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                    }
                  };
              if (WallpaperManagerService.this.canBindComponentNow(dataForEvent.mWhich | i2)) {
                WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
                wallpaperManagerService3.bindWallpaperComponentLocked(
                    wallpaperManagerService3.mImageWallpaper, true, false, dataForEvent, stub2);
              } else if (Rune.SUPPORT_SUB_DISPLAY_MODE
                  && WhichChecker.isLock(dataForEvent.mWhich)) {
                sendRefreshCacheCommandLocked(dataForEvent, i2 | 2);
              }
            } else if (z7) {
              WallpaperManagerService wallpaperManagerService4 = WallpaperManagerService.this;
              WallpaperData wallpaperData = this.mWallpaper;
              WallpaperData peekWallpaperDataLocked =
                  wallpaperManagerService4.peekWallpaperDataLocked(
                      wallpaperData.userId, wallpaperData.mWhich);
              if (peekWallpaperDataLocked != null) {
                WallpaperManagerService.this.detachWallpaperLocked(peekWallpaperDataLocked);
              }
              WallpaperManagerService.this.mLockWallpaperMap.remove(dataForEvent.userId, i2);
              if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                sendRefreshCacheCommandLocked(dataForEvent, i2 | 2);
              }
              i3 |= 2;
            }
            WallpaperManagerService.this.saveSettingsLocked(dataForEvent.userId);
            if (z2 && !z) {
              WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
            }
            if (i3 != 0) {
              WallpaperManagerService.this.notifyWallpaperColorsChanged(dataForEvent, i3);
            }
          }
        }
      }
    }

    public final void sendRefreshCacheCommandLocked(WallpaperData wallpaperData, int i) {
      Log.m129d(
          "WallpaperManagerService",
          "sendRefreshCacheCommand: send command to update cached wallpaper, which = " + i);
      Bundle bundle = new Bundle();
      bundle.putInt("which", i);
      WallpaperManagerService.this.semSendWallpaperCommand(i, "refresh_cache", bundle);
      WallpaperManagerService.this.notifyWallpaperChanged(wallpaperData);
    }

    public final void updateEvent(int i, String str, File file, boolean z, boolean z2) {
      int i2;
      if (file != null && file.getName().contains("wallpaper_desktop")) {
        i2 = 8;
      } else if (file == null || !file.getName().contains("wallpaper_sub_display")) {
        i2 = (file == null || !file.getName().contains("wallpaper_virtual_display")) ? 4 : 32;
      } else {
        i2 = 16;
      }
      int i3 = i2;
      if (WallpaperManagerService.this.mIsLockscreenLiveWallpaperEnabled) {
        updateWallpapers(i, str, file, z, z2, i3);
      } else {
        updateWallpapersLegacy(i, str, file, z, z2, i3);
      }
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x01ee, code lost:

       if (r2 == false) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01f6, code lost:

       if (com.samsung.android.wallpaper.utils.WhichChecker.isSystem(r15.mWhich) == false) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01f8, code lost:

       r11.mSemWallpaperData.setIsCopied(false);
    */
    /* JADX WARN: Removed duplicated region for block: B:71:0x020f A[Catch: all -> 0x028d, TryCatch #0 {, blocks: (B:26:0x027f, B:36:0x00c0, B:38:0x00ec, B:39:0x00ee, B:43:0x0154, B:45:0x015d, B:46:0x0166, B:49:0x016c, B:53:0x0174, B:57:0x01a1, B:59:0x01ad, B:61:0x01b2, B:62:0x01bb, B:64:0x01db, B:69:0x01ff, B:71:0x020f, B:72:0x0236, B:74:0x0241, B:76:0x026d, B:79:0x0278, B:81:0x0249, B:82:0x0254, B:84:0x025c, B:85:0x026a, B:86:0x0222, B:88:0x0229, B:90:0x0231, B:93:0x01f0, B:95:0x01f8, B:99:0x00f9, B:103:0x0105, B:104:0x010f, B:106:0x0118, B:108:0x011c, B:110:0x0124, B:111:0x0132, B:113:0x013b, B:115:0x013f, B:117:0x0147), top: B:35:0x00c0 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0222 A[Catch: all -> 0x028d, TryCatch #0 {, blocks: (B:26:0x027f, B:36:0x00c0, B:38:0x00ec, B:39:0x00ee, B:43:0x0154, B:45:0x015d, B:46:0x0166, B:49:0x016c, B:53:0x0174, B:57:0x01a1, B:59:0x01ad, B:61:0x01b2, B:62:0x01bb, B:64:0x01db, B:69:0x01ff, B:71:0x020f, B:72:0x0236, B:74:0x0241, B:76:0x026d, B:79:0x0278, B:81:0x0249, B:82:0x0254, B:84:0x025c, B:85:0x026a, B:86:0x0222, B:88:0x0229, B:90:0x0231, B:93:0x01f0, B:95:0x01f8, B:99:0x00f9, B:103:0x0105, B:104:0x010f, B:106:0x0118, B:108:0x011c, B:110:0x0124, B:111:0x0132, B:113:0x013b, B:115:0x013f, B:117:0x0147), top: B:35:0x00c0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWallpapersLegacy(
        int i, String str, File file, boolean z, boolean z2, int i2) {
      int i3;
      SemWallpaperColors semWallpaperColors;
      WallpaperData wallpaperData;
      int i4;
      int i5;
      boolean z3 = i == 128;
      boolean z4 = i == 8 || z3;
      final WallpaperData dataForEvent = dataForEvent(z, z2, i2);
      if (str != null && str.contains("wallpaper")) {
        Slog.v(
            "WallpaperManagerService",
            "Wallpaper file change: evt="
                + i
                + " path="
                + str
                + " sys="
                + z
                + " lock="
                + z2
                + " imagePending="
                + dataForEvent.imageWallpaperPending
                + " mWhich=0x"
                + Integer.toHexString(dataForEvent.mWhich)
                + " written="
                + z4
                + " mode="
                + i2
                + " primaryColors:"
                + dataForEvent.primaryColors
                + " wallpaper = "
                + dataForEvent);
      }
      if (z3 && z2) {
        SELinux.restorecon(file);
        int i6 = 2 | i2;
        WallpaperManagerService.this.notifyLockWallpaperChanged(
            dataForEvent.mSemWallpaperData.getWpType(), i6);
        WallpaperManagerService.this.notifyWallpaperColorsChanged(dataForEvent, i6);
        return;
      }
      synchronized (WallpaperManagerService.this.mLock) {
        if (z || z2) {
          int i7 = i2 | 2;
          WallpaperData peekWallpaperDataLocked =
              WallpaperManagerService.this.peekWallpaperDataLocked(dataForEvent.userId, i7);
          boolean isSystemAndLockPaired =
              WallpaperManagerService.this.isSystemAndLockPaired(dataForEvent.userId, i2);
          Slog.i("WallpaperManagerService", "isHomeAndLockPaired = " + isSystemAndLockPaired);
          if (dataForEvent.primaryColors != null) {
            dataForEvent.primaryColors = null;
          }
          if (!WhichChecker.isSystemAndLock(dataForEvent.mWhich) || !z2) {
            if (WhichChecker.isSystemAndLock(dataForEvent.mWhich) && z) {
              if (peekWallpaperDataLocked != null) {
                peekWallpaperDataLocked.mSemWallpaperData.setPrimarySemColors(null);
                WallpaperManagerService.this.notifyWallpaperColorsChanged(
                    peekWallpaperDataLocked, i7);
              }
              WallpaperManagerService.this.notifyCallbacksLocked(dataForEvent);
              if (Rune.SUPPORT_SUB_DISPLAY_MODE
                  && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                  && WhichChecker.isWatchFaceDisplay(dataForEvent.mWhich)) {
                WallpaperManagerService.this.notifyCoverWallpaperChanged(
                    dataForEvent.mSemWallpaperData.getWpType(), dataForEvent.mWhich);
              }
            } else {
              WallpaperManagerService.this.notifyCallbacksLocked(dataForEvent);
              if (Rune.SUPPORT_SUB_DISPLAY_MODE
                  && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                  && WhichChecker.isWatchFaceDisplay(dataForEvent.mWhich)) {
                WallpaperManagerService.this.notifyCoverWallpaperChanged(
                    dataForEvent.mSemWallpaperData.getWpType(), dataForEvent.mWhich);
              }
            }
          }
          if (dataForEvent.mSemWallpaperData.getWpType() != 5) {
            WallpaperManagerService.this.resetSemWallpaperData(
                dataForEvent.mSemWallpaperData, dataForEvent.userId);
          }
          if (dataForEvent.wallpaperComponent == null
              || i != 8
              || dataForEvent.imageWallpaperPending
              || (z && i == 8)) {
            Log.m132i(
                "WallpaperManagerService",
                "wallpaper.imageWallpaperPending = " + dataForEvent.imageWallpaperPending);
            StringBuilder sb = new StringBuilder();
            sb.append("needToGenerateSysCropWallpaper = ");
            sb.append(z && i == 8);
            Log.m132i("WallpaperManagerService", sb.toString());
            if (z4) {
              SELinux.restorecon(file);
              if (z3) {
                WallpaperManagerService.this.loadSettingsLocked(dataForEvent.userId, true, 3, i2);
              }
              WallpaperManagerService.this.mWallpaperCropper.generateCrop(dataForEvent);
              Slog.v("WallpaperManagerService", "Crop done; invoking completion callback");
              WallpaperManagerService.this.mSemService.generateResizedBitmap(
                  dataForEvent.cropFile, dataForEvent.mSemWallpaperData);
              dataForEvent.imageWallpaperPending = false;
              if (z) {
                if (WallpaperManagerService.this.mSemService.hasLockscreenWallpaper(false)
                    && !isSystemAndLockPaired) {
                  i5 = 0;
                  IRemoteCallback.Stub stub =
                      new IRemoteCallback
                          .Stub() { // from class:
                                    // com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.3
                        public void sendResult(Bundle bundle) {
                          Slog.d("WallpaperManagerService", "publish system wallpaper changed!");
                          WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                        }
                      };
                  if (!WallpaperManagerService.this.canBindComponentNow(dataForEvent.mWhich | i2)) {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    i3 = i7;
                    semWallpaperColors = null;
                    wallpaperData = dataForEvent;
                    wallpaperManagerService.bindWallpaperComponentLocked(
                        wallpaperManagerService.mImageWallpaper, true, false, dataForEvent, stub);
                  } else {
                    i3 = i7;
                    semWallpaperColors = null;
                    wallpaperData = dataForEvent;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE
                        && WhichChecker.isSystem(wallpaperData.mWhich)) {
                      sendRefreshCacheCommandLocked(wallpaperData, i2 | 1);
                    }
                  }
                  i4 = i5 | i2 | 1;
                }
                i5 = 2;
                IRemoteCallback.Stub stub2 =
                    new IRemoteCallback
                        .Stub() { // from class:
                                  // com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.3
                      public void sendResult(Bundle bundle) {
                        Slog.d("WallpaperManagerService", "publish system wallpaper changed!");
                        WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                      }
                    };
                if (!WallpaperManagerService.this.canBindComponentNow(dataForEvent.mWhich | i2)) {}
                i4 = i5 | i2 | 1;
              } else {
                i3 = i7;
                semWallpaperColors = null;
                wallpaperData = dataForEvent;
                i4 = 0;
              }
              if (z2 || (wallpaperData.mWhich & 2) != 0) {
                if (!z2) {
                  WallpaperManagerService.this.mLockWallpaperMap.remove(wallpaperData.userId, i2);
                }
                if (wallpaperData.mSemWallpaperData.getWpType() == 0) {
                  WallpaperManagerService.this.notifyLockWallpaperChanged(
                      wallpaperData.mSemWallpaperData.getWpType(), i3);
                  wallpaperData.cleanUp();
                }
                i4 |= i3;
              }
              WallpaperManagerService.this.saveSettingsLocked(wallpaperData.userId, i2);
              if (z2 && !z) {
                WallpaperManagerService.this.notifyWallpaperChanged(wallpaperData);
              }
            }
          }
        }
        semWallpaperColors = null;
        wallpaperData = dataForEvent;
        i4 = 0;
      }
      if (i4 != 0) {
        wallpaperData.mSemWallpaperData.setPrimarySemColors(semWallpaperColors);
        WallpaperManagerService.this.notifyWallpaperColorsChanged(wallpaperData, i4);
      }
    }
  }

  public final void notifyWallpaperChanged(WallpaperData wallpaperData) {
    IWallpaperManagerCallback iWallpaperManagerCallback = wallpaperData.setComplete;
    if (iWallpaperManagerCallback != null) {
      try {
        iWallpaperManagerCallback.onWallpaperChanged();
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "onWallpaperChanged threw an exception", e);
      }
    }
  }

  public void notifyLockWallpaperChanged(int i, int i2) {
    notifyLockWallpaperChanged(i, i2, null);
  }

  public void notifyLockWallpaperChanged(int i, int i2, Bundle bundle) {
    Slog.d("WallpaperManagerService", "notifyLockWallpaperChanged type = " + i);
    synchronized (this.mLock) {
      Iterator it = this.mKeyguardListenerList.iterator();
      while (it.hasNext()) {
        IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
        if (iWallpaperManagerCallback != null) {
          try {
            Slog.d(
                "WallpaperManagerService",
                "notifyLockWallpaperChanged cb=" + iWallpaperManagerCallback);
            iWallpaperManagerCallback.onWallpaperChanged();
            iWallpaperManagerCallback.onSemWallpaperChanged(i, i2, bundle);
          } catch (RemoteException e) {
            Log.m129d("WallpaperManagerService", "onWallpaperChanged() fail. : " + e);
          }
        }
      }
    }
  }

  public void notifyMultipackApplied(int i) {
    Slog.d("WallpaperManagerService", "notifyMultipackApplied: which = " + i);
    synchronized (this.mLock) {
      Iterator it = this.mKeyguardListenerList.iterator();
      while (it.hasNext()) {
        IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
        if (iWallpaperManagerCallback != null) {
          try {
            Slog.d(
                "WallpaperManagerService",
                "notifyMultipackApplied: cb = " + iWallpaperManagerCallback);
            iWallpaperManagerCallback.onSemMultipackApplied(i);
          } catch (RemoteException e) {
            Log.m129d("WallpaperManagerService", "notifyMultipackApplied: fail. : " + e);
          }
        }
      }
    }
  }

  public void notifyCoverWallpaperChanged(int i, int i2) {
    Slog.d(
        "WallpaperManagerService", "notifyCoverWallpaperChanged: type = " + i + ", which = " + i2);
    synchronized (this.mLock) {
      Iterator it = this.mCoverWallpaperListenerList.iterator();
      while (it.hasNext()) {
        IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
        if (iWallpaperManagerCallback != null) {
          try {
            Slog.d(
                "WallpaperManagerService",
                "notifyCoverWallpaperChanged: cb = " + iWallpaperManagerCallback);
            iWallpaperManagerCallback.onSemWallpaperChanged(i, i2, (Bundle) null);
          } catch (RemoteException e) {
            Log.m129d("WallpaperManagerService", "notifyCoverWallpaperChanged: fail. : " + e);
          }
        }
      }
    }
  }

  public void notifyWallpaperColorsChanged(final WallpaperData wallpaperData, final int i) {
    WallpaperConnection wallpaperConnection = wallpaperData.connection;
    if (wallpaperConnection != null) {
      wallpaperConnection.forEachDisplayConnector(
          new Consumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WallpaperManagerService.this.lambda$notifyWallpaperColorsChanged$0(
                  wallpaperData, i, (WallpaperManagerService.DisplayConnector) obj);
            }
          });
    } else {
      notifyWallpaperColorsChangedOnDisplay(wallpaperData, i, 0);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyWallpaperColorsChanged$0(
      WallpaperData wallpaperData, int i, DisplayConnector displayConnector) {
    notifyWallpaperColorsChangedOnDisplay(wallpaperData, i, displayConnector.mDisplayId);
  }

  public final RemoteCallbackList getWallpaperCallbacks(int i, int i2) {
    SparseArray sparseArray = (SparseArray) this.mColorsChangedListeners.get(i);
    if (sparseArray != null) {
      return (RemoteCallbackList) sparseArray.get(i2);
    }
    return null;
  }

  /* JADX WARN: Removed duplicated region for block: B:31:0x006e  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void notifyWallpaperColorsChangedOnDisplay(
      WallpaperData wallpaperData, int i, int i2) {
    boolean z;
    synchronized (this.mLock) {
      RemoteCallbackList wallpaperCallbacks = getWallpaperCallbacks(wallpaperData.userId, i2);
      RemoteCallbackList wallpaperCallbacks2 = getWallpaperCallbacks(-1, i2);
      if (emptyCallbackList(wallpaperCallbacks) && emptyCallbackList(wallpaperCallbacks2)) {
        if (this.mSemService.mDesktopMode.isDesktopDualMode()) {
          this.mSemService.mLegibilityColor.extractColor(
              wallpaperData.mSemWallpaperData.getWhich());
        }
        if ((!Rune.SUPPORT_SUB_DISPLAY_MODE || !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE)
            && !Rune.VIRTUAL_DISPLAY_WALLPAPER) {
          return;
        }
      }
      Slog.v("WallpaperManagerService", "notifyWallpaperColorsChangedOnDisplay " + i);
      if (wallpaperData.primaryColors != null
          && !wallpaperData.mIsColorExtractedFromDim
          && wallpaperData.mSemWallpaperData.getPrimarySemColors() != null) {
        z = false;
        if (z) {
          extractColors(wallpaperData);
        }
        notifyColorListeners(
            getAdjustedWallpaperColorsOnDimming(wallpaperData), i, wallpaperData.userId, i2);
      }
      z = true;
      if (z) {}
      notifyColorListeners(
          getAdjustedWallpaperColorsOnDimming(wallpaperData), i, wallpaperData.userId, i2);
    }
  }

  public static boolean emptyCallbackList(RemoteCallbackList remoteCallbackList) {
    return remoteCallbackList == null || remoteCallbackList.getRegisteredCallbackCount() == 0;
  }

  public final void notifyColorListeners(WallpaperColors wallpaperColors, int i, int i2, int i3) {
    ArrayList arrayList;
    int i4;
    ArrayList arrayList2 = new ArrayList();
    synchronized (this.mLock) {
      RemoteCallbackList wallpaperCallbacks = getWallpaperCallbacks(i2, i3);
      RemoteCallbackList wallpaperCallbacks2 = getWallpaperCallbacks(-1, i3);
      arrayList = this.mKeyguardListenerList;
      if (wallpaperCallbacks != null) {
        int beginBroadcast = wallpaperCallbacks.beginBroadcast();
        for (int i5 = 0; i5 < beginBroadcast; i5++) {
          arrayList2.add(wallpaperCallbacks.getBroadcastItem(i5));
        }
        wallpaperCallbacks.finishBroadcast();
      }
      if (wallpaperCallbacks2 != null) {
        int beginBroadcast2 = wallpaperCallbacks2.beginBroadcast();
        for (int i6 = 0; i6 < beginBroadcast2; i6++) {
          arrayList2.add(wallpaperCallbacks2.getBroadcastItem(i6));
        }
        wallpaperCallbacks2.finishBroadcast();
      }
    }
    int size = arrayList2.size();
    for (i4 = 0; i4 < size; i4++) {
      try {
        ((IWallpaperManagerCallback) arrayList2.get(i4))
            .onWallpaperColorsChanged(wallpaperColors, i, i2);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "onWallpaperColorsChanged() threw an exception", e);
      }
    }
    synchronized (this.mLock) {
      if (arrayList != null && i3 == 0) {
        try {
          Iterator it = arrayList.iterator();
          while (it.hasNext()) {
            IWallpaperManagerCallback iWallpaperManagerCallback =
                (IWallpaperManagerCallback) it.next();
            if (iWallpaperManagerCallback != null) {
              iWallpaperManagerCallback.onWallpaperColorsChanged(wallpaperColors, i, i2);
            }
          }
        } catch (RemoteException unused) {
        }
      }
    }
  }

  public final void extractColors(WallpaperData wallpaperData) {
    boolean z;
    boolean z2;
    WallpaperColors wallpaperColors;
    String str;
    int i;
    float f;
    File file;
    synchronized (this.mLock) {
      z = false;
      wallpaperData.mIsColorExtractedFromDim = false;
    }
    if (wallpaperData.equals(this.mFallbackWallpaper)) {
      synchronized (this.mLock) {
        if (this.mFallbackWallpaper.primaryColors != null) {
          return;
        }
        WallpaperColors extractDefaultImageWallpaperColors =
            extractDefaultImageWallpaperColors(wallpaperData);
        synchronized (this.mLock) {
          this.mFallbackWallpaper.primaryColors = extractDefaultImageWallpaperColors;
        }
        return;
      }
    }
    synchronized (this.mLock) {
      if (!this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)
          && wallpaperData.wallpaperComponent != null) {
        z2 = false;
        wallpaperColors = null;
        if (!z2 && (file = wallpaperData.cropFile) != null && file.exists()) {
          str = wallpaperData.cropFile.getAbsolutePath();
        } else {
          if (z2 && !wallpaperData.cropExists() && !wallpaperData.sourceExists()) {
            z = true;
          }
          str = null;
        }
        i = wallpaperData.wallpaperId;
        f = wallpaperData.mWallpaperDimAmount;
      }
      z2 = true;
      wallpaperColors = null;
      if (!z2) {}
      if (z2) {
        z = true;
      }
      str = null;
      i = wallpaperData.wallpaperId;
      f = wallpaperData.mWallpaperDimAmount;
    }
    if (str != null) {
      Bitmap decodeFile = BitmapFactory.decodeFile(str);
      if (decodeFile != null) {
        wallpaperColors = WallpaperColors.fromBitmap(decodeFile, f);
        decodeFile.recycle();
      }
    } else if (z) {
      wallpaperColors = extractDefaultImageWallpaperColors(wallpaperData);
    }
    if (str == null && !z) {
      Bitmap croppedBitmap = wallpaperData.mSemWallpaperData.getCroppedBitmap();
      if (croppedBitmap != null) {
        Log.m132i(
            "WallpaperManagerService",
            "extractColors: crop file is none so, get colors from cropped bitmap (CSC, OMC case)");
        wallpaperColors = WallpaperColors.fromBitmap(croppedBitmap);
      } else {
        this.mSemService.generateResizedBitmap(
            wallpaperData.cropFile, wallpaperData.mSemWallpaperData);
        if (wallpaperData.mSemWallpaperData.getCroppedBitmap() != null) {
          wallpaperColors =
              WallpaperColors.fromBitmap(wallpaperData.mSemWallpaperData.getCroppedBitmap());
        }
      }
    }
    this.mSemService.mLegibilityColor.extractColor(wallpaperData.mSemWallpaperData.getWhich());
    this.mSemService.mLegibilityColor.extractColor(
        wallpaperData.mSemWallpaperData.getWhich(), true);
    if (wallpaperColors == null) {
      Slog.w(
          "WallpaperManagerService", "Cannot extract colors because wallpaper could not be read.");
      return;
    }
    synchronized (this.mLock) {
      if (wallpaperData.wallpaperId == i) {
        wallpaperData.primaryColors = wallpaperColors;
        saveSettingsLocked(
            wallpaperData.userId, WhichChecker.getMode(wallpaperData.mSemWallpaperData.getWhich()));
      } else {
        Slog.w("WallpaperManagerService", "Not setting primary colors since wallpaper changed");
      }
    }
  }

  public final WallpaperColors extractDefaultImageWallpaperColors(WallpaperData wallpaperData) {
    InputStream openDefaultWallpaper;
    synchronized (this.mLock) {
      WallpaperColors wallpaperColors = this.mCacheDefaultImageWallpaperColors;
      if (wallpaperColors != null) {
        return wallpaperColors;
      }
      float f = wallpaperData.mWallpaperDimAmount;
      WallpaperColors wallpaperColors2 = null;
      try {
        openDefaultWallpaper = WallpaperManager.openDefaultWallpaper(this.mContext, 1);
        try {
        } finally {
        }
      } catch (IOException e) {
        Slog.w("WallpaperManagerService", "Can't close default wallpaper stream", e);
      } catch (OutOfMemoryError e2) {
        Slog.w("WallpaperManagerService", "Can't decode default wallpaper stream", e2);
      }
      if (openDefaultWallpaper == null) {
        Slog.w("WallpaperManagerService", "Can't open default wallpaper stream");
        if (openDefaultWallpaper != null) {
          openDefaultWallpaper.close();
        }
        return null;
      }
      Bitmap decodeStream =
          BitmapFactory.decodeStream(openDefaultWallpaper, null, new BitmapFactory.Options());
      if (decodeStream != null) {
        wallpaperColors2 = WallpaperColors.fromBitmap(decodeStream, f);
        decodeStream.recycle();
      }
      openDefaultWallpaper.close();
      if (wallpaperColors2 == null) {
        Slog.e("WallpaperManagerService", "Extract default image wallpaper colors failed");
      } else {
        synchronized (this.mLock) {
          this.mCacheDefaultImageWallpaperColors = wallpaperColors2;
        }
      }
      return wallpaperColors2;
    }
  }

  public final boolean supportsMultiDisplay(WallpaperConnection wallpaperConnection) {
    if (wallpaperConnection == null) {
      return false;
    }
    WallpaperInfo wallpaperInfo = wallpaperConnection.mInfo;
    return wallpaperInfo == null
        || wallpaperInfo.supportsMultipleDisplays()
        || Rune.SUPPORT_SUB_DISPLAY_MODE;
  }

  public final void updateFallbackConnection() {
    WallpaperData wallpaperData;
    WallpaperData wallpaperData2 = this.mLastWallpaper;
    if (wallpaperData2 == null || (wallpaperData = this.mFallbackWallpaper) == null) {
      return;
    }
    WallpaperConnection wallpaperConnection = wallpaperData2.connection;
    final WallpaperConnection wallpaperConnection2 = wallpaperData.connection;
    if (wallpaperConnection2 == null) {
      Slog.w("WallpaperManagerService", "Fallback wallpaper connection has not been created yet!!");
      return;
    }
    if (supportsMultiDisplay(wallpaperConnection)) {
      if (wallpaperConnection2.mDisplayConnector.size() != 0) {
        wallpaperConnection2.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda18
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$updateFallbackConnection$1(
                    WallpaperManagerService.WallpaperConnection.this,
                    (WallpaperManagerService.DisplayConnector) obj);
              }
            });
        wallpaperConnection2.mDisplayConnector.clear();
        return;
      }
      return;
    }
    wallpaperConnection2.appendConnectorWithCondition(
        new Predicate() { // from class:
                          // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda19
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$updateFallbackConnection$2;
            lambda$updateFallbackConnection$2 =
                WallpaperManagerService.this.lambda$updateFallbackConnection$2(
                    wallpaperConnection2, (Display) obj);
            return lambda$updateFallbackConnection$2;
          }
        });
    wallpaperConnection2.forEachDisplayConnector(
        new Consumer() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda20
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            WallpaperManagerService.this.lambda$updateFallbackConnection$3(
                wallpaperConnection2, (WallpaperManagerService.DisplayConnector) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$updateFallbackConnection$1(
      WallpaperConnection wallpaperConnection, DisplayConnector displayConnector) {
    if (displayConnector.mEngine != null) {
      displayConnector.disconnectLocked(wallpaperConnection);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$updateFallbackConnection$2(
      WallpaperConnection wallpaperConnection, Display display) {
    return (!this.mWallpaperDisplayHelper.isUsableDisplay(display, wallpaperConnection.mClientUid)
            || display.getDisplayId() == 0
            || wallpaperConnection.containsDisplay(display.getDisplayId()))
        ? false
        : true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateFallbackConnection$3(
      WallpaperConnection wallpaperConnection, DisplayConnector displayConnector) {
    if (displayConnector.mEngine == null) {
      displayConnector.connectLocked(wallpaperConnection, this.mFallbackWallpaper);
    }
  }

  final class DisplayConnector {
    public boolean mDimensionsChanged;
    public final int mDisplayId;
    public IWallpaperEngine mEngine;
    public boolean mPaddingChanged;
    public final Binder mToken = new Binder();

    public DisplayConnector(int i) {
      this.mDisplayId = i;
    }

    public void ensureStatusHandled() {
      WallpaperDisplayHelper.DisplayData displayDataOrCreate =
          WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplayDataOrCreate(
              this.mDisplayId);
      if (this.mDimensionsChanged) {
        try {
          this.mEngine.setDesiredSize(displayDataOrCreate.mWidth, displayDataOrCreate.mHeight);
        } catch (RemoteException e) {
          Slog.w("WallpaperManagerService", "Failed to set wallpaper dimensions", e);
        }
        this.mDimensionsChanged = false;
      }
      if (this.mPaddingChanged) {
        try {
          this.mEngine.setDisplayPadding(displayDataOrCreate.mPadding);
        } catch (RemoteException e2) {
          Slog.w("WallpaperManagerService", "Failed to set wallpaper padding", e2);
        }
        this.mPaddingChanged = false;
      }
    }

    public void connectLocked(
        WallpaperConnection wallpaperConnection, WallpaperData wallpaperData) {
      if (wallpaperConnection.mService == null) {
        Slog.w("WallpaperManagerService", "WallpaperService is not connected yet");
        return;
      }
      TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
      timingsTraceAndSlog.traceBegin("WPMS.connectLocked-" + wallpaperData.wallpaperComponent);
      if (!WhichChecker.isWatchFaceDisplay(wallpaperData.mWhich) || this.mDisplayId == 1) {
        if (!WhichChecker.isVirtualDisplay(wallpaperData.mWhich)
            || WallpaperManagerService.this.mSemService.mVirtualDisplayMode
                .isVirtualWallpaperDisplay(this.mDisplayId)) {
          Slog.v(
              "WallpaperManagerService",
              "Adding window token: " + this.mToken + " , wallpaper = " + wallpaperData);
          WallpaperManagerService.this.mWindowManagerInternal.addWindowToken(
              this.mToken, 2013, this.mDisplayId, null);
          WallpaperManagerService.this.mWindowManagerInternal.setWallpaperShowWhenLocked(
              this.mToken,
              WallpaperManagerService.this.isVisibleWhichWhenKeyguardLocked(wallpaperData.mWhich));
          WallpaperManagerService.this.mWindowManagerInternal.setWallpaperFoldedType(
              this.mToken, WhichChecker.isSubDisplay(wallpaperData.mWhich));
          WallpaperDisplayHelper.DisplayData displayDataOrCreate =
              WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplayDataOrCreate(
                  this.mDisplayId);
          try {
            wallpaperConnection.mService.attach(
                wallpaperConnection,
                this.mToken,
                2013,
                false,
                displayDataOrCreate.mWidth,
                displayDataOrCreate.mHeight,
                displayDataOrCreate.mPadding,
                this.mDisplayId,
                wallpaperData.mWhich);
          } catch (RemoteException e) {
            Slog.w("WallpaperManagerService", "Failed attaching wallpaper on display", e);
            if (!wallpaperData.wallpaperUpdating
                && wallpaperConnection.getConnectedEngineSize() == 0) {
              WallpaperManagerService.this.bindWallpaperComponentLocked(
                  null, false, false, wallpaperData, null);
            }
          }
          timingsTraceAndSlog.traceEnd();
        }
      }
    }

    public void disconnectLocked(WallpaperConnection wallpaperConnection) {
      if (wallpaperConnection != null) {
        Slog.v(
            "WallpaperManagerService",
            "Removing window token: " + this.mToken + " , mDisplayId = " + this.mDisplayId);
      }
      WallpaperManagerService.this.mWindowManagerInternal.removeWindowToken(
          this.mToken, false, this.mDisplayId);
      try {
        IWallpaperService iWallpaperService = wallpaperConnection.mService;
        if (iWallpaperService != null) {
          iWallpaperService.detach(this.mToken);
        }
      } catch (RemoteException e) {
        Slog.w(
            "WallpaperManagerService", "connection.mService.destroy() threw a RemoteException", e);
      }
      this.mEngine = null;
    }
  }

  public class WallpaperConnection extends IWallpaperConnection.Stub implements ServiceConnection {
    public final int mClientUid;
    public final WallpaperInfo mInfo;
    public IRemoteCallback mReply;
    public IWallpaperService mService;
    public WallpaperData mWallpaper;
    public final SparseArray mDisplayConnector = new SparseArray();
    public Runnable mResetRunnable =
        new Runnable() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            WallpaperManagerService.WallpaperConnection.this.lambda$new$0();
          }
        };
    public Runnable mTryToRebindRunnable =
        new Runnable() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            WallpaperManagerService.WallpaperConnection.this.tryToRebind();
          }
        };
    public Runnable mDisconnectRunnable =
        new Runnable() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            WallpaperManagerService.WallpaperConnection.this.lambda$new$5();
          }
        };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mShuttingDown) {
          Slog.i("WallpaperManagerService", "Ignoring relaunch timeout during shutdown");
          return;
        }
        if (this.mWallpaper != null
            && WallpaperManagerService.this.mImageWallpaper.equals(
                this.mWallpaper.wallpaperComponent)) {
          Slog.w(
              "WallpaperManagerService",
              "Ignore reset for image wallpaper. This connection isn't valid.");
          return;
        }
        WallpaperData wallpaperData = this.mWallpaper;
        if (!wallpaperData.wallpaperUpdating
            && wallpaperData.userId == WallpaperManagerService.this.mCurrentUserId) {
          Slog.w(
              "WallpaperManagerService",
              "Wallpaper reconnect timed out for "
                  + this.mWallpaper.wallpaperComponent
                  + ", reverting to built-in wallpaper!");
          WallpaperManagerService.this.clearWallpaperLocked(
              true, WhichChecker.getMode(this.mWallpaper.mWhich) | 1, this.mWallpaper.userId, null);
        }
      }
    }

    public WallpaperConnection(WallpaperInfo wallpaperInfo, WallpaperData wallpaperData, int i) {
      this.mInfo = wallpaperInfo;
      this.mWallpaper = wallpaperData;
      this.mClientUid = i;
      initDisplayState();
    }

    public final void initDisplayState() {
      if (this.mWallpaper.equals(WallpaperManagerService.this.mFallbackWallpaper)) {
        return;
      }
      if (WallpaperManagerService.this.supportsMultiDisplay(this)) {
        appendConnectorWithCondition(
            new Predicate() { // from class:
                              // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda6
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$initDisplayState$1;
                lambda$initDisplayState$1 =
                    WallpaperManagerService.WallpaperConnection.this.lambda$initDisplayState$1(
                        (Display) obj);
                return lambda$initDisplayState$1;
              }
            });
      } else {
        this.mDisplayConnector.append(0, WallpaperManagerService.this.new DisplayConnector(0));
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initDisplayState$1(Display display) {
      return WallpaperManagerService.this.mWallpaperDisplayHelper.isUsableDisplay(
          display, this.mClientUid);
    }

    public final void appendConnectorWithCondition(Predicate predicate) {
      int i;
      synchronized (WallpaperManagerService.this.mLock) {
        i = this.mWallpaper.mWhich;
      }
      int i2 = 0;
      if (WhichChecker.isVirtualDisplay(i)) {
        Display[] displays =
            WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplays(
                "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (displays.length > 0) {
          WallpaperManagerService.this.mActiveVirtualDisplayId = displays[0].getDisplayId();
          appendConnectorInternal(WallpaperManagerService.this.mActiveVirtualDisplayId);
          return;
        }
        return;
      }
      if (WhichChecker.isDex(i)) {
        Display[] displays2 =
            WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplays(
                "com.samsung.android.hardware.display.category.DESKTOP");
        int length = displays2.length;
        while (i2 < length) {
          Display display = displays2[i2];
          if (predicate.test(display)) {
            appendConnectorInternal(display.getDisplayId());
          }
          i2++;
        }
        return;
      }
      if (WhichChecker.isWatchFaceDisplay(i)) {
        appendConnectorInternal(1);
        return;
      }
      if (WhichChecker.isSubDisplay(i)) {
        appendConnectorInternal(0);
        return;
      }
      if (WhichChecker.isPhone(i)) {
        Display[] displays3 = WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplays();
        int length2 = displays3.length;
        while (i2 < length2) {
          Display display2 = displays3[i2];
          if (predicate.test(display2)) {
            appendConnectorInternal(display2.getDisplayId());
          }
          i2++;
        }
        return;
      }
      Slog.e(
          "WallpaperManagerService", "appendConnectorWithCondition: unexpected which. which=" + i);
    }

    public void appendConnectorInternal(int i) {
      if (this.mDisplayConnector.contains(i)) {
        return;
      }
      Log.m129d("WallpaperManagerService", "appendConnectorInternal, displayId " + i);
      this.mDisplayConnector.append(i, WallpaperManagerService.this.new DisplayConnector(i));
    }

    public void forEachDisplayConnector(Consumer consumer) {
      for (int size = this.mDisplayConnector.size() - 1; size >= 0; size--) {
        consumer.accept((DisplayConnector) this.mDisplayConnector.valueAt(size));
      }
    }

    public int getConnectedEngineSize() {
      int i = 0;
      for (int size = this.mDisplayConnector.size() - 1; size >= 0; size--) {
        if (((DisplayConnector) this.mDisplayConnector.valueAt(size)).mEngine != null) {
          i++;
        }
      }
      return i;
    }

    public DisplayConnector getDisplayConnectorOrCreate(int i) {
      DisplayConnector displayConnector = (DisplayConnector) this.mDisplayConnector.get(i);
      if (displayConnector != null
          || !WallpaperManagerService.this.mWallpaperDisplayHelper.isUsableDisplay(
              i, this.mClientUid)) {
        return displayConnector;
      }
      DisplayConnector displayConnector2 = WallpaperManagerService.this.new DisplayConnector(i);
      this.mDisplayConnector.append(i, displayConnector2);
      return displayConnector2;
    }

    public boolean containsDisplay(int i) {
      return this.mDisplayConnector.get(i) != null;
    }

    public void removeDisplayConnector(int i) {
      if (((DisplayConnector) this.mDisplayConnector.get(i)) != null) {
        this.mDisplayConnector.remove(i);
      }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
      timingsTraceAndSlog.traceBegin("WPMS.onServiceConnected-" + componentName);
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mSemService.mDesktopMode
            .getWallpaperBindingFallbackExecuted()) {
          WallpaperManagerService.this.mSemService.mDesktopMode
              .increaseWallpaperBindingFallbackCount();
          WallpaperManagerService.this.mSemService.mDesktopMode.setWallpaperBindingFallbackExecuted(
              false);
        }
        WallpaperData wallpaperData = this.mWallpaper;
        if (wallpaperData.connection == this) {
          int mode = WhichChecker.getMode(wallpaperData.mWhich);
          boolean z = true;
          WallpaperManagerService.this.mSemService.handleWallpaperBindingTimeout(true, false);
          Slog.w(
              "WallpaperManagerService",
              "Wallpaper onServiceConnected : " + this.mWallpaper.wallpaperComponent);
          Log.m129d(
              "WallpaperManagerService",
              "Wallpaper onServiceConnected : " + this.mWallpaper + " mode = " + mode);
          this.mService = IWallpaperService.Stub.asInterface(iBinder);
          if (WallpaperManagerService.this.needUpdateWallpaperData(this.mWallpaper)) {
            Log.m129d("WallpaperManagerService", "onServiceConnected needUpdateWallpaperData");
            WallpaperManagerService.this.mWallpaperDataParser.ensureSaneWallpaperData(
                this.mWallpaper);
            WallpaperData wallpaperData2 =
                WallpaperManagerService.this.mLockWallpaperMap.get(
                    WallpaperManagerService.this.mCurrentUserId, mode);
            if (wallpaperData2 != null) {
              WallpaperManagerService.this.mWallpaperDataParser.ensureSaneWallpaperData(
                  wallpaperData2);
            }
          } else {
            z = false;
          }
          WallpaperManagerService.this.attachServiceLocked(this, this.mWallpaper);
          if (!WallpaperManagerService.this.mWallpaperDataParser.isSameWithPreviousWallpaper(
                  this.mWallpaper, mode)
              || z) {
            WallpaperData wallpaperData3 = this.mWallpaper;
            wallpaperData3.nextWallpaperComponent = wallpaperData3.wallpaperComponent;
            WallpaperManagerService.this.saveSettingsLocked(wallpaperData3.userId, mode);
          }
          FgThread.getHandler().removeCallbacks(this.mResetRunnable);
          WallpaperManagerService.this
              .mContext
              .getMainThreadHandler()
              .removeCallbacks(this.mTryToRebindRunnable);
          WallpaperManagerService.this
              .mContext
              .getMainThreadHandler()
              .removeCallbacks(this.mDisconnectRunnable);
          if (WallpaperManagerService.this.mImageWallpaper.equals(
              this.mWallpaper.wallpaperComponent)) {
            if ((Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE)
                || Rune.VIRTUAL_DISPLAY_WALLPAPER) {
              int wpType = this.mWallpaper.mSemWallpaperData.getWpType();
              if (wpType != 3 && wpType != 5 && wpType != 8) {
                this.mWallpaper.mSemWallpaperData.setWpType(0);
              }
            } else {
              this.mWallpaper.mSemWallpaperData.setWpType(0);
            }
          } else {
            this.mWallpaper.mSemWallpaperData.setWpType(7);
          }
          try {
            IWallpaperService iWallpaperService = this.mService;
            if (iWallpaperService != null) {
              iWallpaperService.setCurrentUserId(WallpaperManagerService.this.mCurrentUserId);
            }
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            wallpaperManagerService.notifyAodVisibilityState(
                wallpaperManagerService.mSemService.getAodVisibilityState());
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
      }
      timingsTraceAndSlog.traceEnd();
    }

    public void onLocalWallpaperColorsChanged(
        final RectF rectF, final WallpaperColors wallpaperColors, final int i) {
      forEachDisplayConnector(
          new Consumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WallpaperManagerService.WallpaperConnection.this
                  .lambda$onLocalWallpaperColorsChanged$3(
                      rectF, wallpaperColors, i, (WallpaperManagerService.DisplayConnector) obj);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLocalWallpaperColorsChanged$3(
        final RectF rectF,
        final WallpaperColors wallpaperColors,
        int i,
        DisplayConnector displayConnector) {
      Consumer consumer =
          new Consumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WallpaperManagerService.WallpaperConnection.lambda$onLocalWallpaperColorsChanged$2(
                  rectF, wallpaperColors, (ILocalWallpaperColorConsumer) obj);
            }
          };
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService.this.mLocalColorRepo.forEachCallback(consumer, rectF, i);
      }
    }

    public static /* synthetic */ void lambda$onLocalWallpaperColorsChanged$2(
        RectF rectF,
        WallpaperColors wallpaperColors,
        ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
      try {
        iLocalWallpaperColorConsumer.onColorsChanged(rectF, wallpaperColors);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to notify local color callbacks", e);
      }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      synchronized (WallpaperManagerService.this.mLock) {
        Slog.w("WallpaperManagerService", "Wallpaper service gone: " + componentName);
        if (!Objects.equals(componentName, this.mWallpaper.wallpaperComponent)) {
          Slog.e(
              "WallpaperManagerService",
              "Does not match expected wallpaper component " + this.mWallpaper.wallpaperComponent);
        }
        this.mService = null;
        forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((WallpaperManagerService.DisplayConnector) obj).mEngine = null;
              }
            });
        WallpaperData wallpaperData = this.mWallpaper;
        if (wallpaperData.connection == this && !wallpaperData.wallpaperUpdating) {
          WallpaperManagerService.this
              .mContext
              .getMainThreadHandler()
              .postDelayed(this.mDisconnectRunnable, 1000L);
        }
      }
    }

    public final void scheduleTimeoutLocked() {
      Handler handler = FgThread.getHandler();
      handler.removeCallbacks(this.mResetRunnable);
      handler.postDelayed(this.mResetRunnable, 10000L);
      Slog.i(
          "WallpaperManagerService",
          "Started wallpaper reconnect timeout for " + this.mWallpaper.wallpaperComponent);
    }

    public final void tryToRebind() {
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperData wallpaperData = this.mWallpaper;
        if (wallpaperData.wallpaperUpdating) {
          return;
        }
        ComponentName componentName = wallpaperData.wallpaperComponent;
        if (WallpaperManagerService.this.bindWallpaperComponentLocked(
            componentName, true, false, wallpaperData, null)) {
          this.mWallpaper.connection.scheduleTimeoutLocked();
        } else if (SystemClock.uptimeMillis() - this.mWallpaper.lastDiedTime < 10000) {
          Slog.w("WallpaperManagerService", "Rebind fail! Try again later");
          WallpaperManagerService.this
              .mContext
              .getMainThreadHandler()
              .postDelayed(this.mTryToRebindRunnable, 1000L);
        } else {
          Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
          WallpaperManagerService.this.clearWallpaperLocked(
              true, WhichChecker.getMode(this.mWallpaper.mWhich) | 1, this.mWallpaper.userId, null);
          String flattenToString = componentName.flattenToString();
          EventLog.writeEvent(
              33000, flattenToString.substring(0, Math.min(flattenToString.length(), 128)));
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5() {
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperData wallpaperData = this.mWallpaper;
        if (this == wallpaperData.connection) {
          ComponentName componentName = wallpaperData.wallpaperComponent;
          Log.m129d(
              "WallpaperManagerService", "Wallpaper onServiceDisconnected : " + componentName);
          WallpaperData wallpaperData2 = this.mWallpaper;
          if (!wallpaperData2.wallpaperUpdating
              && wallpaperData2.userId == WallpaperManagerService.this.mCurrentUserId
              && !Objects.equals(
                  WallpaperManagerService.this.getDefaultPreloadedLiveWallpaperComponentName(
                      this.mWallpaper.mSemWallpaperData.getWhich()),
                  componentName)
              && !Objects.equals(
                  WallpaperManagerService.this.mDefaultWallpaperComponent, componentName)
              && !Objects.equals(WallpaperManagerService.this.mImageWallpaper, componentName)) {
            long j = this.mWallpaper.lastDiedTime;
            if (j != 0 && j + 10000 > SystemClock.uptimeMillis()) {
              Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
              if (componentName.equals(WallpaperManagerService.this.mImageWallpaper)) {
                Slog.w("WallpaperManagerService", "ImageWallpaper not reverted");
              } else {
                int mode = WhichChecker.getMode(this.mWallpaper.mWhich);
                if (!WallpaperManagerService.this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(
                    this.mWallpaper)) {
                  WallpaperManagerService.this.clearWallpaperLocked(
                      true, mode | 1, this.mWallpaper.userId, null);
                }
              }
            } else {
              this.mWallpaper.lastDiedTime = SystemClock.uptimeMillis();
              tryToRebind();
            }
          }
        } else {
          Slog.i(
              "WallpaperManagerService", "Wallpaper changed during disconnect tracking; ignoring");
        }
      }
    }

    public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) {
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mImageWallpaper.equals(
            this.mWallpaper.wallpaperComponent)) {
          return;
        }
        int mode =
            WallpaperManagerService.this.mIsLockscreenLiveWallpaperEnabled
                ? this.mWallpaper.mWhich
                : WhichChecker.getMode(this.mWallpaper.mWhich) | 1;
        this.mWallpaper.primaryColors = wallpaperColors;
        if (i == 0
            && WallpaperManagerService.this.mLockWallpaperMap.get(this.mWallpaper.userId) == null) {
          mode |= 2;
        }
        if (mode != 0) {
          WallpaperManagerService.this.notifyWallpaperColorsChangedOnDisplay(
              this.mWallpaper, mode, i);
        }
      }
    }

    public void attachEngine(IWallpaperEngine iWallpaperEngine, int i) {
      synchronized (WallpaperManagerService.this.mLock) {
        DisplayConnector displayConnectorOrCreate = getDisplayConnectorOrCreate(i);
        if (displayConnectorOrCreate == null) {
          throw new IllegalStateException("Connector has already been destroyed");
        }
        displayConnectorOrCreate.mEngine = iWallpaperEngine;
        displayConnectorOrCreate.ensureStatusHandled();
        WallpaperInfo wallpaperInfo = this.mInfo;
        if (wallpaperInfo != null && wallpaperInfo.supportsAmbientMode() && i == 0) {
          try {
            displayConnectorOrCreate.mEngine.setInAmbientMode(
                WallpaperManagerService.this.mInAmbientMode, 0L);
          } catch (RemoteException e) {
            Slog.w("WallpaperManagerService", "Failed to set ambient mode state", e);
          }
        }
        try {
          displayConnectorOrCreate.mEngine.requestWallpaperColors();
        } catch (RemoteException e2) {
          Slog.w("WallpaperManagerService", "Failed to request wallpaper colors", e2);
        }
        List areasByDisplayId = WallpaperManagerService.this.mLocalColorRepo.getAreasByDisplayId(i);
        if (areasByDisplayId != null && areasByDisplayId.size() != 0) {
          try {
            displayConnectorOrCreate.mEngine.addLocalColorsAreas(areasByDisplayId);
          } catch (RemoteException e3) {
            Slog.w("WallpaperManagerService", "Failed to register local colors areas", e3);
          }
        }
        float f = this.mWallpaper.mWallpaperDimAmount;
        if (f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          try {
            displayConnectorOrCreate.mEngine.applyDimming(f);
          } catch (RemoteException e4) {
            Slog.w("WallpaperManagerService", "Failed to dim wallpaper", e4);
          }
        }
      }
    }

    public void engineShown(IWallpaperEngine iWallpaperEngine) {
      synchronized (WallpaperManagerService.this.mLock) {
        if (Rune.DESKTOP_STANDALONE_MODE_WALLPAPER
            || WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopDualMode()) {
          WallpaperData wallpaperData = this.mWallpaper;
          WallpaperManagerService.this.mSemService.mDesktopMode.sendWallpaperEngineShownIntent(
              wallpaperData == null ? 0 : wallpaperData.mSemWallpaperData.getWhich());
        }
        if (this.mReply != null) {
          TimingsTraceAndSlog timingsTraceAndSlog =
              new TimingsTraceAndSlog("WallpaperManagerService");
          timingsTraceAndSlog.traceBegin("WPMS.mReply.sendResult");
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            try {
              this.mReply.sendResult((Bundle) null);
            } catch (RemoteException e) {
              Slog.d("WallpaperManagerService", "Failed to send callback!", e);
            }
            timingsTraceAndSlog.traceEnd();
            this.mReply = null;
          } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
          }
        }
      }
    }

    public ParcelFileDescriptor setWallpaper(String str) {
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperData wallpaperData = this.mWallpaper;
        if (wallpaperData.connection != this) {
          return null;
        }
        return WallpaperManagerService.this.updateWallpaperBitmapLocked(str, wallpaperData, null);
      }
    }
  }

  public class WallpaperDestinationChangeHandler {
    public final int mMode;
    public final WallpaperData mNewWallpaper;
    public final WallpaperData mOriginalSystem;

    public WallpaperDestinationChangeHandler(WallpaperData wallpaperData, int i) {
      this.mNewWallpaper = wallpaperData;
      this.mOriginalSystem =
          new WallpaperData(
              WallpaperManagerService.this.mWallpaperMap.get(wallpaperData.userId, i));
      this.mMode = i;
      Slog.i("WallpaperManagerService", "WallpaperDestinationChangeHandler: mode=" + i);
    }

    public void complete() {
      WallpaperData wallpaperData;
      WallpaperData wallpaperData2 = this.mNewWallpaper;
      if (wallpaperData2.mSystemWasBoth) {
        if (WhichChecker.isSystem(wallpaperData2.mWhich)) {
          if (WallpaperManagerService.this.mImageWallpaper.equals(
              this.mOriginalSystem.wallpaperComponent)) {
            WallpaperData wallpaperData3 =
                WallpaperManagerService.this.mLockWallpaperMap.get(
                    this.mNewWallpaper.userId, this.mMode);
            if (wallpaperData3 != null) {
              WallpaperData wallpaperData4 = this.mOriginalSystem;
              wallpaperData3.wallpaperComponent = wallpaperData4.wallpaperComponent;
              WallpaperConnection wallpaperConnection = wallpaperData4.connection;
              wallpaperData3.connection = wallpaperConnection;
              if (wallpaperConnection != null) {
                wallpaperConnection.mWallpaper = wallpaperData3;
              }
              wallpaperData4.mWhich = this.mMode | 2;
              WallpaperManagerService.this.updateEngineFlags(wallpaperData4);
              WallpaperManagerService.this.notifyWallpaperColorsChanged(
                  wallpaperData3, this.mMode | 2);
              return;
            }
            WallpaperData wallpaperData5 =
                WallpaperManagerService.this.mWallpaperMap.get(
                    this.mNewWallpaper.userId, this.mMode);
            if (wallpaperData5 != null) {
              wallpaperData5.mWhich = this.mMode | 3;
              WallpaperManagerService.this.updateEngineFlags(wallpaperData5);
            }
            WallpaperManagerService.this.mLockWallpaperMap.remove(
                this.mNewWallpaper.userId, this.mMode);
            return;
          }
          WallpaperData wallpaperData6 = this.mOriginalSystem;
          wallpaperData6.mWhich = this.mMode | 2;
          WallpaperManagerService.this.updateEngineFlags(wallpaperData6);
          WallpaperManagerService.this.mLockWallpaperMap.put(
              this.mNewWallpaper.userId, this.mMode, this.mOriginalSystem);
          WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
          WallpaperData wallpaperData7 = this.mOriginalSystem;
          wallpaperManagerService.mLastLockWallpaper = wallpaperData7;
          wallpaperManagerService.notifyWallpaperColorsChanged(wallpaperData7, this.mMode | 2);
          return;
        }
        if (WhichChecker.isLock(this.mNewWallpaper.mWhich)
            && (wallpaperData =
                    WallpaperManagerService.this.mWallpaperMap.get(this.mNewWallpaper.userId))
                != null
            && wallpaperData.wallpaperId == this.mOriginalSystem.wallpaperId) {
          wallpaperData.mWhich = this.mMode | 1;
          WallpaperManagerService.this.updateEngineFlags(wallpaperData);
        }
      }
    }
  }

  public class MyPackageMonitor extends PackageMonitor {
    public MyPackageMonitor() {}

    public void onPackageUpdateFinished(String str, int i) {
      ComponentName componentName;
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
          return;
        }
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(
                WallpaperManagerService.this.mCurrentUserId);
        if (wallpaperData != null
            && (componentName = wallpaperData.wallpaperComponent) != null
            && componentName.getPackageName().equals(str)) {
          Slog.i("WallpaperManagerService", "Wallpaper " + componentName + " update has finished");
          wallpaperData.wallpaperUpdating = false;
          WallpaperManagerService.this.clearWallpaperComponentLocked(wallpaperData);
          if (!WallpaperManagerService.this.bindWallpaperComponentLocked(
              componentName, false, false, wallpaperData, null)) {
            Slog.w(
                "WallpaperManagerService",
                "Wallpaper " + componentName + " no longer available; reverting to default");
            WallpaperManagerService.this.clearWallpaperLocked(false, 1, wallpaperData.userId, null);
          }
        }
      }
    }

    public void onPackageModified(String str) {
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
          return;
        }
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(
                WallpaperManagerService.this.mCurrentUserId);
        if (wallpaperData != null) {
          ComponentName componentName = wallpaperData.wallpaperComponent;
          if (componentName != null && componentName.getPackageName().equals(str)) {
            doPackagesChangedLocked(true, wallpaperData);
          }
        }
      }
    }

    public void onPackageUpdateStarted(String str, int i) {
      ComponentName componentName;
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
          return;
        }
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(
                WallpaperManagerService.this.mCurrentUserId);
        if (wallpaperData != null
            && (componentName = wallpaperData.wallpaperComponent) != null
            && componentName.getPackageName().equals(str)) {
          Slog.i(
              "WallpaperManagerService",
              "Wallpaper service " + wallpaperData.wallpaperComponent + " is updating");
          wallpaperData.wallpaperUpdating = true;
          if (wallpaperData.connection != null) {
            FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
          }
        }
      }
    }

    public boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
          return false;
        }
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(
                WallpaperManagerService.this.mCurrentUserId);
        return wallpaperData != null ? false | doPackagesChangedLocked(z, wallpaperData) : false;
      }
    }

    public void onSomePackagesChanged() {
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
          return;
        }
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(
                WallpaperManagerService.this.mCurrentUserId);
        if (wallpaperData != null) {
          doPackagesChangedLocked(true, wallpaperData);
        }
      }
    }

    public boolean doPackagesChangedLocked(boolean z, WallpaperData wallpaperData) {
      boolean z2;
      int isPackageDisappearing;
      int isPackageDisappearing2;
      ComponentName componentName = wallpaperData.wallpaperComponent;
      if (componentName == null
          || !((isPackageDisappearing2 = isPackageDisappearing(componentName.getPackageName())) == 3
              || isPackageDisappearing2 == 2)) {
        z2 = false;
      } else {
        if (z
            && !WallpaperManagerService.this.mImageWallpaper.equals(
                wallpaperData.wallpaperComponent)) {
          Slog.w(
              "WallpaperManagerService",
              "Wallpaper uninstalled, removing: " + wallpaperData.wallpaperComponent);
          if ("com.samsung.android.wallpaper.live"
              .equals(wallpaperData.wallpaperComponent.getPackageName())) {
            Log.addLogString("WallpaperManagerService", "doPackagesChangedLocked : try to rebind");
            WallpaperManagerService.this.forceRebindWallpaper(
                wallpaperData.mSemWallpaperData.getWhich(), wallpaperData.userId);
            return true;
          }
          if (wallpaperData.connection != null) {
            Log.addLogString(
                "WallpaperManagerService", "doPackagesChangedLocked : removeCallbacks");
            WallpaperManagerService.this
                .mContext
                .getMainThreadHandler()
                .removeCallbacks(wallpaperData.connection.mTryToRebindRunnable);
          }
          WallpaperManagerService.this.clearWallpaperLocked(false, 1, wallpaperData.userId, null);
          WallpaperManagerService.this.mSemService.mLegibilityColor.doPackagesChangedLocked(
              wallpaperData.mSemWallpaperData);
          WallpaperManagerService.this.mSemService.mDefaultWallpaper.setKWPTypeLiveWallpaper(1);
        }
        z2 = true;
      }
      ComponentName componentName2 = wallpaperData.nextWallpaperComponent;
      if (componentName2 != null
          && ((isPackageDisappearing = isPackageDisappearing(componentName2.getPackageName())) == 3
              || isPackageDisappearing == 2)) {
        wallpaperData.nextWallpaperComponent = null;
      }
      if (wallpaperData.wallpaperComponent != null
          && !WallpaperManagerService.this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)
          && isPackageModified(wallpaperData.wallpaperComponent.getPackageName())) {
        try {
          WallpaperManagerService.this
              .mContext
              .getPackageManager()
              .getServiceInfo(wallpaperData.wallpaperComponent, 786432);
        } catch (PackageManager.NameNotFoundException unused) {
          Slog.w(
              "WallpaperManagerService",
              "Wallpaper component gone, removing: " + wallpaperData.wallpaperComponent);
          WallpaperManagerService.this.clearWallpaperLocked(false, 1, wallpaperData.userId, null);
        }
      }
      ComponentName componentName3 = wallpaperData.nextWallpaperComponent;
      if (componentName3 != null && isPackageModified(componentName3.getPackageName())) {
        try {
          WallpaperManagerService.this
              .mContext
              .getPackageManager()
              .getServiceInfo(wallpaperData.nextWallpaperComponent, 786432);
        } catch (PackageManager.NameNotFoundException unused2) {
          wallpaperData.nextWallpaperComponent = null;
        }
      }
      return z2;
    }
  }

  public WallpaperData getCurrentWallpaperData(int i, int i2) {
    WallpaperData wallpaperData;
    synchronized (this.mLock) {
      wallpaperData =
          (WhichChecker.isSystem(i) ? this.mWallpaperMap : this.mLockWallpaperMap).get(i2);
    }
    return wallpaperData;
  }

  public WallpaperManagerService(Context context) {
    Object obj = new Object();
    this.mLock = obj;
    DisplayManager.DisplayListener displayListener =
        new DisplayManager
            .DisplayListener() { // from class:
                                 // com.android.server.wallpaper.WallpaperManagerService.1
          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayChanged(int i) {}

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayAdded(int i) {
            Log.m129d("WallpaperManagerService", "onDisplayAdded, " + i);
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER
                && WallpaperManagerService.this.mSemService.mVirtualDisplayMode
                    .isVirtualWallpaperDisplay(i)) {
              WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
              WallpaperData wallpaperSafeLocked =
                  wallpaperManagerService.getWallpaperSafeLocked(
                      wallpaperManagerService.mCurrentUserId, 33);
              wallpaperSafeLocked.mWhich |= 32;
              WallpaperManagerService.this.switchWallpaper(wallpaperSafeLocked, null);
            }
          }

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayRemoved(int i) {
            synchronized (WallpaperManagerService.this.mLock) {
              WallpaperData targetWallpaper = WallpaperManagerService.this.getTargetWallpaper(i);
              if (targetWallpaper != null) {
                if (WallpaperManagerService.this.mLastWallpaper.connection.containsDisplay(i)) {
                  targetWallpaper = WallpaperManagerService.this.mLastWallpaper;
                } else if (WallpaperManagerService.this.mFallbackWallpaper.connection
                    .containsDisplay(i)) {
                  targetWallpaper = WallpaperManagerService.this.mFallbackWallpaper;
                }
                if (targetWallpaper == null) {
                  return;
                }
                DisplayConnector displayConnectorOrCreate =
                    targetWallpaper.connection.getDisplayConnectorOrCreate(i);
                if (displayConnectorOrCreate == null) {
                  return;
                }
                displayConnectorOrCreate.disconnectLocked(targetWallpaper.connection);
                targetWallpaper.connection.removeDisplayConnector(i);
                WallpaperManagerService.this.mWallpaperDisplayHelper.removeDisplayData(i);
              }
              for (int size = WallpaperManagerService.this.mColorsChangedListeners.size() - 1;
                  size >= 0;
                  size--) {
                ((SparseArray) WallpaperManagerService.this.mColorsChangedListeners.valueAt(size))
                    .delete(i);
              }
            }
          }
        };
    this.mDisplayListener = displayListener;
    this.mIsWallpaperInitialized = new SparseArray();
    this.mKeyguardListenerList = new ArrayList();
    this.mKeyguardListenerClientList = new ArrayList();
    this.mCoverWallpaperListenerList = new ArrayList();
    this.mCoverWallpaperListenerClientList = new ArrayList();
    this.mUserRestorecon = new SparseBooleanArray();
    this.mCurrentUserId = -10000;
    this.mLocalColorRepo = new LocalColorRepository();
    this.mContext = context;
    this.mShuttingDown = false;
    this.mImageWallpaper =
        ComponentName.unflattenFromString(
            context.getResources().getString(R.string.mime_type_presentation));
    this.mDefaultWallpaperComponent = WallpaperManager.getDefaultWallpaperComponent(context);
    Log.m129d(
        "WallpaperManagerService",
        "WallpaperService startup (support sub display ? " + Rune.SUPPORT_SUB_DISPLAY_MODE + ")");
    this.mSemWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
    SemWallpaperManagerService semWallpaperManagerService =
        new SemWallpaperManagerService(
            context, new SemCallback(), this, this.mSemWallpaperResourcesInfo);
    this.mSemService = semWallpaperManagerService;
    this.mLiveWallpaperHelper =
        new PreloadedLiveWallpaperHelper(
            context,
            semWallpaperManagerService,
            new PreloadedLiveWallpaperHelper
                .Callback() { // from class: com.android.server.wallpaper.WallpaperManagerService.2
              @Override // com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper.Callback
              public WallpaperData getWallpaperData(int i, int i2) {
                WallpaperData wallpaperSafeLocked;
                synchronized (WallpaperManagerService.this.mLock) {
                  wallpaperSafeLocked = WallpaperManagerService.this.getWallpaperSafeLocked(i2, i);
                }
                return wallpaperSafeLocked;
              }
            });
    AssetFileManager assetFileManager = new AssetFileManager();
    this.mAssetFileManager = assetFileManager;
    assetFileManager.migrateDirectory(0);
    this.mWallpaperMap = new WallpaperDataManager(obj, 1, this.mSemService);
    this.mLockWallpaperMap = new WallpaperDataManager(obj, 2, this.mSemService);
    WindowManagerInternal windowManagerInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    this.mWindowManagerInternal = windowManagerInternal;
    this.mPackageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.mIPackageManager = AppGlobals.getPackageManager();
    this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
    DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
    displayManager.registerDisplayListener(displayListener, null);
    WallpaperDisplayHelper wallpaperDisplayHelper =
        new WallpaperDisplayHelper(displayManager, windowManagerInternal);
    this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
    WallpaperCropper wallpaperCropper =
        new WallpaperCropper(wallpaperDisplayHelper, this.mSemService);
    this.mWallpaperCropper = wallpaperCropper;
    this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
    this.mMonitor = new MyPackageMonitor();
    this.mColorsChangedListeners = new SparseArray();
    boolean z = SystemProperties.getBoolean("persist.wm.debug.lockscreen_live_wallpaper", false);
    this.mIsLockscreenLiveWallpaperEnabled = z;
    this.mWallpaperDataParser =
        new WallpaperDataParser(
            context,
            wallpaperDisplayHelper,
            wallpaperCropper,
            z,
            this.mSemService,
            this.mSemWallpaperResourcesInfo);
    this.mIsMultiCropEnabled =
        SystemProperties.getBoolean("persist.wm.debug.wallpaper_multi_crop", false);
    LocalServices.addService(WallpaperManagerInternal.class, new LocalService());
    this.mIsPreviewLockLiveWallpaperEnabled = Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER;
    this.mStatusBarService =
        IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
    HandlerThread handlerThread = new HandlerThread("WallpaperManagerService");
    this.mWallpaperHandlerThread = handlerThread;
    handlerThread.start();
    this.mWallpaperHanlder = new Handler(handlerThread.getLooper());
  }

  public final class LocalService extends WallpaperManagerInternal {
    public LocalService() {}

    @Override // com.android.server.wallpaper.WallpaperManagerInternal
    public void onDisplayReady(int i) {
      WallpaperManagerService.this.onDisplayReadyInternal(i);
    }

    @Override // com.android.server.wallpaper.WallpaperManagerInternal
    public void onScreenTurnedOn(int i) {
      WallpaperManagerService.this.notifyScreenTurnedOn(i);
    }

    @Override // com.android.server.wallpaper.WallpaperManagerInternal
    public void onScreenTurningOn(int i) {
      WallpaperManagerService.this.notifyScreenTurningOn(i);
    }

    @Override // com.android.server.wallpaper.WallpaperManagerInternal
    public void onKeyguardGoingAway() {
      WallpaperManagerService.this.notifyKeyguardGoingAway();
    }
  }

  public void initialize() {
    this.mMonitor.register(this.mContext, (Looper) null, UserHandle.ALL, true);
    WallpaperUtils.getWallpaperDir(0).mkdirs();
    WallpaperUtils.getWallpaperLockDir(0).mkdir();
    if (this.mSemWallpaperResourcesInfo.isSupportCMF()) {
      this.mSemService.mCMFWallpaper.initDeviceColor();
    }
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      this.mSemService.mSubDisplayMode.updateLidStateFromInputManager();
    }
    this.mIsWallpaperInitialized.set(0, Boolean.valueOf(isWallpaperFileExists(0)));
    loadSettingsLocked(0, false, 1, 4);
    getWallpaperSafeLocked(0, 5);
    loadSettingsLocked(0, false, 2, 4);
    getWallpaperSafeLocked(0, 6);
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      loadSettingsLocked(0, false, 1, 16);
      getWallpaperSafeLocked(0, 17);
      if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
        loadSettingsLocked(0, false, 2, 16);
        getWallpaperSafeLocked(0, 18);
      }
    }
    if (Rune.VIRTUAL_DISPLAY_WALLPAPER) {
      loadSettingsLocked(0, false, 1, 32);
      getWallpaperSafeLocked(0, 33);
    }
  }

  public void finalize() {
    super.finalize();
    for (int i = 0; i < this.mWallpaperMap.size(); i++) {
      this.mWallpaperMap.valueAt(i).wallpaperObserver.stopWatching();
    }
    for (int i2 = 0; i2 < this.mLockWallpaperMap.size(); i2++) {
      this.mLockWallpaperMap.valueAt(i2).wallpaperObserver.stopWatching();
    }
  }

  /* renamed from: systemReady, reason: merged with bridge method [inline-methods] */
  public void lambda$onBootPhase$4() {
    initialize();
    WallpaperData wallpaperData = this.mWallpaperMap.get(0, 4);
    Log.m129d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 1");
    initImageWallpaperCropFile(wallpaperData, 5);
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      WallpaperData wallpaperData2 = this.mWallpaperMap.get(0, 16);
      Log.m129d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 2");
      initImageWallpaperCropFile(wallpaperData2, 17);
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.wallpaper.WallpaperManagerService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
              WallpaperManagerService.this.onRemoveUser(
                  intent.getIntExtra("android.intent.extra.user_handle", -10000));
            }
          }
        },
        intentFilter);
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.wallpaper.WallpaperManagerService.4
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
              synchronized (WallpaperManagerService.this.mLock) {
                WallpaperManagerService.this.mShuttingDown = true;
              }
            }
          }
        },
        new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
    this.mSemService.mLegibilityColor.initWallpaperLegibilityColors();
    try {
      ActivityManager.getService()
          .registerUserSwitchObserver(
              new UserSwitchObserver() { // from class:
                                         // com.android.server.wallpaper.WallpaperManagerService.5
                public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                  WallpaperManagerService.this.errorCheck(i);
                  WallpaperManagerService.this.switchUser(i, iRemoteCallback);
                }
              },
              "WallpaperManagerService");
    } catch (RemoteException e) {
      e.rethrowAsRuntimeException();
    }
    this.mSemService.mOMCWallpaper.registerOMCWallpaperUpdatedReceiver();
    WallpaperData wallpaperData3 = this.mLockWallpaperMap.get(UserHandle.getCallingUserId());
    this.mSemService.mOMCWallpaper.checkTSSActivation(
        wallpaperData3 == null || wallpaperData3.mSemWallpaperData.getWpType() == -1);
  }

  public String getName() {
    if (Binder.getCallingUid() != 1000) {
      throw new RuntimeException("getName() can only be called from the system process");
    }
    synchronized (this.mLock) {
      WallpaperData wallpaperData = this.mWallpaperMap.get(0);
      if (wallpaperData == null) {
        return null;
      }
      return wallpaperData.name;
    }
  }

  public void stopObserver(WallpaperData wallpaperData) {
    FileObserver fileObserver;
    if (wallpaperData == null || (fileObserver = wallpaperData.wallpaperObserver) == null) {
      return;
    }
    fileObserver.stopWatching();
    wallpaperData.wallpaperObserver = null;
  }

  public void stopObserversLocked(int i) {
    stopObserver(this.mWallpaperMap.get(i));
    stopObserver(this.mLockWallpaperMap.get(i));
    this.mWallpaperMap.remove(i);
    this.mLockWallpaperMap.remove(i);
    if (i == 0) {
      stopObserver(this.mWallpaperMap.get(i, 8));
      stopObserver(this.mLockWallpaperMap.get(i, 8));
      this.mWallpaperMap.remove(i, 8);
      this.mLockWallpaperMap.remove(i, 8);
    }
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      stopObserver(this.mWallpaperMap.get(i, 16));
      stopObserver(this.mLockWallpaperMap.get(i, 16));
      this.mWallpaperMap.remove(i, 16);
      this.mLockWallpaperMap.remove(i, 16);
    }
    SemWallpaperManagerService.putLog(SemWallpaperManagerService.getCallStackString());
  }

  @Override // com.android.server.wallpaper.IWallpaperManagerService
  public void onBootPhase(int i) {
    errorCheck(0);
    if (i == 550) {
      Log.m129d("WallpaperManagerService", "onBootPhase: " + i);
      this.mWallpaperHanlder.post(
          new Runnable() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
              WallpaperManagerService.this.lambda$onBootPhase$4();
            }
          });
      return;
    }
    if (i == 600) {
      Log.m129d("WallpaperManagerService", "onBootPhase: " + i);
      this.mWallpaperHanlder.post(
          new Runnable() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
              WallpaperManagerService.this.lambda$onBootPhase$5();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBootPhase$5() {
    switchUser(0, null);
  }

  public final void errorCheck(final int i) {
    sWallpaperType.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda23
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            WallpaperManagerService.this.lambda$errorCheck$6(i, (Integer) obj, (String) obj2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$errorCheck$6(int i, Integer num, String str) {
    File file = new File(WallpaperUtils.getWallpaperDir(i), str);
    if (file.exists()) {
      Slog.w(
          "WallpaperManagerService",
          "User:"
              + i
              + ", wallpaper tyep = "
              + num
              + ", wallpaper fail detect!! reset to default wallpaper");
      clearWallpaperData(i, num.intValue());
      file.delete();
    }
  }

  public final void clearWallpaperData(int i, int i2) {
    WallpaperData wallpaperData = new WallpaperData(i, i2);
    if (wallpaperData.sourceExists()) {
      wallpaperData.wallpaperFile.delete();
    }
    if (wallpaperData.cropExists()) {
      wallpaperData.cropFile.delete();
    }
  }

  @Override // com.android.server.wallpaper.IWallpaperManagerService
  public void onUnlockUser(final int i) {
    this.mWallpaperHanlder.post(
        new Runnable() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            WallpaperManagerService.this.lambda$onUnlockUser$7(i);
          }
        });
  }

  /* renamed from: completeUnlockUser, reason: merged with bridge method [inline-methods] */
  public final void lambda$onUnlockUser$7(final int i) {
    synchronized (this.mLock) {
      if (this.mCurrentUserId == i) {
        if (!this.mIsInitialLoadSucceed) {
          SemWallpaperManagerService.putLog(SemWallpaperManagerService.getCallStackString());
          loadSettingsLocked(0, false, 1);
          if (!this.mIsInitialLoadSucceed) {
            SemWallpaperManagerService.putLog("onUnlockUser: loadSettingsLocked failed.");
          }
        }
        if (this.mIsLockscreenLiveWallpaperEnabled) {
          if (this.mHomeWallpaperWaitingForUnlock) {
            WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, 1);
            switchWallpaper(wallpaperSafeLocked, null);
            notifyCallbacksLocked(wallpaperSafeLocked);
          }
          if (this.mLockWallpaperWaitingForUnlock) {
            WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i, 2);
            switchWallpaper(wallpaperSafeLocked2, null);
            notifyCallbacksLocked(wallpaperSafeLocked2);
          }
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
          WallpaperData wallpaperSafeLocked3 = getWallpaperSafeLocked(this.mCurrentUserId, 5);
          wallpaperSafeLocked3.mWhich |= 4;
          if (wallpaperSafeLocked3.mSemWallpaperData.getWaitingForUnlockUser()) {
            switchWallpaper(wallpaperSafeLocked3, null);
          }
          WallpaperData wallpaperSafeLocked4 = getWallpaperSafeLocked(this.mCurrentUserId, 17);
          wallpaperSafeLocked4.mWhich |= 16;
          if (wallpaperSafeLocked4.mSemWallpaperData.getWaitingForUnlockUser()) {
            switchWallpaper(wallpaperSafeLocked4, null);
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
              notifyCoverWallpaperChanged(
                  wallpaperSafeLocked4.mSemWallpaperData.getWpType(),
                  wallpaperSafeLocked4.mSemWallpaperData.getWhich());
            }
          }
        } else {
          WallpaperData wallpaperSafeLocked5 = getWallpaperSafeLocked(i, 1);
          if (wallpaperSafeLocked5.mSemWallpaperData.getWaitingForUnlockUser()) {
            switchWallpaper(wallpaperSafeLocked5, null);
            notifyCallbacksLocked(wallpaperSafeLocked5);
          }
        }
        if (!this.mUserRestorecon.get(i)) {
          this.mUserRestorecon.put(i, true);
          BackgroundThread.getHandler()
              .post(
                  new Runnable() { // from class:
                                   // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda25
                    @Override // java.lang.Runnable
                    public final void run() {
                      WallpaperManagerService.lambda$completeUnlockUser$8(i);
                    }
                  });
        }
      }
    }
  }

  public static /* synthetic */ void lambda$completeUnlockUser$8(int i) {
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
    timingsTraceAndSlog.traceBegin("Wallpaper_selinux_restorecon-" + i);
    try {
      for (File file : WallpaperUtils.getWallpaperFiles(i)) {
        if (file.exists()) {
          SELinux.restorecon(file);
        }
      }
    } finally {
      timingsTraceAndSlog.traceEnd();
    }
  }

  public void onRemoveUser(int i) {
    if (i < 1) {
      return;
    }
    synchronized (this.mLock) {
      stopObserversLocked(i);
      WallpaperUtils.getWallpaperFiles(i)
          .forEach(
              new Consumer() { // from class:
                               // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ((File) obj).delete();
                }
              });
      this.mUserRestorecon.delete(i);
    }
  }

  public final WallpaperData peekWallpaperDataLocked(int i, int i2) {
    return (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap)
        .get(i, WhichChecker.getMode(i2));
  }

  public final WallpaperData peekPairingConsideredWallpaperDataLocked(int i, int i2) {
    boolean isLock = WhichChecker.isLock(i);
    int mode = WhichChecker.getMode(i);
    if (isLock && isSystemAndLockPaired(i2, mode)) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, mode | 1);
      if ((peekWallpaperDataLocked == null
              ? -1
              : peekWallpaperDataLocked.mSemWallpaperData.getWpType())
          == 7) {
        return null;
      }
    }
    return peekWallpaperDataLocked(i2, i);
  }

  public final boolean isWallpaperFileExists(int i) {
    if (new File(WallpaperUtils.getWallpaperDir(i), SemWallpaperManagerService.getFileName(4, 2, 0))
        .exists()) {
      Log.m129d("WallpaperManagerService", "isWallpaperFileExists: TRUE");
      return true;
    }
    Log.m129d("WallpaperManagerService", "isWallpaperFileExists: FALSE");
    return false;
  }

  public void switchUser(int i, IRemoteCallback iRemoteCallback) {
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
    timingsTraceAndSlog.traceBegin("Wallpaper_switch-user-" + i);
    if (i >= 0) {
      try {
        if (this.mIsWallpaperInitialized.get(i) == null) {
          this.mIsWallpaperInitialized.set(i, Boolean.valueOf(isWallpaperFileExists(i)));
        }
      } finally {
        timingsTraceAndSlog.traceEnd();
      }
    }
    synchronized (this.mLock) {
      int i2 = this.mCurrentUserId;
      if (i2 == i) {
        return;
      }
      this.mSemService.setOldUserId(i2);
      this.mSemService.setCurrentUserId(i);
      Log.addLogString(
          "WallpaperManagerService", "switchUser, change " + this.mCurrentUserId + " to " + i);
      this.mCurrentUserId = i;
      Log.m129d(
          "WallpaperManagerService", "switchUser: userId = " + i + ", lidState = " + getLidState());
      if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
        this.mSemService.mSubDisplayMode.updateLidStateFromInputManager();
      }
      WallpaperData wallpaperSafeLocked =
          Rune.SUPPORT_SUB_DISPLAY_MODE
              ? getWallpaperSafeLocked(i, 5)
              : getWallpaperSafeLocked(i, 1);
      WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i, 2);
      if (wallpaperSafeLocked != null && wallpaperSafeLocked2 != null) {
        if (isSystemAndLockPaired(i, WhichChecker.getMode(wallpaperSafeLocked.mWhich))) {
          wallpaperSafeLocked.mWhich |= 2;
        }
        File wallpaperLockDir = WallpaperUtils.getWallpaperLockDir(i);
        if (!wallpaperLockDir.exists()) {
          wallpaperLockDir.mkdirs();
        }
        if (i >= 0
            && (this.mIsWallpaperInitialized.get(i) == null
                || !((Boolean) this.mIsWallpaperInitialized.get(i)).booleanValue())) {
          setDefaultWallpapers(i);
          this.mIsWallpaperInitialized.set(i, Boolean.TRUE);
        }
        this.mCurrentUserId = i;
        this.mSemService.setCurrentUserId(i);
        WallpaperObserver wallpaperObserver = new WallpaperObserver(wallpaperSafeLocked);
        this.mWallpaperObserver = wallpaperObserver;
        if (wallpaperSafeLocked.wallpaperObserver == null) {
          FileObserver wallpaperFileObserver =
              wallpaperObserver.mSemObserver.getWallpaperFileObserver();
          wallpaperSafeLocked.wallpaperObserver = wallpaperFileObserver;
          wallpaperFileObserver.startWatching();
        }
        if (wallpaperSafeLocked2.wallpaperObserver == null) {
          FileObserver lockWallpaperFileObserver =
              this.mWallpaperObserver.mSemObserver.getLockWallpaperFileObserver();
          wallpaperSafeLocked2.wallpaperObserver = lockWallpaperFileObserver;
          lockWallpaperFileObserver.startWatching();
        }
        if (this.mIsLockscreenLiveWallpaperEnabled && !isSystemAndLockPaired(i, 4)) {
          switchWallpaper(wallpaperSafeLocked2, null);
        }
        switchWallpaper(wallpaperSafeLocked, iRemoteCallback);
        int i3 = 16;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
          WallpaperData wallpaperSafeLocked3 = getWallpaperSafeLocked(this.mCurrentUserId, 17);
          wallpaperSafeLocked3.mWhich |= 16;
          switchWallpaper(wallpaperSafeLocked3, null);
        }
        if (Rune.VIRTUAL_DISPLAY_WALLPAPER
            && this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(
                this.mActiveVirtualDisplayId)) {
          WallpaperData wallpaperSafeLocked4 = getWallpaperSafeLocked(this.mCurrentUserId, 33);
          wallpaperSafeLocked4.mWhich |= 32;
          WallpaperConnection wallpaperConnection = wallpaperSafeLocked4.connection;
          if (wallpaperConnection == null || wallpaperConnection.mService == null) {
            Log.m129d(
                "WallpaperManagerService",
                "switchUser: userId = "
                    + i
                    + ", mActiveVirtualDisplayId = "
                    + this.mActiveVirtualDisplayId);
            switchWallpaper(wallpaperSafeLocked4, null);
          }
        }
        if (getLidState() != 0) {
          i3 = 0;
        }
        notifyWallpaperColorsChanged(wallpaperSafeLocked, i3 | 1);
        notifyWallpaperColorsChanged(wallpaperSafeLocked2, i3 | 2);
        this.mSemService.handleWallpaperBindingTimeout(true, true);
        WallpaperData wallpaperSafeLocked5 =
            getWallpaperSafeLocked(
                this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(1));
        if (wallpaperSafeLocked5.mSemWallpaperData.getPrimarySemColors() == null) {
          this.mSemService.mLegibilityColor.extractColor(
              wallpaperSafeLocked5.mSemWallpaperData.getWhich());
        } else {
          notifySemColorListeners(wallpaperSafeLocked5);
        }
        WallpaperData wallpaperSafeLocked6 =
            getWallpaperSafeLocked(
                this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2));
        if (wallpaperSafeLocked6.mSemWallpaperData.getPrimarySemColors() == null) {
          this.mSemService.mLegibilityColor.extractColor(
              wallpaperSafeLocked6.mSemWallpaperData.getWhich());
        } else {
          notifySemColorListeners(wallpaperSafeLocked6);
        }
        if (i >= 0) {
          this.mSemService.loadSettingsLockedForSnapshot(i);
        }
        return;
      }
      Log.m130e("WallpaperManagerService", "switchUser wallpaper == null");
    }
  }

  public void switchWallpaper(WallpaperData wallpaperData, IRemoteCallback iRemoteCallback) {
    synchronized (this.mLock) {
      wallpaperData.mSemWallpaperData.setWaitingForUnlockUser(false);
      ComponentName componentName = wallpaperData.wallpaperComponent;
      if (componentName == null) {
        componentName = wallpaperData.nextWallpaperComponent;
      }
      ServiceInfo serviceInfo = null;
      if (this.mImageWallpaper.equals(componentName) && wallpaperData.getWallpaperType() == 7) {
        Slog.d(
            "WallpaperManagerService",
            "switchWallpaper: Type and ComponentName mismatch. Setting null ComponentName.");
        componentName = null;
      }
      if (!bindWallpaperComponentLocked(
          componentName,
          true,
          false,
          wallpaperData,
          iRemoteCallback,
          getCoverWallpaperInfo(componentName, wallpaperData.userId))) {
        try {
          serviceInfo =
              this.mIPackageManager.getServiceInfo(componentName, 262144L, wallpaperData.userId);
        } catch (RemoteException e) {
          Slog.w("WallpaperManagerService", "Failure starting previous wallpaper; clearing", e);
        }
        if (this.mIsLockscreenLiveWallpaperEnabled) {
          onSwitchWallpaperFailLocked(wallpaperData, iRemoteCallback, serviceInfo);
          return;
        }
        if (serviceInfo == null) {
          clearWallpaperLocked(false, 1, wallpaperData.userId, iRemoteCallback);
        } else {
          Slog.w(
              "WallpaperManagerService",
              "Wallpaper isn't direct boot aware; using fallback until unlocked");
          wallpaperData.wallpaperComponent = wallpaperData.nextWallpaperComponent;
          WallpaperData wallpaperData2 = new WallpaperData(wallpaperData.userId, 2);
          wallpaperData2.setWhichPending(wallpaperData.mWhich);
          bindWallpaperComponentLocked(
              this.mImageWallpaper, true, false, wallpaperData2, iRemoteCallback);
          wallpaperData.mSemWallpaperData.setWaitingForUnlockUser(true);
        }
      }
    }
  }

  public final void onSwitchWallpaperFailLocked(
      WallpaperData wallpaperData, IRemoteCallback iRemoteCallback, ServiceInfo serviceInfo) {
    if (serviceInfo == null) {
      if (WhichChecker.isSystemAndLock(wallpaperData.mWhich)) {
        int mode = WhichChecker.getMode(wallpaperData.mWhich);
        clearWallpaperLocked(false, mode | 1, wallpaperData.userId, null);
        clearWallpaperLocked(false, mode | 2, wallpaperData.userId, iRemoteCallback);
        return;
      }
      clearWallpaperLocked(false, wallpaperData.mWhich, wallpaperData.userId, iRemoteCallback);
      return;
    }
    Slog.w(
        "WallpaperManagerService",
        "Wallpaper isn't direct boot aware; using fallback until unlocked");
    wallpaperData.wallpaperComponent = wallpaperData.nextWallpaperComponent;
    WallpaperData wallpaperData2 = new WallpaperData(wallpaperData.userId, wallpaperData.mWhich);
    if (wallpaperData.wallpaperFile.exists()) {
      wallpaperData.wallpaperFile.delete();
      wallpaperData.cropFile.delete();
    }
    bindWallpaperComponentLocked(
        this.mImageWallpaper, true, false, wallpaperData2, iRemoteCallback);
    int i = wallpaperData.mWhich;
    if ((i & 1) != 0) {
      this.mHomeWallpaperWaitingForUnlock = true;
    }
    if ((i & 2) != 0) {
      this.mLockWallpaperWaitingForUnlock = true;
    }
  }

  public void clearWallpaper(String str, int i, int i2) {
    int i3;
    WallpaperData wallpaperData;
    int i4 = i;
    Log.m133v(
        "WallpaperManagerService", "clearWallpaper: callingPackage = " + str + ", which = " + i4);
    Log.addLogString(
        "WallpaperManagerService", "clearWallpaper: callingPackage = " + str + ", which = " + i4);
    checkPermission("android.permission.SET_WALLPAPER");
    if (isWallpaperSupported(str) && isSetWallpaperAllowed(str)) {
      if (!this.mSemService.isSupportingMode(i4)) {
        Log.m130e(
            "WallpaperManagerService",
            "clearWallpaper [" + WhichChecker.getMode(i) + "] mode isn't support");
        return;
      }
      int handleIncomingUser =
          ActivityManager.handleIncomingUser(
              Binder.getCallingPid(),
              Binder.getCallingUid(),
              i2,
              false,
              true,
              "clearWallpaper",
              null);
      synchronized (this.mLock) {
        if (!"com.samsung.android.themecenter".equals(str)) {
          i4 = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i4);
        }
        i3 = i4;
        this.mSemService.removeSnapshotByWhich(i3);
        clearWallpaperLocked(false, i3, handleIncomingUser, null, str);
        if (!WhichChecker.isLock(i3)) {
          wallpaperData = null;
        } else if (WhichChecker.isSubDisplay(i3)) {
          wallpaperData = this.mLockWallpaperMap.get(handleIncomingUser, 16);
        } else {
          wallpaperData = this.mLockWallpaperMap.get(handleIncomingUser, 4);
        }
        if (WhichChecker.isSystem(i3) || wallpaperData == null) {
          if (WhichChecker.isSubDisplay(i3)) {
            wallpaperData = this.mWallpaperMap.get(handleIncomingUser, 16);
          } else if (WhichChecker.isVirtualDisplay(i3)) {
            wallpaperData = this.mWallpaperMap.get(handleIncomingUser, 32);
          } else {
            wallpaperData = this.mWallpaperMap.get(handleIncomingUser, 4);
          }
        }
      }
      if (wallpaperData != null) {
        notifyWallpaperColorsChanged(wallpaperData, i3);
        notifyWallpaperColorsChanged(this.mFallbackWallpaper, 1);
      }
    }
  }

  public void clearWallpaperLocked(boolean z, int i, int i2, IRemoteCallback iRemoteCallback) {
    clearWallpaperLocked(z, i, i2, iRemoteCallback, null);
  }

  /* JADX WARN: Code restructure failed: missing block: B:170:0x02b3, code lost:

     if (android.app.WallpaperManager.isDefaultOperatorWallpaper(r16.mContext, r10) != false) goto L136;
  */
  /* JADX WARN: Removed duplicated region for block: B:144:0x032b  */
  /* JADX WARN: Removed duplicated region for block: B:154:0x032d  */
  /* JADX WARN: Removed duplicated region for block: B:156:0x0352  */
  /* JADX WARN: Removed duplicated region for block: B:28:0x0093 A[Catch: all -> 0x0362, TRY_ENTER, TryCatch #1 {all -> 0x0362, blocks: (B:14:0x0046, B:16:0x004c, B:18:0x0056, B:20:0x0061, B:22:0x0067, B:25:0x0072, B:28:0x0093, B:30:0x009e, B:32:0x00ae, B:35:0x00b3, B:37:0x00bc, B:38:0x00c6, B:40:0x00cc, B:42:0x012a, B:44:0x0157, B:46:0x015d, B:47:0x01fe, B:48:0x0174, B:50:0x017c, B:52:0x018d, B:53:0x0194, B:55:0x01a2, B:57:0x01a6, B:59:0x01aa, B:61:0x01b1, B:63:0x01bf, B:65:0x01cd, B:67:0x01d3, B:69:0x01d9, B:70:0x01ec, B:71:0x01e3, B:73:0x01fb, B:74:0x00d2, B:76:0x00da, B:78:0x00e2, B:82:0x0108, B:83:0x010e, B:85:0x0125, B:86:0x00ec, B:88:0x00fd, B:92:0x0208, B:94:0x020e, B:96:0x0211, B:187:0x0215, B:189:0x0219, B:99:0x0223, B:103:0x0230, B:105:0x0248, B:107:0x024e, B:109:0x0256, B:111:0x025d, B:113:0x0268, B:115:0x0292, B:117:0x0298, B:118:0x029a, B:121:0x02a0, B:123:0x02a6, B:126:0x02b5, B:128:0x02bd, B:131:0x02cb, B:134:0x02d3, B:138:0x0315, B:140:0x0321, B:142:0x0325, B:145:0x032f, B:147:0x0335, B:149:0x0339, B:151:0x0347, B:158:0x0353, B:162:0x0359, B:169:0x02ad, B:173:0x02dd, B:175:0x02e1, B:177:0x02e5, B:179:0x02ef, B:180:0x0306), top: B:13:0x0046, inners: #4, #5 }] */
  /* JADX WARN: Removed duplicated region for block: B:82:0x0108 A[Catch: all -> 0x0362, TryCatch #1 {all -> 0x0362, blocks: (B:14:0x0046, B:16:0x004c, B:18:0x0056, B:20:0x0061, B:22:0x0067, B:25:0x0072, B:28:0x0093, B:30:0x009e, B:32:0x00ae, B:35:0x00b3, B:37:0x00bc, B:38:0x00c6, B:40:0x00cc, B:42:0x012a, B:44:0x0157, B:46:0x015d, B:47:0x01fe, B:48:0x0174, B:50:0x017c, B:52:0x018d, B:53:0x0194, B:55:0x01a2, B:57:0x01a6, B:59:0x01aa, B:61:0x01b1, B:63:0x01bf, B:65:0x01cd, B:67:0x01d3, B:69:0x01d9, B:70:0x01ec, B:71:0x01e3, B:73:0x01fb, B:74:0x00d2, B:76:0x00da, B:78:0x00e2, B:82:0x0108, B:83:0x010e, B:85:0x0125, B:86:0x00ec, B:88:0x00fd, B:92:0x0208, B:94:0x020e, B:96:0x0211, B:187:0x0215, B:189:0x0219, B:99:0x0223, B:103:0x0230, B:105:0x0248, B:107:0x024e, B:109:0x0256, B:111:0x025d, B:113:0x0268, B:115:0x0292, B:117:0x0298, B:118:0x029a, B:121:0x02a0, B:123:0x02a6, B:126:0x02b5, B:128:0x02bd, B:131:0x02cb, B:134:0x02d3, B:138:0x0315, B:140:0x0321, B:142:0x0325, B:145:0x032f, B:147:0x0335, B:149:0x0339, B:151:0x0347, B:158:0x0353, B:162:0x0359, B:169:0x02ad, B:173:0x02dd, B:175:0x02e1, B:177:0x02e5, B:179:0x02ef, B:180:0x0306), top: B:13:0x0046, inners: #4, #5 }] */
  /* JADX WARN: Removed duplicated region for block: B:85:0x0125 A[Catch: all -> 0x0362, TryCatch #1 {all -> 0x0362, blocks: (B:14:0x0046, B:16:0x004c, B:18:0x0056, B:20:0x0061, B:22:0x0067, B:25:0x0072, B:28:0x0093, B:30:0x009e, B:32:0x00ae, B:35:0x00b3, B:37:0x00bc, B:38:0x00c6, B:40:0x00cc, B:42:0x012a, B:44:0x0157, B:46:0x015d, B:47:0x01fe, B:48:0x0174, B:50:0x017c, B:52:0x018d, B:53:0x0194, B:55:0x01a2, B:57:0x01a6, B:59:0x01aa, B:61:0x01b1, B:63:0x01bf, B:65:0x01cd, B:67:0x01d3, B:69:0x01d9, B:70:0x01ec, B:71:0x01e3, B:73:0x01fb, B:74:0x00d2, B:76:0x00da, B:78:0x00e2, B:82:0x0108, B:83:0x010e, B:85:0x0125, B:86:0x00ec, B:88:0x00fd, B:92:0x0208, B:94:0x020e, B:96:0x0211, B:187:0x0215, B:189:0x0219, B:99:0x0223, B:103:0x0230, B:105:0x0248, B:107:0x024e, B:109:0x0256, B:111:0x025d, B:113:0x0268, B:115:0x0292, B:117:0x0298, B:118:0x029a, B:121:0x02a0, B:123:0x02a6, B:126:0x02b5, B:128:0x02bd, B:131:0x02cb, B:134:0x02d3, B:138:0x0315, B:140:0x0321, B:142:0x0325, B:145:0x032f, B:147:0x0335, B:149:0x0339, B:151:0x0347, B:158:0x0353, B:162:0x0359, B:169:0x02ad, B:173:0x02dd, B:175:0x02e1, B:177:0x02e5, B:179:0x02ef, B:180:0x0306), top: B:13:0x0046, inners: #4, #5 }] */
  /* JADX WARN: Removed duplicated region for block: B:92:0x0208 A[Catch: all -> 0x0362, TRY_ENTER, TryCatch #1 {all -> 0x0362, blocks: (B:14:0x0046, B:16:0x004c, B:18:0x0056, B:20:0x0061, B:22:0x0067, B:25:0x0072, B:28:0x0093, B:30:0x009e, B:32:0x00ae, B:35:0x00b3, B:37:0x00bc, B:38:0x00c6, B:40:0x00cc, B:42:0x012a, B:44:0x0157, B:46:0x015d, B:47:0x01fe, B:48:0x0174, B:50:0x017c, B:52:0x018d, B:53:0x0194, B:55:0x01a2, B:57:0x01a6, B:59:0x01aa, B:61:0x01b1, B:63:0x01bf, B:65:0x01cd, B:67:0x01d3, B:69:0x01d9, B:70:0x01ec, B:71:0x01e3, B:73:0x01fb, B:74:0x00d2, B:76:0x00da, B:78:0x00e2, B:82:0x0108, B:83:0x010e, B:85:0x0125, B:86:0x00ec, B:88:0x00fd, B:92:0x0208, B:94:0x020e, B:96:0x0211, B:187:0x0215, B:189:0x0219, B:99:0x0223, B:103:0x0230, B:105:0x0248, B:107:0x024e, B:109:0x0256, B:111:0x025d, B:113:0x0268, B:115:0x0292, B:117:0x0298, B:118:0x029a, B:121:0x02a0, B:123:0x02a6, B:126:0x02b5, B:128:0x02bd, B:131:0x02cb, B:134:0x02d3, B:138:0x0315, B:140:0x0321, B:142:0x0325, B:145:0x032f, B:147:0x0335, B:149:0x0339, B:151:0x0347, B:158:0x0353, B:162:0x0359, B:169:0x02ad, B:173:0x02dd, B:175:0x02e1, B:177:0x02e5, B:179:0x02ef, B:180:0x0306), top: B:13:0x0046, inners: #4, #5 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void clearWallpaperLocked(
      boolean z, int i, int i2, IRemoteCallback iRemoteCallback, String str) {
    WallpaperData wallpaperData;
    boolean z2;
    IllegalArgumentException illegalArgumentException;
    WallpaperData wallpaperSafeLocked;
    ComponentName componentName;
    boolean z3;
    SemWallpaperData semWallpaperData;
    if (!WhichChecker.isSingleType(i)) {
      throw new IllegalArgumentException("Must specify exactly one kind of wallpaper to clear");
    }
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    int mode = WhichChecker.getMode(modeEnsuredWhich);
    if (WhichChecker.isLock(modeEnsuredWhich)) {
      wallpaperData = this.mLockWallpaperMap.get(i2, mode);
      if (wallpaperData == null) {
        return;
      }
    } else {
      wallpaperData = this.mWallpaperMap.get(i2, mode);
      if (wallpaperData == null) {
        loadSettingsLocked(i2, false, 1, mode);
        wallpaperData = this.mWallpaperMap.get(i2);
      }
    }
    WallpaperData wallpaperData2 = wallpaperData;
    if (wallpaperData2 == null) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
    if (wallpaperData2.getWallpaperFile() != null && wallpaperData2.getWallpaperFile().exists()) {
      wallpaperData2.getWallpaperFile().delete();
      File file = wallpaperData2.cropFile;
      if (file != null && file.exists() && wallpaperData2.cropFile.delete()) {
        z2 = true;
        resetWallpaperData(wallpaperData2);
        resetSemWallpaperData(wallpaperData2.mSemWallpaperData, wallpaperData2.userId);
        wallpaperData2.mSemWallpaperData.setExternalParams(null);
        this.mAssetFileManager.removeAssetFiles(modeEnsuredWhich, i2);
        String str2 = "(clear)";
        if (!WhichChecker.isLock(modeEnsuredWhich)) {
          FileObserver fileObserver = wallpaperData2.wallpaperObserver;
          wallpaperData2.cleanUp();
          if ("android.app.cts.wallpapers".equals(str)) {
            Log.m129d(
                "WallpaperManagerService", "clearWallpaperLocked (CTS): REMOVE LOCK WALLPAPER.");
            this.mLockWallpaperMap.remove(i2, WhichChecker.getMode(modeEnsuredWhich));
            if (!z2) {
              notifyCallbacksLocked(wallpaperData2);
            }
          } else if ("com.android.wallpaperbackup".equals(str)) {
            Log.m129d(
                "WallpaperManagerService",
                "clearWallpaperLocked: DO NOT RESTORE TO DEFAULT LOCK WALLPAPER.");
            notifyLockWallpaperChanged(-1, modeEnsuredWhich);
          } else {
            if ((WhichChecker.isPhone(modeEnsuredWhich)
                    || WhichChecker.isSubDisplay(modeEnsuredWhich))
                && getDefaultWallpaperType(modeEnsuredWhich) == 8
                && !WallpaperManager.isDefaultOperatorWallpaper(this.mContext, modeEnsuredWhich)) {
              if (!"com.android.wallpaper.livepicker".equals(str)) {
                SemWallpaperData semWallpaperData2 =
                    getWallpaperSafeLocked(i2, WhichChecker.getMode(modeEnsuredWhich) | 1)
                        .mSemWallpaperData;
                if (!semWallpaperData2.getIsPreloaded() || semWallpaperData2.getWpType() != 7) {
                  z3 = true;
                  if (!z3) {
                    Log.addLogString(
                        "WallpaperManagerService",
                        "clearWallpaperLocked: Reset to default video wallpaper. But we don't"
                            + " update settings.");
                    Log.m129d(
                        "WallpaperManagerService",
                        "clearWallpaperLocked: Reset to default video wallpaper. But we don't"
                            + " update settings.");
                  }
                  wallpaperData2.setCallingPackage(str + "(clear)");
                  initVideoWallpaper(modeEnsuredWhich, true, z3);
                  if (!z2) {
                    notifyCallbacksLocked(wallpaperData2);
                  }
                }
              }
              z3 = false;
              if (!z3) {}
              wallpaperData2.setCallingPackage(str + "(clear)");
              initVideoWallpaper(modeEnsuredWhich, true, z3);
              if (!z2) {}
            } else {
              this.mLockWallpaperMap.remove(i2, WhichChecker.getMode(modeEnsuredWhich));
              saveSettingsLocked(i2, mode);
              WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i2, modeEnsuredWhich);
              wallpaperSafeLocked2.mSemWallpaperData.setWallpaperHistories(
                  wallpaperData2.mSemWallpaperData.getWallpaperHistories());
              wallpaperSafeLocked2.setCallingPackage(str + "(clear)");
              try {
                if (isDefaultMultipack(i2, modeEnsuredWhich, str)) {
                  wallpaperSafeLocked2.mSemWallpaperData.setWpType(3);
                  wallpaperSafeLocked2.mSemWallpaperData.setUri(
                      getDefaultMultiPackUri(modeEnsuredWhich));
                  notifyCallbacksLocked(wallpaperData2);
                  notifyMultipackApplied(modeEnsuredWhich);
                } else if (!this.mSemWallpaperResourcesInfo.isDefaultMultipack(modeEnsuredWhich)
                    || WallpaperManager.isDefaultOperatorWallpaper(
                        this.mContext, 2, this.mSemService.mCMFWallpaper.getDeviceColor())) {
                  wallpaperSafeLocked2.mSemWallpaperData.setWpType(0);
                  WallpaperData wallpaperData3 = this.mWallpaperMap.get(i2, mode);
                  if (wallpaperData3 == null
                      || (semWallpaperData = wallpaperData3.mSemWallpaperData) == null
                      || wallpaperData3.wallpaperComponent == null
                      || semWallpaperData.getWpType() != 7
                      || !wallpaperData3.wallpaperComponent.equals(
                          getDefaultPreloadedLiveWallpaperComponentName(mode | 1))) {
                    notifyLockWallpaperChanged(
                        wallpaperSafeLocked2.mSemWallpaperData.getWpType(), modeEnsuredWhich);
                  } else {
                    wallpaperSafeLocked2.mSemWallpaperData.setWpType(-1);
                    setKWPTypeLiveWallpaperWithMode(mode, 0);
                    if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY
                        && getLidState() == 0
                        && WhichChecker.isPhone(modeEnsuredWhich)) {
                      notifyLockWallpaperChanged(
                          wallpaperSafeLocked2.mSemWallpaperData.getWpType(), modeEnsuredWhich);
                    }
                  }
                  this.mSemService.mLegibilityColor.extractColor(
                      wallpaperSafeLocked2.mSemWallpaperData.getWhich());
                } else {
                  notifyCallbacksLocked(wallpaperData2);
                  notifyMultipackApplied(modeEnsuredWhich);
                }
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
            }
            getWallpaperSafeLocked(i2, modeEnsuredWhich).wallpaperObserver = fileObserver;
          }
          return;
        }
        if (isCoverVideoWallpaperDefault(modeEnsuredWhich)) {
          initCoverVideoWallpaper();
        }
        try {
          WallpaperData wallpaperData4 = this.mLastWallpaper;
          if (wallpaperData4 != null) {
            try {
              ComponentName componentName2 = wallpaperData4.wallpaperComponent;
              if (componentName2 != null) {
                componentName2.getClassName();
              }
            } catch (IllegalArgumentException e2) {
              illegalArgumentException = e2;
              str2 = "WallpaperManagerService";
            }
          }
          wallpaperData2.primaryColors = null;
          wallpaperData2.imageWallpaperPending = false;
        } catch (IllegalArgumentException e3) {
          e = e3;
          str2 = "WallpaperManagerService";
        }
        if (i2 != this.mCurrentUserId) {
          return;
        }
        wallpaperData2.setCallingPackage(str + "(clear)");
        if (WhichChecker.isSystem(modeEnsuredWhich)
            && WhichChecker.isPhone(modeEnsuredWhich)
            && !WallpaperManager.isDefaultOperatorWallpaper(this.mContext, modeEnsuredWhich)
            && getDefaultWallpaperType(modeEnsuredWhich) == 7) {
          wallpaperData2.mSemWallpaperData.setWpType(7);
          componentName = getDefaultPreloadedLiveWallpaperComponentName(modeEnsuredWhich);
          if (componentName != null) {
            Log.m129d(
                "WallpaperManagerService",
                "clearWallpaperLocked: Set default preload live wallpaper, which = "
                    + modeEnsuredWhich
                    + ", componentName = "
                    + componentName);
            wallpaperData2.mSemWallpaperData.setIsPreloaded(true);
            wallpaperData2.wallpaperComponent = componentName;
            putDefaultLiveWallpaperProperties(wallpaperData2);
          }
        } else {
          componentName = null;
        }
        if (str != null && str.contains("android.app.cts.wallpapers")) {
          componentName = this.mImageWallpaper;
        }
        try {
        } catch (IllegalArgumentException e4) {
          e = e4;
          illegalArgumentException = e;
          Slog.e(str2, "Default wallpaper component not found!", illegalArgumentException);
          clearWallpaperComponentLocked(wallpaperData2);
          if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            wallpaperSafeLocked =
                getWallpaperSafeLocked(i2, !WhichChecker.isSubDisplay(modeEnsuredWhich) ? 5 : 17);
            if (wallpaperSafeLocked != null) {
              Log.m132i(
                  str2,
                  "clearWallpaperLocked: default failed on both screen. So bind wallpaper agian");
              return;
            }
          }
          if (iRemoteCallback != null) {}
          return;
        }
        if (!canBindComponentNow(modeEnsuredWhich)) {
          if (Rune.SUPPORT_SUB_DISPLAY_MODE && (modeEnsuredWhich & 1) == 1) {
            if (this.mImageWallpaper.equals(wallpaperData2.wallpaperComponent)) {
              Log.m129d(
                  "WallpaperManagerService",
                  "clearWallpaperLocked: send command to update cached wallpaper for homescreen.");
              Bundle bundle = new Bundle();
              bundle.putInt("which", modeEnsuredWhich);
              semSendWallpaperCommand(modeEnsuredWhich, "refresh_cache", bundle);
            } else {
              clearWallpaperComponentLocked(wallpaperData2);
              wallpaperData2.nextWallpaperComponent = null;
            }
          }
          return;
        }
        if (isCoverVideoWallpaperDefault(modeEnsuredWhich)
            || getDefaultWallpaperType(modeEnsuredWhich) == 7) {}
        wallpaperData2.mSemWallpaperData.setWpType(0);
        str2 = "WallpaperManagerService";
        if (bindWallpaperComponentLocked(
            z ? this.mImageWallpaper : componentName,
            true,
            false,
            wallpaperData2,
            iRemoteCallback)) {
          if (!z2) {
            notifyCallbacksLocked(wallpaperData2);
          }
          return;
        }
        illegalArgumentException = null;
        Slog.e(str2, "Default wallpaper component not found!", illegalArgumentException);
        clearWallpaperComponentLocked(wallpaperData2);
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
          wallpaperSafeLocked =
              getWallpaperSafeLocked(i2, !WhichChecker.isSubDisplay(modeEnsuredWhich) ? 5 : 17);
          if (wallpaperSafeLocked != null
              && wallpaperSafeLocked.wallpaperComponent == null
              && bindWallpaperComponentLocked(
                  this.mImageWallpaper, true, false, wallpaperSafeLocked, iRemoteCallback)) {
            Log.m132i(
                str2,
                "clearWallpaperLocked: default failed on both screen. So bind wallpaper agian");
            return;
          }
        }
        if (iRemoteCallback != null) {
          try {
            iRemoteCallback.sendResult((Bundle) null);
          } catch (RemoteException e5) {
            Slog.w(str2, "Failed to notify callback after wallpaper clear", e5);
          }
        }
        return;
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    z2 = false;
    resetWallpaperData(wallpaperData2);
    resetSemWallpaperData(wallpaperData2.mSemWallpaperData, wallpaperData2.userId);
    wallpaperData2.mSemWallpaperData.setExternalParams(null);
    this.mAssetFileManager.removeAssetFiles(modeEnsuredWhich, i2);
    String str22 = "(clear)";
    if (!WhichChecker.isLock(modeEnsuredWhich)) {}
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final boolean isCoverVideoWallpaperDefault(int i) {
    return Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY
        && WhichChecker.isSubDisplay(i)
        && WhichChecker.isSystem(i)
        && getDefaultWallpaperType(i) == 8;
  }

  public final boolean hasCrossUserPermission() {
    return this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL")
        == 0;
  }

  public boolean hasNamedWallpaper(String str) {
    int callingUserId = UserHandle.getCallingUserId();
    boolean hasCrossUserPermission = hasCrossUserPermission();
    synchronized (this.mLock) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        List<UserInfo> users = ((UserManager) this.mContext.getSystemService("user")).getUsers();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        for (UserInfo userInfo : users) {
          if (hasCrossUserPermission || callingUserId == userInfo.id) {
            if (!userInfo.isManagedProfile()) {
              WallpaperData wallpaperData = this.mWallpaperMap.get(userInfo.id);
              if (wallpaperData == null) {
                loadSettingsLocked(userInfo.id, false, 3);
                wallpaperData = this.mWallpaperMap.get(userInfo.id);
              }
              if (wallpaperData != null && str.equals(wallpaperData.name)) {
                return true;
              }
            }
          }
        }
        return false;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
  }

  public void setDimensionHints(int i, int i2, String str, int i3) {
    checkPermission("android.permission.SET_WALLPAPER_HINTS");
    if (isWallpaperSupported(str)) {
      int min = Math.min(i, GLHelper.getMaxTextureSize());
      int min2 = Math.min(i2, GLHelper.getMaxTextureSize());
      synchronized (this.mLock) {
        int callingUserId = UserHandle.getCallingUserId();
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(callingUserId, 1);
        if (min <= 0 || min2 <= 0) {
          throw new IllegalArgumentException("width and height must be > 0");
        }
        if (!this.mWallpaperDisplayHelper.isValidDisplay(i3)) {
          throw new IllegalArgumentException("Cannot find display with id=" + i3);
        }
        WallpaperDisplayHelper.DisplayData displayDataOrCreate =
            this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i3);
        if (min != displayDataOrCreate.mWidth || min2 != displayDataOrCreate.mHeight) {
          displayDataOrCreate.mWidth = min;
          displayDataOrCreate.mHeight = min2;
          if (i3 == 0) {
            saveSettingsLocked(callingUserId);
          }
          if (this.mCurrentUserId != callingUserId) {
            return;
          }
          WallpaperConnection wallpaperConnection = wallpaperSafeLocked.connection;
          if (wallpaperConnection != null) {
            DisplayConnector displayConnectorOrCreate =
                wallpaperConnection.getDisplayConnectorOrCreate(i3);
            IWallpaperEngine iWallpaperEngine =
                displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
            if (iWallpaperEngine != null) {
              try {
                iWallpaperEngine.setDesiredSize(min, min2);
              } catch (RemoteException e) {
                Slog.w("WallpaperManagerService", "Failed to set desired size", e);
              }
              notifyCallbacksLocked(wallpaperSafeLocked);
            } else if (wallpaperSafeLocked.connection.mService != null
                && displayConnectorOrCreate != null) {
              displayConnectorOrCreate.mDimensionsChanged = true;
            }
          }
        }
      }
    }
  }

  public int getWidthHint(int i) {
    synchronized (this.mLock) {
      if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
        throw new IllegalArgumentException("Cannot find display with id=" + i);
      }
      if (this.mWallpaperMap.get(UserHandle.getCallingUserId()) == null) {
        return 0;
      }
      return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mWidth;
    }
  }

  public int getHeightHint(int i) {
    synchronized (this.mLock) {
      if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
        throw new IllegalArgumentException("Cannot find display with id=" + i);
      }
      if (this.mWallpaperMap.get(UserHandle.getCallingUserId()) == null) {
        return 0;
      }
      return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mHeight;
    }
  }

  public void setDisplayPadding(Rect rect, String str, int i) {
    checkPermission("android.permission.SET_WALLPAPER_HINTS");
    if (isWallpaperSupported(str)) {
      synchronized (this.mLock) {
        if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
          throw new IllegalArgumentException("Cannot find display with id=" + i);
        }
        int callingUserId = UserHandle.getCallingUserId();
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(callingUserId, 1);
        if (rect.left < 0 || rect.top < 0 || rect.right < 0 || rect.bottom < 0) {
          throw new IllegalArgumentException("padding must be positive: " + rect);
        }
        int maximumSizeDimension = this.mWallpaperDisplayHelper.getMaximumSizeDimension(i);
        int i2 = rect.left + rect.right;
        int i3 = rect.top + rect.bottom;
        if (i2 > maximumSizeDimension) {
          throw new IllegalArgumentException(
              "padding width " + i2 + " exceeds max width " + maximumSizeDimension);
        }
        if (i3 > maximumSizeDimension) {
          throw new IllegalArgumentException(
              "padding height " + i3 + " exceeds max height " + maximumSizeDimension);
        }
        WallpaperDisplayHelper.DisplayData displayDataOrCreate =
            this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i);
        if (!rect.equals(displayDataOrCreate.mPadding)) {
          displayDataOrCreate.mPadding.set(rect);
          if (i == 0) {
            saveSettingsLocked(callingUserId);
          }
          if (this.mCurrentUserId != callingUserId) {
            return;
          }
          WallpaperConnection wallpaperConnection = wallpaperSafeLocked.connection;
          if (wallpaperConnection != null) {
            DisplayConnector displayConnectorOrCreate =
                wallpaperConnection.getDisplayConnectorOrCreate(i);
            IWallpaperEngine iWallpaperEngine =
                displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
            if (iWallpaperEngine != null) {
              try {
                iWallpaperEngine.setDisplayPadding(rect);
              } catch (RemoteException e) {
                Slog.w("WallpaperManagerService", "Failed to set display padding", e);
              }
              notifyCallbacksLocked(wallpaperSafeLocked);
            } else if (wallpaperSafeLocked.connection.mService != null
                && displayConnectorOrCreate != null) {
              displayConnectorOrCreate.mPaddingChanged = true;
            }
          }
        }
      }
    }
  }

  public ParcelFileDescriptor getWallpaper(
      String str,
      IWallpaperManagerCallback iWallpaperManagerCallback,
      int i,
      Bundle bundle,
      int i2) {
    return getWallpaperWithFeature(
        str, null, iWallpaperManagerCallback, i, bundle, i2, true, false, -1);
  }

  public ParcelFileDescriptor getWallpaperWithFeature(
      String str,
      String str2,
      IWallpaperManagerCallback iWallpaperManagerCallback,
      int i,
      Bundle bundle,
      int i2,
      boolean z,
      boolean z2,
      int i3) {
    int folderStateBasedWhich;
    WallpaperData peekWallpaperDataLocked;
    if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL")) {
      ((StorageManager) this.mContext.getSystemService(StorageManager.class))
          .checkPermissionReadImages(
              true, Binder.getCallingPid(), Binder.getCallingUid(), str, str2);
    }
    synchronized (this.mLock) {
      folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
      peekWallpaperDataLocked = peekWallpaperDataLocked(i2, folderStateBasedWhich);
    }
    return getWallpaper(
        iWallpaperManagerCallback,
        folderStateBasedWhich,
        bundle,
        i2,
        (i3 == 0 || peekWallpaperDataLocked == null)
            ? 0
            : peekWallpaperDataLocked.mSemWallpaperData.getWpType(),
        z2,
        z);
  }

  public ParcelFileDescriptor getLockWallpaper(
      IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) {
    int callingUid = Binder.getCallingUid();
    if (!isSignedWithPlatformSignature(callingUid)) {
      throw new SecurityException(
          "The calling app does not have the required permission. uid = " + callingUid);
    }
    return getWallpaper(iWallpaperManagerCallback, i2, bundle, i, 0, true, true);
  }

  public final boolean isSignedWithPlatformSignature(int i) {
    return this.mContext.getPackageManager().checkSignatures(1000, i) == 0;
  }

  public final ParcelFileDescriptor getWallpaper(
      IWallpaperManagerCallback iWallpaperManagerCallback,
      int i,
      Bundle bundle,
      int i2,
      int i3,
      boolean z,
      boolean z2) {
    Log.m129d(
        "WallpaperManagerService",
        "getWallpaper: which = "
            + i
            + ", wallpaperUserId = "
            + i2
            + ", wpType = "
            + i3
            + ", isDexEnabled = "
            + this.mSemService.mDesktopMode.isDesktopModeEnabled(i)
            + ", getCropped = "
            + z2
            + ", includeCopiedFile = "
            + z);
    if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER
        && this.mSemService.mDesktopMode.isDesktopSingleMode()) {
      i = (i & (-9)) | 4;
    }
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "getWallpaper", null);
    if (!WhichChecker.isSingleType(modeEnsuredWhich)) {
      throw new IllegalArgumentException("Must specify exactly one kind of wallpaper to read");
    }
    try {
      if (this.mStatusBarService.isSysUiSafeModeEnabled()) {
        return null;
      }
    } catch (Exception e) {
      Slog.e("WallpaperManagerService", "SAFEMODE Exception occurs! " + e.getMessage());
    }
    synchronized (this.mLock) {
      WallpaperDataManager wallpaperDataManager =
          WhichChecker.isLock(modeEnsuredWhich) ? this.mLockWallpaperMap : this.mWallpaperMap;
      WallpaperData wallpaperData =
          wallpaperDataManager.get(handleIncomingUser, WhichChecker.getMode(modeEnsuredWhich));
      if (wallpaperData == null) {
        int mode = WhichChecker.getMode(modeEnsuredWhich);
        loadSettingsLocked(handleIncomingUser, false, modeEnsuredWhich, mode);
        wallpaperData = wallpaperDataManager.get(handleIncomingUser, mode);
        if (wallpaperData == null) {
          return null;
        }
      }
      boolean isPreloadedLiveWallpaper =
          this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(wallpaperData);
      boolean waitingForUnlockUser = wallpaperData.mSemWallpaperData.getWaitingForUnlockUser();
      if (isPreloadedLiveWallpaper) {
        if (waitingForUnlockUser) {
          Log.m134w("WallpaperManagerService", "Error getting wallpaper before unlock user.");
          return null;
        }
        try {
          int mode2 = WhichChecker.getMode(modeEnsuredWhich);
          if (WhichChecker.isLock(modeEnsuredWhich) && isSystemAndLockPaired(mode2)) {
            modeEnsuredWhich = mode2 | 1;
          }
          File thumbnailFile =
              this.mLiveWallpaperHelper.getThumbnailFile(modeEnsuredWhich, handleIncomingUser, 1);
          if (thumbnailFile != null) {
            return ParcelFileDescriptor.open(thumbnailFile, 268435456);
          }
          return null;
        } catch (FileNotFoundException e2) {
          Log.m135w("WallpaperManagerService", "Error getting live wallpaper", e2);
          return null;
        }
      }
      synchronized (this.mLock) {
        Log.m129d(
            "WallpaperManagerService",
            "getWallpaper: which = "
                + modeEnsuredWhich
                + ", isCopied = "
                + wallpaperData.mSemWallpaperData.getIsCopied());
        if (!z
            && WhichChecker.isLock(modeEnsuredWhich)
            && wallpaperData.mSemWallpaperData.getIsCopied()) {
          Log.m129d("WallpaperManagerService", "getWallpaper: Returns null.");
          return null;
        }
        WallpaperDisplayHelper.DisplayData displayDataOrCreate =
            this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        if (bundle != null) {
          try {
            bundle.putInt("width", displayDataOrCreate.mWidth);
            bundle.putInt("height", displayDataOrCreate.mHeight);
          } catch (FileNotFoundException e3) {
            Slog.w("WallpaperManagerService", "Error getting wallpaper", e3);
            return null;
          }
        }
        if (iWallpaperManagerCallback != null) {
          wallpaperData.callbacks.register(iWallpaperManagerCallback);
        }
        File file = z2 ? wallpaperData.cropFile : wallpaperData.wallpaperFile;
        if (WhichChecker.isLock(modeEnsuredWhich)) {
          file =
              this.mSemService.mLockWallpaper.getWallpaperFile(
                  file, wallpaperData.mSemWallpaperData);
        } else if (i3 == 8) {
          file = wallpaperData.mSemWallpaperData.getVideoFirstFrameFile();
        }
        if (file != null && file.exists()) {
          return ParcelFileDescriptor.open(file, 268435456);
        }
        return null;
      }
    }
  }

  public boolean isWaitingForUnlockUser(int i, int i2) {
    WallpaperData wallpaperSafeLocked;
    synchronized (this.mLock) {
      wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
    }
    if (wallpaperSafeLocked == null) {
      return false;
    }
    Slog.d(
        "WallpaperManagerService",
        "which : "
            + i
            + ", isWaitingForUnlockUser : "
            + wallpaperSafeLocked.mSemWallpaperData.getWaitingForUnlockUser()
            + " (called by userId= "
            + i2
            + ")");
    return wallpaperSafeLocked.mSemWallpaperData.getWaitingForUnlockUser();
  }

  public final boolean hasPermission(String str) {
    return this.mContext.checkCallingOrSelfPermission(str) == 0;
  }

  public WallpaperInfo getWallpaperInfo(int i) {
    return getWallpaperInfoWithFlags(1, i);
  }

  public WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) {
    WallpaperConnection wallpaperConnection;
    WallpaperInfo wallpaperInfo;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(),
            Binder.getCallingUid(),
            i2,
            false,
            true,
            "getWallpaperInfo",
            null);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(i, handleIncomingUser);
      if (peekPairingConsideredWallpaperDataLocked != null
          && (wallpaperConnection = peekPairingConsideredWallpaperDataLocked.connection) != null
          && (wallpaperInfo = wallpaperConnection.mInfo) != null) {
        if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL")
            && !this.mPackageManagerInternal.canQueryPackage(
                Binder.getCallingUid(), wallpaperInfo.getComponent().getPackageName())) {
          return null;
        }
        return wallpaperInfo;
      }
      return null;
    }
  }

  public ParcelFileDescriptor getWallpaperInfoFile(int i) {
    synchronized (this.mLock) {
      try {
        try {
          File file = new File(WallpaperUtils.getWallpaperDir(i), "wallpaper_info.xml");
          if (!file.exists()) {
            return null;
          }
          return ParcelFileDescriptor.open(file, 268435456);
        } catch (FileNotFoundException e) {
          Slog.w("WallpaperManagerService", "Error getting wallpaper info file", e);
          return null;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public int getWallpaperIdForUser(int i, int i2) {
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(),
            Binder.getCallingUid(),
            i2,
            false,
            true,
            "getWallpaperIdForUser",
            null);
    if (!WhichChecker.isSingleType(i)) {
      throw new IllegalArgumentException("Must specify exactly one kind of wallpaper");
    }
    boolean isLock = WhichChecker.isLock(i);
    WallpaperDataManager wallpaperDataManager =
        isLock ? this.mLockWallpaperMap : this.mWallpaperMap;
    synchronized (this.mLock) {
      WallpaperData wallpaperData =
          wallpaperDataManager.get(handleIncomingUser, WhichChecker.getMode(i));
      if (wallpaperData != null) {
        if (isLock && wallpaperData.mSemWallpaperData.getIsCopied()) {
          Slog.w("WallpaperManagerService", "getWallpaperIdForUser which = " + i + " , return -1");
          return -1;
        }
        Slog.w("WallpaperManagerService", "getWallpaperIdForUser wallpaper = " + wallpaperData);
        return wallpaperData.wallpaperId;
      }
      Slog.w(
          "WallpaperManagerService", "getWallpaperIdForUser which = " + i + " , return -1 default");
      return -1;
    }
  }

  public void registerWallpaperColorsCallback(
      IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(),
            Binder.getCallingUid(),
            i,
            true,
            true,
            "registerWallpaperColorsCallback",
            null);
    synchronized (this.mLock) {
      SparseArray sparseArray = (SparseArray) this.mColorsChangedListeners.get(handleIncomingUser);
      if (sparseArray == null) {
        sparseArray = new SparseArray();
        this.mColorsChangedListeners.put(handleIncomingUser, sparseArray);
      }
      RemoteCallbackList remoteCallbackList = (RemoteCallbackList) sparseArray.get(i2);
      if (remoteCallbackList == null) {
        remoteCallbackList = new RemoteCallbackList();
        sparseArray.put(i2, remoteCallbackList);
      }
      remoteCallbackList.register(iWallpaperManagerCallback);
    }
  }

  public void unregisterWallpaperColorsCallback(
      IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
    RemoteCallbackList remoteCallbackList;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(),
            Binder.getCallingUid(),
            i,
            true,
            true,
            "unregisterWallpaperColorsCallback",
            null);
    synchronized (this.mLock) {
      SparseArray sparseArray = (SparseArray) this.mColorsChangedListeners.get(handleIncomingUser);
      if (sparseArray != null
          && (remoteCallbackList = (RemoteCallbackList) sparseArray.get(i2)) != null) {
        remoteCallbackList.unregister(iWallpaperManagerCallback);
      }
    }
  }

  public void setInAmbientMode(boolean z, long j) {
    IWallpaperEngine iWallpaperEngine;
    WallpaperConnection wallpaperConnection;
    WallpaperInfo wallpaperInfo;
    IWallpaperEngine iWallpaperEngine2;
    if (this.mIsLockscreenLiveWallpaperEnabled) {
      ArrayList arrayList = new ArrayList();
      synchronized (this.mLock) {
        this.mInAmbientMode = z;
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          WallpaperInfo wallpaperInfo2 = wallpaperData.connection.mInfo;
          if ((wallpaperInfo2 == null || wallpaperInfo2.supportsAmbientMode())
              && (iWallpaperEngine2 =
                      wallpaperData.connection.getDisplayConnectorOrCreate(0).mEngine)
                  != null) {
            arrayList.add(iWallpaperEngine2);
          }
        }
      }
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        try {
          ((IWallpaperEngine) it.next()).setInAmbientMode(z, j);
        } catch (RemoteException e) {
          Slog.w("WallpaperManagerService", "Failed to set ambient mode", e);
        }
      }
      return;
    }
    synchronized (this.mLock) {
      this.mInAmbientMode = z;
      WallpaperData wallpaperData2 = this.mWallpaperMap.get(this.mCurrentUserId);
      iWallpaperEngine =
          (wallpaperData2 == null
                  || (wallpaperConnection = wallpaperData2.connection) == null
                  || !((wallpaperInfo = wallpaperConnection.mInfo) == null
                      || wallpaperInfo.supportsAmbientMode()))
              ? null
              : wallpaperData2.connection.getDisplayConnectorOrCreate(0).mEngine;
    }
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.setInAmbientMode(z, j);
      } catch (RemoteException e2) {
        Slog.w("WallpaperManagerService", "Failed to set ambient mode", e2);
      }
    }
  }

  public void notifyWakingUp(final int i, final int i2, final Bundle bundle) {
    WallpaperConnection wallpaperConnection;
    checkCallerIsSystemOrSystemUi();
    synchronized (this.mLock) {
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          wallpaperData.connection.forEachDisplayConnector(
              new Consumer() { // from class:
                               // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WallpaperManagerService.lambda$notifyWakingUp$9(
                      i, i2, bundle, (WallpaperManagerService.DisplayConnector) obj);
                }
              });
        }
        return;
      }
      WallpaperData wallpaperData2 = this.mWallpaperMap.get(this.mCurrentUserId);
      if (wallpaperData2 != null && (wallpaperConnection = wallpaperData2.connection) != null) {
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda16
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$notifyWakingUp$10(
                    i, i2, bundle, (WallpaperManagerService.DisplayConnector) obj);
              }
            });
      }
    }
  }

  public static /* synthetic */ void lambda$notifyWakingUp$9(
      int i, int i2, Bundle bundle, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand("android.wallpaper.wakingup", i, i2, -1, bundle);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to dispatch COMMAND_WAKING_UP", e);
      }
    }
  }

  public static /* synthetic */ void lambda$notifyWakingUp$10(
      int i, int i2, Bundle bundle, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand("android.wallpaper.wakingup", i, i2, -1, bundle);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to dispatch COMMAND_WAKING_UP", e);
      }
    }
  }

  public void notifyGoingToSleep(final int i, final int i2, final Bundle bundle) {
    WallpaperConnection wallpaperConnection;
    checkCallerIsSystemOrSystemUi();
    synchronized (this.mLock) {
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          wallpaperData.connection.forEachDisplayConnector(
              new Consumer() { // from class:
                               // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WallpaperManagerService.lambda$notifyGoingToSleep$11(
                      i, i2, bundle, (WallpaperManagerService.DisplayConnector) obj);
                }
              });
        }
        return;
      }
      WallpaperData wallpaperData2 = this.mWallpaperMap.get(this.mCurrentUserId);
      if (wallpaperData2 != null && (wallpaperConnection = wallpaperData2.connection) != null) {
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda10
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$notifyGoingToSleep$12(
                    i, i2, bundle, (WallpaperManagerService.DisplayConnector) obj);
              }
            });
      }
    }
  }

  public static /* synthetic */ void lambda$notifyGoingToSleep$11(
      int i, int i2, Bundle bundle, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.goingtosleep", i, i2, -1, bundle);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to dispatch COMMAND_GOING_TO_SLEEP", e);
      }
    }
  }

  public static /* synthetic */ void lambda$notifyGoingToSleep$12(
      int i, int i2, Bundle bundle, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.goingtosleep", i, i2, -1, bundle);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to dispatch COMMAND_GOING_TO_SLEEP", e);
      }
    }
  }

  public final void notifyScreenTurnedOn(int i) {
    WallpaperConnection wallpaperConnection;
    IWallpaperEngine iWallpaperEngine;
    IWallpaperEngine iWallpaperEngine2;
    synchronized (this.mLock) {
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          if (wallpaperData.connection.containsDisplay(i)
              && (iWallpaperEngine2 =
                      wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine)
                  != null) {
            try {
              iWallpaperEngine2.onScreenTurnedOn();
            } catch (RemoteException e) {
              Slog.w("WallpaperManagerService", "Failed to notify that the screen turned on", e);
            }
          }
        }
        return;
      }
      ArrayList activeWallpapers = this.mWallpaperMap.getActiveWallpapers(i);
      if (activeWallpapers == null) {
        Slog.w(
            "WallpaperManagerService",
            "Active wallpaper is null. Failed to notify that the screen is turned on");
        return;
      }
      Iterator it = activeWallpapers.iterator();
      while (it.hasNext()) {
        WallpaperData wallpaperData2 = (WallpaperData) it.next();
        if (wallpaperData2 != null
            && (wallpaperConnection = wallpaperData2.connection) != null
            && wallpaperConnection.containsDisplay(i)
            && (iWallpaperEngine = wallpaperData2.connection.getDisplayConnectorOrCreate(i).mEngine)
                != null) {
          try {
            iWallpaperEngine.onScreenTurnedOn();
          } catch (RemoteException unused) {
            Slog.w(
                "WallpaperManagerService",
                "Failed to notify that the screen turned on to " + wallpaperData2);
          }
        }
      }
      return;
    }
  }

  public final void notifyScreenTurningOn(int i) {
    WallpaperConnection wallpaperConnection;
    IWallpaperEngine iWallpaperEngine;
    IWallpaperEngine iWallpaperEngine2;
    synchronized (this.mLock) {
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          if (wallpaperData.connection.containsDisplay(i)
              && (iWallpaperEngine2 =
                      wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine)
                  != null) {
            try {
              iWallpaperEngine2.onScreenTurningOn();
            } catch (RemoteException e) {
              Slog.w(
                  "WallpaperManagerService", "Failed to notify that the screen is turning on", e);
            }
          }
        }
        return;
      }
      ArrayList activeWallpapers = this.mWallpaperMap.getActiveWallpapers(i);
      if (activeWallpapers == null) {
        Slog.w(
            "WallpaperManagerService",
            "Active wallpaper is null. Failed to notify that the screen is turning on");
        return;
      }
      Iterator it = activeWallpapers.iterator();
      while (it.hasNext()) {
        WallpaperData wallpaperData2 = (WallpaperData) it.next();
        if (wallpaperData2 != null
            && (wallpaperConnection = wallpaperData2.connection) != null
            && wallpaperConnection.containsDisplay(i)
            && (iWallpaperEngine = wallpaperData2.connection.getDisplayConnectorOrCreate(i).mEngine)
                != null) {
          try {
            iWallpaperEngine.onScreenTurningOn();
          } catch (RemoteException unused) {
            Slog.w(
                "WallpaperManagerService",
                "Failed to notify that the screen turning on to " + wallpaperData2);
          }
        }
      }
      return;
    }
  }

  public final void notifyKeyguardGoingAway() {
    WallpaperConnection wallpaperConnection;
    synchronized (this.mLock) {
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        for (WallpaperData wallpaperData : getActiveWallpapers()) {
          wallpaperData.connection.forEachDisplayConnector(
              new Consumer() { // from class:
                               // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda26
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WallpaperManagerService.lambda$notifyKeyguardGoingAway$13(
                      (WallpaperManagerService.DisplayConnector) obj);
                }
              });
        }
        return;
      }
      WallpaperData wallpaperData2 = this.mWallpaperMap.get(this.mCurrentUserId);
      if (wallpaperData2 != null && (wallpaperConnection = wallpaperData2.connection) != null) {
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda27
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$notifyKeyguardGoingAway$14(
                    (WallpaperManagerService.DisplayConnector) obj);
              }
            });
      }
    }
  }

  public static /* synthetic */ void lambda$notifyKeyguardGoingAway$13(
      DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.keyguardgoingaway", -1, -1, -1, new Bundle());
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to notify that the keyguard is going away", e);
      }
    }
  }

  public static /* synthetic */ void lambda$notifyKeyguardGoingAway$14(
      DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.keyguardgoingaway", -1, -1, -1, new Bundle());
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to notify that the keyguard is going away", e);
      }
    }
  }

  public void notifyAodVisibilityState(final int i) {
    WallpaperConnection wallpaperConnection;
    checkCallerIsSystemOrSystemUi();
    this.mSemService.setAodVisibilityState(i);
    synchronized (this.mLock) {
      WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
      if (wallpaperData != null && (wallpaperConnection = wallpaperData.connection) != null) {
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda21
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$notifyAodVisibilityState$15(
                    i, (WallpaperManagerService.DisplayConnector) obj);
              }
            });
      }
    }
  }

  public static /* synthetic */ void lambda$notifyAodVisibilityState$15(
      int i, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.aodstate", i, -1, -1, new Bundle());
      } catch (RemoteException e) {
        Slog.w(
            "WallpaperManagerService",
            "notifyAodVisibilityState: Failed to dispatch COMMAND_AOD_STATE",
            e);
      }
    }
  }

  public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
    checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW");
    synchronized (this.mLock) {
      Log.m129d("WallpaperManagerService", "setLockWallpaperCallback()");
      Iterator it = this.mKeyguardListenerList.iterator();
      while (it.hasNext()) {
        if (((IWallpaperManagerCallback) it.next()) == iWallpaperManagerCallback) {
          Log.m129d("WallpaperManagerService", "IWallpaperManagerCallback is already added.");
          return true;
        }
      }
      this.mKeyguardListenerClientList.add(
          new WallpaperManagerCallbackClient(iWallpaperManagerCallback));
      this.mKeyguardListenerList.add(iWallpaperManagerCallback);
      return true;
    }
  }

  public boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
    checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW");
    synchronized (this.mLock) {
      Log.m129d("WallpaperManagerService", "setCoverWallpaperCallback()");
      Iterator it = this.mCoverWallpaperListenerList.iterator();
      while (it.hasNext()) {
        if (((IWallpaperManagerCallback) it.next()) == iWallpaperManagerCallback) {
          Log.m129d("WallpaperManagerService", "IWallpaperManagerCallback is already added.");
          return true;
        }
      }
      this.mCoverWallpaperListenerClientList.add(
          new WallpaperManagerCallbackClient(iWallpaperManagerCallback));
      this.mCoverWallpaperListenerList.add(iWallpaperManagerCallback);
      return true;
    }
  }

  public final WallpaperData[] getActiveWallpapers() {
    WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
    WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId);
    boolean z = (wallpaperData == null || wallpaperData.connection == null) ? false : true;
    boolean z2 = (wallpaperData2 == null || wallpaperData2.connection == null) ? false : true;
    if (z && z2) {
      return new WallpaperData[] {wallpaperData, wallpaperData2};
    }
    if (z) {
      return new WallpaperData[] {wallpaperData};
    }
    return z2 ? new WallpaperData[] {wallpaperData2} : new WallpaperData[0];
  }

  public final IWallpaperEngine getEngine(int i, int i2, int i3) {
    WallpaperConnection wallpaperConnection;
    WallpaperData findWallpaperAtDisplay = findWallpaperAtDisplay(i2, i3, WhichChecker.getMode(i));
    IWallpaperEngine iWallpaperEngine = null;
    if (findWallpaperAtDisplay == null
        || (wallpaperConnection = findWallpaperAtDisplay.connection) == null) {
      return null;
    }
    synchronized (this.mLock) {
      for (int i4 = 0; i4 < wallpaperConnection.mDisplayConnector.size(); i4++) {
        int i5 = ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
        int i6 = ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
        if (i5 == i3 || i6 == i) {
          iWallpaperEngine =
              ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mEngine;
          break;
        }
      }
    }
    return iWallpaperEngine;
  }

  public void addOnLocalColorsChangedListener(
      ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i, int i2, int i3) {
    if (!WhichChecker.isSingleType(i)) {
      throw new IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
    }
    IWallpaperEngine engine = getEngine(i, i2, i3);
    if (engine == null) {
      return;
    }
    synchronized (this.mLock) {
      this.mLocalColorRepo.addAreas(iLocalWallpaperColorConsumer, list, i3);
    }
    engine.addLocalColorsAreas(list);
  }

  public void removeOnLocalColorsChangedListener(
      ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i, int i2, int i3) {
    if (!WhichChecker.isSingleType(i)) {
      throw new IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
    }
    if (Binder.getCallingUserHandle().getIdentifier() != i2) {
      throw new SecurityException("calling user id does not match");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    List list2 = null;
    try {
      synchronized (this.mLock) {
        list2 = this.mLocalColorRepo.removeAreas(iLocalWallpaperColorConsumer, list, i3);
      }
    } catch (Exception unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    IWallpaperEngine engine = getEngine(i, i2, i3);
    if (engine == null || list2 == null || list2.size() <= 0) {
      return;
    }
    engine.removeLocalColorsAreas(list2);
  }

  public boolean lockScreenWallpaperExists() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mLockWallpaperMap.get(this.mCurrentUserId) != null;
    }
    return z;
  }

  public boolean isStaticWallpaper(int i) {
    synchronized (this.mLock) {
      WallpaperData wallpaperData =
          (WhichChecker.isLock(i) ? this.mLockWallpaperMap : this.mWallpaperMap)
              .get(this.mCurrentUserId);
      if (wallpaperData == null) {
        return false;
      }
      return this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
    }
  }

  public void setWallpaperDimAmount(float f) {
    setWallpaperDimAmountForUid(Binder.getCallingUid(), f);
  }

  public void setWallpaperDimAmountForUid(int i, float f) {
    WallpaperConnection wallpaperConnection;
    checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
        WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId);
        if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          wallpaperData.mUidToDimAmount.remove(i);
        } else {
          wallpaperData.mUidToDimAmount.put(i, Float.valueOf(f));
        }
        final float highestDimAmountFromMap =
            getHighestDimAmountFromMap(wallpaperData.mUidToDimAmount);
        wallpaperData.mWallpaperDimAmount = highestDimAmountFromMap;
        if (wallpaperData2 != null) {
          wallpaperData2.mWallpaperDimAmount = highestDimAmountFromMap;
        }
        if (this.mIsLockscreenLiveWallpaperEnabled) {
          boolean z = false;
          for (WallpaperData wallpaperData3 : getActiveWallpapers()) {
            if (wallpaperData3 != null
                && (wallpaperConnection = wallpaperData3.connection) != null) {
              wallpaperConnection.forEachDisplayConnector(
                  new Consumer() { // from class:
                                   // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      WallpaperManagerService.lambda$setWallpaperDimAmountForUid$16(
                          highestDimAmountFromMap, (WallpaperManagerService.DisplayConnector) obj);
                    }
                  });
              wallpaperData3.mIsColorExtractedFromDim = true;
              notifyWallpaperColorsChanged(wallpaperData3, wallpaperData3.mWhich);
              z = true;
            }
          }
          if (z) {
            saveSettingsLocked(wallpaperData.userId);
          }
        } else {
          WallpaperConnection wallpaperConnection2 = wallpaperData.connection;
          if (wallpaperConnection2 != null) {
            wallpaperConnection2.forEachDisplayConnector(
                new Consumer() { // from class:
                                 // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda6
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    WallpaperManagerService.lambda$setWallpaperDimAmountForUid$17(
                        highestDimAmountFromMap, (WallpaperManagerService.DisplayConnector) obj);
                  }
                });
            wallpaperData.mIsColorExtractedFromDim = true;
            notifyWallpaperColorsChanged(wallpaperData, 1);
            if (wallpaperData2 != null) {
              wallpaperData2.mIsColorExtractedFromDim = true;
              notifyWallpaperColorsChanged(wallpaperData2, 2);
            }
            saveSettingsLocked(wallpaperData.userId);
          }
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$setWallpaperDimAmountForUid$16(
      float f, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.applyDimming(f);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Can't apply dimming on wallpaper display connector", e);
      }
    }
  }

  public static /* synthetic */ void lambda$setWallpaperDimAmountForUid$17(
      float f, DisplayConnector displayConnector) {
    IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
    if (iWallpaperEngine != null) {
      try {
        iWallpaperEngine.applyDimming(f);
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Can't apply dimming on wallpaper display connector", e);
      }
    }
  }

  public float getWallpaperDimAmount() {
    checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
    synchronized (this.mLock) {
      WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
      if (wallpaperData == null && (wallpaperData = this.mWallpaperMap.get(0)) == null) {
        Slog.e("WallpaperManagerService", "getWallpaperDimAmount: wallpaperData is null");
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
      return wallpaperData.mWallpaperDimAmount;
    }
  }

  public final float getHighestDimAmountFromMap(SparseArray sparseArray) {
    float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    for (int i = 0; i < sparseArray.size(); i++) {
      f = Math.max(f, ((Float) sparseArray.valueAt(i)).floatValue());
    }
    return f;
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x006b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public WallpaperColors getWallpaperColors(int i, int i2, int i3) {
    boolean z;
    if (!WhichChecker.isSingleType(i)) {
      throw new IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
    }
    int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
    Log.m129d(
        "WallpaperManagerService",
        "getWallpaperColors: which = " + folderStateBasedWhich + " , userId = " + i2);
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            Binder.getCallingPid(),
            Binder.getCallingUid(),
            i2,
            false,
            true,
            "getWallpaperColors",
            null);
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked =
          WhichChecker.isLock(folderStateBasedWhich)
              ? peekWallpaperDataLocked(handleIncomingUser, folderStateBasedWhich)
              : null;
      if (peekWallpaperDataLocked == null) {
        peekWallpaperDataLocked =
            findWallpaperAtDisplay(
                handleIncomingUser, i3, WhichChecker.getMode(folderStateBasedWhich));
      }
      if (peekWallpaperDataLocked == null) {
        return null;
      }
      if (peekWallpaperDataLocked.primaryColors != null
          && !peekWallpaperDataLocked.mIsColorExtractedFromDim) {
        z = false;
        if (z) {
          extractColors(peekWallpaperDataLocked);
        }
        return getAdjustedWallpaperColorsOnDimming(peekWallpaperDataLocked);
      }
      z = true;
      if (z) {}
      return getAdjustedWallpaperColorsOnDimming(peekWallpaperDataLocked);
    }
  }

  public WallpaperColors getAdjustedWallpaperColorsOnDimming(WallpaperData wallpaperData) {
    synchronized (this.mLock) {
      WallpaperColors wallpaperColors = wallpaperData.primaryColors;
      if (wallpaperColors == null
          || (wallpaperColors.getColorHints() & 4) != 0
          || wallpaperData.mWallpaperDimAmount == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        return wallpaperColors;
      }
      return new WallpaperColors(
          wallpaperColors.getPrimaryColor(),
          wallpaperColors.getSecondaryColor(),
          wallpaperColors.getTertiaryColor(),
          wallpaperColors.getColorHints() & (-2) & (-3));
    }
  }

  public final WallpaperData findWallpaperAtDisplay(int i, int i2, int i3) {
    WallpaperConnection wallpaperConnection;
    WallpaperData wallpaperData = this.mFallbackWallpaper;
    if (wallpaperData != null
        && (wallpaperConnection = wallpaperData.connection) != null
        && wallpaperConnection.containsDisplay(i2)) {
      return this.mFallbackWallpaper;
    }
    return this.mWallpaperMap.get(i, i3);
  }

  public ParcelFileDescriptor setWallpaper(
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
      Bundle bundle2) {
    String str3;
    Rect rect2;
    int i4;
    WallpaperData peekWallpaperDataLocked;
    ParcelFileDescriptor updateWallpaperBitmapLocked;
    Rect rect3 = rect;
    int i5 = i;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            IWallpaperManager.Stub.getCallingPid(),
            IWallpaperManager.Stub.getCallingUid(),
            i2,
            false,
            true,
            "changing wallpaper",
            null);
    StringBuilder sb = new StringBuilder();
    sb.append("setWallpaper() name:");
    sb.append(str);
    sb.append(" callingPackage = ");
    sb.append(str2);
    sb.append(" cropHint = ");
    sb.append(rect3);
    sb.append(" allowBackup = ");
    sb.append(z);
    sb.append(" which = 0x");
    sb.append(Integer.toHexString(i));
    sb.append(" wpType = ");
    sb.append(i3);
    sb.append(" userId = ");
    sb.append(handleIncomingUser);
    sb.append(" current userId = ");
    sb.append(this.mCurrentUserId);
    sb.append(" extras = ");
    if (bundle2 != null) {
      str3 = bundle2.keySet().size() + " fields";
    } else {
      str3 = "null";
    }
    sb.append(str3);
    sb.append(" isDexEnabled = ");
    sb.append(this.mSemService.mDesktopMode.isDesktopModeEnabled(i5));
    Log.m129d("WallpaperManagerService", sb.toString());
    checkPermission("android.permission.SET_WALLPAPER");
    if (bundle2 != null
        && Binder.getCallingUid() != 1000
        && !"com.android.systemui".equals(str2)
        && !"com.samsung.android.app.dressroom".equals(str2)) {
      throw new SecurityException(
          "Only the system or SystemUI may invoke setWallpaper() with extras");
    }
    if ((i5 & 3) == 0) {
      Slog.e("WallpaperManagerService", "Must specify a valid wallpaper category to set");
      throw new IllegalArgumentException("Must specify a valid wallpaper category to set");
    }
    if (!isWallpaperSupported(str2) || !isSetWallpaperAllowed(str2)) {
      Log.m130e("WallpaperManagerService", "setWallpaper callingPackage is wrong.");
      return null;
    }
    if (!this.mSemService.isSupportingMode(i5)) {
      Log.m130e(
          "WallpaperManagerService",
          "setWallpaper [" + WhichChecker.getMode(i) + "] mode isn't support");
      return null;
    }
    if (rect3 == null) {
      rect3 = new Rect(0, 0, 0, 0);
    } else if (rect.width() < 0 || rect.height() < 0 || rect3.left < 0 || rect3.top < 0) {
      throw new IllegalArgumentException("Invalid crop rect supplied: " + rect3);
    }
    if (SemDualAppManager.isDualAppId(handleIncomingUser)) {
      handleIncomingUser = 0;
    }
    if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER
        && this.mSemService.mDesktopMode.isDesktopSingleMode()) {
      i5 = (i5 & (-9)) | 4;
    }
    if (!"com.samsung.android.themecenter".equals(str2) && !"com.android.systemui".equals(str2)) {
      i5 = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i5);
    }
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i5);
    int mode = WhichChecker.getMode(modeEnsuredWhich);
    if (!WhichChecker.isVirtualDisplay(modeEnsuredWhich)) {
      this.mSemService.mDefaultWallpaper.updateTransparencySettingIfNeed(
          str2, modeEnsuredWhich, z2);
    }
    synchronized (this.mLock) {
      WallpaperData wallpaperData = this.mWallpaperMap.get(handleIncomingUser);
      boolean z3 =
          wallpaperData != null && this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
      boolean isSystemAndLockPaired = isSystemAndLockPaired(handleIncomingUser, mode);
      if (WhichChecker.isSystem(modeEnsuredWhich) && isSystemAndLockPaired) {
        if (z3) {
          WallpaperData peekWallpaperDataLocked2 =
              peekWallpaperDataLocked(handleIncomingUser, mode | 2);
          if (peekWallpaperDataLocked2 == null) {
            Slog.i(
                "WallpaperManagerService",
                "Migrating current wallpaper to be lock-only before updating system wallpaper");
            if (!migrateStaticSystemToLockWallpaperLocked(handleIncomingUser, modeEnsuredWhich)
                && !isLockscreenLiveWallpaperEnabled()) {
              modeEnsuredWhich |= 2;
            }
          } else {
            peekWallpaperDataLocked2.mSemWallpaperData.setIsCopied(false);
          }
        } else if (this.mIsPreviewLockLiveWallpaperEnabled) {
          WallpaperData wallpaperSafeLocked =
              getWallpaperSafeLocked(handleIncomingUser, modeEnsuredWhich);
          int wpType = wallpaperSafeLocked.mSemWallpaperData.getWpType();
          boolean isPreloadedLiveWallpaperComponent =
              this.mLiveWallpaperHelper.isPreloadedLiveWallpaperComponent(
                  wallpaperSafeLocked.wallpaperComponent);
          boolean isSupportSystemAndLockPairOnly =
              this.mLiveWallpaperHelper.isSupportSystemAndLockPairOnly(
                  wallpaperSafeLocked.wallpaperComponent);
          StringBuilder sb2 = new StringBuilder();
          rect2 = rect3;
          sb2.append("setWallpaper: isPreloadedLiveComponent = ");
          sb2.append(isPreloadedLiveWallpaperComponent);
          sb2.append("isSystemAndLockPairOnly = ");
          sb2.append(isSupportSystemAndLockPairOnly);
          Log.m129d("WallpaperManagerService", sb2.toString());
          if (wpType == 7 && isPreloadedLiveWallpaperComponent && !isSupportSystemAndLockPairOnly) {
            Slog.i(
                "WallpaperManagerService",
                "Migrating current live wallpaper to be lock-only before updating system"
                    + " wallpaper");
            if (migrateLiveSystemToLockWallpaperLocked(mode, handleIncomingUser)) {
              setKWPTypeLiveWallpaperWithMode(mode, 1);
              int i6 = mode | 2;
              notifyLockWallpaperChanged(7, i6);
              this.mSemService.mLegibilityColor.extractColor(i6);
            } else {
              modeEnsuredWhich |= 2;
            }
          }
        }
        rect2 = rect3;
      } else {
        rect2 = rect3;
        if (WhichChecker.isLock(modeEnsuredWhich)
            && isSystemAndLockPaired
            && (peekWallpaperDataLocked =
                    peekWallpaperDataLocked(handleIncomingUser, (i4 = mode | 1)))
                != null) {
          peekWallpaperDataLocked.mWhich = i4;
          updateEngineFlags(peekWallpaperDataLocked);
        }
      }
      setKWPTypeLiveWallpaperWithMode(mode, 1);
      WallpaperData wallpaperSafeLocked2 =
          getWallpaperSafeLocked(handleIncomingUser, modeEnsuredWhich);
      if (wallpaperSafeLocked2.mSemWallpaperData.getWpType() == 7) {
        resetSemWallpaperData(wallpaperSafeLocked2.mSemWallpaperData, wallpaperSafeLocked2.userId);
      }
      checkWallpaperData(handleIncomingUser, wallpaperSafeLocked2, i3, modeEnsuredWhich);
      if (this.mPendingMigrationViaStatic != null) {
        Slog.w(
            "WallpaperManagerService",
            "Starting new static wp migration before previous migration finished");
      }
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        this.mPendingMigrationViaStatic =
            new WallpaperDestinationChangeHandler(
                wallpaperSafeLocked2, WhichChecker.getMode(modeEnsuredWhich));
      }
      writeAssetFiles(modeEnsuredWhich, handleIncomingUser, bundle2);
      if (bundle2 != null) {
        bundle2.remove("assetFiles");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      this.mSemService.removeOriginalSavedFile(str2, modeEnsuredWhich);
      try {
        updateWallpaperBitmapLocked =
            updateWallpaperBitmapLocked(str, wallpaperSafeLocked2, bundle);
        if (updateWallpaperBitmapLocked != null) {
          wallpaperSafeLocked2.imageWallpaperPending = true;
          wallpaperSafeLocked2.mSystemWasBoth = isSystemAndLockPaired;
          wallpaperSafeLocked2.mWhich = modeEnsuredWhich;
          wallpaperSafeLocked2.setComplete = iWallpaperManagerCallback;
          wallpaperSafeLocked2.fromForegroundApp = isFromForegroundApp(str2);
          wallpaperSafeLocked2.cropHint.set(rect2);
          wallpaperSafeLocked2.allowBackup = z;
          wallpaperSafeLocked2.mWallpaperDimAmount = getWallpaperDimAmount();
          wallpaperSafeLocked2.wallpaperComponent = this.mImageWallpaper;
          wallpaperSafeLocked2.mSemWallpaperData.setIsPreloaded(z2);
          wallpaperSafeLocked2.mSemWallpaperData.setWpType(i3);
          wallpaperSafeLocked2.setCallingPackage(str2);
          wallpaperSafeLocked2.mSemWallpaperData.setIsCopied(false);
          int i7 = bundle2 != null ? bundle2.getInt("orientation", 0) : 0;
          if (i7 == 0) {
            i7 = this.mSemService.getOrientation();
          }
          wallpaperSafeLocked2.mSemWallpaperData.setOrientation(i7);
          wallpaperSafeLocked2.mSemWallpaperData.setUri(null);
          wallpaperSafeLocked2.mSemWallpaperData.setExternalParams(bundle2);
          Log.m129d("WallpaperManagerService", "setWallpaper() updated");
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return updateWallpaperBitmapLocked;
  }

  public final boolean migrateStaticSystemToLockWallpaperLocked(int i, int i2) {
    int mode = WhichChecker.getMode(i2);
    WallpaperData wallpaperData = this.mWallpaperMap.get(i, mode);
    if (wallpaperData == null) {
      return true;
    }
    WallpaperData wallpaperData2 =
        new WallpaperData(
            i,
            WallpaperUtils.getWallpaperLockDir(i),
            SemWallpaperManagerService.getFileName(i2, 1, 0),
            SemWallpaperManagerService.getFileName(i2, 1, 1));
    wallpaperData2.wallpaperId = wallpaperData.wallpaperId;
    wallpaperData2.cropHint.set(wallpaperData.cropHint);
    wallpaperData2.allowBackup = wallpaperData.allowBackup;
    wallpaperData2.primaryColors = wallpaperData.primaryColors;
    wallpaperData2.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
    int i3 = mode | 2;
    wallpaperData2.mWhich = i3;
    wallpaperData2.mSemWallpaperData.setWidth(wallpaperData.mSemWallpaperData.getWidth());
    wallpaperData2.mSemWallpaperData.setHeight(wallpaperData.mSemWallpaperData.getHeight());
    try {
      Slog.e(
          "WallpaperManagerService",
          "migrateStaticSystemToLockWallpaperLocked : "
              + wallpaperData.wallpaperFile
              + " , "
              + wallpaperData2.wallpaperFile);
      File file = wallpaperData.wallpaperFile;
      if (file != null && file.exists()) {
        Os.rename(
            wallpaperData.wallpaperFile.getAbsolutePath(),
            wallpaperData2.wallpaperFile.getAbsolutePath());
      }
      File file2 = wallpaperData.cropFile;
      if (file2 != null && file2.exists()) {
        Os.rename(
            wallpaperData.cropFile.getAbsolutePath(), wallpaperData2.cropFile.getAbsolutePath());
      }
      this.mAssetFileManager.moveAssetFiles(mode | 1, i3, i);
      this.mLockWallpaperMap.put(i, mode, wallpaperData2);
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        SELinux.restorecon(wallpaperData2.wallpaperFile);
        this.mLastLockWallpaper = wallpaperData2;
      }
      return true;
    } catch (ErrnoException e) {
      Slog.w("WallpaperManagerService", "Couldn't migrate system wallpaper: " + e.getMessage());
      wallpaperData2.wallpaperFile.delete();
      wallpaperData2.cropFile.delete();
      return false;
    }
  }

  public ParcelFileDescriptor updateWallpaperBitmapLocked(
      String str, WallpaperData wallpaperData, Bundle bundle) {
    return updateWallpaperBitmapLocked(str, wallpaperData, bundle, 1);
  }

  public ParcelFileDescriptor updateWallpaperBitmapLocked(
      String str, WallpaperData wallpaperData, Bundle bundle, int i) {
    File wallpaperDir;
    if (str == null) {
      str = "";
    }
    try {
      if ((i & 2) != 0) {
        wallpaperDir = WallpaperUtils.getWallpaperLockDir(wallpaperData.userId);
      } else {
        wallpaperDir = WallpaperUtils.getWallpaperDir(wallpaperData.userId);
      }
      if (!wallpaperDir.exists()) {
        wallpaperDir.mkdir();
        FileUtils.setPermissions(wallpaperDir.getPath(), 505, -1, -1);
      }
      File file = wallpaperData.wallpaperFile;
      ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 1006632960);
      if (file != null && !SELinux.restorecon(file)) {
        Log.m130e("WallpaperManagerService", "fail to restorecon");
        return null;
      }
      wallpaperData.name = str;
      int makeWallpaperIdLocked = WallpaperUtils.makeWallpaperIdLocked();
      wallpaperData.wallpaperId = makeWallpaperIdLocked;
      if (bundle != null) {
        bundle.putInt("android.service.wallpaper.extra.ID", makeWallpaperIdLocked);
      }
      wallpaperData.primaryColors = null;
      if (wallpaperData.wallpaperFile != null) {
        Log.m133v(
            "WallpaperManagerService",
            "updateWallpaperBitmapLocked: which = "
                + i
                + ", id = "
                + wallpaperData.wallpaperId
                + ", name = "
                + str
                + ",file = "
                + wallpaperData.wallpaperFile.getName());
      }
      return open;
    } catch (FileNotFoundException e) {
      Slog.w("WallpaperManagerService", "Error setting wallpaper", e);
      return null;
    }
  }

  public void setWallpaperComponentChecked(
      ComponentName componentName, String str, int i, int i2, Bundle bundle) {
    if (isWallpaperSupported(str) && isSetWallpaperAllowed(str)) {
      setWallpaperComponent(componentName, str, i, i2, bundle);
    }
  }

  public void setWallpaperComponent(ComponentName componentName) {
    setWallpaperComponent(componentName, "", 1, UserHandle.getCallingUserId(), null);
  }

  public Bundle getWallpaperComponentExtras(int i, int i2) {
    return getWallpaperExtras(i, i2);
  }

  public Bundle getWallpaperExtras(int i, int i2) {
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, i2);
      if (peekPairingConsideredWallpaperDataLocked == null) {
        Slog.i("WallpaperManagerService", "getWallpaperExtras: wallpaper data is null");
        return null;
      }
      return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getExternalParams();
    }
  }

  public Bundle getWallpaperAssets(int i, int i2) {
    boolean hasPermission = hasPermission("android.permission.READ_WALLPAPER_INTERNAL");
    boolean z = Binder.getCallingUid() == 1000;
    if (!hasPermission && !z) {
      throw new SecurityException("No permission to invoke getWallpaperAssetFile()");
    }
    return this.mAssetFileManager.getAssets(i, i2);
  }

  public ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) {
    boolean hasPermission = hasPermission("android.permission.READ_WALLPAPER_INTERNAL");
    boolean z = Binder.getCallingUid() == 1000;
    if (!hasPermission && !z) {
      throw new SecurityException("No permission to invoke getWallpaperAssetFile()");
    }
    return this.mAssetFileManager.getAssetFile(i, i2, str2);
  }

  public int getWallpaperOrientation(int i, int i2) {
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(i, i2);
      if (peekPairingConsideredWallpaperDataLocked == null) {
        Slog.i("WallpaperManagerService", "getWallpaperOrientation: wallpaper data is null");
        return 0;
      }
      return peekPairingConsideredWallpaperDataLocked.getOrientation();
    }
  }

  public final void setWallpaperComponent(int i, ComponentName componentName) {
    setWallpaperComponentInternalLegacy(componentName, "", i, UserHandle.getCallingUserId(), null);
  }

  public void setWallpaperComponent(
      ComponentName componentName, String str, int i, int i2, Bundle bundle) {
    if (this.mIsLockscreenLiveWallpaperEnabled) {
      setWallpaperComponentInternal(componentName, str, i, i2, bundle);
    } else {
      setWallpaperComponentInternalLegacy(componentName, str, i, i2, bundle);
    }
  }

  public final void setWallpaperComponentInternal(
      ComponentName componentName, String str, int i, int i2, Bundle bundle) {
    int i3;
    boolean z;
    WallpaperData wallpaperData;
    boolean z2;
    WallpaperData wallpaperData2;
    Object obj;
    boolean z3;
    boolean z4;
    boolean z5;
    int modeEnsuredWhich;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            IWallpaperManager.Stub.getCallingPid(),
            IWallpaperManager.Stub.getCallingUid(),
            i2,
            false,
            true,
            "changing live wallpaper",
            null);
    checkPermission("android.permission.SET_WALLPAPER_COMPONENT");
    boolean z6 = getCoverWallpaperInfo(componentName, handleIncomingUser) != null;
    if (WhichChecker.isModeAbsent(i)) {
      if (z6) {
        modeEnsuredWhich = WhichChecker.getType(i) | 16;
      } else {
        modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
      }
      i3 = modeEnsuredWhich;
    } else {
      i3 = i;
    }
    boolean z7 = WhichChecker.isSubDisplay(i3) && z6;
    int mode = WhichChecker.getMode(i3);
    Slog.v(
        "WallpaperManagerService", "setWallpaperComponent name=" + componentName + ", which=" + i3);
    Object obj2 = this.mLock;
    synchronized (obj2) {
      try {
        try {
          WallpaperData wallpaperData3 = this.mWallpaperMap.get(handleIncomingUser, mode);
          if (wallpaperData3 == null) {
            throw new IllegalStateException(
                "Wallpaper not yet initialized for user " + handleIncomingUser);
          }
          boolean equals = this.mImageWallpaper.equals(wallpaperData3.wallpaperComponent);
          boolean isSystemAndLockPaired = isSystemAndLockPaired(handleIncomingUser, mode);
          if (WhichChecker.isSystem(i3) && isSystemAndLockPaired && equals) {
            Slog.i(
                "WallpaperManagerService",
                "Migrating current wallpaper to be lock-only beforeupdating system wallpaper");
            migrateStaticSystemToLockWallpaperLocked(handleIncomingUser, i3);
          }
          WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(handleIncomingUser, i3);
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            wallpaperSafeLocked.imageWallpaperPending = false;
            wallpaperSafeLocked.mWhich = i3;
            wallpaperSafeLocked.mSystemWasBoth = isSystemAndLockPaired;
            wallpaperSafeLocked.fromForegroundApp = isFromForegroundApp(str);
            final WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler =
                new WallpaperDestinationChangeHandler(wallpaperSafeLocked, mode);
            boolean changingToSame = changingToSame(componentName, wallpaperSafeLocked);
            IRemoteCallback.Stub stub =
                new IRemoteCallback
                    .Stub() { // from class: com.android.server.wallpaper.WallpaperManagerService.6
                  public void sendResult(Bundle bundle2) {
                    wallpaperDestinationChangeHandler.complete();
                  }
                };
            boolean isPreloadedLiveWallpaper = isPreloadedLiveWallpaper(componentName, bundle, i3);
            if (!isPreloadLiveWallpaperReApplied(componentName, wallpaperSafeLocked, bundle)) {
              resetSemWallpaperData(
                  wallpaperSafeLocked.mSemWallpaperData, wallpaperSafeLocked.userId);
            }
            boolean z8 = changingToSame && isSystemAndLockPaired && WhichChecker.isSystem(i3);
            try {
              if (canBindComponentNow(i3)) {
                if (!z8) {
                  if (!this.mImageWallpaper.equals(wallpaperSafeLocked.wallpaperComponent)) {
                    z5 = false;
                    z = isPreloadedLiveWallpaper;
                    boolean z9 = z5;
                    z2 = isSystemAndLockPaired;
                    wallpaperData2 = wallpaperData3;
                    obj = obj2;
                    z3 =
                        bindWallpaperComponentLocked(
                            componentName, z9, true, wallpaperSafeLocked, stub);
                    wallpaperData = wallpaperSafeLocked;
                  }
                }
                z5 = true;
                z = isPreloadedLiveWallpaper;
                boolean z92 = z5;
                z2 = isSystemAndLockPaired;
                wallpaperData2 = wallpaperData3;
                obj = obj2;
                z3 =
                    bindWallpaperComponentLocked(
                        componentName, z92, true, wallpaperSafeLocked, stub);
                wallpaperData = wallpaperSafeLocked;
              } else {
                z = isPreloadedLiveWallpaper;
                wallpaperData = wallpaperSafeLocked;
                z2 = isSystemAndLockPaired;
                wallpaperData2 = wallpaperData3;
                obj = obj2;
                wallpaperData.wallpaperComponent = componentName;
                z3 = false;
              }
              if (z3) {
                if (!changingToSame) {
                  wallpaperData.primaryColors = null;
                } else {
                  WallpaperConnection wallpaperConnection = wallpaperData.connection;
                  if (wallpaperConnection != null) {
                    wallpaperConnection.forEachDisplayConnector(
                        new Consumer() { // from class:
                                         // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda12
                          @Override // java.util.function.Consumer
                          public final void accept(Object obj3) {
                            WallpaperManagerService.lambda$setWallpaperComponentInternal$18(
                                (WallpaperManagerService.DisplayConnector) obj3);
                          }
                        });
                  }
                }
                wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                if (!componentName.equals(this.mImageWallpaper)) {
                  wallpaperData.mSemWallpaperData.setWpType(7);
                } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                  if (wallpaperData.mSemWallpaperData.getWpType() != 3) {
                    wallpaperData.mSemWallpaperData.setWpType(0);
                  }
                } else {
                  wallpaperData.mSemWallpaperData.setWpType(0);
                }
                wallpaperData.mSemWallpaperData.setExternalParams(bundle);
                wallpaperData.mSemWallpaperData.setIsPreloaded(z);
                wallpaperData.setCallingPackage(str);
                wallpaperData.cleanUp();
                notifyCallbacksLocked(wallpaperData);
                if (WhichChecker.isSystemAndLock(i3)) {
                  WallpaperData wallpaperData4 =
                      this.mLockWallpaperMap.get(wallpaperData.userId, mode);
                  if (wallpaperData4 != null) {
                    detachWallpaperLocked(wallpaperData4);
                    if (changingToSame) {
                      updateEngineFlags(wallpaperData);
                    }
                  }
                  this.mLockWallpaperMap.remove(wallpaperData.userId, mode);
                } else if (WhichChecker.isLock(i3) && z2) {
                  wallpaperData2.mWhich = mode | 1;
                  updateEngineFlags(wallpaperData2);
                }
                z4 = true;
              } else {
                z4 = false;
              }
              Binder.restoreCallingIdentity(clearCallingIdentity);
              if (z4) {
                notifyWallpaperColorsChanged(wallpaperData, i3);
                notifyWallpaperColorsChanged(this.mFallbackWallpaper, 1);
              }
              if (canBindComponentNow(i3)) {
                this.mSemService.mLegibilityColor.extractColor(
                    getWallpaperSafeLocked(handleIncomingUser, mode | 2)
                        .mSemWallpaperData
                        .getWhich());
              }
              if (z7) {
                notifyCoverWallpaperChanged(wallpaperData.mSemWallpaperData.getWpType(), i3);
                return;
              }
              return;
            } catch (Throwable th) {
              th = th;
              Binder.restoreCallingIdentity(clearCallingIdentity);
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
          }
        } catch (Throwable th3) {
          th = th3;
          throw th;
        }
      } catch (Throwable th4) {
        th = th4;
      }
    }
    throw th;
  }

  public static /* synthetic */ void lambda$setWallpaperComponentInternal$18(
      DisplayConnector displayConnector) {
    try {
      IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
      if (iWallpaperEngine != null) {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.reapply", 0, 0, 0, (Bundle) null);
      }
    } catch (RemoteException e) {
      Slog.w("WallpaperManagerService", "Error sending apply message to wallpaper", e);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:104:0x0284 A[Catch: all -> 0x0320, TryCatch #0 {, blocks: (B:76:0x01e6, B:78:0x01ec, B:81:0x01fc, B:86:0x0209, B:88:0x020e, B:92:0x021c, B:95:0x0235, B:96:0x0239, B:98:0x023d, B:99:0x024e, B:101:0x025c, B:104:0x0284, B:106:0x028e, B:107:0x0296, B:111:0x02b5, B:112:0x02bc, B:130:0x02b0, B:132:0x0264, B:134:0x0268, B:136:0x026c, B:138:0x0275, B:139:0x027c, B:142:0x0246), top: B:75:0x01e6, outer: #3 }] */
  /* JADX WARN: Removed duplicated region for block: B:111:0x02b5 A[Catch: all -> 0x0320, TryCatch #0 {, blocks: (B:76:0x01e6, B:78:0x01ec, B:81:0x01fc, B:86:0x0209, B:88:0x020e, B:92:0x021c, B:95:0x0235, B:96:0x0239, B:98:0x023d, B:99:0x024e, B:101:0x025c, B:104:0x0284, B:106:0x028e, B:107:0x0296, B:111:0x02b5, B:112:0x02bc, B:130:0x02b0, B:132:0x0264, B:134:0x0268, B:136:0x026c, B:138:0x0275, B:139:0x027c, B:142:0x0246), top: B:75:0x01e6, outer: #3 }] */
  /* JADX WARN: Removed duplicated region for block: B:129:0x02bb  */
  /* JADX WARN: Removed duplicated region for block: B:131:0x0294  */
  /* JADX WARN: Removed duplicated region for block: B:160:0x017a  */
  /* JADX WARN: Removed duplicated region for block: B:45:0x015d A[Catch: all -> 0x0341, TryCatch #1 {, blocks: (B:19:0x006d, B:21:0x009f, B:25:0x00a3, B:26:0x00a9, B:28:0x00b1, B:31:0x00b7, B:32:0x00b9, B:34:0x00bf, B:37:0x00d1, B:39:0x00d9, B:41:0x00e8, B:45:0x015d, B:46:0x0167, B:48:0x0170, B:52:0x017f, B:53:0x0186, B:56:0x018e, B:58:0x0196, B:59:0x01a6, B:158:0x01a2, B:165:0x00f6, B:167:0x00fa, B:171:0x013a, B:174:0x014a, B:176:0x0150, B:178:0x032a, B:179:0x0340), top: B:18:0x006d }] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x0170 A[Catch: all -> 0x0341, TryCatch #1 {, blocks: (B:19:0x006d, B:21:0x009f, B:25:0x00a3, B:26:0x00a9, B:28:0x00b1, B:31:0x00b7, B:32:0x00b9, B:34:0x00bf, B:37:0x00d1, B:39:0x00d9, B:41:0x00e8, B:45:0x015d, B:46:0x0167, B:48:0x0170, B:52:0x017f, B:53:0x0186, B:56:0x018e, B:58:0x0196, B:59:0x01a6, B:158:0x01a2, B:165:0x00f6, B:167:0x00fa, B:171:0x013a, B:174:0x014a, B:176:0x0150, B:178:0x032a, B:179:0x0340), top: B:18:0x006d }] */
  /* JADX WARN: Removed duplicated region for block: B:52:0x017f A[Catch: all -> 0x0341, TryCatch #1 {, blocks: (B:19:0x006d, B:21:0x009f, B:25:0x00a3, B:26:0x00a9, B:28:0x00b1, B:31:0x00b7, B:32:0x00b9, B:34:0x00bf, B:37:0x00d1, B:39:0x00d9, B:41:0x00e8, B:45:0x015d, B:46:0x0167, B:48:0x0170, B:52:0x017f, B:53:0x0186, B:56:0x018e, B:58:0x0196, B:59:0x01a6, B:158:0x01a2, B:165:0x00f6, B:167:0x00fa, B:171:0x013a, B:174:0x014a, B:176:0x0150, B:178:0x032a, B:179:0x0340), top: B:18:0x006d }] */
  /* JADX WARN: Removed duplicated region for block: B:55:0x018c A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:63:0x01aa  */
  /* JADX WARN: Removed duplicated region for block: B:71:0x01d6 A[Catch: all -> 0x01b4, TryCatch #2 {all -> 0x01b4, blocks: (B:152:0x01ac, B:66:0x01ba, B:68:0x01c2, B:69:0x01c7, B:71:0x01d6, B:72:0x01db), top: B:151:0x01ac, outer: #3 }] */
  /* JADX WARN: Removed duplicated region for block: B:94:0x0233  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setWallpaperComponentInternalLegacy(
      ComponentName componentName, String str, int i, int i2, Bundle bundle) {
    int i3;
    boolean z;
    WallpaperInfo wallpaperInfo;
    boolean migrateLiveSystemToLockWallpaperLocked;
    int i4;
    boolean z2;
    ComponentName componentName2;
    boolean z3;
    boolean z4;
    int i5;
    int i6;
    String str2;
    boolean z5;
    int i7;
    boolean containsLock;
    boolean z6;
    int i8;
    WallpaperData peekWallpaperDataLocked;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(
            IWallpaperManager.Stub.getCallingPid(),
            IWallpaperManager.Stub.getCallingUid(),
            i2,
            false,
            true,
            "changing live wallpaper",
            null);
    checkPermission("android.permission.SET_WALLPAPER_COMPONENT");
    boolean isLock = WhichChecker.isLock(i);
    boolean isSystem = WhichChecker.isSystem(i);
    int i9 = (this.mIsPreviewLockLiveWallpaperEnabled && isLock) ? 2 : 1;
    WallpaperInfo coverWallpaperInfo = getCoverWallpaperInfo(componentName, handleIncomingUser);
    boolean z7 = coverWallpaperInfo != null;
    if (!WhichChecker.isModeAbsent(i)) {
      i3 = i;
    } else if (z7) {
      i3 = WhichChecker.getType(i) | 16;
    } else {
      i3 = this.mSemService.getModeEnsuredWhich(i);
    }
    boolean z8 = WhichChecker.isSubDisplay(i3) && z7;
    int mode = WhichChecker.getMode(i3);
    boolean isSystemAndLockPaired = isSystemAndLockPaired(handleIncomingUser, mode);
    synchronized (this.mLock) {
      Slog.v(
          "WallpaperManagerService",
          "setWallpaperComponent name="
              + componentName
              + " , which = "
              + i3
              + " , coverWallpaperInfo = "
              + coverWallpaperInfo);
      if (!this.mSemService.mKnox.isWallpaperChangeAllowed(true)) {
        return;
      }
      String packageName = componentName != null ? componentName.getPackageName() : null;
      if (!"com.samsung.android.wallpaper.live".equals(packageName)
          && this.mIsPreviewLockLiveWallpaperEnabled
          && isLock) {
        i3 = mode | 1;
      }
      WallpaperData peekWallpaperDataLocked2 = peekWallpaperDataLocked(handleIncomingUser, i3);
      if (peekWallpaperDataLocked2 == null) {
        throw new IllegalStateException(
            "Wallpaper not yet initialized for user " + handleIncomingUser);
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (this.mImageWallpaper.equals(peekWallpaperDataLocked2.wallpaperComponent)) {
          if (isSystemAndLockPaired && !"com.samsung.android.wallpaper.live".equals(packageName)) {
            Slog.i(
                "WallpaperManagerService",
                "Migrating current wallpaper to be lock-only beforeupdating system wallpaper");
            boolean migrateStaticSystemToLockWallpaperLocked =
                migrateStaticSystemToLockWallpaperLocked(handleIncomingUser, mode | 1);
            if (!migrateStaticSystemToLockWallpaperLocked) {
              i3 |= 2;
            }
            i4 = i3;
            wallpaperInfo = coverWallpaperInfo;
            migrateLiveSystemToLockWallpaperLocked = migrateStaticSystemToLockWallpaperLocked;
            z = isSystem;
          }
          wallpaperInfo = coverWallpaperInfo;
          z = isSystem;
          i4 = i3;
          migrateLiveSystemToLockWallpaperLocked = false;
        } else {
          if (isSystem && isSystemAndLockPaired && this.mIsPreviewLockLiveWallpaperEnabled) {
            int wpType = peekWallpaperDataLocked2.mSemWallpaperData.getWpType();
            z = isSystem;
            boolean isPreloadedLiveWallpaperComponent =
                this.mLiveWallpaperHelper.isPreloadedLiveWallpaperComponent(
                    peekWallpaperDataLocked2.wallpaperComponent);
            wallpaperInfo = coverWallpaperInfo;
            boolean isSupportSystemAndLockPairOnly =
                this.mLiveWallpaperHelper.isSupportSystemAndLockPairOnly(
                    peekWallpaperDataLocked2.wallpaperComponent);
            Slog.i(
                "WallpaperManagerService",
                "setWallpaper: isPreloadedLiveComponent = "
                    + isPreloadedLiveWallpaperComponent
                    + "isSystemAndLockPairOnly = "
                    + isSupportSystemAndLockPairOnly);
            if (wpType == 7
                && isPreloadedLiveWallpaperComponent
                && !isSupportSystemAndLockPairOnly) {
              Slog.i(
                  "WallpaperManagerService",
                  "Migrating current live wallpaper to be lock-only before updating system"
                      + " wallpaper");
              migrateLiveSystemToLockWallpaperLocked =
                  migrateLiveSystemToLockWallpaperLocked(mode, handleIncomingUser);
              if (!migrateLiveSystemToLockWallpaperLocked) {
                if (!isLockscreenLiveWallpaperEnabled()) {
                  i3 |= 2;
                }
                i4 = i3;
              } else {
                i4 = i3;
                z2 = true;
                if (isSystemAndLockPaired) {
                  Slog.d(
                      "WallpaperManagerService",
                      "setWallpaperComponent: Set both flags, FLAG_SYSTEM and FLAG_LOCK");
                  i9 |= 2;
                }
                int i10 = i9;
                if ("com.samsung.android.wallpaper.live".equals(packageName)) {
                  componentName2 = componentName;
                } else {
                  componentName2 = componentName;
                  if (isPreloadLiveWallpaperReApplied(
                      componentName2, peekWallpaperDataLocked2, bundle)) {
                    z3 = true;
                    if (!z3) {
                      resetSemWallpaperData(
                          peekWallpaperDataLocked2.mSemWallpaperData,
                          peekWallpaperDataLocked2.userId);
                    }
                    if (!WhichChecker.isSystemAndLock(i4)
                        && !migrateLiveSystemToLockWallpaperLocked) {
                      WallpaperData peekWallpaperDataLocked3 =
                          peekWallpaperDataLocked(handleIncomingUser, mode | 2);
                      if (peekWallpaperDataLocked3 != null) {
                        resetSemWallpaperData(
                            peekWallpaperDataLocked3.mSemWallpaperData,
                            peekWallpaperDataLocked3.userId);
                        setKWPTypeLiveWallpaperWithMode(mode, 0);
                      }
                    } else {
                      setKWPTypeLiveWallpaperWithMode(mode, 1);
                    }
                    synchronized (this.mLock) {
                      if (isSystemAndLockPaired) {
                        try {
                          if (WhichChecker.isLock(i4)) {
                            z4 = true;
                            if (z4
                                && (peekWallpaperDataLocked =
                                        peekWallpaperDataLocked(
                                            handleIncomingUser, (i8 = mode | 1)))
                                    != null) {
                              peekWallpaperDataLocked.mWhich = i8;
                              updateEngineFlags(peekWallpaperDataLocked);
                            }
                            peekWallpaperDataLocked2.imageWallpaperPending = false;
                            peekWallpaperDataLocked2.mWhich = i4;
                            peekWallpaperDataLocked2.mSemWallpaperData.setExternalParams(bundle);
                            writeAssetFiles(i4, handleIncomingUser, bundle);
                            if (bundle != null) {
                              bundle.remove("assetFiles");
                            }
                          }
                        } finally {
                        }
                      }
                      z4 = false;
                      if (z4) {
                        peekWallpaperDataLocked.mWhich = i8;
                        updateEngineFlags(peekWallpaperDataLocked);
                      }
                      peekWallpaperDataLocked2.imageWallpaperPending = false;
                      peekWallpaperDataLocked2.mWhich = i4;
                      peekWallpaperDataLocked2.mSemWallpaperData.setExternalParams(bundle);
                      writeAssetFiles(i4, handleIncomingUser, bundle);
                      if (bundle != null) {}
                    }
                    putContentAttributes(
                        peekWallpaperDataLocked2,
                        invokePrepare(handleIncomingUser, i4, componentName2, bundle));
                    synchronized (this.mLock) {
                      if (canBindComponentNow(i4)) {
                        peekWallpaperDataLocked2.fromForegroundApp = isFromForegroundApp(str);
                        boolean changingToSame =
                            changingToSame(componentName2, peekWallpaperDataLocked2);
                        boolean z9 =
                            changingToSame
                                && !isSystemAndLockPaired
                                && WhichChecker.isSystemAndLock(i4);
                        if (changingToSame && !z9) {
                          updateEngineFlags(peekWallpaperDataLocked2);
                        }
                        if (!z9
                            && !this.mImageWallpaper.equals(
                                peekWallpaperDataLocked2.wallpaperComponent)) {
                          z6 = false;
                          i5 = mode;
                          i6 = i10;
                          str2 = str;
                          if (bindWallpaperComponentLocked(
                              componentName,
                              z6,
                              true,
                              peekWallpaperDataLocked2,
                              null,
                              wallpaperInfo)) {
                            if (!changingToSame) {
                              peekWallpaperDataLocked2.primaryColors = null;
                            } else {
                              WallpaperConnection wallpaperConnection =
                                  peekWallpaperDataLocked2.connection;
                              if (wallpaperConnection != null) {
                                wallpaperConnection.forEachDisplayConnector(
                                    new Consumer() { // from class:
                                                     // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda8
                                      @Override // java.util.function.Consumer
                                      public final void accept(Object obj) {
                                        WallpaperManagerService
                                            .lambda$setWallpaperComponentInternalLegacy$19(
                                                (WallpaperManagerService.DisplayConnector) obj);
                                      }
                                    });
                              }
                            }
                          }
                        }
                        z6 = true;
                        i5 = mode;
                        i6 = i10;
                        str2 = str;
                        if (bindWallpaperComponentLocked(
                            componentName,
                            z6,
                            true,
                            peekWallpaperDataLocked2,
                            null,
                            wallpaperInfo)) {}
                      } else {
                        i5 = mode;
                        i6 = i10;
                        str2 = str;
                        peekWallpaperDataLocked2.wallpaperComponent = componentName2;
                      }
                      peekWallpaperDataLocked2.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                      if (!componentName2.equals(this.mImageWallpaper)) {
                        peekWallpaperDataLocked2.mSemWallpaperData.setWpType(7);
                      } else {
                        if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                          if (peekWallpaperDataLocked2.mSemWallpaperData.getWpType() != 3) {
                            z5 = false;
                            peekWallpaperDataLocked2.mSemWallpaperData.setWpType(0);
                          }
                        } else {
                          z5 = false;
                          peekWallpaperDataLocked2.mSemWallpaperData.setWpType(0);
                        }
                        if (z) {
                          i7 = i5;
                        } else {
                          i7 = i5;
                          WallpaperData peekWallpaperDataLocked4 =
                              peekWallpaperDataLocked(handleIncomingUser, i7 | 2);
                          if (peekWallpaperDataLocked4 != null) {
                            peekWallpaperDataLocked4.mSemWallpaperData.setIsCopied(z5);
                          }
                        }
                        peekWallpaperDataLocked2.mSemWallpaperData.setIsPreloaded(
                            isPreloadedLiveWallpaper(componentName2, bundle, i4));
                        peekWallpaperDataLocked2.setCallingPackage(str2);
                        peekWallpaperDataLocked2.cleanUp();
                        saveSettingsLocked(peekWallpaperDataLocked2.userId, i7);
                        if (this.mIsPreviewLockLiveWallpaperEnabled || !isLock) {
                          notifyCallbacksLocked(peekWallpaperDataLocked2);
                        }
                        containsLock = z2 ? WhichChecker.containsLock(i4) : z2;
                      }
                      z5 = false;
                      if (z) {}
                      peekWallpaperDataLocked2.mSemWallpaperData.setIsPreloaded(
                          isPreloadedLiveWallpaper(componentName2, bundle, i4));
                      peekWallpaperDataLocked2.setCallingPackage(str2);
                      peekWallpaperDataLocked2.cleanUp();
                      saveSettingsLocked(peekWallpaperDataLocked2.userId, i7);
                      if (this.mIsPreviewLockLiveWallpaperEnabled) {}
                      notifyCallbacksLocked(peekWallpaperDataLocked2);
                      if (z2) {}
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.i(
                        "WallpaperManagerService", "setWallpaperComponent: clear thumbnail files");
                    this.mSemService.deleteThumbnailFile(i4, handleIncomingUser);
                    notifyWallpaperColorsChanged(peekWallpaperDataLocked2, i6);
                    notifyWallpaperColorsChanged(this.mFallbackWallpaper, 1);
                    if (!this.mIsPreviewLockLiveWallpaperEnabled) {
                      containsLock = z5;
                    }
                    if (containsLock) {
                      notifyLockWallpaperChanged(7, i7 | 2);
                    }
                    if (containsLock || canBindComponentNow(i4)) {
                      peekWallpaperDataLocked2 = getWallpaperSafeLocked(handleIncomingUser, i7 | 2);
                      this.mSemService.mLegibilityColor.extractColor(
                          peekWallpaperDataLocked2.mSemWallpaperData.getWhich());
                      this.mSemService.mLegibilityColor.extractColor(
                          peekWallpaperDataLocked2.mSemWallpaperData.getWhich(), true);
                    }
                    if (z8 || WhichChecker.isWatchFaceDisplay(i4)) {
                      notifyCoverWallpaperChanged(
                          peekWallpaperDataLocked2.mSemWallpaperData.getWpType(), i4);
                      return;
                    }
                    return;
                  }
                }
                z3 = false;
                if (!z3) {}
                if (!WhichChecker.isSystemAndLock(i4)) {}
                setKWPTypeLiveWallpaperWithMode(mode, 1);
                synchronized (this.mLock) {
                }
              }
            }
            i4 = i3;
            migrateLiveSystemToLockWallpaperLocked = false;
          }
          wallpaperInfo = coverWallpaperInfo;
          z = isSystem;
          i4 = i3;
          migrateLiveSystemToLockWallpaperLocked = false;
        }
        synchronized (this.mLock) {
        }
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
      z2 = false;
      if (isSystemAndLockPaired) {}
      int i102 = i9;
      if ("com.samsung.android.wallpaper.live".equals(packageName)) {}
      z3 = false;
      if (!z3) {}
      if (!WhichChecker.isSystemAndLock(i4)) {}
      setKWPTypeLiveWallpaperWithMode(mode, 1);
    }
  }

  public static /* synthetic */ void lambda$setWallpaperComponentInternalLegacy$19(
      DisplayConnector displayConnector) {
    try {
      IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
      if (iWallpaperEngine != null) {
        iWallpaperEngine.dispatchWallpaperCommand(
            "android.wallpaper.reapply", 0, 0, 0, (Bundle) null);
      }
    } catch (RemoteException e) {
      Slog.w("WallpaperManagerService", "Error sending apply message to wallpaper", e);
    }
  }

  public final Bundle invokePrepare(int i, int i2, ComponentName componentName, Bundle bundle) {
    if (this.mLiveWallpaperHelper.isPreloadedLiveWallpaperComponent(componentName)) {
      return this.mLiveWallpaperHelper.requestWallpaperPrepare(componentName, i2, i, bundle);
    }
    return null;
  }

  public final void putContentAttributes(WallpaperData wallpaperData, Bundle bundle) {
    Bundle externalParams = wallpaperData.mSemWallpaperData.getExternalParams();
    if (externalParams != null) {
      externalParams.remove("contentAttributes");
    } else {
      if (bundle == null) {
        return;
      }
      externalParams = new Bundle();
      wallpaperData.mSemWallpaperData.setExternalParams(externalParams);
    }
    if (bundle == null || bundle.isEmpty()) {
      return;
    }
    externalParams.putBundle("contentAttributes", bundle);
  }

  public final void writeAssetFiles(int i, int i2, Bundle bundle) {
    Bundle bundle2;
    String str;
    if (bundle != null) {
      bundle2 = bundle.getBundle("assetFiles");
      str = bundle.getString("key");
    } else {
      bundle2 = null;
      str = null;
    }
    int mode = WhichChecker.getMode(i);
    boolean isSingleType = WhichChecker.isSingleType(i);
    if (!isSingleType) {
      i = mode | 1;
    }
    if (bundle2 != null) {
      if (!this.mAssetFileManager.writeAssetFiles(i, i2, bundle2, str) || isSingleType) {
        return;
      }
      this.mAssetFileManager.removeAssetFiles(mode | 2, i2);
      return;
    }
    this.mAssetFileManager.removeAssetFiles(i, i2);
  }

  public final boolean migrateLiveSystemToLockWallpaperLocked(int i, int i2) {
    int i3 = i | 1;
    int i4 = i | 2;
    WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, i3);
    if (peekWallpaperDataLocked == null) {
      return true;
    }
    Slog.i(
        "WallpaperManagerService",
        "migrateLiveSystemToLockWallpaperLocked : currentSystem = "
            + peekWallpaperDataLocked
            + ", to which = "
            + i4);
    WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i2, i4);
    wallpaperSafeLocked.mWhich = i4;
    wallpaperSafeLocked.wallpaperId = peekWallpaperDataLocked.wallpaperId;
    wallpaperSafeLocked.userId = peekWallpaperDataLocked.userId;
    wallpaperSafeLocked.wallpaperComponent = peekWallpaperDataLocked.wallpaperComponent;
    wallpaperSafeLocked.cropHint.set(peekWallpaperDataLocked.cropHint);
    wallpaperSafeLocked.allowBackup = peekWallpaperDataLocked.allowBackup;
    wallpaperSafeLocked.connection = null;
    wallpaperSafeLocked.mSemWallpaperData.setWpType(
        peekWallpaperDataLocked.mSemWallpaperData.getWpType());
    wallpaperSafeLocked.mSemWallpaperData.setExternalParams(
        peekWallpaperDataLocked.mSemWallpaperData.getExternalParams());
    wallpaperSafeLocked.mSemWallpaperData.setIsPreloaded(
        peekWallpaperDataLocked.mSemWallpaperData.getIsPreloaded());
    wallpaperSafeLocked.mSemWallpaperData.setLandscapeColors(null);
    wallpaperSafeLocked.mSemWallpaperData.setPrimarySemColors(null);
    wallpaperSafeLocked.mSemWallpaperData.setDlsSemColors(null);
    wallpaperSafeLocked.cleanUp();
    saveSettingsLocked(i2, i);
    this.mAssetFileManager.moveAssetFiles(i3, i4, i2);
    return true;
  }

  public final boolean isPreloadedLiveWallpaper(ComponentName componentName, Bundle bundle, int i) {
    if (componentName == null || componentName.equals(this.mImageWallpaper)) {
      return false;
    }
    if (bundle != null) {
      return bundle.getBoolean("isPreloaded", false);
    }
    return componentName.equals(getDefaultPreloadedLiveWallpaperComponentName(i));
  }

  public final boolean isPreloadLiveWallpaperReApplied(
      ComponentName componentName, WallpaperData wallpaperData, Bundle bundle) {
    Bundle externalParams;
    ComponentName componentName2 = wallpaperData.wallpaperComponent;
    if (componentName2 != null
        && componentName.equals(componentName2)
        && (externalParams = wallpaperData.mSemWallpaperData.getExternalParams()) != null
        && bundle != null) {
      String json = WallpaperExtraBundleHelper.toJson(externalParams.getBundle("serviceSettings"));
      String json2 = WallpaperExtraBundleHelper.toJson(bundle.getBundle("serviceSettings"));
      Log.m129d(
          "WallpaperManagerService",
          "setWallpaperComponent preload settings : prev = " + json + ", new = " + json2);
      if (TextUtils.equals(json, json2)
          && !TextUtils.isEmpty(json2)
          && !TextUtils.equals(json2, "{}")) {
        return true;
      }
    }
    return false;
  }

  public final WallpaperInfo getCoverWallpaperInfo(ComponentName componentName, int i) {
    if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
      return null;
    }
    try {
      return getWallpaperInfo(
          new Intent("com.samsung.android.service.wallpaper.CoverWallpaperService"),
          this.mIPackageManager.getServiceInfo(componentName, 4224L, i),
          i,
          true);
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  public final boolean canBindComponentNow(int i) {
    Log.m129d("WallpaperManagerService", "bindComponentNow " + i);
    if (!this.mIsLockscreenLiveWallpaperEnabled && WhichChecker.isLock(i)) {
      return false;
    }
    if (!WhichChecker.isDex(i) && Rune.SUPPORT_SUB_DISPLAY_MODE) {
      return WhichChecker.isSubDisplay(i) || WhichChecker.isPhone(i);
    }
    return true;
  }

  public final boolean changingToSame(ComponentName componentName, WallpaperData wallpaperData) {
    if (wallpaperData.connection == null) {
      return false;
    }
    ComponentName componentName2 = wallpaperData.wallpaperComponent;
    return componentName2 == null ? componentName == null : componentName2.equals(componentName);
  }

  public final boolean bindWallpaperComponentLocked(
      ComponentName componentName,
      boolean z,
      boolean z2,
      WallpaperData wallpaperData,
      IRemoteCallback iRemoteCallback) {
    return bindWallpaperComponentLocked(componentName, z, z2, wallpaperData, iRemoteCallback, null);
  }

  public final boolean bindWallpaperComponentLocked(
      ComponentName componentName,
      boolean z,
      boolean z2,
      WallpaperData wallpaperData,
      IRemoteCallback iRemoteCallback,
      WallpaperInfo wallpaperInfo) {
    WallpaperInfo wallpaperInfo2;
    ComponentName componentName2 = componentName;
    Slog.v(
        "WallpaperManagerService",
        "bindWallpaperComponentLocked: which = "
            + wallpaperData.mSemWallpaperData.getWhich()
            + ",componentName = "
            + componentName2
            + ", force="
            + z);
    if (!z && changingToSame(componentName2, wallpaperData)) {
      return true;
    }
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
    timingsTraceAndSlog.traceBegin("WPMS.bindWallpaperComponentLocked-" + componentName2);
    try {
      if (componentName2 == null) {
        componentName2 = WallpaperManager.getDefaultWallpaperComponent(this.mContext);
        if (componentName2 == null) {
          int which = wallpaperData.mSemWallpaperData.getWhich();
          if (getDefaultWallpaperType(which) == 7
              && !WallpaperManager.isDefaultOperatorWallpaper(this.mContext, which)
              && (componentName2 = getDefaultPreloadedLiveWallpaperComponentName(which)) != null) {
            Slog.i(
                "WallpaperManagerService",
                "bindWallpaperComponentLocked: default Live Wallpaper : " + componentName2);
            putDefaultLiveWallpaperProperties(wallpaperData);
            wallpaperData.mSemWallpaperData.setWpType(7);
          }
        }
        if (componentName2 == null) {
          componentName2 = this.mImageWallpaper;
          Slog.v("WallpaperManagerService", "No default component; using image wallpaper");
        }
      }
      try {
        if (this.mStatusBarService.isSysUiSafeModeEnabled()
            && componentName2 != null
            && !componentName2.equals(this.mImageWallpaper)) {
          componentName2 = this.mImageWallpaper;
        }
      } catch (Exception e) {
        Slog.e("WallpaperManagerService", "SAFEMODE Exception occurs! " + e.getMessage());
      }
      int i = wallpaperData.userId;
      ServiceInfo serviceInfo = this.mIPackageManager.getServiceInfo(componentName2, 4224L, i);
      if (serviceInfo == null) {
        Slog.w(
            "WallpaperManagerService", "Attempted wallpaper " + componentName2 + " is unavailable");
        return false;
      }
      if (!"android.permission.BIND_WALLPAPER".equals(serviceInfo.permission)) {
        String str =
            "Selected service does not have android.permission.BIND_WALLPAPER: " + componentName2;
        if (z2) {
          throw new SecurityException(str);
        }
        Slog.w("WallpaperManagerService", str);
        return false;
      }
      Intent intent = new Intent("android.service.wallpaper.WallpaperService");
      if (componentName2 == null
          || componentName2.equals(this.mImageWallpaper)
          || wallpaperInfo != null) {
        wallpaperInfo2 = wallpaperInfo != null ? wallpaperInfo : null;
      } else {
        WallpaperInfo wallpaperInfo3 = getWallpaperInfo(intent, serviceInfo, i, z2);
        if (wallpaperInfo3 == null) {
          intent = new Intent("com.samsung.android.service.wallpaper.LiveWallpaperService");
          wallpaperInfo3 = getWallpaperInfo(intent, serviceInfo, i, z2);
        }
        wallpaperInfo2 =
            (wallpaperInfo3 == null
                    && WhichChecker.isWatchFaceDisplay(wallpaperData.mSemWallpaperData.getWhich()))
                ? getCoverWallpaperInfo(componentName2, i)
                : wallpaperInfo3;
        if (wallpaperInfo2 == null) {
          String str2 = "Selected service is not a wallpaper: " + componentName2;
          if (z2) {
            throw new SecurityException(str2);
          }
          Slog.w("WallpaperManagerService", str2);
          return false;
        }
      }
      if (wallpaperInfo2 != null
          && wallpaperInfo2.supportsAmbientMode()
          && this.mIPackageManager.checkPermission(
                  "android.permission.AMBIENT_WALLPAPER", wallpaperInfo2.getPackageName(), i)
              != 0) {
        String str3 =
            "Selected service does not have android.permission.AMBIENT_WALLPAPER: "
                + componentName2;
        if (z2) {
          throw new SecurityException(str3);
        }
        Slog.w("WallpaperManagerService", str3);
        return false;
      }
      PendingIntent activityAsUser =
          PendingIntent.getActivityAsUser(
              this.mContext,
              0,
              Intent.createChooser(
                  new Intent("android.intent.action.SET_WALLPAPER"),
                  this.mContext.getText(R.string.config_retailDemoPackage)),
              67108864,
              ActivityOptions.makeBasic()
                  .setPendingIntentCreatorBackgroundActivityStartMode(2)
                  .toBundle(),
              UserHandle.of(i));
      WallpaperConnection wallpaperConnection =
          new WallpaperConnection(
              wallpaperInfo2,
              wallpaperData,
              this.mIPackageManager.getPackageUid(
                  componentName2.getPackageName(), 268435456L, wallpaperData.userId));
      intent.setComponent(componentName2);
      intent.putExtra("android.intent.extra.client_label", 17043261);
      intent.putExtra("android.intent.extra.client_intent", activityAsUser);
      if (!this.mContext.bindServiceAsUser(
          intent, wallpaperConnection, 570429441, new UserHandle(i))) {
        String str4 = "Unable to bind service: " + componentName2;
        if (z2) {
          throw new IllegalArgumentException(str4);
        }
        Slog.w("WallpaperManagerService", str4);
        return false;
      }
      Log.m129d(
          "WallpaperManagerService",
          "bindService request success. connect : " + wallpaperConnection);
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        maybeDetachLastWallpapers(wallpaperData);
      } else if (wallpaperData.userId == this.mCurrentUserId
          && !wallpaperData.equals(this.mFallbackWallpaper)) {
        WallpaperData lastWallpaper = getLastWallpaper(wallpaperData);
        if (lastWallpaper != null) {
          detachWallpaperLocked(lastWallpaper);
        }
        if (rebindWallpaper(componentName2, lastWallpaper)) {
          this.mSemService.handleWallpaperBindingTimeout(true, true);
        }
      }
      wallpaperData.wallpaperComponent = componentName2;
      wallpaperData.connection = wallpaperConnection;
      wallpaperConnection.mReply = iRemoteCallback;
      Slog.d(
          "WallpaperManagerService",
          "wallpaper userId = "
              + wallpaperData.userId
              + " , current user Id = "
              + this.mCurrentUserId);
      if (this.mIsLockscreenLiveWallpaperEnabled) {
        updateCurrentWallpapers(wallpaperData);
      } else if (wallpaperData.userId
              == getConvertedUserId(this.mCurrentUserId, this.mSemService.getOldUserId())
          && !wallpaperData.equals(this.mFallbackWallpaper)) {
        setLastWallpaper(wallpaperData);
      }
      updateFallbackConnection();
      timingsTraceAndSlog.traceEnd();
      return true;
    } catch (RemoteException e2) {
      String str5 =
          "Remote exception for "
              + componentName2
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
              + e2;
      if (z2) {
        throw new IllegalArgumentException(str5);
      }
      Slog.w("WallpaperManagerService", str5);
      return false;
    } finally {
      timingsTraceAndSlog.traceEnd();
    }
  }

  public final void updateCurrentWallpapers(WallpaperData wallpaperData) {
    if (wallpaperData.userId != this.mCurrentUserId
        || wallpaperData.equals(this.mFallbackWallpaper)) {
      return;
    }
    if (WhichChecker.isSystemAndLock(wallpaperData.mWhich)) {
      this.mLastWallpaper = wallpaperData;
    } else if (WhichChecker.isSystem(wallpaperData.mWhich)) {
      this.mLastWallpaper = wallpaperData;
    } else if (WhichChecker.isLock(wallpaperData.mWhich)) {
      this.mLastLockWallpaper = wallpaperData;
    }
  }

  public final void maybeDetachLastWallpapers(WallpaperData wallpaperData) {
    if (wallpaperData.userId != this.mCurrentUserId
        || wallpaperData.equals(this.mFallbackWallpaper)) {
      return;
    }
    int i = wallpaperData.mWhich;
    boolean z = false;
    boolean z2 = (i & 1) != 0;
    boolean z3 = (i & 2) != 0;
    if (wallpaperData.mSystemWasBoth && !z3) {
      z = true;
    }
    WallpaperData wallpaperData2 = this.mLastWallpaper;
    if (wallpaperData2 != null && z2 && !z) {
      detachWallpaperLocked(wallpaperData2);
    }
    WallpaperData wallpaperData3 = this.mLastLockWallpaper;
    if (wallpaperData3 == null || !z3) {
      return;
    }
    detachWallpaperLocked(wallpaperData3);
  }

  public final void putDefaultLiveWallpaperProperties(WallpaperData wallpaperData) {
    int which = wallpaperData.mSemWallpaperData.getWhich();
    Bundle defaultLiveWallpaperExtras =
        this.mSemWallpaperResourcesInfo.getDefaultLiveWallpaperExtras(which);
    if (defaultLiveWallpaperExtras == null) {
      Log.m134w(
          "WallpaperManagerService",
          "putDefaultLiveWallpaperProperties: default extra data is not present. which=" + which);
      defaultLiveWallpaperExtras = new Bundle();
    }
    defaultLiveWallpaperExtras.putBoolean("isPreloaded", true);
    wallpaperData.mSemWallpaperData.setExternalParams(defaultLiveWallpaperExtras);
    wallpaperData.mSemWallpaperData.setIsPreloaded(true);
  }

  public final WallpaperInfo getWallpaperInfo(
      Intent intent, ServiceInfo serviceInfo, int i, boolean z) {
    String str;
    String str2;
    try {
      List list =
          this.mIPackageManager
              .queryIntentServices(
                  intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 128L, i)
              .getList();
      for (int i2 = 0; i2 < list.size(); i2++) {
        ServiceInfo serviceInfo2 = ((ResolveInfo) list.get(i2)).serviceInfo;
        if (serviceInfo2 != null
            && serviceInfo != null
            && (str = serviceInfo2.name) != null
            && str.equals(serviceInfo.name)
            && (str2 = serviceInfo2.packageName) != null
            && str2.equals(serviceInfo.packageName)) {
          try {
            return new WallpaperInfo(this.mContext, (ResolveInfo) list.get(i2));
          } catch (IOException e) {
            if (z) {
              throw new IllegalArgumentException(e);
            }
            Slog.w("WallpaperManagerService", e);
            return null;
          } catch (XmlPullParserException e2) {
            if (z) {
              throw new IllegalArgumentException(e2);
            }
            Slog.w("WallpaperManagerService", e2);
            return null;
          }
        }
        if (serviceInfo2 != null
            && (serviceInfo2.name == null || serviceInfo2.packageName == null)) {
          Log.m134w(
              "WallpaperManagerService",
              "getWallpaperInfo: Invalid ServiceInfo, name = "
                  + serviceInfo2.name
                  + " , packageName = "
                  + serviceInfo2.packageName);
        }
      }
      return null;
    } catch (RemoteException e3) {
      Slog.w("WallpaperManagerService", "getWallpaperInfo: Failed getting wallpaper info ", e3);
      return null;
    }
  }

  public final WallpaperData getLastWallpaper(WallpaperData wallpaperData) {
    synchronized (this.mLock) {
      if (wallpaperData.mSemWallpaperData.getIsDesktopWallpaper()
          && !this.mSemService.mDesktopMode.isDesktopSingleMode()) {
        return this.mLastDexWallpaper;
      }
      if (WhichChecker.isSubDisplay(wallpaperData.mWhich)) {
        return this.mLastWatchFace;
      }
      if (WhichChecker.isVirtualDisplay(wallpaperData.mWhich)) {
        return this.mLastVirtualWallpaper;
      }
      return this.mLastWallpaper;
    }
  }

  public final WallpaperData getTargetWallpaper(int i) {
    WallpaperConnection wallpaperConnection;
    WallpaperData wallpaperData;
    WallpaperConnection wallpaperConnection2;
    WallpaperConnection wallpaperConnection3;
    WallpaperData wallpaperData2 = this.mLastWallpaper;
    if (wallpaperData2 != null
        && (wallpaperConnection3 = wallpaperData2.connection) != null
        && wallpaperConnection3.containsDisplay(i)) {
      return this.mLastWallpaper;
    }
    if (Rune.VIRTUAL_DISPLAY_WALLPAPER
        && (wallpaperData = this.mLastVirtualWallpaper) != null
        && (wallpaperConnection2 = wallpaperData.connection) != null
        && wallpaperConnection2.containsDisplay(i)) {
      return this.mLastVirtualWallpaper;
    }
    WallpaperData wallpaperData3 = this.mFallbackWallpaper;
    if (wallpaperData3 == null
        || (wallpaperConnection = wallpaperData3.connection) == null
        || !wallpaperConnection.containsDisplay(i)) {
      return null;
    }
    return this.mFallbackWallpaper;
  }

  public final void setLastWallpaper(WallpaperData wallpaperData) {
    synchronized (this.mLock) {
      if (wallpaperData.mSemWallpaperData.getIsDesktopWallpaper()
          && !this.mSemService.mDesktopMode.isDesktopSingleMode()) {
        this.mLastDexWallpaper = wallpaperData;
      } else if (WhichChecker.isSubDisplay(wallpaperData.mWhich)) {
        this.mLastWatchFace = wallpaperData;
      } else if (WhichChecker.isVirtualDisplay(wallpaperData.mWhich)) {
        this.mLastVirtualWallpaper = wallpaperData;
      } else {
        this.mLastWallpaper = wallpaperData;
      }
    }
  }

  public final boolean rebindWallpaper(ComponentName componentName, WallpaperData wallpaperData) {
    ComponentName componentName2;
    if (componentName == null
        || !componentName.equals(this.mImageWallpaper)
        || wallpaperData == null
        || (componentName2 = wallpaperData.wallpaperComponent) == null
        || !componentName2.equals(this.mImageWallpaper)) {
      return false;
    }
    Log.m129d(
        "WallpaperManagerService",
        "Run binding timeout cause duplicated bind / unbind of image wallpaper");
    return true;
  }

  public final void detachWallpaperLocked(final WallpaperData wallpaperData) {
    WallpaperConnection wallpaperConnection = wallpaperData.connection;
    if (wallpaperConnection != null) {
      IRemoteCallback iRemoteCallback = wallpaperConnection.mReply;
      if (iRemoteCallback != null) {
        try {
          iRemoteCallback.sendResult((Bundle) null);
        } catch (RemoteException e) {
          Slog.w(
              "WallpaperManagerService", "Error sending reply to wallpaper before disconnect", e);
        }
        wallpaperData.connection.mReply = null;
      }
      wallpaperData.connection.forEachDisplayConnector(
          new Consumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WallpaperManagerService.lambda$detachWallpaperLocked$20(
                  WallpaperData.this, (WallpaperManagerService.DisplayConnector) obj);
            }
          });
      WallpaperConnection wallpaperConnection2 = wallpaperData.connection;
      wallpaperConnection2.mService = null;
      wallpaperConnection2.mDisplayConnector.clear();
      FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
      this.mContext
          .getMainThreadHandler()
          .removeCallbacks(wallpaperData.connection.mDisconnectRunnable);
      this.mContext
          .getMainThreadHandler()
          .removeCallbacks(wallpaperData.connection.mTryToRebindRunnable);
      this.mContext.unbindService(wallpaperData.connection);
      wallpaperData.connection = null;
    }
  }

  public static /* synthetic */ void lambda$detachWallpaperLocked$20(
      WallpaperData wallpaperData, DisplayConnector displayConnector) {
    displayConnector.disconnectLocked(wallpaperData.connection);
  }

  public final void updateEngineFlags(final WallpaperData wallpaperData) {
    WallpaperConnection wallpaperConnection = wallpaperData.connection;
    if (wallpaperConnection == null) {
      return;
    }
    wallpaperConnection.forEachDisplayConnector(
        new Consumer() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda7
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            WallpaperManagerService.this.lambda$updateEngineFlags$21(
                wallpaperData, (WallpaperManagerService.DisplayConnector) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateEngineFlags$21(
      WallpaperData wallpaperData, DisplayConnector displayConnector) {
    try {
      IWallpaperEngine iWallpaperEngine = displayConnector.mEngine;
      if (iWallpaperEngine != null) {
        iWallpaperEngine.setWallpaperFlags(wallpaperData.mWhich);
        this.mWindowManagerInternal.setWallpaperShowWhenLocked(
            displayConnector.mToken, isVisibleWhichWhenKeyguardLocked(wallpaperData.mWhich));
      }
    } catch (RemoteException e) {
      Slog.e("WallpaperManagerService", "Failed to update wallpaper engine flags", e);
    }
  }

  public final void clearWallpaperComponentLocked(WallpaperData wallpaperData) {
    wallpaperData.wallpaperComponent = null;
    detachWallpaperLocked(wallpaperData);
  }

  public final void attachServiceLocked(
      final WallpaperConnection wallpaperConnection, final WallpaperData wallpaperData) {
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
    timingsTraceAndSlog.traceBegin("WPMS.attachServiceLocked");
    wallpaperConnection.forEachDisplayConnector(
        new Consumer() { // from class:
                         // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda24
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((WallpaperManagerService.DisplayConnector) obj)
                .connectLocked(WallpaperManagerService.WallpaperConnection.this, wallpaperData);
          }
        });
    timingsTraceAndSlog.traceEnd();
  }

  public final void notifyCallbacksLocked(WallpaperData wallpaperData) {
    int beginBroadcast = wallpaperData.callbacks.beginBroadcast();
    for (int i = 0; i < beginBroadcast; i++) {
      try {
        wallpaperData.callbacks.getBroadcastItem(i).onWallpaperChanged();
      } catch (RemoteException e) {
        Slog.w("WallpaperManagerService", "Failed to notify callbacks about wallpaper changes", e);
      }
    }
    wallpaperData.callbacks.finishBroadcast();
    Intent intent = new Intent("android.intent.action.WALLPAPER_CHANGED");
    intent.putExtra(
        "android.service.wallpaper.extra.FROM_FOREGROUND_APP", wallpaperData.fromForegroundApp);
    intent.putExtra("which", wallpaperData.mWhich);
    this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
  }

  public final void checkPermission(String str) {
    if (hasPermission(str)) {
      return;
    }
    throw new SecurityException(
        "Access denied to process: " + Binder.getCallingPid() + ", must have permission " + str);
  }

  public final boolean packageBelongsToUid(String str, int i) {
    try {
      return this.mContext.getPackageManager().getPackageUidAsUser(str, UserHandle.getUserId(i))
          == i;
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public final void enforcePackageBelongsToUid(String str, int i) {
    if (packageBelongsToUid(str, i)) {
      return;
    }
    throw new IllegalArgumentException("Invalid package or package does not belong to uid:" + i);
  }

  public final boolean isFromForegroundApp(final String str) {
    return ((Boolean)
            Binder.withCleanCallingIdentity(
                new FunctionalUtils
                    .ThrowingSupplier() { // from class:
                                          // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda17
                  public final Object getOrThrow() {
                    Boolean lambda$isFromForegroundApp$23;
                    lambda$isFromForegroundApp$23 =
                        WallpaperManagerService.this.lambda$isFromForegroundApp$23(str);
                    return lambda$isFromForegroundApp$23;
                  }
                }))
        .booleanValue();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$isFromForegroundApp$23(String str) {
    return Boolean.valueOf(this.mActivityManager.getPackageImportance(str) == 100);
  }

  public final void checkCallerIsSystemOrSystemUi() {
    if (Binder.getCallingUid() != Process.myUid()
        && this.mContext.checkCallingPermission("android.permission.STATUS_BAR_SERVICE") != 0) {
      throw new SecurityException("Access denied: only system processes can call this");
    }
  }

  public boolean isWallpaperSupported(String str) {
    int callingUid = Binder.getCallingUid();
    enforcePackageBelongsToUid(str, callingUid);
    return this.mAppOpsManager.checkOpNoThrow(48, callingUid, str) == 0;
  }

  public boolean isSetWallpaperAllowed(String str) {
    if (!this.mSemService.mKnox.isWallpaperChangeAllowed(true)
        || !Arrays.asList(
                this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid()))
            .contains(str)) {
      return false;
    }
    DevicePolicyManagerInternal devicePolicyManagerInternal =
        (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
    if (devicePolicyManagerInternal != null
        && devicePolicyManagerInternal.isDeviceOrProfileOwnerInCallingUser(str)) {
      return true;
    }
    int callingUserId = UserHandle.getCallingUserId();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return !((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
          .hasUserRestriction("no_set_wallpaper", callingUserId);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isWallpaperBackupEligible(int i, int i2) {
    if (this.mSemService.mDesktopMode.isDesktopMode()) {
      return false;
    }
    WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, i);
    Log.m129d(
        "WallpaperManagerService",
        "isWallpaperBackupEligible: which = "
            + i
            + ", userId = "
            + i2
            + ", wallpaper = "
            + peekWallpaperDataLocked);
    if (peekWallpaperDataLocked != null) {
      return peekWallpaperDataLocked.allowBackup;
    }
    return false;
  }

  public boolean isLockscreenLiveWallpaperEnabled() {
    return this.mIsLockscreenLiveWallpaperEnabled;
  }

  public boolean isMultiCropEnabled() {
    return this.mIsMultiCropEnabled;
  }

  public final void onDisplayReadyInternal(int i) {
    synchronized (this.mLock) {
      Log.m129d("WallpaperManagerService", "onDisplayReadyInternal, displayId = " + i);
      if (i == 2) {
        Log.addLogString("WallpaperManagerService", "DEX_DISPLAY is added");
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(this.mCurrentUserId, 9);
        wallpaperSafeLocked.mWhich |= 8;
        bindWallpaperComponentLocked(this.mImageWallpaper, true, false, wallpaperSafeLocked, null);
        return;
      }
      if (Rune.VIRTUAL_DISPLAY_WALLPAPER && isVirtualWallpaperDisplay(i)) {
        this.mActiveVirtualDisplayId = i;
        return;
      }
      WallpaperData wallpaperData = this.mLastWallpaper;
      if (wallpaperData == null) {
        return;
      }
      if (supportsMultiDisplay(wallpaperData.connection)) {
        DisplayConnector displayConnectorOrCreate =
            this.mLastWallpaper.connection.getDisplayConnectorOrCreate(i);
        if (displayConnectorOrCreate == null) {
          return;
        }
        WallpaperData wallpaperData2 = this.mLastWallpaper;
        displayConnectorOrCreate.connectLocked(wallpaperData2.connection, wallpaperData2);
        return;
      }
      WallpaperData wallpaperData3 = this.mFallbackWallpaper;
      if (wallpaperData3 != null) {
        DisplayConnector displayConnectorOrCreate2 =
            wallpaperData3.connection.getDisplayConnectorOrCreate(i);
        if (displayConnectorOrCreate2 == null) {
          return;
        }
        WallpaperData wallpaperData4 = this.mFallbackWallpaper;
        displayConnectorOrCreate2.connectLocked(wallpaperData4.connection, wallpaperData4);
      } else {
        Slog.w("WallpaperManagerService", "No wallpaper can be added to the new display");
      }
    }
  }

  public void saveSettingsLocked(int i) {
    saveSettingsLocked(i, this.mSemService.getCurrentImplicitMode());
  }

  public void saveSettingsLocked(int i, int i2) {
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
    timingsTraceAndSlog.traceBegin("WPMS.saveSettingsLocked-" + i);
    this.mWallpaperDataParser.saveSettingsLocked(
        i, this.mWallpaperMap.get(i, i2), this.mLockWallpaperMap.get(i, i2));
    timingsTraceAndSlog.traceEnd();
  }

  public WallpaperData getWallpaperSafeLocked(int i, int i2) {
    WallpaperData wallpaperData;
    Log.m129d(
        "WallpaperManagerService",
        "getWallpaperSafeLocked, userId = "
            + i
            + ", which = "
            + i2
            + ", caller = "
            + Debug.getCaller());
    WallpaperDataManager wallpaperDataManager =
        WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap;
    WallpaperData wallpaperData2 = wallpaperDataManager.get(i, WhichChecker.getMode(i2));
    if (wallpaperData2 != null) {
      return wallpaperData2;
    }
    Log.m129d("WallpaperManagerService", "getWallpaperSafeLocked, didn't find wallpaper.");
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i2);
    loadSettingsLocked(i, false, modeEnsuredWhich, WhichChecker.getMode(modeEnsuredWhich));
    WallpaperData wallpaperData3 =
        wallpaperDataManager.get(i, WhichChecker.getMode(modeEnsuredWhich));
    if (wallpaperData3 != null) {
      return wallpaperData3;
    }
    Log.m129d("WallpaperManagerService", "getWallpaperSafeLocked, didn't find yet.");
    int i3 = 4;
    if (WhichChecker.isLock(modeEnsuredWhich)) {
      if (WhichChecker.isDex(modeEnsuredWhich)) {
        wallpaperData =
            new WallpaperData(
                i,
                WallpaperUtils.getWallpaperLockDir(i),
                "wallpaper_desktop_lock_orig",
                "wallpaper_desktop_lock");
        i3 = 8;
      } else if (WhichChecker.isSubDisplay(modeEnsuredWhich)) {
        wallpaperData =
            new WallpaperData(
                i,
                WallpaperUtils.getWallpaperLockDir(i),
                "wallpaper_sub_display_lock_orig",
                "wallpaper_sub_display_lock");
        i3 = 16;
      } else {
        wallpaperData =
            new WallpaperData(
                i, WallpaperUtils.getWallpaperLockDir(i), "wallpaper_lock_orig", "wallpaper_lock");
      }
      this.mLockWallpaperMap.put(i, i3, wallpaperData);
    } else {
      Slog.wtf("WallpaperManagerService", "Didn't find wallpaper in non-lock case!");
      if (WhichChecker.isDex(modeEnsuredWhich)) {
        wallpaperData =
            new WallpaperData(
                i,
                WallpaperUtils.getWallpaperDir(i),
                "wallpaper_desktop_orig",
                "wallpaper_desktop");
        i3 = 8;
      } else if (WhichChecker.isSubDisplay(modeEnsuredWhich)) {
        wallpaperData =
            new WallpaperData(
                i,
                WallpaperUtils.getWallpaperDir(i),
                "wallpaper_sub_display_orig",
                "wallpaper_sub_display");
        i3 = 16;
      } else if (WhichChecker.isVirtualDisplay(modeEnsuredWhich)) {
        wallpaperData =
            new WallpaperData(
                i,
                WallpaperUtils.getWallpaperDir(i),
                "wallpaper_virtual_display_orig",
                "wallpaper_virtual_display");
        i3 = 32;
      } else {
        wallpaperData =
            new WallpaperData(i, WallpaperUtils.getWallpaperDir(i), "wallpaper_orig", "wallpaper");
      }
      this.mWallpaperMap.put(i, i3, wallpaperData);
    }
    return wallpaperData;
  }

  public final void loadSettingsLocked(int i, boolean z, int i2) {
    Slog.v("WallpaperManagerService", "loadSettingsLocked");
    loadSettingsLocked(i, z, i2, this.mSemService.getCurrentImplicitMode());
  }

  public final void loadSettingsLocked(int i, boolean z, int i2, int i3) {
    Slog.v(
        "WallpaperManagerService",
        "loadSettingsLocked: userId=" + i + ", which=" + i2 + ", mode=" + i3);
    initializeFallbackWallpaper();
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i3);
    WallpaperData wallpaperData = this.mWallpaperMap.get(i, modeEnsuredWhich);
    WallpaperDataParser.WallpaperLoadingResult loadSettingsLocked =
        this.mWallpaperDataParser.loadSettingsLocked(
            i,
            z,
            wallpaperData,
            this.mLockWallpaperMap.get(i, modeEnsuredWhich),
            i2,
            modeEnsuredWhich);
    boolean z2 = this.mIsLockscreenLiveWallpaperEnabled;
    boolean z3 = (z2 && (i2 & 1) == 0) ? false : true;
    boolean z4 = (z2 && (i2 & 2) == 0) ? false : true;
    if (z3) {
      wallpaperData = loadSettingsLocked.getSystemWallpaperData();
      this.mWallpaperMap.put(i, modeEnsuredWhich, wallpaperData);
      if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY
          && WhichChecker.isSubDisplay(modeEnsuredWhich)
          && !loadSettingsLocked.success()
          && isCoverVideoWallpaperDefault(modeEnsuredWhich | 1)) {
        wallpaperData.mSemWallpaperData.setWpType(-1);
      }
    }
    if (z4) {
      if (loadSettingsLocked.success()) {
        this.mIsInitialLoadSucceed = true;
        this.mLockWallpaperMap.put(i, modeEnsuredWhich, loadSettingsLocked.getLockWallpaperData());
      } else {
        Slog.d("WallpaperManagerService", "Didn't set wallpaperData for lockscreen");
        this.mIsInitialLoadSucceed = false;
        initLockWallpaperData(i, modeEnsuredWhich);
      }
      if (wallpaperData != null) {
        wallpaperData.mWhich &= -3;
      }
    }
  }

  public final void initializeFallbackWallpaper() {
    if (this.mFallbackWallpaper == null) {
      File wallpaperDir = WallpaperUtils.getWallpaperDir(0);
      String fileName = SemWallpaperManagerService.getFileName(1, 0, 0);
      String fileName2 = SemWallpaperManagerService.getFileName(1, 0, 1);
      Log.m129d("WallpaperManagerService", "initializeFallbackWallpaper oriFileName : " + fileName);
      Log.m129d(
          "WallpaperManagerService", "initializeFallbackWallpaper cropFileName : " + fileName2);
      WallpaperData wallpaperData = new WallpaperData(0, wallpaperDir, fileName, fileName2);
      this.mFallbackWallpaper = wallpaperData;
      wallpaperData.allowBackup = false;
      wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
      bindWallpaperComponentLocked(
          this.mImageWallpaper, true, false, this.mFallbackWallpaper, null);
    }
  }

  public void settingsRestored() {
    WallpaperData wallpaperData;
    boolean z;
    if (Binder.getCallingUid() != 1000) {
      throw new RuntimeException("settingsRestored() can only be called from the system process");
    }
    synchronized (this.mLock) {
      loadSettingsLocked(0, false, 3);
      wallpaperData = this.mWallpaperMap.get(0);
      wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
      z = true;
      wallpaperData.allowBackup = true;
      ComponentName componentName = wallpaperData.nextWallpaperComponent;
      if (componentName != null && !componentName.equals(this.mImageWallpaper)) {
        if (!bindWallpaperComponentLocked(
            wallpaperData.nextWallpaperComponent, false, false, wallpaperData, null)) {
          bindWallpaperComponentLocked(null, false, false, wallpaperData, null);
        }
      } else {
        if (!"".equals(wallpaperData.name)) {
          z = this.mWallpaperDataParser.restoreNamedResourceLocked(wallpaperData);
        }
        if (z) {
          this.mWallpaperCropper.generateCrop(wallpaperData);
          bindWallpaperComponentLocked(
              wallpaperData.nextWallpaperComponent, true, false, wallpaperData, null);
        }
      }
    }
    if (!z) {
      Slog.e(
          "WallpaperManagerService", "Failed to restore wallpaper: '" + wallpaperData.name + "'");
      wallpaperData.name = "";
      WallpaperUtils.getWallpaperDir(0).delete();
    }
    synchronized (this.mLock) {
      saveSettingsLocked(0);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    new WallpaperManagerShellCommand(this)
        .exec(
            this,
            fileDescriptor,
            fileDescriptor2,
            fileDescriptor3,
            strArr,
            shellCallback,
            resultReceiver);
  }

  public void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
    String str;
    String str2;
    if (DumpUtils.checkDumpPermission(this.mContext, "WallpaperManagerService", printWriter)) {
      printWriter.print("mDefaultWallpaperComponent=");
      printWriter.println(this.mDefaultWallpaperComponent);
      printWriter.print("mImageWallpaper=");
      printWriter.println(this.mImageWallpaper);
      printWriter.println("mLastWallpaper state:");
      printWriter.print(" User ");
      printWriter.print(this.mLastWallpaper.userId);
      printWriter.print(": id=");
      printWriter.println(this.mLastWallpaper.wallpaperId);
      printWriter.print("  mCropHint=");
      printWriter.println(this.mLastWallpaper.cropHint);
      printWriter.print("  mName=");
      printWriter.println(this.mLastWallpaper.name);
      printWriter.print("  mAllowBackup=");
      printWriter.println(this.mLastWallpaper.allowBackup);
      WallpaperConnection wallpaperConnection = this.mLastWallpaper.connection;
      if (wallpaperConnection != null) {
        printWriter.print("  mLastWallpaper connection ");
        printWriter.print(wallpaperConnection);
        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (wallpaperConnection.mInfo != null) {
          printWriter.print("    mInfo.component=");
          printWriter.println(wallpaperConnection.mInfo.getComponent());
        }
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.lambda$dump$26(
                    printWriter, (WallpaperManagerService.DisplayConnector) obj);
              }
            });
        printWriter.print("    mService=");
        printWriter.println(wallpaperConnection.mService);
        printWriter.print("    mLastDiedTime=");
        str = "    mLastDiedTime=";
        str2 = "    mService=";
        printWriter.println(this.mLastWallpaper.lastDiedTime - SystemClock.uptimeMillis());
      } else {
        str = "    mLastDiedTime=";
        str2 = "    mService=";
      }
      if (this.mLastDexWallpaper != null) {
        printWriter.println("mLastDexWallpaper state:");
        printWriter.print(" User ");
        printWriter.print(this.mLastDexWallpaper.userId);
        printWriter.print(": id=");
        printWriter.println(this.mLastDexWallpaper.wallpaperId);
        printWriter.print("  mCropHint=");
        printWriter.println(this.mLastDexWallpaper.cropHint);
        printWriter.print("  mName=");
        printWriter.println(this.mLastDexWallpaper.name);
        printWriter.print("  mAllowBackup=");
        printWriter.println(this.mLastDexWallpaper.allowBackup);
        WallpaperConnection wallpaperConnection2 = this.mLastDexWallpaper.connection;
        if (wallpaperConnection2 != null) {
          printWriter.print("  mLastDexWallpaper connection ");
          printWriter.print(wallpaperConnection2);
          printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
          if (wallpaperConnection2.mInfo != null) {
            printWriter.print("    mInfo.component=");
            printWriter.println(wallpaperConnection2.mInfo.getComponent());
          }
          wallpaperConnection2.forEachDisplayConnector(
              new Consumer() { // from class:
                               // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WallpaperManagerService.lambda$dump$27(
                      printWriter, (WallpaperManagerService.DisplayConnector) obj);
                }
              });
          printWriter.print(str2);
          printWriter.println(wallpaperConnection2.mService);
          printWriter.print(str);
          printWriter.println(this.mLastDexWallpaper.lastDiedTime - SystemClock.uptimeMillis());
        }
      }
      printWriter.println(" Display state:");
      this.mWallpaperDisplayHelper.forEachDisplayData(
          new Consumer() { // from class:
                           // com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WallpaperManagerService.lambda$dump$28(
                  printWriter, (WallpaperDisplayHelper.DisplayData) obj);
            }
          });
      this.mSemService.dump(fileDescriptor, printWriter, strArr);
      this.mWallpaperMap.dump(printWriter);
      this.mLockWallpaperMap.dump(printWriter);
      this.mAssetFileManager.dump(printWriter, this.mCurrentUserId);
      Log.dump("WallpaperManagerService", fileDescriptor, printWriter, strArr);
    }
  }

  public static /* synthetic */ void lambda$dump$26(
      PrintWriter printWriter, DisplayConnector displayConnector) {
    printWriter.print("     mDisplayId=");
    printWriter.println(displayConnector.mDisplayId);
    printWriter.print("     mToken=");
    printWriter.println(displayConnector.mToken);
    printWriter.print("     mEngine=");
    printWriter.println(displayConnector.mEngine);
  }

  public static /* synthetic */ void lambda$dump$27(
      PrintWriter printWriter, DisplayConnector displayConnector) {
    printWriter.print("     mDisplayId=");
    printWriter.println(displayConnector.mDisplayId);
    printWriter.print("     mToken=");
    printWriter.println(displayConnector.mToken);
    printWriter.print("     mEngine=");
    printWriter.println(displayConnector.mEngine);
  }

  public static /* synthetic */ void lambda$dump$28(
      PrintWriter printWriter, WallpaperDisplayHelper.DisplayData displayData) {
    printWriter.print("  displayId=");
    printWriter.println(displayData.mDisplayId);
    printWriter.print("  mWidth=");
    printWriter.print(displayData.mWidth);
    printWriter.print("  mHeight=");
    printWriter.println(displayData.mHeight);
    printWriter.print("  mPadding=");
    printWriter.println(displayData.mPadding);
  }

  public void notifyPid(int i, int i2, String str, boolean z) {
    ActivityManagerPerformance booster = ActivityManagerPerformance.getBooster();
    if (booster != null) {
      booster.notifyPidOfWallpaper(i, i2, str, z);
    }
  }

  public boolean isDesktopMode() {
    return this.mSemService.mDesktopMode.isDesktopMode();
  }

  public int getDesktopMode() {
    return this.mSemService.mDesktopMode.getDesktopMode();
  }

  public boolean isDesktopModeEnabled(int i) {
    return this.mSemService.mDesktopMode.isDesktopModeEnabled(i);
  }

  public boolean isDesktopStandAloneMode() {
    return this.mSemService.mDesktopMode.isDesktopSingleMode();
  }

  public final void checkWallpaperData(int i, WallpaperData wallpaperData, int i2, int i3) {
    Log.m129d(
        "WallpaperManagerService",
        "checkWallpaperData userId="
            + i
            + ", wpType="
            + i2
            + ", wallpaperFile= "
            + wallpaperData.wallpaperFile);
    if (i2 == 0 && wallpaperData.wallpaperFile == null) {
      boolean isLock = WhichChecker.isLock(i3);
      wallpaperData.setDefaultWallpaperData(
          isLock ? WallpaperUtils.getWallpaperLockDir(i) : WallpaperUtils.getWallpaperDir(i),
          SemWallpaperManagerService.getFileName(i3, isLock ? 1 : 0, 0),
          SemWallpaperManagerService.getFileName(i3, isLock ? 1 : 0, 1));
      (isLock ? this.mLockWallpaperMap : this.mWallpaperMap)
          .put(i, WhichChecker.getMode(i3), wallpaperData);
    }
  }

  public int getLockWallpaperType() {
    return semGetWallpaperType(2);
  }

  public int semGetWallpaperType(int i) {
    int currentUserId = getCurrentUserId();
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(i, currentUserId);
      if (peekPairingConsideredWallpaperDataLocked != null) {
        int wpType = peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getWpType();
        Slog.d(
            "WallpaperManagerService",
            "semGetWallpaperType: which="
                + i
                + ", return type="
                + wpType
                + " (called by userId= "
                + currentUserId
                + ")");
        return wpType;
      }
      SemWallpaperManagerService.putLog("semGetWallpaperType : return -1. which=" + i);
      return -1;
    }
  }

  public ComponentName semGetWallpaperComponent(int i, int i2) {
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(i, i2);
      if (peekPairingConsideredWallpaperDataLocked == null) {
        return null;
      }
      return peekPairingConsideredWallpaperDataLocked.wallpaperComponent;
    }
  }

  public boolean isDefaultWallpaperState(int i) {
    WallpaperData peekWallpaperDataLocked;
    int currentUserId = getCurrentUserId();
    synchronized (this.mLock) {
      peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, i);
    }
    if (peekWallpaperDataLocked == null) {
      return true;
    }
    int wpType = peekWallpaperDataLocked.mSemWallpaperData.getWpType();
    if (wpType != getDefaultWallpaperType(i)) {
      Log.m129d(
          "WallpaperManagerService", "isDefaultWallpaperState false, currentType = " + wpType);
      return false;
    }
    File file = peekWallpaperDataLocked.cropFile;
    if (file == null || !file.exists()) {
      return true;
    }
    Log.m129d("WallpaperManagerService", "isDefaultWallpaperState false, cropFile is not null");
    return false;
  }

  public Rect semGetWallpaperCropHint(int i) {
    WallpaperData peekPairingConsideredWallpaperDataLocked;
    checkPermission("android.permission.SET_WALLPAPER");
    int currentUserId = getCurrentUserId();
    synchronized (this.mLock) {
      peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(i, currentUserId);
    }
    if (peekPairingConsideredWallpaperDataLocked != null) {
      return peekPairingConsideredWallpaperDataLocked.cropHint;
    }
    return null;
  }

  public void setVideoWallpaper(
      String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) {
    if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
      Slog.e(
          "WallpaperManagerService",
          "setVideoWallpaper() packageName is null or empty. videoFilePath = "
              + str
              + ", themePackage = "
              + str2
              + ", userId = "
              + i
              + ", videoFileName = "
              + str3
              + ", which = "
              + i2
              + ", allowBackup = "
              + z);
      return;
    }
    checkPermission("android.permission.SET_WALLPAPER");
    if (this.mSemService.mKnox.isWallpaperChangeAllowed(true)) {
      Log.m129d(
          "WallpaperManagerService",
          "setVideoWallpaper() videoFilePath = "
              + str
              + ", themePackage = "
              + str2
              + ", userId = "
              + i
              + ", callingPackage = "
              + str4
              + ", which = "
              + i2
              + ", videoFileName = "
              + str3
              + ", allowBackup = "
              + z
              + ", extras = "
              + bundle);
      saveVideoWallpaperData(
          i2,
          str,
          str2,
          str3,
          str4,
          i,
          "com.samsung.android.wallpaper.res".equals(str2),
          z,
          bundle);
    }
  }

  public final void saveVideoWallpaperData(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      int i2,
      boolean z,
      boolean z2,
      Bundle bundle) {
    WallpaperData wallpaperSafeLocked;
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      wallpaperSafeLocked = getWallpaperSafeLocked(i2, modeEnsuredWhich);
    }
    if (wallpaperSafeLocked == null) {
      Slog.e("WallpaperManagerService", "saveVideoWallpaperData wallpaper == null");
      return;
    }
    if (WhichChecker.isSystem(modeEnsuredWhich)) {
      resetSemWallpaperData(wallpaperSafeLocked.mSemWallpaperData, i2);
    }
    int mode = WhichChecker.getMode(modeEnsuredWhich);
    wallpaperSafeLocked.mSemWallpaperData.setWpType(8);
    wallpaperSafeLocked.mSemWallpaperData.setVideoFilePath(str);
    wallpaperSafeLocked.mSemWallpaperData.setVideoPkgName(str2);
    wallpaperSafeLocked.mSemWallpaperData.setVideoFileName(str3);
    wallpaperSafeLocked.mSemWallpaperData.setIsPreloaded(z);
    wallpaperSafeLocked.mSemWallpaperData.setUri(null);
    wallpaperSafeLocked.mSemWallpaperData.setExternalParams(bundle);
    wallpaperSafeLocked.mWhich = modeEnsuredWhich;
    wallpaperSafeLocked.setCallingPackage(str4);
    wallpaperSafeLocked.allowBackup = z2;
    synchronized (this.mLock) {
      wallpaperSafeLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
    }
    this.mSemService.generateResizedBitmap(
        wallpaperSafeLocked.cropFile, wallpaperSafeLocked.mSemWallpaperData);
    if (TextUtils.isEmpty(str2)
        && (TextUtils.isEmpty(str4) || !"com.samsung.android.themecenter".equals(str4))) {
      Log.m129d("WallpaperManagerService", "saveVideoWallpaperData: Set allowBackup true.");
      wallpaperSafeLocked.allowBackup = true;
    }
    if (bundle != null) {
      Rect rect = (Rect) bundle.getParcelable("videoCropHint");
      Log.m129d("WallpaperManagerService", "saveVideoWallpaperData cropHint = " + rect);
      if (rect != null) {
        wallpaperSafeLocked.cropHint.set(rect);
      } else {
        wallpaperSafeLocked.cropHint.set(0, 0, 0, 0);
      }
    } else {
      wallpaperSafeLocked.cropHint.set(0, 0, 0, 0);
    }
    wallpaperSafeLocked.cleanUp();
    synchronized (this.mLock) {
      saveSettingsLocked(i2, mode);
      if (!WhichChecker.isLock(modeEnsuredWhich)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperSafeLocked, null);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          if (WhichChecker.isWatchFaceDisplay(modeEnsuredWhich)) {
            notifyCoverWallpaperChanged(
                wallpaperSafeLocked.mSemWallpaperData.getWpType(), modeEnsuredWhich);
          }
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
    }
    if (WhichChecker.isLock(modeEnsuredWhich)) {
      notifyLockWallpaperChanged(
          wallpaperSafeLocked.mSemWallpaperData.getWpType(), modeEnsuredWhich);
    }
    this.mSemService.mLegibilityColor.extractColor(
        wallpaperSafeLocked.mSemWallpaperData.getWhich());
  }

  public boolean isVideoWallpaper() {
    WallpaperData peekWallpaperDataLocked =
        peekWallpaperDataLocked(
            this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2));
    if (peekWallpaperDataLocked != null) {
      boolean z = peekWallpaperDataLocked.mSemWallpaperData.getWpType() == 8;
      Slog.d(
          "WallpaperManagerService",
          "isVideoWallpaper userId=" + this.mCurrentUserId + " isVideoWallpaper=" + z);
      return z;
    }
    Slog.d("WallpaperManagerService", "Lock wallpaper data is null. So kwp can not be determined");
    return false;
  }

  public boolean hasVideoWallpaper() {
    WallpaperData peekWallpaperDataLocked =
        peekWallpaperDataLocked(
            this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2));
    if (peekWallpaperDataLocked != null) {
      int wpType = peekWallpaperDataLocked.mSemWallpaperData.getWpType();
      if (wpType == 3) {
        try {
          return Boolean.parseBoolean(
              Uri.parse(peekWallpaperDataLocked.mSemWallpaperData.getUri())
                  .getQueryParameter("hasVideo"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (wpType == 8) {
        return true;
      }
      return false;
    }
    Slog.d("WallpaperManagerService", "Lock wallpaper data is null. So kwp can not be determined");
    return false;
  }

  public ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(
      int i, int i2, int i3, int i4, int i5) {
    File initializeThumnailFile;
    boolean z;
    ParcelFileDescriptor parcelFileDescriptor;
    int callingUid = Binder.getCallingUid();
    if (!isSignedWithPlatformSignature(callingUid)) {
      throw new SecurityException(
          "Calling app does not have required permission. uid = " + callingUid);
    }
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i3);
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, modeEnsuredWhich);
      if (peekWallpaperDataLocked == null) {
        return null;
      }
      if (7 == i) {
        z = this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(peekWallpaperDataLocked);
        initializeThumnailFile = null;
      } else {
        initializeThumnailFile =
            this.mSemService.initializeThumnailFile(
                peekWallpaperDataLocked, modeEnsuredWhich, i, i2);
        z = false;
      }
      if (z) {
        initializeThumnailFile =
            this.mLiveWallpaperHelper.getThumbnailFile(modeEnsuredWhich, i2, i4);
      }
      if (initializeThumnailFile == null) {
        return null;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          parcelFileDescriptor = ParcelFileDescriptor.open(initializeThumnailFile, i5);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (FileNotFoundException unused) {
        parcelFileDescriptor = null;
      }
      try {
        if (!SELinux.restorecon(initializeThumnailFile)) {
          IoUtils.closeQuietly(parcelFileDescriptor);
          return null;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Slog.d(
            "WallpaperManagerService",
            "getWallpaperThumbnailFileDescriptor: which="
                + modeEnsuredWhich
                + ", orientation="
                + i4
                + ", fileMode="
                + i5
                + ", fd="
                + parcelFileDescriptor);
        return parcelFileDescriptor;
      } catch (FileNotFoundException unused2) {
        IoUtils.closeQuietly(parcelFileDescriptor);
        return null;
      }
    }
  }

  public String getVideoFileName(int i) {
    int currentUserId = getCurrentUserId();
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, currentUserId);
      if (peekPairingConsideredWallpaperDataLocked != null) {
        String videoFileName =
            peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getVideoFileName();
        Slog.d(
            "WallpaperManagerService",
            "getVideoFileName: userId="
                + currentUserId
                + ", which = "
                + modeEnsuredWhich
                + ", videoFileName="
                + videoFileName);
        return videoFileName;
      }
      Slog.d(
          "WallpaperManagerService",
          "getVideoFileName: Lock wallpaper data is null. So videoFileName is null");
      return null;
    }
  }

  public String getVideoFilePath(int i) {
    int currentUserId = getCurrentUserId();
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, currentUserId);
      if (peekPairingConsideredWallpaperDataLocked != null) {
        String videoFilePath =
            peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getVideoFilePath();
        Slog.d(
            "WallpaperManagerService",
            "getVideoFilePath userId=" + currentUserId + " path=" + videoFilePath);
        return videoFilePath;
      }
      Slog.d(
          "WallpaperManagerService",
          "getVideoFilePath: Lock wallpaper data is null. So video file path is null");
      return null;
    }
  }

  public String getVideoPackage(int i) {
    int currentUserId = getCurrentUserId();
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, currentUserId);
      if (peekPairingConsideredWallpaperDataLocked != null) {
        String videoPkgName =
            peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getVideoPkgName();
        Slog.d(
            "WallpaperManagerService",
            "getVideoFilePath userId="
                + currentUserId
                + " pkg="
                + videoPkgName
                + " which= "
                + modeEnsuredWhich);
        return videoPkgName;
      }
      Slog.d("WallpaperManagerService", "Lock wallpaper data is null. So video pkg is null");
      return null;
    }
  }

  public final void initCoverVideoWallpaper() {
    WallpaperData wallpaperSafeLocked;
    Log.m129d("WallpaperManagerService", "initCoverVideoWallpaper");
    synchronized (this.mLock) {
      wallpaperSafeLocked = getWallpaperSafeLocked(this.mCurrentUserId, 17);
    }
    wallpaperSafeLocked.mSemWallpaperData.setVideoDefaultHasBeenUsed(true);
    WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
    String defaultVideoWallpaperFileName =
        this.mSemWallpaperResourcesInfo.getDefaultVideoWallpaperFileName(17);
    Log.m129d(
        "WallpaperManagerService",
        "initCoverVideoWallpaper: fileName = " + defaultVideoWallpaperFileName);
    if (TextUtils.isEmpty(defaultVideoWallpaperFileName)) {
      return;
    }
    wallpaperManager.setVideoWallpaper(
        null,
        "com.samsung.android.wallpaper.res",
        defaultVideoWallpaperFileName,
        this.mCurrentUserId,
        17,
        false,
        false,
        null);
  }

  public final void initVideoWallpaper(boolean z) {
    initVideoWallpaper(z, true);
  }

  public final void initVideoWallpaper(int i, boolean z) {
    initVideoWallpaper(i, z, false);
  }

  public final void initVideoWallpaper(boolean z, boolean z2) {
    initVideoWallpaper(6, z, false);
  }

  public final void initVideoWallpaper(int i, boolean z, boolean z2) {
    WallpaperData wallpaperSafeLocked;
    Log.m129d(
        "WallpaperManagerService",
        "initVideoWallpaper: which = " + i + ", forceInit = " + z + ", updateSetting = " + z2);
    if (WallpaperManager.isDefaultOperatorWallpaper(this.mContext, i)) {
      return;
    }
    synchronized (this.mLock) {
      wallpaperSafeLocked = getWallpaperSafeLocked(this.mCurrentUserId, i);
    }
    if (wallpaperSafeLocked == null) {
      Slog.e("WallpaperManagerService", "initVideWallpaper lockWallpaper == null");
    }
    if (!wallpaperSafeLocked.mSemWallpaperData.getVideoDefaultHasBeenUsed() || z) {
      wallpaperSafeLocked.mSemWallpaperData.setVideoDefaultHasBeenUsed(true);
      String oMCVideoWallpaperFilePath = WallpaperManager.getOMCVideoWallpaperFilePath(null);
      WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
      boolean z3 = !TextUtils.isEmpty(oMCVideoWallpaperFilePath);
      String defaultVideoWallpaperFileName =
          this.mSemWallpaperResourcesInfo.getDefaultVideoWallpaperFileName(i);
      Log.m129d(
          "WallpaperManagerService",
          "initVideoWallpaper: omcVideoFilePath = "
              + oMCVideoWallpaperFilePath
              + " , hasOMCVideoWallpaper = "
              + z3
              + " , fileName = "
              + defaultVideoWallpaperFileName);
      if (z3) {
        wallpaperManager.setVideoWallpaper(
            oMCVideoWallpaperFilePath, "android", null, this.mCurrentUserId, i, z2, false, null);
      } else {
        if (TextUtils.isEmpty(defaultVideoWallpaperFileName)) {
          return;
        }
        wallpaperManager.setVideoWallpaper(
            null,
            "com.samsung.android.wallpaper.res",
            defaultVideoWallpaperFileName,
            this.mCurrentUserId,
            i,
            z2,
            false,
            null);
      }
    }
  }

  public void setMotionWallpaper(String str, String str2, int i, boolean z) {
    WallpaperData peekWallpaperDataLocked;
    checkPermission("android.permission.SET_WALLPAPER");
    if (this.mSemService.mKnox.isWallpaperChangeAllowed(true)) {
      int i2 = this.mCurrentUserId;
      if (TextUtils.isEmpty(str)) {
        Slog.e(
            "WallpaperManagerService",
            "packageName is null or empty. packageName=" + str + " userId=" + i2);
        return;
      }
      Slog.d(
          "WallpaperManagerService",
          "setMotionWallpaper pkgName = "
              + str
              + ", userId = "
              + i2
              + ", callingPackage = "
              + str2
              + ", which = "
              + i
              + ", allowBackup = "
              + z);
      int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
      synchronized (this.mLock) {
        peekWallpaperDataLocked = peekWallpaperDataLocked(i2, folderStateBasedWhich);
      }
      if (peekWallpaperDataLocked == null) {
        Slog.e("WallpaperManagerService", "setMotionWallpaper wallpaper == null");
        return;
      }
      peekWallpaperDataLocked.mSemWallpaperData.setWpType(1);
      peekWallpaperDataLocked.mSemWallpaperData.setMotionPkgName(str);
      peekWallpaperDataLocked.mSemWallpaperData.setIsPreloaded(false);
      peekWallpaperDataLocked.mSemWallpaperData.setUri(null);
      peekWallpaperDataLocked.mSemWallpaperData.setExternalParams(null);
      peekWallpaperDataLocked.setCallingPackage(str2);
      peekWallpaperDataLocked.allowBackup = z;
      this.mSemService.generateResizedBitmap(
          peekWallpaperDataLocked.cropFile, peekWallpaperDataLocked.mSemWallpaperData);
      peekWallpaperDataLocked.cleanUp();
      synchronized (this.mLock) {
        peekWallpaperDataLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
        saveSettingsLocked(i2);
      }
      notifyLockWallpaperChanged(
          peekWallpaperDataLocked.mSemWallpaperData.getWpType(), folderStateBasedWhich);
      this.mSemService.mLegibilityColor.extractColor(
          peekWallpaperDataLocked.mSemWallpaperData.getWhich());
    }
  }

  public String getMotionWallpaperPkgName(int i) {
    int currentUserId = getCurrentUserId();
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, currentUserId);
      if (peekPairingConsideredWallpaperDataLocked != null) {
        String motionPkgName =
            peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getMotionPkgName();
        Slog.d(
            "WallpaperManagerService",
            "getMotionWallpaperPkgName: userId="
                + currentUserId
                + " name="
                + motionPkgName
                + " , which="
                + modeEnsuredWhich);
        return motionPkgName;
      }
      Slog.d(
          "WallpaperManagerService",
          "getMotionWallpaperPkgName: Lock wallpaper data is null. So motion package name is null");
      return null;
    }
  }

  public void setAnimatedWallpaper(String str, String str2, int i, boolean z) {
    checkPermission("android.permission.SET_WALLPAPER");
    if (this.mSemService.mKnox.isWallpaperChangeAllowed(true)) {
      int i2 = this.mCurrentUserId;
      if (TextUtils.isEmpty(str)) {
        Slog.e(
            "WallpaperManagerService",
            "packageName is null or empty. packageName = " + str + ", userId = " + i2);
        return;
      }
      Slog.d(
          "WallpaperManagerService",
          "setAnimatedWallpaper pkgName = "
              + str
              + ", userId = "
              + i2
              + ", callingPackage = "
              + str2
              + ", which = "
              + i
              + ", allowBackup = "
              + z);
      saveAnimatedWallpaperData(i, str, str2, i2, z);
    }
  }

  public final void saveAnimatedWallpaperData(int i, String str, String str2, int i2, boolean z) {
    WallpaperData wallpaperSafeLocked;
    synchronized (this.mLock) {
      wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
    }
    int mode = WhichChecker.getMode(i);
    if (wallpaperSafeLocked != null) {
      wallpaperSafeLocked.mSemWallpaperData.setWpType(4);
      wallpaperSafeLocked.mSemWallpaperData.setAnimatedPkgName(str);
      wallpaperSafeLocked.mSemWallpaperData.setIsPreloaded(false);
      wallpaperSafeLocked.mSemWallpaperData.setUri(null);
      wallpaperSafeLocked.mSemWallpaperData.setExternalParams(null);
      wallpaperSafeLocked.setCallingPackage(str2);
      wallpaperSafeLocked.allowBackup = z;
      this.mSemService.generateResizedBitmap(
          wallpaperSafeLocked.cropFile, wallpaperSafeLocked.mSemWallpaperData);
      wallpaperSafeLocked.cleanUp();
      synchronized (this.mLock) {
        wallpaperSafeLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
        saveSettingsLocked(i2, mode);
      }
      notifyLockWallpaperChanged(wallpaperSafeLocked.mSemWallpaperData.getWpType(), i);
    } else {
      Log.m132i("WallpaperManagerService", "saveAnimatedWallpaperData: wallpaper data is null.");
    }
    this.mSemService.mLegibilityColor.extractColor(
        wallpaperSafeLocked.mSemWallpaperData.getWhich());
  }

  public String getAnimatedPkgName(int i) {
    int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
    synchronized (this.mLock) {
      WallpaperData peekPairingConsideredWallpaperDataLocked =
          peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, getCurrentUserId());
      if (peekPairingConsideredWallpaperDataLocked != null) {
        String animatedPkgName =
            peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getAnimatedPkgName();
        Slog.d("WallpaperManagerService", "getAnimatedPkgName: userId=0 name=" + animatedPkgName);
        return animatedPkgName;
      }
      Slog.d(
          "WallpaperManagerService",
          "getAnimatedPkgName: Lock wallpaper data is null. So animated package name is null");
      return null;
    }
  }

  public class WallpaperRestoreCompletion extends IWallpaperManagerCallback.Stub {
    public final CountDownLatch mLatch = new CountDownLatch(1);

    public void onSemBackupStatusChanged(int i, int i2, int i3) {}

    public void onSemMultipackApplied(int i) {}

    public void onSemWallpaperChanged(int i, int i2, Bundle bundle) {}

    public void onSemWallpaperColorsAnalysisRequested(int i, int i2) {}

    public void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {}

    public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {}

    public WallpaperRestoreCompletion() {}

    public void waitForCompletion() {
      try {
        this.mLatch.await(30L, TimeUnit.SECONDS);
      } catch (InterruptedException unused) {
      }
    }

    public void onWallpaperChanged() {
      this.mLatch.countDown();
    }
  }

  public final void handleOMCWallpaperUpdatedLocked(int i) {
    synchronized (this.mLock) {
      WallpaperData wallpaperData = this.mWallpaperMap.get(UserHandle.getCallingUserId(), i);
      if (wallpaperData == null) {
        return;
      }
      wallpaperData.mSemWallpaperData.getWhich();
      if (this.mSemService.mOMCWallpaper.needToUpdateOMCWallpaper(wallpaperData.wallpaperFile)) {
        notifyCallbacksLocked(wallpaperData);
        if (isDefaultComponent(wallpaperData)) {
          Log.m132i(
              "WallpaperManagerService",
              "handleOMCWallpaperUpdated: mCacheDefaultImageWallpaperColors = "
                  + this.mCacheDefaultImageWallpaperColors);
          wallpaperData.mSemWallpaperData.setCroppedBitmap(null);
          this.mCacheDefaultImageWallpaperColors = null;
          setWallpaperComponent(i | 1, this.mImageWallpaper);
          if (WallpaperManager.isDefaultOperatorWallpaper(
              this.mContext, 2, this.mSemService.mCMFWallpaper.getDeviceColor())) {
            clearWallpaper("android", i | 2, UserHandle.getCallingUserId());
          }
          if (!this.mSemService.hasLockscreenWallpaper(WhichChecker.isSubDisplay(i))) {
            setKWPTypeLiveWallpaperWithMode(i, 1);
          }
        } else {
          Slog.e(
              "WallpaperManagerService",
              "handleOMCWallpaperUpdatedLocked: Fail to set OMC wallpaper, component = "
                  + wallpaperData.wallpaperComponent);
        }
      }
      this.mSemService.mLegibilityColor.extractColor(
          getWallpaperSafeLocked(getCurrentUserId(), i | 1).mSemWallpaperData.getWhich());
      this.mSemService.mLegibilityColor.extractColor(
          getWallpaperSafeLocked(getCurrentUserId(), i | 2).mSemWallpaperData.getWhich());
    }
  }

  public final boolean isDefaultComponent(WallpaperData wallpaperData) {
    String lastCallingPackage = wallpaperData.mSemWallpaperData.getLastCallingPackage();
    int which = wallpaperData.mSemWallpaperData.getWhich();
    if (getDefaultWallpaperType(which) != 7) {
      return this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)
          || wallpaperData.wallpaperComponent == null
          || TextUtils.isEmpty(lastCallingPackage);
    }
    ComponentName componentName = wallpaperData.wallpaperComponent;
    if ((wallpaperData.mSemWallpaperData.getWpType() == 7
            && componentName != null
            && componentName.equals(
                getDefaultPreloadedLiveWallpaperComponentName(
                    wallpaperData.mSemWallpaperData.getWhich())))
        || componentName == null) {
      return true;
    }
    Slog.d(
        "WallpaperManagerService",
        "isDefaultComponent: which = "
            + which
            + ", type = "
            + wallpaperData.mSemWallpaperData.getWpType()
            + ", ComponentName = "
            + componentName);
    return false;
  }

  public int getDisplayId(int i) {
    return this.mSemService.getDisplayId(i);
  }

  public boolean isVirtualWallpaperDisplay(int i) {
    return this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(i);
  }

  public void setKWPTypeLiveWallpaper(int i) {
    this.mSemService.mDefaultWallpaper.setKWPTypeLiveWallpaper(i);
  }

  public void setKWPTypeLiveWallpaperWithMode(int i, int i2) {
    if (WhichChecker.isVirtualDisplay(i) || WhichChecker.isDex(i)) {
      return;
    }
    if (i2 == 0) {
      if (!this.mSemService.hasLockscreenWallpaper(WhichChecker.isSubDisplay(i))) {
        Log.m129d(
            "WallpaperManagerService",
            "setKWPTypeLiveWallpaperWithMode: Setting value for transparent is already 0.");
        return;
      }
    } else if (i2 == 1) {
      if (this.mSemService.hasLockscreenWallpaper(WhichChecker.isSubDisplay(i))) {
        Log.m129d(
            "WallpaperManagerService",
            "setKWPTypeLiveWallpaperWithMode: Setting value for transparent is already 1.");
        return;
      }
    } else {
      Log.m130e("WallpaperManagerService", "setKWPTypeLiveWallpaperWithMode: Error. value = " + i2);
    }
    this.mSemService.mDefaultWallpaper.setKWPTypeLiveWallpaper(i2, i);
  }

  public void copyFileToWallpaperFile(int i, String str) {
    copyFileToWallpaperFile(i, false, str);
  }

  public void copyPreloadedFileToWallpaperFile(int i, String str) {
    copyFileToWallpaperFile(i, true, str);
  }

  public final void copyFileToWallpaperFile(int i, boolean z, String str) {
    int i2;
    WallpaperData wallpaperSafeLocked;
    Slog.d(
        "WallpaperManagerService",
        "copyFileToWallpaperFile: which = "
            + i
            + ", isPreloaded = "
            + z
            + ", callingPackage = "
            + str);
    if ("android.app.cts.wallpapers".equals(str)) {
      return;
    }
    int i3 = this.mCurrentUserId;
    int mode = WhichChecker.getMode(this.mSemService.getModeEnsuredWhich(i));
    WallpaperData wallpaperData = this.mWallpaperMap.get(i3, mode);
    File file = wallpaperData != null ? wallpaperData.wallpaperFile : null;
    WallpaperRestoreCompletion wallpaperRestoreCompletion = new WallpaperRestoreCompletion();
    initLockWallpaperData(i3, mode);
    synchronized (this.mLock) {
      i2 = mode | 2;
      wallpaperSafeLocked = getWallpaperSafeLocked(i3, i2);
    }
    wallpaperSafeLocked.mSemWallpaperData.setWpType(0);
    wallpaperSafeLocked.imageWallpaperPending = true;
    wallpaperSafeLocked.mWhich = mode | 3;
    wallpaperSafeLocked.setComplete = wallpaperRestoreCompletion;
    wallpaperSafeLocked.mSemWallpaperData.setIsCopied(true);
    wallpaperSafeLocked.mSemWallpaperData.setIsPreloaded(z);
    if (wallpaperData != null) {
      wallpaperSafeLocked.name = wallpaperData.name;
      wallpaperSafeLocked.allowBackup = wallpaperData.allowBackup;
      wallpaperSafeLocked.cropHint.set(wallpaperData.cropHint);
      wallpaperSafeLocked.setCallingPackage(wallpaperData.mSemWallpaperData.getWallpaperHistory());
      wallpaperSafeLocked.mSemWallpaperData.setExternalParams(
          wallpaperData.mSemWallpaperData.getExternalParams());
      wallpaperSafeLocked.mSemWallpaperData.setOrientation(wallpaperData.getOrientation());
    }
    if (FileUtils.copyFile(file, wallpaperSafeLocked.wallpaperFile)) {
      if (wallpaperSafeLocked.wallpaperFile.exists()) {
        Slog.v(
            "WallpaperManagerService",
            "copyFileToWallpaperFile: restorecon() of lock file returned "
                + SELinux.restorecon(wallpaperSafeLocked.wallpaperFile.getAbsoluteFile()));
        wallpaperRestoreCompletion.waitForCompletion();
      } else {
        Slog.e(
            "WallpaperManagerService",
            "copyFileToWallpaperFile: lockWallpaper.wallpaperFile does not exist.");
      }
    } else {
      Slog.e("WallpaperManagerService", "copyFileToWallpaperFile: failed copyFile (0x03)");
    }
    this.mSemService.mDefaultWallpaper.setKWPTypeLiveWallpaper(1);
    this.mSemService.mDefaultWallpaper.updateTransparencySettingIfNeed(str, i2, z);
  }

  public boolean isSystemAndLockPaired(int i) {
    int currentUserId = getCurrentUserId();
    boolean isSystemAndLockPaired = isSystemAndLockPaired(currentUserId, i);
    Slog.d(
        "WallpaperManagerService",
        "isSystemAndLockPaired : mode="
            + i
            + ", isPaired="
            + isSystemAndLockPaired
            + " (called by userId= "
            + currentUserId
            + ")");
    return isSystemAndLockPaired;
  }

  public int getHighlightFilterState(int i) {
    int highlightFilterState;
    synchronized (this.mLock) {
      highlightFilterState =
          getHighlightFilterState(peekPairingConsideredWallpaperDataLocked(i, getCurrentUserId()));
    }
    return highlightFilterState;
  }

  public final int getHighlightFilterState(WallpaperData wallpaperData) {
    if (wallpaperData == null) {
      Slog.e(
          "WallpaperManagerService",
          "getHighlightFilterState : filterState=-1, failed to get wallpaper");
      return -1;
    }
    int which = wallpaperData.mSemWallpaperData.getWhich();
    int i = wallpaperData.userId;
    if (!WhichChecker.isSystem(which)) {
      Slog.e(
          "WallpaperManagerService",
          "getHighlightFilterState : which="
              + which
              + ", filterState=0, not System type wallpaper data");
      return 0;
    }
    if (wallpaperData.mSemWallpaperData.getIsPreloaded()) {
      Slog.i(
          "WallpaperManagerService",
          "getHighlightFilterState : which=" + which + ", filterState=0, preloaded wallpaper");
      return 0;
    }
    if (WhichChecker.isWatchFaceDisplay(which)
        || WhichChecker.isVirtualDisplay(which)
        || WhichChecker.isDex(which)) {
      Slog.e(
          "WallpaperManagerService",
          "getHighlightFilterState : which=" + which + ", filterState=0, unsupported mode");
      return 0;
    }
    if (isOpenThemeWallpaperApplied(wallpaperData)) {
      Slog.i(
          "WallpaperManagerService",
          "getHighlightFilterState : which="
              + which
              + ", filterState=0, open theme wallpaper enabled");
      return 0;
    }
    SemWallpaperColors primarySemColors = wallpaperData.mSemWallpaperData.getPrimarySemColors();
    if (primarySemColors == null) {
      Slog.i(
          "WallpaperManagerService",
          "getHighlightFilterState : which="
              + which
              + ", filterState=-1, primary color is not ready");
      return -1;
    }
    SemWallpaperColors.Item item = primarySemColors.get(64L);
    if (item == null) {
      Slog.i(
          "WallpaperManagerService",
          "getHighlightFilterState : which="
              + which
              + ", filterState=-1, failed to get home screen color item");
      return -1;
    }
    int i2 = item.getFontColor() == 0 ? 1 : 2;
    Slog.d(
        "WallpaperManagerService",
        "getHighlightFilterState : which="
            + which
            + ", filterState="
            + i2
            + ", callerUserId= "
            + i);
    return i2;
  }

  public final boolean isSystemAndLockPaired(int i, int i2) {
    int mode = WhichChecker.getMode(i2);
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i, mode | 2);
      if (peekWallpaperDataLocked == null) {
        return true;
      }
      SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
      if (semWallpaperData.getWpType() == 0 && semWallpaperData.getIsCopied()) {
        return true;
      }
      if (!this.mIsLockscreenLiveWallpaperEnabled) {
        WallpaperData peekWallpaperDataLocked2 = peekWallpaperDataLocked(i, mode | 1);
        if (peekWallpaperDataLocked2 == null) {
          return false;
        }
        if (peekWallpaperDataLocked2.mSemWallpaperData.getWpType() == 7
            && !this.mSemService.hasLockscreenWallpaper(WhichChecker.isSubDisplay(mode))) {
          return true;
        }
      }
      return false;
    }
  }

  public void semSendWallpaperCommand(int i, String str, Bundle bundle) {
    semSendWallpaperCommand(i, str, bundle, false);
  }

  /* JADX WARN: Code restructure failed: missing block: B:33:0x009b, code lost:

     if (com.samsung.android.wallpaper.Rune.SUPPORT_COVER_DISPLAY_WATCHFACE != false) goto L37;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void semSendWallpaperCommand(int i, String str, Bundle bundle, boolean z) {
    boolean z2;
    int i2 = 1;
    if (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER
        && WhichChecker.isLock(i)
        && !isSystemAndLockPaired(WhichChecker.getMode(i))
        && hasPermission("android.permission.READ_WALLPAPER_INTERNAL")) {
      synchronized (this.mLock) {
        WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
        z2 =
            peekWallpaperDataLocked != null
                && this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(peekWallpaperDataLocked);
      }
      if (z2) {
        Log.m129d(
            "WallpaperManagerService", "semSendWallpaperCommand: which=" + i + ", action=" + str);
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.wallpaper.intent.action.WALLPAPER_COMMAND");
        intent.setPackage("com.samsung.android.wallpaper.live");
        intent.putExtra("action", str);
        intent.putExtra("which", modeEnsuredWhich);
        if (bundle != null) {
          intent.putExtra("extras", bundle);
        }
        this.mContext.sendBroadcastAsUser(
            intent,
            new UserHandle(this.mCurrentUserId),
            "android.permission.READ_WALLPAPER_INTERNAL");
        return;
      }
    }
    WallpaperData wallpaperData = this.mLastWallpaper;
    if (WhichChecker.isSubDisplay(i)) {
      wallpaperData = this.mLastWatchFace;
    } else {
      if (WhichChecker.isVirtualDisplay(i)) {
        wallpaperData = this.mLastVirtualWallpaper;
        i2 = this.mActiveVirtualDisplayId;
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && z) {
          dispatchWallpaperCommand(this.mLastWallpaper, i2, str, bundle);
          dispatchWallpaperCommand(this.mLastWatchFace, i2, str, bundle);
          return;
        } else {
          dispatchWallpaperCommand(wallpaperData, i2, str, bundle);
        }
      }
      i2 = 0;
      if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {}
      dispatchWallpaperCommand(wallpaperData, i2, str, bundle);
    }
  }

  public final void dispatchWallpaperCommand(
      WallpaperData wallpaperData, int i, String str, Bundle bundle) {
    WallpaperConnection wallpaperConnection;
    if (wallpaperData == null
        || (wallpaperConnection = wallpaperData.connection) == null
        || wallpaperConnection.getDisplayConnectorOrCreate(i).mEngine == null) {
      return;
    }
    try {
      wallpaperData
          .connection
          .getDisplayConnectorOrCreate(i)
          .mEngine
          .dispatchWallpaperCommand(str, 0, 0, 0, bundle);
    } catch (RemoteException e) {
      Slog.e("WallpaperManagerService", e.toString());
    }
  }

  public boolean isWallpaperBackupAllowed(int i, int i2) {
    WallpaperData wallpaperData =
        (WhichChecker.isLock(i) ? this.mLockWallpaperMap : this.mWallpaperMap)
            .get(i2, WhichChecker.getMode(i));
    if (wallpaperData != null) {
      return wallpaperData.allowBackup;
    }
    return false;
  }

  public void removeSnapshotByWhich(int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    this.mSemService.removeSnapshotByWhich(i);
  }

  public void removeSnapshotByKey(int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    this.mSemService.removeSnapshotByKey(i);
  }

  public void removeSnapshotBySource(String str) {
    checkPermission("android.permission.SET_WALLPAPER");
    this.mSemService.removeSnapshotBySource(str);
  }

  public int makeSnapshot(int i, int i2, Bundle bundle) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.makeSnapshot(i, i2, bundle);
  }

  public boolean restoreSnapshot(int i, String str) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.restoreSnapshot(i, str);
  }

  public boolean isSnapshotTestMode() {
    if (SHIPPED) {
      return false;
    }
    return this.mSemService.isSnapshotTestMode();
  }

  public void setSnapshotTestMode(boolean z) {
    if (SHIPPED) {
      return;
    }
    this.mSemService.setSnapshotTestMode(z);
  }

  public int getSnapshotCount(int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.getSnapshotCount(i);
  }

  public boolean setSnapshotSource(int i, String str) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.setSnapshotSource(i, str);
  }

  public boolean isValidSnapshot(int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.isValidSnapshot(i);
  }

  public int[] getSnapshotKeys(String str, int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    return this.mSemService.getSnapshotKeys(str, i);
  }

  public void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) {
    checkPermission("android.permission.SET_WALLPAPER");
    Slog.d("WallpaperManagerService", "semSetDLSWallpaperColors: which = " + i);
    if (semWallpaperColors != null) {
      int mode = WhichChecker.getMode(i);
      int type = WhichChecker.getType(i);
      if (mode == 0) {
        mode = 4;
      }
      int mode2 = WhichChecker.getMode(semWallpaperColors.getWhich());
      int type2 = WhichChecker.getType(semWallpaperColors.getWhich());
      if (mode != (mode2 != 0 ? mode2 : 4) || type != type2) {
        IllegalArgumentException illegalArgumentException =
            new IllegalArgumentException(
                "Parameter which and the value from colors are not matched. which = "
                    + i
                    + ", colorWhich = "
                    + semWallpaperColors.getWhich());
        illegalArgumentException.printStackTrace();
        throw illegalArgumentException;
      }
    }
    if (i == 0) {
      Slog.e("WallpaperManagerService", "semSetDLSWallpaperColors: which is 0");
      return;
    }
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
      if (peekWallpaperDataLocked == null) {
        Slog.e("WallpaperManagerService", "semSetDLSWallpaperColors wallpaper == null");
        return;
      }
      SemWallpaperColors dlsSemColors = peekWallpaperDataLocked.mSemWallpaperData.getDlsSemColors();
      peekWallpaperDataLocked.mSemWallpaperData.setDlsSemColors(semWallpaperColors);
      if (dlsSemColors == null && semWallpaperColors == null) {
        return;
      }
      notifySemColorListeners(peekWallpaperDataLocked);
    }
  }

  public final void notifySemColorListeners(WallpaperData wallpaperData) {
    notifySemColorListeners(wallpaperData, 0);
  }

  public final void notifySemColorListeners(WallpaperData wallpaperData, int i) {
    ArrayList arrayList;
    Integer num;
    SemWallpaperColors semWallpaperColors = getSemWallpaperColors(wallpaperData, false);
    int which = wallpaperData.mSemWallpaperData.getWhich();
    int i2 = wallpaperData.userId;
    if (semWallpaperColors == null) {
      Log.addLogString("WallpaperManagerService", "notifySemColorListeners colors == null");
      return;
    }
    if ((!Rune.SUPPORT_SUB_DISPLAY_MODE && (which & 16) == 16)
        || (!Rune.VIRTUAL_DISPLAY_WALLPAPER && (which & 32) == 32)) {
      Log.m129d("WallpaperManagerService", "Unsupported wallpaper, ignore notifySemColorListeners");
      return;
    }
    if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER
        && this.mSemService.mDesktopMode.isDesktopSingleMode()
        && WhichChecker.isDex(which)) {
      Log.m129d(
          "WallpaperManagerService", "ignore colors changed of dex wallpaper if standalone mode");
      return;
    }
    int i3 = this.mCurrentUserId;
    if (i3 > 0 && i2 != i3) {
      Log.addLogString(
          "WallpaperManagerService",
          "notifySemColorListeners ignore, " + i2 + ", " + this.mCurrentUserId);
      return;
    }
    if (WhichChecker.isSystem(which)) {
      int highlightFilterState = getHighlightFilterState(wallpaperData);
      if (highlightFilterState == 1) {
        num = 0;
      } else {
        num = highlightFilterState == 2 ? 1 : null;
      }
      this.mSemService.mLegibilityColor.setWhiteBgSettings(semWallpaperColors, which, i2, num);
    }
    ArrayList arrayList2 = new ArrayList();
    synchronized (this.mLock) {
      RemoteCallbackList wallpaperCallbacks = getWallpaperCallbacks(i2, i);
      RemoteCallbackList wallpaperCallbacks2 = getWallpaperCallbacks(-1, i);
      arrayList = this.mKeyguardListenerList;
      if (wallpaperCallbacks != null) {
        int beginBroadcast = wallpaperCallbacks.beginBroadcast();
        for (int i4 = 0; i4 < beginBroadcast; i4++) {
          arrayList2.add(wallpaperCallbacks.getBroadcastItem(i4));
        }
        wallpaperCallbacks.finishBroadcast();
      }
      if (wallpaperCallbacks2 != null) {
        int beginBroadcast2 = wallpaperCallbacks2.beginBroadcast();
        for (int i5 = 0; i5 < beginBroadcast2; i5++) {
          arrayList2.add(wallpaperCallbacks2.getBroadcastItem(i5));
        }
        wallpaperCallbacks2.finishBroadcast();
      }
    }
    int size = arrayList2.size();
    for (int i6 = 0; i6 < size; i6++) {
      try {
        ((IWallpaperManagerCallback) arrayList2.get(i6))
            .onSemWallpaperColorsChanged(semWallpaperColors, which, i2);
      } catch (RemoteException unused) {
      }
    }
    synchronized (this.mLock) {
      if (arrayList != null && i == 0) {
        try {
          Iterator it = arrayList.iterator();
          while (it.hasNext()) {
            IWallpaperManagerCallback iWallpaperManagerCallback =
                (IWallpaperManagerCallback) it.next();
            if (iWallpaperManagerCallback != null) {
              iWallpaperManagerCallback.onSemWallpaperColorsChanged(semWallpaperColors, which, i2);
            }
          }
        } catch (RemoteException unused2) {
        }
      }
    }
    Log.addLogString(
        "WallpaperManagerService", "notifySemColorListeners: " + which + ", " + i2 + ", " + i);
  }

  public SemWallpaperColors semGetWallpaperColors(int i) {
    WallpaperData peekWallpaperDataLocked;
    int currentUserId = getCurrentUserId();
    int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
    synchronized (this.mLock) {
      peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, folderStateBasedWhich);
    }
    return getSemWallpaperColors(peekWallpaperDataLocked, false);
  }

  public SemWallpaperColors semGetPrimaryWallpaperColors(int i) {
    WallpaperData peekWallpaperDataLocked;
    int currentUserId = getCurrentUserId();
    int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
    synchronized (this.mLock) {
      peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, folderStateBasedWhich);
    }
    return getSemWallpaperColors(peekWallpaperDataLocked, true);
  }

  public final SemWallpaperColors getSemWallpaperColors(WallpaperData wallpaperData, boolean z) {
    SemWallpaperColors[] landscapeColors;
    if (wallpaperData == null) {
      Log.addLogString("WallpaperManagerService", "getSemWallpaperColors: wallpaper == null");
      return null;
    }
    SemWallpaperColors primarySemColors = wallpaperData.mSemWallpaperData.getPrimarySemColors();
    SemWallpaperColors dlsSemColors = wallpaperData.mSemWallpaperData.getDlsSemColors();
    if (this.mCurrentUserId == 0 && dlsSemColors != null) {
      Log.m129d("WallpaperManagerService", "getSemWallpaperColors: return dlsSemColors");
      return dlsSemColors;
    }
    if (z || this.mSemService.getOrientation() != 2) {
      return primarySemColors;
    }
    int which = wallpaperData.mSemWallpaperData.getWhich();
    if (!((WhichChecker.isSystem(which)
                && this.mSemService.mLegibilityColor.getAllowScreenRotateSystem())
            || (WhichChecker.isLock(which)
                && this.mSemService.mLegibilityColor.getAllowScreenRotateLock()))
        || (landscapeColors = wallpaperData.mSemWallpaperData.getLandscapeColors()) == null) {
      return primarySemColors;
    }
    SemWallpaperColors semWallpaperColors = landscapeColors[0];
    return (landscapeColors.length == 2 && this.mSemService.getDisplayInfo().rotation == 3)
        ? landscapeColors[1]
        : semWallpaperColors;
  }

  public void semClearWallpaperThumbnailCache(int i, int i2, String str) {
    if (Binder.getCallingUid() != 1000
        && !hasPermission("android.permission.READ_WALLPAPER_INTERNAL")) {
      throw new SecurityException(
          "semClearWallpaperThumbnailCache failed, Required permission : READ_WALLPAPER_INTERNAL or"
              + " Required UID : SYSTEM_UID");
    }
    Log.m129d(
        "WallpaperManagerService",
        "semClearWallpaperThumbnailCache : which = " + i + ", callingPackage = " + str);
    this.mSemService.deleteThumbnailFile(i, i2);
    Log.addLogString(
        "WallpaperManagerService",
        "semClearWallpaperThumbnailCache: " + i + ", " + i2 + ", " + str);
  }

  public void semRequestWallpaperColorsAnalysis(int i, String str) {
    int folderStateBasedWhich;
    if (Binder.getCallingUid() != 1000
        && !"com.android.systemui".equals(str)
        && !"com.samsung.android.app.dressroom".equals(str)
        && !"com.samsung.android.wallpaper.live".equals(str)) {
      throw new SecurityException("No permission to invoke semRequestWallpaperColorsAnalysis()");
    }
    int currentUserId = getCurrentUserId();
    if (this.mSemService.mDesktopMode.isDesktopSingleMode()) {
      folderStateBasedWhich = WhichChecker.getType(i) | 8;
    } else {
      folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
    }
    try {
      Iterator it = this.mKeyguardListenerList.iterator();
      while (it.hasNext()) {
        IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
        if (iWallpaperManagerCallback != null) {
          iWallpaperManagerCallback.onSemWallpaperColorsAnalysisRequested(
              folderStateBasedWhich, currentUserId);
        }
      }
    } catch (RemoteException e) {
      Log.m130e("WallpaperManagerService", "semRequestWallpaperColorsAnalysis : e=" + e);
    }
    SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
    semWallpaperManagerService.mLegibilityColor.extractColor(
        folderStateBasedWhich, semWallpaperManagerService.getOrientation() == 2);
  }

  public void semSetWallpaperColorOverrideAreas(int i, int i2, String str) {
    int callingUid = Binder.getCallingUid();
    if (!isSignedWithPlatformSignature(callingUid)) {
      throw new SecurityException(
          "The calling app does not have the required permission. uid = " + callingUid);
    }
    if (i != 2 && i != 1) {
      throw new IllegalArgumentException("which value should be one of FLAG_LOCK or FLAG_SYSTEM");
    }
    Log.m132i(
        "WallpaperManagerService",
        "semSetWallpaperColorOverrideAreas : which="
            + i
            + ", userId="
            + i2
            + ", len="
            + str.length());
    String str2 =
        i == 2 ? "custom_wallpaper_color_areas_lock" : "custom_wallpaper_color_areas_home";
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Settings.System.putStringForUser(this.mContext.getContentResolver(), str2, str, i2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void semSetSmartCropRect(int i, Rect rect, Rect rect2) {
    checkPermission("android.permission.SET_WALLPAPER");
    if (rect2.left < 0 || rect2.top < 0 || rect2.right < 0 || rect2.bottom < 0) {
      Log.addLogString("WallpaperManagerService", "invalid rect " + rect2);
      return;
    }
    Log.m129d("WallpaperManagerService", "semSetSmartCropRect, " + i + ", " + rect + ", " + rect2);
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
      if (peekWallpaperDataLocked == null) {
        Slog.e("WallpaperManagerService", "semSetSmartCropRect wallpaper == null");
        return;
      }
      if (WhichChecker.isSystem(i)
          && !this.mImageWallpaper.equals(peekWallpaperDataLocked.wallpaperComponent)) {
        Slog.e("WallpaperManagerService", "semSetSmartCropRect request is not for image wallpaper");
        return;
      }
      SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
      if (semWallpaperData.getLandscapeColors() != null) {
        Slog.e("WallpaperManagerService", "landscapeColors is calculated already");
        return;
      }
      semWallpaperData.setSmartCropOriginalRect(new Rect(rect));
      semWallpaperData.setSmartCropRect(new Rect(rect2));
      this.mSemService.mLegibilityColor.extractColor(semWallpaperData.getWhich(), true);
    }
  }

  public Rect semGetSmartCropRect(int i) {
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
      if (peekWallpaperDataLocked == null) {
        Slog.e("WallpaperManagerService", "semSetSmartCropRect wallpaper == null");
        return null;
      }
      Log.m129d(
          "WallpaperManagerService",
          "semgetSmartCropRect, "
              + i
              + ", "
              + peekWallpaperDataLocked.mSemWallpaperData.getSmartCropRect());
      return peekWallpaperDataLocked.mSemWallpaperData.getSmartCropRect();
    }
  }

  public String getDeviceColor() {
    return this.mSemService.mCMFWallpaper.getDeviceColor();
  }

  public String getLegacyDeviceColor() {
    return this.mSemService.mCMFWallpaper.getLegacyDeviceColor();
  }

  public String getLastCallingPackage(int i) {
    WallpaperData peekWallpaperDataLocked;
    checkPermission("android.permission.SET_WALLPAPER");
    int currentUserId = getCurrentUserId();
    synchronized (this.mLock) {
      peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, i);
    }
    if (peekWallpaperDataLocked == null) {
      Slog.e("WallpaperManagerService", "getLastCallingPackage wallpaper == null");
      return null;
    }
    return peekWallpaperDataLocked.semGetLastCallingPackage(false);
  }

  public void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) {
    WallpaperData wallpaperSafeLocked;
    WallpaperData wallpaperData;
    checkPermission("android.permission.SET_WALLPAPER");
    if (!isSignedWithPlatformSignature(Binder.getCallingUid())) {
      throw new SecurityException("Only the platform signed application can invoke semSetUri()");
    }
    boolean z2 = true;
    if (!this.mSemService.mKnox.isWallpaperChangeAllowed(true)) {
      Slog.i("WallpaperManagerService", "semSetUri: wallpaper change not allowed");
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Uri parse = Uri.parse(str);
      String scheme = parse.getScheme();
      String str3 = parse.getHost() + parse.getPath();
      StringBuilder sb = new StringBuilder();
      sb.append("semSetUri: path = ");
      sb.append(str);
      sb.append(", allowBackup =");
      sb.append(z);
      sb.append(", which = ");
      sb.append(i);
      sb.append(", type = ");
      sb.append(i2);
      sb.append(", callerPackage = ");
      sb.append(str2);
      sb.append(", userId = ");
      sb.append(i3);
      sb.append(", scheme = ");
      sb.append(scheme);
      sb.append(", path = ");
      sb.append(str3);
      sb.append(", hasExtras = ");
      if (bundle == null) {
        z2 = false;
      }
      sb.append(z2);
      Slog.i("WallpaperManagerService", sb.toString());
      int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
      synchronized (this.mLock) {
        if (WhichChecker.isSystemAndLock(modeEnsuredWhich)) {
          int mode = WhichChecker.getMode(modeEnsuredWhich);
          WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i3, mode | 1);
          wallpaperData = getWallpaperSafeLocked(i3, mode | 2);
          wallpaperSafeLocked = wallpaperSafeLocked2;
        } else {
          wallpaperSafeLocked = getWallpaperSafeLocked(i3, modeEnsuredWhich);
          wallpaperData = null;
        }
      }
      if (wallpaperSafeLocked == null) {
        Slog.e("WallpaperManagerService", "semSetUri wallpaper == null");
        return;
      }
      if (WhichChecker.isVirtualDisplay(modeEnsuredWhich)
          && isSameRequest(i2, str, wallpaperSafeLocked)) {
        Log.m129d("WallpaperManagerService", "semSetUri: Ignoring same request as previous one.");
        return;
      }
      wallpaperSafeLocked.mSemWallpaperData.setUri(str);
      if (WhichChecker.isSystemAndLock(modeEnsuredWhich) && wallpaperData != null) {
        wallpaperData.mSemWallpaperData.setUri(str);
      }
      if (i2 != -1) {
        setUriInternal(wallpaperSafeLocked, z, modeEnsuredWhich, i2, str2, i3, bundle);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean isSameRequest(int i, String str, WallpaperData wallpaperData) {
    WallpaperData lastWallpaper = getLastWallpaper(wallpaperData);
    if (lastWallpaper.mSemWallpaperData.getWpType() != i) {
      return false;
    }
    String uri = lastWallpaper.mSemWallpaperData.getUri();
    if (TextUtils.isEmpty(uri) || TextUtils.isEmpty(str)) {
      return false;
    }
    Log.m129d(
        "WallpaperManagerService",
        "isSameRequest: prevUriString = " + uri + ", uriString = " + str);
    int wpType = wallpaperData.mSemWallpaperData.getWpType();
    if (wpType != 3) {
      if (wpType != 5) {
        return false;
      }
      return uri.equals(str);
    }
    Uri parse = Uri.parse(uri);
    Uri parse2 = Uri.parse(str);
    return TextUtils.equals(parse.getScheme(), parse2.getScheme())
        && TextUtils.equals(parse.getHost(), parse2.getHost())
        && TextUtils.equals(parse.getPath(), parse2.getPath());
  }

  public final void setUriInternal(
      WallpaperData wallpaperData, boolean z, int i, int i2, String str, int i3, Bundle bundle) {
    if (WhichChecker.isSystem(i)) {
      resetSemWallpaperData(wallpaperData.mSemWallpaperData, i3);
    }
    int mode = WhichChecker.getMode(i);
    if (WhichChecker.isSystemAndLock(i)) {
      i = mode | 1;
    }
    wallpaperData.mSemWallpaperData.setWhich(i);
    wallpaperData.mSemWallpaperData.setWpType(i2);
    wallpaperData.mSemWallpaperData.setExternalParams(bundle);
    wallpaperData.setCallingPackage(str);
    wallpaperData.allowBackup = z;
    wallpaperData.mWhich = i;
    synchronized (this.mLock) {
      wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
      saveSettingsLocked(i3, mode);
    }
    wallpaperData.cleanUp();
    Log.m129d("WallpaperManagerService", "setUriInternal: which = " + i + ", type = " + i2);
    if (WhichChecker.isLock(i)) {
      if (i2 == 3) {
        synchronized (this.mLock) {
          notifyCallbacksLocked(wallpaperData);
        }
        notifyMultipackApplied(this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i));
        return;
      }
      if (i2 == 1000) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("trigger", "dls");
        notifyLockWallpaperChanged(i2, i, bundle2);
        return;
      }
      Log.m129d("WallpaperManagerService", "Not supported.");
      return;
    }
    if (i2 == 3 || i2 == 5) {
      if (((Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE)
              || Rune.VIRTUAL_DISPLAY_WALLPAPER)
          && !WhichChecker.isSupportLock(i)
          && WhichChecker.isSystem(i)) {
        synchronized (this.mLock) {
          bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperData, null);
        }
        if (WhichChecker.isWatchFaceDisplay(i)) {
          notifyCoverWallpaperChanged(i2, i);
        }
        wallpaperData.mSemWallpaperData.setPrimarySemColors(null);
        if (this.mSemService.generateCroppedBitmap(wallpaperData.mSemWallpaperData, str)) {
          this.mSemService.mLegibilityColor.extractColor(i);
          return;
        } else {
          Log.m130e("WallpaperManagerService", "setUriInternal: Fail setting cropped bitmap.");
          return;
        }
      }
      return;
    }
    Log.m129d("WallpaperManagerService", "Not supported.");
  }

  public String semGetUri(int i, String str) {
    WallpaperData peekWallpaperDataLocked;
    checkPermission("android.permission.SET_WALLPAPER");
    int currentUserId = getCurrentUserId();
    synchronized (this.mLock) {
      peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, i);
    }
    if (peekWallpaperDataLocked == null) {
      Slog.e("WallpaperManagerService", "semGetUri wallpaper == null");
      return null;
    }
    int wpType = peekWallpaperDataLocked.mSemWallpaperData.getWpType();
    if (wpType != -1) {
      if (wpType == 0 || wpType == 3 || wpType == 5 || wpType == 1000) {
        return peekWallpaperDataLocked.mSemWallpaperData.getUri();
      }
      return null;
    }
    if (isDefaultMultipack(currentUserId, i, str)) {
      return getDefaultMultiPackUri(i);
    }
    Log.m129d(
        "WallpaperManagerService",
        "semGetUri: which = " + i + ", type = -1. Default type is not multiple!");
    return null;
  }

  public void forceRebindWallpaper(int i, int i2) {
    checkPermission("android.permission.SET_WALLPAPER");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
        Slog.i(
            "WallpaperManagerService",
            "forceRebindWallpaper: which ="
                + i
                + ", component = "
                + wallpaperSafeLocked.wallpaperComponent);
        bindWallpaperComponentLocked(
            wallpaperSafeLocked.wallpaperComponent, true, true, wallpaperSafeLocked, null);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean isOpenThemeWallpaperApplied(WallpaperData wallpaperData) {
    if (getCurrentUserId() != 0) {
      return false;
    }
    if (TextUtils.isEmpty(
        Settings.System.getStringForUser(
            this.mContext.getContentResolver(), "current_sec_active_themepackage", 0))) {
      return WhichChecker.isSystem(wallpaperData.mSemWallpaperData.getWhich())
          && wallpaperData.mSemWallpaperData.isThemeContents();
    }
    return true;
  }

  public final String getDefaultMultiPackUri(int i) {
    boolean contains;
    String str;
    String defaultMultipackStyle = this.mSemWallpaperResourcesInfo.getDefaultMultipackStyle(i);
    if (TextUtils.isEmpty(defaultMultipackStyle) || !defaultMultipackStyle.contains("MULTIPLE")) {
      Log.m129d(
          "WallpaperManagerService",
          "startMultipleWallpaper: defaultWallpaperStyle is empty or not MULTIPLE!");
      return null;
    }
    int lastIndexOf = defaultMultipackStyle.lastIndexOf("=") + 1;
    int lastIndexOf2 =
        defaultMultipackStyle.contains(XmlUtils.STRING_ARRAY_SEPARATOR)
            ? defaultMultipackStyle.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR)
            : -1;
    if (lastIndexOf2 == -1) {
      str = defaultMultipackStyle.substring(lastIndexOf);
      contains = false;
    } else {
      String substring = defaultMultipackStyle.substring(lastIndexOf, lastIndexOf2);
      contains = defaultMultipackStyle.contains("tilt");
      str = substring;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("multipack");
    sb.append("://");
    sb.append(str);
    if (contains) {
      sb.append("?");
      sb.append("tilt");
      sb.append("=true");
    }
    return sb.toString();
  }

  public int getLidState() {
    return this.mSemService.mSubDisplayMode.getLidState();
  }

  public final int getConvertedUserId(int i, int i2) {
    if (!SemPersonaManager.isSecureFolderId(i)) {
      return i;
    }
    if (i2 == -10000) {
      return 0;
    }
    return i2;
  }

  public final void initImageWallpaperCropFile(WallpaperData wallpaperData, int i) {
    if (wallpaperData == null) {
      Log.m134w(
          "WallpaperManagerService", "initImageWallpaperCropFile: wallpaper data is not exist");
      return;
    }
    if (this.mImageWallpaper.equals(wallpaperData.nextWallpaperComponent)) {
      if (!wallpaperData.cropExists()) {
        if (wallpaperData.sourceExists()) {
          Slog.i("WallpaperManagerService", "No crop; regenerating from source");
          this.mWallpaperCropper.generateCrop(wallpaperData);
        }
        this.mSemService.generateResizedBitmap(
            wallpaperData.cropFile, wallpaperData.mSemWallpaperData);
        int wpType = wallpaperData.mSemWallpaperData.getWpType();
        if (wpType != 0 && wpType != -1) {
          Slog.i(
              "WallpaperManagerService",
              "Unnecessary to clear because other type is set on system wallpaper");
          return;
        }
      }
      if (wallpaperData.cropExists()) {
        return;
      }
      Slog.i("WallpaperManagerService", "Unable to regenerate crop; resetting");
      synchronized (this.mLock) {
        clearWallpaperLocked(false, i, 0, null);
      }
      return;
    }
    Slog.i("WallpaperManagerService", "Nondefault wallpaper component; gracefully ignoring");
  }

  /* JADX WARN: Removed duplicated region for block: B:25:0x00ce  */
  /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setDefaultWallpapers(int i) {
    WallpaperData wallpaperData;
    WallpaperData wallpaperData2;
    int i2;
    WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, 5);
    WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i, 6);
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      wallpaperData2 = getWallpaperSafeLocked(i, 17);
      wallpaperData = Rune.SUPPORT_COVER_DISPLAY_WATCHFACE ? null : getWallpaperSafeLocked(i, 18);
    } else {
      wallpaperData = null;
      wallpaperData2 = null;
    }
    boolean isDefaultOperatorWallpaper =
        WallpaperManager.isDefaultOperatorWallpaper(
            this.mContext, 5, this.mSemService.mCMFWallpaper.getDeviceColor());
    boolean isDefaultOperatorWallpaper2 =
        WallpaperManager.isDefaultOperatorWallpaper(
            this.mContext, 6, this.mSemService.mCMFWallpaper.getDeviceColor());
    Slog.d(
        "WallpaperManagerService",
        "setDefaultWallpapers: isDefaultOperatorMainSystem = "
            + isDefaultOperatorWallpaper
            + ", isDefaultOperatorMainLock = "
            + isDefaultOperatorWallpaper2);
    if (!isDefaultOperatorWallpaper && getDefaultWallpaperType(5) == 7) {
      wallpaperSafeLocked.mSemWallpaperData.setWpType(7);
      putDefaultLiveWallpaperProperties(wallpaperSafeLocked);
    }
    if (wallpaperSafeLocked2.mSemWallpaperData.getWpType() == -1) {
      int defaultWallpaperType = getDefaultWallpaperType(6);
      if (!isDefaultOperatorWallpaper2) {
        if (defaultWallpaperType == 8) {
          initVideoWallpaper(true);
        } else {
          String lastCallingPackage =
              wallpaperSafeLocked2.mSemWallpaperData.getLastCallingPackage();
          if (i > 0 && isDefaultMultipack(i, 6, lastCallingPackage)) {
            wallpaperSafeLocked2.mSemWallpaperData.setUri(getDefaultMultiPackUri(6));
            i2 = -1;
            setUriInternal(
                wallpaperSafeLocked2,
                false,
                6,
                3,
                lastCallingPackage,
                i,
                wallpaperSafeLocked2.mSemWallpaperData.getExternalParams());
          } else {
            i2 = -1;
            if (!isDefaultOperatorWallpaper && getDefaultWallpaperType(5) == 7) {
              setKWPTypeLiveWallpaperWithMode(4, 0);
            }
          }
          if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return;
          }
          if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
            if (isCoverVideoWallpaperDefault(17)
                && wallpaperData2 != null
                && wallpaperData2.mSemWallpaperData.getWpType() == i2) {
              initCoverVideoWallpaper();
              return;
            }
            return;
          }
          if (wallpaperData == null
              || wallpaperData.mSemWallpaperData.getWpType() != i2
              || WallpaperManager.isDefaultOperatorWallpaper(
                  this.mContext, 18, this.mSemService.mCMFWallpaper.getDeviceColor())) {
            return;
          }
          if (getDefaultWallpaperType(18) == 8) {
            initVideoWallpaper(18, true);
            return;
          }
          String lastCallingPackage2 = wallpaperData.mSemWallpaperData.getLastCallingPackage();
          if (i <= 0 || !isDefaultMultipack(i, 18, lastCallingPackage2)) {
            return;
          }
          wallpaperData.mSemWallpaperData.setUri(getDefaultMultiPackUri(18));
          setUriInternal(
              wallpaperData,
              false,
              18,
              3,
              lastCallingPackage2,
              i,
              wallpaperData.mSemWallpaperData.getExternalParams());
          return;
        }
      }
    }
    i2 = -1;
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {}
  }

  public final void initLockWallpaperData(int i, int i2) {
    FileObserver fileObserver;
    WallpaperObserver wallpaperObserver;
    SemWallpaperManagerService.SemWallpaperObserver semWallpaperObserver;
    WallpaperData wallpaperData = this.mLockWallpaperMap.get(i, i2);
    if (wallpaperData != null) {
      fileObserver = wallpaperData.wallpaperObserver;
      this.mLockWallpaperMap.remove(i, i2);
    } else {
      fileObserver = null;
    }
    WallpaperData wallpaperData2 =
        new WallpaperData(
            i,
            WallpaperUtils.getWallpaperLockDir(i),
            SemWallpaperManagerService.getFileName(i2, 1, 0),
            SemWallpaperManagerService.getFileName(i2, 1, 1));
    wallpaperData2.wallpaperObserver = fileObserver;
    if (fileObserver == null
        && (wallpaperObserver = this.mWallpaperObserver) != null
        && (semWallpaperObserver = wallpaperObserver.mSemObserver) != null
        && i == wallpaperObserver.mUserId) {
      FileObserver lockWallpaperFileObserver = semWallpaperObserver.getLockWallpaperFileObserver();
      wallpaperData2.wallpaperObserver = lockWallpaperFileObserver;
      if (lockWallpaperFileObserver != null) {
        lockWallpaperFileObserver.startWatching();
      }
    }
    wallpaperData2.mSemWallpaperData.setWpType(-1);
    this.mLockWallpaperMap.put(i, i2, wallpaperData2);
  }

  public final boolean needUpdateWallpaperData(WallpaperData wallpaperData) {
    int maximumSizeDimension = this.mWallpaperDisplayHelper.getMaximumSizeDimension(0);
    return wallpaperData.mSemWallpaperData.getWidth() < maximumSizeDimension
        || wallpaperData.mSemWallpaperData.getHeight() < maximumSizeDimension;
  }

  public final void resetWallpaperData(WallpaperData wallpaperData) {
    wallpaperData.name = "";
  }

  public final void resetSemWallpaperData(SemWallpaperData semWallpaperData, int i) {
    if (semWallpaperData == null) {
      Log.addLogString("WallpaperManagerService", "resetSemWallpaperData wallpaper == null");
      return;
    }
    if (semWallpaperData.getCroppedBitmap() != null
        && !semWallpaperData.getCroppedBitmap().isRecycled()) {
      Log.m129d(
          "WallpaperManagerService",
          "Wallpaper file is changed or deleted. Delete previous cropped bitmap");
      semWallpaperData.setCroppedBitmap(null);
    }
    this.mSemService.deleteThumbnailFile(semWallpaperData.getWhich(), i);
    semWallpaperData.setLandscapeColors(null);
  }

  public final boolean isVisibleWhichWhenKeyguardLocked(int i) {
    if (!this.mIsLockscreenLiveWallpaperEnabled) {
      return true;
    }
    boolean isPhone = WhichChecker.isPhone(i);
    boolean isSubDisplay = WhichChecker.isSubDisplay(i);
    if ((isPhone || isSubDisplay) && !this.mSemService.hasLockscreenWallpaper(isSubDisplay)) {
      Log.m129d(
          "WallpaperManagerService",
          "isVisibleWhichWhenKeyguardLocked: No lockscreen wallpaper. SHOULD be visible on keyguard"
              + " locked status.");
      return true;
    }
    if (WhichChecker.isSupportLock(i)) {
      return WhichChecker.containsLock(i);
    }
    return true;
  }

  public final int getCurrentUserId() {
    int i = this.mCurrentUserId;
    if (i < 0) {
      return 0;
    }
    return i;
  }

  public boolean isWallpaperDataExists(int i, int i2) {
    boolean z;
    synchronized (this.mLock) {
      z =
          (WhichChecker.isSystem(i2) ? this.mWallpaperMap : this.mLockWallpaperMap)
                  .get(i, WhichChecker.getMode(i2))
              != null;
    }
    return z;
  }

  public class WallpaperDataManager {
    public final Object mLock;
    public final SemWallpaperManagerService mSemService;
    public final int mType;
    public final SparseArray mWallpaperMap = new SparseArray();

    public WallpaperDataManager(
        Object obj, int i, SemWallpaperManagerService semWallpaperManagerService) {
      this.mLock = obj;
      this.mType = i;
      this.mSemService = semWallpaperManagerService;
    }

    public WallpaperData get(int i) {
      return get(i, 0);
    }

    public WallpaperData get(int i, int i2) {
      WallpaperData wallpaperData;
      int mode = getMode(i2);
      synchronized (this.mLock) {
        SparseArray sparseArray = this.mWallpaperMap;
        if (mode == 8 && i > 0) {
          Log.addLogString("WallpaperManagerService", "get, dex mode support only user = 0");
          i = 0;
        }
        wallpaperData = (WallpaperData) sparseArray.get(i + mode);
      }
      return wallpaperData;
    }

    public ArrayList getActiveWallpapers(int i) {
      ArrayList arrayList = new ArrayList();
      synchronized (this.mLock) {
        for (int i2 = 0; i2 < this.mWallpaperMap.size(); i2++) {
          WallpaperData wallpaperData = (WallpaperData) this.mWallpaperMap.valueAt(i2);
          WallpaperConnection wallpaperConnection = wallpaperData.connection;
          if (wallpaperConnection != null
              && wallpaperConnection.mDisplayConnector != null
              && wallpaperData.connection.mDisplayConnector.contains(i)) {
            arrayList.add(wallpaperData);
          }
        }
      }
      return arrayList;
    }

    public void put(int i, int i2, WallpaperData wallpaperData) {
      if (i < 0 || ((i > 150 && i < 160) || (i > 95 && i < 99))) {
        Log.addLogString("WallpaperManagerService", "put, invalid userId = " + i);
        return;
      }
      if (wallpaperData != null) {
        Log.addLogString(
            "WallpaperManagerService",
            "put, userId:"
                + i
                + ", mode:"
                + i2
                + ", data:"
                + wallpaperData
                + ", hash:"
                + wallpaperData.hashCode());
        int mode = getMode(i2);
        synchronized (this.mLock) {
          SparseArray sparseArray = this.mWallpaperMap;
          if (WhichChecker.isDex(mode)) {
            if (i > 0) {
              Log.addLogString("WallpaperManagerService", "put, dex mode support only user = 0");
              return;
            }
            wallpaperData.mSemWallpaperData.setIsDesktopWallpaper(true);
          }
          wallpaperData.mSemWallpaperData.setWhich(this.mType | mode);
          sparseArray.put(i + mode, wallpaperData);
          int which = wallpaperData.mSemWallpaperData.getWhich();
          File file = wallpaperData.wallpaperFile;
          if (WhichChecker.isSystem(which)
              && WhichChecker.isSubDisplay(which)
              && file != null
              && "wallpaper_orig".equals(file.getName())) {
            SemWallpaperManagerService.putLog(
                "\nUnexpected file assignment detected!\n"
                    + SemWallpaperManagerService.getCallStackString());
          }
        }
      }
    }

    public void remove(int i) {
      remove(i, 0);
    }

    public void remove(int i, int i2) {
      int mode = getMode(i2);
      synchronized (this.mLock) {
        this.mWallpaperMap.remove(i + mode);
      }
    }

    public WallpaperData valueAt(int i) {
      WallpaperData wallpaperData;
      synchronized (this.mLock) {
        wallpaperData = (WallpaperData) this.mWallpaperMap.valueAt(i);
      }
      return wallpaperData;
    }

    public int size() {
      int size;
      synchronized (this.mLock) {
        size = this.mWallpaperMap.size();
      }
      return size;
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println("WallpaperDataManager");
      int i = this.mType;
      if (i == 1) {
        printWriter.println("Home Wallpaper");
      } else if (i == 2) {
        printWriter.println("Lock Wallpaper");
      }
      synchronized (this.mLock) {
        for (int i2 = 0; i2 < this.mWallpaperMap.size(); i2++) {
          WallpaperData wallpaperData = (WallpaperData) this.mWallpaperMap.valueAt(i2);
          print(printWriter, wallpaperData, this.mWallpaperMap.keyAt(i2) - wallpaperData.userId);
        }
      }
      printWriter.println("");
    }

    public final int getMode(int i) {
      return WhichChecker.getMode(i) == 0 ? this.mSemService.getCurrentImplicitMode() : i;
    }

    public final void print(final PrintWriter printWriter, WallpaperData wallpaperData, int i) {
      printWriter.print(" User ");
      printWriter.print(wallpaperData.userId);
      printWriter.print(": id=");
      printWriter.print(wallpaperData.wallpaperId);
      printWriter.print(", hash=@");
      printWriter.println(wallpaperData.hashCode());
      printWriter.print("  mWhich=");
      printWriter.println(wallpaperData.mWhich);
      printWriter.print("  mCropHint=");
      printWriter.println(wallpaperData.cropHint);
      printWriter.print("  mName=");
      printWriter.println(wallpaperData.name);
      printWriter.print("  mMode=");
      printWriter.println(i);
      printWriter.print("  mAllowBackup=");
      printWriter.println(wallpaperData.allowBackup);
      printWriter.print("  mWallpaperFile=");
      File file = wallpaperData.wallpaperFile;
      printWriter.println(file == null ? "null" : file.getAbsolutePath());
      printWriter.print("  mCropFile=");
      File file2 = wallpaperData.cropFile;
      printWriter.println(file2 != null ? file2.getAbsolutePath() : "null");
      printWriter.print("  mWallpaperComponent=");
      printWriter.println(wallpaperData.wallpaperComponent);
      WallpaperConnection wallpaperConnection = wallpaperData.connection;
      if (wallpaperConnection != null) {
        printWriter.print("  Wallpaper connection ");
        printWriter.print(wallpaperConnection);
        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (wallpaperConnection.mInfo != null) {
          printWriter.print("    mInfo.component=");
          printWriter.println(wallpaperConnection.mInfo.getComponent());
        }
        wallpaperConnection.forEachDisplayConnector(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$WallpaperDataManager$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.WallpaperDataManager.lambda$print$0(
                    printWriter, (WallpaperManagerService.DisplayConnector) obj);
              }
            });
        printWriter.print("    mService=");
        printWriter.println(wallpaperConnection.mService);
        printWriter.print("    mLastDiedTime=");
        printWriter.println(wallpaperData.lastDiedTime - SystemClock.uptimeMillis());
      }
      printWriter.print("  mSemWallpaperData=");
      printWriter.println(wallpaperData.mSemWallpaperData);
    }

    public static /* synthetic */ void lambda$print$0(
        PrintWriter printWriter, DisplayConnector displayConnector) {
      printWriter.print("     mDisplayId=");
      printWriter.println(displayConnector.mDisplayId);
      printWriter.print("     mToken=");
      printWriter.println(displayConnector.mToken);
      printWriter.print("     mEngine=");
      printWriter.println(displayConnector.mEngine);
    }
  }

  public class WallpaperManagerCallbackClient implements IBinder.DeathRecipient {
    public IWallpaperManagerCallback mCb;

    public WallpaperManagerCallbackClient(IWallpaperManagerCallback iWallpaperManagerCallback) {
      Slog.d(
          "WallpaperManagerService", "WallpaperManagerCallbackClient " + iWallpaperManagerCallback);
      if (iWallpaperManagerCallback != null) {
        try {
          iWallpaperManagerCallback.asBinder().linkToDeath(this, 0);
        } catch (Exception e) {
          Log.m130e("WallpaperManagerService", "exception " + e);
          iWallpaperManagerCallback = null;
        }
      }
      this.mCb = iWallpaperManagerCallback;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      synchronized (WallpaperManagerService.this.mLock) {
        Slog.d("WallpaperManagerService", "binderDied " + this.mCb);
        WallpaperManagerService.this.mKeyguardListenerList.remove(this.mCb);
        WallpaperManagerService.this.mKeyguardListenerClientList.remove(this);
      }
      this.mCb = null;
    }
  }

  public class SemCallback {
    public SemCallback() {}

    public void rebindDefaultWallpaperIfNeeded() {
      if (WallpaperManagerService.this.mSemWallpaperResourcesInfo.isSupportCMF()) {
        String deviceColor =
            WallpaperManagerService.this.mSemService.mCMFWallpaper.getDeviceColor();
        Slog.d("WallpaperManagerService", "Change system wallpaper by color: " + deviceColor);
        WallpaperData wallpaperData =
            WallpaperManagerService.this.mWallpaperMap.get(UserHandle.getCallingUserId());
        if (wallpaperData != null) {
          synchronized (WallpaperManagerService.this.mLock) {
            WallpaperManagerService.this.bindWallpaperComponentLocked(
                null, true, false, wallpaperData, null);
          }
          wallpaperData.mSemWallpaperData.setCroppedBitmap(null);
          WallpaperManagerService.this.mSemService.mLegibilityColor.extractColor(
              wallpaperData.mSemWallpaperData.getWhich());
        }
        if (WallpaperManagerService.this.mSemWallpaperResourcesInfo.getDefaultWallpaperType(
                2, WallpaperManagerService.this.mSemService.mCMFWallpaper.getDeviceColor())
            == 8) {
          Slog.d(
              "WallpaperManagerService", "Change lock wallpaper (VIDEO) by color : " + deviceColor);
          WallpaperManagerService.this.initVideoWallpaper(true);
          return;
        }
        Slog.d("WallpaperManagerService", "Change lock wallpaper by color : " + deviceColor);
        WallpaperData wallpaperData2 =
            WallpaperManagerService.this.mLockWallpaperMap.get(UserHandle.getCallingUserId());
        if (wallpaperData2 != null) {
          WallpaperManagerService.this.notifyLockWallpaperChanged(
              wallpaperData2.mSemWallpaperData.getWpType(),
              wallpaperData2.mSemWallpaperData.getWhich());
          wallpaperData2.mSemWallpaperData.setCroppedBitmap(null);
          WallpaperManagerService.this.mSemService.mLegibilityColor.extractColor(
              wallpaperData2.mSemWallpaperData.getWhich());
        }
      }
    }

    public void updateOmcWallpaper() {
      handleOMCWallpaperUpdated();
    }

    public void updateEvent(int i, String str, File file, boolean z, boolean z2) {
      WallpaperManagerService.this.mWallpaperObserver.updateEvent(i, str, file, z, z2);
    }

    public void updateDesktopModeState(boolean z) {
      boolean isDesktopDualMode =
          WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopDualMode();
      synchronized (WallpaperManagerService.this.mLock) {
        if (!z) {
          Log.addLogString("WallpaperManagerService", "DesktopMode disabled");
          WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
          WallpaperManagerService.this.detachWallpaperLocked(
              wallpaperManagerService.getWallpaperSafeLocked(
                  wallpaperManagerService.mCurrentUserId, 9));
        }
      }
      WallpaperManagerService.this.mSemService.mDesktopMode.onRefreshWallpaperByUiMode(z);
      if ((isDesktopDualMode && !z)
          || (WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopDualMode() && z)) {
        Log.m132i(
            "WallpaperManagerService",
            "Wallpaper ignore wallpaper refresh in default display when desktop dual mode is"
                + " enabled/disabled");
      } else {
        WallpaperManagerService.this.mSemService.handleWallpaperBindingTimeout(true, true);
      }
    }

    public void switchDexWallpaper(int i, boolean z) {
      int i2 = z ? 9 : 5;
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperData wallpaperData = WallpaperManagerService.this.mWallpaperMap.get(i, i2);
        if (wallpaperData == null) {
          Log.m129d("WallpaperManagerService", "no current wallpaper -- first switching DeX?");
          wallpaperData = WallpaperManagerService.this.getWallpaperSafeLocked(i, i2);
          if (wallpaperData == null) {
            Log.m130e("WallpaperManagerService", "no current wallpaper");
            return;
          }
        }
        if (WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopWallpaperFileExist(i)
            || !z) {
          WallpaperManagerService.this.switchWallpaper(wallpaperData, null);
          notifySemWallpaperColors(i2);
        }
        WallpaperManagerService.this.notifyLockWallpaperChanged(
            wallpaperData.mSemWallpaperData.getWpType(), i2);
      }
    }

    public void handleWallpaperBindingTimeout() {
      WallpaperData wallpaperSafeLocked;
      WallpaperData wallpaperData;
      synchronized (WallpaperManagerService.this.mLock) {
        if (WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopMode()
            && WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopDualMode()) {
          WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
          wallpaperSafeLocked =
              wallpaperManagerService.getWallpaperSafeLocked(
                  wallpaperManagerService.mCurrentUserId, 9);
        } else {
          WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
          wallpaperSafeLocked =
              wallpaperManagerService2.getWallpaperSafeLocked(
                  wallpaperManagerService2.mCurrentUserId, 1);
        }
        wallpaperData = wallpaperSafeLocked;
      }
      if (wallpaperData != null) {
        WallpaperConnection wallpaperConnection = wallpaperData.connection;
        if (wallpaperConnection == null
            || wallpaperConnection.mService == null
            || wallpaperConnection.getDisplayConnectorOrCreate(0).mEngine == null
            || !wallpaperData.connection.isBinderAlive()) {
          Log.m129d("WallpaperManagerService", "trying to bind wallpaperComponent with timeout");
          synchronized (WallpaperManagerService.this.mLock) {
            WallpaperManagerService.this.bindWallpaperComponentLocked(
                wallpaperData.wallpaperComponent, true, false, wallpaperData, null);
          }
          WallpaperManagerService.this.mSemService.mDesktopMode.setWallpaperBindingFallbackExecuted(
              true);
          WallpaperManagerService.this.mSemService.handleWallpaperBindingTimeout(true, true);
          return;
        }
        Log.m129d("WallpaperManagerService", "wallpaper binded already!");
        return;
      }
      Log.m130e("WallpaperManagerService", "WallpaperData is not exist!");
    }

    public void switchWallpaperByDisplayChanged(int i, int i2) {
      boolean z = true;
      int folderStateBasedWhich =
          WallpaperManagerService.this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(1, i);
      int folderStateBasedWhich2 =
          WallpaperManagerService.this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(1, i2);
      WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
      Log.m129d(
          "WallpaperManagerService",
          "switchWallpaperByDisplayChanged   prevFolderState : "
              + i
              + " curFolderState : "
              + i2
              + " which : "
              + folderStateBasedWhich2
              + " oldwhich : "
              + folderStateBasedWhich
              + " mCurrentUserId : "
              + WallpaperManagerService.this.mCurrentUserId
              + " mOldUserId : "
              + WallpaperManagerService.this.mSemService.getOldUserId()
              + " userId : "
              + wallpaperManagerService.getConvertedUserId(
                  wallpaperManagerService.mCurrentUserId,
                  WallpaperManagerService.this.mSemService.getOldUserId()));
      int modeEnsuredWhich = WallpaperManagerService.this.mSemService.getModeEnsuredWhich(1);
      int modeEnsuredWhich2 = WallpaperManagerService.this.mSemService.getModeEnsuredWhich(2);
      WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
      WallpaperData wallpaperSafeLocked =
          wallpaperManagerService2.getWallpaperSafeLocked(
              wallpaperManagerService2.mCurrentUserId, modeEnsuredWhich);
      if (wallpaperSafeLocked.mSemWallpaperData.getPrimarySemColors() == null) {
        WallpaperManagerService.this.mSemService.mLegibilityColor.extractColor(
            wallpaperSafeLocked.mSemWallpaperData.getWhich());
      } else {
        WallpaperManagerService.this.notifySemColorListeners(wallpaperSafeLocked);
      }
      WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
      WallpaperData wallpaperSafeLocked2 =
          wallpaperManagerService3.getWallpaperSafeLocked(
              wallpaperManagerService3.mCurrentUserId, modeEnsuredWhich2);
      if (wallpaperSafeLocked2.mSemWallpaperData.getPrimarySemColors() == null) {
        WallpaperManagerService.this.mSemService.mLegibilityColor.extractColor(
            wallpaperSafeLocked2.mSemWallpaperData.getWhich());
      } else {
        WallpaperManagerService.this.notifySemColorListeners(wallpaperSafeLocked2);
      }
      Bundle bundle = new Bundle();
      bundle.putBoolean("isFolded", i2 == 0);
      WallpaperManagerService wallpaperManagerService4 = WallpaperManagerService.this;
      if ((wallpaperManagerService4.mLastWallpaper == null
              || wallpaperManagerService4.mImageWallpaper.equals(
                  WallpaperManagerService.this.mLastWallpaper.wallpaperComponent))
          && (WallpaperManagerService.this.mLastWatchFace == null
              || WallpaperManagerService.this.mImageWallpaper.equals(
                  WallpaperManagerService.this.mLastWatchFace.wallpaperComponent))) {
        z = false;
      }
      WallpaperManagerService.this.semSendWallpaperCommand(
          modeEnsuredWhich, "switch_display", bundle, z);
      if (z) {
        return;
      }
      WallpaperManagerService.this.semSendWallpaperCommand(
          folderStateBasedWhich, "reset_offset", bundle, false);
    }

    public void notifySemWallpaperColors() {
      synchronized (WallpaperManagerService.this.mLock) {
        Log.m129d("WallpaperManagerService", "orientation is changed, notifySemColorListeners");
        if (WallpaperManagerService.this.mSemService.mLegibilityColor
            .getAllowScreenRotateSystem()) {
          int folderStateBasedWhich =
              WallpaperManagerService.this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(1);
          WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
          WallpaperData peekWallpaperDataLocked =
              wallpaperManagerService.peekWallpaperDataLocked(
                  wallpaperManagerService.getCurrentUserId(), folderStateBasedWhich);
          if (peekWallpaperDataLocked != null) {
            WallpaperManagerService.this.notifySemColorListeners(peekWallpaperDataLocked);
          }
        }
        if (WallpaperManagerService.this.mSemService.mLegibilityColor.getAllowScreenRotateLock()) {
          int folderStateBasedWhich2 =
              WallpaperManagerService.this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2);
          WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
          WallpaperData peekWallpaperDataLocked2 =
              wallpaperManagerService2.peekWallpaperDataLocked(
                  wallpaperManagerService2.getCurrentUserId(), folderStateBasedWhich2);
          if (peekWallpaperDataLocked2 != null) {
            WallpaperManagerService.this.notifySemColorListeners(peekWallpaperDataLocked2);
          }
        }
      }
    }

    public void handleOMCWallpaperUpdated() {
      WallpaperManagerService.this.handleOMCWallpaperUpdatedLocked(4);
      if (!Rune.SUPPORT_SUB_DISPLAY_MODE || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
        return;
      }
      WallpaperManagerService.this.handleOMCWallpaperUpdatedLocked(16);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:61|62|92|67|(4:69|(1:74)|75|(6:79|80|81|(1:86)|88|(2:90|91)(2:92|93)))|97|80|81|(2:84|86)|88|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00f7, code lost:

       r2 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00f8, code lost:

       r2 = r1;
       r1 = r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0101, code lost:

       com.samsung.server.wallpaper.Log.m130e("WallpaperManagerService", "calcSemWallpaperColors - live case Exception: " + r1);
       r1 = r2;
    */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x013b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SemWallpaperColors calcSemWallpaperColors(int i, int i2) {
      WallpaperData peekWallpaperDataLocked;
      Bitmap bitmap;
      WallpaperData peekWallpaperDataLocked2;
      WallpaperInfo wallpaperInfoWithFlags;
      BitmapDrawable bitmapDrawable;
      Bitmap bitmap2;
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
        peekWallpaperDataLocked =
            wallpaperManagerService.peekWallpaperDataLocked(
                wallpaperManagerService.getCurrentUserId(), i);
      }
      if (peekWallpaperDataLocked == null) {
        Log.addLogString("WallpaperManagerService", "calcSemWallpaperColors, wallpaper == null");
        return null;
      }
      Log.m129d("WallpaperManagerService", "calcSemWallpaperColors " + i + ", " + i2);
      if (i != peekWallpaperDataLocked.mSemWallpaperData.getWhich()) {
        Log.addLogString(
            "WallpaperManagerService",
            "calcSemWallpaperColors:"
                + i
                + ", "
                + peekWallpaperDataLocked.mSemWallpaperData.getWhich());
        i = peekWallpaperDataLocked.mSemWallpaperData.getWhich();
      }
      int liveWallpaperThumbnailTarget = getLiveWallpaperThumbnailTarget(i);
      Log.m132i(
          "WallpaperManagerService",
          "calcSemWallpaperColors targetWhich = " + liveWallpaperThumbnailTarget);
      if (liveWallpaperThumbnailTarget != -1) {
        try {
          synchronized (WallpaperManagerService.this.mLock) {
            WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
            peekWallpaperDataLocked2 =
                wallpaperManagerService2.peekWallpaperDataLocked(
                    wallpaperManagerService2.getCurrentUserId(), liveWallpaperThumbnailTarget);
          }
        } catch (Exception e) {
          e = e;
          Bitmap bitmap3 = null;
        }
        if (WallpaperManagerService.this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(
            peekWallpaperDataLocked2)) {
          if (i2 != 90 && i2 != 270) {
            r4 = 1;
          }
          File thumbnailFile =
              WallpaperManagerService.this.mLiveWallpaperHelper.getThumbnailFile(
                  liveWallpaperThumbnailTarget, peekWallpaperDataLocked2.userId, r4);
          if (thumbnailFile != null && thumbnailFile.exists()) {
            bitmap = BitmapFactory.decodeFile(thumbnailFile.getAbsolutePath());
            WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
            wallpaperInfoWithFlags =
                wallpaperManagerService3.getWallpaperInfoWithFlags(
                    liveWallpaperThumbnailTarget, wallpaperManagerService3.mCurrentUserId);
            if (wallpaperInfoWithFlags != null
                && bitmap == null
                && (bitmapDrawable =
                        (BitmapDrawable)
                            wallpaperInfoWithFlags.loadThumbnail(
                                WallpaperManagerService.this.mContext.getPackageManager()))
                    != null) {
              bitmap = bitmapDrawable.getBitmap();
            }
            Log.m129d(
                "WallpaperManagerService",
                "calcSemWallpaperColors - live case drawable: " + bitmap);
            if (bitmap != null) {
              return SemWallpaperColors.fromBitmap(
                  WallpaperManagerService.this.mContext, bitmap, i, i2, (Rect[]) null);
            }
            SemWallpaperColors.Builder builder = new SemWallpaperColors.Builder();
            builder.setWhich(WhichChecker.getType(i));
            builder.setColorType(0);
            return builder.build();
          }
        }
        bitmap = null;
        WallpaperManagerService wallpaperManagerService32 = WallpaperManagerService.this;
        wallpaperInfoWithFlags =
            wallpaperManagerService32.getWallpaperInfoWithFlags(
                liveWallpaperThumbnailTarget, wallpaperManagerService32.mCurrentUserId);
        if (wallpaperInfoWithFlags != null) {
          bitmap = bitmapDrawable.getBitmap();
        }
        Log.m129d(
            "WallpaperManagerService", "calcSemWallpaperColors - live case drawable: " + bitmap);
        if (bitmap != null) {}
      } else {
        SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
        synchronized (WallpaperManagerService.this.mLock) {
          if (semWallpaperData.getCroppedBitmap() == null
              || semWallpaperData.getCroppedBitmap().isRecycled()) {
            Log.m129d("WallpaperManagerService", "wallpaper.croppedBitmap == null");
            WallpaperManagerService.this.mSemService.generateResizedBitmap(
                peekWallpaperDataLocked.cropFile, semWallpaperData);
          }
          if (semWallpaperData.getCroppedBitmap() != null
              && !semWallpaperData.getCroppedBitmap().isRecycled()) {
            bitmap2 =
                semWallpaperData
                    .getCroppedBitmap()
                    .copy(semWallpaperData.getCroppedBitmap().getConfig(), true);
          } else {
            Log.addLogString(
                "WallpaperManagerService", "wallpaper.croppedBitmap == null or recycled");
            bitmap2 = null;
          }
        }
        if (bitmap2 != null) {
          Log.m129d("WallpaperManagerService", "croppedBitmap=" + bitmap2);
          if (i2 != 0 && !semWallpaperData.getIsPreloaded()) {
            bitmap2 =
                WallpaperManagerService.this.mSemService.mLegibilityColor.smartCropBitmap(
                    bitmap2, semWallpaperData, i2);
          }
          if (Rune.isTablet() && semWallpaperData.getIsPreloaded()) {
            if (WallpaperManagerService.this.mSemService.getOrientation() != (i2 == 0 ? 1 : 2)) {
              bitmap2 =
                  WallpaperManagerService.this.mSemService.getPreloadRotatedCroppedBitmap(
                      peekWallpaperDataLocked.cropFile, semWallpaperData);
            }
          }
          if (bitmap2 == null) {
            return null;
          }
          try {
            return SemWallpaperColors.fromBitmap(
                WallpaperManagerService.this.mContext, bitmap2, i, i2, (Rect[]) null);
          } catch (Exception e2) {
            Log.addLogString(
                "WallpaperManagerService",
                "exception "
                    + e2
                    + ", which = "
                    + i
                    + ", smart crop info = "
                    + semWallpaperData.getSmartCropOriginalRect()
                    + ", "
                    + semWallpaperData.getSmartCropRect());
            return null;
          }
        }
        Log.m129d("WallpaperManagerService", "croppedBitmap == null");
        return null;
      }
    }

    public final int getLiveWallpaperThumbnailTarget(int i) {
      int modeEnsuredWhich = WallpaperManagerService.this.mSemService.getModeEnsuredWhich(i);
      int mode = WhichChecker.getMode(modeEnsuredWhich);
      int i2 = mode | 1;
      int semGetWallpaperType = WallpaperManagerService.this.semGetWallpaperType(i2);
      int i3 = mode | 2;
      int semGetWallpaperType2 = WallpaperManagerService.this.semGetWallpaperType(i3);
      boolean z = false;
      boolean z2 = semGetWallpaperType == 7;
      boolean z3 = semGetWallpaperType2 == 7;
      if (WhichChecker.isSystem(modeEnsuredWhich)) {
        if (z2) {
          return i2;
        }
        return -1;
      }
      boolean isSubDisplay = WhichChecker.isSubDisplay(modeEnsuredWhich);
      if ((WhichChecker.isPhone(modeEnsuredWhich) || isSubDisplay)
          && !WallpaperManagerService.this.mSemService.hasLockscreenWallpaper(isSubDisplay)) {
        z = true;
      }
      if (z) {
        if (z2) {
          return i2;
        }
      } else if (z3) {
        return i3;
      }
      return -1;
    }

    public void saveSemWallpaperColors(
        int i, boolean z, SemWallpaperColors[] semWallpaperColorsArr) {
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
        WallpaperData peekWallpaperDataLocked =
            wallpaperManagerService.peekWallpaperDataLocked(
                wallpaperManagerService.getCurrentUserId(), i);
        if (peekWallpaperDataLocked == null) {
          Log.addLogString("WallpaperManagerService", "saveSemWallpaperColors, wallpaper == null");
          return;
        }
        SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
        if (z) {
          semWallpaperData.setLandscapeColors(semWallpaperColorsArr);
        } else {
          semWallpaperData.setPrimarySemColors(semWallpaperColorsArr[0]);
          LegibilityColor legibilityColor =
              WallpaperManagerService.this.mSemService.mLegibilityColor;
          String wallpaperColorPath =
              LegibilityColor.getWallpaperColorPath(
                  peekWallpaperDataLocked.userId,
                  peekWallpaperDataLocked.mSemWallpaperData.getWhich(),
                  z);
          Log.m129d(
              "WallpaperManagerService", "saveSemWallpaperColors " + i + ", " + wallpaperColorPath);
          semWallpaperColorsArr[0].save(wallpaperColorPath);
        }
      }
    }

    public void notifySemWallpaperColors(int i) {
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
        WallpaperData peekWallpaperDataLocked =
            wallpaperManagerService.peekWallpaperDataLocked(
                wallpaperManagerService.getCurrentUserId(), i);
        if (peekWallpaperDataLocked == null) {
          Log.addLogString(
              "WallpaperManagerService", "notifySemWallpaperColors, wallpaper == null");
        } else {
          WallpaperManagerService.this.notifySemColorListeners(peekWallpaperDataLocked);
        }
      }
    }

    public void updateDisplayData() {
      Log.m129d("WallpaperManagerService", "updateDisplayData");
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService.this.mWallpaperDisplayHelper.forEachDisplayData(
            new Consumer() { // from class:
                             // com.android.server.wallpaper.WallpaperManagerService$SemCallback$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WallpaperManagerService.SemCallback.this.lambda$updateDisplayData$0(
                    (WallpaperDisplayHelper.DisplayData) obj);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDisplayData$0(
        WallpaperDisplayHelper.DisplayData displayData) {
      displayData.mWidth = -1;
      displayData.mHeight = -1;
      WallpaperManagerService.this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(
          displayData, displayData.mDisplayId);
    }

    public void dispatchHomeVisibilityChanged(boolean z) {
      WallpaperConnection wallpaperConnection;
      synchronized (WallpaperManagerService.this.mLock) {
        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
        boolean z2 = true;
        WallpaperData peekWallpaperDataLocked =
            wallpaperManagerService.peekWallpaperDataLocked(
                wallpaperManagerService.mCurrentUserId, 1);
        if (peekWallpaperDataLocked != null) {
          if (!WallpaperManagerService.this.mLiveWallpaperHelper.isPreloadedLiveWallpaper(
              peekWallpaperDataLocked)) {
            z2 = false;
          }
          Log.m129d(
              "WallpaperManagerService",
              "dispatchHomeVisibilityChanged: visible = "
                  + z
                  + ", connection = "
                  + peekWallpaperDataLocked.connection
                  + ", systemWallpaper = "
                  + peekWallpaperDataLocked);
          if (z
              && z2
              && ((wallpaperConnection = peekWallpaperDataLocked.connection) == null
                  || wallpaperConnection.mService == null)) {
            Log.addLogString(
                "WallpaperManagerService", "dispatchHomeVisibilityChanged, try to rebind");
            WallpaperManagerService.this.bindWallpaperComponentLocked(
                peekWallpaperDataLocked.wallpaperComponent,
                true,
                true,
                peekWallpaperDataLocked,
                null);
          }
        } else {
          Log.m134w("WallpaperManagerService", "dispatchHomeVisibilityChanged : rebind failed.");
        }
      }
    }
  }

  public ComponentName getDefaultPreloadedLiveWallpaperComponentName(int i) {
    ComponentName defaultLiveWallpaperComponentName =
        this.mSemWallpaperResourcesInfo.getDefaultLiveWallpaperComponentName(i);
    if (defaultLiveWallpaperComponentName == null) {
      return defaultLiveWallpaperComponentName;
    }
    try {
      this.mContext
          .getPackageManager()
          .getPackageInfo(defaultLiveWallpaperComponentName.getPackageName(), 786432);
      return defaultLiveWallpaperComponentName;
    } catch (PackageManager.NameNotFoundException unused) {
      return null;
    }
  }

  public int getDefaultWallpaperType(int i) {
    return this.mSemWallpaperResourcesInfo.getDefaultWallpaperType(
        i, this.mSemService.mCMFWallpaper.getDeviceColor());
  }

  public final boolean isDefaultMultipack(int i, int i2, String str) {
    return (!this.mSemWallpaperResourcesInfo.isDefaultMultipack(i2)
            || WallpaperManager.isDefaultOperatorWallpaper(
                this.mContext, 2, this.mSemService.mCMFWallpaper.getDeviceColor())
            || "com.android.wallpaper.livepicker".equals(str))
        ? false
        : true;
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public WallpaperData requestWallpaperData(int i, int i2) {
    WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i, i2);
    if (peekWallpaperDataLocked == null) {
      synchronized (this.mLock) {
        peekWallpaperDataLocked = getWallpaperSafeLocked(i, i2);
      }
    }
    return peekWallpaperDataLocked;
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public List requestKeyguardListeners() {
    return this.mKeyguardListenerList;
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestBindWallpaper(int i, int i2, ComponentName componentName) {
    WallpaperData wallpaperData =
        (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap)
            .get(i, WhichChecker.getMode(i2));
    if (wallpaperData == null) {
      Log.m130e("WallpaperManagerService", "requestBindWallpaper: wallpaper is null.");
      return;
    }
    putContentAttributes(
        wallpaperData,
        invokePrepare(i, i2, componentName, wallpaperData.mSemWallpaperData.getExternalParams()));
    synchronized (this.mLock) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        resetSemWallpaperData(wallpaperData.mSemWallpaperData, i);
        if (canBindComponentNow(i2)) {
          wallpaperData.mWhich = i2;
          Log.m129d("WallpaperManagerService", "requestBindWallpaper: which = " + i2);
          if (componentName == null) {
            componentName = this.mImageWallpaper;
          }
          bindWallpaperComponentLocked(componentName, true, false, wallpaperData, null);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestSaveRestoredWallpaperLocked(int i, int i2, WallpaperData wallpaperData) {
    synchronized (this.mLock) {
      (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap)
          .put(i, WhichChecker.getMode(i2), wallpaperData);
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestWallpaperId(WallpaperData wallpaperData) {
    wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestSaveSettingsLocked(int i, int i2) {
    synchronized (this.mLock) {
      saveSettingsLocked(i, WhichChecker.getMode(i2));
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestClearWallpaper(int i, int i2) {
    clearWallpaper(this.mContext.getOpPackageName(), i2, i);
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestNotifyLockWallpaperChanged(int i, int i2, Bundle bundle) {
    int mode = WhichChecker.getMode(i2) | 2;
    WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i, mode);
    if (peekWallpaperDataLocked != null) {
      notifyLockWallpaperChanged(peekWallpaperDataLocked.getWallpaperType(), mode, bundle);
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestNotifyCoverWallpaperChanged(int i, int i2, Bundle bundle) {
    WallpaperData peekWallpaperDataLocked;
    Log.m130e(
        "WallpaperManagerService",
        "requestNotifyCoverWallpaperChanged: userId = " + i + ", which = " + i2);
    if (WhichChecker.isWatchFaceDisplay(i2)
        && (peekWallpaperDataLocked = peekWallpaperDataLocked(i, i2)) != null) {
      notifyCoverWallpaperChanged(peekWallpaperDataLocked.getWallpaperType(), i2);
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestNotifyMultipackApplied(int i, int i2, Bundle bundle) {
    int mode = WhichChecker.getMode(i2) | 2;
    Log.m129d(
        "WallpaperManagerService",
        "requestNotifyMultipackApplied: which = " + i2 + "adjustedWhich = " + mode);
    notifyMultipackApplied(mode);
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestNotifySemWallpaperColors(int i) {
    synchronized (this.mLock) {
      WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(getCurrentUserId(), i);
      if (peekWallpaperDataLocked != null) {
        notifySemColorListeners(peekWallpaperDataLocked);
      }
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestParseWallpaperAttributes(
      TypedXmlPullParser typedXmlPullParser, WallpaperData wallpaperData, boolean z) {
    try {
      this.mWallpaperDataParser.parseWallpaperAttributes(typedXmlPullParser, wallpaperData, z);
    } catch (XmlPullParserException e) {
      Log.m130e("WallpaperManagerService", "requestParseWallpaperAttributes: " + e.getMessage());
    }
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestWriteWallpaperAttributes(
      TypedXmlSerializer typedXmlSerializer, String str, WallpaperData wallpaperData) {
    this.mWallpaperDataParser.writeWallpaperAttributes(typedXmlSerializer, str, wallpaperData);
  }

  @Override // com.samsung.server.wallpaper.snapshot.SnapshotCallback
  public void requestInitializeThumnailFile(WallpaperData wallpaperData, int i, int i2) {
    this.mSemService.initializeThumnailFile(
        wallpaperData, i, wallpaperData.mSemWallpaperData.getWpType(), i2);
  }
}
