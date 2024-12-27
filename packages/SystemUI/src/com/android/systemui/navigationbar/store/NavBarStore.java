package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface NavBarStore {
    default NavBarStateManager getNavStateManager() {
        return ((NavBarStoreImpl) this).getNavStateManager(0);
    }

    default void handleEvent(Object obj, EventTypeFactory.EventType eventType, int i) {
        ((NavBarStoreImpl) this).handleEvent(obj, eventType, i, Unit.INSTANCE);
    }
}
