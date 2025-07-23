package kotlinx.serialization.internal;

import kotlin.jvm.internal.ShortCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ShortArraySerializer extends PrimitiveArraySerializer {
    public static final ShortArraySerializer INSTANCE = new ShortArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ShortArraySerializer() {
        super(ShortSerializer.INSTANCE);
        int i = ShortCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((short[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        short[] sArr = (short[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            short s = sArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeShort(s);
        }
    }
}
