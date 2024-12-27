package com.android.server.battery;

import android.os.SystemProperties;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BattFeatures {
    public static final boolean FEATURE_FULL_BATTERY_CYCLE;
    public static final boolean FEATURE_SAVE_BATTERY_CYCLE;
    public static final boolean FEATURE_SUPPORT_ASOC;
    public static final boolean SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR;
    public static final boolean SUPPORT_ECO_BATTERY;
    public static final String cachedData;

    static {
        String trim = SystemProperties.get("ro.product.vendor.device", "NONE").trim();
        boolean z = false;
        boolean z2 = trim.startsWith("e1") || trim.startsWith("e2") || trim.startsWith("e3");
        SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR =
                trim.startsWith("r0")
                        || trim.startsWith("g0")
                        || trim.startsWith("dm1")
                        || trim.startsWith("dm2");
        FEATURE_SAVE_BATTERY_CYCLE =
                BattUtils.isExist("/sys/class/power_supply/battery/battery_cycle");
        if (!z2) {
            if (cachedData == null) {
                cachedData =
                        BattUtils.readNode(
                                "/sys/class/power_supply/battery/support_functions", true);
            }
            BootReceiver$$ExternalSyntheticOutline0.m(
                    new StringBuilder("cachedData = "), cachedData, "BattFeatures");
            String str = cachedData;
            if (str != null && str.contains("BFSU")) {
                Slog.d("BattFeatures", "isSupport(BFSU) = true");
            }
            FEATURE_FULL_BATTERY_CYCLE = z;
            FEATURE_SUPPORT_ASOC = BattUtils.isExist("/sys/class/power_supply/battery/fg_asoc");
            SUPPORT_ECO_BATTERY =
                    !SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY_FEATURE");
        }
        z = true;
        FEATURE_FULL_BATTERY_CYCLE = z;
        FEATURE_SUPPORT_ASOC = BattUtils.isExist("/sys/class/power_supply/battery/fg_asoc");
        SUPPORT_ECO_BATTERY =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY_FEATURE");
    }
}
