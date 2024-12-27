package com.android.server.pm;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
