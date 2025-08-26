package kotlinx.serialization.internal;

import kotlin.jvm.internal.DoubleCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class DoubleArraySerializer extends PrimitiveArraySerializer {
    public static final DoubleArraySerializer INSTANCE = new DoubleArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private DoubleArraySerializer() {
        super(DoubleSerializer.INSTANCE);
        int i = DoubleCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((double[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        double[] dArr = (double[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            double d = dArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeDouble(d);
        }
    }
}
