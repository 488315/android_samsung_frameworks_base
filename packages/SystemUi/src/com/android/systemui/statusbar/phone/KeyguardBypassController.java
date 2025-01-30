package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardBypassController implements Dumpable, StackScrollAlgorithm.BypassController {
    public boolean altBouncerShowing;
    public boolean bouncerShowing;
    public boolean bypassEnabled;
    public final int bypassOverride;
    public final int configFaceAuthSupportedPosture;
    public final KeyguardBypassController$faceAuthEnabledChangedCallback$1 faceAuthEnabledChangedCallback;
    public final boolean hasFaceFeature;
    public boolean isPulseExpanding;
    public boolean launchingAffordance;
    public final List listeners = new ArrayList();
    public final boolean lockStayEnabled;
    public final KeyguardStateController mKeyguardStateController;
    public PendingUnlock pendingUnlock;
    public int postureState;
    public boolean qsExpanded;
    public final StatusBarStateController statusBarStateController;
    public BiometricUnlockController unlockController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnBypassStateChangedListener {
        void onBypassStateChanged(boolean z);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = this.pendingUnlockType.hashCode() * 31;
            boolean z = this.isStrongBiometric;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public final String toString() {
            return "PendingUnlock(pendingUnlockType=" + this.pendingUnlockType + ", isStrongBiometric=" + this.isStrongBiometric + ")";
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1] */
    public KeyguardBypassController(Context context, TunerService tunerService, StatusBarStateController statusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, ShadeExpansionStateManager shadeExpansionStateManager, DevicePostureController devicePostureController, DumpManager dumpManager) {
        new DevicePostureController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController$postureCallback$1
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                KeyguardBypassController keyguardBypassController = KeyguardBypassController.this;
                if (keyguardBypassController.postureState != i) {
                    keyguardBypassController.postureState = i;
                    keyguardBypassController.notifyListeners();
                }
            }
        };
        this.faceAuthEnabledChangedCallback = new KeyguardStateController.Callback(this) { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1
        };
        this.lockStayEnabled = true;
        this.mKeyguardStateController = keyguardStateController;
        this.statusBarStateController = statusBarStateController;
        this.bypassOverride = context.getResources().getInteger(R.integer.config_face_unlock_bypass_override);
        int integer = context.getResources().getInteger(R.integer.config_face_auth_supported_posture);
        this.configFaceAuthSupportedPosture = integer;
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
        this.hasFaceFeature = hasSystemFeature;
        if (hasSystemFeature) {
            if (integer != 0) {
                ((DevicePostureControllerImpl) devicePostureController).addCallback(new DevicePostureController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.1
                    @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                    public final void onPostureChanged(int i) {
                        KeyguardBypassController keyguardBypassController = KeyguardBypassController.this;
                        if (keyguardBypassController.postureState != i) {
                            keyguardBypassController.postureState = i;
                            keyguardBypassController.notifyListeners();
                        }
                    }
                });
            }
            DumpManager.registerDumpable$default(dumpManager, "KeyguardBypassController", this);
            statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.2
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStateChanged(int i) {
                    if (i != 1) {
                        KeyguardBypassController.this.pendingUnlock = null;
                    }
                }
            });
            shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.3
                @Override // com.android.systemui.shade.ShadeQsExpansionListener
                public final void onQsExpansionChanged(boolean z) {
                    KeyguardBypassController keyguardBypassController = KeyguardBypassController.this;
                    boolean z2 = keyguardBypassController.qsExpanded != z;
                    keyguardBypassController.qsExpanded = z;
                    if (!z2 || z) {
                        return;
                    }
                    keyguardBypassController.maybePerformPendingUnlock();
                }
            });
            this.bypassEnabled = false;
            notifyListeners();
            ((ArrayList) ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mListeners).add(new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBypassController.5
                @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
                public final void onUserChanged(int i) {
                    KeyguardBypassController.this.pendingUnlock = null;
                }
            });
        }
    }

    public final boolean canBypass() {
        if (getBypassEnabled()) {
            return this.bouncerShowing || this.altBouncerShowing || !(this.statusBarStateController.getState() != 1 || this.launchingAffordance || this.isPulseExpanding || this.qsExpanded);
        }
        return false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardBypassController:");
        PendingUnlock pendingUnlock = this.pendingUnlock;
        if (pendingUnlock != null) {
            printWriter.println("  mPendingUnlock.pendingUnlockType: " + pendingUnlock.pendingUnlockType);
            PendingUnlock pendingUnlock2 = this.pendingUnlock;
            Intrinsics.checkNotNull(pendingUnlock2);
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("  mPendingUnlock.isStrongBiometric: "), pendingUnlock2.isStrongBiometric, printWriter);
        } else {
            printWriter.println("  mPendingUnlock: " + pendingUnlock);
        }
        printWriter.println("  bypassEnabled: " + getBypassEnabled());
        printWriter.println("  lockStayEnabled: " + getLockStayEnabled());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  canBypass: ", canBypass(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  bouncerShowing: ", this.bouncerShowing, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  altBouncerShowing: ", this.altBouncerShowing, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  isPulseExpanding: ", this.isPulseExpanding, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  launchingAffordance: ", this.launchingAffordance, printWriter);
        printWriter.println("  qSExpanded: " + this.qsExpanded);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("  hasFaceFeature: "), this.hasFaceFeature, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  postureState: ", this.postureState, printWriter);
    }

    public final boolean getBypassEnabled() {
        int i = this.bypassOverride;
        if (!(i != 1 ? i != 2 ? this.bypassEnabled : false : true) || !((KeyguardStateControllerImpl) this.mKeyguardStateController).mFaceAuthEnabled) {
            return false;
        }
        int i2 = this.configFaceAuthSupportedPosture;
        return i2 == 0 || this.postureState == i2;
    }

    public final boolean getLockStayEnabled() {
        return this.lockStayEnabled && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mFaceAuthEnabled && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isEnabledFaceStayOnLock();
    }

    public final void maybePerformPendingUnlock() {
        PendingUnlock pendingUnlock = this.pendingUnlock;
        if (pendingUnlock != null) {
            Intrinsics.checkNotNull(pendingUnlock);
            if (onBiometricAuthenticated(pendingUnlock.pendingUnlockType, pendingUnlock.isStrongBiometric)) {
                BiometricUnlockController biometricUnlockController = this.unlockController;
                if (biometricUnlockController == null) {
                    biometricUnlockController = null;
                }
                PendingUnlock pendingUnlock2 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock2);
                PendingUnlock pendingUnlock3 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock3);
                biometricUnlockController.startWakeAndUnlock(pendingUnlock2.pendingUnlockType, pendingUnlock3.isStrongBiometric);
                this.pendingUnlock = null;
            }
        }
    }

    public final void notifyListeners() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((OnBypassStateChangedListener) it.next()).onBypassStateChanged(getBypassEnabled());
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
        List list = this.listeners;
        boolean isEmpty = list.isEmpty();
        list.add(onBypassStateChangedListener);
        if (isEmpty) {
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.faceAuthEnabledChangedCallback);
        }
    }
}
