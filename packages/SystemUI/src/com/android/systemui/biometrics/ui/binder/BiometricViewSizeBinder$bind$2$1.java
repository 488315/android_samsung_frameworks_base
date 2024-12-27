package com.android.systemui.biometrics.ui.binder;

import android.graphics.Rect;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.R;
import com.android.systemui.biometrics.ui.viewmodel.PromptPosition;
import com.android.systemui.biometrics.ui.viewmodel.PromptSize;
import com.android.systemui.biometrics.ui.viewmodel.PromptSizeKt;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class BiometricViewSizeBinder$bind$2$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AccessibilityManager $accessibilityManager;
    final /* synthetic */ Ref$ObjectRef<PromptPosition> $currentPosition;
    final /* synthetic */ Ref$ObjectRef<PromptSize> $currentSize;
    final /* synthetic */ ConstraintSet $flipConstraintSet;
    final /* synthetic */ View $iconHolderView;
    final /* synthetic */ ConstraintSet $largeConstraintSet;
    final /* synthetic */ Guideline $leftGuideline;
    final /* synthetic */ ConstraintSet $mediumConstraintSet;
    final /* synthetic */ Guideline $midGuideline;
    final /* synthetic */ View $panelView;
    final /* synthetic */ float $pxToDp;
    final /* synthetic */ Guideline $rightGuideline;
    final /* synthetic */ ConstraintSet $smallConstraintSet;
    final /* synthetic */ Guideline $topGuideline;
    final /* synthetic */ View $view;
    final /* synthetic */ PromptViewModel $viewModel;
    final /* synthetic */ List<View> $viewsToHideWhenSmall;
    final /* synthetic */ WindowManager $windowManager;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $iconHolderView;
        final /* synthetic */ ConstraintSet $mediumConstraintSet;
        final /* synthetic */ ConstraintSet $smallConstraintSet;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PromptViewModel promptViewModel, View view, ConstraintSet constraintSet, ConstraintSet constraintSet2, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$iconHolderView = view;
            this.$mediumConstraintSet = constraintSet;
            this.$smallConstraintSet = constraintSet2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$iconHolderView, this.$mediumConstraintSet, this.$smallConstraintSet, continuation);
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
                Flow flow = this.$viewModel.iconPosition;
                final View view = this.$iconHolderView;
                final ConstraintSet constraintSet = this.$mediumConstraintSet;
                final ConstraintSet constraintSet2 = this.$smallConstraintSet;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder.bind.2.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Rect rect = (Rect) obj2;
                        if (!Intrinsics.areEqual(rect, new Rect())) {
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                            int i2 = rect.left;
                            ConstraintSet constraintSet3 = constraintSet2;
                            ConstraintSet constraintSet4 = constraintSet;
                            if (i2 != 0) {
                                layoutParams.endToEnd = -1;
                                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i2;
                                constraintSet4.clear(R.id.biometric_icon, 2);
                                constraintSet4.connect(R.id.biometric_icon, 1, 0, 1);
                                constraintSet4.setMargin(R.id.biometric_icon, 1, rect.left);
                                constraintSet3.clear(R.id.biometric_icon, 2);
                                constraintSet3.connect(R.id.biometric_icon, 1, 0, 1);
                                constraintSet3.setMargin(R.id.biometric_icon, 1, rect.left);
                            }
                            int i3 = rect.top;
                            if (i3 != 0) {
                                layoutParams.bottomToBottom = -1;
                                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = i3;
                                constraintSet4.clear(R.id.biometric_icon, 4);
                                constraintSet4.setMargin(R.id.biometric_icon, 3, rect.top);
                                constraintSet3.clear(R.id.biometric_icon, 4);
                                constraintSet3.setMargin(R.id.biometric_icon, 3, rect.top);
                            }
                            int i4 = rect.right;
                            if (i4 != 0) {
                                layoutParams.startToStart = -1;
                                ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i4;
                                constraintSet4.clear(R.id.biometric_icon, 1);
                                constraintSet4.connect(R.id.biometric_icon, 2, 0, 2);
                                constraintSet4.setMargin(R.id.biometric_icon, 2, rect.right);
                                constraintSet3.clear(R.id.biometric_icon, 1);
                                constraintSet3.connect(R.id.biometric_icon, 2, 0, 2);
                                constraintSet3.setMargin(R.id.biometric_icon, 2, rect.right);
                            }
                            int i5 = rect.bottom;
                            if (i5 != 0) {
                                layoutParams.topToTop = -1;
                                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = i5;
                                constraintSet4.clear(R.id.biometric_icon, 3);
                                constraintSet4.setMargin(R.id.biometric_icon, 4, rect.bottom);
                                constraintSet3.clear(R.id.biometric_icon, 3);
                                constraintSet3.setMargin(R.id.biometric_icon, 4, rect.bottom);
                            }
                            view.setLayoutParams(layoutParams);
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
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $iconHolderView;
        final /* synthetic */ ConstraintSet $mediumConstraintSet;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PromptViewModel promptViewModel, View view, ConstraintSet constraintSet, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$iconHolderView = view;
            this.$mediumConstraintSet = constraintSet;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$viewModel, this.$iconHolderView, this.$mediumConstraintSet, continuation);
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
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.iconSize;
                final View view = this.$iconHolderView;
                final ConstraintSet constraintSet = this.$mediumConstraintSet;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder.bind.2.1.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        view.getLayoutParams().width = ((Number) pair.getFirst()).intValue();
                        view.getLayoutParams().height = ((Number) pair.getSecond()).intValue();
                        int intValue = ((Number) pair.getFirst()).intValue();
                        ConstraintSet constraintSet2 = constraintSet;
                        constraintSet2.constrainWidth(R.id.biometric_icon, intValue);
                        constraintSet2.constrainHeight(R.id.biometric_icon, ((Number) pair.getSecond()).intValue());
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
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Guideline $leftGuideline;
        final /* synthetic */ ConstraintSet $mediumConstraintSet;
        final /* synthetic */ Guideline $midGuideline;
        final /* synthetic */ Guideline $rightGuideline;
        final /* synthetic */ ConstraintSet $smallConstraintSet;
        final /* synthetic */ Guideline $topGuideline;
        final /* synthetic */ View $view;
        final /* synthetic */ PromptViewModel $viewModel;
        final /* synthetic */ WindowManager $windowManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(PromptViewModel promptViewModel, WindowManager windowManager, ConstraintSet constraintSet, Guideline guideline, ConstraintSet constraintSet2, Guideline guideline2, Guideline guideline3, Guideline guideline4, View view, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$windowManager = windowManager;
            this.$mediumConstraintSet = constraintSet;
            this.$leftGuideline = guideline;
            this.$smallConstraintSet = constraintSet2;
            this.$rightGuideline = guideline2;
            this.$topGuideline = guideline3;
            this.$midGuideline = guideline4;
            this.$view = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$viewModel, this.$windowManager, this.$mediumConstraintSet, this.$leftGuideline, this.$smallConstraintSet, this.$rightGuideline, this.$topGuideline, this.$midGuideline, this.$view, continuation);
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
                Flow flow = this.$viewModel.guidelineBounds;
                final WindowManager windowManager = this.$windowManager;
                final ConstraintSet constraintSet = this.$mediumConstraintSet;
                final Guideline guideline = this.$leftGuideline;
                final ConstraintSet constraintSet2 = this.$smallConstraintSet;
                final Guideline guideline2 = this.$rightGuideline;
                final Guideline guideline3 = this.$topGuideline;
                final Guideline guideline4 = this.$midGuideline;
                final View view = this.$view;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder.bind.2.1.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Rect rect = (Rect) obj2;
                        int i2 = windowManager.getMaximumWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars()).bottom;
                        ConstraintSet constraintSet3 = constraintSet;
                        constraintSet3.setGuidelineEnd(R.id.bottomGuideline, i2);
                        int i3 = rect.left;
                        Guideline guideline5 = guideline;
                        ConstraintSet constraintSet4 = constraintSet2;
                        if (i3 >= 0) {
                            constraintSet3.setGuidelineBegin(guideline5.getId(), rect.left);
                            constraintSet4.setGuidelineBegin(guideline5.getId(), rect.left);
                        } else if (i3 < 0) {
                            constraintSet3.setGuidelineEnd(guideline5.getId(), Math.abs(rect.left));
                            constraintSet4.setGuidelineEnd(guideline5.getId(), Math.abs(rect.left));
                        }
                        int i4 = rect.right;
                        Guideline guideline6 = guideline2;
                        if (i4 >= 0) {
                            constraintSet3.setGuidelineEnd(guideline6.getId(), rect.right);
                            constraintSet4.setGuidelineEnd(guideline6.getId(), rect.right);
                        } else if (i4 < 0) {
                            constraintSet3.setGuidelineBegin(guideline6.getId(), Math.abs(rect.right));
                            constraintSet4.setGuidelineBegin(guideline6.getId(), Math.abs(rect.right));
                        }
                        int i5 = rect.top;
                        Guideline guideline7 = guideline3;
                        if (i5 >= 0) {
                            constraintSet3.setGuidelineBegin(guideline7.getId(), rect.top);
                            constraintSet4.setGuidelineBegin(guideline7.getId(), rect.top);
                        } else if (i5 < 0) {
                            constraintSet3.setGuidelineEnd(guideline7.getId(), Math.abs(rect.top));
                            constraintSet4.setGuidelineEnd(guideline7.getId(), Math.abs(rect.top));
                        }
                        Guideline guideline8 = guideline4;
                        if (guideline8 != null) {
                            int i6 = rect.left;
                            int abs = i6 >= 0 ? Math.abs(i6) : ((ConstraintLayout) view).getWidth() - Math.abs(rect.left);
                            int i7 = rect.right;
                            constraintSet3.setGuidelineBegin(guideline8.getId(), (abs + (i7 >= 0 ? ((ConstraintLayout) view).getWidth() - Math.abs(rect.right) : Math.abs(i7))) / 2);
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
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $iconHolderView;
        final /* synthetic */ ConstraintSet $largeConstraintSet;
        final /* synthetic */ ConstraintSet $mediumConstraintSet;
        final /* synthetic */ ConstraintSet $smallConstraintSet;
        final /* synthetic */ PromptViewModel $viewModel;
        final /* synthetic */ List<View> $viewsToHideWhenSmall;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$4$2, reason: invalid class name */
        final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

            public AnonymousClass2() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return new Pair(bool, (PromptSize) obj2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass4(PromptViewModel promptViewModel, List<? extends View> list, ConstraintSet constraintSet, View view, ConstraintSet constraintSet2, ConstraintSet constraintSet3, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$viewsToHideWhenSmall = list;
            this.$largeConstraintSet = constraintSet;
            this.$iconHolderView = view;
            this.$smallConstraintSet = constraintSet2;
            this.$mediumConstraintSet = constraintSet3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$viewModel, this.$viewsToHideWhenSmall, this.$largeConstraintSet, this.$iconHolderView, this.$smallConstraintSet, this.$mediumConstraintSet, continuation);
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
                PromptViewModel promptViewModel = this.$viewModel;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.hideSensorIcon, promptViewModel.size, AnonymousClass2.INSTANCE);
                final List<View> list = this.$viewsToHideWhenSmall;
                final ConstraintSet constraintSet = this.$largeConstraintSet;
                final View view = this.$iconHolderView;
                final ConstraintSet constraintSet2 = this.$smallConstraintSet;
                final ConstraintSet constraintSet3 = this.$mediumConstraintSet;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder.bind.2.1.4.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                        PromptSize promptSize = (PromptSize) pair.component2();
                        List<View> list2 = list;
                        View view2 = view;
                        BiometricViewSizeBinder biometricViewSizeBinder = BiometricViewSizeBinder.INSTANCE;
                        for (View view3 : list2) {
                            boolean isSmall = PromptSizeKt.isSmall(promptSize);
                            int i2 = 0;
                            boolean z = (view3 instanceof TextView) && StringsKt__StringsJVMKt.isBlank(((TextView) view3).getText());
                            boolean z2 = (view3 instanceof ImageView) && ((ImageView) view3).getDrawable() == null;
                            if (isSmall || z || z2) {
                                i2 = 8;
                            }
                            view3.setVisibility(i2);
                        }
                        int id = view2.getId();
                        ConstraintSet constraintSet4 = constraintSet;
                        constraintSet4.setVisibility(id, 8);
                        constraintSet4.setVisibility(R.id.biometric_icon_overlay, 8);
                        constraintSet4.setVisibility(R.id.indicator, 8);
                        constraintSet4.setVisibility(R.id.scrollView, 8);
                        if (booleanValue) {
                            int id2 = view2.getId();
                            ConstraintSet constraintSet5 = constraintSet2;
                            constraintSet5.setVisibility(id2, 8);
                            constraintSet5.setVisibility(R.id.biometric_icon_overlay, 8);
                            constraintSet5.setVisibility(R.id.indicator, 8);
                            int id3 = view2.getId();
                            ConstraintSet constraintSet6 = constraintSet3;
                            constraintSet6.setVisibility(id3, 8);
                            constraintSet6.setVisibility(R.id.biometric_icon_overlay, 8);
                            constraintSet6.setVisibility(R.id.indicator, 8);
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
    /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        final /* synthetic */ AccessibilityManager $accessibilityManager;
        final /* synthetic */ Ref$ObjectRef<PromptPosition> $currentPosition;
        final /* synthetic */ Ref$ObjectRef<PromptSize> $currentSize;
        final /* synthetic */ ConstraintSet $flipConstraintSet;
        final /* synthetic */ ConstraintSet $largeConstraintSet;
        final /* synthetic */ ConstraintSet $mediumConstraintSet;
        final /* synthetic */ View $panelView;
        final /* synthetic */ float $pxToDp;
        final /* synthetic */ ConstraintSet $smallConstraintSet;
        final /* synthetic */ View $view;
        final /* synthetic */ PromptViewModel $viewModel;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder$bind$2$1$5$2, reason: invalid class name */
        final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

            public AnonymousClass2() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return new Pair((PromptPosition) obj, (PromptSize) obj2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(PromptViewModel promptViewModel, ConstraintSet constraintSet, ConstraintSet constraintSet2, ConstraintSet constraintSet3, float f, View view, Ref$ObjectRef<PromptSize> ref$ObjectRef, ConstraintSet constraintSet4, Ref$ObjectRef<PromptPosition> ref$ObjectRef2, View view2, AccessibilityManager accessibilityManager, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptViewModel;
            this.$flipConstraintSet = constraintSet;
            this.$smallConstraintSet = constraintSet2;
            this.$mediumConstraintSet = constraintSet3;
            this.$pxToDp = f;
            this.$view = view;
            this.$currentSize = ref$ObjectRef;
            this.$largeConstraintSet = constraintSet4;
            this.$currentPosition = ref$ObjectRef2;
            this.$panelView = view2;
            this.$accessibilityManager = accessibilityManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass5(this.$viewModel, this.$flipConstraintSet, this.$smallConstraintSet, this.$mediumConstraintSet, this.$pxToDp, this.$view, this.$currentSize, this.$largeConstraintSet, this.$currentPosition, this.$panelView, this.$accessibilityManager, continuation);
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
                PromptViewModel promptViewModel = this.$viewModel;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptViewModel.position, promptViewModel.size, AnonymousClass2.INSTANCE);
                final ConstraintSet constraintSet = this.$flipConstraintSet;
                final ConstraintSet constraintSet2 = this.$smallConstraintSet;
                final ConstraintSet constraintSet3 = this.$mediumConstraintSet;
                final float f = this.$pxToDp;
                final View view = this.$view;
                final Ref$ObjectRef<PromptSize> ref$ObjectRef = this.$currentSize;
                final ConstraintSet constraintSet4 = this.$largeConstraintSet;
                final Ref$ObjectRef<PromptPosition> ref$ObjectRef2 = this.$currentPosition;
                final View view2 = this.$panelView;
                final AccessibilityManager accessibilityManager = this.$accessibilityManager;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.BiometricViewSizeBinder.bind.2.1.5.3
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r10v2, types: [T, com.android.systemui.biometrics.ui.viewmodel.PromptPosition] */
                    /* JADX WARN: Type inference failed for: r9v3, types: [T, com.android.systemui.biometrics.ui.viewmodel.PromptSize] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        ?? r10 = (PromptPosition) pair.component1();
                        ?? r9 = (PromptSize) pair.component2();
                        boolean z = r10 != 0 && r10 == PromptPosition.Left;
                        ConstraintSet constraintSet5 = constraintSet2;
                        ConstraintSet constraintSet6 = ConstraintSet.this;
                        ConstraintSet constraintSet7 = constraintSet3;
                        if (z) {
                            if (PromptSizeKt.isSmall(r9)) {
                                constraintSet6.clone(constraintSet5);
                            } else {
                                constraintSet6.clone(constraintSet7);
                            }
                            constraintSet6.connect(R.id.scrollView, 1, R.id.midGuideline, 1);
                            constraintSet6.connect(R.id.scrollView, 2, R.id.rightGuideline, 2);
                        } else if (r10 != 0 && r10 == PromptPosition.Top) {
                            constraintSet7.connect(R.id.scrollView, 3, R.id.indicator, 4);
                            constraintSet7.connect(R.id.scrollView, 4, R.id.button_bar, 3);
                            constraintSet7.connect(R.id.panel, 3, R.id.biometric_icon, 3);
                            constraintSet7.setMargin(R.id.panel, 3, (int) ((-24) * f));
                            constraintSet7.setVerticalBias(R.id.scrollView, 0.0f);
                        }
                        boolean isSmall = PromptSizeKt.isSmall(r9);
                        Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                        if (isSmall) {
                            if (r10 == 0 || r10 != PromptPosition.Left) {
                                constraintSet5.applyTo((ConstraintLayout) view);
                            } else {
                                constraintSet6.applyTo((ConstraintLayout) view);
                            }
                        } else if (PromptSizeKt.isMedium(r9) && PromptSizeKt.isSmall((PromptSize) ref$ObjectRef3.element)) {
                            AutoTransition autoTransition = new AutoTransition();
                            autoTransition.setDuration(150L);
                            TransitionManager.beginDelayedTransition((ViewGroup) view, autoTransition);
                            if (r10 == 0 || r10 != PromptPosition.Left) {
                                constraintSet7.applyTo((ConstraintLayout) view);
                            } else {
                                constraintSet6.applyTo((ConstraintLayout) view);
                            }
                        } else if (PromptSizeKt.isMedium(r9)) {
                            if (r10 == 0 || r10 != PromptPosition.Left) {
                                constraintSet7.applyTo((ConstraintLayout) view);
                            } else {
                                constraintSet6.applyTo((ConstraintLayout) view);
                            }
                        } else if (r9 != 0 && r9 == PromptSize.LARGE && PromptSizeKt.isMedium((PromptSize) ref$ObjectRef3.element)) {
                            AutoTransition autoTransition2 = new AutoTransition();
                            autoTransition2.setDuration(450L);
                            TransitionManager.beginDelayedTransition((ViewGroup) view, autoTransition2);
                            constraintSet4.applyTo((ConstraintLayout) view);
                        }
                        ref$ObjectRef3.element = r9;
                        ref$ObjectRef2.element = r10;
                        BiometricViewSizeBinder.access$bind$notifyAccessibilityChanged(accessibilityManager, view);
                        view2.invalidateOutline();
                        view.invalidate();
                        view.requestLayout();
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
    /* JADX WARN: Multi-variable type inference failed */
    public BiometricViewSizeBinder$bind$2$1(PromptViewModel promptViewModel, View view, ConstraintSet constraintSet, ConstraintSet constraintSet2, WindowManager windowManager, Guideline guideline, Guideline guideline2, Guideline guideline3, Guideline guideline4, View view2, List<? extends View> list, ConstraintSet constraintSet3, ConstraintSet constraintSet4, float f, Ref$ObjectRef<PromptSize> ref$ObjectRef, Ref$ObjectRef<PromptPosition> ref$ObjectRef2, View view3, AccessibilityManager accessibilityManager, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = promptViewModel;
        this.$iconHolderView = view;
        this.$mediumConstraintSet = constraintSet;
        this.$smallConstraintSet = constraintSet2;
        this.$windowManager = windowManager;
        this.$leftGuideline = guideline;
        this.$rightGuideline = guideline2;
        this.$topGuideline = guideline3;
        this.$midGuideline = guideline4;
        this.$view = view2;
        this.$viewsToHideWhenSmall = list;
        this.$largeConstraintSet = constraintSet3;
        this.$flipConstraintSet = constraintSet4;
        this.$pxToDp = f;
        this.$currentSize = ref$ObjectRef;
        this.$currentPosition = ref$ObjectRef2;
        this.$panelView = view3;
        this.$accessibilityManager = accessibilityManager;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricViewSizeBinder$bind$2$1 biometricViewSizeBinder$bind$2$1 = new BiometricViewSizeBinder$bind$2$1(this.$viewModel, this.$iconHolderView, this.$mediumConstraintSet, this.$smallConstraintSet, this.$windowManager, this.$leftGuideline, this.$rightGuideline, this.$topGuideline, this.$midGuideline, this.$view, this.$viewsToHideWhenSmall, this.$largeConstraintSet, this.$flipConstraintSet, this.$pxToDp, this.$currentSize, this.$currentPosition, this.$panelView, this.$accessibilityManager, (Continuation) obj3);
        biometricViewSizeBinder$bind$2$1.L$0 = (LifecycleOwner) obj;
        return biometricViewSizeBinder$bind$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(this.$viewModel, this.$iconHolderView, this.$mediumConstraintSet, this.$smallConstraintSet, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(this.$viewModel, this.$iconHolderView, this.$mediumConstraintSet, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass3(this.$viewModel, this.$windowManager, this.$mediumConstraintSet, this.$leftGuideline, this.$smallConstraintSet, this.$rightGuideline, this.$topGuideline, this.$midGuideline, this.$view, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass4(this.$viewModel, this.$viewsToHideWhenSmall, this.$largeConstraintSet, this.$iconHolderView, this.$smallConstraintSet, this.$mediumConstraintSet, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass5(this.$viewModel, this.$flipConstraintSet, this.$smallConstraintSet, this.$mediumConstraintSet, this.$pxToDp, this.$view, this.$currentSize, this.$largeConstraintSet, this.$currentPosition, this.$panelView, this.$accessibilityManager, null), 3);
        return Unit.INSTANCE;
    }
}
