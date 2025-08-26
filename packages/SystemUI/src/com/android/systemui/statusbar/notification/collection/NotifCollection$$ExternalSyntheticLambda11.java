package com.android.systemui.statusbar.notification.collection;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import java.util.Collections;
import java.util.Map;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda11 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return ((Map) this.f$0).keySet();
            default:
                return Collections.unmodifiableSet(((ArrayMap) ((GroupCoalescer) this.f$0).mCoalescedEvents).keySet());
        }
    }
}
