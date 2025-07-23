package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeCarrierGroupMobileIconViewModel extends LocationBasedMobileViewModel {
    public final Flow carrierName;
    public final ReadonlyStateFlow isVisible;

    public ShadeCarrierGroupMobileIconViewModel(MobileIconViewModelCommon mobileIconViewModelCommon, MobileIconInteractor mobileIconInteractor, CoroutineScope coroutineScope) {
        super(mobileIconViewModelCommon, StatusBarLocation.SHADE_CARRIER_GROUP, null);
        Flow isSingleCarrier = mobileIconInteractor.isSingleCarrier();
        this.carrierName = mobileIconInteractor.getCarrierName();
        this.isVisible = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.commonImpl.isVisible(), isSingleCarrier, new ShadeCarrierGroupMobileIconViewModel$isVisible$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), this.commonImpl.isVisible().getValue());
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel, com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
