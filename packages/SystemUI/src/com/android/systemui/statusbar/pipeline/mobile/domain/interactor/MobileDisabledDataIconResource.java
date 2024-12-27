package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModelKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.IconLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileDisabledDataIconResource {
    public final CarrierInfraMediator carrierInfraMediator;
    private final SettingsHelper settingsHelper;

    public MobileDisabledDataIconResource(CarrierInfraMediator carrierInfraMediator, SettingsHelper settingsHelper) {
        this.carrierInfraMediator = carrierInfraMediator;
        this.settingsHelper = settingsHelper;
    }

    public final DisabledDataIconModel getTypeIcon(int i, NetworkTypeIconModel networkTypeIconModel, boolean z, boolean z2, boolean z3, boolean z4) {
        int i2 = 0;
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i, new Object[0])) {
            boolean z5 = !this.settingsHelper.isMobileDataEnabled() || (z2 && !this.settingsHelper.isDataRoamingEnabled());
            if (z5) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("Use slash icon since data=", " roaming=", "DisabledDataIconResource", this.settingsHelper.isMobileDataEnabled(), this.settingsHelper.isDataRoamingEnabled());
            }
            String name = networkTypeIconModel.getName();
            if (!Intrinsics.areEqual(name, TelephonyIcons.UNKNOWN.name)) {
                if (Intrinsics.areEqual(name, TelephonyIcons.E_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_E_VZW : SamsungMobileIcons.DISABLED_E_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.THREE_G_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_3G_VZW : SamsungMobileIcons.DISABLED_3G_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.H_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_H_VZW : SamsungMobileIcons.DISABLED_H_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.H_PLUS_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_H_PLUS_VZW : SamsungMobileIcons.DISABLED_H_PLUS_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.ONE_X_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_1X_VZW : SamsungMobileIcons.DISABLED_1X_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.FOUR_G_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_4G_VZW : SamsungMobileIcons.DISABLED_4G_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_5G_VZW : SamsungMobileIcons.DISABLED_5G_VZW;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_CONNECTED.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_5G_CONNECTED : SamsungMobileIcons.DISABLED_5G_CONNECTED;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_VZW_UWB.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_5G_VZW_UWB : SamsungMobileIcons.DISABLED_5G_VZW_UWB;
                } else if (Intrinsics.areEqual(name, TelephonyIcons.G_VZW.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_G_VZW : SamsungMobileIcons.DISABLED_G_VZW;
                } else {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = z5 ? SamsungMobileIcons.DISABLED_SLASH_G_VZW : SamsungMobileIcons.DISABLED_G_VZW;
                }
            }
            return new DisabledDataIconModel(IconLocation.DATA_ICON, i2);
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, i, new Object[0])) {
            String name2 = networkTypeIconModel.getName();
            if (Intrinsics.areEqual(name2, TelephonyIcons.G.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_G;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.E.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_E;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.THREE_G.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_3G;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.H.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_H;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.H_PLUS.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_H_PLUS;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_G.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_4G;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_G_PLUS.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_4G_PLUS;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_HALF_G.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_4_HALF_G;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.NR_5G_AVAILABLE.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_5G_AVAILABLE;
            } else if (Intrinsics.areEqual(name2, TelephonyIcons.NR_5G_CONNECTED.name)) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.DISABLED_5G_CONNECTED;
            }
            return new DisabledDataIconModel(IconLocation.DATA_ICON, i2);
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON, i, new Object[0])) {
            return new DisabledDataIconModel(IconLocation.DATA_ICON, networkTypeIconModel.getIconId());
        }
        if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA, i, new Object[0]) || !z) {
            return DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, i, new Object[0])) {
            if (z2) {
                boolean z6 = BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
                if (!z6 || (z6 && !z3)) {
                    return new DisabledDataIconModel(IconLocation.DATA_ICON, networkTypeIconModel.getIconId());
                }
            } else {
                String name3 = networkTypeIconModel.getName();
                if (Intrinsics.areEqual(name3, TelephonyIcons.ONE_X_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_1X;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.G_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_G;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.E_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_E;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.TWO_G_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_2G;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.THREE_G_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_3G;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.THREE_G_PLUS_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_3G_PLUS;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.H_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_H;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.H_PLUS_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_H_PLUS;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.FOUR_G_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_4G;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.FOUR_G_PLUS_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_4G_PLUS;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.NR_5G_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_5G;
                } else if (Intrinsics.areEqual(name3, TelephonyIcons.NR_5GA_CHN.name)) {
                    SamsungMobileIcons.Companion.getClass();
                    i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_5GA;
                }
            }
        }
        if (BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL && z3) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Display limited icon? ", "DisabledDataIconResource", z4);
            if (!z4) {
                SamsungMobileIcons.Companion.getClass();
                i2 = SamsungMobileIcons.SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
            }
        }
        return new DisabledDataIconModel(IconLocation.ROAMING_ICON, i2);
    }
}
