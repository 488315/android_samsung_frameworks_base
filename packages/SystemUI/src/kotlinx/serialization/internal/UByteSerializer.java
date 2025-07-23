package kotlinx.serialization.internal;

import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UByteSerializer implements KSerializer {
    public static final UByteSerializer INSTANCE = new UByteSerializer();
    public static final InlineClassDescriptor descriptor;

    static {
        int i = ByteCompanionObject.$r8$clinit;
        descriptor = new InlineClassDescriptor("kotlin.UByte", new InlineClassDescriptorKt$InlinePrimitiveDescriptor$1(ByteSerializer.INSTANCE));
    }

    private UByteSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        abstractEncoder.encodeByte(((UByte) obj).data);
    }
}
