package kotlinx.serialization.internal;

import kotlin.jvm.internal.LongCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LongArraySerializer extends PrimitiveArraySerializer {
    public static final LongArraySerializer INSTANCE = new LongArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private LongArraySerializer() {
        super(LongSerializer.INSTANCE);
        int i = LongCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((long[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        long[] jArr = (long[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeLong(j);
        }
    }
}
