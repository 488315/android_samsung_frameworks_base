package kotlinx.serialization.internal;

import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
