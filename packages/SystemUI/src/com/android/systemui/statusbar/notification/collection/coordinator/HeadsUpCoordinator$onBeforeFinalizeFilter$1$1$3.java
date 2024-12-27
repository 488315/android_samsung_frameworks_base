package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3 extends FunctionReferenceImpl implements Function1 {
    public HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(Object obj) {
        super(1, obj, HeadsUpCoordinatorKt.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final GroupLocation invoke(String str) {
        GroupLocation location;
        location = HeadsUpCoordinatorKt.getLocation((Map) this.receiver, str);
        return location;
    }
}
