package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface MobileConnectionsRepository {
    boolean bootstrapProfile(int i);

    ReadonlyStateFlow getActiveMobileDataRepository();

    StateFlow getActiveMobileDataSubscriptionId();

    Flow getActiveSubChangedInGroupEvent();

    StateFlow getDefaultConnectionIsValidated();

    StateFlow getDefaultDataSubId();

    StateFlow getDefaultDataSubRatConfig();

    Flow getDefaultMobileIconGroup();

    Flow getDefaultMobileIconMapping();

    Flow getDefaultMobileIconMappingTable();

    StateFlow getDeviceOnTheCall();

    Flow getHasCarrierMergedConnection();

    StateFlow getMobileIsDefault();

    CoverScreenNetworkSignalModel getNoServiceInfo();

    MobileConnectionRepository getRepoForSubId(int i);

    StateFlow getSubscriptions();
}
