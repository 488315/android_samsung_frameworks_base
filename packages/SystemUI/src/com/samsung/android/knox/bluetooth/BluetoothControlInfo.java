package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.ControlInfo;

/* loaded from: classes4.dex */
public class BluetoothControlInfo extends ControlInfo {
    public static final Parcelable.Creator<BluetoothControlInfo> CREATOR = new Parcelable.Creator<BluetoothControlInfo>() { // from class: com.samsung.android.knox.bluetooth.BluetoothControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothControlInfo createFromParcel(Parcel parcel) {
            return new BluetoothControlInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothControlInfo[] newArray(int i) {
            return new BluetoothControlInfo[i];
        }
    };

    public /* synthetic */ BluetoothControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public BluetoothControlInfo() {
    }

    private BluetoothControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
