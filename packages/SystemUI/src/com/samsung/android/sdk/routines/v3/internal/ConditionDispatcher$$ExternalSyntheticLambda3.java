package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;
import com.samsung.android.sdk.routines.v3.data.ConditionValidity;
import com.samsung.android.sdk.routines.v3.data.SatisfactionStatus;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class ConditionDispatcher$$ExternalSyntheticLambda3 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Bundle f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ConditionDispatcher$$ExternalSyntheticLambda3(Bundle bundle, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bundle;
        this.f$1 = obj;
    }

    public final void setResponse(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Bundle bundle = this.f$0;
                Object obj2 = this.f$1;
                bundle.putInt(ExtraKey.RESULT_INT.a, ((SatisfactionStatus) obj).a);
                synchronized (obj2) {
                    obj2.notify();
                }
                return;
            default:
                Bundle bundle2 = this.f$0;
                Object obj3 = this.f$1;
                ConditionValidity conditionValidity = (ConditionValidity) obj;
                ConditionValidity.ValidityType validityType = conditionValidity.type;
                if (validityType == ConditionValidity.ValidityType.CUSTOM_ERROR) {
                    bundle2.putInt(ExtraKey.RESULT_TYPE.a, 16);
                    bundle2.putInt(ExtraKey.RESULT_INT.a, conditionValidity.validityCode);
                } else if (validityType == ConditionValidity.ValidityType.CUSTOM_WARNING) {
                    bundle2.putInt(ExtraKey.RESULT_TYPE.a, 32);
                    bundle2.putInt(ExtraKey.RESULT_INT.a, conditionValidity.validityCode);
                } else {
                    bundle2.putInt(ExtraKey.RESULT_INT.a, conditionValidity.validityCode);
                }
                synchronized (obj3) {
                    obj3.notify();
                }
                return;
        }
    }
}
