package com.android.systemui.shade.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
final class ShadeInteractorLegacyImpl$userInteractingFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $expansion;
    final /* synthetic */ Flow $tracking;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorLegacyImpl$userInteractingFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0);
        }
    }

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorLegacyImpl$userInteractingFlow$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(!this.Z$0);
        }
    }

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorLegacyImpl$userInteractingFlow$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ float F$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.F$0 = ((Number) obj).floatValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            float f = this.F$0;
            return Boolean.valueOf(f <= 0.0f || f >= 1.0f);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeInteractorLegacyImpl$userInteractingFlow$1(Flow flow, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.$tracking = flow;
        this.$expansion = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeInteractorLegacyImpl$userInteractingFlow$1 shadeInteractorLegacyImpl$userInteractingFlow$1 = new ShadeInteractorLegacyImpl$userInteractingFlow$1(this.$tracking, this.$expansion, continuation);
        shadeInteractorLegacyImpl$userInteractingFlow$1.L$0 = obj;
        return shadeInteractorLegacyImpl$userInteractingFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeInteractorLegacyImpl$userInteractingFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0050 A[PHI: r1
      0x0050: PHI (r1v14 kotlinx.coroutines.flow.FlowCollector) = 
      (r1v2 kotlinx.coroutines.flow.FlowCollector)
      (r1v4 kotlinx.coroutines.flow.FlowCollector)
      (r1v15 kotlinx.coroutines.flow.FlowCollector)
     binds: [B:12:0x004d, B:10:0x0032, B:29:0x00ac] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007b A[PHI: r1
      0x007b: PHI (r1v12 kotlinx.coroutines.flow.FlowCollector) = (r1v8 kotlinx.coroutines.flow.FlowCollector), (r1v13 kotlinx.coroutines.flow.FlowCollector) binds: [B:8:0x0022, B:20:0x0078] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008e A[PHI: r1
      0x008e: PHI (r1v11 kotlinx.coroutines.flow.FlowCollector) = (r1v10 kotlinx.coroutines.flow.FlowCollector), (r1v12 kotlinx.coroutines.flow.FlowCollector) binds: [B:7:0x0019, B:23:0x008b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a1 A[PHI: r1
      0x00a1: PHI (r1v15 kotlinx.coroutines.flow.FlowCollector) = (r1v11 kotlinx.coroutines.flow.FlowCollector), (r1v17 kotlinx.coroutines.flow.FlowCollector) binds: [B:26:0x009e, B:6:0x0010] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00af  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x009e -> B:28:0x00a1). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        StateFlow stateFlow;
        AnonymousClass3 anonymousClass3;
        Flow flow;
        AnonymousClass2 anonymousClass2;
        Boolean bool;
        Boolean bool2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                Boolean bool3 = Boolean.FALSE;
                this.L$0 = flowCollector;
                this.label = 1;
                if (flowCollector.emit(bool3, this) != coroutineSingletons) {
                    if (JobKt.isActive(getContext())) {
                        return Unit.INSTANCE;
                    }
                    Flow flow2 = this.$tracking;
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
                    this.L$0 = flowCollector;
                    this.label = 2;
                    if (FlowKt.first(flow2, anonymousClass1, this) != coroutineSingletons) {
                        bool = Boolean.TRUE;
                        this.L$0 = flowCollector;
                        this.label = 3;
                        if (flowCollector.emit(bool, this) != coroutineSingletons) {
                            flow = this.$tracking;
                            anonymousClass2 = new AnonymousClass2(null);
                            this.L$0 = flowCollector;
                            this.label = 4;
                            if (FlowKt.first(flow, anonymousClass2, this) != coroutineSingletons) {
                                stateFlow = this.$expansion;
                                anonymousClass3 = new AnonymousClass3(null);
                                this.L$0 = flowCollector;
                                this.label = 5;
                                if (FlowKt.first(stateFlow, anonymousClass3, this) != coroutineSingletons) {
                                    bool2 = Boolean.FALSE;
                                    this.L$0 = flowCollector;
                                    this.label = 6;
                                    if (flowCollector.emit(bool2, this) != coroutineSingletons) {
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
            case 6:
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                if (JobKt.isActive(getContext())) {
                }
                break;
            case 2:
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                bool = Boolean.TRUE;
                this.L$0 = flowCollector;
                this.label = 3;
                if (flowCollector.emit(bool, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                flow = this.$tracking;
                anonymousClass2 = new AnonymousClass2(null);
                this.L$0 = flowCollector;
                this.label = 4;
                if (FlowKt.first(flow, anonymousClass2, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                stateFlow = this.$expansion;
                anonymousClass3 = new AnonymousClass3(null);
                this.L$0 = flowCollector;
                this.label = 5;
                if (FlowKt.first(stateFlow, anonymousClass3, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                bool2 = Boolean.FALSE;
                this.L$0 = flowCollector;
                this.label = 6;
                if (flowCollector.emit(bool2, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
