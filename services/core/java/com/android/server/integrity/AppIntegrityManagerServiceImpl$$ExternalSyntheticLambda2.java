package com.android.server.integrity;

import java.nio.file.Path;
import java.util.function.Function;

public final /* synthetic */ class AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Path) obj).toAbsolutePath().toString();
    }
}
