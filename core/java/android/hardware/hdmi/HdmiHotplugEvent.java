package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class HdmiHotplugEvent implements Parcelable {
    public static final Parcelable.Creator<HdmiHotplugEvent> CREATOR =
            new Parcelable.Creator<
                    HdmiHotplugEvent>() { // from class: android.hardware.hdmi.HdmiHotplugEvent.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public HdmiHotplugEvent createFromParcel(Parcel p) {
                    int port = p.readInt();
                    boolean connected = p.readByte() == 1;
                    return new HdmiHotplugEvent(port, connected);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public HdmiHotplugEvent[] newArray(int size) {
                    return new HdmiHotplugEvent[size];
                }
            };
    private final boolean mConnected;
    private final int mPort;

    public HdmiHotplugEvent(int port, boolean connected) {
        this.mPort = port;
        this.mConnected = connected;
    }

    public int getPort() {
        return this.mPort;
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPort);
        parcel.writeByte(this.mConnected ? (byte) 1 : (byte) 0);
    }
}
