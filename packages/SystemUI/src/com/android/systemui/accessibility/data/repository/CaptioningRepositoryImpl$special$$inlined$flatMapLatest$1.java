package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.CaptioningManager;
import com.android.systemui.accessibility.data.model.CaptioningModel;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes.dex */
public final class CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CaptioningRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, CaptioningRepositoryImpl captioningRepositoryImpl) {
        super(3, continuation);
        this.this$0 = captioningRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1 captioningRepositoryImpl$special$$inlined$flatMapLatest$1 = new CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        captioningRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        captioningRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return captioningRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final CaptioningManager captioningManager = (CaptioningManager) this.L$1;
            CaptioningRepositoryImpl captioningRepositoryImpl = this.this$0;
            captioningRepositoryImpl.getClass();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CaptioningRepositoryImpl$captioningModel$3(null), FlowConflatedKt.conflatedCallbackFlow(new CaptioningRepositoryImpl$captioningModel$2(captioningManager, null)));
            Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$captioningModel$$inlined$map$1

                /* renamed from: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$captioningModel$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ CaptioningManager $this_captioningModel$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$captioningModel$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, CaptioningManager captioningManager) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$this_captioningModel$inlined = captioningManager;
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
                            CaptioningModel captioningModel = new CaptioningModel(this.$this_captioningModel$inlined.isSystemAudioCaptioningUiEnabled(), this.$this_captioningModel$inlined.isSystemAudioCaptioningEnabled());
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(captioningModel, anonymousClass1) == coroutineSingletons) {
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
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector2, captioningManager), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, captioningRepositoryImpl.backgroundCoroutineContext);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowFlowOn, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
