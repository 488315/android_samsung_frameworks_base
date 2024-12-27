package com.android.server.policy;

import java.util.function.Supplier;

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
