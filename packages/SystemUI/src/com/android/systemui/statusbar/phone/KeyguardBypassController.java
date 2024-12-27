package com.android.systemui.statusbar.phone;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Trace;
import com.android.app.tracing.ListenersTracing;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBypassController implements Dumpable, StackScrollAlgorithm.BypassController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public boolean bouncerShowing;
    public final boolean bypassEnabled;
    public final int bypassOverride;
    public final int configFaceAuthSupportedPosture;
    public final boolean hasFaceFeature;
    public boolean isPulseExpanding;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public boolean launchingAffordance;
    public PendingUnlock pendingUnlock;
    public int postureState;
    public boolean qsExpanded;
    private final SettingsHelper settingsHelper;
    public final Lazy shadeInteractorLazy;
    public final StatusBarStateController statusBarStateController;
    public BiometricUnlockController unlockController;
    public final List listeners = new ArrayList();
    public final KeyguardBypassController$faceAuthEnabledChangedCallback$1 faceAuthEnabledChangedCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onFaceEnrolledChanged() {
            int i = KeyguardBypassController.$r8$clinit;
            KeyguardBypassController.this.notifyListeners$1$1();
        }
    };
    public final boolean lockStayEnabled = true;
    public final boolean fpLockStayEnabled = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnBypassStateChangedListener {
        void onBypassStateChanged(boolean z);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PendingUnlock {
        public final boolean isStrongBiometric;
        public final BiometricSourceType pendingUnlockType;

        public PendingUnlock(BiometricSourceType biometricSourceType, boolean z) {
            this.pendingUnlockType = biometricSourceType;
            this.isStrongBiometric = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingUnlock)) {
                return false;
            }
            PendingUnlock pendingUnlock = (PendingUnlock) obj;
            return this.pendingUnlockType == pendingUnlock.pendingUnlockType && this.isStrongBiometric == pendingUnlock.isStrongBiometric;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isStrongBiometric) + (this.pendingUnlockType.hashCode() * 31);
        }

        public final String toString() {
            return "PendingUnlock(pendingUnlockType=" + this.pendingUnlockType + ", isStrongBiometric=" + this.isStrongBiometric + ")";
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1] */
    public KeyguardBypassController(Resources resources, PackageManager packageManager, CoroutineScope coroutineScope, TunerService tunerService, StatusBarStateController statusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, Lazy lazy, DevicePostureController devicePostureController, KeyguardTransitionInteractor keyguardTransitionInteractor, DumpManager dumpManager, SettingsHelper settingsHelper) {
        this.applicationScope = coroutineScope;
        this.statusBarStateController = statusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.shadeInteractorLazy = lazy;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.settingsHelper = settingsHelper;
        this.bypassOverride = resources.getInteger(R.integer.config_face_unlock_bypass_override);
        int integer = resources.getInteger(R.integer.config_face_auth_supported_posture);
        this.configFaceAuthSupportedPosture = integer;
        boolean hasSystemFeature = packageManager.hasSystemFeature("android.hardware.biometrics.face");
        this.hasFaceFeature = hasSystemFeature;
        if (hasSystemFeature) {
            if (integer != 0) {
                ((DevicePostureControllerImpl) devicePostureController).addCallback(new DevicePostureController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.1
                    @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                    public final void onPostureChanged(int i) {
                        KeyguardBypassController keyguardBypassController = KeyguardBypassController.this;
                        if (keyguardBypassController.postureState != i) {
                            keyguardBypassController.postureState = i;
                            keyguardBypassController.notifyListeners$1$1();
                        }
                    }
                });
            }
            dumpManager.registerNormalDumpable("KeyguardBypassController", this);
            statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.2
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStateChanged(int i) {
                    if (i != 1) {
                        KeyguardBypassController.this.pendingUnlock = null;
                    }
                }
            });
            this.bypassEnabled = false;
            notifyListeners$1$1();
            ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).addUserChangedListener(new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.4
                @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
                public final void onUserChanged(int i) {
                    KeyguardBypassController.this.pendingUnlock = null;
                }
            });
        }
    }

    public final boolean canBypass() {
        if (getBypassEnabled()) {
            return this.bouncerShowing || this.keyguardTransitionInteractor.getCurrentState() == KeyguardState.ALTERNATE_BOUNCER || !(this.statusBarStateController.getState() != 1 || this.launchingAffordance || this.isPulseExpanding || this.qsExpanded);
        }
        return false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardBypassController:");
        PendingUnlock pendingUnlock = this.pendingUnlock;
        if (pendingUnlock != null) {
            Intrinsics.checkNotNull(pendingUnlock);
            printWriter.println("  mPendingUnlock.pendingUnlockType: " + pendingUnlock.pendingUnlockType);
            PendingUnlock pendingUnlock2 = this.pendingUnlock;
            Intrinsics.checkNotNull(pendingUnlock2);
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mPendingUnlock.isStrongBiometric: "), pendingUnlock2.isStrongBiometric, printWriter);
        } else {
            printWriter.println("  mPendingUnlock: " + pendingUnlock);
        }
        printWriter.println("  bypassEnabled: " + getBypassEnabled());
        printWriter.println("  lockStayEnabled: " + getLockStayEnabled());
        printWriter.println(" fplockstayEnabled: " + getFpLockStayEnabled());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  canBypass: ", canBypass(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  bouncerShowing: ", this.bouncerShowing, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  altBouncerShowing: ", this.keyguardTransitionInteractor.getCurrentState() == KeyguardState.ALTERNATE_BOUNCER, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  isPulseExpanding: ", this.isPulseExpanding, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  launchingAffordance: ", this.launchingAffordance, printWriter);
        printWriter.println("  qSExpanded: " + this.qsExpanded);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  hasFaceFeature: "), this.hasFaceFeature, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  postureState: ", this.postureState, printWriter);
    }

    public final boolean getBypassEnabled() {
        int i = this.bypassOverride;
        if (!(i != 1 ? i != 2 ? this.bypassEnabled : false : true) || !((KeyguardStateControllerImpl) this.keyguardStateController).mFaceEnrolledAndEnabled) {
            return false;
        }
        int i2 = this.configFaceAuthSupportedPosture;
        return i2 == 0 || this.postureState == i2;
    }

    public final boolean getFpLockStayEnabled() {
        return this.fpLockStayEnabled && ((KeyguardStateControllerImpl) this.keyguardStateController).mFingerprintEnabled && this.settingsHelper.isEnabledFingerprintStayOnLock();
    }

    public final boolean getLockStayEnabled() {
        return this.lockStayEnabled && ((KeyguardStateControllerImpl) this.keyguardStateController).mFaceEnrolledAndEnabled && this.settingsHelper.isEnabledFaceStayOnLock();
    }

    public final void maybePerformPendingUnlock() {
        PendingUnlock pendingUnlock = this.pendingUnlock;
        if (pendingUnlock != null) {
            Intrinsics.checkNotNull(pendingUnlock);
            BiometricSourceType biometricSourceType = pendingUnlock.pendingUnlockType;
            PendingUnlock pendingUnlock2 = this.pendingUnlock;
            Intrinsics.checkNotNull(pendingUnlock2);
            if (onBiometricAuthenticated(biometricSourceType, pendingUnlock2.isStrongBiometric)) {
                BiometricUnlockController biometricUnlockController = this.unlockController;
                if (biometricUnlockController == null) {
                    biometricUnlockController = null;
                }
                PendingUnlock pendingUnlock3 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock3);
                BiometricSourceType biometricSourceType2 = pendingUnlock3.pendingUnlockType;
                PendingUnlock pendingUnlock4 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock4);
                biometricUnlockController.startWakeAndUnlock(biometricSourceType2, pendingUnlock4.isStrongBiometric);
                this.pendingUnlock = null;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.KeyguardBypassController$notifyListeners$$inlined$forEachTraced$1] */
    public final void notifyListeners$1$1() {
        ListenersTracing listenersTracing = ListenersTracing.INSTANCE;
        for (final Object obj : this.listeners) {
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("KeyguardBypassController#".concat(((Class) new PropertyReference0Impl(obj) { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController$notifyListeners$$inlined$forEachTraced$1
                    @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                    public final Object get() {
                        return this.receiver.getClass();
                    }
                }.get()).getName()));
            }
            try {
                ((OnBypassStateChangedListener) obj).onBypassStateChanged(getBypassEnabled());
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    public final boolean onBiometricAuthenticated(BiometricSourceType biometricSourceType, boolean z) {
        if (biometricSourceType != BiometricSourceType.FACE || !getBypassEnabled()) {
            return true;
        }
        boolean canBypass = canBypass();
        if (!canBypass && (this.isPulseExpanding || this.qsExpanded)) {
            this.pendingUnlock = new PendingUnlock(biometricSourceType, z);
        }
        return canBypass;
    }

    public final void registerOnBypassStateChangedListener(OnBypassStateChangedListener onBypassStateChangedListener) {
        boolean isEmpty = this.listeners.isEmpty();
        this.listeners.add(onBypassStateChangedListener);
        if (isEmpty) {
            ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(this.faceAuthEnabledChangedCallback);
        }
    }

    public final void unregisterOnBypassStateChangedListener(OnBypassStateChangedListener onBypassStateChangedListener) {
        ((ArrayList) this.listeners).remove(onBypassStateChangedListener);
        if (((ArrayList) this.listeners).isEmpty()) {
            ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this.faceAuthEnabledChangedCallback);
        }
    }
}
