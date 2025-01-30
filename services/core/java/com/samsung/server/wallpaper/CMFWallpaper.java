package com.samsung.server.wallpaper;

import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
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
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.wallpaper.Rune;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class CMFWallpaper {
    public static HashMap sCmfSwitchMap;
    public String mAlternativeCode;
    public Thread mColorCodePollingThread;
    public final Context mContext;
    public final Handler mHandler;
    public String mProductCode;
    public final SemWallpaperManagerService mService;
    public final ContentObserver mSettingsObserver;
    public final SemWallpaperResourcesInfo mWallpaperResourcesInfo;
    public String mDeviceColor = "zk";
    public String mLegacyDeviceColor = "black";
    public int mLastColorCodePollingThreadCount = 60;

    public CMFWallpaper(Context context, SemWallpaperManagerService semWallpaperManagerService, SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.CMFWallpaper.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1013) {
                    return;
                }
                Settings.System.putString(CMFWallpaper.this.mContext.getContentResolver(), "cmf_color_code", (String) message.obj);
            }
        };
        this.mHandler = handler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.samsung.server.wallpaper.CMFWallpaper.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                String cmfColorCodeFromSettingsDB = CMFWallpaper.this.getCmfColorCodeFromSettingsDB();
                if (TextUtils.isEmpty(CMFWallpaper.this.mDeviceColor) || CMFWallpaper.this.mDeviceColor.equals(cmfColorCodeFromSettingsDB)) {
                    return;
                }
                Log.m129d("CMFWallpaper", "setDeviceColor:" + cmfColorCodeFromSettingsDB);
                CMFWallpaper.this.setDeviceColor(cmfColorCodeFromSettingsDB);
                CMFWallpaper.this.mService.mDefaultWallpaper.updateDefaultWallpaper();
            }
        };
        this.mSettingsObserver = contentObserver;
        Log.m129d("CMFWallpaper", "CMFWallpaper");
        this.mContext = context;
        this.mService = semWallpaperManagerService;
        this.mWallpaperResourcesInfo = semWallpaperResourcesInfo;
        this.mAlternativeCode = readAlternativeColorCodeFromEfs();
        String str = Build.TYPE;
        if ("eng".equals(str) || "userdebug".equals(str)) {
            context.getContentResolver().registerContentObserver(Settings.System.getUriFor("cmf_color_code"), false, contentObserver);
        }
    }

    public final void setCMFColorCode(String str) {
        Message obtainMessage = this.mHandler.obtainMessage(1013);
        obtainMessage.obj = str;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void initDeviceColor() {
        String defaultColorCode = getDefaultColorCode();
        this.mDeviceColor = ProductFeatures.getFeatureBasedColor(defaultColorCode);
        setLegacyDeviceColor(defaultColorCode);
    }

    public final void setDeviceColor(String str) {
        this.mDeviceColor = ProductFeatures.getFeatureBasedColor(str);
        setLegacyDeviceColor(str);
    }

    public String getLegacyDeviceColor() {
        Log.m129d("CMFWallpaper", "legacyDeviceColor = " + this.mLegacyDeviceColor + " , color code = " + this.mDeviceColor);
        return this.mLegacyDeviceColor;
    }

    public String getDeviceColor() {
        Log.m129d("CMFWallpaper", "deviceColor:" + this.mDeviceColor);
        return this.mDeviceColor;
    }

    public final String getDefaultColorCode() {
        String cmfColorCodeFromSettingsDB = getCmfColorCodeFromSettingsDB();
        Log.m133v("CMFWallpaper", "getDefaultDeviceColor : " + cmfColorCodeFromSettingsDB);
        return TextUtils.isEmpty(cmfColorCodeFromSettingsDB) ? getProperColorCode(getDeviceColorCodeFromSystemProperty(this.mContext), this.mAlternativeCode) : cmfColorCodeFromSettingsDB;
    }

    public final String getProperColorCode(String str, String str2) {
        if (this.mWallpaperResourcesInfo.isKnownColorCode(str) || !this.mWallpaperResourcesInfo.isKnownColorCode(str2)) {
            return str;
        }
        Log.m129d("CMFWallpaper", "getProperColorCode : use alt color. " + str2);
        return str2;
    }

    public final String readAlternativeColorCodeFromEfs() {
        String str = null;
        try {
            str = FileUtils.readTextFile(new File("/efs/imei/alt_cmf.dat"), 0, null);
            if (str != null) {
                str = str.toLowerCase();
            }
        } catch (IOException unused) {
        }
        Log.m132i("CMFWallpaper", "readAlternativeColorCodeFromEfs : " + str);
        return str;
    }

    public final String getCmfColorCodeFromSettingsDB() {
        return Settings.System.getString(this.mContext.getContentResolver(), "cmf_color_code");
    }

    public final String extractColorCodeFromProductCode(String str) {
        if (str == null || str.length() < 10) {
            return null;
        }
        return str.substring(8, 10).toLowerCase();
    }

    public final String getDeviceColorCodeFromSystemProperty(Context context) {
        String str = SystemProperties.get("ril.product_code");
        Log.m133v("CMFWallpaper", "rilProductCode = " + str);
        String extractColorCodeFromProductCode = extractColorCodeFromProductCode(str);
        if (extractColorCodeFromProductCode != null) {
            Log.m129d("CMFWallpaper", "rilProductCode='" + str + "', colorCode=" + extractColorCodeFromProductCode);
            String properColorCode = getProperColorCode(extractColorCodeFromProductCode, this.mAlternativeCode);
            setCMFColorCode(properColorCode);
            return properColorCode;
        }
        if (this.mColorCodePollingThread != null) {
            return null;
        }
        Thread thread = new Thread() { // from class: com.samsung.server.wallpaper.CMFWallpaper.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.m133v("CMFWallpaper", "ColorCodePollingThread run()");
                CMFWallpaper.this.mProductCode = SystemProperties.get("ril.product_code");
                while (TextUtils.isEmpty(CMFWallpaper.this.mProductCode) && CMFWallpaper.this.mLastColorCodePollingThreadCount > 0) {
                    Log.m133v("CMFWallpaper", "ColorCodePollingThread sleep(1000) count=" + CMFWallpaper.this.mLastColorCodePollingThreadCount);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException unused) {
                        Log.m130e("CMFWallpaper", "InterruptedException occurred");
                    }
                    CMFWallpaper cMFWallpaper = CMFWallpaper.this;
                    cMFWallpaper.mLastColorCodePollingThreadCount--;
                    CMFWallpaper.this.mProductCode = SystemProperties.get("ril.product_code");
                }
                CMFWallpaper cMFWallpaper2 = CMFWallpaper.this;
                String extractColorCodeFromProductCode2 = cMFWallpaper2.extractColorCodeFromProductCode(cMFWallpaper2.mProductCode);
                if (extractColorCodeFromProductCode2 != null) {
                    Log.m133v("CMFWallpaper", "Color code (" + extractColorCodeFromProductCode2 + ") retrieved!!");
                    CMFWallpaper cMFWallpaper3 = CMFWallpaper.this;
                    String properColorCode2 = cMFWallpaper3.getProperColorCode(extractColorCodeFromProductCode2, cMFWallpaper3.mAlternativeCode);
                    CMFWallpaper.this.setCMFColorCode(properColorCode2);
                    CMFWallpaper.this.setDeviceColor(properColorCode2);
                    if (!CMFWallpaper.this.isOperatorWallpaper()) {
                        CMFWallpaper.this.mService.mDefaultWallpaper.updateDefaultWallpaper();
                    }
                }
                CMFWallpaper.this.mColorCodePollingThread = null;
            }
        };
        this.mColorCodePollingThread = thread;
        thread.setName("ColorCodePollingThread");
        this.mColorCodePollingThread.start();
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void setLegacyDeviceColor(String str) {
        String featureBasedColor = ProductFeatures.getFeatureBasedColor(str);
        Log.m129d("CMFWallpaper", "setLegacyDeviceColor cmfColorCode = " + str + ", featureBasedColor = " + featureBasedColor);
        if (featureBasedColor == null || featureBasedColor.isEmpty()) {
            return;
        }
        char c = 65535;
        switch (featureBasedColor.hashCode()) {
            case 3176:
                if (featureBasedColor.equals("ck")) {
                    c = 0;
                    break;
                }
                break;
            case 3188:
                if (featureBasedColor.equals("cw")) {
                    c = 1;
                    break;
                }
                break;
            case 3205:
                if (featureBasedColor.equals("di")) {
                    c = 2;
                    break;
                }
                break;
            case 3231:
                if (featureBasedColor.equals("ed")) {
                    c = 3;
                    break;
                }
                break;
            case 3291:
                if (featureBasedColor.equals("gb")) {
                    c = 4;
                    break;
                }
                break;
            case 3446:
                if (featureBasedColor.equals("lb")) {
                    c = 5;
                    break;
                }
                break;
            case 3477:
                if (featureBasedColor.equals("mb")) {
                    c = 6;
                    break;
                }
                break;
            case 3482:
                if (featureBasedColor.equals("mg")) {
                    c = 7;
                    break;
                }
                break;
            case 3494:
                if (featureBasedColor.equals("ms")) {
                    c = '\b';
                    break;
                }
                break;
            case 3879:
                if (featureBasedColor.equals("za")) {
                    c = '\t';
                    break;
                }
                break;
            case 3880:
                if (featureBasedColor.equals("zb")) {
                    c = '\n';
                    break;
                }
                break;
            case 3882:
                if (featureBasedColor.equals("zd")) {
                    c = 11;
                    break;
                }
                break;
            case 3885:
                if (featureBasedColor.equals("zg")) {
                    c = '\f';
                    break;
                }
                break;
            case 3887:
                if (featureBasedColor.equals("zi")) {
                    c = '\r';
                    break;
                }
                break;
            case 3889:
                if (featureBasedColor.equals("zk")) {
                    c = 14;
                    break;
                }
                break;
            case 3892:
                if (featureBasedColor.equals("zn")) {
                    c = 15;
                    break;
                }
                break;
            case 3894:
                if (featureBasedColor.equals("zp")) {
                    c = 16;
                    break;
                }
                break;
            case 3896:
                if (featureBasedColor.equals("zr")) {
                    c = 17;
                    break;
                }
                break;
            case 3897:
                if (featureBasedColor.equals("zs")) {
                    c = 18;
                    break;
                }
                break;
            case 3900:
                if (featureBasedColor.equals("zv")) {
                    c = 19;
                    break;
                }
                break;
            case 3901:
                if (featureBasedColor.equals("zw")) {
                    c = 20;
                    break;
                }
                break;
            case 3903:
                if (featureBasedColor.equals("zy")) {
                    c = 21;
                    break;
                }
                break;
        }
        String str2 = "black";
        switch (c) {
            case 1:
            case 20:
                str2 = "white";
                break;
            case 2:
            case 3:
            case '\r':
                str2 = "pink";
                break;
            case 4:
            case 5:
            case 6:
            case '\n':
                str2 = "blue";
                break;
            case 7:
            case '\f':
                str2 = "green";
                break;
            case '\b':
            case 18:
                str2 = "silver";
                break;
            case '\t':
            case 17:
            case 19:
                str2 = "orchid";
                break;
            case 11:
                str2 = "gold";
                break;
            case 15:
                str2 = "brown";
                break;
            case 16:
                str2 = "purple";
                break;
            case 21:
                str2 = "yellow";
                break;
        }
        this.mLegacyDeviceColor = str2;
    }

    public abstract class ProductFeatures {
        public static String getProductInfo() {
            return (SystemProperties.get("ro.build.flavor", "None").trim() + XmlUtils.STRING_ARRAY_SEPARATOR + SystemProperties.get("ro.build.product", "None").trim() + XmlUtils.STRING_ARRAY_SEPARATOR + SystemProperties.get("ro.product.model", "None").trim()).toLowerCase();
        }

        public static boolean isBeyondX() {
            return getProductInfo().contains("beyondx");
        }

        public static boolean isBeyond() {
            return getProductInfo().contains("beyond") && !isBeyondX();
        }

        public static String getFeatureBasedColor(String str) {
            if (str == null || str.isEmpty()) {
                return null;
            }
            String switchCmfByCscFeature = CMFWallpaper.switchCmfByCscFeature(str);
            return isBeyond() ? "zs".equals(switchCmfByCscFeature) ? "zw" : "zr".equals(switchCmfByCscFeature) ? "zi" : switchCmfByCscFeature : switchCmfByCscFeature;
        }
    }

    public static String switchCmfByCscFeature(String str) {
        String str2;
        String str3;
        long nanoTime = System.nanoTime();
        String str4 = "switchCmfByCscFeature: (" + nanoTime + ") ";
        if (TextUtils.isEmpty(Rune.SUPPORT_CSC_REPLACE_WALLPAPER_CMF) || TextUtils.isEmpty(str)) {
            str2 = null;
            str3 = null;
        } else {
            synchronized (Rune.SUPPORT_CSC_REPLACE_WALLPAPER_CMF) {
                if (sCmfSwitchMap == null) {
                    Log.m132i("CMFWallpaper", str4 + "init: start: " + Rune.SUPPORT_CSC_REPLACE_WALLPAPER_CMF);
                    sCmfSwitchMap = new HashMap();
                    String[] split = Rune.SUPPORT_CSC_REPLACE_WALLPAPER_CMF.split(",");
                    int length = split.length;
                    Log.m132i("CMFWallpaper", str4 + "init: replace Items: " + length);
                    for (int i = 0; i < length; i++) {
                        String str5 = split[i];
                        try {
                        } catch (Exception e) {
                            Log.m130e("CMFWallpaper", str4 + "init: [" + i + "] skipped: [" + str5 + "]: " + e.getMessage());
                        }
                        if (TextUtils.isEmpty(str5)) {
                            throw new IllegalArgumentException("null switchItem");
                        }
                        String[] split2 = str5.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                        if (split2.length != 2) {
                            throw new IllegalArgumentException("wrong format");
                        }
                        String str6 = split2[0];
                        String str7 = split2[1];
                        if (TextUtils.isEmpty(str6) || TextUtils.isEmpty(str7)) {
                            throw new IllegalArgumentException("empty value included!!");
                        }
                        String str8 = (String) sCmfSwitchMap.get(str6.toLowerCase());
                        if (str8 != null) {
                            throw new IllegalArgumentException("duplicated from color!! key " + str6 + " has value already: " + str8);
                        }
                        Log.m129d("CMFWallpaper", str4 + "init: [" + i + "] put " + str6 + " -> " + str7);
                        sCmfSwitchMap.put(str6.toLowerCase(), str7.toLowerCase());
                    }
                    Log.m132i("CMFWallpaper", str4 + "init: Done. (" + sCmfSwitchMap.size() + "/" + length + ") took :" + getPreciseMillisTimeString(System.nanoTime() - nanoTime));
                }
                str2 = (String) sCmfSwitchMap.get(str.toLowerCase());
                str3 = str2 != null ? "switched " + str + " -> " + str2 : "no need to switch " + str;
            }
        }
        if (str2 == null && str != null) {
            str2 = str.toLowerCase();
        }
        Log.m132i("CMFWallpaper", str4 + "[DONE: " + str3 + "] took :" + getPreciseMillisTimeString(System.nanoTime() - nanoTime));
        return str2;
    }

    public static String getPreciseMillisTimeString(long j) {
        String valueOf = String.valueOf(Math.abs(j) % 1000000);
        return "" + (j / 1000000) + "." + "000000".substring(valueOf.length()) + valueOf + "ms";
    }

    public final boolean isOperatorWallpaper() {
        String deviceColor = getDeviceColor();
        return (WallpaperManager.getCSCWallpaperFile(this.mContext, 1, null, deviceColor) == null && WallpaperManager.getCSCWallpaperFile(this.mContext, 2, null, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(this.mContext, 1, deviceColor) == null && WallpaperManager.getOMCWallpaperFile(this.mContext, 2, deviceColor) == null) ? false : true;
    }
}
