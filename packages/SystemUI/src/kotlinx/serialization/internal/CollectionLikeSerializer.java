package kotlinx.serialization.internal;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public abstract class CollectionLikeSerializer extends AbstractCollectionSerializer {
    public final KSerializer elementSerializer;

    public /* synthetic */ CollectionLikeSerializer(KSerializer kSerializer, DefaultConstructorMarker defaultConstructorMarker) {
        this(kSerializer);
    }

    @Override // kotlinx.serialization.KSerializer
    public void serialize(AbstractEncoder abstractEncoder, Object obj) {
        int iCollectionSize = collectionSize(obj);
        getDescriptor();
        Iterator itCollectionIterator = collectionIterator(obj);
        for (int i = 0; i < iCollectionSize; i++) {
            abstractEncoder.encodeSerializableElement(getDescriptor(), i, this.elementSerializer, itCollectionIterator.next());
        }
    }

    private CollectionLikeSerializer(KSerializer kSerializer) {
        super(null);
        this.elementSerializer = kSerializer;
    }
}
