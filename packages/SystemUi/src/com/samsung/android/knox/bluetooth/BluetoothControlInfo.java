package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.ControlInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BluetoothControlInfo extends ControlInfo {
    public static final Parcelable.Creator<BluetoothControlInfo> CREATOR = new Parcelable.Creator<BluetoothControlInfo>() { // from class: com.samsung.android.knox.bluetooth.BluetoothControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BluetoothControlInfo[] newArray(int i) {
            return new BluetoothControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BluetoothControlInfo createFromParcel(Parcel parcel) {
            return new BluetoothControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final BluetoothControlInfo[] newArray(int i) {
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
