package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class HdmiHotplugEvent implements Parcelable {
    public static final Parcelable.Creator<HdmiHotplugEvent> CREATOR = new Parcelable.Creator<HdmiHotplugEvent>() { // from class: android.hardware.hdmi.HdmiHotplugEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiHotplugEvent createFromParcel(Parcel parcel) {
            return new HdmiHotplugEvent(parcel.readInt(), parcel.readByte() == 1);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiHotplugEvent[] newArray(int i) {
            return new HdmiHotplugEvent[i];
        }
    };
    private final boolean mConnected;
    private final int mPort;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HdmiHotplugEvent(int i, boolean z) {
        this.mPort = i;
        this.mConnected = z;
    }

    public int getPort() {
        return this.mPort;
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPort);
        parcel.writeByte(this.mConnected ? (byte) 1 : (byte) 0);
    }
}
