package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes2.dex */
public class MarshalQueryableString implements MarshalQueryable<String> {
    private static final boolean DEBUG = false;
    private static final byte NUL = 0;
    private static final String TAG = "MarshalQueryableString";

    private static class PreloadHolder {
        public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

        private PreloadHolder() {
        }
    }

    private class MarshalerString extends Marshaler<String> {
        protected MarshalerString(MarshalQueryableString marshalQueryableString, TypeReference<String> typeReference, int i) {
            super(marshalQueryableString, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(String str, ByteBuffer byteBuffer) {
            byteBuffer.put(str.getBytes(PreloadHolder.UTF8_CHARSET));
            byteBuffer.put((byte) 0);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(String str) {
            return str.getBytes(PreloadHolder.UTF8_CHARSET).length + 1;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public String unmarshal(ByteBuffer byteBuffer) {
            byteBuffer.mark();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                if (byteBuffer.get() == 0) {
                    byteBuffer.reset();
                    int i2 = i + 1;
                    byte[] bArr = new byte[i2];
                    byteBuffer.get(bArr, 0, i2);
                    return new String(bArr, 0, i, PreloadHolder.UTF8_CHARSET);
                }
                i++;
            }
            throw new UnsupportedOperationException("Strings must be null-terminated");
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<String> createMarshaler(TypeReference<String> typeReference, int i) {
        return new MarshalerString(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<String> typeReference, int i) {
        return i == 0 && String.class.equals(typeReference.getType());
    }
}
