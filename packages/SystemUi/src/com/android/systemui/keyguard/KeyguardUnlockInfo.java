package com.android.systemui.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardUnlockInfo {
    public static final boolean DEBUG;
    public static final int HISTORY_MAX;
    public static final KeyguardUnlockInfo INSTANCE = new KeyguardUnlockInfo();
    public static AuthType authType;
    public static BiometricSourceType biometricSourceType;
    public static final Queue history;
    public static KeyguardSecurityModel.SecurityMode securityMode;
    public static SkipBouncerReason skipBouncerReason;
    public static UnlockTrigger unlockTrigger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum AuthType {
        AUTH_UNKNOWN,
        AUTH_SECURITY_MODE,
        AUTH_BIOMETRICS,
        /* JADX INFO: Fake field, exist only in values array */
        AUTH_EXTEND_LOCK,
        AUTH_SKIP_BOUNCER
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SkipBouncerReason {
        /* JADX INFO: Fake field, exist only in values array */
        FIXED,
        EXTEND_LOCK,
        FACE_UNLOCK_LOCK_STAY
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum UnlockTrigger {
        TRIGGER_UNKNOWN,
        TRIGGER_EXTERNAL,
        TRIGGER_INTERNAL,
        TRIGGER_BIO_UNLOCK,
        TRIGGER_BIO_WAKE_AND_UNLOCK,
        TRIGGER_SWIPE,
        TRIGGER_SHELL,
        TRIGGER_NOTIFICATION,
        /* JADX INFO: Fake field, exist only in values array */
        TRIGGER_AOD_NOTIFICATION,
        TRIGGER_QUICK_TILE,
        TRIGGER_SHORTCUT,
        TRIGGER_FACE_WIDGET,
        TRIGGER_KEYBOARD,
        TRIGGER_PENDING_INTENT,
        TRIGGER_PLUGIN_LOCK,
        TRIGGER_FOLD_OPENED,
        TRIGGER_COVER_OPENED,
        TRIGGER_FMM,
        TRIGGER_RMM,
        TRIGGER_CARRIER,
        TRIGGER_KNOX_GUARD,
        /* JADX INFO: Fake field, exist only in values array */
        TRIGGER_PENDING_WAKE_UP_ACTION,
        TRIGGER_GUTS,
        TRIGGER_REMOTE_INPUT,
        TRIGGER_SPEN_DETACHED,
        TRIGGER_EDIT_MODE
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AuthType.values().length];
            try {
                iArr[AuthType.AUTH_SECURITY_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AuthType.AUTH_BIOMETRICS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AuthType.AUTH_SKIP_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        DEBUG = (DeviceType.isShipBuild() && DeviceType.getDebugLevel() == 0) ? false : true;
        authType = AuthType.AUTH_UNKNOWN;
        history = new LinkedList();
        HISTORY_MAX = 50;
        reset();
    }

    private KeyguardUnlockInfo() {
    }

    public static void leaveHistory(String str, boolean z) {
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(LogUtil.makeDateTimeStr(System.currentTimeMillis()), " ", str);
        Queue queue = history;
        synchronized (queue) {
            if (((LinkedList) queue).size() > HISTORY_MAX) {
                ((LinkedList) queue).poll();
            }
            ((LinkedList) queue).offer(m15m);
        }
        if (DEBUG || z) {
            LogUtil.m223d("KeyguardUnlockInfo", str, new Object[0]);
        }
    }

    public static /* synthetic */ void leaveHistory$default(KeyguardUnlockInfo keyguardUnlockInfo, String str) {
        keyguardUnlockInfo.getClass();
        leaveHistory(str, false);
    }

    public static final void reset() {
        authType = AuthType.AUTH_UNKNOWN;
        biometricSourceType = null;
        securityMode = null;
        skipBouncerReason = null;
        unlockTrigger = UnlockTrigger.TRIGGER_UNKNOWN;
    }

    public static final void setAuthDetail(BiometricSourceType biometricSourceType2) {
        authType = AuthType.AUTH_BIOMETRICS;
        biometricSourceType = biometricSourceType2;
        securityMode = null;
        skipBouncerReason = null;
        leaveHistory$default(INSTANCE, "setAuthDetail: " + (biometricSourceType2 != null ? Integer.valueOf(biometricSourceType2.ordinal()) : null));
    }

    public static final void setAuthDetailSkipBouncer(SkipBouncerReason skipBouncerReason2) {
        authType = AuthType.AUTH_SKIP_BOUNCER;
        securityMode = null;
        biometricSourceType = null;
        skipBouncerReason = skipBouncerReason2;
        leaveHistory$default(INSTANCE, "setAuthDetailSkipBouncer: " + (skipBouncerReason2 != null ? Integer.valueOf(skipBouncerReason2.ordinal()) : null));
    }

    public static final void setUnlockTrigger(UnlockTrigger unlockTrigger2) {
        UnlockTrigger unlockTrigger3 = unlockTrigger;
        KeyguardUnlockInfo keyguardUnlockInfo = INSTANCE;
        if (unlockTrigger3 == unlockTrigger2) {
            leaveHistory$default(keyguardUnlockInfo, "setUnlockTrigger already set type " + (unlockTrigger2 != null ? Integer.valueOf(unlockTrigger2.ordinal()) : null));
            return;
        }
        unlockTrigger = unlockTrigger2;
        leaveHistory$default(keyguardUnlockInfo, "setUnlockTrigger: " + (unlockTrigger2 != null ? Integer.valueOf(unlockTrigger2.ordinal()) : null));
    }

    public static final void setUnlockTriggerByRemoteLock(int i) {
        UnlockTrigger unlockTrigger2 = i != 0 ? i != 1 ? i != 2 ? i != 3 ? null : UnlockTrigger.TRIGGER_KNOX_GUARD : UnlockTrigger.TRIGGER_RMM : UnlockTrigger.TRIGGER_CARRIER : UnlockTrigger.TRIGGER_FMM;
        unlockTrigger = unlockTrigger2;
        leaveHistory$default(INSTANCE, "setUnlockTriggerByRemoteLock: " + (unlockTrigger2 != null ? Integer.valueOf(unlockTrigger2.ordinal()) : null));
    }

    public static final void setUnlockTriggerIfNotSet(UnlockTrigger unlockTrigger2) {
        UnlockTrigger unlockTrigger3 = unlockTrigger;
        KeyguardUnlockInfo keyguardUnlockInfo = INSTANCE;
        if (unlockTrigger3 == unlockTrigger2) {
            leaveHistory$default(keyguardUnlockInfo, "setUnlockTriggerIfNotSet already set type " + (unlockTrigger2 != null ? Integer.valueOf(unlockTrigger2.ordinal()) : null));
            return;
        }
        leaveHistory$default(keyguardUnlockInfo, "setUnlockTriggerIfNotSet: " + (unlockTrigger3 != null ? Integer.valueOf(unlockTrigger3.ordinal()) : null) + " " + (unlockTrigger2 != null ? Integer.valueOf(unlockTrigger2.ordinal()) : null));
        if (unlockTrigger == UnlockTrigger.TRIGGER_UNKNOWN) {
            unlockTrigger = unlockTrigger2;
        }
    }

    public static final void setAuthDetail(KeyguardSecurityModel.SecurityMode securityMode2) {
        authType = AuthType.AUTH_SECURITY_MODE;
        securityMode = securityMode2;
        biometricSourceType = null;
        skipBouncerReason = null;
        leaveHistory$default(INSTANCE, "setAuthDetail: " + (securityMode2 != null ? Integer.valueOf(securityMode2.ordinal()) : null));
    }
}
