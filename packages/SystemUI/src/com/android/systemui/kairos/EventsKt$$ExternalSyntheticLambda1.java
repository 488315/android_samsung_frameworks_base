package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class EventsKt$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function2 f$0;

    public /* synthetic */ EventsKt$$ExternalSyntheticLambda1(int i, Function2 function2) {
        this.$r8$classId = i;
        this.f$0 = function2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int i = this.$r8$classId;
        EvalScope evalScope = (EvalScope) obj;
        ((Integer) obj3).intValue();
        switch (i) {
        }
        return this.f$0.invoke(evalScope, obj2);
    }
}
