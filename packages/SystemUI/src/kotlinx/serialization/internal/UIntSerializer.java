package kotlinx.serialization.internal;

import kotlin.UInt;
import kotlin.jvm.internal.IntCompanionObject;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UIntSerializer implements KSerializer {
    public static final UIntSerializer INSTANCE = new UIntSerializer();
    public static final InlineClassDescriptor descriptor;

    static {
        int i = IntCompanionObject.$r8$clinit;
        descriptor = new InlineClassDescriptor("kotlin.UInt", new InlineClassDescriptorKt$InlinePrimitiveDescriptor$1(IntSerializer.INSTANCE));
    }

    private UIntSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        abstractEncoder.encodeInt(((UInt) obj).data);
    }
}
