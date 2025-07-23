package kotlinx.serialization.internal;

import kotlin.jvm.internal.BooleanCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BooleanArraySerializer extends PrimitiveArraySerializer {
    public static final BooleanArraySerializer INSTANCE = new BooleanArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private BooleanArraySerializer() {
        super(BooleanSerializer.INSTANCE);
        int i = BooleanCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((boolean[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        boolean[] zArr = (boolean[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            boolean z = zArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeBoolean(z);
        }
    }
}
