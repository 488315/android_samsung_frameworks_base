package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActiveTimeMonitor extends SContextEventContext {
  public static final Parcelable.Creator<SContextActiveTimeMonitor> CREATOR =
      new Parcelable.Creator<
          SContextActiveTimeMonitor>() { // from class:
                                         // android.hardware.scontext.SContextActiveTimeMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActiveTimeMonitor createFromParcel(Parcel in) {
          return new SContextActiveTimeMonitor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActiveTimeMonitor[] newArray(int size) {
          return new SContextActiveTimeMonitor[size];
        }
      };
  private Bundle mContext;

  SContextActiveTimeMonitor() {
    this.mContext = new Bundle();
  }

  SContextActiveTimeMonitor(Parcel src) {
    readFromParcel(src);
  }

  public int getDuration() {
    return this.mContext.getInt("ActiveTimeDuration");
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
