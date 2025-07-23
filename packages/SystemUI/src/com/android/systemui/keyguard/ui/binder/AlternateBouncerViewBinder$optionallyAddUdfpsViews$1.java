package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import com.android.systemui.log.TouchHandlingViewLogger;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AlternateBouncerViewBinder$optionallyAddUdfpsViews$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ TouchHandlingViewLogger $logger;
    final /* synthetic */ Lazy $udfpsA11yOverlayViewModel;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel;
    final /* synthetic */ ConstraintLayout $view;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ TouchHandlingViewLogger $logger;
        final /* synthetic */ Lazy $udfpsA11yOverlayViewModel;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel;
        final /* synthetic */ ConstraintLayout $view;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01381 extends SuspendLambda implements Function2 {
            final /* synthetic */ TouchHandlingViewLogger $logger;
            final /* synthetic */ Lazy $udfpsA11yOverlayViewModel;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel;
            final /* synthetic */ ConstraintLayout $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01381(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy, TouchHandlingViewLogger touchHandlingViewLogger, Continuation continuation) {
                super(2, continuation);
                this.$udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
                this.$view = constraintLayout;
                this.$udfpsA11yOverlayViewModel = lazy;
                this.$logger = touchHandlingViewLogger;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01381(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, this.$logger, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01381) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel = this.$udfpsIconViewModel;
                    ChannelFlowTransformLatest channelFlowTransformLatest = alternateBouncerUdfpsIconViewModel.iconLocation;
                    final ConstraintLayout constraintLayout = this.$view;
                    final Lazy lazy = this.$udfpsA11yOverlayViewModel;
                    final TouchHandlingViewLogger touchHandlingViewLogger = this.$logger;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.optionallyAddUdfpsViews.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            AlternateBouncerUdfpsIconViewModel.IconLocation iconLocation = (AlternateBouncerUdfpsIconViewModel.IconLocation) obj2;
                            ConstraintLayout constraintLayout2 = ConstraintLayout.this;
                            if (constraintLayout2.getViewById(R.id.alternate_bouncer_udfps_accessibility_overlay) == null) {
                                UdfpsAccessibilityOverlay udfpsAccessibilityOverlay = new UdfpsAccessibilityOverlay(constraintLayout2.getContext());
                                udfpsAccessibilityOverlay.setId(R.id.alternate_bouncer_udfps_accessibility_overlay);
                                constraintLayout2.addView(udfpsAccessibilityOverlay);
                                UdfpsAccessibilityOverlayBinder.bind(udfpsAccessibilityOverlay, (UdfpsAccessibilityOverlayViewModel) lazy.get());
                            }
                            if (constraintLayout2.getViewById(R.id.alternate_bouncer_udfps_icon_view) == null) {
                                DeviceEntryIconView deviceEntryIconView = new DeviceEntryIconView(constraintLayout2.getContext(), null, 0, touchHandlingViewLogger, 4, null);
                                deviceEntryIconView.setId(R.id.alternate_bouncer_udfps_icon_view);
                                deviceEntryIconView.setContentDescription(deviceEntryIconView.getContext().getResources().getString(R.string.accessibility_fingerprint_label));
                                constraintLayout2.addView(deviceEntryIconView);
                                AlternateBouncerUdfpsViewBinder.bind(deviceEntryIconView, alternateBouncerUdfpsIconViewModel);
                            }
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(constraintLayout2);
                            constraintSet.constrainWidth(R.id.alternate_bouncer_udfps_icon_view, iconLocation.width);
                            constraintSet.constrainHeight(R.id.alternate_bouncer_udfps_icon_view, iconLocation.height);
                            constraintSet.connect(R.id.alternate_bouncer_udfps_icon_view, 3, 0, 3, iconLocation.top);
                            constraintSet.connect(R.id.alternate_bouncer_udfps_icon_view, 6, 0, 6, iconLocation.left);
                            constraintSet.constrainWidth(R.id.alternate_bouncer_udfps_accessibility_overlay, -1);
                            constraintSet.constrainHeight(R.id.alternate_bouncer_udfps_accessibility_overlay, -1);
                            constraintSet.applyTo(constraintLayout2);
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
        public AnonymousClass1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy, TouchHandlingViewLogger touchHandlingViewLogger, Continuation continuation) {
            super(2, continuation);
            this.$udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
            this.$view = constraintLayout;
            this.$udfpsA11yOverlayViewModel = lazy;
            this.$logger = touchHandlingViewLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, this.$logger, continuation);
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
            CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C01381(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, this.$logger, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerViewBinder$optionallyAddUdfpsViews$1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy, TouchHandlingViewLogger touchHandlingViewLogger, Continuation continuation) {
        super(3, continuation);
        this.$udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
        this.$view = constraintLayout;
        this.$udfpsA11yOverlayViewModel = lazy;
        this.$logger = touchHandlingViewLogger;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerViewBinder$optionallyAddUdfpsViews$1 alternateBouncerViewBinder$optionallyAddUdfpsViews$1 = new AlternateBouncerViewBinder$optionallyAddUdfpsViews$1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, this.$logger, (Continuation) obj3);
        alternateBouncerViewBinder$optionallyAddUdfpsViews$1.L$0 = (LifecycleOwner) obj;
        return alternateBouncerViewBinder$optionallyAddUdfpsViews$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, this.$logger, null);
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
