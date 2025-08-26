package kotlinx.serialization.internal;

import kotlin.jvm.internal.CharCompanionObject;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class CharArraySerializer extends PrimitiveArraySerializer {
    public static final CharArraySerializer INSTANCE = new CharArraySerializer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CharArraySerializer() {
        super(CharSerializer.INSTANCE);
        int i = CharCompanionObject.$r8$clinit;
    }

    @Override // kotlinx.serialization.internal.AbstractCollectionSerializer
    public final int collectionSize(Object obj) {
        return ((char[]) obj).length;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArraySerializer
    public final void writeContent(AbstractEncoder abstractEncoder, Object obj, int i) {
        char[] cArr = (char[]) obj;
        for (int i2 = 0; i2 < i; i2++) {
            char c = cArr[i2];
            abstractEncoder.encodeElement(i2);
            abstractEncoder.encodeChar(c);
        }
    }
}
