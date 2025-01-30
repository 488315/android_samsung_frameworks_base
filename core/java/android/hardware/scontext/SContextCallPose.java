package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCallPose extends SContextEventContext {
  public static final Parcelable.Creator<SContextCallPose> CREATOR =
      new Parcelable.Creator<
          SContextCallPose>() { // from class: android.hardware.scontext.SContextCallPose.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallPose createFromParcel(Parcel in) {
          return new SContextCallPose(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallPose[] newArray(int size) {
          return new SContextCallPose[size];
        }
      };
  private Bundle mContext;

  SContextCallPose() {
    this.mContext = new Bundle();
  }

  SContextCallPose(Parcel src) {
    readFromParcel(src);
  }

  public int getPose() {
    return this.mContext.getInt("Pose");
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
