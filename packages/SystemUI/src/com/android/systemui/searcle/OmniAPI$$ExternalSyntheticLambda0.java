package com.android.systemui.searcle;

import android.util.Log;
import java.util.function.Consumer;

public final /* synthetic */ class OmniAPI$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Log.i("OmniAPI", "requestUpdateOmniPackageInfo");
        OmniAPI.THREAD_POOL_EXECUTOR.execute(new OmniAPI$$ExternalSyntheticLambda1());
    }
}
