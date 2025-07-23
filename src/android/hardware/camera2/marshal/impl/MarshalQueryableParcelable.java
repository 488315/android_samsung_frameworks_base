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
            Parcel obtain = Parcel.obtain();
            try {
                t.writeToParcel(obtain, 0);
                if (obtain.hasFileDescriptors()) {
                    throw new UnsupportedOperationException("Parcelable " + t + " must not have file descriptors");
                }
                int position = byteBuffer.position();
                obtain.marshall(byteBuffer);
                if (byteBuffer.position() != position) {
                    return;
                }
                throw new AssertionError("No data marshaled for " + t);
            } finally {
                obtain.recycle();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(ByteBuffer byteBuffer) {
            byteBuffer.mark();
            Parcel obtain = Parcel.obtain();
            try {
                int remaining = byteBuffer.remaining();
                byte[] bArr = new byte[remaining];
                byteBuffer.get(bArr);
                obtain.unmarshall(bArr, 0, remaining);
                obtain.setDataPosition(0);
                T createFromParcel = this.mCreator.createFromParcel(obtain);
                int dataPosition = obtain.dataPosition();
                if (dataPosition == 0) {
                    throw new AssertionError("No data marshaled for " + createFromParcel);
                }
                byteBuffer.reset();
                byteBuffer.position(byteBuffer.position() + dataPosition);
                return this.mClass.cast(createFromParcel);
            } finally {
                obtain.recycle();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(T t) {
            Parcel obtain = Parcel.obtain();
            try {
                t.writeToParcel(obtain, 0);
                return obtain.marshall().length;
            } finally {
                obtain.recycle();
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
