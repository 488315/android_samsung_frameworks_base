package com.android.systemui.keyguard.domain.interactor;

import android.util.MathUtils;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.util.kotlin.Quint;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class KeyguardInteractor$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ KeyguardInteractor this$0;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02231 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C02231(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, KeyguardInteractor keyguardInteractor) {
            this.this$0 = keyguardInteractor;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0093, code lost:
        
            if (r10.emit(r12, r0) == r1) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x00ae, code lost:
        
            if (r10.emit(r11, r0) == r1) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00b0, code lost:
        
            return r1;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C02231 c02231;
            if (continuation instanceof C02231) {
                c02231 = (C02231) continuation;
                int i = c02231.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c02231.label = i - Integer.MIN_VALUE;
                } else {
                    c02231 = new C02231(continuation);
                }
            }
            Object obj2 = c02231.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c02231.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                Quint quint = (Quint) obj;
                float fFloatValue = ((Number) quint.component1()).floatValue();
                KeyguardState keyguardState = (KeyguardState) quint.component2();
                boolean zBooleanValue = ((Boolean) quint.component4()).booleanValue();
                boolean zBooleanValue2 = ((Boolean) quint.component5()).booleanValue();
                Object value = this.this$0.statusBarState.$$delegate_0.getValue();
                StatusBarState statusBarState = StatusBarState.KEYGUARD;
                FlowCollector flowCollector = this.$$this$flow;
                if (value == statusBarState && zBooleanValue && keyguardState == KeyguardState.LOCKSCREEN && fFloatValue != 1.0f) {
                    Float f = new Float(MathUtils.constrainedMap(0.0f, 1.0f, 0.82f, 1.0f, fFloatValue));
                    c02231.label = 1;
                } else if (!zBooleanValue2 && zBooleanValue && (fFloatValue == 0.0f || fFloatValue == 1.0f)) {
                    Float f2 = new Float(1.0f);
                    c02231.label = 2;
                }
            } else {
                if (i2 != 1 && i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$special$$inlined$transform$1(Flow flow, Continuation continuation, KeyguardInteractor keyguardInteractor) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = keyguardInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardInteractor$special$$inlined$transform$1 keyguardInteractor$special$$inlined$transform$1 = new KeyguardInteractor$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        keyguardInteractor$special$$inlined$transform$1.L$0 = obj;
        return keyguardInteractor$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardInteractor$special$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
