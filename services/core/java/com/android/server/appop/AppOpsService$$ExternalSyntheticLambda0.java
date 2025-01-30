package com.android.server.appop;

import com.android.internal.util.function.TriConsumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda0 implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        ((AppOpsService) obj).notifyWatchersOfChange(((Integer) obj2).intValue(), ((Integer) obj3).intValue());
    }
}
