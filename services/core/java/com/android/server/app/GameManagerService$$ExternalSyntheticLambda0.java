package com.android.server.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.function.Predicate;

public final /* synthetic */ class GameManagerService$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = GameManagerService.$r8$clinit;
        ApplicationInfo applicationInfo = ((PackageInfo) obj).applicationInfo;
        return applicationInfo != null && applicationInfo.category == 0;
    }
}
