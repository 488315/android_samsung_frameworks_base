package com.android.server.devicepolicy;

import android.os.Binder;
import android.os.Process;
import android.sec.enterprise.auditlog.AuditLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
