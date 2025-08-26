package com.android.systemui.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.view.WindowManager;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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

/* loaded from: classes3.dex */
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "getDeviceResolutionPixelSize - currentDensity = ", " deviceDensity = ");
        sbM.append(deviceDensity);
        sbM.append(" initialDisplaySizeFactor = ");
        sbM.append(initialDisplaySizeFactor);
        sbM.append(" currentDisplaySizeFactor = ");
        sbM.append(currentDisplaySizeFactor);
        sbM.append(" initialDisplayDensity = ");
        sbM.append(initialDisplayDensity);
        sbM.append(" proportionalDensity = ");
        sbM.append(proportionalDensity);
        sbM.append(" proportionalPixel = ");
        RecyclerView$$ExternalSyntheticOutline0.m(proportionalPixel, TAG, sbM);
        return proportionalPixel;
    }

    public static float getDeviceScreenInches(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        float f = iWidth / displayMetrics.xdpi;
        float f2 = iHeight / displayMetrics.ydpi;
        return (float) Math.sqrt((f2 * f2) + (f * f));
    }

    public static int getDisplayHeight(Context context) {
        boolean zUpdateScreenElements = updateScreenElements(context);
        boolean z = context.getResources().getConfiguration().orientation == 1;
        Point point = sSizePoint;
        int i = point.x;
        int i2 = point.y;
        int iMax = z ? Math.max(i, i2) : Math.min(i, i2);
        if (zUpdateScreenElements) {
            Log.d(TAG, "getDisplayHeight portrait? " + z + "  displayHeight= " + iMax);
        }
        return iMax;
    }

    public static int getDisplayWidth(Context context) {
        boolean zUpdateScreenElements = updateScreenElements(context);
        boolean z = context.getResources().getConfiguration().orientation == 1;
        Point point = sSizePoint;
        int i = point.x;
        int i2 = point.y;
        int iMin = z ? Math.min(i, i2) : Math.max(i, i2);
        if (zUpdateScreenElements) {
            Log.d(TAG, "getDisplayWidth portrait? " + z + "  displayWidth= " + iMin);
        }
        return iMin;
    }

    private static int getESimSwitchingSlotIndex() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH");
        try {
            if (string.length() <= 4) {
                return 1;
            }
            return Integer.parseInt(string.substring(4)) - 1;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getMSimSystemProperty(String str, int i, String str2) {
        String str3;
        String str4 = SystemProperties.get(str);
        if (str4 == null || str4.length() <= 0) {
            str3 = null;
        } else {
            String[] strArrSplit = str4.split(",");
            if (i < 0 || i >= strArrSplit.length || (str3 = strArrSplit[i]) == null) {
            }
        }
        return str3 == null ? str2 : str3;
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
        return i == 0 ? Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON, 1) : Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON, 1);
    }

    public static int getSimState(Context context, int i) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return 0;
        }
        int simState = telephonyManager.getSimState(i);
        return (LsRune.SECURITY_SIM_PERSO_LOCK && simState == 4) ? "PERSO_LOCKED".equals(getMSimSystemProperty("gsm.sim.state", i, "NOT_READY")) ? 12 : 0 : simState;
    }

    public static int getVoWifiEnableState(Context context) {
        return Settings.System.getInt(context.getContentResolver(), VOWIFI_MENU_ENABLE, 0);
    }

    public static boolean isAlreadyBooted() {
        return IS_ALREADY_BOOTED;
    }

    public static boolean isAppInstalled(Context context, String str) throws PackageManager.NameNotFoundException {
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
        try {
            if (sDisplaySize == null) {
                sDisplaySize = new Point();
                SemWindowManager.getInstance().getInitialDisplaySize(sDisplaySize);
            }
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        } catch (Throwable th) {
            throw th;
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isCenterDisplayCutOut: ", TAG, z);
        return z;
    }

    public static boolean isClearSideViewCoverType(int i) {
        return i == 15;
    }

    public static boolean isCoverUIType(int i) {
        if (i != 1 && i != 3 && i != 6 && i != 8) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static boolean isCoverUiWithWallpaper(int i) {
        return i == 17;
    }

    public static boolean isDataAllowed(Context context) {
        SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) context.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        if (activeSubscriptionInfo == null) {
            return true;
        }
        int simSlotIndex = activeSubscriptionInfo.getSimSlotIndex();
        Log.d(TAG, "Restriction in Settings Mobile Data On");
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
        try {
            Log.d(TAG, "isDataAllowedFromSimSlot(slotId)");
            return enterpriseDeviceManager.getPhoneRestrictionPolicy().isDataAllowedFromSimSlot(simSlotIndex);
        } catch (SecurityException e) {
            Log.w(TAG, "SecurityException: " + e);
            return true;
        }
    }

    public static boolean isDesktopMode(Context context) {
        return false;
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

    public static boolean isLargeScreenTablet(Context context) {
        return isTablet() && getDeviceScreenInches(context) >= 12.0f;
    }

    public static boolean isMultiFoldMain() {
        return DeviceType.isMultiFoldDevice() && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
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

    public static boolean isShowingPopOverStatusBar(Context context) {
        if (!DeviceType.isSupportModelPopOverStatusBar()) {
            return false;
        }
        if (isTablet()) {
            return true;
        }
        return DeviceType.isMultiFoldDevice() && context != null && context.getResources().getConfiguration().semDisplayDeviceType == 0;
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
        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
        boolean z = semWifiDisplayStatusSemGetWifiDisplayStatus != null && semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplayState() == 2;
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
        if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() == 2) {
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
            if (i == 2 && ((activeSubscriptionInfoList.get(0).getSubscriptionId() > activeSubscriptionInfoList.get(1).getSubscriptionId() && activeSubscriptionInfoList.get(0).getSimSlotIndex() < activeSubscriptionInfoList.get(1).getSimSlotIndex()) || (activeSubscriptionInfoList.get(0).getSubscriptionId() < activeSubscriptionInfoList.get(1).getSubscriptionId() && activeSubscriptionInfoList.get(0).getSimSlotIndex() > activeSubscriptionInfoList.get(1).getSimSlotIndex()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTablet() {
        return DeviceType.isTablet();
    }

    public static boolean isTelephonyIdle(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
        boolean zIsInCall = telecomManager != null ? true ^ telecomManager.isInCall() : true;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isTelephonyIdle() - ", TAG, zIsInCall);
        return zIsInCall;
    }

    public static boolean isTestModeIndicatorGarden() {
        return DeviceType.isEngOrUTBinary() && SystemProperties.getBoolean("debug.status_bar.show_icons", false);
    }

    public static boolean isTesting() {
        return "true".equals(System.getProperty(SYSTEM_PROPERTY_KEY_TEST_MODE));
    }

    public static boolean isVoiceCapable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String string = SemCscFeature.getInstance().getString("CscFeature_IMS_ConfigMdmnType");
        if (telephonyManager != null) {
            return telephonyManager.isVoiceCapable() || string.equalsIgnoreCase(ImsSettings.MDMN.SOFTPHONE);
        }
        return false;
    }

    private static void readFingerprintSensor() throws Throwable {
        FileInputStream fileInputStream;
        if (sLoadedSensorValue) {
            return;
        }
        File file = new File("/sys/class/fingerprint/fingerprint/position");
        if (!file.exists()) {
            Log.w(TAG, "readFingerprintSensor : No file for sensor pos");
            return;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                byte[] bArr = new byte[(int) file.length()];
                if (fileInputStream.read(bArr) > 0) {
                    fileInputStream.close();
                    String[] strArrSplit = new String(bArr, StandardCharsets.UTF_8).split(",");
                    sSemSensorMarginBottom = strArrSplit[0];
                    sSemSensorAreaHeight = strArrSplit[3];
                    sSemSensorImageSize = strArrSplit[7];
                    sLoadedSensorValue = true;
                } else {
                    fileInputStream2 = fileInputStream;
                }
            } catch (Exception e2) {
                e = e2;
                fileInputStream2 = fileInputStream;
                Log.e(TAG, "readFingerprintSensor : failure to read sensor info : ", e);
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                return;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e3) {
                        Log.e(TAG, "readFingerprintSensor : failed to close file", e3);
                    }
                }
                throw th;
            }
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
        } catch (Exception e4) {
            Log.e(TAG, "readFingerprintSensor : failed to close file", e4);
        }
    }

    public static void resetDeviceDensity() {
        deviceDensity = 0;
    }

    public static void setInDisplayFingerprintSensorPosition(DisplayMetrics displayMetrics) throws Throwable {
        readFingerprintSensor();
        float fApplyDimension = TypedValue.applyDimension(5, Float.parseFloat(sSemSensorImageSize), displayMetrics);
        int iApplyDimension = (int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorMarginBottom), displayMetrics);
        int i = (int) fApplyDimension;
        sInDisplayFingerprintHeight = (i / 2) + (((int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorAreaHeight), displayMetrics)) / 2) + iApplyDimension;
        sInDisplayFingerprintImageSize = i;
        sInDisplayFingerprintMarginBottom = iApplyDimension;
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
        Resources resources = context.getResources();
        int i = SystemProperties.getInt("ro.product.first_api_level", 0);
        if (SystemProperties.getBoolean("lockscreen.rot_override", false) || resources.getBoolean(R.bool.config_enableLockScreenRotation) || DeviceType.isTablet()) {
            return true;
        }
        return !DeviceType.isSEPLiteDevice(context) && i >= 28;
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
