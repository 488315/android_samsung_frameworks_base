package kotlinx.serialization;

import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public interface KSerializer {
    SerialDescriptor getDescriptor();

    void serialize(AbstractEncoder abstractEncoder, Object obj);
}
