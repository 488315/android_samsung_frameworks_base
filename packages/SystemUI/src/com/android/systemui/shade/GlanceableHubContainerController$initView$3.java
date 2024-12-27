package com.android.systemui.shade;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.RepeatOnLifecycleKt;
import java.util.Collections;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class GlanceableHubContainerController$initView$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ int $bottomEdgeSwipeRegionWidth;
    final /* synthetic */ View $containerView;
    final /* synthetic */ int $topEdgeSwipeRegionWidth;
    int label;
    final /* synthetic */ GlanceableHubContainerController this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.shade.GlanceableHubContainerController$initView$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $bottomEdgeSwipeRegionWidth;
        final /* synthetic */ View $containerView;
        final /* synthetic */ int $topEdgeSwipeRegionWidth;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, View view, int i2, Continuation continuation) {
            super(2, continuation);
            this.$topEdgeSwipeRegionWidth = i;
            this.$containerView = view;
            this.$bottomEdgeSwipeRegionWidth = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$topEdgeSwipeRegionWidth, this.$containerView, this.$bottomEdgeSwipeRegionWidth, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$containerView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, this.$topEdgeSwipeRegionWidth, this.$containerView.getRight(), this.$containerView.getBottom() - this.$bottomEdgeSwipeRegionWidth)));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlanceableHubContainerController$initView$3(GlanceableHubContainerController glanceableHubContainerController, int i, View view, int i2, Continuation continuation) {
        super(3, continuation);
        this.this$0 = glanceableHubContainerController;
        this.$topEdgeSwipeRegionWidth = i;
        this.$containerView = view;
        this.$bottomEdgeSwipeRegionWidth = i2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new GlanceableHubContainerController$initView$3(this.this$0, this.$topEdgeSwipeRegionWidth, this.$containerView, this.$bottomEdgeSwipeRegionWidth, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleRegistry lifecycleRegistry = this.this$0.lifecycleRegistry;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$topEdgeSwipeRegionWidth, this.$containerView, this.$bottomEdgeSwipeRegionWidth, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleRegistry, state, anonymousClass1, this) == coroutineSingletons) {
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
