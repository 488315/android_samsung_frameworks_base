package com.android.server.people.prediction;

import java.util.Comparator;

public final /* synthetic */ class ShareTargetPredictor$$ExternalSyntheticLambda2
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return -Float.compare(
                ((ShareTargetPredictor.ShareTarget) obj).getScore(),
                ((ShareTargetPredictor.ShareTarget) obj2).getScore());
    }
}
