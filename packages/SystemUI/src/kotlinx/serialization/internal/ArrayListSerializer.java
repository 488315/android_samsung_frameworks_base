package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class ArrayListSerializer extends CollectionSerializer {
    public final ArrayListClassDesc descriptor;

    public ArrayListSerializer(KSerializer kSerializer) {
        super(kSerializer);
        this.descriptor = new ArrayListClassDesc(kSerializer.getDescriptor());
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }
}
