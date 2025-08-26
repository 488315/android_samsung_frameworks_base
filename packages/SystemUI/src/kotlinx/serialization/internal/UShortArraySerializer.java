package kotlinx.serialization.internal;

import kotlin.UShort;
import kotlin.UShortArray;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class UShortArraySerializer extends PrimitiveArraySerializer {
    public static final UShortArraySerializer INSTANCE = new UShortArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private UShortArraySerializer() {
        super(UShortSerializer.INSTANCE);
        int i = UShort.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((UShortArray) obj).storage.length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        short[] sArr = ((UShortArray) obj).storage;
        for (int i2 = 0; i2 < i; i2++) {
            abstractEncoder.encodeElement(i2);
            this.descriptor.getElementDescriptor(i2);
            short s = sArr[i2];
            int i3 = UShort.$r8$clinit;
            abstractEncoder.encodeShort(s);
        }
    }
}
