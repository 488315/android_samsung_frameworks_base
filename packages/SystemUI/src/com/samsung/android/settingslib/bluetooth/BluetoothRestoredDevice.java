package com.samsung.android.settingslib.bluetooth;

import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;

/* loaded from: classes4.dex */
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

    public final void setUuids(String str) {
        String[] stringToken = BluetoothUtils.getStringToken(str);
        if (stringToken != null) {
            ParcelUuid[] parcelUuidArr = new ParcelUuid[stringToken.length];
            for (int i = 0; i < stringToken.length; i++) {
                try {
                    parcelUuidArr[i] = ParcelUuid.fromString(stringToken[i]);
                } catch (Exception e) {
                    Log.d("BluetoothUtils", "failed makeParcelUuids");
                    e.printStackTrace();
                }
            }
            this.mUuids = parcelUuidArr;
        }
    }
}
