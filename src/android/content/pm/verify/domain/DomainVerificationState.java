package android.content.pm.verify.domain;

import android.provider.VoicemailContract;

/* loaded from: classes.dex */
public interface DomainVerificationState {
    public static final int STATE_APPROVED = 2;
    public static final int STATE_DENIED = 3;
    public static final int STATE_FIRST_VERIFIER_DEFINED = 1024;
    public static final int STATE_LEGACY_FAILURE = 6;
    public static final int STATE_MIGRATED = 4;
    public static final int STATE_NO_RESPONSE = 0;
    public static final int STATE_PRE_VERIFIED = 8;
    public static final int STATE_RESTORED = 5;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_SYS_CONFIG = 7;

    public @interface State {
    }

    static boolean isDefault(int i) {
        return i == 0 || i == 4 || i == 5;
    }

    static boolean isModifiable(int i) {
        switch (i) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            case 8:
                return true;
            case 2:
            case 3:
            case 7:
                return false;
            default:
                return i >= 1024;
        }
    }

    static boolean isVerified(int i) {
        return i == 1 || i == 2 || i == 4 || i == 5 || i == 7 || i == 8;
    }

    static boolean shouldMigrate(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8;
    }

    static String stateToDebugString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "verified";
            case 2:
                return "approved";
            case 3:
                return "denied";
            case 4:
                return "migrated";
            case 5:
                return VoicemailContract.Voicemails.RESTORED;
            case 6:
                return "legacy_failure";
            case 7:
                return "system_configured";
            case 8:
                return "pre_verified";
            default:
                return String.valueOf(i);
        }
    }

    static int convertToInfoState(int i) {
        if (i >= 1024) {
            return i;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (isModifiable(i)) {
            return isVerified(i) ? 4 : 3;
        }
        return 2;
    }
}
