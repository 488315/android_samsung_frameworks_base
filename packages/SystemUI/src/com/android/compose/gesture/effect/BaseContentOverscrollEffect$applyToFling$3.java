package com.android.compose.gesture.effect;

import com.android.compose.ui.util.SpaceVectorConverter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BaseContentOverscrollEffect$applyToFling$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $performFling;
    final /* synthetic */ SpaceVectorConverter $this_applyToFling;
    final /* synthetic */ long $velocity;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BaseContentOverscrollEffect this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.compose.gesture.effect.BaseContentOverscrollEffect$applyToFling$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $performFling;
        final /* synthetic */ SpaceVectorConverter $this_applyToFling;
        final /* synthetic */ long $velocity;
        int label;
        final /* synthetic */ BaseContentOverscrollEffect this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function2 function2, long j, BaseContentOverscrollEffect baseContentOverscrollEffect, SpaceVectorConverter spaceVectorConverter, Continuation continuation) {
            super(2, continuation);
            this.$performFling = function2;
            this.$velocity = j;
            this.this$0 = baseContentOverscrollEffect;
            this.$this_applyToFling = spaceVectorConverter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$performFling, this.$velocity, this.this$0, this.$this_applyToFling, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0076, code lost:
        
            if (androidx.compose.animation.core.Animatable.animateTo$default(r5, r6, r7, r8, null, r12, 8) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0078, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x002d, code lost:
        
            if (r13 == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) {
            /*
                r12 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r12.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1c
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r13)
                goto L79
            L10:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                r12.<init>(r13)
                throw r12
            L18:
                kotlin.ResultKt.throwOnFailure(r13)
                goto L30
            L1c:
                kotlin.ResultKt.throwOnFailure(r13)
                kotlin.jvm.functions.Function2 r13 = r12.$performFling
                long r4 = r12.$velocity
                androidx.compose.ui.unit.Velocity r1 = androidx.compose.ui.unit.Velocity.m876boximpl(r4)
                r12.label = r3
                java.lang.Object r13 = r13.invoke(r1, r12)
                if (r13 != r0) goto L30
                goto L78
            L30:
                androidx.compose.ui.unit.Velocity r13 = (androidx.compose.ui.unit.Velocity) r13
                long r3 = r13.packedValue
                long r5 = r12.$velocity
                long r3 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r5, r3)
                com.android.compose.gesture.effect.BaseContentOverscrollEffect r13 = r12.this$0
                androidx.compose.animation.core.Animatable r5 = r13.animatable
                java.lang.Float r6 = new java.lang.Float
                r13 = 0
                r6.<init>(r13)
                com.android.compose.gesture.effect.BaseContentOverscrollEffect r13 = r12.this$0
                androidx.compose.animation.core.AnimationSpec r13 = r13.animationSpec
                java.lang.Float r1 = new java.lang.Float
                r7 = 1065353216(0x3f800000, float:1.0)
                r1.<init>(r7)
                boolean r7 = r13 instanceof androidx.compose.animation.core.SpringSpec
                if (r7 == 0) goto L60
                androidx.compose.animation.core.SpringSpec r13 = (androidx.compose.animation.core.SpringSpec) r13
                float r7 = r13.stiffness
                androidx.compose.animation.core.SpringSpec r8 = new androidx.compose.animation.core.SpringSpec
                float r13 = r13.dampingRatio
                r8.<init>(r13, r7, r1)
                r7 = r8
                goto L61
            L60:
                r7 = r13
            L61:
                com.android.compose.ui.util.SpaceVectorConverter r13 = r12.$this_applyToFling
                float r13 = r13.mo914toFloatTH1AsA0$1(r3)
                java.lang.Float r8 = new java.lang.Float
                r8.<init>(r13)
                r12.label = r2
                r9 = 0
                r11 = 8
                r10 = r12
                java.lang.Object r12 = androidx.compose.animation.core.Animatable.animateTo$default(r5, r6, r7, r8, r9, r10, r11)
                if (r12 != r0) goto L79
            L78:
                return r0
            L79:
                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.effect.BaseContentOverscrollEffect$applyToFling$3.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseContentOverscrollEffect$applyToFling$3(Function2 function2, long j, BaseContentOverscrollEffect baseContentOverscrollEffect, SpaceVectorConverter spaceVectorConverter, Continuation continuation) {
        super(2, continuation);
        this.$performFling = function2;
        this.$velocity = j;
        this.this$0 = baseContentOverscrollEffect;
        this.$this_applyToFling = spaceVectorConverter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BaseContentOverscrollEffect$applyToFling$3 baseContentOverscrollEffect$applyToFling$3 = new BaseContentOverscrollEffect$applyToFling$3(this.$performFling, this.$velocity, this.this$0, this.$this_applyToFling, continuation);
        baseContentOverscrollEffect$applyToFling$3.L$0 = obj;
        return baseContentOverscrollEffect$applyToFling$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BaseContentOverscrollEffect$applyToFling$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$performFling, this.$velocity, this.this$0, this.$this_applyToFling, null), 3);
    }
}
