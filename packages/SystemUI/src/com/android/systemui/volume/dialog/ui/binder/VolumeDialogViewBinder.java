package com.android.systemui.volume.dialog.ui.binder;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.compose.ui.util.MathHelpersKt;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.ui.utils.JankListenerFactory;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import com.android.systemui.volume.dialog.utils.VolumeTracer;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogViewBinder {
    public final float halfOpenedOffsetPx;
    public final JankListenerFactory jankListenerFactory;
    public final VolumeTracer tracer;
    public final List viewBinders;
    public final VolumeDialogViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public VolumeDialogViewBinder(Context context, VolumeDialogViewModel volumeDialogViewModel, JankListenerFactory jankListenerFactory, VolumeTracer volumeTracer, List<ViewBinder> list) {
        this.viewModel = volumeDialogViewModel;
        this.jankListenerFactory = jankListenerFactory;
        this.tracer = volumeTracer;
        this.viewBinders = list;
        this.halfOpenedOffsetPx = context.getResources().getDimensionPixelSize(R.dimen.volume_dialog_half_opened_offset);
    }

    public static void applyAnimationProgress(float f, View view) {
        Float f2;
        view.setAlpha((float) Math.ceil(f));
        if (view.getDisplay().getRotation() == 0) {
            f2 = Float.valueOf((view.getWidth() * (view.isLayoutRtl() ? -1 : 1)) / 2.0f);
        } else {
            f2 = null;
        }
        if (f2 != null) {
            view.setTranslationX(MathHelpersKt.lerp(f2.floatValue(), 0.0f, f));
        }
    }

    public final void bind(CoroutineScope coroutineScope, Dialog dialog) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new WindowInsets.Builder().build());
        final ViewGroup viewGroup = (ViewGroup) dialog.requireViewById(R.id.volume_dialog);
        VolumeDialogViewModel volumeDialogViewModel = this.viewModel;
        viewGroup.setAccessibilityDelegate(new Accessibility(volumeDialogViewModel));
        viewGroup.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$1
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
                VolumeDialogViewBinder.this.getClass();
                VolumeDialogViewBinder.applyAnimationProgress(f, view);
            }
        });
        CoroutineTracingKt.launchInTraced(FlowKt.mapLatest(readonlyStateFlow, new VolumeDialogViewBinder$animateVisibility$1(this, new Ref$ObjectRef(), springAnimation, viewGroup, dialog, null)), coroutineScope);
        final ReadonlyStateFlow readonlyStateFlow2 = volumeDialogViewModel.dialogTitle;
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1$2$1 r0 = (com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1$2$1 r0 = new com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.String r6 = (java.lang.String) r6
                        int r6 = r6.length()
                        if (r6 <= 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new VolumeDialogViewBinder$bind$3(dialog, null)), coroutineScope);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, volumeDialogViewModel.isHalfOpened, new VolumeDialogViewBinder$bind$4(this, viewGroup, null)), coroutineScope);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new VolumeDialogViewBinder$bind$5(this, viewGroup, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new VolumeDialogViewBinder$bind$6(viewGroup, MutableStateFlow, null), 6);
        Iterator it = this.viewBinders.iterator();
        while (it.hasNext()) {
            ((ViewBinder) it.next()).bind(coroutineScope, viewGroup);
        }
    }
}
