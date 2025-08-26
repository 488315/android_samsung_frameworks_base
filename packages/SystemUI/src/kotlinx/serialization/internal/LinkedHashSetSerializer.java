package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class LinkedHashSetSerializer extends CollectionSerializer {
    public final LinkedHashSetClassDesc descriptor;

    public LinkedHashSetSerializer(KSerializer kSerializer) {
        super(kSerializer);
        this.descriptor = new LinkedHashSetClassDesc(kSerializer.getDescriptor());
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }
}
