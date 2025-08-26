package kotlinx.serialization.internal;

import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public final class HashSetClassDesc extends ListLikeDescriptor {
    public HashSetClassDesc(SerialDescriptor serialDescriptor) {
        super(serialDescriptor, null);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return "kotlin.collections.HashSet";
    }
}
