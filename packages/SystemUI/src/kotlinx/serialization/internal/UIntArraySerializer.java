package kotlinx.serialization.internal;

import kotlin.UInt;
import kotlin.UIntArray;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class UIntArraySerializer extends PrimitiveArraySerializer {
    public static final UIntArraySerializer INSTANCE = new UIntArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private UIntArraySerializer() {
        super(UIntSerializer.INSTANCE);
        int i = UInt.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((UIntArray) obj).storage.length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        int[] iArr = ((UIntArray) obj).storage;
        for (int i2 = 0; i2 < i; i2++) {
            abstractEncoder.encodeElement(i2);
            this.descriptor.getElementDescriptor(i2);
            int i3 = iArr[i2];
            int i4 = UInt.$r8$clinit;
            abstractEncoder.encodeInt(i3);
        }
    }
}
