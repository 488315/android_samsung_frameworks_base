package android.app.admin;

import android.util.EventLog;

/* loaded from: classes.dex */
public class SecurityLogTags {
    public static final int SECURITY_ADB_SHELL_COMMAND = 210002;
    public static final int SECURITY_ADB_SHELL_INTERACTIVE = 210001;
    public static final int SECURITY_ADB_SYNC_RECV = 210003;
    public static final int SECURITY_ADB_SYNC_SEND = 210004;
    public static final int SECURITY_APP_PROCESS_START = 210005;
    public static final int SECURITY_BACKUP_SERVICE_TOGGLED = 210044;
    public static final int SECURITY_BLUETOOTH_CONNECTION = 210039;
    public static final int SECURITY_BLUETOOTH_DISCONNECTION = 210040;
    public static final int SECURITY_CAMERA_POLICY_SET = 210034;
    public static final int SECURITY_CERT_AUTHORITY_INSTALLED = 210029;
    public static final int SECURITY_CERT_AUTHORITY_REMOVED = 210030;
    public static final int SECURITY_CERT_VALIDATION_FAILURE = 210033;
    public static final int SECURITY_CRYPTO_SELF_TEST_COMPLETED = 210031;
    public static final int SECURITY_KEYGUARD_DISABLED_FEATURES_SET = 210021;
    public static final int SECURITY_KEYGUARD_DISMISSED = 210006;
    public static final int SECURITY_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int SECURITY_KEYGUARD_SECURED = 210008;
    public static final int SECURITY_KEY_DESTROYED = 210026;
    public static final int SECURITY_KEY_GENERATED = 210024;
    public static final int SECURITY_KEY_IMPORTED = 210025;
    public static final int SECURITY_KEY_INTEGRITY_VIOLATION = 210032;
    public static final int SECURITY_LOGGING_STARTED = 210011;
    public static final int SECURITY_LOGGING_STOPPED = 210012;
    public static final int SECURITY_LOG_BUFFER_SIZE_CRITICAL = 210015;
    public static final int SECURITY_MAX_PASSWORD_ATTEMPTS_SET = 210020;
    public static final int SECURITY_MAX_SCREEN_LOCK_TIMEOUT_SET = 210019;
    public static final int SECURITY_MEDIA_MOUNTED = 210013;
    public static final int SECURITY_MEDIA_UNMOUNTED = 210014;
    public static final int SECURITY_NFC_DISABLED = 210046;
    public static final int SECURITY_NFC_ENABLED = 210045;
    public static final int SECURITY_OS_SHUTDOWN = 210010;
    public static final int SECURITY_OS_STARTUP = 210009;
    public static final int SECURITY_PACKAGE_INSTALLED = 210041;
    public static final int SECURITY_PACKAGE_UNINSTALLED = 210043;
    public static final int SECURITY_PACKAGE_UPDATED = 210042;
    public static final int SECURITY_PASSWORD_CHANGED = 210036;
    public static final int SECURITY_PASSWORD_COMPLEXITY_REQUIRED = 210035;
    public static final int SECURITY_PASSWORD_COMPLEXITY_SET = 210017;
    public static final int SECURITY_PASSWORD_EXPIRATION_SET = 210016;
    public static final int SECURITY_PASSWORD_HISTORY_LENGTH_SET = 210018;
    public static final int SECURITY_REMOTE_LOCK = 210022;
    public static final int SECURITY_USER_RESTRICTION_ADDED = 210027;
    public static final int SECURITY_USER_RESTRICTION_REMOVED = 210028;
    public static final int SECURITY_WIFI_CONNECTION = 210037;
    public static final int SECURITY_WIFI_DISCONNECTION = 210038;
    public static final int SECURITY_WIPE_FAILED = 210023;

    private SecurityLogTags() {
    }

    public static void writeSecurityAdbShellInteractive() {
        EventLog.writeEvent(210001, new Object[0]);
    }

    public static void writeSecurityAdbShellCommand(String str) {
        EventLog.writeEvent(210002, str);
    }

    public static void writeSecurityAdbSyncRecv(String str) {
        EventLog.writeEvent(210003, str);
    }

    public static void writeSecurityAdbSyncSend(String str) {
        EventLog.writeEvent(210004, str);
    }

    public static void writeSecurityAppProcessStart(String str, long j, int i, int i2, String str2, String str3) {
        EventLog.writeEvent(210005, str, Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), str2, str3);
    }

    public static void writeSecurityKeyguardDismissed() {
        EventLog.writeEvent(210006, new Object[0]);
    }

    public static void writeSecurityKeyguardDismissAuthAttempt(int i, int i2) {
        EventLog.writeEvent(210007, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityKeyguardSecured() {
        EventLog.writeEvent(210008, new Object[0]);
    }

    public static void writeSecurityOsStartup(String str, String str2) {
        EventLog.writeEvent(210009, str, str2);
    }

    public static void writeSecurityOsShutdown() {
        EventLog.writeEvent(210010, new Object[0]);
    }

    public static void writeSecurityLoggingStarted() {
        EventLog.writeEvent(210011, new Object[0]);
    }

    public static void writeSecurityLoggingStopped() {
        EventLog.writeEvent(210012, new Object[0]);
    }

    public static void writeSecurityMediaMounted(String str, String str2) {
        EventLog.writeEvent(210013, str, str2);
    }

    public static void writeSecurityMediaUnmounted(String str, String str2) {
        EventLog.writeEvent(210014, str, str2);
    }

    public static void writeSecurityLogBufferSizeCritical() {
        EventLog.writeEvent(210015, new Object[0]);
    }

    public static void writeSecurityPasswordExpirationSet(String str, int i, int i2, long j) {
        EventLog.writeEvent(210016, str, Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(j));
    }

    public static void writeSecurityPasswordComplexitySet(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        EventLog.writeEvent(210017, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9), Integer.valueOf(i10));
    }

    public static void writeSecurityPasswordHistoryLengthSet(String str, int i, int i2, int i3) {
        EventLog.writeEvent(210018, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeSecurityMaxScreenLockTimeoutSet(String str, int i, int i2, long j) {
        EventLog.writeEvent(210019, str, Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(j));
    }

    public static void writeSecurityMaxPasswordAttemptsSet(String str, int i, int i2, int i3) {
        EventLog.writeEvent(210020, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeSecurityKeyguardDisabledFeaturesSet(String str, int i, int i2, int i3) {
        EventLog.writeEvent(210021, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeSecurityRemoteLock(String str, int i, int i2) {
        EventLog.writeEvent(210022, str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityWipeFailed(String str, int i) {
        EventLog.writeEvent(210023, str, Integer.valueOf(i));
    }

    public static void writeSecurityKeyGenerated(int i, String str, int i2) {
        EventLog.writeEvent(210024, Integer.valueOf(i), str, Integer.valueOf(i2));
    }

    public static void writeSecurityKeyImported(int i, String str, int i2) {
        EventLog.writeEvent(210025, Integer.valueOf(i), str, Integer.valueOf(i2));
    }

    public static void writeSecurityKeyDestroyed(int i, String str, int i2) {
        EventLog.writeEvent(210026, Integer.valueOf(i), str, Integer.valueOf(i2));
    }

    public static void writeSecurityUserRestrictionAdded(String str, int i, String str2) {
        EventLog.writeEvent(210027, str, Integer.valueOf(i), str2);
    }

    public static void writeSecurityUserRestrictionRemoved(String str, int i, String str2) {
        EventLog.writeEvent(210028, str, Integer.valueOf(i), str2);
    }

    public static void writeSecurityCertAuthorityInstalled(int i, String str, int i2) {
        EventLog.writeEvent(210029, Integer.valueOf(i), str, Integer.valueOf(i2));
    }

    public static void writeSecurityCertAuthorityRemoved(int i, String str, int i2) {
        EventLog.writeEvent(210030, Integer.valueOf(i), str, Integer.valueOf(i2));
    }

    public static void writeSecurityCryptoSelfTestCompleted(int i) {
        EventLog.writeEvent(210031, i);
    }

    public static void writeSecurityKeyIntegrityViolation(String str, int i) {
        EventLog.writeEvent(210032, str, Integer.valueOf(i));
    }

    public static void writeSecurityCertValidationFailure(String str) {
        EventLog.writeEvent(210033, str);
    }

    public static void writeSecurityCameraPolicySet(String str, int i, int i2, int i3) {
        EventLog.writeEvent(210034, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeSecurityPasswordComplexityRequired(String str, int i, int i2, int i3) {
        EventLog.writeEvent(210035, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeSecurityPasswordChanged(int i, int i2) {
        EventLog.writeEvent(210036, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityWifiConnection(String str, String str2, String str3) {
        EventLog.writeEvent(210037, str, str2, str3);
    }

    public static void writeSecurityWifiDisconnection(String str, String str2) {
        EventLog.writeEvent(210038, str, str2);
    }

    public static void writeSecurityBluetoothConnection(String str, int i, String str2) {
        EventLog.writeEvent(210039, str, Integer.valueOf(i), str2);
    }

    public static void writeSecurityBluetoothDisconnection(String str, String str2) {
        EventLog.writeEvent(210040, str, str2);
    }

    public static void writeSecurityPackageInstalled(String str, int i, int i2) {
        EventLog.writeEvent(210041, str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityPackageUpdated(String str, int i, int i2) {
        EventLog.writeEvent(210042, str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityPackageUninstalled(String str, int i, int i2) {
        EventLog.writeEvent(210043, str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityBackupServiceToggled(String str, int i, int i2) {
        EventLog.writeEvent(210044, str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSecurityNfcEnabled() {
        EventLog.writeEvent(210045, new Object[0]);
    }

    public static void writeSecurityNfcDisabled() {
        EventLog.writeEvent(210046, new Object[0]);
    }
}
