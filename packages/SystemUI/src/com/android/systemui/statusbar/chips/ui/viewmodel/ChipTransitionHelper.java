package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ChipTransitionHelper {
    public final SharedFlowImpl activityStoppedFromDialogEvent;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow wasActivityRecentlyStoppedFromDialog;

    public ChipTransitionHelper(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.activityStoppedFromDialogEvent = MutableSharedFlow$default;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(MutableSharedFlow$default, new ChipTransitionHelper$wasActivityRecentlyStoppedFromDialog$1(null));
        SharingStarted.Companion.getClass();
        this.wasActivityRecentlyStoppedFromDialog = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.Lazily, Boolean.FALSE);
    }

    public final ReadonlyStateFlow createChipFlow(ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, this.wasActivityRecentlyStoppedFromDialog, new ChipTransitionHelper$createChipFlow$1(null)), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
    }

    public final void onActivityStoppedFromDialog() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new ChipTransitionHelper$onActivityStoppedFromDialog$1(this, null), 7);
    }
}
