package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class MarshalQueryableParcelable<T extends Parcelable> implements MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final String FIELD_CREATOR = "CREATOR";
    private static final String TAG = "MarshalParcelable";

    private class MarshalerParcelable extends Marshaler<T> {
        private final Class<T> mClass;
        private final Parcelable.Creator<T> mCreator;

        protected MarshalerParcelable(MarshalQueryableParcelable marshalQueryableParcelable, TypeReference<T> typeReference, int i) {
            super(marshalQueryableParcelable, typeReference, i);
            Class<? super T> rawType = typeReference.getRawType();
            this.mClass = rawType;
            try {
                try {
                    this.mCreator = (Parcelable.Creator) rawType.getDeclaredField(MarshalQueryableParcelable.FIELD_CREATOR).get(null);
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                } catch (IllegalArgumentException e2) {
                    throw new AssertionError(e2);
                }
            } catch (NoSuchFieldException e3) {
                throw new AssertionError(e3);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, ByteBuffer byteBuffer) {
            Parcel parcelObtain = Parcel.obtain();
            try {
                t.writeToParcel(parcelObtain, 0);
                if (parcelObtain.hasFileDescriptors()) {
                    throw new UnsupportedOperationException("Parcelable " + t + " must not have file descriptors");
                }
                int iPosition = byteBuffer.position();
                parcelObtain.marshall(byteBuffer);
                if (byteBuffer.position() != iPosition) {
                    return;
                }
                throw new AssertionError("No data marshaled for " + t);
            } finally {
                parcelObtain.recycle();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(ByteBuffer byteBuffer) {
            byteBuffer.mark();
            Parcel parcelObtain = Parcel.obtain();
            try {
                int iRemaining = byteBuffer.remaining();
                byte[] bArr = new byte[iRemaining];
                byteBuffer.get(bArr);
                parcelObtain.unmarshall(bArr, 0, iRemaining);
                parcelObtain.setDataPosition(0);
                T tCreateFromParcel = this.mCreator.createFromParcel(parcelObtain);
                int iDataPosition = parcelObtain.dataPosition();
                if (iDataPosition == 0) {
                    throw new AssertionError("No data marshaled for " + tCreateFromParcel);
                }
                byteBuffer.reset();
                byteBuffer.position(byteBuffer.position() + iDataPosition);
                return this.mClass.cast(tCreateFromParcel);
            } finally {
                parcelObtain.recycle();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(T t) {
            Parcel parcelObtain = Parcel.obtain();
            try {
                t.writeToParcel(parcelObtain, 0);
                return parcelObtain.marshall().length;
            } finally {
                parcelObtain.recycle();
            }
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int i) {
        return new MarshalerParcelable(this, typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<T> typeReference, int i) {
        return Parcelable.class.isAssignableFrom(typeReference.getRawType());
    }
}
