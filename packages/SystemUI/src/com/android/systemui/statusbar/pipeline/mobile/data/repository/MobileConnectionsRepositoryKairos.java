package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.State;

/* loaded from: classes3.dex */
public interface MobileConnectionsRepositoryKairos {
    State getActiveMobileDataRepository();

    State getActiveMobileDataSubscriptionId();

    Events getActiveSubChangedInGroupEvent();

    State getDefaultConnectionIsValidated();

    State getDefaultDataSubId();

    State getDefaultDataSubRatConfig();

    State getDefaultMobileIconGroup();

    State getDefaultMobileIconMapping();

    State getHasCarrierMergedConnection();

    Incremental getMobileConnectionsBySubId();

    State getMobileIsDefault();

    State getSubscriptions();

    State isAnySimSecure();

    State isDeviceEmergencyCallCapable();

    State isInEcmMode();
}
