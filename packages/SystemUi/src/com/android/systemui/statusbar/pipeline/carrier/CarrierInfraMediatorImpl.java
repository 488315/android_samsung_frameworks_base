package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.p045ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.p046zt.devicetrust.cert.CertProvisionProfile;
import java.io.PrintWriter;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CarrierInfraMediatorImpl implements CarrierInfraMediator, Dumpable {
    public final CarrierInfoUtil carrierInfoUtil;
    public final CommonUtil commonUtil;
    public final MobileDataUtil mobileDataUtil;
    public final MobileRoamingUtil roamingUtil;
    public final MobileSignalUtil signalUtil;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                iArr[CarrierInfraMediator.Conditions.IS_USA_SPRINT.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUPPORT_ROAMING_ICON.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.GSM_ROAMING_ICON_ONLY.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.CDMA_ROAMING_ICON_ONLY.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.NO_ROAMING_ICON_AT_GSM.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CSC_MATCHED_SIM.ordinal()] = 19;
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
                iArr[CarrierInfraMediator.Conditions.USE_FEMTOCELL_ICON.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_VZW.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_DOR.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_DISABLED_DATA_ICON.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_KT.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SHOW_TWO_PHONE_MODE_ICON.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.ZERO_SIGNAL_LEVEL_ON_VOWIFI.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.DISPLAY_CBCH50.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_CLARO_PLMN.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_LGT.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_OPEN.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUB_SCREEN_SIGNAL.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.USE_VOICE_NO_SERVICE_ICON.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_VOICE_CAPABLE.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUB_SCREEN_MODE_ICON.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.SUPPORT_CARRIER_ENABLED_SATELLITE.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[CarrierInfraMediator.Values.values().length];
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.MAX_SIGNAL_LEVEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.EXTRA_SYSTEM_ICON_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.ICON_BRANDING_FROM_CSC_FEATURE.ordinal()] = 5;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr2[CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING.ordinal()] = 6;
            } catch (NoSuchFieldError unused50) {
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
                return commonUtil.supportTSS20() ? StringsKt__StringsKt.split$default(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigDefIndicatorAdditionalSystemIcon", "", false), new String[]{","}, 0, 6) : commonUtil.extraSystemIcons;
            case 5:
                commonUtil.getClass();
                return SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "");
            case 6:
                return commonUtil.overriddenIconBranding;
            default:
                return new Object();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0390, code lost:
    
        if (kotlin.collections.CollectionsKt__CollectionsKt.arrayListOf("OYB", "VID", "OYA").contains(r0.getIconBranding(r25)) == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x040a, code lost:
    
        if (kotlin.text.StringsKt__StringsJVMKt.equals("ICE", r0.salesCode, true) != false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0092, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0.getIconBranding(r25), "ATT") != false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x016f, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7.getCarrierLogoPolicy(r25), "BOTH") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x028e, code lost:
    
        if (r0.equals("TMB_OPEN") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0296, code lost:
    
        if (r0.equals("TMK") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x029e, code lost:
    
        if (r0.equals("TMB") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x02a6, code lost:
    
        if (r0.equals("ATT") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x02ae, code lost:
    
        if (r0.equals("ASR") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x02b6, code lost:
    
        if (r0.equals("AIO") == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x02c0, code lost:
    
        if (r0.equals("TMK_OPEN") != false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x02fa, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual("USC", r7.commonUtil.getIconBranding(r25)) == false) goto L82;
     */
    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(CarrierInfraMediator.Conditions conditions, int i, Object... objArr) {
        boolean z;
        boolean z2;
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
                if (!Intrinsics.areEqual("DE", commonUtil2.countryISO)) {
                    z = true;
                    break;
                } else {
                    z = true;
                }
                z2 = z;
                return z2;
            case 3:
                mobileSignalUtil.getClass();
                return SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_RIL_DisplayAntennaLimited", false, false);
            case 4:
                return Intrinsics.areEqual(mobileSignalUtil.commonUtil.countryISO, "KR");
            case 5:
                return Intrinsics.areEqual("ZVV", carrierInfoUtil.commonUtil.getIconBranding(i));
            case 6:
                return Intrinsics.areEqual("LTE", mobileDataUtil.commonUtil.supportTSS20() ? SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOverrideDataIcon", "", false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOverrideDataIcon", ""));
            case 7:
                return Intrinsics.areEqual("DCM", mobileDataUtil.commonUtil.getIconBranding(i));
            case 8:
                return Intrinsics.areEqual("4.5G", mobileDataUtil.getLteWideBandConfig(i));
            case 9:
                CommonUtil commonUtil3 = mobileDataUtil.commonUtil;
                commonUtil3.getClass();
                if (!CollectionsKt__CollectionsKt.arrayListOf("SKT", "KTT", "LGT", "KOO").contains(commonUtil3.getIconBranding(i))) {
                    break;
                }
                z2 = false;
                return z2;
            case 10:
                return !Intrinsics.areEqual(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, mobileDataUtil.getLteWideBandConfig(i));
            case 11:
                return StringsKt__StringsKt.contains(mobileDataUtil.get5gIconConfig(i), "UseOneShapedIcon", false);
            case 12:
                return StringsKt__StringsKt.contains(mobileDataUtil.get5gIconConfig(i), "UseEnlargedIcon", false);
            case 13:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU", "CTC").contains(commonUtil.getIconBranding(i));
            case 14:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("SPR", "VMU", "BST", "XAS").contains(commonUtil.getIconBranding(i));
            case 15:
                if (!Intrinsics.areEqual(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, mobileRoamingUtil.getRoamingIconType(i))) {
                    break;
                }
                z2 = false;
                return z2;
            case 16:
                return Intrinsics.areEqual("GSM", mobileRoamingUtil.getRoamingIconType(i));
            case 17:
                return Intrinsics.areEqual("CDMA", mobileRoamingUtil.getRoamingIconType(i));
            case 18:
                String iconBranding = mobileRoamingUtil.commonUtil.getIconBranding(i);
                switch (iconBranding.hashCode()) {
                    case -2072127113:
                        break;
                    case 64807:
                        break;
                    case 65120:
                        break;
                    case 65153:
                        break;
                    case 83177:
                        break;
                    case 83186:
                        break;
                    case 1965177824:
                        break;
                    default:
                        z2 = false;
                        break;
                }
                return z2;
            case 19:
                return Intrinsics.areEqual(mobileDataUtil.commonUtil.getIconBranding(0), SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false));
            case 20:
                return Intrinsics.areEqual("TMB", commonUtil.getIconBranding(i));
            case 21:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("TMK", "TMB", "ASR").contains(commonUtil.getIconBranding(i));
            case 22:
                if (mobileRoamingUtil.commonUtil.supportTSS20()) {
                    return false;
                }
                return SemCscFeature.getInstance().getBoolean("CscFeature_SystemUI_SupportFemToCellIcon", false);
            case 23:
                commonUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CDR", "AMX", "PCT", "CHL", "TCE").contains(commonUtil.getIconBranding(i));
            case 24:
                return Intrinsics.areEqual("VZW", commonUtil.getIconBranding(i));
            case 25:
                return Intrinsics.areEqual(commonUtil.getIconBranding(i), "DOR");
            case 26:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU", "BRI", "TGY", "VZW", "ZVV", "ZTM").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 27:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("CHC", "CHM", "CHU").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 28:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("BRI", "TGY").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 29:
                mobileDataUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("ZVV", "ZTM").contains(mobileDataUtil.commonUtil.getIconBranding(i));
            case 30:
                if (!Intrinsics.areEqual(carrierInfoUtil.getCarrierLogoPolicy(i), "HOME")) {
                    break;
                }
                z2 = true;
                return z2;
            case 31:
                return Intrinsics.areEqual(commonUtil.getIconBranding(i), "KTT");
            case 32:
                carrierInfoUtil.getClass();
                return SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");
            case 33:
                mobileSignalUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("XFA", "TMB").contains(mobileSignalUtil.commonUtil.getIconBranding(i));
            case 34:
                CommonUtil commonUtil4 = carrierInfoUtil.commonUtil;
                commonUtil4.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("AR", "BO", "BS", "BR", "CL", "CO", "CR", "DM", "DO", CertProvisionProfile.KEY_TYPE_EC, "GT", "HN", "JM", "MX", "NI", "PA", "PR", "PE", "PY", "SV", "TT", "UY").contains(commonUtil4.countryISO);
            case 35:
                return Intrinsics.areEqual("ZTA", carrierInfoUtil.commonUtil.salesCode);
            case 36:
                return Intrinsics.areEqual(commonUtil.getIconBranding(i), "LGT");
            case 37:
                return Intrinsics.areEqual(commonUtil.countryISO, "US");
            case 38:
                commonUtil.getClass();
                return SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "").endsWith("_OPEN");
            case 39:
                mobileSignalUtil.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("ATT", "AIO", "TFN", "XAR").contains(mobileSignalUtil.commonUtil.getIconBranding(i));
            case 40:
                if (StringsKt__StringsKt.contains(commonUtil.VALUE_SUB_DISPLAY_POLICY, "WATCHFACE", false)) {
                    break;
                }
                z2 = false;
                return z2;
            case 41:
                CommonUtil commonUtil5 = mobileSignalUtil.commonUtil;
                commonUtil5.getClass();
                return CollectionsKt__CollectionsKt.arrayListOf("KDI", "DCM", "RKT", "XJP", "SBM").contains(commonUtil5.getIconBranding(i));
            case 42:
                return mobileSignalUtil.isVoiceCapable;
            case 43:
                return StringsKt__StringsKt.contains(commonUtil.VALUE_SUB_DISPLAY_POLICY, "WATCHFACE", false);
            case 44:
                commonUtil.getClass();
                return SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_Common_Support_Satellite", false, false);
            default:
                return false;
        }
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final void set(CarrierInfraMediator.Values values, Object... objArr) {
        if (WhenMappings.$EnumSwitchMapping$1[values.ordinal()] == 6) {
            this.commonUtil.overriddenIconBranding = (String) ArraysKt___ArraysKt.getOrNull(objArr);
        }
    }
}
