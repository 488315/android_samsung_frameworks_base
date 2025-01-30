package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotification extends SContextEventContext {
  public static final Parcelable.Creator<SContextActivityNotification> CREATOR =
      new Parcelable.Creator<
          SContextActivityNotification>() { // from class:
                                            // android.hardware.scontext.SContextActivityNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityNotification createFromParcel(Parcel in) {
          return new SContextActivityNotification(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityNotification[] newArray(int size) {
          return new SContextActivityNotification[size];
        }
      };
  private Bundle mContext;

  SContextActivityNotification() {
    this.mContext = new Bundle();
  }

  SContextActivityNotification(Parcel src) {
    readFromParcel(src);
  }

  public long getTimeStamp() {
    return this.mContext.getLong("TimeStamp");
  }

  public int getStatus() {
    return this.mContext.getInt("ActivityType");
  }

  public int getAccuracy() {
    return this.mContext.getInt("Accuracy");
  }

  @Override // android.hardware.scontext.SContextEventContext,
            // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle();
  }
}
