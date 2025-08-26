package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
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
public final class KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1(Continuation continuation, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel) {
        super(3, continuation);
        this.$viewModel$inlined = keyguardIndicationAreaViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1 keyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1 = new KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1((Continuation) obj3, this.$viewModel$inlined);
        keyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ((Number) this.L$1).intValue();
            KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel = this.$viewModel$inlined;
            final Flow flow = keyguardIndicationAreaViewModel.burnIn;
            Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel$indicationAreaTranslationY$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1, reason: invalid class name */
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
                            Float f = new Float(((BurnInModel) obj).translationY);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flow.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, keyguardIndicationAreaViewModel.mainDispatcher);
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
