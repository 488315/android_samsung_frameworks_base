package com.android.systemui.flags;

import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ParcelableFlag extends Flag, Parcelable {
    @Override // android.os.Parcelable
    default int describeContents() {
        return 0;
    }
}
