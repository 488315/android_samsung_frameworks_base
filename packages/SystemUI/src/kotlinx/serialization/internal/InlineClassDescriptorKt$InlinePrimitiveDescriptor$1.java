package kotlinx.serialization.internal;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class InlineClassDescriptorKt$InlinePrimitiveDescriptor$1 implements GeneratedSerializer {
    public final /* synthetic */ KSerializer $primitiveSerializer;

    public InlineClassDescriptorKt$InlinePrimitiveDescriptor$1(KSerializer kSerializer) {
        this.$primitiveSerializer = kSerializer;
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        throw new IllegalStateException("unsupported");
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        throw new IllegalStateException("unsupported");
    }
}
