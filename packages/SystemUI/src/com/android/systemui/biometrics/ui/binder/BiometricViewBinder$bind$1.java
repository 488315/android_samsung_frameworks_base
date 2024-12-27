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
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode;
import com.android.systemui.biometrics.ui.viewmodel.HapticsToPlay;
import com.android.systemui.biometrics.ui.viewmodel.PromptAuthState;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptSize;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.KotlinNothingValueException;
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
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    final /* synthetic */ LottieAnimationView $iconOverlayView;
    final /* synthetic */ Pair<Integer, Integer> $iconSizeOverride;
    final /* synthetic */ LottieAnimationView $iconView;
    final /* synthetic */ TextView $indicatorMessageView;
    final /* synthetic */ Animator.AnimatorListener $jankListener;
    final /* synthetic */ Spaghetti.Callback $legacyCallback;
    final /* synthetic */ TextView $logoDescriptionView;
    final /* synthetic */ ImageView $logoView;
    final /* synthetic */ Button $negativeButton;
    final /* synthetic */ AuthPanelController $panelViewController;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        final /* synthetic */ LottieAnimationView $iconOverlayView;
        final /* synthetic */ Pair<Integer, Integer> $iconSizeOverride;
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(PromptViewModel promptViewModel, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, Pair<Integer, Integer> pair, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$iconView = lottieAnimationView;
            this.$iconOverlayView = lottieAnimationView2;
            this.$iconSizeOverride = pair;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass6(this.$viewModel, this.$iconView, this.$iconOverlayView, this.$iconSizeOverride, continuation);
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
                final LottieAnimationView lottieAnimationView2 = this.$iconOverlayView;
                final Pair<Integer, Integer> pair = this.$iconSizeOverride;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (!((Boolean) obj2).booleanValue()) {
                            int i2 = PromptIconViewBinder.$r8$clinit;
                            PromptViewModel promptViewModel2 = promptViewModel;
                            PromptIconViewModel promptIconViewModel = promptViewModel2.iconViewModel;
                            Pair pair2 = pair;
                            LottieAnimationView lottieAnimationView3 = LottieAnimationView.this;
                            RepeatWhenAttachedKt.repeatWhenAttached(lottieAnimationView3, EmptyCoroutineContext.INSTANCE, new PromptIconViewBinder$bind$1(promptIconViewModel, lottieAnimationView3, pair2, lottieAnimationView2, promptViewModel2, null));
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlow = this.$viewModel.fingerprintStartMode;
                this.label = 1;
                obj = FlowKt.first(readonlyStateFlow, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            final FingerprintStartMode fingerprintStartMode = (FingerprintStartMode) obj;
            ReadonlyStateFlow readonlyStateFlow2 = this.$viewModel.fingerprintStartMode;
            final Spaghetti.Callback callback = this.$legacyCallback;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.7.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    FingerprintStartMode fingerprintStartMode2 = (FingerprintStartMode) obj2;
                    if (FingerprintStartMode.this == FingerprintStartMode.Pending && fingerprintStartMode2 == FingerprintStartMode.Delayed) {
                        callback.onStartDelayedFingerprintSensor();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 2;
            if (readonlyStateFlow2.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        final /* synthetic */ AccessibilityManager $accessibilityManager;
        final /* synthetic */ View $backgroundView;
        final /* synthetic */ Button $cancelButton;
        final /* synthetic */ Button $confirmationButton;
        final /* synthetic */ Button $credentialFallbackButton;
        final /* synthetic */ LottieAnimationView $iconOverlayView;
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ TextView $indicatorMessageView;
        final /* synthetic */ Spaghetti.Callback $legacyCallback;
        final /* synthetic */ BiometricModalities $modalities;
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $backgroundView;
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1$1, reason: invalid class name and collision with other inner class name */
            final class C00271 extends SuspendLambda implements Function3 {
                /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public C00271(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C00271 c00271 = new C00271((Continuation) obj3);
                    c00271.L$0 = (PromptAuthState) obj;
                    c00271.L$1 = (PromptSize) obj2;
                    return c00271.invokeSuspend(Unit.INSTANCE);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.isAuthenticated, promptViewModel.size, new C00271(null));
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $credentialFallbackButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$credentialFallbackButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.$viewModel, this.$credentialFallbackButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = this.$viewModel.isCredentialButtonVisible;
                    final Button button = this.$credentialFallbackButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.10.1
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconOverlayView;
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ BiometricModalities $modalities;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(PromptViewModel promptViewModel, BiometricModalities biometricModalities, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$modalities = biometricModalities;
                this.$iconOverlayView = lottieAnimationView;
                this.$iconView = lottieAnimationView2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.$viewModel, this.$modalities, this.$iconOverlayView, this.$iconView, continuation);
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
                    final PromptViewModel promptViewModel = this.$viewModel;
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = promptViewModel.isIconConfirmButton;
                    final BiometricModalities biometricModalities = this.$modalities;
                    Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ BiometricModalities $modalities$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ PromptViewModel $viewModel$inlined;

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, BiometricModalities biometricModalities, PromptViewModel promptViewModel) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$modalities$inlined = biometricModalities;
                                this.$viewModel$inlined = promptViewModel;
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
                                    boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L56
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    java.lang.Boolean r5 = (java.lang.Boolean) r5
                                    boolean r5 = r5.booleanValue()
                                    if (r5 == 0) goto L4a
                                    com.android.systemui.biometrics.shared.model.BiometricModalities r5 = r4.$modalities$inlined
                                    boolean r5 = r5.getHasFaceAndFingerprint()
                                    if (r5 == 0) goto L4a
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$1$1 r5 = new com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$1$1
                                    com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r6 = r4.$viewModel$inlined
                                    r5.<init>(r6)
                                    goto L4b
                                L4a:
                                    r5 = 0
                                L4b:
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L56
                                    return r1
                                L56:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, biometricModalities, promptViewModel), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final LottieAnimationView lottieAnimationView = this.$iconOverlayView;
                    final LottieAnimationView lottieAnimationView2 = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.11.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            View.OnTouchListener onTouchListener = (View.OnTouchListener) obj2;
                            LottieAnimationView.this.setOnTouchListener(onTouchListener);
                            lottieAnimationView2.setOnTouchListener(onTouchListener);
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            final /* synthetic */ AccessibilityManager $accessibilityManager;
            final /* synthetic */ View $backgroundView;
            final /* synthetic */ LottieAnimationView $iconOverlayView;
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ TextView $subtitleView;
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(PromptViewModel promptViewModel, TextView textView, View view, AccessibilityManager accessibilityManager, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, View view2, Spaghetti.Callback callback, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$subtitleView = textView;
                this.$backgroundView = view;
                this.$accessibilityManager = accessibilityManager;
                this.$iconOverlayView = lottieAnimationView;
                this.$iconView = lottieAnimationView2;
                this.$view = view2;
                this.$legacyCallback = callback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass12 anonymousClass12 = new AnonymousClass12(this.$viewModel, this.$subtitleView, this.$backgroundView, this.$accessibilityManager, this.$iconOverlayView, this.$iconView, this.$view, this.$legacyCallback, continuation);
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
                    final PromptViewModel promptViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = promptViewModel.isAuthenticated;
                    final TextView textView = this.$subtitleView;
                    final View view = this.$backgroundView;
                    final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                    final LottieAnimationView lottieAnimationView = this.$iconOverlayView;
                    final LottieAnimationView lottieAnimationView2 = this.$iconView;
                    final View view2 = this.$view;
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.12.1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$12$1$3, reason: invalid class name */
                        final class AnonymousClass3 extends SuspendLambda implements Function2 {
                            final /* synthetic */ PromptAuthState $authState;
                            final /* synthetic */ Spaghetti.Callback $legacyCallback;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass3(PromptAuthState promptAuthState, Spaghetti.Callback callback, Continuation continuation) {
                                super(2, continuation);
                                this.$authState = promptAuthState;
                                this.$legacyCallback = callback;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new AnonymousClass3(this.$authState, this.$legacyCallback, continuation);
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
                                PromptAuthState promptAuthState = this.$authState;
                                if (promptAuthState.isAuthenticated && promptAuthState.wasConfirmed) {
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
                                if (accessibilityManager.isTouchExplorationEnabled()) {
                                    final PromptViewModel promptViewModel2 = promptViewModel;
                                    lottieAnimationView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.12.1.1
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view3) {
                                            PromptViewModel.this.confirmAuthenticated();
                                        }
                                    });
                                    lottieAnimationView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.12.1.2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view3) {
                                            PromptViewModel.this.confirmAuthenticated();
                                        }
                                    });
                                }
                            }
                            if (promptAuthState.isAuthenticated && !promptAuthState.needsUserConfirmation) {
                                View view3 = view2;
                                view3.announceForAccessibility(view3.getResources().getString(R.string.biometric_dialog_authenticated));
                                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(promptAuthState, callback, null), 3);
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                            if (!StringsKt__StringsJVMKt.isBlank(str)) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$16, reason: invalid class name */
        final class AnonymousClass16 extends SuspendLambda implements Function2 {
            final /* synthetic */ VibratorHelper $vibratorHelper;
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass16(PromptViewModel promptViewModel, VibratorHelper vibratorHelper, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$vibratorHelper = vibratorHelper;
                this.$view = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass16(this.$viewModel, this.$vibratorHelper, this.$view, continuation);
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
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.16.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Object value;
                            HapticsToPlay hapticsToPlay = (HapticsToPlay) obj2;
                            int i2 = hapticsToPlay.hapticFeedbackConstant;
                            if (i2 != -1) {
                                VibratorHelper vibratorHelper2 = VibratorHelper.this;
                                Integer num = hapticsToPlay.flag;
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
                                StateFlowImpl stateFlowImpl = promptViewModel._hapticsToPlay;
                                do {
                                    value = stateFlowImpl.getValue();
                                } while (!stateFlowImpl.compareAndSet(value, new HapticsToPlay(-1, ((HapticsToPlay) value).flag)));
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
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$17, reason: invalid class name */
        final class AnonymousClass17 extends SuspendLambda implements Function2 {
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$17$2, reason: invalid class name */
            final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                public AnonymousClass2() {
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.canTryAgainNow, promptViewModel.hasFingerOnSensor, AnonymousClass2.INSTANCE);
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.17.3
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PromptViewModel promptViewModel, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$view = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.promptPadding;
                    final View view = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Flags.constraintBp();
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $credentialFallbackButton;
            final /* synthetic */ View $view;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PromptViewModel promptViewModel, View view, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$view = view;
                this.$credentialFallbackButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$view, this.$credentialFallbackButton, continuation);
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
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.credentialKind;
                    final View view = this.$view;
                    Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ View $view$inlined;

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1$2$1
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
                                    r6 = 2131952200(0x7f130248, float:1.9540836E38)
                                    java.lang.String r5 = r5.getString(r6)
                                    goto L78
                                L4a:
                                    com.android.systemui.biometrics.shared.model.PromptKind$Password r6 = com.android.systemui.biometrics.shared.model.PromptKind.Password.INSTANCE
                                    boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                    if (r6 == 0) goto L60
                                    android.view.View r5 = r4.$view$inlined
                                    android.content.res.Resources r5 = r5.getResources()
                                    r6 = 2131952198(0x7f130246, float:1.9540832E38)
                                    java.lang.String r5 = r5.getString(r6)
                                    goto L78
                                L60:
                                    com.android.systemui.biometrics.shared.model.PromptKind$Pattern r6 = com.android.systemui.biometrics.shared.model.PromptKind.Pattern.INSTANCE
                                    boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                    if (r5 == 0) goto L76
                                    android.view.View r5 = r4.$view$inlined
                                    android.content.res.Resources r5 = r5.getResources()
                                    r6 = 2131952199(0x7f130247, float:1.9540834E38)
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
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$4$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, view), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final Button button = this.$credentialFallbackButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.4.2
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $negativeButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$negativeButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$negativeButton, continuation);
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
                    PromptViewModel$special$$inlined$map$4 promptViewModel$special$$inlined$map$4 = this.$viewModel.negativeButtonText;
                    final Button button = this.$negativeButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            button.setText((String) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (promptViewModel$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $confirmationButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$confirmationButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$confirmationButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = this.$viewModel.isConfirmButtonVisible;
                    final Button button = this.$confirmationButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.6.1
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $cancelButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$cancelButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$cancelButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = this.$viewModel.isCancelButtonVisible;
                    final Button button = this.$cancelButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.7.1
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$8, reason: invalid class name and collision with other inner class name */
        final class C00308 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $negativeButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00308(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$negativeButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00308(this.$viewModel, this.$negativeButton, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00308) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = this.$viewModel.isNegativeButtonVisible;
                    final Button button = this.$negativeButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.8.1
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $retryButton;
            final /* synthetic */ PromptViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptViewModel;
                this.$retryButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.$retryButton, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isTryAgainButtonVisible;
                    final Button button = this.$retryButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.9.1
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(View view, PromptViewModel promptViewModel, View view2, Spaghetti.Callback callback, TextView textView, View view3, Button button, Button button2, Button button3, Button button4, Button button5, BiometricModalities biometricModalities, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, TextView textView2, AccessibilityManager accessibilityManager, int i, int i2, VibratorHelper vibratorHelper, Continuation continuation) {
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
            this.$modalities = biometricModalities;
            this.$iconOverlayView = lottieAnimationView;
            this.$iconView = lottieAnimationView2;
            this.$subtitleView = textView2;
            this.$accessibilityManager = accessibilityManager;
            this.$textColorError = i;
            this.$textColorHint = i2;
            this.$vibratorHelper = vibratorHelper;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$udfpsGuidanceView, this.$viewModel, this.$backgroundView, this.$legacyCallback, this.$indicatorMessageView, this.$view, this.$credentialFallbackButton, this.$negativeButton, this.$confirmationButton, this.$cancelButton, this.$retryButton, this.$modalities, this.$iconOverlayView, this.$iconView, this.$subtitleView, this.$accessibilityManager, this.$textColorError, this.$textColorHint, this.$vibratorHelper, continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$backgroundView, this.$legacyCallback, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$indicatorMessageView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$view, this.$credentialFallbackButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$negativeButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$confirmationButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$cancelButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new C00308(this.$viewModel, this.$negativeButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$retryButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$credentialFallbackButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$modalities, this.$iconOverlayView, this.$iconView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$subtitleView, this.$backgroundView, this.$accessibilityManager, this.$iconOverlayView, this.$iconView, this.$view, this.$legacyCallback, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$indicatorMessageView, this.$textColorError, this.$textColorHint, this.$accessibilityManager, null), 3);
            View view = this.$udfpsGuidanceView;
            final PromptViewModel promptViewModel = this.$viewModel;
            final AccessibilityManager accessibilityManager = this.$accessibilityManager;
            view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.14

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                    BuildersKt.launch$default(CoroutineScope.this, null, null, new AnonymousClass1(promptViewModel, motionEvent, accessibilityManager, null), 3);
                    return false;
                }
            });
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass15(this.$viewModel, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass16(this.$viewModel, this.$vibratorHelper, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass17(this.$viewModel, this.$legacyCallback, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricViewBinder$bind$1(PromptViewModel promptViewModel, View view, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, LinearLayout linearLayout, Spaghetti.Callback callback, Button button, Button button2, Button button3, Button button4, Button button5, Spaghetti spaghetti, Ref$BooleanRef ref$BooleanRef, TextView textView5, AuthPanelController authPanelController, Animator.AnimatorListener animatorListener, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2, Pair<Integer, Integer> pair, View view2, View view3, AccessibilityManager accessibilityManager, int i, int i2, VibratorHelper vibratorHelper, Continuation continuation) {
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
        this.$indicatorMessageView = textView5;
        this.$panelViewController = authPanelController;
        this.$jankListener = animatorListener;
        this.$iconView = lottieAnimationView;
        this.$iconOverlayView = lottieAnimationView2;
        this.$iconSizeOverride = pair;
        this.$udfpsGuidanceView = view2;
        this.$backgroundView = view3;
        this.$accessibilityManager = accessibilityManager;
        this.$textColorError = i;
        this.$textColorHint = i2;
        this.$vibratorHelper = vibratorHelper;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricViewBinder$bind$1 biometricViewBinder$bind$1 = new BiometricViewBinder$bind$1(this.$viewModel, this.$view, this.$logoView, this.$logoDescriptionView, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$negativeButton, this.$cancelButton, this.$credentialFallbackButton, this.$confirmationButton, this.$retryButton, this.$adapter, this.$boundSize, this.$indicatorMessageView, this.$panelViewController, this.$jankListener, this.$iconView, this.$iconOverlayView, this.$iconSizeOverride, this.$udfpsGuidanceView, this.$backgroundView, this.$accessibilityManager, this.$textColorError, this.$textColorHint, this.$vibratorHelper, (Continuation) obj3);
        biometricViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return biometricViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x04ed A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x023b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0221 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0207 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01d6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01ba A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01bb  */
    /* JADX WARN: Type inference failed for: r9v3, types: [T, com.android.systemui.biometrics.ui.viewmodel.PromptPosition] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r40) {
        /*
            Method dump skipped, instructions count: 1288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
