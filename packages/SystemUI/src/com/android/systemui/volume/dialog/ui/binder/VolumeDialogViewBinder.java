package com.android.systemui.volume.dialog.ui.binder;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.compose.ui.util.MathHelpersKt;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.ViewExtKt$onApplyWindowInsets$1;
import com.android.systemui.util.kotlin.DisposableHandleExtKt;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.ui.utils.JankListenerFactory;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import com.android.systemui.volume.dialog.utils.VolumeTracer;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogViewBinder {
    public final float halfOpenedOffsetPx;
    public final JankListenerFactory jankListenerFactory;
    public final VolumeTracer tracer;
    public final List viewBinders;
    public final VolumeDialogViewModel viewModel;

    public final class Accessibility extends View.AccessibilityDelegate {
        public final VolumeDialogViewModel viewModel;

        public Accessibility(VolumeDialogViewModel volumeDialogViewModel) {
            this.viewModel = volumeDialogViewModel;
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            String str = (String) this.viewModel.dialogTitle.$$delegate_0.getValue();
            if (str.length() <= 0) {
                return true;
            }
            accessibilityEvent.getText().add(str);
            return true;
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            this.viewModel.dialogVisibilityInteractor.resetDismissTimeout();
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Dialog $dialog;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Dialog dialog, Continuation continuation) {
            super(2, continuation);
            this.$dialog = dialog;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$dialog, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            String str = (String) this.L$0;
            Window window = this.$dialog.getWindow();
            if (window != null) {
                window.setTitle(str);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        final /* synthetic */ ViewGroup $root;
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(ViewGroup viewGroup, Continuation continuation) {
            super(3, continuation);
            this.$root = viewGroup;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass4 anonymousClass4 = VolumeDialogViewBinder.this.new AnonymousClass4(this.$root, (Continuation) obj3);
            anonymousClass4.L$0 = (Boolean) obj;
            anonymousClass4.Z$0 = zBooleanValue;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object result;
            boolean z;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Boolean bool = (Boolean) this.L$0;
                boolean z2 = this.Z$0;
                VolumeDialogViewBinder volumeDialogViewBinder = VolumeDialogViewBinder.this;
                ViewGroup viewGroup = this.$root;
                float f = z2 ? volumeDialogViewBinder.halfOpenedOffsetPx : 0.0f;
                boolean z3 = bool != null;
                this.Z$0 = z2;
                this.label = 1;
                volumeDialogViewBinder.getClass();
                if (z3) {
                    final ViewPropertyAnimator viewPropertyAnimatorTranslationY = viewGroup.animate().setDuration(150L).translationY(f);
                    final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                    cancellableContinuationImpl.initCancellability();
                    viewPropertyAnimatorTranslationY.start();
                    final Animator.AnimatorListener animatorListener = null;
                    viewPropertyAnimatorTranslationY.setListener(new Animator.AnimatorListener() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$2$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            SuspendAnimatorsKt.access$resumeIfCan(cancellableContinuationImpl, Unit.INSTANCE);
                            Animator.AnimatorListener animatorListener2 = animatorListener;
                            if (animatorListener2 != null) {
                                animatorListener2.onAnimationCancel(animator);
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SuspendAnimatorsKt.access$resumeIfCan(cancellableContinuationImpl, Unit.INSTANCE);
                            Animator.AnimatorListener animatorListener2 = animatorListener;
                            if (animatorListener2 != null) {
                                animatorListener2.onAnimationEnd(animator);
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                            Animator.AnimatorListener animatorListener2 = animatorListener;
                            if (animatorListener2 != null) {
                                animatorListener2.onAnimationRepeat(animator);
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            Animator.AnimatorListener animatorListener2 = animatorListener;
                            if (animatorListener2 != null) {
                                animatorListener2.onAnimationStart(animator);
                            }
                        }
                    });
                    cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$2$2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            viewPropertyAnimatorTranslationY.cancel();
                            return Unit.INSTANCE;
                        }
                    });
                    result = cancellableContinuationImpl.getResult();
                    if (result != coroutineSingletons) {
                        result = Unit.INSTANCE;
                    }
                    if (result != coroutineSingletons) {
                        result = Unit.INSTANCE;
                    }
                } else {
                    viewGroup.setTranslationY(f);
                    result = Unit.INSTANCE;
                }
                if (result == coroutineSingletons) {
                    return coroutineSingletons;
                }
                z = z2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z = this.Z$0;
                ResultKt.throwOnFailure(obj);
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        final /* synthetic */ ViewGroup $root;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(ViewGroup viewGroup, Continuation continuation) {
            super(2, continuation);
            this.$root = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogViewBinder.this.new AnonymousClass5(this.$root, continuation);
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
                final VolumeDialogViewBinder volumeDialogViewBinder = VolumeDialogViewBinder.this;
                final ViewTreeObserver viewTreeObserver = this.$root.getViewTreeObserver();
                this.label = 1;
                volumeDialogViewBinder.getClass();
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                final ViewTreeObserver.OnComputeInternalInsetsListener onComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$listenToComputeInternalInsets$2$listener$1
                    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                        VolumeDialogViewModel volumeDialogViewModel = volumeDialogViewBinder.viewModel;
                        internalInsetsInfo.getClass();
                        for (View view : volumeDialogViewModel.touchableBoundsViews) {
                            Rect rect = new Rect();
                            internalInsetsInfo.setTouchableInsets(3);
                            view.getBoundsInWindow(rect, false);
                            internalInsetsInfo.touchableRegion.op(rect, Region.Op.UNION);
                        }
                    }
                };
                viewTreeObserver.addOnComputeInternalInsetsListener(onComputeInternalInsetsListener);
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$listenToComputeInternalInsets$2$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        viewTreeObserver.removeOnComputeInternalInsetsListener(onComputeInternalInsetsListener);
                        return Unit.INSTANCE;
                    }
                });
                Object result = cancellableContinuationImpl.getResult();
                if (result != coroutineSingletons) {
                    result = Unit.INSTANCE;
                }
                if (result == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        final /* synthetic */ MutableStateFlow $insets;
        final /* synthetic */ ViewGroup $root;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(ViewGroup viewGroup, MutableStateFlow mutableStateFlow, Continuation continuation) {
            super(2, continuation);
            this.$root = viewGroup;
            this.$insets = mutableStateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass6(this.$root, this.$insets, continuation);
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
                ViewGroup viewGroup = this.$root;
                final MutableStateFlow mutableStateFlow = this.$insets;
                viewGroup.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder.bind.6.1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        Insets insets = windowInsets.getInsets(WindowInsets.Type.displayCutout());
                        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                        mutableStateFlow.setValue(windowInsets);
                        return WindowInsets.CONSUMED;
                    }
                });
                ViewExtKt$onApplyWindowInsets$1 viewExtKt$onApplyWindowInsets$1 = new ViewExtKt$onApplyWindowInsets$1(viewGroup);
                this.label = 1;
                if (DisposableHandleExtKt.awaitCancellationThenDispose(viewExtKt$onApplyWindowInsets$1, this) == coroutineSingletons) {
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

    public VolumeDialogViewBinder(Context context, VolumeDialogViewModel volumeDialogViewModel, JankListenerFactory jankListenerFactory, VolumeTracer volumeTracer, List<ViewBinder> list) {
        this.viewModel = volumeDialogViewModel;
        this.jankListenerFactory = jankListenerFactory;
        this.tracer = volumeTracer;
        this.viewBinders = list;
        this.halfOpenedOffsetPx = context.getResources().getDimensionPixelSize(R.dimen.volume_dialog_half_opened_offset);
    }

    public static void applyAnimationProgress(float f, View view) {
        Float fValueOf;
        view.setAlpha((float) Math.ceil(f));
        if (view.getDisplay().getRotation() == 0) {
            fValueOf = Float.valueOf((view.getWidth() * (view.isLayoutRtl() ? -1 : 1)) / 2.0f);
        } else {
            fValueOf = null;
        }
        if (fValueOf != null) {
            view.setTranslationX(MathHelpersKt.lerp(fValueOf.floatValue(), 0.0f, f));
        }
    }

    public final void bind(CoroutineScope coroutineScope, Dialog dialog) {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new WindowInsets.Builder().build());
        final ViewGroup viewGroup = (ViewGroup) dialog.requireViewById(R.id.volume_dialog);
        VolumeDialogViewModel volumeDialogViewModel = this.viewModel;
        viewGroup.setAccessibilityDelegate(new Accessibility(volumeDialogViewModel));
        viewGroup.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder.bind.1
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                Object value;
                VolumeDialogViewModel volumeDialogViewModel2 = VolumeDialogViewBinder.this.viewModel;
                boolean z = motionEvent.getActionMasked() == 9 || motionEvent.getActionMasked() == 7;
                StateFlowImpl stateFlowImpl = volumeDialogViewModel2.volumeDialogStateInteractor.volumeDialogStateRepository.mutableState;
                do {
                    value = stateFlowImpl.getValue();
                } while (!stateFlowImpl.compareAndSet(value, VolumeDialogStateModel.copy$default((VolumeDialogStateModel) value, false, null, null, z, null, 0, 0, 0, null, null, 0, false, false, false, false, 32759)));
                volumeDialogViewModel2.dialogVisibilityInteractor.resetDismissTimeout();
                return true;
            }
        });
        ReadonlyStateFlow readonlyStateFlow = volumeDialogViewModel.dialogVisibilityModel;
        applyAnimationProgress(0.0f, viewGroup);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(0.0f));
        springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(700.0f, 0.9f);
        springAnimation.setMinimumVisibleChange(0.01f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$animateVisibility$animation$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                View view = viewGroup;
                this.this$0.getClass();
                VolumeDialogViewBinder.applyAnimationProgress(f, view);
            }
        });
        CoroutineTracingKt.launchInTraced(FlowKt.mapLatest(readonlyStateFlow, new VolumeDialogViewBinder$animateVisibility$1(this, new Ref$ObjectRef(), springAnimation, viewGroup, dialog, null)), coroutineScope);
        final ReadonlyStateFlow readonlyStateFlow2 = volumeDialogViewModel.dialogTitle;
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1

            /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1$2$1, reason: invalid class name */
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
                        if (((String) obj).length() > 0) {
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass3(dialog, null)), coroutineScope);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, volumeDialogViewModel.isHalfOpened, new AnonymousClass4(viewGroup, null)), coroutineScope);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(viewGroup, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(viewGroup, stateFlowImplMutableStateFlow, null), 6);
        Iterator it = this.viewBinders.iterator();
        while (it.hasNext()) {
            ((ViewBinder) it.next()).bind(coroutineScope, viewGroup);
        }
    }
}
