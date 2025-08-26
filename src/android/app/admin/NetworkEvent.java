package android.app.admin;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/* loaded from: classes.dex */
public abstract class NetworkEvent implements Parcelable {
    public static final Parcelable.Creator<NetworkEvent> CREATOR = new Parcelable.Creator<NetworkEvent>() { // from class: android.app.admin.NetworkEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkEvent createFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            parcel.setDataPosition(iDataPosition);
            if (i == 1) {
                return DnsEvent.CREATOR.createFromParcel(parcel);
            }
            if (i == 2) {
                return ConnectEvent.CREATOR.createFromParcel(parcel);
            }
            throw new ParcelFormatException("Unexpected NetworkEvent token in parcel: " + i);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkEvent[] newArray(int i) {
            return new NetworkEvent[i];
        }
    };
    static final int PARCEL_TOKEN_CONNECT_EVENT = 2;
    static final int PARCEL_TOKEN_DNS_EVENT = 1;
    long mId;
    String mPackageName;
    long mTimestamp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public abstract void writeToParcel(Parcel parcel, int i);

    NetworkEvent() {
    }

    NetworkEvent(String str, long j) {
        this.mPackageName = str;
        this.mTimestamp = j;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public long getId() {
        return this.mId;
    }
}
