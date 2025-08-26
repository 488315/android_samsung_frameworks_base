package com.samsung.android.hardware.display;

import android.hardware.display.DisplayManagerGlobal;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.DisplayAddress;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public final class RefreshRateConfig {
    private static final String PROPERTY_AMBIENT_BRIGHTNESS = "persist.dm.passive.ambient_brightness";
    private static final String PROPERTY_DISPLAY_BRIGHTNESS = "persist.dm.passive.display_brightness";
    private static final String PROPERTY_SUB_AMBIENT_BRIGHTNESS = "persist.dm.passive.sub_ambient_brightness";
    private static final String PROPERTY_SUB_DISPLAY_BRIGHTNESS = "persist.dm.passive.sub_display_brightness";
    private static final int TYPE_SEAMLESS = 2;
    private static final int TYPE_SEAMLESS_PLUS = 3;
    private static final int TYPE_SWITCHABLE = 1;
    private static RefreshRateConfig sExtraInstance = null;
    private static RefreshRateConfig sInstance = null;
    private static boolean sIsFolded = false;
    private static DisplayAddress sPrimaryPhysicalDisplayAddress;
    private BrightnessThreshold mBrightnessThreshold;
    private final int mDisplayType;
    SupportedRefreshRate mHighSpeedRefreshRates;
    SupportedRefreshRate mNormalSpeedRefreshRates;
    private final boolean mUnsupportedNS;

    public static RefreshRateConfig getInstance(boolean z) {
        if (CoreRune.FW_VRR_MULTI_DISPLAY && z) {
            return getExtraInstance();
        }
        return getMainInstance();
    }

    public static RefreshRateConfig getInstance(int i) {
        if (CoreRune.FW_VRR_MULTI_DISPLAY && (i == 1 || (CoreRune.FW_VRR_FOLD && sIsFolded && i == 0))) {
            return getExtraInstance();
        }
        return getMainInstance();
    }

    public static RefreshRateConfig getExtraInstance() {
        if (sExtraInstance == null) {
            sExtraInstance = createRefreshRateConfig("0", "", "", new BrightnessThreshold("", "", PROPERTY_SUB_DISPLAY_BRIGHTNESS, PROPERTY_SUB_AMBIENT_BRIGHTNESS));
        }
        return sExtraInstance;
    }

    public static RefreshRateConfig getMainInstance() {
        if (sInstance == null) {
            sInstance = createRefreshRateConfig("3", "24,10,30,48,60,80,120", "", new BrightnessThreshold("", "", PROPERTY_DISPLAY_BRIGHTNESS, PROPERTY_AMBIENT_BRIGHTNESS));
        }
        return sInstance;
    }

    public BrightnessThreshold getBrightnessThreshold() {
        return this.mBrightnessThreshold;
    }

    private RefreshRateConfig(String str, String str2, String str3, BrightnessThreshold brightnessThreshold) {
        this.mDisplayType = Integer.parseInt(str);
        this.mHighSpeedRefreshRates = createSupportedRefreshRate(str2, false);
        this.mNormalSpeedRefreshRates = createSupportedRefreshRate(str3, true);
        this.mUnsupportedNS = TextUtils.isEmpty(str3);
        this.mBrightnessThreshold = brightnessThreshold;
    }

    public boolean isSwitchable() {
        return this.mDisplayType == 1;
    }

    public boolean isSeamless() {
        return this.mDisplayType == 2;
    }

    public boolean isSeamlessPlus() {
        return this.mDisplayType == 3;
    }

    public boolean unsupportedNS() {
        return this.mUnsupportedNS;
    }

    public SupportedRefreshRate getNormalSpeedRefreshRates() {
        return this.mNormalSpeedRefreshRates;
    }

    public SupportedRefreshRate getHighSpeedRefreshRates() {
        return this.mHighSpeedRefreshRates;
    }

    private static boolean isInPrimaryDevice(DisplayAddress displayAddress) {
        if (sPrimaryPhysicalDisplayAddress == null) {
            sPrimaryPhysicalDisplayAddress = DisplayAddress.fromPhysicalDisplayId(DisplayManagerGlobal.getInstance().getPrimaryPhysicalDisplayId());
        }
        return displayAddress.equals(sPrimaryPhysicalDisplayAddress);
    }

    public static void updateFoldStateIfNeeded(DisplayAddress displayAddress) {
        sIsFolded = !isInPrimaryDevice(displayAddress);
    }

    public static boolean isInExtraDisplay() {
        return sIsFolded;
    }

    public static RefreshRateConfig createRefreshRateConfig(String str, String str2, String str3, BrightnessThreshold brightnessThreshold) {
        return new RefreshRateConfig(str, str2, str3, brightnessThreshold);
    }

    public SupportedRefreshRate createSupportedRefreshRate(String str, boolean z) {
        return new SupportedRefreshRate(str, z);
    }

    public static class SupportedRefreshRate {
        static final int DEFAULT_REFRESH_RATE = 60;
        private int maxRefreshRate;
        private int minRefreshRate;
        private List<Integer> supportedRefreshRateListForPassive;

        private SupportedRefreshRate(String str, boolean z) {
            this.minRefreshRate = Integer.MAX_VALUE;
            this.maxRefreshRate = Integer.MIN_VALUE;
            this.supportedRefreshRateListForPassive = new ArrayList();
            if (!TextUtils.isEmpty(str)) {
                List list = (List) Arrays.stream(str.split(",")).mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0()).boxed().collect(Collectors.toList());
                this.minRefreshRate = ((Integer) Collections.min(list)).intValue();
                this.maxRefreshRate = ((Integer) Collections.max(list)).intValue();
                this.supportedRefreshRateListForPassive = (List) list.stream().filter(new Predicate() { // from class: com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.lambda$new$0((Integer) obj);
                    }
                }).collect(Collectors.toList());
                return;
            }
            if (z) {
                this.maxRefreshRate = 60;
                this.minRefreshRate = 60;
                this.supportedRefreshRateListForPassive.add(60);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(Integer num) {
            return this.maxRefreshRate % num.intValue() == 0;
        }

        public int min() {
            return this.minRefreshRate;
        }

        public int max() {
            return this.maxRefreshRate;
        }

        public int getSupportedRefreshRateForPassive(final int i) {
            return this.supportedRefreshRateListForPassive.stream().filter(new Predicate() { // from class: com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RefreshRateConfig.SupportedRefreshRate.lambda$getSupportedRefreshRateForPassive$1(i, (Integer) obj);
                }
            }).min(new Comparator() { // from class: com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Integer.compare(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                }
            }).orElse(Integer.valueOf(i)).intValue();
        }

        static /* synthetic */ boolean lambda$getSupportedRefreshRateForPassive$1(int i, Integer num) {
            return num.intValue() >= i;
        }
    }

    public static class BrightnessThreshold {
        static final int INVALID = -1;
        String mAmbientBrightnessProperties;
        String mDisplayBrightnessProperties;
        public int mHighAmbientLuxThreshold;
        public int mHighBrightnessThreshold;
        public int mLowAmbientLuxThreshold;
        public int mLowBrightnessThreshold;

        BrightnessThreshold(String str, String str2, String str3, String str4) {
            this.mLowBrightnessThreshold = -1;
            this.mLowAmbientLuxThreshold = -1;
            this.mHighBrightnessThreshold = -1;
            this.mHighAmbientLuxThreshold = -1;
            this.mDisplayBrightnessProperties = null;
            this.mAmbientBrightnessProperties = null;
            String str5 = SystemProperties.get(str3, "");
            if (!TextUtils.isEmpty(str5)) {
                this.mDisplayBrightnessProperties = str5;
                str = str5;
            }
            if (!TextUtils.isEmpty(str)) {
                List list = (List) Arrays.stream(str.split(",")).mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0()).boxed().collect(Collectors.toList());
                this.mLowBrightnessThreshold = !list.isEmpty() ? ((Integer) list.get(0)).intValue() : -1;
                this.mHighBrightnessThreshold = list.size() > 1 ? ((Integer) list.get(1)).intValue() : -1;
            }
            String str6 = SystemProperties.get(str4, "");
            if (!TextUtils.isEmpty(str6)) {
                this.mAmbientBrightnessProperties = str6;
                str2 = str6;
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            List list2 = (List) Arrays.stream(str2.split(",")).mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0()).boxed().collect(Collectors.toList());
            this.mLowAmbientLuxThreshold = !list2.isEmpty() ? ((Integer) list2.get(0)).intValue() : -1;
            this.mHighAmbientLuxThreshold = list2.size() > 1 ? ((Integer) list2.get(1)).intValue() : -1;
        }
    }

    public static void dump(PrintWriter printWriter, String str, boolean z) {
        if (CoreRune.FW_VRR_MULTI_DISPLAY && z) {
            printWriter.println(str + "RefreshRateConfigs");
            printWriter.println(str + "  SUB_HFR_MODE: 0");
            printWriter.println(str + "  SUB_HFR_SUPPORTED_REFRESH_RATE: ");
            printWriter.println(str + "  SUB_HFR_SUPPORTED_REFRESH_RATE_NS: ");
            printWriter.println(str + "  SUB_SEAMLESS_BRT: ");
            RefreshRateConfig refreshRateConfig = sExtraInstance;
            if (refreshRateConfig != null && refreshRateConfig.getBrightnessThreshold().mDisplayBrightnessProperties != null) {
                printWriter.println(str + "  persist.dm.passive.sub_display_brightness: " + sExtraInstance.getBrightnessThreshold().mDisplayBrightnessProperties);
            }
            printWriter.println(str + "  SUB_SEAMLESS_LUX: ");
            RefreshRateConfig refreshRateConfig2 = sExtraInstance;
            if (refreshRateConfig2 == null || refreshRateConfig2.getBrightnessThreshold().mAmbientBrightnessProperties == null) {
                return;
            }
            printWriter.println(str + "  persist.dm.passive.sub_ambient_brightness: " + sExtraInstance.getBrightnessThreshold().mAmbientBrightnessProperties);
            return;
        }
        printWriter.println(str + "RefreshRateConfigs");
        String str2 = str + "  ";
        printWriter.println(str2 + "HFR_DEFAULT_REFRESH_RATE: 120");
        printWriter.println(str2 + "HFR_MODE: 3");
        printWriter.println(str2 + "HFR_SUPPORTED_REFRESH_RATE: 24,10,30,48,60,80,120");
        printWriter.println(str2 + "HFR_SUPPORTED_REFRESH_RATE_NS: ");
        printWriter.println(str2 + "SEAMLESS_BRT: ");
        RefreshRateConfig refreshRateConfig3 = sInstance;
        if (refreshRateConfig3 != null && refreshRateConfig3.getBrightnessThreshold().mDisplayBrightnessProperties != null) {
            printWriter.println(str2 + "persist.dm.passive.display_brightness: " + sInstance.getBrightnessThreshold().mDisplayBrightnessProperties);
        }
        printWriter.println(str2 + "SEAMLESS_LUX: ");
        RefreshRateConfig refreshRateConfig4 = sInstance;
        if (refreshRateConfig4 == null || refreshRateConfig4.getBrightnessThreshold().mAmbientBrightnessProperties == null) {
            return;
        }
        printWriter.println(str2 + "persist.dm.passive.ambient_brightness: " + sInstance.getBrightnessThreshold().mAmbientBrightnessProperties);
    }
}
