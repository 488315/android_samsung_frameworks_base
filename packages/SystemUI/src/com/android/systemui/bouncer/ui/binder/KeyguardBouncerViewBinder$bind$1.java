package com.android.systemui.bouncer.ui.binder;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda4;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3;
import com.android.systemui.bouncer.shared.model.BouncerShowMessageModel;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.log.BouncerLogger;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

final class KeyguardBouncerViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BouncerLogger $bouncerLogger;
    final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
    final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
    final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
    final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
    final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
    final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BouncerLogger $bouncerLogger;
        final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
        final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
        final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
        final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
        final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
        final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00551 extends SuspendLambda implements Function2 {
            final /* synthetic */ BouncerLogger $bouncerLogger;
            final /* synthetic */ BouncerMessageInteractor $bouncerMessageInteractor;
            final /* synthetic */ KeyguardMessageAreaController.Factory $messageAreaControllerFactory;
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            public C00551(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
                this.$securityContainerController = keyguardSecSecurityContainerController;
                this.$bouncerLogger = bouncerLogger;
                this.$bouncerMessageInteractor = bouncerMessageInteractor;
                this.$messageAreaControllerFactory = factory;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00551(this.$viewModel, this.$view, this.$securityContainerController, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00551) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isShowing;
                    final ViewGroup viewGroup = this.$view;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    final BouncerLogger bouncerLogger = this.$bouncerLogger;
                    final BouncerMessageInteractor bouncerMessageInteractor = this.$bouncerMessageInteractor;
                    final KeyguardMessageAreaController.Factory factory = this.$messageAreaControllerFactory;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            viewGroup.setVisibility(booleanValue ? 0 : 4);
                            final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = keyguardSecSecurityContainerController;
                            if (booleanValue) {
                                keyguardSecSecurityContainerController2.prepareToShow();
                                final BouncerLogger bouncerLogger2 = bouncerLogger;
                                final BouncerMessageInteractor bouncerMessageInteractor2 = bouncerMessageInteractor;
                                final KeyguardMessageAreaController.Factory factory2 = factory;
                                keyguardSecSecurityContainerController2.reinflateViewFlipper(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1.1
                                    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                                    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = KeyguardSecSecurityContainerController.this;
                                        keyguardSecSecurityContainerController3.getClass();
                                        keyguardSecSecurityContainerController3.showPrimarySecurityScreen();
                                        ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController3.mViewMediatorCallback;
                                        CharSequence consumeCustomMessage = viewMediatorCallback.consumeCustomMessage();
                                        if (TextUtils.isEmpty(consumeCustomMessage)) {
                                            int bouncerPromptReason = viewMediatorCallback.getBouncerPromptReason();
                                            if (bouncerPromptReason == 5) {
                                                keyguardSecSecurityContainerController3.getClass();
                                                Log.i("KeyguardSecSecurityContainer", "return, biometric lockout");
                                            } else if (keyguardSecSecurityContainerController3.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                                                if (bouncerPromptReason != 0) {
                                                    KeyguardSecPatternView$$ExternalSyntheticOutline0.m(bouncerPromptReason, "Strong auth required, reason: ", "KeyguardSecurityContainer");
                                                }
                                                keyguardSecSecurityContainerController3.getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4(bouncerPromptReason));
                                            }
                                        } else {
                                            keyguardSecSecurityContainerController3.showMessage(consumeCustomMessage, null, false);
                                        }
                                        if (!LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                                            keyguardSecSecurityContainerController3.appear();
                                        } else if (!((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened() || keyguardSecSecurityContainerController3.mIsDisappearAnimation) {
                                            keyguardSecSecurityContainerController3.appear();
                                        }
                                        keyguardSecSecurityContainerController3.onResume(1);
                                        BouncerLogger bouncerLogger3 = bouncerLogger2;
                                        bouncerLogger3.getClass();
                                        bouncerLogger3.buffer.log("BouncerLog", LogLevel.DEBUG, "Binding BouncerMessageView", null);
                                        keyguardInputViewController.bindMessageView(bouncerMessageInteractor2, factory2, bouncerLogger3);
                                    }
                                });
                                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                                    ViewGroup viewGroup2 = viewGroup;
                                    if (viewGroup2 instanceof KeyguardBouncerContainer) {
                                        ((KeyguardBouncerContainer) viewGroup2).checkBackInvokedCallback(true);
                                    }
                                }
                            } else {
                                keyguardSecSecurityContainerController2.onBouncerVisibilityChanged();
                                keyguardSecSecurityContainerController2.mUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
                                keyguardSecSecurityContainerController2.setOnDismissAction(null, null);
                                keyguardSecSecurityContainerController2.reset$1();
                                keyguardSecSecurityContainerController2.onPause();
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.bouncerShowMessage;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.10.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj2;
                            KeyguardSecSecurityContainerController.this.showMessage(bouncerShowMessageModel.message, bouncerShowMessageModel.colorStateList, false);
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ SelectedUserInteractor $selectedUserInteractor;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            public AnonymousClass11(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
                this.$selectedUserInteractor = selectedUserInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.$viewModel, this.$securityContainerController, this.$selectedUserInteractor, continuation);
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
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.keyguardAuthenticated;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    final SelectedUserInteractor selectedUserInteractor = this.$selectedUserInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.11.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            KeyguardSecSecurityContainerController.this.mKeyguardSecurityCallback.finish(selectedUserInteractor.getSelectedUserId(false));
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            public AnonymousClass12(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass12(this.$viewModel, this.$view, continuation);
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
                    KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    final ViewGroup viewGroup = this.$view;
                    final Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.1
                        {
                            super(0);
                        }

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

                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L5e
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    java.lang.Boolean r5 = (java.lang.Boolean) r5
                                    boolean r5 = r5.booleanValue()
                                    kotlin.jvm.functions.Function0 r6 = r4.$systemUiVisibility$inlined
                                    java.lang.Object r6 = r6.invoke()
                                    java.lang.Number r6 = (java.lang.Number) r6
                                    int r6 = r6.intValue()
                                    if (r5 == 0) goto L4b
                                    r5 = -4194305(0xffffffffffbfffff, float:NaN)
                                    r5 = r5 & r6
                                    goto L4e
                                L4b:
                                    r5 = 4194304(0x400000, float:5.877472E-39)
                                    r5 = r5 | r6
                                L4e:
                                    java.lang.Integer r6 = new java.lang.Integer
                                    r6.<init>(r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r6, r0)
                                    if (r4 != r1) goto L5e
                                    return r1
                                L5e:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function0), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final ViewGroup viewGroup2 = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.2
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13, reason: invalid class name */
        final class AnonymousClass13 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            public AnonymousClass13(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass13(this.$viewModel, this.$securityContainerController, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.isInflated;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.13.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (((Boolean) obj2).booleanValue()) {
                                KeyguardSecSecurityContainerController.this.reinflateViewFlipper(null);
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
                    PrimaryBouncerInteractor$special$$inlined$map$2 primaryBouncerInteractor$special$$inlined$map$2 = this.$viewModel.startingToHide;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.onStartingToHide();
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$viewModel.startDisappearAnimation;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.startDisappearAnimation((Runnable) obj2);
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.setExpansion(((Number) obj2).floatValue());
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            int label;

            public AnonymousClass5(PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation continuation) {
                super(2, continuation);
                this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, continuation);
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
                    KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.$primaryBouncerToGoneTransitionViewModel.bouncerAlpha;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.setAlpha(((Number) obj2).floatValue());
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            public AnonymousClass6(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$securityContainerController, this.$view, continuation);
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
                    final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L49
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    r6 = r5
                                    java.lang.Number r6 = (java.lang.Number) r6
                                    float r6 = r6.floatValue()
                                    r2 = 0
                                    int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                                    if (r6 != 0) goto L49
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L49
                                    return r1
                                L49:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.6.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Number) obj2).floatValue();
                            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
                            keyguardSecSecurityContainerController2.onResume(1);
                            viewGroup.announceForAccessibility(keyguardSecSecurityContainerController2.getTitle());
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    PrimaryBouncerInteractor$special$$inlined$map$3 primaryBouncerInteractor$special$$inlined$map$3 = this.$viewModel.isInteractable;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            KeyguardSecSecurityContainerController.this.setInteractable();
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$viewModel.keyguardPosition;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.updateKeyguardPosition(((Number) obj2).floatValue());
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

        /* renamed from: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

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
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    PrimaryBouncerInteractor$special$$inlined$filter$3 primaryBouncerInteractor$special$$inlined$filter$3 = keyguardBouncerViewModel.updateResources;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder.bind.1.1.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            KeyguardSecSecurityContainerController.this.updateResources$1();
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

        public AnonymousClass1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBouncerViewModel;
            this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
            this.$view = viewGroup;
            this.$securityContainerController = keyguardSecSecurityContainerController;
            this.$bouncerLogger = bouncerLogger;
            this.$bouncerMessageInteractor = bouncerMessageInteractor;
            this.$messageAreaControllerFactory = factory;
            this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
            this.$selectedUserInteractor = selectedUserInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineContext coroutineContext;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    this.$viewModel.setBouncerViewDelegate(this.$delegate);
                    if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                        DefaultScheduler defaultScheduler = Dispatchers.Default;
                        coroutineContext = MainDispatcherLoader.dispatcher.getImmediate();
                    } else {
                        coroutineContext = EmptyCoroutineContext.INSTANCE;
                    }
                    BuildersKt.launch$default(coroutineScope, coroutineContext, null, new C00551(this.$viewModel, this.$view, this.$securityContainerController, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, null), 2);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$securityContainerController, null), 3);
                    DefaultScheduler defaultScheduler2 = Dispatchers.Default;
                    BuildersKt.launch$default(coroutineScope, MainDispatcherLoader.dispatcher.getImmediate(), null, new AnonymousClass3(this.$viewModel, this.$securityContainerController, null), 2);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$securityContainerController, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$securityContainerController, this.$selectedUserInteractor, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$securityContainerController, null), 3);
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
    }

    public KeyguardBouncerViewBinder$bind$1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, SelectedUserInteractor selectedUserInteractor, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBouncerViewModel;
        this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
        this.$view = viewGroup;
        this.$securityContainerController = keyguardSecSecurityContainerController;
        this.$bouncerLogger = bouncerLogger;
        this.$bouncerMessageInteractor = bouncerMessageInteractor;
        this.$messageAreaControllerFactory = factory;
        this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.$selectedUserInteractor = selectedUserInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBouncerViewBinder$bind$1 keyguardBouncerViewBinder$bind$1 = new KeyguardBouncerViewBinder$bind$1(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, (Continuation) obj3);
        keyguardBouncerViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardBouncerViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$bouncerLogger, this.$bouncerMessageInteractor, this.$messageAreaControllerFactory, this.$primaryBouncerToGoneTransitionViewModel, this.$selectedUserInteractor, null);
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
