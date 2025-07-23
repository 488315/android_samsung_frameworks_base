package com.android.systemui.searcle;

import android.content.Context;
import android.util.Log;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class OmniAPI$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Context context = OmniAPI.mContext;
        Log.i("OmniAPI", "requestUpdateOmniPackageInfo");
        OmniAPI.THREAD_POOL_EXECUTOR.execute(new OmniAPI$$ExternalSyntheticLambda1());
    }
}
