package com.android.systemui.qs.composefragment;

import android.view.View;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class QSFragmentCompose$onCreateView$composeView$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ComposeView $this_apply;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QSFragmentCompose this$0;

    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$onCreateView$composeView$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ View $it;
        final /* synthetic */ ComposeView $this_apply;
        int label;
        final /* synthetic */ QSFragmentCompose this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ComposeView composeView, LifecycleOwner lifecycleOwner, View view, QSFragmentCompose qSFragmentCompose, Continuation continuation) {
            super(2, continuation);
            this.$this_apply = composeView;
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$it = view;
            this.this$0 = qSFragmentCompose;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$this_apply, this.$$this$repeatWhenAttached, this.$it, this.this$0, continuation);
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
            ViewTreeOnBackPressedDispatcherOwner.set(this.$this_apply, new OnBackPressedDispatcherOwner(this.$$this$repeatWhenAttached, this.$it) { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose.onCreateView.composeView.1.1.1.1
                public final Lifecycle lifecycle;
                public final OnBackPressedDispatcher onBackPressedDispatcher;

                {
                    OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(null, 1, null);
                    onBackPressedDispatcher.setOnBackInvokedDispatcher(view.getViewRootImpl().getOnBackInvokedDispatcher());
                    this.onBackPressedDispatcher = onBackPressedDispatcher;
                    this.lifecycle = lifecycleOwner.getLifecycle();
                }

                @Override // androidx.lifecycle.LifecycleOwner
                public final Lifecycle getLifecycle() {
                    return this.lifecycle;
                }

                @Override // androidx.activity.OnBackPressedDispatcherOwner
                public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
                    return this.onBackPressedDispatcher;
                }
            });
            ComposeView composeView = this.$this_apply;
            final QSFragmentCompose qSFragmentCompose = this.this$0;
            composeView.setContent(new ComposableLambdaImpl(587635122, true, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose.onCreateView.composeView.1.1.1.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj2, Object obj3) {
                    Composer composer = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 3) == 2) {
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.getSkipping()) {
                            composerImpl.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.onCreateView.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:235)");
                            }
                            int i = QSFragmentCompose.$r8$clinit;
                            qSFragmentCompose.Content$1(0, composer);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentCompose$onCreateView$composeView$1$1(ComposeView composeView, QSFragmentCompose qSFragmentCompose, Continuation continuation) {
        super(3, continuation);
        this.$this_apply = composeView;
        this.this$0 = qSFragmentCompose;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSFragmentCompose$onCreateView$composeView$1$1 qSFragmentCompose$onCreateView$composeView$1$1 = new QSFragmentCompose$onCreateView$composeView$1$1(this.$this_apply, this.this$0, (Continuation) obj3);
        qSFragmentCompose$onCreateView$composeView$1$1.L$0 = (LifecycleOwner) obj;
        qSFragmentCompose$onCreateView$composeView$1$1.L$1 = (View) obj2;
        return qSFragmentCompose$onCreateView$composeView$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            View view = (View) this.L$1;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_apply, lifecycleOwner, view, this.this$0, null);
            this.L$0 = null;
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
