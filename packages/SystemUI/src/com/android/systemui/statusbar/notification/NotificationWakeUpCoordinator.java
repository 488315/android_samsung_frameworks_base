package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationsKeyguardInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class NotificationWakeUpCoordinator implements OnHeadsUpChangedListener, StatusBarStateController.StateListener, ShadeExpansionListener, Dumpable {
    public static final NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 delayedDozeAmount;
    public static final NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationVisibility;
    public final KeyguardBypassController bypassController;
    public boolean collapsedEnoughToHide;
    public final CommunalInteractor communalInteractor;
    public ObjectAnimator delayedDozeAmountAnimator;
    public float delayedDozeAmountOverride;
    public final DozeParameters dozeParameters;
    public boolean fullyAwake;
    public Float hardDozeAmountOverride;
    public float inputEasedDozeAmount;
    public float inputLinearDozeAmount;
    public final NotificationWakeUpCoordinatorLogger logger;
    public final HeadsUpManager mHeadsUpManager;
    public float mLinearVisibilityAmount;
    public boolean mNotificationsVisible;
    public boolean mNotificationsVisibleForExpansion;
    public NotificationStackScrollLayoutController mStackScrollerController;
    public float mVisibilityAmount;
    public ObjectAnimator mVisibilityAnimator;
    public boolean notificationsFullyHidden;
    public final NotificationsKeyguardInteractor notifsKeyguardInteractor;
    public float outputEasedDozeAmount;
    public float outputLinearDozeAmount;
    public boolean pulseExpanding;
    public boolean pulsing;
    public final ScreenOffAnimationController screenOffAnimationController;
    public int state;
    public final StatusBarStateController statusBarStateController;
    public final ArrayList wakeUpListeners;
    public boolean wakingUp;
    public boolean willWakeUp;
    public Interpolator mVisibilityInterpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
    public String hardDozeAmountOverrideSource = "n/a";
    public final Interpolator dozeAmountInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    public final Set mEntrySetToClearWhenFinished = new LinkedHashSet();

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
                        ((Boolean) obj2).getClass();
                        NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                        NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = NotificationWakeUpCoordinator.this;
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$Companion$notificationVisibility$1] */
    static {
        new Companion(null);
        notificationVisibility = new FloatProperty() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$Companion$notificationVisibility$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((NotificationWakeUpCoordinator) obj).mLinearVisibilityAmount);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                ((NotificationWakeUpCoordinator) obj).setVisibilityAmount(f);
            }
        };
        delayedDozeAmount = new NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1();
    }

    public NotificationWakeUpCoordinator(CoroutineScope coroutineScope, DumpManager dumpManager, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger, NotificationsKeyguardInteractor notificationsKeyguardInteractor, CommunalInteractor communalInteractor) {
        this.mHeadsUpManager = headsUpManager;
        this.statusBarStateController = statusBarStateController;
        this.bypassController = keyguardBypassController;
        this.dozeParameters = dozeParameters;
        this.screenOffAnimationController = screenOffAnimationController;
        this.logger = notificationWakeUpCoordinatorLogger;
        this.notifsKeyguardInteractor = notificationsKeyguardInteractor;
        this.communalInteractor = communalInteractor;
        ArrayList arrayList = new ArrayList();
        this.wakeUpListeners = arrayList;
        this.state = 1;
        KeyguardBypassController.OnBypassStateChangedListener onBypassStateChangedListener = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$bypassStateChangedListener$1
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z) {
                NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationWakeUpCoordinator$Companion$notificationVisibility$1 = NotificationWakeUpCoordinator.notificationVisibility;
                NotificationWakeUpCoordinator.this.maybeClearHardDozeAmountOverrideHidingNotifs();
            }
        };
        dumpManager.registerDumpable(this);
        ((BaseHeadsUpManager) headsUpManager).addListener(this);
        statusBarStateController.addCallback(this);
        keyguardBypassController.registerOnBypassStateChangedListener(onBypassStateChangedListener);
        arrayList.add(new WakeUpListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.1
            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onFullyHiddenChanged(boolean z) {
                if (z) {
                    NotificationWakeUpCoordinator notificationWakeUpCoordinator = NotificationWakeUpCoordinator.this;
                    if (notificationWakeUpCoordinator.mNotificationsVisibleForExpansion) {
                        notificationWakeUpCoordinator.setNotificationsVisibleForExpansion(false, false, false);
                    }
                }
            }
        });
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
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
        printWriter.println("delayedDozeAmountOverride: " + this.delayedDozeAmountOverride);
        printWriter.println("hardDozeAmountOverride: " + this.hardDozeAmountOverride);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "hardDozeAmountOverrideSource: ", this.hardDozeAmountOverrideSource);
        printWriter.println("outputLinearDozeAmount: " + this.outputLinearDozeAmount);
        printWriter.println("outputEasedDozeAmount: " + this.outputEasedDozeAmount);
        printWriter.println("mNotificationVisibleAmount: 0.0");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("mNotificationsVisible: ", this.mNotificationsVisible, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("mNotificationsVisibleForExpansion: ", this.mNotificationsVisibleForExpansion, printWriter);
        printWriter.println("mVisibilityAmount: " + this.mVisibilityAmount);
        printWriter.println("mLinearVisibilityAmount: " + this.mLinearVisibilityAmount);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("pulseExpanding: ", this.pulseExpanding, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "state: ", StatusBarState.toString(this.state));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("fullyAwake: ", this.fullyAwake, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("wakingUp: ", this.wakingUp, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("willWakeUp: ", this.willWakeUp, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("collapsedEnoughToHide: ", this.collapsedEnoughToHide, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("pulsing: ", this.pulsing, printWriter);
        printWriter.println("notificationsFullyHidden: " + this.notificationsFullyHidden);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("canShowPulsingHuns: ", getCanShowPulsingHuns(), printWriter);
    }

    public final boolean getCanShowPulsingHuns() {
        boolean z = this.pulsing;
        if (!this.bypassController.getBypassEnabled()) {
            return z;
        }
        boolean z2 = z || ((this.wakingUp || this.willWakeUp || this.fullyAwake) && this.statusBarStateController.getState() == 1);
        if (this.collapsedEnoughToHide) {
            return false;
        }
        return z2;
    }

    public final void logDelayingClockWakeUpAnimation(boolean z) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2 notificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "logDelayingClockWakeUpAnimation(" + ((LogMessage) obj).getBool1() + ")";
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void maybeClearHardDozeAmountOverrideHidingNotifs() {
        if (Intrinsics.areEqual(this.hardDozeAmountOverride, 1.0f)) {
            StatusBarStateController statusBarStateController = this.statusBarStateController;
            boolean z = false;
            boolean z2 = statusBarStateController.getState() == 1;
            boolean isDozing = statusBarStateController.isDozing();
            boolean bypassEnabled = this.bypassController.getBypassEnabled();
            boolean booleanValue = ((Boolean) this.communalInteractor.isIdleOnCommunal.$$delegate_0.getValue()).booleanValue();
            boolean overrideNotificationsFullyDozingOnKeyguard = this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard();
            if ((!z2 || !isDozing) && !bypassEnabled && !overrideNotificationsFullyDozingOnKeyguard && !booleanValue) {
                z = true;
            }
            NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
            notificationWakeUpCoordinatorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2 notificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("maybeClearHardDozeAmountOverrideHidingNotifs() ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2, null);
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("willRemove=", " onKeyguard=", " dozing=", z, z2);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isDozing, " bypass=", bypassEnabled, " animating=");
            ((LogMessageImpl) obtain).str1 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, overrideNotificationsFullyDozingOnKeyguard, " idleOnCommunal=", booleanValue);
            logBuffer.commit(obtain);
            if (z) {
                clearHardDozeAmountOverride();
            }
        }
    }

    public final void notifyAnimationStart(boolean z) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        boolean z2 = !z;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        float f = notificationStackScrollLayout.mInterpolatedHideAmount;
        if (f == 0.0f || f == 1.0f) {
            notificationStackScrollLayout.mBackgroundXFactor = z2 ? 1.8f : 1.5f;
            notificationStackScrollLayout.mHideXInterpolator = z2 ? Interpolators.FAST_OUT_SLOW_IN_REVERSE : Interpolators.FAST_OUT_SLOW_IN;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = (f == 1.0f || f == 0.0f) ? false : true;
        if (!notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
            notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2 notificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "onDozeAmountChanged(linear=" + logMessage.getDouble1() + ", eased=" + logMessage.getStr2() + ")";
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.double1 = f;
            logMessageImpl.str2 = String.valueOf(f2);
            logBuffer.commit(obtain);
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

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        boolean shouldAnimateVisibility = shouldAnimateVisibility();
        if (z) {
            if (this.mEntrySetToClearWhenFinished.contains(notificationEntry)) {
                this.mEntrySetToClearWhenFinished.remove(notificationEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
        } else if (this.outputLinearDozeAmount != 0.0f && this.mLinearVisibilityAmount != 0.0f) {
            if (notificationEntry.isRowDismissed()) {
                shouldAnimateVisibility = false;
            } else if (!this.wakingUp && !this.willWakeUp) {
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if (expandableNotificationRow2 != null) {
                    expandableNotificationRow2.setHeadsUpAnimatingAway(true);
                }
                this.mEntrySetToClearWhenFinished.add(notificationEntry);
            }
        }
        updateNotificationVisibility(shouldAnimateVisibility, false);
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = shadeExpansionChangeEvent.fraction <= 0.9f;
        if (z != this.collapsedEnoughToHide) {
            boolean canShowPulsingHuns = getCanShowPulsingHuns();
            this.collapsedEnoughToHide = z;
            if (!canShowPulsingHuns || getCanShowPulsingHuns()) {
                return;
            }
            updateNotificationVisibility(true, true);
            ((BaseHeadsUpManager) this.mHeadsUpManager).releaseAllImmediately();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        int i2 = this.state;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logOnStateChanged$2 notificationWakeUpCoordinatorLogger$logOnStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logOnStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("onStateChanged(newState=", StatusBarState.toString(logMessage.getInt1()), ") stored=", StatusBarState.toString(logMessage.getInt2()));
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logOnStateChanged$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
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
        } else {
            setHardDozeAmountOverride("Override: communal (shade)", false);
        }
        return true;
    }

    public final void setHardDozeAmountOverride(String str, boolean z) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2 notificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "setDozeAmountOverride(dozing=" + logMessage.getBool1() + ", source=\"" + logMessage.getStr1() + "\")";
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
        Float f = this.hardDozeAmountOverride;
        Float valueOf = Float.valueOf(z ? 1.0f : 0.0f);
        this.hardDozeAmountOverride = valueOf;
        this.hardDozeAmountOverrideSource = str;
        if (f != null && f.floatValue() == valueOf.floatValue()) {
            return;
        }
        updateDozeAmount();
    }

    public final void setNotificationsVisible(boolean z, boolean z2, boolean z3) {
        if (this.mNotificationsVisible == z) {
            return;
        }
        this.mNotificationsVisible = z;
        ObjectAnimator objectAnimator = this.mVisibilityAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!z2) {
            setVisibilityAmount(z ? 1.0f : 0.0f);
            return;
        }
        notifyAnimationStart(z);
        boolean z4 = this.mNotificationsVisible;
        this.mVisibilityInterpolator = z4 ? Interpolators.TOUCH_RESPONSE : Interpolators.FAST_OUT_SLOW_IN_REVERSE;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, notificationVisibility, z4 ? 1.0f : 0.0f);
        ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR);
        ofFloat.m788setDuration(z3 ? (long) (500 / 1.5f) : 500L);
        ofFloat.start();
        this.mVisibilityAnimator = ofFloat;
    }

    public final void setNotificationsVisibleForExpansion(boolean z, boolean z2, boolean z3) {
        this.mNotificationsVisibleForExpansion = z;
        updateNotificationVisibility(z2, z3);
        if (z || !this.mNotificationsVisible) {
            return;
        }
        ((BaseHeadsUpManager) this.mHeadsUpManager).releaseAllImmediately();
    }

    public final void setVisibilityAmount(float f) {
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = (f == 1.0f || f == 0.0f) ? false : true;
        if (!notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
            notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2 notificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "setVisibilityAmount(" + ((LogMessage) obj).getDouble1() + ")";
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2, null);
            ((LogMessageImpl) obtain).double1 = f;
            logBuffer.commit(obtain);
        }
        this.mLinearVisibilityAmount = f;
        this.mVisibilityAmount = this.mVisibilityInterpolator.getInterpolation(f);
        if (this.outputLinearDozeAmount == 0.0f || this.mLinearVisibilityAmount == 0.0f) {
            Iterator it = this.mEntrySetToClearWhenFinished.iterator();
            while (it.hasNext()) {
                ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
            this.mEntrySetToClearWhenFinished.clear();
        }
        updateHideAmount();
    }

    public final void setWakingUp(boolean z, boolean z2) {
        int i;
        ExpandableView expandableView;
        float f;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logSetWakingUp$2 notificationWakeUpCoordinatorLogger$logSetWakingUp$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetWakingUp$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "setWakingUp(wakingUp=" + logMessage.getBool1() + ", requestDelayedAnimation=" + logMessage.getBool2() + ")";
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetWakingUp$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
        this.wakingUp = z;
        this.willWakeUp = false;
        if (z) {
            boolean z3 = this.mNotificationsVisible;
            KeyguardBypassController keyguardBypassController = this.bypassController;
            if (z3 && !this.mNotificationsVisibleForExpansion && !keyguardBypassController.getBypassEnabled()) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
                if (notificationStackScrollLayoutController == null) {
                    notificationStackScrollLayoutController = null;
                }
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                int childCount = notificationStackScrollLayout.getChildCount();
                int i2 = 0;
                while (true) {
                    i = 8;
                    if (i2 >= childCount) {
                        expandableView = null;
                        break;
                    }
                    expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    if (expandableView.getVisibility() != 8 && !(expandableView instanceof StackScrollerDecorView) && expandableView != notificationStackScrollLayout.mShelf) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (expandableView != null) {
                    f = notificationStackScrollLayout.mKeyguardBypassEnabled ? expandableView.getHeadsUpHeightWithoutHeader() : expandableView.getCollapsedHeight();
                } else {
                    f = 0.0f;
                }
                notificationStackScrollLayout.setPulseHeight(f);
                int childCount2 = notificationStackScrollLayout.getChildCount();
                float f2 = -1.0f;
                int i3 = 0;
                boolean z4 = true;
                while (i3 < childCount2) {
                    ExpandableView expandableView2 = (ExpandableView) notificationStackScrollLayout.getChildAt(i3);
                    if (expandableView2.getVisibility() != i) {
                        boolean z5 = expandableView2 == notificationStackScrollLayout.mShelf;
                        if ((expandableView2 instanceof ExpandableNotificationRow) || z5) {
                            if (expandableView2.getVisibility() != 0 || z5) {
                                if (!z4) {
                                    expandableView2.setTranslationY(f2);
                                }
                            } else if (z4) {
                                z4 = false;
                                f2 = (expandableView2.getTranslationY() + expandableView2.mActualHeight) - notificationStackScrollLayout.mShelf.getHeight();
                            }
                        }
                    }
                    i3++;
                    i = 8;
                }
            }
            if (keyguardBypassController.getBypassEnabled() && !this.mNotificationsVisible) {
                updateNotificationVisibility(shouldAnimateVisibility(), false);
            }
        }
        if (z && z2) {
            boolean z6 = this.delayedDozeAmountAnimator != null;
            LogMessage obtain2 = logBuffer.obtain("NotificationWakeUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logStartDelayedDozeAmountAnimation$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("startDelayedDozeAmountAnimation() alreadyRunning=", ((LogMessage) obj).getBool1());
                }
            }, null);
            ((LogMessageImpl) obtain2).bool1 = z6;
            logBuffer.commit(obtain2);
            if (z6) {
                return;
            }
            NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 notificationWakeUpCoordinator$Companion$delayedDozeAmount$1 = delayedDozeAmount;
            notificationWakeUpCoordinator$Companion$delayedDozeAmount$1.getClass();
            NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1.setValue(this, 1.0f);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, notificationWakeUpCoordinator$Companion$delayedDozeAmount$1, 0.0f);
            ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR);
            ofFloat.m788setDuration(500L);
            ofFloat.setStartDelay(250L);
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnStart$1
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    Iterator it = NotificationWakeUpCoordinator.this.wakeUpListeners.iterator();
                    while (it.hasNext()) {
                        ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onDelayedDozeAmountAnimationRunning();
                    }
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }
            });
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnEnd$1
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    NotificationWakeUpCoordinator notificationWakeUpCoordinator = NotificationWakeUpCoordinator.this;
                    notificationWakeUpCoordinator.delayedDozeAmountAnimator = null;
                    Iterator it = notificationWakeUpCoordinator.wakeUpListeners.iterator();
                    while (it.hasNext()) {
                        ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onDelayedDozeAmountAnimationRunning();
                    }
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            ofFloat.start();
            this.delayedDozeAmountAnimator = ofFloat;
        }
    }

    public final boolean shouldAnimateVisibility() {
        DozeParameters dozeParameters = this.dozeParameters;
        return dozeParameters.getAlwaysOn() && !dozeParameters.getDisplayNeedsBlanking();
    }

    public final void updateDozeAmount() {
        Float f;
        Float f2 = this.hardDozeAmountOverride;
        float floatValue = f2 != null ? f2.floatValue() : Math.max(this.inputLinearDozeAmount, this.delayedDozeAmountOverride);
        float f3 = this.outputLinearDozeAmount;
        boolean z = !(f3 == floatValue);
        if (floatValue != 1.0f && floatValue != 0.0f && (f3 == 0.0f || f3 == 1.0f)) {
            notifyAnimationStart(f3 == 1.0f);
        }
        this.outputLinearDozeAmount = floatValue;
        this.outputEasedDozeAmount = ((PathInterpolator) this.dozeAmountInterpolator).getInterpolation(floatValue);
        float f4 = this.inputLinearDozeAmount;
        float f5 = this.delayedDozeAmountOverride;
        Float f6 = this.hardDozeAmountOverride;
        float f7 = this.outputLinearDozeAmount;
        int state = this.statusBarStateController.getState();
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z2 = (f4 == 1.0f || f4 == 0.0f) ? false : true;
        boolean z3 = (f5 == 1.0f || f5 == 0.0f) ? false : true;
        if ((!z2 && !z3) || notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional != z2 || notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional != z3 || notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState != state || ((f = notificationWakeUpCoordinatorLogger.lastSetHardOverride) != null ? f6 == null || f.floatValue() != f6.floatValue() : f6 != null) || !notificationWakeUpCoordinatorLogger.allowThrottle) {
            notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional = z2;
            notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional = z3;
            notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState = state;
            notificationWakeUpCoordinatorLogger.lastSetHardOverride = f6;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2 notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    double double1 = logMessage.getDouble1();
                    String str3 = logMessage.getStr3();
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    String statusBarState = StatusBarState.toString(logMessage.getInt1());
                    boolean bool1 = logMessage.getBool1();
                    StringBuilder sb = new StringBuilder("updateDozeAmount() inputLinear=");
                    sb.append(double1);
                    sb.append(" delayLinear=");
                    sb.append(str3);
                    ConstraintWidget$$ExternalSyntheticOutline0.m(sb, " hardOverride=", str1, " outputLinear=", str2);
                    sb.append(" state=");
                    sb.append(statusBarState);
                    sb.append(" changed=");
                    sb.append(bool1);
                    return sb.toString();
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2, null);
            double d = f4;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.double1 = d;
            logMessageImpl.str1 = String.valueOf(f6);
            logMessageImpl.str2 = String.valueOf(f7);
            logMessageImpl.str3 = String.valueOf(f5);
            logMessageImpl.int1 = state;
            logMessageImpl.bool1 = z;
            logBuffer.commit(obtain);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController : null;
        float f8 = this.outputEasedDozeAmount;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (f8 != ambientState.mDozeAmount) {
            ambientState.mDozeAmount = f8;
            if (f8 == 0.0f || f8 == 1.0f) {
                ambientState.setPulseHeight(100000.0f);
            }
        }
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
        updateHideAmount();
        if (z && this.outputLinearDozeAmount == 0.0f) {
            setNotificationsVisible(false, false, false);
            setNotificationsVisibleForExpansion(false, false, false);
        }
    }

    public final void updateHideAmount() {
        float min = Math.min(1.0f - this.mLinearVisibilityAmount, this.outputLinearDozeAmount);
        float min2 = Math.min(1.0f - this.mVisibilityAmount, this.outputEasedDozeAmount);
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        float f = notificationWakeUpCoordinatorLogger.lastSetHideAmount;
        boolean z = notificationWakeUpCoordinatorLogger.allowThrottle;
        if (f != min || !z) {
            notificationWakeUpCoordinatorLogger.lastSetHideAmount = min;
            boolean z2 = (min == 1.0f || min == 0.0f) ? false : true;
            if (!notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional || !z2 || !z) {
                notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional = z2;
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationWakeUpCoordinatorLogger$logSetHideAmount$2 notificationWakeUpCoordinatorLogger$logSetHideAmount$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetHideAmount$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return "setHideAmount(" + ((LogMessage) obj).getDouble1() + ")";
                    }
                };
                LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
                LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetHideAmount$2, null);
                ((LogMessageImpl) obtain).double1 = min;
                logBuffer.commit(obtain);
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mLinearHideAmount = min;
        notificationStackScrollLayout.mInterpolatedHideAmount = min2;
        boolean isFullyHidden = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean isHiddenAtAll = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (min2 == 1.0f && ambientState.mHideAmount != min2) {
            ambientState.setPulseHeight(100000.0f);
        }
        ambientState.mHideAmount = min2;
        boolean isFullyHidden2 = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean isHiddenAtAll2 = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        if (isFullyHidden2 != isFullyHidden) {
            notificationStackScrollLayout.updateVisibility$1();
            notificationStackScrollLayout.resetAllSwipeState();
        }
        if (!isHiddenAtAll && isHiddenAtAll2) {
            notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView(true, true);
        }
        if (isFullyHidden2 != isFullyHidden || isHiddenAtAll != isHiddenAtAll2) {
            notificationStackScrollLayout.invalidateOutline();
        }
        notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
        notificationStackScrollLayout.requestChildrenUpdate();
        notificationStackScrollLayout.updateOwnTranslationZ();
        boolean z3 = min == 1.0f;
        if (this.notificationsFullyHidden != z3) {
            this.notificationsFullyHidden = z3;
            Iterator it = this.wakeUpListeners.iterator();
            while (it.hasNext()) {
                ((WakeUpListener) it.next()).onFullyHiddenChanged(z3);
            }
            this.notifsKeyguardInteractor.repository.areNotificationsFullyHidden.updateState(null, Boolean.valueOf(z3));
        }
    }

    public final void updateNotificationVisibility(boolean z, boolean z2) {
        boolean z3 = (this.mNotificationsVisibleForExpansion || ((BaseHeadsUpManager) this.mHeadsUpManager).hasNotifications()) && getCanShowPulsingHuns();
        if (z3 || !this.mNotificationsVisible || (!(this.wakingUp || this.willWakeUp) || this.outputLinearDozeAmount == 0.0f)) {
            setNotificationsVisible(z3, z, z2);
        }
    }

    public interface WakeUpListener {
        void onFullyHiddenChanged(boolean z);

        default void onDelayedDozeAmountAnimationRunning() {
        }

        default void onPulseExpansionAmountChanged() {
        }
    }

    public static /* synthetic */ void getDozeAmountInterpolator$annotations() {
    }

    public static /* synthetic */ void getStatusBarState$annotations() {
    }
}
