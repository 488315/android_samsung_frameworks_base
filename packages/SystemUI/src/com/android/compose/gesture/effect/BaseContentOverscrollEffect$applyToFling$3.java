package com.android.compose.gesture.effect;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.ui.unit.Velocity;
import com.android.compose.ui.util.SpaceVectorConverter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class BaseContentOverscrollEffect$applyToFling$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $performFling;
    final /* synthetic */ SpaceVectorConverter $this_applyToFling;
    final /* synthetic */ long $velocity;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BaseContentOverscrollEffect this$0;

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

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0076, code lost:
        
            if (androidx.compose.animation.core.Animatable.animateTo$default(r5, r6, r7, r8, null, r12, 8) == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AnimationSpec springSpec;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Function2 function2 = this.$performFling;
                Velocity velocityM878boximpl = Velocity.m878boximpl(this.$velocity);
                this.label = 1;
                obj = function2.invoke(velocityM878boximpl, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            long jM882minusAH228Gc = Velocity.m882minusAH228Gc(this.$velocity, ((Velocity) obj).packedValue);
            Animatable animatable = this.this$0.animatable;
            Float f = new Float(0.0f);
            AnimationSpec animationSpec = this.this$0.animationSpec;
            Float f2 = new Float(1.0f);
            if (animationSpec instanceof SpringSpec) {
                SpringSpec springSpec2 = (SpringSpec) animationSpec;
                springSpec = new SpringSpec(springSpec2.dampingRatio, springSpec2.stiffness, f2);
            } else {
                springSpec = animationSpec;
            }
            Float f3 = new Float(this.$this_applyToFling.mo916toFloatTH1AsA0$1(jM882minusAH228Gc));
            this.label = 2;
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
