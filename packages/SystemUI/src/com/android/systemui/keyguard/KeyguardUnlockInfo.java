package com.android.systemui.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import java.util.LinkedList;
import java.util.Queue;
import kotlin.enums.EnumEntriesKt;

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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class AuthType {
        public static final /* synthetic */ AuthType[] $VALUES;
        public static final AuthType AUTH_BIOMETRICS;
        public static final AuthType AUTH_SECURITY_MODE;
        public static final AuthType AUTH_SKIP_BOUNCER;
        public static final AuthType AUTH_UNKNOWN;

        static {
            AuthType authType = new AuthType("AUTH_UNKNOWN", 0);
            AUTH_UNKNOWN = authType;
            AuthType authType2 = new AuthType("AUTH_SECURITY_MODE", 1);
            AUTH_SECURITY_MODE = authType2;
            AuthType authType3 = new AuthType("AUTH_BIOMETRICS", 2);
            AUTH_BIOMETRICS = authType3;
            AuthType authType4 = new AuthType("AUTH_EXTEND_LOCK", 3);
            AuthType authType5 = new AuthType("AUTH_SKIP_BOUNCER", 4);
            AUTH_SKIP_BOUNCER = authType5;
            AuthType[] authTypeArr = {authType, authType2, authType3, authType4, authType5};
            $VALUES = authTypeArr;
            EnumEntriesKt.enumEntries(authTypeArr);
        }

        private AuthType(String str, int i) {
        }

        public static AuthType valueOf(String str) {
            return (AuthType) Enum.valueOf(AuthType.class, str);
        }

        public static AuthType[] values() {
            return (AuthType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class SkipBouncerReason {
        public static final /* synthetic */ SkipBouncerReason[] $VALUES;
        public static final SkipBouncerReason BIOMETRICS_UNLOCK_LOCK_STAY;
        public static final SkipBouncerReason EXTEND_LOCK;

        static {
            SkipBouncerReason skipBouncerReason = new SkipBouncerReason("FIXED", 0);
            SkipBouncerReason skipBouncerReason2 = new SkipBouncerReason("EXTEND_LOCK", 1);
            EXTEND_LOCK = skipBouncerReason2;
            SkipBouncerReason skipBouncerReason3 = new SkipBouncerReason("BIOMETRICS_UNLOCK_LOCK_STAY", 2);
            BIOMETRICS_UNLOCK_LOCK_STAY = skipBouncerReason3;
            SkipBouncerReason[] skipBouncerReasonArr = {skipBouncerReason, skipBouncerReason2, skipBouncerReason3};
            $VALUES = skipBouncerReasonArr;
            EnumEntriesKt.enumEntries(skipBouncerReasonArr);
        }

        private SkipBouncerReason(String str, int i) {
        }

        public static SkipBouncerReason valueOf(String str) {
            return (SkipBouncerReason) Enum.valueOf(SkipBouncerReason.class, str);
        }

        public static SkipBouncerReason[] values() {
            return (SkipBouncerReason[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class UnlockTrigger {
        public static final /* synthetic */ UnlockTrigger[] $VALUES;
        public static final UnlockTrigger TRIGGER_BIO_UNLOCK;
        public static final UnlockTrigger TRIGGER_BIO_WAKE_AND_UNLOCK;
        public static final UnlockTrigger TRIGGER_CARRIER;
        public static final UnlockTrigger TRIGGER_COVER_OPENED;
        public static final UnlockTrigger TRIGGER_EDIT_MODE;
        public static final UnlockTrigger TRIGGER_EXTERNAL;
        public static final UnlockTrigger TRIGGER_FACE_WIDGET;
        public static final UnlockTrigger TRIGGER_FMM;
        public static final UnlockTrigger TRIGGER_FOLD_OPENED;
        public static final UnlockTrigger TRIGGER_GUTS;
        public static final UnlockTrigger TRIGGER_INTERNAL;
        public static final UnlockTrigger TRIGGER_KEYBOARD;
        public static final UnlockTrigger TRIGGER_KNOX_GUARD;
        public static final UnlockTrigger TRIGGER_NOTIFICATION;
        public static final UnlockTrigger TRIGGER_PENDING_INTENT;
        public static final UnlockTrigger TRIGGER_PLUGIN_LOCK;
        public static final UnlockTrigger TRIGGER_QUICK_TILE;
        public static final UnlockTrigger TRIGGER_REMOTE_INPUT;
        public static final UnlockTrigger TRIGGER_RMM;
        public static final UnlockTrigger TRIGGER_SHELL;
        public static final UnlockTrigger TRIGGER_SHORTCUT;
        public static final UnlockTrigger TRIGGER_SPEN_DETACHED;
        public static final UnlockTrigger TRIGGER_SWIPE;
        public static final UnlockTrigger TRIGGER_UNKNOWN;

        static {
            UnlockTrigger unlockTrigger = new UnlockTrigger("TRIGGER_UNKNOWN", 0);
            TRIGGER_UNKNOWN = unlockTrigger;
            UnlockTrigger unlockTrigger2 = new UnlockTrigger("TRIGGER_EXTERNAL", 1);
            TRIGGER_EXTERNAL = unlockTrigger2;
            UnlockTrigger unlockTrigger3 = new UnlockTrigger("TRIGGER_INTERNAL", 2);
            TRIGGER_INTERNAL = unlockTrigger3;
            UnlockTrigger unlockTrigger4 = new UnlockTrigger("TRIGGER_BIO_UNLOCK", 3);
            TRIGGER_BIO_UNLOCK = unlockTrigger4;
            UnlockTrigger unlockTrigger5 = new UnlockTrigger("TRIGGER_BIO_WAKE_AND_UNLOCK", 4);
            TRIGGER_BIO_WAKE_AND_UNLOCK = unlockTrigger5;
            UnlockTrigger unlockTrigger6 = new UnlockTrigger("TRIGGER_SWIPE", 5);
            TRIGGER_SWIPE = unlockTrigger6;
            UnlockTrigger unlockTrigger7 = new UnlockTrigger("TRIGGER_SHELL", 6);
            TRIGGER_SHELL = unlockTrigger7;
            UnlockTrigger unlockTrigger8 = new UnlockTrigger("TRIGGER_NOTIFICATION", 7);
            TRIGGER_NOTIFICATION = unlockTrigger8;
            UnlockTrigger unlockTrigger9 = new UnlockTrigger("TRIGGER_AOD_NOTIFICATION", 8);
            UnlockTrigger unlockTrigger10 = new UnlockTrigger("TRIGGER_QUICK_TILE", 9);
            TRIGGER_QUICK_TILE = unlockTrigger10;
            UnlockTrigger unlockTrigger11 = new UnlockTrigger("TRIGGER_SHORTCUT", 10);
            TRIGGER_SHORTCUT = unlockTrigger11;
            UnlockTrigger unlockTrigger12 = new UnlockTrigger("TRIGGER_FACE_WIDGET", 11);
            TRIGGER_FACE_WIDGET = unlockTrigger12;
            UnlockTrigger unlockTrigger13 = new UnlockTrigger("TRIGGER_KEYBOARD", 12);
            TRIGGER_KEYBOARD = unlockTrigger13;
            UnlockTrigger unlockTrigger14 = new UnlockTrigger("TRIGGER_PENDING_INTENT", 13);
            TRIGGER_PENDING_INTENT = unlockTrigger14;
            UnlockTrigger unlockTrigger15 = new UnlockTrigger("TRIGGER_PLUGIN_LOCK", 14);
            TRIGGER_PLUGIN_LOCK = unlockTrigger15;
            UnlockTrigger unlockTrigger16 = new UnlockTrigger("TRIGGER_FOLD_OPENED", 15);
            TRIGGER_FOLD_OPENED = unlockTrigger16;
            UnlockTrigger unlockTrigger17 = new UnlockTrigger("TRIGGER_COVER_OPENED", 16);
            TRIGGER_COVER_OPENED = unlockTrigger17;
            UnlockTrigger unlockTrigger18 = new UnlockTrigger("TRIGGER_FMM", 17);
            TRIGGER_FMM = unlockTrigger18;
            UnlockTrigger unlockTrigger19 = new UnlockTrigger("TRIGGER_RMM", 18);
            TRIGGER_RMM = unlockTrigger19;
            UnlockTrigger unlockTrigger20 = new UnlockTrigger("TRIGGER_CARRIER", 19);
            TRIGGER_CARRIER = unlockTrigger20;
            UnlockTrigger unlockTrigger21 = new UnlockTrigger("TRIGGER_KNOX_GUARD", 20);
            TRIGGER_KNOX_GUARD = unlockTrigger21;
            UnlockTrigger unlockTrigger22 = new UnlockTrigger("TRIGGER_PENDING_WAKE_UP_ACTION", 21);
            UnlockTrigger unlockTrigger23 = new UnlockTrigger("TRIGGER_GUTS", 22);
            TRIGGER_GUTS = unlockTrigger23;
            UnlockTrigger unlockTrigger24 = new UnlockTrigger("TRIGGER_REMOTE_INPUT", 23);
            TRIGGER_REMOTE_INPUT = unlockTrigger24;
            UnlockTrigger unlockTrigger25 = new UnlockTrigger("TRIGGER_SPEN_DETACHED", 24);
            TRIGGER_SPEN_DETACHED = unlockTrigger25;
            UnlockTrigger unlockTrigger26 = new UnlockTrigger("TRIGGER_EDIT_MODE", 25);
            TRIGGER_EDIT_MODE = unlockTrigger26;
            UnlockTrigger[] unlockTriggerArr = {unlockTrigger, unlockTrigger2, unlockTrigger3, unlockTrigger4, unlockTrigger5, unlockTrigger6, unlockTrigger7, unlockTrigger8, unlockTrigger9, unlockTrigger10, unlockTrigger11, unlockTrigger12, unlockTrigger13, unlockTrigger14, unlockTrigger15, unlockTrigger16, unlockTrigger17, unlockTrigger18, unlockTrigger19, unlockTrigger20, unlockTrigger21, unlockTrigger22, unlockTrigger23, unlockTrigger24, unlockTrigger25, unlockTrigger26};
            $VALUES = unlockTriggerArr;
            EnumEntriesKt.enumEntries(unlockTriggerArr);
        }

        private UnlockTrigger(String str, int i) {
        }

        public static UnlockTrigger valueOf(String str) {
            return (UnlockTrigger) Enum.valueOf(UnlockTrigger.class, str);
        }

        public static UnlockTrigger[] values() {
            return (UnlockTrigger[]) $VALUES.clone();
        }
    }

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
        DEBUG = (DeviceType.isShipBuild() && DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_LOW) ? false : true;
        authType = AuthType.AUTH_UNKNOWN;
        history = new LinkedList();
        HISTORY_MAX = 50;
        reset();
    }

    private KeyguardUnlockInfo() {
    }

    public static void leaveHistory(String str, boolean z) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(LogUtil.makeDateTimeStr(System.currentTimeMillis()), " ", str);
        Queue queue = history;
        synchronized (queue) {
            if (((LinkedList) queue).size() > HISTORY_MAX) {
                ((LinkedList) queue).poll();
            }
            ((LinkedList) queue).offer(m);
        }
        if (DEBUG || z) {
            LogUtil.d("KeyguardUnlockInfo", str, new Object[0]);
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
        unlockTrigger = null;
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
