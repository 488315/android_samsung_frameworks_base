package com.android.server.pm;

import java.util.function.ToLongFunction;

public final /* synthetic */
class PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda0
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        PackageInstallerSession packageInstallerSession = (PackageInstallerSession) obj;
        if (packageInstallerSession != null) {
            return packageInstallerSession.createdMillis;
        }
        return -1L;
    }
}
