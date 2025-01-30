package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BluetoothSecureModeWhitelistConfig implements Parcelable {
    public static final Parcelable.Creator<BluetoothSecureModeWhitelistConfig> CREATOR = new Parcelable.Creator<BluetoothSecureModeWhitelistConfig>() { // from class: com.samsung.android.knox.bluetooth.BluetoothSecureModeWhitelistConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BluetoothSecureModeWhitelistConfig[] newArray(int i) {
            return new BluetoothSecureModeWhitelistConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BluetoothSecureModeWhitelistConfig createFromParcel(Parcel parcel) {
            return new BluetoothSecureModeWhitelistConfig(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final BluetoothSecureModeWhitelistConfig[] newArray(int i) {
            return new BluetoothSecureModeWhitelistConfig[i];
        }
    };
    public int cod;
    public String name;
    public String[] uuids;

    public /* synthetic */ BluetoothSecureModeWhitelistConfig(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.name = parcel.readString();
        this.cod = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            this.uuids = null;
            return;
        }
        String[] strArr = new String[readInt];
        this.uuids = strArr;
        parcel.readStringArray(strArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.name);
        parcel.writeInt(this.cod);
        String[] strArr = this.uuids;
        if (strArr == null || strArr.length <= 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(strArr.length);
            parcel.writeStringArray(this.uuids);
        }
    }

    public BluetoothSecureModeWhitelistConfig() {
    }

    public BluetoothSecureModeWhitelistConfig(String str, int i, String[] strArr) {
        this.name = str;
        this.cod = i;
        this.uuids = strArr;
    }

    private BluetoothSecureModeWhitelistConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
