package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.SizeF;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableSizeF implements MarshalQueryable<SizeF> {
    private static final int SIZE = 8;

    private class MarshalerSizeF extends Marshaler<SizeF> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 8;
        }

        protected MarshalerSizeF(MarshalQueryableSizeF marshalQueryableSizeF, TypeReference<SizeF> typeReference, int i) {
            super(marshalQueryableSizeF, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(SizeF sizeF, ByteBuffer byteBuffer) {
            byteBuffer.putFloat(sizeF.getWidth());
            byteBuffer.putFloat(sizeF.getHeight());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public SizeF unmarshal(ByteBuffer byteBuffer) {
            return new SizeF(byteBuffer.getFloat(), byteBuffer.getFloat());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<SizeF> createMarshaler(TypeReference<SizeF> typeReference, int i) {
        return new MarshalerSizeF(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<SizeF> typeReference, int i) {
        return i == 2 && SizeF.class.equals(typeReference.getType());
    }
}
