package com.android.systemui.log.table;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DiffableKt$$ExternalSyntheticLambda9 implements Function2 {
    public final /* synthetic */ TableLogBuffer f$0;
    public final /* synthetic */ Ref$BooleanRef f$3;

    public /* synthetic */ DiffableKt$$ExternalSyntheticLambda9(TableLogBuffer tableLogBuffer, Ref$BooleanRef ref$BooleanRef) {
        this.f$0 = tableLogBuffer;
        this.f$3 = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Ref$BooleanRef ref$BooleanRef = this.f$3;
        boolean z = ref$BooleanRef.element;
        this.f$0.logChange("", "operatorName", (String) obj2, z);
        ref$BooleanRef.element = false;
        return Unit.INSTANCE;
    }
}
