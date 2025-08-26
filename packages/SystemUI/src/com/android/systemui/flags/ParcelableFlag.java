package com.android.systemui.flags;

import android.os.Parcelable;

/* loaded from: classes2.dex */
public interface ParcelableFlag extends Flag, Parcelable {
    @Override // android.os.Parcelable
    default int describeContents() {
        return 0;
    }
}
