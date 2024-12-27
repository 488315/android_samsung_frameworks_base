package com.android.server.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GameManagerService$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = GameManagerService.$r8$clinit;
        ApplicationInfo applicationInfo = ((PackageInfo) obj).applicationInfo;
        return applicationInfo != null && applicationInfo.category == 0;
    }
}
