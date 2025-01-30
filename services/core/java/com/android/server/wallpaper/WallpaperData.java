package com.android.server.wallpaper;

import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.FileObserver;
import android.os.RemoteCallbackList;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.SemWallpaperData;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class WallpaperData implements Cloneable {
    public boolean allowBackup;
    public RemoteCallbackList callbacks;
    public WallpaperManagerService.WallpaperConnection connection;
    public File cropFile;
    public Rect cropHint;
    public boolean fromForegroundApp;
    public boolean imageWallpaperPending;
    public long lastDiedTime;
    public boolean mIsColorExtractedFromDim;
    public SemWallpaperData mSemWallpaperData;
    public boolean mSystemWasBoth;
    public SparseArray mUidToDimAmount;
    public float mWallpaperDimAmount;
    public int mWhich;
    public String name;
    public ComponentName nextWallpaperComponent;
    public WallpaperColors primaryColors;
    public IWallpaperManagerCallback setComplete;
    public int userId;
    public ComponentName wallpaperComponent;
    public File wallpaperFile;
    public int wallpaperId;
    public FileObserver wallpaperObserver;
    public boolean wallpaperUpdating;

    public WallpaperData(int i, int i2) {
        this(i, WallpaperUtils.getWallpaperDir(i), WhichChecker.isPhone(i2) ? WhichChecker.isLock(i2) ? "wallpaper_lock_orig" : "wallpaper_orig" : null, WhichChecker.isPhone(i2) ? WhichChecker.isLock(i2) ? "wallpaper_lock" : "wallpaper" : null);
        WhichChecker.assertModeIsPresent(i2);
        this.mWhich = i2;
    }

    public WallpaperData(int i, File file, String str, String str2) {
        this.name = "";
        this.mWallpaperDimAmount = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mUidToDimAmount = new SparseArray();
        this.callbacks = new RemoteCallbackList();
        this.cropHint = new Rect(0, 0, 0, 0);
        this.mSemWallpaperData = new SemWallpaperData();
        this.userId = i;
        if (str != null) {
            this.wallpaperFile = new File(file, str);
            if (str2 != null) {
                this.cropFile = new File(file, str2);
            }
            this.mSemWallpaperData.setWpType(0);
        }
    }

    public WallpaperData(WallpaperData wallpaperData) {
        this.name = "";
        this.mWallpaperDimAmount = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mUidToDimAmount = new SparseArray();
        this.callbacks = new RemoteCallbackList();
        this.cropHint = new Rect(0, 0, 0, 0);
        this.mSemWallpaperData = new SemWallpaperData();
        this.userId = wallpaperData.userId;
        this.wallpaperFile = wallpaperData.wallpaperFile;
        this.cropFile = wallpaperData.cropFile;
        this.wallpaperComponent = wallpaperData.wallpaperComponent;
        this.mWhich = wallpaperData.mWhich;
        this.wallpaperId = wallpaperData.wallpaperId;
        this.cropHint.set(wallpaperData.cropHint);
        this.allowBackup = wallpaperData.allowBackup;
        this.primaryColors = wallpaperData.primaryColors;
        this.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
        WallpaperManagerService.WallpaperConnection wallpaperConnection = wallpaperData.connection;
        this.connection = wallpaperConnection;
        if (wallpaperConnection != null) {
            wallpaperConnection.mWallpaper = this;
        }
        this.mSemWallpaperData = wallpaperData.mSemWallpaperData.m14847clone();
        WhichChecker.assertModeIsPresent(this.mWhich);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder(defaultString(this));
        sb.append(", id: ");
        sb.append(this.wallpaperId);
        sb.append(", which: ");
        sb.append(this.mWhich);
        sb.append(", file mod: ");
        File file = this.wallpaperFile;
        sb.append(file != null ? Long.valueOf(file.lastModified()) : "null");
        if (this.connection == null) {
            sb.append(", no connection");
        } else {
            sb.append(", info: ");
            sb.append(this.connection.mInfo);
            sb.append(", engine(s):");
            this.connection.forEachDisplayConnector(new Consumer() { // from class: com.android.server.wallpaper.WallpaperData$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WallpaperData.lambda$toString$0(sb, (WallpaperManagerService.DisplayConnector) obj);
                }
            });
        }
        sb.append(", type = ");
        sb.append(this.mSemWallpaperData.getWpType());
        sb.append(", userId = ");
        sb.append(this.userId);
        sb.append(", uri= ");
        sb.append(this.mSemWallpaperData.getUri());
        sb.append(", allowBackup= ");
        sb.append(this.allowBackup);
        sb.append(", wallpaperComponent = ");
        sb.append(this.wallpaperComponent);
        sb.append(", nextWallpaperComponent = ");
        sb.append(this.nextWallpaperComponent);
        return sb.toString();
    }

    public static /* synthetic */ void lambda$toString$0(StringBuilder sb, WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            sb.append(" ");
            sb.append(defaultString(displayConnector.mEngine));
        } else {
            sb.append(" null");
        }
    }

    public static String defaultString(Object obj) {
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public boolean cropExists() {
        File file = this.cropFile;
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public boolean sourceExists() {
        File file = this.wallpaperFile;
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public String semGetLastCallingPackage(boolean z) {
        return this.mSemWallpaperData.getLastCallingPackage(z);
    }

    public int getWallpaperType() {
        return this.mSemWallpaperData.getWpType();
    }

    public int getWhich() {
        return this.mWhich;
    }

    public void setWallpaperComponent(ComponentName componentName) {
        this.wallpaperComponent = componentName;
    }

    public ComponentName getWallpaperComponent() {
        return this.wallpaperComponent;
    }

    public void setNextWallpaperComponent(ComponentName componentName) {
        this.nextWallpaperComponent = componentName;
    }

    public ComponentName getNextWallpaperComponent() {
        return this.nextWallpaperComponent;
    }

    public void setDefaultWallpaperData(File file, String str, String str2) {
        this.mSemWallpaperData.setWpType(0);
        this.wallpaperFile = new File(file, str);
        this.cropFile = new File(file, str2);
    }

    public void setWallpaperFile(File file) {
        int wpType = this.mSemWallpaperData.getWpType();
        if (wpType == 1) {
            this.mSemWallpaperData.setMotionBackground(file);
            return;
        }
        if (wpType == 4) {
            this.mSemWallpaperData.setAnimatedBackground(file);
            return;
        }
        if (wpType == 8) {
            this.mSemWallpaperData.setVideoFirstFrameFile(file);
            return;
        }
        this.wallpaperFile = file;
        int which = this.mSemWallpaperData.getWhich();
        if (WhichChecker.isSystem(which) && WhichChecker.isSubDisplay(which) && file != null && "wallpaper_orig".equals(file.getName())) {
            SemWallpaperManagerService.putLog("\nUnexpected file assignment detected!\n" + SemWallpaperManagerService.getCallStackString());
        }
    }

    public File getWallpaperFile() {
        int wpType = this.mSemWallpaperData.getWpType();
        if (wpType == -1 || wpType == 0) {
            return this.wallpaperFile;
        }
        if (wpType == 1) {
            return this.mSemWallpaperData.getMotionBackground();
        }
        if (wpType == 4) {
            return this.mSemWallpaperData.getAnimatedBackground();
        }
        if (wpType != 8) {
            return null;
        }
        return this.mSemWallpaperData.getVideoFirstFrameFile();
    }

    public int getWallpaperId() {
        return this.wallpaperId;
    }

    public void setWallpaperCropFile(File file) {
        this.cropFile = file;
    }

    public File getWallpaperCropFile() {
        return this.cropFile;
    }

    public SemWallpaperData getSemWallpaperData() {
        return this.mSemWallpaperData;
    }

    public void setImageWallpaperPending(boolean z) {
        this.imageWallpaperPending = z;
    }

    public void setWhich(int i) {
        this.mSemWallpaperData.setWhich(i);
    }

    public void setWhichPending(int i) {
        this.mWhich = i;
    }

    public int getOrientation() {
        return this.mSemWallpaperData.getOrientation();
    }

    public boolean isSameWallpaperData(WallpaperData wallpaperData) {
        if (this.mSemWallpaperData.getWidth() == wallpaperData.mSemWallpaperData.getWidth() && this.mSemWallpaperData.getHeight() == wallpaperData.mSemWallpaperData.getHeight() && this.name.equals(wallpaperData.name)) {
            ComponentName componentName = this.wallpaperComponent;
            if (componentName == null) {
                if (new ComponentName("com.android.systemui", "com.android.systemui.ImageWallpaper").equals(wallpaperData.wallpaperComponent) || wallpaperData.wallpaperComponent == null) {
                    Slog.d("WallpaperData", "Same Image Wallpaper!");
                    return true;
                }
            } else if (componentName.equals(wallpaperData.wallpaperComponent)) {
                Slog.d("WallpaperData", "Same Live wallpaper!");
                return true;
            }
        }
        Slog.d("WallpaperData", "Different wallpaper");
        return false;
    }

    public void setCallingPackage(String str) {
        if (TextUtils.isEmpty(str) || str.startsWith("null")) {
            long currentTimeMillis = System.currentTimeMillis();
            this.mSemWallpaperData.setLastClearCallstackWithNullPackage((SimpleDateFormat.getDateTimeInstance().format(new Date(currentTimeMillis)) + "." + (currentTimeMillis % 1000)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + SemWallpaperManagerService.getCallStackString());
        }
        SemWallpaperData semWallpaperData = this.mSemWallpaperData;
        semWallpaperData.setWallpaperHistory(str, null, semWallpaperData.getWallpaperTypeString(), getPackageName());
    }

    public void cleanUp() {
        Slog.d("WallpaperData", "cleanUp");
        int wpType = this.mSemWallpaperData.getWpType();
        if (Rune.SUPPORT_VIDEO_WALLPAPER && wpType != 8) {
            Slog.d("WallpaperData", "delete video thumbnail file. wpType=" + wpType);
            if (this.mSemWallpaperData.getVideoFirstFrameFile() != null && this.mSemWallpaperData.getVideoFirstFrameFile().exists()) {
                Slog.d("WallpaperData", "delete video thumbnail file path : " + this.mSemWallpaperData.getVideoFirstFrameFile().getPath());
                this.mSemWallpaperData.getVideoFirstFrameFile().delete();
                this.mSemWallpaperData.setVideoFirstFrameFile(null);
                this.mSemWallpaperData.setVideoPkgName(null);
                this.mSemWallpaperData.setVideoFileName(null);
            }
        }
        if (wpType != 0) {
            Slog.d("WallpaperData", "delete wallpaper and crop file. wpType=" + this.mSemWallpaperData.getWpType());
            File file = this.wallpaperFile;
            if (file != null && file.exists()) {
                Slog.d("WallpaperData", "delete wallpaper file path : " + this.wallpaperFile.getPath());
                this.wallpaperFile.delete();
            }
            this.wallpaperFile = null;
            File file2 = this.cropFile;
            if (file2 != null && file2.exists()) {
                Slog.d("WallpaperData", "delete crop file path : " + this.cropFile.getPath());
                this.cropFile.delete();
            }
            this.cropFile = null;
            this.mSemWallpaperData.setIsCopied(false);
        }
        if (wpType != 0 && wpType != 3 && wpType != 5 && wpType != 8 && wpType != 7) {
            this.mSemWallpaperData.setExternalParams(null);
        }
        if (wpType != 4) {
            Slog.d("WallpaperData", "delete animated background file. wpType=" + wpType);
            if (this.mSemWallpaperData.getAnimatedBackground() != null && this.mSemWallpaperData.getAnimatedBackground().exists()) {
                Slog.d("WallpaperData", "delete animated background file path : " + this.mSemWallpaperData.getAnimatedBackground().getPath());
                this.mSemWallpaperData.getAnimatedBackground().delete();
                this.mSemWallpaperData.setAnimatedBackground(null);
                this.mSemWallpaperData.setAnimatedPkgName(null);
            }
        }
        if (wpType != 1) {
            Slog.d("WallpaperData", "delete motion background file. wpType=" + wpType);
            if (this.mSemWallpaperData.getMotionBackground() != null && this.mSemWallpaperData.getMotionBackground().exists()) {
                Slog.d("WallpaperData", "delete motion background file path : " + this.mSemWallpaperData.getMotionBackground().getPath());
                this.mSemWallpaperData.getMotionBackground().delete();
                this.mSemWallpaperData.setMotionBackground(null);
                this.mSemWallpaperData.setMotionPkgName(null);
            }
        }
        if (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER && WhichChecker.isLock(this.mSemWallpaperData.getWhich()) && wpType != 7) {
            this.wallpaperComponent = null;
        }
        this.primaryColors = null;
    }

    public String getPackageName() {
        int wpType = this.mSemWallpaperData.getWpType();
        if (wpType == 0) {
            return this.name;
        }
        if (wpType == 1) {
            return this.mSemWallpaperData.getMotionPkgName();
        }
        if (wpType == 4) {
            return this.mSemWallpaperData.getAnimatedPkgName();
        }
        if (wpType != 7) {
            return wpType != 8 ? "" : this.mSemWallpaperData.getVideoPkgName();
        }
        ComponentName componentName = this.wallpaperComponent;
        return componentName != null ? componentName.getPackageName() : "";
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public WallpaperData m12812clone() {
        try {
            WallpaperData wallpaperData = (WallpaperData) super.clone();
            wallpaperData.cropHint = new Rect(this.cropHint);
            wallpaperData.mSemWallpaperData = this.mSemWallpaperData.m14847clone();
            return wallpaperData;
        } catch (CloneNotSupportedException e) {
            Slog.d("WallpaperData", "clone : e=" + e, e);
            return null;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\t\t userId = " + this.userId);
        printWriter.println("\t\t wallpaperComponent = " + this.wallpaperComponent);
        printWriter.println("\t\t nextWallpaperComponent =" + this.nextWallpaperComponent);
        printWriter.println("\t\t mWhich = " + this.mWhich);
        try {
            printWriter.println("\t\t mTimeCreated = " + this.mSemWallpaperData.getCreationTime());
            printWriter.println("\t\t mSemWallpaperData.getWhich() = " + this.mSemWallpaperData.getWhich());
            printWriter.println("\t\t mWpType = " + this.mSemWallpaperData.getWpType());
            printWriter.println("\t\t cropHint = " + this.cropHint);
            printWriter.println("\t\t mUri = " + this.mSemWallpaperData.getUri());
            printWriter.println("\t\t wallpaperFile = " + this.wallpaperFile);
            printWriter.println("\t\t getWallpaperFile() = " + getWallpaperFile());
            if (this.mSemWallpaperData.getWpType() == 1) {
                printWriter.println("\t\t mMotionPkgName = " + this.mSemWallpaperData.getMotionPkgName());
            }
            if (this.mSemWallpaperData.getWpType() == 4) {
                printWriter.println("\t\t mAnimatedPkgName = " + this.mSemWallpaperData.getAnimatedPkgName());
            }
            if (this.mSemWallpaperData.getWpType() == 8) {
                printWriter.println("\t\t mVideoFilePath = " + this.mSemWallpaperData.getVideoFilePath());
                printWriter.println("\t\t mVideoPkgName = " + this.mSemWallpaperData.getVideoPkgName());
                printWriter.println("\t\t mVideoFileName = " + this.mSemWallpaperData.getVideoFileName());
                printWriter.println("\t\t mVideoDefaultHasBeenUsed = " + this.mSemWallpaperData.getVideoDefaultHasBeenUsed());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
