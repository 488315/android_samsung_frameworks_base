package android.os;


/* loaded from: classes3.dex */
public class SemWakeupReasonInfo implements Cloneable, Parcelable {
  public static final Parcelable.Creator<SemWakeupReasonInfo> CREATOR =
      new Parcelable.Creator<
          SemWakeupReasonInfo>() { // from class: android.os.SemWakeupReasonInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWakeupReasonInfo createFromParcel(Parcel in) {
          return new SemWakeupReasonInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWakeupReasonInfo[] newArray(int size) {
          return new SemWakeupReasonInfo[size];
        }
      };
  private long count;
  private long recordTime;
  private String tag;
  private long time;

  public SemWakeupReasonInfo(String tag) {
    this.tag = tag;
    this.recordTime = 0L;
    this.count = 0L;
    this.time = 0L;
  }

  public SemWakeupReasonInfo(String tag, long count, long time) {
    this.tag = tag;
    this.recordTime = 0L;
    this.count = count;
    this.time = time;
  }

  public SemWakeupReasonInfo(long recordTime, long count, long time) {
    this.tag = null;
    this.recordTime = recordTime;
    this.count = count;
    this.time = time;
  }

  public String getTag() {
    return this.tag;
  }

  public long getRecordTime() {
    return this.recordTime;
  }

  public long getCount() {
    return this.count;
  }

  public long getTime() {
    return this.time;
  }

  public void calculateDelta(SemWakeupReasonInfo prev) {
    if (this.tag.equals(prev.getTag())) {
      this.count = Math.max(0L, this.count - prev.getCount());
      this.time = Math.max(0L, this.time - prev.getTime());
    }
  }

  public void updateInfo(long count, long time) {
    this.count = count;
    this.time = time;
  }

  protected SemWakeupReasonInfo(Parcel in) {
    this.tag = in.readString();
    this.count = in.readLong();
    this.time = in.readLong();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.tag);
    parcel.writeLong(this.count);
    parcel.writeLong(this.time);
  }

  /* renamed from: clone, reason: merged with bridge method [inline-methods] */
  public SemWakeupReasonInfo m3689clone() {
    try {
      return (SemWakeupReasonInfo) super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
}
