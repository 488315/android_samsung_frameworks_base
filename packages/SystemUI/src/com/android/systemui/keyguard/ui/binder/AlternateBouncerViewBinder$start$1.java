package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.keyguard.AuthKeyguardMessageArea;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.view.AlternateBouncerWindowViewLayoutParams;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerWindowViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.scrim.ScrimView;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AlternateBouncerViewBinder$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AlternateBouncerViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerViewBinder$start$1(AlternateBouncerViewBinder alternateBouncerViewBinder, Continuation continuation) {
        super(2, continuation);
        this.this$0 = alternateBouncerViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AlternateBouncerViewBinder$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlternateBouncerViewBinder$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelFlowTransformLatest channelFlowTransformLatest = ((AlternateBouncerWindowViewModel) this.this$0.alternateBouncerWindowViewModel.get()).alternateBouncerWindowRequired;
            final AlternateBouncerViewBinder alternateBouncerViewBinder = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("alternateBouncerWindowRequired=", "AlternateBouncerViewBinder", booleanValue);
                    final AlternateBouncerViewBinder alternateBouncerViewBinder2 = AlternateBouncerViewBinder.this;
                    if (booleanValue) {
                        if (alternateBouncerViewBinder2.alternateBouncerView == null) {
                            alternateBouncerViewBinder2.alternateBouncerView = (ConstraintLayout) ((LayoutInflater) alternateBouncerViewBinder2.layoutInflater.get()).inflate(R.layout.alternate_bouncer, (ViewGroup) null, false);
                            Log.d("AlternateBouncerViewBinder", "Adding alternate bouncer view");
                            WindowManager windowManager = (WindowManager) alternateBouncerViewBinder2.windowManager.get();
                            ConstraintLayout constraintLayout = alternateBouncerViewBinder2.alternateBouncerView;
                            AlternateBouncerWindowViewLayoutParams.INSTANCE.getClass();
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2009, UcmAgentService.ERROR_APDU_CREATION, -3);
                            layoutParams.setTitle("AlternateBouncerView");
                            layoutParams.setFitInsetsTypes(0);
                            layoutParams.gravity = 51;
                            layoutParams.layoutInDisplayCutoutMode = 3;
                            layoutParams.privateFlags = 536870976;
                            layoutParams.accessibilityTitle = " ";
                            windowManager.addView(constraintLayout, layoutParams);
                            ConstraintLayout constraintLayout2 = alternateBouncerViewBinder2.alternateBouncerView;
                            constraintLayout2.getClass();
                            constraintLayout2.addOnAttachStateChangeListener(alternateBouncerViewBinder2.onAttachAddBackGestureHandler);
                        }
                        ConstraintLayout constraintLayout3 = alternateBouncerViewBinder2.alternateBouncerView;
                        constraintLayout3.getClass();
                        ((ScrimView) constraintLayout3.requireViewById(R.id.alternate_bouncer_scrim)).setViewAlpha(0.0f);
                        ConstraintLayout constraintLayout4 = alternateBouncerViewBinder2.alternateBouncerView;
                        constraintLayout4.getClass();
                        AlternateBouncerDependencies alternateBouncerDependencies = (AlternateBouncerDependencies) alternateBouncerViewBinder2.alternateBouncerDependencies.get();
                        RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout4, EmptyCoroutineContext.INSTANCE, new AlternateBouncerViewBinder$optionallyAddUdfpsViews$1(alternateBouncerDependencies.udfpsIconViewModel, constraintLayout4, alternateBouncerDependencies.udfpsAccessibilityOverlayViewModel, alternateBouncerDependencies.logger, null));
                        AuthKeyguardMessageArea authKeyguardMessageArea = (AuthKeyguardMessageArea) constraintLayout4.requireViewById(R.id.alternate_bouncer_message_area);
                        int i2 = AlternateBouncerMessageAreaViewBinder.$r8$clinit;
                        if (!authKeyguardMessageArea.mIsVisible) {
                            authKeyguardMessageArea.mIsVisible = true;
                            authKeyguardMessageArea.update$8();
                        }
                        RepeatWhenAttachedKt.repeatWhenAttached(authKeyguardMessageArea, EmptyCoroutineContext.INSTANCE, new AlternateBouncerMessageAreaViewBinder$bind$1(alternateBouncerDependencies.messageAreaViewModel, authKeyguardMessageArea, null));
                        ScrimView scrimView = (ScrimView) constraintLayout4.requireViewById(R.id.alternate_bouncer_scrim);
                        RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout4, EmptyCoroutineContext.INSTANCE, new AlternateBouncerViewBinder$bind$1(alternateBouncerDependencies.viewModel, alternateBouncerDependencies.swipeUpAnywhereGestureHandler, alternateBouncerDependencies.tapGestureDetector, alternateBouncerDependencies, scrimView, null));
                    } else {
                        final ConstraintLayout constraintLayout5 = alternateBouncerViewBinder2.alternateBouncerView;
                        if (constraintLayout5 != null) {
                            alternateBouncerViewBinder2.alternateBouncerView = null;
                            if (constraintLayout5.isAttachedToWindow()) {
                                constraintLayout5.removeOnAttachStateChangeListener(alternateBouncerViewBinder2.onAttachAddBackGestureHandler);
                                Log.d("AlternateBouncerViewBinder", "Removing alternate bouncer view immediately");
                                ((WindowManager) alternateBouncerViewBinder2.windowManager.get()).removeView(constraintLayout5);
                            } else {
                                constraintLayout5.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$removeViewFromWindowManager$1$1
                                    @Override // android.view.View.OnAttachStateChangeListener
                                    public final void onViewAttachedToWindow(View view) {
                                        ConstraintLayout.this.removeOnAttachStateChangeListener(this);
                                        ConstraintLayout.this.removeOnAttachStateChangeListener(alternateBouncerViewBinder2.onAttachAddBackGestureHandler);
                                        Log.d("AlternateBouncerViewBinder", "Removing alternate bouncer view on attached");
                                        ((WindowManager) alternateBouncerViewBinder2.windowManager.get()).removeView(ConstraintLayout.this);
                                    }

                                    @Override // android.view.View.OnAttachStateChangeListener
                                    public final void onViewDetachedFromWindow(View view) {
                                    }
                                });
                            }
                        }
                        ((AlternateBouncerDependencies) alternateBouncerViewBinder2.alternateBouncerDependencies.get()).viewModel.statusBarKeyguardViewManager.hideAlternateBouncer(false);
                    }
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
