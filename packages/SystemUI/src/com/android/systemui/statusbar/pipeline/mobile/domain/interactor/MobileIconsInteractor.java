package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

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
