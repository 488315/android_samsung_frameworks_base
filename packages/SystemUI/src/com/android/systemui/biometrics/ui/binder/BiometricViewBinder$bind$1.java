package com.android.systemui.biometrics.ui.binder;

import android.animation.Animator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.PromptAuthState;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptSize;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6;
import com.android.systemui.common.ui.view.ViewExtKt$onTouchListener$1;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.VibratorHelper;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BiometricViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AccessibilityManager $accessibilityManager;
    final /* synthetic */ Spaghetti $adapter;
    final /* synthetic */ View $backgroundView;
    final /* synthetic */ Ref$BooleanRef $boundSize;
    final /* synthetic */ Button $cancelButton;
    final /* synthetic */ Button $confirmationButton;
    final /* synthetic */ Button $credentialFallbackButton;
    final /* synthetic */ LinearLayout $customizedViewContainer;
    final /* synthetic */ TextView $descriptionView;
    final /* synthetic */ LottieAnimationView $iconView;
    final /* synthetic */ TextView $indicatorMessageView;
    final /* synthetic */ Animator.AnimatorListener $jankListener;
    final /* synthetic */ Spaghetti.Callback $legacyCallback;
    final /* synthetic */ TextView $logoDescriptionView;
    final /* synthetic */ ImageView $logoView;
    final /* synthetic */ MSDLPlayer $msdlPlayer;
    final /* synthetic */ Button $negativeButton;
    final /* synthetic */ Button $retryButton;
    final /* synthetic */ TextView $subtitleView;
    final /* synthetic */ int $textColorError;
    final /* synthetic */ int $textColorHint;
    final /* synthetic */ TextView $titleView;
    final /* synthetic */ View $udfpsGuidanceView;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ View $view;
    final /* synthetic */ PromptViewModel $viewModel;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(PromptViewModel promptViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$iconView = lottieAnimationView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass6(this.$viewModel, this.$iconView, continuation);
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
                final PromptViewModel promptViewModel = this.$viewModel;
                Flow flow = promptViewModel.hideSensorIcon;
                final LottieAnimationView lottieAnimationView = this.$iconView;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (!((Boolean) obj2).booleanValue()) {
                            int i2 = PromptIconViewBinder.$r8$clinit;
                            PromptIconViewModel promptIconViewModel = promptViewModel.iconViewModel;
                            LottieAnimationView lottieAnimationView2 = LottieAnimationView.this;
                            RepeatWhenAttachedKt.repeatWhenAttached(lottieAnimationView2, EmptyCoroutineContext.INSTANCE, new PromptIconViewBinder$bind$1(promptIconViewModel, lottieAnimationView2, null));
                        }
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        final /* synthetic */ Spaghetti.Callback $legacyCallback;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(PromptViewModel promptViewModel, Spaghetti.Callback callback, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$legacyCallback = callback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass7(this.$viewModel, this.$legacyCallback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0041, code lost:
        
            if (r1.$$delegate_0.collect(r3, r5) == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0043, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0029, code lost:
        
            if (r6 == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1c
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L44
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                kotlin.ResultKt.throwOnFailure(r6)
                goto L2c
            L1c:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r6 = r5.$viewModel
                kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.fingerprintStartMode
                r5.label = r3
                java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r6, r5)
                if (r6 != r0) goto L2c
                goto L43
            L2c:
                com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode r6 = (com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode) r6
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r1 = r5.$viewModel
                kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.fingerprintStartMode
                com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$7$1 r3 = new com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$7$1
                com.android.systemui.biometrics.ui.binder.Spaghetti$Callback r4 = r5.$legacyCallback
                r3.<init>()
                r5.label = r2
                kotlinx.coroutines.flow.StateFlow r6 = r1.$$delegate_0
                java.lang.Object r5 = r6.collect(r3, r5)
                if (r5 != r0) goto L44
            L43:
                return r0
            L44:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1.AnonymousClass7.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        final /* synthetic */ AccessibilityManager $accessibilityManager;
        final /* synthetic */ View $backgroundView;
        final /* synthetic */ Button $cancelButton;
        final /* synthetic */ Button $confirmationButton;
        final /* synthetic */ Button $credentialFallbackButton;
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ TextView $indicatorMessageView;
        final /* synthetic */ Spaghetti.Callback $legacyCallback;
        final /* synthetic */ MSDLPlayer $msdlPlayer;
        final /* synthetic */ Button $negativeButton;
        final /* synthetic */ Button $retryButton;
        final /* synthetic */ TextView $subtitleView;
        final /* synthetic */ int $textColorError;
        final /* synthetic */ int $textColorHint;
        final /* synthetic */ View $udfpsGuidanceView;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ View $view;
        final /* synthetic */ PromptViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $backgroundView;
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1$1, reason: invalid class name and collision with other inner class name */
            final class C00281 extends SuspendLambda implements Function3 {
                /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public C00281(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C00281 c00281 = new C00281((Continuation) obj3);
                    c00281.L$0 = (PromptAuthState) obj;
                    c00281.L$1 = (PromptSize) obj2;
                    return c00281.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    PromptAuthState promptAuthState = (PromptAuthState) this.L$0;
                    PromptSize promptSize = (PromptSize) this.L$1;
                    boolean z = false;
                    if (!promptAuthState.isAuthenticated && promptSize != PromptSize.SMALL && promptSize != PromptSize.LARGE) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PromptViewModel promptViewModel, View view, Spaghetti.Callback callback, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$backgroundView = view;
                this.$legacyCallback = callback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$backgroundView, this.$legacyCallback, continuation);
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
                    PromptViewModel promptViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.isAuthenticated, promptViewModel.size, new C00281(null));
                    final View view = this.$backgroundView;
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.1.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final boolean booleanValue = ((Boolean) obj2).booleanValue();
                            View view2 = view;
                            final Spaghetti.Callback callback2 = callback;
                            view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.1.2.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view3) {
                                    if (booleanValue) {
                                        callback2.onUserCanceled();
                                    } else {
                                        Log.w("BiometricViewBinder", "Ignoring background click");
                                    }
                                }
                            });
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ AccessibilityManager $accessibilityManager;
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(PromptViewModel promptViewModel, AccessibilityManager accessibilityManager, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$accessibilityManager = accessibilityManager;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.$viewModel, this.$accessibilityManager, this.$iconView, continuation);
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
                    final PromptViewModel promptViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = promptViewModel.isIconConfirmButton;
                    final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.10.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                            if (!booleanValue || accessibilityManager.isEnabled()) {
                                lottieAnimationView2.setOnTouchListener(null);
                            } else {
                                final PromptViewModel promptViewModel2 = promptViewModel;
                                lottieAnimationView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.10.1.1
                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                        PromptViewModel promptViewModel3 = PromptViewModel.this;
                                        promptViewModel3.getClass();
                                        int actionMasked = motionEvent.getActionMasked();
                                        StateFlowImpl stateFlowImpl = promptViewModel3._isOverlayTouched;
                                        if (actionMasked == 0) {
                                            stateFlowImpl.updateState(null, Boolean.TRUE);
                                            if (((PromptAuthState) promptViewModel3._isAuthenticated.getValue()).needsUserConfirmation) {
                                                promptViewModel3.confirmAuthenticated();
                                            }
                                            return true;
                                        }
                                        if (motionEvent.getActionMasked() != 1) {
                                            return false;
                                        }
                                        stateFlowImpl.updateState(null, Boolean.FALSE);
                                        return false;
                                    }
                                });
                                new ViewExtKt$onTouchListener$1(lottieAnimationView2);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ AccessibilityManager $accessibilityManager;
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                public AnonymousClass3() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return new Pair(bool, (PromptAuthState) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(PromptViewModel promptViewModel, AccessibilityManager accessibilityManager, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$accessibilityManager = accessibilityManager;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.$viewModel, this.$accessibilityManager, this.$iconView, continuation);
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
                    PromptViewModel promptViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.isIconConfirmButton, promptViewModel.isAuthenticated, AnonymousClass3.INSTANCE);
                    final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    final PromptViewModel promptViewModel2 = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.11.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                            PromptAuthState promptAuthState = (PromptAuthState) pair.component2();
                            if (accessibilityManager.isEnabled() && booleanValue && promptAuthState.isAuthenticated) {
                                final PromptViewModel promptViewModel3 = promptViewModel2;
                                lottieAnimationView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.11.4.1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        PromptViewModel.this.confirmAuthenticated();
                                    }
                                });
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $backgroundView;
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ TextView $subtitleView;
            final /* synthetic */ PromptViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(PromptViewModel promptViewModel, TextView textView, View view, Spaghetti.Callback callback, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$subtitleView = textView;
                this.$backgroundView = view;
                this.$legacyCallback = callback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass12 anonymousClass12 = new AnonymousClass12(this.$viewModel, this.$subtitleView, this.$backgroundView, this.$legacyCallback, continuation);
                anonymousClass12.L$0 = obj;
                return anonymousClass12;
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
                    final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.isAuthenticated;
                    final TextView textView = this.$subtitleView;
                    final View view = this.$backgroundView;
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.12.1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$12$1$1, reason: invalid class name and collision with other inner class name */
                        final class C00311 extends SuspendLambda implements Function2 {
                            final /* synthetic */ PromptAuthState $authState;
                            final /* synthetic */ Spaghetti.Callback $legacyCallback;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public C00311(PromptAuthState promptAuthState, Spaghetti.Callback callback, Continuation continuation) {
                                super(2, continuation);
                                this.$authState = promptAuthState;
                                this.$legacyCallback = callback;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new C00311(this.$authState, this.$legacyCallback, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((C00311) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    long j = this.$authState.delay;
                                    this.label = 1;
                                    if (DelayKt.delay(j, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                if (this.$authState.isAuthenticatedAndExplicitlyConfirmed()) {
                                    this.$legacyCallback.onAuthenticatedAndConfirmed();
                                } else {
                                    this.$legacyCallback.onAuthenticated();
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            PromptAuthState promptAuthState = (PromptAuthState) obj2;
                            if (promptAuthState.isAuthenticated) {
                                textView.setImportantForAccessibility(2);
                                view.setOnClickListener(null);
                                view.setImportantForAccessibility(2);
                            }
                            if (promptAuthState.isAuthenticated && !promptAuthState.needsUserConfirmation) {
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00311(promptAuthState, callback, null), 7);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$13, reason: invalid class name */
        final class AnonymousClass13 extends SuspendLambda implements Function2 {
            final /* synthetic */ AccessibilityManager $accessibilityManager;
            final /* synthetic */ TextView $indicatorMessageView;
            final /* synthetic */ int $textColorError;
            final /* synthetic */ int $textColorHint;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass13(PromptViewModel promptViewModel, TextView textView, int i, int i2, AccessibilityManager accessibilityManager, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$indicatorMessageView = textView;
                this.$textColorError = i;
                this.$textColorHint = i2;
                this.$accessibilityManager = accessibilityManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass13(this.$viewModel, this.$indicatorMessageView, this.$textColorError, this.$textColorHint, this.$accessibilityManager, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.message;
                    final TextView textView = this.$indicatorMessageView;
                    final int i2 = this.$textColorError;
                    final int i3 = this.$textColorHint;
                    final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.13.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            PromptMessage promptMessage = (PromptMessage) obj2;
                            boolean z = promptMessage instanceof PromptMessage.Error;
                            TextView textView2 = textView;
                            promptMessage.getClass();
                            textView2.setText(promptMessage instanceof PromptMessage.Error ? ((PromptMessage.Error) promptMessage).errorMessage : promptMessage instanceof PromptMessage.Help ? ((PromptMessage.Help) promptMessage).helpMessage : "");
                            textView.setTextColor(z ? i2 : i3);
                            textView.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$15, reason: invalid class name */
        final class AnonymousClass15 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass15(PromptViewModel promptViewModel, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$view = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass15(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass15) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlySharedFlow readonlySharedFlow = this.$viewModel.accessibilityHint;
                    final View view = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.15.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            String str = (String) obj2;
                            if (!StringsKt__StringsKt.isBlank(str)) {
                                view.announceForAccessibility(str);
                            }
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$16, reason: invalid class name */
        final class AnonymousClass16 extends SuspendLambda implements Function2 {
            final /* synthetic */ MSDLPlayer $msdlPlayer;
            final /* synthetic */ VibratorHelper $vibratorHelper;
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass16(PromptViewModel promptViewModel, VibratorHelper vibratorHelper, View view, MSDLPlayer mSDLPlayer, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$vibratorHelper = vibratorHelper;
                this.$view = view;
                this.$msdlPlayer = mSDLPlayer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass16(this.$viewModel, this.$vibratorHelper, this.$view, this.$msdlPlayer, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass16) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final PromptViewModel promptViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = promptViewModel.hapticsToPlay;
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    final View view = this.$view;
                    final MSDLPlayer mSDLPlayer = this.$msdlPlayer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.16.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Object value;
                            PromptViewModel.HapticsToPlay hapticsToPlay = (PromptViewModel.HapticsToPlay) obj2;
                            if (hapticsToPlay instanceof PromptViewModel.HapticsToPlay.HapticConstant) {
                                PromptViewModel.HapticsToPlay.HapticConstant hapticConstant = (PromptViewModel.HapticsToPlay.HapticConstant) hapticsToPlay;
                                Integer num = hapticConstant.flag;
                                int i2 = hapticConstant.constant;
                                VibratorHelper vibratorHelper2 = VibratorHelper.this;
                                if (num != null) {
                                    View view2 = view;
                                    int intValue = num.intValue();
                                    vibratorHelper2.getClass();
                                    view2.performHapticFeedback(i2, intValue);
                                } else {
                                    View view3 = view;
                                    vibratorHelper2.getClass();
                                    view3.performHapticFeedback(i2);
                                }
                            } else if (hapticsToPlay instanceof PromptViewModel.HapticsToPlay.MSDL) {
                                PromptViewModel.HapticsToPlay.MSDL msdl = (PromptViewModel.HapticsToPlay.MSDL) hapticsToPlay;
                                mSDLPlayer.playToken(msdl.token, msdl.properties);
                            } else if (!(hapticsToPlay instanceof PromptViewModel.HapticsToPlay.None)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            StateFlowImpl stateFlowImpl = promptViewModel._hapticsToPlay;
                            do {
                                value = stateFlowImpl.getValue();
                            } while (!stateFlowImpl.compareAndSet(value, PromptViewModel.HapticsToPlay.None.INSTANCE));
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$17, reason: invalid class name */
        final class AnonymousClass17 extends SuspendLambda implements Function2 {
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$17$3, reason: invalid class name */
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
            public AnonymousClass17(PromptViewModel promptViewModel, Spaghetti.Callback callback, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$legacyCallback = callback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass17(this.$viewModel, this.$legacyCallback, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass17) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PromptViewModel promptViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.canTryAgainNow, promptViewModel.hasFingerOnSensor, AnonymousClass3.INSTANCE);
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.17.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                            boolean booleanValue2 = ((Boolean) pair.component2()).booleanValue();
                            if (booleanValue && booleanValue2) {
                                Spaghetti.Callback.this.onButtonTryAgain();
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ TextView $indicatorMessageView;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(PromptViewModel promptViewModel, TextView textView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$indicatorMessageView = textView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$indicatorMessageView, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = this.$viewModel.isIndicatorMessageVisible;
                    final TextView textView = this.$indicatorMessageView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            textView.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 4);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $credentialFallbackButton;
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PromptViewModel promptViewModel, View view, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$view = view;
                this.$credentialFallbackButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, this.$credentialFallbackButton, continuation);
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
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.credentialKind;
                    final View view = this.$view;
                    Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ View $view$inlined;

                            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, View view) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$view$inlined = view;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L83
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.biometrics.shared.model.PromptKind r5 = (com.android.systemui.biometrics.shared.model.PromptKind) r5
                                    com.android.systemui.biometrics.shared.model.PromptKind$Pin r6 = com.android.systemui.biometrics.shared.model.PromptKind.Pin.INSTANCE
                                    boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                    if (r6 == 0) goto L4a
                                    android.view.View r5 = r4.$view$inlined
                                    android.content.res.Resources r5 = r5.getResources()
                                    r6 = 2131952253(0x7f13027d, float:1.9540944E38)
                                    java.lang.String r5 = r5.getString(r6)
                                    goto L78
                                L4a:
                                    com.android.systemui.biometrics.shared.model.PromptKind$Password r6 = com.android.systemui.biometrics.shared.model.PromptKind.Password.INSTANCE
                                    boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                    if (r6 == 0) goto L60
                                    android.view.View r5 = r4.$view$inlined
                                    android.content.res.Resources r5 = r5.getResources()
                                    r6 = 2131952251(0x7f13027b, float:1.954094E38)
                                    java.lang.String r5 = r5.getString(r6)
                                    goto L78
                                L60:
                                    com.android.systemui.biometrics.shared.model.PromptKind$Pattern r6 = com.android.systemui.biometrics.shared.model.PromptKind.Pattern.INSTANCE
                                    boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                    if (r5 == 0) goto L76
                                    android.view.View r5 = r4.$view$inlined
                                    android.content.res.Resources r5 = r5.getResources()
                                    r6 = 2131952252(0x7f13027c, float:1.9540941E38)
                                    java.lang.String r5 = r5.getString(r6)
                                    goto L78
                                L76:
                                    java.lang.String r5 = ""
                                L78:
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L83
                                    return r1
                                L83:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, view), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final Button button = this.$credentialFallbackButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.3.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setText((String) obj2);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $negativeButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$negativeButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$negativeButton, continuation);
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
                    PromptViewModel$special$$inlined$map$6 promptViewModel$special$$inlined$map$6 = this.$viewModel.negativeButtonText;
                    final Button button = this.$negativeButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setText((String) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (promptViewModel$special$$inlined$map$6.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $confirmationButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$confirmationButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$confirmationButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = this.$viewModel.isConfirmButtonVisible;
                    final Button button = this.$confirmationButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $cancelButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$cancelButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$cancelButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = this.$viewModel.isCancelButtonVisible;
                    final Button button = this.$cancelButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $negativeButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$negativeButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$negativeButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = this.$viewModel.isNegativeButtonVisible;
                    final Button button = this.$negativeButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$8, reason: invalid class name and collision with other inner class name */
        final class C00328 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $retryButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00328(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$retryButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00328(this.$viewModel, this.$retryButton, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00328) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isTryAgainButtonVisible;
                    final Button button = this.$retryButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $credentialFallbackButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$credentialFallbackButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.$credentialFallbackButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = this.$viewModel.isCredentialButtonVisible;
                    final Button button = this.$credentialFallbackButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass8(View view, PromptViewModel promptViewModel, View view2, Spaghetti.Callback callback, TextView textView, View view3, Button button, Button button2, Button button3, Button button4, Button button5, AccessibilityManager accessibilityManager, LottieAnimationView lottieAnimationView, TextView textView2, int i, int i2, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, Continuation continuation) {
            super(2, continuation);
            this.$udfpsGuidanceView = view;
            this.$viewModel = promptViewModel;
            this.$backgroundView = view2;
            this.$legacyCallback = callback;
            this.$indicatorMessageView = textView;
            this.$view = view3;
            this.$credentialFallbackButton = button;
            this.$negativeButton = button2;
            this.$confirmationButton = button3;
            this.$cancelButton = button4;
            this.$retryButton = button5;
            this.$accessibilityManager = accessibilityManager;
            this.$iconView = lottieAnimationView;
            this.$subtitleView = textView2;
            this.$textColorError = i;
            this.$textColorHint = i2;
            this.$vibratorHelper = vibratorHelper;
            this.$msdlPlayer = mSDLPlayer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$udfpsGuidanceView, this.$viewModel, this.$backgroundView, this.$legacyCallback, this.$indicatorMessageView, this.$view, this.$credentialFallbackButton, this.$negativeButton, this.$confirmationButton, this.$cancelButton, this.$retryButton, this.$accessibilityManager, this.$iconView, this.$subtitleView, this.$textColorError, this.$textColorHint, this.$vibratorHelper, this.$msdlPlayer, continuation);
            anonymousClass8.L$0 = obj;
            return anonymousClass8;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$backgroundView, this.$legacyCallback, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$indicatorMessageView, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, this.$credentialFallbackButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$negativeButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$confirmationButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$cancelButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$negativeButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00328(this.$viewModel, this.$retryButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$credentialFallbackButton, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$accessibilityManager, this.$iconView, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$accessibilityManager, this.$iconView, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$subtitleView, this.$backgroundView, this.$legacyCallback, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$indicatorMessageView, this.$textColorError, this.$textColorHint, this.$accessibilityManager, null), 7);
            View view = this.$udfpsGuidanceView;
            final PromptViewModel promptViewModel = this.$viewModel;
            final AccessibilityManager accessibilityManager = this.$accessibilityManager;
            view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.14

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$14$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ AccessibilityManager $accessibilityManager;
                    final /* synthetic */ MotionEvent $event;
                    final /* synthetic */ PromptViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(PromptViewModel promptViewModel, MotionEvent motionEvent, AccessibilityManager accessibilityManager, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = promptViewModel;
                        this.$event = motionEvent;
                        this.$accessibilityManager = accessibilityManager;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.$viewModel, this.$event, this.$accessibilityManager, continuation);
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
                            PromptViewModel promptViewModel = this.$viewModel;
                            MotionEvent motionEvent = this.$event;
                            motionEvent.getClass();
                            boolean isTouchExplorationEnabled = this.$accessibilityManager.isTouchExplorationEnabled();
                            this.label = 1;
                            if (promptViewModel.onAnnounceAccessibilityHint(motionEvent, isTouchExplorationEnabled, this) == coroutineSingletons) {
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

                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    CoroutineTracingKt.launchTraced$default(CoroutineScope.this, null, null, new AnonymousClass1(promptViewModel, motionEvent, accessibilityManager, null), 7);
                    return false;
                }
            });
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass15(this.$viewModel, this.$view, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass16(this.$viewModel, this.$vibratorHelper, this.$view, this.$msdlPlayer, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass17(this.$viewModel, this.$legacyCallback, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricViewBinder$bind$1(PromptViewModel promptViewModel, View view, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, LinearLayout linearLayout, Spaghetti.Callback callback, Button button, Button button2, Button button3, Button button4, Button button5, Spaghetti spaghetti, Ref$BooleanRef ref$BooleanRef, Animator.AnimatorListener animatorListener, LottieAnimationView lottieAnimationView, View view2, View view3, TextView textView5, AccessibilityManager accessibilityManager, int i, int i2, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = promptViewModel;
        this.$view = view;
        this.$logoView = imageView;
        this.$logoDescriptionView = textView;
        this.$titleView = textView2;
        this.$subtitleView = textView3;
        this.$descriptionView = textView4;
        this.$customizedViewContainer = linearLayout;
        this.$legacyCallback = callback;
        this.$negativeButton = button;
        this.$cancelButton = button2;
        this.$credentialFallbackButton = button3;
        this.$confirmationButton = button4;
        this.$retryButton = button5;
        this.$adapter = spaghetti;
        this.$boundSize = ref$BooleanRef;
        this.$jankListener = animatorListener;
        this.$iconView = lottieAnimationView;
        this.$udfpsGuidanceView = view2;
        this.$backgroundView = view3;
        this.$indicatorMessageView = textView5;
        this.$accessibilityManager = accessibilityManager;
        this.$textColorError = i;
        this.$textColorHint = i2;
        this.$vibratorHelper = vibratorHelper;
        this.$msdlPlayer = mSDLPlayer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricViewBinder$bind$1 biometricViewBinder$bind$1 = new BiometricViewBinder$bind$1(this.$viewModel, this.$view, this.$logoView, this.$logoDescriptionView, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$negativeButton, this.$cancelButton, this.$credentialFallbackButton, this.$confirmationButton, this.$retryButton, this.$adapter, this.$boundSize, this.$jankListener, this.$iconView, this.$udfpsGuidanceView, this.$backgroundView, this.$indicatorMessageView, this.$accessibilityManager, this.$textColorError, this.$textColorHint, this.$vibratorHelper, this.$msdlPlayer, (Continuation) obj3);
        biometricViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return biometricViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x05c6, code lost:
    
        if (androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r12, r2, r13, r81) != r1) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0369, code lost:
    
        if (r12 != r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x009c, code lost:
    
        if (r10 == r1) goto L76;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0332  */
    /* JADX WARN: Type inference failed for: r9v2, types: [T, com.android.systemui.biometrics.ui.viewmodel.PromptPosition] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r82) {
        /*
            Method dump skipped, instructions count: 1504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
