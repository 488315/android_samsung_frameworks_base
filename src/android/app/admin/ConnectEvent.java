package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes.dex */
public final class ConnectEvent extends NetworkEvent implements Parcelable {
    public static final Parcelable.Creator<ConnectEvent> CREATOR = new Parcelable.Creator<ConnectEvent>() { // from class: android.app.admin.ConnectEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectEvent createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 2) {
                return null;
            }
            return new ConnectEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectEvent[] newArray(int i) {
            return new ConnectEvent[i];
        }
    };
    private final String mIpAddress;
    private final int mPort;

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConnectEvent(String str, int i, String str2, long j) {
        super(str2, j);
        this.mIpAddress = str;
        this.mPort = i;
    }

    private ConnectEvent(Parcel parcel) {
        this.mIpAddress = parcel.readString();
        this.mPort = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public InetAddress getInetAddress() {
        try {
            return InetAddress.getByName(this.mIpAddress);
        } catch (UnknownHostException unused) {
            return InetAddress.getLoopbackAddress();
        }
    }

    public int getPort() {
        return this.mPort;
    }

    public String toString() {
        return String.format("ConnectEvent(%d, %s, %d, %d, %s)", Long.valueOf(this.mId), this.mIpAddress, Integer.valueOf(this.mPort), Long.valueOf(this.mTimestamp), this.mPackageName);
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(2);
        parcel.writeString(this.mIpAddress);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }
}
