package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OngoingActivityChipsViewModel {
    public final ReadonlyStateFlow chip;

    public OngoingActivityChipsViewModel(CoroutineScope coroutineScope, ScreenRecordChipViewModel screenRecordChipViewModel, ShareToAppChipViewModel shareToAppChipViewModel, CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel, CallChipViewModel callChipViewModel) {
        this.chip = FlowKt.stateIn(FlowKt.combine(screenRecordChipViewModel.chip, shareToAppChipViewModel.chip, castToOtherDeviceChipViewModel.chip, callChipViewModel.chip, new OngoingActivityChipsViewModel$chip$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), OngoingActivityChipModel.Hidden.INSTANCE);
    }
}
