package kotlinx.serialization.internal;

import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class ArrayClassDesc extends ListLikeDescriptor {
    public ArrayClassDesc(SerialDescriptor serialDescriptor) {
        super(serialDescriptor, null);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return "kotlin.Array";
    }
}
