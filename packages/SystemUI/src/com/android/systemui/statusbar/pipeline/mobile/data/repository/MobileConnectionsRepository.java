package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

public interface MobileConnectionsRepository {
    boolean bootstrapProfile(int i);

    StateFlow getActiveMobileDataRepository();

    StateFlow getActiveMobileDataSubscriptionId();

    Flow getActiveSubChangedInGroupEvent();

    StateFlow getDefaultConnectionIsValidated();

    StateFlow getDefaultDataSubId();

    StateFlow getDefaultDataSubRatConfig();

    Flow getDefaultMobileIconGroup();

    Flow getDefaultMobileIconMapping();

    Flow getDefaultMobileIconMappingTable();

    StateFlow getDeviceOnTheCall();

    StateFlow getDeviceServiceState();

    Flow getHasCarrierMergedConnection();

    boolean getIsAnySimSecure();

    StateFlow getMobileIsDefault();

    CoverScreenNetworkSignalModel getNoServiceInfo();

    MobileConnectionRepository getRepoForSubId(int i);

    StateFlow getSubscriptions();

    Flow isAnySimSecure();

    Object isInEcmMode(Continuation continuation);
}
