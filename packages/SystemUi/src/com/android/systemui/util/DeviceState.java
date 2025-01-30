package com.android.systemui.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.media.MediaRouter;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import androidx.core.app.AbstractC0147x487e7be7;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceState {
    public static final boolean IS_ALREADY_BOOTED;
    public static boolean QUICKBOARD_AVAILABLE_CHECKED = false;
    public static int ROTATION_0 = 0;
    public static int ROTATION_180 = 0;
    public static int ROTATION_270 = 0;
    public static int ROTATION_90 = 0;
    public static boolean mQuickboardAvailable = false;
    public static Point sDisplaySize = null;
    public static int sInDisplayFingerprintHeight = 0;
    public static int sInDisplayFingerprintImageSize = 0;
    public static int sInDisplayFingerprintMarginBottom = 0;
    public static boolean sLoadedSensorValue = false;
    public static int sOldScreenLayout = 0;
    public static int sOldScreenWidthDp = 0;
    public static String sSemSensorAreaHeight = "4";
    public static String sSemSensorImageSize = "10.80";
    public static String sSemSensorMarginBottom = "13.77";
    public static final Point sSizePoint = new Point(0, 0);
    public static final int sPhoneCount = TelephonyManager.getDefault().getPhoneCount();

    static {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH");
        try {
            if (string.length() > 4) {
                Integer.parseInt(string.substring(4));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH", "").toLowerCase().contains("tsds");
        IS_ALREADY_BOOTED = "1".equals(SystemProperties.get("sys.boot_completed"));
        ROTATION_0 = 0;
        ROTATION_90 = 1;
        ROTATION_180 = 2;
        ROTATION_270 = 3;
        QUICKBOARD_AVAILABLE_CHECKED = false;
    }

    public static int getActiveSimCount(Context context) {
        int i = 0;
        int i2 = 0;
        while (i < sPhoneCount) {
            String mSimSystemProperty = getMSimSystemProperty(i, "gsm.sim.state", "NOT_READY");
            if ("READY".equals(mSimSystemProperty) || "LOADED".equals(mSimSystemProperty)) {
                if ((i == 0 ? Settings.Global.getInt(context.getContentResolver(), "phone1_on", 1) : Settings.Global.getInt(context.getContentResolver(), "phone2_on", 1)) != 0) {
                    i2++;
                }
            }
            i++;
        }
        return i2;
    }

    public static int getDisplayHeight(Context context) {
        boolean updateScreenElements = updateScreenElements(context);
        boolean z = context.getResources().getConfiguration().orientation == 1;
        Point point = sSizePoint;
        int i = point.x;
        int i2 = point.y;
        int max = z ? Math.max(i, i2) : Math.min(i, i2);
        if (updateScreenElements) {
            Log.d("DeviceState", "getDisplayHeight portrait? " + z + "  displayHeight= " + max);
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
            Log.d("DeviceState", "getDisplayWidth portrait? " + z + "  displayWidth= " + min);
        }
        return min;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:
    
        if (r1 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getMSimSystemProperty(int i, String str, String str2) {
        String str3;
        String str4 = SystemProperties.get(str);
        if (str4 != null && str4.length() > 0) {
            String[] split = str4.split(",");
            if (i >= 0) {
                if (i < split.length) {
                    str3 = split[i];
                }
            }
        }
        str3 = null;
        return str3 == null ? str2 : str3;
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

    public static boolean isCapturedBlurAllowed() {
        if (sDisplaySize == null) {
            sDisplaySize = new Point();
            SemWindowManager.getInstance().getInitialDisplaySize(sDisplaySize);
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL) {
            Point point = sDisplaySize;
            if (Math.min(point.x, point.y) <= 720) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCenterDisplayCutOut(Context context) {
        boolean z = false;
        try {
            Resources resources = context.getResources();
            String str = "config_mainBuiltInDisplayCutout";
            if (!(LsRune.SECURITY_SUB_DISPLAY_LOCK ? ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened : true)) {
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
            Log.w("DeviceState", "Can not update isCenterDisplayCutOut. " + e.toString());
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isCenterDisplayCutOut: ", z, "DeviceState");
        return z;
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

    public static boolean isDataAllowed(Context context) {
        SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) context.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        if (activeSubscriptionInfo != null) {
            int simSlotIndex = activeSubscriptionInfo.getSimSlotIndex();
            Log.d("DeviceState", "Restriction in Settings Mobile Data On");
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
            try {
                Log.d("DeviceState", "isDataAllowedFromSimSlot(slotId)");
                return enterpriseDeviceManager.getPhoneRestrictionPolicy(null).isDataAllowedFromSimSlot(simSlotIndex);
            } catch (SecurityException e) {
                Log.w("DeviceState", "SecurityException: " + e);
            }
        }
        return true;
    }

    public static boolean isESIM(int i, Context context) {
        for (UiccCardInfo uiccCardInfo : ((TelephonyManager) context.getSystemService("phone")).getUiccCardsInfo()) {
            Iterator<UiccPortInfo> it = uiccCardInfo.getPorts().iterator();
            while (it.hasNext()) {
                if (it.next().getLogicalSlotIndex() == i && uiccCardInfo.isEuicc()) {
                    AbstractC0147x487e7be7.m26m("isEuicc() :", i, " : true", "DeviceState");
                    return true;
                }
            }
        }
        AbstractC0147x487e7be7.m26m("isESIM() :", i, " : false", "DeviceState");
        return false;
    }

    public static boolean isInDisplayFpSensorPositionHigh() {
        Point realSize = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getRealSize();
        return ((float) sInDisplayFingerprintHeight) / ((float) Math.max(realSize.y, realSize.x)) > 0.22f;
    }

    public static boolean isMediaQuickControlBarAvailable(Context context) {
        if (QUICKBOARD_AVAILABLE_CHECKED) {
            return mQuickboardAvailable;
        }
        try {
            QUICKBOARD_AVAILABLE_CHECKED = true;
            context.getPackageManager().getPackageInfo("com.samsung.android.mdx.quickboard", 1);
            Log.d("DeviceState", "quickboard activity is available.");
            mQuickboardAvailable = true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("DeviceState", "quickboard activity isn't available.");
            mQuickboardAvailable = false;
        }
        return mQuickboardAvailable;
    }

    public static boolean isNoSimState(Context context) {
        for (int i = 0; i < sPhoneCount; i++) {
            String mSimSystemProperty = getMSimSystemProperty(i, "gsm.sim.state", "NOT_READY");
            if (isESIM(i, context) && "NOT_READY".equals(mSimSystemProperty)) {
                mSimSystemProperty = "ABSENT";
            }
            if (!"ABSENT".equals(mSimSystemProperty)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOpenTheme(Context context) {
        return Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") != null;
    }

    public static boolean isShopDemo(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
    }

    public static boolean isSmartViewDisplayWithFitToAspectRatio(Context context) {
        if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            return isSmartViewFitToActiveDisplay();
        }
        SemWifiDisplayStatus semGetWifiDisplayStatus = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
        boolean z = semGetWifiDisplayStatus != null && semGetWifiDisplayStatus.getActiveDisplayState() == 2;
        MediaRouter.RouteInfo selectedRoute = ((MediaRouter) context.getSystemService("media_router")).getSelectedRoute(4);
        return (z || ((4 & selectedRoute.getSupportedTypes()) != 0 && selectedRoute.semGetDeviceAddress() == null && selectedRoute.semGetStatusCode() == 6 && (selectedRoute.getPresentationDisplay() != null || (selectedRoute.getDescription() != null && selectedRoute.getDescription().toString().contains("Audio")))) || (Settings.Global.getInt(context.getContentResolver(), "lelink_cast_on", 0) == 1)) && isSmartViewFitToActiveDisplay();
    }

    public static boolean isSmartViewFitToActiveDisplay() {
        return ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFitToActiveDisplay;
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

    public static boolean isTesting() {
        return "true".equals(System.getProperty("dexmaker.share_classloader"));
    }

    public static boolean isVoiceCapable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return false;
        }
        if (!telephonyManager.isVoiceCapable()) {
            if (!(DeviceType.isTablet() && Operator.QUICK_IS_ATT_BRANDING)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004d, code lost:
    
        if (r2 != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0060, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0064, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0065, code lost:
    
        android.util.Log.e("DeviceState", "readFingerprintSensor : failed to close file", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005e, code lost:
    
        if (r2 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setInDisplayFingerprintSensorPosition(DisplayMetrics displayMetrics) {
        FileInputStream fileInputStream;
        if (!sLoadedSensorValue) {
            File file = new File("/sys/class/fingerprint/fingerprint/position");
            if (file.exists()) {
                FileInputStream fileInputStream2 = null;
                try {
                    try {
                        fileInputStream = new FileInputStream(file);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = null;
                    }
                } catch (Exception e) {
                    e = e;
                }
                try {
                    byte[] bArr = new byte[(int) file.length()];
                    if (fileInputStream.read(bArr) > 0) {
                        fileInputStream.close();
                        String[] split = new String(bArr, StandardCharsets.UTF_8).split(",");
                        sSemSensorMarginBottom = split[0];
                        sSemSensorAreaHeight = split[3];
                        sSemSensorImageSize = split[7];
                        sLoadedSensorValue = true;
                    } else {
                        fileInputStream2 = fileInputStream;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream2 = fileInputStream;
                    Log.e("DeviceState", "readFingerprintSensor : failure to read sensor info : ", e);
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e3) {
                            Log.e("DeviceState", "readFingerprintSensor : failed to close file", e3);
                        }
                    }
                    throw th;
                }
            } else {
                Log.w("DeviceState", "readFingerprintSensor : No file for sensor pos");
            }
        }
        float applyDimension = TypedValue.applyDimension(5, Float.parseFloat(sSemSensorImageSize), displayMetrics);
        int applyDimension2 = (int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorMarginBottom), displayMetrics);
        int i = (int) applyDimension;
        sInDisplayFingerprintHeight = (i / 2) + (((int) TypedValue.applyDimension(5, Float.parseFloat(sSemSensorAreaHeight), displayMetrics)) / 2) + applyDimension2;
        sInDisplayFingerprintImageSize = i;
        sInDisplayFingerprintMarginBottom = applyDimension2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean shouldEnableKeyguardScreenRotation(Context context) {
        Resources resources = context.getResources();
        int i = SystemProperties.getInt("ro.product.first_api_level", 0);
        if (!SystemProperties.getBoolean("lockscreen.rot_override", false) && !resources.getBoolean(R.bool.config_enableLockScreenRotation) && !DeviceType.isTablet()) {
            if (DeviceType.supportSEPLite == -1) {
                DeviceType.supportSEPLite = context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite") ? 1 : 0;
            }
            if ((DeviceType.supportSEPLite == 1) != false || i < 28) {
                return false;
            }
        }
        return true;
    }

    public static boolean supportsMultipleUsers() {
        if (SystemProperties.getBoolean("debug.quick_mum_test_trigger", false)) {
            return true;
        }
        return UserManager.supportsMultipleUsers();
    }

    public static boolean updateScreenElements(Context context) {
        int screenWidth = getScreenWidth(context);
        int i = context.getResources().getConfiguration().screenLayout;
        if (sOldScreenWidthDp == screenWidth && sOldScreenLayout == i) {
            return false;
        }
        Point point = sSizePoint;
        point.x = screenWidth;
        point.y = getScreenHeight(context);
        sOldScreenWidthDp = screenWidth;
        sOldScreenLayout = i;
        return true;
    }
}
