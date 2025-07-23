package android.app.backup;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public class RestoreSet implements Parcelable {
    public static final Parcelable.Creator<RestoreSet> CREATOR = new Parcelable.Creator<RestoreSet>() { // from class: android.app.backup.RestoreSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestoreSet createFromParcel(Parcel parcel) {
            return new RestoreSet(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestoreSet[] newArray(int i) {
            return new RestoreSet[i];
        }
    };
    public final int backupTransportFlags;
    public String device;
    public String name;
    public long token;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RestoreSet() {
        this.backupTransportFlags = 0;
    }

    public RestoreSet(String str, String str2, long j) {
        this(str, str2, j, 0);
    }

    public RestoreSet(String str, String str2, long j, int i) {
        this.name = str;
        this.device = str2;
        this.token = j;
        this.backupTransportFlags = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.device);
        parcel.writeLong(this.token);
        parcel.writeInt(this.backupTransportFlags);
    }

    private RestoreSet(Parcel parcel) {
        this.name = parcel.readString();
        this.device = parcel.readString();
        this.token = parcel.readLong();
        this.backupTransportFlags = parcel.readInt();
    }
}
