package com.android.server.appop;

import java.util.function.Consumer;

public final /* synthetic */ class HistoricalRegistry$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((HistoricalRegistry) obj).persistPendingHistory();
    }
}
