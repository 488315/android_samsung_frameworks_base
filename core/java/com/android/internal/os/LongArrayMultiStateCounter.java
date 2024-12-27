package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

import libcore.util.NativeAllocationRegistry;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class LongArrayMultiStateCounter implements Parcelable {
    private static volatile NativeAllocationRegistry sRegistry;
    private final int mLength;
    final long mNativeObject;
    private final int mStateCount;
    private static final AtomicReference<LongArrayContainer> sTmpArrayContainer =
            new AtomicReference<>();
    public static final Parcelable.Creator<LongArrayMultiStateCounter> CREATOR =
            new Parcelable.Creator<
                    LongArrayMultiStateCounter>() { // from class:
                                                    // com.android.internal.os.LongArrayMultiStateCounter.1
                @Override // android.os.Parcelable.Creator
                public LongArrayMultiStateCounter createFromParcel(Parcel in) {
                    return new LongArrayMultiStateCounter(in);
                }

                @Override // android.os.Parcelable.Creator
                public LongArrayMultiStateCounter[] newArray(int size) {
                    return new LongArrayMultiStateCounter[size];
                }
            };

    @CriticalNative
    private static native void native_addCounts(long j, long j2);

    @CriticalNative
    private static native void native_copyStatesFrom(long j, long j2);

    @CriticalNative
    private static native int native_getArrayLength(long j);

    @CriticalNative
    private static native void native_getCounts(long j, long j2, int i);

    @CriticalNative
    private static native long native_getReleaseFunc();

    @CriticalNative
    private static native int native_getStateCount(long j);

    @CriticalNative
    private static native void native_incrementValues(long j, long j2, long j3);

    @CriticalNative
    private static native long native_init(int i, int i2);

    @FastNative
    private static native long native_initFromParcel(Parcel parcel);

    @CriticalNative
    private static native void native_reset(long j);

    @CriticalNative
    private static native void native_setEnabled(long j, boolean z, long j2);

    @CriticalNative
    private static native void native_setState(long j, int i, long j2);

    @CriticalNative
    private static native void native_setValues(long j, int i, long j2);

    @FastNative
    private static native String native_toString(long j);

    @CriticalNative
    private static native void native_updateValues(long j, long j2, long j3);

    @FastNative
    private static native void native_writeToParcel(long j, Parcel parcel, int i);

    public static class LongArrayContainer {
        private static NativeAllocationRegistry sRegistry;
        private final int mLength;
        final long mNativeObject;

        @FastNative
        private static native boolean native_combineValues(long j, long[] jArr, int[] iArr);

        @CriticalNative
        private static native long native_getReleaseFunc();

        @FastNative
        private static native void native_getValues(long j, long[] jArr);

        @CriticalNative
        private static native long native_init(int i);

        @FastNative
        private static native void native_setValues(long j, long[] jArr);

        public LongArrayContainer(int length) {
            this.mLength = length;
            this.mNativeObject = native_init(length);
            registerNativeAllocation();
        }

        private void registerNativeAllocation() {
            if (sRegistry == null) {
                synchronized (LongArrayMultiStateCounter.class) {
                    if (sRegistry == null) {
                        sRegistry =
                                NativeAllocationRegistry.createMalloced(
                                        LongArrayContainer.class.getClassLoader(),
                                        native_getReleaseFunc());
                    }
                }
            }
            sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }

        private void registerNativeAllocation$ravenwood() {}

        public void setValues(long[] array) {
            if (array.length != this.mLength) {
                throw new IllegalArgumentException(
                        "Invalid array length: " + array.length + ", expected: " + this.mLength);
            }
            native_setValues(this.mNativeObject, array);
        }

        public void getValues(long[] array) {
            if (array.length != this.mLength) {
                throw new IllegalArgumentException(
                        "Invalid array length: " + array.length + ", expected: " + this.mLength);
            }
            native_getValues(this.mNativeObject, array);
        }

        public boolean combineValues(long[] array, int[] indexMap) {
            if (indexMap.length != this.mLength) {
                throw new IllegalArgumentException(
                        "Wrong index map size " + indexMap.length + ", expected " + this.mLength);
            }
            return native_combineValues(this.mNativeObject, array, indexMap);
        }

        public String toString() {
            long[] array = new long[this.mLength];
            getValues(array);
            return Arrays.toString(array);
        }
    }

    public LongArrayMultiStateCounter(int stateCount, int arrayLength) {
        Preconditions.checkArgumentPositive(stateCount, "stateCount must be greater than 0");
        this.mStateCount = stateCount;
        this.mLength = arrayLength;
        this.mNativeObject = native_init(stateCount, arrayLength);
        registerNativeAllocation();
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (LongArrayMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry =
                            NativeAllocationRegistry.createMalloced(
                                    LongArrayMultiStateCounter.class.getClassLoader(),
                                    native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    private void registerNativeAllocation$ravenwood() {}

    private LongArrayMultiStateCounter(Parcel in) {
        this.mNativeObject = native_initFromParcel(in);
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(this.mNativeObject);
        this.mLength = native_getArrayLength(this.mNativeObject);
    }

    public int getStateCount() {
        return this.mStateCount;
    }

    public int getArrayLength() {
        return this.mLength;
    }

    public void setEnabled(boolean enabled, long timestampMs) {
        native_setEnabled(this.mNativeObject, enabled, timestampMs);
    }

    public void setState(int state, long timestampMs) {
        if (state < 0 || state >= this.mStateCount) {
            throw new IllegalArgumentException(
                    "State: "
                            + state
                            + ", outside the range: [0-"
                            + (this.mStateCount - 1)
                            + NavigationBarInflaterView.SIZE_MOD_END);
        }
        native_setState(this.mNativeObject, state, timestampMs);
    }

    public void copyStatesFrom(LongArrayMultiStateCounter counter) {
        if (this.mStateCount != counter.mStateCount) {
            throw new IllegalArgumentException(
                    "State count is not the same: "
                            + this.mStateCount
                            + " vs. "
                            + counter.mStateCount);
        }
        native_copyStatesFrom(this.mNativeObject, counter.mNativeObject);
    }

    public void setValues(int state, long[] values) {
        if (state < 0 || state >= this.mStateCount) {
            throw new IllegalArgumentException(
                    "State: "
                            + state
                            + ", outside the range: [0-"
                            + (this.mStateCount - 1)
                            + NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (values.length != this.mLength) {
            throw new IllegalArgumentException(
                    "Invalid array length: " + values.length + ", expected: " + this.mLength);
        }
        LongArrayContainer container = sTmpArrayContainer.getAndSet(null);
        if (container == null || container.mLength != values.length) {
            container = new LongArrayContainer(values.length);
        }
        container.setValues(values);
        native_setValues(this.mNativeObject, state, container.mNativeObject);
        sTmpArrayContainer.set(container);
    }

    public void updateValues(long[] values, long timestampMs) {
        LongArrayContainer container = sTmpArrayContainer.getAndSet(null);
        if (container == null || container.mLength != values.length) {
            container = new LongArrayContainer(values.length);
        }
        container.setValues(values);
        updateValues(container, timestampMs);
        sTmpArrayContainer.set(container);
    }

    public void incrementValues(long[] values, long timestampMs) {
        LongArrayContainer container = sTmpArrayContainer.getAndSet(null);
        if (container == null || container.mLength != values.length) {
            container = new LongArrayContainer(values.length);
        }
        container.setValues(values);
        native_incrementValues(this.mNativeObject, container.mNativeObject, timestampMs);
        sTmpArrayContainer.set(container);
    }

    public void updateValues(LongArrayContainer longArrayContainer, long timestampMs) {
        if (longArrayContainer.mLength != this.mLength) {
            throw new IllegalArgumentException(
                    "Invalid array length: "
                            + longArrayContainer.mLength
                            + ", expected: "
                            + this.mLength);
        }
        native_updateValues(this.mNativeObject, longArrayContainer.mNativeObject, timestampMs);
    }

    public void addCounts(LongArrayContainer counts) {
        if (counts.mLength != this.mLength) {
            throw new IllegalArgumentException(
                    "Invalid array length: " + counts.mLength + ", expected: " + this.mLength);
        }
        native_addCounts(this.mNativeObject, counts.mNativeObject);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public void getCounts(long[] counts, int state) {
        LongArrayContainer container = sTmpArrayContainer.getAndSet(null);
        if (container == null || container.mLength != counts.length) {
            container = new LongArrayContainer(counts.length);
        }
        getCounts(container, state);
        container.getValues(counts);
        sTmpArrayContainer.set(container);
    }

    public void getCounts(LongArrayContainer longArrayContainer, int state) {
        if (state < 0 || state >= this.mStateCount) {
            throw new IllegalArgumentException(
                    "State: "
                            + state
                            + ", outside the range: [0-"
                            + this.mStateCount
                            + NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (longArrayContainer.mLength != this.mLength) {
            throw new IllegalArgumentException(
                    "Invalid array length: "
                            + longArrayContainer.mLength
                            + ", expected: "
                            + this.mLength);
        }
        native_getCounts(this.mNativeObject, longArrayContainer.mNativeObject, state);
    }

    public String toString() {
        return native_toString(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        native_writeToParcel(this.mNativeObject, dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
