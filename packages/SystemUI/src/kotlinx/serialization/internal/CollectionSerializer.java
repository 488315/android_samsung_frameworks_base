package kotlinx.serialization.internal;

import java.util.Collection;
import java.util.Iterator;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CollectionSerializer extends CollectionLikeSerializer {
    public CollectionSerializer(KSerializer kSerializer) {
        super(kSerializer, null);
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final Iterator collectionIterator(Object obj) {
        return ((Collection) obj).iterator();
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((Collection) obj).size();
    }
}
