package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.log.table.TableLogBuffer;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface MobileConnectionRepository {
    StateFlow getCarrierId();

    StateFlow getCarrierNetworkChangeActive();

    StateFlow getCdmaLevel();

    StateFlow getCdmaRoaming();

    StateFlow getDataActivityDirection();

    StateFlow getDataConnectionState();

    StateFlow getDataEnabled();

    StateFlow getImsRegState();

    StateFlow getMobileDataEnabledChanged();

    StateFlow getMobileServiceState();

    StateFlow getNetworkName();

    StateFlow getNumberOfLevels();

    StateFlow getOnTheCall();

    StateFlow getOperatorAlphaShort();

    StateFlow getPrimaryLevel();

    StateFlow getResolvedNetworkType();

    StateFlow getSim1On();

    StateFlow getSimCardInfo();

    int getSlotId();

    int getSubId();

    StateFlow getSwRoaming();

    TableLogBuffer getTableLogBuffer();

    StateFlow isEmergencyOnly();

    StateFlow isGsm();

    StateFlow isInService();

    StateFlow isNonTerrestrial();

    StateFlow isRoaming();

    void setSim1On(boolean z);
}
