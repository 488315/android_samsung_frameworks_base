package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HeadsUpCoordinatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final GroupLocation getLocation(Map<String, ? extends GroupLocation> map, String str) {
        return map.getOrDefault(str, GroupLocation.Detached);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <R> R modifyHuns(HeadsUpManager headsUpManager, Function1 function1) {
        HunMutatorImpl hunMutatorImpl = new HunMutatorImpl(headsUpManager);
        R r = (R) function1.invoke(hunMutatorImpl);
        hunMutatorImpl.commitModifications();
        return r;
    }
}
