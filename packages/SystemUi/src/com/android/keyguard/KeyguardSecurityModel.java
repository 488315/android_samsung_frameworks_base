package com.android.keyguard;

import android.R;
import android.content.res.Resources;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecurityModel {
    public final boolean mIsPukScreenAvailable;
    public KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SecurityMode {
        Invalid,
        None,
        Pattern,
        Password,
        PIN,
        SimPin,
        SimPuk,
        Permanent,
        FMM,
        RMM,
        KNOXGUARD,
        SKTCarrierLock,
        SKTCarrierPassword,
        SmartcardPIN,
        AdminLock,
        SimPerso,
        Swipe,
        ForgotPassword
    }

    public KeyguardSecurityModel(Resources resources, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mIsPukScreenAvailable = resources.getBoolean(R.bool.config_enableHapticTextHandle);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SecurityMode getSecurityMode(final int i) {
        boolean z;
        boolean z2;
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isAdminLockEnabled()) {
            return SecurityMode.AdminLock;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isPermanentLock()) {
            return SecurityMode.Permanent;
        }
        boolean z3 = false;
        if (!keyguardUpdateMonitor.isActiveDismissAction()) {
            if (this.mIsPukScreenAvailable && SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(3))) {
                return SecurityMode.SimPuk;
            }
            if (LsRune.SECURITY_SIM_PERSO_LOCK && SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(12))) {
                return SecurityMode.SimPerso;
            }
            if (SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(2))) {
                EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
                if (edmMonitor != null) {
                    if (edmMonitor.mLockedIccIdList != null) {
                        int nextSubIdForState = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getNextSubIdForState(2);
                        if (SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                            SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) edmMonitor.knoxStateMonitor.mContext.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(nextSubIdForState);
                            String iccId = activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null;
                            if (iccId != null) {
                                for (String str : edmMonitor.mLockedIccIdList) {
                                    if (!str.equals(iccId)) {
                                    }
                                }
                            }
                            z2 = true;
                            if (z2) {
                                z = true;
                                if (!z) {
                                    return SecurityMode.SimPin;
                                }
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                z = false;
                if (!z) {
                }
            }
        }
        if (keyguardUpdateMonitor.isRemoteLockEnabled()) {
            int remoteLockType = keyguardUpdateMonitor.getRemoteLockType();
            if (remoteLockType == 2) {
                return SecurityMode.RMM;
            }
            if (remoteLockType == 3) {
                return SecurityMode.KNOXGUARD;
            }
        }
        if (keyguardUpdateMonitor.isCarrierLock()) {
            return SecurityMode.SKTCarrierLock;
        }
        if (keyguardUpdateMonitor.isFMMLock()) {
            return SecurityMode.FMM;
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            if (keyguardStateControllerImpl.mIsSwipeBouncer && keyguardStateControllerImpl.mCanDismissLockScreen && keyguardStateControllerImpl.mShowing) {
                z3 = true;
            }
            if (z3) {
                return SecurityMode.Swipe;
            }
        }
        int intValue = ((Integer) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardSecurityModel$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                KeyguardSecurityModel keyguardSecurityModel = KeyguardSecurityModel.this;
                return Integer.valueOf(keyguardSecurityModel.mLockPatternUtils.getActivePasswordQuality(i));
            }
        })).intValue();
        if (intValue == 0) {
            return SecurityMode.None;
        }
        if (intValue == 65536) {
            return SecurityMode.Pattern;
        }
        if (intValue == 131072 || intValue == 196608) {
            return SecurityMode.PIN;
        }
        if (intValue != 262144 && intValue != 327680 && intValue != 393216) {
            if (intValue == 458752) {
                return SecurityMode.SmartcardPIN;
            }
            if (intValue != 524288) {
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Unknown security quality:", intValue));
            }
        }
        return SecurityMode.Password;
    }
}
