package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.State;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
