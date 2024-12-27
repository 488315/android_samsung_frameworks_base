package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CarrierInfraMediatorImpl implements CarrierInfraMediator, Dumpable {
    public final CarrierInfoUtil carrierInfoUtil;
    public final CommonUtil commonUtil;
    public final MobileDataUtil mobileDataUtil;
    public final MobileRoamingUtil roamingUtil;
    public final MobileSignalUtil signalUtil;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CarrierInfraMediator.Conditions.values().length];
            try {
                iArr[CarrierInfraMediator.Conditions.SUPPORT_TSS20.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.NO_SERVICE_WHEN_NO_SIM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SIGNAL_BAR_WHEN_EMERGENCY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.CHANGE_SIGNAL_ONE_LEVEL_PER_SEC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.MULTI_LINE_CARRIER_LABEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_LTE_INSTEAD_OF_4G.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_4G_PLUS_INSTEAD_OF_4G.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_4_HALF_G_INSTEAD_OF_4G_PLUS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_HSPA_DATA_ICON.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_LTE_CA_ICON.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_5G_ONE_SHAPED_ICON.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CHINA.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CHINA_DEVICE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_SPRINT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUPPORT_ROAMING_ICON.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.GSM_ROAMING_ICON_ONLY.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.CDMA_ROAMING_ICON_ONLY.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.NO_ROAMING_ICON_AT_GSM.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMOBILE.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMO_DEVICE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_ATT_DEVICE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_VZW.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_DOR.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_DISABLED_DATA_ICON.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_KT.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SHOW_TWO_PHONE_MODE_ICON.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.ZERO_SIGNAL_LEVEL_ON_VOWIFI.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.DISPLAY_CBCH50.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CLARO_PLMN.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LGT.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_OPEN.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUB_SCREEN_SIGNAL.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_VOICE_NO_SERVICE_ICON.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_VOICE_CAPABLE.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUB_SCREEN_MODE_ICON.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUPPORT_CARRIER_ENABLED_SATELLITE.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[CarrierInfraMediator.Values.values().length];
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.MAX_SIGNAL_LEVEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.EXTRA_SYSTEM_ICON_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING_FROM_CSC_FEATURE.ordinal()] = 5;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING.ordinal()] = 6;
            } catch (NoSuchFieldError unused51) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public CarrierInfraMediatorImpl(DumpManager dumpManager, CommonUtil commonUtil, MobileSignalUtil mobileSignalUtil, CarrierInfoUtil carrierInfoUtil, MobileDataUtil mobileDataUtil, MobileRoamingUtil mobileRoamingUtil) {
        this.commonUtil = commonUtil;
        this.signalUtil = mobileSignalUtil;
        this.carrierInfoUtil = carrierInfoUtil;
        this.mobileDataUtil = mobileDataUtil;
        this.roamingUtil = mobileRoamingUtil;
        dumpManager.registerCriticalDumpable("CarrierInfraMediator", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  countryISO: ");
        CommonUtil commonUtil = this.commonUtil;
        printWriter.println(commonUtil.countryISO);
        printWriter.print("  salesCode: ");
        printWriter.println(commonUtil.salesCode);
        boolean z = BasicRune.STATUS_NETWORK_MULTI_SIM;
        int i = z ? 2 : 1;
        printWriter.println("  multiSims=" + z + ", numSlot=" + i);
        for (int i2 = 0; i2 < i; i2++) {
            printWriter.println("  - SIM " + i2 + " -----");
            CarrierInfraMediator.Conditions[] values = CarrierInfraMediator.Conditions.values();
            int length = values.length;
            for (int i3 = 0; i3 < length; i3++) {
                CarrierInfraMediator.Conditions conditions = values[i3];
                printWriter.print("    " + conditions + "=");
                try {
                    printWriter.print(isEnabled(conditions, i2, new Object[0]));
                } catch (ArrayIndexOutOfBoundsException unused) {
                }
                printWriter.println();
            }
            for (CarrierInfraMediator.Values values2 : CarrierInfraMediator.Values.values()) {
                printWriter.print("    " + values2 + "=");
                printWriter.println(get(values2, i2, new Object[0]));
            }
        }
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final Object get(CarrierInfraMediator.Values values, int i, Object... objArr) {
        int i2 = WhenMappings.$EnumSwitchMapping$1[values.ordinal()];
        CommonUtil commonUtil = this.commonUtil;
        switch (i2) {
            case 1:
                return commonUtil.getIconBranding(i);
            case 2:
                commonUtil.getClass();
                return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false);
            case 3:
                return Integer.valueOf(this.signalUtil.commonUtil.supportTSS20() ? SemCarrierFeature.getInstance().getInt(i, "CarrierFeature_SystemUI_ConfigMaxRssiLevel", 4, false) : SemCscFeature.getInstance().getInteger("CscFeature_SystemUI_ConfigMaxRssiLevel", 4));
            case 4:
                if (commonUtil.supportTSS20()) {
                    return StringsKt__StringsKt.split$default(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigDefIndicatorAdditionalSystemIcon", "", false), new String[]{","}, 0, 6);
                }
                if (CollectionsKt__CollectionsKt.arrayListOf("O2U", "XEC", "ATT").contains(SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", ""))) {
                    return Collections.singletonList("nfc");
                }
                List emptyList = Collections.emptyList();
                Intrinsics.checkNotNull(emptyList);
                return emptyList;
            case 5:
                commonUtil.getClass();
                return SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "");
            case 6:
                return commonUtil.overriddenIconBranding;
            default:
                return new Object();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final boolean isEnabled(CarrierInfraMediator.Conditions conditions, int i, Object... objArr) {
        String string;
        int i2 = WhenMappings.$EnumSwitchMapping$0[conditions.ordinal()];
        MobileRoamingUtil mobileRoamingUtil = this.roamingUtil;
        CarrierInfoUtil carrierInfoUtil = this.carrierInfoUtil;
        MobileSignalUtil mobileSignalUtil = this.signalUtil;
        MobileDataUtil mobileDataUtil = this.mobileDataUtil;
        CommonUtil commonUtil = this.commonUtil;
        switch (i2) {
            case 1:
                return commonUtil.supportTSS20();
            case 2:
                CommonUtil commonUtil2 = mobileSignalUtil.commonUtil;
                if ("DE".equals(commonUtil2.countryISO) || "ICE".equalsIgnoreCase(commonUtil2.salesCode)) {
                    return true;
                }
                break;
            case 3:
                mobileSignalUtil.getClass();
                return SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_RIL_DisplayAntennaLimited", false, false);
            case 4:
                return Intrinsics.areEqual(mobileSignalUtil.commonUtil.countryISO, "KR");
            case 5:
                return "ZVV".equals(carrierInfoUtil.commonUtil.getIconBranding(i));
            case 6:
                if (mobileDataUtil.commonUtil.supportTSS20()) {
                    string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOverrideDataIcon", "", false);
                } else {
                    string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOverrideDataIcon", "");
                    Intrinsics.checkNotNull(string);
                }
                return "LTE".equals(string);
            case 7:
                return "DCM".equals(mobileDataUtil.commonUtil.getIconBranding(i));
            case 8:
                return "4.5G".equals(mobileDataUtil.getLteWideBandConfig(i));
            case 9:
                CommonUtil commonUtil3 = mobileDataUtil.commonUtil;
                commonUtil3.getClass();
                if (!CollectionsKt__CollectionsKt.arrayListOf("SKT", "KTT", "LGT", "KOO").contains(commonUtil3.getIconBranding(i)) && !CollectionsKt__CollectionsKt.arrayListOf("OYB", "VID", "OYA").contains(commonUtil3.getIconBranding(i))) {
                    return true;
                }
                break;
            case 10:
                return !PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE.equals(mobileDataUtil.getLteWideBandConfig(i));
            case 11:
                return StringsKt__StringsKt.contains(mobileDataUtil.get5gIconConfig(i), "UseOneShapedIcon", false);
            case 12:
                if (StringsKt__StringsKt.contains(mobileDataUtil.get5gIconConfig(i), "UseEnlargedIcon", false) || ArraysKt___ArraysKt.contains(new String[]{"XSP", "SIN", "MM1", "STH"}, commonUtil.salesCode)) {
                    return true;
                }
                break;
            case 13:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU", "CTC").contains(commonUtil.getIconBranding(i));
            case 14:
                return Intrinsics.areEqual(commonUtil.countryISO, "CN");
            case 15:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("SPR", "VMU", "BST", "XAS").contains(commonUtil.getIconBranding(i));
            case 16:
                if (!PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE.equals(mobileRoamingUtil.getRoamingIconType(i)) && !"USC".equals(mobileRoamingUtil.commonUtil.getIconBranding(i))) {
                    return true;
                }
                break;
            case 17:
                return "GSM".equals(mobileRoamingUtil.getRoamingIconType(i));
            case 18:
                return "CDMA".equals(mobileRoamingUtil.getRoamingIconType(i));
            case 19:
                String iconBranding = mobileRoamingUtil.commonUtil.getIconBranding(i);
                switch (iconBranding.hashCode()) {
                    case -2072127113:
                        if (iconBranding.equals("TMK_OPEN")) {
                            return true;
                        }
                        break;
                    case 64807:
                        if (iconBranding.equals("AIO")) {
                            return true;
                        }
                        break;
                    case 65120:
                        if (iconBranding.equals("ASR")) {
                            return true;
                        }
                        break;
                    case 65153:
                        if (iconBranding.equals("ATT")) {
                            return true;
                        }
                        break;
                    case 83177:
                        if (iconBranding.equals("TMB")) {
                            return true;
                        }
                        break;
                    case 83186:
                        if (iconBranding.equals("TMK")) {
                            return true;
                        }
                        break;
                    case 1965177824:
                        if (iconBranding.equals("TMB_OPEN")) {
                            return true;
                        }
                        break;
                }
            case 20:
                return "TMB".equals(commonUtil.getIconBranding(i));
            case 21:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("TMK", "TMB", "ASR").contains(commonUtil.getIconBranding(i));
            case 22:
                String[] strArr = {"TMB", "TMK", "ASR"};
                if (Intrinsics.areEqual(commonUtil.countryISO, "US") && ArraysKt___ArraysKt.contains(strArr, commonUtil.salesCode)) {
                    return true;
                }
                break;
            case 23:
                String[] strArr2 = {"ATT", "APP", "AIO"};
                if (Intrinsics.areEqual(commonUtil.countryISO, "US") && ArraysKt___ArraysKt.contains(strArr2, commonUtil.salesCode)) {
                    return true;
                }
                break;
            case 24:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CDR", "AMX", "PCT", "CHL", "TCE").contains(commonUtil.getIconBranding(i));
            case 25:
                return "VZW".equals(commonUtil.getIconBranding(i));
            case 26:
                return commonUtil.getIconBranding(i).equals("DOR");
            case 27:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU", "BRI", "TGY", "VZW", "ZVV", "ZTM").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 28:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 29:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("BRI", "TGY").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 30:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("ZVV", "ZTM").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 31:
                if (carrierInfoUtil.getCarrierLogoPolicy(i).equals("HOME") || carrierInfoUtil.getCarrierLogoPolicy(i).equals("BOTH")) {
                    return true;
                }
                break;
            case 32:
                return commonUtil.getIconBranding(i).equals("KTT");
            case 33:
                carrierInfoUtil.getClass();
                return SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");
            case 34:
                mobileSignalUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("XFA", "TMB").contains(mobileSignalUtil.commonUtil.getIconBranding(i));
            case 35:
                CommonUtil commonUtil4 = carrierInfoUtil.commonUtil;
                commonUtil4.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("AR", "BO", "BS", "BR", "CL", "CO", "CR", "DM", "DO", CertProvisionProfile.KEY_TYPE_EC, "GT", "HN", "JM", "MX", "NI", "PA", "PR", "PE", "PY", "SV", "TT", "UY").contains(commonUtil4.countryISO);
            case 36:
                return "ZTA".equals(carrierInfoUtil.commonUtil.salesCode);
            case 37:
                return commonUtil.getIconBranding(i).equals("LGT");
            case 38:
                return Intrinsics.areEqual(commonUtil.countryISO, "US");
            case 39:
                commonUtil.getClass();
                return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false).endsWith("_OPEN");
            case 40:
                mobileSignalUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("ATT", "AIO", "TFN", "XAR").contains(mobileSignalUtil.commonUtil.getIconBranding(i));
            case 41:
                commonUtil.getClass();
                if (StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY"), "WATCHFACE", false) && commonUtil.getIconBranding(i).equals("ATT")) {
                    return true;
                }
                break;
            case 42:
                CommonUtil commonUtil5 = mobileSignalUtil.commonUtil;
                commonUtil5.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("KDI", "DCM", "RKT", "XJP", "SBM").contains(commonUtil5.getIconBranding(i));
            case 43:
                return mobileSignalUtil.isVoiceCapable;
            case 44:
                commonUtil.getClass();
                return StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY"), "WATCHFACE", false);
            case 45:
                commonUtil.getClass();
                return SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_Common_Support_Satellite", false, false);
        }
        return false;
    }
}
