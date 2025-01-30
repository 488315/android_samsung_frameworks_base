package com.android.systemui.statusbar.pipeline.shared.p028ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.pipeline.shared.p028ui.viewmodel.CollapsedStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.p028ui.viewmodel.CollapsedStatusBarViewModelImpl;
import com.android.systemui.statusbar.pipeline.shared.p028ui.viewmodel.CollapsedStatusBarViewModelImpl$special$$inlined$map$2;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1", m277f = "CollapsedStatusBarViewBinder.kt", m278l = {52}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CollapsedStatusBarViewBinderImpl$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ StatusBarVisibilityChangeListener $listener;
    final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1", m277f = "CollapsedStatusBarViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1 */
    final class C33491 extends SuspendLambda implements Function2 {
        final /* synthetic */ StatusBarVisibilityChangeListener $listener;
        final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$1", m277f = "CollapsedStatusBarViewBinder.kt", m278l = {54}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarVisibilityChangeListener $listener;
            final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = collapsedStatusBarViewModel;
                this.$listener = statusBarVisibilityChangeListener;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$listener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = ((CollapsedStatusBarViewModelImpl) this.$viewModel).isTransitioningFromLockscreenToOccluded;
                    final StatusBarVisibilityChangeListener statusBarVisibilityChangeListener = this.$listener;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).booleanValue();
                            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                            collapsedStatusBarFragment.mStatusBarHideChecker.printStatusBarInfoLog("onStatusBarVisibilityMaybeChanged()");
                            collapsedStatusBarFragment.updateStatusBarVisibilities(true);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$2", m277f = "CollapsedStatusBarViewBinder.kt", m278l = {60}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarVisibilityChangeListener $listener;
            final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = collapsedStatusBarViewModel;
                this.$listener = statusBarVisibilityChangeListener;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$listener, continuation);
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
                    CollapsedStatusBarViewModelImpl$special$$inlined$map$2 collapsedStatusBarViewModelImpl$special$$inlined$map$2 = ((CollapsedStatusBarViewModelImpl) this.$viewModel).transitionFromLockscreenToDreamStartedEvent;
                    final StatusBarVisibilityChangeListener statusBarVisibilityChangeListener = this.$listener;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                            collapsedStatusBarFragment.mTransitionFromLockscreenToDreamStarted = true;
                            collapsedStatusBarFragment.mStatusBarHideChecker.printStatusBarInfoLog("onTransitionFromLockscreenToDreamStarted()");
                            collapsedStatusBarFragment.mStatusBarHideChecker.postUpdateStatusBarVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (collapsedStatusBarViewModelImpl$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
        public C33491(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation<? super C33491> continuation) {
            super(2, continuation);
            this.$viewModel = collapsedStatusBarViewModel;
            this.$listener = statusBarVisibilityChangeListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C33491 c33491 = new C33491(this.$viewModel, this.$listener, continuation);
            c33491.L$0 = obj;
            return c33491;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33491) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$listener, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$listener, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollapsedStatusBarViewBinderImpl$bind$1(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation<? super CollapsedStatusBarViewBinderImpl$bind$1> continuation) {
        super(3, continuation);
        this.$viewModel = collapsedStatusBarViewModel;
        this.$listener = statusBarVisibilityChangeListener;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CollapsedStatusBarViewBinderImpl$bind$1 collapsedStatusBarViewBinderImpl$bind$1 = new CollapsedStatusBarViewBinderImpl$bind$1(this.$viewModel, this.$listener, (Continuation) obj3);
        collapsedStatusBarViewBinderImpl$bind$1.L$0 = (LifecycleOwner) obj;
        return collapsedStatusBarViewBinderImpl$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            C33491 c33491 = new C33491(this.$viewModel, this.$listener, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c33491, this) == coroutineSingletons) {
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
