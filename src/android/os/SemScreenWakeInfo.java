package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SemScreenWakeInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<SemScreenWakeInfo> CREATOR = new Parcelable.Creator<SemScreenWakeInfo>() { // from class: android.os.SemScreenWakeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemScreenWakeInfo createFromParcel(Parcel parcel) {
            return new SemScreenWakeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemScreenWakeInfo[] newArray(int i) {
            return new SemScreenWakeInfo[i];
        }
    };
    private long count;
    private String tag;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemScreenWakeInfo(String str) {
        this.tag = str;
        this.count = 0L;
    }

    public SemScreenWakeInfo(String str, long j) {
        this.tag = str;
        this.count = j;
    }

    public String getTag() {
        return this.tag;
    }

    public long getCount() {
        return this.count;
    }

    public void calculateDelta(SemScreenWakeInfo semScreenWakeInfo) {
        if (this.tag.equals(semScreenWakeInfo.getTag())) {
            this.count = Math.max(0L, this.count - semScreenWakeInfo.getCount());
        }
    }

    protected SemScreenWakeInfo(Parcel parcel) {
        this.tag = parcel.readString();
        this.count = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tag);
        parcel.writeLong(this.count);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemScreenWakeInfo m3617clone() {
        try {
            return (SemScreenWakeInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
