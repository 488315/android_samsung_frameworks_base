package com.android.server.media;

import java.util.function.Predicate;

public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda21
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((MediaRouter2ServiceImpl.ManagerRecord) obj).mHasMediaContentControl;
    }
}
