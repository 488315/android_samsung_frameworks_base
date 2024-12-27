package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.dock.DockManagerExtensionsKt;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardQuickAffordancesCombinedViewModel this$0;

    public KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordancesCombinedViewModel;
        this.$position$inlined = keyguardQuickAffordancePosition;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 = new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$position$inlined);
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardQuickAffordancesCombinedViewModel.PreviewMode previewMode;
        FlowCollector flowCollector;
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            previewMode = (KeyguardQuickAffordancesCombinedViewModel.PreviewMode) this.L$1;
            if (previewMode.isInPreviewMode) {
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.this$0.quickAffordanceInteractor;
                KeyguardQuickAffordancePosition keyguardQuickAffordancePosition = this.$position$inlined;
                this.L$0 = flowCollector2;
                this.L$1 = previewMode;
                this.label = 1;
                Object quickAffordanceAlwaysVisible = keyguardQuickAffordanceInteractor.quickAffordanceAlwaysVisible(keyguardQuickAffordancePosition, this);
                if (quickAffordanceAlwaysVisible == coroutineSingletons) {
                    return coroutineSingletons;
                }
                flowCollector = flowCollector2;
                obj = quickAffordanceAlwaysVisible;
                flow = (Flow) obj;
            } else {
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor2 = this.this$0.quickAffordanceInteractor;
                KeyguardQuickAffordancePosition keyguardQuickAffordancePosition2 = this.$position$inlined;
                this.L$0 = flowCollector2;
                this.L$1 = previewMode;
                this.label = 2;
                Object quickAffordance = keyguardQuickAffordanceInteractor2.quickAffordance(keyguardQuickAffordancePosition2, this);
                if (quickAffordance == coroutineSingletons) {
                    return coroutineSingletons;
                }
                flowCollector = flowCollector2;
                obj = quickAffordance;
                flow = (Flow) obj;
            }
        } else if (i == 1) {
            previewMode = (KeyguardQuickAffordancesCombinedViewModel.PreviewMode) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flow = (Flow) obj;
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            previewMode = (KeyguardQuickAffordancesCombinedViewModel.PreviewMode) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flow = (Flow) obj;
        }
        Flow flow2 = flow;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged((Flow) this.this$0.keyguardInteractor.animateDozingTransitions$delegate.getValue());
        KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = this.this$0;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(flow2, distinctUntilChanged, keyguardQuickAffordancesCombinedViewModel.areQuickAffordancesFullyOpaque, keyguardQuickAffordancesCombinedViewModel.selectedPreviewSlotId, new KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1(DockManagerExtensionsKt.retrieveIsDocked(keyguardQuickAffordancesCombinedViewModel.quickAffordanceInteractor.dockManager)), new KeyguardQuickAffordancesCombinedViewModel$button$1$1(this.$position$inlined, this.this$0, previewMode, null)));
        this.L$0 = null;
        this.L$1 = null;
        this.label = 3;
        if (FlowKt.emitAll(this, distinctUntilChanged2, flowCollector) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
