package com.samsung.server.wallpaper;

import android.app.ActivityManager;
import android.app.HomeVisibilityListener;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.SemWallpaperResourcesInfo;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import com.samsung.server.wallpaper.snapshot.SnapshotCallback;
import com.samsung.server.wallpaper.snapshot.SnapshotHelper;
import com.samsung.server.wallpaper.snapshot.SnapshotManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class SemWallpaperManagerService {
    public static final boolean SHIPPED = !Debug.semIsProductDev();
    public static final ArrayList sLogList = new ArrayList();
    public static boolean sSnapshotTestMode = false;
    public CMFWallpaper mCMFWallpaper;
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public DefaultWallpaper mDefaultWallpaper;
    public DesktopMode mDesktopMode;
    public final DisplayManager mDisplayManager;
    public Knox mKnox;
    public LegibilityColor mLegibilityColor;
    public LockWallpaper mLockWallpaper;
    public OMCWallpaper mOMCWallpaper;
    public final SnapshotCallback mSnapshotCallback;
    public final SnapshotManager mSnapshotManager;
    public SubDisplayMode mSubDisplayMode;
    public VirtualDisplayMode mVirtualDisplayMode;
    public int mCurrentUserId = -10000;
    public int mOldUserId = -10000;
    public int mDensityDpi = -1;
    public int mOrientation = -1;
    public final ExecutorService mExecutor = Executors.newFixedThreadPool(2);
    public final Object mSnapshotDataLock = new Object();
    public HomeVisibilityListener mHomeVisibilityListener = null;
    public int mAodVisibilityState = 0;
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.SemWallpaperManagerService.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1009) {
                return;
            }
            SemWallpaperManagerService.this.mCallback.handleWallpaperBindingTimeout();
        }
    };

    public SemWallpaperManagerService(Context context, WallpaperManagerService.SemCallback semCallback, SnapshotCallback snapshotCallback, SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        this.mDefaultWallpaper = null;
        this.mCMFWallpaper = null;
        this.mOMCWallpaper = null;
        this.mLockWallpaper = null;
        this.mDesktopMode = null;
        this.mSubDisplayMode = null;
        this.mVirtualDisplayMode = null;
        this.mLegibilityColor = null;
        this.mKnox = null;
        Log.m129d("SemWallpaperManagerService", "SemWallpaperManagerService");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mSnapshotCallback = snapshotCallback;
        this.mSnapshotManager = new SnapshotManager(context, snapshotCallback);
        this.mDefaultWallpaper = new DefaultWallpaper(context, semCallback, this, semWallpaperResourcesInfo);
        this.mCMFWallpaper = new CMFWallpaper(context, this, semWallpaperResourcesInfo);
        this.mOMCWallpaper = new OMCWallpaper(context, semCallback, this);
        this.mLockWallpaper = new LockWallpaper();
        this.mDesktopMode = new DesktopMode(context, semCallback, this);
        this.mSubDisplayMode = new SubDisplayMode(semCallback);
        this.mLegibilityColor = new LegibilityColor(context, semCallback, this);
        this.mKnox = new Knox(context);
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        this.mVirtualDisplayMode = new VirtualDisplayMode(displayManager);
        Configuration configuration = context.getResources().getConfiguration();
        setDensityDpi(configuration.densityDpi);
        setOrientation(configuration.orientation);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        registerUserActivityReceiver();
        context.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.server.wallpaper.SemWallpaperManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.CONFIGURATION_CHANGED".equals(action)) {
                    Configuration configuration2 = SemWallpaperManagerService.this.mContext.getResources().getConfiguration();
                    int i = configuration2.densityDpi;
                    int i2 = configuration2.orientation;
                    Log.m129d("SemWallpaperManagerService", "onReceive: " + action + ", densityDpi=" + SemWallpaperManagerService.this.getDensityDpi() + ", orientation [old=" + SemWallpaperManagerService.this.getOrientation() + ", new=" + i2 + "]");
                    if (SemWallpaperManagerService.this.getDensityDpi() != i) {
                        SemWallpaperManagerService.this.setDensityDpi(i);
                        SemWallpaperManagerService.this.mCallback.updateDisplayData();
                        if (!Rune.SUPPORT_HOME_CONTROLLER) {
                            SemWallpaperManagerService.this.mLegibilityColor.extractColor(1);
                            SemWallpaperManagerService.this.mLegibilityColor.extractColor(2);
                        }
                    }
                    if (SemWallpaperManagerService.this.getOrientation() != i2) {
                        SemWallpaperManagerService.this.setOrientation(i2);
                        if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && SemWallpaperManagerService.this.mSubDisplayMode.getLidState() == 0) {
                            SemWallpaperManagerService.this.mCallback.notifySemWallpaperColors(6);
                            SemWallpaperManagerService.this.mCallback.notifySemWallpaperColors(5);
                        } else {
                            SemWallpaperManagerService.this.mCallback.notifySemWallpaperColors();
                        }
                    }
                }
            }
        }, intentFilter);
    }

    public void setAodVisibilityState(int i) {
        this.mAodVisibilityState = i;
    }

    public int getAodVisibilityState() {
        return this.mAodVisibilityState;
    }

    /* renamed from: com.samsung.server.wallpaper.SemWallpaperManagerService$2 */
    public class HomeVisibilityListenerC32052 extends HomeVisibilityListener {
        public HomeVisibilityListenerC32052() {
        }

        public void onHomeVisibilityChanged(final boolean z) {
            SemWallpaperManagerService.this.mHandler.post(new Runnable() { // from class: com.samsung.server.wallpaper.SemWallpaperManagerService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemWallpaperManagerService.HomeVisibilityListenerC32052.this.lambda$onHomeVisibilityChanged$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHomeVisibilityChanged$0(boolean z) {
            Log.m132i("SemWallpaperManagerService", "isHomeActivityVisible : " + z);
            SemWallpaperManagerService.this.mCallback.dispatchHomeVisibilityChanged(z);
        }
    }

    public final void registerUserActivityReceiver() {
        if (this.mHomeVisibilityListener == null) {
            this.mHomeVisibilityListener = new HomeVisibilityListenerC32052();
            ((ActivityManager) this.mContext.getSystemService("activity")).addHomeVisibilityListener(this.mExecutor, this.mHomeVisibilityListener);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("SemWallpaperManagerService start");
        printWriter.println("  Orientation:" + getOrientation());
        printWriter.println(" Legibility Version:" + SemWallpaperColors.getLegibilityVersion());
        printWriter.println("  allowScreenRotate:" + this.mLegibilityColor.getAllowScreenRotateSystem() + ", " + this.mLegibilityColor.getAllowScreenRotateLock());
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            printWriter.println(" Lid state:" + this.mSubDisplayMode.getLidState());
        }
        printWriter.println(" ------------ Snapshot History ------------");
        this.mSnapshotManager.dump(fileDescriptor, printWriter, strArr);
        String[] logArray = getLogArray();
        printWriter.println(" --------------LogArray--------------");
        if (logArray != null) {
            int length = logArray.length;
            for (int i = 0; i < length; i++) {
                printWriter.println("  #" + i + " " + logArray[i]);
            }
        } else {
            printWriter.println("  logArray is null");
        }
        Log.dump("SemWallpaperManagerService", fileDescriptor, printWriter, strArr);
        printWriter.println("SemWallpaperManagerService end");
    }

    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    public int getCurrentUserId() {
        return this.mCurrentUserId;
    }

    public DisplayInfo getDisplayInfo() {
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);
        DisplayInfo displayInfo = new DisplayInfo();
        defaultDisplay.getDisplayInfo(displayInfo);
        return displayInfo;
    }

    public void setOldUserId(int i) {
        this.mOldUserId = i;
    }

    public int getOldUserId() {
        return this.mOldUserId;
    }

    public void setDensityDpi(int i) {
        this.mDensityDpi = i;
    }

    public int getDensityDpi() {
        return this.mDensityDpi;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public static String getFileName(int i, int i2, int i3) {
        return WhichChecker.isDex(i) ? i2 == 2 ? "wallpaper_desktop_info.xml" : i2 == 1 ? i3 == 1 ? "wallpaper_desktop_lock" : "wallpaper_desktop_lock_orig" : i3 == 1 ? "wallpaper_desktop" : "wallpaper_desktop_orig" : WhichChecker.isSubDisplay(i) ? i2 == 2 ? "wallpaper_subdisplay_info.xml" : i2 == 1 ? i3 == 1 ? "wallpaper_sub_display_lock" : "wallpaper_sub_display_lock_orig" : i3 == 1 ? "wallpaper_sub_display" : "wallpaper_sub_display_orig" : WhichChecker.isVirtualDisplay(i) ? i2 == 2 ? "wallpaper_virtualdisplay_info.xml" : i3 == 1 ? "wallpaper_virtual_display" : "wallpaper_virtual_display_orig" : i2 == 2 ? "wallpaper_info.xml" : i2 == 1 ? i3 == 1 ? "wallpaper_lock" : "wallpaper_lock_orig" : i3 == 1 ? "wallpaper" : "wallpaper_orig";
    }

    public class SemWallpaperObserver {
        public WallpaperManagerService.SemCallback mCallback;
        public final File mDesktopWallpaperFile;
        public final File mDesktopWallpaperInfoFile;
        public final File mDesktopWallpaperLockFile;
        public FileObserver mLockWallpaperFileObserver;
        public final File mSubDisplayWallpaperFile;
        public final File mSubDisplayWallpaperInfoFile;
        public final File mSubDisplayWallpaperLockFile;
        public final File mVirtualDisplayWallpaperFile;
        public final File mVirtualDisplayWallpaperInfoFile;
        public final File mWallpaperDir;
        public final File mWallpaperFile;
        public FileObserver mWallpaperFileObserver;
        public final File mWallpaperLockDir;
        public final File mWallpaperLockFile;

        public SemWallpaperObserver(File file, File file2, WallpaperManagerService.SemCallback semCallback) {
            this.mWallpaperFileObserver = null;
            this.mLockWallpaperFileObserver = null;
            Log.m129d("SemWallpaperManagerService", "SemWallpaperObserver");
            this.mWallpaperDir = file;
            this.mWallpaperFile = new File(file, "wallpaper_orig");
            this.mWallpaperLockDir = file2;
            this.mWallpaperLockFile = new File(file2, "wallpaper_lock_orig");
            this.mDesktopWallpaperFile = new File(file, "wallpaper_desktop_orig");
            this.mDesktopWallpaperLockFile = new File(file2, "wallpaper_desktop_lock_orig");
            this.mDesktopWallpaperInfoFile = new File(file, "wallpaper_desktop_info.xml");
            this.mSubDisplayWallpaperFile = new File(file, "wallpaper_sub_display_orig");
            this.mSubDisplayWallpaperLockFile = new File(file2, "wallpaper_sub_display_lock_orig");
            this.mSubDisplayWallpaperInfoFile = new File(file, "wallpaper_subdisplay_info.xml");
            this.mVirtualDisplayWallpaperFile = new File(file, "wallpaper_virtual_display_orig");
            this.mVirtualDisplayWallpaperInfoFile = new File(file, "wallpaper_virtualdisplay_info.xml");
            int i = 1672;
            this.mLockWallpaperFileObserver = new FileObserver(file2.getAbsolutePath(), i) { // from class: com.samsung.server.wallpaper.SemWallpaperManagerService.SemWallpaperObserver.1
                @Override // android.os.FileObserver
                public void onEvent(int i2, String str) {
                    if (str == null) {
                        return;
                    }
                    File file3 = new File(SemWallpaperObserver.this.mWallpaperLockDir, str);
                    if (SemWallpaperObserver.this.mWallpaperLockFile.equals(file3) || SemWallpaperObserver.this.mDesktopWallpaperLockFile.equals(file3) || SemWallpaperObserver.this.mSubDisplayWallpaperLockFile.equals(file3)) {
                        SemWallpaperObserver.this.mCallback.updateEvent(i2, str, file3, false, true);
                    }
                }
            };
            this.mWallpaperFileObserver = new FileObserver(file.getAbsolutePath(), i) { // from class: com.samsung.server.wallpaper.SemWallpaperManagerService.SemWallpaperObserver.2
                @Override // android.os.FileObserver
                public void onEvent(int i2, String str) {
                    if (str == null) {
                        return;
                    }
                    File file3 = new File(SemWallpaperObserver.this.mWallpaperDir, str);
                    SemWallpaperObserver.this.mCallback.updateEvent(i2, str, file3, SemWallpaperObserver.this.mWallpaperFile.equals(file3) || SemWallpaperObserver.this.mDesktopWallpaperFile.equals(file3) || SemWallpaperObserver.this.mSubDisplayWallpaperFile.equals(file3) || SemWallpaperObserver.this.mVirtualDisplayWallpaperFile.equals(file3), false);
                }
            };
            this.mCallback = semCallback;
        }

        public FileObserver getWallpaperFileObserver() {
            return this.mWallpaperFileObserver;
        }

        public FileObserver getLockWallpaperFileObserver() {
            Slog.d("SemWallpaperManagerService", "getLockWallpaperFileObserver: mLockWallpaperFileObserver = " + this.mLockWallpaperFileObserver + ", mWallpaperLockDir.getAbsolutePath() = " + this.mWallpaperLockDir.getAbsolutePath());
            return this.mLockWallpaperFileObserver;
        }
    }

    public static String getStringFromFile(String str) {
        String str2;
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            try {
                str2 = convertStreamToString(fileInputStream);
            } catch (Exception e) {
                Log.m130e("SemWallpaperManagerService", "getStringFromFile " + e);
                fileInputStream.close();
                str2 = null;
            }
            return str2;
        } finally {
            fileInputStream.close();
        }
    }

    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        Boolean bool = Boolean.TRUE;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                if (bool.booleanValue()) {
                    sb.append(readLine);
                    bool = Boolean.FALSE;
                } else {
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    sb.append(readLine);
                }
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    public static String getCallStackString() {
        return getCallStackString(-1, true);
    }

    public static String getCallStackString(int i, boolean z) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (i < 0) {
            i = stackTrace.length;
        }
        int i2 = 2;
        for (int i3 = 0; i3 < stackTrace.length; i3++) {
            if (stackTrace[i3].getMethodName().equals("getCallStackString")) {
                i2 = i3 + 1;
            }
        }
        if (stackTrace.length - i2 < 3) {
            i = stackTrace.length - i2;
        }
        String str = "";
        for (int i4 = i2; i4 < i2 + i && i4 < stackTrace.length; i4++) {
            StackTraceElement stackTraceElement = stackTrace[i4];
            if (i4 == i2) {
                str = stackTraceElement.getMethodName();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(stackTraceElement.getMethodName());
                sb.append(" -> ");
                sb.append(z ? KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE : "");
                sb.append(str);
                str = sb.toString();
            }
            if (z) {
                str = stackTraceElement.getClassName() + "(line " + stackTraceElement.getLineNumber() + ") :" + str;
            }
        }
        if (str.length() <= 0) {
            return "";
        }
        return "(" + str + ") ";
    }

    public void removeOriginalSavedFile(String str, int i) {
        if (str.equals("com.android.systemui") || str.equals("com.samsung.android.app.dressroom")) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.intent.action.REQUEST_DELETE_WALLPAPER");
        intent.setPackage("com.android.systemui");
        intent.putExtra("WHICH", i);
        this.mContext.sendBroadcast(intent);
    }

    public boolean hasLockscreenWallpaper(boolean z) {
        if (z) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int i = this.mCurrentUserId;
            if (i <= 0) {
                i = 0;
            }
            return Settings.System.getIntForUser(contentResolver, "lockscreen_wallpaper_sub", 1, i) != 0;
        }
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        int i2 = this.mCurrentUserId;
        if (i2 <= 0) {
            i2 = 0;
        }
        return Settings.System.getIntForUser(contentResolver2, "lockscreen_wallpaper", 1, i2) != 0;
    }

    public boolean isSupportingMode(int i) {
        return WhichChecker.getMode(i) != 16 || Rune.SUPPORT_SUB_DISPLAY_MODE;
    }

    public Bitmap getPreloadRotatedCroppedBitmap(File file, SemWallpaperData semWallpaperData) {
        return generateResizedBitmap(file, semWallpaperData, true);
    }

    public void generateResizedBitmap(File file, SemWallpaperData semWallpaperData) {
        Bitmap generateResizedBitmap = generateResizedBitmap(file, semWallpaperData, false);
        if (generateResizedBitmap != null) {
            semWallpaperData.setCroppedBitmap(generateResizedBitmap);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap generateResizedBitmap(File file, SemWallpaperData semWallpaperData, boolean z) {
        Bitmap decodeFile;
        int which = semWallpaperData.getWhich();
        Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: which = " + which + " type = " + semWallpaperData.getWpType() + " isPreloadRotated = " + z);
        long nanoTime = System.nanoTime();
        int displayId = getDisplayId(semWallpaperData);
        Display display = this.mDisplayManager.getDisplay(displayId);
        if (display == null) {
            Log.m130e("SemWallpaperManagerService", "generateResizedBitmap: failed to get display. displayId = " + displayId);
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (displayInfo.logicalWidth == 0 || displayInfo.logicalHeight == 0) {
            return null;
        }
        if ((Rune.isTablet() || Rune.isFolder()) && semWallpaperData.getOrientation() != 0 && getOrientation() != 0 && semWallpaperData.getOrientation() != getOrientation()) {
            int i = displayInfo.logicalWidth;
            displayInfo.logicalWidth = displayInfo.logicalHeight;
            displayInfo.logicalHeight = i;
        }
        if (!WhichChecker.isSingleType(which)) {
            Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: invalid which = " + which);
            return null;
        }
        if (WhichChecker.isLock(which)) {
            file = this.mLockWallpaper.getWallpaperFile(file, semWallpaperData);
        } else if ((Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || WhichChecker.isVirtualDisplay(which)) && semWallpaperData.getWpType() == 8) {
            file = semWallpaperData.getVideoFirstFrameFile();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (file != null) {
            try {
                if (file.exists()) {
                    decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (decodeFile != null) {
                        Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: bitmap is null");
                        return null;
                    }
                    int width = decodeFile.getWidth();
                    int height = decodeFile.getHeight();
                    if (width <= 0 || height <= 0) {
                        Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: bitmap size must be > 0");
                        return null;
                    }
                    int i2 = displayInfo.logicalWidth;
                    int i3 = displayInfo.logicalHeight;
                    if (semWallpaperData.getOrientation() == 2) {
                        if (Rune.isTablet() || (Rune.SUPPORT_SUB_DISPLAY_MODE && !WhichChecker.isSubDisplay(semWallpaperData.getWhich()))) {
                            i2 = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            i3 = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                        } else {
                            i2 = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            i3 = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                        }
                    }
                    if (z) {
                        int i4 = i3;
                        i3 = i2;
                        i2 = i4;
                    }
                    Point[] croppedBitmapInfo = getCroppedBitmapInfo(width, height, i2, i3);
                    Point point = croppedBitmapInfo[0];
                    int i5 = point.x;
                    int i6 = point.y;
                    Point point2 = croppedBitmapInfo[1];
                    int i7 = point2.x;
                    int i8 = point2.y;
                    if (i7 < 0) {
                        i7 = 0;
                    }
                    int i9 = i8 >= 0 ? i8 : 0;
                    Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: start bitmapWidth = " + width + ", bitmapHeight = " + height + " optimalWidth = " + width + ", optimalHeight = " + height + ", finalWidth = " + i5 + " , finalHeight = " + i6 + ", optimalDx = " + i7 + " , optimalDy = " + i9 + ", deviceWidth = " + i2 + " , deviceHeight = " + i3);
                    if (width <= 0 || height <= 0 || i5 <= 0 || i6 <= 0) {
                        Log.m133v("SemWallpaperManagerService", "generateResizedBitmap: Width or height of newly generated bitmap should be greater than 0.");
                        Log.addLogString("SemWallpaperManagerService", "generateResizedBitmap: Width or height of newly generated bitmap should be greater than 0.");
                        return null;
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(decodeFile, i7, i9, i5, i6);
                    Log.m129d("SemWallpaperManagerService", "generateResizedBitmap end createBitmap");
                    if (!decodeFile.equals(createBitmap)) {
                        decodeFile.recycle();
                    }
                    long nanoTime2 = System.nanoTime() - nanoTime;
                    Log.m132i("SemWallpaperManagerService", "generateResizedBitmap : took :" + String.format("%d.%06dms", Long.valueOf(nanoTime2 / 1000000), Long.valueOf(nanoTime2 % 1000000)) + "ms");
                    Log.m132i("SemWallpaperManagerService", "generateResizedBitmap : " + createBitmap.getWidth() + ", " + createBitmap.getHeight());
                    return createBitmap;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: file is null, (which = " + which + ") so create Operator wallpaper bitmap");
        decodeFile = this.mOMCWallpaper.getOperatorWallpaperBitmap(which);
        if (decodeFile == null) {
            Log.m129d("SemWallpaperManagerService", "generateResizedBitmap: file operator bitmap is null, (which = " + which + ") so create Default wallpaper bitmap");
            decodeFile = this.mDefaultWallpaper.getDefaultWallpaperBitmap(which);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (decodeFile != null) {
        }
    }

    public boolean generateCroppedBitmap(SemWallpaperData semWallpaperData, String str) {
        Bitmap decodeFile;
        int i;
        Uri parse = Uri.parse(semWallpaperData.getUri());
        if (parse == null) {
            return false;
        }
        File file = new File(parse.getPath());
        if (!file.exists() || (decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath())) == null) {
            return false;
        }
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        if (width <= 0 || height <= 0) {
            Log.m129d("SemWallpaperManagerService", "generateCroppedBitmap: bitmap size must be > 0");
            return false;
        }
        if (!"com.samsung.android.themecenter".equals(str)) {
            semWallpaperData.setCroppedBitmap(decodeFile);
            return true;
        }
        int displayId = getDisplayId(semWallpaperData);
        DisplayInfo displayInfo = new DisplayInfo();
        this.mDisplayManager.getDisplay(displayId).getDisplayInfo(displayInfo);
        int i2 = displayInfo.logicalWidth;
        if (i2 == 0 || (i = displayInfo.logicalHeight) == 0) {
            semWallpaperData.setCroppedBitmap(decodeFile);
            return true;
        }
        Point[] croppedBitmapInfo = getCroppedBitmapInfo(width, height, i2, i);
        Point point = croppedBitmapInfo[0];
        int i3 = point.x;
        int i4 = point.y;
        Point point2 = croppedBitmapInfo[1];
        int i5 = point2.x;
        int i6 = point2.y;
        if (i5 < 0) {
            i5 = 0;
        }
        int i7 = i6 >= 0 ? i6 : 0;
        Log.m129d("SemWallpaperManagerService", "generateCroppedBitmap:\n\tbitmapWidth = " + width + "\n\tbitmapHeight = " + height + "\n\tfinalWidth = " + i3 + "\n\tfinalHeight = " + i4 + "\n\toptimalDx = " + i5 + "\n\toptimalDy = " + i7);
        if (i3 <= 0 || i4 <= 0) {
            Log.m133v("SemWallpaperManagerService", "generateCroppedBitmap: Width or height of newly generated bitmap should be greater than 0.");
            Log.addLogString("SemWallpaperManagerService", "generateResizedBitmap: Width or height of newly generated bitmap should be greater than 0.");
            semWallpaperData.setCroppedBitmap(decodeFile);
            return true;
        }
        Bitmap createBitmap = Bitmap.createBitmap(decodeFile, i5, i7, i3, i4);
        semWallpaperData.setCroppedBitmap(createBitmap);
        if (!decodeFile.equals(createBitmap)) {
            decodeFile.recycle();
        }
        return true;
    }

    public int getDisplayId(SemWallpaperData semWallpaperData) {
        return getDisplayId(semWallpaperData.getWhich());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getDisplayId(int i) {
        int i2 = 0;
        if (this.mDesktopMode.isDesktopDualMode()) {
            if (((i & 60) == 8) != false) {
                return 2;
            }
        }
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return 0;
        }
        boolean isSubDisplay = WhichChecker.isSubDisplay(i);
        if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
            i2 = isSubDisplay ? 1 : 0;
        } else {
            DisplayInfo displayInfo = new DisplayInfo();
            for (Display display : this.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN")) {
                if (display != null && display.getDisplayInfo(displayInfo)) {
                    Object[] objArr = (displayInfo.flags & 262144) != 0;
                    if ((isSubDisplay && objArr != false) || (!isSubDisplay && objArr == false)) {
                        i2 = displayInfo.displayId;
                        break;
                    }
                } else {
                    Log.m130e("SemWallpaperManagerService", "getDisplayId: failed to get display. display=" + display);
                }
            }
        }
        Log.m129d("SemWallpaperManagerService", "getDisplayId: which=" + i + ", displayId=" + i2);
        return i2;
    }

    public final Point[] getCroppedBitmapInfo(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        Point[] pointArr = new Point[2];
        Log.m129d("SemWallpaperManagerService", "getCroppedBitmapInfo: start, optimalWidth = " + i + ", optimalHeight = " + i2 + ", deviceWidth = " + i3 + ", deviceHeight = " + i4);
        float f = ((float) i4) / ((float) i3);
        if (i <= 0 || i2 <= 0) {
            i = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
        } else if (f > i2 / i) {
            int i8 = (i3 * i2) / i4;
            i7 = i2;
            i5 = (int) ((i - i8) * 0.5d);
            i = i8;
            i6 = 0;
        } else {
            i7 = (i4 * i) / i3;
            i6 = (int) ((i2 - i7) * 0.5d);
            i5 = 0;
        }
        pointArr[0] = new Point(i, i7);
        pointArr[1] = new Point(i5, i6);
        Log.m129d("SemWallpaperManagerService", "getCroppedBitmapInfo: end width = " + i + ", height = " + i7 + ", dx = " + i5 + " , dy = " + i6);
        return pointArr;
    }

    public void handleWallpaperBindingTimeout(boolean z, boolean z2) {
        if (z) {
            this.mHandler.removeMessages(1009);
        }
        if (z2) {
            this.mHandler.sendEmptyMessageDelayed(1009, 2000L);
        }
    }

    public File initializeThumnailFile(WallpaperData wallpaperData, int i, int i2, int i3) {
        SemWallpaperData semWallpaperData = wallpaperData.getSemWallpaperData();
        File file = null;
        if (semWallpaperData == null) {
            Log.m129d("SemWallpaperManagerService", "initializeThumnailFile: SemWallpaperData is null.");
            return null;
        }
        boolean isLock = WhichChecker.isLock(i);
        if (i2 == 8 && (file = semWallpaperData.getVideoFirstFrameFile()) == null) {
            if (WhichChecker.isVirtualDisplay(i)) {
                semWallpaperData.setVideoFirstFrameFile(new File(isLock ? getWallpaperLockDir(i3) : getWallpaperDir(i3), isLock ? "wallpaper_first_virtual" : "wallpaper_first_virtual_home"));
            } else if (WhichChecker.isSubDisplay(i)) {
                semWallpaperData.setVideoFirstFrameFile(new File(isLock ? getWallpaperLockDir(i3) : getWallpaperDir(i3), isLock ? "wallpaper_first_sub" : "wallpaper_first_sub_home"));
            } else {
                semWallpaperData.setVideoFirstFrameFile(new File(isLock ? getWallpaperLockDir(i3) : getWallpaperDir(i3), isLock ? "wallpaper_first" : "wallpaper_first_home"));
            }
            file = semWallpaperData.getVideoFirstFrameFile();
        }
        if (i2 == 4 && isLock && (file = semWallpaperData.getAnimatedBackground()) == null) {
            if (WhichChecker.isSubDisplay(i)) {
                semWallpaperData.setAnimatedBackground(new File(getWallpaperLockDir(i3), "wallpaper_animated_background_sub"));
            } else {
                semWallpaperData.setAnimatedBackground(new File(getWallpaperLockDir(i3), "wallpaper_animated_background"));
            }
            file = semWallpaperData.getAnimatedBackground();
        }
        if (i2 != 1 || !isLock) {
            return file;
        }
        File motionBackground = semWallpaperData.getMotionBackground();
        if (motionBackground != null) {
            return motionBackground;
        }
        if (WhichChecker.isSubDisplay(i)) {
            semWallpaperData.setMotionBackground(new File(getWallpaperLockDir(i3), "wallpaper_motion_background_sub"));
        } else {
            semWallpaperData.setMotionBackground(new File(getWallpaperLockDir(i3), "wallpaper_motion_background"));
        }
        return semWallpaperData.getMotionBackground();
    }

    public int getCurrentImplicitMode() {
        return WhichChecker.determineMode(this.mSubDisplayMode.getLidState() == 0);
    }

    public int getModeEnsuredWhich(int i) {
        if (WhichChecker.getMode(i) != 0) {
            return i;
        }
        int currentImplicitMode = getCurrentImplicitMode() | WhichChecker.getType(i);
        Slog.d("SemWallpaperManagerService", "getModeEnsuredWhich: detected which = " + currentImplicitMode);
        return currentImplicitMode;
    }

    public File getThumbnailFile(int i, int i2, int i3) {
        int modeEnsuredWhich = getModeEnsuredWhich(i);
        boolean isLock = WhichChecker.isLock(modeEnsuredWhich);
        String str = i3 == 2 ? "_land" : "";
        return new File(isLock ? getWallpaperLockDir(i2) : getWallpaperDir(i2), "wallpaper_thumb_" + modeEnsuredWhich + str);
    }

    public boolean deleteThumbnailFile(int i, int i2) {
        boolean z = true;
        if (WhichChecker.isSystemAndLock(i)) {
            int mode = WhichChecker.getMode(i);
            return deleteThumbnailFile(mode | 1, i2) | deleteThumbnailFile(mode | 2, i2) | false;
        }
        int modeEnsuredWhich = getModeEnsuredWhich(i);
        int[] iArr = {1, 2};
        for (int i3 = 0; i3 < 2; i3++) {
            int i4 = iArr[i3];
            File thumbnailFile = getThumbnailFile(modeEnsuredWhich, i2, i4);
            if (thumbnailFile != null && thumbnailFile.exists()) {
                boolean delete = thumbnailFile.delete();
                Log.m129d("SemWallpaperManagerService", "deleteThumbnailFile: which=" + modeEnsuredWhich + ", orientation=" + i4 + ", success=" + delete);
                if (!delete) {
                    z = false;
                }
            }
        }
        return z;
    }

    public static File getWallpaperLockDir(int i) {
        return new File(Environment.getUserSystemDirectory(i), "wallpaper_lock_images");
    }

    public static File getWallpaperDir(int i) {
        return Environment.getUserSystemDirectory(i);
    }

    public void removeSnapshotByWhich(int i) {
        ArrayList whiches = SnapshotHelper.getWhiches(i);
        Log.m129d("SemWallpaperManagerService", "removeSnapshotByWhich: whiches = " + whiches);
        HashMap hashMap = new HashMap();
        synchronized (this.mSnapshotDataLock) {
            Iterator it = whiches.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                this.mSnapshotManager.removeSnapshotByWhich(this.mCurrentUserId, intValue);
                notifySnapshotStatus(intValue, 4, -1);
                hashMap.put(Integer.valueOf(intValue), 1);
            }
            this.mSnapshotManager.addHistory(this.mCurrentUserId, 3, -1, hashMap);
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
    }

    public void removeSnapshotByKey(int i) {
        synchronized (this.mSnapshotDataLock) {
            Map removeSnapshotByKey = this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, i);
            notifySnapshotStatus(-1, 4, i);
            this.mSnapshotManager.addHistory(this.mCurrentUserId, 3, i, removeSnapshotByKey);
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
    }

    public void removeSnapshotBySource(String str) {
        Log.m129d("SemWallpaperManagerService", "removeSnapshotBySource: source = " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mSnapshotDataLock) {
            Iterator it = this.mSnapshotManager.getAllSnapshots(this.mCurrentUserId).iterator();
            while (it.hasNext()) {
                SnapshotManager.SnapshotData snapshotData = (SnapshotManager.SnapshotData) it.next();
                if (snapshotData != null && TextUtils.equals(str, snapshotData.getSource())) {
                    removeSnapshotByKey(snapshotData.getKey());
                }
            }
        }
    }

    public int makeSnapshot(int i, int i2, Bundle bundle) {
        boolean z;
        SnapshotManager.SnapshotData snapshot;
        HashMap hashMap = new HashMap();
        ArrayList whiches = SnapshotHelper.getWhiches(i);
        if (i2 <= 0) {
            synchronized (this.mSnapshotDataLock) {
                i2 = this.mSnapshotManager.makeSnapshotKey(this.mCurrentUserId);
            }
        }
        int i3 = 0;
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT) {
            z = bundle != null ? bundle.getBoolean("is_paired", false) : false;
            if (!z) {
                int pairedDlsSnapshotKey = this.mSnapshotManager.getPairedDlsSnapshotKey(this.mCurrentUserId, i2);
                Log.m129d("SemWallpaperManagerService", "makeSnapshot: pairedDlsSnapshotKey = " + pairedDlsSnapshotKey);
                if (pairedDlsSnapshotKey > 0) {
                    SnapshotManager snapshotManager = this.mSnapshotManager;
                    int i4 = this.mCurrentUserId;
                    snapshotManager.addHistory(i4, 5, pairedDlsSnapshotKey, doRestoreOrMigrate(i4, pairedDlsSnapshotKey));
                    this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, pairedDlsSnapshotKey);
                }
            }
        } else {
            z = false;
        }
        SnapshotManager.SnapshotData snapshot2 = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i2);
        if (snapshot2 != null) {
            ArrayList whiches2 = snapshot2.getWhiches();
            if (whiches.size() != whiches2.size()) {
                Log.m134w("SemWallpaperManagerService", "makeSnapshot: Number of 'which' in key + [" + i2 + "] is not the same as previous on");
            }
            Iterator it = whiches.iterator();
            while (it.hasNext()) {
                if (!whiches2.contains(Integer.valueOf(((Integer) it.next()).intValue()))) {
                    Log.m134w("SemWallpaperManagerService", "makeSnapshot: 'which' values are not matched with previous snapshot. prevWhiches = " + whiches2 + ", whiches = " + whiches);
                }
            }
            Iterator it2 = whiches.iterator();
            while (it2.hasNext()) {
                SnapshotManager.SnapshotData lastSnapshotByWhich = this.mSnapshotManager.getLastSnapshotByWhich(this.mCurrentUserId, ((Integer) it2.next()).intValue());
                if (lastSnapshotByWhich != null && i2 != lastSnapshotByWhich.getKey()) {
                    SnapshotManager snapshotManager2 = this.mSnapshotManager;
                    int i5 = this.mCurrentUserId;
                    snapshotManager2.addHistory(i5, 5, i2, doRestoreOrMigrate(i5, i2));
                    this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, i2);
                }
            }
        }
        Iterator it3 = whiches.iterator();
        while (it3.hasNext()) {
            int intValue = ((Integer) it3.next()).intValue();
            WallpaperData m12812clone = this.mSnapshotCallback.requestWallpaperData(this.mCurrentUserId, intValue).m12812clone();
            if (m12812clone != null) {
                SemWallpaperData semWallpaperData = m12812clone.getSemWallpaperData();
                if (semWallpaperData != null) {
                    semWallpaperData.setCreationTime(SnapshotHelper.getCurrentTime());
                }
                Log.m129d("SemWallpaperManagerService", "makeSnapshot: which = " + intValue + ", key = " + i2 + ", wallpaperData [" + m12812clone + "]");
                synchronized (this.mSnapshotDataLock) {
                    int addSnapshot = this.mSnapshotManager.addSnapshot(this.mContext, this.mCurrentUserId, intValue, i2, m12812clone);
                    hashMap.put(Integer.valueOf(intValue), Integer.valueOf(addSnapshot));
                    if (addSnapshot > 0) {
                        connectSnapshotForLiveWallpaper(intValue, whiches, i2);
                    }
                }
            } else {
                Log.m130e("SemWallpaperManagerService", "makeSnapshot: wallpaperCopied is null.");
                hashMap.put(Integer.valueOf(intValue), -5);
            }
        }
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT && (snapshot = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i2)) != null && bundle != null) {
            snapshot.setFromPairedService(z);
        }
        this.mSnapshotManager.addHistory(this.mCurrentUserId, 1, i2, hashMap);
        for (Map.Entry entry : hashMap.entrySet()) {
            int intValue2 = ((Integer) entry.getKey()).intValue();
            int intValue3 = ((Integer) entry.getValue()).intValue();
            Log.m129d("SemWallpaperManagerService", "makeSnapshot: Result <" + intValue2 + ", " + intValue3 + ">");
            if (intValue3 > 0 || intValue3 == -3) {
                notifySnapshotStatus(intValue2, 1, i2);
                i3++;
            }
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
        if (i3 > 0) {
            return i2;
        }
        return -1;
    }

    public final void connectSnapshotForLiveWallpaper(int i, ArrayList arrayList, int i2) {
        int correspondingWhich;
        SnapshotManager.SnapshotData nearestSnapshot;
        if (!shouldCheckCorrespondingWhichForLiveWallpaper(i, arrayList) || (correspondingWhich = SnapshotHelper.getCorrespondingWhich(i)) <= 0 || getSnapshotCount(correspondingWhich) <= 0 || (nearestSnapshot = this.mSnapshotManager.getNearestSnapshot(this.mCurrentUserId, correspondingWhich)) == null || nearestSnapshot.hasWallpaperData(i) || nearestSnapshot.getLockscreenVisibility(correspondingWhich) != 0) {
            return;
        }
        SnapshotManager.SnapshotData lastSnapshot = this.mSnapshotManager.getLastSnapshot(this.mCurrentUserId);
        if (nearestSnapshot.getConnectedSnapshotForLiveWallpaper(correspondingWhich) == -1) {
            lastSnapshot.setConnectedSnapshotForLiveWallpaper(i, nearestSnapshot.getKey());
            nearestSnapshot.setConnectedSnapshotForLiveWallpaper(correspondingWhich, i2);
        }
    }

    public boolean restoreSnapshot(int i, String str) {
        int i2;
        SnapshotManager.SnapshotData lastSnapshot;
        Log.m129d("SemWallpaperManagerService", "restoreSnapshot: key = " + i + ", callingPackage = " + str);
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT) {
            i2 = this.mSnapshotManager.getPairedDlsSnapshotKey(this.mCurrentUserId, i);
            Log.m129d("SemWallpaperManagerService", "restoreSnapshot: pairedDlsSnapshotKey = " + i2);
        } else {
            i2 = -1;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (!canRestore(i)) {
            notifySnapshotStatus(-1, 2, i);
            return true;
        }
        if (isNeedToNotifySnapshotStatus(i)) {
            notifySnapshotStatus(-1, 3, i);
        }
        synchronized (this.mSnapshotDataLock) {
            lastSnapshot = this.mSnapshotManager.getLastSnapshot(this.mCurrentUserId);
        }
        if (lastSnapshot != null) {
            if (i == lastSnapshot.getKey()) {
                SnapshotManager snapshotManager = this.mSnapshotManager;
                int i3 = this.mCurrentUserId;
                snapshotManager.addHistory(i3, 2, i, doRestore(i3, i));
            } else {
                Log.m129d("SemWallpaperManagerService", "restoreSnapshot: SnapshotData for key " + i + " is not the latest one.");
                SnapshotManager snapshotManager2 = this.mSnapshotManager;
                int i4 = this.mCurrentUserId;
                snapshotManager2.addHistory(i4, 2, i, doRestoreOrMigrate(i4, i));
            }
        } else {
            Log.m129d("SemWallpaperManagerService", "restoreSnapshot: No snapshot.");
            this.mSnapshotManager.addHistory(this.mCurrentUserId, 2, i, "No snapshot");
        }
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.removeSnapshotByKey(this.mCurrentUserId, i);
        }
        saveSettingsLockedForSnapshot(this.mCurrentUserId);
        Log.m129d("SemWallpaperManagerService", "restoreSnapshot: Elapsed Time = " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT && i2 > 0) {
            restoreSnapshot(i2, str);
        }
        return true;
    }

    public final boolean shouldCheckCorrespondingWhichForLiveWallpaper(int i, ArrayList arrayList) {
        int correspondingWhich;
        if (arrayList == null || arrayList.size() <= 0 || (correspondingWhich = SnapshotHelper.getCorrespondingWhich(i)) <= 0) {
            return false;
        }
        Log.m129d("SemWallpaperManagerService", "shouldCheckCorrespondingWhichForLiveWallpaper: Check existance of correspondingWhich [" + correspondingWhich + "]");
        return !arrayList.contains(Integer.valueOf(correspondingWhich));
    }

    public final boolean canRestore(int i) {
        if (this.mSnapshotManager.getSnapshotCount(this.mCurrentUserId) <= 0) {
            Log.m130e("SemWallpaperManagerService", "canRestore: No snapshot.");
            this.mSnapshotManager.addHistory(this.mCurrentUserId, 2, i, "No snapshot");
            return false;
        }
        SnapshotManager.SnapshotData snapshot = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i);
        if (snapshot != null && snapshot.hasWallpaperData()) {
            return true;
        }
        Log.m130e("SemWallpaperManagerService", "canRestore: No snapshot for key [" + i + "].");
        this.mSnapshotManager.addHistory(this.mCurrentUserId, 2, i, "No snapshot");
        return false;
    }

    public final boolean isNeedToNotifySnapshotStatus(int i) {
        SnapshotManager.SnapshotData snapshot;
        WallpaperData wallpaperData;
        WallpaperData wallpaperData2;
        if (Rune.SUPPORT_DLS_SNAPSHOT || (snapshot = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i)) == null) {
            return false;
        }
        boolean hasWallpaperData = snapshot.hasWallpaperData(6);
        boolean hasWallpaperData2 = snapshot.hasWallpaperData(18);
        boolean hasWallpaperData3 = Rune.SUPPORT_COVER_DISPLAY_WATCHFACE ? snapshot.hasWallpaperData(17) : false;
        boolean hasWallpaperData4 = Rune.VIRTUAL_DISPLAY_WALLPAPER ? snapshot.hasWallpaperData(33) : false;
        boolean z = hasWallpaperData3 && (wallpaperData2 = snapshot.getWallpaperData(17)) != null && wallpaperData2.getSemWallpaperData().getWpType() == 3;
        boolean z2 = hasWallpaperData4 && (wallpaperData = snapshot.getWallpaperData(33)) != null && wallpaperData.getSemWallpaperData().getWpType() == 3;
        Log.m129d("SemWallpaperManagerService", "isNeedToNotifySnapshotStatus: key = " + i + ", hasMainLock = " + hasWallpaperData + ", hasSubLock = " + hasWallpaperData2 + ", hasCoverHome = " + hasWallpaperData3 + ", hasVirtualHome = " + hasWallpaperData4 + ", hasCoverMultipack = " + z + ", hasVirtualMultipack = " + z2);
        if (z || z2) {
            return true;
        }
        if (hasWallpaperData && this.mSnapshotManager.getSnapshotCount(this.mCurrentUserId, 6) == 1) {
            return true;
        }
        return hasWallpaperData2 && this.mSnapshotManager.getSnapshotCount(this.mCurrentUserId, 18) == 1;
    }

    public final Map doRestore(int i, int i2) {
        SnapshotManager.SnapshotData snapshot;
        HashMap hashMap = new HashMap();
        synchronized (this.mSnapshotDataLock) {
            snapshot = this.mSnapshotManager.getSnapshot(i, i2);
        }
        if (snapshot != null) {
            Log.m129d("SemWallpaperManagerService", "doRestore: which set = " + snapshot.getWhiches());
            Iterator it = snapshot.getWhiches().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                int restoreSnapshotInternal = restoreSnapshotInternal(i, intValue, snapshot);
                postProcess(i, intValue, snapshot, restoreSnapshotInternal);
                hashMap.put(Integer.valueOf(intValue), Integer.valueOf(restoreSnapshotInternal));
            }
        }
        return hashMap;
    }

    public final Map doRestoreOrMigrate(int i, int i2) {
        SnapshotManager.SnapshotData snapshot;
        HashMap hashMap = new HashMap();
        synchronized (this.mSnapshotDataLock) {
            snapshot = this.mSnapshotManager.getSnapshot(i, i2);
        }
        if (snapshot != null) {
            Iterator it = snapshot.getWhiches().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (this.mSnapshotManager.shouldRestoreSnapshot(i, i2, intValue)) {
                    int restoreSnapshotInternal = restoreSnapshotInternal(i, intValue, snapshot);
                    postProcess(i, intValue, snapshot, restoreSnapshotInternal);
                    hashMap.put(Integer.valueOf(intValue), Integer.valueOf(restoreSnapshotInternal));
                } else {
                    synchronized (this.mSnapshotDataLock) {
                        this.mSnapshotManager.migrateToPriorSnapshot(i, i2, intValue);
                    }
                    hashMap.put(Integer.valueOf(intValue), 1004);
                }
            }
        }
        return hashMap;
    }

    public final void postProcess(int i, int i2, SnapshotManager.SnapshotData snapshotData, int i3) {
        long clearCallingIdentity;
        SemWallpaperData semWallpaperData;
        if (i3 == 1001) {
            WallpaperData wallpaperData = snapshotData.getWallpaperData(i2);
            int i4 = 0;
            if (wallpaperData != null && (semWallpaperData = wallpaperData.getSemWallpaperData()) != null) {
                int wpType = semWallpaperData.getWpType();
                try {
                    WallpaperData requestWallpaperData = this.mSnapshotCallback.requestWallpaperData(i, i2);
                    String wallpaperColorPath = LegibilityColor.getWallpaperColorPath(i, i2, false);
                    SemWallpaperColors primarySemColors = semWallpaperData.getPrimarySemColors();
                    if (primarySemColors != null) {
                        primarySemColors.save(wallpaperColorPath);
                        requestWallpaperData.getSemWallpaperData().setPrimarySemColors(semWallpaperData.getPrimarySemColors());
                    }
                    SemWallpaperColors[] landscapeColors = semWallpaperData.getLandscapeColors();
                    if (landscapeColors != null && landscapeColors.length > 0) {
                        landscapeColors[0].save(LegibilityColor.getWallpaperColorPath(i, i2, true));
                        requestWallpaperData.getSemWallpaperData().setLandscapeColors(landscapeColors);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i4 = wpType;
            }
            this.mSnapshotCallback.requestSaveSettingsLocked(i, i2);
            migrateSettingsForLiveWallpaper(i, i2, snapshotData);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SnapshotHelper.updateSettings(this.mContext, i, snapshotData.getSettingsData(i2));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (!WhichChecker.isLock(i2)) {
                    if (WhichChecker.isWatchFaceDisplay(i2)) {
                        this.mSnapshotCallback.requestNotifyCoverWallpaperChanged(i, i2, null);
                        return;
                    }
                    return;
                } else if (i4 == 3) {
                    if (Rune.SUPPORT_DLS_SNAPSHOT) {
                        this.mSnapshotCallback.requestNotifyMultipackApplied(i, i2, null);
                        return;
                    }
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("trigger", "snapshot");
                    this.mSnapshotCallback.requestNotifyLockWallpaperChanged(i, i2, bundle);
                    this.mSnapshotCallback.requestNotifySemWallpaperColors(i2);
                    return;
                }
            } finally {
            }
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SnapshotHelper.writeDefaultSettings(this.mContext, i, i2);
            this.mSnapshotCallback.requestClearWallpaper(i, i2);
        } finally {
        }
    }

    public final void migrateSettingsForLiveWallpaper(int i, int i2, SnapshotManager.SnapshotData snapshotData) {
        int connectedSnapshotForLiveWallpaper;
        if (snapshotData.getLockscreenVisibility(i2) == 1 || (connectedSnapshotForLiveWallpaper = snapshotData.getConnectedSnapshotForLiveWallpaper(i2)) == -1) {
            return;
        }
        SnapshotManager.SnapshotData snapshot = this.mSnapshotManager.getSnapshot(i, connectedSnapshotForLiveWallpaper);
        int correspondingWhich = SnapshotHelper.getCorrespondingWhich(i2);
        if (snapshot == null || !snapshot.hasWallpaperData(correspondingWhich)) {
            return;
        }
        snapshot.setLockscreenVisibility(correspondingWhich, 0);
        snapshotData.setLockscreenVisibility(i2, 1);
    }

    public final int restoreSnapshotInternal(int i, int i2, SnapshotManager.SnapshotData snapshotData) {
        WallpaperData requestWallpaperData = this.mSnapshotCallback.requestWallpaperData(i, i2);
        if (requestWallpaperData == null) {
            Log.m130e("SemWallpaperManagerService", "restoreSnapshotInternal: wallpaper is null.");
            return 1003;
        }
        if (snapshotData == null || snapshotData.getWallpaperData(i2) == null) {
            Log.m130e("SemWallpaperManagerService", "restoreSnapshotInternal: snapshot or WallpaperData in snapshot is null.");
            return 1003;
        }
        try {
            WallpaperData m12812clone = snapshotData.getWallpaperData(i2).m12812clone();
            if (m12812clone == null) {
                Log.m130e("SemWallpaperManagerService", "restoreSnapshotInternal: wallpaperToRestore is null.");
                return 1003;
            }
            SemWallpaperData semWallpaperData = requestWallpaperData.getSemWallpaperData();
            SemWallpaperData semWallpaperData2 = m12812clone.getSemWallpaperData();
            if (requestWallpaperData.getWallpaperId() == m12812clone.getWallpaperId() && WhichChecker.containsSystem(semWallpaperData.getWhich())) {
                Log.m130e("SemWallpaperManagerService", "restoreSnapshotInternal: Same image wallpaper does not need to be restored.");
                return 1001;
            }
            File wallpaperFile = m12812clone.getWallpaperFile();
            SnapshotHelper.deleteFiles(requestWallpaperData);
            File targetFile = getTargetFile(i, i2, m12812clone);
            m12812clone.setWallpaperFile(targetFile);
            m12812clone.setWallpaperCropFile(new File(WhichChecker.isLock(i2) ? getWallpaperLockDir(i) : getWallpaperDir(i), getFileName(i2, WhichChecker.isLock(i2) ? 1 : 0, 1)));
            int wpType = semWallpaperData2.getWpType();
            String lastCallingPackage = semWallpaperData2.getLastCallingPackage();
            Log.m129d("SemWallpaperManagerService", "restoreSnapshotInternal: which = " + i2 + ", type = " + wpType + ", backupFile = " + wallpaperFile + ", targetFile = " + targetFile + ", lastCallingPackage = " + lastCallingPackage);
            semWallpaperData2.setWallpaperHistories(semWallpaperData.getWallpaperHistories());
            if (!TextUtils.isEmpty(lastCallingPackage)) {
                if (!lastCallingPackage.contains("[RESTORE]")) {
                    m12812clone.setCallingPackage("[RESTORE]" + lastCallingPackage);
                } else {
                    m12812clone.setCallingPackage(lastCallingPackage);
                }
            } else {
                m12812clone.setCallingPackage("[RESTORE]");
            }
            Log.m129d("SemWallpaperManagerService", "restoreSnapshotInternal: wallpaperToRestore [" + m12812clone + "]");
            if (wpType != 1) {
                if (wpType == 1000) {
                    this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                } else if (wpType == 3) {
                    this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                    if (WhichChecker.isWatchFaceDisplay(i2)) {
                        m12812clone.setImageWallpaperPending(true);
                        m12812clone.setWhichPending(i2);
                        if (Rune.SUPPORT_DLS_SNAPSHOT) {
                            this.mSnapshotCallback.requestWallpaperId(m12812clone);
                        }
                        this.mSnapshotCallback.requestBindWallpaper(i, i2, null);
                    }
                } else if (wpType != 4) {
                    if (wpType == 5) {
                        m12812clone.setImageWallpaperPending(true);
                        m12812clone.setWhichPending(i2);
                        this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                        this.mSnapshotCallback.requestBindWallpaper(i, i2, null);
                    } else {
                        if (wpType != 7) {
                            if (wpType == 8) {
                                this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                                if (WhichChecker.isSystem(i2)) {
                                    m12812clone.setImageWallpaperPending(true);
                                    m12812clone.setWhichPending(i2);
                                    this.mSnapshotCallback.requestBindWallpaper(i, i2, null);
                                }
                                return SnapshotHelper.saveFile(wallpaperFile, targetFile) ? 1001 : -2;
                            }
                            if (wallpaperFile != null && wallpaperFile.exists()) {
                                m12812clone.setImageWallpaperPending(true);
                                m12812clone.setWhichPending(i2);
                                this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                                return SnapshotHelper.saveFile(wallpaperFile, targetFile) ? 1001 : -2;
                            }
                            Log.m129d("SemWallpaperManagerService", "restoreSnapshotInternal: backupFile is not exist. But return success for default resources");
                            this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                            if (WhichChecker.isSystem(i2)) {
                                m12812clone.setImageWallpaperPending(true);
                                m12812clone.setWhichPending(i2);
                                this.mSnapshotCallback.requestBindWallpaper(i, i2, null);
                            }
                            return 1001;
                        }
                        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
                            if (SnapshotHelper.getBackupWallpaperAssetsDir(i, snapshotData.getKey(), i2).exists()) {
                                Log.m129d("SemWallpaperManagerService", "restoreSnapshotInternal: Asset files exist.");
                                SnapshotHelper.renameDirectory(SnapshotHelper.getBackupWallpaperAssetsDir(i, snapshotData.getKey(), i2), AssetFileManager.getBaseAssetDir(i2, i));
                            }
                            PreloadedLiveWallpaperHelper.recoverComponentNameIfMissed(m12812clone);
                        }
                        int which = m12812clone.getWhich() == 0 ? i2 : m12812clone.getWhich();
                        this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
                        this.mSnapshotCallback.requestBindWallpaper(i, which, m12812clone.getWallpaperComponent());
                    }
                }
                return 1001;
            }
            this.mSnapshotCallback.requestSaveRestoredWallpaperLocked(i, i2, m12812clone);
            return 1001;
        } catch (Exception e) {
            Log.m130e("SemWallpaperManagerService", "restoreSnapshotInternal: " + e.getMessage());
            return 1003;
        }
    }

    public void loadSettingsLockedForSnapshot(int i) {
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.loadSettingsLockedForSnapshot(i);
        }
    }

    public void saveSettingsLockedForSnapshot(int i) {
        synchronized (this.mSnapshotDataLock) {
            this.mSnapshotManager.saveSettingsLockedForSnapshot(i);
        }
    }

    public final File getTargetFile(int i, int i2, WallpaperData wallpaperData) {
        File file;
        int wpType = wallpaperData.getSemWallpaperData().getWpType();
        int which = wallpaperData.getSemWallpaperData().getWhich();
        if (wpType != 1) {
            if (wpType != 4) {
                if (wpType == 8) {
                    if (WhichChecker.isSubDisplay(i2)) {
                        file = new File(WhichChecker.isLock(i2) ? getWallpaperLockDir(i) : getWallpaperDir(i), WhichChecker.isLock(i2) ? "wallpaper_first_sub" : "wallpaper_first_sub_home");
                    } else {
                        file = new File(WhichChecker.isLock(i2) ? getWallpaperLockDir(i) : getWallpaperDir(i), WhichChecker.isLock(i2) ? "wallpaper_first" : "wallpaper_first_home");
                    }
                } else if (WhichChecker.isSystem(i2)) {
                    file = new File(getWallpaperDir(i), getFileName(i2, 0, 0));
                } else {
                    file = new File(getWallpaperLockDir(i), getFileName(i2, 1, 0));
                }
            } else if (WhichChecker.isSubDisplay(i2)) {
                file = new File(getWallpaperLockDir(i), "wallpaper_animated_background_sub");
            } else {
                file = new File(getWallpaperLockDir(i), "wallpaper_animated_background");
            }
        } else if (WhichChecker.isSubDisplay(i2)) {
            file = new File(getWallpaperLockDir(i), "wallpaper_motion_background_sub");
        } else {
            file = new File(getWallpaperLockDir(i), "wallpaper_motion_background");
        }
        Log.m129d("SemWallpaperManagerService", "getTargetFile: which = " + i2 + ", WallpaperDataWhich = " + which + ", wallpaperType = " + wpType + ", targetFile = " + file);
        return file;
    }

    public final void notifySnapshotStatus(int i, int i2, int i3) {
        ArrayList arrayList;
        if (Rune.SUPPORT_DLS_SNAPSHOT || (arrayList = (ArrayList) this.mSnapshotCallback.requestKeyguardListeners()) == null) {
            return;
        }
        Log.m129d("SemWallpaperManagerService", "notifySnapshotStatus: which = " + i + ", status = " + i2 + ", key = " + i3);
        if (i == -1 || WhichChecker.isLock(i) || WhichChecker.isWatchFaceDisplay(i)) {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    IWallpaperManagerCallback iWallpaperManagerCallback = (IWallpaperManagerCallback) it.next();
                    if (iWallpaperManagerCallback != null) {
                        iWallpaperManagerCallback.onSemBackupStatusChanged(i, i2, i3);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSnapshotCount(int i) {
        if (i == -1) {
            return this.mSnapshotManager.getSnapshotCount(this.mCurrentUserId);
        }
        return this.mSnapshotManager.getSnapshotCount(this.mCurrentUserId, i);
    }

    public boolean setSnapshotSource(int i, String str) {
        Log.m129d("SemWallpaperManagerService", "setSnapshotSource: key = " + i + ", source = " + str);
        synchronized (this.mSnapshotDataLock) {
            SnapshotManager.SnapshotData snapshot = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i);
            if (snapshot == null) {
                Log.m130e("SemWallpaperManagerService", "setSnapshotSource: No snapshot for key = " + i);
                return false;
            }
            snapshot.setSource(str);
            this.mSnapshotManager.saveSettingsLockedForSnapshot(this.mCurrentUserId);
            return true;
        }
    }

    public boolean isValidSnapshot(int i) {
        Log.m129d("SemWallpaperManagerService", "isValidSnapshot: key = " + i);
        SnapshotManager.SnapshotData snapshot = this.mSnapshotManager.getSnapshot(this.mCurrentUserId, i);
        if (snapshot != null && snapshot.hasWallpaperData()) {
            return true;
        }
        Log.m130e("SemWallpaperManagerService", "isValidSnapshot: No snapshot for key [" + i + "].");
        return false;
    }

    public int[] getSnapshotKeys(String str, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mSnapshotManager.getAllSnapshots(this.mCurrentUserId).iterator();
        while (it.hasNext()) {
            SnapshotManager.SnapshotData snapshotData = (SnapshotManager.SnapshotData) it.next();
            if (snapshotData != null && TextUtils.equals(str, snapshotData.getSource()) && snapshotData.hasWallpaperData(i)) {
                arrayList.add(Integer.valueOf(snapshotData.getKey()));
            }
        }
        return Arrays.stream((Integer[]) arrayList.toArray(new Integer[arrayList.size()])).mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public boolean isSnapshotTestMode() {
        if (SHIPPED) {
            return false;
        }
        return sSnapshotTestMode;
    }

    public void setSnapshotTestMode(boolean z) {
        if (SHIPPED) {
            return;
        }
        sSnapshotTestMode = z;
    }

    public static void putLog(String str) {
        Log.m129d("SemWallpaperManagerService", str);
        ArrayList arrayList = sLogList;
        synchronized (arrayList) {
            long currentTimeMillis = System.currentTimeMillis();
            arrayList.add((SimpleDateFormat.getDateTimeInstance().format(new Date(currentTimeMillis)) + "." + (currentTimeMillis % 1000)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str);
            if (arrayList.size() > 20) {
                arrayList.remove(0);
            }
        }
    }

    public static String[] getLogArray() {
        ArrayList arrayList = sLogList;
        synchronized (arrayList) {
            if (arrayList.isEmpty()) {
                return null;
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
    }
}
