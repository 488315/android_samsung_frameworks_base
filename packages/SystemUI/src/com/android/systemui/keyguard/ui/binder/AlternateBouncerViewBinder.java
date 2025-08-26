package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.AuthKeyguardMessageArea;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.view.AlternateBouncerWindowViewLayoutParams;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerWindowViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.scrim.ScrimView;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class AlternateBouncerViewBinder implements CoreStartable {
    public final Lazy alternateBouncerDependencies;
    public ConstraintLayout alternateBouncerView;
    public final Lazy alternateBouncerWindowViewModel;
    public final CoroutineScope applicationScope;
    public final Lazy layoutInflater;
    public final AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1 onAttachAddBackGestureHandler = new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1
        public final AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1 onBackInvokedCallback;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1] */
        {
            this.onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    AlternateBouncerViewModel alternateBouncerViewModel = ((AlternateBouncerDependencies) this.alternateBouncerDependencies.get()).viewModel;
                    alternateBouncerViewModel.statusBarKeyguardViewManager.hideAlternateBouncer(false);
                    alternateBouncerViewModel.dismissCallbackRegistry.notifyDismissCancelled();
                    alternateBouncerViewModel.primaryBouncerInteractor.setDismissAction(null, null);
                }
            };
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
            if (onBackInvokedDispatcherFindOnBackInvokedDispatcher != null) {
                onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, this.onBackInvokedCallback);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
            if (onBackInvokedDispatcherFindOnBackInvokedDispatcher != null) {
                onBackInvokedDispatcherFindOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.onBackInvokedCallback);
            }
        }
    };
    public final Lazy windowManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AlternateBouncerViewBinder.this.new AnonymousClass1(continuation);
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
                ChannelFlowTransformLatest channelFlowTransformLatest = ((AlternateBouncerWindowViewModel) AlternateBouncerViewBinder.this.alternateBouncerWindowViewModel.get()).alternateBouncerWindowRequired;
                final AlternateBouncerViewBinder alternateBouncerViewBinder = AlternateBouncerViewBinder.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("alternateBouncerWindowRequired=", "AlternateBouncerViewBinder", zBooleanValue);
                        final AlternateBouncerViewBinder alternateBouncerViewBinder2 = alternateBouncerViewBinder;
                        if (zBooleanValue) {
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
                                authKeyguardMessageArea.update$1$1();
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
                                            constraintLayout5.removeOnAttachStateChangeListener(this);
                                            constraintLayout5.removeOnAttachStateChangeListener(alternateBouncerViewBinder2.onAttachAddBackGestureHandler);
                                            Log.d("AlternateBouncerViewBinder", "Removing alternate bouncer view on attached");
                                            ((WindowManager) alternateBouncerViewBinder2.windowManager.get()).removeView(constraintLayout5);
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1] */
    public AlternateBouncerViewBinder(CoroutineScope coroutineScope, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4) {
        this.applicationScope = coroutineScope;
        this.alternateBouncerWindowViewModel = lazy;
        this.alternateBouncerDependencies = lazy2;
        this.windowManager = lazy3;
        this.layoutInflater = lazy4;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 6);
    }
}
