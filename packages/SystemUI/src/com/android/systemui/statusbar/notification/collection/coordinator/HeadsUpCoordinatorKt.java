package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Map;
import kotlin.jvm.functions.Function1;

public final class HeadsUpCoordinatorKt {
    public static final GroupLocation getLocation(Map<String, ? extends GroupLocation> map, String str) {
        return map.getOrDefault(str, GroupLocation.Detached);
    }

    public static final <R> R modifyHuns(HeadsUpManager headsUpManager, Function1 function1) {
        HunMutatorImpl hunMutatorImpl = new HunMutatorImpl(headsUpManager);
        R r = (R) function1.invoke(hunMutatorImpl);
        hunMutatorImpl.commitModifications();
        return r;
    }
}
