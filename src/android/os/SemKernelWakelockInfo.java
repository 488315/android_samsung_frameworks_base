package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SemKernelWakelockInfo implements Cloneable, Parcelable, Comparable<SemKernelWakelockInfo> {
    public static final Parcelable.Creator<SemKernelWakelockInfo> CREATOR = new Parcelable.Creator<SemKernelWakelockInfo>() { // from class: android.os.SemKernelWakelockInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemKernelWakelockInfo createFromParcel(Parcel parcel) {
            return new SemKernelWakelockInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemKernelWakelockInfo[] newArray(int i) {
            return new SemKernelWakelockInfo[i];
        }
    };
    private int count;
    private String tag;
    private long time;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemKernelWakelockInfo(String str) {
        this.tag = str;
        this.count = 0;
        this.time = 0L;
    }

    public SemKernelWakelockInfo(String str, int i, long j) {
        this.tag = str;
        this.count = i;
        this.time = j;
    }

    public String getTag() {
        return this.tag;
    }

    public int getCount() {
        return this.count;
    }

    public long getTime() {
        return this.time;
    }

    public void calculateDelta(SemKernelWakelockInfo semKernelWakelockInfo) {
        if (this.tag.equals(semKernelWakelockInfo.getTag())) {
            this.count = Math.max(0, this.count - semKernelWakelockInfo.getCount());
            this.time = Math.max(0L, this.time - semKernelWakelockInfo.getTime());
        }
    }

    protected SemKernelWakelockInfo(Parcel parcel) {
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

    @Override // java.lang.Comparable
    public int compareTo(SemKernelWakelockInfo semKernelWakelockInfo) {
        return (int) (semKernelWakelockInfo.getTime() - this.time);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemKernelWakelockInfo m3616clone() {
        try {
            return (SemKernelWakelockInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
