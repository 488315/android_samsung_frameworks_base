package kotlinx.serialization.internal;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CollectionLikeSerializer extends AbstractCollectionSerializer {
    public final KSerializer elementSerializer;

    public /* synthetic */ CollectionLikeSerializer(KSerializer kSerializer, DefaultConstructorMarker defaultConstructorMarker) {
        this(kSerializer);
    }

    @Override // kotlinx.serialization.KSerializer
    public void serialize(AbstractEncoder abstractEncoder, Object obj) {
        int collectionSize = collectionSize(obj);
        getDescriptor();
        Iterator collectionIterator = collectionIterator(obj);
        for (int i = 0; i < collectionSize; i++) {
            abstractEncoder.encodeSerializableElement(getDescriptor(), i, this.elementSerializer, collectionIterator.next());
        }
    }

    private CollectionLikeSerializer(KSerializer kSerializer) {
        super(null);
        this.elementSerializer = kSerializer;
    }
}
