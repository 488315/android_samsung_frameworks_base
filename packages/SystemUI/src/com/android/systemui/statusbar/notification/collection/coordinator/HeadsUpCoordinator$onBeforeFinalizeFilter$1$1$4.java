package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4 extends FunctionReferenceImpl implements Function1 {
    public HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4(Object obj) {
        super(1, obj, HeadsUpCoordinatorKt.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final GroupLocation invoke(String str) {
        GroupLocation location;
        location = HeadsUpCoordinatorKt.getLocation((Map) this.receiver, str);
        return location;
    }
}
