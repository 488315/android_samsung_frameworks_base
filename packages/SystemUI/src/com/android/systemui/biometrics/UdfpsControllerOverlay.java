package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Build;
import android.util.RotationUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Flags;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class UdfpsControllerOverlay {
    public final AccessibilityManager accessibilityManager;
    public UdfpsControllerOverlay$addViewNowOrLater$$inlined$Runnable$1 addViewRunnable;
    public final IUdfpsOverlayControllerCallback controllerCallback;
    public final WindowManager.LayoutParams coreLayoutParams;
    public final UdfpsControllerOverlay$special$$inlined$map$1 currentStateUpdatedToOffAodOrDozing;
    public final Lazy defaultUdfpsTouchOverlayViewModel;
    public final Lazy deviceEntryUdfpsTouchOverlayViewModel;
    public final LayoutInflater inflater;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public Job listenForCurrentKeyguardState;
    public final Function3 onTouch;
    public UdfpsOverlayParams overlayParams;
    public UdfpsControllerOverlay$show$3$1 overlayTouchListener;
    public UdfpsTouchOverlay overlayTouchView;
    public final PowerInteractor powerInteractor;
    public final long requestId;
    public final int requestReason;
    public final CoroutineScope scope;
    public Rect sensorBounds;
    public final StatusBarStateController statusBarStateController;
    public final UdfpsDisplayModeProvider udfpsDisplayModeProvider;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final WindowManager windowManager;

    public UdfpsControllerOverlay(Context context, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityTransitionAnimator activityTransitionAnimator, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate, KeyguardTransitionInteractor keyguardTransitionInteractor, SelectedUserInteractor selectedUserInteractor, Lazy lazy, Lazy lazy2, ShadeInteractor shadeInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope) {
        this(context, layoutInflater, windowManager, accessibilityManager, statusBarStateController, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, j, i, iUdfpsOverlayControllerCallback, function3, activityTransitionAnimator, primaryBouncerInteractor, alternateBouncerInteractor, false, udfpsKeyguardAccessibilityDelegate, keyguardTransitionInteractor, selectedUserInteractor, lazy, lazy2, shadeInteractor, udfpsOverlayInteractor, powerInteractor, coroutineScope, QuickStepContract.SYSUI_STATE_DEVICE_DOZING, null);
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
            Flags.deviceEntryUdfpsRefactor();
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

    public UdfpsControllerOverlay(Context context, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityTransitionAnimator activityTransitionAnimator, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, boolean z, UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate, KeyguardTransitionInteractor keyguardTransitionInteractor, SelectedUserInteractor selectedUserInteractor, Lazy lazy, Lazy lazy2, ShadeInteractor shadeInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope) {
        this.inflater = layoutInflater;
        this.windowManager = windowManager;
        this.accessibilityManager = accessibilityManager;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.udfpsDisplayModeProvider = udfpsDisplayModeProvider;
        this.requestId = j;
        this.requestReason = i;
        this.controllerCallback = iUdfpsOverlayControllerCallback;
        this.onTouch = function3;
        this.deviceEntryUdfpsTouchOverlayViewModel = lazy;
        this.defaultUdfpsTouchOverlayViewModel = lazy2;
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = (com.android.systemui.keyguard.shared.model.KeyguardState) r6
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OFF
                        if (r6 == r2) goto L41
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        if (r6 == r2) goto L41
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.DOZING
                        if (r6 != r2) goto L4c
                    L41:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsControllerOverlay$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 0, 127, null);
        this.sensorBounds = new Rect();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 0, -3);
        layoutParams.setTitle("UdfpsControllerOverlay");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.flags = 25166120;
        layoutParams.privateFlags = 538968064;
        layoutParams.accessibilityTitle = " ";
        layoutParams.inputFeatures = 4;
        this.coreLayoutParams = layoutParams;
    }

    public /* synthetic */ UdfpsControllerOverlay(Context context, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityTransitionAnimator activityTransitionAnimator, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, boolean z, UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate, KeyguardTransitionInteractor keyguardTransitionInteractor, SelectedUserInteractor selectedUserInteractor, Lazy lazy, Lazy lazy2, ShadeInteractor shadeInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, PowerInteractor powerInteractor, CoroutineScope coroutineScope, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, windowManager, accessibilityManager, statusBarStateController, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, j, i, iUdfpsOverlayControllerCallback, function3, activityTransitionAnimator, primaryBouncerInteractor, alternateBouncerInteractor, (i2 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? Build.IS_DEBUGGABLE : z, udfpsKeyguardAccessibilityDelegate, keyguardTransitionInteractor, selectedUserInteractor, lazy, lazy2, shadeInteractor, udfpsOverlayInteractor, powerInteractor, coroutineScope);
    }
}
