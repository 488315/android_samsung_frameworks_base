package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Range;
import java.lang.Comparable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class MarshalQueryableRange<T extends Comparable<? super T>> implements MarshalQueryable<Range<T>> {
    private static final int RANGE_COUNT = 2;

    private class MarshalerRange extends Marshaler<Range<T>> {
        private final Class<? super Range<T>> mClass;
        private final Constructor<Range<T>> mConstructor;
        private final Marshaler<T> mNestedTypeMarshaler;

        protected MarshalerRange(TypeReference<Range<T>> typeReference, int i) {
            super(MarshalQueryableRange.this, typeReference, i);
            Class<? super Range<T>> rawType = typeReference.getRawType();
            this.mClass = rawType;
            try {
                this.mNestedTypeMarshaler = MarshalRegistry.getMarshaler(TypeReference.createSpecializedTypeReference(((ParameterizedType) typeReference.getType()).getActualTypeArguments()[0]), this.mNativeType);
                try {
                    this.mConstructor = rawType.getConstructor(Comparable.class, Comparable.class);
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            } catch (ClassCastException e2) {
                throw new AssertionError("Raw use of Range is not supported", e2);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(Range<T> value, ByteBuffer buffer) {
            this.mNestedTypeMarshaler.marshal(value.getLower(), buffer);
            this.mNestedTypeMarshaler.marshal(value.getUpper(), buffer);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public Range<T> unmarshal(ByteBuffer buffer) {
            T lower = this.mNestedTypeMarshaler.unmarshal(buffer);
            T upper = this.mNestedTypeMarshaler.unmarshal(buffer);
            try {
                return this.mConstructor.newInstance(lower, upper);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalArgumentException e2) {
                throw new AssertionError(e2);
            } catch (InstantiationException e3) {
                throw new AssertionError(e3);
            } catch (InvocationTargetException e4) {
                throw new AssertionError(e4);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            int nestedSize = this.mNestedTypeMarshaler.getNativeSize();
            if (nestedSize != NATIVE_SIZE_DYNAMIC) {
                return nestedSize * 2;
            }
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(Range<T> value) {
            int nativeSize = getNativeSize();
            if (nativeSize != NATIVE_SIZE_DYNAMIC) {
                return nativeSize;
            }
            int lowerSize = this.mNestedTypeMarshaler.calculateMarshalSize(value.getLower());
            int upperSize = this.mNestedTypeMarshaler.calculateMarshalSize(value.getUpper());
            return lowerSize + upperSize;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public Marshaler<Range<T>> createMarshaler(TypeReference<Range<T>> managedType, int nativeType) {
        return new MarshalerRange(managedType, nativeType);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(TypeReference<Range<T>> managedType, int nativeType) {
        return Range.class.equals(managedType.getRawType());
    }
}
