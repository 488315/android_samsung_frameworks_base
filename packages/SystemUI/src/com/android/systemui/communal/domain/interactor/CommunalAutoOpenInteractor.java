package com.android.systemui.communal.domain.interactor;

import com.android.systemui.common.domain.interactor.BatteryInteractor;
import com.android.systemui.communal.data.model.SuppressionReason;
import com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class CommunalAutoOpenInteractor {
    public final boolean allowSwipeAlways;
    public final BatteryInteractor batteryInteractor;
    public final DockManager dockManager;
    public final PosturingInteractor posturingInteractor;
    public final CommunalAutoOpenInteractor$special$$inlined$map$1 suppressionReason;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.communal.domain.interactor.CommunalAutoOpenInteractor$special$$inlined$map$1] */
    public CommunalAutoOpenInteractor(CommunalSettingsInteractor communalSettingsInteractor, CoroutineContext coroutineContext, BatteryInteractor batteryInteractor, PosturingInteractor posturingInteractor, DockManager dockManager, boolean z) {
        this.batteryInteractor = batteryInteractor;
        this.posturingInteractor = posturingInteractor;
        this.dockManager = dockManager;
        this.allowSwipeAlways = z;
        final Flow flowFlowOn = FlowKt.flowOn(LatestConflatedKt.flatMapLatestConflated(communalSettingsInteractor.whenToStartHub, new CommunalAutoOpenInteractor$shouldAutoOpen$1(this, null)), coroutineContext);
        this.suppressionReason = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalAutoOpenInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalAutoOpenInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CommunalAutoOpenInteractor this$0;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalAutoOpenInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalAutoOpenInteractor communalAutoOpenInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalAutoOpenInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    SuppressionReason.ReasonWhenToAutoShow reasonWhenToAutoShow;
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
                        if (((Boolean) obj).booleanValue()) {
                            reasonWhenToAutoShow = null;
                        } else {
                            reasonWhenToAutoShow = new SuppressionReason.ReasonWhenToAutoShow(!this.this$0.allowSwipeAlways ? 3 : 1);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(reasonWhenToAutoShow, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
