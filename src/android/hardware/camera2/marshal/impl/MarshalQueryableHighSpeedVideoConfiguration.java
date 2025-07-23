package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableHighSpeedVideoConfiguration implements MarshalQueryable<HighSpeedVideoConfiguration> {
    private static final int SIZE = 20;

    private class MarshalerHighSpeedVideoConfiguration extends Marshaler<HighSpeedVideoConfiguration> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }

        protected MarshalerHighSpeedVideoConfiguration(MarshalQueryableHighSpeedVideoConfiguration marshalQueryableHighSpeedVideoConfiguration, TypeReference<HighSpeedVideoConfiguration> typeReference, int i) {
            super(marshalQueryableHighSpeedVideoConfiguration, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(HighSpeedVideoConfiguration highSpeedVideoConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(highSpeedVideoConfiguration.getWidth());
            byteBuffer.putInt(highSpeedVideoConfiguration.getHeight());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMin());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMax());
            byteBuffer.putInt(highSpeedVideoConfiguration.getBatchSizeMax());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public HighSpeedVideoConfiguration unmarshal(ByteBuffer byteBuffer) {
            return new HighSpeedVideoConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<HighSpeedVideoConfiguration> createMarshaler(TypeReference<HighSpeedVideoConfiguration> typeReference, int i) {
        return new MarshalerHighSpeedVideoConfiguration(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<HighSpeedVideoConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(HighSpeedVideoConfiguration.class);
    }
}
