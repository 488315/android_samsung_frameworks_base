package android.sec.enterprise.auditlog;

import android.os.Process;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;

import java.util.ArrayList;
import java.util.List;

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

    public static void log(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int uid,
            String swComponent,
            String logMessage) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.auditLogger(
                        severityGrade, moduleGroup, outcome, uid, swComponent, logMessage);
            }
        } catch (Exception e) {
        }
    }

    public static void log(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int uid,
            String swComponent,
            String logMessage,
            String redactedLogMessage) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.redactedAuditLogger(
                        severityGrade,
                        moduleGroup,
                        outcome,
                        uid,
                        swComponent,
                        logMessage,
                        redactedLogMessage);
            }
        } catch (Exception e) {
        }
    }

    public static void logMMS(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int pid,
            String swComponent,
            String logMessage) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.auditLogger(
                        severityGrade, moduleGroup, outcome, pid, swComponent, logMessage);
            }
        } catch (Exception e) {
        }
    }

    public static void logAsUser(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int uid,
            String swComponent,
            String logMessage,
            int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.auditLoggerAsUser(
                        severityGrade, moduleGroup, outcome, uid, swComponent, logMessage, userId);
            }
        } catch (Exception e) {
        }
    }

    public static void logEvent(int eventTag, Object... params) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.logEvent(Process.myPid(), eventTag, convertToStringList(params));
            }
        } catch (Exception e) {
        }
    }

    public static void logEventForComponent(String componentName, int eventTag, Object... params) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.logEventForComponent(
                        Process.myPid(), componentName, eventTag, convertToStringList(params));
            }
        } catch (Exception e) {
        }
    }

    public static void logSecurityLogEvent(int eventTag, List<String> params) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.logEvent(Process.myPid(), eventTag, params);
            }
        } catch (Exception e) {
        }
    }

    public static void logEventAsUser(int userId, int eventTag, Object... params) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.logEventAsUser(
                        userId, Process.myPid(), eventTag, convertToStringList(params));
            }
        } catch (Exception e) {
        }
    }

    private static List<String> convertToStringList(Object... params) {
        List<String> paramsList = new ArrayList<>();
        for (Object param : params) {
            paramsList.add(param.toString());
        }
        return paramsList;
    }

    public static void logAsUser(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int uid,
            String swComponent,
            String logMessage,
            String redactedLogMessage,
            int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.redactedAuditLoggerAsUser(
                        severityGrade,
                        moduleGroup,
                        outcome,
                        uid,
                        swComponent,
                        logMessage,
                        redactedLogMessage,
                        userId);
            }
        } catch (Exception e) {
        }
    }

    public static void logPrivileged(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int pid,
            String swComponent,
            String logMessage) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.auditLoggerPrivileged(
                        severityGrade, moduleGroup, outcome, pid, swComponent, logMessage);
            }
        } catch (Exception e) {
        }
    }

    public static void logPrivileged(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int pid,
            String swComponent,
            String logMessage,
            String redactedLogMessage) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.redactedAuditLoggerPrivileged(
                        severityGrade,
                        moduleGroup,
                        outcome,
                        pid,
                        swComponent,
                        logMessage,
                        redactedLogMessage);
            }
        } catch (Exception e) {
        }
    }

    public static void logPrivilegedAsUser(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int pid,
            String swComponent,
            String logMessage,
            int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.auditLoggerPrivilegedAsUser(
                        severityGrade, moduleGroup, outcome, pid, swComponent, logMessage, userId);
            }
        } catch (Exception e) {
        }
    }

    public static void logPrivilegedAsUser(
            int severityGrade,
            int moduleGroup,
            boolean outcome,
            int pid,
            String swComponent,
            String logMessage,
            String redactedLogMessage,
            int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.redactedAuditLoggerPrivilegedAsUser(
                        severityGrade,
                        moduleGroup,
                        outcome,
                        pid,
                        swComponent,
                        logMessage,
                        redactedLogMessage,
                        userId);
            }
        } catch (Exception e) {
        }
    }

    public static boolean isAuditLogEnabledAsUser(int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isAuditLogEnabledAsUser(userId);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
