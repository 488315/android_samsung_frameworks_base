package com.android.server.enterprise.security;

import android.os.Process;
import android.sec.enterprise.auditlog.AuditLog;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda3 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda3(int i, String str, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = str;
        this.f$2 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = this.f$1;
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password forbidden strings to %s", Integer.valueOf(i), str), this.f$2);
                break;
            default:
                int i2 = this.f$0;
                String str2 = this.f$1;
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password required pattern to %s", Integer.valueOf(i2), str2), this.f$2);
                break;
        }
    }
}
