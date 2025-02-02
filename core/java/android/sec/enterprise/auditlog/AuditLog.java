package android.sec.enterprise.auditlog;

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
        lService.AuditLogger(severityGrade, moduleGroup, outcome, uid, swComponent, logMessage);
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
        lService.RedactedAuditLogger(
            severityGrade, moduleGroup, outcome, uid, swComponent, logMessage, redactedLogMessage);
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
        lService.AuditLogger(severityGrade, moduleGroup, outcome, pid, swComponent, logMessage);
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
        lService.AuditLoggerAsUser(
            severityGrade, moduleGroup, outcome, uid, swComponent, logMessage, userId);
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
      String redactedLogMessage,
      int userId) {
    try {
      IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
      if (lService != null) {
        lService.RedactedAuditLoggerAsUser(
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
        lService.AuditLoggerPrivileged(
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
        lService.RedactedAuditLoggerPrivileged(
            severityGrade, moduleGroup, outcome, pid, swComponent, logMessage, redactedLogMessage);
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
        lService.AuditLoggerPrivilegedAsUser(
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
        lService.RedactedAuditLoggerPrivilegedAsUser(
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
