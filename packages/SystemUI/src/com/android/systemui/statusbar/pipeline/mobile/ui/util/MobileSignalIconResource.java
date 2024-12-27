package com.android.systemui.statusbar.pipeline.mobile.ui.util;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileSignalIconResource {
    public final CarrierInfraMediator carrierInfraMediator;

    public MobileSignalIconResource(CarrierInfraMediator carrierInfraMediator) {
        this.carrierInfraMediator = carrierInfraMediator;
    }

    public final int[] getMobileSignalIconGroup(int i, int i2, boolean z) {
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i, new Object[0])) {
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.MOBILE_SIGNAL_STRENGTH_ICONS_SPR;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA, i, new Object[0])) {
            if (z) {
                SamsungMobileIcons.Companion.getClass();
                return SamsungMobileIcons.MOBILE_SIGNAL_STRENGTH_ICONS_CHN_DISABLED;
            }
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.MOBILE_SIGNAL_STRENGTH_ICONS_CHN;
        }
        if (i2 == 5) {
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.MOBILE_SIGNAL_STRENGTH_5LEVEL_ICONS;
        }
        SamsungMobileIcons.Companion.getClass();
        return SamsungMobileIcons.MOBILE_SIGNAL_STRENGTH_ICONS;
    }

    public final int getNoServiceIcon(int i) {
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE, i, new Object[0])) {
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.NO_SERVICE_ICON_TMOBILE;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i, new Object[0])) {
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.NO_SERVICE_ICON_VZW;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i, new Object[0])) {
            SamsungMobileIcons.Companion.getClass();
            return SamsungMobileIcons.NO_SERVICE_ICON_SPR;
        }
        SamsungMobileIcons.Companion.getClass();
        return SamsungMobileIcons.NO_SERVICE_ICON;
    }
}
