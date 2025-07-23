package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.log.table.TableLogBuffer;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MobileIconInteractor {
    Flow getActivity();

    Flow getAlwaysShowDataRatIcon();

    Flow getCarrierName();

    Flow getCarrierNetworkChangeActive();

    Flow getDisabledActivityIcon();

    Flow getDisabledDataIcon();

    Flow getImsRegState();

    Flow getMobileIsDefault();

    Flow getMobileServiceState();

    Flow getNetworkName();

    Flow getNetworkTypeIconGroup();

    Flow getOtherSlotInCallState();

    Flow getRoamingId();

    Flow getShowSliceAttribution();

    Flow getSignalLevelIcon();

    int getSlotId();

    TableLogBuffer getTableLogBuffer();

    Flow getVoiceNoServiceIcon();

    Flow isAllowedDuringAirplaneMode();

    Flow isDataConnected();

    Flow isDataEnabled();

    Flow isEmergencyOnly();

    Flow isForceHidden();

    Flow isInService();

    Flow isNonTerrestrial();

    Flow isRoaming();

    Flow isSim1On();

    Flow isSimOn();

    Flow isSingleCarrier();

    Flow isVoWifiConnected();
}
