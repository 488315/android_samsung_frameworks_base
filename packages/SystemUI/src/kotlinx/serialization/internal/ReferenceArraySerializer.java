package kotlinx.serialization.internal;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ReferenceArraySerializer extends CollectionLikeSerializer {
    public final ArrayClassDesc descriptor;

    public ReferenceArraySerializer(KClass kClass, KSerializer kSerializer) {
        super(kSerializer, null);
        this.descriptor = new ArrayClassDesc(kSerializer.getDescriptor());
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final Iterator collectionIterator(Object obj) {
        return new ArrayIterator((Object[]) obj);
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((Object[]) obj).length;
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }
}
