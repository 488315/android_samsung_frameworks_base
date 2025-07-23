package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
