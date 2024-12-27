package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import java.util.function.Consumer;

public final class NavBarStoreImpl$initInteractor$14 implements Consumer {
    public final /* synthetic */ NavBarStoreImpl this$0;

    public NavBarStoreImpl$initInteractor$14(NavBarStoreImpl navBarStoreImpl) {
        this.this$0 = navBarStoreImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NavBarStoreImpl navBarStoreImpl = this.this$0;
        navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnOneHandModeChanged(obj instanceof String ? (String) obj : null));
    }
}
