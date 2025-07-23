package android.sec.enterprise.auditlog;

import android.os.Process;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;

/* loaded from: classes3.dex */
public class AuditLog {
    public static final int ALERT = 1;
    public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
    public static final int AUDIT_LOG_GROUP_EVENTS = 4;
    public static final int AUDIT_LOG_GROUP_NETWORK = 3;
    public static final int AUDIT_LOG_GROUP_SECURITY = 1;
    public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
    public static final int CRITICAL = 2;
    public static final int ERROR = 3;
    public static final int NOTICE = 5;
    private static final String TAG = "AuditLog";
    public static final int WARNING = 4;

    public static void log(int i, int i2, boolean z, int i3, String str, String str2) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.logEventForLegacyComponents(i3, i, i2, z, str, str2);
            }
        } catch (Exception unused) {
        }
    }

    public static void logEvent(int i, Object... objArr) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.logEventAsUser(-1, Process.myPid(), null, i, new AuditLogParams(objArr));
            }
        } catch (Exception unused) {
        }
    }

    public static void logEventAsUser(int i, int i2, Object... objArr) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.logEventAsUser(i, Process.myPid(), null, i2, new AuditLogParams(objArr));
            }
        } catch (Exception unused) {
        }
    }

    public static boolean isAuditLogEnabledAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isAuditLogEnabledAsUser(i);
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
