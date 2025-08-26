package com.android.systemui.deviceentry.ui.binder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class UdfpsAccessibilityOverlayBinder {

    /* renamed from: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ UdfpsAccessibilityOverlay $view;
        final /* synthetic */ UdfpsAccessibilityOverlayViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ UdfpsAccessibilityOverlay $view;
            final /* synthetic */ UdfpsAccessibilityOverlayViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel, UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = udfpsAccessibilityOverlayViewModel;
                this.$view = udfpsAccessibilityOverlay;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.visible;
                    final UdfpsAccessibilityOverlay udfpsAccessibilityOverlay = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder.bind.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            udfpsAccessibilityOverlay.setVisibility(!((Boolean) obj2).booleanValue() ? 4 : 0);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass2(UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel, UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = udfpsAccessibilityOverlayViewModel;
            this.$view = udfpsAccessibilityOverlay;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$view, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, null);
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

    static {
        new UdfpsAccessibilityOverlayBinder();
    }

    private UdfpsAccessibilityOverlayBinder() {
    }

    public static final void bind(UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, final UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel) {
        udfpsAccessibilityOverlay.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder.bind.1
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                View viewFindViewById;
                UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel2 = udfpsAccessibilityOverlayViewModel;
                view.getClass();
                motionEvent.getClass();
                udfpsAccessibilityOverlayViewModel2.getClass();
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) udfpsAccessibilityOverlayViewModel2.udfpsOverlayParams.$$delegate_0.getValue();
                int pointerId = motionEvent.getPointerId(0);
                UdfpsUtils udfpsUtils = udfpsAccessibilityOverlayViewModel2.udfpsUtils;
                udfpsUtils.getClass();
                Point touchInNativeCoordinates = UdfpsUtils.getTouchInNativeCoordinates(pointerId, motionEvent, udfpsOverlayParams, false);
                if (UdfpsUtils.isWithinSensorArea(motionEvent.getPointerId(0), motionEvent, udfpsOverlayParams, false)) {
                    View rootView = view.getRootView();
                    if (rootView != null && (viewFindViewById = rootView.findViewById(R.id.keyguard_bottom_shortcut_area)) != null) {
                        int i = udfpsOverlayParams.rotation;
                        final TextView textView = (TextView) viewFindViewById.findViewById((i == 1 || i == 3) ? R.id.keyguard_indication_text : R.id.keyguard_upper_fingerprint_indication);
                        if (textView != null) {
                            textView.postDelayed(new Runnable() { // from class: com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel$onHoverEvent$1$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TextView textView2 = textView;
                                    textView2.announceForAccessibility(textView2.getText());
                                }
                            }, 500L);
                        }
                    }
                } else {
                    Context context = view.getContext();
                    int i2 = touchInNativeCoordinates.x;
                    int i3 = touchInNativeCoordinates.y;
                    udfpsUtils.getClass();
                    String strOnTouchOutsideOfSensorArea = UdfpsUtils.onTouchOutsideOfSensorArea(true, context, i2, i3, udfpsOverlayParams, false);
                    if (strOnTouchOutsideOfSensorArea != null) {
                        Resources resources = view.getContext().getResources();
                        String[] strArr = {resources.getString(R.string.accessibility_control_move_left), resources.getString(R.string.accessibility_control_move_down), resources.getString(R.string.accessibility_control_move_right), resources.getString(R.string.accessibility_control_move_up)};
                        view.announceForAccessibility(StringsKt__StringsKt.contains(strOnTouchOutsideOfSensorArea, "left", false) ? strArr[0] : StringsKt__StringsKt.contains(strOnTouchOutsideOfSensorArea, "down", false) ? strArr[1] : StringsKt__StringsKt.contains(strOnTouchOutsideOfSensorArea, "right", false) ? strArr[2] : StringsKt__StringsKt.contains(strOnTouchOutsideOfSensorArea, "up", false) ? strArr[3] : "");
                        return false;
                    }
                }
                return false;
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsAccessibilityOverlay, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(udfpsAccessibilityOverlayViewModel, udfpsAccessibilityOverlay, null));
    }
}
