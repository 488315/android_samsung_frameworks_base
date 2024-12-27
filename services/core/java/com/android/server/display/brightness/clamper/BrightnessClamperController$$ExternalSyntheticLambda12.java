package com.android.server.display.brightness.clamper;

import java.util.Comparator;

public final /* synthetic */ class BrightnessClamperController$$ExternalSyntheticLambda12
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Float.compare(
                ((BrightnessClamper) obj).mBrightnessCap,
                ((BrightnessClamper) obj2).mBrightnessCap);
    }
}
