package com.android.server.blob;

import java.util.function.Predicate;

public final /* synthetic */ class BlobMetadata$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((BlobMetadata.Leasee) obj).isStillValid();
    }
}
