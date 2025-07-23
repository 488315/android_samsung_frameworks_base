package android.app.pinner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class PinnedFileStat implements Parcelable {
    public static final Parcelable.Creator<PinnedFileStat> CREATOR = new Parcelable.Creator<PinnedFileStat>() { // from class: android.app.pinner.PinnedFileStat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PinnedFileStat createFromParcel(Parcel parcel) {
            return new PinnedFileStat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PinnedFileStat[] newArray(int i) {
            return new PinnedFileStat[i];
        }
    };
    private long bytesPinned;
    private String filename;
    private String groupName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getBytesPinned() {
        return this.bytesPinned;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public PinnedFileStat(String str, long j, String str2) {
        this.filename = str;
        this.bytesPinned = j;
        this.groupName = str2;
    }

    private PinnedFileStat(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.filename);
        parcel.writeLong(this.bytesPinned);
        parcel.writeString8(this.groupName);
    }

    private void readFromParcel(Parcel parcel) {
        this.filename = parcel.readString8();
        this.bytesPinned = parcel.readLong();
        this.groupName = parcel.readString8();
    }
}
