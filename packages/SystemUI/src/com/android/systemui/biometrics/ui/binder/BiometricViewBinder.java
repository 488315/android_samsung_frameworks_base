package com.android.systemui.biometrics.ui.binder;

import android.animation.Animator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptContentView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieCompositionFactory;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode;
import com.android.systemui.biometrics.ui.viewmodel.PromptAuthState;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptPosition;
import com.android.systemui.biometrics.ui.viewmodel.PromptSize;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6;
import com.android.systemui.common.ui.view.ViewExtKt$onTouchListener$1;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsKt;
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

/* loaded from: classes.dex */
public final class BiometricViewBinder {

    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
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
                                LottieAnimationView lottieAnimationView2 = lottieAnimationView;
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

            /* JADX WARN: Code restructure failed: missing block: B:14:0x0041, code lost:
            
                if (r1.$$delegate_0.collect(r3, r5) == r0) goto L15;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.fingerprintStartMode;
                    this.label = 1;
                    obj = FlowKt.first(readonlyStateFlow, this);
                    if (obj != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
                final FingerprintStartMode fingerprintStartMode = (FingerprintStartMode) obj;
                ReadonlyStateFlow readonlyStateFlow2 = this.$viewModel.fingerprintStartMode;
                final Spaghetti.Callback callback = this.$legacyCallback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.7.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        FingerprintStartMode fingerprintStartMode2 = (FingerprintStartMode) obj2;
                        if (fingerprintStartMode == FingerprintStartMode.Pending && fingerprintStartMode2 == FingerprintStartMode.Delayed) {
                            callback.onStartDelayedFingerprintSensor();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 2;
            }
        }

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

            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1, reason: invalid class name and collision with other inner class name */
            final class C00631 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $backgroundView;
                final /* synthetic */ Spaghetti.Callback $legacyCallback;
                final /* synthetic */ PromptViewModel $viewModel;
                int label;

                /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$1$1, reason: invalid class name and collision with other inner class name */
                final class C00641 extends SuspendLambda implements Function3 {
                    /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public C00641(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        C00641 c00641 = new C00641((Continuation) obj3);
                        c00641.L$0 = (PromptAuthState) obj;
                        c00641.L$1 = (PromptSize) obj2;
                        return c00641.invokeSuspend(Unit.INSTANCE);
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
                public C00631(PromptViewModel promptViewModel, View view, Spaghetti.Callback callback, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = promptViewModel;
                    this.$backgroundView = view;
                    this.$legacyCallback = callback;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00631(this.$viewModel, this.$backgroundView, this.$legacyCallback, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00631) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        PromptViewModel promptViewModel = this.$viewModel;
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.isAuthenticated, promptViewModel.size, new C00641(null));
                        final View view = this.$backgroundView;
                        final Spaghetti.Callback callback = this.$legacyCallback;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.1.2
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                final boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                View view2 = view;
                                final Spaghetti.Callback callback2 = callback;
                                view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.1.2.1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view3) {
                                        if (zBooleanValue) {
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
                                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                                if (!zBooleanValue || accessibilityManager.isEnabled()) {
                                    lottieAnimationView2.setOnTouchListener(null);
                                } else {
                                    final PromptViewModel promptViewModel2 = promptViewModel;
                                    lottieAnimationView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.10.1.1
                                        @Override // android.view.View.OnTouchListener
                                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                                            PromptViewModel promptViewModel3 = promptViewModel2;
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

            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$11, reason: invalid class name */
            final class AnonymousClass11 extends SuspendLambda implements Function2 {
                final /* synthetic */ AccessibilityManager $accessibilityManager;
                final /* synthetic */ LottieAnimationView $iconView;
                final /* synthetic */ PromptViewModel $viewModel;
                int label;

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
                                boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                                PromptAuthState promptAuthState = (PromptAuthState) pair.component2();
                                if (accessibilityManager.isEnabled() && zBooleanValue && promptAuthState.isAuthenticated) {
                                    final PromptViewModel promptViewModel3 = promptViewModel2;
                                    lottieAnimationView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.11.4.1
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            promptViewModel3.confirmAuthenticated();
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

                            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$12$1$1, reason: invalid class name and collision with other inner class name */
                            final class C00701 extends SuspendLambda implements Function2 {
                                final /* synthetic */ PromptAuthState $authState;
                                final /* synthetic */ Spaghetti.Callback $legacyCallback;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C00701(PromptAuthState promptAuthState, Spaghetti.Callback callback, Continuation continuation) {
                                    super(2, continuation);
                                    this.$authState = promptAuthState;
                                    this.$legacyCallback = callback;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    return new C00701(this.$authState, this.$legacyCallback, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    return ((C00701) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00701(promptAuthState, callback, null), 7);
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
                                    VibratorHelper vibratorHelper2 = vibratorHelper;
                                    if (num != null) {
                                        View view2 = view;
                                        int iIntValue = num.intValue();
                                        vibratorHelper2.getClass();
                                        view2.performHapticFeedback(i2, iIntValue);
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

            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$17, reason: invalid class name */
            final class AnonymousClass17 extends SuspendLambda implements Function2 {
                final /* synthetic */ Spaghetti.Callback $legacyCallback;
                final /* synthetic */ PromptViewModel $viewModel;
                int label;

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
                                boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                                boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
                                if (zBooleanValue && zBooleanValue2) {
                                    callback.onButtonTryAgain();
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
                                        PromptKind promptKind = (PromptKind) obj;
                                        String string = Intrinsics.areEqual(promptKind, PromptKind.Pin.INSTANCE) ? this.$view$inlined.getResources().getString(R.string.biometric_dialog_use_pin) : Intrinsics.areEqual(promptKind, PromptKind.Password.INSTANCE) ? this.$view$inlined.getResources().getString(R.string.biometric_dialog_use_password) : Intrinsics.areEqual(promptKind, PromptKind.Pattern.INSTANCE) ? this.$view$inlined.getResources().getString(R.string.biometric_dialog_use_pattern) : "";
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(string, anonymousClass1) == coroutineSingletons) {
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
                                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, view), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

            /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$8, reason: invalid class name and collision with other inner class name */
            final class C00808 extends SuspendLambda implements Function2 {
                final /* synthetic */ Button $retryButton;
                final /* synthetic */ PromptViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00808(PromptViewModel promptViewModel, Button button, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = promptViewModel;
                    this.$retryButton = button;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00808(this.$viewModel, this.$retryButton, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00808) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00631(this.$viewModel, this.$backgroundView, this.$legacyCallback, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$indicatorMessageView, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, this.$credentialFallbackButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$negativeButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$confirmationButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$cancelButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$negativeButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00808(this.$viewModel, this.$retryButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$credentialFallbackButton, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$accessibilityManager, this.$iconView, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$accessibilityManager, this.$iconView, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$subtitleView, this.$backgroundView, this.$legacyCallback, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$indicatorMessageView, this.$textColorError, this.$textColorHint, this.$accessibilityManager, null), 7);
                View view = this.$udfpsGuidanceView;
                final PromptViewModel promptViewModel = this.$viewModel;
                final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.8.14

                    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$1$8$14$1, reason: invalid class name and collision with other inner class name */
                    final class C00721 extends SuspendLambda implements Function2 {
                        final /* synthetic */ AccessibilityManager $accessibilityManager;
                        final /* synthetic */ MotionEvent $event;
                        final /* synthetic */ PromptViewModel $viewModel;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C00721(PromptViewModel promptViewModel, MotionEvent motionEvent, AccessibilityManager accessibilityManager, Continuation continuation) {
                            super(2, continuation);
                            this.$viewModel = promptViewModel;
                            this.$event = motionEvent;
                            this.$accessibilityManager = accessibilityManager;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C00721(this.$viewModel, this.$event, this.$accessibilityManager, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C00721) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                boolean zIsTouchExplorationEnabled = this.$accessibilityManager.isTouchExplorationEnabled();
                                this.label = 1;
                                if (promptViewModel.onAnnounceAccessibilityHint(motionEvent, zIsTouchExplorationEnabled, this) == coroutineSingletons) {
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
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00721(promptViewModel, motionEvent, accessibilityManager, null), 7);
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
        public AnonymousClass1(PromptViewModel promptViewModel, View view, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, LinearLayout linearLayout, Spaghetti.Callback callback, Button button, Button button2, Button button3, Button button4, Button button5, Spaghetti spaghetti, Ref$BooleanRef ref$BooleanRef, Animator.AnimatorListener animatorListener, LottieAnimationView lottieAnimationView, View view2, View view3, TextView textView5, AccessibilityManager accessibilityManager, int i, int i2, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, Continuation continuation) {
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$logoView, this.$logoDescriptionView, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$negativeButton, this.$cancelButton, this.$credentialFallbackButton, this.$confirmationButton, this.$retryButton, this.$adapter, this.$boundSize, this.$jankListener, this.$iconView, this.$udfpsGuidanceView, this.$backgroundView, this.$indicatorMessageView, this.$accessibilityManager, this.$textColorError, this.$textColorHint, this.$vibratorHelper, this.$msdlPlayer, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:75:0x05c6, code lost:
        
            if (androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r12, r2, r13, r81) != r1) goto L77;
         */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0105  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x01f6  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x02e9 A[LOOP:0: B:36:0x02e3->B:38:0x02e9, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x030f  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x032b  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0332  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x034c  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x036d A[PHI: r2 r10 r11 r12
          0x036d: PHI (r2v15 android.widget.TextView) = (r2v12 android.widget.TextView), (r2v18 android.widget.TextView) binds: [B:52:0x0369, B:9:0x004a] A[DONT_GENERATE, DONT_INLINE]
          0x036d: PHI (r10v14 com.android.systemui.biometrics.shared.model.BiometricModalities) = 
          (r10v11 com.android.systemui.biometrics.shared.model.BiometricModalities)
          (r10v17 com.android.systemui.biometrics.shared.model.BiometricModalities)
         binds: [B:52:0x0369, B:9:0x004a] A[DONT_GENERATE, DONT_INLINE]
          0x036d: PHI (r11v58 androidx.lifecycle.LifecycleOwner) = (r11v55 androidx.lifecycle.LifecycleOwner), (r11v61 androidx.lifecycle.LifecycleOwner) binds: [B:52:0x0369, B:9:0x004a] A[DONT_GENERATE, DONT_INLINE]
          0x036d: PHI (r12v18 java.lang.Object) = (r12v16 java.lang.Object), (r12v23 java.lang.Object) binds: [B:52:0x0369, B:9:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0388  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x03ac  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x041b  */
        /* JADX WARN: Type inference failed for: r9v2, types: [T, com.android.systemui.biometrics.ui.viewmodel.PromptPosition] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
            LifecycleOwner lifecycleOwner;
            Object objFirst;
            BiometricModalities biometricModalities;
            List listAsList;
            Iterator it;
            Object objFirst2;
            LifecycleOwner lifecycleOwner2;
            BiometricModalities biometricModalities2;
            Object objFirst3;
            BiometricModalities biometricModalities3;
            TextView textView;
            LifecycleOwner lifecycleOwner3;
            TextView textView2;
            Object objFirst4;
            TextView textView3;
            Object objFirst5;
            BiometricModalities biometricModalities4;
            LifecycleOwner lifecycleOwner4;
            Object objFirst6;
            BiometricCustomizedViewBinder biometricCustomizedViewBinder;
            LinearLayout linearLayout;
            LifecycleOwner lifecycleOwner5;
            Ref$BooleanRef ref$BooleanRef;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    lifecycleOwner = (LifecycleOwner) this.L$0;
                    Flow flow = this.$viewModel.modalities;
                    this.L$0 = lifecycleOwner;
                    this.label = 1;
                    objFirst = FlowKt.first(flow, this);
                    if (objFirst != coroutineSingletons) {
                        biometricModalities = (BiometricModalities) objFirst;
                        if (!biometricModalities.getHasFaceAndFingerprint()) {
                            PromptIconViewModel promptIconViewModel = this.$viewModel.iconViewModel;
                            boolean hasSfps = biometricModalities.getHasSfps();
                            promptIconViewModel.getClass();
                            listAsList = hasSfps ? Arrays.asList(Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_unlock), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_90), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_180), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_270), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_90), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_180), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_270), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_unlock), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_unlock_90), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_unlock_180), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_unlock_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_270), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_90), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_180), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_270)) : Arrays.asList(Integer.valueOf(R.raw.fingerprint_dialogue_unlocked_to_checkmark_success_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_error_to_unlock_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_fingerprint_to_unlock_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_error_to_success_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_fingerprint_to_success_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_error_to_fingerprint_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_fingerprint_to_error_lottie));
                        } else if (biometricModalities.getHasFingerprint() && biometricModalities.faceProperties == null) {
                            PromptIconViewModel promptIconViewModel2 = this.$viewModel.iconViewModel;
                            boolean hasSfps2 = biometricModalities.getHasSfps();
                            promptIconViewModel2.getClass();
                            listAsList = hasSfps2 ? Arrays.asList(Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_90), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_180), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_error_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_270), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_90), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_180), Integer.valueOf(R.raw.biometricprompt_sfps_error_to_fingerprint_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_270), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_90), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_180), Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_to_success_270), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_90), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_180), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_270)) : Arrays.asList(Integer.valueOf(R.raw.fingerprint_dialogue_error_to_fingerprint_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_error_to_success_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_fingerprint_to_error_lottie), Integer.valueOf(R.raw.fingerprint_dialogue_fingerprint_to_success_lottie));
                        } else if (biometricModalities.getHasFaceOnly()) {
                            this.$viewModel.iconViewModel.getClass();
                            listAsList = Arrays.asList(Integer.valueOf(R.raw.face_dialog_wink_from_dark), Integer.valueOf(R.raw.face_dialog_dark_to_checkmark), Integer.valueOf(R.raw.face_dialog_dark_to_error), Integer.valueOf(R.raw.face_dialog_error_to_idle), Integer.valueOf(R.raw.face_dialog_idle_static), Integer.valueOf(R.raw.face_dialog_authenticating));
                        } else {
                            listAsList = EmptyList.INSTANCE;
                        }
                        it = listAsList.iterator();
                        while (it.hasNext()) {
                            LottieCompositionFactory.fromRawRes(((Number) it.next()).intValue(), this.$view.getContext());
                        }
                        Flow flow2 = this.$viewModel.logoInfo;
                        this.L$0 = lifecycleOwner;
                        this.L$1 = biometricModalities;
                        this.label = 2;
                        objFirst2 = FlowKt.first(flow2, this);
                        if (objFirst2 != coroutineSingletons) {
                            lifecycleOwner2 = lifecycleOwner;
                            biometricModalities2 = biometricModalities;
                            Pair pair = (Pair) objFirst2;
                            this.$logoView.setImageDrawable((Drawable) pair.getFirst());
                            TextView textView4 = this.$logoDescriptionView;
                            String str = (String) pair.getSecond();
                            textView4.setText(str == null ? Utils.ellipsize(30, str) : null);
                            TextView textView5 = this.$titleView;
                            Flow flow3 = this.$viewModel.title;
                            this.L$0 = lifecycleOwner2;
                            this.L$1 = biometricModalities2;
                            this.L$2 = textView5;
                            this.label = 3;
                            objFirst3 = FlowKt.first(flow3, this);
                            if (objFirst3 != coroutineSingletons) {
                                LifecycleOwner lifecycleOwner6 = lifecycleOwner2;
                                biometricModalities3 = biometricModalities2;
                                textView = textView5;
                                lifecycleOwner3 = lifecycleOwner6;
                                textView.setText((CharSequence) objFirst3);
                                textView2 = this.$subtitleView;
                                Flow flow4 = this.$viewModel.subtitle;
                                this.L$0 = lifecycleOwner3;
                                this.L$1 = biometricModalities3;
                                this.L$2 = textView2;
                                this.label = 4;
                                objFirst4 = FlowKt.first(flow4, this);
                                if (objFirst4 != coroutineSingletons) {
                                    textView2.setText((CharSequence) objFirst4);
                                    textView3 = this.$descriptionView;
                                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.description;
                                    this.L$0 = lifecycleOwner3;
                                    this.L$1 = biometricModalities3;
                                    this.L$2 = textView3;
                                    this.label = 5;
                                    objFirst5 = FlowKt.first(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this);
                                    if (objFirst5 != coroutineSingletons) {
                                        LifecycleOwner lifecycleOwner7 = lifecycleOwner3;
                                        biometricModalities4 = biometricModalities3;
                                        lifecycleOwner4 = lifecycleOwner7;
                                        textView3.setText((CharSequence) objFirst5);
                                        BiometricCustomizedViewBinder biometricCustomizedViewBinder2 = BiometricCustomizedViewBinder.INSTANCE;
                                        LinearLayout linearLayout2 = this.$customizedViewContainer;
                                        Flow flow5 = this.$viewModel.contentView;
                                        this.L$0 = lifecycleOwner4;
                                        this.L$1 = biometricModalities4;
                                        this.L$2 = biometricCustomizedViewBinder2;
                                        this.L$3 = linearLayout2;
                                        this.label = 6;
                                        objFirst6 = FlowKt.first(flow5, this);
                                        if (objFirst6 != coroutineSingletons) {
                                            LifecycleOwner lifecycleOwner8 = lifecycleOwner4;
                                            biometricCustomizedViewBinder = biometricCustomizedViewBinder2;
                                            linearLayout = linearLayout2;
                                            lifecycleOwner5 = lifecycleOwner8;
                                            Spaghetti.Callback callback = this.$legacyCallback;
                                            biometricCustomizedViewBinder.getClass();
                                            RepeatWhenAttachedKt.repeatWhenAttached(linearLayout, EmptyCoroutineContext.INSTANCE, new BiometricCustomizedViewBinder$bind$1((PromptContentView) objFirst6, callback, null));
                                            Button button = this.$negativeButton;
                                            final Spaghetti.Callback callback2 = this.$legacyCallback;
                                            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.1
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    callback2.onButtonNegative();
                                                }
                                            });
                                            Button button2 = this.$cancelButton;
                                            final Spaghetti.Callback callback3 = this.$legacyCallback;
                                            button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.2
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    callback3.onUserCanceled();
                                                }
                                            });
                                            Button button3 = this.$credentialFallbackButton;
                                            final PromptViewModel promptViewModel = this.$viewModel;
                                            final Spaghetti.Callback callback4 = this.$legacyCallback;
                                            button3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.3
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    promptViewModel.onSwitchToCredential();
                                                    callback4.onUseDeviceCredential();
                                                }
                                            });
                                            Button button4 = this.$confirmationButton;
                                            final PromptViewModel promptViewModel2 = this.$viewModel;
                                            button4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.4
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    promptViewModel2.confirmAuthenticated();
                                                }
                                            });
                                            Button button5 = this.$retryButton;
                                            final PromptViewModel promptViewModel3 = this.$viewModel;
                                            final Spaghetti.Callback callback5 = this.$legacyCallback;
                                            button5.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.5
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    PromptViewModel.showAuthenticating$default(promptViewModel3, null, 1);
                                                    callback5.onButtonTryAgain();
                                                }
                                            });
                                            final Spaghetti spaghetti = this.$adapter;
                                            Spaghetti.Callback callback6 = this.$legacyCallback;
                                            spaghetti.modalities = biometricModalities4;
                                            spaghetti.legacyCallback = callback6;
                                            lifecycleOwner5.getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.biometrics.ui.binder.Spaghetti$attach$1
                                                @Override // androidx.lifecycle.DefaultLifecycleObserver
                                                public final void onCreate(LifecycleOwner lifecycleOwner9) {
                                                    LifecycleKt.getCoroutineScope(lifecycleOwner9.getLifecycle());
                                                    spaghetti.getClass();
                                                }

                                                @Override // androidx.lifecycle.DefaultLifecycleObserver
                                                public final void onDestroy$1() {
                                                    spaghetti.getClass();
                                                }
                                            });
                                            ref$BooleanRef = this.$boundSize;
                                            if (!ref$BooleanRef.element) {
                                                ref$BooleanRef.element = true;
                                                BiometricViewSizeBinder biometricViewSizeBinder = BiometricViewSizeBinder.INSTANCE;
                                                final View view = this.$view;
                                                final PromptViewModel promptViewModel4 = this.$viewModel;
                                                final List listAsList2 = Arrays.asList(this.$logoView, this.$logoDescriptionView, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer);
                                                biometricViewSizeBinder.getClass();
                                                final WindowManager windowManager = WindowManagerUtils.getWindowManager(view.getContext());
                                                Object systemService = view.getContext().getSystemService((Class<Object>) AccessibilityManager.class);
                                                if (systemService == null) {
                                                    throw new IllegalArgumentException("Required value was null.");
                                                }
                                                final AccessibilityManager accessibilityManager = (AccessibilityManager) systemService;
                                                final Guideline guideline = (Guideline) view.requireViewById(R.id.leftGuideline);
                                                final Guideline guideline2 = (Guideline) view.requireViewById(R.id.topGuideline);
                                                final Guideline guideline3 = (Guideline) view.requireViewById(R.id.rightGuideline);
                                                final Guideline guideline4 = (Guideline) view.findViewById(R.id.midGuideline);
                                                final View viewRequireViewById = view.requireViewById(R.id.biometric_icon);
                                                final View viewRequireViewById2 = view.requireViewById(R.id.panel);
                                                float dimension = view.getResources().getDimension(R.dimen.biometric_dialog_corner_size);
                                                final float fApplyDimension = TypedValue.applyDimension(1, 1.0f, view.getResources().getDisplayMetrics());
                                                final int i = (int) (dimension * fApplyDimension);
                                                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                                                ref$ObjectRef2.element = PromptPosition.Bottom;
                                                viewRequireViewById2.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$1

                                                    public abstract /* synthetic */ class WhenMappings {
                                                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                                        static {
                                                            int[] iArr = new int[PromptPosition.values().length];
                                                            try {
                                                                iArr[PromptPosition.Right.ordinal()] = 1;
                                                            } catch (NoSuchFieldError unused) {
                                                            }
                                                            try {
                                                                iArr[PromptPosition.Left.ordinal()] = 2;
                                                            } catch (NoSuchFieldError unused2) {
                                                            }
                                                            try {
                                                                iArr[PromptPosition.Bottom.ordinal()] = 3;
                                                            } catch (NoSuchFieldError unused3) {
                                                            }
                                                            try {
                                                                iArr[PromptPosition.Top.ordinal()] = 4;
                                                            } catch (NoSuchFieldError unused4) {
                                                            }
                                                            $EnumSwitchMapping$0 = iArr;
                                                        }
                                                    }

                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // android.view.ViewOutlineProvider
                                                    public final void getOutline(View view2, Outline outline) {
                                                        int i2 = WhenMappings.$EnumSwitchMapping$0[((PromptPosition) ref$ObjectRef2.element).ordinal()];
                                                        if (i2 == 1) {
                                                            outline.setRoundRect(0, 0, view2.getWidth() + i, view2.getHeight(), i);
                                                            return;
                                                        }
                                                        if (i2 == 2) {
                                                            outline.setRoundRect(-i, 0, view2.getWidth(), view2.getHeight(), i);
                                                            return;
                                                        }
                                                        if (i2 != 3 && i2 != 4) {
                                                            throw new NoWhenBranchMatchedException();
                                                        }
                                                        int width = view2.getWidth();
                                                        int height = view2.getHeight();
                                                        int i3 = i;
                                                        outline.setRoundRect(0, 0, width, height + i3, i3);
                                                    }
                                                });
                                                final ConstraintSet constraintSet = new ConstraintSet();
                                                constraintSet.clone((ConstraintLayout) view);
                                                final ConstraintSet constraintSet2 = new ConstraintSet();
                                                constraintSet2.clone(constraintSet);
                                                final ConstraintSet constraintSet3 = new ConstraintSet();
                                                constraintSet3.clone(constraintSet);
                                                constraintSet3.constrainMaxWidth(R.id.panel, 0);
                                                constraintSet3.setGuidelineBegin(R.id.leftGuideline, 0);
                                                constraintSet3.setGuidelineEnd(R.id.rightGuideline, 0);
                                                final ConstraintSet constraintSet4 = new ConstraintSet();
                                                if (!view.isLaidOut() || view.isLayoutRequested()) {
                                                    view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$$inlined$doOnLayout$1
                                                        @Override // android.view.View.OnLayoutChangeListener
                                                        public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                                                            view2.removeOnLayoutChangeListener(this);
                                                            View view3 = view;
                                                            RepeatWhenAttachedKt.repeatWhenAttached(view3, EmptyCoroutineContext.INSTANCE, new BiometricViewSizeBinder$bind$2$1(promptViewModel4, viewRequireViewById, constraintSet, constraintSet2, windowManager, guideline, guideline3, guideline2, guideline4, view3, listAsList2, constraintSet3, constraintSet4, fApplyDimension, ref$ObjectRef, ref$ObjectRef2, viewRequireViewById2, accessibilityManager, null));
                                                        }
                                                    });
                                                } else {
                                                    RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new BiometricViewSizeBinder$bind$2$1(promptViewModel4, viewRequireViewById, constraintSet, constraintSet2, windowManager, guideline, guideline3, guideline2, guideline4, view, listAsList2, constraintSet3, constraintSet4, fApplyDimension, ref$ObjectRef, ref$ObjectRef2, viewRequireViewById2, accessibilityManager, null));
                                                }
                                            }
                                            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner5.getLifecycle()), null, null, new AnonymousClass6(this.$viewModel, this.$iconView, null), 7);
                                            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner5.getLifecycle()), null, null, new AnonymousClass7(this.$viewModel, this.$legacyCallback, null), 7);
                                            Lifecycle.State state = Lifecycle.State.STARTED;
                                            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$udfpsGuidanceView, this.$viewModel, this.$backgroundView, this.$legacyCallback, this.$indicatorMessageView, this.$view, this.$credentialFallbackButton, this.$negativeButton, this.$confirmationButton, this.$cancelButton, this.$retryButton, this.$accessibilityManager, this.$iconView, this.$subtitleView, this.$textColorError, this.$textColorHint, this.$vibratorHelper, this.$msdlPlayer, null);
                                            this.L$0 = null;
                                            this.L$1 = null;
                                            this.L$2 = null;
                                            this.L$3 = null;
                                            this.label = 7;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                case 1:
                    lifecycleOwner = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objFirst = obj;
                    biometricModalities = (BiometricModalities) objFirst;
                    if (!biometricModalities.getHasFaceAndFingerprint()) {
                    }
                    it = listAsList.iterator();
                    while (it.hasNext()) {
                    }
                    Flow flow22 = this.$viewModel.logoInfo;
                    this.L$0 = lifecycleOwner;
                    this.L$1 = biometricModalities;
                    this.label = 2;
                    objFirst2 = FlowKt.first(flow22, this);
                    if (objFirst2 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 2:
                    biometricModalities2 = (BiometricModalities) this.L$1;
                    lifecycleOwner2 = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objFirst2 = obj;
                    Pair pair2 = (Pair) objFirst2;
                    this.$logoView.setImageDrawable((Drawable) pair2.getFirst());
                    TextView textView42 = this.$logoDescriptionView;
                    String str2 = (String) pair2.getSecond();
                    textView42.setText(str2 == null ? Utils.ellipsize(30, str2) : null);
                    TextView textView52 = this.$titleView;
                    Flow flow32 = this.$viewModel.title;
                    this.L$0 = lifecycleOwner2;
                    this.L$1 = biometricModalities2;
                    this.L$2 = textView52;
                    this.label = 3;
                    objFirst3 = FlowKt.first(flow32, this);
                    if (objFirst3 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 3:
                    textView = (TextView) this.L$2;
                    biometricModalities3 = (BiometricModalities) this.L$1;
                    lifecycleOwner3 = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objFirst3 = obj;
                    textView.setText((CharSequence) objFirst3);
                    textView2 = this.$subtitleView;
                    Flow flow42 = this.$viewModel.subtitle;
                    this.L$0 = lifecycleOwner3;
                    this.L$1 = biometricModalities3;
                    this.L$2 = textView2;
                    this.label = 4;
                    objFirst4 = FlowKt.first(flow42, this);
                    if (objFirst4 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 4:
                    textView2 = (TextView) this.L$2;
                    biometricModalities3 = (BiometricModalities) this.L$1;
                    lifecycleOwner3 = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objFirst4 = obj;
                    textView2.setText((CharSequence) objFirst4);
                    textView3 = this.$descriptionView;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = this.$viewModel.description;
                    this.L$0 = lifecycleOwner3;
                    this.L$1 = biometricModalities3;
                    this.L$2 = textView3;
                    this.label = 5;
                    objFirst5 = FlowKt.first(flowKt__ZipKt$combine$$inlined$unsafeFlow$12, this);
                    if (objFirst5 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 5:
                    textView3 = (TextView) this.L$2;
                    BiometricModalities biometricModalities5 = (BiometricModalities) this.L$1;
                    LifecycleOwner lifecycleOwner9 = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    biometricModalities4 = biometricModalities5;
                    lifecycleOwner4 = lifecycleOwner9;
                    objFirst5 = obj;
                    textView3.setText((CharSequence) objFirst5);
                    BiometricCustomizedViewBinder biometricCustomizedViewBinder22 = BiometricCustomizedViewBinder.INSTANCE;
                    LinearLayout linearLayout22 = this.$customizedViewContainer;
                    Flow flow52 = this.$viewModel.contentView;
                    this.L$0 = lifecycleOwner4;
                    this.L$1 = biometricModalities4;
                    this.L$2 = biometricCustomizedViewBinder22;
                    this.L$3 = linearLayout22;
                    this.label = 6;
                    objFirst6 = FlowKt.first(flow52, this);
                    if (objFirst6 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 6:
                    linearLayout = (LinearLayout) this.L$3;
                    biometricCustomizedViewBinder = (BiometricCustomizedViewBinder) this.L$2;
                    biometricModalities4 = (BiometricModalities) this.L$1;
                    lifecycleOwner5 = (LifecycleOwner) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objFirst6 = obj;
                    Spaghetti.Callback callback7 = this.$legacyCallback;
                    biometricCustomizedViewBinder.getClass();
                    RepeatWhenAttachedKt.repeatWhenAttached(linearLayout, EmptyCoroutineContext.INSTANCE, new BiometricCustomizedViewBinder$bind$1((PromptContentView) objFirst6, callback7, null));
                    Button button6 = this.$negativeButton;
                    final Spaghetti.Callback callback22 = this.$legacyCallback;
                    button6.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            callback22.onButtonNegative();
                        }
                    });
                    Button button22 = this.$cancelButton;
                    final Spaghetti.Callback callback32 = this.$legacyCallback;
                    button22.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            callback32.onUserCanceled();
                        }
                    });
                    Button button32 = this.$credentialFallbackButton;
                    final PromptViewModel promptViewModel5 = this.$viewModel;
                    final Spaghetti.Callback callback42 = this.$legacyCallback;
                    button32.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            promptViewModel5.onSwitchToCredential();
                            callback42.onUseDeviceCredential();
                        }
                    });
                    Button button42 = this.$confirmationButton;
                    final PromptViewModel promptViewModel22 = this.$viewModel;
                    button42.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            promptViewModel22.confirmAuthenticated();
                        }
                    });
                    Button button52 = this.$retryButton;
                    final PromptViewModel promptViewModel32 = this.$viewModel;
                    final Spaghetti.Callback callback52 = this.$legacyCallback;
                    button52.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder.bind.1.5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            PromptViewModel.showAuthenticating$default(promptViewModel32, null, 1);
                            callback52.onButtonTryAgain();
                        }
                    });
                    final Spaghetti spaghetti2 = this.$adapter;
                    Spaghetti.Callback callback62 = this.$legacyCallback;
                    spaghetti2.modalities = biometricModalities4;
                    spaghetti2.legacyCallback = callback62;
                    lifecycleOwner5.getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.biometrics.ui.binder.Spaghetti$attach$1
                        @Override // androidx.lifecycle.DefaultLifecycleObserver
                        public final void onCreate(LifecycleOwner lifecycleOwner92) {
                            LifecycleKt.getCoroutineScope(lifecycleOwner92.getLifecycle());
                            spaghetti2.getClass();
                        }

                        @Override // androidx.lifecycle.DefaultLifecycleObserver
                        public final void onDestroy$1() {
                            spaghetti2.getClass();
                        }
                    });
                    ref$BooleanRef = this.$boundSize;
                    if (!ref$BooleanRef.element) {
                    }
                    CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner5.getLifecycle()), null, null, new AnonymousClass6(this.$viewModel, this.$iconView, null), 7);
                    CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner5.getLifecycle()), null, null, new AnonymousClass7(this.$viewModel, this.$legacyCallback, null), 7);
                    Lifecycle.State state2 = Lifecycle.State.STARTED;
                    AnonymousClass8 anonymousClass82 = new AnonymousClass8(this.$udfpsGuidanceView, this.$viewModel, this.$backgroundView, this.$legacyCallback, this.$indicatorMessageView, this.$view, this.$credentialFallbackButton, this.$negativeButton, this.$confirmationButton, this.$cancelButton, this.$retryButton, this.$accessibilityManager, this.$iconView, this.$subtitleView, this.$textColorError, this.$textColorHint, this.$vibratorHelper, this.$msdlPlayer, null);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.L$3 = null;
                    this.label = 7;
                    break;
                case 7:
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    static {
        new BiometricViewBinder();
    }

    private BiometricViewBinder() {
    }

    public static final Spaghetti bind(final View view, PromptViewModel promptViewModel, AuthContainerView.AnonymousClass1 anonymousClass1, View view2, Spaghetti.Callback callback, CoroutineScope coroutineScope, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer) throws Resources.NotFoundException {
        Object systemService = view.getContext().getSystemService((Class<Object>) AccessibilityManager.class);
        systemService.getClass();
        AccessibilityManager accessibilityManager = (AccessibilityManager) systemService;
        int color = view.getResources().getColor(R.color.biometric_dialog_error, view.getContext().getTheme());
        TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(R.style.TextAppearance_AuthCredential_Indicator, new int[]{android.R.attr.textColor});
        int color2 = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        ImageView imageView = (ImageView) view.requireViewById(R.id.logo);
        TextView textView = (TextView) view.requireViewById(R.id.logo_description);
        TextView textView2 = (TextView) view.requireViewById(R.id.title);
        TextView textView3 = (TextView) view.requireViewById(R.id.subtitle);
        TextView textView4 = (TextView) view.requireViewById(R.id.description);
        LinearLayout linearLayout = (LinearLayout) view.requireViewById(R.id.customized_view_container);
        View viewRequireViewById = view.requireViewById(R.id.panel);
        textView2.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
        textView3.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) view.requireViewById(R.id.biometric_icon);
        TextView textView5 = (TextView) view.requireViewById(R.id.indicator);
        Button button = (Button) view.requireViewById(R.id.button_negative);
        Button button2 = (Button) view.requireViewById(R.id.button_cancel);
        Button button3 = (Button) view.requireViewById(R.id.button_use_credential);
        Button button4 = (Button) view.requireViewById(R.id.button_confirm);
        Button button5 = (Button) view.requireViewById(R.id.button_try_again);
        AccessibilityDelegateCompat accessibilityDelegateCompat = new AccessibilityDelegateCompat() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewBinder$bind$cancelDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view3, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view3, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, view.getContext().getString(R.string.biometric_dialog_cancel_authentication)));
            }
        };
        ViewCompat.setAccessibilityDelegate(view2, accessibilityDelegateCompat);
        ViewCompat.setAccessibilityDelegate(button2, accessibilityDelegateCompat);
        Spaghetti spaghetti = new Spaghetti(view, promptViewModel, view.getContext().getApplicationContext(), coroutineScope);
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(promptViewModel, view, imageView, textView, textView2, textView3, textView4, linearLayout, callback, button, button2, button3, button4, button5, spaghetti, new Ref$BooleanRef(), anonymousClass1, lottieAnimationView, viewRequireViewById, view2, textView5, accessibilityManager, color, color2, vibratorHelper, mSDLPlayer, null));
        return spaghetti;
    }
}
