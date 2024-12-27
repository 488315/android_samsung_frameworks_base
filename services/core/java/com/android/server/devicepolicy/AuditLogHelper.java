package com.android.server.devicepolicy;

import android.os.Binder;
import android.os.Process;
import android.sec.enterprise.auditlog.AuditLog;

public final class AuditLogHelper {
    public static void makeAuditLogGroupSecurity(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "AuditLogHelper", str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
