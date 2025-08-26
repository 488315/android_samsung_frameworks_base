package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public final class LongMultiStateCounter implements Parcelable {
    public static final Parcelable.Creator<LongMultiStateCounter> CREATOR = new Parcelable.Creator<LongMultiStateCounter>() { // from class: com.android.internal.os.LongMultiStateCounter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongMultiStateCounter createFromParcel(Parcel parcel) {
            return new LongMultiStateCounter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongMultiStateCounter[] newArray(int i) {
            return new LongMultiStateCounter[i];
        }
    };
    private static NativeAllocationRegistry sRegistry;
    final long mNativeObject;
    private final int mStateCount;

    @CriticalNative
    private static native void native_addCount(long j, long j2);

    @CriticalNative
    private static native long native_getCount(long j, int i);

    @CriticalNative
    private static native long native_getReleaseFunc();

    @CriticalNative
    private static native int native_getStateCount(long j);

    @CriticalNative
    private static native void native_incrementValue(long j, long j2, long j3);

    @CriticalNative
    private static native long native_init(int i);

    @FastNative
    private static native long native_initFromParcel(Parcel parcel);

    @CriticalNative
    private static native void native_reset(long j);

    @CriticalNative
    private static native void native_setEnabled(long j, boolean z, long j2);

    @CriticalNative
    private static native void native_setState(long j, int i, long j2);

    @FastNative
    private static native String native_toString(long j);

    @CriticalNative
    private static native long native_updateValue(long j, long j2, long j3);

    @FastNative
    private static native void native_writeToParcel(long j, Parcel parcel, int i);

    private void registerNativeAllocation$ravenwood() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LongMultiStateCounter(int i) {
        Preconditions.checkArgumentPositive(i, "stateCount must be greater than 0");
        this.mStateCount = i;
        this.mNativeObject = native_init(i);
        registerNativeAllocation();
    }

    private LongMultiStateCounter(Parcel parcel) {
        long jNative_initFromParcel = native_initFromParcel(parcel);
        this.mNativeObject = jNative_initFromParcel;
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(jNative_initFromParcel);
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (LongMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry = NativeAllocationRegistry.createMalloced(LongMultiStateCounter.class.getClassLoader(), native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    public int getStateCount() {
        return this.mStateCount;
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

    public long updateValue(long j, long j2) {
        return native_updateValue(this.mNativeObject, j, j2);
    }

    public void incrementValue(long j, long j2) {
        native_incrementValue(this.mNativeObject, j, j2);
    }

    public void addCount(long j) {
        native_addCount(this.mNativeObject, j);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public long getCount(int i) {
        if (i < 0 || i >= this.mStateCount) {
            throw new IllegalArgumentException("State: " + i + ", outside the range: [0-" + this.mStateCount + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return native_getCount(this.mNativeObject, i);
    }

    public long getTotalCount() {
        long jNative_getCount = 0;
        for (int i = 0; i < this.mStateCount; i++) {
            jNative_getCount += native_getCount(this.mNativeObject, i);
        }
        return jNative_getCount;
    }

    public String toString() {
        return native_toString(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        native_writeToParcel(this.mNativeObject, parcel, i);
    }
}
