package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ObjectAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.PulseExpansionInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationsKeyguardInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class NotificationWakeUpCoordinator implements OnHeadsUpChangedListener, StatusBarStateController.StateListener, ShadeExpansionListener, Dumpable {
    public static final NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationVisibility;
    public final KeyguardBypassController bypassController;
    public boolean collapsedEnoughToHide;
    public final CommunalInteractor communalInteractor;
    public final DozeParameters dozeParameters;
    public boolean fullyAwake;
    public Float hardDozeAmountOverride;
    public final HeadsUpManager headsUpManager;
    public float inputEasedDozeAmount;
    public float inputLinearDozeAmount;
    public float linearVisibilityAmount;
    public final NotificationWakeUpCoordinatorLogger logger;
    public boolean notificationsFullyHidden;
    public boolean notificationsVisible;
    public boolean notificationsVisibleForExpansion;
    public final NotificationsKeyguardInteractor notifsKeyguardInteractor;
    public float outputEasedDozeAmount;
    public float outputLinearDozeAmount;
    public boolean pulseExpanding;
    public final PulseExpansionInteractor pulseExpansionInteractor;
    public boolean pulsing;
    public final ScreenOffAnimationController screenOffAnimationController;
    public NotificationStackScrollLayoutController stackScrollerController;
    public int state;
    public final StatusBarStateController statusBarStateController;
    public float visibilityAmount;
    public ObjectAnimator visibilityAnimator;
    public final ArrayList wakeUpListeners;
    public boolean wakingUp;
    public boolean willWakeUp;
    public Interpolator visibilityInterpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
    public String hardDozeAmountOverrideSource = "n/a";
    public final Interpolator dozeAmountInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    public final Set entrySetToClearWhenFinished = new LinkedHashSet();

    /* renamed from: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationWakeUpCoordinator.this.new AnonymousClass2(continuation);
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
                final NotificationWakeUpCoordinator notificationWakeUpCoordinator = NotificationWakeUpCoordinator.this;
                ReadonlyStateFlow readonlyStateFlow = notificationWakeUpCoordinator.communalInteractor.isIdleOnCommunal;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).booleanValue();
                        NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                        NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = notificationWakeUpCoordinator;
                        if (!notificationWakeUpCoordinator2.overrideDozeAmountIfCommunalShowing()) {
                            notificationWakeUpCoordinator2.maybeClearHardDozeAmountOverrideHidingNotifs();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface WakeUpListener {
        void onFullyHiddenChanged(boolean z);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$Companion$notificationVisibility$1] */
    static {
        new Companion(null);
        notificationVisibility = new FloatProperty() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$Companion$notificationVisibility$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((NotificationWakeUpCoordinator) obj).linearVisibilityAmount);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                ((NotificationWakeUpCoordinator) obj).setVisibilityAmount(f);
            }
        };
    }

    public NotificationWakeUpCoordinator(CoroutineScope coroutineScope, DumpManager dumpManager, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger, NotificationsKeyguardInteractor notificationsKeyguardInteractor, CommunalInteractor communalInteractor, PulseExpansionInteractor pulseExpansionInteractor) {
        this.headsUpManager = headsUpManager;
        this.statusBarStateController = statusBarStateController;
        this.bypassController = keyguardBypassController;
        this.dozeParameters = dozeParameters;
        this.screenOffAnimationController = screenOffAnimationController;
        this.logger = notificationWakeUpCoordinatorLogger;
        this.notifsKeyguardInteractor = notificationsKeyguardInteractor;
        this.communalInteractor = communalInteractor;
        this.pulseExpansionInteractor = pulseExpansionInteractor;
        ArrayList arrayList = new ArrayList();
        this.wakeUpListeners = arrayList;
        this.state = 1;
        KeyguardBypassController.OnBypassStateChangedListener onBypassStateChangedListener = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$bypassStateChangedListener$1
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z) {
                NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                this.this$0.maybeClearHardDozeAmountOverrideHidingNotifs();
            }
        };
        dumpManager.registerDumpable(this);
        ((HeadsUpManagerImpl) headsUpManager).addListener(this);
        statusBarStateController.addCallback(this);
        keyguardBypassController.registerOnBypassStateChangedListener(onBypassStateChangedListener);
        arrayList.add(new WakeUpListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.1
            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onFullyHiddenChanged(boolean z) {
                if (z) {
                    NotificationWakeUpCoordinator notificationWakeUpCoordinator = NotificationWakeUpCoordinator.this;
                    if (notificationWakeUpCoordinator.notificationsVisibleForExpansion) {
                        notificationWakeUpCoordinator.setNotificationsVisibleForExpansion(false, false, false);
                    }
                }
            }
        });
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 7);
    }

    public final boolean clearHardDozeAmountOverride() {
        if (this.hardDozeAmountOverride == null) {
            return false;
        }
        this.hardDozeAmountOverride = null;
        this.hardDozeAmountOverrideSource = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Cleared: ", this.hardDozeAmountOverrideSource);
        updateDozeAmount();
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("inputLinearDozeAmount: " + this.inputLinearDozeAmount);
        printWriter.println("inputEasedDozeAmount: " + this.inputEasedDozeAmount);
        printWriter.println("hardDozeAmountOverride: " + this.hardDozeAmountOverride);
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "hardDozeAmountOverrideSource: ", this.hardDozeAmountOverrideSource);
        printWriter.println("outputLinearDozeAmount: " + this.outputLinearDozeAmount);
        printWriter.println("outputEasedDozeAmount: " + this.outputEasedDozeAmount);
        printWriter.println("notificationVisibleAmount: 0.0");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "notificationsVisible: ", this.notificationsVisible);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "notificationsVisibleForExpansion: ", this.notificationsVisibleForExpansion);
        printWriter.println("visibilityAmount: " + this.visibilityAmount);
        printWriter.println("linearVisibilityAmount: " + this.linearVisibilityAmount);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "pulseExpanding: ", this.pulseExpanding);
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "state: ", StatusBarState.toString(this.state));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "fullyAwake: ", this.fullyAwake);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "wakingUp: ", this.wakingUp);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "willWakeUp: ", this.willWakeUp);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "collapsedEnoughToHide: ", this.collapsedEnoughToHide);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "pulsing: ", this.pulsing);
        printWriter.println("notificationsFullyHidden: " + this.notificationsFullyHidden);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "canShowPulsingHuns: ", getCanShowPulsingHuns());
    }

    public final boolean getCanShowPulsingHuns() {
        boolean z = this.pulsing;
        if (!this.bypassController.getBypassEnabled()) {
            return z;
        }
        boolean z2 = true;
        if (!z && ((!this.wakingUp && !this.willWakeUp && !this.fullyAwake) || this.statusBarStateController.getState() != 1)) {
            z2 = false;
        }
        if (this.collapsedEnoughToHide) {
            return false;
        }
        return z2;
    }

    public final void maybeClearHardDozeAmountOverrideHidingNotifs() {
        if (Intrinsics.areEqual(this.hardDozeAmountOverride, 1.0f)) {
            StatusBarStateController statusBarStateController = this.statusBarStateController;
            boolean z = false;
            boolean z2 = statusBarStateController.getState() == 1;
            boolean zIsDozing = statusBarStateController.isDozing();
            boolean bypassEnabled = this.bypassController.getBypassEnabled();
            boolean zBooleanValue = ((Boolean) this.communalInteractor.isIdleOnCommunal.$$delegate_0.getValue()).booleanValue();
            boolean zOverrideNotificationsFullyDozingOnKeyguard = this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard();
            if ((!z2 || !zIsDozing) && !bypassEnabled && !zOverrideNotificationsFullyDozingOnKeyguard && !zBooleanValue) {
                z = true;
            }
            NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
            notificationWakeUpCoordinatorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("willRemove=", " onKeyguard=", " dozing=", z, z2);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zIsDozing, " bypass=", bypassEnabled, " animating=");
            ((LogMessageImpl) logMessageObtain).str1 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zOverrideNotificationsFullyDozingOnKeyguard, " idleOnCommunal=", zBooleanValue);
            logBuffer.commit(logMessageObtain);
            if (z) {
                clearHardDozeAmountOverride();
            }
        }
    }

    public final void notifyAnimationStart(boolean z) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        float f = notificationStackScrollLayout.mInterpolatedHideAmount;
        if (f == 0.0f || f == 1.0f) {
            notificationStackScrollLayout.mBackgroundXFactor = !z ? 1.8f : 1.5f;
            notificationStackScrollLayout.mHideXInterpolator = !z ? Interpolators.FAST_OUT_SLOW_IN_REVERSE : Interpolators.FAST_OUT_SLOW_IN;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = (f == 1.0f || f == 0.0f) ? false : true;
        if (!notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional || !z) {
            notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.double1 = f;
            logMessageImpl.str2 = String.valueOf(f2);
            logBuffer.commit(logMessageObtain);
        }
        this.inputLinearDozeAmount = f;
        this.inputEasedDozeAmount = f2;
        if (this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard()) {
            setHardDozeAmountOverride("Override: animating screen off", true);
        } else {
            if (overrideDozeAmountIfBypass() || overrideDozeAmountIfCommunalShowing() || clearHardDozeAmountOverride()) {
                return;
            }
            updateDozeAmount();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        if (z) {
            setNotificationsVisible(false, false, false);
        }
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        boolean zShouldAnimateVisibility = shouldAnimateVisibility();
        if (z) {
            if (this.entrySetToClearWhenFinished.contains(notificationEntry)) {
                this.entrySetToClearWhenFinished.remove(notificationEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
        } else if (this.outputLinearDozeAmount != 0.0f && this.linearVisibilityAmount != 0.0f) {
            if (notificationEntry.isRowDismissed()) {
                zShouldAnimateVisibility = false;
            } else if (!this.wakingUp && !this.willWakeUp) {
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if (expandableNotificationRow2 != null) {
                    expandableNotificationRow2.setHeadsUpAnimatingAway(true);
                }
                this.entrySetToClearWhenFinished.add(notificationEntry);
            }
        }
        updateNotificationVisibility(zShouldAnimateVisibility, false);
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = this.collapsedEnoughToHide;
        float f = shadeExpansionChangeEvent.fraction;
        boolean z2 = f <= 0.9f;
        if (z2 != z) {
            boolean canShowPulsingHuns = getCanShowPulsingHuns();
            this.collapsedEnoughToHide = z2;
            boolean canShowPulsingHuns2 = getCanShowPulsingHuns();
            NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
            notificationWakeUpCoordinatorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
            double d = f;
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.double1 = d;
            logMessageImpl.bool1 = z;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = canShowPulsingHuns;
            logMessageImpl.bool4 = canShowPulsingHuns2;
            logBuffer.commit(logMessageObtain);
            if (!canShowPulsingHuns || canShowPulsingHuns2) {
                return;
            }
            updateNotificationVisibility(true, true);
            ((HeadsUpManagerImpl) this.headsUpManager).releaseAllImmediately();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        int i2 = this.state;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(logMessageObtain);
        if (this.state == 0 && i == 0) {
            setHardDozeAmountOverride("Override: Shade->Shade (lock cancelled by unlock)", false);
            this.state = i;
            return;
        }
        if (this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard()) {
            setHardDozeAmountOverride("Override: animating screen off", true);
            this.state = i;
        } else if (overrideDozeAmountIfBypass()) {
            this.state = i;
        } else if (overrideDozeAmountIfCommunalShowing()) {
            this.state = i;
        } else {
            maybeClearHardDozeAmountOverrideHidingNotifs();
            this.state = i;
        }
    }

    public final boolean overrideDozeAmountIfBypass() {
        if (!this.bypassController.getBypassEnabled()) {
            return false;
        }
        if (this.statusBarStateController.getState() == 1) {
            setHardDozeAmountOverride("Override: bypass (keyguard)", true);
        } else {
            setHardDozeAmountOverride("Override: bypass (shade)", false);
        }
        return true;
    }

    public final boolean overrideDozeAmountIfCommunalShowing() {
        if (!((Boolean) this.communalInteractor.isIdleOnCommunal.$$delegate_0.getValue()).booleanValue()) {
            return false;
        }
        if (this.statusBarStateController.getState() == 1) {
            setHardDozeAmountOverride("Override: communal (keyguard)", true);
            return true;
        }
        setHardDozeAmountOverride("Override: communal (shade)", false);
        return true;
    }

    public final void setHardDozeAmountOverride(String str, boolean z) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str1 = str;
        logBuffer.commit(logMessageObtain);
        Float f = this.hardDozeAmountOverride;
        Float fValueOf = Float.valueOf(z ? 1.0f : 0.0f);
        this.hardDozeAmountOverride = fValueOf;
        this.hardDozeAmountOverrideSource = str;
        if (f != null && f.floatValue() == fValueOf.floatValue()) {
            return;
        }
        updateDozeAmount();
    }

    public final void setNotificationsVisible(boolean z, boolean z2, boolean z3) {
        if (this.notificationsVisible == z) {
            return;
        }
        this.notificationsVisible = z;
        ObjectAnimator objectAnimator = this.visibilityAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!z2) {
            setVisibilityAmount(z ? 1.0f : 0.0f);
            return;
        }
        notifyAnimationStart(z);
        boolean z4 = this.notificationsVisible;
        this.visibilityInterpolator = z4 ? Interpolators.TOUCH_RESPONSE : Interpolators.FAST_OUT_SLOW_IN_REVERSE;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, notificationVisibility, z4 ? 1.0f : 0.0f);
        objectAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.LINEAR);
        objectAnimatorOfFloat.m896setDuration(z3 ? (long) (500 / 1.5f) : 500L);
        objectAnimatorOfFloat.start();
        this.visibilityAnimator = objectAnimatorOfFloat;
    }

    public final void setNotificationsVisibleForExpansion(boolean z, boolean z2, boolean z3) {
        this.notificationsVisibleForExpansion = z;
        updateNotificationVisibility(z2, z3);
        if (z || !this.notificationsVisible) {
            return;
        }
        ((HeadsUpManagerImpl) this.headsUpManager).releaseAllImmediately();
    }

    public final void setVisibilityAmount(float f) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = (f == 1.0f || f == 0.0f) ? false : true;
        if (!notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional || !z) {
            notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).double1 = f;
            logBuffer.commit(logMessageObtain);
        }
        this.linearVisibilityAmount = f;
        this.visibilityAmount = this.visibilityInterpolator.getInterpolation(f);
        if (this.outputLinearDozeAmount == 0.0f || this.linearVisibilityAmount == 0.0f) {
            Iterator it = this.entrySetToClearWhenFinished.iterator();
            while (it.hasNext()) {
                ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
            this.entrySetToClearWhenFinished.clear();
        }
        updateHideAmount();
    }

    public final void setWakingUp(boolean z) {
        this.wakingUp = z;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(8);
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        ExpandableView expandableView = null;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        this.willWakeUp = false;
        if (z) {
            boolean z2 = this.notificationsVisible;
            KeyguardBypassController keyguardBypassController = this.bypassController;
            if (z2 && !this.notificationsVisibleForExpansion && !keyguardBypassController.getBypassEnabled()) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
                if (notificationStackScrollLayoutController == null) {
                    notificationStackScrollLayoutController = null;
                }
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                int childCount = notificationStackScrollLayout.getChildCount();
                int i = 0;
                while (true) {
                    if (i >= childCount) {
                        break;
                    }
                    ExpandableView expandableView2 = (ExpandableView) notificationStackScrollLayout.getChildAt(i);
                    if (expandableView2.getVisibility() != 8 && !(expandableView2 instanceof StackScrollerDecorView) && expandableView2 != notificationStackScrollLayout.mShelf) {
                        expandableView = expandableView2;
                        break;
                    }
                    i++;
                }
                notificationStackScrollLayout.setPulseHeight(expandableView != null ? notificationStackScrollLayout.mKeyguardBypassEnabled ? expandableView.getHeadsUpHeightWithoutHeader() : expandableView.getCollapsedHeight() : 0.0f);
                int childCount2 = notificationStackScrollLayout.getChildCount();
                float translationY = -1.0f;
                boolean z3 = true;
                for (int i2 = 0; i2 < childCount2; i2++) {
                    ExpandableView expandableView3 = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    if (expandableView3.getVisibility() != 8) {
                        boolean z4 = expandableView3 == notificationStackScrollLayout.mShelf;
                        if ((expandableView3 instanceof ExpandableNotificationRow) || z4) {
                            if (expandableView3.getVisibility() != 0 || z4) {
                                if (!z3) {
                                    expandableView3.setTranslationY(translationY);
                                }
                            } else if (z3) {
                                translationY = (expandableView3.getTranslationY() + expandableView3.mActualHeight) - notificationStackScrollLayout.mShelf.getHeight();
                                z3 = false;
                            }
                        }
                    }
                }
            }
            if (!keyguardBypassController.getBypassEnabled() || this.notificationsVisible) {
                return;
            }
            updateNotificationVisibility(shouldAnimateVisibility(), false);
        }
    }

    public final boolean shouldAnimateVisibility() {
        DozeParameters dozeParameters = this.dozeParameters;
        return dozeParameters.getAlwaysOn() && !dozeParameters.getDisplayNeedsBlanking();
    }

    public final void updateDozeAmount() {
        Float f;
        Float f2 = this.hardDozeAmountOverride;
        float fFloatValue = f2 != null ? f2.floatValue() : this.inputLinearDozeAmount;
        float f3 = this.outputLinearDozeAmount;
        boolean z = f3 == fFloatValue;
        boolean z2 = !z;
        if (fFloatValue != 1.0f && fFloatValue != 0.0f && (f3 == 0.0f || f3 == 1.0f)) {
            notifyAnimationStart(f3 == 1.0f);
        }
        this.outputLinearDozeAmount = fFloatValue;
        this.outputEasedDozeAmount = ((PathInterpolator) this.dozeAmountInterpolator).getInterpolation(fFloatValue);
        float f4 = this.inputLinearDozeAmount;
        Float f5 = this.hardDozeAmountOverride;
        float f6 = this.outputLinearDozeAmount;
        int state = this.statusBarStateController.getState();
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z3 = (f4 == 1.0f || f4 == 0.0f) ? false : true;
        if (!z3 || notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional != z3 || notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState != state || ((f = notificationWakeUpCoordinatorLogger.lastSetHardOverride) != null ? f5 == null || f.floatValue() != f5.floatValue() : f5 != null)) {
            notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional = z3;
            notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState = state;
            notificationWakeUpCoordinatorLogger.lastSetHardOverride = f5;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
            double d = f4;
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.double1 = d;
            logMessageImpl.str1 = String.valueOf(f5);
            logMessageImpl.str2 = String.valueOf(f6);
            logMessageImpl.int1 = state;
            logMessageImpl.bool1 = z2;
            logBuffer.commit(logMessageObtain);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController : null;
        float f7 = this.outputEasedDozeAmount;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (f7 != ambientState.mDozeAmount) {
            ambientState.mDozeAmount = f7;
            if (f7 == 0.0f || f7 == 1.0f) {
                ambientState.setPulseHeight(100000.0f);
            }
        }
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
        updateHideAmount();
        if (z || this.outputLinearDozeAmount != 0.0f) {
            return;
        }
        setNotificationsVisible(false, false, false);
        setNotificationsVisibleForExpansion(false, false, false);
    }

    public final void updateHideAmount() {
        float fMin = Math.min(1.0f - this.linearVisibilityAmount, this.outputLinearDozeAmount);
        float fMin2 = Math.min(1.0f - this.visibilityAmount, this.outputEasedDozeAmount);
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        int i = 0;
        if (notificationWakeUpCoordinatorLogger.lastSetHideAmount != fMin) {
            notificationWakeUpCoordinatorLogger.lastSetHideAmount = fMin;
            boolean z = (fMin == 1.0f || fMin == 0.0f) ? false : true;
            if (!notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional || !z) {
                notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional = z;
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 = new NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).double1 = fMin;
                logBuffer.commit(logMessageObtain);
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mLinearHideAmount = fMin;
        notificationStackScrollLayout.mInterpolatedHideAmount = fMin2;
        boolean zIsFullyHidden = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean zIsHiddenAtAll = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (fMin2 == 1.0f && ambientState.mHideAmount != fMin2) {
            ambientState.setPulseHeight(100000.0f);
        }
        ambientState.mHideAmount = fMin2;
        boolean zIsFullyHidden2 = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean zIsHiddenAtAll2 = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        if (zIsFullyHidden2 != zIsFullyHidden) {
            notificationStackScrollLayout.mController.updateVisibility((notificationStackScrollLayout.mAmbientState.isFullyHidden() && notificationStackScrollLayout.onKeyguard()) ? false : true);
            notificationStackScrollLayout.resetAllSwipeState();
        }
        if (!zIsHiddenAtAll && zIsHiddenAtAll2) {
            notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView$1(true, true);
        }
        if (zIsFullyHidden2 != zIsFullyHidden || zIsHiddenAtAll != zIsHiddenAtAll2) {
            notificationStackScrollLayout.invalidateOutline();
        }
        notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
        notificationStackScrollLayout.requestChildrenUpdate();
        notificationStackScrollLayout.updateOwnTranslationZ();
        boolean z2 = fMin == 1.0f;
        if (this.notificationsFullyHidden != z2) {
            this.notificationsFullyHidden = z2;
            ArrayList arrayList = this.wakeUpListeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((WakeUpListener) obj).onFullyHiddenChanged(z2);
            }
            this.notifsKeyguardInteractor.repository.areNotificationsFullyHidden.updateState(null, Boolean.valueOf(z2));
        }
    }

    public final void updateNotificationVisibility(boolean z, boolean z2) {
        boolean z3 = (this.notificationsVisibleForExpansion || ((HeadsUpManagerImpl) this.headsUpManager).hasNotifications()) && getCanShowPulsingHuns();
        if (z3 || !this.notificationsVisible || (!(this.wakingUp || this.willWakeUp) || this.outputLinearDozeAmount == 0.0f)) {
            setNotificationsVisible(z3, z, z2);
        }
    }

    public static /* synthetic */ void getDozeAmountInterpolator$annotations() {
    }

    public static /* synthetic */ void getStatusBarState$annotations() {
    }
}
