package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;
import com.samsung.android.sdk.routines.v3.data.ActionResult;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;

/* loaded from: classes4.dex */
public final /* synthetic */ class ActionDispatcher$$ExternalSyntheticLambda6 {
    public final /* synthetic */ Bundle f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ActionDispatcher$$ExternalSyntheticLambda6(Bundle bundle, long j, Object obj) {
        this.f$0 = bundle;
        this.f$1 = j;
        this.f$2 = obj;
    }

    public final void actionFinished(ActionResult actionResult) {
        Bundle bundle = this.f$0;
        long j = this.f$1;
        Object obj = this.f$2;
        bundle.putLong(ExtraKey.INSTANCE_ID.a, j);
        int i = actionResult.customErrorCode;
        if (i < 1 || i > 16777215) {
            bundle.putInt(ExtraKey.RESULT_INT.a, actionResult.resultCode.value);
        } else {
            bundle.putInt(ExtraKey.RESULT_TYPE.a, 16);
            bundle.putInt(ExtraKey.RESULT_INT.a, actionResult.customErrorCode);
        }
        ParameterValues parameterValues = actionResult.outputValues;
        if (parameterValues != null) {
            bundle.putString(ExtraKey.OUTPUT_PARAMS.a, parameterValues.toJsonString());
        }
        synchronized (obj) {
            obj.notify();
        }
    }
}
