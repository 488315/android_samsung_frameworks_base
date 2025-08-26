package kotlinx.serialization.internal;

import kotlin.jvm.internal.IntCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class IntArraySerializer extends PrimitiveArraySerializer {
    public static final IntArraySerializer INSTANCE = new IntArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private IntArraySerializer() {
        super(IntSerializer.INSTANCE);
        int i = IntCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((int[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        int[] iArr = (int[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeInt(i3);
        }
    }
}
