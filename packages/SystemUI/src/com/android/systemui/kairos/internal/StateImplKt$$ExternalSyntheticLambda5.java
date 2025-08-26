package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda1;
import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda11;
import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda4;
import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda9;
import java.util.List;
import kotlin.Function;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateImplKt$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function f$0;

    public /* synthetic */ StateImplKt$$ExternalSyntheticLambda5(Function function, int i) {
        this.$r8$classId = i;
        this.f$0 = function;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                List list = (List) obj2;
                Object obj3 = list.get(0);
                Object obj4 = list.get(1);
                return ((CombineKt$$ExternalSyntheticLambda1) this.f$0).invoke((EvalScope) obj, obj3, obj4);
            case 1:
                List list2 = (List) obj2;
                Object obj5 = list2.get(0);
                Object obj6 = list2.get(1);
                Object obj7 = list2.get(2);
                return ((CombineKt$$ExternalSyntheticLambda4) this.f$0).invoke((EvalScope) obj, obj5, obj6, obj7);
            case 2:
                List list3 = (List) obj2;
                Object obj8 = list3.get(0);
                Object obj9 = list3.get(1);
                Object obj10 = list3.get(2);
                Object obj11 = list3.get(3);
                Object obj12 = list3.get(4);
                return ((CombineKt$$ExternalSyntheticLambda9) this.f$0).invoke((EvalScope) obj, obj8, obj9, obj10, obj11, obj12);
            default:
                List list4 = (List) obj2;
                Object obj13 = list4.get(0);
                Object obj14 = list4.get(1);
                Object obj15 = list4.get(2);
                Object obj16 = list4.get(3);
                return ((CombineKt$$ExternalSyntheticLambda11) this.f$0).invoke((EvalScope) obj, obj13, obj14, obj15, obj16);
        }
    }
}
