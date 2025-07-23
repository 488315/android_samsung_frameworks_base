package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.BlackLevelPattern;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableBlackLevelPattern implements MarshalQueryable<BlackLevelPattern> {
    private static final int SIZE = 16;

    private class MarshalerBlackLevelPattern extends Marshaler<BlackLevelPattern> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }

        protected MarshalerBlackLevelPattern(MarshalQueryableBlackLevelPattern marshalQueryableBlackLevelPattern, TypeReference<BlackLevelPattern> typeReference, int i) {
            super(marshalQueryableBlackLevelPattern, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(BlackLevelPattern blackLevelPattern, ByteBuffer byteBuffer) {
            for (int i = 0; i < 2; i++) {
                for (int i2 = 0; i2 < 2; i2++) {
                    byteBuffer.putInt(blackLevelPattern.getOffsetForIndex(i2, i));
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public BlackLevelPattern unmarshal(ByteBuffer byteBuffer) {
            int[] iArr = new int[4];
            for (int i = 0; i < 4; i++) {
                iArr[i] = byteBuffer.getInt();
            }
            return new BlackLevelPattern(iArr);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<BlackLevelPattern> createMarshaler(TypeReference<BlackLevelPattern> typeReference, int i) {
        return new MarshalerBlackLevelPattern(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<BlackLevelPattern> typeReference, int i) {
        return i == 1 && BlackLevelPattern.class.equals(typeReference.getType());
    }
}
