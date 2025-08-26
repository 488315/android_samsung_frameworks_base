package com.android.systemui.keyguard.ui.binder;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable2;
import android.os.VibrationEffect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.animation.Animator;
import androidx.core.animation.CycleInterpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $alpha;
    final /* synthetic */ ImageView $button;
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
    final /* synthetic */ KeyguardQuickAffordanceHapticViewModel $hapticsViewModel;
    final /* synthetic */ Function1 $messageDisplayer;
    final /* synthetic */ Flow $viewModel;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceViewBinder this$0;

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $alpha;
        final /* synthetic */ ImageView $button;
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        final /* synthetic */ KeyguardQuickAffordanceHapticViewModel $hapticsViewModel;
        final /* synthetic */ Function1 $messageDisplayer;
        final /* synthetic */ Flow $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ KeyguardQuickAffordanceViewBinder this$0;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02981 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $button;
            final /* synthetic */ KeyguardQuickAffordanceHapticViewModel $hapticsViewModel;
            final /* synthetic */ Function1 $messageDisplayer;
            final /* synthetic */ Flow $viewModel;
            int label;
            final /* synthetic */ KeyguardQuickAffordanceViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02981(Flow flow, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, ImageView imageView, Function1 function1, KeyguardQuickAffordanceHapticViewModel keyguardQuickAffordanceHapticViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = flow;
                this.this$0 = keyguardQuickAffordanceViewBinder;
                this.$button = imageView;
                this.$messageDisplayer = function1;
                this.$hapticsViewModel = keyguardQuickAffordanceHapticViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02981(this.$viewModel, this.this$0, this.$button, this.$messageDisplayer, this.$hapticsViewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02981) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel;
                    final KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder = this.this$0;
                    final ImageView imageView = this.$button;
                    final Function1 function1 = this.$messageDisplayer;
                    final KeyguardQuickAffordanceHapticViewModel keyguardQuickAffordanceHapticViewModel = this.$hapticsViewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder.bind.disposableHandle.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ColorStateList colorStateListValueOf;
                            final ImageView imageView2;
                            final KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj2;
                            ImageView imageView3 = imageView;
                            final KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder2 = keyguardQuickAffordanceViewBinder;
                            KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger = keyguardQuickAffordanceViewBinder2.logger;
                            keyguardQuickAffordancesLogger.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 keyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 = new KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0(1);
                            LogBuffer logBuffer = keyguardQuickAffordancesLogger.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("KeyguardQuickAffordancesLogger", logLevel, keyguardQuickAffordancesLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = keyguardQuickAffordanceViewModel.toString();
                            logBuffer.commit(logMessageObtain);
                            boolean z = keyguardQuickAffordanceViewModel.isVisible;
                            boolean z2 = keyguardQuickAffordanceViewModel.isActivated;
                            if (z) {
                                if (imageView3.getVisibility() != 0) {
                                    imageView3.setVisibility(0);
                                }
                                IconViewBinder.INSTANCE.getClass();
                                Icon icon = keyguardQuickAffordanceViewModel.icon;
                                IconViewBinder.bind(icon, imageView3);
                                Object drawable = imageView3.getDrawable();
                                Animatable2 animatable2 = drawable instanceof Animatable2 ? (Animatable2) drawable : null;
                                if (animatable2 != null) {
                                    Icon.Resource resource = icon instanceof Icon.Resource ? (Icon.Resource) icon : null;
                                    if (resource != null) {
                                        animatable2.start();
                                        Object tag = imageView3.getTag();
                                        int i2 = resource.res;
                                        if (Intrinsics.areEqual(tag, Integer.valueOf(i2))) {
                                            animatable2.stop();
                                        } else {
                                            imageView3.setTag(Integer.valueOf(i2));
                                        }
                                    }
                                }
                                imageView3.setActivated(z2);
                                imageView3.getDrawable().setTint(imageView3.getContext().getColor(z2 ? R.color.ripple_material_dark : R.color.search_url_text_material_light));
                                boolean z3 = keyguardQuickAffordanceViewModel.isSelected;
                                if (z3) {
                                    colorStateListValueOf = null;
                                } else {
                                    colorStateListValueOf = ColorStateList.valueOf(imageView3.getContext().getColor(z2 ? R.color.secondary_text_material_dark : R.color.sliding_tab_text_color_shadow));
                                }
                                imageView3.setBackgroundTintList(colorStateListValueOf);
                                imageView3.animate().scaleX(z3 ? 1.23f : 1.0f).scaleY(z3 ? 1.23f : 1.0f).start();
                                boolean z4 = keyguardQuickAffordanceViewModel.isClickable;
                                imageView3.setClickable(z4);
                                if (!z4) {
                                    imageView2 = imageView3;
                                    imageView2.setOnLongClickListener(null);
                                    imageView2.setOnClickListener(null);
                                    imageView2.setOnTouchListener(null);
                                } else if (keyguardQuickAffordanceViewModel.useLongPress) {
                                    VibratorHelper vibratorHelper = keyguardQuickAffordanceViewBinder2.vibratorHelper;
                                    FalsingManager falsingManager = keyguardQuickAffordanceViewBinder2.falsingManager;
                                    final Function1 function12 = function1;
                                    KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener = new KeyguardQuickAffordanceOnTouchListener(imageView3, keyguardQuickAffordanceViewModel, function12, vibratorHelper, falsingManager);
                                    imageView2 = imageView3;
                                    imageView2.setOnTouchListener(keyguardQuickAffordanceOnTouchListener);
                                    imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButton$2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            Pair pair;
                                            function12.mo781invoke(Integer.valueOf(com.android.systemui.R.string.keyguard_affordance_press_too_short));
                                            float dimensionPixelSize = imageView2.getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_affordance_shake_amplitude);
                                            float f = 2;
                                            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView2, "translationX", (-dimensionPixelSize) / f, dimensionPixelSize / f);
                                            KeyguardBottomAreaVibrations.INSTANCE.getClass();
                                            objectAnimatorOfFloat.m896setDuration(Duration.m3457getInWholeMillisecondsimpl(KeyguardBottomAreaVibrations.ShakeAnimationDuration));
                                            objectAnimatorOfFloat.mInterpolator = new CycleInterpolator(KeyguardBottomAreaVibrations.ShakeAnimationCycles);
                                            final ImageView imageView4 = imageView2;
                                            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButton$2$onClick$$inlined$doOnEnd$1
                                                @Override // androidx.core.animation.Animator.AnimatorListener
                                                public final void onAnimationEnd(Animator animator) {
                                                    imageView4.setTranslationX(0.0f);
                                                }

                                                @Override // androidx.core.animation.Animator.AnimatorListener
                                                public final void onAnimationCancel(Animator animator) {
                                                }

                                                @Override // androidx.core.animation.Animator.AnimatorListener
                                                public final void onAnimationRepeat(Animator animator) {
                                                }

                                                @Override // androidx.core.animation.Animator.AnimatorListener
                                                public final void onAnimationStart(Animator animator) {
                                                }
                                            });
                                            objectAnimatorOfFloat.start();
                                            KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder3 = keyguardQuickAffordanceViewBinder2;
                                            VibratorHelper vibratorHelper2 = keyguardQuickAffordanceViewBinder3.vibratorHelper;
                                            if (vibratorHelper2 != null) {
                                                VibrationEffect vibrationEffect = KeyguardBottomAreaVibrations.Shake;
                                                MSDLPlayer mSDLPlayer = keyguardQuickAffordanceViewBinder3.msdlPlayer;
                                                vibratorHelper2.vibrate(vibrationEffect);
                                            }
                                            KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger2 = keyguardQuickAffordanceViewBinder2.logger;
                                            String str = keyguardQuickAffordanceViewModel.configKey;
                                            keyguardQuickAffordancesLogger2.getClass();
                                            if (str != null) {
                                                List listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, 0, 6);
                                                pair = new Pair(listSplit$default.get(0), listSplit$default.get(1));
                                            } else {
                                                pair = new Pair("", "");
                                            }
                                            String str2 = (String) pair.component1();
                                            String str3 = (String) pair.component2();
                                            LogLevel logLevel2 = LogLevel.DEBUG;
                                            KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 keyguardQuickAffordancesLogger$$ExternalSyntheticLambda02 = new KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0(2);
                                            LogBuffer logBuffer2 = keyguardQuickAffordancesLogger2.buffer;
                                            LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardQuickAffordancesLogger", logLevel2, keyguardQuickAffordancesLogger$$ExternalSyntheticLambda02, null);
                                            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                                            logMessageImpl.str1 = str3;
                                            logMessageImpl.str2 = str2;
                                            logBuffer2.commit(logMessageObtain2);
                                        }
                                    });
                                    imageView2.setOnLongClickListener(new KeyguardQuickAffordanceViewBinder.OnLongClickListener(keyguardQuickAffordanceViewBinder2.falsingManager, keyguardQuickAffordanceViewModel, keyguardQuickAffordanceViewBinder2.vibratorHelper, keyguardQuickAffordanceOnTouchListener, keyguardQuickAffordanceViewBinder2.msdlPlayer));
                                } else {
                                    imageView2 = imageView3;
                                    FalsingManager falsingManager2 = keyguardQuickAffordanceViewBinder2.falsingManager;
                                    if (falsingManager2 == null) {
                                        throw new IllegalStateException("Required value was null.");
                                    }
                                    imageView2.setOnClickListener(new KeyguardQuickAffordanceViewBinder.OnClickListener(keyguardQuickAffordanceViewModel, falsingManager2));
                                    imageView2.setOnLongClickListener(null);
                                    imageView2.setLongClickable(false);
                                }
                                imageView2.setSelected(z3);
                            } else {
                                imageView3.setVisibility(4);
                            }
                            KeyguardQuickAffordanceHapticViewModel keyguardQuickAffordanceHapticViewModel2 = keyguardQuickAffordanceHapticViewModel;
                            if (keyguardQuickAffordanceHapticViewModel2 != null) {
                                StateFlowImpl stateFlowImpl = keyguardQuickAffordanceHapticViewModel2.activatedHistory;
                                stateFlowImpl.updateState(null, new KeyguardQuickAffordanceHapticViewModel.ActivatedHistory(z2, Boolean.valueOf(((KeyguardQuickAffordanceHapticViewModel.ActivatedHistory) stateFlowImpl.getValue()).currentValue)));
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Flow $alpha;
            final /* synthetic */ ImageView $button;
            final /* synthetic */ Flow $viewModel;
            int label;
            final /* synthetic */ KeyguardQuickAffordanceViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, ImageView imageView, Flow flow, Flow flow2, Continuation continuation) {
                super(2, continuation);
                this.this$0 = keyguardQuickAffordanceViewBinder;
                this.$button = imageView;
                this.$viewModel = flow;
                this.$alpha = flow2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, this.$button, this.$viewModel, this.$alpha, continuation);
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
                    KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder = this.this$0;
                    final ImageView imageView = this.$button;
                    final Flow flow = this.$viewModel;
                    Flow flow2 = this.$alpha;
                    this.label = 1;
                    keyguardQuickAffordanceViewBinder.getClass();
                    Object objCollect = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButtonAlpha$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButtonAlpha$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButtonAlpha$$inlined$map$1$2$1, reason: invalid class name */
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
                                    Boolean boolValueOf = Boolean.valueOf(((KeyguardQuickAffordanceViewModel) obj).isDimmed);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect2 = flow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect2 : Unit.INSTANCE;
                        }
                    }, flow2, new KeyguardQuickAffordanceViewBinder$updateButtonAlpha$3(keyguardQuickAffordanceViewBinder, null)).collect(new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$updateButtonAlpha$4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            imageView.setAlpha(((Number) obj2).floatValue());
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $button;
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(MutableStateFlow mutableStateFlow, ImageView imageView, Continuation continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$button = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$configurationBasedDimensions, this.$button, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    final ImageView imageView = this.$button;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder.bind.disposableHandle.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardQuickAffordanceViewBinder.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardQuickAffordanceViewBinder.ConfigurationBasedDimensions) obj2;
                            ImageView imageView2 = imageView;
                            ViewGroup.LayoutParams layoutParams = imageView2.getLayoutParams();
                            if (layoutParams == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            layoutParams.width = configurationBasedDimensions.buttonSizePx.getWidth();
                            layoutParams.height = configurationBasedDimensions.buttonSizePx.getHeight();
                            imageView2.setLayoutParams(layoutParams);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(Flow flow, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, ImageView imageView, Function1 function1, KeyguardQuickAffordanceHapticViewModel keyguardQuickAffordanceHapticViewModel, Flow flow2, MutableStateFlow mutableStateFlow, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = flow;
            this.this$0 = keyguardQuickAffordanceViewBinder;
            this.$button = imageView;
            this.$messageDisplayer = function1;
            this.$hapticsViewModel = keyguardQuickAffordanceHapticViewModel;
            this.$alpha = flow2;
            this.$configurationBasedDimensions = mutableStateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.this$0, this.$button, this.$messageDisplayer, this.$hapticsViewModel, this.$alpha, this.$configurationBasedDimensions, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02981(this.$viewModel, this.this$0, this.$button, this.$messageDisplayer, this.$hapticsViewModel, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$button, this.$viewModel, this.$alpha, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$configurationBasedDimensions, this.$button, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1(Flow flow, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, ImageView imageView, Function1 function1, KeyguardQuickAffordanceHapticViewModel keyguardQuickAffordanceHapticViewModel, Flow flow2, MutableStateFlow mutableStateFlow, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = flow;
        this.this$0 = keyguardQuickAffordanceViewBinder;
        this.$button = imageView;
        this.$messageDisplayer = function1;
        this.$hapticsViewModel = keyguardQuickAffordanceHapticViewModel;
        this.$alpha = flow2;
        this.$configurationBasedDimensions = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1 keyguardQuickAffordanceViewBinder$bind$disposableHandle$1 = new KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1(this.$viewModel, this.this$0, this.$button, this.$messageDisplayer, this.$hapticsViewModel, this.$alpha, this.$configurationBasedDimensions, (Continuation) obj3);
        keyguardQuickAffordanceViewBinder$bind$disposableHandle$1.L$0 = (LifecycleOwner) obj;
        return keyguardQuickAffordanceViewBinder$bind$disposableHandle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.this$0, this.$button, this.$messageDisplayer, this.$hapticsViewModel, this.$alpha, this.$configurationBasedDimensions, null);
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
