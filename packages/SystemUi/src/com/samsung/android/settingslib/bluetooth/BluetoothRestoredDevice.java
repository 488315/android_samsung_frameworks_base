package com.samsung.android.settingslib.bluetooth;

import android.content.Context;
import android.os.ParcelUuid;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BluetoothRestoredDevice {
    public final String mAddress;
    public int mAppearance;
    public int mBondState;
    public int mCod;
    public int mLinkType = 0;
    public byte[] mManufacturerData;
    public String mName;
    public long mTimeStamp;
    public ParcelUuid[] mUuids;

    public BluetoothRestoredDevice(Context context, String str) {
        this.mAddress = str;
    }
}
