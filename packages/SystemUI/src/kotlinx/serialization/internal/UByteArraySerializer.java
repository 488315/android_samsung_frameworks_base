package kotlinx.serialization.internal;

import kotlin.UByte;
import kotlin.UByteArray;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UByteArraySerializer extends PrimitiveArraySerializer {
    public static final UByteArraySerializer INSTANCE = new UByteArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private UByteArraySerializer() {
        super(UByteSerializer.INSTANCE);
        int i = UByte.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((UByteArray) obj).storage.length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        byte[] bArr = ((UByteArray) obj).storage;
        for (int i2 = 0; i2 < i; i2++) {
            abstractEncoder.encodeElement(i2);
            this.descriptor.getElementDescriptor(i2);
            byte b = bArr[i2];
            int i3 = UByte.$r8$clinit;
            abstractEncoder.encodeByte(b);
        }
    }
}
