package kotlinx.serialization.internal;

import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class ArrayListClassDesc extends ListLikeDescriptor {
    public ArrayListClassDesc(SerialDescriptor serialDescriptor) {
        super(serialDescriptor, null);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return "kotlin.collections.ArrayList";
    }
}
