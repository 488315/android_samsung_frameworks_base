package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MobileIconsInteractor {
    ReadonlyStateFlow getActiveDataIconInteractor();

    StateFlow getActiveMobileDataSubscriptionId();

    StateFlow getDefaultDataSubId$1();

    Flow getFilteredSubscriptions();

    StateFlow getIcons();

    MobileIconInteractor getMobileConnectionInteractorForSubId(int i);

    StateFlow isDeviceInEmergencyCallsOnlyMode();

    StateFlow isSingleCarrier();

    Flow isStackable();
}
