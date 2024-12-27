package android.os;


public class SemScreenWakeInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<SemScreenWakeInfo> CREATOR =
            new Parcelable.Creator<
                    SemScreenWakeInfo>() { // from class: android.os.SemScreenWakeInfo.1
                @Override // android.os.Parcelable.Creator
                public SemScreenWakeInfo createFromParcel(Parcel in) {
                    return new SemScreenWakeInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemScreenWakeInfo[] newArray(int size) {
                    return new SemScreenWakeInfo[size];
                }
            };
    private long count;
    private String tag;

    public SemScreenWakeInfo(String tag) {
        this.tag = tag;
        this.count = 0L;
    }

    public SemScreenWakeInfo(String tag, long count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return this.tag;
    }

    public long getCount() {
        return this.count;
    }

    public void calculateDelta(SemScreenWakeInfo prev) {
        if (this.tag.equals(prev.getTag())) {
            this.count = Math.max(0L, this.count - prev.getCount());
        }
    }

    protected SemScreenWakeInfo(Parcel in) {
        this.tag = in.readString();
        this.count = in.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tag);
        parcel.writeLong(this.count);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemScreenWakeInfo m3365clone() {
        try {
            return (SemScreenWakeInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
