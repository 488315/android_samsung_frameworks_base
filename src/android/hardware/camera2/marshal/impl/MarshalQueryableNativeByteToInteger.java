package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableNativeByteToInteger implements MarshalQueryable<Integer> {
    private static final int UINT8_MASK = 255;

    private class MarshalerNativeByteToInteger extends Marshaler<Integer> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 1;
        }

        protected MarshalerNativeByteToInteger(MarshalQueryableNativeByteToInteger marshalQueryableNativeByteToInteger, TypeReference<Integer> typeReference, int i) {
            super(marshalQueryableNativeByteToInteger, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(Integer num, ByteBuffer byteBuffer) {
            byteBuffer.put((byte) num.intValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public Integer unmarshal(ByteBuffer byteBuffer) {
            return Integer.valueOf(byteBuffer.get() & 255);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<Integer> createMarshaler(TypeReference<Integer> typeReference, int i) {
        return new MarshalerNativeByteToInteger(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<Integer> typeReference, int i) {
        return (Integer.class.equals(typeReference.getType()) || Integer.TYPE.equals(typeReference.getType())) && i == 0;
    }
}
