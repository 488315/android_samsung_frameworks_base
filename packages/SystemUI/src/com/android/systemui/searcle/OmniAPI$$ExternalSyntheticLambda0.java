package com.android.systemui.searcle;

import android.util.Log;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class OmniAPI$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Log.i("OmniAPI", "requestUpdateOmniPackageInfo");
        OmniAPI.THREAD_POOL_EXECUTOR.execute(new OmniAPI$$ExternalSyntheticLambda1());
    }
}
