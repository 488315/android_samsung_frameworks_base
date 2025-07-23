package kotlinx.serialization.internal;

import kotlin.ULong;
import kotlin.jvm.internal.LongCompanionObject;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ULongSerializer implements KSerializer {
    public static final ULongSerializer INSTANCE = new ULongSerializer();
    public static final InlineClassDescriptor descriptor;

    static {
        int i = LongCompanionObject.$r8$clinit;
        descriptor = new InlineClassDescriptor("kotlin.ULong", new InlineClassDescriptorKt$InlinePrimitiveDescriptor$1(LongSerializer.INSTANCE));
    }

    private ULongSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        abstractEncoder.encodeLong(((ULong) obj).data);
    }
}
