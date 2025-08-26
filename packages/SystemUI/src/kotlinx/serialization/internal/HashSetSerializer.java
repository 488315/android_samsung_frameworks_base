package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class HashSetSerializer extends CollectionSerializer {
    public final HashSetClassDesc descriptor;

    public HashSetSerializer(KSerializer kSerializer) {
        super(kSerializer);
        this.descriptor = new HashSetClassDesc(kSerializer.getDescriptor());
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }
}
