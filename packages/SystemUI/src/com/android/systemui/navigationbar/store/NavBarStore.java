package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.store.EventTypeFactory;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface NavBarStore {
    default NavBarStateManager getNavStateManager() {
        return ((NavBarStoreImpl) this).getNavStateManager(0);
    }

    default void handleEvent(Object obj, EventTypeFactory.EventType eventType, int i) {
        ((NavBarStoreImpl) this).handleEvent(obj, eventType, i, Unit.INSTANCE);
    }
}
