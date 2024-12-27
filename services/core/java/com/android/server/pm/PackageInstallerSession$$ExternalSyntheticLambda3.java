package com.android.server.pm;

import java.util.function.Predicate;

public final /* synthetic */ class PackageInstallerSession$$ExternalSyntheticLambda3
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PackageInstallerSession$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((PackageInstallerSession) obj).isApexSession();
            case 1:
                return !((PackageInstallerSession) obj).isApexSession();
            default:
                return ((PackageInstallerSession.StagedSession)
                                ((StagingManager.StagedSession) obj))
                        .isApexSession();
        }
    }
}
