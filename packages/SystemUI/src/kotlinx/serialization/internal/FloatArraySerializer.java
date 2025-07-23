package kotlinx.serialization.internal;

import kotlin.jvm.internal.FloatCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FloatArraySerializer extends PrimitiveArraySerializer {
    public static final FloatArraySerializer INSTANCE = new FloatArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private FloatArraySerializer() {
        super(FloatSerializer.INSTANCE);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((float[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        float[] fArr = (float[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            float f = fArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeFloat(f);
        }
    }
}
