package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class FromAodTransitionInteractor$listenForAodToOccluded$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAodTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToOccluded$1(FromAodTransitionInteractor fromAodTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromAodTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromAodTransitionInteractor$listenForAodToOccluded$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAodTransitionInteractor$listenForAodToOccluded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromAodTransitionInteractor fromAodTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromAodTransitionInteractor.keyguardInteractor.isKeyguardOccluded, fromAodTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(0));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(anonymousClass2, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromAodTransitionInteractor this$0;

        public AnonymousClass2(FromAodTransitionInteractor fromAodTransitionInteractor) {
            this.this$0 = fromAodTransitionInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(boolean z, Continuation continuation) {
            FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1;
            if (continuation instanceof FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1) {
                fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 = (FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1) continuation;
                int i = fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 = new FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1(this, continuation);
                }
            }
            Object objMaybeHandleInsecurePowerGesture = fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1.label = 1;
                objMaybeHandleInsecurePowerGesture = this.this$0.maybeHandleInsecurePowerGesture(fromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1);
                if (objMaybeHandleInsecurePowerGesture == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
            }
            if (!((Boolean) objMaybeHandleInsecurePowerGesture).booleanValue()) {
                Log.i("FromAodTransitionInteractor", "Ignoring change to isOccluded to prevent errant AOD->OCCLUDED");
            }
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
