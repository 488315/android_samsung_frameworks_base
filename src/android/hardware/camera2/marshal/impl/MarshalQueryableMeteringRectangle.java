package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableMeteringRectangle implements MarshalQueryable<MeteringRectangle> {
    private static final int SIZE = 20;

    private class MarshalerMeteringRectangle extends Marshaler<MeteringRectangle> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }

        protected MarshalerMeteringRectangle(MarshalQueryableMeteringRectangle marshalQueryableMeteringRectangle, TypeReference<MeteringRectangle> typeReference, int i) {
            super(marshalQueryableMeteringRectangle, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(MeteringRectangle meteringRectangle, ByteBuffer byteBuffer) {
            int x = meteringRectangle.getX();
            int y = meteringRectangle.getY();
            int width = meteringRectangle.getWidth() + x;
            int height = meteringRectangle.getHeight() + y;
            int meteringWeight = meteringRectangle.getMeteringWeight();
            byteBuffer.putInt(x);
            byteBuffer.putInt(y);
            byteBuffer.putInt(width);
            byteBuffer.putInt(height);
            byteBuffer.putInt(meteringWeight);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public MeteringRectangle unmarshal(ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            int i3 = byteBuffer.getInt();
            return new MeteringRectangle(i, i2, i3 - i, byteBuffer.getInt() - i2, byteBuffer.getInt());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<MeteringRectangle> createMarshaler(TypeReference<MeteringRectangle> typeReference, int i) {
        return new MarshalerMeteringRectangle(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<MeteringRectangle> typeReference, int i) {
        return i == 1 && MeteringRectangle.class.equals(typeReference.getType());
    }
}
