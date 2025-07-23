package com.android.systemui.qs.composefragment;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.lifecycle.SnapshotViewBinding;
import com.android.systemui.lifecycle.SnapshotViewBindingKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSFragmentCompose$setListenerCollections$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSFragmentCompose this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ QSFragmentCompose this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSFragmentCompose this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(QSFragmentCompose qSFragmentCompose, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSFragmentCompose;
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
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    QSFragmentCompose qSFragmentCompose = this.this$0;
                    StateFlowImpl stateFlowImpl = qSFragmentCompose.heightListener;
                    QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
                    if (qSFragmentComposeViewModel == null) {
                        qSFragmentComposeViewModel = null;
                    }
                    QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1 qSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1 = new QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1(stateFlowImpl, qSFragmentComposeViewModel.containerViewModel.editModeViewModel.isEditing, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(qSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSFragmentCompose this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(QSFragmentCompose qSFragmentCompose, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSFragmentCompose;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    QSFragmentCompose qSFragmentCompose = this.this$0;
                    StateFlowImpl stateFlowImpl = qSFragmentCompose.qsContainerController;
                    QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
                    if (qSFragmentComposeViewModel == null) {
                        qSFragmentComposeViewModel = null;
                    }
                    QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1 qSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1 = new QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1(stateFlowImpl, qSFragmentComposeViewModel.containerViewModel.editModeViewModel.isEditing, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(qSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QSFragmentCompose qSFragmentCompose, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentCompose;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            View view = this.this$0.getView();
            if (view != null) {
                QSFragmentCompose$$ExternalSyntheticLambda0 qSFragmentCompose$$ExternalSyntheticLambda0 = new QSFragmentCompose$$ExternalSyntheticLambda0(this.this$0, 4);
                SnapshotViewBindingKt$$ExternalSyntheticLambda0 snapshotViewBindingKt$$ExternalSyntheticLambda0 = new SnapshotViewBindingKt$$ExternalSyntheticLambda0();
                Object tag = view.getTag(R.id.snapshot_view_binding);
                SnapshotViewBinding snapshotViewBinding = tag instanceof SnapshotViewBinding ? (SnapshotViewBinding) tag : null;
                if (snapshotViewBinding != null) {
                    view.removeOnAttachStateChangeListener(snapshotViewBinding);
                    if (view.isAttachedToWindow()) {
                        snapshotViewBinding.onViewDetachedFromWindow(view);
                    }
                }
                SnapshotViewBinding snapshotViewBinding2 = new SnapshotViewBinding(qSFragmentCompose$$ExternalSyntheticLambda0, snapshotViewBindingKt$$ExternalSyntheticLambda0);
                view.setTag(R.id.snapshot_view_binding, snapshotViewBinding2);
                view.addOnAttachStateChangeListener(snapshotViewBinding2);
                if (view.isAttachedToWindow()) {
                    snapshotViewBinding2.onViewAttachedToWindow(view);
                }
            }
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentCompose$setListenerCollections$1(QSFragmentCompose qSFragmentCompose, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSFragmentCompose;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSFragmentCompose$setListenerCollections$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentCompose$setListenerCollections$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Lifecycle lifecycle = this.this$0.getLifecycle();
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycle, state, anonymousClass1, this) == coroutineSingletons) {
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
