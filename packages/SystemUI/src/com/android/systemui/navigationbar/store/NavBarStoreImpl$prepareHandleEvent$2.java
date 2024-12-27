package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NavBarStoreImpl$prepareHandleEvent$2 implements Consumer {
    public final /* synthetic */ NavBarStoreImpl this$0;

    public NavBarStoreImpl$prepareHandleEvent$2(NavBarStoreImpl navBarStoreImpl) {
        this.this$0 = navBarStoreImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NavBarStoreImpl navBarStoreImpl = this.this$0;
        navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnNewDexModeChanged(((Boolean) obj).booleanValue()));
        NavBarStoreImpl navBarStoreImpl2 = this.this$0;
        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
    }
}
