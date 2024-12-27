package com.android.server.pm;

import java.util.function.ToIntFunction;

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
