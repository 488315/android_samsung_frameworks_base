package com.android.server.wallpaper;

import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Environment;
import android.os.FileObserver;
import android.os.RemoteCallbackList;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.SemWallpaperData;
import com.samsung.server.wallpaper.SemWallpaperManagerService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public final class WallpaperData implements Cloneable {
    public boolean allowBackup;
    public final RemoteCallbackList callbacks;
    public WallpaperManagerService.WallpaperConnection connection;
    public Rect cropHint;
    public boolean fromForegroundApp;
    public boolean imageWallpaperPending;
    public long lastDiedTime;
    public BindSource mBindSource;
    public final SparseArray mCropFiles;
    public SparseArray mCropHints;
    public boolean mIsColorExtractedFromDim;
    public int mOrientationWhenSet;
    public float mSampleSize;
    public SemWallpaperData mSemWallpaperData;
    public boolean mSystemWasBoth;
    public SparseArray mUidToDimAmount;
    public float mWallpaperDimAmount;
    public final SparseArray mWallpaperFiles;
    public int mWhich;
    public String name;
    public ComponentName nextWallpaperComponent;
    public WallpaperColors primaryColors;
    public IWallpaperManagerCallback setComplete;
    public final int userId;
    public ComponentName wallpaperComponent;
    public int wallpaperId;
    public FileObserver wallpaperObserver;
    public boolean wallpaperUpdating;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class BindSource {
        public static final /* synthetic */ BindSource[] $VALUES;
        public static final BindSource CONNECTION_TRY_TO_REBIND;
        public static final BindSource CONNECT_LOCKED;
        public static final BindSource INITIALIZE_FALLBACK;
        public static final BindSource PACKAGE_UPDATE_FINISHED;
        public static final BindSource RESTORE_SETTINGS_LIVE_FAILURE;
        public static final BindSource RESTORE_SETTINGS_LIVE_SUCCESS;
        public static final BindSource RESTORE_SETTINGS_STATIC;
        public static final BindSource SET_LIVE;
        public static final BindSource SET_LIVE_TO_CLEAR;
        public static final BindSource SET_STATIC;
        public static final BindSource SWITCH_WALLPAPER_FAILURE;
        public static final BindSource SWITCH_WALLPAPER_UNLOCK_USER;
        public static final BindSource UNKNOWN;

        static {
            BindSource bindSource = new BindSource("UNKNOWN", 0);
            UNKNOWN = bindSource;
            BindSource bindSource2 = new BindSource("CONNECT_LOCKED", 1);
            CONNECT_LOCKED = bindSource2;
            BindSource bindSource3 = new BindSource("CONNECTION_TRY_TO_REBIND", 2);
            CONNECTION_TRY_TO_REBIND = bindSource3;
            BindSource bindSource4 = new BindSource("INITIALIZE_FALLBACK", 3);
            INITIALIZE_FALLBACK = bindSource4;
            BindSource bindSource5 = new BindSource("PACKAGE_UPDATE_FINISHED", 4);
            PACKAGE_UPDATE_FINISHED = bindSource5;
            BindSource bindSource6 = new BindSource("RESTORE_SETTINGS_LIVE_FAILURE", 5);
            RESTORE_SETTINGS_LIVE_FAILURE = bindSource6;
            BindSource bindSource7 = new BindSource("RESTORE_SETTINGS_LIVE_SUCCESS", 6);
            RESTORE_SETTINGS_LIVE_SUCCESS = bindSource7;
            BindSource bindSource8 = new BindSource("RESTORE_SETTINGS_STATIC", 7);
            RESTORE_SETTINGS_STATIC = bindSource8;
            BindSource bindSource9 = new BindSource("SET_LIVE", 8);
            SET_LIVE = bindSource9;
            BindSource bindSource10 = new BindSource("SET_LIVE_TO_CLEAR", 9);
            SET_LIVE_TO_CLEAR = bindSource10;
            BindSource bindSource11 = new BindSource("SET_STATIC", 10);
            SET_STATIC = bindSource11;
            BindSource bindSource12 = new BindSource("SWITCH_WALLPAPER_FAILURE", 11);
            SWITCH_WALLPAPER_FAILURE = bindSource12;
            BindSource bindSource13 = new BindSource("SWITCH_WALLPAPER_SWITCH_USER", 12);
            BindSource bindSource14 = new BindSource("SWITCH_WALLPAPER_UNLOCK_USER", 13);
            SWITCH_WALLPAPER_UNLOCK_USER = bindSource14;
            $VALUES =
                    new BindSource[] {
                        bindSource,
                        bindSource2,
                        bindSource3,
                        bindSource4,
                        bindSource5,
                        bindSource6,
                        bindSource7,
                        bindSource8,
                        bindSource9,
                        bindSource10,
                        bindSource11,
                        bindSource12,
                        bindSource13,
                        bindSource14
                    };
        }

        public static BindSource valueOf(String str) {
            return (BindSource) Enum.valueOf(BindSource.class, str);
        }

        public static BindSource[] values() {
            return (BindSource[]) $VALUES.clone();
        }
    }

    public WallpaperData(int i, int i2) {
        this.name = "";
        this.mWallpaperDimAmount = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mUidToDimAmount = new SparseArray();
        this.callbacks = new RemoteCallbackList();
        this.cropHint = new Rect(0, 0, 0, 0);
        this.mSampleSize = 1.0f;
        this.mBindSource = BindSource.UNKNOWN;
        this.mWallpaperFiles = new SparseArray();
        this.mCropFiles = new SparseArray();
        this.mCropHints = new SparseArray();
        this.mOrientationWhenSet = -1;
        this.mSemWallpaperData = new SemWallpaperData();
        this.userId = i;
        WhichChecker.assertModeIsPresent(i2);
        this.mSemWallpaperData.mWhich = i2;
        this.mWhich = i2;
    }

    public WallpaperData(WallpaperData wallpaperData) {
        this.name = "";
        this.mWallpaperDimAmount = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mUidToDimAmount = new SparseArray();
        this.callbacks = new RemoteCallbackList();
        this.cropHint = new Rect(0, 0, 0, 0);
        this.mSampleSize = 1.0f;
        this.mBindSource = BindSource.UNKNOWN;
        this.mWallpaperFiles = new SparseArray();
        this.mCropFiles = new SparseArray();
        this.mCropHints = new SparseArray();
        this.mOrientationWhenSet = -1;
        this.mSemWallpaperData = new SemWallpaperData();
        this.userId = wallpaperData.userId;
        this.wallpaperComponent = wallpaperData.wallpaperComponent;
        this.mWhich = wallpaperData.mWhich;
        this.wallpaperId = wallpaperData.wallpaperId;
        this.cropHint.set(wallpaperData.cropHint);
        SparseArray sparseArray = wallpaperData.mCropHints;
        if (sparseArray != null) {
            this.mCropHints = sparseArray.clone();
        }
        this.allowBackup = wallpaperData.allowBackup;
        this.primaryColors = wallpaperData.primaryColors;
        this.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
        WallpaperManagerService.WallpaperConnection wallpaperConnection = wallpaperData.connection;
        this.connection = wallpaperConnection;
        if (wallpaperConnection != null) {
            wallpaperConnection.mWallpaper = this;
        }
        this.mSemWallpaperData = wallpaperData.mSemWallpaperData.m1237clone();
        WhichChecker.assertModeIsPresent(this.mWhich);
    }

    public static String defaultString(Object obj) {
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public final void cleanUp() {
        Slog.d("WallpaperData", "cleanUp");
        SemWallpaperData semWallpaperData = this.mSemWallpaperData;
        int i = semWallpaperData.mWpType;
        if (i == -1) {
            this.wallpaperId = -1;
            this.wallpaperComponent = null;
            semWallpaperData.mPrimarySemColors = null;
            semWallpaperData.mLandscapeColors = null;
            semWallpaperData.mDlsSemColors = null;
        }
        if (Rune.SUPPORT_VIDEO_WALLPAPER && i != 8) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "delete video thumbnail file. wpType=", "WallpaperData");
            File file = this.mSemWallpaperData.mVideoFirstFrameFile;
            if (file != null && file.exists()) {
                Slog.d(
                        "WallpaperData",
                        "delete video thumbnail file path : "
                                + this.mSemWallpaperData.mVideoFirstFrameFile.getPath());
                this.mSemWallpaperData.mVideoFirstFrameFile.delete();
                SemWallpaperData semWallpaperData2 = this.mSemWallpaperData;
                semWallpaperData2.mVideoFirstFrameFile = null;
                semWallpaperData2.mVideoPkgName = null;
                semWallpaperData2.mVideoFileName = null;
            }
        }
        if (i != 0) {
            this.name = "";
            Slog.d(
                    "WallpaperData",
                    "delete wallpaper and crop file. wpType=" + this.mSemWallpaperData.mWpType);
            File wallpaperFile = getWallpaperFile(0);
            if (wallpaperFile != null && wallpaperFile.exists()) {
                Slog.d("WallpaperData", "Delete wallpaper file: " + wallpaperFile.getPath());
                wallpaperFile.delete();
            }
            this.mWallpaperFiles.put(this.mWhich, wallpaperFile);
            getCropFile();
            if (getCropFile().exists()) {
                Slog.d("WallpaperData", "Delete crop file: " + getCropFile().getPath());
                getCropFile().delete();
            }
            this.mSemWallpaperData.mIsCopied = false;
        }
        if (i != 0 && i != 3 && i != 5 && i != 8 && i != 7) {
            this.mSemWallpaperData.mExternalParams = null;
        }
        if (i != 4) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "delete animated background file. wpType=", "WallpaperData");
            File file2 = this.mSemWallpaperData.mAnimatedBackground;
            if (file2 != null && file2.exists()) {
                Slog.d(
                        "WallpaperData",
                        "delete animated background file path : "
                                + this.mSemWallpaperData.mAnimatedBackground.getPath());
                this.mSemWallpaperData.mAnimatedBackground.delete();
                SemWallpaperData semWallpaperData3 = this.mSemWallpaperData;
                semWallpaperData3.mAnimatedBackground = null;
                semWallpaperData3.mAnimatedPkgName = null;
            }
        }
        if (i != 1) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "delete motion background file. wpType=", "WallpaperData");
            File file3 = this.mSemWallpaperData.mMotionBackground;
            if (file3 != null && file3.exists()) {
                Slog.d(
                        "WallpaperData",
                        "delete motion background file path : "
                                + this.mSemWallpaperData.mMotionBackground.getPath());
                this.mSemWallpaperData.mMotionBackground.delete();
                SemWallpaperData semWallpaperData4 = this.mSemWallpaperData;
                semWallpaperData4.mMotionBackground = null;
                semWallpaperData4.mMotionPkgName = null;
            }
        }
        this.primaryColors = null;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final WallpaperData m1039clone() {
        try {
            WallpaperData wallpaperData = (WallpaperData) super.clone();
            wallpaperData.cropHint = new Rect(this.cropHint);
            wallpaperData.mSemWallpaperData = this.mSemWallpaperData.m1237clone();
            return wallpaperData;
        } catch (CloneNotSupportedException e) {
            Slog.d("WallpaperData", "clone : e=" + e, e);
            return null;
        }
    }

    public final boolean cropExists() {
        return getCropFile().exists();
    }

    public final File getCropFile() {
        return getFile(this.mCropFiles, WallpaperUtils.getCropFileName(this.mWhich));
    }

    public final File getFile(SparseArray sparseArray, String str) {
        File file = (File) sparseArray.get(this.mWhich);
        if (file == null) {
            file =
                    new File(
                            WhichChecker.isLock(this.mWhich)
                                    ? WallpaperUtils.getWallpaperLockDir(this.userId)
                                    : Environment.getUserSystemDirectory(this.userId),
                            str);
            sparseArray.put(this.mWhich, file);
        }
        return file;
    }

    public final File getWallpaperFile(int i) {
        File file;
        if (i == 1) {
            file = this.mSemWallpaperData.mMotionBackground;
        } else if (i == 4) {
            file = this.mSemWallpaperData.mAnimatedBackground;
        } else if (i != 8) {
            file = getFile(this.mWallpaperFiles, WallpaperUtils.getFileName(this.mWhich));
        } else {
            file = this.mSemWallpaperData.mVideoFirstFrameFile;
        }
        Log.d("WallpaperData", "getWallpaperFile: file = " + file);
        return file;
    }

    public final void setCallingPackage(String str) {
        String str2;
        String str3;
        String str4;
        String[] split;
        if (TextUtils.isEmpty(str) || str.startsWith("null")) {
            long currentTimeMillis = System.currentTimeMillis();
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            SimpleDateFormat.getDateTimeInstance()
                                    .format(new Date(currentTimeMillis)),
                            ".");
            m.append(currentTimeMillis % 1000);
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(m.toString(), "\n");
            m2.append(SemWallpaperManagerService.getCallStackString());
            this.mSemWallpaperData.mLastClearCallstackWithNullPackage = m2.toString();
        }
        SemWallpaperData semWallpaperData = this.mSemWallpaperData;
        int i = semWallpaperData.mWpType;
        if (i == 0) {
            str2 = "image";
        } else if (i == 1) {
            str2 = "motion";
        } else if (i == 3) {
            str2 = "multiple";
        } else if (i == 4) {
            str2 = "animated";
        } else if (i == 5) {
            str2 = "gif";
        } else if (i == 7) {
            str2 = "live";
        } else if (i != 8) {
            str2 = "default";
            if (i == 1000
                    && (str4 = semWallpaperData.mUri) != null
                    && (split = str4.split("/")) != null
                    && split.length > 1) {
                str2 = split[1];
            }
        } else {
            str2 = "video";
        }
        SemWallpaperData semWallpaperData2 = this.mSemWallpaperData;
        int i2 = semWallpaperData2.mWpType;
        if (i2 == 0) {
            str3 = this.name;
        } else if (i2 == 1) {
            str3 = semWallpaperData2.mMotionPkgName;
        } else if (i2 != 4) {
            str3 = "";
            if (i2 == 7) {
                ComponentName componentName = this.wallpaperComponent;
                if (componentName != null) {
                    str3 = componentName.getPackageName();
                }
            } else if (i2 == 8) {
                str3 = semWallpaperData2.mVideoPkgName;
            }
        } else {
            str3 = semWallpaperData2.mAnimatedPkgName;
        }
        semWallpaperData.setWallpaperHistory(str, null, str2, str3);
    }

    public final void setWallpaperFile(File file) {
        SemWallpaperData semWallpaperData = this.mSemWallpaperData;
        int i = semWallpaperData.mWpType;
        if (i == 1) {
            semWallpaperData.mMotionBackground = file;
            return;
        }
        if (i == 4) {
            semWallpaperData.mAnimatedBackground = file;
            return;
        }
        if (i == 8) {
            semWallpaperData.mVideoFirstFrameFile = file;
            return;
        }
        this.mWallpaperFiles.put(this.mWhich, file);
        int i2 = this.mSemWallpaperData.mWhich;
        if (WhichChecker.isSystem(i2)
                && WhichChecker.isSubDisplay(i2)
                && file != null
                && "wallpaper_orig".equals(file.getName())) {
            SemWallpaperManagerService.putLog(
                    "\nUnexpected file assignment detected!\n"
                            + SemWallpaperManagerService.getCallStackString());
        }
    }

    public final boolean sourceExists() {
        return getWallpaperFile(0).exists();
    }

    public final String toString() {
        final StringBuilder sb = new StringBuilder(defaultString(this));
        sb.append(", id: ");
        sb.append(this.wallpaperId);
        sb.append(", which: ");
        sb.append(this.mWhich);
        sb.append(", file mod: ");
        sb.append(
                getWallpaperFile(this.mSemWallpaperData.mWpType) != null
                        ? Long.valueOf(
                                getWallpaperFile(this.mSemWallpaperData.mWpType).lastModified())
                        : "null");
        if (this.connection == null) {
            sb.append(", no connection");
        } else {
            sb.append(", info: ");
            sb.append(this.connection.mInfo);
            sb.append(", engine(s):");
            this.connection.forEachDisplayConnector(
                    new Consumer() { // from class:
                                     // com.android.server.wallpaper.WallpaperData$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            StringBuilder sb2 = sb;
                            WallpaperManagerService.DisplayConnector displayConnector =
                                    (WallpaperManagerService.DisplayConnector) obj;
                            if (displayConnector.mEngine == null) {
                                sb2.append(" null");
                            } else {
                                sb2.append(" ");
                                sb2.append(WallpaperData.defaultString(displayConnector.mEngine));
                            }
                        }
                    });
        }
        sb.append(", type = ");
        sb.append(this.mSemWallpaperData.mWpType);
        sb.append(", userId = ");
        sb.append(this.userId);
        sb.append(", uri= ");
        sb.append(this.mSemWallpaperData.mUri);
        sb.append(", allowBackup= ");
        sb.append(this.allowBackup);
        sb.append(", wallpaperComponent = ");
        sb.append(this.wallpaperComponent);
        sb.append(", nextWallpaperComponent = ");
        sb.append(this.nextWallpaperComponent);
        return sb.toString();
    }
}
