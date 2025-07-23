package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableColorSpaceTransform implements MarshalQueryable<ColorSpaceTransform> {
    private static final int ELEMENTS_INT32 = 18;
    private static final int SIZE = 72;

    private class MarshalerColorSpaceTransform extends Marshaler<ColorSpaceTransform> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 72;
        }

        protected MarshalerColorSpaceTransform(MarshalQueryableColorSpaceTransform marshalQueryableColorSpaceTransform, TypeReference<ColorSpaceTransform> typeReference, int i) {
            super(marshalQueryableColorSpaceTransform, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(ColorSpaceTransform colorSpaceTransform, ByteBuffer byteBuffer) {
            int[] iArr = new int[18];
            colorSpaceTransform.copyElements(iArr, 0);
            for (int i = 0; i < 18; i++) {
                byteBuffer.putInt(iArr[i]);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public ColorSpaceTransform unmarshal(ByteBuffer byteBuffer) {
            int[] iArr = new int[18];
            for (int i = 0; i < 18; i++) {
                iArr[i] = byteBuffer.getInt();
            }
            return new ColorSpaceTransform(iArr);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<ColorSpaceTransform> createMarshaler(TypeReference<ColorSpaceTransform> typeReference, int i) {
        return new MarshalerColorSpaceTransform(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<ColorSpaceTransform> typeReference, int i) {
        return i == 5 && ColorSpaceTransform.class.equals(typeReference.getType());
    }
}
