package com.android.systemui.searcle;

import android.content.Context;
import android.util.Log;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class OmniAPI$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Context context = OmniAPI.mContext;
        Log.i("OmniAPI", "requestUpdateOmniPackageInfo");
        OmniAPI.THREAD_POOL_EXECUTOR.execute(new OmniAPI$$ExternalSyntheticLambda1());
    }
}
