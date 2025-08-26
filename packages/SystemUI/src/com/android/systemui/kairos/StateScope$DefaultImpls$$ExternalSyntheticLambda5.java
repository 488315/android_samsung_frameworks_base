package com.android.systemui.kairos;

import java.util.Map;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateScope$DefaultImpls$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ StateScope$DefaultImpls$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return EventsKt.emptyEvents;
            case 1:
                return StateKt.getChanges((State) ((Map.Entry) obj2).getValue());
            default:
                return EventsKt.emptyEvents;
        }
    }
}
