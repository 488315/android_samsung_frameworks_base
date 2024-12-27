package com.android.systemui.flags;

import android.os.Parcelable;

public interface ParcelableFlag extends Flag, Parcelable {
    @Override // android.os.Parcelable
    default int describeContents() {
        return 0;
    }
}
