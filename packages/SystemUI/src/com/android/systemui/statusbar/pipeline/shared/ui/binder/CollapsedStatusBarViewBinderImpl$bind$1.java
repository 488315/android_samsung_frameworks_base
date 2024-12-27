package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModelImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModelImpl$special$$inlined$map$1;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class CollapsedStatusBarViewBinderImpl$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ StatusBarVisibilityChangeListener $listener;
    final /* synthetic */ View $view;
    final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CollapsedStatusBarViewBinderImpl this$0;

    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ StatusBarVisibilityChangeListener $listener;
        final /* synthetic */ View $view;
        final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CollapsedStatusBarViewBinderImpl this$0;

        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02421 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarVisibilityChangeListener $listener;
            final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
            int label;

            public C02421(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = collapsedStatusBarViewModel;
                this.$listener = statusBarVisibilityChangeListener;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02421(this.$viewModel, this.$listener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02421) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            ((Boolean) obj2).getClass();
                            CollapsedStatusBarFragment.AnonymousClass5 anonymousClass5 = (CollapsedStatusBarFragment.AnonymousClass5) StatusBarVisibilityChangeListener.this;
                            anonymousClass5.getClass();
                            int i2 = CollapsedStatusBarFragment.$r8$clinit;
                            CollapsedStatusBarFragment.this.updateStatusBarVisibilities(true);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarVisibilityChangeListener $listener;
            final /* synthetic */ CollapsedStatusBarViewModel $viewModel;
            int label;

            public AnonymousClass2(CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation continuation) {
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
                    CollapsedStatusBarViewModelImpl$special$$inlined$map$1 collapsedStatusBarViewModelImpl$special$$inlined$map$1 = ((CollapsedStatusBarViewModelImpl) this.$viewModel).transitionFromLockscreenToDreamStartedEvent;
                    final StatusBarVisibilityChangeListener statusBarVisibilityChangeListener = this.$listener;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CollapsedStatusBarFragment.AnonymousClass5 anonymousClass5 = (CollapsedStatusBarFragment.AnonymousClass5) StatusBarVisibilityChangeListener.this;
                            anonymousClass5.getClass();
                            int i2 = CollapsedStatusBarFragment.$r8$clinit;
                            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                            collapsedStatusBarFragment.getClass();
                            collapsedStatusBarFragment.mStatusBarVisibilityExt.postUpdateStatusBarVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (collapsedStatusBarViewModelImpl$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

        public AnonymousClass1(View view, CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, CollapsedStatusBarViewBinderImpl collapsedStatusBarViewBinderImpl, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$viewModel = collapsedStatusBarViewModel;
            this.$listener = statusBarVisibilityChangeListener;
            this.this$0 = collapsedStatusBarViewBinderImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, this.$listener, this.this$0, continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new C02421(this.$viewModel, this.$listener, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$listener, null), 3);
            Flags.notificationsLiveDataStoreRefactor();
            Flags.statusBarScreenSharingChips();
            return Unit.INSTANCE;
        }
    }

    public CollapsedStatusBarViewBinderImpl$bind$1(View view, CollapsedStatusBarViewModel collapsedStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, CollapsedStatusBarViewBinderImpl collapsedStatusBarViewBinderImpl, Continuation continuation) {
        super(3, continuation);
        this.$view = view;
        this.$viewModel = collapsedStatusBarViewModel;
        this.$listener = statusBarVisibilityChangeListener;
        this.this$0 = collapsedStatusBarViewBinderImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CollapsedStatusBarViewBinderImpl$bind$1 collapsedStatusBarViewBinderImpl$bind$1 = new CollapsedStatusBarViewBinderImpl$bind$1(this.$view, this.$viewModel, this.$listener, this.this$0, (Continuation) obj3);
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, this.$listener, this.this$0, null);
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
