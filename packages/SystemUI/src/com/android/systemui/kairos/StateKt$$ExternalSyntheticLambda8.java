package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.NoScope;
import com.android.systemui.kairos.util.WithPrev;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class StateKt$$ExternalSyntheticLambda8 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StateKt$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return new WithPrev(((TransactionScope) obj).sample((StateInit) this.f$0), obj2);
            default:
                return ((BuildScope$DefaultImpls$$ExternalSyntheticLambda7) this.f$0).invoke(NoScope.INSTANCE, obj2);
        }
    }
}
