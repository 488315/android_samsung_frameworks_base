package com.android.systemui.unfold;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.unfold.FoldAodAnimationController$initialize$1", m277f = "FoldAodAnimationController.kt", m278l = {98}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FoldAodAnimationController$initialize$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FoldAodAnimationController this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.unfold.FoldAodAnimationController$initialize$1$1", m277f = "FoldAodAnimationController.kt", m278l = {98}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.unfold.FoldAodAnimationController$initialize$1$1 */
    final class C35121 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ FoldAodAnimationController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C35121(FoldAodAnimationController foldAodAnimationController, Continuation<? super C35121> continuation) {
            super(2, continuation);
            this.this$0 = foldAodAnimationController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C35121 c35121 = new C35121(this.this$0, continuation);
            c35121.L$0 = obj;
            return c35121;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35121) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                FoldAodAnimationController foldAodAnimationController = this.this$0;
                this.label = 1;
                if (foldAodAnimationController.m222xde5cc31b(coroutineScope, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FoldAodAnimationController$initialize$1(FoldAodAnimationController foldAodAnimationController, Continuation<? super FoldAodAnimationController$initialize$1> continuation) {
        super(3, continuation);
        this.this$0 = foldAodAnimationController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FoldAodAnimationController$initialize$1 foldAodAnimationController$initialize$1 = new FoldAodAnimationController$initialize$1(this.this$0, (Continuation) obj3);
        foldAodAnimationController$initialize$1.L$0 = (LifecycleOwner) obj;
        return foldAodAnimationController$initialize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C35121 c35121 = new C35121(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c35121, this) == coroutineSingletons) {
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
