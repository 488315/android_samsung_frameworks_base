package kotlinx.serialization.internal;

import kotlin.jvm.internal.ByteCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class ByteArraySerializer extends PrimitiveArraySerializer {
    public static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ByteArraySerializer() {
        super(ByteSerializer.INSTANCE);
        int i = ByteCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((byte[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        byte[] bArr = (byte[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            byte b = bArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeByte(b);
        }
    }
}
