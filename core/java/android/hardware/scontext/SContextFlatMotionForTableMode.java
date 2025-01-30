package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlatMotionForTableMode extends SContextEventContext {
  public static final Parcelable.Creator<SContextFlatMotionForTableMode> CREATOR =
      new Parcelable.Creator<
          SContextFlatMotionForTableMode>() { // from class:
                                              // android.hardware.scontext.SContextFlatMotionForTableMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotionForTableMode createFromParcel(Parcel in) {
          return new SContextFlatMotionForTableMode(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotionForTableMode[] newArray(int size) {
          return new SContextFlatMotionForTableMode[size];
        }
      };
  private Bundle mContext;

  SContextFlatMotionForTableMode() {
    this.mContext = new Bundle();
  }

  SContextFlatMotionForTableMode(Parcel src) {
    readFromParcel(src);
  }

  public int getAction() {
    return this.mContext.getInt("Action");
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
