package com.android.server.pm.verify.domain;

import android.app.ActivityManager;
import android.os.Binder;

public final class DomainVerificationShell {
    public final DomainVerificationService mCallback;

    public DomainVerificationShell(DomainVerificationService domainVerificationService) {
        this.mCallback = domainVerificationService;
    }

    public static int translateUserId(int i, String str) {
        return ActivityManager.handleIncomingUser(
                Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, str, "pm command");
    }

    /* JADX WARN: Code restructure failed: missing block: B:218:0x0331, code lost:

       if (r10.equals("STATE_NO_RESPONSE") == false) goto L183;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean runCommand(
            com.android.modules.utils.BasicShellCommandHandler r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 1704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.verify.domain.DomainVerificationShell.runCommand(com.android.modules.utils.BasicShellCommandHandler,"
                    + " java.lang.String):java.lang.Boolean");
    }
}
