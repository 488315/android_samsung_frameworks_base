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
    public static final Parcelable.Creator<LongMultiStateCounter> CREATOR =
            new Parcelable.Creator<
                    LongMultiStateCounter>() { // from class:
                                               // com.android.internal.os.LongMultiStateCounter.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LongMultiStateCounter createFromParcel(Parcel in) {
                    return new LongMultiStateCounter(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LongMultiStateCounter[] newArray(int size) {
                    return new LongMultiStateCounter[size];
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

    public LongMultiStateCounter(int stateCount) {
        Preconditions.checkArgumentPositive(stateCount, "stateCount must be greater than 0");
        this.mStateCount = stateCount;
        this.mNativeObject = native_init(stateCount);
        registerNativeAllocation();
    }

    private LongMultiStateCounter(Parcel in) {
        this.mNativeObject = native_initFromParcel(in);
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(this.mNativeObject);
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (LongMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry =
                            NativeAllocationRegistry.createMalloced(
                                    LongMultiStateCounter.class.getClassLoader(),
                                    native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    private void registerNativeAllocation$ravenwood() {}

    public int getStateCount() {
        return this.mStateCount;
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

    public long updateValue(long value, long timestampMs) {
        return native_updateValue(this.mNativeObject, value, timestampMs);
    }

    public void incrementValue(long count, long timestampMs) {
        native_incrementValue(this.mNativeObject, count, timestampMs);
    }

    public void addCount(long count) {
        native_addCount(this.mNativeObject, count);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public long getCount(int state) {
        if (state < 0 || state >= this.mStateCount) {
            throw new IllegalArgumentException(
                    "State: "
                            + state
                            + ", outside the range: [0-"
                            + this.mStateCount
                            + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return native_getCount(this.mNativeObject, state);
    }

    public long getTotalCount() {
        long total = 0;
        for (int state = 0; state < this.mStateCount; state++) {
            total += native_getCount(this.mNativeObject, state);
        }
        return total;
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
