package com.samsung.android.sume.core.filter;

import java.util.function.Consumer;

public final /* synthetic */ class MediaFilterGroupBase$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((MediaFilter) obj).release();
    }
}
