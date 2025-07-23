package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableRecommendedStreamConfiguration implements MarshalQueryable<RecommendedStreamConfiguration> {
    private static final int SIZE = 20;

    private class MarshalerRecommendedStreamConfiguration extends Marshaler<RecommendedStreamConfiguration> {
        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }

        protected MarshalerRecommendedStreamConfiguration(MarshalQueryableRecommendedStreamConfiguration marshalQueryableRecommendedStreamConfiguration, TypeReference<RecommendedStreamConfiguration> typeReference, int i) {
            super(marshalQueryableRecommendedStreamConfiguration, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(RecommendedStreamConfiguration recommendedStreamConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(recommendedStreamConfiguration.getWidth());
            byteBuffer.putInt(recommendedStreamConfiguration.getHeight());
            byteBuffer.putInt(recommendedStreamConfiguration.getFormat());
            byteBuffer.putInt(recommendedStreamConfiguration.isInput() ? 1 : 0);
            byteBuffer.putInt(recommendedStreamConfiguration.getUsecaseBitmap());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public RecommendedStreamConfiguration unmarshal(ByteBuffer byteBuffer) {
            return new RecommendedStreamConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt() != 0, byteBuffer.getInt());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<RecommendedStreamConfiguration> createMarshaler(TypeReference<RecommendedStreamConfiguration> typeReference, int i) {
        return new MarshalerRecommendedStreamConfiguration(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<RecommendedStreamConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(RecommendedStreamConfiguration.class);
    }
}
