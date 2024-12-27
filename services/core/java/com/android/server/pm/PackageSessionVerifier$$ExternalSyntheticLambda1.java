package com.android.server.pm;

import java.util.function.Predicate;

public final /* synthetic */ class PackageSessionVerifier$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ PackageSessionVerifier$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        StagingManager.StagedSession stagedSession = (StagingManager.StagedSession) obj;
        switch (i) {
        }
        return str.equals(PackageInstallerSession.this.getPackageName());
    }
}
