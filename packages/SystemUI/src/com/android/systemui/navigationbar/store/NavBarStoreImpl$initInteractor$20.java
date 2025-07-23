package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NavBarStoreImpl$initInteractor$20 implements Consumer {
    public final /* synthetic */ NavBarStoreImpl this$0;

    public NavBarStoreImpl$initInteractor$20(NavBarStoreImpl navBarStoreImpl) {
        this.this$0 = navBarStoreImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NavBarStateManager navBarStateManager = (NavBarStateManager) this.this$0.navStateManager.get(1);
        if (navBarStateManager != null) {
            NavBarStoreImpl navBarStoreImpl = this.this$0;
            if (((NavBarStateManagerImpl) navBarStateManager).isGestureMode()) {
                navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnCoverRotationChanged(((Number) obj).intValue()), 1);
            }
        }
    }
}
