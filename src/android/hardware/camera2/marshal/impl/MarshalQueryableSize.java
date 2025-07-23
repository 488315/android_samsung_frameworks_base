package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Size;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableSize implements MarshalQueryable<Size> {
    private static final int SIZE = 8;

    private class MarshalerSize extends Marshaler<Size> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 8;
        }

        protected MarshalerSize(MarshalQueryableSize marshalQueryableSize, TypeReference<Size> typeReference, int i) {
            super(marshalQueryableSize, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(Size size, ByteBuffer byteBuffer) {
            byteBuffer.putInt(size.getWidth());
            byteBuffer.putInt(size.getHeight());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public Size unmarshal(ByteBuffer byteBuffer) {
            return new Size(byteBuffer.getInt(), byteBuffer.getInt());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<Size> createMarshaler(TypeReference<Size> typeReference, int i) {
        return new MarshalerSize(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<Size> typeReference, int i) {
        return i == 1 && Size.class.equals(typeReference.getType());
    }
}
