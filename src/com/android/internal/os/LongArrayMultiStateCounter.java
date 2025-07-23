package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public final class LongArrayMultiStateCounter implements Parcelable {
    public static final Parcelable.Creator<LongArrayMultiStateCounter> CREATOR = new Parcelable.Creator<LongArrayMultiStateCounter>() { // from class: com.android.internal.os.LongArrayMultiStateCounter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongArrayMultiStateCounter createFromParcel(Parcel parcel) {
            return new LongArrayMultiStateCounter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongArrayMultiStateCounter[] newArray(int i) {
            return new LongArrayMultiStateCounter[i];
        }
    };
    private static volatile NativeAllocationRegistry sRegistry;
    private final int mLength;
    final long mNativeObject;
    private final int mStateCount;

    @FastNative
    private static native void native_addCounts(long j, long[] jArr);

    @CriticalNative
    private static native void native_copyStatesFrom(long j, long j2);

    @CriticalNative
    private static native int native_getArrayLength(long j);

    @FastNative
    private static native boolean native_getCounts(long j, long[] jArr, int i);

    @CriticalNative
    private static native long native_getReleaseFunc();

    @CriticalNative
    private static native int native_getStateCount(long j);

    @FastNative
    private static native void native_incrementValues(long j, long[] jArr, long j2);

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

    @FastNative
    private static native void native_setValues(long j, int i, long[] jArr);

    @FastNative
    private static native String native_toString(long j);

    @FastNative
    private static native void native_updateValues(long j, long[] jArr, long j2);

    @FastNative
    private static native void native_writeToParcel(long j, Parcel parcel, int i);

    private void registerNativeAllocation$ravenwood() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LongArrayMultiStateCounter(int i, int i2) {
        Preconditions.checkArgumentPositive(i, "stateCount must be greater than 0");
        this.mStateCount = i;
        this.mLength = i2;
        this.mNativeObject = native_init(i, i2);
        registerNativeAllocation();
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (LongArrayMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry = NativeAllocationRegistry.createMalloced(LongArrayMultiStateCounter.class.getClassLoader(), native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    private LongArrayMultiStateCounter(Parcel parcel) {
        long native_initFromParcel = native_initFromParcel(parcel);
        this.mNativeObject = native_initFromParcel;
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(native_initFromParcel);
        this.mLength = native_getArrayLength(native_initFromParcel);
    }

    public int getStateCount() {
        return this.mStateCount;
    }

    public int getArrayLength() {
        return this.mLength;
    }

    public void setEnabled(boolean z, long j) {
        native_setEnabled(this.mNativeObject, z, j);
    }

    public void setState(int i, long j) {
        if (i < 0 || i >= this.mStateCount) {
            StringBuilder sb = new StringBuilder("State: ");
            sb.append(i);
            sb.append(", outside the range: [0-");
            sb.append(this.mStateCount - 1);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            throw new IllegalArgumentException(sb.toString());
        }
        native_setState(this.mNativeObject, i, j);
    }

    public void copyStatesFrom(LongArrayMultiStateCounter longArrayMultiStateCounter) {
        if (this.mStateCount != longArrayMultiStateCounter.mStateCount) {
            throw new IllegalArgumentException("State count is not the same: " + this.mStateCount + " vs. " + longArrayMultiStateCounter.mStateCount);
        }
        native_copyStatesFrom(this.mNativeObject, longArrayMultiStateCounter.mNativeObject);
    }

    public void setValues(int i, long[] jArr) {
        if (i < 0 || i >= this.mStateCount) {
            StringBuilder sb = new StringBuilder("State: ");
            sb.append(i);
            sb.append(", outside the range: [0-");
            sb.append(this.mStateCount - 1);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            throw new IllegalArgumentException(sb.toString());
        }
        if (jArr.length != this.mLength) {
            throw new IllegalArgumentException("Invalid array length: " + jArr.length + ", expected: " + this.mLength);
        }
        native_setValues(this.mNativeObject, i, jArr);
    }

    public void incrementValues(long[] jArr, long j) {
        native_incrementValues(this.mNativeObject, jArr, j);
    }

    public void updateValues(long[] jArr, long j) {
        if (jArr.length != this.mLength) {
            throw new IllegalArgumentException("Invalid array length: " + jArr.length + ", expected: " + this.mLength);
        }
        native_updateValues(this.mNativeObject, jArr, j);
    }

    public void addCounts(long[] jArr) {
        if (jArr.length != this.mLength) {
            throw new IllegalArgumentException("Invalid array length: " + jArr.length + ", expected: " + this.mLength);
        }
        native_addCounts(this.mNativeObject, jArr);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public boolean getCounts(long[] jArr, int i) {
        if (i < 0 || i >= this.mStateCount) {
            throw new IllegalArgumentException("State: " + i + ", outside the range: [0-" + this.mStateCount + NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (jArr.length != this.mLength) {
            throw new IllegalArgumentException("Invalid array length: " + jArr.length + ", expected: " + this.mLength);
        }
        return native_getCounts(this.mNativeObject, jArr, i);
    }

    public String toString() {
        return native_toString(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        native_writeToParcel(this.mNativeObject, parcel, i);
    }
}
