package com.android.server.policy;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PhoneWindowManager$Injector$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ PhoneWindowManager.Injector f$0;

    public /* synthetic */ PhoneWindowManager$Injector$$ExternalSyntheticLambda0(
            PhoneWindowManager.Injector injector) {
        this.f$0 = injector;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        PhoneWindowManager.Injector injector = this.f$0;
        return new GlobalActions(injector.mContext, injector.mWindowManagerFuncs);
    }
}
