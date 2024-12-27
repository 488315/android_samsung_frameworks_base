package com.android.server.autofill;

import java.util.function.Consumer;

public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda27
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PresentationStatsEventLogger.PresentationStatsEventInternal) obj).shouldResetShownCount =
                true;
    }
}
