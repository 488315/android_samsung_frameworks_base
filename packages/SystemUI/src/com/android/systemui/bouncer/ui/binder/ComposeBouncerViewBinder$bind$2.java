package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ComposeBouncerViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
    final /* synthetic */ KeyguardInteractor $keyguardInteractor;
    final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
    final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
        final /* synthetic */ KeyguardInteractor $keyguardInteractor;
        final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
        final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00671 extends SuspendLambda implements Function2 {
            final /* synthetic */ AuthenticationInteractor $authenticationInteractor;
            final /* synthetic */ KeyguardInteractor $keyguardInteractor;
            final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
            final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ ViewMediatorCallback $viewMediatorCallback;
            /* synthetic */ boolean Z$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00671(ViewGroup viewGroup, KeyguardInteractor keyguardInteractor, ViewMediatorCallback viewMediatorCallback, PrimaryBouncerInteractor primaryBouncerInteractor, AuthenticationInteractor authenticationInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
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
                C00671 c00671 = new C00671(this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$authenticationInteractor, this.$selectedUserInteractor, continuation);
                c00671.Z$0 = ((Boolean) obj).booleanValue();
                return c00671;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C00671) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
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
                        Object emit = ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._reset.emit(Boolean.TRUE, this);
                        if (emit != coroutineSingletons) {
                            emit = Unit.INSTANCE;
                        }
                        if (emit != coroutineSingletons) {
                            emit = Unit.INSTANCE;
                        }
                        if (emit == coroutineSingletons) {
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
                C00671 c00671 = new C00671(this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, authenticationInteractor, this.$selectedUserInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlySharedFlow, c00671, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardInteractor $keyguardInteractor;
        final /* synthetic */ PrimaryBouncerInteractor $legacyInteractor;
        final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public AnonymousClass2(PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardInteractor keyguardInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
            super(2, continuation);
            this.$legacyInteractor = primaryBouncerInteractor;
            this.$keyguardInteractor = keyguardInteractor;
            this.$selectedUserInteractor = selectedUserInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$legacyInteractor, this.$keyguardInteractor, this.$selectedUserInteractor, continuation);
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
                Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(this.$legacyInteractor.isShowing, this.$keyguardInteractor.isKeyguardDismissible, AnonymousClass3.INSTANCE);
                final PrimaryBouncerInteractor primaryBouncerInteractor = this.$legacyInteractor;
                final SelectedUserInteractor selectedUserInteractor = this.$selectedUserInteractor;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.2.2.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                        boolean booleanValue2 = ((Boolean) pair.component2()).booleanValue();
                        if (booleanValue && booleanValue2) {
                            PrimaryBouncerInteractor.this.notifyUserRequestedBouncerWhenAlreadyAuthenticated(selectedUserInteractor.getSelectedUserId());
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (sample.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        PrimaryBouncerInteractor.this.hide();
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
    public ComposeBouncerViewBinder$bind$2(AuthenticationInteractor authenticationInteractor, ViewGroup viewGroup, KeyguardInteractor keyguardInteractor, ViewMediatorCallback viewMediatorCallback, PrimaryBouncerInteractor primaryBouncerInteractor, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
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
        ComposeBouncerViewBinder$bind$2 composeBouncerViewBinder$bind$2 = new ComposeBouncerViewBinder$bind$2(this.$authenticationInteractor, this.$view, this.$keyguardInteractor, this.$viewMediatorCallback, this.$legacyInteractor, this.$selectedUserInteractor, continuation);
        composeBouncerViewBinder$bind$2.L$0 = obj;
        return composeBouncerViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ComposeBouncerViewBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$legacyInteractor, this.$keyguardInteractor, this.$selectedUserInteractor, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$legacyInteractor, null), 3);
        return Unit.INSTANCE;
    }
}
