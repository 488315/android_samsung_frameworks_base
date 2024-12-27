package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.os.Bundle;

import com.android.internal.util.function.QuadConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda9
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((ActivityManagerInternal) obj)
                .killAllBackgroundProcessesExcept(
                        ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (Bundle) obj4);
    }
}
