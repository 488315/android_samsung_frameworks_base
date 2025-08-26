package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class SwitchKt$$ExternalSyntheticLambda3 implements Function3 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SwitchKt$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int i = this.$r8$classId;
        EvalScope evalScope = (EvalScope) obj;
        Events events = (Events) obj2;
        ((Integer) obj3).intValue();
        switch (i) {
        }
        return (EventsImpl) EventsKt.getInit(events).connect(evalScope);
    }
}
