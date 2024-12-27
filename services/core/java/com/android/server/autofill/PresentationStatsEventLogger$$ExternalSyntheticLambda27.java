package com.android.server.autofill;

import com.android.server.autofill.PresentationStatsEventLogger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda27 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PresentationStatsEventLogger.PresentationStatsEventInternal) obj).shouldResetShownCount = true;
    }
}
