package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3 extends FunctionReferenceImpl implements Function1 {
    public HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(Object obj) {
        super(1, obj, HeadsUpCoordinatorKt.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
    public final GroupLocation mo779invoke(String str) {
        GroupLocation location;
        location = HeadsUpCoordinatorKt.getLocation((Map) this.receiver, str);
        return location;
    }
}
