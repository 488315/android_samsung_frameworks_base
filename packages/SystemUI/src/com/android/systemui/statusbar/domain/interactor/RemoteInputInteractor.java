package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.RemoteInputRepository;
import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class RemoteInputInteractor {
    public final Flow isRemoteInputActive;
    public final RemoteInputRepository remoteInputRepository;
    public final RemoteInputInteractor$special$$inlined$mapNotNull$1 remoteInputRowBottomBound;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1] */
    public RemoteInputInteractor(RemoteInputRepository remoteInputRepository) {
        this.remoteInputRepository = remoteInputRepository;
        RemoteInputRepositoryImpl remoteInputRepositoryImpl = (RemoteInputRepositoryImpl) remoteInputRepository;
        this.isRemoteInputActive = remoteInputRepositoryImpl.isRemoteInputActive;
        final StateFlowImpl stateFlowImpl = remoteInputRepositoryImpl.remoteInputRowBottomBound;
        this.remoteInputRowBottomBound = new Flow() { // from class: com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        Float f = (Float) obj;
                        if (f != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
