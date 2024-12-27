package com.android.server.media;

import com.android.server.media.MediaRouter2ServiceImpl;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda21 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((MediaRouter2ServiceImpl.ManagerRecord) obj).mHasMediaContentControl;
    }
}
