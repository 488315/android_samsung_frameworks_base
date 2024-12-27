package com.android.systemui.statusbar.chips.call.ui.viewmodel;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.chips.call.domain.interactor.CallChipInteractor;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class CallChipViewModel implements OngoingActivityChipViewModel {
    public final ActivityStarter activityStarter;
    public final ReadonlyStateFlow chip;

    public CallChipViewModel(CoroutineScope coroutineScope, CallChipInteractor callChipInteractor, final SystemClock systemClock, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        final ReadonlyStateFlow readonlyStateFlow = callChipInteractor.ongoingCallState;
        this.chip = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SystemClock $systemClock$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CallChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SystemClock systemClock, CallChipViewModel callChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$systemClock$inlined = systemClock;
                    this.this$0 = callChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L71
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel r9 = (com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel) r9
                        boolean r10 = r9 instanceof com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.NoCall
                        if (r10 == 0) goto L3b
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Hidden r9 = com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Hidden.INSTANCE
                        goto L66
                    L3b:
                        boolean r10 = r9 instanceof com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.InCall
                        if (r10 == 0) goto L74
                        r10 = r9
                        com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel$InCall r10 = (com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.InCall) r10
                        long r4 = r10.startTimeMs
                        com.android.systemui.util.time.SystemClock r10 = r8.$systemClock$inlined
                        long r6 = r10.currentTimeMillis()
                        long r4 = r4 - r6
                        long r6 = r10.elapsedRealtime()
                        long r6 = r6 + r4
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown r10 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown
                        com.android.systemui.common.shared.model.Icon$Resource r2 = new com.android.systemui.common.shared.model.Icon$Resource
                        r4 = 17302942(0x108059e, float:2.4983285E-38)
                        r5 = 0
                        r2.<init>(r4, r5)
                        com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$chip$1$1 r4 = new com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$chip$1$1
                        com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel r5 = r8.this$0
                        r4.<init>(r9, r5)
                        r10.<init>(r2, r6, r4)
                        r9 = r10
                    L66:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r9, r0)
                        if (r8 != r1) goto L71
                        return r1
                    L71:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    L74:
                        kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
                        r8.<init>()
                        throw r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, systemClock, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), OngoingActivityChipModel.Hidden.INSTANCE);
    }
}
