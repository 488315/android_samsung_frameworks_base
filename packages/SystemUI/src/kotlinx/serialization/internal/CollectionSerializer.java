package kotlinx.serialization.internal;

import java.util.Collection;
import java.util.Iterator;
import kotlinx.serialization.KSerializer;

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
