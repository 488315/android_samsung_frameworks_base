package com.android.systemui.biometrics;

import android.graphics.Rect;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Trace;
import android.util.RotationUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
public final class UdfpsControllerOverlay {
    public final AccessibilityManager accessibilityManager;
    public AnonymousClass1 addViewRunnable;
    public final IUdfpsOverlayControllerCallback controllerCallback;
    public final WindowManager.LayoutParams coreLayoutParams;
    public final UdfpsControllerOverlay$special$$inlined$map$1 currentStateUpdatedToOffAodOrDozing;
    public final Lazy defaultUdfpsTouchOverlayViewModel;
    public final Lazy deviceEntryUdfpsTouchOverlayViewModel;
    public final LayoutInflater inflater;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public StandaloneCoroutine listenForCurrentKeyguardState;
    public final Function2 onTouch;
    public UdfpsOverlayParams overlayParams;
    public UdfpsControllerOverlay$show$2$1 overlayTouchListener;
    public UdfpsTouchOverlay overlayTouchView;
    public final PowerInteractor powerInteractor;
    public final Lazy promptUdfpsTouchOverlayViewModel;
    public final long requestId;
    public final int requestReason;
    public final CoroutineScope scope;
    public Rect sensorBounds;
    public final UdfpsDisplayModeProvider udfpsDisplayModeProvider;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final WindowManager windowManager;

    /* renamed from: com.android.systemui.biometrics.UdfpsControllerOverlay$addViewNowOrLater$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ UdfpsAnimationViewController $animation;
        public final /* synthetic */ View $view;

        public AnonymousClass1(View view, UdfpsAnimationViewController udfpsAnimationViewController) {
            this.$view = view;
            this.$animation = udfpsAnimationViewController;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Trace.setCounter("UdfpsAddView", 1L);
            UdfpsControllerOverlay udfpsControllerOverlay = UdfpsControllerOverlay.this;
            WindowManager windowManager = udfpsControllerOverlay.windowManager;
            View view = this.$view;
            WindowManager.LayoutParams layoutParams = udfpsControllerOverlay.coreLayoutParams;
            udfpsControllerOverlay.updateDimensions(layoutParams, this.$animation);
            windowManager.addView(view, layoutParams);
        }
    }

    /* renamed from: com.android.systemui.biometrics.UdfpsControllerOverlay$addViewNowOrLater$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UdfpsControllerOverlay.this.new AnonymousClass2(continuation);
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
                final UdfpsControllerOverlay udfpsControllerOverlay = UdfpsControllerOverlay.this;
                UdfpsControllerOverlay$special$$inlined$map$1 udfpsControllerOverlay$special$$inlined$map$1 = udfpsControllerOverlay.currentStateUpdatedToOffAodOrDozing;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay.addViewNowOrLater.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsControllerOverlay;
                        AnonymousClass1 anonymousClass1 = udfpsControllerOverlay2.addViewRunnable;
                        if (anonymousClass1 != null) {
                            StandaloneCoroutine standaloneCoroutine = udfpsControllerOverlay2.listenForCurrentKeyguardState;
                            if (standaloneCoroutine != null) {
                                standaloneCoroutine.cancel(null);
                            }
                            anonymousClass1.run();
                        }
                        udfpsControllerOverlay2.addViewRunnable = null;
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (udfpsControllerOverlay$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

    public UdfpsControllerOverlay(LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function2 function2, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Lazy lazy2, Lazy lazy3, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope) {
        this.inflater = layoutInflater;
        this.windowManager = windowManager;
        this.accessibilityManager = accessibilityManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.udfpsDisplayModeProvider = udfpsDisplayModeProvider;
        this.requestId = j;
        this.requestReason = i;
        this.controllerCallback = iUdfpsOverlayControllerCallback;
        this.onTouch = function2;
        this.deviceEntryUdfpsTouchOverlayViewModel = lazy;
        this.defaultUdfpsTouchOverlayViewModel = lazy2;
        this.promptUdfpsTouchOverlayViewModel = lazy3;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.powerInteractor = powerInteractor;
        this.scope = coroutineScope;
        final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.currentKeyguardState;
        this.currentStateUpdatedToOffAodOrDozing = new UdfpsControllerOverlay$special$$inlined$map$1(new Flow() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1

            /* renamed from: com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        KeyguardState keyguardState = (KeyguardState) obj;
                        if (keyguardState == KeyguardState.OFF || keyguardState == KeyguardState.AOD || keyguardState == KeyguardState.DOZING) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 0, 127, null);
        this.sensorBounds = new Rect();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 0, -3);
        layoutParams.setTitle("UdfpsControllerOverlay");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.flags = 16777512;
        layoutParams.privateFlags = 538968064;
        layoutParams.accessibilityTitle = " ";
        layoutParams.inputFeatures = 4;
        this.coreLayoutParams = layoutParams;
    }

    public final void addViewNowOrLater(UdfpsTouchOverlay udfpsTouchOverlay) {
        this.addViewRunnable = new AnonymousClass1(udfpsTouchOverlay, null);
        if (!((WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).isAwake()) {
            StandaloneCoroutine standaloneCoroutine = this.listenForCurrentKeyguardState;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            this.listenForCurrentKeyguardState = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass2(null), 7);
            return;
        }
        AnonymousClass1 anonymousClass1 = this.addViewRunnable;
        if (anonymousClass1 != null) {
            StandaloneCoroutine standaloneCoroutine2 = this.listenForCurrentKeyguardState;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
            }
            anonymousClass1.run();
        }
        this.addViewRunnable = null;
    }

    public final void updateDimensions(WindowManager.LayoutParams layoutParams, UdfpsAnimationViewController udfpsAnimationViewController) {
        Rect rect;
        int i = this.requestReason;
        boolean z = i == 1 || i == 2;
        if (this.accessibilityManager.isTouchExplorationEnabled() && z) {
            rect = new Rect(this.overlayParams.sensorBounds);
        } else {
            UdfpsOverlayParams udfpsOverlayParams = this.overlayParams;
            rect = new Rect(0, 0, udfpsOverlayParams.naturalDisplayWidth, udfpsOverlayParams.naturalDisplayHeight);
        }
        int i2 = this.overlayParams.rotation;
        if (i2 == 1 || i2 == 3) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing && (this.keyguardUpdateMonitor.mGoingToSleep || !keyguardStateControllerImpl.mOccluded)) {
                Surface.rotationToString(i2);
                boolean z2 = keyguardStateControllerImpl.mOccluded;
                Objects.toString(udfpsAnimationViewController);
            } else {
                Surface.rotationToString(i2);
                UdfpsOverlayParams udfpsOverlayParams2 = this.overlayParams;
                RotationUtils.rotateBounds(rect, udfpsOverlayParams2.naturalDisplayWidth, udfpsOverlayParams2.naturalDisplayHeight, i2);
                Rect rect2 = this.sensorBounds;
                UdfpsOverlayParams udfpsOverlayParams3 = this.overlayParams;
                RotationUtils.rotateBounds(rect2, udfpsOverlayParams3.naturalDisplayWidth, udfpsOverlayParams3.naturalDisplayHeight, i2);
            }
        }
        layoutParams.x = rect.left;
        layoutParams.y = rect.top;
        layoutParams.height = rect.height();
        layoutParams.width = rect.width();
    }
}
