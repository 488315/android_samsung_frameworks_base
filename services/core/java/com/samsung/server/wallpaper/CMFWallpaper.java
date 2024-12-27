package com.samsung.server.wallpaper;

import android.app.SemWallpaperResourcesInfo;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Build;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.server.input.KeyboardMetricsCollector;
import com.samsung.android.wallpaper.Rune;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CMFWallpaper {
    public static HashMap sCmfSwitchMap;
    public final String mAlternativeCode;
    public Thread mColorCodePollingThread;
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public String mProductCode;
    public final SemWallpaperManagerService mService;
    public final AnonymousClass2 mSettingsObserver;
    public final SemWallpaperResourcesInfo mWallpaperResourcesInfo;
    public String mDeviceColor = "zk";
    public String mLegacyDeviceColor = "black";
    public int mLastColorCodePollingThreadCount = 60;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ProductFeatures {
        public static String getFeatureBasedColor(String str) {
            String str2;
            int i;
            String str3;
            String str4 = null;
            if (str != null && !str.isEmpty()) {
                long nanoTime = System.nanoTime();
                String str5 = "switchCmfByCscFeature: (" + nanoTime + ") ";
                String str6 = Rune.SUPPORT_CSC_REPLACE_WALLPAPER_CMF;
                if (TextUtils.isEmpty(str6) || TextUtils.isEmpty(str)) {
                    str2 = null;
                } else {
                    synchronized (str6) {
                        try {
                        } catch (Exception e) {
                            Log.e("CMFWallpaper", str5 + "init: [" + i + "] skipped: [" + str3 + "]: " + e.getMessage());
                        } finally {
                        }
                        if (CMFWallpaper.sCmfSwitchMap == null) {
                            Log.i("CMFWallpaper", str5 + "init: start: " + str6);
                            CMFWallpaper.sCmfSwitchMap = new HashMap();
                            String[] split = str6.split(",");
                            int length = split.length;
                            Log.i("CMFWallpaper", str5 + "init: replace Items: " + length);
                            i = 0;
                            while (i < length) {
                                str3 = split[i];
                                if (TextUtils.isEmpty(str3)) {
                                    throw new IllegalArgumentException("null switchItem");
                                }
                                String[] split2 = str3.split(":");
                                if (split2.length != 2) {
                                    throw new IllegalArgumentException("wrong format");
                                }
                                String str7 = split2[0];
                                String str8 = split2[1];
                                if (TextUtils.isEmpty(str7) || TextUtils.isEmpty(str8)) {
                                    throw new IllegalArgumentException("empty value included!!");
                                }
                                String str9 = (String) CMFWallpaper.sCmfSwitchMap.get(str7.toLowerCase());
                                if (str9 != null) {
                                    throw new IllegalArgumentException("duplicated from color!! key " + str7 + " has value already: " + str9);
                                }
                                Log.d("CMFWallpaper", str5 + "init: [" + i + "] put " + str7 + " -> " + str8);
                                CMFWallpaper.sCmfSwitchMap.put(str7.toLowerCase(), str8.toLowerCase());
                                i++;
                            }
                            Log.i("CMFWallpaper", str5 + "init: Done. (" + CMFWallpaper.sCmfSwitchMap.size() + "/" + length + ") took :" + CMFWallpaper.getPreciseMillisTimeString(System.nanoTime() - nanoTime));
                        }
                        str4 = (String) CMFWallpaper.sCmfSwitchMap.get(str.toLowerCase());
                        if (str4 != null) {
                            str2 = "switched " + str + " -> " + str4;
                        } else {
                            str2 = "no need to switch " + str;
                        }
                    }
                }
                if (str4 == null) {
                    str4 = str.toLowerCase();
                }
                Log.i("CMFWallpaper", str5 + "[DONE: " + str2 + "] took :" + CMFWallpaper.getPreciseMillisTimeString(System.nanoTime() - nanoTime));
                if (getProductInfo().contains("beyond") && !getProductInfo().contains("beyondx")) {
                    if ("zs".equals(str4)) {
                        return "zw";
                    }
                    if ("zr".equals(str4)) {
                        return "zi";
                    }
                }
            }
            return str4;
        }

        public static String getProductInfo() {
            return (SystemProperties.get("ro.build.flavor", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG).trim() + ":" + SystemProperties.get("ro.build.product", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG).trim() + ":" + SystemProperties.get("ro.product.model", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG).trim()).toLowerCase();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.server.wallpaper.CMFWallpaper$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.server.wallpaper.CMFWallpaper$2] */
    public CMFWallpaper(Context context, SemWallpaperManagerService semWallpaperManagerService, SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        ?? r0 = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.CMFWallpaper.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1013) {
                    return;
                }
                Settings.System.putString(CMFWallpaper.this.mContext.getContentResolver(), "cmf_color_code", (String) message.obj);
            }
        };
        this.mHandler = r0;
        this.mSettingsObserver = new ContentObserver(r0) { // from class: com.samsung.server.wallpaper.CMFWallpaper.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                String string = Settings.System.getString(CMFWallpaper.this.mContext.getContentResolver(), "cmf_color_code");
                if (TextUtils.isEmpty(CMFWallpaper.this.mDeviceColor) || CMFWallpaper.this.mDeviceColor.equals(string)) {
                    return;
                }
                Log.d("CMFWallpaper", "setDeviceColor:" + string);
                CMFWallpaper cMFWallpaper = CMFWallpaper.this;
                cMFWallpaper.getClass();
                cMFWallpaper.mDeviceColor = ProductFeatures.getFeatureBasedColor(string);
                cMFWallpaper.setLegacyDeviceColor(string);
                CMFWallpaper.this.mService.mDefaultWallpaper.mHandler.sendEmptyMessage(1008);
            }
        };
        Log.d("CMFWallpaper", "CMFWallpaper");
        this.mContext = context;
        this.mService = semWallpaperManagerService;
        this.mWallpaperResourcesInfo = semWallpaperResourcesInfo;
        String str = null;
        try {
            str = FileUtils.readTextFile(new File("/efs/imei/alt_cmf.dat"), 0, null);
            if (str != null) {
                str = str.toLowerCase();
            }
        } catch (IOException unused) {
        }
        Log.i("CMFWallpaper", "readAlternativeColorCodeFromEfs : " + str);
        this.mAlternativeCode = str;
        String str2 = Build.TYPE;
        if ("eng".equals(str2) || "userdebug".equals(str2)) {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("cmf_color_code"), false, this.mSettingsObserver);
        }
    }

    public static String getPreciseMillisTimeString(long j) {
        String valueOf = String.valueOf(Math.abs(j) % 1000000);
        return "" + (j / 1000000) + "." + "000000".substring(valueOf.length()) + valueOf + "ms";
    }

    public final String getDeviceColor() {
        Log.d("CMFWallpaper", "deviceColor:" + this.mDeviceColor);
        return this.mDeviceColor;
    }

    public final String getProperColorCode(String str, String str2) {
        if (this.mWallpaperResourcesInfo.isKnownColorCode(str) || !this.mWallpaperResourcesInfo.isKnownColorCode(str2)) {
            return str;
        }
        Log.d("CMFWallpaper", "getProperColorCode : use alt color. " + str2);
        return str2;
    }

    public final void setLegacyDeviceColor(String str) {
        String str2;
        String featureBasedColor = ProductFeatures.getFeatureBasedColor(str);
        Log.d("CMFWallpaper", "setLegacyDeviceColor cmfColorCode = " + str + ", featureBasedColor = " + featureBasedColor);
        if (featureBasedColor == null || featureBasedColor.isEmpty()) {
            return;
        }
        switch (featureBasedColor) {
            case "cw":
            case "zw":
                str2 = "white";
                break;
            case "di":
            case "ed":
            case "zi":
                str2 = "pink";
                break;
            case "gb":
            case "lb":
            case "mb":
            case "zb":
                str2 = "blue";
                break;
            case "mg":
            case "zg":
                str2 = "green";
                break;
            case "ms":
            case "zs":
                str2 = "silver";
                break;
            case "za":
            case "zr":
            case "zv":
                str2 = "orchid";
                break;
            case "zd":
                str2 = "gold";
                break;
            case "zn":
                str2 = "brown";
                break;
            case "zp":
                str2 = "purple";
                break;
            case "zy":
                str2 = "yellow";
                break;
            default:
                str2 = "black";
                break;
        }
        this.mLegacyDeviceColor = str2;
    }
}
