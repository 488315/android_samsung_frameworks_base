package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final GroupLocation getLocation(Map<String, ? extends GroupLocation> map, String str) {
        return map.getOrDefault(str, GroupLocation.Detached);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <R> R modifyHuns(HeadsUpManager headsUpManager, Function1 function1) {
        HunMutatorImpl hunMutatorImpl = new HunMutatorImpl(headsUpManager);
        R r = (R) function1.mo779invoke(hunMutatorImpl);
        hunMutatorImpl.commitModifications();
        return r;
    }
}
