package kotlinx.serialization.internal;

import java.util.Iterator;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PrimitiveArraySerializer extends CollectionLikeSerializer {
    public final PrimitiveArrayDescriptor descriptor;

    public PrimitiveArraySerializer(KSerializer kSerializer) {
        super(kSerializer, null);
        this.descriptor = new PrimitiveArrayDescriptor(kSerializer.getDescriptor());
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final Iterator collectionIterator(Object obj) {
        throw new IllegalStateException("This method lead to boxing and must not be used, use writeContents instead");
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // kotlinx.serialization.internal.CollectionLikeSerializer, kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        writeContent(abstractEncoder, obj, collectionSize(obj));
    }

    public abstract void writeContent(AbstractEncoder abstractEncoder, Object obj, int i);
}
