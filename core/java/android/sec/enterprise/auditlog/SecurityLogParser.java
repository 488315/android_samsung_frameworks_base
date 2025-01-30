package android.sec.enterprise.auditlog;

import android.p009os.Binder;
import android.p009os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
public class SecurityLogParser {
    private static final int AUDIT_LOG_GROUP_INVALID = -1;
    private static final String TAG = SecurityLogParser.class.getSimpleName();
    private static final Map<Integer, String> logFormat = Map.ofEntries(Map.entry(210005, "TAG_APP_PROCESS_START: process name = %s - start time = %d - uid = %d - pid = %d - SE info tag = %s - SHA256 hash = %s"), Map.entry(210039, "TAG_BLUETOOTH_CONNECTION: MAC address = %s - connection successful = %b "), Map.entry(210040, "TAG_BLUETOOTH_DISCONNECTION: MAC address = %s "), Map.entry(210006, "TAG_KEYGUARD_DISMISSED"), Map.entry(210007, "TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT: result: %b - strong authentication: %b"), Map.entry(210008, "TAG_KEYGUARD_SECURED"), Map.entry(210009, "TAG_OS_STARTUP: Verified Boot state: %s - dm-verity mode: %s"), Map.entry(210010, "TAG_OS_SHUTDOWN"), Map.entry(210011, "TAG_LOGGING_STARTED"), Map.entry(210012, "TAG_LOGGING_STOPPED"), Map.entry(210013, "TAG_MEDIA_MOUNT: mount point: %s - volume label: %s"), Map.entry(210014, "TAG_MEDIA_UNMOUNT: mount point: %s - volume label: %s"), Map.entry(210015, "TAG_LOG_BUFFER_SIZE_CRITICAL"), Map.entry(210041, "TAG_PACKAGE_INSTALLED: package name = %s - package version code = %d - user id = %d"), Map.entry(210043, "TAG_PACKAGE_UNINSTALLED: package name = %s - package version code = %d - user id = %d"), Map.entry(210042, "TAG_PACKAGE_UPDATED: package name = %s - package version code = %d - user id = %d"), Map.entry(210036, "TAG_PASSWORD_CHANGED: new password complexity = %d - target user ID = %d"), Map.entry(210016, "TAG_PASSWORD_EXPIRATION_SET: admin package name = %s - admin user ID = %d - target user ID = %d - new timeout = %d"), Map.entry(210017, "TAG_PASSWORD_COMPLEXITY_SET: admin package name = %s - admin user ID = %d - target user ID = %d - minimum password length = %d - password quality constraint = %d - minimum of letters = %d - minimum of non-letters = %d - minimum of digits = %d - minimum of uppercase letters = %d - minimum of lowercase letters = %d - minimum of symbols = %d"), Map.entry(210018, "TAG_PASSWORD_HISTORY_LENGTH_SET: admin package name = %s - admin user ID = %d - target user ID = %d - new password history length = %d"), Map.entry(210019, "TAG_MAX_SCREEN_LOCK_TIMEOUT_SET: admin package name = %s - admin user ID = %d - target user ID = %d - new screen lock timeout = %d"), Map.entry(210020, "TAG_MAX_PASSWORD_ATTEMPTS_SET: admin package name = %s - admin user ID = %d - target user ID = %d - new maximum of failed attempts = %d"), Map.entry(210021, "TAG_KEYGUARD_DISABLED_FEATURES_SET: admin package name = %s - admin user ID = %d - target user ID = %d - disabled keyguard feature mask = %d"), Map.entry(210022, "TAG_REMOTE_LOCK: admin package name = %s - admin user ID = %d - target user ID = %d"), Map.entry(210023, "TAG_WIPE_FAILURE"), Map.entry(210029, "TAG_CERT_AUTHORITY_INSTALLED: result = %b - certificate subject = %s - user id = %d"), Map.entry(210030, "TAG_CERT_AUTHORITY_REMOVED: result = %b - certificate subject = %s - user id = %d"), Map.entry(210027, "TAG_USER_RESTRICTION_ADDED: admin package name = %s - user id = %d - user restriction = %s"), Map.entry(210028, "TAG_USER_RESTRICTION_REMOVED: admin package name = %s - user id = %d - user restriction = %s"), Map.entry(210031, "TAG_CRYPTO_SELF_TEST_COMPLETED: result = %b"), Map.entry(210034, "TAG_CAMERA_POLICY_SET: admin package name = %s - admin user ID = %d - target user ID = %d - camera is disabled = %b"), Map.entry(210035, "TAG_PASSWORD_COMPLEXITY_REQUIRED: admin package name = %s - admin user ID = %d - target user ID = %d - password complexity = %d"), Map.entry(210037, "TAG_WIFI_CONNECTION: attempt of connection BSSID = %s - eventType = %s "), Map.entry(210038, "TAG_WIFI_DISCONNECTION: disconnected from BSSID = %s "));
    private static final Map<Integer, String> logFormatEmptyTag = Map.ofEntries(Map.entry(210005, "TAG_APP_PROCESS_START"), Map.entry(210006, "TAG_KEYGUARD_DISMISSED"), Map.entry(210007, "TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT"), Map.entry(210008, "TAG_KEYGUARD_SECURED"), Map.entry(210009, "TAG_OS_STARTUP"), Map.entry(210010, "TAG_OS_SHUTDOWN"), Map.entry(210011, "TAG_LOGGING_STARTED"), Map.entry(210012, "TAG_LOGGING_STOPPED"), Map.entry(210013, "TAG_MEDIA_MOUNT"), Map.entry(210014, "TAG_MEDIA_UNMOUNT"), Map.entry(210015, "TAG_LOG_BUFFER_SIZE_CRITICAL"), Map.entry(210016, "TAG_PASSWORD_EXPIRATION_SET"), Map.entry(210017, "TAG_PASSWORD_COMPLEXITY_SET"), Map.entry(210018, "TAG_PASSWORD_HISTORY_LENGTH_SET"), Map.entry(210019, "TAG_MAX_SCREEN_LOCK_TIMEOUT_SET"), Map.entry(210020, "TAG_MAX_PASSWORD_ATTEMPTS_SET"), Map.entry(210021, "TAG_KEYGUARD_DISABLED_FEATURES_SET"), Map.entry(210022, "TAG_REMOTE_LOCK"), Map.entry(210023, "TAG_WIPE_FAILURE"), Map.entry(210029, "TAG_CERT_AUTHORITY_INSTALLED"), Map.entry(210030, "TAG_CERT_AUTHORITY_REMOVED"), Map.entry(210027, "TAG_USER_RESTRICTION_ADDED"), Map.entry(210028, "TAG_USER_RESTRICTION_REMOVED"), Map.entry(210031, "TAG_CRYPTO_SELF_TEST_COMPLETED"), Map.entry(210034, "TAG_CAMERA_POLICY_SET: admin package name"), Map.entry(210035, "TAG_PASSWORD_COMPLEXITY_REQUIRED"), Map.entry(210037, "TAG_WIFI_CONNECTION"), Map.entry(210038, "TAG_WIFI_DISCONNECTION"));

    public static void parse(int tag, Object... payloads) {
        int group = getLogGroup(tag);
        if (group == -1) {
            Log.m94d(TAG, "Unimplemented SecurityLog tag");
            return;
        }
        int uid = Binder.getCallingUid();
        boolean outcome = getOutcome(tag, payloads);
        int severity = getLogSeverity(tag, outcome);
        int userId = getUserId(tag, payloads);
        String redactedLogMessage = getRedactedLogMessage(tag);
        String logMessage = buildLogMessage(tag, payloads);
        AuditLog.logAsUser(severity, group, outcome, uid, "SecurityLog", logMessage, redactedLogMessage, userId);
    }

    public static void parse(int tag, String str) {
        ArrayList<Object> payloads = new ArrayList<>();
        payloads.add(str);
        parse(tag, payloads.toArray());
    }

    private static int getLogSeverity(int tag, boolean outcome) {
        switch (tag) {
            case 210007:
            case 210029:
                if (outcome) {
                }
                break;
            case 210015:
            case 210023:
                break;
            case 210030:
            case 210031:
            case 210039:
                if (outcome) {
                    break;
                }
                break;
        }
        return 5;
    }

    private static int getLogGroup(int tag) {
        switch (tag) {
            case 210005:
            case 210037:
            case 210038:
            case 210039:
            case 210040:
            case 210041:
            case 210042:
            case 210043:
                return 5;
            case 210006:
            case 210007:
            case 210008:
            case 210011:
            case 210012:
            case 210015:
            case 210016:
            case 210017:
            case 210018:
            case 210019:
            case 210020:
            case 210021:
            case 210022:
            case 210027:
            case 210028:
            case 210029:
            case 210030:
            case 210031:
            case 210034:
            case 210035:
            case 210036:
                return 1;
            case 210009:
            case 210010:
            case 210013:
            case 210014:
            case 210023:
                return 2;
            case 210024:
            case 210025:
            case 210026:
            case 210032:
            case 210033:
            default:
                return -1;
        }
    }

    private static int getUserId(int tag, Object... payloads) {
        switch (tag) {
            case 210005:
                return UserHandle.getUserId(((Integer) payloads[2]).intValue());
            case 210016:
            case 210017:
            case 210018:
            case 210019:
            case 210020:
            case 210021:
            case 210022:
            case 210029:
            case 210030:
            case 210034:
            case 210035:
            case 210041:
            case 210042:
            case 210043:
                return ((Integer) payloads[2]).intValue();
            case 210027:
            case 210028:
            case 210036:
                if (payloads == null || payloads.length <= 0) {
                    return -1;
                }
                return ((Integer) payloads[1]).intValue();
            default:
                return -1;
        }
    }

    private static boolean getOutcome(int tag, Object... payloads) {
        switch (tag) {
            case 210007:
            case 210029:
            case 210030:
            case 210031:
                if (((Integer) payloads[0]).intValue() == 1) {
                    break;
                }
                break;
            case 210037:
                break;
            case 210039:
                if (((Integer) payloads[1]).intValue() == 1) {
                    break;
                }
                break;
        }
        return true;
    }

    private static String getRedactedLogMessage(int tag) {
        switch (tag) {
            case 210005:
            case 210029:
            case 210030:
            case 210041:
            case 210042:
            case 210043:
                return "";
            default:
                return null;
        }
    }

    private static String buildLogMessage(int tag, Object... payloads) {
        if (tag == 210037 || tag == 210039) {
            String failureReason = String.valueOf(payloads[2]);
            if (!TextUtils.isEmpty(failureReason)) {
                return String.format(logFormat.get(Integer.valueOf(tag)) + "- %s", payloads);
            }
        } else if (tag == 210038 || tag == 210040) {
            String reason = String.valueOf(payloads[1]);
            if (!TextUtils.isEmpty(reason)) {
                return String.format(logFormat.get(Integer.valueOf(tag)) + "- %s", payloads);
            }
        }
        if (payloads == null || payloads.length == 0) {
            return logFormatEmptyTag.get(Integer.valueOf(tag));
        }
        return String.format(logFormat.get(Integer.valueOf(tag)), payloads);
    }
}
