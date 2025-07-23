package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarOperatorNameViewModel {
    public final ChannelFlowTransformLatest operatorName;

    public StatusBarOperatorNameViewModel(MobileIconsInteractor mobileIconsInteractor) {
        this.operatorName = FlowKt.transformLatest(mobileIconsInteractor.getDefaultDataSubId$1(), new StatusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1(null, mobileIconsInteractor));
    }
}
