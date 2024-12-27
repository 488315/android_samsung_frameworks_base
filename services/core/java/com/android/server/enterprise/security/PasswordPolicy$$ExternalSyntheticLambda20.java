package com.android.server.enterprise.security;

import android.os.Process;
import android.sec.enterprise.auditlog.AuditLog;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda20
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda20(
            int i, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = i;
        this.f$1 = i2;
        this.f$2 = i3;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                int i2 = this.f$1;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "PasswordPolicy",
                        String.format(
                                "Admin %d has changed password maximum character sequence length to"
                                    + " %d",
                                Integer.valueOf(i), Integer.valueOf(i2)),
                        this.f$2);
                break;
            case 1:
                int i3 = this.f$0;
                int i4 = this.f$1;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "PasswordPolicy",
                        String.format(
                                "Admin %d has changed password minimum number of changed characters"
                                    + " to %d",
                                Integer.valueOf(i3), Integer.valueOf(i4)),
                        this.f$2);
                break;
            case 2:
                int i5 = this.f$0;
                int i6 = this.f$1;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "PasswordPolicy",
                        String.format(
                                "Admin %d has changed password maximum character occurrences to %d",
                                Integer.valueOf(i5), Integer.valueOf(i6)),
                        this.f$2);
                break;
            case 3:
                int i7 = this.f$0;
                int i8 = this.f$1;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "PasswordPolicy",
                        String.format(
                                "Admin %d has changed maximum failed passwords for disable to %d",
                                Integer.valueOf(i7), Integer.valueOf(i8)),
                        this.f$2);
                break;
            default:
                int i9 = this.f$0;
                int i10 = this.f$1;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "PasswordPolicy",
                        String.format(
                                "Admin %d has changed password maximum numeric sequence to %d",
                                Integer.valueOf(i9), Integer.valueOf(i10)),
                        this.f$2);
                break;
        }
    }
}
