package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.provider.Settings;
import android.text.TextUtils;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

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
                    obj = obj2;
                    mutable = mutable22;
                    mutable = mutable3;
                }

                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    synchronized (obj) {
                        mutable.value = bool;
                        mutable.value = Boolean.TRUE;
                        obj.notify();
                    }
                }
            });
            synchronized (obj2) {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    long j = 10000 + jCurrentTimeMillis;
                    while (!((Boolean) mutable3.value).booleanValue() && jCurrentTimeMillis < j) {
                        try {
                            obj2.wait(j - jCurrentTimeMillis);
                            jCurrentTimeMillis = System.currentTimeMillis();
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setIsSupportedWifi7(Context context) {
        boolean z;
        int i = SemWifiUtils.$r8$clinit;
        String countryCode = ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).getCountryCode();
        if (!TextUtils.isEmpty(countryCode)) {
            String wifi7DisabledCountry = ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE)).getWifi7DisabledCountry();
            if (TextUtils.isEmpty(wifi7DisabledCountry) || !wifi7DisabledCountry.contains(countryCode)) {
                z = Settings.Secure.getInt(context.getContentResolver(), "sec_wifi_7_mode_enabled", 1) == 1;
            }
        }
        this.isSupportedWifi7 = z;
    }
}
