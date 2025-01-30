package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationWakeUpCoordinator implements OnHeadsUpChangedListener, StatusBarStateController.StateListener, ShadeExpansionListener, Dumpable {
    public static final NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 delayedDozeAmount;
    public static final NotificationWakeUpCoordinator$Companion$notificationVisibility$1 notificationVisibility;
    public final KeyguardBypassController bypassController;
    public boolean collapsedEnoughToHide;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public NotificationWakeUpCoordinator(DumpManager dumpManager, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger) {
        this.mHeadsUpManager = headsUpManager;
        this.statusBarStateController = statusBarStateController;
        this.bypassController = keyguardBypassController;
        this.dozeParameters = dozeParameters;
        this.screenOffAnimationController = screenOffAnimationController;
        this.logger = notificationWakeUpCoordinatorLogger;
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
        headsUpManager.addListener(this);
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
    }

    public final boolean clearHardDozeAmountOverride() {
        if (this.hardDozeAmountOverride == null) {
            return false;
        }
        this.hardDozeAmountOverride = null;
        this.hardDozeAmountOverrideSource = KeyAttributes$$ExternalSyntheticOutline0.m21m("Cleared: ", this.hardDozeAmountOverrideSource);
        updateDozeAmount();
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("inputLinearDozeAmount: " + this.inputLinearDozeAmount);
        printWriter.println("inputEasedDozeAmount: " + this.inputEasedDozeAmount);
        printWriter.println("delayedDozeAmountOverride: " + this.delayedDozeAmountOverride);
        printWriter.println("hardDozeAmountOverride: " + this.hardDozeAmountOverride);
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("hardDozeAmountOverrideSource: ", this.hardDozeAmountOverrideSource, printWriter);
        printWriter.println("outputLinearDozeAmount: " + this.outputLinearDozeAmount);
        printWriter.println("outputEasedDozeAmount: " + this.outputEasedDozeAmount);
        printWriter.println("mNotificationVisibleAmount: 0.0");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("mNotificationsVisible: ", this.mNotificationsVisible, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("mNotificationsVisibleForExpansion: ", this.mNotificationsVisibleForExpansion, printWriter);
        printWriter.println("mVisibilityAmount: " + this.mVisibilityAmount);
        printWriter.println("mLinearVisibilityAmount: " + this.mLinearVisibilityAmount);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("pulseExpanding: ", this.pulseExpanding, printWriter);
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("state: ", StatusBarState.toString(this.state), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("fullyAwake: ", this.fullyAwake, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("wakingUp: ", this.wakingUp, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("willWakeUp: ", this.willWakeUp, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("collapsedEnoughToHide: ", this.collapsedEnoughToHide, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("pulsing: ", this.pulsing, printWriter);
        printWriter.println("notificationsFullyHidden: " + this.notificationsFullyHidden);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("canShowPulsingHuns: ", getCanShowPulsingHuns(), printWriter);
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
        C2722x75a3371a c2722x75a3371a = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "logDelayingClockWakeUpAnimation(" + ((LogMessage) obj).getBool1() + ")";
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, c2722x75a3371a, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void maybeClearHardDozeAmountOverrideHidingNotifs() {
        Float f = this.hardDozeAmountOverride;
        if (f != null && f.floatValue() == 1.0f) {
            StatusBarStateController statusBarStateController = this.statusBarStateController;
            boolean z = statusBarStateController.getState() == 1;
            boolean isDozing = statusBarStateController.isDozing();
            boolean bypassEnabled = this.bypassController.getBypassEnabled();
            boolean overrideNotificationsFullyDozingOnKeyguard = this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard();
            boolean z2 = ((z && isDozing) || bypassEnabled || overrideNotificationsFullyDozingOnKeyguard) ? false : true;
            NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
            notificationWakeUpCoordinatorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            C2723x8f6f8990 c2723x8f6f8990 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("maybeClearHardDozeAmountOverrideHidingNotifs() ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, c2723x8f6f8990, null);
            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("willRemove=", z2, " onKeyguard=", z, " dozing=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, isDozing, " bypass=", bypassEnabled, " animating=");
            m69m.append(overrideNotificationsFullyDozingOnKeyguard);
            obtain.setStr1(m69m.toString());
            logBuffer.commit(obtain);
            if (z2) {
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0059  */
    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDozeAmountChanged(float f, float f2) {
        boolean z;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z2 = true;
        if (!(f == 1.0f)) {
            if (!(f == 0.0f)) {
                z = true;
                if (notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
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
                    obtain.setDouble1(f);
                    obtain.setStr2(String.valueOf(f2));
                    logBuffer.commit(obtain);
                }
                this.inputLinearDozeAmount = f;
                this.inputEasedDozeAmount = f2;
                if (this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard()) {
                    z2 = false;
                } else {
                    setHardDozeAmountOverride("Override: animating screen off", true);
                }
                if (!z2 || overrideDozeAmountIfBypass() || clearHardDozeAmountOverride()) {
                    return;
                }
                updateDozeAmount();
            }
        }
        z = false;
        if (notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional) {
        }
        notificationWakeUpCoordinatorLogger.lastOnDozeAmountChangedLogWasFractional = z;
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2 notificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "onDozeAmountChanged(linear=" + logMessage.getDouble1() + ", eased=" + logMessage.getStr2() + ")";
            }
        };
        LogBuffer logBuffer2 = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotificationWakeUpCoordinator", logLevel2, notificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$22, null);
        obtain2.setDouble1(f);
        obtain2.setStr2(String.valueOf(f2));
        logBuffer2.commit(obtain2);
        this.inputLinearDozeAmount = f;
        this.inputEasedDozeAmount = f2;
        if (this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard()) {
        }
        if (!z2) {
            return;
        }
        updateDozeAmount();
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
        Set set = this.mEntrySetToClearWhenFinished;
        if (!z) {
            if (!(this.outputLinearDozeAmount == 0.0f)) {
                if (!(this.mLinearVisibilityAmount == 0.0f)) {
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow != null && expandableNotificationRow.mDismissed) {
                        shouldAnimateVisibility = false;
                    } else if (!this.wakingUp && !this.willWakeUp) {
                        if (expandableNotificationRow != null) {
                            expandableNotificationRow.setHeadsUpAnimatingAway(true);
                        }
                        set.add(notificationEntry);
                    }
                }
            }
        } else if (set.contains(notificationEntry)) {
            set.remove(notificationEntry);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.setHeadsUpAnimatingAway(false);
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
            this.mHeadsUpManager.releaseAllImmediately();
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
                return FontProvider$$ExternalSyntheticOutline0.m32m("onStateChanged(newState=", StatusBarState.toString(logMessage.getInt1()), ") stored=", StatusBarState.toString(logMessage.getInt2()));
            }
        };
        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logOnStateChanged$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
        boolean z = false;
        if (this.state == 0 && i == 0) {
            setHardDozeAmountOverride("Override: Shade->Shade (lock cancelled by unlock)", false);
            this.state = i;
            return;
        }
        if (this.screenOffAnimationController.overrideNotificationsFullyDozingOnKeyguard()) {
            z = true;
            setHardDozeAmountOverride("Override: animating screen off", true);
        }
        if (z) {
            this.state = i;
        } else if (overrideDozeAmountIfBypass()) {
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
        obtain.setBool1(z);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
        this.hardDozeAmountOverride = Float.valueOf(z ? 1.0f : 0.0f);
        this.hardDozeAmountOverrideSource = str;
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
        ofFloat.m309setDuration(z3 ? (long) (500 / 1.5f) : 500L);
        ofFloat.start();
        this.mVisibilityAnimator = ofFloat;
    }

    public final void setNotificationsVisibleForExpansion(boolean z, boolean z2, boolean z3) {
        this.mNotificationsVisibleForExpansion = z;
        updateNotificationVisibility(z2, z3);
        if (z || !this.mNotificationsVisible) {
            return;
        }
        this.mHeadsUpManager.releaseAllImmediately();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005d, code lost:
    
        if ((r8.mLinearVisibilityAmount == 0.0f) != false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setVisibilityAmount(float f) {
        boolean z;
        Iterator it;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        if (!(f == 1.0f)) {
            if (!(f == 0.0f)) {
                z = true;
                if (notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
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
                    obtain.setDouble1(f);
                    logBuffer.commit(obtain);
                }
                this.mLinearVisibilityAmount = f;
                this.mVisibilityAmount = this.mVisibilityInterpolator.getInterpolation(f);
                if (!(this.outputLinearDozeAmount != 0.0f)) {
                }
                Set set = this.mEntrySetToClearWhenFinished;
                it = set.iterator();
                while (it.hasNext()) {
                    ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.setHeadsUpAnimatingAway(false);
                    }
                }
                set.clear();
                updateHideAmount();
            }
        }
        z = false;
        if (notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional) {
        }
        notificationWakeUpCoordinatorLogger.lastSetVisibilityAmountLogWasFractional = z;
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2 notificationWakeUpCoordinatorLogger$logSetVisibilityAmount$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "setVisibilityAmount(" + ((LogMessage) obj).getDouble1() + ")";
            }
        };
        LogBuffer logBuffer2 = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotificationWakeUpCoordinator", logLevel2, notificationWakeUpCoordinatorLogger$logSetVisibilityAmount$22, null);
        obtain2.setDouble1(f);
        logBuffer2.commit(obtain2);
        this.mLinearVisibilityAmount = f;
        this.mVisibilityAmount = this.mVisibilityInterpolator.getInterpolation(f);
        if (!(this.outputLinearDozeAmount != 0.0f)) {
        }
        Set set2 = this.mEntrySetToClearWhenFinished;
        it = set2.iterator();
        while (it.hasNext()) {
        }
        set2.clear();
        updateHideAmount();
    }

    public final void setWakingUp(boolean z, boolean z2) {
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
        obtain.setBool1(z);
        obtain.setBool2(z2);
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
                ExpandableView firstChildWithBackground = notificationStackScrollLayout.getFirstChildWithBackground();
                notificationStackScrollLayout.setPulseHeight(firstChildWithBackground != null ? notificationStackScrollLayout.mKeyguardBypassEnabled ? firstChildWithBackground.getHeadsUpHeightWithoutHeader() : firstChildWithBackground.getCollapsedHeight() : 0.0f);
                int childCount = notificationStackScrollLayout.getChildCount();
                float f = -1.0f;
                boolean z4 = true;
                for (int i = 0; i < childCount; i++) {
                    ExpandableView childAtIndex = notificationStackScrollLayout.getChildAtIndex(i);
                    if (childAtIndex.getVisibility() != 8) {
                        boolean z5 = childAtIndex == notificationStackScrollLayout.mShelf;
                        if ((childAtIndex instanceof ExpandableNotificationRow) || z5) {
                            if (childAtIndex.getVisibility() != 0 || z5) {
                                if (!z4) {
                                    childAtIndex.setTranslationY(f);
                                }
                            } else if (z4) {
                                z4 = false;
                                f = (childAtIndex.getTranslationY() + childAtIndex.mActualHeight) - notificationStackScrollLayout.mShelf.getHeight();
                            }
                        }
                    }
                }
                notificationStackScrollLayout.mDimmedNeedsAnimation = true;
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
                    return AbstractC0866xb1ce8deb.m86m("startDelayedDozeAmountAnimation() alreadyRunning=", ((LogMessage) obj).getBool1());
                }
            }, null);
            obtain2.setBool1(z6);
            logBuffer.commit(obtain2);
            if (z6) {
                return;
            }
            NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 notificationWakeUpCoordinator$Companion$delayedDozeAmount$1 = delayedDozeAmount;
            notificationWakeUpCoordinator$Companion$delayedDozeAmount$1.getClass();
            NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1.setValue(this, 1.0f);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, notificationWakeUpCoordinator$Companion$delayedDozeAmount$1, 0.0f);
            ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR);
            ofFloat.m309setDuration(500L);
            ofFloat.setStartDelay(250L);
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnStart$1
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart$1() {
                    Iterator it = NotificationWakeUpCoordinator.this.wakeUpListeners.iterator();
                    while (it.hasNext()) {
                        ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onDelayedDozeAmountAnimationRunning();
                    }
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd$1(Animator animator) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel() {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat() {
                }
            });
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnEnd$1
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd$1(Animator animator) {
                    NotificationWakeUpCoordinator notificationWakeUpCoordinator = NotificationWakeUpCoordinator.this;
                    notificationWakeUpCoordinator.delayedDozeAmountAnimator = null;
                    Iterator it = notificationWakeUpCoordinator.wakeUpListeners.iterator();
                    while (it.hasNext()) {
                        ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onDelayedDozeAmountAnimationRunning();
                    }
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel() {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat() {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart$1() {
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0044, code lost:
    
        if ((r2 == 1.0f) != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00c6, code lost:
    
        if (r11.allowThrottle != false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00bf, code lost:
    
        r15 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00ae A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00b1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDozeAmount() {
        boolean z;
        boolean z2;
        boolean z3;
        float f;
        AmbientState ambientState;
        Float f2 = this.hardDozeAmountOverride;
        float floatValue = f2 != null ? f2.floatValue() : Math.max(this.inputLinearDozeAmount, this.delayedDozeAmountOverride);
        float f3 = this.outputLinearDozeAmount;
        boolean z4 = !(f3 == floatValue);
        if (!(floatValue == 1.0f)) {
            if (!(floatValue == 0.0f)) {
                if (!(f3 == 0.0f)) {
                }
                notifyAnimationStart(f3 == 1.0f);
            }
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
        if (!(f4 == 1.0f)) {
            if (!(f4 == 0.0f)) {
                z = true;
                if (!(f5 != 1.0f)) {
                    if (!(f5 == 0.0f)) {
                        z2 = true;
                        if ((!z || z2) && notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional == z && notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional == z2 && notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState == state) {
                            z3 = (r15 = notificationWakeUpCoordinatorLogger.lastSetHardOverride) == null ? false : false;
                            if (z3) {
                            }
                        }
                        notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional = z;
                        notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional = z2;
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
                                AppOpItem$$ExternalSyntheticOutline0.m97m(sb, " hardOverride=", str1, " outputLinear=", str2);
                                sb.append(" state=");
                                sb.append(statusBarState);
                                sb.append(" changed=");
                                sb.append(bool1);
                                return sb.toString();
                            }
                        };
                        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2, null);
                        obtain.setDouble1(f4);
                        obtain.setStr1(String.valueOf(f6));
                        obtain.setStr2(String.valueOf(f7));
                        obtain.setStr3(String.valueOf(f5));
                        obtain.setInt1(state);
                        obtain.setBool1(z4);
                        logBuffer.commit(obtain);
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController == null ? null : notificationStackScrollLayoutController;
                        f = this.outputEasedDozeAmount;
                        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
                        ambientState = notificationStackScrollLayout.mAmbientState;
                        if (f != ambientState.mDozeAmount) {
                            ambientState.mDozeAmount = f;
                            if ((f == 0.0f || f == 1.0f) && 100000.0f != ambientState.mPulseHeight) {
                                ambientState.mPulseHeight = 100000.0f;
                                Runnable runnable = ambientState.mOnPulseHeightChangedListener;
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }
                        }
                        notificationStackScrollLayout.updateContinuousBackgroundDrawing();
                        notificationStackScrollLayout.updateStackPosition(false);
                        notificationStackScrollLayout.requestChildrenUpdate();
                        updateHideAmount();
                        if (z4) {
                            if (this.outputLinearDozeAmount == 0.0f) {
                                setNotificationsVisible(false, false, false);
                                setNotificationsVisibleForExpansion(false, false, false);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
                z2 = false;
                if (!z) {
                }
                if ((r15 = notificationWakeUpCoordinatorLogger.lastSetHardOverride) == null) {
                }
                if (z3) {
                }
                notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional = z;
                notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional = z2;
                notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState = state;
                notificationWakeUpCoordinatorLogger.lastSetHardOverride = f6;
                LogLevel logLevel2 = LogLevel.DEBUG;
                NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2 notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2
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
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb, " hardOverride=", str1, " outputLinear=", str2);
                        sb.append(" state=");
                        sb.append(statusBarState);
                        sb.append(" changed=");
                        sb.append(bool1);
                        return sb.toString();
                    }
                };
                LogBuffer logBuffer2 = notificationWakeUpCoordinatorLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("NotificationWakeUpCoordinator", logLevel2, notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$22, null);
                obtain2.setDouble1(f4);
                obtain2.setStr1(String.valueOf(f6));
                obtain2.setStr2(String.valueOf(f7));
                obtain2.setStr3(String.valueOf(f5));
                obtain2.setInt1(state);
                obtain2.setBool1(z4);
                logBuffer2.commit(obtain2);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = this.mStackScrollerController;
                if (notificationStackScrollLayoutController3 == null) {
                }
                f = this.outputEasedDozeAmount;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                ambientState = notificationStackScrollLayout2.mAmbientState;
                if (f != ambientState.mDozeAmount) {
                }
                notificationStackScrollLayout2.updateContinuousBackgroundDrawing();
                notificationStackScrollLayout2.updateStackPosition(false);
                notificationStackScrollLayout2.requestChildrenUpdate();
                updateHideAmount();
                if (z4) {
                }
            }
        }
        z = false;
        if (!(f5 != 1.0f)) {
        }
        z2 = false;
        if (!z) {
        }
        if ((r15 = notificationWakeUpCoordinatorLogger.lastSetHardOverride) == null) {
        }
        if (z3) {
        }
        notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogInputWasFractional = z;
        notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogDelayWasFractional = z2;
        notificationWakeUpCoordinatorLogger.lastSetDozeAmountLogState = state;
        notificationWakeUpCoordinatorLogger.lastSetHardOverride = f6;
        LogLevel logLevel22 = LogLevel.DEBUG;
        NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2 notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$222 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2
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
                AppOpItem$$ExternalSyntheticOutline0.m97m(sb, " hardOverride=", str1, " outputLinear=", str2);
                sb.append(" state=");
                sb.append(statusBarState);
                sb.append(" changed=");
                sb.append(bool1);
                return sb.toString();
            }
        };
        LogBuffer logBuffer22 = notificationWakeUpCoordinatorLogger.buffer;
        LogMessage obtain22 = logBuffer22.obtain("NotificationWakeUpCoordinator", logLevel22, notificationWakeUpCoordinatorLogger$logUpdateDozeAmount$222, null);
        obtain22.setDouble1(f4);
        obtain22.setStr1(String.valueOf(f6));
        obtain22.setStr2(String.valueOf(f7));
        obtain22.setStr3(String.valueOf(f5));
        obtain22.setInt1(state);
        obtain22.setBool1(z4);
        logBuffer22.commit(obtain22);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController32 = this.mStackScrollerController;
        if (notificationStackScrollLayoutController32 == null) {
        }
        f = this.outputEasedDozeAmount;
        NotificationStackScrollLayout notificationStackScrollLayout22 = notificationStackScrollLayoutController2.mView;
        ambientState = notificationStackScrollLayout22.mAmbientState;
        if (f != ambientState.mDozeAmount) {
        }
        notificationStackScrollLayout22.updateContinuousBackgroundDrawing();
        notificationStackScrollLayout22.updateStackPosition(false);
        notificationStackScrollLayout22.requestChildrenUpdate();
        updateHideAmount();
        if (z4) {
        }
    }

    public final void updateHideAmount() {
        boolean z;
        float min = Math.min(1.0f - this.mLinearVisibilityAmount, this.outputLinearDozeAmount);
        float min2 = Math.min(1.0f - this.mVisibilityAmount, this.outputEasedDozeAmount);
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = this.logger;
        boolean z2 = notificationWakeUpCoordinatorLogger.lastSetHideAmount == min;
        boolean z3 = notificationWakeUpCoordinatorLogger.allowThrottle;
        if (!z2 || !z3) {
            notificationWakeUpCoordinatorLogger.lastSetHideAmount = min;
            if (!(min == 1.0f)) {
                if (!(min == 0.0f)) {
                    z = true;
                    if (notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional || !z || !z3) {
                        notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional = z;
                        LogLevel logLevel = LogLevel.DEBUG;
                        NotificationWakeUpCoordinatorLogger$logSetHideAmount$2 notificationWakeUpCoordinatorLogger$logSetHideAmount$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetHideAmount$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return "setHideAmount(" + ((LogMessage) obj).getDouble1() + ")";
                            }
                        };
                        LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetHideAmount$2, null);
                        obtain.setDouble1(min);
                        logBuffer.commit(obtain);
                    }
                }
            }
            z = false;
            if (notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional) {
            }
            notificationWakeUpCoordinatorLogger.lastSetHideAmountLogWasFractional = z;
            LogLevel logLevel2 = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logSetHideAmount$2 notificationWakeUpCoordinatorLogger$logSetHideAmount$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetHideAmount$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "setHideAmount(" + ((LogMessage) obj).getDouble1() + ")";
                }
            };
            LogBuffer logBuffer2 = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotificationWakeUpCoordinator", logLevel2, notificationWakeUpCoordinatorLogger$logSetHideAmount$22, null);
            obtain2.setDouble1(min);
            logBuffer2.commit(obtain2);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        NotificationStackScrollLayout notificationStackScrollLayout = (notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController : null).mView;
        notificationStackScrollLayout.mLinearHideAmount = min;
        notificationStackScrollLayout.mInterpolatedHideAmount = min2;
        boolean isFullyHidden = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean isHiddenAtAll = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (min2 == 1.0f && ambientState.mHideAmount != min2 && 100000.0f != ambientState.mPulseHeight) {
            ambientState.mPulseHeight = 100000.0f;
            Runnable runnable = ambientState.mOnPulseHeightChangedListener;
            if (runnable != null) {
                runnable.run();
            }
        }
        ambientState.mHideAmount = min2;
        boolean isFullyHidden2 = notificationStackScrollLayout.mAmbientState.isFullyHidden();
        boolean isHiddenAtAll2 = notificationStackScrollLayout.mAmbientState.isHiddenAtAll();
        if (isFullyHidden2 != isFullyHidden) {
            notificationStackScrollLayout.updateVisibility();
            notificationStackScrollLayout.resetAllSwipeState();
        }
        if (!isHiddenAtAll && isHiddenAtAll2) {
            notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView(true, true);
        }
        if (isFullyHidden2 != isFullyHidden || isHiddenAtAll != isHiddenAtAll2) {
            notificationStackScrollLayout.invalidateOutline();
        }
        notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
        notificationStackScrollLayout.updateBackgroundDimming();
        notificationStackScrollLayout.requestChildrenUpdate();
        notificationStackScrollLayout.updateOwnTranslationZ();
        boolean z4 = min == 1.0f;
        if (this.notificationsFullyHidden != z4) {
            this.notificationsFullyHidden = z4;
            Iterator it = this.wakeUpListeners.iterator();
            while (it.hasNext()) {
                ((WakeUpListener) it.next()).onFullyHiddenChanged(z4);
            }
        }
    }

    public final void updateNotificationVisibility(boolean z, boolean z2) {
        boolean z3 = (this.mNotificationsVisibleForExpansion || (this.mHeadsUpManager.mAlertEntries.isEmpty() ^ true)) && getCanShowPulsingHuns();
        if (!z3 && this.mNotificationsVisible && (this.wakingUp || this.willWakeUp)) {
            if (!(this.outputLinearDozeAmount == 0.0f)) {
                return;
            }
        }
        setNotificationsVisible(z3, z, z2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface WakeUpListener {
        void onFullyHiddenChanged(boolean z);

        default void onPulseExpansionChanged(boolean z) {
        }

        default void onDelayedDozeAmountAnimationRunning() {
        }
    }

    public static /* synthetic */ void getDozeAmountInterpolator$annotations() {
    }

    public static /* synthetic */ void getStatusBarState$annotations() {
    }
}
