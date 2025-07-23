package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.util.DeviceState;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarrierInfraMediatorImpl implements CarrierInfraMediator, Dumpable {
    public final CarrierInfoUtil carrierInfoUtil;
    public final CommonUtil commonUtil;
    public final MobileDataUtil mobileDataUtil;
    public final MobileRoamingUtil roamingUtil;
    public final MobileSignalUtil signalUtil;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                iArr[CarrierInfraMediator.Conditions.CDMA_ROAMING_ICON_ONLY.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.NO_ROAMING_ICON_AT_GSM.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMOBILE.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_TMO_DEVICE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[CarrierInfraMediator.Conditions.IS_USA_ATT_DEVICE.ordinal()] = 22;
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
            try {
                iArr[CarrierInfraMediator.Conditions.IS_KDDI_DEVICE.ordinal()] = 45;
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
                return DeviceState.isTestModeIndicatorGarden() ? "SKT" : commonUtil.getIconBranding(i);
            case 2:
                commonUtil.getClass();
                return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false);
            case 3:
                this.signalUtil.commonUtil.supportTSS20();
                return Integer.valueOf(SemCarrierFeature.getInstance().getInt(i, "CarrierFeature_SystemUI_ConfigMaxRssiLevel", 4, false));
            case 4:
                if (commonUtil.supportTSS20()) {
                    return StringsKt__StringsKt.split$default(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigDefIndicatorAdditionalSystemIcon", "", false), new String[]{","}, 0, 6);
                }
                List list = Collections.EMPTY_LIST;
                list.getClass();
                return list;
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
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:135:0x04a5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x04a6 A[RETURN] */
    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabled(com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions r28, int r29, java.lang.Object... r30) {
        /*
            Method dump skipped, instructions count: 1320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediatorImpl.isEnabled(com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions, int, java.lang.Object[]):boolean");
    }
}
