package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.provider.Settings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SemWifiEntryFlags {
    public static int isBlockedUnSecureWifiAutoJoin = -1;
    public static int isShowBandSummaryOn = -1;
    public static int isWepAllowed = -1;
    public static int isWifiDeveloperOptionOn = -1;
    public static int isWpa3OweSupported = -1;
    public static int isWpa3SaeSupported = -1;
    public static int isWpa3SuiteBSupported = -1;
    public boolean has6EStandard;
    public boolean isCarrierNetwork;
    public boolean isOpenRoamingNetwork;
    public boolean isSamsungMobileHotspot;
    public boolean isSupportedWifi7;
    public boolean networkScoringUiEnabled;
    public int networkType;
    public PasspointConfiguration passpointConfiguration;
    public SemWifiConfiguration semConfig;
    public int wifiStandard;
    public int staCount = -1;
    public final Map qosScoredNetworkCache = new HashMap();

    public static boolean isShowBandInfoOn(Context context) {
        if (isShowBandSummaryOn == -1) {
            int i = SemWifiUtils.$r8$clinit;
            isShowBandSummaryOn = Settings.Global.getInt(context.getContentResolver(), "sec_wifi_developer_show_band", 0) == 1 ? 1 : 0;
        }
        return isShowBandSummaryOn == 1;
    }

    public static boolean isWepAllowed(Context context) {
        if (isWepAllowed == -1) {
            int i = SemWifiUtils.$r8$clinit;
            HandlerThread handlerThread = new HandlerThread("SemWifiUtils.isWepAllowed");
            handlerThread.start();
            Executor handlerExecutor = new HandlerExecutor(new Handler(handlerThread.getLooper()));
            Object obj = new Object();
            SemWifiUtils.Mutable mutable = new SemWifiUtils.Mutable(Boolean.FALSE);
            SemWifiUtils.Mutable mutable2 = new SemWifiUtils.Mutable(Boolean.TRUE);
            ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).queryWepAllowed(handlerExecutor, new Consumer() { // from class: com.samsung.android.wifitrackerlib.SemWifiUtils.1
                public final /* synthetic */ Mutable val$isQuerySucceeded;
                public final /* synthetic */ Mutable val$isWepAllowed;
                public final /* synthetic */ Object val$mLock;

                public AnonymousClass1(Object obj2, Mutable mutable22, Mutable mutable3) {
                    r1 = obj2;
                    r2 = mutable22;
                    r3 = mutable3;
                }

                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    synchronized (r1) {
                        r2.value = bool;
                        r3.value = Boolean.TRUE;
                        r1.notify();
                    }
                }
            });
            synchronized (obj2) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = 10000 + currentTimeMillis;
                    while (!((Boolean) mutable3.value).booleanValue() && currentTimeMillis < j) {
                        try {
                            obj2.wait(j - currentTimeMillis);
                            currentTimeMillis = System.currentTimeMillis();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            isWepAllowed = ((Boolean) mutable22.value).booleanValue() ? 1 : 0;
        }
        return isWepAllowed == 1;
    }

    public static boolean isWifiDeveloperOptionOn(Context context) {
        if (isWifiDeveloperOptionOn == -1) {
            int i = SemWifiUtils.$r8$clinit;
            isWifiDeveloperOptionOn = Settings.Global.getInt(context.getContentResolver(), "sem_wifi_developer_option_visible", 0) == 1 ? 1 : 0;
        }
        return isWifiDeveloperOptionOn == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0037, code lost:
    
        if (android.provider.Settings.Secure.getInt(r4.getContentResolver(), "sec_wifi_7_mode_enabled", 1) == 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setIsSupportedWifi7(android.content.Context r4) {
        /*
            r3 = this;
            int r0 = com.samsung.android.wifitrackerlib.SemWifiUtils.$r8$clinit
            java.lang.String r0 = "wifi"
            java.lang.Object r0 = r4.getSystemService(r0)
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0
            java.lang.String r0 = r0.getCountryCode()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L3a
            java.lang.String r1 = "sem_wifi"
            java.lang.Object r1 = r4.getSystemService(r1)
            com.samsung.android.wifi.SemWifiManager r1 = (com.samsung.android.wifi.SemWifiManager) r1
            java.lang.String r1 = r1.getWifi7DisabledCountry()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L2c
            boolean r0 = r1.contains(r0)
            if (r0 != 0) goto L3a
        L2c:
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r0 = "sec_wifi_7_mode_enabled"
            r1 = 1
            int r4 = android.provider.Settings.Secure.getInt(r4, r0, r1)
            if (r4 != r1) goto L3a
            goto L3b
        L3a:
            r1 = 0
        L3b:
            r3.isSupportedWifi7 = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wifitrackerlib.SemWifiEntryFlags.setIsSupportedWifi7(android.content.Context):void");
    }
}
