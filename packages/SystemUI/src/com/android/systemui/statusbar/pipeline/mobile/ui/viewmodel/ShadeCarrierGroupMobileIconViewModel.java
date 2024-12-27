package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeCarrierGroupMobileIconViewModel extends LocationBasedMobileViewModel {
    public final ReadonlyStateFlow carrierName;
    public final ReadonlyStateFlow isVisible;

    public ShadeCarrierGroupMobileIconViewModel(MobileIconViewModelCommon mobileIconViewModelCommon, MobileIconInteractor mobileIconInteractor, CoroutineScope coroutineScope) {
        super(mobileIconViewModelCommon, StatusBarLocation.SHADE_CARRIER_GROUP, null);
        MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
        StateFlow stateFlow = mobileIconInteractorImpl.isSingleCarrier;
        ReadonlyStateFlow readonlyStateFlow = mobileIconInteractorImpl.carrierName;
        this.isVisible = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.commonImpl.isVisible(), stateFlow, new ShadeCarrierGroupMobileIconViewModel$isVisible$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), this.commonImpl.isVisible().getValue());
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel, com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
