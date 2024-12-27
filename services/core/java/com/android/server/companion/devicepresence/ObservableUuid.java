package com.android.server.companion.devicepresence;

import android.os.ParcelUuid;

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
