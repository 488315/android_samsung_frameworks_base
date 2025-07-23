package kotlinx.serialization.descriptors;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SerialDescriptorKt$special$$inlined$Iterable$2 implements Iterable, KMappedMarker {
    public final /* synthetic */ SerialDescriptor $this_elementNames$inlined;

    public SerialDescriptorKt$special$$inlined$Iterable$2(SerialDescriptor serialDescriptor) {
        this.$this_elementNames$inlined = serialDescriptor;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new SerialDescriptorKt$elementNames$1$1(this.$this_elementNames$inlined);
    }
}
