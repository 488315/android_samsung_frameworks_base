package kotlinx.serialization.internal;

import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public abstract class MapLikeSerializer extends AbstractCollectionSerializer {
    public final KSerializer keySerializer;
    public final KSerializer valueSerializer;

    public /* synthetic */ MapLikeSerializer(KSerializer kSerializer, KSerializer kSerializer2, DefaultConstructorMarker defaultConstructorMarker) {
        this(kSerializer, kSerializer2);
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        collectionSize(obj);
        getDescriptor();
        Iterator itCollectionIterator = collectionIterator(obj);
        int i = 0;
        while (itCollectionIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) itCollectionIterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            int i2 = i + 1;
            abstractEncoder.encodeSerializableElement(getDescriptor(), i, this.keySerializer, key);
            i += 2;
            abstractEncoder.encodeSerializableElement(getDescriptor(), i2, this.valueSerializer, value);
        }
    }

    private MapLikeSerializer(KSerializer kSerializer, KSerializer kSerializer2) {
        super(null);
        this.keySerializer = kSerializer;
        this.valueSerializer = kSerializer2;
    }
}
