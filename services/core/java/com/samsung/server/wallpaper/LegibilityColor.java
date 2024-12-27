package com.samsung.server.wallpaper;

import android.app.SemWallpaperColors;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.DefaultWallpaper;
import java.io.File;
import java.io.FileNotFoundException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LegibilityColor {
    public final WallpaperManagerService.SemCallback mCallback;
    public ContentResolver mContentResolver;
    public final Context mContext;
    public final SemWallpaperManagerService mService;
    public final SparseArray mColorExtractors = new SparseArray();
    public boolean mAllowScreenRotateSystem = false;
    public boolean mAllowScreenRotateLock = false;
    public final AnonymousClass1 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.LegibilityColor.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            LegibilityColor legibilityColor = LegibilityColor.this;
            if (message.what != 1015) {
                return;
            }
            String[] strArr = {"need_dark_statusbar", "need_dark_font", "need_dark_navigationbar"};
            int[] iArr = (int[]) message.obj;
            int i = message.arg1;
            Log.d("LegibilityColor", "userId: " + i);
            String str = "setWhiteBgSettings";
            boolean z = false;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                try {
                    int i3 = iArr[i2];
                    int intForUser = Settings.System.getIntForUser(legibilityColor.mContext.getContentResolver(), strArr[i2], 0, i);
                    str = str + ", area[" + i2 + "] oldVal = " + intForUser + " newVal = " + i3;
                    if (intForUser != i3) {
                        Settings.System.putIntForUser(legibilityColor.mContext.getContentResolver(), strArr[i2], i3, i);
                        Log.d("LegibilityColor", "set " + strArr[i2] + " :" + i3);
                        z = true;
                    }
                } catch (Exception e) {
                    Log.addLogString("LegibilityColor", "failed to get/put " + e);
                }
            }
            Log.addLogString("LegibilityColor", str);
            if (z) {
                DefaultWallpaper.AnonymousClass1 anonymousClass1 = legibilityColor.mService.mDefaultWallpaper.mHandler;
                anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1007));
                Log.d("DefaultWallpaper", "send ChangedIntent complete");
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            LegibilityColor legibilityColor = LegibilityColor.this;
            legibilityColor.mAllowScreenRotateSystem = legibilityColor.allowScreenRotate(1);
            LegibilityColor legibilityColor2 = LegibilityColor.this;
            legibilityColor2.mAllowScreenRotateLock = legibilityColor2.allowScreenRotate(2);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.server.wallpaper.LegibilityColor$1] */
    public LegibilityColor(Context context, WallpaperManagerService.SemCallback semCallback, SemWallpaperManagerService semWallpaperManagerService) {
        Log.d("LegibilityColor", "LegibilityColor");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
    }

    public static String getWallpaperColorPath(int i, int i2, boolean z) {
        String str;
        File file = new File(Environment.getUserSystemDirectory(i), "wallpaper_colors_info");
        if (!file.exists() && !file.mkdir()) {
            Log.e("LegibilityColor", "getWallpaperColorPath failed to mkdir");
        }
        String path = file.getPath();
        int type = WhichChecker.getType(i2);
        String str2 = null;
        if (type == 1) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(path, "/home");
        } else if (type == 2) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(path, "/lock");
        } else {
            Log.e("LegibilityColor", "unhandle type " + i2);
            str = null;
        }
        if (z) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_landscape");
        }
        int mode = WhichChecker.getMode(i2);
        if (mode == 0 || mode == 4) {
            str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_phone.xml");
        } else if (mode == 8) {
            str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_dex.xml");
        } else if (mode == 16) {
            str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_sub.xml");
        } else if (mode == 32) {
            str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_virtual.xml");
        } else {
            Log.e("LegibilityColor", "unhandle mode " + i2);
        }
        Log.d("LegibilityColor", "getWallpaperColorPath, path = " + str2);
        return str2;
    }

    public final boolean allowScreenRotate(int i) {
        int type = WhichChecker.getType(i);
        boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "accelerometer_rotation", 0, this.mContext.getUserId()) == 0;
        if (z) {
            return false;
        }
        if (Rune.WPAPER_SUPPORT_ROTATABLE_WALLPAPER && !WhichChecker.isSubDisplay(i)) {
            Log.i("LegibilityColor", "allowScreenRotate, allow rotate on tablet or dual main : " + i);
            return true;
        }
        if (type != 1 && type != 2) {
            Log.e("LegibilityColor", "allowScreenRoatate, unhandle type " + i);
        }
        return z;
    }

    public final void extractColor(int i, boolean z) {
        try {
            getColorExtractor(i, z).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } catch (Exception e) {
            Log.d("LegibilityColor", "extractcolor: Error. " + e.getMessage());
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.samsung.server.wallpaper.LegibilityColor$2, java.lang.Object] */
    public final synchronized AnonymousClass2 getColorExtractor(final int i, final boolean z) {
        ?? r1;
        try {
            int i2 = (z ? 100 : 0) + i;
            AsyncTask asyncTask = (AsyncTask) this.mColorExtractors.get(i2);
            if (asyncTask != null && !asyncTask.isCancelled()) {
                Log.d("LegibilityColor", "getColorExtractor cancel");
                asyncTask.cancel(true);
                this.mColorExtractors.remove(i2);
            }
            Log.i("LegibilityColor", "makeColorExtractor: " + Integer.toHexString(i));
            r1 = new AsyncTask() { // from class: com.samsung.server.wallpaper.LegibilityColor.2
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    Log.d("LegibilityColor", "extractColor start which = " + i + ", landscape = " + z);
                    SemWallpaperColors[] semWallpaperColorsArr = z ? (!Rune.WPAPER_SUPPORT_ROTATABLE_WALLPAPER || WhichChecker.isSubDisplay(i)) ? new SemWallpaperColors[]{LegibilityColor.this.mCallback.calcSemWallpaperColors(i, 90), LegibilityColor.this.mCallback.calcSemWallpaperColors(i, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6)} : new SemWallpaperColors[]{LegibilityColor.this.mCallback.calcSemWallpaperColors(i, 90)} : new SemWallpaperColors[]{LegibilityColor.this.mCallback.calcSemWallpaperColors(i, 0)};
                    if (isCancelled()) {
                        Log.w("LegibilityColor", "doInBackground: this task is cancelled, which = " + i);
                        return null;
                    }
                    Log.d("LegibilityColor", "extractColor end which = " + i);
                    if (semWallpaperColorsArr[0] == null) {
                        Log.e("LegibilityColor", "colors == null, return");
                        return null;
                    }
                    WallpaperManagerService.SemCallback semCallback = LegibilityColor.this.mCallback;
                    int i3 = i;
                    boolean z2 = z;
                    synchronized (WallpaperManagerService.this.mLock) {
                        try {
                            WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                            WallpaperData peekWallpaperDataLocked = wallpaperManagerService.peekWallpaperDataLocked(wallpaperManagerService.getCurrentUserId(), i3);
                            if (peekWallpaperDataLocked == null) {
                                Log.addLogString("WallpaperManagerService", "saveSemWallpaperColors, wallpaper == null");
                            } else {
                                SemWallpaperData semWallpaperData = peekWallpaperDataLocked.mSemWallpaperData;
                                if (z2) {
                                    semWallpaperData.mLandscapeColors = semWallpaperColorsArr;
                                } else {
                                    semWallpaperData.mPrimarySemColors = semWallpaperColorsArr[0];
                                    LegibilityColor legibilityColor = WallpaperManagerService.this.mSemService.mLegibilityColor;
                                    String wallpaperColorPath = LegibilityColor.getWallpaperColorPath(peekWallpaperDataLocked.userId, semWallpaperData.mWhich, z2);
                                    Log.d("WallpaperManagerService", "saveSemWallpaperColors " + i3 + ", " + wallpaperColorPath);
                                    semWallpaperColorsArr[0].save(wallpaperColorPath);
                                }
                            }
                        } finally {
                        }
                    }
                    LegibilityColor.this.mCallback.notifySemWallpaperColors(i);
                    Log.d("LegibilityColor", "notifyColor end which = " + i);
                    return null;
                }

                @Override // android.os.AsyncTask
                public final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                }
            };
            this.mColorExtractors.put(i2, r1);
        } catch (Throwable th) {
            throw th;
        }
        return r1;
    }

    public final void initSemWallpaperColors(int i, SemWallpaperData semWallpaperData) {
        if (semWallpaperData == null) {
            Log.d("LegibilityColor", "initSemWallpaperColors wallpaper == null");
            return;
        }
        int i2 = semWallpaperData.mWhich;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "initSemWallpaperColors which = ", ", version = ");
        m.append(SemWallpaperColors.getDeviceVersion());
        Log.d("LegibilityColor", m.toString());
        String wallpaperColorPath = getWallpaperColorPath(i, i2, false);
        if (wallpaperColorPath == null) {
            return;
        }
        Log.addLogString("LegibilityColor", "initSemWallpaperColors:".concat(wallpaperColorPath));
        try {
            String stringFromFile = SemWallpaperManagerService.getStringFromFile(wallpaperColorPath);
            if (SemWallpaperColors.getXmlVersion(stringFromFile) != SemWallpaperColors.getDeviceVersion()) {
                Log.d("LegibilityColor", "fota, calSemWallpaperColors");
                extractColor(i2, false);
            } else {
                SemWallpaperColors fromXml = SemWallpaperColors.fromXml(stringFromFile);
                if (fromXml != null && fromXml.getSeedColors() != null && fromXml.getSeedColors().length > 0) {
                    semWallpaperData.mPrimarySemColors = fromXml;
                    this.mCallback.notifySemWallpaperColors(i2);
                }
                Log.d("LegibilityColor", "initSemWallpaperColors: SemWallpaperColor or its seed color is null. Extract color again!");
                extractColor(i2, false);
            }
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                Log.d("LegibilityColor", "fota, calcSemWallpaperColors");
                extractColor(i2, false);
            } else {
                Log.e("LegibilityColor", "exception " + e);
            }
        }
        Log.d("LegibilityColor", "initSemWallpaperColors done");
    }
}
