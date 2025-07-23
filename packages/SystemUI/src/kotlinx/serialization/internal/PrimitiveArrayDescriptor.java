package kotlinx.serialization.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PrimitiveArrayDescriptor extends ListLikeDescriptor {
    public final String serialName;

    public PrimitiveArrayDescriptor(SerialDescriptor serialDescriptor) {
        super(serialDescriptor, null);
        this.serialName = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(serialDescriptor.getSerialName(), "Array");
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return this.serialName;
    }
}
