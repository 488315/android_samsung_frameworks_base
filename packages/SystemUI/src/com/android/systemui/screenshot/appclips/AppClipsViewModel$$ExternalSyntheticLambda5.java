package com.android.systemui.screenshot.appclips;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CollectionFuture;
import com.google.common.util.concurrent.ListenableFuture;

/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsViewModel$$ExternalSyntheticLambda5 implements AsyncFunction {
    @Override // com.google.common.util.concurrent.AsyncFunction
    public final ListenableFuture apply(Object obj) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf((Iterable) obj), true);
    }
}
