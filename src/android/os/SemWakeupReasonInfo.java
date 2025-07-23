package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SemWakeupReasonInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<SemWakeupReasonInfo> CREATOR = new Parcelable.Creator<SemWakeupReasonInfo>() { // from class: android.os.SemWakeupReasonInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWakeupReasonInfo createFromParcel(Parcel parcel) {
            return new SemWakeupReasonInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWakeupReasonInfo[] newArray(int i) {
            return new SemWakeupReasonInfo[i];
        }
    };
    private int count;
    private long recordTime;
    private String tag;
    private long time;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemWakeupReasonInfo(String str) {
        this.tag = str;
        this.recordTime = 0L;
        this.count = 0;
        this.time = 0L;
    }

    public SemWakeupReasonInfo(String str, int i, long j) {
        this.tag = str;
        this.recordTime = 0L;
        this.count = i;
        this.time = j;
    }

    public SemWakeupReasonInfo(long j, int i, long j2) {
        this.tag = null;
        this.recordTime = j;
        this.count = i;
        this.time = j2;
    }

    public String getTag() {
        return this.tag;
    }

    public long getRecordTime() {
        return this.recordTime;
    }

    public int getCount() {
        return this.count;
    }

    public long getTime() {
        return this.time;
    }

    public void calculateDelta(SemWakeupReasonInfo semWakeupReasonInfo) {
        if (this.tag.equals(semWakeupReasonInfo.getTag())) {
            this.count = Math.max(0, this.count - semWakeupReasonInfo.getCount());
            this.time = Math.max(0L, this.time - semWakeupReasonInfo.getTime());
        }
    }

    public void updateInfo(int i, long j) {
        this.count = i;
        this.time = j;
    }

    protected SemWakeupReasonInfo(Parcel parcel) {
        this.tag = parcel.readString();
        this.count = parcel.readInt();
        this.time = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tag);
        parcel.writeInt(this.count);
        parcel.writeLong(this.time);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemWakeupReasonInfo m3618clone() {
        try {
            return (SemWakeupReasonInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
