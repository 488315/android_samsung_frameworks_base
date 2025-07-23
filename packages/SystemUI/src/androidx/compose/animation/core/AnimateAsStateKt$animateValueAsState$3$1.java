package androidx.compose.animation.core;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnimateAsStateKt$animateValueAsState$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<AnimationSpec<Object>> $animSpec$delegate;
    final /* synthetic */ Animatable<Object, AnimationVector> $animatable;
    final /* synthetic */ Channel $channel;
    final /* synthetic */ State<Function1> $listener$delegate;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ State<AnimationSpec<Object>> $animSpec$delegate;
        final /* synthetic */ Animatable<Object, AnimationVector> $animatable;
        final /* synthetic */ State<Function1> $listener$delegate;
        final /* synthetic */ Object $newTarget;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Object obj, Animatable<Object, AnimationVector> animatable, State<? extends AnimationSpec<Object>> state, State<? extends Function1> state2, Continuation continuation) {
            super(2, continuation);
            this.$newTarget = obj;
            this.$animatable = animatable;
            this.$animSpec$delegate = state;
            this.$listener$delegate = state2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$newTarget, this.$animatable, this.$animSpec$delegate, this.$listener$delegate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            AnonymousClass1 anonymousClass1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!Intrinsics.areEqual(this.$newTarget, ((SnapshotMutableStateImpl) this.$animatable.targetValue$delegate).getValue())) {
                    Animatable<Object, AnimationVector> animatable = this.$animatable;
                    Object obj2 = this.$newTarget;
                    State<AnimationSpec<Object>> state = this.$animSpec$delegate;
                    SpringSpec springSpec = AnimateAsStateKt.defaultAnimation;
                    AnimationSpec animationSpec = (AnimationSpec) state.getValue();
                    this.label = 1;
                    anonymousClass1 = this;
                    if (Animatable.animateTo$default(animatable, obj2, animationSpec, null, null, anonymousClass1, 12) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            anonymousClass1 = this;
            State<Function1> state2 = anonymousClass1.$listener$delegate;
            SpringSpec springSpec2 = AnimateAsStateKt.defaultAnimation;
            Function1 function1 = (Function1) state2.getValue();
            if (function1 != null) {
                function1.mo779invoke(anonymousClass1.$animatable.internalState.getValue());
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AnimateAsStateKt$animateValueAsState$3$1(Channel channel, Animatable<Object, AnimationVector> animatable, State<? extends AnimationSpec<Object>> state, State<? extends Function1> state2, Continuation continuation) {
        super(2, continuation);
        this.$channel = channel;
        this.$animatable = animatable;
        this.$animSpec$delegate = state;
        this.$listener$delegate = state2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AnimateAsStateKt$animateValueAsState$3$1 animateAsStateKt$animateValueAsState$3$1 = new AnimateAsStateKt$animateValueAsState$3$1(this.$channel, this.$animatable, this.$animSpec$delegate, this.$listener$delegate, continuation);
        animateAsStateKt$animateValueAsState$3$1.L$0 = obj;
        return animateAsStateKt$animateValueAsState$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnimateAsStateKt$animateValueAsState$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0040  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0035 -> B:5:0x0038). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L1d
            if (r1 != r2) goto L15
            java.lang.Object r1 = r11.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            java.lang.Object r3 = r11.L$0
            kotlinx.coroutines.CoroutineScope r3 = (kotlinx.coroutines.CoroutineScope) r3
            kotlin.ResultKt.throwOnFailure(r12)
            goto L38
        L15:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1d:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.CoroutineScope r12 = (kotlinx.coroutines.CoroutineScope) r12
            kotlinx.coroutines.channels.Channel r1 = r11.$channel
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = r1.iterator()
            r3 = r12
        L2b:
            r11.L$0 = r3
            r11.L$1 = r1
            r11.label = r2
            java.lang.Object r12 = r1.hasNext(r11)
            if (r12 != r0) goto L38
            return r0
        L38:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L65
            java.lang.Object r12 = r1.next()
            kotlinx.coroutines.channels.Channel r4 = r11.$channel
            java.lang.Object r4 = r4.mo3455tryReceivePtdJZtk()
            java.lang.Object r4 = kotlinx.coroutines.channels.ChannelResult.m3459getOrNullimpl(r4)
            if (r4 != 0) goto L52
            r6 = r12
            goto L53
        L52:
            r6 = r4
        L53:
            androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1$1 r5 = new androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1$1
            androidx.compose.animation.core.Animatable<java.lang.Object, androidx.compose.animation.core.AnimationVector> r7 = r11.$animatable
            androidx.compose.runtime.State<androidx.compose.animation.core.AnimationSpec<java.lang.Object>> r8 = r11.$animSpec$delegate
            androidx.compose.runtime.State<kotlin.jvm.functions.Function1> r9 = r11.$listener$delegate
            r10 = 0
            r5.<init>(r6, r7, r8, r9, r10)
            r12 = 3
            r4 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r3, r4, r4, r5, r12)
            goto L2b
        L65:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
