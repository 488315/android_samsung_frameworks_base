package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class DnsEvent extends NetworkEvent implements Parcelable {
    public static final Parcelable.Creator<DnsEvent> CREATOR = new Parcelable.Creator<DnsEvent>() { // from class: android.app.admin.DnsEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DnsEvent createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 1) {
                return null;
            }
            return new DnsEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DnsEvent[] newArray(int i) {
            return new DnsEvent[i];
        }
    };
    private final String mHostname;
    private final String[] mIpAddresses;
    private final int mIpAddressesCount;

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DnsEvent(String str, String[] strArr, int i, String str2, long j) {
        super(str2, j);
        this.mHostname = str;
        this.mIpAddresses = strArr;
        this.mIpAddressesCount = i;
    }

    private DnsEvent(Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mIpAddresses = parcel.createStringArray();
        this.mIpAddressesCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public String getHostname() {
        return this.mHostname;
    }

    public List<InetAddress> getInetAddresses() {
        String[] strArr = this.mIpAddresses;
        if (strArr == null || strArr.length == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(this.mIpAddresses.length);
        for (String str : this.mIpAddresses) {
            try {
                arrayList.add(InetAddress.getByName(str));
            } catch (UnknownHostException unused) {
            }
        }
        return arrayList;
    }

    public int getTotalResolvedAddressCount() {
        return this.mIpAddressesCount;
    }

    public String toString() {
        Long valueOf = Long.valueOf(this.mId);
        String str = this.mHostname;
        String[] strArr = this.mIpAddresses;
        return String.format("DnsEvent(%d, %s, %s, %d, %d, %s)", valueOf, str, strArr == null ? KeyProperties.DIGEST_NONE : String.join(" ", strArr), Integer.valueOf(this.mIpAddressesCount), Long.valueOf(this.mTimestamp), this.mPackageName);
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeString(this.mHostname);
        parcel.writeStringArray(this.mIpAddresses);
        parcel.writeInt(this.mIpAddressesCount);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }
}
