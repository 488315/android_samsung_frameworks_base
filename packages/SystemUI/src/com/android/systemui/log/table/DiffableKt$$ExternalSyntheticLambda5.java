package com.android.systemui.log.table;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DiffableKt$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TableLogBuffer f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ Ref$BooleanRef f$3;

    public /* synthetic */ DiffableKt$$ExternalSyntheticLambda5(TableLogBuffer tableLogBuffer, String str, String str2, Ref$BooleanRef ref$BooleanRef, int i) {
        this.$r8$classId = i;
        this.f$0 = tableLogBuffer;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                Ref$BooleanRef ref$BooleanRef = this.f$3;
                this.f$0.logChange(this.f$1, this.f$2, booleanValue, ref$BooleanRef.element);
                ref$BooleanRef.element = false;
                break;
            case 1:
                Ref$BooleanRef ref$BooleanRef2 = this.f$3;
                boolean z = ref$BooleanRef2.element;
                this.f$0.logChange(this.f$1, this.f$2, (Integer) obj2, z);
                ref$BooleanRef2.element = false;
                break;
            default:
                String obj3 = ((List) obj2).toString();
                Ref$BooleanRef ref$BooleanRef3 = this.f$3;
                this.f$0.logChange(this.f$1, this.f$2, obj3, ref$BooleanRef3.element);
                ref$BooleanRef3.element = false;
                break;
        }
        return Unit.INSTANCE;
    }
}
