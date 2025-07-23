package kotlinx.serialization.internal;

import kotlin.UShort;
import kotlin.jvm.internal.ShortCompanionObject;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UShortSerializer implements KSerializer {
    public static final UShortSerializer INSTANCE = new UShortSerializer();
    public static final InlineClassDescriptor descriptor;

    static {
        int i = ShortCompanionObject.$r8$clinit;
        descriptor = new InlineClassDescriptor("kotlin.UShort", new InlineClassDescriptorKt$InlinePrimitiveDescriptor$1(ShortSerializer.INSTANCE));
    }

    private UShortSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        abstractEncoder.encodeShort(((UShort) obj).data);
    }
}
