package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import kotlin.Unit;

public interface NavBarStore {
    default NavBarStateManager getNavStateManager() {
        return ((NavBarStoreImpl) this).getNavStateManager(0);
    }

    default void handleEvent(Object obj, EventTypeFactory.EventType eventType, int i) {
        ((NavBarStoreImpl) this).handleEvent(obj, eventType, i, Unit.INSTANCE);
    }
}
