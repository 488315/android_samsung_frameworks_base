package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableRggbChannelVector implements MarshalQueryable<RggbChannelVector> {
    private static final int SIZE = 16;

    private class MarshalerRggbChannelVector extends Marshaler<RggbChannelVector> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }

        protected MarshalerRggbChannelVector(MarshalQueryableRggbChannelVector marshalQueryableRggbChannelVector, TypeReference<RggbChannelVector> typeReference, int i) {
            super(marshalQueryableRggbChannelVector, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(RggbChannelVector rggbChannelVector, ByteBuffer byteBuffer) {
            for (int i = 0; i < 4; i++) {
                byteBuffer.putFloat(rggbChannelVector.getComponent(i));
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public RggbChannelVector unmarshal(ByteBuffer byteBuffer) {
            return new RggbChannelVector(byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<RggbChannelVector> createMarshaler(TypeReference<RggbChannelVector> typeReference, int i) {
        return new MarshalerRggbChannelVector(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<RggbChannelVector> typeReference, int i) {
        return i == 2 && RggbChannelVector.class.equals(typeReference.getType());
    }
}
