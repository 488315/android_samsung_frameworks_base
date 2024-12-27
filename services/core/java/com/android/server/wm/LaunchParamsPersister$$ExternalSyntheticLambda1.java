package com.android.server.wm;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LaunchParamsPersister$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LaunchParamsPersister f$0;

    public /* synthetic */ LaunchParamsPersister$$ExternalSyntheticLambda1(
            LaunchParamsPersister launchParamsPersister, int i) {
        this.$r8$classId = i;
        this.f$0 = launchParamsPersister;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        this.f$0.getClass();
        switch (i) {
        }
        return new LaunchParamsPersister.PersistableLaunchParams();
    }
}
