package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.log.table.TableLogBuffer;
import kotlinx.coroutines.flow.Flow;

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
