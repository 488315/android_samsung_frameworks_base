package com.android.server.pm;

import android.util.ExceptionUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class PrepareFailure extends PackageManagerException {
    public String mConflictingPackage;
    public String mConflictingPermission;

    public PrepareFailure() {
        super(-116, "Failed to prepare for install.");
    }

    public PrepareFailure(int i, String str) {
        super(i, str);
    }

    public PrepareFailure(Exception exc, String str) {
        super(
                exc instanceof PackageManagerException
                        ? ((PackageManagerException) exc).error
                        : -110,
                ExceptionUtils.getCompleteMessage(str, exc));
    }

    public final void conflictsWithExistingPermission(String str, String str2) {
        this.mConflictingPermission = str;
        this.mConflictingPackage = str2;
    }
}
