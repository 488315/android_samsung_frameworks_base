package com.android.server.wm;

import com.android.internal.util.function.QuadConsumer;
import com.android.server.am.ProcessRecord;

public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda2
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((ProcessRecord) obj)
                .updateProcessInfo(
                        ((Boolean) obj2).booleanValue(),
                        ((Boolean) obj3).booleanValue(),
                        ((Boolean) obj4).booleanValue());
    }
}
