package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3 extends FunctionReferenceImpl implements Function1 {
    public HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(Object obj) {
        super(1, obj, HeadsUpCoordinatorKt.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return (GroupLocation) ((Map) this.receiver).getOrDefault((String) obj, GroupLocation.Detached);
    }
}
