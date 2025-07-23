package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackedMobileIconViewModelImpl extends ExclusiveActivatable implements StackedMobileIconViewModel {
    public final State dualSim$delegate;
    public final Hydrator hydrator;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconViewModelFlow;
    public final State isIconVisible$delegate;
    public final State isStackable$delegate;
    public final State networkTypeIcon$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        StackedMobileIconViewModelImpl create();
    }

    public StackedMobileIconViewModelImpl(MobileIconsViewModel mobileIconsViewModel) {
        Hydrator hydrator = new Hydrator("StackedMobileIconViewModel", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        this.isStackable$delegate = hydrator.hydratedStateOf("isStackable", Boolean.FALSE, mobileIconsViewModel.isStackable);
        StackedMobileIconViewModelImpl$iconViewModelFlow$1 stackedMobileIconViewModelImpl$iconViewModelFlow$1 = new StackedMobileIconViewModelImpl$iconViewModelFlow$1(null);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconsViewModel.mobileSubViewModels, mobileIconsViewModel.activeMobileDataSubscriptionId, stackedMobileIconViewModelImpl$iconViewModelFlow$1);
        this.iconViewModelFlow = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.dualSim$delegate = hydrator.hydratedStateOf("dualSim", null, FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1(null)));
        this.networkTypeIcon$delegate = hydrator.hydratedStateOf("networkTypeIcon", null, FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2(null)));
        this.isIconVisible$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StackedMobileIconViewModelImpl stackedMobileIconViewModelImpl = StackedMobileIconViewModelImpl.this;
                return Boolean.valueOf(((Boolean) ((SnapshotMutableStateImpl) stackedMobileIconViewModelImpl.isStackable$delegate).getValue()).booleanValue() && stackedMobileIconViewModelImpl.getDualSim() != null);
            }
        });
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final StackedMobileIconViewModel.DualSim getDualSim() {
        return (StackedMobileIconViewModel.DualSim) ((SnapshotMutableStateImpl) this.dualSim$delegate).getValue();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final Icon.Resource getNetworkTypeIcon() {
        return (Icon.Resource) ((SnapshotMutableStateImpl) this.networkTypeIcon$delegate).getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            com.android.systemui.lifecycle.Hydrator r4 = r4.hydrator
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
