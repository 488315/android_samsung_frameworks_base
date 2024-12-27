package com.android.keyguard;

import android.R;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardSecurityModel {
    public final boolean mIsPukScreenAvailable;
    public KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.mIsPukScreenAvailable = resources.getBoolean(R.bool.config_flexibleSplitRatios);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final SecurityMode getSecurityMode(final int i) {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAdminLockEnabled()) {
            return SecurityMode.AdminLock;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.isActiveDismissAction()) {
            if (this.mIsPukScreenAvailable && SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(3))) {
                return SecurityMode.SimPuk;
            }
            if (LsRune.SECURITY_SIM_PERSO_LOCK && SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(12))) {
                return SecurityMode.SimPerso;
            }
            if (SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(2))) {
                EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
                if (edmMonitor != null && edmMonitor.mLockedIccIdList != null) {
                    int nextSubIdForState = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getNextSubIdForState(2);
                    if (SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                        SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) edmMonitor.knoxStateMonitor.mContext.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(nextSubIdForState);
                        String iccId = activeSubscriptionInfo != null ? activeSubscriptionInfo.getIccId() : null;
                        if (iccId != null) {
                            for (String str : edmMonitor.mLockedIccIdList) {
                                if (!str.equals(iccId)) {
                                }
                            }
                        }
                    }
                }
                return SecurityMode.SimPin;
            }
        }
        if (keyguardUpdateMonitor.isFMMLock()) {
            return SecurityMode.FMM;
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
        if (keyguardUpdateMonitor.isPermanentLock()) {
            return SecurityMode.Permanent;
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER && ((KeyguardStateControllerImpl) this.mKeyguardStateController).isShownSwipeBouncer()) {
            return SecurityMode.Swipe;
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
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(intValue, "Unknown security quality:"));
            }
        }
        return SecurityMode.Password;
    }
}
