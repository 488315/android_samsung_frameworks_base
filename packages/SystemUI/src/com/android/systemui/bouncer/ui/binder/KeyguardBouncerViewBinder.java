package com.android.systemui.bouncer.ui.binder;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda4;
import com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda5;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.plugins.AuthContextPlugins;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3;
import com.android.systemui.bouncer.shared.model.BouncerShowMessageModel;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class KeyguardBouncerViewBinder {
    static {
        new KeyguardBouncerViewBinder();
    }

    private KeyguardBouncerViewBinder() {
    }

    public static final void bind(ViewGroup viewGroup, KeyguardBouncerViewModel keyguardBouncerViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, KeyguardBouncerComponent.Factory factory, KeyguardMessageAreaController.Factory factory2, BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, SelectedUserInteractor selectedUserInteractor) {
        KeyguardSecSecurityContainerController securityContainerController = ((DaggerReferenceGlobalRootComponent.KeyguardBouncerComponentImpl) factory.create(viewGroup)).getSecurityContainerController();
        securityContainerController.init();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(keyguardBouncerViewModel, new KeyguardBouncerViewBinder$bind$delegate$1(securityContainerController, selectedUserInteractor), null, viewGroup, securityContainerController, glanceableHubToPrimaryBouncerTransitionViewModel, bouncerLogger, bouncerMessageInteractor, factory2, primaryBouncerToGoneTransitionViewModel, selectedUserInteractor, null));
    }

    /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ BouncerLogger $bouncerLogger;
        final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
        final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
        final /* synthetic */ GlanceableHubToPrimaryBouncerTransitionViewModel $glanceableHubToPrimaryBouncerTransitionViewModel;
        final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
        final /* synthetic */ AuthContextPlugins $plugins;
        final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
        final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
        final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, AuthContextPlugins authContextPlugins, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardBouncerViewModel;
            this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
            this.$view = viewGroup;
            this.$securityContainerController = keyguardSecSecurityContainerController;
            this.$glanceableHubToPrimaryBouncerTransitionViewModel = glanceableHubToPrimaryBouncerTransitionViewModel;
            this.$bouncerLogger = bouncerLogger;
            this.$bouncerMessageInteractor = bouncerMessageInteractor;
            this.$messageAreaControllerFactory = factory;
            this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
            this.$selectedUserInteractor = selectedUserInteractor;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$delegate, null, this.$view, this.$securityContainerController, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C01251 c01251 = new C01251(this.$viewModel, this.$delegate, null, this.$view, this.$securityContainerController, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c01251, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C01251 extends SuspendLambda implements Function2 {
            final /* synthetic */ BouncerLogger $bouncerLogger;
            final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
            final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
            final /* synthetic */ GlanceableHubToPrimaryBouncerTransitionViewModel $glanceableHubToPrimaryBouncerTransitionViewModel;
            final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
            final /* synthetic */ AuthContextPlugins $plugins;
            final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10, reason: invalid class name */
            final class AnonymousClass10 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass10(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass10(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                        PrimaryBouncerInteractor$special$$inlined$filter$3 primaryBouncerInteractor$special$$inlined$filter$3 = keyguardBouncerViewModel.updateResources;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.10.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                keyguardSecSecurityContainerController.updateResources$1();
                                KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository;
                                keyguardBouncerRepositoryImpl._resourceUpdateRequests.updateState(null, Boolean.FALSE);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (primaryBouncerInteractor$special$$inlined$filter$3.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11, reason: invalid class name */
            final class AnonymousClass11 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass11(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass11(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.bouncerShowMessage;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.11.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj2;
                                keyguardSecSecurityContainerController.showMessage(bouncerShowMessageModel.message, bouncerShowMessageModel.colorStateList, false);
                                ((KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository)._showMessage.setValue(null);
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12, reason: invalid class name */
            final class AnonymousClass12 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass12(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                    this.$selectedUserInteractor = selectedUserInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass12(this.$viewModel, this.$securityContainerController, this.$selectedUserInteractor, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass12) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.keyguardAuthenticated;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        final SelectedUserInteractor selectedUserInteractor = this.$selectedUserInteractor;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                keyguardSecSecurityContainerController.mKeyguardSecurityCallback.finish(selectedUserInteractor.getSelectedUserId());
                                ((KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository)._keyguardAuthenticatedBiometrics.setValue(null);
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13, reason: invalid class name */
            final class AnonymousClass13 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass13(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass13(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass13) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                        final ViewGroup viewGroup = this.$view;
                        final Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return Integer.valueOf(viewGroup.getSystemUiVisibility());
                            }
                        };
                        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.interactor.isBackButtonEnabled;
                        Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ Function0 $systemUiVisibility$inlined;
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
                                    int label;
                                    /* synthetic */ Object result;

                                    public AnonymousClass1(Continuation continuation) {
                                        super(continuation);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        this.result = obj;
                                        this.label |= Integer.MIN_VALUE;
                                        return AnonymousClass2.this.emit(null, this);
                                    }
                                }

                                public AnonymousClass2(FlowCollector flowCollector, Function0 function0) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.$systemUiVisibility$inlined = function0;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i = anonymousClass1.label;
                                        if ((i & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i2 = anonymousClass1.label;
                                    if (i2 == 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                                        int iIntValue = ((Number) this.$systemUiVisibility$inlined.invoke()).intValue();
                                        Integer num = new Integer(zBooleanValue ? (-4194305) & iIntValue : 4194304 | iIntValue);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i2 != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, function0), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        };
                        final ViewGroup viewGroup2 = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.13.2
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                viewGroup2.setSystemUiVisibility(((Number) obj2).intValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$14, reason: invalid class name */
            final class AnonymousClass14 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass14(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass14(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass14) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ReadonlyStateFlow readonlyStateFlow = this.$viewModel.isInflated;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.14.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) throws InterruptedException {
                                if (((Boolean) obj2).booleanValue()) {
                                    KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = keyguardSecSecurityContainerController;
                                    KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = keyguardSecSecurityContainerController2.mSecurityViewFlipperController;
                                    keyguardSecurityViewFlipperController.clearViews();
                                    keyguardSecurityViewFlipperController.getSecurityView(keyguardSecSecurityContainerController2.mCurrentSecurityMode, keyguardSecSecurityContainerController2.mKeyguardSecurityCallback, new KeyguardSecurityContainerController$$ExternalSyntheticLambda5(keyguardSecSecurityContainerController2, 0));
                                }
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
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$viewModel, this.$securityContainerController, continuation);
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
                        ReadonlySharedFlow readonlySharedFlow = this.$viewModel.reset;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).booleanValue();
                                keyguardSecSecurityContainerController.reset$1();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$viewModel, this.$securityContainerController, continuation);
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
                        PrimaryBouncerInteractor$special$$inlined$map$2 primaryBouncerInteractor$special$$inlined$map$2 = this.$viewModel.startingToHide;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                keyguardSecSecurityContainerController.onStartingToHide();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (primaryBouncerInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4, reason: invalid class name */
            final class AnonymousClass4 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass4(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass4(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$viewModel.startDisappearAnimation;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.4.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                keyguardSecSecurityContainerController.startDisappearAnimation((Runnable) obj2);
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.5.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                keyguardSecSecurityContainerController.setExpansion(((Number) obj2).floatValue());
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
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.$primaryBouncerToGoneTransitionViewModel.bouncerAlpha;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.6.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                keyguardSecSecurityContainerController.setAlpha(((Number) obj2).floatValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7, reason: invalid class name */
            final class AnonymousClass7 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass7(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass7(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                        Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1

                            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
                                    Object L$1;
                                    int label;
                                    /* synthetic */ Object result;

                                    public AnonymousClass1(Continuation continuation) {
                                        super(continuation);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        this.result = obj;
                                        this.label |= Integer.MIN_VALUE;
                                        return AnonymousClass2.this.emit(null, this);
                                    }
                                }

                                public AnonymousClass2(FlowCollector flowCollector) {
                                    this.$this_unsafeFlow = flowCollector;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i = anonymousClass1.label;
                                        if ((i & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i2 = anonymousClass1.label;
                                    if (i2 == 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        if (((Number) obj).floatValue() == 0.0f) {
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        }
                                    } else {
                                        if (i2 != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        };
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.7.2
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Number) obj2).floatValue();
                                keyguardSecSecurityContainerController.onResume(1);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8, reason: invalid class name */
            final class AnonymousClass8 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass8(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass8(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        PrimaryBouncerInteractor$special$$inlined$map$3 primaryBouncerInteractor$special$$inlined$map$3 = this.$viewModel.isInteractable;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.8.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                keyguardSecSecurityContainerController.setInteractable();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (primaryBouncerInteractor$special$$inlined$map$3.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9, reason: invalid class name */
            final class AnonymousClass9 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass9(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass9(this.$viewModel, this.$securityContainerController, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$viewModel.keyguardPosition;
                        final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.9.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                keyguardSecSecurityContainerController.updateKeyguardPosition(((Number) obj2).floatValue());
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
            public C01251(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, AuthContextPlugins authContextPlugins, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
                this.$view = viewGroup;
                this.$securityContainerController = keyguardSecSecurityContainerController;
                this.$glanceableHubToPrimaryBouncerTransitionViewModel = glanceableHubToPrimaryBouncerTransitionViewModel;
                this.$bouncerLogger = bouncerLogger;
                this.$bouncerMessageInteractor = bouncerMessageInteractor;
                this.$messageAreaControllerFactory = factory;
                this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
                this.$selectedUserInteractor = selectedUserInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01251 c01251 = new C01251(this.$viewModel, this.$delegate, null, this.$view, this.$securityContainerController, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, continuation);
                c01251.L$0 = obj;
                return c01251;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01251) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineContext context;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        this.$viewModel.setBouncerViewDelegate(this.$delegate);
                        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                        if (z) {
                            DefaultScheduler defaultScheduler = Dispatchers.Default;
                            context = MainDispatcherLoader.dispatcher.immediate;
                        } else {
                            context = getContext();
                        }
                        CoroutineTracingKt.launchTraced$default(coroutineScope, context, null, new C01261(this.$viewModel, this.$view, this.$securityContainerController, null, this.$delegate, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, null), 5);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, z ? MainDispatcherLoader.dispatcher.immediate : getContext(), null, new AnonymousClass4(this.$viewModel, this.$securityContainerController, null), 5);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$securityContainerController, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$securityContainerController, this.$selectedUserInteractor, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass14(this.$viewModel, this.$securityContainerController, null), 7);
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
                    this.$viewModel.setBouncerViewDelegate(null);
                    throw th;
                }
            }

            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C01261 extends SuspendLambda implements Function2 {
                final /* synthetic */ BouncerLogger $bouncerLogger;
                final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
                final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
                final /* synthetic */ GlanceableHubToPrimaryBouncerTransitionViewModel $glanceableHubToPrimaryBouncerTransitionViewModel;
                final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
                final /* synthetic */ AuthContextPlugins $plugins;
                final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardBouncerViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01261(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, AuthContextPlugins authContextPlugins, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBouncerViewModel;
                    this.$view = viewGroup;
                    this.$securityContainerController = keyguardSecSecurityContainerController;
                    this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
                    this.$glanceableHubToPrimaryBouncerTransitionViewModel = glanceableHubToPrimaryBouncerTransitionViewModel;
                    this.$bouncerLogger = bouncerLogger;
                    this.$bouncerMessageInteractor = bouncerMessageInteractor;
                    this.$messageAreaControllerFactory = factory;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C01261(this.$viewModel, this.$view, this.$securityContainerController, null, this.$delegate, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isShowing;
                        C01271 c01271 = new C01271(this.$view, this.$securityContainerController, null, this.$delegate, this.$glanceableHubToPrimaryBouncerTransitionViewModel, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory);
                        this.label = 1;
                        if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(c01271, this) == coroutineSingletons) {
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

                /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
                public final class C01271 implements FlowCollector {
                    public final /* synthetic */ BouncerLogger $bouncerLogger;
                    public final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
                    public final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
                    public final /* synthetic */ GlanceableHubToPrimaryBouncerTransitionViewModel $glanceableHubToPrimaryBouncerTransitionViewModel;
                    public final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
                    public final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
                    public final /* synthetic */ ViewGroup $view;

                    public C01271(ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, AuthContextPlugins authContextPlugins, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory) {
                        this.$view = viewGroup;
                        this.$securityContainerController = keyguardSecSecurityContainerController;
                        this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
                        this.$glanceableHubToPrimaryBouncerTransitionViewModel = glanceableHubToPrimaryBouncerTransitionViewModel;
                        this.$bouncerLogger = bouncerLogger;
                        this.$bouncerMessageInteractor = bouncerMessageInteractor;
                        this.$messageAreaControllerFactory = factory;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(boolean z, Continuation continuation) {
                        KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1 keyguardBouncerViewBinder$bind$1$1$1$1$emit$1;
                        if (continuation instanceof KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1) {
                            keyguardBouncerViewBinder$bind$1$1$1$1$emit$1 = (KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1) continuation;
                            int i = keyguardBouncerViewBinder$bind$1$1$1$1$emit$1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                keyguardBouncerViewBinder$bind$1$1$1$1$emit$1.label = i - Integer.MIN_VALUE;
                            } else {
                                keyguardBouncerViewBinder$bind$1$1$1$1$emit$1 = new KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1(this, continuation);
                            }
                        }
                        Object obj = keyguardBouncerViewBinder$bind$1$1$1$1$emit$1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = keyguardBouncerViewBinder$bind$1$1$1$1$emit$1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj);
                            this.$view.setVisibility(z ? 0 : 4);
                            final View viewFindViewById = this.$view.findViewById(R.id.keyguard_security_container);
                            Log.d("KeyguardBouncerViewBinder", "PrimaryBouncer isShowing : " + z + " , alpha : " + (viewFindViewById != null ? new Float(viewFindViewById.getAlpha()) : null) + " ");
                            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                            if (z) {
                                keyguardSecSecurityContainerController.prepareToShow();
                                final GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel = this.$glanceableHubToPrimaryBouncerTransitionViewModel;
                                final BouncerLogger bouncerLogger = this.$bouncerLogger;
                                final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = this.$securityContainerController;
                                final KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1 = this.$delegate;
                                final BouncerMessageInteractor bouncerMessageInteractor = this.$bouncerMessageInteractor;
                                final KeyguardMessageAreaController.Factory factory = this.$messageAreaControllerFactory;
                                keyguardSecSecurityContainerController2.getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1.1
                                    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                                    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = keyguardSecSecurityContainerController2;
                                        keyguardSecSecurityContainerController3.getClass();
                                        keyguardSecSecurityContainerController3.showPrimarySecurityScreen();
                                        ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController3.mViewMediatorCallback;
                                        CharSequence charSequenceConsumeCustomMessage = viewMediatorCallback.consumeCustomMessage();
                                        if (TextUtils.isEmpty(charSequenceConsumeCustomMessage)) {
                                            final int bouncerPromptReason = viewMediatorCallback.getBouncerPromptReason();
                                            if (bouncerPromptReason == 5) {
                                                Log.i("KeyguardSecSecurityContainer", "return, biometric lockout");
                                            } else if (keyguardSecSecurityContainerController3.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                                                if (bouncerPromptReason != 0) {
                                                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(bouncerPromptReason, "Strong auth required, reason: ", "KeyguardSecurityContainer");
                                                }
                                                keyguardSecSecurityContainerController3.getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda12
                                                    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                                                    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController2) {
                                                        keyguardInputViewController2.showPromptReason(bouncerPromptReason);
                                                    }
                                                });
                                            }
                                        } else {
                                            keyguardSecSecurityContainerController3.showMessage(charSequenceConsumeCustomMessage, null, false);
                                        }
                                        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                                            if (!((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened() || keyguardSecSecurityContainerController3.mIsDisappearAnimation) {
                                                keyguardSecSecurityContainerController3.appear();
                                            } else {
                                                keyguardSecSecurityContainerController3.getCurrentSecurityController(new KeyguardSecSecurityContainerController$$ExternalSyntheticLambda4());
                                            }
                                        } else if (SecurityUtils.checkFullscreenBouncer(keyguardBouncerViewBinder$bind$delegate$1.$securityContainerController.mCurrentSecurityMode)) {
                                            View view = viewFindViewById;
                                            if (view != null && Float.compare(view.getAlpha(), 1.0f) != 0) {
                                                Log.d("KeyguardBouncerViewBinder", "restoreAppearance : keyguardSecurityContainer.alpha to 1.0f");
                                                view.setAlpha(1.0f);
                                            }
                                            keyguardSecSecurityContainerController3.appear();
                                        } else {
                                            glanceableHubToPrimaryBouncerTransitionViewModel.communalSettingsInteractor.isV2FlagEnabled();
                                            keyguardSecSecurityContainerController3.appear();
                                        }
                                        keyguardSecSecurityContainerController3.onResume(1);
                                        BouncerLogger bouncerLogger2 = bouncerLogger;
                                        bouncerLogger2.getClass();
                                        LogBuffer.log$default(bouncerLogger2.buffer, "BouncerLog", LogLevel.DEBUG, "Binding BouncerMessageView");
                                        keyguardInputViewController.bindMessageView(bouncerMessageInteractor, factory, bouncerLogger2);
                                    }
                                });
                                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                                    ViewGroup viewGroup = this.$view;
                                    if (viewGroup instanceof KeyguardBouncerContainer) {
                                        ((KeyguardBouncerContainer) viewGroup).checkBackInvokedCallback(true);
                                    }
                                }
                            } else {
                                keyguardSecSecurityContainerController.onBouncerVisibilityChanged();
                                keyguardSecSecurityContainerController.mUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
                                keyguardSecSecurityContainerController.setOnDismissAction(null, null);
                                keyguardSecSecurityContainerController.reset$1();
                                keyguardSecSecurityContainerController.onPause();
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            if (keyguardBouncerViewBinder$bind$1$1$1$1$emit$1.L$0 != null) {
                                throw new ClassCastException();
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        return Unit.INSTANCE;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                        return emit(((Boolean) obj).booleanValue(), continuation);
                    }
                }
            }
        }
    }
}
