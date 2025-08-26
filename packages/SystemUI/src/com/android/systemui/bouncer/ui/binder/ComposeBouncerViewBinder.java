package com.android.systemui.bouncer.ui.binder;

import android.view.View;
import android.view.ViewGroup;
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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.composable.BouncerContainerKt;
import com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.lifecycle.WindowLifecycleState;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* loaded from: classes.dex */
public final class ComposeBouncerViewBinder {
    public static final ComposeBouncerViewBinder INSTANCE = new ComposeBouncerViewBinder();
    public static StandaloneCoroutine persistentBouncerJob;

    /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
        final /* synthetic */ KeyguardInteractor $keyguardInteractor;
        final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
        final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
            final /* synthetic */ KeyguardInteractor $keyguardInteractor;
            final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
            final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
            int label;

            /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C01231 extends SuspendLambda implements Function2 {
                final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
                final /* synthetic */ KeyguardInteractor $keyguardInteractor;
                final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
                final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
                /* synthetic */ boolean Z$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01231(ViewGroup viewGroup, KeyguardInteractor keyguardInteractor, ViewMediatorCallback viewMediatorCallback, PrimaryBouncerInteractor primaryBouncerInteractor, AuthenticationInteractor authenticationInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.$view = viewGroup;
                    this.$keyguardInteractor = keyguardInteractor;
                    this.$viewMediatorCallback = viewMediatorCallback;
                    this.$legacyInteractor = primaryBouncerInteractor;
                    this.$authenticationInteractor = authenticationInteractor;
                    this.$selectedUserInteractor = selectedUserInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C01231 c01231 = new C01231(this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$authenticationInteractor, this.$selectedUserInteractor, continuation);
                    c01231.Z$0 = ((Boolean) obj).booleanValue();
                    return c01231;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((C01231) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws Throwable {
                    ActivityStarter.OnDismissAction onDismissAction;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        boolean z = this.Z$0;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onAuthenticationResult : ", "ComposeBouncerViewBinder", z);
                        if (z) {
                            this.$view.removeAllViews();
                            BouncerDismissActionModel bouncerDismissActionModel = ((KeyguardBouncerRepositoryImpl) this.$keyguardInteractor.bouncerRepository).bouncerDismissActionModelForDex;
                            if (bouncerDismissActionModel != null && (onDismissAction = bouncerDismissActionModel.onDismissActionForDex) != null) {
                                onDismissAction.onDismiss();
                            }
                            this.$keyguardInteractor.setDismissActionForDex(null);
                            ViewMediatorCallback viewMediatorCallback = this.$viewMediatorCallback;
                            if (viewMediatorCallback != null) {
                                viewMediatorCallback.keyguardDone(this.$selectedUserInteractor.getSelectedUserId());
                            }
                        } else if (this.$legacyInteractor.isBouncerShowing()) {
                            AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.$authenticationInteractor.repository;
                            long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
                            if ((authenticationRepositoryImpl.clock.elapsedRealtime() < lockoutAttemptDeadline ? Long.valueOf(lockoutAttemptDeadline) : null) != null) {
                                this.$legacyInteractor.keyguardUpdateMonitor.updateSecureLockTimeout(this.$selectedUserInteractor.getSelectedUserId());
                            }
                            PrimaryBouncerInteractor primaryBouncerInteractor = this.$legacyInteractor;
                            this.label = 1;
                            Object objEmit = ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._reset.emit(Boolean.TRUE, this);
                            if (objEmit != coroutineSingletons) {
                                objEmit = Unit.INSTANCE;
                            }
                            if (objEmit != coroutineSingletons) {
                                objEmit = Unit.INSTANCE;
                            }
                            if (objEmit == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
            public AnonymousClass1(AuthenticationInteractor authenticationInteractor, ViewGroup viewGroup, KeyguardInteractor keyguardInteractor, ViewMediatorCallback viewMediatorCallback, PrimaryBouncerInteractor primaryBouncerInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                super(2, continuation);
                this.$authenticationInteractor = authenticationInteractor;
                this.$view = viewGroup;
                this.$keyguardInteractor = keyguardInteractor;
                this.$viewMediatorCallback = viewMediatorCallback;
                this.$legacyInteractor = primaryBouncerInteractor;
                this.$selectedUserInteractor = selectedUserInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$authenticationInteractor, this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$selectedUserInteractor, continuation);
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
                    AuthenticationInteractor authenticationInteractor = this.$authenticationInteractor;
                    ReadonlySharedFlow readonlySharedFlow = authenticationInteractor.onAuthenticationResult;
                    C01231 c01231 = new C01231(this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, authenticationInteractor, this.$selectedUserInteractor, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlySharedFlow, c01231, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$2, reason: invalid class name and collision with other inner class name */
        final class C01242 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardInteractor $keyguardInteractor;
            final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
            final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
            int label;

            /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$2$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                public AnonymousClass3() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    Boolean bool2 = (Boolean) obj2;
                    bool2.booleanValue();
                    return new Pair(bool, bool2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01242(PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardInteractor keyguardInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                super(2, continuation);
                this.$legacyInteractor = primaryBouncerInteractor;
                this.$keyguardInteractor = keyguardInteractor;
                this.$selectedUserInteractor = selectedUserInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01242(this.$legacyInteractor, this.$keyguardInteractor, this.$selectedUserInteractor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01242) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flowSample = com.android.systemui.util.kotlin.FlowKt.sample(this.$legacyInteractor.isShowing, this.$keyguardInteractor.isKeyguardDismissible, AnonymousClass3.INSTANCE);
                    final PrimaryBouncerInteractor primaryBouncerInteractor = this.$legacyInteractor;
                    final SelectedUserInteractor selectedUserInteractor = this.$selectedUserInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.2.2.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                            boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
                            if (zBooleanValue && zBooleanValue2) {
                                primaryBouncerInteractor.notifyUserRequestedBouncerWhenAlreadyAuthenticated(selectedUserInteractor.getSelectedUserId());
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PrimaryBouncerInteractor primaryBouncerInteractor, Continuation continuation) {
                super(2, continuation);
                this.$legacyInteractor = primaryBouncerInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$legacyInteractor, continuation);
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
                    final PrimaryBouncerInteractor primaryBouncerInteractor = this.$legacyInteractor;
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = primaryBouncerInteractor.startingDisappearAnimation;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Runnable) obj2).run();
                            primaryBouncerInteractor.hide();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass2(AuthenticationInteractor authenticationInteractor, ViewGroup viewGroup, KeyguardInteractor keyguardInteractor, ViewMediatorCallback viewMediatorCallback, PrimaryBouncerInteractor primaryBouncerInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
            super(2, continuation);
            this.$authenticationInteractor = authenticationInteractor;
            this.$view = viewGroup;
            this.$keyguardInteractor = keyguardInteractor;
            this.$viewMediatorCallback = viewMediatorCallback;
            this.$legacyInteractor = primaryBouncerInteractor;
            this.$selectedUserInteractor = selectedUserInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$authenticationInteractor, this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$selectedUserInteractor, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$authenticationInteractor, this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$selectedUserInteractor, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new C01242(this.$legacyInteractor, this.$keyguardInteractor, this.$selectedUserInteractor, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$legacyInteractor, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ BouncerContainerViewModel.Factory $bouncerContainerViewModelFactory;
        final /* synthetic */ BouncerDialogFactory $dialogFactory;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ BouncerOverlayContentViewModel.Factory $viewModelFactory;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function3 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ BouncerDialogFactory $dialogFactory;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ BouncerOverlayContentViewModel.Factory $viewModelFactory;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(ViewGroup viewGroup, LifecycleOwner lifecycleOwner, BouncerOverlayContentViewModel.Factory factory, BouncerDialogFactory bouncerDialogFactory, Continuation continuation) {
                super(3, continuation);
                this.$view = viewGroup;
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$viewModelFactory = factory;
                this.$dialogFactory = bouncerDialogFactory;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return new AnonymousClass2(this.$view, this.$$this$repeatWhenAttached, this.$viewModelFactory, this.$dialogFactory, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ViewGroup viewGroup = this.$view;
                        ViewTreeOnBackPressedDispatcherOwner.set(viewGroup, new OnBackPressedDispatcherOwner(this.$$this$repeatWhenAttached, viewGroup) { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.3.2.1
                            public final Lifecycle lifecycle;
                            public final OnBackPressedDispatcher onBackPressedDispatcher;

                            {
                                OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(null, 1, null);
                                onBackPressedDispatcher.setOnBackInvokedDispatcher(viewGroup.getViewRootImpl().getOnBackInvokedDispatcher());
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
                        ViewGroup viewGroup2 = this.$view;
                        ComposeView composeView = new ComposeView(this.$view.getContext(), null, 0, 6, null);
                        final BouncerOverlayContentViewModel.Factory factory = this.$viewModelFactory;
                        final BouncerDialogFactory bouncerDialogFactory = this.$dialogFactory;
                        composeView.setContent(new ComposableLambdaImpl(491646943, true, new Function2() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$2$2$1
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
                                            ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ComposeBouncerViewBinder.kt:146)");
                                        }
                                        BouncerContainerKt.BouncerContainer(factory, bouncerDialogFactory, composer, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }));
                        viewGroup2.addView(composeView);
                        this.label = 1;
                        if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    throw new KotlinNothingValueException();
                } catch (Throwable th) {
                    this.$view.removeAllViews();
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ViewGroup viewGroup, BouncerContainerViewModel.Factory factory, BouncerOverlayContentViewModel.Factory factory2, BouncerDialogFactory bouncerDialogFactory, Continuation continuation) {
            super(3, continuation);
            this.$view = viewGroup;
            this.$bouncerContainerViewModelFactory = factory;
            this.$viewModelFactory = factory2;
            this.$dialogFactory = bouncerDialogFactory;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$view, this.$bouncerContainerViewModelFactory, this.$viewModelFactory, this.$dialogFactory, (Continuation) obj3);
            anonymousClass3.L$0 = (LifecycleOwner) obj;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                WindowLifecycleState windowLifecycleState = WindowLifecycleState.ATTACHED;
                ViewGroup viewGroup = this.$view;
                final BouncerContainerViewModel.Factory factory = this.$bouncerContainerViewModelFactory;
                Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return factory.create();
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(viewGroup, lifecycleOwner, this.$viewModelFactory, this.$dialogFactory, null);
                this.label = 1;
                if (SysUiViewModelKt.viewModel(viewGroup, "ComposeBouncerViewBinder", windowLifecycleState, function0, anonymousClass2, this) == coroutineSingletons) {
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

    private ComposeBouncerViewBinder() {
    }

    public static void bind(final ViewGroup viewGroup, CoroutineScope coroutineScope, PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardInteractor keyguardInteractor, SelectedUserInteractor selectedUserInteractor, BouncerOverlayContentViewModel.Factory factory, BouncerDialogFactory bouncerDialogFactory, BouncerContainerViewModel.Factory factory2, AuthenticationInteractor authenticationInteractor, ViewMediatorCallback viewMediatorCallback) {
        viewGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ComposeInitializer composeInitializer = ComposeInitializer.INSTANCE;
                ViewGroup viewGroup2 = viewGroup;
                composeInitializer.getClass();
                ComposeInitializer.onAttachedToWindow(viewGroup2);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ComposeInitializer composeInitializer = ComposeInitializer.INSTANCE;
                ViewGroup viewGroup2 = viewGroup;
                composeInitializer.getClass();
                ComposeInitializer.onDetachedFromWindow(viewGroup2);
            }
        });
        StandaloneCoroutine standaloneCoroutine = persistentBouncerJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        persistentBouncerJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(authenticationInteractor, viewGroup, keyguardInteractor, viewMediatorCallback, primaryBouncerInteractor, selectedUserInteractor, null), 3);
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass3(viewGroup, factory2, factory, bouncerDialogFactory, null));
    }
}
