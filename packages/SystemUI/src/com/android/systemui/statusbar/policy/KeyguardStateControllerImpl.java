package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.os.Trace;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardStateControllerImpl implements KeyguardStateController {
    public final ConcurrentHashMap.KeySetView mCallbacks = ConcurrentHashMap.newKeySet();
    public boolean mCanDismissLockScreen;
    public final Context mContext;
    public float mDismissAmount;
    public boolean mFaceEnrolledAndEnabled;
    public boolean mFingerprintEnabled;
    public boolean mFlingingToDismissKeyguard;
    public boolean mFlingingToDismissKeyguardDuringSwipeGesture;
    public boolean mIsSwipeBouncer;
    public boolean mKeyguardFadingAway;
    public long mKeyguardFadingAwayDelay;
    public long mKeyguardFadingAwayDuration;
    public boolean mKeyguardGoingAway;
    public final Lazy mKeyguardInteractorLazy;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KeyguardUpdateMonitorLogger mLogger;
    public boolean mOccluded;
    public boolean mPrimaryBouncerShowing;
    public boolean mSecure;
    public boolean mShowing;
    public boolean mSnappingKeyguardBackAfterSwipe;
    public boolean mTrustManaged;
    public boolean mTrusted;
    public final Lazy mUnlockAnimationControllerLazy;
    public final SelectedUserInteractor mUserInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class UpdateMonitorCallback extends KeyguardUpdateMonitorCallback {
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
        public final void onBiometricEnrollmentStateChanged(BiometricSourceType biometricSourceType) {
            if (biometricSourceType == BiometricSourceType.FACE) {
                KeyguardStateControllerImpl keyguardStateControllerImpl = KeyguardStateControllerImpl.this;
                keyguardStateControllerImpl.update(false);
                keyguardStateControllerImpl.getClass();
                keyguardStateControllerImpl.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(6));
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEnabledTrustAgentsChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFacesCleared() {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFingerprintsCleared() {
            KeyguardStateControllerImpl.this.update(false);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onForceIsDismissibleChanged(boolean z) {
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
            keyguardStateControllerImpl.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(2));
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

    public KeyguardStateControllerImpl(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, Lazy lazy, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, DumpManager dumpManager, Lazy lazy2, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        UpdateMonitorCallback updateMonitorCallback = new UpdateMonitorCallback(this, 0);
        this.mKeyguardUpdateMonitorCallback = updateMonitorCallback;
        this.mDismissAmount = 0.0f;
        this.mFlingingToDismissKeyguard = false;
        this.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        this.mSnappingKeyguardBackAfterSwipe = false;
        this.mIsSwipeBouncer = false;
        this.mFingerprintEnabled = false;
        this.mContext = context;
        this.mLogger = keyguardUpdateMonitorLogger;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mUserInteractor = selectedUserInteractor;
        keyguardUpdateMonitor.registerCallback(updateMonitorCallback);
        this.mUnlockAnimationControllerLazy = lazy;
        this.mKeyguardInteractorLazy = lazy2;
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
        update(true);
        boolean z = Build.IS_DEBUGGABLE;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.add(callback);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardStateController:", "  mShowing: "), this.mShowing, printWriter, "  mOccluded: "), this.mOccluded, printWriter, "  mSecure: "), this.mSecure, printWriter, "  mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, "  mTrustManaged: "), this.mTrustManaged, printWriter, "  mTrusted: ");
        m.append(this.mTrusted);
        printWriter.println(m.toString());
        printWriter.println("  mDebugUnlocked: false");
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mFaceEnrolled: "), this.mFaceEnrolledAndEnabled, printWriter, "  mFingerprintEnabled: "), this.mFingerprintEnabled, printWriter, "  isKeyguardFadingAway: "), this.mKeyguardFadingAway, printWriter, "  isKeyguardGoingAway: ");
        m2.append(this.mKeyguardGoingAway);
        printWriter.println(m2.toString());
        printWriter.println("  isLaunchTransitionFadingAway: false");
    }

    public final void invokeForEachCallback(Consumer consumer) {
        this.mCallbacks.stream().filter(new KeyguardStateControllerImpl$$ExternalSyntheticLambda6()).forEach(consumer);
    }

    public final boolean isShownSwipeBouncer() {
        return this.mIsSwipeBouncer && this.mCanDismissLockScreen && this.mShowing;
    }

    public final void notifyKeyguardGoingAway(boolean z) {
        if (this.mKeyguardGoingAway != z) {
            Trace.traceCounter(4096L, "keyguard##GoingAway", z ? 1 : 0);
            this.mKeyguardGoingAway = z;
            ((KeyguardRepositoryImpl) ((KeyguardInteractor) this.mKeyguardInteractorLazy.get()).repository).isKeyguardGoingAway.updateState(null, Boolean.valueOf(z));
            invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(5));
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
        invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(2));
        Trace.endSection();
        this.mDismissAmount = z ? 0.0f : 1.0f;
        invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(4));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.remove(callback);
    }

    public void update(boolean z) {
        boolean z2;
        Trace.beginSection("KeyguardStateController#update");
        int selectedUserId = this.mUserInteractor.getSelectedUserId();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean isSecure = keyguardUpdateMonitor.isSecure(selectedUserId);
        if (!isSecure || keyguardUpdateMonitor.getUserCanSkipBouncer(selectedUserId)) {
            z2 = true;
        } else {
            boolean z3 = Build.IS_DEBUGGABLE;
            z2 = false;
        }
        boolean userTrustIsManaged = keyguardUpdateMonitor.getUserTrustIsManaged(selectedUserId);
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(selectedUserId);
        boolean isFaceEnabledAndEnrolled = keyguardUpdateMonitor.isFaceEnabledAndEnrolled();
        boolean isFingerprintOptionEnabled = keyguardUpdateMonitor.isFingerprintOptionEnabled();
        if (isSecure != this.mSecure || z2 != this.mCanDismissLockScreen || userTrustIsManaged != this.mTrustManaged || this.mTrusted != userHasTrust || this.mFaceEnrolledAndEnabled != isFaceEnabledAndEnrolled || this.mFingerprintEnabled != isFingerprintOptionEnabled || z) {
            this.mSecure = isSecure;
            this.mCanDismissLockScreen = z2;
            this.mTrusted = userHasTrust;
            this.mTrustManaged = userTrustIsManaged;
            this.mFaceEnrolledAndEnabled = isFaceEnabledAndEnrolled;
            this.mFingerprintEnabled = isFingerprintOptionEnabled;
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(19);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("KeyguardState", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.bool1 = isSecure;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = userHasTrust;
            logMessageImpl.bool4 = userTrustIsManaged;
            logBuffer.commit(obtain);
            Trace.beginSection("KeyguardStateController#notifyUnlockedChanged");
            invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(3));
            Trace.endSection();
        }
        Trace.endSection();
    }
}
