package android.app.pinner;

import android.os.Parcel;
import android.os.Parcelable;

public final class PinnedFileStat implements Parcelable {
    public static final Parcelable.Creator<PinnedFileStat> CREATOR =
            new Parcelable.Creator<
                    PinnedFileStat>() { // from class: android.app.pinner.PinnedFileStat.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PinnedFileStat createFromParcel(Parcel source) {
                    return new PinnedFileStat(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PinnedFileStat[] newArray(int size) {
                    return new PinnedFileStat[size];
                }
            };
    private long bytesPinned;
    private String filename;
    private String groupName;

    public long getBytesPinned() {
        return this.bytesPinned;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public PinnedFileStat(String filename, long bytesPinned, String groupName) {
        this.filename = filename;
        this.bytesPinned = bytesPinned;
        this.groupName = groupName;
    }

    private PinnedFileStat(Parcel source) {
        readFromParcel(source);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.filename);
        dest.writeLong(this.bytesPinned);
        dest.writeString8(this.groupName);
    }

    private void readFromParcel(Parcel source) {
        this.filename = source.readString8();
        this.bytesPinned = source.readLong();
        this.groupName = source.readString8();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
