package com.android.systemui.flags;

import android.os.Parcelable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ParcelableFlag extends Flag, Parcelable {
    @Override // android.os.Parcelable
    default int describeContents() {
        return 0;
    }
}
