package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableReprocessFormatsMap implements MarshalQueryable<ReprocessFormatsMap> {

    private class MarshalerReprocessFormatsMap extends Marshaler<ReprocessFormatsMap> {
        protected MarshalerReprocessFormatsMap(MarshalQueryableReprocessFormatsMap marshalQueryableReprocessFormatsMap, TypeReference<ReprocessFormatsMap> typeReference, int i) {
            super(marshalQueryableReprocessFormatsMap, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(ReprocessFormatsMap reprocessFormatsMap, ByteBuffer byteBuffer) {
            for (int i : StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getInputs())) {
                byteBuffer.putInt(i);
                int[] iArrImageFormatToInternal = StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getOutputs(i));
                byteBuffer.putInt(iArrImageFormatToInternal.length);
                for (int i2 : iArrImageFormatToInternal) {
                    byteBuffer.putInt(i2);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public ReprocessFormatsMap unmarshal(ByteBuffer byteBuffer) {
            int iRemaining = byteBuffer.remaining() / 4;
            if (byteBuffer.remaining() % 4 != 0) {
                throw new AssertionError("ReprocessFormatsMap was not TYPE_INT32");
            }
            int[] iArr = new int[iRemaining];
            byteBuffer.asIntBuffer().get(iArr);
            return new ReprocessFormatsMap(iArr);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(ReprocessFormatsMap reprocessFormatsMap) {
            int length = 0;
            for (int i : reprocessFormatsMap.getInputs()) {
                length = length + 2 + reprocessFormatsMap.getOutputs(i).length;
            }
            return length * 4;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<ReprocessFormatsMap> createMarshaler(TypeReference<ReprocessFormatsMap> typeReference, int i) {
        return new MarshalerReprocessFormatsMap(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<ReprocessFormatsMap> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(ReprocessFormatsMap.class);
    }
}
