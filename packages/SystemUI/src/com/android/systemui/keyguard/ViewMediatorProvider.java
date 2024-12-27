package com.android.systemui.keyguard;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ViewMediatorProvider {
    public final Function0 adjustStatusBarLocked;
    public final Function0 alarmManager;
    public final Function0 cancelKeyguardExitAnimMsg;
    public final Function0 dismissMsg;
    public final Function0 doKeyguardLaterLocked;
    public final Function1 doKeyguardLocked;
    public final Function0 getDelayedShowingSequence;
    public final Function1 getLockTimeout;
    public final Function0 getStateCallbackCount;
    public final Function0 getSurfaceBehindRemoteAnimationFinishedCallback;
    public final Function0 handleHide;
    public final Function0 handler;
    public final Function0 hasPendingLock;
    public final Function0 hideLocked;
    public final Function0 hideMsg;
    public final Function0 increaseDelayedShowingSeq;
    public final Function1 initAlphaForAnimationTargets;
    public final Function0 isBootCompleted;
    public final Function0 isExternallyEnabled;
    public final Function0 isKeyguardDonePending;
    public final Function0 isShowing;
    public final Function0 isWakeAndUnlocking;
    public final Function0 keyguardDOnePendingTimeoutMsg;
    public final Function0 keyguardDoneDrawingMsg;
    public final Function0 keyguardDoneMsg;
    public final Function0 keyguardTimeoutMsg;
    public final Function0 lock;
    public final Function0 notifyFinishedGoingToSleepMsg;
    public final Function0 notifyStartedGoingToSleepMsg;
    public final Function0 notifyStartedWakingUoMsg;
    public final Function1 playSound;
    public final Function0 resetMsg;
    public final Function0 resetPendingLock;
    public final Function0 resetPendingReset;
    public final Function0 resetStateLocked;
    public final Function0 resetSurfaceBehindRemoteAnimationFinishedCallback;
    public final Function1 setHiding;
    public final Function0 setOccludedMsg;
    public final Function1 setRemoteAnimationTarget;
    public final Function2 setShowingLocked;
    public final Function1 setUnoccludeFinishedCallback;
    public final Function0 showKeyguardWakeLock;
    public final Function0 showMsg;
    public final Function0 startKeyguardExitAnimMsg;
    public final Function0 systemReadyMsg;
    public final Function0 tryKeyguardDone;
    public final Function1 updatePhoneState;
    public final Function0 userPresentIntent;

    public ViewMediatorProvider(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06, Function0 function07, Function0 function08, Function0 function09, Function0 function010, Function0 function011, Function0 function012, Function0 function013, Function0 function014, Function0 function015, Function0 function016, Function0 function017, Function0 function018, Function0 function019, Function0 function020, Function0 function021, Function0 function022, Function0 function023, Function0 function024, Function0 function025, Function0 function026, Function0 function027, Function0 function028, Function0 function029, Function0 function030, Function0 function031, Function0 function032, Function0 function033, Function0 function034, Function0 function035, Function0 function036, Function0 function037, Function2 function2, Function1 function1, Function1 function12, Function0 function038, Function0 function039, Function1 function13, Function0 function040, Function0 function041, Function1 function14, Function1 function15, Function1 function16, Function1 function17, Function1 function18) {
        this.showMsg = function0;
        this.hideMsg = function02;
        this.resetMsg = function03;
        this.notifyFinishedGoingToSleepMsg = function04;
        this.keyguardDoneMsg = function05;
        this.keyguardDoneDrawingMsg = function06;
        this.setOccludedMsg = function07;
        this.keyguardTimeoutMsg = function08;
        this.dismissMsg = function09;
        this.startKeyguardExitAnimMsg = function010;
        this.keyguardDOnePendingTimeoutMsg = function011;
        this.notifyStartedWakingUoMsg = function012;
        this.notifyStartedGoingToSleepMsg = function013;
        this.systemReadyMsg = function014;
        this.cancelKeyguardExitAnimMsg = function015;
        this.lock = function016;
        this.handler = function017;
        this.alarmManager = function018;
        this.showKeyguardWakeLock = function019;
        this.userPresentIntent = function020;
        this.isWakeAndUnlocking = function021;
        this.hasPendingLock = function022;
        this.isShowing = function023;
        this.isExternallyEnabled = function025;
        this.isBootCompleted = function027;
        this.isKeyguardDonePending = function028;
        this.getDelayedShowingSequence = function029;
        this.getSurfaceBehindRemoteAnimationFinishedCallback = function030;
        this.resetSurfaceBehindRemoteAnimationFinishedCallback = function031;
        this.resetStateLocked = function032;
        this.adjustStatusBarLocked = function033;
        this.doKeyguardLaterLocked = function034;
        this.handleHide = function035;
        this.hideLocked = function036;
        this.tryKeyguardDone = function037;
        this.setShowingLocked = function2;
        this.doKeyguardLocked = function1;
        this.playSound = function12;
        this.resetPendingLock = function038;
        this.resetPendingReset = function039;
        this.setHiding = function13;
        this.increaseDelayedShowingSeq = function040;
        this.getStateCallbackCount = function041;
        this.getLockTimeout = function14;
        this.updatePhoneState = function15;
        this.initAlphaForAnimationTargets = function16;
        this.setRemoteAnimationTarget = function17;
        this.setUnoccludeFinishedCallback = function18;
    }
}
