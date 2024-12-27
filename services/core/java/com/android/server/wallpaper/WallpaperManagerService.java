package com.android.server.wallpaper;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.ApplicationExitInfo;
import android.app.ILocalWallpaperColorConsumer;
import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.SemWallpaperResourcesInfo;
import android.app.UidObserver;
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
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
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
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.WindowManager;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.JournaledFile;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperDisplayHelper;
import com.android.server.wallpaper.WallpaperManagerService;
import com.android.server.wm.ActivityManagerPerformance;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.android.window.flags.Flags;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.server.wallpaper.BitmapStringEncoder;
import com.samsung.android.server.wallpaper.ThumbnailFileManager;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.AssetFileManager;
import com.samsung.server.wallpaper.CMFWallpaper;
import com.samsung.server.wallpaper.DesktopMode;
import com.samsung.server.wallpaper.LegibilityColor;
import com.samsung.server.wallpaper.LegibilityColor.SettingsObserver;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.OMCWallpaper;
import com.samsung.server.wallpaper.OMCWallpaper.OMCWallpaperUpdatedReceiver;
import com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper;
import com.samsung.server.wallpaper.ProviderRequester;
import com.samsung.server.wallpaper.SemWallpaperData;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import com.samsung.server.wallpaper.snapshot.SnapshotCallback;
import com.samsung.server.wallpaper.snapshot.SnapshotHelper;
import com.samsung.server.wallpaper.snapshot.SnapshotManager;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class WallpaperManagerService extends IWallpaperManager.Stub implements IWallpaperManagerService, SnapshotCallback {
    public static final boolean SHIPPED;
    public static final Map sWallpaperType;
    public int mActiveVirtualDisplayId;
    public final ActivityManager mActivityManager;
    public final AppOpsManager mAppOpsManager;
    public final AssetFileManager mAssetFileManager;
    public WallpaperColors mCacheDefaultImageWallpaperColors;
    public final SparseArray mColorsChangedListeners;
    public final Context mContext;
    public final ArrayList mCoverWallpaperListenerClientList;
    public final ArrayList mCoverWallpaperListenerList;
    public int mCurrentUserId;
    public final ComponentName mDefaultWallpaperComponent;
    public final AnonymousClass1 mDisplayListener;
    public WallpaperData mFallbackWallpaper;
    public ServiceThread mHandlerThread;
    public final IPackageManager mIPackageManager;
    public final ComponentName mImageWallpaper;
    public boolean mInAmbientMode;
    public final boolean mInitialUserSwitch;
    public final SparseArray mIsWallpaperInitialized;
    public final ArrayList mKeyguardListenerClientList;
    public final ArrayList mKeyguardListenerList;
    public WallpaperData mLastDexLockWallpaper;
    public WallpaperData mLastDexWallpaper;
    public WallpaperData mLastLockWallpaper;
    public WallpaperData mLastSubLockWallpaper;
    public WallpaperData mLastSubWallpaper;
    public WallpaperData mLastVirtualWallpaper;
    public WallpaperData mLastWallpaper;
    public final LocalColorRepository mLocalColorRepo;
    public final Object mLock;
    public final WallpaperObserver mLockWallpaperMap;
    public final MyPackageMonitor mMonitor;
    public final PackageManagerInternal mPackageManagerInternal;
    public WallpaperObserver mPendingMigrationViaStatic;
    public final SemWallpaperManagerService mSemService;
    public final SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public boolean mShuttingDown;
    public final IStatusBarService mStatusBarService;
    public final SparseBooleanArray mUserRestorecon;
    public final WallpaperCropper mWallpaperCropper;
    final WallpaperDataParser mWallpaperDataParser;
    final WallpaperDisplayHelper mWallpaperDisplayHelper;
    public final Handler mWallpaperHanlder;
    public final WallpaperObserver mWallpaperMap;
    public WallpaperObserver mWallpaperObserver = null;
    public final WindowManagerInternal mWindowManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class DisplayConnector {
        public boolean mDimensionsChanged;
        public final int mDisplayId;
        public IWallpaperEngine mEngine;
        public boolean mPaddingChanged;
        public final Binder mToken = new Binder();

        public DisplayConnector(int i) {
            this.mDisplayId = i;
        }

        public final void connectLocked(WallpaperConnection wallpaperConnection, WallpaperData wallpaperData) {
            TimingsTraceAndSlog timingsTraceAndSlog;
            if (wallpaperConnection.mService == null) {
                Slog.w("WallpaperManagerService", "WallpaperService is not connected yet");
                return;
            }
            TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog("WallpaperManagerService");
            timingsTraceAndSlog2.traceBegin("WPMS.connectLocked-" + wallpaperData.wallpaperComponent);
            boolean isWatchFaceDisplay = WhichChecker.isWatchFaceDisplay(wallpaperData.mWhich);
            int i = this.mDisplayId;
            if (!isWatchFaceDisplay || i == 1) {
                boolean isVirtualDisplay = WhichChecker.isVirtualDisplay(wallpaperData.mWhich);
                WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                if (!isVirtualDisplay || wallpaperManagerService.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(i)) {
                    Slog.v("WallpaperManagerService", "Adding window token: " + this.mToken + " , wallpaper = " + wallpaperData);
                    DisplayAddress.Physical physical = null;
                    wallpaperManagerService.mWindowManagerInternal.addWindowToken(this.mToken, 2013, i, null);
                    wallpaperManagerService.mWindowManagerInternal.setWallpaperShowWhenLocked(this.mToken, wallpaperManagerService.isVisibleWhichWhenKeyguardLocked(wallpaperData.mWhich));
                    int i2 = wallpaperData.mWhich;
                    Log.v("WallpaperManagerService", "getDisplayAddressByWhich: which = " + i2);
                    int i3 = 0;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                        long primaryPhysicalDisplayId = DisplayManagerGlobal.getInstance().getPrimaryPhysicalDisplayId();
                        if (WhichChecker.isPhone(i2)) {
                            physical = DisplayAddress.fromPhysicalDisplayId(primaryPhysicalDisplayId);
                        } else if (WhichChecker.isSubDisplay(i2)) {
                            long[] physicalDisplayIds = SurfaceControl.getPhysicalDisplayIds();
                            int length = physicalDisplayIds.length;
                            int i4 = 0;
                            while (true) {
                                if (i4 >= length) {
                                    break;
                                }
                                long j = physicalDisplayIds[i4];
                                if (j != primaryPhysicalDisplayId) {
                                    physical = DisplayAddress.fromPhysicalDisplayId(j);
                                    break;
                                }
                                i4++;
                            }
                        }
                    }
                    if (physical != null) {
                        wallpaperManagerService.mWindowManagerInternal.setWallpaperDisplayAddress(this.mToken, physical);
                    }
                    if (Flags.multiCrop() && wallpaperManagerService.mImageWallpaper.equals(wallpaperData.wallpaperComponent)) {
                        WindowManagerInternal windowManagerInternal = wallpaperManagerService.mWindowManagerInternal;
                        Binder binder = this.mToken;
                        wallpaperManagerService.mWallpaperCropper.getClass();
                        windowManagerInternal.setWallpaperCropHints(binder, WallpaperCropper.getRelativeCropHints(wallpaperData));
                    } else {
                        wallpaperManagerService.mWindowManagerInternal.setWallpaperCropHints(this.mToken, new SparseArray());
                    }
                    WallpaperDisplayHelper.DisplayData displayDataOrCreate = wallpaperManagerService.mWallpaperDisplayHelper.getDisplayDataOrCreate(i);
                    try {
                        timingsTraceAndSlog = timingsTraceAndSlog2;
                        try {
                            wallpaperConnection.mService.attach(wallpaperConnection, this.mToken, 2013, false, displayDataOrCreate.mWidth, displayDataOrCreate.mHeight, displayDataOrCreate.mPadding, this.mDisplayId, wallpaperData.mWhich, wallpaperConnection.mInfo);
                        } catch (RemoteException e) {
                            e = e;
                            Slog.w("WallpaperManagerService", "Failed attaching wallpaper on display", e);
                            if (!wallpaperData.wallpaperUpdating) {
                                for (int size = wallpaperConnection.mDisplayConnector.size() - 1; size >= 0; size--) {
                                    if (((DisplayConnector) wallpaperConnection.mDisplayConnector.valueAt(size)).mEngine != null) {
                                        i3++;
                                    }
                                }
                                if (i3 == 0) {
                                    wallpaperData.mBindSource = WallpaperData.BindSource.CONNECT_LOCKED;
                                    WallpaperManagerService.this.bindWallpaperComponentLocked(null, false, false, wallpaperData, null, null);
                                }
                            }
                            timingsTraceAndSlog.traceEnd();
                        }
                    } catch (RemoteException e2) {
                        e = e2;
                        timingsTraceAndSlog = timingsTraceAndSlog2;
                    }
                    timingsTraceAndSlog.traceEnd();
                }
            }
        }

        public final void disconnectLocked(WallpaperConnection wallpaperConnection) {
            StringBuilder sb = new StringBuilder("Removing window token: ");
            sb.append(this.mToken);
            sb.append(" , mDisplayId = ");
            int i = this.mDisplayId;
            GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, i, "WallpaperManagerService");
            WallpaperManagerService.this.mWindowManagerInternal.removeWindowToken(this.mToken, false, i);
            try {
                IWallpaperService iWallpaperService = wallpaperConnection.mService;
                if (iWallpaperService != null) {
                    iWallpaperService.detach(this.mToken);
                }
            } catch (RemoteException e) {
                Slog.w("WallpaperManagerService", "connection.mService.destroy() threw a RemoteException", e);
            }
            this.mEngine = null;
        }

        public final void ensureStatusHandled() {
            WallpaperDisplayHelper.DisplayData displayDataOrCreate = WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplayDataOrCreate(this.mDisplayId);
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public IWallpaperManagerService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            IWallpaperManagerService iWallpaperManagerService = this.mService;
            if (iWallpaperManagerService != null) {
                final WallpaperManagerService wallpaperManagerService = (WallpaperManagerService) iWallpaperManagerService;
                WallpaperManagerService.sWallpaperType.forEach(new WallpaperManagerService$$ExternalSyntheticLambda25(wallpaperManagerService, 0));
                if (i == 550) {
                    Log.d("WallpaperManagerService", "onBootPhase: " + i);
                    final int i2 = 0;
                    wallpaperManagerService.mWallpaperHanlder.post(new Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str;
                            String str2;
                            final int i3 = 1;
                            FileOutputStream fileOutputStream = null;
                            final int i4 = 0;
                            int i5 = i2;
                            final WallpaperManagerService wallpaperManagerService2 = wallpaperManagerService;
                            switch (i5) {
                                case 0:
                                    wallpaperManagerService2.mMonitor.register(wallpaperManagerService2.mContext, (Looper) null, UserHandle.ALL, true);
                                    Environment.getUserSystemDirectory(0).mkdirs();
                                    WallpaperUtils.getWallpaperLockDir(0).mkdir();
                                    wallpaperManagerService2.mSemService.mCurrentUserId = 0;
                                    if (wallpaperManagerService2.mSemWallpaperResourcesInfo.isSupportCMF()) {
                                        CMFWallpaper cMFWallpaper = wallpaperManagerService2.mSemService.mCMFWallpaper;
                                        String string = Settings.System.getString(cMFWallpaper.mContext.getContentResolver(), "cmf_color_code");
                                        Log.v("CMFWallpaper", "getDefaultDeviceColor : " + string);
                                        if (TextUtils.isEmpty(string)) {
                                            String str3 = SystemProperties.get("ril.product_code");
                                            Log.v("CMFWallpaper", "rilProductCode = " + str3);
                                            String lowerCase = (str3 == null || str3.length() < 10) ? null : str3.substring(8, 10).toLowerCase();
                                            String str4 = cMFWallpaper.mAlternativeCode;
                                            if (lowerCase != null) {
                                                Log.d("CMFWallpaper", "rilProductCode='" + str3 + "', colorCode=" + lowerCase);
                                                str2 = cMFWallpaper.getProperColorCode(lowerCase, str4);
                                                CMFWallpaper.AnonymousClass1 anonymousClass1 = cMFWallpaper.mHandler;
                                                Message obtainMessage = anonymousClass1.obtainMessage(1013);
                                                obtainMessage.obj = str2;
                                                anonymousClass1.sendMessage(obtainMessage);
                                            } else {
                                                if (cMFWallpaper.mColorCodePollingThread == null) {
                                                    CMFWallpaper.AnonymousClass3 anonymousClass3 = new Thread() { // from class: com.samsung.server.wallpaper.CMFWallpaper.3
                                                        public AnonymousClass3() {
                                                        }

                                                        @Override // java.lang.Thread, java.lang.Runnable
                                                        public final void run() {
                                                            Log.v("CMFWallpaper", "ColorCodePollingThread run()");
                                                            CMFWallpaper.this.mProductCode = SystemProperties.get("ril.product_code");
                                                            while (TextUtils.isEmpty(CMFWallpaper.this.mProductCode) && CMFWallpaper.this.mLastColorCodePollingThreadCount > 0) {
                                                                Log.v("CMFWallpaper", "ColorCodePollingThread sleep(1000) count=" + CMFWallpaper.this.mLastColorCodePollingThreadCount);
                                                                try {
                                                                    Thread.sleep(1000L);
                                                                } catch (InterruptedException unused) {
                                                                    Log.e("CMFWallpaper", "InterruptedException occurred");
                                                                }
                                                                CMFWallpaper cMFWallpaper2 = CMFWallpaper.this;
                                                                cMFWallpaper2.mLastColorCodePollingThreadCount--;
                                                                cMFWallpaper2.mProductCode = SystemProperties.get("ril.product_code");
                                                            }
                                                            String str5 = CMFWallpaper.this.mProductCode;
                                                            String lowerCase2 = (str5 == null || str5.length() < 10) ? null : str5.substring(8, 10).toLowerCase();
                                                            if (lowerCase2 != null) {
                                                                Log.v("CMFWallpaper", "Color code (" + lowerCase2 + ") retrieved!!");
                                                                CMFWallpaper cMFWallpaper3 = CMFWallpaper.this;
                                                                String properColorCode = cMFWallpaper3.getProperColorCode(lowerCase2, cMFWallpaper3.mAlternativeCode);
                                                                AnonymousClass1 anonymousClass12 = CMFWallpaper.this.mHandler;
                                                                Message obtainMessage2 = anonymousClass12.obtainMessage(1013);
                                                                obtainMessage2.obj = properColorCode;
                                                                anonymousClass12.sendMessage(obtainMessage2);
                                                                CMFWallpaper cMFWallpaper4 = CMFWallpaper.this;
                                                                cMFWallpaper4.getClass();
                                                                cMFWallpaper4.mDeviceColor = ProductFeatures.getFeatureBasedColor(properColorCode);
                                                                cMFWallpaper4.setLegacyDeviceColor(properColorCode);
                                                                CMFWallpaper cMFWallpaper5 = CMFWallpaper.this;
                                                                String deviceColor = cMFWallpaper5.getDeviceColor();
                                                                if (WallpaperManager.getCSCWallpaperFile(cMFWallpaper5.mContext, 1, null, deviceColor) == null && WallpaperManager.getCSCWallpaperFile(cMFWallpaper5.mContext, 2, null, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(cMFWallpaper5.mContext, 1, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(cMFWallpaper5.mContext, 2, deviceColor) == null) {
                                                                    CMFWallpaper.this.mService.mDefaultWallpaper.mHandler.sendEmptyMessage(1008);
                                                                }
                                                            }
                                                            CMFWallpaper.this.mColorCodePollingThread = null;
                                                        }
                                                    };
                                                    cMFWallpaper.mColorCodePollingThread = anonymousClass3;
                                                    anonymousClass3.setName("ColorCodePollingThread");
                                                    cMFWallpaper.mColorCodePollingThread.start();
                                                }
                                                str2 = null;
                                            }
                                            string = cMFWallpaper.getProperColorCode(str2, str4);
                                        }
                                        cMFWallpaper.mDeviceColor = CMFWallpaper.ProductFeatures.getFeatureBasedColor(string);
                                        cMFWallpaper.setLegacyDeviceColor(string);
                                    }
                                    boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE;
                                    if (z) {
                                        wallpaperManagerService2.mSemService.mSubDisplayMode.updateLidStateFromInputManager();
                                    }
                                    wallpaperManagerService2.mIsWallpaperInitialized.set(0, Boolean.valueOf(WallpaperManagerService.isWallpaperFileExists(0)));
                                    SemWallpaperManagerService semWallpaperManagerService = wallpaperManagerService2.mSemService;
                                    synchronized (semWallpaperManagerService.mSnapshotDataLock) {
                                        semWallpaperManagerService.mSnapshotManager.loadSettingsLockedForSnapshot(0);
                                    }
                                    wallpaperManagerService2.loadSettingsLocked(0, 3, 4, false);
                                    wallpaperManagerService2.getWallpaperSafeLocked(0, 5);
                                    if (z && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 3, 16, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 17);
                                    }
                                    if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 1, 16, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 17);
                                    }
                                    if (Rune.VIRTUAL_DISPLAY_WALLPAPER && wallpaperManagerService2.isVirtualWallpaperDisplay(wallpaperManagerService2.mActiveVirtualDisplayId)) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 1, 32, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 33);
                                    }
                                    WallpaperData wallpaperData = wallpaperManagerService2.mWallpaperMap.get(0, 4);
                                    Log.d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 1");
                                    wallpaperManagerService2.initImageWallpaperCropFile(5, wallpaperData);
                                    if (z) {
                                        WallpaperData wallpaperData2 = wallpaperManagerService2.mWallpaperMap.get(0, 16);
                                        Log.d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 2");
                                        wallpaperManagerService2.initImageWallpaperCropFile(17, wallpaperData2);
                                    }
                                    wallpaperManagerService2.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.3
                                        @Override // android.content.BroadcastReceiver
                                        public final void onReceive(Context context, Intent intent) {
                                            switch (i4) {
                                                case 0:
                                                    if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                                                        WallpaperManagerService wallpaperManagerService3 = wallpaperManagerService2;
                                                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                                                        if (intExtra < 1) {
                                                            wallpaperManagerService3.getClass();
                                                            return;
                                                        }
                                                        synchronized (wallpaperManagerService3.mLock) {
                                                            wallpaperManagerService3.stopObserversLocked(intExtra);
                                                            ((ArrayList) WallpaperUtils.getWallpaperFiles(intExtra)).forEach(new WallpaperManagerService$$ExternalSyntheticLambda28(2));
                                                            wallpaperManagerService3.mUserRestorecon.delete(intExtra);
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                                                        synchronized (wallpaperManagerService2.mLock) {
                                                            wallpaperManagerService2.mShuttingDown = true;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                            }
                                        }
                                    }, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"));
                                    wallpaperManagerService2.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.3
                                        @Override // android.content.BroadcastReceiver
                                        public final void onReceive(Context context, Intent intent) {
                                            switch (i3) {
                                                case 0:
                                                    if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                                                        WallpaperManagerService wallpaperManagerService3 = wallpaperManagerService2;
                                                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                                                        if (intExtra < 1) {
                                                            wallpaperManagerService3.getClass();
                                                            return;
                                                        }
                                                        synchronized (wallpaperManagerService3.mLock) {
                                                            wallpaperManagerService3.stopObserversLocked(intExtra);
                                                            ((ArrayList) WallpaperUtils.getWallpaperFiles(intExtra)).forEach(new WallpaperManagerService$$ExternalSyntheticLambda28(2));
                                                            wallpaperManagerService3.mUserRestorecon.delete(intExtra);
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                                                        synchronized (wallpaperManagerService2.mLock) {
                                                            wallpaperManagerService2.mShuttingDown = true;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                            }
                                        }
                                    }, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
                                    LegibilityColor legibilityColor = wallpaperManagerService2.mSemService.mLegibilityColor;
                                    legibilityColor.mContentResolver = legibilityColor.mContext.getContentResolver();
                                    legibilityColor.mContentResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, legibilityColor.new SettingsObserver());
                                    legibilityColor.mAllowScreenRotateSystem = legibilityColor.allowScreenRotate(1);
                                    legibilityColor.mAllowScreenRotateLock = legibilityColor.allowScreenRotate(2);
                                    try {
                                        ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.server.wallpaper.WallpaperManagerService.5
                                            public final void onUserSwitching(int i6, IRemoteCallback iRemoteCallback) {
                                                WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
                                                boolean z2 = WallpaperManagerService.SHIPPED;
                                                wallpaperManagerService3.getClass();
                                                WallpaperManagerService.sWallpaperType.forEach(new WallpaperManagerService$$ExternalSyntheticLambda25(wallpaperManagerService3, i6));
                                                WallpaperManagerService.this.switchUser(i6, iRemoteCallback);
                                            }
                                        }, "WallpaperManagerService");
                                    } catch (RemoteException e) {
                                        e.rethrowAsRuntimeException();
                                    }
                                    OMCWallpaper oMCWallpaper = wallpaperManagerService2.mSemService.mOMCWallpaper;
                                    oMCWallpaper.getClass();
                                    Log.d("OMCWallpaper", "registerOMCWallpaperUpdatedReceiver");
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction("com.samsung.intent.action.RSCUPDATE_START");
                                    oMCWallpaper.mContext.registerReceiver(oMCWallpaper.new OMCWallpaperUpdatedReceiver(), intentFilter);
                                    WallpaperData wallpaperData3 = wallpaperManagerService2.mLockWallpaperMap.get(UserHandle.getCallingUserId(), 0);
                                    if (wallpaperData3 != null && wallpaperData3.mSemWallpaperData.mWpType != -1) {
                                        i3 = 0;
                                    }
                                    OMCWallpaper oMCWallpaper2 = wallpaperManagerService2.mSemService.mOMCWallpaper;
                                    oMCWallpaper2.getClass();
                                    Log.d("OMCWallpaper", "checkTSSActivation");
                                    File file = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
                                    if (file.exists()) {
                                        try {
                                            str = SemWallpaperManagerService.getStringFromFile(file.getPath());
                                        } catch (Exception unused) {
                                            str = null;
                                        }
                                    } else {
                                        str = "false";
                                    }
                                    String str5 = SystemProperties.get("mdc.singlesku.activated");
                                    Log.d("OMCWallpaper", "checkTSSActivation, old= " + str + ", new=" + str5);
                                    if (str5.equals(str)) {
                                        if ("true".equals(str)) {
                                            int i6 = Settings.System.getInt(oMCWallpaper2.mContext.getContentResolver(), "tss_activated", -1);
                                            if (i3 == 0 || i6 != -1) {
                                                return;
                                            }
                                            oMCWallpaper2.saveTSSActivationSettings(str);
                                            return;
                                        }
                                        return;
                                    }
                                    WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
                                    WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(wallpaperManagerService3, 4);
                                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(wallpaperManagerService3, 16);
                                    }
                                    Log.d("OMCWallpaper", "saveTSSActivation, ".concat(str5));
                                    File file2 = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
                                    try {
                                        file2.createNewFile();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                    try {
                                        try {
                                            try {
                                                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                                try {
                                                    fileOutputStream2.write(str5.getBytes(StandardCharsets.UTF_8));
                                                    Log.d("OMCWallpaper", "save done");
                                                    fileOutputStream2.close();
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    fileOutputStream = fileOutputStream2;
                                                    e.printStackTrace();
                                                    if (fileOutputStream != null) {
                                                        fileOutputStream.close();
                                                    }
                                                    oMCWallpaper2.saveTSSActivationSettings(str5);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    fileOutputStream = fileOutputStream2;
                                                    if (fileOutputStream != null) {
                                                        try {
                                                            fileOutputStream.close();
                                                        } catch (IOException e4) {
                                                            e4.printStackTrace();
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            } catch (Exception e5) {
                                                e = e5;
                                            }
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                        oMCWallpaper2.saveTSSActivationSettings(str5);
                                        return;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                    break;
                                default:
                                    boolean z2 = WallpaperManagerService.SHIPPED;
                                    wallpaperManagerService2.switchUser(0, null);
                                    return;
                            }
                        }
                    });
                    return;
                }
                if (i == 600) {
                    Log.d("WallpaperManagerService", "onBootPhase: " + i);
                    final int i3 = 1;
                    wallpaperManagerService.mWallpaperHanlder.post(new Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str;
                            String str2;
                            final int i32 = 1;
                            FileOutputStream fileOutputStream = null;
                            final int i4 = 0;
                            int i5 = i3;
                            final WallpaperManagerService wallpaperManagerService2 = wallpaperManagerService;
                            switch (i5) {
                                case 0:
                                    wallpaperManagerService2.mMonitor.register(wallpaperManagerService2.mContext, (Looper) null, UserHandle.ALL, true);
                                    Environment.getUserSystemDirectory(0).mkdirs();
                                    WallpaperUtils.getWallpaperLockDir(0).mkdir();
                                    wallpaperManagerService2.mSemService.mCurrentUserId = 0;
                                    if (wallpaperManagerService2.mSemWallpaperResourcesInfo.isSupportCMF()) {
                                        CMFWallpaper cMFWallpaper = wallpaperManagerService2.mSemService.mCMFWallpaper;
                                        String string = Settings.System.getString(cMFWallpaper.mContext.getContentResolver(), "cmf_color_code");
                                        Log.v("CMFWallpaper", "getDefaultDeviceColor : " + string);
                                        if (TextUtils.isEmpty(string)) {
                                            String str3 = SystemProperties.get("ril.product_code");
                                            Log.v("CMFWallpaper", "rilProductCode = " + str3);
                                            String lowerCase = (str3 == null || str3.length() < 10) ? null : str3.substring(8, 10).toLowerCase();
                                            String str4 = cMFWallpaper.mAlternativeCode;
                                            if (lowerCase != null) {
                                                Log.d("CMFWallpaper", "rilProductCode='" + str3 + "', colorCode=" + lowerCase);
                                                str2 = cMFWallpaper.getProperColorCode(lowerCase, str4);
                                                CMFWallpaper.AnonymousClass1 anonymousClass1 = cMFWallpaper.mHandler;
                                                Message obtainMessage = anonymousClass1.obtainMessage(1013);
                                                obtainMessage.obj = str2;
                                                anonymousClass1.sendMessage(obtainMessage);
                                            } else {
                                                if (cMFWallpaper.mColorCodePollingThread == null) {
                                                    CMFWallpaper.AnonymousClass3 anonymousClass3 = new Thread() { // from class: com.samsung.server.wallpaper.CMFWallpaper.3
                                                        public AnonymousClass3() {
                                                        }

                                                        @Override // java.lang.Thread, java.lang.Runnable
                                                        public final void run() {
                                                            Log.v("CMFWallpaper", "ColorCodePollingThread run()");
                                                            CMFWallpaper.this.mProductCode = SystemProperties.get("ril.product_code");
                                                            while (TextUtils.isEmpty(CMFWallpaper.this.mProductCode) && CMFWallpaper.this.mLastColorCodePollingThreadCount > 0) {
                                                                Log.v("CMFWallpaper", "ColorCodePollingThread sleep(1000) count=" + CMFWallpaper.this.mLastColorCodePollingThreadCount);
                                                                try {
                                                                    Thread.sleep(1000L);
                                                                } catch (InterruptedException unused) {
                                                                    Log.e("CMFWallpaper", "InterruptedException occurred");
                                                                }
                                                                CMFWallpaper cMFWallpaper2 = CMFWallpaper.this;
                                                                cMFWallpaper2.mLastColorCodePollingThreadCount--;
                                                                cMFWallpaper2.mProductCode = SystemProperties.get("ril.product_code");
                                                            }
                                                            String str5 = CMFWallpaper.this.mProductCode;
                                                            String lowerCase2 = (str5 == null || str5.length() < 10) ? null : str5.substring(8, 10).toLowerCase();
                                                            if (lowerCase2 != null) {
                                                                Log.v("CMFWallpaper", "Color code (" + lowerCase2 + ") retrieved!!");
                                                                CMFWallpaper cMFWallpaper3 = CMFWallpaper.this;
                                                                String properColorCode = cMFWallpaper3.getProperColorCode(lowerCase2, cMFWallpaper3.mAlternativeCode);
                                                                AnonymousClass1 anonymousClass12 = CMFWallpaper.this.mHandler;
                                                                Message obtainMessage2 = anonymousClass12.obtainMessage(1013);
                                                                obtainMessage2.obj = properColorCode;
                                                                anonymousClass12.sendMessage(obtainMessage2);
                                                                CMFWallpaper cMFWallpaper4 = CMFWallpaper.this;
                                                                cMFWallpaper4.getClass();
                                                                cMFWallpaper4.mDeviceColor = ProductFeatures.getFeatureBasedColor(properColorCode);
                                                                cMFWallpaper4.setLegacyDeviceColor(properColorCode);
                                                                CMFWallpaper cMFWallpaper5 = CMFWallpaper.this;
                                                                String deviceColor = cMFWallpaper5.getDeviceColor();
                                                                if (WallpaperManager.getCSCWallpaperFile(cMFWallpaper5.mContext, 1, null, deviceColor) == null && WallpaperManager.getCSCWallpaperFile(cMFWallpaper5.mContext, 2, null, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(cMFWallpaper5.mContext, 1, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(cMFWallpaper5.mContext, 2, deviceColor) == null) {
                                                                    CMFWallpaper.this.mService.mDefaultWallpaper.mHandler.sendEmptyMessage(1008);
                                                                }
                                                            }
                                                            CMFWallpaper.this.mColorCodePollingThread = null;
                                                        }
                                                    };
                                                    cMFWallpaper.mColorCodePollingThread = anonymousClass3;
                                                    anonymousClass3.setName("ColorCodePollingThread");
                                                    cMFWallpaper.mColorCodePollingThread.start();
                                                }
                                                str2 = null;
                                            }
                                            string = cMFWallpaper.getProperColorCode(str2, str4);
                                        }
                                        cMFWallpaper.mDeviceColor = CMFWallpaper.ProductFeatures.getFeatureBasedColor(string);
                                        cMFWallpaper.setLegacyDeviceColor(string);
                                    }
                                    boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE;
                                    if (z) {
                                        wallpaperManagerService2.mSemService.mSubDisplayMode.updateLidStateFromInputManager();
                                    }
                                    wallpaperManagerService2.mIsWallpaperInitialized.set(0, Boolean.valueOf(WallpaperManagerService.isWallpaperFileExists(0)));
                                    SemWallpaperManagerService semWallpaperManagerService = wallpaperManagerService2.mSemService;
                                    synchronized (semWallpaperManagerService.mSnapshotDataLock) {
                                        semWallpaperManagerService.mSnapshotManager.loadSettingsLockedForSnapshot(0);
                                    }
                                    wallpaperManagerService2.loadSettingsLocked(0, 3, 4, false);
                                    wallpaperManagerService2.getWallpaperSafeLocked(0, 5);
                                    if (z && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 3, 16, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 17);
                                    }
                                    if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 1, 16, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 17);
                                    }
                                    if (Rune.VIRTUAL_DISPLAY_WALLPAPER && wallpaperManagerService2.isVirtualWallpaperDisplay(wallpaperManagerService2.mActiveVirtualDisplayId)) {
                                        wallpaperManagerService2.loadSettingsLocked(0, 1, 32, false);
                                        wallpaperManagerService2.getWallpaperSafeLocked(0, 33);
                                    }
                                    WallpaperData wallpaperData = wallpaperManagerService2.mWallpaperMap.get(0, 4);
                                    Log.d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 1");
                                    wallpaperManagerService2.initImageWallpaperCropFile(5, wallpaperData);
                                    if (z) {
                                        WallpaperData wallpaperData2 = wallpaperManagerService2.mWallpaperMap.get(0, 16);
                                        Log.d("WallpaperManagerService", "systemReady: initImageWallpaperCropFile - 2");
                                        wallpaperManagerService2.initImageWallpaperCropFile(17, wallpaperData2);
                                    }
                                    wallpaperManagerService2.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.3
                                        @Override // android.content.BroadcastReceiver
                                        public final void onReceive(Context context, Intent intent) {
                                            switch (i4) {
                                                case 0:
                                                    if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                                                        WallpaperManagerService wallpaperManagerService3 = wallpaperManagerService2;
                                                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                                                        if (intExtra < 1) {
                                                            wallpaperManagerService3.getClass();
                                                            return;
                                                        }
                                                        synchronized (wallpaperManagerService3.mLock) {
                                                            wallpaperManagerService3.stopObserversLocked(intExtra);
                                                            ((ArrayList) WallpaperUtils.getWallpaperFiles(intExtra)).forEach(new WallpaperManagerService$$ExternalSyntheticLambda28(2));
                                                            wallpaperManagerService3.mUserRestorecon.delete(intExtra);
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                                                        synchronized (wallpaperManagerService2.mLock) {
                                                            wallpaperManagerService2.mShuttingDown = true;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                            }
                                        }
                                    }, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"));
                                    wallpaperManagerService2.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.3
                                        @Override // android.content.BroadcastReceiver
                                        public final void onReceive(Context context, Intent intent) {
                                            switch (i32) {
                                                case 0:
                                                    if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                                                        WallpaperManagerService wallpaperManagerService3 = wallpaperManagerService2;
                                                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                                                        if (intExtra < 1) {
                                                            wallpaperManagerService3.getClass();
                                                            return;
                                                        }
                                                        synchronized (wallpaperManagerService3.mLock) {
                                                            wallpaperManagerService3.stopObserversLocked(intExtra);
                                                            ((ArrayList) WallpaperUtils.getWallpaperFiles(intExtra)).forEach(new WallpaperManagerService$$ExternalSyntheticLambda28(2));
                                                            wallpaperManagerService3.mUserRestorecon.delete(intExtra);
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                                                        synchronized (wallpaperManagerService2.mLock) {
                                                            wallpaperManagerService2.mShuttingDown = true;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                            }
                                        }
                                    }, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
                                    LegibilityColor legibilityColor = wallpaperManagerService2.mSemService.mLegibilityColor;
                                    legibilityColor.mContentResolver = legibilityColor.mContext.getContentResolver();
                                    legibilityColor.mContentResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, legibilityColor.new SettingsObserver());
                                    legibilityColor.mAllowScreenRotateSystem = legibilityColor.allowScreenRotate(1);
                                    legibilityColor.mAllowScreenRotateLock = legibilityColor.allowScreenRotate(2);
                                    try {
                                        ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.server.wallpaper.WallpaperManagerService.5
                                            public final void onUserSwitching(int i6, IRemoteCallback iRemoteCallback) {
                                                WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
                                                boolean z2 = WallpaperManagerService.SHIPPED;
                                                wallpaperManagerService3.getClass();
                                                WallpaperManagerService.sWallpaperType.forEach(new WallpaperManagerService$$ExternalSyntheticLambda25(wallpaperManagerService3, i6));
                                                WallpaperManagerService.this.switchUser(i6, iRemoteCallback);
                                            }
                                        }, "WallpaperManagerService");
                                    } catch (RemoteException e) {
                                        e.rethrowAsRuntimeException();
                                    }
                                    OMCWallpaper oMCWallpaper = wallpaperManagerService2.mSemService.mOMCWallpaper;
                                    oMCWallpaper.getClass();
                                    Log.d("OMCWallpaper", "registerOMCWallpaperUpdatedReceiver");
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction("com.samsung.intent.action.RSCUPDATE_START");
                                    oMCWallpaper.mContext.registerReceiver(oMCWallpaper.new OMCWallpaperUpdatedReceiver(), intentFilter);
                                    WallpaperData wallpaperData3 = wallpaperManagerService2.mLockWallpaperMap.get(UserHandle.getCallingUserId(), 0);
                                    if (wallpaperData3 != null && wallpaperData3.mSemWallpaperData.mWpType != -1) {
                                        i32 = 0;
                                    }
                                    OMCWallpaper oMCWallpaper2 = wallpaperManagerService2.mSemService.mOMCWallpaper;
                                    oMCWallpaper2.getClass();
                                    Log.d("OMCWallpaper", "checkTSSActivation");
                                    File file = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
                                    if (file.exists()) {
                                        try {
                                            str = SemWallpaperManagerService.getStringFromFile(file.getPath());
                                        } catch (Exception unused) {
                                            str = null;
                                        }
                                    } else {
                                        str = "false";
                                    }
                                    String str5 = SystemProperties.get("mdc.singlesku.activated");
                                    Log.d("OMCWallpaper", "checkTSSActivation, old= " + str + ", new=" + str5);
                                    if (str5.equals(str)) {
                                        if ("true".equals(str)) {
                                            int i6 = Settings.System.getInt(oMCWallpaper2.mContext.getContentResolver(), "tss_activated", -1);
                                            if (i32 == 0 || i6 != -1) {
                                                return;
                                            }
                                            oMCWallpaper2.saveTSSActivationSettings(str);
                                            return;
                                        }
                                        return;
                                    }
                                    WallpaperManagerService wallpaperManagerService3 = WallpaperManagerService.this;
                                    WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(wallpaperManagerService3, 4);
                                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                        WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(wallpaperManagerService3, 16);
                                    }
                                    Log.d("OMCWallpaper", "saveTSSActivation, ".concat(str5));
                                    File file2 = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
                                    try {
                                        file2.createNewFile();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                    try {
                                        try {
                                            try {
                                                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                                try {
                                                    fileOutputStream2.write(str5.getBytes(StandardCharsets.UTF_8));
                                                    Log.d("OMCWallpaper", "save done");
                                                    fileOutputStream2.close();
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    fileOutputStream = fileOutputStream2;
                                                    e.printStackTrace();
                                                    if (fileOutputStream != null) {
                                                        fileOutputStream.close();
                                                    }
                                                    oMCWallpaper2.saveTSSActivationSettings(str5);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    fileOutputStream = fileOutputStream2;
                                                    if (fileOutputStream != null) {
                                                        try {
                                                            fileOutputStream.close();
                                                        } catch (IOException e4) {
                                                            e4.printStackTrace();
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            } catch (Exception e5) {
                                                e = e5;
                                            }
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                        oMCWallpaper2.saveTSSActivationSettings(str5);
                                        return;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                    break;
                                default:
                                    boolean z2 = WallpaperManagerService.SHIPPED;
                                    wallpaperManagerService2.switchUser(0, null);
                                    return;
                            }
                        }
                    });
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            try {
                IWallpaperManagerService iWallpaperManagerService = (IWallpaperManagerService) Class.forName(getContext().getResources().getString(R.string.face_error_lockout_screen_lock)).getConstructor(Context.class).newInstance(getContext());
                this.mService = iWallpaperManagerService;
                publishBinderService("wallpaper", iWallpaperManagerService);
            } catch (Exception e) {
                Slog.wtf("WallpaperManagerService", "Failed to instantiate WallpaperManagerService", e);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            IWallpaperManagerService iWallpaperManagerService = this.mService;
            if (iWallpaperManagerService != null) {
                final int userIdentifier = targetUser.getUserIdentifier();
                final WallpaperManagerService wallpaperManagerService = (WallpaperManagerService) iWallpaperManagerService;
                wallpaperManagerService.mWallpaperHanlder.post(new Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                        final int i = userIdentifier;
                        synchronized (wallpaperManagerService2.mLock) {
                            try {
                                if (wallpaperManagerService2.mCurrentUserId == i) {
                                    WallpaperData wallpaperSafeLocked = wallpaperManagerService2.getWallpaperSafeLocked(i, 1);
                                    if (wallpaperSafeLocked.mSemWallpaperData.mWaitingForUnlockUser) {
                                        wallpaperSafeLocked.mBindSource = WallpaperData.BindSource.SWITCH_WALLPAPER_UNLOCK_USER;
                                        wallpaperManagerService2.switchWallpaper(wallpaperSafeLocked, null);
                                        wallpaperManagerService2.notifyCallbacksLocked(wallpaperSafeLocked);
                                    }
                                    WallpaperData wallpaperSafeLocked2 = wallpaperManagerService2.getWallpaperSafeLocked(i, 2);
                                    if (wallpaperSafeLocked2.mSemWallpaperData.mWaitingForUnlockUser) {
                                        wallpaperSafeLocked2.mBindSource = WallpaperData.BindSource.SWITCH_WALLPAPER_UNLOCK_USER;
                                        wallpaperManagerService2.switchWallpaper(wallpaperSafeLocked2, null);
                                        wallpaperManagerService2.notifyCallbacksLocked(wallpaperSafeLocked2);
                                    }
                                    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                                        WallpaperData wallpaperSafeLocked3 = wallpaperManagerService2.getWallpaperSafeLocked(wallpaperManagerService2.mCurrentUserId, 17);
                                        wallpaperSafeLocked3.mWhich |= 16;
                                        if (wallpaperSafeLocked3.mSemWallpaperData.mWaitingForUnlockUser) {
                                            wallpaperManagerService2.switchWallpaper(wallpaperSafeLocked3, null);
                                            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                                SemWallpaperData semWallpaperData = wallpaperSafeLocked3.mSemWallpaperData;
                                                wallpaperManagerService2.notifyCoverWallpaperChanged(semWallpaperData.mWpType, semWallpaperData.mWhich);
                                            }
                                        }
                                    }
                                    if (Rune.VIRTUAL_DISPLAY_WALLPAPER && wallpaperManagerService2.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(wallpaperManagerService2.mActiveVirtualDisplayId)) {
                                        WallpaperData wallpaperSafeLocked4 = wallpaperManagerService2.getWallpaperSafeLocked(wallpaperManagerService2.mCurrentUserId, 33);
                                        wallpaperSafeLocked4.mWhich |= 32;
                                        if (wallpaperSafeLocked4.mSemWallpaperData.mWaitingForUnlockUser) {
                                            wallpaperManagerService2.switchWallpaper(wallpaperSafeLocked4, null);
                                            SemWallpaperData semWallpaperData2 = wallpaperSafeLocked4.mSemWallpaperData;
                                            wallpaperManagerService2.notifyCoverWallpaperChanged(semWallpaperData2.mWpType, semWallpaperData2.mWhich);
                                        }
                                    }
                                    if (!wallpaperManagerService2.mUserRestorecon.get(i)) {
                                        wallpaperManagerService2.mUserRestorecon.put(i, true);
                                        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda20
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i2 = i;
                                                boolean z = WallpaperManagerService.SHIPPED;
                                                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
                                                timingsTraceAndSlog.traceBegin("Wallpaper_selinux_restorecon-" + i2);
                                                try {
                                                    Iterator it = ((ArrayList) WallpaperUtils.getWallpaperFiles(i2)).iterator();
                                                    while (it.hasNext()) {
                                                        File file = (File) it.next();
                                                        if (file.exists()) {
                                                            SELinux.restorecon(file);
                                                        }
                                                    }
                                                } finally {
                                                    timingsTraceAndSlog.traceEnd();
                                                }
                                            }
                                        });
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {
        }

        public final void onDisplayReady(int i) {
            WallpaperConnection wallpaperConnection;
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            synchronized (wallpaperManagerService.mLock) {
                try {
                    Log.d("WallpaperManagerService", "onDisplayReadyInternal, displayId = " + i);
                    if (i == 2) {
                        Log.addLogString("WallpaperManagerService", "DEX_DISPLAY is added");
                        WallpaperData wallpaperSafeLocked = wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.mCurrentUserId, 9);
                        wallpaperSafeLocked.mWhich |= 8;
                        wallpaperManagerService.bindWallpaperComponentLocked(wallpaperManagerService.mImageWallpaper, true, false, wallpaperSafeLocked, null, null);
                        WallpaperData wallpaperSafeLocked2 = wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.mCurrentUserId, 10);
                        wallpaperManagerService.bindWallpaperComponentLocked(wallpaperManagerService.mImageWallpaper, true, false, wallpaperSafeLocked2, null, null);
                        wallpaperSafeLocked2.mWhich |= 8;
                        return;
                    }
                    if (Rune.VIRTUAL_DISPLAY_WALLPAPER && wallpaperManagerService.isVirtualWallpaperDisplay(i)) {
                        wallpaperManagerService.mActiveVirtualDisplayId = i;
                        return;
                    }
                    WallpaperData wallpaperData = wallpaperManagerService.mLastWallpaper;
                    if (wallpaperData == null) {
                        return;
                    }
                    if (WallpaperManagerService.supportsMultiDisplay(wallpaperData.connection)) {
                        DisplayConnector displayConnectorOrCreate = wallpaperManagerService.mLastWallpaper.connection.getDisplayConnectorOrCreate(i);
                        if (displayConnectorOrCreate == null) {
                            return;
                        }
                        WallpaperData wallpaperData2 = wallpaperManagerService.mLastWallpaper;
                        displayConnectorOrCreate.connectLocked(wallpaperData2.connection, wallpaperData2);
                        return;
                    }
                    WallpaperData wallpaperData3 = wallpaperManagerService.mFallbackWallpaper;
                    if (wallpaperData3 == null || (wallpaperConnection = wallpaperData3.connection) == null) {
                        Slog.w("WallpaperManagerService", "No wallpaper can be added to the new display");
                    } else {
                        DisplayConnector displayConnectorOrCreate2 = wallpaperConnection.getDisplayConnectorOrCreate(i);
                        if (displayConnectorOrCreate2 == null) {
                            return;
                        }
                        WallpaperData wallpaperData4 = wallpaperManagerService.mFallbackWallpaper;
                        displayConnectorOrCreate2.connectLocked(wallpaperData4.connection, wallpaperData4);
                    }
                } finally {
                }
            }
        }

        public final void onScreenTurnedOn(int i) {
            IWallpaperEngine iWallpaperEngine;
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            synchronized (wallpaperManagerService.mLock) {
                for (WallpaperData wallpaperData : wallpaperManagerService.getActiveWallpapers(i)) {
                    Log.d("WallpaperManagerService", "notifyScreenTurnedOn: displayId= " + i + " , target data= " + wallpaperData);
                    if (wallpaperData.connection.containsDisplay(i) && (iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine) != null) {
                        try {
                            iWallpaperEngine.onScreenTurnedOn();
                        } catch (RemoteException e) {
                            Slog.w("WallpaperManagerService", "Failed to notify that the screen turned on", e);
                        }
                    }
                }
            }
        }

        public final void onScreenTurningOn(int i) {
            IWallpaperEngine iWallpaperEngine;
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            synchronized (wallpaperManagerService.mLock) {
                for (WallpaperData wallpaperData : wallpaperManagerService.getActiveWallpapers(i)) {
                    Log.d("WallpaperManagerService", "notifyScreenTurningOn: displayId= " + i + " , target data= " + wallpaperData);
                    if (wallpaperData.connection.containsDisplay(i) && (iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine) != null) {
                        try {
                            iWallpaperEngine.onScreenTurningOn();
                        } catch (RemoteException e) {
                            Slog.w("WallpaperManagerService", "Failed to notify that the screen is turning on", e);
                        }
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {
            super(true);
        }

        public final boolean doPackagesChangedLocked(WallpaperData wallpaperData, boolean z) {
            boolean z2;
            int isPackageDisappearing;
            int isPackageDisappearing2;
            ComponentName componentName = wallpaperData.wallpaperComponent;
            if (componentName == null || !((isPackageDisappearing2 = isPackageDisappearing(componentName.getPackageName())) == 3 || isPackageDisappearing2 == 2)) {
                z2 = false;
            } else {
                z2 = true;
                if (z && !WallpaperManagerService.this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)) {
                    Slog.w("WallpaperManagerService", "Wallpaper uninstalled, removing: " + wallpaperData.wallpaperComponent);
                    if ("com.samsung.android.wallpaper.live".equals(wallpaperData.wallpaperComponent.getPackageName())) {
                        Log.addLogString("WallpaperManagerService", "doPackagesChangedLocked : try to rebind");
                        WallpaperManagerService.this.forceRebindWallpaper(wallpaperData.mSemWallpaperData.mWhich, wallpaperData.userId);
                        return true;
                    }
                    if (wallpaperData.connection != null) {
                        Log.addLogString("WallpaperManagerService", "doPackagesChangedLocked : removeCallbacks");
                        WallpaperManagerService.this.mContext.getMainThreadHandler().removeCallbacks(wallpaperData.connection.mTryToRebindRunnable);
                    }
                    WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperData);
                }
            }
            ComponentName componentName2 = wallpaperData.nextWallpaperComponent;
            if (componentName2 != null && ((isPackageDisappearing = isPackageDisappearing(componentName2.getPackageName())) == 3 || isPackageDisappearing == 2)) {
                wallpaperData.nextWallpaperComponent = null;
            }
            ComponentName componentName3 = wallpaperData.wallpaperComponent;
            if (componentName3 != null && !WallpaperManagerService.this.mImageWallpaper.equals(componentName3) && isPackageModified(wallpaperData.wallpaperComponent.getPackageName())) {
                try {
                    WallpaperManagerService.this.mContext.getPackageManager().getServiceInfo(wallpaperData.wallpaperComponent, 786432);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.w("WallpaperManagerService", "Wallpaper component gone, removing: " + wallpaperData.wallpaperComponent);
                    WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperData);
                }
            }
            ComponentName componentName4 = wallpaperData.nextWallpaperComponent;
            if (componentName4 != null && isPackageModified(componentName4.getPackageName())) {
                try {
                    WallpaperManagerService.this.mContext.getPackageManager().getServiceInfo(wallpaperData.nextWallpaperComponent, 786432);
                } catch (PackageManager.NameNotFoundException unused2) {
                    wallpaperData.nextWallpaperComponent = null;
                }
            }
            return z2;
        }

        public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return false;
                    }
                    boolean z2 = false;
                    for (WallpaperData wallpaperData : WallpaperManagerService.m1041$$Nest$mgetWallpapers(WallpaperManagerService.this)) {
                        z2 |= doPackagesChangedLocked(wallpaperData, z);
                    }
                    return z2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageModified(String str) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (WallpaperData wallpaperData : WallpaperManagerService.m1041$$Nest$mgetWallpapers(WallpaperManagerService.this)) {
                        ComponentName componentName = wallpaperData.wallpaperComponent;
                        if (componentName != null && componentName.getPackageName().equals(str)) {
                            doPackagesChangedLocked(wallpaperData, true);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageUpdateFinished(String str, int i) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (WallpaperData wallpaperData : WallpaperManagerService.m1041$$Nest$mgetWallpapers(WallpaperManagerService.this)) {
                        ComponentName componentName = wallpaperData.wallpaperComponent;
                        if (componentName != null && componentName.getPackageName().equals(str)) {
                            Slog.i("WallpaperManagerService", "Wallpaper " + componentName + " update has finished");
                            wallpaperData.wallpaperUpdating = false;
                            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                            wallpaperManagerService.getClass();
                            wallpaperData.wallpaperComponent = null;
                            wallpaperManagerService.detachWallpaperLocked(wallpaperData);
                            wallpaperData.mBindSource = WallpaperData.BindSource.PACKAGE_UPDATE_FINISHED;
                            if (!WallpaperManagerService.this.bindWallpaperComponentLocked(componentName, false, false, wallpaperData, null, null)) {
                                Slog.w("WallpaperManagerService", "Wallpaper " + componentName + " no longer available; reverting to default");
                                WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperData);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageUpdateStarted(String str, int i) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (WallpaperData wallpaperData : WallpaperManagerService.m1041$$Nest$mgetWallpapers(WallpaperManagerService.this)) {
                        ComponentName componentName = wallpaperData.wallpaperComponent;
                        if (componentName != null && componentName.getPackageName().equals(str)) {
                            Slog.i("WallpaperManagerService", "Wallpaper service " + wallpaperData.wallpaperComponent + " is updating");
                            wallpaperData.wallpaperUpdating = true;
                            if (wallpaperData.connection != null) {
                                FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSomePackagesChanged() {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (WallpaperData wallpaperData : WallpaperManagerService.m1041$$Nest$mgetWallpapers(WallpaperManagerService.this)) {
                        doPackagesChangedLocked(wallpaperData, true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SemCallback {
        public SemCallback() {
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x00f3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.app.SemWallpaperColors calcSemWallpaperColors(int r11, int r12) {
            /*
                Method dump skipped, instructions count: 344
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.SemCallback.calcSemWallpaperColors(int, int):android.app.SemWallpaperColors");
        }

        public final void handleWallpaperBindingTimeout() {
            WallpaperData wallpaperSafeLocked;
            WallpaperData wallpaperData;
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    if (WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopMode() && WallpaperManagerService.this.mSemService.mDesktopMode.isDesktopDualMode()) {
                        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                        wallpaperSafeLocked = wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.mCurrentUserId, 9);
                    } else {
                        WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                        wallpaperSafeLocked = wallpaperManagerService2.getWallpaperSafeLocked(wallpaperManagerService2.mCurrentUserId, 1);
                    }
                    wallpaperData = wallpaperSafeLocked;
                } catch (Throwable th) {
                    throw th;
                }
            }
            WallpaperConnection wallpaperConnection = wallpaperData.connection;
            if (wallpaperConnection != null && wallpaperConnection.mService != null && wallpaperConnection.getDisplayConnectorOrCreate(0).mEngine != null && wallpaperData.connection.isBinderAlive()) {
                Log.d("WallpaperManagerService", "wallpaper binded already!");
                return;
            }
            Log.d("WallpaperManagerService", "trying to bind wallpaperComponent with timeout");
            synchronized (WallpaperManagerService.this.mLock) {
                WallpaperManagerService.this.bindWallpaperComponentLocked(wallpaperData.wallpaperComponent, true, false, wallpaperData, null, null);
            }
            SemWallpaperManagerService semWallpaperManagerService = WallpaperManagerService.this.mSemService;
            semWallpaperManagerService.mDesktopMode.mWallpaperBindingFallbackExecuted = true;
            semWallpaperManagerService.handleWallpaperBindingTimeout(true);
        }

        public final void notifySemWallpaperColors(int i) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    WallpaperData peekWallpaperDataLocked = wallpaperManagerService.peekWallpaperDataLocked(wallpaperManagerService.getCurrentUserId(), i);
                    if (peekWallpaperDataLocked == null) {
                        Log.addLogString("WallpaperManagerService", "notifySemWallpaperColors, wallpaper == null");
                    } else {
                        WallpaperManagerService.this.notifySemColorListeners(0, peekWallpaperDataLocked);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Finally extract failed */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00ef A[Catch: all -> 0x0106, TryCatch #0 {all -> 0x0106, blocks: (B:23:0x0097, B:35:0x00e3, B:37:0x00ef, B:38:0x0108, B:43:0x010a, B:44:0x010d, B:25:0x009b, B:27:0x00a6, B:30:0x00c8, B:31:0x00d0, B:33:0x00d4, B:34:0x00d7), top: B:22:0x0097, inners: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBindWallpaperRequested(int r18, int r19) {
            /*
                Method dump skipped, instructions count: 272
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.SemCallback.onBindWallpaperRequested(int, int):void");
        }

        public final void onDetachWallpaper(WallpaperData wallpaperData) {
            synchronized (WallpaperManagerService.this.mLock) {
                WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                wallpaperManagerService.getClass();
                wallpaperManagerService.detachWallpaperLocked(wallpaperManagerService.getLastWallpaper(wallpaperData.mWhich));
            }
        }

        public final void onLockWallpaperChanged(int i, int i2, Bundle bundle) {
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            WallpaperData wallpaperData = wallpaperManagerService.mLockWallpaperMap.get(i, WhichChecker.getMode(i2));
            if (wallpaperData != null) {
                wallpaperManagerService.notifyLockWallpaperChanged(wallpaperData.mSemWallpaperData.mWpType, i2, bundle);
            }
        }

        public final void onSetWallpaperComponent(WallpaperData wallpaperData) {
            final ComponentName componentName = wallpaperData.wallpaperComponent;
            final int i = wallpaperData.mWhich;
            final int i2 = wallpaperData.userId;
            Bundle bundle = wallpaperData.mSemWallpaperData.mExternalParams;
            if (bundle == null) {
                bundle = new Bundle();
            }
            final Bundle bundle2 = bundle;
            bundle2.putBoolean("isPreloaded", wallpaperData.mSemWallpaperData.mIsPreloaded);
            final String str = wallpaperData.mSemWallpaperData.mLastCallingPackage;
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "onSetWallpaperComponent: userId = ", ", which = ", ", extras = ");
            m.append(bundle2);
            m.append(", componentName = ");
            m.append(componentName);
            m.append(", isPreloaded = ");
            m.append(wallpaperData.mSemWallpaperData.mIsPreloaded);
            m.append(", fromForeground = false, reply = null, callingPackage = ");
            m.append(str);
            Log.d("WallpaperManagerService", m.toString());
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.wallpaper.WallpaperManagerService$SemCallback$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    WallpaperManagerService.SemCallback semCallback = WallpaperManagerService.SemCallback.this;
                    ComponentName componentName2 = componentName;
                    String str2 = str;
                    int i3 = i;
                    int i4 = i2;
                    Bundle bundle3 = bundle2;
                    semCallback.getClass();
                    boolean z = WallpaperManagerService.SHIPPED;
                    return Boolean.valueOf(WallpaperManagerService.this.setWallpaperComponentInternal(componentName2, str2, i3, i4, true, false, bundle3));
                }
            });
        }

        public final WallpaperData onWallpaperDataRequested(int i, int i2) {
            WallpaperData peekWallpaperDataLocked;
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    peekWallpaperDataLocked = WallpaperManagerService.this.peekWallpaperDataLocked(i, i2);
                    if (peekWallpaperDataLocked == null) {
                        peekWallpaperDataLocked = WallpaperManagerService.this.getWallpaperSafeLocked(i, i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return peekWallpaperDataLocked;
        }

        public final void onWallpaperFlagUpdated(int i, int i2) {
            Log.d("WallpaperManagerService", "onWallpaperFlagUpdated: which = " + i2);
            if (WhichChecker.isSystem(i2)) {
                return;
            }
            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
            WallpaperData wallpaperData = wallpaperManagerService.mWallpaperMap.get(i, WhichChecker.getMode(i2));
            WallpaperData wallpaperData2 = wallpaperManagerService.mLockWallpaperMap.get(i, WhichChecker.getMode(i2));
            if (wallpaperData2 != null) {
                wallpaperManagerService.detachWallpaperLocked(wallpaperData2);
            }
            if (wallpaperData != null) {
                wallpaperData.mWhich |= 2;
                wallpaperManagerService.updateEngineFlags(wallpaperData);
            }
        }

        public final void updateDisplayData() {
            if (WallpaperManagerService.this.mWallpaperDisplayHelper == null) {
                Log.w("WallpaperManagerService", "updateDisplayData: display helper is not ready yet");
                return;
            }
            Log.d("WallpaperManagerService", "updateDisplayData");
            synchronized (WallpaperManagerService.this.mLock) {
                WallpaperDisplayHelper wallpaperDisplayHelper = WallpaperManagerService.this.mWallpaperDisplayHelper;
                for (int size = wallpaperDisplayHelper.mDisplayDatas.size() - 1; size >= 0; size--) {
                    WallpaperDisplayHelper.DisplayData displayData = (WallpaperDisplayHelper.DisplayData) wallpaperDisplayHelper.mDisplayDatas.valueAt(size);
                    displayData.mWidth = -1;
                    displayData.mHeight = -1;
                    WallpaperManagerService.this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData, displayData.mDisplayId);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:65:0x019e A[Catch: all -> 0x011a, TryCatch #1 {all -> 0x011a, blocks: (B:42:0x0105, B:46:0x0112, B:48:0x0116, B:49:0x011d, B:51:0x012c, B:53:0x0134, B:55:0x013c, B:56:0x0149, B:58:0x0157, B:59:0x0162, B:61:0x0170, B:63:0x018a, B:65:0x019e, B:67:0x01b8, B:68:0x01c6, B:69:0x0234, B:71:0x023f, B:72:0x0242, B:79:0x01d7, B:81:0x01e9, B:83:0x0211, B:84:0x0225, B:86:0x0256), top: B:41:0x0105 }] */
        /* JADX WARN: Removed duplicated region for block: B:71:0x023f A[Catch: all -> 0x011a, TryCatch #1 {all -> 0x011a, blocks: (B:42:0x0105, B:46:0x0112, B:48:0x0116, B:49:0x011d, B:51:0x012c, B:53:0x0134, B:55:0x013c, B:56:0x0149, B:58:0x0157, B:59:0x0162, B:61:0x0170, B:63:0x018a, B:65:0x019e, B:67:0x01b8, B:68:0x01c6, B:69:0x0234, B:71:0x023f, B:72:0x0242, B:79:0x01d7, B:81:0x01e9, B:83:0x0211, B:84:0x0225, B:86:0x0256), top: B:41:0x0105 }] */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0249  */
        /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x01d5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateEvent(int r17, java.lang.String r18, java.io.File r19, boolean r20, boolean r21) {
            /*
                Method dump skipped, instructions count: 604
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.SemCallback.updateEvent(int, java.lang.String, java.io.File, boolean, boolean):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperConnection extends IWallpaperConnection.Stub implements ServiceConnection {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final int mClientUid;
        public final WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0 mDisconnectRunnable;
        public final SparseArray mDisplayConnector;
        public final WallpaperInfo mInfo;
        public int mLmkLimitRebindRetries;
        public IRemoteCallback mReply;
        public final WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0 mResetRunnable;
        public IWallpaperService mService;
        public final WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0 mTryToRebindRunnable;
        public WallpaperData mWallpaper;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0] */
        public WallpaperConnection(WallpaperInfo wallpaperInfo, WallpaperData wallpaperData, int i) {
            SparseArray sparseArray = new SparseArray();
            this.mDisplayConnector = sparseArray;
            this.mLmkLimitRebindRetries = 3;
            final int i2 = 0;
            this.mResetRunnable = new Runnable(this) { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0
                public final /* synthetic */ WallpaperManagerService.WallpaperConnection f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i3 = i2;
                    WallpaperManagerService.WallpaperConnection wallpaperConnection = this.f$0;
                    switch (i3) {
                        case 0:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                                    if (wallpaperManagerService.mShuttingDown) {
                                        Slog.i("WallpaperManagerService", "Ignoring relaunch timeout during shutdown");
                                        return;
                                    }
                                    WallpaperData wallpaperData2 = wallpaperConnection.mWallpaper;
                                    if (!wallpaperData2.wallpaperUpdating && wallpaperData2.userId == wallpaperManagerService.mCurrentUserId) {
                                        Slog.w("WallpaperManagerService", "Wallpaper reconnect timed out for " + wallpaperConnection.mWallpaper.wallpaperComponent + ", reverting to built-in wallpaper!");
                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                    }
                                    return;
                                } finally {
                                }
                            }
                        case 1:
                            wallpaperConnection.tryToRebind();
                            return;
                        default:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperData wallpaperData3 = wallpaperConnection.mWallpaper;
                                    if (wallpaperConnection == wallpaperData3.connection) {
                                        ComponentName componentName = wallpaperData3.wallpaperComponent;
                                        Log.d("WallpaperManagerService", "Wallpaper onServiceDisconnected : " + componentName);
                                        WallpaperData wallpaperData4 = wallpaperConnection.mWallpaper;
                                        if (!wallpaperData4.wallpaperUpdating) {
                                            int i4 = wallpaperData4.userId;
                                            WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                                            if (i4 == wallpaperManagerService2.mCurrentUserId && componentName != null && !Objects.equals(wallpaperManagerService2.mSemService.getDefaultPreloadedLiveWallpaperComponentName(wallpaperData4.mSemWallpaperData.mWhich), componentName) && !Objects.equals(WallpaperManagerService.this.mDefaultWallpaperComponent, componentName) && !Objects.equals(WallpaperManagerService.this.mImageWallpaper, componentName)) {
                                                int i5 = 0;
                                                List<ApplicationExitInfo> historicalProcessExitReasons = WallpaperManagerService.this.mActivityManager.getHistoricalProcessExitReasons(componentName.getPackageName(), 0, 1);
                                                if (historicalProcessExitReasons != null && !historicalProcessExitReasons.isEmpty()) {
                                                    i5 = historicalProcessExitReasons.get(0).getReason();
                                                }
                                                Slog.d("WallpaperManagerService", "exitReason: " + i5);
                                                if (i5 == 3) {
                                                    WallpaperManagerService.this.mActivityManager.getMemoryInfo(new ActivityManager.MemoryInfo());
                                                    if ((r1.availMem / r1.totalMem) * 100.0d < 10.0d) {
                                                        Slog.i("WallpaperManagerService", "Rebind is delayed due to lmk");
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                        wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                    } else {
                                                        int i6 = wallpaperConnection.mLmkLimitRebindRetries;
                                                        if (i6 <= 0) {
                                                            Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper due to lmk!");
                                                            WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                            wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                            return;
                                                        }
                                                        wallpaperConnection.mLmkLimitRebindRetries = i6 - 1;
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                    }
                                                } else {
                                                    long j = wallpaperConnection.mWallpaper.lastDiedTime;
                                                    if (j == 0 || j + 10000 <= SystemClock.uptimeMillis()) {
                                                        wallpaperConnection.mWallpaper.lastDiedTime = SystemClock.uptimeMillis();
                                                        wallpaperConnection.tryToRebind();
                                                    } else {
                                                        Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
                                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Slog.i("WallpaperManagerService", "Wallpaper changed during disconnect tracking; ignoring");
                                    }
                                    return;
                                } finally {
                                }
                            }
                    }
                }
            };
            final int i3 = 1;
            this.mTryToRebindRunnable = new Runnable(this) { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0
                public final /* synthetic */ WallpaperManagerService.WallpaperConnection f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i32 = i3;
                    WallpaperManagerService.WallpaperConnection wallpaperConnection = this.f$0;
                    switch (i32) {
                        case 0:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                                    if (wallpaperManagerService.mShuttingDown) {
                                        Slog.i("WallpaperManagerService", "Ignoring relaunch timeout during shutdown");
                                        return;
                                    }
                                    WallpaperData wallpaperData2 = wallpaperConnection.mWallpaper;
                                    if (!wallpaperData2.wallpaperUpdating && wallpaperData2.userId == wallpaperManagerService.mCurrentUserId) {
                                        Slog.w("WallpaperManagerService", "Wallpaper reconnect timed out for " + wallpaperConnection.mWallpaper.wallpaperComponent + ", reverting to built-in wallpaper!");
                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                    }
                                    return;
                                } finally {
                                }
                            }
                        case 1:
                            wallpaperConnection.tryToRebind();
                            return;
                        default:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperData wallpaperData3 = wallpaperConnection.mWallpaper;
                                    if (wallpaperConnection == wallpaperData3.connection) {
                                        ComponentName componentName = wallpaperData3.wallpaperComponent;
                                        Log.d("WallpaperManagerService", "Wallpaper onServiceDisconnected : " + componentName);
                                        WallpaperData wallpaperData4 = wallpaperConnection.mWallpaper;
                                        if (!wallpaperData4.wallpaperUpdating) {
                                            int i4 = wallpaperData4.userId;
                                            WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                                            if (i4 == wallpaperManagerService2.mCurrentUserId && componentName != null && !Objects.equals(wallpaperManagerService2.mSemService.getDefaultPreloadedLiveWallpaperComponentName(wallpaperData4.mSemWallpaperData.mWhich), componentName) && !Objects.equals(WallpaperManagerService.this.mDefaultWallpaperComponent, componentName) && !Objects.equals(WallpaperManagerService.this.mImageWallpaper, componentName)) {
                                                int i5 = 0;
                                                List<ApplicationExitInfo> historicalProcessExitReasons = WallpaperManagerService.this.mActivityManager.getHistoricalProcessExitReasons(componentName.getPackageName(), 0, 1);
                                                if (historicalProcessExitReasons != null && !historicalProcessExitReasons.isEmpty()) {
                                                    i5 = historicalProcessExitReasons.get(0).getReason();
                                                }
                                                Slog.d("WallpaperManagerService", "exitReason: " + i5);
                                                if (i5 == 3) {
                                                    WallpaperManagerService.this.mActivityManager.getMemoryInfo(new ActivityManager.MemoryInfo());
                                                    if ((r1.availMem / r1.totalMem) * 100.0d < 10.0d) {
                                                        Slog.i("WallpaperManagerService", "Rebind is delayed due to lmk");
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                        wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                    } else {
                                                        int i6 = wallpaperConnection.mLmkLimitRebindRetries;
                                                        if (i6 <= 0) {
                                                            Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper due to lmk!");
                                                            WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                            wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                            return;
                                                        }
                                                        wallpaperConnection.mLmkLimitRebindRetries = i6 - 1;
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                    }
                                                } else {
                                                    long j = wallpaperConnection.mWallpaper.lastDiedTime;
                                                    if (j == 0 || j + 10000 <= SystemClock.uptimeMillis()) {
                                                        wallpaperConnection.mWallpaper.lastDiedTime = SystemClock.uptimeMillis();
                                                        wallpaperConnection.tryToRebind();
                                                    } else {
                                                        Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
                                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Slog.i("WallpaperManagerService", "Wallpaper changed during disconnect tracking; ignoring");
                                    }
                                    return;
                                } finally {
                                }
                            }
                    }
                }
            };
            final int i4 = 2;
            this.mDisconnectRunnable = new Runnable(this) { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0
                public final /* synthetic */ WallpaperManagerService.WallpaperConnection f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i32 = i4;
                    WallpaperManagerService.WallpaperConnection wallpaperConnection = this.f$0;
                    switch (i32) {
                        case 0:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                                    if (wallpaperManagerService.mShuttingDown) {
                                        Slog.i("WallpaperManagerService", "Ignoring relaunch timeout during shutdown");
                                        return;
                                    }
                                    WallpaperData wallpaperData2 = wallpaperConnection.mWallpaper;
                                    if (!wallpaperData2.wallpaperUpdating && wallpaperData2.userId == wallpaperManagerService.mCurrentUserId) {
                                        Slog.w("WallpaperManagerService", "Wallpaper reconnect timed out for " + wallpaperConnection.mWallpaper.wallpaperComponent + ", reverting to built-in wallpaper!");
                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                    }
                                    return;
                                } finally {
                                }
                            }
                        case 1:
                            wallpaperConnection.tryToRebind();
                            return;
                        default:
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperData wallpaperData3 = wallpaperConnection.mWallpaper;
                                    if (wallpaperConnection == wallpaperData3.connection) {
                                        ComponentName componentName = wallpaperData3.wallpaperComponent;
                                        Log.d("WallpaperManagerService", "Wallpaper onServiceDisconnected : " + componentName);
                                        WallpaperData wallpaperData4 = wallpaperConnection.mWallpaper;
                                        if (!wallpaperData4.wallpaperUpdating) {
                                            int i42 = wallpaperData4.userId;
                                            WallpaperManagerService wallpaperManagerService2 = WallpaperManagerService.this;
                                            if (i42 == wallpaperManagerService2.mCurrentUserId && componentName != null && !Objects.equals(wallpaperManagerService2.mSemService.getDefaultPreloadedLiveWallpaperComponentName(wallpaperData4.mSemWallpaperData.mWhich), componentName) && !Objects.equals(WallpaperManagerService.this.mDefaultWallpaperComponent, componentName) && !Objects.equals(WallpaperManagerService.this.mImageWallpaper, componentName)) {
                                                int i5 = 0;
                                                List<ApplicationExitInfo> historicalProcessExitReasons = WallpaperManagerService.this.mActivityManager.getHistoricalProcessExitReasons(componentName.getPackageName(), 0, 1);
                                                if (historicalProcessExitReasons != null && !historicalProcessExitReasons.isEmpty()) {
                                                    i5 = historicalProcessExitReasons.get(0).getReason();
                                                }
                                                Slog.d("WallpaperManagerService", "exitReason: " + i5);
                                                if (i5 == 3) {
                                                    WallpaperManagerService.this.mActivityManager.getMemoryInfo(new ActivityManager.MemoryInfo());
                                                    if ((r1.availMem / r1.totalMem) * 100.0d < 10.0d) {
                                                        Slog.i("WallpaperManagerService", "Rebind is delayed due to lmk");
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                        wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                    } else {
                                                        int i6 = wallpaperConnection.mLmkLimitRebindRetries;
                                                        if (i6 <= 0) {
                                                            Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper due to lmk!");
                                                            WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                            wallpaperConnection.mLmkLimitRebindRetries = 3;
                                                            return;
                                                        }
                                                        wallpaperConnection.mLmkLimitRebindRetries = i6 - 1;
                                                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(wallpaperConnection.mTryToRebindRunnable, 5000L);
                                                    }
                                                } else {
                                                    long j = wallpaperConnection.mWallpaper.lastDiedTime;
                                                    if (j == 0 || j + 10000 <= SystemClock.uptimeMillis()) {
                                                        wallpaperConnection.mWallpaper.lastDiedTime = SystemClock.uptimeMillis();
                                                        wallpaperConnection.tryToRebind();
                                                    } else {
                                                        Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
                                                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(wallpaperConnection.mWallpaper);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Slog.i("WallpaperManagerService", "Wallpaper changed during disconnect tracking; ignoring");
                                    }
                                    return;
                                } finally {
                                }
                            }
                    }
                }
            };
            this.mInfo = wallpaperInfo;
            this.mWallpaper = wallpaperData;
            this.mClientUid = i;
            if (wallpaperData.equals(WallpaperManagerService.this.mFallbackWallpaper)) {
                return;
            }
            if (WallpaperManagerService.supportsMultiDisplay(this)) {
                appendConnectorWithCondition(new Predicate() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        WallpaperManagerService.WallpaperConnection wallpaperConnection = WallpaperManagerService.WallpaperConnection.this;
                        return WallpaperManagerService.this.mWallpaperDisplayHelper.isUsableDisplay((Display) obj, wallpaperConnection.mClientUid);
                    }
                });
            } else {
                sparseArray.append(0, WallpaperManagerService.this.new DisplayConnector(0));
            }
        }

        public final void appendConnectorInternal(int i) {
            if (this.mDisplayConnector.contains(i)) {
                return;
            }
            Log.d("WallpaperManagerService", "appendConnectorInternal, displayId " + i);
            this.mDisplayConnector.append(i, WallpaperManagerService.this.new DisplayConnector(i));
        }

        public final void appendConnectorWithCondition(Predicate predicate) {
            int i;
            synchronized (WallpaperManagerService.this.mLock) {
                i = this.mWallpaper.mWhich;
            }
            int i2 = 0;
            if (WhichChecker.isVirtualDisplay(i)) {
                Display[] displays = WallpaperManagerService.this.mWallpaperDisplayHelper.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
                if (displays.length > 0) {
                    Display display = displays[0];
                    WallpaperManagerService.this.mActiveVirtualDisplayId = display.getDisplayId();
                    appendConnectorInternal(WallpaperManagerService.this.mActiveVirtualDisplayId);
                    return;
                }
                return;
            }
            if (WhichChecker.isDex(i)) {
                Display[] displays2 = WallpaperManagerService.this.mWallpaperDisplayHelper.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.DESKTOP");
                int length = displays2.length;
                while (i2 < length) {
                    Display display2 = displays2[i2];
                    if (predicate.test(display2)) {
                        appendConnectorInternal(display2.getDisplayId());
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
            if (!WhichChecker.isPhone(i)) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "appendConnectorWithCondition: unexpected which. which=", "WallpaperManagerService");
                return;
            }
            Display[] displays3 = WallpaperManagerService.this.mWallpaperDisplayHelper.mDisplayManager.getDisplays();
            int length2 = displays3.length;
            while (i2 < length2) {
                Display display3 = displays3[i2];
                if (predicate.test(display3)) {
                    appendConnectorInternal(display3.getDisplayId());
                }
                i2++;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0065 A[Catch: all -> 0x0028, TryCatch #2 {, blocks: (B:4:0x0005, B:6:0x000b, B:8:0x0014, B:12:0x001c, B:15:0x002c, B:17:0x0033, B:19:0x0039, B:22:0x004f, B:23:0x005d, B:25:0x0065, B:29:0x008c, B:30:0x0070, B:35:0x007a, B:37:0x0080, B:40:0x008f, B:54:0x0095, B:42:0x00a3, B:49:0x00ac, B:45:0x00ba, B:52:0x00b3, B:57:0x009c, B:58:0x0042, B:62:0x0048, B:63:0x00bc, B:64:0x00c3), top: B:3:0x0005, inners: #0, #1, #3, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x0095 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void attachEngine(android.service.wallpaper.IWallpaperEngine r9, int r10) {
            /*
                r8 = this;
                com.android.server.wallpaper.WallpaperManagerService r0 = com.android.server.wallpaper.WallpaperManagerService.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                com.android.server.wallpaper.WallpaperManagerService$DisplayConnector r1 = r8.getDisplayConnectorOrCreate(r10)     // Catch: java.lang.Throwable -> L28
                if (r1 == 0) goto Lbc
                r1.mEngine = r9     // Catch: java.lang.Throwable -> L28
                r1.ensureStatusHandled()     // Catch: java.lang.Throwable -> L28
                android.app.WallpaperInfo r9 = r8.mInfo     // Catch: java.lang.Throwable -> L28
                if (r9 == 0) goto L33
                boolean r9 = r9.supportsAmbientMode()     // Catch: java.lang.Throwable -> L28
                if (r9 == 0) goto L33
                if (r10 != 0) goto L33
                android.service.wallpaper.IWallpaperEngine r9 = r1.mEngine     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L2b
                com.android.server.wallpaper.WallpaperManagerService r2 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L2b
                boolean r2 = r2.mInAmbientMode     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L2b
                r3 = 0
                r9.setInAmbientMode(r2, r3)     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L2b
                goto L33
            L28:
                r8 = move-exception
                goto Lc4
            L2b:
                r9 = move-exception
                java.lang.String r2 = "WallpaperManagerService"
                java.lang.String r3 = "Failed to set ambient mode state"
                android.util.Slog.w(r2, r3, r9)     // Catch: java.lang.Throwable -> L28
            L33:
                boolean r9 = com.android.window.flags.Flags.offloadColorExtraction()     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L40
                if (r9 == 0) goto L42
                com.android.server.wallpaper.WallpaperData r9 = r8.mWallpaper     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L40
                android.app.WallpaperColors r9 = r9.primaryColors     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L40
                if (r9 != 0) goto L4f
                goto L42
            L40:
                r9 = move-exception
                goto L48
            L42:
                android.service.wallpaper.IWallpaperEngine r9 = r1.mEngine     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L40
                r9.requestWallpaperColors()     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L40
                goto L4f
            L48:
                java.lang.String r2 = "WallpaperManagerService"
                java.lang.String r3 = "Failed to request wallpaper colors"
                android.util.Slog.w(r2, r3, r9)     // Catch: java.lang.Throwable -> L28
            L4f:
                com.android.server.wallpaper.WallpaperManagerService r9 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L28
                com.android.server.wallpaper.LocalColorRepository r9 = r9.mLocalColorRepo     // Catch: java.lang.Throwable -> L28
                r9.getClass()     // Catch: java.lang.Throwable -> L28
                java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L28
                r2.<init>()     // Catch: java.lang.Throwable -> L28
                r3 = 0
                r4 = r3
            L5d:
                android.util.ArrayMap r5 = r9.mLocalColorAreas     // Catch: java.lang.Throwable -> L28
                int r5 = r5.size()     // Catch: java.lang.Throwable -> L28
                if (r4 >= r5) goto L8f
                android.util.ArrayMap r5 = r9.mLocalColorAreas     // Catch: java.lang.Throwable -> L28
                java.lang.Object r5 = r5.valueAt(r4)     // Catch: java.lang.Throwable -> L28
                android.util.SparseArray r5 = (android.util.SparseArray) r5     // Catch: java.lang.Throwable -> L28
                if (r5 != 0) goto L70
                goto L8c
            L70:
                java.lang.Object r5 = r5.get(r10)     // Catch: java.lang.Throwable -> L28
                android.util.ArraySet r5 = (android.util.ArraySet) r5     // Catch: java.lang.Throwable -> L28
                if (r5 != 0) goto L79
                goto L8c
            L79:
                r6 = r3
            L7a:
                int r7 = r5.size()     // Catch: java.lang.Throwable -> L28
                if (r6 >= r7) goto L8c
                java.lang.Object r7 = r5.valueAt(r6)     // Catch: java.lang.Throwable -> L28
                android.graphics.RectF r7 = (android.graphics.RectF) r7     // Catch: java.lang.Throwable -> L28
                r2.add(r7)     // Catch: java.lang.Throwable -> L28
                int r6 = r6 + 1
                goto L7a
            L8c:
                int r4 = r4 + 1
                goto L5d
            L8f:
                int r9 = r2.size()     // Catch: java.lang.Throwable -> L28
                if (r9 == 0) goto La3
                android.service.wallpaper.IWallpaperEngine r9 = r1.mEngine     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L9b
                r9.addLocalColorsAreas(r2)     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> L9b
                goto La3
            L9b:
                r9 = move-exception
                java.lang.String r10 = "WallpaperManagerService"
                java.lang.String r2 = "Failed to register local colors areas"
                android.util.Slog.w(r10, r2, r9)     // Catch: java.lang.Throwable -> L28
            La3:
                com.android.server.wallpaper.WallpaperData r8 = r8.mWallpaper     // Catch: java.lang.Throwable -> L28
                float r8 = r8.mWallpaperDimAmount     // Catch: java.lang.Throwable -> L28
                r9 = 0
                int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                if (r9 == 0) goto Lba
                android.service.wallpaper.IWallpaperEngine r9 = r1.mEngine     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> Lb2
                r9.applyDimming(r8)     // Catch: java.lang.Throwable -> L28 android.os.RemoteException -> Lb2
                goto Lba
            Lb2:
                r8 = move-exception
                java.lang.String r9 = "WallpaperManagerService"
                java.lang.String r10 = "Failed to dim wallpaper"
                android.util.Slog.w(r9, r10, r8)     // Catch: java.lang.Throwable -> L28
            Lba:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                return
            Lbc:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L28
                java.lang.String r9 = "Connector has already been destroyed"
                r8.<init>(r9)     // Catch: java.lang.Throwable -> L28
                throw r8     // Catch: java.lang.Throwable -> L28
            Lc4:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.attachEngine(android.service.wallpaper.IWallpaperEngine, int):void");
        }

        public final boolean containsDisplay(int i) {
            return this.mDisplayConnector.get(i) != null;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x003b A[Catch: all -> 0x0016, TryCatch #1 {all -> 0x0016, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x0031, B:11:0x003b, B:13:0x0057, B:14:0x005f, B:16:0x006a, B:20:0x0080, B:21:0x008f, B:25:0x0095, B:26:0x0098, B:30:0x0099, B:32:0x0019, B:19:0x007b, B:29:0x0087), top: B:3:0x0005, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x006a A[Catch: all -> 0x0016, TRY_LEAVE, TryCatch #1 {all -> 0x0016, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x0031, B:11:0x003b, B:13:0x0057, B:14:0x005f, B:16:0x006a, B:20:0x0080, B:21:0x008f, B:25:0x0095, B:26:0x0098, B:30:0x0099, B:32:0x0019, B:19:0x007b, B:29:0x0087), top: B:3:0x0005, inners: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void engineShown(android.service.wallpaper.IWallpaperEngine r8) {
            /*
                r7 = this;
                com.android.server.wallpaper.WallpaperManagerService r8 = com.android.server.wallpaper.WallpaperManagerService.this
                java.lang.Object r8 = r8.mLock
                monitor-enter(r8)
                boolean r0 = com.samsung.android.wallpaper.Rune.DESKTOP_STANDALONE_MODE_WALLPAPER     // Catch: java.lang.Throwable -> L16
                if (r0 != 0) goto L19
                com.android.server.wallpaper.WallpaperManagerService r0 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.SemWallpaperManagerService r0 = r0.mSemService     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.DesktopMode r0 = r0.mDesktopMode     // Catch: java.lang.Throwable -> L16
                boolean r0 = r0.isDesktopDualMode()     // Catch: java.lang.Throwable -> L16
                if (r0 == 0) goto L31
                goto L19
            L16:
                r7 = move-exception
                goto L9b
            L19:
                com.android.server.wallpaper.WallpaperManagerService r0 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.SemWallpaperManagerService r0 = r0.mSemService     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.DesktopMode r0 = r0.mDesktopMode     // Catch: java.lang.Throwable -> L16
                com.android.server.wallpaper.WallpaperData r1 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.SemWallpaperData r1 = r1.mSemWallpaperData     // Catch: java.lang.Throwable -> L16
                int r1 = r1.mWhich     // Catch: java.lang.Throwable -> L16
                android.os.Handler r2 = r0.mHandler     // Catch: java.lang.Throwable -> L16
                com.samsung.server.wallpaper.DesktopMode$2 r3 = new com.samsung.server.wallpaper.DesktopMode$2     // Catch: java.lang.Throwable -> L16
                r3.<init>()     // Catch: java.lang.Throwable -> L16
                r0 = 1500(0x5dc, double:7.41E-321)
                r2.postDelayed(r3, r0)     // Catch: java.lang.Throwable -> L16
            L31:
                com.android.server.wallpaper.WallpaperData r0 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                int r0 = r0.mWhich     // Catch: java.lang.Throwable -> L16
                boolean r0 = com.samsung.android.wallpaper.utils.WhichChecker.isSystemAndLock(r0)     // Catch: java.lang.Throwable -> L16
                if (r0 == 0) goto L5f
                com.android.server.wallpaper.WallpaperData r0 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                int r0 = r0.mWhich     // Catch: java.lang.Throwable -> L16
                int r0 = com.samsung.android.wallpaper.utils.WhichChecker.getMode(r0)     // Catch: java.lang.Throwable -> L16
                com.android.server.wallpaper.WallpaperManagerService r1 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L16
                com.android.server.wallpaper.WallpaperData r2 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                int r2 = r2.userId     // Catch: java.lang.Throwable -> L16
                r0 = r0 | 1
                com.android.server.wallpaper.WallpaperData r0 = r1.getWallpaperSafeLocked(r2, r0)     // Catch: java.lang.Throwable -> L16
                int r0 = r0.mWhich     // Catch: java.lang.Throwable -> L16
                boolean r0 = com.samsung.android.wallpaper.utils.WhichChecker.containsLock(r0)     // Catch: java.lang.Throwable -> L16
                if (r0 != 0) goto L5f
                com.android.server.wallpaper.WallpaperData r0 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                int r1 = r0.mWhich     // Catch: java.lang.Throwable -> L16
                r1 = r1 & (-3)
                r0.mWhich = r1     // Catch: java.lang.Throwable -> L16
            L5f:
                com.android.server.wallpaper.WallpaperManagerService r0 = com.android.server.wallpaper.WallpaperManagerService.this     // Catch: java.lang.Throwable -> L16
                com.android.server.wallpaper.WallpaperData r1 = r7.mWallpaper     // Catch: java.lang.Throwable -> L16
                r0.updateEngineFlags(r1)     // Catch: java.lang.Throwable -> L16
                android.os.IRemoteCallback r0 = r7.mReply     // Catch: java.lang.Throwable -> L16
                if (r0 == 0) goto L99
                com.android.server.utils.TimingsTraceAndSlog r0 = new com.android.server.utils.TimingsTraceAndSlog     // Catch: java.lang.Throwable -> L16
                java.lang.String r1 = "WallpaperManagerService"
                r0.<init>(r1)     // Catch: java.lang.Throwable -> L16
                java.lang.String r1 = "WPMS.mReply.sendResult"
                r0.traceBegin(r1)     // Catch: java.lang.Throwable -> L16
                long r1 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L16
                r3 = 0
                android.os.IRemoteCallback r4 = r7.mReply     // Catch: java.lang.Throwable -> L84 android.os.RemoteException -> L86
                r4.sendResult(r3)     // Catch: java.lang.Throwable -> L84 android.os.RemoteException -> L86
            L80:
                android.os.Binder.restoreCallingIdentity(r1)     // Catch: java.lang.Throwable -> L16
                goto L8f
            L84:
                r7 = move-exception
                goto L95
            L86:
                r4 = move-exception
                java.lang.String r5 = "WallpaperManagerService"
                java.lang.String r6 = "Failed to send callback!"
                android.util.Slog.d(r5, r6, r4)     // Catch: java.lang.Throwable -> L84
                goto L80
            L8f:
                r0.traceEnd()     // Catch: java.lang.Throwable -> L16
                r7.mReply = r3     // Catch: java.lang.Throwable -> L16
                goto L99
            L95:
                android.os.Binder.restoreCallingIdentity(r1)     // Catch: java.lang.Throwable -> L16
                throw r7     // Catch: java.lang.Throwable -> L16
            L99:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L16
                return
            L9b:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L16
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.engineShown(android.service.wallpaper.IWallpaperEngine):void");
        }

        public final void forEachDisplayConnector(Consumer consumer) {
            for (int size = this.mDisplayConnector.size() - 1; size >= 0; size--) {
                consumer.accept((DisplayConnector) this.mDisplayConnector.valueAt(size));
            }
        }

        public final DisplayConnector getDisplayConnectorOrCreate(int i) {
            DisplayConnector displayConnector = (DisplayConnector) this.mDisplayConnector.get(i);
            if (displayConnector != null) {
                return displayConnector;
            }
            WallpaperDisplayHelper wallpaperDisplayHelper = WallpaperManagerService.this.mWallpaperDisplayHelper;
            if (!wallpaperDisplayHelper.isUsableDisplay(wallpaperDisplayHelper.mDisplayManager.getDisplay(i), this.mClientUid)) {
                return displayConnector;
            }
            DisplayConnector displayConnector2 = WallpaperManagerService.this.new DisplayConnector(i);
            this.mDisplayConnector.append(i, displayConnector2);
            return displayConnector2;
        }

        public final void onLocalWallpaperColorsChanged(final RectF rectF, final WallpaperColors wallpaperColors, final int i) {
            forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WallpaperManagerService.WallpaperConnection wallpaperConnection = WallpaperManagerService.WallpaperConnection.this;
                    final RectF rectF2 = rectF;
                    WallpaperColors wallpaperColors2 = wallpaperColors;
                    final int i2 = i;
                    wallpaperConnection.getClass();
                    final WallpaperManagerService$$ExternalSyntheticLambda4 wallpaperManagerService$$ExternalSyntheticLambda4 = new WallpaperManagerService$$ExternalSyntheticLambda4(3, rectF2, wallpaperColors2);
                    synchronized (WallpaperManagerService.this.mLock) {
                        final LocalColorRepository localColorRepository = WallpaperManagerService.this.mLocalColorRepo;
                        localColorRepository.mCallbacks.broadcast(new Consumer() { // from class: com.android.server.wallpaper.LocalColorRepository$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                ArraySet arraySet;
                                LocalColorRepository localColorRepository2 = LocalColorRepository.this;
                                int i3 = i2;
                                RectF rectF3 = rectF2;
                                Consumer consumer = wallpaperManagerService$$ExternalSyntheticLambda4;
                                ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer = (ILocalWallpaperColorConsumer) obj2;
                                localColorRepository2.getClass();
                                SparseArray sparseArray = (SparseArray) localColorRepository2.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder());
                                if (sparseArray == null || (arraySet = (ArraySet) sparseArray.get(i3)) == null || !arraySet.contains(rectF3)) {
                                    return;
                                }
                                consumer.accept(iLocalWallpaperColorConsumer);
                            }
                        });
                    }
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x012a A[Catch: all -> 0x00b9, TRY_LEAVE, TryCatch #1 {all -> 0x00b9, blocks: (B:4:0x0021, B:6:0x002c, B:7:0x002e, B:9:0x0034, B:11:0x0087, B:15:0x00bd, B:18:0x00f9, B:20:0x012a, B:22:0x0131, B:24:0x0135, B:27:0x013f, B:29:0x0145, B:33:0x014f, B:34:0x00ec, B:35:0x008e, B:37:0x00ae, B:39:0x0152), top: B:3:0x0021, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0135 A[Catch: all -> 0x00b9, RemoteException -> 0x0143, TryCatch #0 {RemoteException -> 0x0143, blocks: (B:22:0x0131, B:24:0x0135, B:27:0x013f, B:29:0x0145), top: B:21:0x0131, outer: #1 }] */
        @Override // android.content.ServiceConnection
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onServiceConnected(android.content.ComponentName r8, android.os.IBinder r9) {
            /*
                Method dump skipped, instructions count: 345
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    Slog.w("WallpaperManagerService", "Wallpaper service gone: " + componentName);
                    if (!Objects.equals(componentName, this.mWallpaper.wallpaperComponent)) {
                        Slog.e("WallpaperManagerService", "Does not match expected wallpaper component " + this.mWallpaper.wallpaperComponent);
                    }
                    this.mService = null;
                    forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda28(1));
                    WallpaperData wallpaperData = this.mWallpaper;
                    if (wallpaperData.connection == this && !wallpaperData.wallpaperUpdating) {
                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mDisconnectRunnable, 1000L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    boolean equals = WallpaperManagerService.this.mImageWallpaper.equals(this.mWallpaper.wallpaperComponent);
                    if (!equals || (Flags.offloadColorExtraction() && wallpaperColors != null)) {
                        this.mWallpaper.primaryColors = wallpaperColors;
                        if (Flags.offloadColorExtraction() && equals) {
                            WallpaperManagerService.this.saveSettingsLocked(this.mWallpaper.userId);
                        }
                        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                        WallpaperData wallpaperData = this.mWallpaper;
                        wallpaperManagerService.getClass();
                        wallpaperManagerService.notifyWallpaperColorsChangedOnDisplay(i, wallpaperData.mWhich, wallpaperData);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void scheduleTimeoutLocked() {
            Handler handler = FgThread.getHandler();
            handler.removeCallbacks(this.mResetRunnable);
            handler.postDelayed(this.mResetRunnable, 10000L);
            Slog.i("WallpaperManagerService", "Started wallpaper reconnect timeout for " + this.mWallpaper.wallpaperComponent);
        }

        public final ParcelFileDescriptor setWallpaper(String str) {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    WallpaperData wallpaperData = this.mWallpaper;
                    if (wallpaperData.connection != this) {
                        return null;
                    }
                    WallpaperManagerService.this.getClass();
                    return WallpaperManagerService.updateWallpaperBitmapLocked(str, wallpaperData, null);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void tryToRebind() {
            synchronized (WallpaperManagerService.this.mLock) {
                try {
                    WallpaperData wallpaperData = this.mWallpaper;
                    if (wallpaperData.wallpaperUpdating) {
                        return;
                    }
                    ComponentName componentName = wallpaperData.wallpaperComponent;
                    wallpaperData.mBindSource = WallpaperData.BindSource.CONNECTION_TRY_TO_REBIND;
                    if (WallpaperManagerService.this.bindWallpaperComponentLocked(componentName, true, false, wallpaperData, null, null)) {
                        this.mWallpaper.connection.scheduleTimeoutLocked();
                    } else if (SystemClock.uptimeMillis() - this.mWallpaper.lastDiedTime < 10000) {
                        Slog.w("WallpaperManagerService", "Rebind fail! Try again later");
                        WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mTryToRebindRunnable, 1000L);
                    } else {
                        Slog.w("WallpaperManagerService", "Reverting to built-in wallpaper!");
                        WallpaperManagerService.this.mSemService.semClearWallpaperLocked(this.mWallpaper);
                        String flattenToString = componentName.flattenToString();
                        EventLog.writeEvent(33000, flattenToString.substring(0, Math.min(flattenToString.length(), 128)));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperManagerCallbackClient implements IBinder.DeathRecipient {
        public IWallpaperManagerCallback mCb;

        public WallpaperManagerCallbackClient(IWallpaperManagerCallback iWallpaperManagerCallback) {
            Slog.d("WallpaperManagerService", "WallpaperManagerCallbackClient " + iWallpaperManagerCallback);
            if (iWallpaperManagerCallback != null) {
                try {
                    iWallpaperManagerCallback.asBinder().linkToDeath(this, 0);
                } catch (Exception e) {
                    Log.e("WallpaperManagerService", "exception " + e);
                    iWallpaperManagerCallback = null;
                }
            }
            this.mCb = iWallpaperManagerCallback;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (WallpaperManagerService.this.mLock) {
                Slog.d("WallpaperManagerService", "binderDied " + this.mCb);
                WallpaperManagerService.this.mKeyguardListenerList.remove(this.mCb);
                WallpaperManagerService.this.mKeyguardListenerClientList.remove(this);
            }
            this.mCb = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperObserver {
        public final Object mSemObserver;
        public final int mUserId;
        public final Object mWallpaper;
        public final Object this$0;

        public WallpaperObserver(WallpaperManagerService wallpaperManagerService, WallpaperData wallpaperData) {
            this.this$0 = wallpaperManagerService;
            int i = wallpaperData.userId;
            this.mUserId = i;
            File userSystemDirectory = Environment.getUserSystemDirectory(i);
            this.mWallpaper = wallpaperData;
            new File(userSystemDirectory, "wallpaper_orig");
            new File(userSystemDirectory, "wallpaper_lock_orig");
            this.mSemObserver = new SemWallpaperManagerService.SemWallpaperObserver(userSystemDirectory, WallpaperUtils.getWallpaperLockDir(wallpaperData.userId), wallpaperManagerService.new SemCallback());
        }

        public WallpaperObserver(WallpaperManagerService wallpaperManagerService, WallpaperData wallpaperData, int i) {
            this.this$0 = wallpaperManagerService;
            this.mWallpaper = wallpaperData;
            this.mSemObserver = new WallpaperData(wallpaperManagerService.mWallpaperMap.get(wallpaperData.userId, i));
            this.mUserId = i;
            HermesService$3$$ExternalSyntheticOutline0.m(i, "WallpaperDestinationChangeHandler: mode = ", "WallpaperManagerService");
        }

        public WallpaperObserver(Object obj, int i, SemWallpaperManagerService semWallpaperManagerService) {
            this.mWallpaper = new SparseArray();
            this.mSemObserver = obj;
            this.mUserId = i;
            this.this$0 = semWallpaperManagerService;
        }

        public static void print(PrintWriter printWriter, WallpaperData wallpaperData, int i) {
            Bitmap decodeFile;
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
            printWriter.print("  mSystemWasBoth=");
            printWriter.println(wallpaperData.mSystemWasBoth);
            printWriter.print("  mAllowBackup=");
            printWriter.println(wallpaperData.allowBackup);
            printWriter.print("  getWallpaperFile()=");
            printWriter.println(wallpaperData.getWallpaperFile(0) == null ? "null" : wallpaperData.getWallpaperFile(0).getAbsolutePath());
            if (wallpaperData.getWallpaperFile(0) != null && (decodeFile = BitmapFactory.decodeFile(wallpaperData.getWallpaperFile(0).getAbsolutePath())) != null) {
                printWriter.print("  file width=");
                printWriter.println(decodeFile.getWidth());
                printWriter.print("  file height=");
                printWriter.println(decodeFile.getHeight());
                decodeFile.recycle();
            }
            printWriter.print("  getCropFile()=");
            wallpaperData.getCropFile();
            printWriter.println(wallpaperData.getCropFile().getAbsolutePath());
            wallpaperData.getCropFile();
            Bitmap decodeFile2 = BitmapFactory.decodeFile(wallpaperData.getCropFile().getAbsolutePath());
            if (decodeFile2 != null) {
                printWriter.print("  cropFile width=");
                printWriter.println(decodeFile2.getWidth());
                printWriter.print("  cropFile height=");
                printWriter.println(decodeFile2.getHeight());
                decodeFile2.recycle();
            }
            printWriter.print("  mWallpaperComponent=");
            printWriter.println(wallpaperData.wallpaperComponent);
            printWriter.print("  wallpaperObserver=");
            printWriter.println(wallpaperData.wallpaperObserver);
            WallpaperConnection wallpaperConnection = wallpaperData.connection;
            if (wallpaperConnection != null) {
                printWriter.print("  Wallpaper connection ");
                printWriter.print(wallpaperConnection);
                printWriter.println(":");
                if (wallpaperConnection.mInfo != null) {
                    printWriter.print("    mInfo.component=");
                    printWriter.println(wallpaperConnection.mInfo.getComponent());
                }
                wallpaperConnection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda14(4, printWriter));
                printWriter.print("    mService=");
                printWriter.println(wallpaperConnection.mService);
                printWriter.print("    mLastDiedTime=");
                printWriter.println(wallpaperData.lastDiedTime - SystemClock.uptimeMillis());
            }
            printWriter.print("  mSemWallpaperData=");
            printWriter.println(wallpaperData.mSemWallpaperData);
        }

        public void complete() {
            WallpaperConnection wallpaperConnection;
            WallpaperData wallpaperData = (WallpaperData) this.mWallpaper;
            boolean z = wallpaperData.mSystemWasBoth;
            WallpaperManagerService wallpaperManagerService = (WallpaperManagerService) this.this$0;
            if (z) {
                boolean isSystem = WhichChecker.isSystem(wallpaperData.mWhich);
                WallpaperData wallpaperData2 = (WallpaperData) this.mSemObserver;
                int i = this.mUserId;
                if (isSystem) {
                    if (wallpaperManagerService.mImageWallpaper.equals(wallpaperData2.wallpaperComponent)) {
                        WallpaperData wallpaperData3 = wallpaperManagerService.mLockWallpaperMap.get(wallpaperData.userId, i);
                        if (wallpaperData3 == null || (wallpaperConnection = wallpaperData2.connection) == null) {
                            WallpaperData wallpaperData4 = wallpaperManagerService.mWallpaperMap.get(wallpaperData.userId, i);
                            if (wallpaperData4 != null) {
                                wallpaperData4.mWhich = i | 3;
                                wallpaperManagerService.updateEngineFlags(wallpaperData4);
                            }
                            WallpaperData wallpaperData5 = wallpaperManagerService.mLockWallpaperMap.get(wallpaperData.userId, i);
                            if (wallpaperData5 != null) {
                                wallpaperData5.mSemWallpaperData.mWpType = -1;
                                wallpaperData5.cleanUp();
                            }
                        } else {
                            wallpaperData3.wallpaperComponent = wallpaperData2.wallpaperComponent;
                            wallpaperData3.connection = wallpaperConnection;
                            if (wallpaperConnection != null) {
                                wallpaperConnection.mWallpaper = wallpaperData3;
                            }
                            wallpaperData3.mSystemWasBoth = false;
                            wallpaperData.mSystemWasBoth = false;
                            wallpaperManagerService.updateEngineFlags(wallpaperData3);
                            SemWallpaperData semWallpaperData = wallpaperData3.mSemWallpaperData;
                            wallpaperManagerService.notifyLockWallpaperChanged(semWallpaperData.mWpType, wallpaperData3.mWhich, semWallpaperData.mExternalParams);
                            if (WhichChecker.isSubDisplay(wallpaperData.mWhich)) {
                                wallpaperManagerService.mLastSubLockWallpaper = wallpaperData3;
                            } else {
                                wallpaperManagerService.mLastLockWallpaper = wallpaperData3;
                            }
                        }
                    } else {
                        WallpaperData wallpaperData6 = wallpaperManagerService.mLockWallpaperMap.get(wallpaperData.userId, i);
                        if (wallpaperData6 != null) {
                            WallpaperConnection wallpaperConnection2 = wallpaperData2.connection;
                            wallpaperData6.connection = wallpaperConnection2;
                            if (wallpaperConnection2 != null) {
                                wallpaperConnection2.mWallpaper = wallpaperData6;
                            }
                            wallpaperData6.mSystemWasBoth = false;
                            wallpaperData.mSystemWasBoth = false;
                            wallpaperManagerService.updateEngineFlags(wallpaperData6);
                            SemWallpaperData semWallpaperData2 = wallpaperData6.mSemWallpaperData;
                            wallpaperManagerService.notifyLockWallpaperChanged(semWallpaperData2.mWpType, wallpaperData6.mWhich, semWallpaperData2.mExternalParams);
                        } else {
                            Slog.d("WallpaperManagerService", "lockWp is null.");
                        }
                        if (WhichChecker.isSubDisplay(wallpaperData.mWhich)) {
                            wallpaperManagerService.mLastSubLockWallpaper = wallpaperData6;
                        } else {
                            wallpaperManagerService.mLastLockWallpaper = wallpaperData6;
                        }
                    }
                } else if (WhichChecker.isLock(wallpaperData.mWhich)) {
                    WallpaperData wallpaperData7 = wallpaperManagerService.mWallpaperMap.get(wallpaperData.userId, i);
                    wallpaperData.mSystemWasBoth = false;
                    if (wallpaperData7 != null && wallpaperData7.wallpaperId == wallpaperData2.wallpaperId) {
                        wallpaperData7.mWhich = i | 1;
                        wallpaperData7.mSystemWasBoth = false;
                        wallpaperManagerService.updateEngineFlags(wallpaperData7);
                    }
                }
            }
            wallpaperManagerService.saveSettingsLocked(wallpaperData.userId);
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("WallpaperDataManager");
            int i = this.mUserId;
            if (i == 1) {
                printWriter.println("Home Wallpaper");
            } else if (i == 2) {
                printWriter.println("Lock Wallpaper");
            }
            synchronized (this.mSemObserver) {
                for (int i2 = 0; i2 < ((SparseArray) this.mWallpaper).size(); i2++) {
                    try {
                        WallpaperData wallpaperData = (WallpaperData) ((SparseArray) this.mWallpaper).valueAt(i2);
                        print(printWriter, wallpaperData, ((SparseArray) this.mWallpaper).keyAt(i2) - wallpaperData.userId);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            printWriter.println("");
        }

        public WallpaperData get(int i, int i2) {
            WallpaperData wallpaperData;
            if (WhichChecker.getMode(i2) == 0) {
                i2 = ((SemWallpaperManagerService) this.this$0).getCurrentImplicitMode();
            }
            synchronized (this.mSemObserver) {
                try {
                    SparseArray sparseArray = (SparseArray) this.mWallpaper;
                    if (i2 == 8 && i > 0) {
                        Log.addLogString("WallpaperManagerService", "get, dex mode support only user = 0");
                        i = 0;
                    }
                    wallpaperData = (WallpaperData) sparseArray.get(i + i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return wallpaperData;
        }

        public void put(int i, int i2, WallpaperData wallpaperData) {
            if (i < 0 || ((i > 150 && i < 160) || (i > 95 && i < 99))) {
                Log.addLogString("WallpaperManagerService", "put, invalid userId = " + i);
                return;
            }
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "put, userId:", ", mode:", ", data:");
            m.append(wallpaperData);
            m.append(", hash:");
            m.append(wallpaperData.hashCode());
            Log.addLogString("WallpaperManagerService", m.toString());
            if (WhichChecker.getMode(i2) == 0) {
                i2 = ((SemWallpaperManagerService) this.this$0).getCurrentImplicitMode();
            }
            synchronized (this.mSemObserver) {
                try {
                    SparseArray sparseArray = (SparseArray) this.mWallpaper;
                    if (WhichChecker.isDex(i2)) {
                        if (i > 0) {
                            Log.addLogString("WallpaperManagerService", "put, dex mode support only user = 0");
                            return;
                        }
                        wallpaperData.mSemWallpaperData.mIsDesktopWallpaper = true;
                    }
                    wallpaperData.mSemWallpaperData.mWhich = this.mUserId | i2;
                    sparseArray.put(i + i2, wallpaperData);
                    int i3 = wallpaperData.mSemWallpaperData.mWhich;
                    File wallpaperFile = wallpaperData.getWallpaperFile(0);
                    if (WhichChecker.isSystem(i3) && WhichChecker.isSubDisplay(i3) && wallpaperFile != null && "wallpaper_orig".equals(wallpaperFile.getName())) {
                        SemWallpaperManagerService.putLog("\nUnexpected file assignment detected!\n" + SemWallpaperManagerService.getCallStackString());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void remove(int i, int i2) {
            if (WhichChecker.getMode(i2) == 0) {
                i2 = ((SemWallpaperManagerService) this.this$0).getCurrentImplicitMode();
            }
            synchronized (this.mSemObserver) {
                ((SparseArray) this.mWallpaper).remove(i + i2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperRestoreCompletion extends IWallpaperManagerCallback.Stub {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        public WallpaperRestoreCompletion() {
        }

        public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        }

        public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
        }

        public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
        }

        public final void onWallpaperChanged() {
            this.mLatch.countDown();
        }

        public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        }
    }

    /* renamed from: -$$Nest$mcheckSameComponentSetOtherDisplay, reason: not valid java name */
    public static int m1040$$Nest$mcheckSameComponentSetOtherDisplay(WallpaperManagerService wallpaperManagerService, WallpaperData wallpaperData) {
        WallpaperInfo wallpaperInfo;
        wallpaperManagerService.getClass();
        ComponentName componentName = wallpaperData.wallpaperComponent;
        if (componentName == null || wallpaperManagerService.mImageWallpaper.equals(componentName)) {
            return 0;
        }
        WallpaperConnection wallpaperConnection = wallpaperData.connection;
        if (wallpaperConnection != null && (wallpaperInfo = wallpaperConnection.mInfo) != null) {
            int i = wallpaperInfo.getServiceInfo().applicationInfo.uid;
            Slog.i("WallpaperManagerService", "checkSameComponentSetOtherDisplay uid = " + i);
            if (wallpaperManagerService.isSignedWithPlatformSignature(i)) {
                return 0;
            }
        }
        int i2 = WhichChecker.isSubDisplay(wallpaperData.mSemWallpaperData.mWhich) ? 4 : 16;
        int i3 = i2 | 1;
        if (wallpaperData.wallpaperComponent.equals(wallpaperManagerService.peekWallpaperDataLocked(wallpaperData.userId, i3).wallpaperComponent)) {
            return wallpaperManagerService.mSemService.isSystemAndLockPaired(wallpaperData.userId, i2) ? i2 | 3 : i3;
        }
        int i4 = i2 | 2;
        if (wallpaperData.wallpaperComponent.equals(wallpaperManagerService.peekWallpaperDataLocked(wallpaperData.userId, i4).wallpaperComponent)) {
            return i4;
        }
        return 0;
    }

    /* renamed from: -$$Nest$mgetWallpapers, reason: not valid java name */
    public static WallpaperData[] m1041$$Nest$mgetWallpapers(WallpaperManagerService wallpaperManagerService) {
        WallpaperData wallpaperData = wallpaperManagerService.mWallpaperMap.get(wallpaperManagerService.mCurrentUserId, 0);
        WallpaperData wallpaperData2 = wallpaperManagerService.mLockWallpaperMap.get(wallpaperManagerService.mCurrentUserId, 0);
        boolean z = wallpaperData != null;
        boolean z2 = wallpaperData2 != null;
        return (z && z2) ? new WallpaperData[]{wallpaperData, wallpaperData2} : z ? new WallpaperData[]{wallpaperData} : z2 ? new WallpaperData[]{wallpaperData2} : new WallpaperData[0];
    }

    /* renamed from: -$$Nest$mhandleOMCWallpaperUpdatedLocked, reason: not valid java name */
    public static void m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(WallpaperManagerService wallpaperManagerService, int i) {
        synchronized (wallpaperManagerService.mLock) {
            try {
                WallpaperData wallpaperData = wallpaperManagerService.mWallpaperMap.get(UserHandle.getCallingUserId(), i);
                if (wallpaperData == null) {
                    return;
                }
                wallpaperData.mSemWallpaperData.getClass();
                OMCWallpaper oMCWallpaper = wallpaperManagerService.mSemService.mOMCWallpaper;
                File wallpaperFile = wallpaperData.getWallpaperFile(0);
                if (wallpaperFile != null) {
                    oMCWallpaper.getClass();
                    if (wallpaperFile.exists() && wallpaperFile.length() > 0) {
                        Log.e("OMCWallpaper", "user wallpaper is being used");
                        wallpaperManagerService.mSemService.mLegibilityColor.extractColor(wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.getCurrentUserId(), i | 1).mSemWallpaperData.mWhich, false);
                        wallpaperManagerService.mSemService.mLegibilityColor.extractColor(wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.getCurrentUserId(), i | 2).mSemWallpaperData.mWhich, false);
                    }
                }
                if (WallpaperManager.getOMCWallpaperFile(oMCWallpaper.mContext, 1, oMCWallpaper.mService.mCMFWallpaper.getDeviceColor()) != null) {
                    wallpaperManagerService.notifyCallbacksLocked(wallpaperData);
                    if (wallpaperManagerService.isDefaultComponent(wallpaperData)) {
                        Log.i("WallpaperManagerService", "handleOMCWallpaperUpdated: mCacheDefaultImageWallpaperColors = " + wallpaperManagerService.mCacheDefaultImageWallpaperColors);
                        wallpaperManagerService.mCacheDefaultImageWallpaperColors = null;
                        wallpaperManagerService.setWallpaperComponentInternal(wallpaperManagerService.mImageWallpaper, "", i | 1, UserHandle.getCallingUserId(), true, false, null);
                        if (WallpaperManager.isDefaultOperatorWallpaper(wallpaperManagerService.mContext, 2, wallpaperManagerService.mSemService.mCMFWallpaper.getDeviceColor())) {
                            wallpaperManagerService.clearWallpaper("android", i | 2, UserHandle.getCallingUserId());
                        }
                    } else {
                        Slog.e("WallpaperManagerService", "handleOMCWallpaperUpdatedLocked: Fail to set OMC wallpaper, component = " + wallpaperData.wallpaperComponent);
                    }
                } else {
                    Log.e("OMCWallpaper", "no omc wallpaper");
                }
                wallpaperManagerService.mSemService.mLegibilityColor.extractColor(wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.getCurrentUserId(), i | 1).mSemWallpaperData.mWhich, false);
                wallpaperManagerService.mSemService.mLegibilityColor.extractColor(wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.getCurrentUserId(), i | 2).mSemWallpaperData.mWhich, false);
            } finally {
            }
        }
    }

    static {
        new RectF(FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, 1.0f);
        SHIPPED = !Debug.semIsProductDev();
        sWallpaperType = Map.of(5, "decode_record", 6, "decode_lock_record");
    }

    public WallpaperManagerService(Context context) {
        Object obj = new Object();
        this.mLock = obj;
        this.mInitialUserSwitch = true;
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.wallpaper.WallpaperManagerService.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
                Log.d("WallpaperManagerService", "onDisplayAdded, " + i);
                if (Rune.VIRTUAL_DISPLAY_WALLPAPER && WallpaperManagerService.this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(i)) {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    WallpaperData wallpaperSafeLocked = wallpaperManagerService.getWallpaperSafeLocked(wallpaperManagerService.mCurrentUserId, 33);
                    wallpaperSafeLocked.mWhich |= 32;
                    WallpaperManagerService.this.switchWallpaper(wallpaperSafeLocked, null);
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
                WallpaperData wallpaperData;
                WallpaperConnection wallpaperConnection;
                WallpaperData wallpaperData2;
                WallpaperConnection wallpaperConnection2;
                WallpaperConnection wallpaperConnection3;
                synchronized (WallpaperManagerService.this.mLock) {
                    try {
                        WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                        WallpaperData wallpaperData3 = wallpaperManagerService.mLastWallpaper;
                        if (wallpaperData3 != null && (wallpaperConnection3 = wallpaperData3.connection) != null && wallpaperConnection3.containsDisplay(i)) {
                            wallpaperData = wallpaperManagerService.mLastWallpaper;
                        } else if (!Rune.VIRTUAL_DISPLAY_WALLPAPER || (wallpaperData2 = wallpaperManagerService.mLastVirtualWallpaper) == null || (wallpaperConnection2 = wallpaperData2.connection) == null || !wallpaperConnection2.containsDisplay(i)) {
                            WallpaperData wallpaperData4 = wallpaperManagerService.mFallbackWallpaper;
                            wallpaperData = (wallpaperData4 == null || (wallpaperConnection = wallpaperData4.connection) == null || !wallpaperConnection.containsDisplay(i)) ? null : wallpaperManagerService.mFallbackWallpaper;
                        } else {
                            wallpaperData = wallpaperManagerService.mLastVirtualWallpaper;
                        }
                        if (wallpaperData != null) {
                            if (WallpaperManagerService.this.mLastWallpaper.connection.containsDisplay(i)) {
                                wallpaperData = WallpaperManagerService.this.mLastWallpaper;
                            } else if (WallpaperManagerService.this.mFallbackWallpaper.connection.containsDisplay(i)) {
                                wallpaperData = WallpaperManagerService.this.mFallbackWallpaper;
                            }
                            if (wallpaperData == null) {
                                return;
                            }
                            DisplayConnector displayConnectorOrCreate = wallpaperData.connection.getDisplayConnectorOrCreate(i);
                            if (displayConnectorOrCreate == null) {
                                return;
                            }
                            displayConnectorOrCreate.disconnectLocked(wallpaperData.connection);
                            WallpaperConnection wallpaperConnection4 = wallpaperData.connection;
                            if (((DisplayConnector) wallpaperConnection4.mDisplayConnector.get(i)) != null) {
                                wallpaperConnection4.mDisplayConnector.remove(i);
                            }
                            WallpaperManagerService.this.mWallpaperDisplayHelper.mDisplayDatas.remove(i);
                        }
                        for (int size = WallpaperManagerService.this.mColorsChangedListeners.size() - 1; size >= 0; size--) {
                            ((SparseArray) WallpaperManagerService.this.mColorsChangedListeners.valueAt(size)).delete(i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mIsWallpaperInitialized = new SparseArray();
        this.mKeyguardListenerList = new ArrayList();
        this.mKeyguardListenerClientList = new ArrayList();
        this.mCoverWallpaperListenerList = new ArrayList();
        this.mCoverWallpaperListenerClientList = new ArrayList();
        this.mUserRestorecon = new SparseBooleanArray();
        this.mCurrentUserId = -10000;
        LocalColorRepository localColorRepository = new LocalColorRepository();
        localColorRepository.mLocalColorAreas = new ArrayMap();
        localColorRepository.mCallbacks = new RemoteCallbackList();
        this.mLocalColorRepo = localColorRepository;
        this.mContext = context;
        this.mShuttingDown = false;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getResources().getString(R.string.network_logging_notification_text));
        this.mImageWallpaper = unflattenFromString;
        ComponentName cmfDefaultWallpaperComponent = WallpaperManager.getCmfDefaultWallpaperComponent(context);
        this.mDefaultWallpaperComponent = cmfDefaultWallpaperComponent != null ? cmfDefaultWallpaperComponent : unflattenFromString;
        Log.d("WallpaperManagerService", "WallpaperService startup (support sub display ? " + Rune.SUPPORT_SUB_DISPLAY_MODE + ")");
        SemWallpaperResourcesInfo semWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
        SemWallpaperManagerService semWallpaperManagerService = new SemWallpaperManagerService(context, new SemCallback(), this, semWallpaperResourcesInfo);
        this.mSemService = semWallpaperManagerService;
        this.mAssetFileManager = new AssetFileManager();
        this.mWallpaperMap = new WallpaperObserver(obj, 1, semWallpaperManagerService);
        this.mLockWallpaperMap = new WallpaperObserver(obj, 2, semWallpaperManagerService);
        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mWindowManagerInternal = windowManagerInternal;
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mIPackageManager = AppGlobals.getPackageManager();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        displayManager.registerDisplayListener(displayListener, null);
        WallpaperDisplayHelper wallpaperDisplayHelper = new WallpaperDisplayHelper(displayManager, (WindowManager) context.getSystemService(WindowManager.class), windowManagerInternal, context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables).length > 0);
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
        WallpaperCropper wallpaperCropper = new WallpaperCropper(wallpaperDisplayHelper, semWallpaperManagerService);
        this.mWallpaperCropper = wallpaperCropper;
        windowManagerInternal.setWallpaperCropUtils(new WallpaperManagerService$$ExternalSyntheticLambda6(wallpaperCropper));
        this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        if (context.getResources().getBoolean(R.bool.config_quickSettingsShowMediaPlayer)) {
            String[] stringArray = context.getResources().getStringArray(17236461);
            IntArray intArray = new IntArray();
            for (String str : stringArray) {
                try {
                    intArray.add(this.mContext.getPackageManager().getApplicationInfo(str, 0).uid);
                } catch (Exception e) {
                    Slog.e("WallpaperManagerService", e.toString());
                }
            }
            if (intArray.size() > 0) {
                try {
                    ActivityManager.getService().registerUidObserverForUids(new UidObserver() { // from class: com.android.server.wallpaper.WallpaperManagerService.2
                        public final void onUidStateChanged(int i, int i2, long j, int i3) {
                            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                            final boolean z = i2 == 2;
                            synchronized (wallpaperManagerService.mLock) {
                                try {
                                    for (WallpaperData wallpaperData : wallpaperManagerService.getActiveWallpapers()) {
                                        if (wallpaperData.connection.mInfo != null) {
                                            if (!z) {
                                                if (ActivityTaskManagerService.this.hasActiveVisibleWindow(wallpaperData.connection.mInfo.getServiceInfo().applicationInfo.uid)) {
                                                }
                                            }
                                            if (wallpaperData.connection.containsDisplay(wallpaperManagerService.mWindowManagerInternal.getTopFocusedDisplayId())) {
                                                wallpaperData.connection.forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda27
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        boolean z2 = z;
                                                        boolean z3 = WallpaperManagerService.SHIPPED;
                                                        IWallpaperEngine iWallpaperEngine = ((WallpaperManagerService.DisplayConnector) obj2).mEngine;
                                                        if (iWallpaperEngine != null) {
                                                            try {
                                                                iWallpaperEngine.setVisibility(!z2);
                                                            } catch (RemoteException e2) {
                                                                Slog.w("WallpaperManagerService", "Failed to set visibility", e2);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }, 1, 2, "android", intArray.toArray());
                } catch (RemoteException e2) {
                    Slog.e("WallpaperManagerService", e2.toString());
                }
            }
        }
        this.mMonitor = new MyPackageMonitor();
        this.mColorsChangedListeners = new SparseArray();
        this.mWallpaperDataParser = new WallpaperDataParser(this.mContext, this.mWallpaperDisplayHelper, this.mWallpaperCropper, this.mSemService, this.mSemWallpaperResourcesInfo);
        LocalServices.addService(LocalService.class, new LocalService());
        this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mWallpaperHanlder = new Handler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("WallpaperManagerService").getLooper());
    }

    public static boolean canBindComponentNow(int i) {
        Log.d("WallpaperManagerService", "bindComponentNow " + i);
        return WhichChecker.isDex(i) || !Rune.SUPPORT_SUB_DISPLAY_MODE || WhichChecker.isSubDisplay(i) || WhichChecker.isPhone(i);
    }

    public static void clearWallpaperBitmaps(WallpaperData wallpaperData) {
        boolean sourceExists = wallpaperData.sourceExists();
        boolean cropExists = wallpaperData.cropExists();
        if (sourceExists) {
            wallpaperData.getWallpaperFile(0).delete();
        }
        if (cropExists) {
            wallpaperData.getCropFile().delete();
        }
    }

    public static SparseArray getCropMap(List list, int[] iArr) {
        if (((list == null) ^ (iArr == null)) || !(list == null || list.size() == iArr.length)) {
            throw new IllegalArgumentException("Illegal crops/orientations lists: must both be null, or both the same size");
        }
        SparseArray sparseArray = new SparseArray();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Rect rect = (Rect) list.get(i);
                int width = rect.width();
                int height = rect.height();
                if (width < 0 || height < 0 || rect.left < 0 || rect.top < 0) {
                    throw new IllegalArgumentException("Invalid crop rect supplied: " + rect);
                }
                int i2 = iArr[i];
                if (i2 == -1 && sparseArray.size() > 1) {
                    throw new IllegalArgumentException("Invalid crops supplied: the UNKNOWNscreen orientation should only be used in a singleton map");
                }
                sparseArray.put(i2, rect);
            }
        }
        return sparseArray;
    }

    public static boolean isWallpaperFileExists(int i) {
        if (new File(Environment.getUserSystemDirectory(i), WallpaperUtils.getInfoFileName(1)).exists()) {
            Log.d("WallpaperManagerService", "isWallpaperFileExists: TRUE");
            return true;
        }
        Log.d("WallpaperManagerService", "isWallpaperFileExists: FALSE");
        return false;
    }

    public static void stopObserver(WallpaperData wallpaperData) {
        FileObserver fileObserver;
        if (wallpaperData == null || (fileObserver = wallpaperData.wallpaperObserver) == null) {
            return;
        }
        fileObserver.stopWatching();
        wallpaperData.wallpaperObserver = null;
    }

    public static boolean supportsMultiDisplay(WallpaperConnection wallpaperConnection) {
        if (wallpaperConnection == null) {
            return false;
        }
        WallpaperInfo wallpaperInfo = wallpaperConnection.mInfo;
        return wallpaperInfo == null || wallpaperInfo.supportsMultipleDisplays() || Rune.SUPPORT_SUB_DISPLAY_MODE;
    }

    public static ParcelFileDescriptor updateWallpaperBitmapLocked(String str, WallpaperData wallpaperData, Bundle bundle) {
        if (str == null) {
            str = "";
        }
        try {
            File userSystemDirectory = Environment.getUserSystemDirectory(wallpaperData.userId);
            if (!userSystemDirectory.exists()) {
                userSystemDirectory.mkdir();
                FileUtils.setPermissions(userSystemDirectory.getPath(), 505, -1, -1);
            }
            ParcelFileDescriptor open = ParcelFileDescriptor.open(wallpaperData.getWallpaperFile(0), 1006632960);
            if (!SELinux.restorecon(wallpaperData.getWallpaperFile(0))) {
                Slog.w("WallpaperManagerService", "restorecon failed for wallpaper file: " + wallpaperData.getWallpaperFile(0).getPath());
                IoUtils.closeQuietly(open);
                return null;
            }
            wallpaperData.name = str;
            int makeWallpaperIdLocked = WallpaperUtils.makeWallpaperIdLocked();
            wallpaperData.wallpaperId = makeWallpaperIdLocked;
            if (bundle != null) {
                bundle.putInt("android.service.wallpaper.extra.ID", makeWallpaperIdLocked);
            }
            wallpaperData.primaryColors = null;
            Slog.v("WallpaperManagerService", "updateWallpaperBitmapLocked() : id=" + wallpaperData.wallpaperId + " name=" + str + " file=" + wallpaperData.getWallpaperFile(0).getName());
            return open;
        } catch (FileNotFoundException e) {
            Slog.w("WallpaperManagerService", "Error setting wallpaper", e);
            return null;
        }
    }

    public final void addOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i, int i2, int i3) {
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

    /* JADX WARN: Can't wrap try/catch for region: R(10:16|(3:97|98|(15:101|102|104|105|106|107|108|(2:115|116)(2:112|(1:114))|19|20|(1:25)|27|(1:29)(2:32|(2:34|(1:36)(2:37|38))(3:39|(1:(1:85)(1:86))(4:44|(1:46)|(1:50)|(2:52|(1:54)(2:55|56)))|(5:69|(1:71)(1:83)|72|73|(2:75|(1:77)(2:78|79))(3:80|81|82))(2:64|(1:66)(2:67|68))))|30|31)(1:100))|18|19|20|(2:23|25)|27|(0)(0)|30|31) */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00ef, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00f0, code lost:
    
        android.util.Slog.e("WallpaperManagerService", "SAFEMODE Exception occurs! " + r0.getMessage());
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010f A[Catch: all -> 0x00b9, RemoteException -> 0x00bc, TRY_LEAVE, TryCatch #3 {RemoteException -> 0x00bc, blocks: (B:98:0x0077, B:114:0x00a0, B:27:0x0103, B:29:0x010f, B:32:0x0128, B:34:0x0132, B:36:0x0140, B:37:0x0144, B:38:0x0149, B:39:0x014a, B:41:0x0153, B:44:0x015d, B:46:0x0163, B:48:0x0170, B:50:0x017a, B:52:0x0181, B:54:0x018f, B:55:0x0193, B:56:0x0198, B:58:0x01a1, B:60:0x01a7, B:62:0x01b5, B:64:0x01c0, B:66:0x01ce, B:67:0x01d3, B:68:0x01d8, B:69:0x01d9, B:73:0x0247, B:75:0x0260, B:77:0x026e, B:78:0x0273, B:79:0x0278, B:80:0x0279, B:96:0x00f0), top: B:97:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0128 A[Catch: all -> 0x00b9, RemoteException -> 0x00bc, TRY_ENTER, TryCatch #3 {RemoteException -> 0x00bc, blocks: (B:98:0x0077, B:114:0x00a0, B:27:0x0103, B:29:0x010f, B:32:0x0128, B:34:0x0132, B:36:0x0140, B:37:0x0144, B:38:0x0149, B:39:0x014a, B:41:0x0153, B:44:0x015d, B:46:0x0163, B:48:0x0170, B:50:0x017a, B:52:0x0181, B:54:0x018f, B:55:0x0193, B:56:0x0198, B:58:0x01a1, B:60:0x01a7, B:62:0x01b5, B:64:0x01c0, B:66:0x01ce, B:67:0x01d3, B:68:0x01d8, B:69:0x01d9, B:73:0x0247, B:75:0x0260, B:77:0x026e, B:78:0x0273, B:79:0x0278, B:80:0x0279, B:96:0x00f0), top: B:97:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02b8 A[Catch: all -> 0x00b9, TryCatch #5 {all -> 0x00b9, blocks: (B:98:0x0077, B:102:0x007f, B:105:0x0083, B:108:0x0087, B:110:0x0090, B:112:0x0098, B:114:0x00a0, B:20:0x00da, B:23:0x00e4, B:25:0x00ec, B:27:0x0103, B:29:0x010f, B:32:0x0128, B:34:0x0132, B:36:0x0140, B:37:0x0144, B:38:0x0149, B:39:0x014a, B:41:0x0153, B:44:0x015d, B:46:0x0163, B:48:0x0170, B:50:0x017a, B:52:0x0181, B:54:0x018f, B:55:0x0193, B:56:0x0198, B:58:0x01a1, B:60:0x01a7, B:62:0x01b5, B:64:0x01c0, B:66:0x01ce, B:67:0x01d3, B:68:0x01d8, B:69:0x01d9, B:73:0x0247, B:75:0x0260, B:77:0x026e, B:78:0x0273, B:79:0x0278, B:80:0x0279, B:96:0x00f0, B:115:0x00c4, B:90:0x02a0, B:92:0x02b8, B:93:0x02bd, B:94:0x02c2), top: B:97:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02bd A[Catch: all -> 0x00b9, TryCatch #5 {all -> 0x00b9, blocks: (B:98:0x0077, B:102:0x007f, B:105:0x0083, B:108:0x0087, B:110:0x0090, B:112:0x0098, B:114:0x00a0, B:20:0x00da, B:23:0x00e4, B:25:0x00ec, B:27:0x0103, B:29:0x010f, B:32:0x0128, B:34:0x0132, B:36:0x0140, B:37:0x0144, B:38:0x0149, B:39:0x014a, B:41:0x0153, B:44:0x015d, B:46:0x0163, B:48:0x0170, B:50:0x017a, B:52:0x0181, B:54:0x018f, B:55:0x0193, B:56:0x0198, B:58:0x01a1, B:60:0x01a7, B:62:0x01b5, B:64:0x01c0, B:66:0x01ce, B:67:0x01d3, B:68:0x01d8, B:69:0x01d9, B:73:0x0247, B:75:0x0260, B:77:0x026e, B:78:0x0273, B:79:0x0278, B:80:0x0279, B:96:0x00f0, B:115:0x00c4, B:90:0x02a0, B:92:0x02b8, B:93:0x02bd, B:94:0x02c2), top: B:97:0x0077 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean bindWallpaperComponentLocked(android.content.ComponentName r25, boolean r26, boolean r27, com.android.server.wallpaper.WallpaperData r28, android.os.IRemoteCallback r29, android.app.WallpaperInfo r30) {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.bindWallpaperComponentLocked(android.content.ComponentName, boolean, boolean, com.android.server.wallpaper.WallpaperData, android.os.IRemoteCallback, android.app.WallpaperInfo):boolean");
    }

    public final boolean changingToSame(ComponentName componentName, WallpaperData wallpaperData) {
        if (wallpaperData.connection != null) {
            ComponentName componentName2 = wallpaperData.wallpaperComponent;
            if ((componentName == null || componentName.equals(this.mDefaultWallpaperComponent)) && (componentName2 == null || componentName2.equals(this.mDefaultWallpaperComponent))) {
                return true;
            }
            if (componentName2 != null && componentName2.equals(componentName)) {
                Slog.v("WallpaperManagerService", "changingToSame: wallpaperName = " + componentName2 + ", componentName = " + componentName);
                return true;
            }
        }
        return false;
    }

    public final void checkCallerIsSystemOrSystemUi() {
        if (Binder.getCallingUid() != Process.myUid() && this.mContext.checkCallingPermission("android.permission.STATUS_BAR_SERVICE") != 0) {
            throw new SecurityException("Access denied: only system processes can call this");
        }
    }

    public final void checkPermission(String str) {
        if (hasPermission(str)) {
            return;
        }
        throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission " + str);
    }

    public final void checkWallpaperData(int i, int i2, int i3, WallpaperData wallpaperData) {
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i3, "checkWallpaperData: userId = ", ", which = ", ", wpType = ");
        m.append(i2);
        m.append(", wallpaperFile= ");
        m.append(wallpaperData.getWallpaperFile(0));
        Log.d("WallpaperManagerService", m.toString());
        if (i2 == 0 && wallpaperData.getWallpaperFile(0) == null) {
            boolean isLock = WhichChecker.isLock(i3);
            File wallpaperLockDir = isLock ? WallpaperUtils.getWallpaperLockDir(i) : Environment.getUserSystemDirectory(i);
            int mode = WhichChecker.isSystemAndLock(i3) ? WhichChecker.getMode(i3) | 1 : i3;
            String fileName = WallpaperUtils.getFileName(mode);
            String cropFileName = WallpaperUtils.getCropFileName(mode);
            wallpaperData.mSemWallpaperData.mWpType = 0;
            File file = new File(wallpaperLockDir, fileName);
            File file2 = new File(wallpaperLockDir, cropFileName);
            wallpaperData.mWallpaperFiles.put(wallpaperData.mWhich, file);
            wallpaperData.mCropFiles.put(wallpaperData.mWhich, file2);
            (isLock ? this.mLockWallpaperMap : this.mWallpaperMap).put(i, WhichChecker.getMode(i3), wallpaperData);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00cb A[Catch: all -> 0x00b9, TryCatch #0 {all -> 0x00b9, blocks: (B:13:0x0086, B:15:0x0098, B:17:0x00a0, B:19:0x00a8, B:21:0x00ae, B:24:0x00bb, B:25:0x00c4, B:27:0x00cb, B:28:0x00d3, B:31:0x00e0, B:34:0x00db, B:36:0x00c1), top: B:12:0x0086 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void clearWallpaper(java.lang.String r8, int r9, int r10) {
        /*
            r7 = this;
            java.lang.String r0 = "WallpaperManagerService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "clearWallpaper: callingPackage = "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r2 = ", which = "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            com.samsung.server.wallpaper.Log.i(r0, r1)
            java.lang.String r0 = "WallpaperManagerService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "clearWallpaper: callingPackage = "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r2 = ", which = "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            com.samsung.server.wallpaper.Log.addLogString(r0, r1)
            java.lang.String r0 = "android.permission.SET_WALLPAPER"
            r7.checkPermission(r0)
            boolean r0 = r7.isWallpaperSupported(r8)
            if (r0 == 0) goto Le4
            boolean r0 = r7.isSetWallpaperAllowed(r8)
            if (r0 != 0) goto L49
            goto Le4
        L49:
            com.samsung.server.wallpaper.SemWallpaperManagerService r0 = r7.mSemService
            r0.getClass()
            boolean r0 = com.samsung.server.wallpaper.SemWallpaperManagerService.isSupportingMode(r9)
            if (r0 != 0) goto L71
            java.lang.String r7 = "WallpaperManagerService"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r10 = "clearWallpaper ["
            r8.<init>(r10)
            int r9 = com.samsung.android.wallpaper.utils.WhichChecker.getMode(r9)
            r8.append(r9)
            java.lang.String r9 = "] mode isn't support"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.samsung.server.wallpaper.Log.e(r7, r8)
            return
        L71:
            int r0 = android.os.Binder.getCallingPid()
            int r1 = android.os.Binder.getCallingUid()
            java.lang.String r5 = "clearWallpaper"
            r3 = 0
            r4 = 1
            r6 = 0
            r2 = r10
            int r10 = android.app.ActivityManager.handleIncomingUser(r0, r1, r2, r3, r4, r5, r6)
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            boolean r1 = r7.isFromForegroundApp(r8)     // Catch: java.lang.Throwable -> Lb9
            com.samsung.server.wallpaper.SemWallpaperManagerService r2 = r7.mSemService     // Catch: java.lang.Throwable -> Lb9
            int r9 = r2.getModeEnsuredWhich(r9)     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r2 = "android.app.cts.wallpapers"
            boolean r2 = r2.equals(r8)     // Catch: java.lang.Throwable -> Lb9
            if (r2 != 0) goto Lc1
            java.lang.String r2 = "com.android.wallpaperbackup"
            boolean r2 = r2.equals(r8)     // Catch: java.lang.Throwable -> Lb9
            if (r2 != 0) goto Lc1
            java.lang.String r2 = "com.android.wallpaper.livepicker"
            boolean r2 = r2.equals(r8)     // Catch: java.lang.Throwable -> Lb9
            if (r2 != 0) goto Lc1
            boolean r2 = com.samsung.android.wallpaper.utils.WhichChecker.isLock(r9)     // Catch: java.lang.Throwable -> Lb9
            if (r2 == 0) goto Lbb
            int r2 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> Lb9
            boolean r2 = r7.isSignedWithPlatformSignature(r2)     // Catch: java.lang.Throwable -> Lb9
            if (r2 != 0) goto Lbb
            goto Lc1
        Lb9:
            r7 = move-exception
            goto Le2
        Lbb:
            com.samsung.server.wallpaper.SemWallpaperManagerService r1 = r7.mSemService     // Catch: java.lang.Throwable -> Lb9
            r1.semClearWallpaperLocked(r9, r10, r8)     // Catch: java.lang.Throwable -> Lb9
            goto Lc4
        Lc1:
            r7.clearWallpaperLocked(r9, r10, r1)     // Catch: java.lang.Throwable -> Lb9
        Lc4:
            boolean r8 = com.samsung.android.wallpaper.utils.WhichChecker.isLock(r9)     // Catch: java.lang.Throwable -> Lb9
            r1 = 0
            if (r8 == 0) goto Ld2
            com.android.server.wallpaper.WallpaperManagerService$WallpaperObserver r8 = r7.mLockWallpaperMap     // Catch: java.lang.Throwable -> Lb9
            com.android.server.wallpaper.WallpaperData r8 = r8.get(r10, r1)     // Catch: java.lang.Throwable -> Lb9
            goto Ld3
        Ld2:
            r8 = 0
        Ld3:
            boolean r9 = com.samsung.android.wallpaper.utils.WhichChecker.isSystem(r9)     // Catch: java.lang.Throwable -> Lb9
            if (r9 != 0) goto Ldb
            if (r8 != 0) goto Le0
        Ldb:
            com.android.server.wallpaper.WallpaperManagerService$WallpaperObserver r7 = r7.mWallpaperMap     // Catch: java.lang.Throwable -> Lb9
            r7.get(r10, r1)     // Catch: java.lang.Throwable -> Lb9
        Le0:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb9
            return
        Le2:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb9
            throw r7
        Le4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.clearWallpaper(java.lang.String, int, int):void");
    }

    public final void clearWallpaperLocked(int i, final int i2, final boolean z) {
        WallpaperData wallpaperData;
        IllegalArgumentException illegalArgumentException;
        int i3;
        ComponentName componentName;
        int mode = WhichChecker.getMode(i);
        if (this.mWallpaperMap.get(i2, mode) != null) {
            wallpaperData = this.mWallpaperMap.get(i2, mode);
        } else {
            loadSettingsLocked(i2, 1, mode, false);
            wallpaperData = this.mWallpaperMap.get(i2, mode);
        }
        final WallpaperData wallpaperData2 = wallpaperData;
        WallpaperData wallpaperData3 = this.mLockWallpaperMap.get(i2, mode);
        if (WhichChecker.isLock(i) && wallpaperData3 == null) {
            return;
        }
        try {
            if (i2 != this.mCurrentUserId && this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            if ((i & 2) > 0 && wallpaperData3 != null) {
                arrayList.add(wallpaperData3);
            }
            if ((i & 1) > 0) {
                arrayList.add(wallpaperData2);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                WallpaperData wallpaperData4 = (WallpaperData) it.next();
                clearWallpaperBitmaps(wallpaperData4);
                if (Flags.multiCrop()) {
                    wallpaperData4.mCropHints.clear();
                    wallpaperData4.cropHint.set(0, 0, 0, 0);
                    wallpaperData4.mSampleSize = 1.0f;
                }
            }
            illegalArgumentException = null;
            if (WhichChecker.isLock(i)) {
                i3 = mode | 3;
                componentName = wallpaperData2.wallpaperComponent;
            } else {
                i3 = mode | i;
                componentName = null;
            }
            final boolean z2 = !WhichChecker.isLock(i);
            final ComponentName componentName2 = componentName;
            final int i4 = i3;
            if (((Boolean) IWallpaperManager.Stub.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda10
                public final /* synthetic */ IRemoteCallback f$6;

                public final Object getOrThrow() {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    ComponentName componentName3 = componentName2;
                    int i5 = i4;
                    int i6 = i2;
                    boolean z3 = z2;
                    boolean z4 = z;
                    WallpaperData wallpaperData5 = wallpaperData2;
                    boolean z5 = WallpaperManagerService.SHIPPED;
                    wallpaperManagerService.getClass();
                    return Boolean.valueOf(wallpaperManagerService.setWallpaperComponentInternal(componentName3, "", i5, i6, z3, z4, wallpaperData5.mSemWallpaperData.mExternalParams));
                }
            })).booleanValue()) {
                return;
            }
        } catch (IllegalArgumentException e) {
            illegalArgumentException = e;
        }
        Slog.e("WallpaperManagerService", "Default wallpaper component not found!", illegalArgumentException);
        IWallpaperManager.Stub.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                WallpaperData wallpaperData5 = wallpaperData2;
                boolean z3 = WallpaperManagerService.SHIPPED;
                wallpaperManagerService.getClass();
                wallpaperData5.wallpaperComponent = null;
                wallpaperManagerService.detachWallpaperLocked(wallpaperData5);
            }
        });
    }

    public final void copyFileToWallpaperFile(int i, String str) {
        copyFileToWallpaperFile(i, str, false);
    }

    public final void copyFileToWallpaperFile(int i, String str, boolean z) {
        int i2;
        WallpaperData wallpaperSafeLocked;
        Slog.d("WallpaperManagerService", "copyFileToWallpaperFile: which = " + i + ", isPreloaded = " + z + ", callingPackage = " + str);
        if ("android.app.cts.wallpapers".equals(str)) {
            return;
        }
        int i3 = this.mCurrentUserId;
        int mode = WhichChecker.getMode(this.mSemService.getModeEnsuredWhich(i));
        WallpaperData wallpaperData = this.mWallpaperMap.get(i3, mode);
        WallpaperRestoreCompletion wallpaperRestoreCompletion = new WallpaperRestoreCompletion();
        synchronized (this.mLock) {
            i2 = mode | 2;
            wallpaperSafeLocked = getWallpaperSafeLocked(i3, i2);
        }
        wallpaperSafeLocked.mSemWallpaperData.mWpType = -1;
        wallpaperSafeLocked.cleanUp();
        SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
        semWallpaperData.mWpType = 0;
        wallpaperSafeLocked.imageWallpaperPending = true;
        wallpaperSafeLocked.mWhich = mode | 3;
        wallpaperSafeLocked.setComplete = wallpaperRestoreCompletion;
        semWallpaperData.mIsCopied = true;
        semWallpaperData.mIsPreloaded = z;
        if (wallpaperData != null) {
            wallpaperSafeLocked.name = wallpaperData.name;
            wallpaperSafeLocked.allowBackup = wallpaperData.allowBackup;
            wallpaperSafeLocked.cropHint.set(wallpaperData.cropHint);
            wallpaperSafeLocked.setCallingPackage(wallpaperData.mSemWallpaperData.getWallpaperHistory());
            SemWallpaperData semWallpaperData2 = wallpaperSafeLocked.mSemWallpaperData;
            SemWallpaperData semWallpaperData3 = wallpaperData.mSemWallpaperData;
            semWallpaperData2.mExternalParams = semWallpaperData3.mExternalParams;
            semWallpaperData2.mOrientation = semWallpaperData3.mOrientation;
            if (!FileUtils.copyFile(wallpaperData.getWallpaperFile(0), wallpaperSafeLocked.getWallpaperFile(0))) {
                Slog.e("WallpaperManagerService", "copyFileToWallpaperFile: failed copyFile (0x03)");
            } else if (wallpaperSafeLocked.getWallpaperFile(0).exists()) {
                Slog.v("WallpaperManagerService", "copyFileToWallpaperFile: restorecon() of lock file returned " + SELinux.restorecon(wallpaperSafeLocked.getWallpaperFile(0).getAbsoluteFile()));
                try {
                    wallpaperRestoreCompletion.mLatch.await(30L, TimeUnit.SECONDS);
                } catch (InterruptedException unused) {
                }
            } else {
                Slog.e("WallpaperManagerService", "copyFileToWallpaperFile: lockWallpaper.getWallpaperFile() does not exist.");
            }
        }
        this.mSemService.mDefaultWallpaper.updateTransparencySettingIfNeed(i2, str, z);
    }

    public final void copyPreloadedFileToWallpaperFile(int i, String str) {
        copyFileToWallpaperFile(i, str, true);
    }

    public final void detachWallpaperLocked(WallpaperData wallpaperData) {
        WallpaperConnection wallpaperConnection;
        if (wallpaperData == null || (wallpaperConnection = wallpaperData.connection) == null) {
            return;
        }
        IRemoteCallback iRemoteCallback = wallpaperConnection.mReply;
        if (iRemoteCallback != null) {
            try {
                iRemoteCallback.sendResult((Bundle) null);
            } catch (RemoteException e) {
                Slog.w("WallpaperManagerService", "Error sending reply to wallpaper before disconnect", e);
            }
            wallpaperData.connection.mReply = null;
        }
        wallpaperData.connection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda9(1, wallpaperData));
        WallpaperConnection wallpaperConnection2 = wallpaperData.connection;
        wallpaperConnection2.mService = null;
        wallpaperConnection2.mDisplayConnector.clear();
        FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
        this.mContext.getMainThreadHandler().removeCallbacks(wallpaperData.connection.mDisconnectRunnable);
        this.mContext.getMainThreadHandler().removeCallbacks(wallpaperData.connection.mTryToRebindRunnable);
        try {
            this.mContext.unbindService(wallpaperData.connection);
        } catch (IllegalArgumentException e2) {
            Slog.w("WallpaperManagerService", "Error unbinding wallpaper when detaching", e2);
        }
        wallpaperData.connection = null;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String[] strArr2;
        int i;
        File[] fileArr;
        int i2;
        SemWallpaperManagerService semWallpaperManagerService;
        SnapshotManager snapshotManager;
        int i3;
        Iterator it;
        SnapshotManager.SnapshotData snapshotData;
        Iterator it2;
        SnapshotManager.PerWhichSnapshot perWhichSnapshot;
        if (DumpUtils.checkDumpPermission(this.mContext, "WallpaperManagerService", printWriter)) {
            printWriter.print("mDefaultWallpaperComponent=");
            printWriter.println(this.mDefaultWallpaperComponent);
            printWriter.print("mImageWallpaper=");
            printWriter.println(this.mImageWallpaper);
            printWriter.println("mLastWallpaper state:");
            if (this.mLastWallpaper != null) {
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
                    printWriter.println(":");
                    if (wallpaperConnection.mInfo != null) {
                        printWriter.print("    mInfo.component=");
                        printWriter.println(wallpaperConnection.mInfo.getComponent());
                    }
                    wallpaperConnection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda14(0, printWriter));
                    printWriter.print("    mService=");
                    printWriter.println(wallpaperConnection.mService);
                    printWriter.print("    mLastDiedTime=");
                    printWriter.println(this.mLastWallpaper.lastDiedTime - SystemClock.uptimeMillis());
                }
            }
            printWriter.println("mLastLockWallpaper state:");
            if (this.mLastLockWallpaper != null) {
                printWriter.print(" User ");
                printWriter.print(this.mLastLockWallpaper.userId);
                printWriter.print(": id=");
                printWriter.println(this.mLastLockWallpaper.wallpaperId);
                printWriter.print("  mCropHint=");
                printWriter.println(this.mLastLockWallpaper.cropHint);
                printWriter.print("  mName=");
                printWriter.println(this.mLastLockWallpaper.name);
                printWriter.print("  mAllowBackup=");
                printWriter.println(this.mLastLockWallpaper.allowBackup);
                WallpaperConnection wallpaperConnection2 = this.mLastLockWallpaper.connection;
                if (wallpaperConnection2 != null) {
                    printWriter.print("  mLastLockWallpaper connection ");
                    printWriter.print(wallpaperConnection2);
                    printWriter.println(":");
                    if (wallpaperConnection2.mInfo != null) {
                        printWriter.print("    mInfo.component=");
                        printWriter.println(wallpaperConnection2.mInfo.getComponent());
                    }
                    wallpaperConnection2.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda14(1, printWriter));
                    printWriter.print("    mService=");
                    printWriter.println(wallpaperConnection2.mService);
                    printWriter.print("    mLastDiedTime=");
                    printWriter.println(this.mLastLockWallpaper.lastDiedTime - SystemClock.uptimeMillis());
                }
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
                WallpaperConnection wallpaperConnection3 = this.mLastDexWallpaper.connection;
                if (wallpaperConnection3 != null) {
                    printWriter.print("  mLastDexWallpaper connection ");
                    printWriter.print(wallpaperConnection3);
                    printWriter.println(":");
                    if (wallpaperConnection3.mInfo != null) {
                        printWriter.print("    mInfo.component=");
                        printWriter.println(wallpaperConnection3.mInfo.getComponent());
                    }
                    wallpaperConnection3.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda14(2, printWriter));
                    printWriter.print("    mService=");
                    printWriter.println(wallpaperConnection3.mService);
                    printWriter.print("    mLastDiedTime=");
                    printWriter.println(this.mLastDexWallpaper.lastDiedTime - SystemClock.uptimeMillis());
                }
            }
            if (this.mLastDexLockWallpaper != null) {
                printWriter.println("mLastDexLockWallpaper state:");
                printWriter.print(" User ");
                printWriter.print(this.mLastDexLockWallpaper.userId);
                printWriter.print(": id=");
                printWriter.println(this.mLastDexLockWallpaper.wallpaperId);
                printWriter.print("  mCropHint=");
                printWriter.println(this.mLastDexLockWallpaper.cropHint);
                printWriter.print("  mName=");
                printWriter.println(this.mLastDexLockWallpaper.name);
                printWriter.print("  mAllowBackup=");
                printWriter.println(this.mLastDexLockWallpaper.allowBackup);
                WallpaperConnection wallpaperConnection4 = this.mLastDexLockWallpaper.connection;
                if (wallpaperConnection4 != null) {
                    printWriter.print("  mLastDexLockWallpaper connection ");
                    printWriter.print(wallpaperConnection4);
                    printWriter.println(":");
                    if (wallpaperConnection4.mInfo != null) {
                        printWriter.print("    mInfo.component=");
                        printWriter.println(wallpaperConnection4.mInfo.getComponent());
                    }
                    wallpaperConnection4.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda14(3, printWriter));
                    printWriter.print("    mService=");
                    printWriter.println(wallpaperConnection4.mService);
                    printWriter.print("    mLastDiedTime=");
                    printWriter.println(this.mLastDexLockWallpaper.lastDiedTime - SystemClock.uptimeMillis());
                }
            }
            printWriter.println("Display state:");
            WallpaperDisplayHelper wallpaperDisplayHelper = this.mWallpaperDisplayHelper;
            for (int size = wallpaperDisplayHelper.mDisplayDatas.size() - 1; size >= 0; size--) {
                WallpaperDisplayHelper.DisplayData displayData = (WallpaperDisplayHelper.DisplayData) wallpaperDisplayHelper.mDisplayDatas.valueAt(size);
                printWriter.print("  displayId=");
                printWriter.println(displayData.mDisplayId);
                printWriter.print("  mWidth=");
                printWriter.print(displayData.mWidth);
                printWriter.print("  mHeight=");
                printWriter.println(displayData.mHeight);
                printWriter.print("  mPadding=");
                printWriter.println(displayData.mPadding);
            }
            printWriter.println("WallpaperObserver:");
            printWriter.print("  mWallpaperObserver.mSemObserver.getWallpaperFileObserver() = ");
            printWriter.println(((SemWallpaperManagerService.SemWallpaperObserver) this.mWallpaperObserver.mSemObserver).mWallpaperFileObserver);
            printWriter.print("  mWallpaperObserver.mSemObserver.getLockWallpaperFileObserver() = ");
            printWriter.println(((SemWallpaperManagerService.SemWallpaperObserver) this.mWallpaperObserver.mSemObserver).getLockWallpaperFileObserver());
            printWriter.println("Num KeyguardListeners = " + this.mKeyguardListenerList.size());
            printWriter.println("Num CoverWallpaperListeners = " + this.mCoverWallpaperListenerList.size());
            SemWallpaperManagerService semWallpaperManagerService2 = this.mSemService;
            semWallpaperManagerService2.getClass();
            printWriter.println("SemWallpaperManagerService start");
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  Orientation:"), semWallpaperManagerService2.mOrientation, printWriter, " Legibility Version:");
            m.append(SemWallpaperColors.getLegibilityVersion());
            printWriter.println(m.toString());
            StringBuilder sb = new StringBuilder("  allowScreenRotate:");
            sb.append(semWallpaperManagerService2.mLegibilityColor.mAllowScreenRotateSystem);
            sb.append(", ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, semWallpaperManagerService2.mLegibilityColor.mAllowScreenRotateLock, printWriter);
            if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder(" Lid state:"), semWallpaperManagerService2.mSubDisplayMode.mLidState, printWriter);
            }
            printWriter.println(" ------------ Snapshot History ------------");
            SnapshotManager snapshotManager2 = semWallpaperManagerService2.mSnapshotManager;
            if (snapshotManager2.mSnapshotRepositories.size() > 0) {
                int i4 = 0;
                while (i4 < snapshotManager2.mSnapshotRepositories.size()) {
                    SnapshotManager.SnapshotRepository snapshotRepository = (SnapshotManager.SnapshotRepository) snapshotManager2.mSnapshotRepositories.get(snapshotManager2.mSnapshotRepositories.keyAt(i4));
                    if (snapshotRepository != null && (i2 = snapshotRepository.userId) >= 0) {
                        printWriter.println("userId = " + i2);
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("lastKey = "), snapshotRepository.key, printWriter);
                        if (snapshotRepository.mSnapshotHistories.size() > 0) {
                            Iterator it3 = snapshotRepository.mSnapshotHistories.iterator();
                            while (it3.hasNext()) {
                                printWriter.println((SnapshotManager.SnapshotHistory) it3.next());
                            }
                        }
                        Iterator it4 = snapshotRepository.getAll().iterator();
                        while (it4.hasNext()) {
                            SnapshotManager.SnapshotData snapshotData2 = (SnapshotManager.SnapshotData) it4.next();
                            snapshotData2.getClass();
                            printWriter.println("\nSnapshotData:");
                            StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, snapshotData2.source, "\tisFromPairedService = ", BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("\tkey = "), snapshotData2.key, printWriter, "\tsource = "));
                            m2.append(snapshotData2.isFromPairedService);
                            printWriter.println(m2.toString());
                            Iterator it5 = snapshotData2.getWhiches().iterator();
                            while (it5.hasNext()) {
                                Integer num = (Integer) it5.next();
                                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter, "\twhich = ", num.intValue());
                                SnapshotManager.PerWhichSnapshot perWhichSnapshot2 = (SnapshotManager.PerWhichSnapshot) ((HashMap) snapshotData2.perWhichSnapshots).get(num);
                                if (perWhichSnapshot2 != null) {
                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("\tconnectedSnapshotForLiveWallpaper = "), perWhichSnapshot2.connectedSnapshotForLiveWallpaper, printWriter);
                                    WallpaperData wallpaperData = perWhichSnapshot2.wallpaper;
                                    if (wallpaperData != null) {
                                        snapshotManager = snapshotManager2;
                                        it = it4;
                                        snapshotData = snapshotData2;
                                        it2 = it5;
                                        semWallpaperManagerService = semWallpaperManagerService2;
                                        i3 = i4;
                                        perWhichSnapshot = perWhichSnapshot2;
                                        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "\tWallpaperData = ", "\t\t userId = "), wallpaperData.userId, printWriter, "\t\t wallpaperComponent = ");
                                        m3.append(wallpaperData.wallpaperComponent);
                                        printWriter.println(m3.toString());
                                        printWriter.println("\t\t nextWallpaperComponent =" + wallpaperData.nextWallpaperComponent);
                                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("\t\t mWhich = "), wallpaperData.mWhich, printWriter);
                                        try {
                                            printWriter.println("\t\t mTimeCreated = " + wallpaperData.mSemWallpaperData.mTimeCreated);
                                            printWriter.println("\t\t mSemWallpaperData.getWhich() = " + wallpaperData.mSemWallpaperData.mWhich);
                                            printWriter.println("\t\t mSemWallpaperData.getWpType() = " + wallpaperData.mSemWallpaperData.mWpType);
                                            printWriter.println("\t\t cropHint = " + wallpaperData.cropHint);
                                            printWriter.println("\t\t mUri = " + wallpaperData.mSemWallpaperData.mUri);
                                            printWriter.println("\t\t getWallpaperFile() = " + wallpaperData.getWallpaperFile(wallpaperData.mSemWallpaperData.mWpType));
                                            printWriter.println("\t\t wallpaperObserver = " + wallpaperData.wallpaperObserver);
                                            if (wallpaperData.mSemWallpaperData.mWpType == 1) {
                                                printWriter.println("\t\t mMotionPkgName = " + wallpaperData.mSemWallpaperData.mMotionPkgName);
                                            }
                                            if (wallpaperData.mSemWallpaperData.mWpType == 4) {
                                                printWriter.println("\t\t mAnimatedPkgName = " + wallpaperData.mSemWallpaperData.mAnimatedPkgName);
                                            }
                                            if (wallpaperData.mSemWallpaperData.mWpType == 8) {
                                                printWriter.println("\t\t mVideoFilePath = " + wallpaperData.mSemWallpaperData.mVideoFilePath);
                                                printWriter.println("\t\t mVideoPkgName = " + wallpaperData.mSemWallpaperData.mVideoPkgName);
                                                printWriter.println("\t\t mVideoFileName = " + wallpaperData.mSemWallpaperData.mVideoFileName);
                                                printWriter.println("\t\t mVideoDefaultHasBeenUsed = " + wallpaperData.mSemWallpaperData.mVideoDefaultHasBeenUsed);
                                            }
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        semWallpaperManagerService = semWallpaperManagerService2;
                                        snapshotManager = snapshotManager2;
                                        i3 = i4;
                                        it = it4;
                                        snapshotData = snapshotData2;
                                        it2 = it5;
                                        perWhichSnapshot = perWhichSnapshot2;
                                    }
                                    printWriter.println("\tSettingsData = ");
                                    for (Map.Entry entry : perWhichSnapshot.settings.entrySet()) {
                                        printWriter.println(AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(((Integer) entry.getValue()).intValue(), "\t\t", (String) entry.getKey(), " [", "]"));
                                    }
                                } else {
                                    semWallpaperManagerService = semWallpaperManagerService2;
                                    snapshotManager = snapshotManager2;
                                    i3 = i4;
                                    it = it4;
                                    snapshotData = snapshotData2;
                                    it2 = it5;
                                }
                                snapshotManager2 = snapshotManager;
                                it4 = it;
                                snapshotData2 = snapshotData;
                                it5 = it2;
                                semWallpaperManagerService2 = semWallpaperManagerService;
                                i4 = i3;
                            }
                        }
                    }
                    i4++;
                    snapshotManager2 = snapshotManager2;
                    semWallpaperManagerService2 = semWallpaperManagerService2;
                }
            }
            SemWallpaperManagerService semWallpaperManagerService3 = semWallpaperManagerService2;
            ArrayList arrayList = SemWallpaperManagerService.sLogList;
            synchronized (arrayList) {
                try {
                    strArr2 = arrayList.isEmpty() ? null : (String[]) arrayList.toArray(new String[arrayList.size()]);
                } finally {
                }
            }
            printWriter.println(" --------------LogArray--------------");
            if (strArr2 != null) {
                int length = strArr2.length;
                for (int i5 = 0; i5 < length; i5++) {
                    StringBuilder m4 = BatteryService$$ExternalSyntheticOutline0.m(i5, "  #", " ");
                    m4.append(strArr2[i5]);
                    printWriter.println(m4.toString());
                }
            } else {
                printWriter.println("  logArray is null");
            }
            Log.dump("SemWallpaperManagerService", printWriter);
            semWallpaperManagerService3.mResourceInfo.dump(fileDescriptor, printWriter, strArr);
            Map ofEntries = Map.ofEntries(Map.entry("MAIN_HOME", "android.wallpaper.settings_systemui_transparency"), Map.entry("SUB_HOME", "sub_display_system_wallpaper_transparency"), Map.entry("MAIN_LOCK", "lockscreen_wallpaper_transparent"), Map.entry("SUB_LOCK", "sub_display_lockscreen_wallpaper_transparency"));
            Map ofEntries2 = Map.ofEntries(Map.entry("MAIN_LOCK", "lockscreen_wallpaper"), Map.entry("SUB_LOCK", "lockscreen_wallpaper_sub"));
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            try {
                hashMap.put("MAIN_HOME", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries.get("MAIN_HOME"), -1, semWallpaperManagerService3.mContext.getUserId())));
                hashMap.put("MAIN_LOCK", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries.get("MAIN_LOCK"), -1, semWallpaperManagerService3.mContext.getUserId())));
                hashMap2.put("MAIN_LOCK", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries2.get("MAIN_LOCK"), -1, semWallpaperManagerService3.mContext.getUserId())));
                if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                    hashMap.put("SUB_HOME", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries.get("SUB_HOME"), -1, semWallpaperManagerService3.mContext.getUserId())));
                    if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                        hashMap.put("SUB_LOCK", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries.get("SUB_LOCK"), -1, semWallpaperManagerService3.mContext.getUserId())));
                        hashMap2.put("SUB_LOCK", Integer.valueOf(Settings.System.getIntForUser(semWallpaperManagerService3.mContext.getContentResolver(), (String) ofEntries2.get("SUB_LOCK"), -1, semWallpaperManagerService3.mContext.getUserId())));
                    }
                }
            } catch (Exception e2) {
                Log.e("SemWallpaperManagerService", "Error while dumpSettings: " + e2.getMessage());
            }
            printWriter.println("[Wallpaper Settings]");
            if (hashMap.size() > 0) {
                Iterator it6 = hashMap.entrySet().iterator();
                while (true) {
                    String str = "UNKNOWN";
                    if (!it6.hasNext()) {
                        break;
                    }
                    Map.Entry entry2 = (Map.Entry) it6.next();
                    Object key = entry2.getKey();
                    int intValue = ((Integer) entry2.getValue()).intValue();
                    if (intValue == 0) {
                        str = "CUSTOM";
                    } else if (intValue == 1) {
                        str = "PRELOADED";
                    } else if (intValue == 2) {
                        str = "THEME";
                    } else if (intValue == 3) {
                        str = "THEME_SINGLE";
                    }
                    printWriter.println(String.format("\t[%10s: %15s(%2d)] name = %s", key, str, entry2.getValue(), ofEntries.get(entry2.getKey())));
                }
                for (Map.Entry entry3 : hashMap2.entrySet()) {
                    Object key2 = entry3.getKey();
                    int intValue2 = ((Integer) entry3.getValue()).intValue();
                    printWriter.println(String.format("\t[%10s: %15s(%2d)] name = %s", key2, intValue2 != 0 ? intValue2 != 1 ? "UNKNOWN" : "VISIBLE" : "INVISIBLE", entry3.getValue(), ofEntries2.get(entry3.getKey())));
                }
            }
            printWriter.println("SemWallpaperManagerService end");
            this.mWallpaperMap.dump(printWriter);
            this.mLockWallpaperMap.dump(printWriter);
            AssetFileManager assetFileManager = this.mAssetFileManager;
            int i6 = this.mCurrentUserId;
            assetFileManager.getClass();
            printWriter.println("[AssetFileManager]");
            AssetFileManager.printDir(new File(Environment.getUserSystemDirectory(i6), "wallpaper_assets"), printWriter);
            printWriter.println();
            ThumbnailFileManager thumbnailFileManager = ThumbnailFileManager.getInstance();
            int i7 = this.mCurrentUserId;
            thumbnailFileManager.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            printWriter.println("[Tml]");
            File file = new File(Environment.getUserSystemDirectory(i7), "wallpaper_thumbs");
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    printWriter.println("No thumbnail dirs");
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    int length2 = listFiles.length;
                    int i8 = 0;
                    while (i8 < length2) {
                        File file2 = listFiles[i8];
                        if (file2.isDirectory()) {
                            try {
                                int parseInt = Integer.parseInt(file2.getName());
                                int[] iArr = ThumbnailFileManager.sRotationTable;
                                int i9 = 0;
                                while (i9 < 4) {
                                    int i10 = iArr[i9];
                                    File thumbnailFile = ThumbnailFileManager.getThumbnailFile(parseInt, i7, i10);
                                    if (thumbnailFile.exists()) {
                                        i = i7;
                                        String format = String.format("%d %2d %d", Integer.valueOf(i7), Integer.valueOf(parseInt), Integer.valueOf(i10));
                                        if (thumbnailFile.length() == 0) {
                                            printWriter.println(format.concat(" empty file"));
                                        } else {
                                            Bitmap decodeFile = BitmapFactory.decodeFile(thumbnailFile.getAbsolutePath());
                                            if (decodeFile == null) {
                                                printWriter.println(format.concat(" decoding fail"));
                                            } else {
                                                int width = decodeFile.getWidth();
                                                int height = decodeFile.getHeight();
                                                fileArr = listFiles;
                                                String str2 = format + " " + width + " " + height;
                                                if (width * height == 0) {
                                                    printWriter.println(str2 + " incorrect bitmap size");
                                                    i9++;
                                                    listFiles = fileArr;
                                                    i7 = i;
                                                } else {
                                                    printWriter.println(str2);
                                                    try {
                                                        try {
                                                            sb2.append(BitmapStringEncoder.encodeToString(new int[]{0, parseInt, i10}, decodeFile));
                                                            sb2.append("\n");
                                                        } catch (Exception e3) {
                                                            Log.e("ThumbnailFileManager", "dump: e=" + e3, e3);
                                                            printWriter.println(str2 + " e=" + e3);
                                                            if (decodeFile.isRecycled()) {
                                                            }
                                                        }
                                                        if (decodeFile.isRecycled()) {
                                                            i9++;
                                                            listFiles = fileArr;
                                                            i7 = i;
                                                        }
                                                        decodeFile.recycle();
                                                        i9++;
                                                        listFiles = fileArr;
                                                        i7 = i;
                                                    } catch (Throwable th) {
                                                        if (!decodeFile.isRecycled()) {
                                                            decodeFile.recycle();
                                                        }
                                                        throw th;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        i = i7;
                                    }
                                    fileArr = listFiles;
                                    i9++;
                                    listFiles = fileArr;
                                    i7 = i;
                                }
                            } catch (NumberFormatException unused) {
                            }
                        }
                        i8++;
                        listFiles = listFiles;
                        i7 = i7;
                    }
                    if (!sb2.isEmpty()) {
                        printWriter.println(sb2.toString());
                    }
                    Log.i("ThumbnailFileManager", "dump: elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            } else {
                printWriter.println("No main dir");
            }
            Log.dump("WallpaperManagerService", printWriter);
        }
    }

    public final boolean extractColors(WallpaperData wallpaperData) {
        boolean z;
        WallpaperColors wallpaperColors;
        boolean z2;
        String str;
        int i;
        float f;
        if (Flags.offloadColorExtraction()) {
            return !this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
        }
        synchronized (this.mLock) {
            wallpaperData.mIsColorExtractedFromDim = false;
        }
        if (wallpaperData.equals(this.mFallbackWallpaper)) {
            synchronized (this.mLock) {
                try {
                    if (this.mFallbackWallpaper.primaryColors != null) {
                        return true;
                    }
                    WallpaperColors extractDefaultImageWallpaperColors = extractDefaultImageWallpaperColors(wallpaperData);
                    synchronized (this.mLock) {
                        this.mFallbackWallpaper.primaryColors = extractDefaultImageWallpaperColors;
                    }
                    return true;
                } finally {
                }
            }
        }
        synchronized (this.mLock) {
            try {
                if (!this.mImageWallpaper.equals(wallpaperData.wallpaperComponent) && wallpaperData.wallpaperComponent != null) {
                    z = false;
                    wallpaperColors = null;
                    if (z || !wallpaperData.getCropFile().exists()) {
                        z2 = (z || wallpaperData.cropExists() || wallpaperData.sourceExists()) ? false : true;
                        str = null;
                    } else {
                        str = wallpaperData.getCropFile().getAbsolutePath();
                        z2 = false;
                    }
                    i = wallpaperData.wallpaperId;
                    f = wallpaperData.mWallpaperDimAmount;
                }
                z = true;
                wallpaperColors = null;
                if (z) {
                }
                if (z) {
                }
                str = null;
                i = wallpaperData.wallpaperId;
                f = wallpaperData.mWallpaperDimAmount;
            } finally {
            }
        }
        if (str != null) {
            Bitmap decodeFile = BitmapFactory.decodeFile(str);
            if (decodeFile != null) {
                wallpaperColors = WallpaperColors.fromBitmap(decodeFile, f);
                decodeFile.recycle();
            }
        } else if (z2) {
            wallpaperColors = extractDefaultImageWallpaperColors(wallpaperData);
        }
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        if (semWallpaperData.mPrimarySemColors == null) {
            this.mSemService.mLegibilityColor.extractColor(semWallpaperData.mWhich, false);
        } else {
            Log.i("WallpaperManagerService", "extractColors : SemWallpaperColors already exist. Do not extract.");
        }
        if (wallpaperColors == null) {
            Slog.w("WallpaperManagerService", "Cannot extract colors because wallpaper could not be read.");
            return true;
        }
        synchronized (this.mLock) {
            try {
                if (wallpaperData.wallpaperId != i) {
                    Slog.w("WallpaperManagerService", "Not setting primary colors since wallpaper changed");
                    return false;
                }
                wallpaperData.primaryColors = wallpaperColors;
                saveSettingsLocked(wallpaperData.userId, WhichChecker.getMode(wallpaperData.mSemWallpaperData.mWhich));
                return true;
            } finally {
            }
        }
    }

    public final WallpaperColors extractDefaultImageWallpaperColors(WallpaperData wallpaperData) {
        InputStream openDefaultWallpaper;
        synchronized (this.mLock) {
            try {
                WallpaperColors wallpaperColors = this.mCacheDefaultImageWallpaperColors;
                if (wallpaperColors != null) {
                    return wallpaperColors;
                }
                float f = wallpaperData.mWallpaperDimAmount;
                WallpaperColors wallpaperColors2 = null;
                try {
                    openDefaultWallpaper = WallpaperManager.openDefaultWallpaper(this.mContext, 1);
                } catch (IOException e) {
                    Slog.w("WallpaperManagerService", "Can't close default wallpaper stream", e);
                } catch (OutOfMemoryError e2) {
                    Slog.w("WallpaperManagerService", "Can't decode default wallpaper stream", e2);
                }
                try {
                    if (openDefaultWallpaper == null) {
                        Slog.w("WallpaperManagerService", "Can't open default wallpaper stream");
                        if (openDefaultWallpaper != null) {
                            openDefaultWallpaper.close();
                        }
                        return null;
                    }
                    Bitmap decodeStream = BitmapFactory.decodeStream(openDefaultWallpaper, null, new BitmapFactory.Options());
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
                } catch (Throwable th) {
                    if (openDefaultWallpaper != null) {
                        try {
                            openDefaultWallpaper.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final void finalize() {
        int size;
        int size2;
        WallpaperData wallpaperData;
        WallpaperData wallpaperData2;
        super.finalize();
        int i = 0;
        int i2 = 0;
        while (true) {
            WallpaperObserver wallpaperObserver = this.mWallpaperMap;
            synchronized (wallpaperObserver.mSemObserver) {
                size = ((SparseArray) wallpaperObserver.mWallpaper).size();
            }
            if (i2 >= size) {
                break;
            }
            WallpaperObserver wallpaperObserver2 = this.mWallpaperMap;
            synchronized (wallpaperObserver2.mSemObserver) {
                wallpaperData2 = (WallpaperData) ((SparseArray) wallpaperObserver2.mWallpaper).valueAt(i2);
            }
            wallpaperData2.wallpaperObserver.stopWatching();
            i2++;
        }
        while (true) {
            WallpaperObserver wallpaperObserver3 = this.mLockWallpaperMap;
            synchronized (wallpaperObserver3.mSemObserver) {
                size2 = ((SparseArray) wallpaperObserver3.mWallpaper).size();
            }
            if (i >= size2) {
                return;
            }
            WallpaperObserver wallpaperObserver4 = this.mLockWallpaperMap;
            synchronized (wallpaperObserver4.mSemObserver) {
                wallpaperData = (WallpaperData) ((SparseArray) wallpaperObserver4.mWallpaper).valueAt(i);
            }
            wallpaperData.wallpaperObserver.stopWatching();
            i++;
        }
    }

    public final void forceRebindWallpaper(int i, int i2) {
        checkPermission("android.permission.SET_WALLPAPER");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
                Slog.i("WallpaperManagerService", "forceRebindWallpaper: which =" + i + ", component = " + wallpaperSafeLocked.wallpaperComponent);
                bindWallpaperComponentLocked(wallpaperSafeLocked.wallpaperComponent, true, true, wallpaperSafeLocked, null, null);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final WallpaperData[] getActiveWallpapers() {
        WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId, 0);
        WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId, 0);
        boolean z = (wallpaperData == null || wallpaperData.connection == null) ? false : true;
        boolean z2 = (wallpaperData2 == null || wallpaperData2.connection == null) ? false : true;
        return (z && z2) ? new WallpaperData[]{wallpaperData, wallpaperData2} : z ? new WallpaperData[]{wallpaperData} : z2 ? new WallpaperData[]{wallpaperData2} : new WallpaperData[0];
    }

    public final WallpaperData[] getActiveWallpapers(int i) {
        int i2 = (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && i == 1) ? 16 : (Rune.VIRTUAL_DISPLAY_WALLPAPER && this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(i)) ? 32 : 0;
        WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId, i2);
        WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId, i2);
        boolean z = (wallpaperData == null || wallpaperData.connection == null) ? false : true;
        boolean z2 = (wallpaperData2 == null || wallpaperData2.connection == null) ? false : true;
        return (z && z2) ? new WallpaperData[]{wallpaperData, wallpaperData2} : z ? new WallpaperData[]{wallpaperData} : z2 ? new WallpaperData[]{wallpaperData2} : new WallpaperData[0];
    }

    public final WallpaperColors getAdjustedWallpaperColorsOnDimming(WallpaperData wallpaperData) {
        synchronized (this.mLock) {
            try {
                WallpaperColors wallpaperColors = wallpaperData.primaryColors;
                if (wallpaperColors == null || (wallpaperColors.getColorHints() & 4) != 0 || wallpaperData.mWallpaperDimAmount == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    return wallpaperColors;
                }
                return new WallpaperColors(wallpaperColors.getPrimaryColor(), wallpaperColors.getSecondaryColor(), wallpaperColors.getTertiaryColor(), wallpaperColors.getColorHints() & (-4));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getAnimatedPkgName(int i) {
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, getCurrentUserId());
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    Slog.d("WallpaperManagerService", "getAnimatedPkgName: Lock wallpaper data is null. So animated package name is null");
                    return null;
                }
                String str = peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mAnimatedPkgName;
                Slog.d("WallpaperManagerService", "getAnimatedPkgName: userId=0 name=" + str);
                return str;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Rect getBitmapCrop(Point point, int[] iArr, List list) {
        if (!Flags.multiCrop()) {
            throw new UnsupportedOperationException("This method should only be called with the multi crop flag enabled");
        }
        return WallpaperCropper.getTotalCrop(this.mWallpaperCropper.getDefaultCrops(4, getCropMap(list, iArr), point));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r12v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v7, types: [java.util.List] */
    public final List getBitmapCrops(List list, int i, boolean z, int i2) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "getBitmapCrop", null);
        synchronized (this.mLock) {
            try {
                checkPermission("android.permission.READ_WALLPAPER_INTERNAL");
                WallpaperData wallpaperData = i == 2 ? this.mLockWallpaperMap.get(handleIncomingUser, 0) : this.mWallpaperMap.get(handleIncomingUser, 0);
                if (wallpaperData != null && this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)) {
                    this.mWallpaperCropper.getClass();
                    SparseArray relativeCropHints = WallpaperCropper.getRelativeCropHints(wallpaperData);
                    Point point = new Point((int) ((wallpaperData.cropHint.width() / wallpaperData.mSampleSize) + 0.5f), (int) ((wallpaperData.cropHint.height() / wallpaperData.mSampleSize) + 0.5f));
                    if (point.equals(0, 0)) {
                        return null;
                    }
                    SparseArray defaultCrops = this.mWallpaperCropper.getDefaultCrops(4, relativeCropHints, point);
                    SparseArray sparseArray = new SparseArray();
                    for (int i3 = 0; i3 < defaultCrops.size(); i3++) {
                        int keyAt = defaultCrops.keyAt(i3);
                        if (relativeCropHints.contains(keyAt)) {
                            sparseArray.put(keyAt, (Rect) defaultCrops.get(keyAt));
                        }
                    }
                    ?? arrayList = new ArrayList();
                    boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(this.mWallpaperCropper.getCrop((Point) it.next(), point, sparseArray, z2));
                    }
                    if (z) {
                        arrayList = WallpaperCropper.getOriginalCropHints(wallpaperData, arrayList);
                    }
                    return arrayList;
                }
                return null;
            } finally {
            }
        }
    }

    public final WallpaperInfo getCoverWallpaperInfo(int i, ComponentName componentName) {
        if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
            return null;
        }
        try {
            return getWallpaperInfo(new Intent("com.samsung.android.service.wallpaper.CoverWallpaperService"), this.mIPackageManager.getServiceInfo(componentName, 4224L, i), i, true);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int getCurrentUserId() {
        int i = this.mCurrentUserId;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public WallpaperData getCurrentWallpaperData(int i, int i2) {
        WallpaperData wallpaperData;
        synchronized (this.mLock) {
            try {
                wallpaperData = (WhichChecker.isSystem(i) ? this.mWallpaperMap : this.mLockWallpaperMap).get(i2, 0);
            } catch (Throwable th) {
                throw th;
            }
        }
        return wallpaperData;
    }

    public final int getDesktopMode() {
        int i;
        DesktopMode desktopMode = this.mSemService.mDesktopMode;
        synchronized (desktopMode.mDesktopModeLock) {
            i = desktopMode.mDesktopMode;
        }
        return i;
    }

    public final String getDeviceColor() {
        return this.mSemService.mCMFWallpaper.getDeviceColor();
    }

    public final int getDisplayId(int i) {
        return this.mSemService.getDisplayId(i);
    }

    public final IWallpaperEngine getEngine(int i, int i2, int i3) {
        WallpaperConnection wallpaperConnection;
        WallpaperConnection wallpaperConnection2;
        int mode = WhichChecker.getMode(i);
        WallpaperData wallpaperData = this.mFallbackWallpaper;
        WallpaperData wallpaperData2 = (wallpaperData == null || (wallpaperConnection2 = wallpaperData.connection) == null || !wallpaperConnection2.containsDisplay(i3)) ? this.mWallpaperMap.get(i2, mode) : this.mFallbackWallpaper;
        IWallpaperEngine iWallpaperEngine = null;
        if (wallpaperData2 == null || (wallpaperConnection = wallpaperData2.connection) == null) {
            return null;
        }
        synchronized (this.mLock) {
            for (int i4 = 0; i4 < wallpaperConnection.mDisplayConnector.size(); i4++) {
                try {
                    int i5 = ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
                    int i6 = ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
                    if (i5 == i3 || i6 == i) {
                        iWallpaperEngine = ((DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mEngine;
                        break;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return iWallpaperEngine;
    }

    public final List getFutureBitmapCrops(Point point, List list, int[] iArr, List list2) {
        SparseArray defaultCrops = this.mWallpaperCropper.getDefaultCrops(4, getCropMap(list2, iArr), point);
        ArrayList arrayList = new ArrayList();
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.mWallpaperCropper.getCrop((Point) it.next(), point, defaultCrops, z));
        }
        return arrayList;
    }

    public final Handler getHandlerForBindingWallpaperLocked() {
        if (android.multiuser.Flags.bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() && !this.mInitialUserSwitch) {
            if (this.mHandlerThread == null) {
                ServiceThread serviceThread = new ServiceThread(-2, "WallpaperManagerService", true);
                this.mHandlerThread = serviceThread;
                serviceThread.start();
            }
            return this.mHandlerThread.getThreadHandler();
        }
        return this.mContext.getMainThreadHandler();
    }

    public final int getHeightHint(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mWallpaperDisplayHelper.mDisplayManager.getDisplay(i) == null) {
                    throw new IllegalArgumentException("Cannot find display with id=" + i);
                }
                if (this.mWallpaperMap.get(UserHandle.getCallingUserId(), 0) == null) {
                    return 0;
                }
                return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mHeight;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getHighlightFilterState(int i) {
        int highlightFilterState;
        synchronized (this.mLock) {
            highlightFilterState = getHighlightFilterState(peekPairingConsideredWallpaperDataLocked(i, getCurrentUserId()));
        }
        return highlightFilterState;
    }

    public final int getHighlightFilterState(WallpaperData wallpaperData) {
        if (wallpaperData == null) {
            Slog.e("WallpaperManagerService", "getHighlightFilterState : filterState=-1, failed to get wallpaper");
            return -1;
        }
        int i = wallpaperData.mSemWallpaperData.mWhich;
        int i2 = wallpaperData.userId;
        if (!WhichChecker.isSystem(i)) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=0, not System type wallpaper data", "WallpaperManagerService");
            return 0;
        }
        if (wallpaperData.mSemWallpaperData.mIsPreloaded) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=0, preloaded wallpaper", "WallpaperManagerService");
            return 0;
        }
        if (WhichChecker.isWatchFaceDisplay(i) || WhichChecker.isVirtualDisplay(i) || WhichChecker.isDex(i)) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=0, unsupported mode", "WallpaperManagerService");
            return 0;
        }
        if (getCurrentUserId() == 0) {
            if (TextUtils.isEmpty(Settings.System.getStringForUser(this.mContext.getContentResolver(), "current_sec_active_themepackage", 0))) {
                if (WhichChecker.isSystem(wallpaperData.mSemWallpaperData.mWhich)) {
                    String wallpaperHistory = wallpaperData.mSemWallpaperData.getWallpaperHistory();
                    if (!TextUtils.isEmpty(wallpaperHistory) && wallpaperHistory.equals("com.samsung.android.themecenter")) {
                        Log.d("SemWallpaperData", "Theme contents.");
                    }
                }
            }
            BootReceiver$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=0, open theme wallpaper enabled", "WallpaperManagerService");
            return 0;
        }
        SemWallpaperColors semWallpaperColors = wallpaperData.mSemWallpaperData.mPrimarySemColors;
        if (semWallpaperColors == null) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=-1, primary color is not ready", "WallpaperManagerService");
            return -1;
        }
        SemWallpaperColors.Item item = semWallpaperColors.get(64L);
        if (item == null) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "getHighlightFilterState : which=", ", filterState=-1, failed to get home screen color item", "WallpaperManagerService");
            return -1;
        }
        int i3 = item.getFontColor() == 0 ? 1 : 2;
        DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i3, "getHighlightFilterState : which=", ", filterState=", ", callerUserId= "), i2, "WallpaperManagerService");
        return i3;
    }

    public final String getLastCallingPackage(int i) {
        WallpaperData peekPairingConsideredWallpaperDataLocked;
        if (!isSignedWithPlatformSignature(Binder.getCallingUid())) {
            checkPermission("android.permission.SET_WALLPAPER");
        }
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, currentUserId);
        }
        if (peekPairingConsideredWallpaperDataLocked != null) {
            return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getLastCallingPackage(false);
        }
        Slog.e("WallpaperManagerService", "getLastCallingPackage wallpaper == null");
        return null;
    }

    public final String getLastCallingPackageWithPrefix(int i, boolean z) {
        WallpaperData peekPairingConsideredWallpaperDataLocked;
        if (!isSignedWithPlatformSignature(Binder.getCallingUid())) {
            checkPermission("android.permission.SET_WALLPAPER");
        }
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, currentUserId);
        }
        if (peekPairingConsideredWallpaperDataLocked != null) {
            return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.getLastCallingPackage(z);
        }
        Slog.e("WallpaperManagerService", "getLastCallingPackage wallpaper == null");
        return null;
    }

    public final WallpaperData getLastWallpaper(int i) {
        synchronized (this.mLock) {
            try {
                if (WhichChecker.isDex(i) && !this.mSemService.mDesktopMode.isDesktopSingleMode()) {
                    return WhichChecker.isLock(i) ? this.mLastDexLockWallpaper : this.mLastDexWallpaper;
                }
                if (WhichChecker.isSubDisplay(i)) {
                    return WhichChecker.isLock(i) ? this.mLastSubLockWallpaper : this.mLastSubWallpaper;
                }
                if (WhichChecker.isVirtualDisplay(i)) {
                    return this.mLastVirtualWallpaper;
                }
                if (WhichChecker.isLock(i)) {
                    return this.mLastLockWallpaper;
                }
                return this.mLastWallpaper;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getLegacyDeviceColor() {
        CMFWallpaper cMFWallpaper = this.mSemService.mCMFWallpaper;
        Log.d("CMFWallpaper", "legacyDeviceColor = " + cMFWallpaper.mLegacyDeviceColor + " , color code = " + cMFWallpaper.mDeviceColor);
        return cMFWallpaper.mLegacyDeviceColor;
    }

    public final int getLidState() {
        return this.mSemService.mSubDisplayMode.mLidState;
    }

    public final ParcelFileDescriptor getLockWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, Bundle bundle, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (isSignedWithPlatformSignature(callingUid)) {
            return getWallpaper(iWallpaperManagerCallback, i2, bundle, i, 0, true, true);
        }
        throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "The calling app does not have the required permission. uid = "));
    }

    public final int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }

    public final String getMotionWallpaperPkgName(int i) {
        int currentUserId = getCurrentUserId();
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, currentUserId);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    Slog.d("WallpaperManagerService", "getMotionWallpaperPkgName: Lock wallpaper data is null. So motion package name is null");
                    return null;
                }
                String str = peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mMotionPkgName;
                Slog.d("WallpaperManagerService", "getMotionWallpaperPkgName: userId=" + currentUserId + " name=" + str + " , which=" + modeEnsuredWhich);
                return str;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getName() {
        if (Binder.getCallingUid() != 1000) {
            throw new RuntimeException("getName() can only be called from the system process");
        }
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = this.mWallpaperMap.get(0, 0);
                if (wallpaperData == null) {
                    return null;
                }
                return wallpaperData.name;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor getScreenshotFileDescriptor(int i, int i2, Bundle bundle) {
        int callingUid = Binder.getCallingUid();
        if (!isSignedWithPlatformSignature(callingUid)) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "Calling app does not have required permission. uid = "));
        }
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        int sourceWhich = getSourceWhich(modeEnsuredWhich, i2);
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, sourceWhich);
                if (peekWallpaperDataLocked == null) {
                    Slog.e("WallpaperManagerService", "getScreenshotFileDescriptor: no wallpaper data.which=" + modeEnsuredWhich + ", srcWhich=" + sourceWhich);
                    return null;
                }
                if (peekWallpaperDataLocked.connection == null) {
                    Slog.e("WallpaperManagerService", "getScreenshotFileDescriptor: wallpaper not connected.which=" + modeEnsuredWhich + ", srcWhich=" + sourceWhich);
                    return null;
                }
                ComponentName componentName = peekWallpaperDataLocked.wallpaperComponent;
                if (componentName == null) {
                    componentName = this.mImageWallpaper;
                }
                ProviderRequester providerRequester = this.mSemService.mLiveWallpaperHelper.mProviderRequester;
                providerRequester.getClass();
                if (ProviderRequester.isValidComponentName(componentName)) {
                    String packageName = componentName.getPackageName();
                    String className = componentName.getClassName();
                    StringBuilder sb = new StringBuilder("requestScreenshot : ");
                    sb.append(componentName);
                    sb.append(", which=");
                    sb.append(sourceWhich);
                    sb.append(", user=");
                    sb.append(i2);
                    sb.append(", hasExtras=");
                    sb.append((bundle == null || bundle.isEmpty()) ? false : true);
                    Log.i("ProviderRequester", sb.toString());
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("which", sourceWhich);
                    bundle2.putInt("user_id", i2);
                    bundle2.putString("wallpaper_service_class_name", className);
                    if (bundle != null) {
                        bundle2.putAll(bundle);
                    }
                    Bundle invokeProviderCall = providerRequester.invokeProviderCall(i2, packageName, "get_screenshot", bundle2);
                    r5 = invokeProviderCall != null ? (ParcelFileDescriptor) invokeProviderCall.getParcelable("image_file_descriptor", ParcelFileDescriptor.class) : null;
                    if (r5 == null) {
                        Log.w("ProviderRequester", "requestScreenshot : fd is null");
                    }
                } else {
                    Log.e("ProviderRequester", "requestScreenshot : service component is invalid - " + componentName);
                }
                Slog.d("WallpaperManagerService", "getScreenshotFileDescriptor: which=" + modeEnsuredWhich + ", svc=" + componentName + ", fd=" + r5);
                return r5;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SemWallpaperColors getSemWallpaperColors(WallpaperData wallpaperData, boolean z) {
        SemWallpaperColors[] semWallpaperColorsArr;
        if (wallpaperData == null) {
            Log.addLogString("WallpaperManagerService", "getSemWallpaperColors: wallpaper == null");
            return null;
        }
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        SemWallpaperColors semWallpaperColors = semWallpaperData.mPrimarySemColors;
        SemWallpaperColors semWallpaperColors2 = semWallpaperData.mDlsSemColors;
        if (this.mCurrentUserId == 0 && semWallpaperColors2 != null) {
            Log.d("WallpaperManagerService", "getSemWallpaperColors: return dlsSemColors");
            return semWallpaperColors2;
        }
        if (z || this.mSemService.mOrientation != 2) {
            return semWallpaperColors;
        }
        int i = semWallpaperData.mWhich;
        if (!((WhichChecker.isSystem(i) && this.mSemService.mLegibilityColor.mAllowScreenRotateSystem) || (WhichChecker.isLock(i) && this.mSemService.mLegibilityColor.mAllowScreenRotateLock)) || (semWallpaperColorsArr = wallpaperData.mSemWallpaperData.mLandscapeColors) == null) {
            return semWallpaperColors;
        }
        SemWallpaperColors semWallpaperColors3 = semWallpaperColorsArr[0];
        if (semWallpaperColorsArr.length != 2) {
            return semWallpaperColors3;
        }
        WindowManager windowManager = (WindowManager) this.mSemService.mContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);
        DisplayInfo displayInfo = new DisplayInfo();
        defaultDisplay.getDisplayInfo(displayInfo);
        return displayInfo.rotation == 3 ? semWallpaperColorsArr[1] : semWallpaperColors3;
    }

    public final int getSnapshotCount(int i) {
        checkPermission("android.permission.SET_WALLPAPER");
        return this.mSemService.getSnapshotCount(i);
    }

    public final int[] getSnapshotKeys(String str, int i) {
        checkPermission("android.permission.SET_WALLPAPER");
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = semWallpaperManagerService.mSnapshotManager.getRepositroy(semWallpaperManagerService.mCurrentUserId).getAll().iterator();
        while (it.hasNext()) {
            SnapshotManager.SnapshotData snapshotData = (SnapshotManager.SnapshotData) it.next();
            if (snapshotData != null && TextUtils.equals(str, snapshotData.source) && snapshotData.hasWallpaperData(i)) {
                arrayList.add(Integer.valueOf(snapshotData.key));
            }
        }
        return Arrays.stream((Integer[]) arrayList.toArray(new Integer[arrayList.size()])).mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
    }

    public final int getSourceWhich(int i, int i2) {
        return (WhichChecker.containsSystem(i) || this.mSemService.isSystemAndLockPaired(i, i2)) ? WhichChecker.getMode(i) | 1 : i;
    }

    public final String getVideoFileName(int i) {
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        int currentUserId = getCurrentUserId();
        int modeEnsuredWhich = semWallpaperManagerService.getModeEnsuredWhich(i);
        WallpaperData onWallpaperDataRequested = semWallpaperManagerService.mCallback.onWallpaperDataRequested(currentUserId, modeEnsuredWhich);
        String str = onWallpaperDataRequested.mSemWallpaperData.mVideoFileName;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(currentUserId, modeEnsuredWhich, "semGetVideoFileName: userId=", ", which = ", ", videoFileName = ");
        m.append(str);
        Slog.d("SemWallpaperManagerService", m.toString());
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        SemWallpaperData semWallpaperData = onWallpaperDataRequested.mSemWallpaperData;
        boolean z = semWallpaperData.mVideoDefaultHasBeenUsed;
        boolean z2 = semWallpaperData.mIsPreloaded;
        AnyMotionDetector$$ExternalSyntheticOutline0.m("SemWallpaperManagerService", ArrayUtils$$ExternalSyntheticOutline0.m(currentUserId, modeEnsuredWhich, "semGetVideoFileName: userId = ", ", which = ", ", isDefault = "), z);
        if (!z || !z2) {
            return null;
        }
        String defaultVideoWallpaperFileName = semWallpaperManagerService.mResourceInfo.getDefaultVideoWallpaperFileName(onWallpaperDataRequested.mSemWallpaperData.mWhich);
        SemWallpaperData semWallpaperData2 = onWallpaperDataRequested.mSemWallpaperData;
        semWallpaperData2.mVideoPkgName = "com.samsung.android.wallpaper.res";
        semWallpaperData2.mVideoFileName = defaultVideoWallpaperFileName;
        return defaultVideoWallpaperFileName;
    }

    public final String getVideoFilePath(int i) {
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        int currentUserId = getCurrentUserId();
        int modeEnsuredWhich = semWallpaperManagerService.getModeEnsuredWhich(i);
        String str = semWallpaperManagerService.mCallback.onWallpaperDataRequested(currentUserId, modeEnsuredWhich).mSemWallpaperData.mVideoFilePath;
        BootReceiver$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(currentUserId, modeEnsuredWhich, "semGetVideoFilePath: userId = ", ", which = ", ", path = "), str, "SemWallpaperManagerService");
        return str;
    }

    public final String getVideoPackage(int i) {
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        int currentUserId = getCurrentUserId();
        int modeEnsuredWhich = semWallpaperManagerService.getModeEnsuredWhich(i);
        WallpaperData onWallpaperDataRequested = semWallpaperManagerService.mCallback.onWallpaperDataRequested(currentUserId, modeEnsuredWhich);
        String str = onWallpaperDataRequested.mSemWallpaperData.mVideoPkgName;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(currentUserId, modeEnsuredWhich, "semGetVideoPackage: userId = ", ", which = ", ", pkg = ");
        m.append(str);
        Slog.d("SemWallpaperManagerService", m.toString());
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        boolean z = onWallpaperDataRequested.mSemWallpaperData.mVideoDefaultHasBeenUsed;
        AnyMotionDetector$$ExternalSyntheticOutline0.m("SemWallpaperManagerService", ArrayUtils$$ExternalSyntheticOutline0.m(currentUserId, modeEnsuredWhich, "semGetVideoPackage: userId = ", ", which = ", ", isDefault = "), z);
        if (!z) {
            return null;
        }
        String defaultVideoWallpaperFileName = semWallpaperManagerService.mResourceInfo.getDefaultVideoWallpaperFileName(onWallpaperDataRequested.mSemWallpaperData.mWhich);
        SemWallpaperData semWallpaperData = onWallpaperDataRequested.mSemWallpaperData;
        semWallpaperData.mVideoPkgName = "com.samsung.android.wallpaper.res";
        semWallpaperData.mVideoFileName = defaultVideoWallpaperFileName;
        return "com.samsung.android.wallpaper.res";
    }

    public final ParcelFileDescriptor getWallpaper(IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, int i3, boolean z, boolean z2) {
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "getWallpaper: which = ", ", wallpaperUserId = ", ", wpType = ");
        m.append(i3);
        m.append(", isDexEnabled = ");
        m.append(this.mSemService.mDesktopMode.isDesktopModeEnabled(i));
        m.append(", getCropped = ");
        m.append(z2);
        m.append(", includeCopiedFile = ");
        m.append(z);
        Log.d("WallpaperManagerService", m.toString());
        if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER && this.mSemService.mDesktopMode.isDesktopSingleMode()) {
            i = (i & (-9)) | 4;
        }
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "getWallpaper", null);
        if (!WhichChecker.isSingleType(modeEnsuredWhich)) {
            throw new IllegalArgumentException("Must specify exactly one kind of wallpaper to read");
        }
        try {
            if (this.mStatusBarService.isSysUiSafeModeEnabled()) {
                return null;
            }
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("SAFEMODE Exception occurs! "), "WallpaperManagerService");
        }
        synchronized (this.mLock) {
            try {
                WallpaperObserver wallpaperObserver = WhichChecker.isLock(modeEnsuredWhich) ? this.mLockWallpaperMap : this.mWallpaperMap;
                WallpaperData wallpaperData = wallpaperObserver.get(handleIncomingUser, WhichChecker.getMode(modeEnsuredWhich));
                if (wallpaperData == null) {
                    int mode = WhichChecker.getMode(modeEnsuredWhich);
                    loadSettingsLocked(handleIncomingUser, modeEnsuredWhich, mode, false);
                    wallpaperData = wallpaperObserver.get(handleIncomingUser, mode);
                }
                if (wallpaperData == null) {
                    return null;
                }
                SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
                int i4 = semWallpaperData.mWpType;
                if (i4 != 0 && i4 != -1) {
                    if (semWallpaperData.mWaitingForUnlockUser) {
                        Log.w("WallpaperManagerService", "getWallpaper: Error getting wallpaper before unlock user.");
                        return null;
                    }
                    boolean supportsSamsungLiveWallpaperProvider = this.mSemService.mLiveWallpaperHelper.supportsSamsungLiveWallpaperProvider(wallpaperData);
                    if (!supportsSamsungLiveWallpaperProvider) {
                        return null;
                    }
                    synchronized (this.mLock) {
                        if (iWallpaperManagerCallback != null) {
                            try {
                                wallpaperData.callbacks.register(iWallpaperManagerCallback);
                            } finally {
                            }
                        }
                    }
                    return this.mSemService.getThumbnailFileDescriptor(getSourceWhich(modeEnsuredWhich, handleIncomingUser), handleIncomingUser, 0);
                }
                Log.d("WallpaperManagerService", "getWallpaper: which=" + modeEnsuredWhich + ", isCopied=" + wallpaperData.mSemWallpaperData.mIsCopied);
                if (!z && WhichChecker.isLock(modeEnsuredWhich) && wallpaperData.mSemWallpaperData.mIsCopied) {
                    Log.d("WallpaperManagerService", "getWallpaper: Returns null.");
                    return null;
                }
                WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
                if (bundle != null) {
                    bundle.putInt("width", displayDataOrCreate.mWidth);
                    bundle.putInt("height", displayDataOrCreate.mHeight);
                }
                if (iWallpaperManagerCallback != null) {
                    wallpaperData.callbacks.register(iWallpaperManagerCallback);
                }
                File cropFile = z2 ? wallpaperData.getCropFile() : wallpaperData.getWallpaperFile(0);
                if (cropFile != null && cropFile.exists()) {
                    return ParcelFileDescriptor.open(cropFile, 268435456);
                }
                return null;
            } catch (FileNotFoundException e2) {
                Slog.w("WallpaperManagerService", "Error getting wallpaper", e2);
                return null;
            } finally {
            }
        }
    }

    public final ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) {
        return getWallpaperWithFeature(str, null, iWallpaperManagerCallback, i, bundle, i2, true, false, -1);
    }

    public final ParcelFileDescriptor getWallpaperAssetFile(String str, int i, int i2, String str2) {
        boolean hasPermission = hasPermission("android.permission.READ_WALLPAPER_INTERNAL");
        boolean z = Binder.getCallingUid() == 1000;
        if (!hasPermission && !z) {
            throw new SecurityException("No permission to invoke getWallpaperAssetFile()");
        }
        this.mAssetFileManager.getClass();
        File file = new File(AssetFileManager.getBaseAssetDir(i, i2, false), str2);
        if (!file.exists()) {
            Log.i("AssetFileManager", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "getAssetFile: ", str2, " not exists! (which = ", ")"));
            return null;
        }
        try {
            return ParcelFileDescriptor.open(file, 268435456);
        } catch (FileNotFoundException e) {
            Log.w("Error getting wallpaper asset file", e);
            return null;
        }
    }

    public final Bundle getWallpaperAssets(int i, int i2) {
        boolean hasPermission = hasPermission("android.permission.READ_WALLPAPER_INTERNAL");
        boolean z = Binder.getCallingUid() == 1000;
        if (!hasPermission && !z) {
            throw new SecurityException("No permission to invoke getWallpaperAssetFile()");
        }
        this.mAssetFileManager.getClass();
        File baseAssetDir = AssetFileManager.getBaseAssetDir(i, i2, false);
        if (!baseAssetDir.exists()) {
            Log.i("AssetFileManager", "getAssets: not exists! (which = " + i + ")");
            return null;
        }
        Bundle bundle = new Bundle();
        try {
            String[] list = baseAssetDir.list();
            if (list != null && list.length > 0) {
                for (int i3 = 0; i3 < list.length; i3++) {
                    bundle.putParcelable(list[i3], ParcelFileDescriptor.open(new File(baseAssetDir, list[i3]), 268435456));
                }
            }
        } catch (FileNotFoundException e) {
            Log.w("getAssets: Error getting wallpaper asset file", e);
        }
        return bundle;
    }

    public final RemoteCallbackList getWallpaperCallbacks(int i, int i2) {
        SparseArray sparseArray = (SparseArray) this.mColorsChangedListeners.get(i);
        if (sparseArray != null) {
            return (RemoteCallbackList) sparseArray.get(i2);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.WallpaperColors getWallpaperColors(int r10, int r11, int r12) {
        /*
            r9 = this;
            boolean r0 = com.samsung.android.wallpaper.utils.WhichChecker.isSingleType(r10)
            if (r0 == 0) goto L89
            com.samsung.server.wallpaper.SemWallpaperManagerService r0 = r9.mSemService
            com.samsung.server.wallpaper.SubDisplayMode r0 = r0.mSubDisplayMode
            int r10 = r0.getFolderStateBasedWhich(r10)
            java.lang.String r0 = "WallpaperManagerService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getWallpaperColors: which = "
            r1.<init>(r2)
            r1.append(r10)
            java.lang.String r2 = " , userId = "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            com.samsung.server.wallpaper.Log.d(r0, r1)
            int r2 = android.os.Binder.getCallingPid()
            int r3 = android.os.Binder.getCallingUid()
            java.lang.String r7 = "getWallpaperColors"
            r5 = 0
            r6 = 1
            r8 = 0
            r4 = r11
            int r11 = android.app.ActivityManager.handleIncomingUser(r2, r3, r4, r5, r6, r7, r8)
            java.lang.Object r0 = r9.mLock
            monitor-enter(r0)
            boolean r1 = com.samsung.android.wallpaper.utils.WhichChecker.isLock(r10)     // Catch: java.lang.Throwable -> L4a
            r2 = 0
            if (r1 == 0) goto L4c
            com.android.server.wallpaper.WallpaperData r1 = r9.peekWallpaperDataLocked(r11, r10)     // Catch: java.lang.Throwable -> L4a
            goto L4d
        L4a:
            r9 = move-exception
            goto L87
        L4c:
            r1 = r2
        L4d:
            if (r1 != 0) goto L6c
            int r10 = com.samsung.android.wallpaper.utils.WhichChecker.getMode(r10)     // Catch: java.lang.Throwable -> L4a
            com.android.server.wallpaper.WallpaperData r1 = r9.mFallbackWallpaper     // Catch: java.lang.Throwable -> L4a
            if (r1 == 0) goto L65
            com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection r1 = r1.connection     // Catch: java.lang.Throwable -> L4a
            if (r1 == 0) goto L65
            boolean r12 = r1.containsDisplay(r12)     // Catch: java.lang.Throwable -> L4a
            if (r12 == 0) goto L65
            com.android.server.wallpaper.WallpaperData r10 = r9.mFallbackWallpaper     // Catch: java.lang.Throwable -> L4a
        L63:
            r1 = r10
            goto L6c
        L65:
            com.android.server.wallpaper.WallpaperManagerService$WallpaperObserver r12 = r9.mWallpaperMap     // Catch: java.lang.Throwable -> L4a
            com.android.server.wallpaper.WallpaperData r10 = r12.get(r11, r10)     // Catch: java.lang.Throwable -> L4a
            goto L63
        L6c:
            if (r1 != 0) goto L70
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4a
            return r2
        L70:
            android.app.WallpaperColors r10 = r1.primaryColors     // Catch: java.lang.Throwable -> L4a
            if (r10 == 0) goto L7b
            boolean r10 = r1.mIsColorExtractedFromDim     // Catch: java.lang.Throwable -> L4a
            if (r10 == 0) goto L79
            goto L7b
        L79:
            r10 = 0
            goto L7c
        L7b:
            r10 = 1
        L7c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4a
            if (r10 == 0) goto L82
            r9.extractColors(r1)
        L82:
            android.app.WallpaperColors r9 = r9.getAdjustedWallpaperColorsOnDimming(r1)
            return r9
        L87:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4a
            throw r9
        L89:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "which should be either FLAG_LOCK or FLAG_SYSTEM"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.getWallpaperColors(int, int, int):android.app.WallpaperColors");
    }

    public final Bundle getWallpaperComponentExtras(int i, int i2) {
        return getWallpaperExtras(i, i2);
    }

    public final float getWallpaperDimAmount() {
        checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId, 0);
                if (wallpaperData != null || (wallpaperData = this.mWallpaperMap.get(0, 0)) != null) {
                    return wallpaperData.mWallpaperDimAmount;
                }
                Slog.e("WallpaperManagerService", "getWallpaperDimAmount: wallpaperData is null");
                return FullScreenMagnificationGestureHandler.MAX_SCALE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Bundle getWallpaperExtras(int i, int i2) {
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(modeEnsuredWhich, i2);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    Slog.i("WallpaperManagerService", "getWallpaperExtras: wallpaper data is null");
                    return null;
                }
                return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mExternalParams;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getWallpaperIdForUser(int i, int i2) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "getWallpaperIdForUser", null);
        if (!WhichChecker.isSingleType(i)) {
            throw new IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
        boolean isLock = WhichChecker.isLock(i);
        WallpaperObserver wallpaperObserver = isLock ? this.mLockWallpaperMap : this.mWallpaperMap;
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = wallpaperObserver.get(handleIncomingUser, WhichChecker.getMode(i));
                if (wallpaperData == null) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "getWallpaperIdForUser which = ", " , return -1 default", "WallpaperManagerService");
                    return -1;
                }
                if (!isLock || !wallpaperData.mSemWallpaperData.mIsCopied) {
                    Slog.w("WallpaperManagerService", "getWallpaperIdForUser wallpaper = " + wallpaperData);
                    return wallpaperData.wallpaperId;
                }
                Slog.w("WallpaperManagerService", "getWallpaperIdForUser which = " + i + " , return -1");
                return -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final WallpaperInfo getWallpaperInfo(int i) {
        return getWallpaperInfoWithFlags(1, i);
    }

    public final WallpaperInfo getWallpaperInfo(Intent intent, ServiceInfo serviceInfo, int i, boolean z) {
        String str;
        String str2;
        try {
            List list = this.mIPackageManager.queryIntentServices(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 128L, i).getList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                ServiceInfo serviceInfo2 = ((ResolveInfo) list.get(i2)).serviceInfo;
                if (serviceInfo2 != null && serviceInfo != null && (str = serviceInfo2.name) != null && str.equals(serviceInfo.name) && (str2 = serviceInfo2.packageName) != null && str2.equals(serviceInfo.packageName)) {
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
                if (serviceInfo2 != null && (serviceInfo2.name == null || serviceInfo2.packageName == null)) {
                    Log.w("WallpaperManagerService", "getWallpaperInfo: Invalid ServiceInfo, name = " + serviceInfo2.name + " , packageName = " + serviceInfo2.packageName);
                }
            }
            return null;
        } catch (RemoteException e3) {
            Slog.w("WallpaperManagerService", "getWallpaperInfo: Failed getting wallpaper info ", e3);
            return null;
        }
    }

    public final ParcelFileDescriptor getWallpaperInfoFile(int i) {
        synchronized (this.mLock) {
            try {
                File file = new File(Environment.getUserSystemDirectory(i), "wallpaper_info.xml");
                if (!file.exists()) {
                    return null;
                }
                return ParcelFileDescriptor.open(file, 268435456);
            } catch (FileNotFoundException e) {
                Slog.w("WallpaperManagerService", "Error getting wallpaper info file", e);
                return null;
            }
        }
    }

    public final WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) {
        WallpaperConnection wallpaperConnection;
        WallpaperInfo wallpaperInfo;
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "getWallpaperInfo", null);
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = WhichChecker.isLock(i) ? this.mLockWallpaperMap.get(handleIncomingUser, 0) : this.mWallpaperMap.get(handleIncomingUser, 0);
                if (wallpaperData != null && (wallpaperConnection = wallpaperData.connection) != null && (wallpaperInfo = wallpaperConnection.mInfo) != null) {
                    if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL") && !this.mPackageManagerInternal.canQueryPackage(Binder.getCallingUid(), wallpaperInfo.getComponent().getPackageName())) {
                        return null;
                    }
                    return wallpaperInfo;
                }
                return null;
            } finally {
            }
        }
    }

    public final int getWallpaperOrientation(int i, int i2) {
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, i2);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    Slog.i("WallpaperManagerService", "getWallpaperOrientation: wallpaper data is null");
                    return 0;
                }
                return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mOrientation;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final WallpaperData getWallpaperSafeLocked(int i, int i2) {
        WallpaperData wallpaperData;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "getWallpaperSafeLocked, userId = ", ", which = ", ", caller = ");
        m.append(Debug.getCaller());
        Log.d("WallpaperManagerService", m.toString());
        WallpaperObserver wallpaperObserver = WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap;
        WallpaperData wallpaperData2 = wallpaperObserver.get(i, WhichChecker.getMode(i2));
        if (wallpaperData2 != null) {
            return wallpaperData2;
        }
        Log.d("WallpaperManagerService", "getWallpaperSafeLocked, didn't find wallpaper.");
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i2);
        loadSettingsLocked(i, modeEnsuredWhich, WhichChecker.getMode(modeEnsuredWhich), false);
        WallpaperData wallpaperData3 = wallpaperObserver.get(i, WhichChecker.getMode(modeEnsuredWhich));
        if (wallpaperData3 != null) {
            return wallpaperData3;
        }
        Log.d("WallpaperManagerService", "getWallpaperSafeLocked, didn't find yet.");
        int i3 = 4;
        if (WhichChecker.isLock(modeEnsuredWhich)) {
            if (WhichChecker.isDex(modeEnsuredWhich)) {
                i3 = 8;
            } else if (WhichChecker.isSubDisplay(modeEnsuredWhich)) {
                i3 = 16;
            }
            wallpaperData = new WallpaperData(i, i3 | 2);
            this.mLockWallpaperMap.put(i, i3, wallpaperData);
        } else {
            Slog.wtf("WallpaperManagerService", "Didn't find wallpaper in non-lock case!");
            if (WhichChecker.isDex(modeEnsuredWhich)) {
                i3 = 8;
            } else if (WhichChecker.isSubDisplay(modeEnsuredWhich)) {
                i3 = 16;
            } else if (WhichChecker.isVirtualDisplay(modeEnsuredWhich)) {
                i3 = 32;
            }
            wallpaperData = new WallpaperData(i, i3 | 1);
            this.mWallpaperMap.put(i, i3, wallpaperData);
        }
        return wallpaperData;
    }

    public final ParcelFileDescriptor getWallpaperThumbnailFileDescriptor(int i, int i2, int i3, int i4, int i5) {
        File file;
        ParcelFileDescriptor parcelFileDescriptor;
        int callingUid = Binder.getCallingUid();
        if (!isSignedWithPlatformSignature(callingUid)) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "Calling app does not have required permission. uid = "));
        }
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i3);
        int sourceWhich = getSourceWhich(modeEnsuredWhich, i2);
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, sourceWhich);
                if (peekWallpaperDataLocked == null) {
                    return null;
                }
                boolean supportsSamsungLiveWallpaperProvider = this.mSemService.mLiveWallpaperHelper.supportsSamsungLiveWallpaperProvider(peekWallpaperDataLocked);
                if (supportsSamsungLiveWallpaperProvider) {
                    file = this.mSemService.getThumbnailFile(sourceWhich, i2, i4 == 2 ? 1 : 0);
                } else {
                    file = null;
                }
                if (file == null) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        parcelFileDescriptor = ParcelFileDescriptor.open(file, i5);
                    } catch (FileNotFoundException unused) {
                        parcelFileDescriptor = null;
                    }
                    try {
                        if (!SELinux.restorecon(file)) {
                            IoUtils.closeQuietly(parcelFileDescriptor);
                            return null;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        StringBuilder sb = new StringBuilder("getWallpaperThumbnailFileDescriptor: which=");
                        sb.append(modeEnsuredWhich);
                        sb.append(", orientation=");
                        ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i5, ", fileMode=", ", fd=", sb);
                        sb.append(parcelFileDescriptor);
                        Slog.d("WallpaperManagerService", sb.toString());
                        return parcelFileDescriptor;
                    } catch (FileNotFoundException unused2) {
                        IoUtils.closeQuietly(parcelFileDescriptor);
                        return null;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor getWallpaperWithFeature(String str, String str2, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2, boolean z, boolean z2, int i3) {
        int folderStateBasedWhich;
        WallpaperData peekWallpaperDataLocked;
        if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL") && !hasPermission("android.permission.MANAGE_EXTERNAL_STORAGE")) {
            ((StorageManager) this.mContext.getSystemService(StorageManager.class)).checkPermissionReadImages(true, Binder.getCallingPid(), Binder.getCallingUid(), str, str2);
        }
        synchronized (this.mLock) {
            folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
            peekWallpaperDataLocked = peekWallpaperDataLocked(i2, folderStateBasedWhich);
        }
        return getWallpaper(iWallpaperManagerCallback, folderStateBasedWhich, bundle, i2, (i3 == 0 || peekWallpaperDataLocked == null) ? 0 : peekWallpaperDataLocked.mSemWallpaperData.mWpType, z2, z);
    }

    public final int getWidthHint(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mWallpaperDisplayHelper.mDisplayManager.getDisplay(i) == null) {
                    throw new IllegalArgumentException("Cannot find display with id=" + i);
                }
                if (this.mWallpaperMap.get(UserHandle.getCallingUserId(), 0) == null) {
                    return 0;
                }
                return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mWidth;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasNamedWallpaper(String str) {
        int callingUserId = UserHandle.getCallingUserId();
        boolean z = this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0;
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List<UserInfo> users = ((UserManager) this.mContext.getSystemService("user")).getUsers();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    for (UserInfo userInfo : users) {
                        if (z || callingUserId == userInfo.id) {
                            if (!userInfo.isProfile()) {
                                WallpaperData wallpaperData = this.mWallpaperMap.get(userInfo.id, 0);
                                if (wallpaperData == null) {
                                    int i = userInfo.id;
                                    Slog.v("WallpaperManagerService", "loadSettingsLocked");
                                    loadSettingsLocked(i, 3, this.mSemService.getCurrentImplicitMode(), false);
                                    wallpaperData = this.mWallpaperMap.get(userInfo.id, 0);
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
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public final boolean hasVideoWallpaper() {
        WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2));
        if (peekWallpaperDataLocked == null) {
            Slog.d("WallpaperManagerService", "Lock wallpaper data is null. So kwp can not be determined");
            return false;
        }
        SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
        int i = semWallpaperData.mWpType;
        if (i == 3) {
            try {
                return Boolean.parseBoolean(Uri.parse(semWallpaperData.mUri).getQueryParameter("hasVideo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i == 8) {
            return true;
        }
        return false;
    }

    public final void initImageWallpaperCropFile(int i, WallpaperData wallpaperData) {
        if (wallpaperData == null) {
            Log.w("WallpaperManagerService", "initImageWallpaperCropFile: WallpaperData is not exist");
            return;
        }
        if (!this.mImageWallpaper.equals(wallpaperData.nextWallpaperComponent)) {
            Slog.i("WallpaperManagerService", "initImageWallpaperCropFile: Nondefault wallpaper component. Gracefully ignoring...");
            return;
        }
        if (!wallpaperData.cropExists()) {
            if (wallpaperData.sourceExists()) {
                Slog.i("WallpaperManagerService", "initImageWallpaperCropFile: No crop. Regenerating from source");
                this.mWallpaperCropper.generateCrop(wallpaperData);
            }
            int i2 = wallpaperData.mSemWallpaperData.mWpType;
            if (i2 != 0 && i2 != -1) {
                Slog.i("WallpaperManagerService", "initImageWallpaperCropFile: System wallpaper is not image type.");
                return;
            }
        }
        if (!wallpaperData.sourceExists() && wallpaperData.mSemWallpaperData.mIsPreloaded) {
            Slog.i("WallpaperManagerService", "initImageWallpaperCropFile: Factory default wallpaper.");
        } else {
            if (wallpaperData.cropExists()) {
                return;
            }
            Slog.i("WallpaperManagerService", "initImageWallpaperCropFile: Unable to regenerate crop. Resetting...");
            synchronized (this.mLock) {
                this.mSemService.semClearWallpaperLocked(i, 0, "android");
            }
        }
    }

    public final Bundle invokePrepare(int i, int i2, int i3, ComponentName componentName, Bundle bundle) {
        long clearCallingIdentity;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "invokePrepare: userId = ", ", which = ", ", type = ");
        m.append(i3);
        m.append(", extras = ");
        m.append(bundle);
        Log.d("WallpaperManagerService", m.toString());
        Bundle bundle2 = null;
        if (i3 == 3) {
            if (bundle == null) {
                bundle = this.mSemService.buildParams(i, i2, i3);
                Log.w("WallpaperManagerService", "invokePrepare: Building extras. extras = " + bundle);
            }
            SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
            semWallpaperManagerService.getClass();
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Bundle call = semWallpaperManagerService.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.dynamiclock.provider"), "user_pack", (String) null, bundle);
                Log.d("SemWallpaperManagerService", "requestCustomPack: result = " + call);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                bundle2 = call;
            } catch (Exception e) {
                Log.e("SemWallpaperManagerService", "requestCustomPack: e = " + e);
            } finally {
            }
            return bundle2;
        }
        if (i3 == 7) {
            this.mSemService.mLiveWallpaperHelper.getClass();
            if (PreloadedLiveWallpaperHelper.isStockLiveWallpaperComponent(componentName)) {
                return this.mSemService.requestWallpaperPrepare(componentName, i2, i, bundle);
            }
            return null;
        }
        if (i3 != 1000) {
            Log.d("WallpaperManagerService", "invokePrepare: type = " + i3);
            return null;
        }
        if (bundle == null) {
            bundle = this.mSemService.buildParams(i, i2, i3);
            Log.w("WallpaperManagerService", "invokePrepare: Building extras. extras = " + bundle);
        }
        SemWallpaperManagerService semWallpaperManagerService2 = this.mSemService;
        semWallpaperManagerService2.getClass();
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle call2 = semWallpaperManagerService2.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.dynamiclock.provider"), "restore_dls", (String) null, bundle);
            Log.d("SemWallpaperManagerService", "requestDls: result = " + call2);
            return call2;
        } catch (Exception e2) {
            Log.e("SemWallpaperManagerService", "requestDls: e = " + e2);
            return null;
        } finally {
        }
    }

    public final boolean isDefaultComponent(WallpaperData wallpaperData) {
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        String str = semWallpaperData.mLastCallingPackage;
        int i = semWallpaperData.mWhich;
        if (this.mSemService.getDefaultWallpaperType(i) != 7) {
            return this.mImageWallpaper.equals(wallpaperData.wallpaperComponent) || wallpaperData.wallpaperComponent == null || TextUtils.isEmpty(str);
        }
        ComponentName componentName = wallpaperData.wallpaperComponent;
        SemWallpaperData semWallpaperData2 = wallpaperData.mSemWallpaperData;
        if ((semWallpaperData2.mWpType == 7 && componentName != null && componentName.equals(this.mSemService.getDefaultPreloadedLiveWallpaperComponentName(semWallpaperData2.mWhich))) || componentName == null) {
            return true;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "isDefaultComponent: which = ", ", type = ");
        m.append(wallpaperData.mSemWallpaperData.mWpType);
        m.append(", ComponentName = ");
        m.append(componentName);
        Slog.d("WallpaperManagerService", m.toString());
        return false;
    }

    public final boolean isDefaultWallpaperState(int i) {
        WallpaperData peekWallpaperDataLocked;
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, i);
        }
        if (peekWallpaperDataLocked == null) {
            return true;
        }
        int i2 = peekWallpaperDataLocked.mSemWallpaperData.mWpType;
        if (i2 != this.mSemService.getDefaultWallpaperType(i)) {
            Log.d("WallpaperManagerService", "isDefaultWallpaperState false, currentType = " + i2);
            return false;
        }
        peekWallpaperDataLocked.getCropFile();
        if (!peekWallpaperDataLocked.getCropFile().exists()) {
            return true;
        }
        Log.d("WallpaperManagerService", "isDefaultWallpaperState false, cropFile is not null");
        return false;
    }

    public final boolean isDesktopMode() {
        return this.mSemService.mDesktopMode.isDesktopMode();
    }

    public final boolean isDesktopModeEnabled(int i) {
        return this.mSemService.mDesktopMode.isDesktopModeEnabled(i);
    }

    public final boolean isDesktopStandAloneMode() {
        return this.mSemService.mDesktopMode.isDesktopSingleMode();
    }

    public final boolean isFromForegroundApp(final String str) {
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda12
            public final Object getOrThrow() {
                return Boolean.valueOf(WallpaperManagerService.this.mActivityManager.getPackageImportance(str) == 100);
            }
        })).booleanValue();
    }

    public final boolean isSameRequest(int i, String str, WallpaperData wallpaperData) {
        SemWallpaperData semWallpaperData = getLastWallpaper(wallpaperData.mWhich).mSemWallpaperData;
        if (semWallpaperData.mWpType != i) {
            return false;
        }
        String str2 = semWallpaperData.mUri;
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return false;
        }
        Log.d("WallpaperManagerService", "isSameRequest: prevUriString = " + str2 + ", uriString = " + str);
        int i2 = wallpaperData.mSemWallpaperData.mWpType;
        if (i2 != 3) {
            if (i2 != 5) {
                return false;
            }
            return str2.equals(str);
        }
        Uri parse = Uri.parse(str2);
        Uri parse2 = Uri.parse(str);
        return TextUtils.equals(parse.getScheme(), parse2.getScheme()) && TextUtils.equals(parse.getHost(), parse2.getHost()) && TextUtils.equals(parse.getPath(), parse2.getPath());
    }

    public final boolean isSetWallpaperAllowed(String str) {
        if (!this.mSemService.mKnox.isWallpaperChangeAllowed() || !Arrays.asList(this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid())).contains(str)) {
            return false;
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isDeviceOrProfileOwnerInCallingUser(str)) {
            return true;
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return !((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).hasUserRestriction("no_set_wallpaper", callingUserId);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSignedWithPlatformSignature(int i) {
        return this.mContext.getPackageManager().checkSignatures(1000, i) == 0;
    }

    public boolean isSnapshotTestMode() {
        if (SHIPPED) {
            return false;
        }
        return this.mSemService.isSnapshotTestMode();
    }

    public final boolean isStaticWallpaper(int i) {
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = (WhichChecker.isLock(i) ? this.mLockWallpaperMap : this.mWallpaperMap).get(this.mCurrentUserId, 0);
                if (wallpaperData == null) {
                    return false;
                }
                return this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isStockLiveWallpaper(int i, int i2) {
        ComponentName componentName;
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        WallpaperData onWallpaperDataRequested = semWallpaperManagerService.mCallback.onWallpaperDataRequested(i2, i);
        semWallpaperManagerService.mLiveWallpaperHelper.getClass();
        if (onWallpaperDataRequested.mSemWallpaperData.mWpType == 7 && (componentName = onWallpaperDataRequested.wallpaperComponent) != null) {
            return PreloadedLiveWallpaperHelper.isStockLiveWallpaperComponent(componentName);
        }
        return false;
    }

    public final boolean isSystemAndLockPaired(int i) {
        int currentUserId = getCurrentUserId();
        boolean isSystemAndLockPaired = this.mSemService.isSystemAndLockPaired(i, currentUserId);
        StringBuilder sb = new StringBuilder("isSystemAndLockPaired : mode=");
        sb.append(WhichChecker.getMode(i));
        sb.append(", isPaired=");
        sb.append(isSystemAndLockPaired);
        sb.append(" (called by userId= ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, currentUserId, ")", "WallpaperManagerService");
        return isSystemAndLockPaired;
    }

    public final boolean isValidSnapshot(int i) {
        checkPermission("android.permission.SET_WALLPAPER");
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.getClass();
        Log.d("SemWallpaperManagerService", "isValidSnapshot: key = " + i);
        SnapshotManager.SnapshotData snapshot = semWallpaperManagerService.mSnapshotManager.getSnapshot(semWallpaperManagerService.mCurrentUserId, i);
        if (snapshot != null && snapshot.hasWallpaperData()) {
            return true;
        }
        Log.e("SemWallpaperManagerService", "isValidSnapshot: No snapshot for key [" + i + "].");
        return false;
    }

    public final boolean isVideoWallpaper() {
        WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(2));
        if (peekWallpaperDataLocked == null) {
            Slog.d("WallpaperManagerService", "Lock wallpaper data is null. So kwp can not be determined");
            return false;
        }
        boolean z = peekWallpaperDataLocked.mSemWallpaperData.mWpType == 8;
        Slog.d("WallpaperManagerService", "isVideoWallpaper userId=" + this.mCurrentUserId + " isVideoWallpaper=" + z);
        return z;
    }

    public final boolean isVirtualWallpaperDisplay(int i) {
        return this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(i);
    }

    public final boolean isVisibleWhichWhenKeyguardLocked(int i) {
        if (!WhichChecker.isDex(i) || this.mSemService.mDesktopMode.isDesktopSingleMode()) {
            if (WhichChecker.isSupportLock(i)) {
                return WhichChecker.containsLock(i);
            }
            return true;
        }
        Log.i("WallpaperManagerService", "isVisibleWhichWhenKeyguardLocked = " + i);
        return WhichChecker.containsLock(i);
    }

    public final boolean isWaitingForUnlockUser(int i, int i2) {
        WallpaperData wallpaperSafeLocked;
        synchronized (this.mLock) {
            wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "which : ", ", isWaitingForUnlockUser : ");
        m.append(wallpaperSafeLocked.mSemWallpaperData.mWaitingForUnlockUser);
        m.append(" (called by userId= ");
        m.append(i2);
        m.append(")");
        Slog.d("WallpaperManagerService", m.toString());
        return wallpaperSafeLocked.mSemWallpaperData.mWaitingForUnlockUser;
    }

    public final boolean isWallpaperBackupAllowed(int i, int i2) {
        WallpaperData wallpaperData = (WhichChecker.isLock(i) ? this.mLockWallpaperMap : this.mWallpaperMap).get(i2, WhichChecker.getMode(i));
        if (wallpaperData != null) {
            return wallpaperData.allowBackup;
        }
        return false;
    }

    public final boolean isWallpaperBackupEligible(int i, int i2) {
        if (this.mSemService.mDesktopMode.isDesktopMode()) {
            return false;
        }
        WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, i);
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "isWallpaperBackupEligible: which = ", ", userId = ", ", wallpaper = ");
        m.append(peekWallpaperDataLocked);
        Log.d("WallpaperManagerService", m.toString());
        if (peekWallpaperDataLocked != null) {
            return peekWallpaperDataLocked.allowBackup;
        }
        return false;
    }

    public final boolean isWallpaperDataExists(int i, int i2) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (WhichChecker.isSystem(i2) ? this.mWallpaperMap : this.mLockWallpaperMap).get(i, WhichChecker.getMode(i2)) != null;
            } finally {
            }
        }
        return z;
    }

    public final boolean isWallpaperSupported(String str) {
        int callingUid = Binder.getCallingUid();
        try {
            if (this.mContext.getPackageManager().getPackageUidAsUser(str, UserHandle.getUserId(callingUid)) == callingUid) {
                return this.mAppOpsManager.checkOpNoThrow(48, callingUid, str) == 0;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        Slog.e("WallpaperManagerService", "enforcePackageBelongsToUid: packageName[" + str + "] is not allowed.");
        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "Invalid package or package does not belong to uid:"));
    }

    /* JADX WARN: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0188 A[LOOP:0: B:26:0x00ac->B:34:0x0188, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0183 A[EDGE_INSN: B:35:0x0183->B:36:0x0183 BREAK  A[LOOP:0: B:26:0x00ac->B:34:0x0188], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02d1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0345  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadSettingsLocked(int r26, int r27, int r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 1133
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.loadSettingsLocked(int, int, int, boolean):void");
    }

    public final boolean lockScreenWallpaperExists() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mLockWallpaperMap.get(this.mCurrentUserId, 0) != null;
        }
        return z;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:? -> B:61:0x01c0). Please report as a decompilation issue!!! */
    public final int makeSnapshot(int i, int i2, Bundle bundle) {
        int i3;
        boolean z;
        SnapshotManager.SnapshotData snapshot;
        Object obj;
        SnapshotManager.SnapshotData snapshotData;
        int i4;
        checkPermission("android.permission.SET_WALLPAPER");
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.getClass();
        HashMap hashMap = new HashMap();
        ArrayList whiches = SnapshotHelper.getWhiches(i);
        if (i2 <= 0) {
            synchronized (semWallpaperManagerService.mSnapshotDataLock) {
                SnapshotManager.SnapshotRepository repositroy = semWallpaperManagerService.mSnapshotManager.getRepositroy(semWallpaperManagerService.mCurrentUserId);
                do {
                    i4 = repositroy.key + 1;
                    repositroy.key = i4;
                } while (i4 <= 0);
            }
            i3 = i4;
        } else {
            i3 = i2;
        }
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT) {
            boolean z2 = bundle != null ? bundle.getBoolean("is_paired", false) : false;
            if (!z2) {
                int pairedDlsSnapshotKey = semWallpaperManagerService.mSnapshotManager.getPairedDlsSnapshotKey(semWallpaperManagerService.mCurrentUserId, i3);
                Log.d("SemWallpaperManagerService", "makeSnapshot: pairedDlsSnapshotKey = " + pairedDlsSnapshotKey);
                if (pairedDlsSnapshotKey > 0) {
                    SnapshotManager snapshotManager = semWallpaperManagerService.mSnapshotManager;
                    int i5 = semWallpaperManagerService.mCurrentUserId;
                    snapshotManager.addHistory(i5, 5, pairedDlsSnapshotKey, semWallpaperManagerService.doRestoreOrMigrate(i5, pairedDlsSnapshotKey));
                    semWallpaperManagerService.mSnapshotManager.removeSnapshotByKey(semWallpaperManagerService.mCurrentUserId, pairedDlsSnapshotKey);
                }
            }
            z = z2;
        } else {
            z = false;
        }
        SnapshotManager.SnapshotData snapshot2 = semWallpaperManagerService.mSnapshotManager.getSnapshot(semWallpaperManagerService.mCurrentUserId, i3);
        if (snapshot2 != null) {
            ArrayList whiches2 = snapshot2.getWhiches();
            if (whiches.size() != whiches2.size()) {
                Log.w("SemWallpaperManagerService", "makeSnapshot: Number of 'which' in key + [" + i3 + "] is not the same as previous on");
            }
            Iterator it = whiches.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                num.getClass();
                if (!whiches2.contains(num)) {
                    Log.w("SemWallpaperManagerService", "makeSnapshot: 'which' values are not matched with previous snapshot. prevWhiches = " + whiches2 + ", whiches = " + whiches);
                }
            }
            Iterator it2 = whiches.iterator();
            while (it2.hasNext()) {
                int intValue = ((Integer) it2.next()).intValue();
                SnapshotManager snapshotManager2 = semWallpaperManagerService.mSnapshotManager;
                int i6 = semWallpaperManagerService.mCurrentUserId;
                Iterator it3 = snapshotManager2.getRepositroy(i6).getAll().iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        Log.d("SnapshotManager", DualAppManagerService$$ExternalSyntheticOutline0.m(i6, intValue, "getLastSnapshotByWhich: userId = ", ", which = ", "No snapshot for the which."));
                        snapshotData = null;
                        break;
                    }
                    snapshotData = (SnapshotManager.SnapshotData) it3.next();
                    if (snapshotData.hasWallpaperData(intValue)) {
                        break;
                    }
                }
                if (snapshotData != null && i3 != snapshotData.key) {
                    SnapshotManager snapshotManager3 = semWallpaperManagerService.mSnapshotManager;
                    int i7 = semWallpaperManagerService.mCurrentUserId;
                    snapshotManager3.addHistory(i7, 5, i3, semWallpaperManagerService.doRestoreOrMigrate(i7, i3));
                    semWallpaperManagerService.mSnapshotManager.removeSnapshotByKey(semWallpaperManagerService.mCurrentUserId, i3);
                }
            }
        }
        Iterator it4 = whiches.iterator();
        while (it4.hasNext()) {
            Integer num2 = (Integer) it4.next();
            int intValue2 = num2.intValue();
            WallpaperData wallpaperData = new WallpaperData(semWallpaperManagerService.mCallback.onWallpaperDataRequested(semWallpaperManagerService.mCurrentUserId, intValue2));
            SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
            String format = SimpleDateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
            semWallpaperData.getClass();
            if (!TextUtils.isEmpty(format)) {
                semWallpaperData.mTimeCreated = format;
            }
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(intValue2, i3, "makeSnapshot: which = ", ", key = ", ", wallpaperData [");
            m.append(wallpaperData);
            m.append("]");
            Log.d("SemWallpaperManagerService", m.toString());
            Object obj2 = semWallpaperManagerService.mSnapshotDataLock;
            synchronized (obj2) {
                try {
                    obj = obj2;
                } catch (Throwable th) {
                    th = th;
                    obj = obj2;
                    throw th;
                }
                try {
                    int addSnapshot = semWallpaperManagerService.mSnapshotManager.addSnapshot(semWallpaperManagerService.mContext, semWallpaperManagerService.mCurrentUserId, intValue2, i3, wallpaperData);
                    hashMap.put(num2, Integer.valueOf(addSnapshot));
                    if (addSnapshot > 0) {
                        semWallpaperManagerService.connectSnapshotForLiveWallpaper(intValue2, i3, whiches);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT && (snapshot = semWallpaperManagerService.mSnapshotManager.getSnapshot(semWallpaperManagerService.mCurrentUserId, i3)) != null && bundle != null) {
            snapshot.isFromPairedService = z;
        }
        semWallpaperManagerService.mSnapshotManager.addHistory(semWallpaperManagerService.mCurrentUserId, 1, i3, hashMap);
        int i8 = 0;
        for (Map.Entry entry : hashMap.entrySet()) {
            int intValue3 = ((Integer) entry.getKey()).intValue();
            int intValue4 = ((Integer) entry.getValue()).intValue();
            Log.d("SemWallpaperManagerService", DualAppManagerService$$ExternalSyntheticOutline0.m(intValue3, intValue4, "makeSnapshot: Result <", ", ", ">"));
            if (intValue4 > 0 || intValue4 == -3) {
                i8++;
            }
        }
        semWallpaperManagerService.saveSettingsLockedForSnapshot(semWallpaperManagerService.mCurrentUserId);
        if (i8 > 0) {
            return i3;
        }
        return -1;
    }

    public final void maybeDetachLastWallpapers(WallpaperData wallpaperData) {
        if (wallpaperData.userId != this.mCurrentUserId || wallpaperData.equals(this.mFallbackWallpaper)) {
            return;
        }
        int i = wallpaperData.mWhich;
        boolean z = false;
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        if (wallpaperData.mSystemWasBoth && !z3) {
            z = true;
        }
        StringBuilder sb = new StringBuilder("maybeDetachLastWallpapers: newWallpaper.mWhich = ");
        sb.append(wallpaperData.mWhich);
        sb.append(", newWallpaper.mSystemWasBoth = ");
        BatteryService$$ExternalSyntheticOutline0.m(sb, wallpaperData.mSystemWasBoth, ", homeUpdated = ", z2, ", lockUpdated = ");
        sb.append(z3);
        sb.append(", systemWillBecomeLock = ");
        sb.append(z);
        Log.d("WallpaperManagerService", sb.toString());
        if (z2 && !z) {
            detachWallpaperLocked(getLastWallpaper(WhichChecker.getMode(wallpaperData.mWhich) | 1));
        }
        if (z3) {
            detachWallpaperLocked(getLastWallpaper(WhichChecker.getMode(wallpaperData.mWhich) | 2));
        }
    }

    public final void migrateLiveSystemToLockWallpaperLocked(int i, int i2) {
        int i3 = i | 1;
        int i4 = i | 2;
        WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, i3);
        if (peekWallpaperDataLocked == null) {
            return;
        }
        Slog.i("WallpaperManagerService", "migrateLiveSystemToLockWallpaperLocked : currentSystem = " + peekWallpaperDataLocked + ", to which = " + i4);
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i2, i4);
        wallpaperSafeLocked.mSemWallpaperData.mWpType = peekWallpaperDataLocked.mSemWallpaperData.mWpType;
        wallpaperSafeLocked.cleanUp();
        wallpaperSafeLocked.mWhich = i4;
        wallpaperSafeLocked.mSemWallpaperData.mWhich = i4;
        wallpaperSafeLocked.wallpaperId = peekWallpaperDataLocked.wallpaperId;
        wallpaperSafeLocked.wallpaperComponent = peekWallpaperDataLocked.wallpaperComponent;
        wallpaperSafeLocked.cropHint.set(peekWallpaperDataLocked.cropHint);
        wallpaperSafeLocked.allowBackup = peekWallpaperDataLocked.allowBackup;
        wallpaperSafeLocked.connection = null;
        SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
        Bundle bundle = semWallpaperData.mExternalParams;
        SemWallpaperData semWallpaperData2 = wallpaperSafeLocked.mSemWallpaperData;
        semWallpaperData2.mExternalParams = bundle;
        semWallpaperData2.mIsPreloaded = semWallpaperData.mIsPreloaded;
        semWallpaperData2.mDlsSemColors = null;
        saveSettingsLocked(i2, i);
        this.mAssetFileManager.getClass();
        AssetFileManager.moveAssetFiles(i3, i4, i2);
        if (WhichChecker.isSubDisplay(i4)) {
            this.mLastSubLockWallpaper = wallpaperSafeLocked;
        } else {
            this.mLastLockWallpaper = wallpaperSafeLocked;
        }
    }

    public final void migrateStaticSystemToLockWallpaperLocked(int i, int i2) {
        int mode = WhichChecker.getMode(i2);
        WallpaperData wallpaperData = this.mWallpaperMap.get(i, mode);
        if (wallpaperData == null) {
            return;
        }
        int i3 = mode | 2;
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, i3);
        wallpaperSafeLocked.mSemWallpaperData.mWpType = wallpaperData.mSemWallpaperData.mWpType;
        wallpaperSafeLocked.cleanUp();
        wallpaperSafeLocked.wallpaperId = wallpaperData.wallpaperId;
        wallpaperSafeLocked.cropHint.set(wallpaperData.cropHint);
        SparseArray sparseArray = wallpaperData.mCropHints;
        if (sparseArray != null) {
            wallpaperSafeLocked.mCropHints = sparseArray.clone();
        }
        wallpaperSafeLocked.allowBackup = wallpaperData.allowBackup;
        wallpaperSafeLocked.primaryColors = wallpaperData.primaryColors;
        wallpaperSafeLocked.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
        wallpaperSafeLocked.mWhich = i3;
        SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
        semWallpaperData.mWhich = i3;
        SemWallpaperData semWallpaperData2 = wallpaperData.mSemWallpaperData;
        semWallpaperData.mWidth = semWallpaperData2.mWidth;
        semWallpaperData.mHeight = semWallpaperData2.mHeight;
        try {
            if (wallpaperData.getWallpaperFile(0).exists()) {
                Os.rename(wallpaperData.getWallpaperFile(0).getAbsolutePath(), wallpaperSafeLocked.getWallpaperFile(0).getAbsolutePath());
            }
            if (wallpaperData.getCropFile().exists()) {
                Os.rename(wallpaperData.getCropFile().getAbsolutePath(), wallpaperSafeLocked.getCropFile().getAbsolutePath());
            }
            this.mAssetFileManager.getClass();
            AssetFileManager.moveAssetFiles(mode | 1, i3, i);
            SELinux.restorecon(wallpaperSafeLocked.getWallpaperFile(0));
            if (WhichChecker.isSubDisplay(i2)) {
                this.mLastSubLockWallpaper = wallpaperSafeLocked;
            } else {
                this.mLastLockWallpaper = wallpaperSafeLocked;
            }
        } catch (ErrnoException e) {
            Slog.w("WallpaperManagerService", "Couldn't migrate system wallpaper: " + e.getMessage());
            clearWallpaperBitmaps(wallpaperSafeLocked);
        }
    }

    public final void notifyAodVisibilityState(final int i) {
        WallpaperConnection wallpaperConnection;
        checkCallerIsSystemOrSystemUi();
        this.mSemService.mAodVisibilityState = i;
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId, 0);
                if (wallpaperData != null && (wallpaperConnection = wallpaperData.connection) != null) {
                    wallpaperConnection.forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i2 = i;
                            boolean z = WallpaperManagerService.SHIPPED;
                            IWallpaperEngine iWallpaperEngine = ((WallpaperManagerService.DisplayConnector) obj).mEngine;
                            if (iWallpaperEngine != null) {
                                try {
                                    iWallpaperEngine.dispatchWallpaperCommand("android.wallpaper.aodstate", i2, -1, -1, new Bundle());
                                } catch (RemoteException e) {
                                    Slog.w("WallpaperManagerService", "notifyAodVisibilityState: Failed to dispatch COMMAND_AOD_STATE", e);
                                }
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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
        intent.putExtra("android.service.wallpaper.extra.FROM_FOREGROUND_APP", wallpaperData.fromForegroundApp);
        intent.putExtra("which", wallpaperData.mWhich);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
    }

    public final void notifyCoverWallpaperChanged(int i, int i2) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "notifyCoverWallpaperChanged: type = ", ", which = ", "WallpaperManagerService");
        synchronized (this.mLock) {
            Iterator it = this.mCoverWallpaperListenerList.iterator();
            while (it.hasNext()) {
                IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
                if (iWallpaperManagerCallback != null) {
                    try {
                        Slog.d("WallpaperManagerService", "notifyCoverWallpaperChanged: cb = " + iWallpaperManagerCallback);
                        iWallpaperManagerCallback.onSemWallpaperChanged(i, i2, (Bundle) null);
                    } catch (RemoteException e) {
                        Log.d("WallpaperManagerService", "notifyCoverWallpaperChanged: fail. : " + e);
                    }
                }
            }
        }
    }

    public final void notifyGoingToSleep(int i, int i2, Bundle bundle) {
        checkCallerIsSystemOrSystemUi();
        synchronized (this.mLock) {
            try {
                for (WallpaperData wallpaperData : getActiveWallpapers()) {
                    wallpaperData.connection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda8(bundle, i, i2, 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyLockWallpaperChanged(int i, int i2, Bundle bundle) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "notifyLockWallpaperChanged type = ", "WallpaperManagerService");
        synchronized (this.mLock) {
            Iterator it = this.mKeyguardListenerList.iterator();
            while (it.hasNext()) {
                IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
                if (iWallpaperManagerCallback != null) {
                    try {
                        Slog.d("WallpaperManagerService", "notifyLockWallpaperChanged cb=" + iWallpaperManagerCallback);
                        iWallpaperManagerCallback.onWallpaperChanged();
                        iWallpaperManagerCallback.onSemWallpaperChanged(i, i2, bundle);
                    } catch (RemoteException e) {
                        Log.d("WallpaperManagerService", "onWallpaperChanged() fail. : " + e);
                    }
                }
            }
        }
    }

    public final void notifyPid(int i, int i2, String str, boolean z) {
        ActivityManagerPerformance activityManagerPerformance = ActivityManagerPerformance.AMP_ENABLE ? ActivityManagerPerformance.booster : null;
        if (activityManagerPerformance != null) {
            if (str != null) {
                for (String str2 : ActivityManagerPerformance.gSystemuiPkgs) {
                    if (str.equals(str2)) {
                        return;
                    }
                }
            }
            if (!z) {
                if (activityManagerPerformance.mIsSdhmsInitCompleted) {
                    SemPerfManager.sendCommandToSsrm("MIDGROUND_PROCESS_DETECT", "FALSE");
                    activityManagerPerformance.mIsMidGroundCpuSetEnable = false;
                    return;
                }
                return;
            }
            Process.requestProcessProfile(i, i2, ActivityManagerPerformance.WALLPAPER_PROFILE);
            if (!activityManagerPerformance.mIsSdhmsInitCompleted || activityManagerPerformance.mIsMidGroundCpuSetEnable) {
                return;
            }
            SemPerfManager.sendCommandToSsrm("MIDGROUND_PROCESS_DETECT", "TRUE");
            activityManagerPerformance.mIsMidGroundCpuSetEnable = true;
        }
    }

    public final void notifySemColorListeners(int i, WallpaperData wallpaperData) {
        boolean z;
        ArrayList arrayList;
        SemWallpaperColors semWallpaperColors = getSemWallpaperColors(wallpaperData, false);
        int i2 = wallpaperData.mSemWallpaperData.mWhich;
        int i3 = wallpaperData.userId;
        if (semWallpaperColors == null) {
            Log.d("WallpaperManagerService", "notifySemColorListeners colors == null");
            return;
        }
        boolean z2 = Rune.SUPPORT_SUB_DISPLAY_MODE;
        if ((!z2 && (i2 & 16) == 16) || (!(z = Rune.VIRTUAL_DISPLAY_WALLPAPER) && (i2 & 32) == 32)) {
            Log.d("WallpaperManagerService", "Unsupported wallpaper, ignore notifySemColorListeners");
            return;
        }
        boolean z3 = Rune.DESKTOP_STANDALONE_MODE_WALLPAPER;
        if (!z3 && this.mSemService.mDesktopMode.isDesktopSingleMode() && WhichChecker.isDex(i2)) {
            Log.d("WallpaperManagerService", "ignore colors changed of dex wallpaper if standalone mode");
            return;
        }
        int i4 = this.mCurrentUserId;
        if (i4 > 0 && i3 != i4) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "notifySemColorListeners ignore, ", ", ");
            m.append(this.mCurrentUserId);
            Log.d("WallpaperManagerService", m.toString());
            return;
        }
        if (WhichChecker.isSystem(i2)) {
            int highlightFilterState = getHighlightFilterState(wallpaperData);
            Integer num = highlightFilterState == 1 ? 0 : highlightFilterState == 2 ? 1 : null;
            LegibilityColor legibilityColor = this.mSemService.mLegibilityColor;
            legibilityColor.getClass();
            Log.d("LegibilityColor", "setWhiteBgSettings which: " + i2 + ", userId : " + i3 + ", homeBodyColor : " + num);
            boolean isDex = WhichChecker.isDex(i2);
            SemWallpaperManagerService semWallpaperManagerService = legibilityColor.mService;
            if (isDex && !semWallpaperManagerService.mDesktopMode.isDesktopSingleMode()) {
                Log.d("LegibilityColor", "Dex dual mode, ignore SemWallpaperColors");
            } else if (z3 && !WhichChecker.isDex(i2) && semWallpaperManagerService.mDesktopMode.isDesktopSingleMode()) {
                Log.d("LegibilityColor", "Dex single mode, ignore SemWallpaperColors");
            } else if (WhichChecker.isWatchFaceDisplay(i2) || WhichChecker.isVirtualDisplay(i2)) {
                Log.d("LegibilityColor", "Cover wallpaper, ignore SemWallpaperColors");
            } else if ((z2 || (i2 & 16) != 16) && (z || (i2 & 32) != 32)) {
                if (z2) {
                    int i5 = semWallpaperManagerService.mSubDisplayMode.mLidState;
                    if ((WhichChecker.isSubDisplay(i2) && i5 != 0) || (!WhichChecker.isSubDisplay(i2) && i5 == 0)) {
                        Log.d("LegibilityColor", DualAppManagerService$$ExternalSyntheticOutline0.m(i5, i2, "setWhiteBgSettings() lidState: ", ", which : ", ", ignore SemWallpaperColors"));
                    }
                }
                int[] iArr = {0, 0, 0};
                SemWallpaperColors.Item item = semWallpaperColors.get(32L);
                if (item != null) {
                    int fontColor = item.getFontColor();
                    iArr[0] = fontColor;
                    if (fontColor == 2) {
                        iArr[0] = item.getFontColorRgb();
                    }
                }
                if (num != null) {
                    iArr[1] = num.intValue();
                } else {
                    SemWallpaperColors.Item item2 = semWallpaperColors.get(64L);
                    if (item2 != null) {
                        iArr[1] = item2.getFontColor();
                    }
                }
                SemWallpaperColors.Item item3 = semWallpaperColors.get(128L);
                if (item3 != null) {
                    iArr[2] = item3.getFontColor();
                }
                LegibilityColor.AnonymousClass1 anonymousClass1 = legibilityColor.mHandler;
                Message obtainMessage = anonymousClass1.obtainMessage(1015);
                obtainMessage.obj = iArr;
                obtainMessage.arg1 = i3;
                anonymousClass1.sendMessage(obtainMessage);
            } else {
                Log.d("LegibilityColor", "Unsupported sub wallpaper, ignore SemWallpaperColors");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        synchronized (this.mLock) {
            try {
                RemoteCallbackList wallpaperCallbacks = getWallpaperCallbacks(i3, i);
                RemoteCallbackList wallpaperCallbacks2 = getWallpaperCallbacks(-1, i);
                arrayList = this.mKeyguardListenerList;
                if (wallpaperCallbacks != null) {
                    int beginBroadcast = wallpaperCallbacks.beginBroadcast();
                    for (int i6 = 0; i6 < beginBroadcast; i6++) {
                        arrayList2.add(wallpaperCallbacks.getBroadcastItem(i6));
                    }
                    wallpaperCallbacks.finishBroadcast();
                }
                if (wallpaperCallbacks2 != null) {
                    int beginBroadcast2 = wallpaperCallbacks2.beginBroadcast();
                    for (int i7 = 0; i7 < beginBroadcast2; i7++) {
                        arrayList2.add(wallpaperCallbacks2.getBroadcastItem(i7));
                    }
                    wallpaperCallbacks2.finishBroadcast();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int size = arrayList2.size();
        for (int i8 = 0; i8 < size; i8++) {
            try {
                ((IWallpaperManagerCallback) arrayList2.get(i8)).onSemWallpaperColorsChanged(semWallpaperColors, i2, i3);
            } catch (RemoteException unused) {
            }
        }
        synchronized (this.mLock) {
            if (arrayList != null && i == 0) {
                try {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
                        if (iWallpaperManagerCallback != null) {
                            iWallpaperManagerCallback.onSemWallpaperColorsChanged(semWallpaperColors, i2, i3);
                        }
                    }
                } catch (RemoteException unused2) {
                }
            }
        }
        StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, "notifySemColorListeners: ", ", ", ", ");
        m2.append(i);
        Log.d("WallpaperManagerService", m2.toString());
    }

    public final void notifyWakingUp(int i, int i2, Bundle bundle) {
        checkCallerIsSystemOrSystemUi();
        synchronized (this.mLock) {
            try {
                for (WallpaperData wallpaperData : getActiveWallpapers()) {
                    wallpaperData.connection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda8(bundle, i, i2, 0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyWallpaperColorsChanged(final int i, final WallpaperData wallpaperData) {
        WallpaperConnection wallpaperConnection = wallpaperData.connection;
        if (wallpaperConnection != null) {
            wallpaperConnection.forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda26
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    WallpaperData wallpaperData2 = wallpaperData;
                    int i2 = i;
                    boolean z = WallpaperManagerService.SHIPPED;
                    wallpaperManagerService.getClass();
                    wallpaperManagerService.notifyWallpaperColorsChangedOnDisplay(((WallpaperManagerService.DisplayConnector) obj).mDisplayId, i2, wallpaperData2);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033 A[Catch: all -> 0x003f, TryCatch #4 {all -> 0x003f, blocks: (B:4:0x0006, B:6:0x0015, B:12:0x0023, B:14:0x0029, B:16:0x0033, B:17:0x0042, B:19:0x0046, B:21:0x004a, B:24:0x004f, B:27:0x0051, B:29:0x0055, B:31:0x0059, B:35:0x0063, B:36:0x007e), top: B:3:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0116 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyWallpaperColorsChangedOnDisplay(int r12, int r13, com.android.server.wallpaper.WallpaperData r14) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.notifyWallpaperColorsChangedOnDisplay(int, int, com.android.server.wallpaper.WallpaperData):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new WallpaperManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onSwitchWallpaperFailLocked(WallpaperData wallpaperData, IRemoteCallback iRemoteCallback, ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            this.mSemService.semClearWallpaperLocked(wallpaperData);
            return;
        }
        Slog.w("WallpaperManagerService", "Wallpaper isn't direct boot aware; using fallback until unlocked");
        wallpaperData.wallpaperComponent = wallpaperData.nextWallpaperComponent;
        WallpaperData wallpaperData2 = new WallpaperData(wallpaperData.userId, wallpaperData.mWhich);
        clearWallpaperBitmaps(wallpaperData);
        wallpaperData2.mBindSource = WallpaperData.BindSource.SWITCH_WALLPAPER_FAILURE;
        bindWallpaperComponentLocked(this.mImageWallpaper, true, false, wallpaperData2, iRemoteCallback, null);
        wallpaperData.mSemWallpaperData.mWaitingForUnlockUser = true;
    }

    public final WallpaperData peekPairingConsideredWallpaperDataLocked(int i, int i2) {
        return peekWallpaperDataLocked(i2, getSourceWhich(i, i2));
    }

    public final WallpaperData peekWallpaperDataLocked(int i, int i2) {
        return (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap).get(i, WhichChecker.getMode(i2));
    }

    public final void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, "registerWallpaperColorsCallback", null);
        synchronized (this.mLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOnLocalColorsChangedListener(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i, int i2, int i3) {
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

    public final void removeSnapshotByKey(int i) {
        checkPermission("android.permission.SET_WALLPAPER");
        this.mSemService.removeSnapshotByKey(i);
    }

    public final void removeSnapshotBySource(String str) {
        checkPermission("android.permission.SET_WALLPAPER");
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.getClass();
        Log.d("SemWallpaperManagerService", "removeSnapshotBySource: source = " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (semWallpaperManagerService.mSnapshotDataLock) {
            try {
                Iterator it = semWallpaperManagerService.mSnapshotManager.getRepositroy(semWallpaperManagerService.mCurrentUserId).getAll().iterator();
                while (it.hasNext()) {
                    SnapshotManager.SnapshotData snapshotData = (SnapshotManager.SnapshotData) it.next();
                    if (snapshotData != null && TextUtils.equals(str, snapshotData.source)) {
                        semWallpaperManagerService.removeSnapshotByKey(snapshotData.key);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeSnapshotByWhich(int i) {
        checkPermission("android.permission.SET_WALLPAPER");
        this.mSemService.removeSnapshotByWhich(i);
    }

    public final void requestInitializeThumnailFile(int i, int i2, WallpaperData wallpaperData) {
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        int i3 = wallpaperData.mSemWallpaperData.mWpType;
        semWallpaperManagerService.getClass();
        SemWallpaperManagerService.initializeThumnailFile(i, i3, i2, wallpaperData);
    }

    public final void requestNotifySemWallpaperColors(int i) {
        synchronized (this.mLock) {
            WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(getCurrentUserId(), i);
            if (peekWallpaperDataLocked != null) {
                notifySemColorListeners(0, peekWallpaperDataLocked);
            }
        }
    }

    public final void requestNotifyWallpaperChanged(int i, int i2) {
        Log.d("WallpaperManagerService", "requestNotifyWallpaperChanged: userId = " + i + ", which = " + i2);
        if (WhichChecker.isLock(i2)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i, i2);
                if (peekWallpaperDataLocked != null) {
                    notifyCallbacksLocked(peekWallpaperDataLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestParseWallpaperAttributes(TypedXmlPullParser typedXmlPullParser, WallpaperData wallpaperData) {
        try {
            this.mWallpaperDataParser.parseWallpaperAttributes(typedXmlPullParser, wallpaperData, false);
        } catch (XmlPullParserException e) {
            Log.e("WallpaperManagerService", "requestParseWallpaperAttributes: " + e.getMessage());
        }
    }

    public final void requestSaveRestoredWallpaperLocked(int i, int i2, WallpaperData wallpaperData) {
        synchronized (this.mLock) {
            try {
                (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap).put(i, WhichChecker.getMode(i2), wallpaperData);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestWriteWallpaperAttributes(TypedXmlSerializer typedXmlSerializer, String str, WallpaperData wallpaperData) {
        this.mWallpaperDataParser.writeWallpaperAttributes(typedXmlSerializer, str, wallpaperData);
    }

    public final boolean restoreSnapshot(int i, String str) {
        checkPermission("android.permission.SET_WALLPAPER");
        this.mSemService.restoreSnapshot(i, str);
        return true;
    }

    public final void saveSettingsLocked(int i) {
        saveSettingsLocked(i, this.mSemService.getCurrentImplicitMode());
    }

    public final void saveSettingsLocked(int i, int i2) {
        int mode;
        FileOutputStream fileOutputStream;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
        timingsTraceAndSlog.traceBegin("WPMS.saveSettingsLocked-" + i);
        WallpaperDataParser wallpaperDataParser = this.mWallpaperDataParser;
        WallpaperData wallpaperData = this.mWallpaperMap.get(i, i2);
        WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(i, i2);
        if (wallpaperData == null) {
            mode = 0;
        } else {
            wallpaperDataParser.getClass();
            mode = WhichChecker.getMode(wallpaperData.mWhich);
        }
        wallpaperDataParser.getClass();
        JournaledFile makeJournaledFile = WallpaperDataParser.makeJournaledFile(i, mode);
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
        } catch (IOException unused) {
        } catch (NullPointerException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            if (wallpaperData != null) {
                wallpaperDataParser.writeWallpaperAttributes(resolveSerializer, "wp", wallpaperData);
            }
            if (wallpaperData2 != null) {
                wallpaperDataParser.writeWallpaperAttributes(resolveSerializer, "kwp", wallpaperData2);
            }
            resolveSerializer.endDocument();
            fileOutputStream.flush();
            FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
            makeJournaledFile.commit();
        } catch (IOException unused2) {
            fileOutputStream2 = fileOutputStream;
            IoUtils.closeQuietly(fileOutputStream2);
            makeJournaledFile.rollback();
            timingsTraceAndSlog.traceEnd();
        } catch (NullPointerException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            Slog.w("WallpaperDataParser", "failed writing " + e);
            IoUtils.closeQuietly(fileOutputStream2);
            makeJournaledFile.rollback();
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void semClearWallpaperThumbnailCache(int i, int i2, String str) {
        if (Binder.getCallingUid() != 1000 && !hasPermission("android.permission.READ_WALLPAPER_INTERNAL")) {
            throw new SecurityException("semClearWallpaperThumbnailCache failed, Required permission : READ_WALLPAPER_INTERNAL or Required UID : SYSTEM_UID");
        }
        Log.d("WallpaperManagerService", "semClearWallpaperThumbnailCache : which = " + i + ", callingPackage = " + str);
        this.mSemService.deleteThumbnailFile(i, i2);
        Log.addLogString("WallpaperManagerService", "semClearWallpaperThumbnailCache: " + i + ", " + i2 + ", " + str);
    }

    public final SemWallpaperColors semGetPrimaryWallpaperColors(int i) {
        WallpaperData peekWallpaperDataLocked;
        int currentUserId = getCurrentUserId();
        int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
        synchronized (this.mLock) {
            peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, folderStateBasedWhich);
        }
        return getSemWallpaperColors(peekWallpaperDataLocked, true);
    }

    public final Rect semGetSmartCropRect(int i) {
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
                if (peekWallpaperDataLocked == null) {
                    Slog.e("WallpaperManagerService", "semSetSmartCropRect wallpaper == null");
                    return null;
                }
                Log.d("WallpaperManagerService", "semgetSmartCropRect, " + i + ", " + peekWallpaperDataLocked.mSemWallpaperData.mSmartCropRect);
                return peekWallpaperDataLocked.mSemWallpaperData.mSmartCropRect;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        if (!isSignedWithPlatformSignature(callingUid)) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "Calling app does not have required permission. uid = "));
        }
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        int sourceWhich = getSourceWhich(modeEnsuredWhich, i2);
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, sourceWhich);
                if (peekWallpaperDataLocked == null) {
                    return null;
                }
                if (!this.mSemService.mLiveWallpaperHelper.supportsSamsungLiveWallpaperProvider(peekWallpaperDataLocked)) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ParcelFileDescriptor thumbnailFileDescriptor = this.mSemService.getThumbnailFileDescriptor(modeEnsuredWhich, i2, i3);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    StringBuilder sb = new StringBuilder("semGetWallpaperThumbnailFileDescriptor: which=");
                    sb.append(modeEnsuredWhich);
                    sb.append(", userId=");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ", rotation=", ", fd=", sb);
                    sb.append(thumbnailFileDescriptor);
                    Slog.d("WallpaperManagerService", sb.toString());
                    return thumbnailFileDescriptor;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final String semGetUri(int i, String str) {
        WallpaperData peekWallpaperDataLocked;
        checkPermission("android.permission.SET_WALLPAPER");
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, i);
        }
        if (peekWallpaperDataLocked != null) {
            return peekWallpaperDataLocked.mSemWallpaperData.mUri;
        }
        Slog.e("WallpaperManagerService", "semGetUri wallpaper == null");
        return null;
    }

    public final SemWallpaperColors semGetWallpaperColors(int i) {
        WallpaperData peekWallpaperDataLocked;
        int currentUserId = getCurrentUserId();
        int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
        synchronized (this.mLock) {
            peekWallpaperDataLocked = peekWallpaperDataLocked(currentUserId, folderStateBasedWhich);
        }
        return getSemWallpaperColors(peekWallpaperDataLocked, false);
    }

    public final ComponentName semGetWallpaperComponent(int i, int i2) {
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, i2);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    return null;
                }
                return peekPairingConsideredWallpaperDataLocked.wallpaperComponent;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Rect semGetWallpaperCropHint(int i) {
        WallpaperData peekPairingConsideredWallpaperDataLocked;
        checkPermission("android.permission.SET_WALLPAPER");
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, currentUserId);
        }
        if (peekPairingConsideredWallpaperDataLocked != null) {
            return peekPairingConsideredWallpaperDataLocked.cropHint;
        }
        return null;
    }

    public final int semGetWallpaperType(int i) {
        int currentUserId = getCurrentUserId();
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, currentUserId);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    SemWallpaperManagerService.putLog("semGetWallpaperType : return -1. which=" + i);
                    return -1;
                }
                int i2 = peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mWpType;
                Slog.d("WallpaperManagerService", "semGetWallpaperType: which=" + i + ", return type=" + i2 + " (called by userId= " + currentUserId + ")");
                return i2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean semIsPreloadedWallpaper(int i, int i2) {
        synchronized (this.mLock) {
            try {
                WallpaperData peekPairingConsideredWallpaperDataLocked = peekPairingConsideredWallpaperDataLocked(i, i2);
                if (peekPairingConsideredWallpaperDataLocked == null) {
                    return false;
                }
                return peekPairingConsideredWallpaperDataLocked.mSemWallpaperData.mIsPreloaded;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void semRequestWallpaperColorsAnalysis(int i, String str) {
        if (Binder.getCallingUid() != 1000 && !Constants.SYSTEMUI_PACKAGE_NAME.equals(str) && !"com.samsung.android.app.dressroom".equals(str) && !"com.samsung.android.wallpaper.live".equals(str)) {
            throw new SecurityException("No permission to invoke semRequestWallpaperColorsAnalysis()");
        }
        Log.d("WallpaperManagerService", "semRequestWallpaperColorsAnalysis : which = " + i + ", callingPackage = " + str);
        int currentUserId = getCurrentUserId();
        int type = this.mSemService.mDesktopMode.isDesktopSingleMode() ? WhichChecker.getType(i) | 8 : this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
        try {
            Iterator it = this.mKeyguardListenerList.iterator();
            while (it.hasNext()) {
                IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
                if (iWallpaperManagerCallback != null) {
                    iWallpaperManagerCallback.onSemWallpaperColorsAnalysisRequested(type, currentUserId);
                }
            }
        } catch (RemoteException e) {
            Log.e("WallpaperManagerService", "semRequestWallpaperColorsAnalysis : e=" + e);
        }
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.mLegibilityColor.extractColor(type, semWallpaperManagerService.mOrientation == 2);
    }

    public final void semSendWallpaperCommand(int i, String str, Bundle bundle) {
        WallpaperData wallpaperData;
        DisplayConnector displayConnectorOrCreate;
        IWallpaperEngine iWallpaperEngine;
        int callingUid = Binder.getCallingUid();
        if (!isSignedWithPlatformSignature(callingUid)) {
            Log.e("WallpaperManagerService", "semSendWallpaperCommand: caller is not system app. uid=" + callingUid);
            return;
        }
        if (!WhichChecker.isSingleType(i)) {
            Log.w("WallpaperManagerService", "semSendWallpaperCommand: unsupported which value. which=" + i);
            return;
        }
        int sourceWhich = getSourceWhich(this.mSemService.getModeEnsuredWhich(i), this.mCurrentUserId);
        int i2 = 0;
        if (WhichChecker.isSubDisplay(sourceWhich)) {
            wallpaperData = WhichChecker.isLock(sourceWhich) ? this.mLastSubLockWallpaper : this.mLastSubWallpaper;
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                i2 = 1;
            }
        } else if (WhichChecker.isVirtualDisplay(sourceWhich)) {
            WallpaperData wallpaperData2 = this.mLastVirtualWallpaper;
            i2 = this.mActiveVirtualDisplayId;
            wallpaperData = wallpaperData2;
        } else {
            wallpaperData = WhichChecker.isLock(sourceWhich) ? this.mLastLockWallpaper : this.mLastWallpaper;
        }
        if (wallpaperData == null) {
            Log.w("WallpaperManagerService", "semSendWallpaperCommand: failed to determine current wallpaper. which=" + i);
            return;
        }
        WallpaperConnection wallpaperConnection = wallpaperData.connection;
        if (wallpaperConnection == null || (displayConnectorOrCreate = wallpaperConnection.getDisplayConnectorOrCreate(i2)) == null || (iWallpaperEngine = displayConnectorOrCreate.mEngine) == null) {
            return;
        }
        try {
            iWallpaperEngine.dispatchWallpaperCommand(str, 0, 0, 0, bundle);
        } catch (RemoteException e) {
            Slog.e("WallpaperManagerService", "semSendWallpaperCommand: e=" + e);
        }
    }

    public final void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) {
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
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Parameter which and the value from colors are not matched. which = ", ", colorWhich = ");
                m.append(semWallpaperColors.getWhich());
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(m.toString());
                illegalArgumentException.printStackTrace();
                throw illegalArgumentException;
            }
        }
        if (i == 0) {
            Slog.e("WallpaperManagerService", "semSetDLSWallpaperColors: which is 0");
            return;
        }
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
                if (peekWallpaperDataLocked == null) {
                    Slog.e("WallpaperManagerService", "semSetDLSWallpaperColors wallpaper == null");
                    return;
                }
                SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
                SemWallpaperColors semWallpaperColors2 = semWallpaperData.mDlsSemColors;
                semWallpaperData.mDlsSemColors = semWallpaperColors;
                if (semWallpaperColors2 == null && semWallpaperColors == null) {
                    return;
                }
                notifySemColorListeners(0, peekWallpaperDataLocked);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void semSetSmartCropRect(int i, Rect rect, Rect rect2) {
        checkPermission("android.permission.SET_WALLPAPER");
        if (rect2.left < 0 || rect2.top < 0 || rect2.right < 0 || rect2.bottom < 0) {
            Log.addLogString("WallpaperManagerService", "invalid rect " + rect2);
            return;
        }
        Log.d("WallpaperManagerService", "semSetSmartCropRect, " + i + ", " + rect + ", " + rect2);
        synchronized (this.mLock) {
            try {
                WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(this.mCurrentUserId, i);
                if (peekWallpaperDataLocked == null) {
                    Slog.e("WallpaperManagerService", "semSetSmartCropRect wallpaper == null");
                    return;
                }
                if (WhichChecker.isSystem(i) && !this.mImageWallpaper.equals(peekWallpaperDataLocked.wallpaperComponent)) {
                    Slog.e("WallpaperManagerService", "semSetSmartCropRect request is not for image wallpaper");
                    return;
                }
                SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
                if (semWallpaperData.mLandscapeColors != null) {
                    Slog.e("WallpaperManagerService", "landscapeColors is calculated already");
                    return;
                }
                semWallpaperData.mSmartCropOriginalRect = new Rect(rect);
                semWallpaperData.mSmartCropRect = new Rect(rect2);
                this.mSemService.mLegibilityColor.extractColor(semWallpaperData.mWhich, true);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void semSetUri(String str, boolean z, int i, int i2, String str2, int i3, Bundle bundle) {
        WallpaperData wallpaperSafeLocked;
        WallpaperData wallpaperData;
        checkPermission("android.permission.SET_WALLPAPER");
        if (!isSignedWithPlatformSignature(Binder.getCallingUid())) {
            throw new SecurityException("Only the platform signed application can invoke semSetUri()");
        }
        if (!this.mSemService.mKnox.isWallpaperChangeAllowed()) {
            Slog.i("WallpaperManagerService", "semSetUri: wallpaper change not allowed");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Uri parse = Uri.parse(str);
            String scheme = parse.getScheme();
            String str3 = parse.getHost() + parse.getPath();
            StringBuilder sb = new StringBuilder("semSetUri: path = ");
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
            sb.append(bundle != null);
            Slog.i("WallpaperManagerService", sb.toString());
            int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
            synchronized (this.mLock) {
                try {
                    if (WhichChecker.isSystemAndLock(modeEnsuredWhich)) {
                        int mode = WhichChecker.getMode(modeEnsuredWhich);
                        wallpaperSafeLocked = getWallpaperSafeLocked(i3, mode | 1);
                        wallpaperData = getWallpaperSafeLocked(i3, mode | 2);
                    } else {
                        wallpaperSafeLocked = getWallpaperSafeLocked(i3, modeEnsuredWhich);
                        wallpaperData = null;
                    }
                } finally {
                }
            }
            if (WhichChecker.isVirtualDisplay(modeEnsuredWhich) && isSameRequest(i2, str, wallpaperSafeLocked)) {
                Log.d("WallpaperManagerService", "semSetUri: Ignoring same request as previous one.");
                return;
            }
            wallpaperSafeLocked.mSemWallpaperData.mUri = str;
            if (WhichChecker.isSystemAndLock(modeEnsuredWhich) && wallpaperData != null) {
                wallpaperData.mSemWallpaperData.mUri = str;
            }
            if (i2 != -1) {
                setUriInternal(wallpaperSafeLocked, z, modeEnsuredWhich, i2, str2, i3, bundle);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParcelFileDescriptor semSetWallpaper(String str, String str2, int[] iArr, List list, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2, int i3, boolean z2, Bundle bundle2) {
        String str3;
        int i4;
        Rect rect;
        SparseArray sparseArray;
        int i5;
        int i6 = i;
        int i7 = i3;
        int handleIncomingUser = ActivityManager.handleIncomingUser(IWallpaperManager.Stub.getCallingPid(), IWallpaperManager.Stub.getCallingUid(), i2, false, true, "changing wallpaper", null);
        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("semSetWallpaper: uri = ", str, " callingPackage = ", str2, ", screenOrientations = ");
        m.append(iArr == null ? null : Arrays.stream(iArr).boxed().toList());
        m.append(" crops = ");
        m.append(list);
        m.append(" allowBackup = ");
        m.append(z);
        m.append(" which = 0x");
        m.append(Integer.toHexString(i));
        m.append(" wallpaperType = ");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i7, handleIncomingUser, " userId = ", " current userId = ", m);
        m.append(this.mCurrentUserId);
        m.append(" extras = ");
        if (bundle2 != null) {
            str3 = bundle2.keySet().size() + " fields";
        } else {
            str3 = "null";
        }
        m.append(str3);
        m.append(" isDexEnabled = ");
        m.append(this.mSemService.mDesktopMode.isDesktopModeEnabled(i6));
        Log.d("WallpaperManagerService", m.toString());
        checkPermission("android.permission.SET_WALLPAPER");
        if (bundle2 != null && Binder.getCallingUid() != 1000 && !Constants.SYSTEMUI_PACKAGE_NAME.equals(str2) && !"com.samsung.android.app.dressroom".equals(str2)) {
            throw new SecurityException("Only the system or SystemUI may invoke semSetWallpaper with extras");
        }
        if ((i6 & 3) == 0) {
            Slog.e("WallpaperManagerService", "Must specify a valid wallpaper category to set");
            throw new IllegalArgumentException("Must specify a valid wallpaper category to set");
        }
        if (!isWallpaperSupported(str2) || !isSetWallpaperAllowed(str2)) {
            Log.e("WallpaperManagerService", "semSetWallpaper: callingPackage is wrong.");
            return null;
        }
        this.mSemService.getClass();
        if (!SemWallpaperManagerService.isSupportingMode(i)) {
            Log.e("WallpaperManagerService", "semSetWallpaper: [" + WhichChecker.getMode(i) + "] mode isn't support");
            return null;
        }
        WallpaperDisplayHelper wallpaperDisplayHelper = this.mWallpaperDisplayHelper;
        wallpaperDisplayHelper.getClass();
        Point point = new Point();
        wallpaperDisplayHelper.mDisplayManager.getDisplay(0).getSize(point);
        int orientation = WallpaperManager.getOrientation(point);
        SparseArray cropMap = !Flags.multiCrop() ? null : getCropMap(list, iArr);
        Rect rect2 = (Flags.multiCrop() || list == null) ? null : (Rect) list.get(0);
        boolean isFromForegroundApp = !Flags.multiCrop() ? false : isFromForegroundApp(str2);
        if (rect2 == null && !Flags.multiCrop()) {
            rect2 = new Rect(0, 0, 0, 0);
        } else if (!Flags.multiCrop() && (rect2.width() < 0 || rect2.height() < 0 || rect2.left < 0 || rect2.top < 0)) {
            throw new IllegalArgumentException("Invalid crop rect supplied: " + rect2);
        }
        Rect rect3 = rect2;
        Slog.d("WallpaperManagerService", "semSetWallpaper: Start invokePrepare");
        if (i7 == -1) {
            SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
            Uri parse = Uri.parse(str);
            semWallpaperManagerService.getClass();
            i7 = "multipack".equals(parse.getScheme()) ? 3 : -1;
        }
        if (i7 == 3) {
            SemWallpaperManagerService semWallpaperManagerService2 = this.mSemService;
            Uri parse2 = Uri.parse(str);
            semWallpaperManagerService2.getClass();
            i4 = i7;
            rect = rect3;
            sparseArray = cropMap;
            i5 = orientation;
            Bundle invokePrepare = invokePrepare(handleIncomingUser, i, i4, this.mImageWallpaper, SemWallpaperManagerService.buildCustompackParams(i6, parse2));
            if (invokePrepare != null) {
                boolean z3 = invokePrepare.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT, false);
                String string = invokePrepare.getString("reason");
                if (!z3) {
                    BootReceiver$$ExternalSyntheticOutline0.m("semSetWallpaper: Failed. ", string, "WallpaperManagerService");
                }
            }
            return null;
        }
        i4 = i7;
        rect = rect3;
        sparseArray = cropMap;
        i5 = orientation;
        Slog.d("WallpaperManagerService", "semSetWallpaper: End invokePrepare");
        if (SemDualAppManager.isDualAppId(handleIncomingUser)) {
            handleIncomingUser = 0;
        }
        if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER && this.mSemService.mDesktopMode.isDesktopSingleMode()) {
            i6 = (i6 & (-9)) | 4;
        }
        if (!"com.samsung.android.themecenter".equals(str2) && !Constants.SYSTEMUI_PACKAGE_NAME.equals(str2)) {
            i6 = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i6);
        }
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i6);
        int mode = WhichChecker.getMode(modeEnsuredWhich);
        if (!WhichChecker.isVirtualDisplay(modeEnsuredWhich)) {
            this.mSemService.mDefaultWallpaper.updateTransparencySettingIfNeed(modeEnsuredWhich, str2, z2);
        }
        synchronized (this.mLock) {
            try {
                WallpaperData wallpaperData = this.mWallpaperMap.get(handleIncomingUser, 0);
                boolean z4 = wallpaperData != null && this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
                boolean isSystemAndLockPaired = this.mSemService.isSystemAndLockPaired(mode, handleIncomingUser);
                if (WhichChecker.isSystem(modeEnsuredWhich) && isSystemAndLockPaired) {
                    if (z4) {
                        Slog.i("WallpaperManagerService", "semSetWallpaper: Migrating current wallpaper to be lock-only before updating system wallpaper");
                        migrateStaticSystemToLockWallpaperLocked(handleIncomingUser, modeEnsuredWhich);
                    } else {
                        Slog.i("WallpaperManagerService", "semSetWallpaper: Migrating current live wallpaper to be lock-only before updating system wallpaper");
                        migrateLiveSystemToLockWallpaperLocked(mode, handleIncomingUser);
                    }
                }
                WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(handleIncomingUser, modeEnsuredWhich);
                wallpaperSafeLocked.cleanUp();
                int i8 = i4;
                checkWallpaperData(handleIncomingUser, i8, modeEnsuredWhich, wallpaperSafeLocked);
                if (this.mPendingMigrationViaStatic != null) {
                    Slog.w("WallpaperManagerService", "semSetWallpaper: Starting new static wp migration before previous migration finished");
                }
                this.mPendingMigrationViaStatic = new WallpaperObserver(this, wallpaperSafeLocked, WhichChecker.getMode(modeEnsuredWhich));
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    wallpaperSafeLocked.name = "";
                    wallpaperSafeLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                    wallpaperSafeLocked.imageWallpaperPending = true;
                    wallpaperSafeLocked.mSystemWasBoth = isSystemAndLockPaired;
                    wallpaperSafeLocked.mWhich = modeEnsuredWhich;
                    wallpaperSafeLocked.setComplete = iWallpaperManagerCallback;
                    if (!Flags.multiCrop()) {
                        isFromForegroundApp = isFromForegroundApp(str2);
                    }
                    wallpaperSafeLocked.fromForegroundApp = isFromForegroundApp;
                    if (!Flags.multiCrop()) {
                        wallpaperSafeLocked.cropHint.set(rect);
                    }
                    if (Flags.multiCrop()) {
                        wallpaperSafeLocked.mCropHints = sparseArray;
                    }
                    wallpaperSafeLocked.allowBackup = z;
                    wallpaperSafeLocked.mWallpaperDimAmount = getWallpaperDimAmount();
                    wallpaperSafeLocked.mOrientationWhenSet = i5;
                    wallpaperSafeLocked.wallpaperComponent = this.mImageWallpaper;
                    wallpaperSafeLocked.setCallingPackage(str2);
                    SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
                    semWallpaperData.mIsPreloaded = z2;
                    semWallpaperData.mWpType = i8;
                    semWallpaperData.mWhich = modeEnsuredWhich;
                    semWallpaperData.mIsCopied = false;
                    semWallpaperData.mUri = str;
                    int i9 = bundle2 != null ? bundle2.getInt("orientation", 0) : 0;
                    if (i9 == 0) {
                        i9 = this.mSemService.mOrientation;
                    }
                    SemWallpaperData semWallpaperData2 = wallpaperSafeLocked.mSemWallpaperData;
                    semWallpaperData2.mOrientation = i9;
                    semWallpaperData2.mExternalParams = bundle2;
                    Log.d("WallpaperManagerService", "semSetWallpaper: Updated");
                    wallpaperSafeLocked.mBindSource = WallpaperData.BindSource.SET_STATIC;
                    if (canBindComponentNow(wallpaperSafeLocked.mWhich | mode)) {
                        bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperSafeLocked, null, null);
                    }
                    WallpaperObserver wallpaperObserver = this.mPendingMigrationViaStatic;
                    this.mPendingMigrationViaStatic = null;
                    if (wallpaperObserver != null) {
                        wallpaperObserver.complete();
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return null;
    }

    public final void setAnimatedWallpaper(String str, String str2, int i, boolean z) {
        WallpaperData wallpaperSafeLocked;
        checkPermission("android.permission.SET_WALLPAPER");
        if (this.mSemService.mKnox.isWallpaperChangeAllowed()) {
            int i2 = this.mCurrentUserId;
            if (TextUtils.isEmpty(str)) {
                Slog.e("WallpaperManagerService", "packageName is null or empty. packageName = " + str + ", userId = " + i2);
                return;
            }
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "setAnimatedWallpaper pkgName = ", str, ", userId = ", ", callingPackage = ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, str2, ", which = ", ", allowBackup = ", m);
            AnyMotionDetector$$ExternalSyntheticOutline0.m("WallpaperManagerService", m, z);
            this.mSemService.deleteThumbnailFile(i, i2);
            synchronized (this.mLock) {
                try {
                    wallpaperSafeLocked = getWallpaperSafeLocked(i2, i);
                    wallpaperSafeLocked.mSystemWasBoth = this.mSemService.isSystemAndLockPaired(WhichChecker.getMode(i), i2);
                    this.mPendingMigrationViaStatic = new WallpaperObserver(this, wallpaperSafeLocked, WhichChecker.getMode(i));
                    wallpaperSafeLocked.mSemWallpaperData.mWpType = 4;
                    wallpaperSafeLocked.cleanUp();
                    SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
                    semWallpaperData.mAnimatedPkgName = str;
                    semWallpaperData.mIsPreloaded = false;
                    semWallpaperData.mUri = null;
                    semWallpaperData.mExternalParams = null;
                    wallpaperSafeLocked.setCallingPackage(str2);
                    wallpaperSafeLocked.allowBackup = z;
                    wallpaperSafeLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperSafeLocked, null, null);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        WallpaperObserver wallpaperObserver = this.mPendingMigrationViaStatic;
                        this.mPendingMigrationViaStatic = null;
                        if (wallpaperObserver != null) {
                            wallpaperObserver.complete();
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (WhichChecker.isLock(i)) {
                notifyLockWallpaperChanged(wallpaperSafeLocked.mSemWallpaperData.mWpType, i, null);
            }
            this.mSemService.mLegibilityColor.extractColor(wallpaperSafeLocked.mSemWallpaperData.mWhich, false);
        }
    }

    public final boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
        checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW");
        synchronized (this.mLock) {
            try {
                Log.d("WallpaperManagerService", "setCoverWallpaperCallback()");
                Iterator it = this.mCoverWallpaperListenerList.iterator();
                while (it.hasNext()) {
                    if (((IWallpaperManagerCallback) it.next()) == iWallpaperManagerCallback) {
                        Log.d("WallpaperManagerService", "IWallpaperManagerCallback is already added.");
                        return true;
                    }
                }
                this.mCoverWallpaperListenerClientList.add(new WallpaperManagerCallbackClient(iWallpaperManagerCallback));
                this.mCoverWallpaperListenerList.add(iWallpaperManagerCallback);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDimensionHints(int i, int i2, String str, int i3) {
        checkPermission("android.permission.SET_WALLPAPER_HINTS");
        if (isWallpaperSupported(str)) {
            int i4 = GLHelper.sMaxTextureSize;
            int min = Math.min(i, i4);
            int min2 = Math.min(i2, i4);
            synchronized (this.mLock) {
                try {
                    int callingUserId = UserHandle.getCallingUserId();
                    WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(callingUserId, 1);
                    if (min <= 0 || min2 <= 0) {
                        throw new IllegalArgumentException("width and height must be > 0");
                    }
                    if (this.mWallpaperDisplayHelper.mDisplayManager.getDisplay(i3) == null) {
                        throw new IllegalArgumentException("Cannot find display with id=" + i3);
                    }
                    WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i3);
                    if (min == displayDataOrCreate.mWidth) {
                        if (min2 != displayDataOrCreate.mHeight) {
                        }
                    }
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
                        DisplayConnector displayConnectorOrCreate = wallpaperConnection.getDisplayConnectorOrCreate(i3);
                        IWallpaperEngine iWallpaperEngine = displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
                        if (iWallpaperEngine != null) {
                            try {
                                iWallpaperEngine.setDesiredSize(min, min2);
                            } catch (RemoteException e) {
                                Slog.w("WallpaperManagerService", "Failed to set desired size", e);
                            }
                            notifyCallbacksLocked(wallpaperSafeLocked);
                        } else if (wallpaperSafeLocked.connection.mService != null && displayConnectorOrCreate != null) {
                            displayConnectorOrCreate.mDimensionsChanged = true;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setDisplayPadding(Rect rect, String str, int i) {
        checkPermission("android.permission.SET_WALLPAPER_HINTS");
        if (isWallpaperSupported(str)) {
            synchronized (this.mLock) {
                try {
                    if (this.mWallpaperDisplayHelper.mDisplayManager.getDisplay(i) == null) {
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
                        throw new IllegalArgumentException("padding width " + i2 + " exceeds max width " + maximumSizeDimension);
                    }
                    if (i3 > maximumSizeDimension) {
                        throw new IllegalArgumentException("padding height " + i3 + " exceeds max height " + maximumSizeDimension);
                    }
                    WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i);
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
                            DisplayConnector displayConnectorOrCreate = wallpaperConnection.getDisplayConnectorOrCreate(i);
                            IWallpaperEngine iWallpaperEngine = displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
                            if (iWallpaperEngine != null) {
                                try {
                                    iWallpaperEngine.setDisplayPadding(rect);
                                } catch (RemoteException e) {
                                    Slog.w("WallpaperManagerService", "Failed to set display padding", e);
                                }
                                notifyCallbacksLocked(wallpaperSafeLocked);
                            } else if (wallpaperSafeLocked.connection.mService != null && displayConnectorOrCreate != null) {
                                displayConnectorOrCreate.mPaddingChanged = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setInAmbientMode(boolean z, long j) {
        int i;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                this.mInAmbientMode = z;
                for (WallpaperData wallpaperData : getActiveWallpapers()) {
                    WallpaperInfo wallpaperInfo = wallpaperData.connection.mInfo;
                    i = (wallpaperInfo == null || wallpaperInfo.supportsAmbientMode()) ? 0 : i + 1;
                    IWallpaperEngine iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(0).mEngine;
                    if (iWallpaperEngine != null) {
                        arrayList.add(iWallpaperEngine);
                    }
                }
            } catch (Throwable th) {
                throw th;
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
    }

    public final boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
        checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW");
        synchronized (this.mLock) {
            try {
                Log.d("WallpaperManagerService", "setLockWallpaperCallback()");
                Iterator it = this.mKeyguardListenerList.iterator();
                while (it.hasNext()) {
                    if (((IWallpaperManagerCallback) it.next()) == iWallpaperManagerCallback) {
                        Log.d("WallpaperManagerService", "IWallpaperManagerCallback is already added.");
                        return true;
                    }
                }
                this.mKeyguardListenerClientList.add(new WallpaperManagerCallbackClient(iWallpaperManagerCallback));
                this.mKeyguardListenerList.add(iWallpaperManagerCallback);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMotionWallpaper(String str, String str2, int i, boolean z) {
        checkPermission("android.permission.SET_WALLPAPER");
        if (this.mSemService.mKnox.isWallpaperChangeAllowed()) {
            int i2 = this.mCurrentUserId;
            if (TextUtils.isEmpty(str)) {
                Slog.e("WallpaperManagerService", "packageName is null or empty. packageName=" + str + " userId=" + i2);
                return;
            }
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "setMotionWallpaper pkgName = ", str, ", userId = ", ", callingPackage = ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, str2, ", which = ", ", allowBackup = ", m);
            AnyMotionDetector$$ExternalSyntheticOutline0.m("WallpaperManagerService", m, z);
            int folderStateBasedWhich = this.mSemService.mSubDisplayMode.getFolderStateBasedWhich(i);
            this.mSemService.deleteThumbnailFile(folderStateBasedWhich, i2);
            synchronized (this.mLock) {
                try {
                    WallpaperData peekWallpaperDataLocked = peekWallpaperDataLocked(i2, folderStateBasedWhich);
                    if (peekWallpaperDataLocked == null) {
                        Slog.e("WallpaperManagerService", "setMotionWallpaper wallpaper == null");
                        return;
                    }
                    peekWallpaperDataLocked.mSystemWasBoth = this.mSemService.isSystemAndLockPaired(WhichChecker.getMode(folderStateBasedWhich), i2);
                    this.mPendingMigrationViaStatic = new WallpaperObserver(this, peekWallpaperDataLocked, WhichChecker.getMode(folderStateBasedWhich));
                    SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
                    semWallpaperData.mWpType = 1;
                    semWallpaperData.mMotionPkgName = str;
                    semWallpaperData.mIsPreloaded = false;
                    semWallpaperData.mUri = null;
                    semWallpaperData.mExternalParams = null;
                    peekWallpaperDataLocked.setCallingPackage(str2);
                    peekWallpaperDataLocked.allowBackup = z;
                    peekWallpaperDataLocked.cleanUp();
                    peekWallpaperDataLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        bindWallpaperComponentLocked(this.mImageWallpaper, true, true, peekWallpaperDataLocked, null, null);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        WallpaperObserver wallpaperObserver = this.mPendingMigrationViaStatic;
                        this.mPendingMigrationViaStatic = null;
                        if (wallpaperObserver != null) {
                            wallpaperObserver.complete();
                        }
                        if (WhichChecker.isLock(folderStateBasedWhich)) {
                            notifyLockWallpaperChanged(peekWallpaperDataLocked.mSemWallpaperData.mWpType, folderStateBasedWhich, null);
                        }
                        this.mSemService.mLegibilityColor.extractColor(peekWallpaperDataLocked.mSemWallpaperData.mWhich, false);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    public final boolean setSemWallpaperColors(WallpaperData wallpaperData, Bundle bundle) {
        if (bundle != null) {
            String str = WhichChecker.containsSystem(wallpaperData.mWhich) ? "systemLegibilityColors" : "lockLegibilityColors";
            Bundle bundle2 = bundle.getBundle(str);
            Log.d("WallpaperManagerService", "setSemWallpaperColors: wallpaper.mWhich = " + wallpaperData.mWhich + ", legibilityColorsBundle = " + bundle2);
            if (bundle2 != null) {
                this.mSemService.getClass();
                SemWallpaperColors parcelable = bundle2.getParcelable("rotation0");
                if (parcelable == null) {
                    Slog.w("SemWallpaperManagerService", "setSemWallpaperColors failed!");
                } else {
                    SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
                    semWallpaperData.mPrimarySemColors = parcelable;
                    parcelable.save(LegibilityColor.getWallpaperColorPath(wallpaperData.userId, semWallpaperData.mWhich, false));
                    SemWallpaperColors[] semWallpaperColorsArr = {(SemWallpaperColors) bundle2.getParcelable("rotation90"), (SemWallpaperColors) bundle2.getParcelable("rotation270")};
                    wallpaperData.mSemWallpaperData.mLandscapeColors = semWallpaperColorsArr;
                    Slog.d("SemWallpaperManagerService", "setSemWallpaperColors: colors = " + parcelable + " , landscapeColors90 = " + semWallpaperColorsArr[0] + " , landscapeColors270 = " + semWallpaperColorsArr[1]);
                }
                bundle.remove(str);
                return true;
            }
        }
        return false;
    }

    public final boolean setSnapshotSource(int i, String str) {
        checkPermission("android.permission.SET_WALLPAPER");
        SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
        semWallpaperManagerService.getClass();
        Log.d("SemWallpaperManagerService", "setSnapshotSource: key = " + i + ", source = " + str);
        synchronized (semWallpaperManagerService.mSnapshotDataLock) {
            try {
                SnapshotManager.SnapshotData snapshot = semWallpaperManagerService.mSnapshotManager.getSnapshot(semWallpaperManagerService.mCurrentUserId, i);
                if (snapshot == null) {
                    Log.e("SemWallpaperManagerService", "setSnapshotSource: No snapshot for key = " + i);
                    return false;
                }
                if (!TextUtils.isEmpty(str)) {
                    snapshot.source = str;
                }
                semWallpaperManagerService.mSnapshotManager.saveSettingsLockedForSnapshot(semWallpaperManagerService.mCurrentUserId);
                return true;
            } finally {
            }
        }
    }

    public void setSnapshotTestMode(boolean z) {
        if (SHIPPED) {
            return;
        }
        this.mSemService.setSnapshotTestMode(z);
    }

    public final void setUriInternal(WallpaperData wallpaperData, boolean z, int i, int i2, String str, int i3, Bundle bundle) {
        this.mSemService.deleteThumbnailFile(i, i3);
        wallpaperData.mWhich = i;
        int mode = WhichChecker.getMode(i);
        if (WhichChecker.isSystemAndLock(i)) {
            i = mode | 1;
        }
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        semWallpaperData.mWhich = i;
        semWallpaperData.mWpType = i2;
        semWallpaperData.mExternalParams = bundle;
        wallpaperData.setCallingPackage(str);
        wallpaperData.allowBackup = z;
        synchronized (this.mLock) {
            wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
            saveSettingsLocked(i3, mode);
        }
        wallpaperData.cleanUp();
        Log.d("WallpaperManagerService", "setUriInternal: which = " + i + ", type = " + i2);
        if (WhichChecker.isSystem(i) && i2 == 5) {
            if (((Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) || Rune.VIRTUAL_DISPLAY_WALLPAPER) && !WhichChecker.isSupportLock(i) && WhichChecker.isSystem(i)) {
                synchronized (this.mLock) {
                    bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperData, null, null);
                }
                if (WhichChecker.isWatchFaceDisplay(i) || WhichChecker.isVirtualDisplay(i)) {
                    notifyCoverWallpaperChanged(i2, i);
                }
                if (setSemWallpaperColors(wallpaperData, bundle)) {
                    return;
                }
                wallpaperData.mSemWallpaperData.mPrimarySemColors = null;
                this.mSemService.mLegibilityColor.extractColor(i, false);
            }
        }
    }

    public final void setVideoWallpaper(String str, String str2, String str3, String str4, int i, int i2, boolean z, Bundle bundle) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("setVideoWallpaper() packageName is null or empty. videoFilePath = ", str, ", themePackage = ", str2, ", userId = ");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i, ", videoFileName = ", str3, ", which = ", m);
            m.append(i2);
            m.append(", allowBackup = ");
            m.append(z);
            Slog.e("WallpaperManagerService", m.toString());
            return;
        }
        checkPermission("android.permission.SET_WALLPAPER");
        if (this.mSemService.mKnox.isWallpaperChangeAllowed()) {
            boolean equals = "com.samsung.android.wallpaper.res".equals(str2);
            StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("setVideoWallpaper() videoFilePath = ", str, ", themePackage = ", str2, ", userId = ");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i, ", callingPackage = ", str4, ", which = ", m2);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i2, ", videoFileName = ", str3, ", allowBackup = ", m2);
            BatteryService$$ExternalSyntheticOutline0.m(m2, z, ", isPreloaded = ", equals, ", extras = ");
            m2.append(bundle);
            Log.d("WallpaperManagerService", m2.toString());
            synchronized (this.mLock) {
                setVideoWallpaperLocked(i2, str, str2, str3, str4, i, equals, z, bundle);
            }
            WallpaperData wallpaperData = (WhichChecker.isLock(i2) ? this.mLockWallpaperMap : this.mWallpaperMap).get(i, WhichChecker.getMode(i2));
            if (wallpaperData != null) {
                if (WhichChecker.isWatchFaceDisplay(i2) || WhichChecker.isVirtualDisplay(i2)) {
                    notifyCoverWallpaperChanged(wallpaperData.mSemWallpaperData.mWpType, i2);
                }
                if (WhichChecker.isLock(i2)) {
                    notifyLockWallpaperChanged(wallpaperData.mSemWallpaperData.mWpType, i2, null);
                }
                if (setSemWallpaperColors(wallpaperData, bundle)) {
                    return;
                }
                SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
                semWallpaperData.mPrimarySemColors = null;
                this.mSemService.mLegibilityColor.extractColor(semWallpaperData.mWhich, false);
            }
        }
    }

    public final void setVideoWallpaperLocked(int i, String str, String str2, String str3, String str4, int i2, boolean z, boolean z2, Bundle bundle) {
        int modeEnsuredWhich = this.mSemService.getModeEnsuredWhich(i);
        this.mSemService.deleteThumbnailFile(modeEnsuredWhich, i2);
        WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i2, modeEnsuredWhich);
        wallpaperSafeLocked.mSystemWasBoth = this.mSemService.isSystemAndLockPaired(WhichChecker.getMode(modeEnsuredWhich), i2);
        this.mPendingMigrationViaStatic = new WallpaperObserver(this, wallpaperSafeLocked, WhichChecker.getMode(modeEnsuredWhich));
        SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
        semWallpaperData.mWpType = 8;
        semWallpaperData.mVideoFilePath = str;
        semWallpaperData.mVideoPkgName = str2;
        semWallpaperData.mVideoFileName = str3;
        semWallpaperData.mIsPreloaded = z;
        semWallpaperData.mUri = null;
        semWallpaperData.mExternalParams = bundle;
        wallpaperSafeLocked.mWhich = modeEnsuredWhich;
        wallpaperSafeLocked.setCallingPackage(str4);
        wallpaperSafeLocked.allowBackup = z2;
        wallpaperSafeLocked.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
        if (bundle != null) {
            Rect rect = (Rect) bundle.getParcelable("videoCropHint");
            Log.d("WallpaperManagerService", "saveVideoWallpaperData cropHint = " + rect);
            if (rect != null) {
                wallpaperSafeLocked.cropHint.set(rect);
            } else {
                wallpaperSafeLocked.cropHint.set(0, 0, 0, 0);
            }
        } else {
            wallpaperSafeLocked.cropHint.set(0, 0, 0, 0);
        }
        wallpaperSafeLocked.cleanUp();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            bindWallpaperComponentLocked(this.mImageWallpaper, true, true, wallpaperSafeLocked, null, null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            WallpaperObserver wallpaperObserver = this.mPendingMigrationViaStatic;
            this.mPendingMigrationViaStatic = null;
            if (wallpaperObserver != null) {
                wallpaperObserver.complete();
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0205 A[Catch: all -> 0x01ef, TryCatch #1 {all -> 0x01ef, blocks: (B:63:0x01d3, B:65:0x01db, B:67:0x01e7, B:70:0x01f5, B:74:0x0205, B:75:0x0210, B:76:0x021a, B:78:0x0225, B:79:0x022c, B:81:0x023d, B:82:0x0242, B:84:0x0248, B:86:0x0254, B:88:0x025a, B:89:0x025f, B:91:0x0264, B:92:0x0269, B:119:0x0312, B:120:0x0315, B:124:0x0317, B:125:0x031a, B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:62:0x01d3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0210 A[Catch: all -> 0x01ef, TryCatch #1 {all -> 0x01ef, blocks: (B:63:0x01d3, B:65:0x01db, B:67:0x01e7, B:70:0x01f5, B:74:0x0205, B:75:0x0210, B:76:0x021a, B:78:0x0225, B:79:0x022c, B:81:0x023d, B:82:0x0242, B:84:0x0248, B:86:0x0254, B:88:0x025a, B:89:0x025f, B:91:0x0264, B:92:0x0269, B:119:0x0312, B:120:0x0315, B:124:0x0317, B:125:0x031a, B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:62:0x01d3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0225 A[Catch: all -> 0x01ef, TryCatch #1 {all -> 0x01ef, blocks: (B:63:0x01d3, B:65:0x01db, B:67:0x01e7, B:70:0x01f5, B:74:0x0205, B:75:0x0210, B:76:0x021a, B:78:0x0225, B:79:0x022c, B:81:0x023d, B:82:0x0242, B:84:0x0248, B:86:0x0254, B:88:0x025a, B:89:0x025f, B:91:0x0264, B:92:0x0269, B:119:0x0312, B:120:0x0315, B:124:0x0317, B:125:0x031a, B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:62:0x01d3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x023d A[Catch: all -> 0x01ef, TryCatch #1 {all -> 0x01ef, blocks: (B:63:0x01d3, B:65:0x01db, B:67:0x01e7, B:70:0x01f5, B:74:0x0205, B:75:0x0210, B:76:0x021a, B:78:0x0225, B:79:0x022c, B:81:0x023d, B:82:0x0242, B:84:0x0248, B:86:0x0254, B:88:0x025a, B:89:0x025f, B:91:0x0264, B:92:0x0269, B:119:0x0312, B:120:0x0315, B:124:0x0317, B:125:0x031a, B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:62:0x01d3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0264 A[Catch: all -> 0x01ef, TryCatch #1 {all -> 0x01ef, blocks: (B:63:0x01d3, B:65:0x01db, B:67:0x01e7, B:70:0x01f5, B:74:0x0205, B:75:0x0210, B:76:0x021a, B:78:0x0225, B:79:0x022c, B:81:0x023d, B:82:0x0242, B:84:0x0248, B:86:0x0254, B:88:0x025a, B:89:0x025f, B:91:0x0264, B:92:0x0269, B:119:0x0312, B:120:0x0315, B:124:0x0317, B:125:0x031a, B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:62:0x01d3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0275 A[Catch: all -> 0x02bb, TryCatch #0 {all -> 0x02bb, blocks: (B:95:0x026f, B:97:0x0275, B:100:0x028d, B:102:0x029a, B:103:0x02bd, B:105:0x02cb, B:106:0x02ce, B:108:0x02e2, B:110:0x02ed, B:111:0x02f1, B:113:0x02f7, B:114:0x0301, B:118:0x0289), top: B:94:0x026f, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ParcelFileDescriptor setWallpaper(java.lang.String r18, java.lang.String r19, int[] r20, java.util.List r21, boolean r22, android.os.Bundle r23, int r24, android.app.IWallpaperManagerCallback r25, int r26, int r27, boolean r28, android.os.Bundle r29) {
        /*
            Method dump skipped, instructions count: 822
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.setWallpaper(java.lang.String, java.lang.String, int[], java.util.List, boolean, android.os.Bundle, int, android.app.IWallpaperManagerCallback, int, int, boolean, android.os.Bundle):android.os.ParcelFileDescriptor");
    }

    public final void setWallpaperComponent(ComponentName componentName) {
        setWallpaperComponent(componentName, "", 1, UserHandle.getCallingUserId(), null);
    }

    public boolean setWallpaperComponent(ComponentName componentName, String str, int i, int i2, Bundle bundle) {
        return setWallpaperComponentInternal(componentName, str, i, i2, false, isFromForegroundApp(str), bundle);
    }

    public final void setWallpaperComponentChecked(ComponentName componentName, String str, int i, int i2, Bundle bundle) {
        if (isWallpaperSupported(str) && isSetWallpaperAllowed(str)) {
            setWallpaperComponent(componentName, str, i, i2, bundle);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0256 A[Catch: all -> 0x0217, TryCatch #2 {all -> 0x0217, blocks: (B:82:0x01f8, B:85:0x0200, B:89:0x0209, B:91:0x020d, B:92:0x021c, B:94:0x0226, B:95:0x0231, B:104:0x0250, B:106:0x0256, B:107:0x025d, B:109:0x0263, B:111:0x0270, B:114:0x0278, B:116:0x027d, B:118:0x0282, B:120:0x0290, B:121:0x029e, B:122:0x02a5, B:137:0x0269, B:141:0x021a, B:148:0x02da), top: B:61:0x018b }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01dd A[Catch: all -> 0x01af, TryCatch #4 {all -> 0x01af, blocks: (B:64:0x018c, B:66:0x0194, B:68:0x0198, B:69:0x01b4, B:73:0x01c7, B:77:0x01d3, B:79:0x01da, B:80:0x01df, B:144:0x01dd), top: B:63:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01da A[Catch: all -> 0x01af, TryCatch #4 {all -> 0x01af, blocks: (B:64:0x018c, B:66:0x0194, B:68:0x0198, B:69:0x01b4, B:73:0x01c7, B:77:0x01d3, B:79:0x01da, B:80:0x01df, B:144:0x01dd), top: B:63:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0226 A[Catch: all -> 0x0217, TryCatch #2 {all -> 0x0217, blocks: (B:82:0x01f8, B:85:0x0200, B:89:0x0209, B:91:0x020d, B:92:0x021c, B:94:0x0226, B:95:0x0231, B:104:0x0250, B:106:0x0256, B:107:0x025d, B:109:0x0263, B:111:0x0270, B:114:0x0278, B:116:0x027d, B:118:0x0282, B:120:0x0290, B:121:0x029e, B:122:0x02a5, B:137:0x0269, B:141:0x021a, B:148:0x02da), top: B:61:0x018b }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setWallpaperComponentInternal(android.content.ComponentName r25, java.lang.String r26, int r27, int r28, boolean r29, boolean r30, android.os.Bundle r31) {
        /*
            Method dump skipped, instructions count: 758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.setWallpaperComponentInternal(android.content.ComponentName, java.lang.String, int, int, boolean, boolean, android.os.Bundle):boolean");
    }

    public final void setWallpaperDimAmount(float f) {
        setWallpaperDimAmountForUid(f, Binder.getCallingUid());
    }

    public final void setWallpaperDimAmountForUid(float f, int i) {
        WallpaperConnection wallpaperConnection;
        checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            synchronized (this.mLock) {
                try {
                    int i2 = this.mCurrentUserId;
                    if (i2 == -10000) {
                        i2 = 0;
                    }
                    WallpaperData wallpaperData = this.mWallpaperMap.get(i2, 0);
                    WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(i2, 0);
                    final float f2 = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    if (f == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        wallpaperData.mUidToDimAmount.remove(i);
                    } else {
                        wallpaperData.mUidToDimAmount.put(i, Float.valueOf(f));
                    }
                    SparseArray sparseArray = wallpaperData.mUidToDimAmount;
                    for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                        f2 = Math.max(f2, ((Float) sparseArray.valueAt(i3)).floatValue());
                    }
                    if (wallpaperData.mWallpaperDimAmount == f2) {
                        return;
                    }
                    wallpaperData.mWallpaperDimAmount = f2;
                    if (wallpaperData2 != null) {
                        wallpaperData2.mWallpaperDimAmount = f2;
                    }
                    boolean z = false;
                    for (WallpaperData wallpaperData3 : getActiveWallpapers()) {
                        if (wallpaperData3 != null && (wallpaperConnection = wallpaperData3.connection) != null) {
                            wallpaperConnection.forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    float f3 = f2;
                                    boolean z2 = WallpaperManagerService.SHIPPED;
                                    IWallpaperEngine iWallpaperEngine = ((WallpaperManagerService.DisplayConnector) obj).mEngine;
                                    if (iWallpaperEngine != null) {
                                        try {
                                            iWallpaperEngine.applyDimming(f3);
                                        } catch (RemoteException e) {
                                            Slog.w("WallpaperManagerService", "Can't apply dimming on wallpaper display connector", e);
                                        }
                                    }
                                }
                            });
                            if (!Flags.offloadColorExtraction()) {
                                wallpaperData3.mIsColorExtractedFromDim = true;
                                arrayList.add(wallpaperData3);
                            }
                            z = true;
                        }
                    }
                    if (z) {
                        saveSettingsLocked(wallpaperData.userId);
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        WallpaperData wallpaperData4 = (WallpaperData) it.next();
                        if (!Flags.offloadColorExtraction()) {
                            notifyWallpaperColorsChanged(wallpaperData4.mWhich, wallpaperData4);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void settingsRestored() {
        WallpaperData wallpaperData;
        boolean z;
        if (Binder.getCallingUid() != 1000) {
            throw new RuntimeException("settingsRestored() can only be called from the system process");
        }
        synchronized (this.mLock) {
            try {
                Slog.v("WallpaperManagerService", "loadSettingsLocked");
                loadSettingsLocked(0, 3, this.mSemService.getCurrentImplicitMode(), false);
                wallpaperData = this.mWallpaperMap.get(0, 0);
                wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                z = true;
                wallpaperData.allowBackup = true;
                ComponentName componentName = wallpaperData.nextWallpaperComponent;
                if (componentName == null || componentName.equals(this.mImageWallpaper)) {
                    if (!"".equals(wallpaperData.name)) {
                        z = this.mWallpaperDataParser.restoreNamedResourceLocked(wallpaperData);
                    }
                    if (z) {
                        this.mWallpaperCropper.generateCrop(wallpaperData);
                        wallpaperData.mBindSource = WallpaperData.BindSource.RESTORE_SETTINGS_STATIC;
                        bindWallpaperComponentLocked(wallpaperData.nextWallpaperComponent, true, false, wallpaperData, null, null);
                    }
                } else {
                    wallpaperData.mBindSource = WallpaperData.BindSource.RESTORE_SETTINGS_LIVE_SUCCESS;
                    if (!bindWallpaperComponentLocked(wallpaperData.nextWallpaperComponent, false, false, wallpaperData, null, null)) {
                        wallpaperData.mBindSource = WallpaperData.BindSource.RESTORE_SETTINGS_LIVE_FAILURE;
                        bindWallpaperComponentLocked(null, false, false, wallpaperData, null, null);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z) {
            Slog.e("WallpaperManagerService", "Failed to restore wallpaper: '" + wallpaperData.name + "'");
            wallpaperData.name = "";
            Environment.getUserSystemDirectory(0).delete();
        }
        synchronized (this.mLock) {
            saveSettingsLocked(0);
        }
    }

    public final void stopObserversLocked(int i) {
        stopObserver(this.mWallpaperMap.get(i, 0));
        stopObserver(this.mLockWallpaperMap.get(i, 0));
        this.mWallpaperMap.remove(i, 0);
        this.mLockWallpaperMap.remove(i, 0);
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

    public final void switchUser(int i, IRemoteCallback iRemoteCallback) {
        WallpaperData wallpaperData;
        WallpaperData wallpaperData2;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("WallpaperManagerService");
        timingsTraceAndSlog.traceBegin("Wallpaper_switch-user-" + i);
        if (i >= 0) {
            try {
                if (this.mIsWallpaperInitialized.get(i) == null) {
                    this.mIsWallpaperInitialized.set(i, Boolean.valueOf(isWallpaperFileExists(i)));
                }
            } catch (Throwable th) {
                timingsTraceAndSlog.traceEnd();
                throw th;
            }
        }
        synchronized (this.mLock) {
            int i2 = this.mCurrentUserId;
            if (i2 == i) {
                timingsTraceAndSlog.traceEnd();
                return;
            }
            SemWallpaperManagerService semWallpaperManagerService = this.mSemService;
            semWallpaperManagerService.mOldUserId = i2;
            semWallpaperManagerService.mCurrentUserId = i;
            Log.addLogString("WallpaperManagerService", "switchUser, change " + this.mCurrentUserId + " to " + i);
            this.mCurrentUserId = i;
            Log.d("WallpaperManagerService", "switchUser: userId = " + i + ", lidState = " + getLidState());
            if (i >= 0) {
                SemWallpaperManagerService semWallpaperManagerService2 = this.mSemService;
                synchronized (semWallpaperManagerService2.mSnapshotDataLock) {
                    semWallpaperManagerService2.mSnapshotManager.loadSettingsLockedForSnapshot(i);
                }
            }
            boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE;
            if (z) {
                this.mSemService.mSubDisplayMode.updateLidStateFromInputManager();
            }
            final WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, 5);
            final WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i, 6);
            File wallpaperLockDir = WallpaperUtils.getWallpaperLockDir(i);
            if (!wallpaperLockDir.exists()) {
                wallpaperLockDir.mkdirs();
            }
            if (z) {
                wallpaperData = getWallpaperSafeLocked(this.mCurrentUserId, 17);
                wallpaperData2 = WhichChecker.isSupportLock(18) ? getWallpaperSafeLocked(this.mCurrentUserId, 18) : null;
            } else {
                wallpaperData = null;
                wallpaperData2 = null;
            }
            if (i >= 0 && (this.mIsWallpaperInitialized.get(i) == null || !((Boolean) this.mIsWallpaperInitialized.get(i)).booleanValue())) {
                this.mSemService.setFactoryDefaultWallpaper(i, 5, wallpaperSafeLocked, wallpaperSafeLocked2);
                this.mSemService.setFactoryDefaultWallpaper(i, 6, wallpaperSafeLocked, wallpaperSafeLocked2);
                if (z) {
                    this.mSemService.setFactoryDefaultWallpaper(i, 17, wallpaperData, wallpaperData2);
                    if (WhichChecker.isSupportLock(18)) {
                        this.mSemService.setFactoryDefaultWallpaper(i, 18, wallpaperData, wallpaperData2);
                    }
                }
                this.mIsWallpaperInitialized.set(i, Boolean.TRUE);
            }
            WallpaperObserver wallpaperObserver = new WallpaperObserver(this, wallpaperSafeLocked);
            this.mWallpaperObserver = wallpaperObserver;
            if (wallpaperSafeLocked.wallpaperObserver == null) {
                SemWallpaperManagerService.SemWallpaperObserver.AnonymousClass1 anonymousClass1 = ((SemWallpaperManagerService.SemWallpaperObserver) wallpaperObserver.mSemObserver).mWallpaperFileObserver;
                wallpaperSafeLocked.wallpaperObserver = anonymousClass1;
                anonymousClass1.startWatching();
            }
            if (wallpaperSafeLocked2.wallpaperObserver == null) {
                SemWallpaperManagerService.SemWallpaperObserver.AnonymousClass1 lockWallpaperFileObserver = ((SemWallpaperManagerService.SemWallpaperObserver) this.mWallpaperObserver.mSemObserver).getLockWallpaperFileObserver();
                wallpaperSafeLocked2.wallpaperObserver = lockWallpaperFileObserver;
                lockWallpaperFileObserver.startWatching();
            }
            if (!this.mSemService.isSystemAndLockPaired(4, i)) {
                switchWallpaper(wallpaperSafeLocked2, null);
            }
            switchWallpaper(wallpaperSafeLocked, iRemoteCallback);
            if (z) {
                if (WhichChecker.isSupportLock(18) && !this.mSemService.isSystemAndLockPaired(18, i)) {
                    switchWallpaper(wallpaperData2, null);
                }
                switchWallpaper(wallpaperData, null);
            }
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER && this.mSemService.mVirtualDisplayMode.isVirtualWallpaperDisplay(this.mActiveVirtualDisplayId)) {
                WallpaperData wallpaperSafeLocked3 = getWallpaperSafeLocked(this.mCurrentUserId, 33);
                wallpaperSafeLocked3.mWhich |= 32;
                WallpaperConnection wallpaperConnection = wallpaperSafeLocked3.connection;
                if (wallpaperConnection == null || wallpaperConnection.mService == null) {
                    Log.d("WallpaperManagerService", "switchUser: userId = " + i + ", mActiveVirtualDisplayId = " + this.mActiveVirtualDisplayId);
                    switchWallpaper(wallpaperSafeLocked3, null);
                }
            }
            FgThread.getHandler().post(new Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    WallpaperData wallpaperData3 = wallpaperSafeLocked;
                    WallpaperData wallpaperData4 = wallpaperSafeLocked2;
                    boolean z2 = WallpaperManagerService.SHIPPED;
                    wallpaperManagerService.getClass();
                    if (Flags.offloadColorExtraction()) {
                        return;
                    }
                    wallpaperManagerService.notifyWallpaperColorsChanged(wallpaperData3.mWhich, wallpaperData3);
                    if (wallpaperData4 != wallpaperData3) {
                        wallpaperManagerService.notifyWallpaperColorsChanged(wallpaperData4.mWhich, wallpaperData4);
                    }
                    WallpaperData wallpaperData5 = wallpaperManagerService.mFallbackWallpaper;
                    wallpaperManagerService.notifyWallpaperColorsChanged(wallpaperData5.mWhich, wallpaperData5);
                }
            });
            timingsTraceAndSlog.traceEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0071 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void switchWallpaper(com.android.server.wallpaper.WallpaperData r11, android.os.IRemoteCallback r12) {
        /*
            r10 = this;
            java.lang.String r0 = "switchWallpaper: wallpaperType = "
            java.lang.Object r1 = r10.mLock
            monitor-enter(r1)
            com.samsung.server.wallpaper.SemWallpaperData r2 = r11.mSemWallpaperData     // Catch: java.lang.Throwable -> L52
            r3 = 0
            r2.mWaitingForUnlockUser = r3     // Catch: java.lang.Throwable -> L52
            android.content.ComponentName r3 = r11.wallpaperComponent     // Catch: java.lang.Throwable -> L52
            if (r3 == 0) goto L10
            goto L12
        L10:
            android.content.ComponentName r3 = r11.nextWallpaperComponent     // Catch: java.lang.Throwable -> L52
        L12:
            int r2 = r2.mWpType     // Catch: java.lang.Throwable -> L52
            java.lang.String r4 = "WallpaperManagerService"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L52
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L52
            r5.append(r2)     // Catch: java.lang.Throwable -> L52
            java.lang.String r0 = ", ComponentName = "
            r5.append(r0)     // Catch: java.lang.Throwable -> L52
            r5.append(r3)     // Catch: java.lang.Throwable -> L52
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L52
            android.util.Slog.d(r4, r0)     // Catch: java.lang.Throwable -> L52
            r0 = 0
            if (r2 == 0) goto L54
            r4 = 5
            if (r2 == r4) goto L54
            r4 = 8
            if (r2 == r4) goto L54
            r4 = 1
            if (r2 == r4) goto L54
            r4 = 4
            if (r2 == r4) goto L54
            r4 = 3
            if (r2 == r4) goto L54
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r2 != r4) goto L45
            goto L54
        L45:
            r4 = 7
            if (r2 != r4) goto L5e
            android.content.ComponentName r2 = r10.mImageWallpaper     // Catch: java.lang.Throwable -> L52
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L52
            if (r2 == 0) goto L5e
            r9 = r0
            goto L5f
        L52:
            r10 = move-exception
            goto L8a
        L54:
            android.content.ComponentName r2 = r10.mImageWallpaper     // Catch: java.lang.Throwable -> L52
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L52
            if (r2 != 0) goto L5e
            android.content.ComponentName r3 = r10.mImageWallpaper     // Catch: java.lang.Throwable -> L52
        L5e:
            r9 = r3
        L5f:
            int r2 = r11.userId     // Catch: java.lang.Throwable -> L52
            android.app.WallpaperInfo r8 = r10.getCoverWallpaperInfo(r2, r9)     // Catch: java.lang.Throwable -> L52
            r4 = 1
            r5 = 0
            r2 = r10
            r3 = r9
            r6 = r11
            r7 = r12
            boolean r2 = r2.bindWallpaperComponentLocked(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L52
            if (r2 != 0) goto L88
            android.content.pm.IPackageManager r2 = r10.mIPackageManager     // Catch: java.lang.Throwable -> L52 android.os.RemoteException -> L7d
            int r3 = r11.userId     // Catch: java.lang.Throwable -> L52 android.os.RemoteException -> L7d
            r4 = 262144(0x40000, double:1.295163E-318)
            android.content.pm.ServiceInfo r0 = r2.getServiceInfo(r9, r4, r3)     // Catch: java.lang.Throwable -> L52 android.os.RemoteException -> L7d
            goto L85
        L7d:
            r2 = move-exception
            java.lang.String r3 = "WallpaperManagerService"
            java.lang.String r4 = "Failure starting previous wallpaper; clearing"
            android.util.Slog.w(r3, r4, r2)     // Catch: java.lang.Throwable -> L52
        L85:
            r10.onSwitchWallpaperFailLocked(r11, r12, r0)     // Catch: java.lang.Throwable -> L52
        L88:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L52
            return
        L8a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L52
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.switchWallpaper(com.android.server.wallpaper.WallpaperData, android.os.IRemoteCallback):void");
    }

    public final void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
        RemoteCallbackList remoteCallbackList;
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, "unregisterWallpaperColorsCallback", null);
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = (SparseArray) this.mColorsChangedListeners.get(handleIncomingUser);
                if (sparseArray != null && (remoteCallbackList = (RemoteCallbackList) sparseArray.get(i2)) != null) {
                    remoteCallbackList.unregister(iWallpaperManagerCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateCurrentWallpapers(WallpaperData wallpaperData) {
        if (wallpaperData.userId != this.mCurrentUserId || wallpaperData.equals(this.mFallbackWallpaper)) {
            return;
        }
        int i = wallpaperData.mWhich;
        boolean isSubDisplay = WhichChecker.isSubDisplay(i);
        boolean isVirtualDisplay = WhichChecker.isVirtualDisplay(wallpaperData.mWhich);
        boolean isLock = WhichChecker.isLock(i);
        if (wallpaperData.mSemWallpaperData.mIsDesktopWallpaper && !this.mSemService.mDesktopMode.isDesktopSingleMode()) {
            if (isLock) {
                this.mLastDexLockWallpaper = wallpaperData;
                return;
            } else {
                this.mLastDexWallpaper = wallpaperData;
                return;
            }
        }
        if (isSubDisplay) {
            if (isLock) {
                this.mLastSubLockWallpaper = wallpaperData;
                return;
            } else {
                this.mLastSubWallpaper = wallpaperData;
                return;
            }
        }
        if (isVirtualDisplay) {
            this.mLastVirtualWallpaper = wallpaperData;
        } else if (isLock) {
            this.mLastLockWallpaper = wallpaperData;
        } else {
            this.mLastWallpaper = wallpaperData;
        }
    }

    public final void updateEngineFlags(WallpaperData wallpaperData) {
        WallpaperConnection wallpaperConnection = wallpaperData.connection;
        if (wallpaperConnection == null) {
            return;
        }
        wallpaperConnection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda4(1, this, wallpaperData));
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
                wallpaperConnection2.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda9(2, wallpaperConnection2));
                wallpaperConnection2.mDisplayConnector.clear();
                return;
            }
            return;
        }
        Predicate predicate = new Predicate() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                WallpaperManagerService.WallpaperConnection wallpaperConnection3 = wallpaperConnection2;
                Display display = (Display) obj;
                return (!wallpaperManagerService.mWallpaperDisplayHelper.isUsableDisplay(display, wallpaperConnection3.mClientUid) || display.getDisplayId() == 0 || wallpaperConnection3.containsDisplay(display.getDisplayId())) ? false : true;
            }
        };
        int i = WallpaperConnection.$r8$clinit;
        wallpaperConnection2.appendConnectorWithCondition(predicate);
        wallpaperConnection2.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda4(0, this, wallpaperConnection2));
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x018c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeAssetFiles(int r25, int r26, android.os.Bundle r27) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wallpaper.WallpaperManagerService.writeAssetFiles(int, int, android.os.Bundle):void");
    }
}
