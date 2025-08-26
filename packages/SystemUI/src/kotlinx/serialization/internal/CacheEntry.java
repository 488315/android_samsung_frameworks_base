package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public final class CacheEntry {
    public final KSerializer serializer;

    public CacheEntry(KSerializer kSerializer) {
        this.serializer = kSerializer;
    }
}
