package com.android.systemui.statusbar.pipeline.mobile.ui.util;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
