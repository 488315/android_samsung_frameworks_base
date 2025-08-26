package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitAll;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;

/* loaded from: classes.dex */
final class PinInputEntry$animateRemoval$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PinInputEntry this$0;

    /* renamed from: com.android.systemui.bouncer.ui.composable.PinInputEntry$animateRemoval$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinInputEntry this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PinInputEntry pinInputEntry, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinInputEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.this$0.entryWidth;
            Dp dpM837boximpl = Dp.m837boximpl(0);
            TweenSpec tweenSpec = this.this$0.shapeAnimations.inputShiftAnimationSpec;
            this.label = 1;
            Object objAnimateTo$default = Animatable.animateTo$default(animatable, dpM837boximpl, tweenSpec, null, null, this, 12);
            return objAnimateTo$default == coroutineSingletons ? coroutineSingletons : objAnimateTo$default;
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.composable.PinInputEntry$animateRemoval$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinInputEntry this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PinInputEntry pinInputEntry, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinInputEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.this$0.shapeSize;
            Dp dpM837boximpl = Dp.m837boximpl(0);
            TweenSpec tweenSpec = this.this$0.shapeAnimations.deleteShapeSizeAnimationSpec;
            this.label = 1;
            Object objAnimateTo$default = Animatable.animateTo$default(animatable, dpM837boximpl, tweenSpec, null, null, this, 12);
            return objAnimateTo$default == coroutineSingletons ? coroutineSingletons : objAnimateTo$default;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputEntry$animateRemoval$2(PinInputEntry pinInputEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PinInputEntry$animateRemoval$2 pinInputEntry$animateRemoval$2 = new PinInputEntry$animateRemoval$2(this.this$0, continuation);
        pinInputEntry$animateRemoval$2.L$0 = obj;
        return pinInputEntry$animateRemoval$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputEntry$animateRemoval$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Deferred[] deferredArr = {BuildersKt.async$default(coroutineScope, null, new AnonymousClass1(this.this$0, null), 3), BuildersKt.async$default(coroutineScope, null, new AnonymousClass2(this.this$0, null), 3)};
        this.label = 1;
        Object objAwait = new AwaitAll(deferredArr).await(this);
        return objAwait == coroutineSingletons ? coroutineSingletons : objAwait;
    }
}
