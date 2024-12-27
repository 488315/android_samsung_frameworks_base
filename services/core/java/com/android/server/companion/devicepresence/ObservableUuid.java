package com.android.server.companion.devicepresence;

import android.os.ParcelUuid;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ObservableUuid {
    public final String mPackageName;
    public final long mTimeApprovedMs;
    public final int mUserId;
    public final ParcelUuid mUuid;

    public ObservableUuid(int i, ParcelUuid parcelUuid, String str, Long l) {
        this.mUserId = i;
        this.mUuid = parcelUuid;
        this.mPackageName = str;
        this.mTimeApprovedMs = l.longValue();
    }
}
