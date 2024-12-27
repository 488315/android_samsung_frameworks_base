package com.samsung.server.wallpaper;

import android.R;
import android.app.ActivityManager;
import android.app.HomeVisibilityListener;
import android.app.SemWallpaperColors;
import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;
import com.android.server.wallpaper.WallpaperUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.server.wallpaper.ThumbnailFileManager;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.snapshot.SnapshotCallback;
import com.samsung.server.wallpaper.snapshot.SnapshotHelper;
import com.samsung.server.wallpaper.snapshot.SnapshotManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemWallpaperManagerService {
    public static final boolean SHIPPED = !Debug.semIsProductDev();
    public static final ArrayList sLogList = new ArrayList();
    public static boolean sSnapshotTestMode = false;
    public int mAodVisibilityState;
    public final CMFWallpaper mCMFWallpaper;
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final DefaultWallpaper mDefaultWallpaper;
    public final ComponentName mDefaultWallpaperComponent;
    public int mDensityDpi;
    public final DesktopMode mDesktopMode;
    public final DisplayManager mDisplayManager;
    public final AnonymousClass4 mHandler;
    public final AnonymousClass3 mHomeVisibilityListener;
    public final ComponentName mImageWallpaper;
    public final Knox mKnox;
    public final LegibilityColor mLegibilityColor;
    public final PreloadedLiveWallpaperHelper mLiveWallpaperHelper;
    public final OMCWallpaper mOMCWallpaper;
    public int mOrientation;
    public final SemWallpaperResourcesInfo mResourceInfo;
    public final SnapshotCallback mSnapshotCallback;
    public final Object mSnapshotDataLock;
    public final SnapshotManager mSnapshotManager;
    public final SubDisplayMode mSubDisplayMode;
    public final VirtualDisplayMode mVirtualDisplayMode;
    public int mCurrentUserId = -10000;
    public int mOldUserId = -10000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.server.wallpaper.SemWallpaperManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.server.wallpaper.SemWallpaperManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends HomeVisibilityListener {
        public AnonymousClass3() {}

        public final void onHomeVisibilityChanged(final boolean z) {
            post(
                    new Runnable() { // from class:
                        // com.samsung.server.wallpaper.SemWallpaperManagerService$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ComponentName componentName;
                            SemWallpaperManagerService.AnonymousClass3 anonymousClass3 =
                                    SemWallpaperManagerService.AnonymousClass3.this;
                            boolean z2 = z;
                            anonymousClass3.getClass();
                            Log.i("SemWallpaperManagerService", "isHomeActivityVisible : " + z2);
                            WallpaperManagerService.SemCallback semCallback =
                                    SemWallpaperManagerService.this.mCallback;
                            synchronized (WallpaperManagerService.this.mLock) {
                                try {
                                    WallpaperManagerService wallpaperManagerService =
                                            WallpaperManagerService.this;
                                    WallpaperData peekWallpaperDataLocked =
                                            wallpaperManagerService.peekWallpaperDataLocked(
                                                    wallpaperManagerService.mCurrentUserId, 1);
                                    if (peekWallpaperDataLocked != null) {
                                        WallpaperManagerService.this.mSemService
                                                .mLiveWallpaperHelper.getClass();
                                        boolean z3 = false;
                                        if (peekWallpaperDataLocked.mSemWallpaperData.mWpType == 7
                                                && (componentName =
                                                                peekWallpaperDataLocked
                                                                        .wallpaperComponent)
                                                        != null) {
                                            z3 =
                                                    PreloadedLiveWallpaperHelper
                                                            .isStockLiveWallpaperComponent(
                                                                    componentName);
                                        }
                                        Log.d(
                                                "WallpaperManagerService",
                                                "dispatchHomeVisibilityChanged: visible = "
                                                        + z2
                                                        + ", connection = "
                                                        + peekWallpaperDataLocked.connection
                                                        + ", systemWallpaper = "
                                                        + peekWallpaperDataLocked);
                                        if (z2) {
                                            if (z3) {
                                                WallpaperManagerService.WallpaperConnection
                                                        wallpaperConnection =
                                                                peekWallpaperDataLocked.connection;
                                                if (wallpaperConnection != null) {
                                                    if (wallpaperConnection.mService == null) {}
                                                }
                                                Log.addLogString(
                                                        "WallpaperManagerService",
                                                        "dispatchHomeVisibilityChanged, try to"
                                                            + " rebind");
                                                WallpaperManagerService.this
                                                        .bindWallpaperComponentLocked(
                                                                peekWallpaperDataLocked
                                                                        .wallpaperComponent,
                                                                true,
                                                                true,
                                                                peekWallpaperDataLocked,
                                                                null,
                                                                null);
                                            }
                                        }
                                    } else {
                                        Log.w(
                                                "WallpaperManagerService",
                                                "dispatchHomeVisibilityChanged : rebind failed.");
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SemWallpaperObserver {
        public final WallpaperManagerService.SemCallback mCallback;
        public final File mDesktopWallpaperFile;
        public final File mDesktopWallpaperLockFile;
        public final AnonymousClass1 mLockWallpaperFileObserver;
        public final File mSubDisplayWallpaperFile;
        public final File mSubDisplayWallpaperLockFile;
        public final File mVirtualDisplayWallpaperFile;
        public final File mWallpaperDir;
        public final File mWallpaperFile;
        public final AnonymousClass1 mWallpaperFileObserver;
        public final File mWallpaperLockDir;
        public final File mWallpaperLockFile;

        /* JADX WARN: Type inference failed for: r0v12, types: [com.samsung.server.wallpaper.SemWallpaperManagerService$SemWallpaperObserver$1] */
        /* JADX WARN: Type inference failed for: r4v2, types: [com.samsung.server.wallpaper.SemWallpaperManagerService$SemWallpaperObserver$1] */
        public SemWallpaperObserver(
                File file, File file2, WallpaperManagerService.SemCallback semCallback) {
            this.mWallpaperFileObserver = null;
            this.mLockWallpaperFileObserver = null;
            Log.d("SemWallpaperManagerService", "SemWallpaperObserver");
            this.mWallpaperDir = file;
            this.mWallpaperLockDir = file2;
            this.mWallpaperFile = new File(file, WallpaperUtils.getFileName(5));
            this.mWallpaperLockFile = new File(file2, WallpaperUtils.getFileName(6));
            this.mDesktopWallpaperFile = new File(file, WallpaperUtils.getFileName(9));
            this.mDesktopWallpaperLockFile = new File(file2, WallpaperUtils.getFileName(10));
            new File(file, WallpaperUtils.getInfoFileName(8));
            this.mSubDisplayWallpaperFile = new File(file, WallpaperUtils.getFileName(17));
            this.mSubDisplayWallpaperLockFile = new File(file2, WallpaperUtils.getFileName(18));
            new File(file, WallpaperUtils.getInfoFileName(16));
            this.mVirtualDisplayWallpaperFile = new File(file, WallpaperUtils.getFileName(33));
            new File(file, WallpaperUtils.getInfoFileName(32));
            final int i = 0;
            this.mLockWallpaperFileObserver =
                    new FileObserver(
                            this,
                            file2
                                    .getAbsolutePath()) { // from class:
                                                          // com.samsung.server.wallpaper.SemWallpaperManagerService.SemWallpaperObserver.1
                        public final /* synthetic */ SemWallpaperObserver this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.os.FileObserver
                        public final void onEvent(int i2, String str) {
                            switch (i) {
                                case 0:
                                    if (str != null) {
                                        File file3 = new File(this.this$0.mWallpaperLockDir, str);
                                        if (this.this$0.mWallpaperLockFile.equals(file3)
                                                || this.this$0.mDesktopWallpaperLockFile.equals(
                                                        file3)
                                                || this.this$0.mSubDisplayWallpaperLockFile.equals(
                                                        file3)) {
                                            this.this$0.mCallback.updateEvent(
                                                    i2, str, file3, false, true);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    if (str != null) {
                                        File file4 = new File(this.this$0.mWallpaperDir, str);
                                        this.this$0.mCallback.updateEvent(
                                                i2,
                                                str,
                                                file4,
                                                this.this$0.mWallpaperFile.equals(file4)
                                                        || this.this$0.mDesktopWallpaperFile.equals(
                                                                file4)
                                                        || this.this$0.mSubDisplayWallpaperFile
                                                                .equals(file4)
                                                        || this.this$0.mVirtualDisplayWallpaperFile
                                                                .equals(file4),
                                                false);
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i2 = 1;
            this.mWallpaperFileObserver =
                    new FileObserver(
                            this,
                            file
                                    .getAbsolutePath()) { // from class:
                                                          // com.samsung.server.wallpaper.SemWallpaperManagerService.SemWallpaperObserver.1
                        public final /* synthetic */ SemWallpaperObserver this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.os.FileObserver
                        public final void onEvent(int i22, String str) {
                            switch (i2) {
                                case 0:
                                    if (str != null) {
                                        File file3 = new File(this.this$0.mWallpaperLockDir, str);
                                        if (this.this$0.mWallpaperLockFile.equals(file3)
                                                || this.this$0.mDesktopWallpaperLockFile.equals(
                                                        file3)
                                                || this.this$0.mSubDisplayWallpaperLockFile.equals(
                                                        file3)) {
                                            this.this$0.mCallback.updateEvent(
                                                    i22, str, file3, false, true);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    if (str != null) {
                                        File file4 = new File(this.this$0.mWallpaperDir, str);
                                        this.this$0.mCallback.updateEvent(
                                                i22,
                                                str,
                                                file4,
                                                this.this$0.mWallpaperFile.equals(file4)
                                                        || this.this$0.mDesktopWallpaperFile.equals(
                                                                file4)
                                                        || this.this$0.mSubDisplayWallpaperFile
                                                                .equals(file4)
                                                        || this.this$0.mVirtualDisplayWallpaperFile
                                                                .equals(file4),
                                                false);
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            this.mCallback = semCallback;
        }

        public final AnonymousClass1 getLockWallpaperFileObserver() {
            Slog.d(
                    "SemWallpaperManagerService",
                    "getLockWallpaperFileObserver: mLockWallpaperFileObserver = "
                            + this.mLockWallpaperFileObserver
                            + ", mWallpaperLockDir.getAbsolutePath() = "
                            + this.mWallpaperLockDir.getAbsolutePath());
            return this.mLockWallpaperFileObserver;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.server.wallpaper.SemWallpaperManagerService$4] */
    public SemWallpaperManagerService(
            Context context,
            WallpaperManagerService.SemCallback semCallback,
            SnapshotCallback snapshotCallback,
            SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        this.mDensityDpi = -1;
        this.mOrientation = -1;
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        this.mDefaultWallpaper = null;
        this.mCMFWallpaper = null;
        this.mOMCWallpaper = null;
        this.mDesktopMode = null;
        this.mSubDisplayMode = null;
        this.mVirtualDisplayMode = null;
        this.mLegibilityColor = null;
        this.mKnox = null;
        this.mSnapshotDataLock = new Object();
        this.mHomeVisibilityListener = null;
        this.mAodVisibilityState = 0;
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.server.wallpaper.SemWallpaperManagerService.4
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1009) {
                            return;
                        }
                        SemWallpaperManagerService.this.mCallback.handleWallpaperBindingTimeout();
                    }
                };
        Log.d("SemWallpaperManagerService", "SemWallpaperManagerService");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mSnapshotCallback = snapshotCallback;
        this.mSnapshotManager = new SnapshotManager(context, snapshotCallback);
        this.mResourceInfo = semWallpaperResourcesInfo;
        this.mDefaultWallpaper =
                new DefaultWallpaper(context, semCallback, this, semWallpaperResourcesInfo);
        this.mCMFWallpaper = new CMFWallpaper(context, this, semWallpaperResourcesInfo);
        this.mOMCWallpaper = new OMCWallpaper(context, semCallback, this);
        Log.d("LockWallpaper", "LockWallpaper");
        this.mDesktopMode = new DesktopMode(context, semCallback, this);
        this.mSubDisplayMode = new SubDisplayMode(semCallback);
        this.mLegibilityColor = new LegibilityColor(context, semCallback, this);
        this.mKnox = new Knox(context);
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        this.mVirtualDisplayMode = new VirtualDisplayMode(displayManager);
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(
                        context.getResources()
                                .getString(R.string.network_logging_notification_text));
        this.mImageWallpaper = unflattenFromString;
        ComponentName cmfDefaultWallpaperComponent =
                WallpaperManager.getCmfDefaultWallpaperComponent(context);
        this.mDefaultWallpaperComponent =
                cmfDefaultWallpaperComponent != null
                        ? cmfDefaultWallpaperComponent
                        : unflattenFromString;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        PreloadedLiveWallpaperHelper preloadedLiveWallpaperHelper =
                new PreloadedLiveWallpaperHelper();
        preloadedLiveWallpaperHelper.mContext = context;
        ProviderRequester providerRequester = new ProviderRequester();
        providerRequester.mContext = context.getApplicationContext();
        preloadedLiveWallpaperHelper.mProviderRequester = providerRequester;
        preloadedLiveWallpaperHelper.mCallback = anonymousClass1;
        this.mLiveWallpaperHelper = preloadedLiveWallpaperHelper;
        Configuration configuration = context.getResources().getConfiguration();
        this.mDensityDpi = configuration.densityDpi;
        this.mOrientation = configuration.orientation;
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.CONFIGURATION_CHANGED");
        if (this.mHomeVisibilityListener == null) {
            this.mHomeVisibilityListener = new AnonymousClass3();
            ((ActivityManager) context.getSystemService("activity"))
                    .addHomeVisibilityListener(newFixedThreadPool, this.mHomeVisibilityListener);
        }
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.samsung.server.wallpaper.SemWallpaperManagerService.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if ("android.intent.action.CONFIGURATION_CHANGED".equals(action)) {
                            Configuration configuration2 =
                                    SemWallpaperManagerService.this
                                            .mContext
                                            .getResources()
                                            .getConfiguration();
                            int i = configuration2.densityDpi;
                            int i2 = configuration2.orientation;
                            StringBuilder m2 =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "onReceive: ", action, ", densityDpi=");
                            m2.append(SemWallpaperManagerService.this.mDensityDpi);
                            m2.append(", orientation [old=");
                            Log.d(
                                    "SemWallpaperManagerService",
                                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                                            SemWallpaperManagerService.this.mOrientation,
                                            i2,
                                            ", new=",
                                            "]",
                                            m2));
                            SemWallpaperManagerService semWallpaperManagerService =
                                    SemWallpaperManagerService.this;
                            if (semWallpaperManagerService.mDensityDpi != i) {
                                semWallpaperManagerService.mDensityDpi = i;
                                semWallpaperManagerService.mCallback.updateDisplayData();
                                if (!Rune.SUPPORT_HOME_CONTROLLER) {
                                    SemWallpaperManagerService.this.mLegibilityColor.extractColor(
                                            1, false);
                                    SemWallpaperManagerService.this.mLegibilityColor.extractColor(
                                            2, false);
                                }
                            }
                            SemWallpaperManagerService semWallpaperManagerService2 =
                                    SemWallpaperManagerService.this;
                            if (semWallpaperManagerService2.mOrientation != i2) {
                                semWallpaperManagerService2.mOrientation = i2;
                                if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                                        && semWallpaperManagerService2.mSubDisplayMode.mLidState
                                                == 0) {
                                    semWallpaperManagerService2.mCallback.notifySemWallpaperColors(
                                            6);
                                    SemWallpaperManagerService.this.mCallback
                                            .notifySemWallpaperColors(5);
                                    return;
                                }
                                WallpaperManagerService.SemCallback semCallback2 =
                                        semWallpaperManagerService2.mCallback;
                                synchronized (WallpaperManagerService.this.mLock) {
                                    try {
                                        Log.d(
                                                "WallpaperManagerService",
                                                "orientation is changed, notifySemColorListeners");
                                        SemWallpaperManagerService semWallpaperManagerService3 =
                                                WallpaperManagerService.this.mSemService;
                                        if (semWallpaperManagerService3
                                                .mLegibilityColor
                                                .mAllowScreenRotateSystem) {
                                            int folderStateBasedWhich =
                                                    semWallpaperManagerService3.mSubDisplayMode
                                                            .getFolderStateBasedWhich(1);
                                            WallpaperManagerService wallpaperManagerService =
                                                    WallpaperManagerService.this;
                                            WallpaperData peekWallpaperDataLocked =
                                                    wallpaperManagerService.peekWallpaperDataLocked(
                                                            wallpaperManagerService
                                                                    .getCurrentUserId(),
                                                            folderStateBasedWhich);
                                            if (peekWallpaperDataLocked != null) {
                                                WallpaperManagerService.this
                                                        .notifySemColorListeners(
                                                                0, peekWallpaperDataLocked);
                                            }
                                        }
                                        SemWallpaperManagerService semWallpaperManagerService4 =
                                                WallpaperManagerService.this.mSemService;
                                        if (semWallpaperManagerService4
                                                .mLegibilityColor
                                                .mAllowScreenRotateLock) {
                                            int folderStateBasedWhich2 =
                                                    semWallpaperManagerService4.mSubDisplayMode
                                                            .getFolderStateBasedWhich(2);
                                            WallpaperManagerService wallpaperManagerService2 =
                                                    WallpaperManagerService.this;
                                            WallpaperData peekWallpaperDataLocked2 =
                                                    wallpaperManagerService2
                                                            .peekWallpaperDataLocked(
                                                                    wallpaperManagerService2
                                                                            .getCurrentUserId(),
                                                                    folderStateBasedWhich2);
                                            if (peekWallpaperDataLocked2 != null) {
                                                WallpaperManagerService.this
                                                        .notifySemColorListeners(
                                                                0, peekWallpaperDataLocked2);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            }
                        }
                    }
                },
                m);
    }

    public static Bundle buildCustompackParams(int i, Uri uri) {
        if (uri == null) {
            return null;
        }
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "/data/overlays/homewallpaper/", uri.getHost() + uri.getPath());
        boolean isSubDisplay = WhichChecker.isSubDisplay(i);
        boolean isVirtualDisplay = WhichChecker.isVirtualDisplay(i);
        boolean isWatchFaceDisplay = WhichChecker.isWatchFaceDisplay(i);
        if (isVirtualDisplay || isWatchFaceDisplay) {
            isSubDisplay = true;
        }
        int i2 = !isSubDisplay ? 0 : 1;
        boolean booleanQueryParameter = uri.getBooleanQueryParameter("isMigration", false);
        boolean booleanQueryParameter2 = uri.getBooleanQueryParameter("isCustom", false);
        Bundle bundle = new Bundle();
        String str = booleanQueryParameter2 ? "USER.PACK." : "MULTI.PACK.";
        bundle.putString(
                "name", i2 != 0 ? str.concat("02") : str.concat(Constants.OTP_BIT_KG_ENABLED));
        bundle.putString("wallpaper_path", m);
        bundle.putInt(KnoxCustomManagerService.SCREEN, i2);
        bundle.putInt("isMigration", booleanQueryParameter ? 1 : 0);
        return bundle;
    }

    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        Boolean bool = Boolean.TRUE;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                bufferedReader.close();
                return sb.toString();
            }
            if (bool.booleanValue()) {
                sb.append(readLine);
                bool = Boolean.FALSE;
            } else {
                sb.append("\n");
                sb.append(readLine);
            }
        }
    }

    public static String getCallStackString() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int length = stackTrace.length;
        int i = 2;
        for (int i2 = 0; i2 < stackTrace.length; i2++) {
            if (stackTrace[i2].getMethodName().equals("getCallStackString")) {
                i = i2 + 1;
            }
        }
        if (stackTrace.length - i < 3) {
            length = stackTrace.length - i;
        }
        String str = "";
        for (int i3 = i; i3 < i + length && i3 < stackTrace.length; i3++) {
            StackTraceElement stackTraceElement = stackTrace[i3];
            str =
                    stackTraceElement.getClassName()
                            + "(line "
                            + stackTraceElement.getLineNumber()
                            + ") :"
                            + (i3 == i
                                    ? stackTraceElement.getMethodName()
                                    : stackTraceElement.getMethodName() + " -> \n" + str);
        }
        return str.length() > 0 ? XmlUtils$$ExternalSyntheticOutline0.m("(", str, ") ") : "";
    }

    public static String getStringFromFile(String str) {
        String str2;
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            try {
                str2 = convertStreamToString(fileInputStream);
            } catch (Exception e) {
                Log.e("SemWallpaperManagerService", "getStringFromFile " + e);
                fileInputStream.close();
                str2 = null;
            }
            return str2;
        } finally {
            fileInputStream.close();
        }
    }

    public static void initializeThumnailFile(int i, int i2, int i3, WallpaperData wallpaperData) {
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        if (semWallpaperData == null) {
            Log.d(
                    "SemWallpaperManagerService",
                    "initializeThumnailFile: SemWallpaperData is null.");
            return;
        }
        boolean isLock = WhichChecker.isLock(i);
        if (i2 == 8 && semWallpaperData.mVideoFirstFrameFile == null) {
            if (WhichChecker.isVirtualDisplay(i)) {
                semWallpaperData.mVideoFirstFrameFile =
                        new File(
                                isLock
                                        ? WallpaperUtils.getWallpaperLockDir(i3)
                                        : Environment.getUserSystemDirectory(i3),
                                isLock
                                        ? "wallpaper_first_virtual"
                                        : "wallpaper_first_virtual_home");
            } else if (WhichChecker.isSubDisplay(i)) {
                semWallpaperData.mVideoFirstFrameFile =
                        new File(
                                isLock
                                        ? WallpaperUtils.getWallpaperLockDir(i3)
                                        : Environment.getUserSystemDirectory(i3),
                                isLock ? "wallpaper_first_sub" : "wallpaper_first_sub_home");
            } else {
                semWallpaperData.mVideoFirstFrameFile =
                        new File(
                                isLock
                                        ? WallpaperUtils.getWallpaperLockDir(i3)
                                        : Environment.getUserSystemDirectory(i3),
                                isLock ? "wallpaper_first" : "wallpaper_first_home");
            }
        }
        if (i2 == 4 && isLock && semWallpaperData.mAnimatedBackground == null) {
            if (WhichChecker.isSubDisplay(i)) {
                semWallpaperData.mAnimatedBackground =
                        new File(
                                WallpaperUtils.getWallpaperLockDir(i3),
                                "wallpaper_animated_background_sub");
            } else {
                semWallpaperData.mAnimatedBackground =
                        new File(
                                WallpaperUtils.getWallpaperLockDir(i3),
                                "wallpaper_animated_background");
            }
        }
        if (i2 == 1 && isLock && semWallpaperData.mMotionBackground == null) {
            if (WhichChecker.isSubDisplay(i)) {
                semWallpaperData.mMotionBackground =
                        new File(
                                WallpaperUtils.getWallpaperLockDir(i3),
                                "wallpaper_motion_background_sub");
            } else {
                semWallpaperData.mMotionBackground =
                        new File(
                                WallpaperUtils.getWallpaperLockDir(i3),
                                "wallpaper_motion_background");
            }
        }
    }

    public static boolean isSupportingMode(int i) {
        int mode = WhichChecker.getMode(i);
        int type = WhichChecker.getType(i);
        if (mode != 16) {
            return true;
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && type == 2) ? false : true;
        }
        return false;
    }

    public static void putLog(String str) {
        Log.d("SemWallpaperManagerService", str);
        ArrayList arrayList = sLogList;
        synchronized (arrayList) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                arrayList.add(
                        (SimpleDateFormat.getDateTimeInstance().format(new Date(currentTimeMillis))
                                        + "."
                                        + (currentTimeMillis % 1000))
                                + "\n"
                                + str);
                if (arrayList.size() > 20) {
                    arrayList.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Bundle buildParams(int i, int i2, int i3) {
        WallpaperData onWallpaperDataRequested = this.mCallback.onWallpaperDataRequested(i, i2);
        if (i3 == 3) {
            return buildCustompackParams(
                    onWallpaperDataRequested.mWhich,
                    Uri.parse(onWallpaperDataRequested.mSemWallpaperData.mUri));
        }
        if (i3 != 1000) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "buildParams: userId = ", ", which = ", ", type = ");
            m.append(i3);
            Log.d("SemWallpaperManagerService", m.toString());
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        String str = onWallpaperDataRequested.mSemWallpaperData.mUri;
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("type", str.substring(str.lastIndexOf("/") + 1));
        }
        bundle.putInt(
                KnoxCustomManagerService.SCREEN,
                WhichChecker.isSubDisplay(onWallpaperDataRequested.mWhich) ? 1 : 0);
        return bundle;
    }

    public final void connectSnapshotForLiveWallpaper(int i, int i2, ArrayList arrayList) {
        int correspondingWhich;
        SnapshotManager.SnapshotData snapshotData;
        SnapshotManager.SnapshotData snapshotData2;
        int correspondingWhich2;
        boolean z = false;
        if (arrayList.size() > 0
                && (correspondingWhich2 = SnapshotHelper.getCorrespondingWhich(i)) > 0) {
            Log.d(
                    "SemWallpaperManagerService",
                    "shouldCheckCorrespondingWhichForLiveWallpaper: Check existance of"
                        + " correspondingWhich ["
                            + correspondingWhich2
                            + "]");
            z = arrayList.contains(Integer.valueOf(correspondingWhich2)) ^ true;
        }
        if (!z
                || (correspondingWhich = SnapshotHelper.getCorrespondingWhich(i)) <= 0
                || getSnapshotCount(correspondingWhich) <= 0) {
            return;
        }
        int i3 = this.mCurrentUserId;
        SnapshotManager snapshotManager = this.mSnapshotManager;
        Iterator it = snapshotManager.getRepositroy(i3).getAll().iterator();
        while (true) {
            snapshotData = null;
            if (!it.hasNext()) {
                snapshotData2 = null;
                break;
            } else {
                snapshotData2 = (SnapshotManager.SnapshotData) it.next();
                if (snapshotData2.hasWallpaperData(correspondingWhich)) {
                    break;
                }
            }
        }
        if (snapshotData2 == null
                || snapshotData2.getWallpaperData(correspondingWhich) == null
                || snapshotData2.hasWallpaperData(i)
                || !WhichChecker.isSystemAndLock(
                        snapshotData2.getWallpaperData(correspondingWhich).mWhich)) {
            return;
        }
        SnapshotManager.SnapshotRepository repositroy =
                snapshotManager.getRepositroy(this.mCurrentUserId);
        LinkedList linkedList = repositroy.mSnapshots;
        if (linkedList != null && linkedList.size() > 0) {
            snapshotData = (SnapshotManager.SnapshotData) repositroy.mSnapshots.getFirst();
        }
        if (snapshotData2.getConnectedSnapshotForLiveWallpaper(correspondingWhich) == -1) {
            snapshotData.setConnectedSnapshotForLiveWallpaper(i, snapshotData2.key);
            snapshotData2.setConnectedSnapshotForLiveWallpaper(correspondingWhich, i2);
        }
    }

    public final void deleteThumbnailFile(int i, int i2) {
        ThumbnailFileManager.getInstance().deleteThumbnailFiles(getModeEnsuredWhich(i), i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:

       r7 = r9.mSnapshotDataLock;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:

       monitor-enter(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:

       r9.mSnapshotManager.migrateToPriorSnapshot(r10, r11, r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:

       monitor-exit(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0055, code lost:

       r0.put(r3, 1004);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map doRestoreOrMigrate(int r10, int r11) {
        /*
            r9 = this;
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.Object r1 = r9.mSnapshotDataLock
            monitor-enter(r1)
            com.samsung.server.wallpaper.snapshot.SnapshotManager r2 = r9.mSnapshotManager     // Catch: java.lang.Throwable -> L78
            com.samsung.server.wallpaper.snapshot.SnapshotManager$SnapshotData r2 = r2.getSnapshot(r10, r11)     // Catch: java.lang.Throwable -> L78
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L78
            if (r2 == 0) goto L77
            java.util.ArrayList r1 = r2.getWhiches()
            java.util.Iterator r1 = r1.iterator()
        L19:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L77
            java.lang.Object r3 = r1.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r4 = r3.intValue()
            com.samsung.server.wallpaper.snapshot.SnapshotManager r5 = r9.mSnapshotManager
            com.samsung.server.wallpaper.snapshot.SnapshotManager$SnapshotRepository r5 = r5.getRepositroy(r10)
            int r6 = r5.getIndex(r11)
            int r6 = r6 + (-1)
        L35:
            if (r6 < 0) goto L65
            com.samsung.server.wallpaper.snapshot.SnapshotManager$SnapshotData r7 = r5.getByIndex(r6)
            if (r7 != 0) goto L46
            java.lang.String r7 = "SnapshotManager"
            java.lang.String r8 = "shouldRestoreSnapshot: Something wrong!"
            com.samsung.server.wallpaper.Log.e(r7, r8)
            goto L62
        L46:
            boolean r7 = r7.hasWallpaperData(r4)
            if (r7 == 0) goto L62
            java.lang.Object r7 = r9.mSnapshotDataLock
            monitor-enter(r7)
            com.samsung.server.wallpaper.snapshot.SnapshotManager r5 = r9.mSnapshotManager     // Catch: java.lang.Throwable -> L5f
            r5.migrateToPriorSnapshot(r10, r11, r4)     // Catch: java.lang.Throwable -> L5f
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L5f
            r4 = 1004(0x3ec, float:1.407E-42)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r0.put(r3, r4)
            goto L19
        L5f:
            r9 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L5f
            throw r9
        L62:
            int r6 = r6 + (-1)
            goto L35
        L65:
            r9.deleteThumbnailFile(r4, r10)
            int r5 = r9.restoreSnapshotInternal(r10, r4, r2)
            r9.postProcess(r10, r4, r2, r5)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r5)
            r0.put(r3, r4)
            goto L19
        L77:
            return r0
        L78:
            r9 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L78
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.server.wallpaper.SemWallpaperManagerService.doRestoreOrMigrate(int,"
                    + " int):java.util.Map");
    }

    public final int getCurrentImplicitMode() {
        return WhichChecker.determineMode(this.mSubDisplayMode.mLidState == 0);
    }

    public final ComponentName getDefaultPreloadedLiveWallpaperComponentName(int i) {
        ComponentName defaultLiveWallpaperComponentName =
                this.mResourceInfo.getDefaultLiveWallpaperComponentName(i);
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

    public final int getDefaultWallpaperType(int i) {
        return this.mResourceInfo.getDefaultWallpaperType(i, this.mCMFWallpaper.getDeviceColor());
    }

    public final int getDisplayId(int i) {
        if (this.mDesktopMode.isDesktopDualMode() && (i & 60) == 8) {
            return 2;
        }
        int i2 = 0;
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return 0;
        }
        boolean isSubDisplay = WhichChecker.isSubDisplay(i);
        if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
            i2 = isSubDisplay ? 1 : 0;
        } else {
            DisplayInfo displayInfo = new DisplayInfo();
            for (Display display :
                    this.mDisplayManager.getDisplays(
                            "com.samsung.android.hardware.display.category.BUILTIN")) {
                if (display != null && display.getDisplayInfo(displayInfo)) {
                    boolean z = (displayInfo.flags & 262144) != 0;
                    if ((isSubDisplay && z) || (!isSubDisplay && !z)) {
                        i2 = displayInfo.displayId;
                        break;
                    }
                } else {
                    Log.e(
                            "SemWallpaperManagerService",
                            "getDisplayId: failed to get display. display=" + display);
                }
            }
        }
        Log.d("SemWallpaperManagerService", "getDisplayId: which=" + i + ", displayId=" + i2);
        return i2;
    }

    public final int getModeEnsuredWhich(int i) {
        if (WhichChecker.getMode(i) != 0) {
            return i;
        }
        int currentImplicitMode = getCurrentImplicitMode() | WhichChecker.getType(i);
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                currentImplicitMode,
                "getModeEnsuredWhich: detected which = ",
                "SemWallpaperManagerService");
        return currentImplicitMode;
    }

    public final int getPairingConsideredWallpaperId(int i, int i2) {
        WallpaperData peekPairingConsideredWallpaperDataLocked;
        WallpaperManagerService.SemCallback semCallback = this.mCallback;
        synchronized (WallpaperManagerService.this.mLock) {
            peekPairingConsideredWallpaperDataLocked =
                    WallpaperManagerService.this.peekPairingConsideredWallpaperDataLocked(i, i2);
        }
        if (peekPairingConsideredWallpaperDataLocked == null) {
            return -1;
        }
        return peekPairingConsideredWallpaperDataLocked.wallpaperId;
    }

    public final int getSnapshotCount(int i) {
        SnapshotManager snapshotManager = this.mSnapshotManager;
        if (i == -1) {
            return snapshotManager.getRepositroy(this.mCurrentUserId).size();
        }
        SnapshotManager.SnapshotRepository repositroy =
                snapshotManager.getRepositroy(this.mCurrentUserId);
        int i2 = 0;
        if (repositroy.size() > 0) {
            Iterator it = repositroy.getAll().iterator();
            while (it.hasNext()) {
                if (((SnapshotManager.SnapshotData) it.next()).hasWallpaperData(i)) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public final File getThumbnailFile(int i, int i2, int i3) {
        ParcelFileDescriptor fetchThumbnailFile;
        ThumbnailFileManager thumbnailFileManager = ThumbnailFileManager.getInstance();
        thumbnailFileManager.getClass();
        File thumbnailFile = ThumbnailFileManager.getThumbnailFile(i, i2, i3);
        if (thumbnailFile.exists() && thumbnailFile.length() > 0) {
            return thumbnailFile;
        }
        int pairingConsideredWallpaperId = getPairingConsideredWallpaperId(i, i2);
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "getThumbnailFile: which=", ", userId=", ", wpId=");
        m.append(pairingConsideredWallpaperId);
        Log.i("SemWallpaperManagerService", m.toString());
        try {
            fetchThumbnailFile = this.mLiveWallpaperHelper.fetchThumbnailFile(i, i2, i3);
        } catch (IOException e) {
            Log.e("SemWallpaperManagerService", "getThumbnailFile: e=" + e);
        }
        if (fetchThumbnailFile == null) {
            if (fetchThumbnailFile != null) {
                fetchThumbnailFile.close();
            }
            return null;
        }
        try {
            int pairingConsideredWallpaperId2 = getPairingConsideredWallpaperId(i, i2);
            if (pairingConsideredWallpaperId2 == pairingConsideredWallpaperId) {
                if (thumbnailFileManager.writeThumbnailFile(i, i2, i3, fetchThumbnailFile)) {
                    fetchThumbnailFile.close();
                    return thumbnailFile;
                }
                fetchThumbnailFile.close();
                return null;
            }
            Log.w(
                    "SemWallpaperManagerService",
                    "getThumbnailFile: wallpaper changed during fetching the thumbnail. which="
                            + i
                            + ", wpId="
                            + pairingConsideredWallpaperId
                            + "->"
                            + pairingConsideredWallpaperId2);
            fetchThumbnailFile.close();
            return null;
        } finally {
        }
    }

    public final ParcelFileDescriptor getThumbnailFileDescriptor(int i, int i2, int i3) {
        try {
            File thumbnailFile = getThumbnailFile(i, i2, i3);
            if (thumbnailFile != null) {
                return ParcelFileDescriptor.open(thumbnailFile, 268435456);
            }
            return null;
        } catch (FileNotFoundException e) {
            Log.w("SemWallpaperManagerService", "getThumbnailFileDescriptor : e=" + e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0040, code lost:

       if (r3.mDefaultWallpaperComponent.equals(r3.mImageWallpaper) != false) goto L24;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getWallpaperTypeByComponentName(
            android.content.ComponentName r4, com.android.server.wallpaper.WallpaperData r5) {
        /*
            r3 = this;
            java.lang.String r0 = "SemWallpaperManagerService"
            r1 = 7
            r2 = 0
            if (r4 != 0) goto L43
            android.content.Context r4 = r3.mContext
            android.content.ComponentName r4 = android.app.WallpaperManager.getDefaultWallpaperComponent(r4)
            if (r4 != 0) goto L58
            com.samsung.server.wallpaper.SemWallpaperData r4 = r5.mSemWallpaperData
            int r4 = r4.mWhich
            int r5 = r3.getDefaultWallpaperType(r4)
            if (r5 != r1) goto L38
            android.content.Context r5 = r3.mContext
            boolean r5 = android.app.WallpaperManager.isDefaultOperatorWallpaper(r5, r4)
            if (r5 != 0) goto L38
            android.content.ComponentName r3 = r3.getDefaultPreloadedLiveWallpaperComponentName(r4)
            if (r3 == 0) goto L58
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "getWallpaperTypeByComponentName: Default live wallpaper = "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Slog.i(r0, r3)
            goto L59
        L38:
            android.content.ComponentName r4 = r3.mDefaultWallpaperComponent
            android.content.ComponentName r3 = r3.mImageWallpaper
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L59
            goto L58
        L43:
            android.content.ComponentName r3 = r3.mImageWallpaper
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L4c
            goto L59
        L4c:
            boolean r3 = com.samsung.android.wallpaper.Rune.SUPPORT_SUB_DISPLAY_MODE
            if (r3 == 0) goto L58
            boolean r3 = com.samsung.android.wallpaper.Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
            if (r3 == 0) goto L58
            com.samsung.server.wallpaper.SemWallpaperData r3 = r5.mSemWallpaperData
            int r3 = r3.mWpType
        L58:
            r1 = r2
        L59:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getWallpaperTypeByComponentName: wallpaperType = "
            r3.<init>(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.samsung.server.wallpaper.Log.d(r0, r3)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.server.wallpaper.SemWallpaperManagerService.getWallpaperTypeByComponentName(android.content.ComponentName,"
                    + " com.android.server.wallpaper.WallpaperData):int");
    }

    public final void handleWallpaperBindingTimeout(boolean z) {
        AnonymousClass4 anonymousClass4 = this.mHandler;
        anonymousClass4.removeMessages(1009);
        if (z) {
            anonymousClass4.sendEmptyMessageDelayed(1009, 2000L);
        }
    }

    public boolean isSnapshotTestMode() {
        if (SHIPPED) {
            return false;
        }
        return sSnapshotTestMode;
    }

    public final boolean isSystemAndLockPaired(int i, int i2) {
        return WhichChecker.isSystemAndLock(
                this.mCallback.onWallpaperDataRequested(i2, WhichChecker.getMode(i) | 1).mWhich);
    }

    public final void postProcess(
            int i, int i2, SnapshotManager.SnapshotData snapshotData, int i3) {
        long clearCallingIdentity;
        int i4;
        int i5;
        WallpaperData peekWallpaperDataLocked;
        int connectedSnapshotForLiveWallpaper;
        SemWallpaperData semWallpaperData;
        if (i3 != 1001) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SnapshotHelper.writeDefaultSettings(this.mContext, i, i2);
                WallpaperManagerService wallpaperManagerService =
                        (WallpaperManagerService) this.mSnapshotCallback;
                String lastCallingPackage = wallpaperManagerService.getLastCallingPackage(i2);
                Log.d(
                        "WallpaperManagerService",
                        "requestClearWallpaper: lastCallingPackage = " + lastCallingPackage);
                wallpaperManagerService.clearWallpaper(lastCallingPackage, i2, i);
                return;
            } finally {
            }
        }
        WallpaperData wallpaperData = snapshotData.getWallpaperData(i2);
        if (wallpaperData == null || (semWallpaperData = wallpaperData.mSemWallpaperData) == null) {
            i4 = 0;
        } else {
            i4 = semWallpaperData.mWpType;
            try {
                WallpaperData onWallpaperDataRequested =
                        this.mCallback.onWallpaperDataRequested(i, i2);
                String wallpaperColorPath = LegibilityColor.getWallpaperColorPath(i, i2, false);
                SemWallpaperColors semWallpaperColors = semWallpaperData.mPrimarySemColors;
                if (semWallpaperColors != null) {
                    semWallpaperColors.save(wallpaperColorPath);
                    onWallpaperDataRequested.mSemWallpaperData.mPrimarySemColors =
                            semWallpaperData.mPrimarySemColors;
                }
                SemWallpaperColors[] semWallpaperColorsArr = semWallpaperData.mLandscapeColors;
                if (semWallpaperColorsArr != null
                        && semWallpaperColorsArr.length > 0
                        && semWallpaperColorsArr[0] != null) {
                    semWallpaperColorsArr[0].save(
                            LegibilityColor.getWallpaperColorPath(i, i2, true));
                    onWallpaperDataRequested.mSemWallpaperData.mLandscapeColors =
                            semWallpaperColorsArr;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        WallpaperManagerService wallpaperManagerService2 =
                (WallpaperManagerService) this.mSnapshotCallback;
        synchronized (wallpaperManagerService2.mLock) {
            wallpaperManagerService2.saveSettingsLocked(i, WhichChecker.getMode(i2));
        }
        SnapshotManager.PerWhichSnapshot perWhichSnapshot =
                (SnapshotManager.PerWhichSnapshot)
                        ((HashMap) snapshotData.perWhichSnapshots).get(Integer.valueOf(i2));
        if (perWhichSnapshot != null) {
            String str =
                    WhichChecker.isSubDisplay(i2)
                            ? "lockscreen_wallpaper_sub"
                            : "lockscreen_wallpaper";
            i5 =
                    ((Integer)
                                    perWhichSnapshot.settings.getOrDefault(
                                            str,
                                            Integer.valueOf(
                                                    SnapshotHelper.SettingsData.getDefaultValue(
                                                            str))))
                            .intValue();
        } else {
            i5 = 1;
        }
        if (i5 != 1
                && (connectedSnapshotForLiveWallpaper =
                                snapshotData.getConnectedSnapshotForLiveWallpaper(i2))
                        != -1) {
            SnapshotManager.SnapshotData snapshot =
                    this.mSnapshotManager.getSnapshot(i, connectedSnapshotForLiveWallpaper);
            int correspondingWhich = SnapshotHelper.getCorrespondingWhich(i2);
            if (snapshot != null && snapshot.hasWallpaperData(correspondingWhich)) {
                snapshot.setLockscreenVisibility(correspondingWhich, 0);
                snapshotData.setLockscreenVisibility(i2, 1);
            }
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Context context = this.mContext;
            SnapshotManager.PerWhichSnapshot perWhichSnapshot2 =
                    (SnapshotManager.PerWhichSnapshot)
                            ((HashMap) snapshotData.perWhichSnapshots)
                                    .get(Integer.valueOf(SnapshotHelper.checkWhich(i2)));
            SnapshotHelper.updateSettings(
                    context,
                    i,
                    perWhichSnapshot2 != null ? perWhichSnapshot2.settings : null,
                    this.mCallback);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (WhichChecker.isLock(i2)) {
                if (i4 == 3 || i4 == 1000) {
                    return;
                }
                Bundle m142m =
                        AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                "trigger", "snapshot");
                WallpaperManagerService wallpaperManagerService3 =
                        (WallpaperManagerService) this.mSnapshotCallback;
                wallpaperManagerService3.getClass();
                int mode = WhichChecker.getMode(i2) | 2;
                WallpaperData peekWallpaperDataLocked2 =
                        wallpaperManagerService3.peekWallpaperDataLocked(i, mode);
                if (peekWallpaperDataLocked2 != null) {
                    wallpaperManagerService3.notifyLockWallpaperChanged(
                            peekWallpaperDataLocked2.mSemWallpaperData.mWpType, mode, m142m);
                }
                ((WallpaperManagerService) this.mSnapshotCallback)
                        .requestNotifySemWallpaperColors(i2);
                return;
            }
            if (WhichChecker.isWatchFaceDisplay(i2) || WhichChecker.isVirtualDisplay(i2)) {
                WallpaperManagerService wallpaperManagerService4 =
                        (WallpaperManagerService) this.mSnapshotCallback;
                wallpaperManagerService4.getClass();
                Log.e(
                        "WallpaperManagerService",
                        "requestNotifyCoverWallpaperChanged: userId = " + i + ", which = " + i2);
                if (WhichChecker.isWatchFaceDisplay(i2)
                        && (peekWallpaperDataLocked =
                                        wallpaperManagerService4.peekWallpaperDataLocked(i, i2))
                                != null) {
                    wallpaperManagerService4.notifyCoverWallpaperChanged(
                            peekWallpaperDataLocked.mSemWallpaperData.mWpType, i2);
                    return;
                }
                return;
            }
            if (i4 == 7) {
                ((WallpaperManagerService) this.mSnapshotCallback)
                        .requestNotifyWallpaperChanged(i, i2);
                ((WallpaperManagerService) this.mSnapshotCallback)
                        .requestNotifySemWallpaperColors(i2);
            } else {
                if (i4 != 0 || wallpaperData == null) {
                    return;
                }
                File wallpaperFile = wallpaperData.getWallpaperFile(i4);
                if (wallpaperFile == null || !wallpaperFile.exists()) {
                    Log.d(
                            "SemWallpaperManagerService",
                            "postProcess: Restored wallpaper is image type with no file.");
                    ((WallpaperManagerService) this.mSnapshotCallback)
                            .requestNotifyWallpaperChanged(i, i2);
                    ((WallpaperManagerService) this.mSnapshotCallback)
                            .requestNotifySemWallpaperColors(i2);
                }
            }
        } finally {
        }
    }

    public final void putDefaultLiveWallpaperProperties(WallpaperData wallpaperData) {
        int i = wallpaperData.mSemWallpaperData.mWhich;
        Bundle defaultLiveWallpaperExtras = this.mResourceInfo.getDefaultLiveWallpaperExtras(i);
        if (defaultLiveWallpaperExtras == null) {
            Log.w(
                    "SemWallpaperManagerService",
                    "putDefaultLiveWallpaperProperties: default extra data is not present. which="
                            + i);
            defaultLiveWallpaperExtras = new Bundle();
        }
        defaultLiveWallpaperExtras.putBoolean("isPreloaded", true);
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        semWallpaperData.mExternalParams = defaultLiveWallpaperExtras;
        semWallpaperData.mIsPreloaded = true;
    }

    public final void removeSnapshotByKey(int i) {
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.addHistory(
                    this.mCurrentUserId,
                    3,
                    i,
                    this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, i));
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
    }

    public final void removeSnapshotByWhich(int i) {
        boolean z;
        int i2;
        ArrayList whiches = SnapshotHelper.getWhiches(i);
        Log.d("SemWallpaperManagerService", "removeSnapshotByWhich: whiches = " + whiches);
        HashMap hashMap = new HashMap();
        synchronized (this.mSnapshotDataLock) {
            try {
                Iterator it = whiches.iterator();
                z = false;
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    ArrayList removeSnapshotByWhich =
                            this.mSnapshotManager.removeSnapshotByWhich(
                                    this.mCurrentUserId, num.intValue());
                    hashMap.put(num, 1);
                    if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT
                            && !z
                            && removeSnapshotByWhich.size() > 0) {
                        Iterator it2 = removeSnapshotByWhich.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            } else if (((SnapshotManager.SnapshotData) it2.next())
                                    .isFromPairedService) {
                                z = true;
                                break;
                            }
                        }
                    }
                }
                i2 = -1;
                this.mSnapshotManager.addHistory(this.mCurrentUserId, 3, -1, hashMap);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT && z) {
            Iterator it3 =
                    this.mSnapshotManager.getRepositroy(this.mCurrentUserId).getAll().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                SnapshotManager.SnapshotData snapshotData =
                        (SnapshotManager.SnapshotData) it3.next();
                if ("com.samsung.android.dynamiclock".equals(snapshotData.source)
                        && snapshotData.isFromPairedService) {
                    StringBuilder sb = new StringBuilder("getPairedDlsSnapshotKey: key = ");
                    i2 = snapshotData.key;
                    sb.append(i2);
                    Log.d("SnapshotManager", sb.toString());
                    break;
                }
            }
            if (i2 > 0) {
                Log.d(
                        "SemWallpaperManagerService",
                        "removeSnapshotByWhich: One of paired snapshot was removed. Restore last"
                            + " paired snapshot.");
                restoreSnapshot(i2, "android");
            }
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
    }

    public final Bundle requestWallpaperPrepare(
            ComponentName componentName, int i, int i2, Bundle bundle) {
        Bundle invokeProviderCall;
        String sb;
        ProviderRequester providerRequester = this.mLiveWallpaperHelper.mProviderRequester;
        providerRequester.getClass();
        if (ProviderRequester.isValidComponentName(componentName)) {
            StringBuilder sb2 = new StringBuilder("requestWallpaperPrepare : ");
            sb2.append(componentName);
            sb2.append(", which=");
            sb2.append(i);
            sb2.append(", hasExtras=");
            sb2.append(bundle != null);
            Log.d("ProviderRequester", sb2.toString());
            Bundle bundle2 = new Bundle();
            bundle2.putInt("which", i);
            bundle2.putInt("user_id", i2);
            bundle2.putString("wallpaper_service_class_name", componentName.getClassName());
            bundle2.putBundle("external_params", bundle);
            invokeProviderCall =
                    providerRequester.invokeProviderCall(
                            i2, componentName.getPackageName(), "prepare_wallpaper", bundle2);
        } else {
            Log.e(
                    "ProviderRequester",
                    "requestWallpaperPrepare : service component is invalid - " + componentName);
            invokeProviderCall = null;
        }
        if (invokeProviderCall == null) {
            return null;
        }
        Bundle bundle3 = new Bundle();
        for (String str : invokeProviderCall.keySet()) {
            Object obj = invokeProviderCall.get(str);
            if (str == null) {
                sb = null;
            } else {
                String[] split = str.split("[\\W_]+");
                if (split.length == 1) {
                    sb = split[0];
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    for (int i3 = 0; i3 < split.length; i3++) {
                        String str2 = split[i3];
                        if (!str2.isEmpty()) {
                            if (i3 == 0) {
                                sb3.append(str2.toLowerCase());
                            } else {
                                sb3.append(Character.toUpperCase(str2.charAt(0)));
                                sb3.append(str2.substring(1).toLowerCase());
                            }
                        }
                    }
                    sb = sb3.toString();
                }
            }
            bundle3.putObject(sb, obj);
        }
        return bundle3;
    }

    public final void restoreSnapshot(int i, String str) {
        int i2;
        SnapshotManager.SnapshotData snapshotData;
        SnapshotManager.SnapshotData snapshot;
        Log.d(
                "SemWallpaperManagerService",
                "restoreSnapshot: key = " + i + ", callingPackage = " + str);
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT) {
            i2 = this.mSnapshotManager.getPairedDlsSnapshotKey(this.mCurrentUserId, i);
            Log.d("SemWallpaperManagerService", "restoreSnapshot: pairedDlsSnapshotKey = " + i2);
        } else {
            i2 = -1;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i3 = this.mCurrentUserId;
        SnapshotManager snapshotManager = this.mSnapshotManager;
        if (snapshotManager.getRepositroy(i3).size() <= 0) {
            Log.e("SemWallpaperManagerService", "canRestore: No snapshot.");
            snapshotManager.addHistory(this.mCurrentUserId, i);
            return;
        }
        SnapshotManager.SnapshotData snapshot2 =
                snapshotManager.getSnapshot(this.mCurrentUserId, i);
        if (snapshot2 == null || !snapshot2.hasWallpaperData()) {
            Log.e("SemWallpaperManagerService", "canRestore: No snapshot for key [" + i + "].");
            snapshotManager.addHistory(this.mCurrentUserId, i);
            return;
        }
        synchronized (this.mSnapshotDataLock) {
            SnapshotManager.SnapshotRepository repositroy =
                    this.mSnapshotManager.getRepositroy(this.mCurrentUserId);
            LinkedList linkedList = repositroy.mSnapshots;
            snapshotData =
                    (linkedList == null || linkedList.size() <= 0)
                            ? null
                            : (SnapshotManager.SnapshotData) repositroy.mSnapshots.getFirst();
        }
        if (snapshotData == null) {
            Log.d("SemWallpaperManagerService", "restoreSnapshot: No snapshot.");
            this.mSnapshotManager.addHistory(this.mCurrentUserId, i);
        } else if (i == snapshotData.key) {
            SnapshotManager snapshotManager2 = this.mSnapshotManager;
            int i4 = this.mCurrentUserId;
            HashMap hashMap = new HashMap();
            synchronized (this.mSnapshotDataLock) {
                snapshot = this.mSnapshotManager.getSnapshot(i4, i);
            }
            if (snapshot != null) {
                Log.d(
                        "SemWallpaperManagerService",
                        "doRestore: which set = " + snapshot.getWhiches());
                Iterator it = snapshot.getWhiches().iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    int intValue = num.intValue();
                    deleteThumbnailFile(intValue, i4);
                    int restoreSnapshotInternal = restoreSnapshotInternal(i4, intValue, snapshot);
                    postProcess(i4, intValue, snapshot, restoreSnapshotInternal);
                    hashMap.put(num, Integer.valueOf(restoreSnapshotInternal));
                }
            }
            snapshotManager2.addHistory(i4, 2, i, hashMap);
        } else {
            Log.d(
                    "SemWallpaperManagerService",
                    "restoreSnapshot: SnapshotData for key " + i + " is not the latest one.");
            SnapshotManager snapshotManager3 = this.mSnapshotManager;
            int i5 = this.mCurrentUserId;
            snapshotManager3.addHistory(i5, 2, i, doRestoreOrMigrate(i5, i));
        }
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, i);
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
        Log.d(
                "SemWallpaperManagerService",
                "restoreSnapshot: Elapsed Time = "
                        + (SystemClock.elapsedRealtime() - elapsedRealtime));
        if (!Rune.SUPPORT_PAIRED_DLS_SNAPSHOT || i2 <= 0) {
            return;
        }
        restoreSnapshot(i2, str);
    }

    public final int restoreSnapshotInternal(
            int i, int i2, SnapshotManager.SnapshotData snapshotData) {
        File file;
        WallpaperManagerService.SemCallback semCallback = this.mCallback;
        WallpaperData onWallpaperDataRequested = semCallback.onWallpaperDataRequested(i, i2);
        if (snapshotData.getWallpaperData(i2) == null) {
            Log.e(
                    "SemWallpaperManagerService",
                    "restoreSnapshotInternal: snapshot or WallpaperData in snapshot is null.");
            return 1003;
        }
        try {
            WallpaperData m1039clone = snapshotData.getWallpaperData(i2).m1039clone();
            if (m1039clone == null) {
                Log.e(
                        "SemWallpaperManagerService",
                        "restoreSnapshotInternal: wallpaperToRestore is null.");
                return 1003;
            }
            SemWallpaperData semWallpaperData = onWallpaperDataRequested.mSemWallpaperData;
            SemWallpaperData semWallpaperData2 = m1039clone.mSemWallpaperData;
            int i3 = m1039clone.wallpaperId;
            if (onWallpaperDataRequested.wallpaperId == i3
                    && WhichChecker.containsSystem(semWallpaperData.mWhich)) {
                Log.e(
                        "SemWallpaperManagerService",
                        "restoreSnapshotInternal: Same image wallpaper does not need to be"
                            + " restored. (id = "
                                + m1039clone.wallpaperId
                                + ")");
                return 1001;
            }
            int i4 = semWallpaperData2.mWpType;
            File wallpaperFile = m1039clone.getWallpaperFile(i4);
            SnapshotHelper.deleteFile(
                    onWallpaperDataRequested.getWallpaperFile(
                            onWallpaperDataRequested.mSemWallpaperData.mWpType));
            SnapshotHelper.deleteFile(onWallpaperDataRequested.getCropFile());
            SemWallpaperData semWallpaperData3 = m1039clone.mSemWallpaperData;
            int i5 = semWallpaperData3.mWpType;
            int i6 = semWallpaperData3.mWhich;
            if (i5 == 1) {
                file =
                        WhichChecker.isSubDisplay(i2)
                                ? new File(
                                        WallpaperUtils.getWallpaperLockDir(i),
                                        "wallpaper_motion_background_sub")
                                : new File(
                                        WallpaperUtils.getWallpaperLockDir(i),
                                        "wallpaper_motion_background");
            } else if (i5 == 4) {
                file =
                        WhichChecker.isSubDisplay(i2)
                                ? new File(
                                        WallpaperUtils.getWallpaperLockDir(i),
                                        "wallpaper_animated_background_sub")
                                : new File(
                                        WallpaperUtils.getWallpaperLockDir(i),
                                        "wallpaper_animated_background");
            } else if (i5 != 8) {
                file =
                        WhichChecker.isSystem(i2)
                                ? new File(
                                        Environment.getUserSystemDirectory(i),
                                        WallpaperUtils.getFileName(i2))
                                : new File(
                                        WallpaperUtils.getWallpaperLockDir(i),
                                        WallpaperUtils.getFileName(i2));
            } else if (WhichChecker.isSubDisplay(i2)) {
                file =
                        new File(
                                WhichChecker.isLock(i2)
                                        ? WallpaperUtils.getWallpaperLockDir(i)
                                        : Environment.getUserSystemDirectory(i),
                                WhichChecker.isLock(i2)
                                        ? "wallpaper_first_sub"
                                        : "wallpaper_first_sub_home");
            } else {
                file =
                        new File(
                                WhichChecker.isLock(i2)
                                        ? WallpaperUtils.getWallpaperLockDir(i)
                                        : Environment.getUserSystemDirectory(i),
                                WhichChecker.isLock(i2)
                                        ? "wallpaper_first"
                                        : "wallpaper_first_home");
            }
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i2,
                            i6,
                            "getTargetFile: which = ",
                            ", WallpaperDataWhich = ",
                            ", wallpaperType = ");
            m.append(i5);
            m.append(", targetFile = ");
            m.append(file);
            Log.d("SemWallpaperManagerService", m.toString());
            m1039clone.setWallpaperFile(file);
            m1039clone.mCropFiles.put(
                    m1039clone.mWhich,
                    new File(
                            WhichChecker.isLock(i2)
                                    ? WallpaperUtils.getWallpaperLockDir(i)
                                    : Environment.getUserSystemDirectory(i),
                            WallpaperUtils.getCropFileName(i2)));
            String str = semWallpaperData2.mLastCallingPackage;
            StringBuilder m2 =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i2,
                            i4,
                            "restoreSnapshotInternal: which = ",
                            ", type = ",
                            ", backupFile = ");
            m2.append(wallpaperFile);
            m2.append(", targetFile = ");
            m2.append(file);
            m2.append(", restoreWallpaperId = ");
            m2.append(i3);
            m2.append(", lastCallingPackage = ");
            m2.append(str);
            Log.d("SemWallpaperManagerService", m2.toString());
            semWallpaperData2.mWallpaperHistory = semWallpaperData.mWallpaperHistory.m1238clone();
            if (TextUtils.isEmpty(str)) {
                m1039clone.setCallingPackage("[RESTORE]");
            } else if (str.contains("[RESTORE]")) {
                m1039clone.setCallingPackage(str);
            } else {
                m1039clone.setCallingPackage("[RESTORE]".concat(str));
            }
            Log.d(
                    "SemWallpaperManagerService",
                    "restoreSnapshotInternal: wallpaperToRestore [" + m1039clone + "]");
            boolean z = false;
            SnapshotCallback snapshotCallback = this.mSnapshotCallback;
            if (i4 == 0) {
                if (wallpaperFile != null && wallpaperFile.exists() && i3 >= 0) {
                    m1039clone.imageWallpaperPending = true;
                    m1039clone.mWhich = i2;
                    ((WallpaperManagerService) snapshotCallback)
                            .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                    return SnapshotHelper.saveFile(wallpaperFile, file) ? 1001 : -2;
                }
                if (WhichChecker.isLock(i2)) {
                    if (snapshotData.hasWallpaperData(SnapshotHelper.getCorrespondingWhich(i2))
                            && WhichChecker.isSystemAndLock(
                                    snapshotData.getWallpaperData(
                                                    SnapshotHelper.getCorrespondingWhich(i2))
                                            .mWhich)) {
                        z = true;
                    }
                    Log.d(
                            "SemWallpaperManagerService",
                            "restoreSnapshotInternal: isPartOfSystemAndLock = "
                                    + z
                                    + ", restoreWallpaperId = "
                                    + i3);
                    if (z || i3 < 0) {
                        WallpaperData onWallpaperDataRequested2 =
                                semCallback.onWallpaperDataRequested(
                                        i, WhichChecker.getMode(i2) | 1);
                        onWallpaperDataRequested2.mWhich |= 2;
                        onWallpaperDataRequested2.mSystemWasBoth = true;
                        semCallback.onWallpaperFlagUpdated(i, i2);
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        return 1001;
                    }
                }
                ((WallpaperManagerService) snapshotCallback)
                        .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                semCallback.onBindWallpaperRequested(i, i2);
                Log.d("SemWallpaperManagerService", "restoreSnapshotInternal: Preloaded ?");
                return 1001;
            }
            if (i4 != 1) {
                if (i4 == 3) {
                    ((WallpaperManagerService) snapshotCallback)
                            .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                    m1039clone.imageWallpaperPending = true;
                    m1039clone.mWhich = i2;
                    if (Rune.SUPPORT_DLS_SNAPSHOT) {
                        m1039clone.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                    }
                    semCallback.onBindWallpaperRequested(i, i2);
                    return 1001;
                }
                if (i4 != 4) {
                    if (i4 == 5) {
                        m1039clone.imageWallpaperPending = true;
                        m1039clone.mWhich = i2;
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        semCallback.onBindWallpaperRequested(i, i2);
                        return 1001;
                    }
                    if (i4 == 7) {
                        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
                            int i7 = snapshotData.key;
                            if (SnapshotHelper.getBackupWallpaperAssetsDir(i, i7, i2).exists()) {
                                Log.d(
                                        "SemWallpaperManagerService",
                                        "restoreSnapshotInternal: Asset files exist.");
                                SnapshotHelper.renameDirectory(
                                        SnapshotHelper.getBackupWallpaperAssetsDir(i, i7, i2),
                                        AssetFileManager.getBaseAssetDir(i2, i, false));
                            }
                            PreloadedLiveWallpaperHelper.recoverComponentNameIfMissed(m1039clone);
                        }
                        if (m1039clone.mWhich == 0) {
                            m1039clone.mWhich = i2;
                        }
                        if (WhichChecker.isSystem(i2)
                                && WhichChecker.isSystemAndLock(m1039clone.mWhich)) {
                            int connectedSnapshotForLiveWallpaper =
                                    snapshotData.getConnectedSnapshotForLiveWallpaper(i2);
                            if (connectedSnapshotForLiveWallpaper == -1) {
                                m1039clone.mSystemWasBoth = true;
                            } else {
                                SnapshotManager.SnapshotData snapshot =
                                        this.mSnapshotManager.getSnapshot(
                                                i, connectedSnapshotForLiveWallpaper);
                                int correspondingWhich = SnapshotHelper.getCorrespondingWhich(i2);
                                if (snapshot == null
                                        || !snapshot.hasWallpaperData(correspondingWhich)) {
                                    m1039clone.mSystemWasBoth = true;
                                } else {
                                    m1039clone.mWhich = WhichChecker.getMode(m1039clone.mWhich) | 1;
                                    snapshot.setConnectedSnapshotForLiveWallpaper(
                                            correspondingWhich, -1);
                                }
                            }
                        }
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        semCallback.onBindWallpaperRequested(i, m1039clone.mWhich);
                        return 1001;
                    }
                    if (i4 == 8) {
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        m1039clone.imageWallpaperPending = true;
                        m1039clone.mWhich = i2;
                        semCallback.onBindWallpaperRequested(i, i2);
                        if (wallpaperFile != null && wallpaperFile.exists()) {
                            return SnapshotHelper.saveFile(wallpaperFile, file) ? 1001 : -2;
                        }
                        Log.d(
                                "SemWallpaperManagerService",
                                "restoreSnapshotInternal: backupFile not exist.");
                        return 1001;
                    }
                    if (i4 == 1000) {
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        semCallback.onBindWallpaperRequested(i, i2);
                        return 1001;
                    }
                    if (wallpaperFile != null && wallpaperFile.exists()) {
                        m1039clone.imageWallpaperPending = true;
                        m1039clone.mWhich = i2;
                        ((WallpaperManagerService) snapshotCallback)
                                .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                        return SnapshotHelper.saveFile(wallpaperFile, file) ? 1001 : -2;
                    }
                    if (!WhichChecker.isLock(i2)) {
                        Log.e(
                                "SemWallpaperManagerService",
                                "restoreSnapshotInternal: Unhandled snapshot!");
                        return 1002;
                    }
                    Log.d("SemWallpaperManagerService", "restoreSnapshotInternal: Live wallpaper.");
                    WallpaperData onWallpaperDataRequested3 =
                            semCallback.onWallpaperDataRequested(i, WhichChecker.getMode(i2) | 1);
                    int i8 = onWallpaperDataRequested3.mWhich | 2;
                    onWallpaperDataRequested3.mWhich = i8;
                    onWallpaperDataRequested3.mSystemWasBoth = true;
                    if (i3 < 0) {
                        semCallback.onWallpaperFlagUpdated(i, i2);
                    } else {
                        semCallback.onBindWallpaperRequested(i, i8);
                    }
                    ((WallpaperManagerService) snapshotCallback)
                            .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
                    return 1001;
                }
            }
            m1039clone.imageWallpaperPending = true;
            m1039clone.mWhich = i2;
            ((WallpaperManagerService) snapshotCallback)
                    .requestSaveRestoredWallpaperLocked(i, i2, m1039clone);
            semCallback.onBindWallpaperRequested(i, i2);
            return 1001;
        } catch (Exception e) {
            Log.e("SemWallpaperManagerService", "restoreSnapshotInternal: " + e.getMessage());
            return 1003;
        }
    }

    public final void saveDefaultLiveWallpaperData(WallpaperData wallpaperData) {
        ComponentName defaultPreloadedLiveWallpaperComponentName =
                getDefaultPreloadedLiveWallpaperComponentName(
                        wallpaperData.mSemWallpaperData.mWhich);
        Slog.d(
                "SemWallpaperManagerService",
                "saveDefaultLiveWallpaperData: componentName = "
                        + defaultPreloadedLiveWallpaperComponentName);
        if (defaultPreloadedLiveWallpaperComponentName != null) {
            wallpaperData.wallpaperComponent = defaultPreloadedLiveWallpaperComponentName;
            wallpaperData.allowBackup = false;
            putDefaultLiveWallpaperProperties(wallpaperData);
        }
    }

    public final void saveDefaultMutipackWallpaperData(WallpaperData wallpaperData) {
        String str;
        boolean contains;
        String str2;
        String defaultMultipackStyle =
                this.mResourceInfo.getDefaultMultipackStyle(wallpaperData.mSemWallpaperData.mWhich);
        if (TextUtils.isEmpty(defaultMultipackStyle)
                || !defaultMultipackStyle.contains("MULTIPLE")) {
            Log.d(
                    "SemWallpaperManagerService",
                    "getDefaultMultiPackUri: defaultWallpaperStyle is empty or not MULTIPLE!");
            str = null;
        } else {
            int lastIndexOf = defaultMultipackStyle.lastIndexOf("=") + 1;
            int lastIndexOf2 =
                    defaultMultipackStyle.contains(":")
                            ? defaultMultipackStyle.lastIndexOf(":")
                            : -1;
            if (lastIndexOf2 == -1) {
                str2 = defaultMultipackStyle.substring(lastIndexOf);
                contains = false;
            } else {
                String substring = defaultMultipackStyle.substring(lastIndexOf, lastIndexOf2);
                contains = defaultMultipackStyle.contains("tilt");
                str2 = substring;
            }
            StringBuilder sb = new StringBuilder("multipack://");
            sb.append(str2);
            if (contains) {
                sb.append("?tilt=true");
            }
            str = sb.toString();
        }
        wallpaperData.allowBackup = false;
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        semWallpaperData.mUri = str;
        semWallpaperData.mExternalParams = null;
        semWallpaperData.mIsPreloaded = true;
        this.mCallback.getClass();
        wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
    }

    public final void saveDefaultVideoWallpaperData(WallpaperData wallpaperData) {
        String oMCVideoWallpaperFilePath = WallpaperManager.getOMCVideoWallpaperFilePath(null);
        String defaultVideoWallpaperFileName =
                this.mResourceInfo.getDefaultVideoWallpaperFileName(
                        wallpaperData.mSemWallpaperData.mWhich);
        wallpaperData.allowBackup = false;
        wallpaperData.cropHint.set(new Rect(0, 0, 0, 0));
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        semWallpaperData.mVideoDefaultHasBeenUsed = true;
        semWallpaperData.mVideoFilePath = oMCVideoWallpaperFilePath;
        semWallpaperData.mVideoPkgName = "com.samsung.android.wallpaper.res";
        semWallpaperData.mVideoFileName = defaultVideoWallpaperFileName;
        semWallpaperData.mIsPreloaded = true;
        semWallpaperData.mUri = null;
        semWallpaperData.mExternalParams = null;
        this.mCallback.getClass();
        wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
    }

    public final void saveSettingsLockedForSnapshot(int i) {
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.saveSettingsLockedForSnapshot(i);
        }
    }

    public final void semClearWallpaperLocked(int i, int i2, String str) {
        WallpaperData wallpaperData;
        SemWallpaperData semWallpaperData;
        ComponentName componentName;
        removeSnapshotByWhich(i);
        String str2 = str + "(clear)";
        int mode = WhichChecker.getMode(i);
        boolean isSystem = WhichChecker.isSystem(i);
        boolean isLock = WhichChecker.isLock(i);
        if (WallpaperManager.isDefaultOperatorWallpaper(
                this.mContext, i, this.mCMFWallpaper.getDeviceColor())) {
            Log.d(
                    "SemWallpaperManagerService",
                    "semClearWallpaperLocked: Default operator wallpaper");
            WallpaperData onWallpaperDataRequested = this.mCallback.onWallpaperDataRequested(i2, i);
            onWallpaperDataRequested.mSemWallpaperData.mWpType = -1;
            onWallpaperDataRequested.cleanUp();
            onWallpaperDataRequested.mSemWallpaperData.mWhich = i;
            onWallpaperDataRequested.setCallingPackage(str2);
            onWallpaperDataRequested.mSemWallpaperData.mIsPreloaded = true;
            File defaultWallpaperFile = WallpaperManager.getDefaultWallpaperFile(this.mContext, i);
            if (defaultWallpaperFile != null && defaultWallpaperFile.exists()) {
                String str3 = "file://" + defaultWallpaperFile.getPath();
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "setFactoryDefaultWallpaper: uriString = ",
                        str3,
                        "SemWallpaperManagerService");
                onWallpaperDataRequested.mSemWallpaperData.mUri = str3;
            }
            this.mCallback.onSetWallpaperComponent(onWallpaperDataRequested);
            return;
        }
        int i3 = mode | 1;
        WallpaperData onWallpaperDataRequested2 = this.mCallback.onWallpaperDataRequested(i2, i3);
        WallpaperData onWallpaperDataRequested3 =
                this.mCallback.onWallpaperDataRequested(i2, mode | 2);
        WallpaperData wallpaperData2 = new WallpaperData(i2, i);
        wallpaperData2.mWhich = i;
        wallpaperData2.mSemWallpaperData.mWhich = i;
        int defaultWallpaperType = getDefaultWallpaperType(i);
        wallpaperData2.mSemWallpaperData.mWpType = defaultWallpaperType;
        wallpaperData2.setCallingPackage(str2);
        Log.d(
                "SemWallpaperManagerService",
                "semClearWallpaperLocked: factoryDefaultType = " + defaultWallpaperType);
        if (defaultWallpaperType == 3) {
            saveDefaultMutipackWallpaperData(wallpaperData2);
            this.mCallback.getClass();
            return;
        }
        if (defaultWallpaperType == 7) {
            if (isSystem) {
                saveDefaultLiveWallpaperData(wallpaperData2);
                this.mCallback.onSetWallpaperComponent(wallpaperData2);
                return;
            }
            return;
        }
        if (defaultWallpaperType != 8) {
            if (isLock
                    && (semWallpaperData = onWallpaperDataRequested2.mSemWallpaperData) != null
                    && (componentName = onWallpaperDataRequested2.wallpaperComponent) != null
                    && semWallpaperData.mWpType == 7
                    && componentName.equals(getDefaultPreloadedLiveWallpaperComponentName(i3))) {
                onWallpaperDataRequested3.mSemWallpaperData.mWpType = -1;
                onWallpaperDataRequested3.cleanUp();
                onWallpaperDataRequested3.mSemWallpaperData.mWpType = defaultWallpaperType;
                onWallpaperDataRequested3.setCallingPackage(str2);
                int i4 = onWallpaperDataRequested2.mWhich | 2;
                onWallpaperDataRequested2.mWhich = i4;
                onWallpaperDataRequested2.mSystemWasBoth = true;
                this.mCallback.onBindWallpaperRequested(i2, i4);
                this.mCallback.onWallpaperFlagUpdated(i2, i);
                this.mCallback.onDetachWallpaper(onWallpaperDataRequested3);
                this.mCallback.onLockWallpaperChanged(
                        i2, i, onWallpaperDataRequested3.mSemWallpaperData.mExternalParams);
                this.mLegibilityColor.extractColor(
                        onWallpaperDataRequested3.mSemWallpaperData.mWhich, false);
                this.mLegibilityColor.extractColor(
                        onWallpaperDataRequested3.mSemWallpaperData.mWhich, true);
                return;
            }
            if (isLock) {
                onWallpaperDataRequested2 = onWallpaperDataRequested3;
            }
            onWallpaperDataRequested2.mSemWallpaperData.mWpType = -1;
            onWallpaperDataRequested2.cleanUp();
            onWallpaperDataRequested2.mSemWallpaperData.mWpType = defaultWallpaperType;
            onWallpaperDataRequested2.setCallingPackage(str2);
            onWallpaperDataRequested2.mSemWallpaperData.mIsPreloaded = true;
            onWallpaperDataRequested2.mSemWallpaperData.mUri =
                    this.mDefaultWallpaper.getDefaultWallpaperUri(i);
            this.mCallback.onSetWallpaperComponent(onWallpaperDataRequested2);
            if (isLock) {
                this.mCallback.onLockWallpaperChanged(
                        i2, i, onWallpaperDataRequested2.mSemWallpaperData.mExternalParams);
            }
            this.mLegibilityColor.extractColor(
                    onWallpaperDataRequested2.mSemWallpaperData.mWhich, false);
            this.mLegibilityColor.extractColor(
                    onWallpaperDataRequested2.mSemWallpaperData.mWhich, true);
            return;
        }
        saveDefaultVideoWallpaperData(wallpaperData2);
        WallpaperManagerService.SemCallback semCallback = this.mCallback;
        semCallback.getClass();
        int i5 = wallpaperData2.mWhich;
        int mode2 = WhichChecker.getMode(i5);
        boolean isLock2 = WhichChecker.isLock(i5);
        int i6 = wallpaperData2.userId;
        SemWallpaperData semWallpaperData2 = wallpaperData2.mSemWallpaperData;
        boolean z = semWallpaperData2.mIsPreloaded;
        boolean z2 = wallpaperData2.allowBackup;
        Bundle bundle = semWallpaperData2.mExternalParams;
        String str4 = semWallpaperData2.mVideoFilePath;
        String str5 = semWallpaperData2.mVideoPkgName;
        String str6 = semWallpaperData2.mVideoFileName;
        String str7 = semWallpaperData2.mLastCallingPackage;
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i6, i5, "onSetVideoWallpaper: userId = ", ", which = ", ", isPreloaded = ");
        BatteryService$$ExternalSyntheticOutline0.m(m, z, ", allowBackup = ", z2, ", extras = ");
        m.append(bundle);
        m.append(", videoFilePath = ");
        m.append(str4);
        m.append(", videoPackage = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, str5, ", videoFileName = ", str6, ", callingPackage = ");
        m.append(str7);
        Log.d("WallpaperManagerService", m.toString());
        synchronized (WallpaperManagerService.this.mLock) {
            try {
                WallpaperManagerService.this.setVideoWallpaperLocked(
                        i5, str4, str5, str6, str7, i6, z, z2, bundle);
                if (isLock2) {
                    WallpaperData wallpaperSafeLocked =
                            WallpaperManagerService.this.getWallpaperSafeLocked(i6, mode2 | 1);
                    wallpaperSafeLocked.mWhich &= -3;
                    WallpaperManagerService.this.updateEngineFlags(wallpaperSafeLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (WhichChecker.isWatchFaceDisplay(i5) || WhichChecker.isVirtualDisplay(i5)) {
            wallpaperData = wallpaperData2;
            WallpaperManagerService.this.notifyCoverWallpaperChanged(
                    wallpaperData.mSemWallpaperData.mWpType, i5);
        } else {
            wallpaperData = wallpaperData2;
        }
        if (isLock2) {
            WallpaperManagerService.this.notifyLockWallpaperChanged(
                    wallpaperData.mSemWallpaperData.mWpType, i5, null);
        }
        WallpaperManagerService.this.mSemService.mLegibilityColor.extractColor(
                wallpaperData.mSemWallpaperData.mWhich, false);
    }

    public final void semClearWallpaperLocked(WallpaperData wallpaperData) {
        Slog.d(
                "SemWallpaperManagerService",
                "semClearWallpaperLocked: wallpaper = " + wallpaperData);
        int i = wallpaperData.mWhich;
        int i2 = wallpaperData.userId;
        if (!WhichChecker.isSystemAndLock(i)) {
            semClearWallpaperLocked(i, i2, "android");
            return;
        }
        int mode = WhichChecker.getMode(i);
        semClearWallpaperLocked(mode | 1, i2, "android");
        semClearWallpaperLocked(mode | 2, i2, "android");
    }

    public final void setFactoryDefaultWallpaper(
            int i, int i2, WallpaperData wallpaperData, WallpaperData wallpaperData2) {
        SemWallpaperData semWallpaperData;
        ComponentName componentName;
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i,
                        i2,
                        "setFactoryDefaultWallpaper: userId = ",
                        ", which = ",
                        ", systemWallpaper = ");
        m.append(wallpaperData);
        m.append(", lockWallpaper = ");
        m.append(wallpaperData2);
        Slog.d("SemWallpaperManagerService", m.toString());
        if (WhichChecker.getMode(i2) == 0) {
            Slog.e(
                    "SemWallpaperManagerService",
                    "setFactoryDefaultWallpaper: which should contain mode.");
            if (!SHIPPED) {
                throw new IllegalArgumentException(
                        "setFactoryDefaultWallpaper: which should contain mode.");
            }
        }
        int mode = WhichChecker.getMode(i2);
        boolean isSystem = WhichChecker.isSystem(i2);
        boolean isLock = WhichChecker.isLock(i2);
        boolean isDefaultOperatorWallpaper =
                WallpaperManager.isDefaultOperatorWallpaper(
                        this.mContext, i2, this.mCMFWallpaper.getDeviceColor());
        deleteThumbnailFile(i2, i);
        WallpaperData wallpaperData3 = isLock ? wallpaperData2 : wallpaperData;
        if (wallpaperData3 == null) {
            Slog.d(
                    "SemWallpaperManagerService",
                    "setFactoryDefaultWallpaper: No WallpaperData to initialize.");
            return;
        }
        wallpaperData3.mSemWallpaperData.mWpType = -1;
        wallpaperData3.cleanUp();
        wallpaperData3.setCallingPackage("android");
        wallpaperData3.mSemWallpaperData.mWhich = i2;
        if (wallpaperData != null) {
            wallpaperData3.mSystemWasBoth = WhichChecker.isSystemAndLock(wallpaperData.mWhich);
        }
        wallpaperData3.mSemWallpaperData.mIsPreloaded = true;
        if (isDefaultOperatorWallpaper) {
            Slog.d(
                    "SemWallpaperManagerService",
                    "setFactoryDefaultWallpaper: Default operator wallpaper.");
            File defaultWallpaperFile = WallpaperManager.getDefaultWallpaperFile(this.mContext, i2);
            if (defaultWallpaperFile != null && defaultWallpaperFile.exists()) {
                String str = "file://" + defaultWallpaperFile.getPath();
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "setFactoryDefaultWallpaper: uriString = ",
                        str,
                        "SemWallpaperManagerService");
                wallpaperData3.mSemWallpaperData.mUri = str;
            }
            wallpaperData3.setCallingPackage("android");
            return;
        }
        int defaultWallpaperType = getDefaultWallpaperType(i2);
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                defaultWallpaperType,
                "setFactoryDefaultWallpaper: factoryDefaultType = ",
                "SemWallpaperManagerService");
        wallpaperData3.mSemWallpaperData.mWpType = defaultWallpaperType;
        if (defaultWallpaperType == 3) {
            saveDefaultMutipackWallpaperData(wallpaperData3);
            return;
        }
        if (defaultWallpaperType == 7) {
            if (!this.mResourceInfo.isDefaultWallpaperPaired(i2, 7)) {
                saveDefaultLiveWallpaperData(wallpaperData3);
                return;
            } else if (isSystem) {
                saveDefaultLiveWallpaperData(wallpaperData3);
                return;
            } else {
                Slog.w(
                        "SemWallpaperManagerService",
                        "setFactoryDefaultWallpaper: unexpected default wallpaper state");
                return;
            }
        }
        if (defaultWallpaperType == 8) {
            saveDefaultVideoWallpaperData(wallpaperData3);
            return;
        }
        if (!isLock
                || wallpaperData == null
                || (semWallpaperData = wallpaperData.mSemWallpaperData) == null
                || (componentName = wallpaperData.wallpaperComponent) == null
                || semWallpaperData.mWpType != 7
                || !componentName.equals(getDefaultPreloadedLiveWallpaperComponentName(mode | 1))
                || !this.mResourceInfo.isDefaultWallpaperPaired(i2, 7)) {
            wallpaperData3.mSemWallpaperData.mUri =
                    this.mDefaultWallpaper.getDefaultWallpaperUri(i2);
            return;
        }
        wallpaperData.mWhich |= 2;
        wallpaperData.mSystemWasBoth = true;
        this.mCallback.onDetachWallpaper(wallpaperData2);
    }

    public void setSnapshotTestMode(boolean z) {
        if (SHIPPED) {
            return;
        }
        sSnapshotTestMode = z;
    }
}
