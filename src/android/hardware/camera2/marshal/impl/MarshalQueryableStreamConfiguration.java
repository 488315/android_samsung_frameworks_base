package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableStreamConfiguration implements MarshalQueryable<StreamConfiguration> {
    private static final int SIZE = 16;

    private class MarshalerStreamConfiguration extends Marshaler<StreamConfiguration> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }

        protected MarshalerStreamConfiguration(MarshalQueryableStreamConfiguration marshalQueryableStreamConfiguration, TypeReference<StreamConfiguration> typeReference, int i) {
            super(marshalQueryableStreamConfiguration, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(StreamConfiguration streamConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(streamConfiguration.getFormat());
            byteBuffer.putInt(streamConfiguration.getWidth());
            byteBuffer.putInt(streamConfiguration.getHeight());
            byteBuffer.putInt(streamConfiguration.isInput() ? 1 : 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public StreamConfiguration unmarshal(ByteBuffer byteBuffer) {
            return new StreamConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt() != 0);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<StreamConfiguration> createMarshaler(TypeReference<StreamConfiguration> typeReference, int i) {
        return new MarshalerStreamConfiguration(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<StreamConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(StreamConfiguration.class);
    }
}
