package com.android.systemui.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.media.MediaRouter;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.settings.ImsSettings;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceState {
    public static final int CAPTURED_BLUR_THRESHOLD_WIDTH = 720;
    private static final int ESIM_SWITCHING_SLOT_INDEX_UNKNOWN = 1;
    private static final float FINGERPRINT_HEIGHT_BASE_PERCENT = 0.22f;
    public static final int IMS_ALL_SLOT_DISABLED = 0;
    private static final String LEBO_SETTING_NAME = "lelink_cast_on";
    private static final String SYSTEM_PROPERTY_KEY_TEST_MODE = "dexmaker.share_classloader";
    private static final String TAG = "DeviceState";
    public static final String VOWIFI_MENU_ENABLE = "vowifi_menu_enable";
    public static Point sDisplaySize = null;
    private static int sInDisplayFingerprintHeight = 0;
    private static int sInDisplayFingerprintImageSize = 0;
    private static int sInDisplayFingerprintMarginBottom = 0;
    private static boolean sLoadedSensorValue = false;
    private static int sOldScreenHeightDp = 0;
    private static int sOldScreenLayout = 0;
    private static int sOldScreenWidthDp = 0;
    private static String sSemSensorAreaHeight = "4";
    private static String sSemSensorImageSize = "10.80";
    private static String sSemSensorMarginBottom = "13.77";
    private static Point sSizePoint = new Point(0, 0);
    private static final int sPhoneCount = TelephonyManager.getDefault().getPhoneCount();
    private static final int ESIM_SLOT = getESimSwitchingSlotIndex();
    private static final boolean HW_SUPPORT_ESIM = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_EMBEDDED_SIM", false);
    private static final boolean SUPPORT_ESIM_SWITCHING = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH", "").toLowerCase().contains("tsds");
    private static final boolean IS_ALREADY_BOOTED = "1".equals(SystemProperties.get("sys.boot_completed"));
    private static int ROTATION_0 = 0;
    private static int ROTATION_90 = 1;
    private static int ROTATION_180 = 2;
    private static int ROTATION_270 = 3;
    private static int deviceDensity = 0;
    private static int initialDisplaySizeFactor = 0;
    private static int currentDisplaySizeFactor = 0;
    private static int initialDisplayDensity = 0;
    private static int proportionalDensity = 0;
    private static int proportionalPixel = 0;

    public static int getActiveSimCount(Context context) {
        int i = 0;
        for (int i2 = 0; i2 < sPhoneCount; i2++) {
            String mSimSystemProperty = getMSimSystemProperty("gsm.sim.state", i2, "NOT_READY");
            if (("READY".equals(mSimSystemProperty) || "LOADED".equals(mSimSystemProperty)) && getSimSettingState(context, i2) != 0) {
                i++;
            }
        }
        return i;
    }

    public static int getDeviceResolutionPixelSize(Context context, int i) {
        int i2 = context.getResources().getConfiguration().densityDpi;
        if (deviceDensity != i2) {
            deviceDensity = i2;
            DisplayInfo displayInfo = new DisplayInfo();
            Display display = context.getDisplay();
            Point point = new Point();
            context.getDisplay().getDisplayInfo(displayInfo);
            SemWindowManager.getInstance().getInitialDisplaySize(point);
            int i3 = point.x;
            int i4 = point.y;
            if (i3 >= i4) {
                i3 = i4;
            }
            initialDisplaySizeFactor = i3;
            initialDisplayDensity = SemWindowManager.getInstance().getInitialDensity();
            display.getRealSize(point);
            int i5 = point.x;
            int i6 = point.y;
            if (i5 >= i6) {
                i5 = i6;
            }
            currentDisplaySizeFactor = i5;
        }
        int i7 = proportionalDensity;
        int i8 = initialDisplayDensity;
        if (i7 == i8) {
            proportionalPixel = i;
        }
        proportionalPixel = (i * i7) / i8;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "getDeviceResolutionPixelSize - currentDensity = ", " deviceDensity = ");
        m.append(deviceDensity);
        m.append(" initialDisplaySizeFactor = ");
        m.append(initialDisplaySizeFactor);
        m.append(" currentDisplaySizeFactor = ");
        m.append(currentDisplaySizeFactor);
        m.append(" initialDisplayDensity = ");
        m.append(initialDisplayDensity);
        m.append(" proportionalDensity = ");
        m.append(proportionalDensity);
        m.append(" proportionalPixel = ");
        RecyclerView$$ExternalSyntheticOutline0.m(proportionalPixel, TAG, m);
        return proportionalPixel;
    }

    public static int getDisplayHeight(Context context) {
        boolean updateScreenElements = updateScreenElements(context);
        boolean z = context.getResources().getConfiguration().orientation == 1;
        Point point = sSizePoint;
        int i = point.x;
        int i2 = point.y;
        int max = z ? Math.max(i, i2) : Math.min(i, i2);
        if (updateScreenElements) {
            Log.d(TAG, "getDisplayHeight portrait? " + z + "  displayHeight= " + max);
        }
        return max;
    }

    public static int getDisplayWidth(Context context) {
        boolean updateScreenElements = updateScreenElements(context);
        boolean z = context.getResources().getConfiguration().orientation == 1;
        Point point = sSizePoint;
        int i = point.x;
        int i2 = point.y;
        int min = z ? Math.min(i, i2) : Math.max(i, i2);
        if (updateScreenElements) {
            Log.d(TAG, "getDisplayWidth portrait? " + z + "  displayWidth= " + min);
        }
        return min;
    }

    private static int getESimSwitchingSlotIndex() {
        try {
            if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH").length() <= 4) {
                return 1;
            }
            return Integer.parseInt(r0.substring(4)) - 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static int getInDisplayFingerprintHeight() {
        return sInDisplayFingerprintHeight;
    }

    public static int getInDisplayFingerprintImageSize() {
        return sInDisplayFingerprintImageSize;
    }

    public static int getInDisplayFingerprintMarginBottom() {
        return sInDisplayFingerprintMarginBottom;
    }

    public static int getLoadedSimCount(Context context) {
        int i = 0;
        for (int i2 = 0; i2 < sPhoneCount; i2++) {
            if ("LOADED".equals(getMSimSystemProperty("gsm.sim.state", i2, "NOT_READY")) && getSimSettingState(context, i2) != 0) {
                i++;
            }
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:
    
        if (r1 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getMSimSystemProperty(java.lang.String r1, int r2, java.lang.String r3) {
        /*
            java.lang.String r1 = android.os.SystemProperties.get(r1)
            if (r1 == 0) goto L1c
            int r0 = r1.length()
            if (r0 <= 0) goto L1c
            java.lang.String r0 = ","
            java.lang.String[] r1 = r1.split(r0)
            if (r2 < 0) goto L1c
            int r0 = r1.length
            if (r2 >= r0) goto L1c
            r1 = r1[r2]
            if (r1 == 0) goto L1c
            goto L1d
        L1c:
            r1 = 0
        L1d:
            if (r1 != 0) goto L20
            goto L21
        L20:
            r3 = r1
        L21:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.DeviceState.getMSimSystemProperty(java.lang.String, int, java.lang.String):java.lang.String");
    }

    public static String getNetworkOperatorNumeric(int i) {
        return getMSimSystemProperty("gsm.operator.numeric", i, "");
    }

    public static int getNumberOfSim(Context context) {
        List<SubscriptionInfo> activeSubscriptionInfoList = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null) {
            return activeSubscriptionInfoList.size();
        }
        return 0;
    }

    public static String getOperatorNumeric(int i) {
        return getMSimSystemProperty("gsm.sim.operator.numeric", i, "");
    }

    public static int getPrimarySimSlot() {
        return SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
    }

    public static int getReadySimCount() {
        int i = 0;
        for (int i2 = 0; i2 < sPhoneCount; i2++) {
            String mSimSystemProperty = getMSimSystemProperty("gsm.sim.state", i2, "NOT_READY");
            if ("READY".equals(mSimSystemProperty) || "LOADED".equals(mSimSystemProperty)) {
                i++;
            }
        }
        return i;
    }

    public static int getRotation(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i : ROTATION_270 : ROTATION_180 : ROTATION_90 : ROTATION_0;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getConfiguration().windowConfiguration.getBounds().height();
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getConfiguration().windowConfiguration.getBounds().width();
    }

    public static int getSimSettingState(Context context, int i) {
        return i == 0 ? Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON, 1) : Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON, 1);
    }

    public static int getSimState(Context context, int i) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return 0;
        }
        int simState = telephonyManager.getSimState(i);
        if (LsRune.SECURITY_SIM_PERSO_LOCK && simState == 4) {
            return "PERSO_LOCKED".equals(getMSimSystemProperty("gsm.sim.state", i, "NOT_READY")) ? 12 : 0;
        }
        return simState;
    }

    public static int getVoWifiEnableState(Context context) {
        return Settings.System.getInt(context.getContentResolver(), VOWIFI_MENU_ENABLE, 0);
    }

    public static boolean isAlreadyBooted() {
        return IS_ALREADY_BOOTED;
    }

    public static boolean isAppInstalled(Context context, String str) {
        if (str == null) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            Log.i(TAG, "Installed - ".concat(str));
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "NOT Installed - ".concat(str));
            return false;
        }
    }

    public static synchronized boolean isCapturedBlurAllowed() {
        synchronized (DeviceState.class) {
            try {
                if (sDisplaySize == null) {
                    sDisplaySize = new Point();
                    SemWindowManager.getInstance().getInitialDisplaySize(sDisplaySize);
                }
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public static boolean isCenterDisplayCutOut(Context context) {
        boolean z = false;
        try {
            Resources resources = context.getResources();
            String str = "config_mainBuiltInDisplayCutout";
            if (!(LsRune.SECURITY_SUB_DISPLAY_LOCK ? ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened : true)) {
                str = "config_subBuiltInDisplayCutout";
            }
            int identifier = resources.getIdentifier(str, "string", "android");
            String string = identifier > 0 ? resources.getString(identifier) : null;
            if (string != null && !TextUtils.isEmpty(string) && !string.endsWith("@left")) {
                if (!string.endsWith("@right")) {
                    z = true;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Can not update isCenterDisplayCutOut. " + e.toString());
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isCenterDisplayCutOut: ", TAG, z);
        return z;
    }

    public static boolean isClearSideViewCoverType(int i) {
        return i == 15;
    }

    public static boolean isCoverUIType(int i) {
        if (i == 1 || i == 3 || i == 6 || i == 8) {
            return true;
        }
        switch (i) {
        }
        return true;
    }

    public static boolean isCoverUiWithWallpaper(int i) {
        return i == 17;
    }

    public static boolean isDataAllowed(Context context) {
        SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) context.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        if (activeSubscriptionInfo != null) {
            int simSlotIndex = activeSubscriptionInfo.getSimSlotIndex();
            Log.d(TAG, "Restriction in Settings Mobile Data On");
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
            try {
                Log.d(TAG, "isDataAllowedFromSimSlot(slotId)");
                return enterpriseDeviceManager.getPhoneRestrictionPolicy().isDataAllowedFromSimSlot(simSlotIndex);
            } catch (SecurityException e) {
                Log.w(TAG, "SecurityException: " + e);
            }
        }
        return true;
    }

    public static boolean isESIM(Context context, int i) {
        for (UiccCardInfo uiccCardInfo : ((TelephonyManager) context.getSystemService("phone")).getUiccCardsInfo()) {
            Iterator<UiccPortInfo> it = uiccCardInfo.getPorts().iterator();
            while (it.hasNext()) {
                if (it.next().getLogicalSlotIndex() == i && uiccCardInfo.isEuicc()) {
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "isEuicc() :", " : true", TAG);
                    return true;
                }
            }
        }
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "isESIM() :", " : false", TAG);
        return false;
    }

    public static boolean isFotaUpdate(Context context) {
        String string = Prefs.get(context).getString("FingerprintVersion", "unknown");
        String string2 = context.getSharedPreferences(context.getPackageName(), 0).getString("CSCVersion", "unknown");
        String string3 = context.getSharedPreferences(context.getPackageName(), 0).getString("SalesCode", "unknown");
        String str = SystemProperties.get("ro.build.fingerprint", "unknown");
        String str2 = SystemProperties.get("ril.official_cscver", "unknown");
        String str3 = SystemProperties.get("ro.csc.sales_code", "unknown");
        if (string.equals(str) && string2.equals(str2) && string3.equals(str3)) {
            return false;
        }
        Log.d(TAG, "isFotaUpdate!!");
        Prefs.putString(context, "FingerprintVersion", str);
        Prefs.putString(context, "CSCVersion", str2);
        Prefs.putString(context, "SalesCode", str3);
        return true;
    }

    public static boolean isInDisplayFpSensorPositionHigh() {
        Point realSize = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getRealSize();
        return ((float) sInDisplayFingerprintHeight) / ((float) Math.max(realSize.y, realSize.x)) > FINGERPRINT_HEIGHT_BASE_PERCENT;
    }

    public static boolean isNoSimState(Context context) {
        int i = sPhoneCount;
        for (int i2 = 0; i2 < i; i2++) {
            String mSimSystemProperty = getMSimSystemProperty("gsm.sim.state", i2, "NOT_READY");
            if (isESIM(context, i2) && "NOT_READY".equals(mSimSystemProperty)) {
                mSimSystemProperty = "ABSENT";
            }
            if ("UNKNOWN".equals(mSimSystemProperty)) {
                mSimSystemProperty = "ABSENT";
            }
            if (!"ABSENT".equals(mSimSystemProperty)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOpenTheme(Context context) {
        return ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getActiveThemePackage() != null;
    }

    public static boolean isShopDemo(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
    }

    public static boolean isSimCardInserted(int i) {
        return Integer.parseInt(i == 1 ? SystemProperties.get("ril.ICC_TYPE1", "0") : SystemProperties.get("ril.ICC_TYPE0", "0")) != 0;
    }

    public static boolean isSimReady() {
        int simState = TelephonyManager.getDefault().getSimState();
        Log.d(TAG, " isSimReady ? mutilSim ? " + DeviceType.isMultiSimSupported() + " readySimCount = " + getReadySimCount() + " SimState =" + simState);
        if (DeviceType.isMultiSimSupported() && getReadySimCount() == 0) {
            return false;
        }
        return DeviceType.isMultiSimSupported() || simState == 5;
    }

    public static boolean isSmartViewDisplayWithFitToAspectRatio(Context context) {
        if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            return isSmartViewFitToActiveDisplay();
        }
        SemWifiDisplayStatus semGetWifiDisplayStatus = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
        boolean z = semGetWifiDisplayStatus != null && semGetWifiDisplayStatus.getActiveDisplayState() == 2;
        MediaRouter.RouteInfo selectedRoute = ((MediaRouter) context.getSystemService("media_router")).getSelectedRoute(4);
        return (z || ((4 & selectedRoute.getSupportedTypes()) != 0 && selectedRoute.semGetDeviceAddress() == null && selectedRoute.semGetStatusCode() == 6 && (selectedRoute.getPresentationDisplay() != null || (selectedRoute.getDescription() != null && selectedRoute.getDescription().toString().contains("Audio")))) || (Settings.Global.getInt(context.getContentResolver(), LEBO_SETTING_NAME, 0) == 1)) && isSmartViewFitToActiveDisplay();
    }

    public static boolean isSmartViewFitToActiveDisplay() {
        return ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFitToActiveDisplay;
    }

    public static boolean isSubDisplay(Context context) {
        return context.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public static boolean isSubInfoReversed(Context context) {
        List<SubscriptionInfo> activeSubscriptionInfoList = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null || activeSubscriptionInfoList.size() != 2) {
            return false;
        }
        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isEmbedded()) {
                i++;
            }
        }
        if (i == 0) {
            return false;
        }
        if (i == 1) {
            return (activeSubscriptionInfoList.get(0).getSimSlotIndex() == 1 && !activeSubscriptionInfoList.get(0).isEmbedded()) || (activeSubscriptionInfoList.get(1).getSimSlotIndex() == 1 && !activeSubscriptionInfoList.get(1).isEmbedded());
        }
        if (i == 2) {
            return (activeSubscriptionInfoList.get(0).getSubscriptionId() > activeSubscriptionInfoList.get(1).getSubscriptionId() && activeSubscriptionInfoList.get(0).getSimSlotIndex() < activeSubscriptionInfoList.get(1).getSimSlotIndex()) || (activeSubscriptionInfoList.get(0).getSubscriptionId() < activeSubscriptionInfoList.get(1).getSubscriptionId() && activeSubscriptionInfoList.get(0).getSimSlotIndex() > activeSubscriptionInfoList.get(1).getSimSlotIndex());
        }
        return false;
    }

    public static boolean isTablet() {
        return DeviceType.isTablet();
    }

    public static boolean isTelephonyIdle(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
        boolean isInCall = telecomManager != null ? true ^ telecomManager.isInCall() : true;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isTelephonyIdle() - ", TAG, isInCall);
        return isInCall;
    }

    public static boolean isTesting() {
        return "true".equals(System.getProperty(SYSTEM_PROPERTY_KEY_TEST_MODE));
    }

    public static boolean isVoiceCapable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager != null && (telephonyManager.isVoiceCapable() || SemCscFeature.getInstance().getString("CscFeature_IMS_ConfigMdmnType").equalsIgnoreCase(ImsSettings.MDMN.SOFTPHONE));
    }

    private static void readFingerprintSensor() {
        if (sLoadedSensorValue) {
            return;
        }
        File file = new File("/sys/class/fingerprint/fingerprint/position");
        if (!file.exists()) {
            Log.w(TAG, "readFingerprintSensor : No file for sensor pos");
            return;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[(int) file.length()];
                        if (fileInputStream2.read(bArr) > 0) {
                            fileInputStream2.close();
                            String[] split = new String(bArr, StandardCharsets.UTF_8).split(",");
                            sSemSensorMarginBottom = split[0];
                            sSemSensorAreaHeight = split[3];
                            sSemSensorImageSize = split[7];
                            sLoadedSensorValue = true;
                        } else {
                            fileInputStream = fileInputStream2;
                        }
                    } catch (Exception e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        Log.e(TAG, "readFingerprintSensor : failure to read sensor info : ", e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e2) {
                                Log.e(TAG, "readFingerprintSensor : failed to close file", e2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (Exception e4) {
            Log.e(TAG, "readFingerprintSensor : failed to close file", e4);
        }
    }

    public static void resetDeviceDensity() {
        deviceDensity = 0;
    }

    public static void setInDisplayFingerprintSensorPosition(DisplayMetrics displayMetrics) {
        readFingerprintSensor();
        float applyDimension = TypedValue.applyDimension(5, Float.parseFloat(sSemSensorImageSize), displayMetrics);
        int applyDimension2 = (int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorMarginBottom), displayMetrics);
        int i = (int) applyDimension;
        sInDisplayFingerprintHeight = (i / 2) + (((int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorAreaHeight), displayMetrics)) / 2) + applyDimension2;
        sInDisplayFingerprintImageSize = i;
        sInDisplayFingerprintMarginBottom = applyDimension2;
    }

    public static void setLandscapeDefaultRotation() {
        ROTATION_0 = 1;
        ROTATION_90 = 2;
        ROTATION_180 = 3;
        ROTATION_270 = 0;
    }

    public static void setTestMode(boolean z) {
        System.setProperty(SYSTEM_PROPERTY_KEY_TEST_MODE, z ? "true" : "false");
    }

    public static boolean shouldEnableKeyguardScreenRotation(Context context) {
        return SystemProperties.getBoolean("lockscreen.rot_override", false) || context.getResources().getBoolean(R.bool.config_enableLockScreenRotation) || DeviceType.isTablet() || (!DeviceType.isSEPLiteDevice(context) && SystemProperties.getInt("ro.product.first_api_level", 0) >= 28);
    }

    private static boolean updateScreenElements(Context context) {
        int screenWidth = getScreenWidth(context);
        int screenHeight = getScreenHeight(context);
        int i = context.getResources().getConfiguration().screenLayout;
        if (sOldScreenWidthDp == screenWidth && sOldScreenHeightDp == screenHeight && sOldScreenLayout == i) {
            return false;
        }
        Point point = sSizePoint;
        point.x = screenWidth;
        point.y = screenHeight;
        sOldScreenWidthDp = screenWidth;
        sOldScreenHeightDp = screenHeight;
        sOldScreenLayout = i;
        return true;
    }
}
