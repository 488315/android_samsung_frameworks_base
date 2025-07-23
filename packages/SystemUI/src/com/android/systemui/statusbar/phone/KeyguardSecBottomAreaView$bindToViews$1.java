package com.android.systemui.statusbar.phone;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class KeyguardSecBottomAreaView$bindToViews$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardSecBottomAreaView this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$bindToViews$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ KeyguardSecBottomAreaView this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$bindToViews$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03311 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ KeyguardSecBottomAreaView this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03311(KeyguardSecBottomAreaView keyguardSecBottomAreaView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = keyguardSecBottomAreaView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03311(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03311) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardSecBottomAreaView keyguardSecBottomAreaView = this.this$0;
                    StateFlowImpl stateFlowImpl = keyguardSecBottomAreaView.configurationBasedDimensions;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView.bindToViews.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecBottomAreaView.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaView.ConfigurationBasedDimensions) obj2;
                            KeyguardSecBottomAreaView keyguardSecBottomAreaView2 = KeyguardSecBottomAreaView.this;
                            KeyguardSecAffordanceView leftView = keyguardSecBottomAreaView2.getLeftView();
                            ViewGroup.LayoutParams layoutParams = leftView.getLayoutParams();
                            if (layoutParams == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            layoutParams.width = configurationBasedDimensions.buttonSizePx.getWidth();
                            layoutParams.height = configurationBasedDimensions.buttonSizePx.getHeight();
                            leftView.setLayoutParams(layoutParams);
                            KeyguardSecAffordanceView rightView = keyguardSecBottomAreaView2.getRightView();
                            ViewGroup.LayoutParams layoutParams2 = rightView.getLayoutParams();
                            if (layoutParams2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            layoutParams2.width = configurationBasedDimensions.buttonSizePx.getWidth();
                            layoutParams2.height = configurationBasedDimensions.buttonSizePx.getHeight();
                            rightView.setLayoutParams(layoutParams2);
                            View view = (View) keyguardSecBottomAreaView2.leftShortcutArea$delegate.getValue();
                            ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                            if (layoutParams3 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                            layoutParams4.bottomMargin = configurationBasedDimensions.shortcutBottomMargin;
                            layoutParams4.setMarginStart(configurationBasedDimensions.shortcutSideMargin);
                            view.setLayoutParams(layoutParams4);
                            View view2 = (View) keyguardSecBottomAreaView2.rightShortcutArea$delegate.getValue();
                            ViewGroup.LayoutParams layoutParams5 = view2.getLayoutParams();
                            if (layoutParams5 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
                            layoutParams6.bottomMargin = configurationBasedDimensions.shortcutBottomMargin;
                            layoutParams6.setMarginEnd(configurationBasedDimensions.shortcutSideMargin);
                            view2.setLayoutParams(layoutParams6);
                            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = keyguardSecBottomAreaView2.updateLeftAffordanceIcon;
                            if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
                                keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
                            }
                            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
                            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = keyguardSecBottomAreaView2.updateRightAffordanceIcon;
                            (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
                            ViewGroup viewGroup = (ViewGroup) keyguardSecBottomAreaView2.indicationArea$delegate.getValue();
                            ViewGroup.LayoutParams layoutParams7 = viewGroup.getLayoutParams();
                            if (layoutParams7 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) layoutParams7;
                            layoutParams8.setMarginStart(configurationBasedDimensions.indicationAreaSideMargin);
                            layoutParams8.setMarginEnd(configurationBasedDimensions.indicationAreaSideMargin);
                            layoutParams8.bottomMargin = configurationBasedDimensions.indicationAreaBottomMargin;
                            int marginStart = layoutParams8.getMarginStart();
                            int marginEnd = layoutParams8.getMarginEnd();
                            int i2 = layoutParams8.bottomMargin;
                            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(marginStart, marginEnd, "mIndicationArea margin start : ", ", end : ", ", bottom : ");
                            m.append(i2);
                            Log.d("KeyguardSecBottomAreaView", m.toString());
                            viewGroup.setLayoutParams(layoutParams8);
                            KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) keyguardSecBottomAreaView2.upperFPIndication$delegate.getValue();
                            ViewGroup.LayoutParams layoutParams9 = keyguardIndicationTextView.getLayoutParams();
                            if (layoutParams9 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                            }
                            FrameLayout.LayoutParams layoutParams10 = (FrameLayout.LayoutParams) layoutParams9;
                            layoutParams10.bottomMargin = configurationBasedDimensions.upperFPIndicationBottomMargin;
                            keyguardIndicationTextView.setLayoutParams(layoutParams10);
                            LinearLayout linearLayout = keyguardSecBottomAreaView2.usimTextArea;
                            if (linearLayout != null) {
                                ViewGroup.LayoutParams layoutParams11 = linearLayout.getLayoutParams();
                                if (layoutParams11 == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                                }
                                LinearLayout.LayoutParams layoutParams12 = (LinearLayout.LayoutParams) layoutParams11;
                                layoutParams12.bottomMargin = configurationBasedDimensions.usimTextAreaBottomMargin;
                                linearLayout.setLayoutParams(layoutParams12);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(KeyguardSecBottomAreaView keyguardSecBottomAreaView, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardSecBottomAreaView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
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
            BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C03311(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSecBottomAreaView$bindToViews$1(KeyguardSecBottomAreaView keyguardSecBottomAreaView, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardSecBottomAreaView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardSecBottomAreaView$bindToViews$1 keyguardSecBottomAreaView$bindToViews$1 = new KeyguardSecBottomAreaView$bindToViews$1(this.this$0, (Continuation) obj3);
        keyguardSecBottomAreaView$bindToViews$1.L$0 = (LifecycleOwner) obj;
        return keyguardSecBottomAreaView$bindToViews$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
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
