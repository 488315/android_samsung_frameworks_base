package kotlinx.serialization.internal;

import java.util.Iterator;
import java.util.Map;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LinkedHashMapSerializer extends MapLikeSerializer {
    public final LinkedHashMapClassDesc descriptor;

    public LinkedHashMapSerializer(KSerializer kSerializer, KSerializer kSerializer2) {
        super(kSerializer, kSerializer2, null);
        this.descriptor = new LinkedHashMapClassDesc(kSerializer.getDescriptor(), kSerializer2.getDescriptor());
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final Iterator collectionIterator(Object obj) {
        return ((Map) obj).entrySet().iterator();
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((Map) obj).size();
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }
}
