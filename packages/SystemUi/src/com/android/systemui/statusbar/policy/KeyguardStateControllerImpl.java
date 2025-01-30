package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.os.Trace;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DeviceState;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardStateControllerImpl implements KeyguardStateController, Dumpable {
    public final ArrayList mCallbacks = new ArrayList();
    public boolean mCanDismissLockScreen;
    public final Context mContext;
    public float mDismissAmount;
    public boolean mDismissingFromTouch;
    public boolean mFaceAuthEnabled;
    public boolean mFlingingToDismissKeyguard;
    public boolean mFlingingToDismissKeyguardDuringSwipeGesture;
    public boolean mIsSwipeBouncer;
    public boolean mKeyguardFadingAway;
    public long mKeyguardFadingAwayDelay;
    public long mKeyguardFadingAwayDuration;
    public boolean mKeyguardGoingAway;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public boolean mLaunchTransitionFadingAway;
    public final KeyguardUpdateMonitorLogger mLogger;
    public boolean mOccluded;
    public boolean mPrimaryBouncerShowing;
    public boolean mSecure;
    public boolean mShowing;
    public boolean mSnappingKeyguardBackAfterSwipe;
    public boolean mTrustManaged;
    public boolean mTrusted;
    public final Lazy mUnlockAnimationControllerLazy;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UpdateMonitorCallback extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ UpdateMonitorCallback(KeyguardStateControllerImpl keyguardStateControllerImpl, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            Trace.beginSection("KeyguardUpdateMonitorCallback#onBiometricAuthenticated");
            KeyguardStateControllerImpl keyguardStateControllerImpl = KeyguardStateControllerImpl.this;
            if (keyguardStateControllerImpl.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                keyguardStateControllerImpl.update(false);
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricsCleared() {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEnabledTrustAgentsChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedWakingUp() {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = KeyguardStateControllerImpl.this;
            keyguardStateControllerImpl.update(false);
            Trace.beginSection("KeyguardStateController#notifyKeyguardChanged");
            new ArrayList(keyguardStateControllerImpl.mCallbacks).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(4));
            Trace.endSection();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustManagedChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        private UpdateMonitorCallback() {
        }
    }

    public KeyguardStateControllerImpl(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, Lazy lazy, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, DumpManager dumpManager) {
        UpdateMonitorCallback updateMonitorCallback = new UpdateMonitorCallback(this, 0);
        this.mKeyguardUpdateMonitorCallback = updateMonitorCallback;
        this.mDismissAmount = 0.0f;
        this.mDismissingFromTouch = false;
        this.mFlingingToDismissKeyguard = false;
        this.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        this.mSnappingKeyguardBackAfterSwipe = false;
        this.mIsSwipeBouncer = false;
        this.mContext = context;
        this.mLogger = keyguardUpdateMonitorLogger;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(updateMonitorCallback);
        this.mUnlockAnimationControllerLazy = lazy;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "KeyguardStateControllerImpl", this);
        update(true);
        boolean z = Build.IS_DEBUGGABLE;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        ArrayList arrayList = this.mCallbacks;
        if (arrayList.contains(callback)) {
            return;
        }
        arrayList.add(callback);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "KeyguardStateController:", "  mShowing: "), this.mShowing, printWriter, "  mOccluded: "), this.mOccluded, printWriter, "  mSecure: "), this.mSecure, printWriter, "  mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, "  mTrustManaged: "), this.mTrustManaged, printWriter, "  mTrusted: ");
        m64m.append(this.mTrusted);
        printWriter.println(m64m.toString());
        printWriter.println("  mDebugUnlocked: false");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mFaceAuthEnabled: "), this.mFaceAuthEnabled, printWriter, "  isKeyguardFadingAway: "), this.mKeyguardFadingAway, printWriter, "  isKeyguardGoingAway: "), this.mKeyguardGoingAway, printWriter, "  isLaunchTransitionFadingAway: "), this.mLaunchTransitionFadingAway, printWriter);
    }

    public final boolean isKeyguardScreenRotationAllowed() {
        return DeviceState.shouldEnableKeyguardScreenRotation(this.mContext);
    }

    public final void notifyKeyguardGoingAway(boolean z) {
        if (this.mKeyguardGoingAway != z) {
            Trace.traceCounter(4096L, "keyguardGoingAway", z ? 1 : 0);
            this.mKeyguardGoingAway = z;
            new ArrayList(this.mCallbacks).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(1));
        }
    }

    public final void notifyKeyguardState(boolean z, boolean z2) {
        if (this.mShowing == z && this.mOccluded == z2) {
            return;
        }
        this.mShowing = z;
        this.mOccluded = z2;
        this.mKeyguardUpdateMonitor.setKeyguardShowing(z, z2);
        Trace.instantForTrack(4096L, "UI Events", "Keyguard showing: " + z + " occluded: " + z2);
        Trace.beginSection("KeyguardStateController#notifyKeyguardChanged");
        ArrayList arrayList = this.mCallbacks;
        new ArrayList(arrayList).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(4));
        Trace.endSection();
        this.mDismissAmount = z ? 0.0f : 1.0f;
        this.mDismissingFromTouch = false;
        new ArrayList(arrayList).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(2));
    }

    public final void notifyPrimaryBouncerShowing(boolean z) {
        if (this.mPrimaryBouncerShowing != z) {
            this.mPrimaryBouncerShowing = z;
            new ArrayList(this.mCallbacks).stream().filter(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0()).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(0));
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.remove(callback);
    }

    public final void setKeyguardFadingAway(boolean z) {
        if (this.mKeyguardFadingAway != z) {
            Trace.traceCounter(4096L, "keyguardFadingAway", z ? 1 : 0);
            this.mKeyguardFadingAway = z;
            ArrayList arrayList = new ArrayList(this.mCallbacks);
            for (int i = 0; i < arrayList.size(); i++) {
                ((KeyguardStateController.Callback) arrayList.get(i)).onKeyguardFadingAwayChanged();
            }
        }
    }

    public void update(boolean z) {
        boolean z2;
        Trace.beginSection("KeyguardStateController#update");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean isSecure = keyguardUpdateMonitor.isSecure(currentUser);
        if (!isSecure || keyguardUpdateMonitor.getUserCanSkipBouncer(currentUser)) {
            z2 = true;
        } else {
            boolean z3 = Build.IS_DEBUGGABLE;
            z2 = false;
        }
        boolean userTrustIsManaged = keyguardUpdateMonitor.getUserTrustIsManaged(currentUser);
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(currentUser);
        boolean isFaceAuthEnabledForUser = keyguardUpdateMonitor.isFaceAuthEnabledForUser(currentUser);
        if (((isSecure == this.mSecure && z2 == this.mCanDismissLockScreen && userTrustIsManaged == this.mTrustManaged && this.mTrusted == userHasTrust && this.mFaceAuthEnabled == isFaceAuthEnabledForUser) ? false : true) || z) {
            this.mSecure = isSecure;
            this.mCanDismissLockScreen = z2;
            this.mTrusted = userHasTrust;
            this.mTrustManaged = userTrustIsManaged;
            this.mFaceAuthEnabled = isFaceAuthEnabledForUser;
            this.mLogger.logKeyguardStateUpdate(isSecure, z2, userHasTrust, userTrustIsManaged);
            Trace.beginSection("KeyguardStateController#notifyUnlockedChanged");
            new ArrayList(this.mCallbacks).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(5));
            Trace.endSection();
        }
        Trace.endSection();
    }
}
