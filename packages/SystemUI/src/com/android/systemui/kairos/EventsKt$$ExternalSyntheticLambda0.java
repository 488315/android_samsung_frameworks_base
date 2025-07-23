package com.android.systemui.kairos;

import com.android.systemui.kairos.util.Maybe;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EventsKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function2 f$0;

    public /* synthetic */ EventsKt$$ExternalSyntheticLambda0(int i, Function2 function2) {
        this.$r8$classId = i;
        this.f$0 = function2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TransactionScope transactionScope = (TransactionScope) obj;
        switch (this.$r8$classId) {
            case 0:
                Object invoke = this.f$0.invoke(transactionScope, obj2);
                if (invoke == null) {
                    return Maybe.Absent.INSTANCE;
                }
                Maybe.Companion.getClass();
                return Maybe.Present.m2573boximpl(invoke);
            default:
                ((MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4) this.f$0).invoke(transactionScope, obj2);
                return obj2;
        }
    }
}
