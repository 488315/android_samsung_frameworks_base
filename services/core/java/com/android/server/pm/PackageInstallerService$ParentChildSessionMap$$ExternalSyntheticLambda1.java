package com.android.server.pm;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda1
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        PackageInstallerSession packageInstallerSession = (PackageInstallerSession) obj;
        if (packageInstallerSession != null) {
            return packageInstallerSession.sessionId;
        }
        return -1;
    }
}
