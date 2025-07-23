package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableBoolean implements MarshalQueryable<Boolean> {

    private class MarshalerBoolean extends Marshaler<Boolean> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 1;
        }

        protected MarshalerBoolean(MarshalQueryableBoolean marshalQueryableBoolean, TypeReference<Boolean> typeReference, int i) {
            super(marshalQueryableBoolean, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(Boolean bool, ByteBuffer byteBuffer) {
            byteBuffer.put(bool.booleanValue() ? (byte) 1 : (byte) 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public Boolean unmarshal(ByteBuffer byteBuffer) {
            return Boolean.valueOf(byteBuffer.get() != 0);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<Boolean> createMarshaler(TypeReference<Boolean> typeReference, int i) {
        return new MarshalerBoolean(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<Boolean> typeReference, int i) {
        return (Boolean.class.equals(typeReference.getType()) || Boolean.TYPE.equals(typeReference.getType())) && i == 0;
    }
}
