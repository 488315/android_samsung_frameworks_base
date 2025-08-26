package com.android.systemui.shade.domain.interactor;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class ShadeExpandedStateInteractorImpl implements ShadeExpandedStateInteractor {
    public final StateFlowImpl currentlyExpandedElement = StateFlowKt.MutableStateFlow(null);

    public ShadeExpandedStateInteractorImpl(ShadeInteractor shadeInteractor, CoroutineScope coroutineScope, NotificationShadeElement notificationShadeElement, QSShadeElement qSShadeElement) {
    }
}
