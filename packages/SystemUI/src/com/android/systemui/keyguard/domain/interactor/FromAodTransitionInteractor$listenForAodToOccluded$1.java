package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromAodTransitionInteractor this$0;

        public AnonymousClass2(FromAodTransitionInteractor fromAodTransitionInteractor) {
            this.this$0 = fromAodTransitionInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(boolean r4, kotlin.coroutines.Continuation r5) {
            /*
                r3 = this;
                boolean r4 = r5 instanceof com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1
                if (r4 == 0) goto L13
                r4 = r5
                com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 r4 = (com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1) r4
                int r0 = r4.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = r0 & r1
                if (r2 == 0) goto L13
                int r0 = r0 - r1
                r4.label = r0
                goto L18
            L13:
                com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 r4 = new com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1
                r4.<init>(r3, r5)
            L18:
                java.lang.Object r5 = r4.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r4.label
                r2 = 1
                if (r1 == 0) goto L2f
                if (r1 != r2) goto L27
                kotlin.ResultKt.throwOnFailure(r5)
                goto L3d
            L27:
                java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                r3.<init>(r4)
                throw r3
            L2f:
                kotlin.ResultKt.throwOnFailure(r5)
                r4.label = r2
                com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r3 = r3.this$0
                java.lang.Object r5 = r3.maybeHandleInsecurePowerGesture(r4)
                if (r5 != r0) goto L3d
                return r0
            L3d:
                java.lang.Boolean r5 = (java.lang.Boolean) r5
                boolean r3 = r5.booleanValue()
                if (r3 != 0) goto L4c
                java.lang.String r3 = "FromAodTransitionInteractor"
                java.lang.String r4 = "Ignoring change to isOccluded to prevent errant AOD->OCCLUDED"
                android.util.Log.i(r3, r4)
            L4c:
                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1.AnonymousClass2.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
