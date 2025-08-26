package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.dock.DockManagerExtensionsKt;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $overrideQuickAffordanceId$inlined;
    final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardQuickAffordancesCombinedViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, String str) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordancesCombinedViewModel;
        this.$position$inlined = keyguardQuickAffordancePosition;
        this.$overrideQuickAffordanceId$inlined = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 = new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$position$inlined, this.$overrideQuickAffordanceId$inlined);
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b8, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, r14, r13) == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
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
                String str = this.$overrideQuickAffordanceId$inlined;
                this.L$0 = flowCollector2;
                this.L$1 = previewMode;
                this.label = 1;
                Object objQuickAffordanceAlwaysVisible = keyguardQuickAffordanceInteractor.quickAffordanceAlwaysVisible(keyguardQuickAffordancePosition, str, this);
                if (objQuickAffordanceAlwaysVisible != coroutineSingletons) {
                    flowCollector = flowCollector2;
                    obj = objQuickAffordanceAlwaysVisible;
                    flow = (Flow) obj;
                }
            } else {
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor2 = this.this$0.quickAffordanceInteractor;
                KeyguardQuickAffordancePosition keyguardQuickAffordancePosition2 = this.$position$inlined;
                this.L$0 = flowCollector2;
                this.L$1 = previewMode;
                this.label = 2;
                Object objQuickAffordance = keyguardQuickAffordanceInteractor2.quickAffordance(keyguardQuickAffordancePosition2, this);
                if (objQuickAffordance != coroutineSingletons) {
                    flowCollector = flowCollector2;
                    obj = objQuickAffordance;
                    flow = (Flow) obj;
                }
            }
            return coroutineSingletons;
        }
        if (i == 1) {
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
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged((Flow) this.this$0.keyguardInteractor.animateDozingTransitions$delegate.getValue());
        KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = this.this$0;
        Flow flow3 = keyguardQuickAffordancesCombinedViewModel.areQuickAffordancesFullyOpaque;
        final KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor3 = keyguardQuickAffordancesCombinedViewModel.quickAffordanceInteractor;
        final Flow flowRetrieveIsDocked = DockManagerExtensionsKt.retrieveIsDocked(keyguardQuickAffordanceInteractor3.dockManager);
        Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(flow2, flowDistinctUntilChanged, flow3, keyguardQuickAffordancesCombinedViewModel.selectedPreviewSlotId, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardQuickAffordanceInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf((((Boolean) obj).booleanValue() || this.this$0.accessibilityManager.isEnabled()) ? false : true);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector3, Continuation continuation) {
                Object objCollect = flowRetrieveIsDocked.collect(new AnonymousClass2(flowCollector3, keyguardQuickAffordanceInteractor3), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new KeyguardQuickAffordancesCombinedViewModel$button$1$1(this.$position$inlined, this.this$0, previewMode, null)));
        this.L$0 = null;
        this.L$1 = null;
        this.label = 3;
    }
}
