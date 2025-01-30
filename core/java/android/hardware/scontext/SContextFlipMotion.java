package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlipMotion extends SContextEventContext {
  public static final Parcelable.Creator<SContextFlipMotion> CREATOR =
      new Parcelable.Creator<
          SContextFlipMotion>() { // from class: android.hardware.scontext.SContextFlipMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlipMotion createFromParcel(Parcel in) {
          return new SContextFlipMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlipMotion[] newArray(int size) {
          return new SContextFlipMotion[size];
        }
      };
  private Bundle mContext;

  SContextFlipMotion() {
    this.mContext = new Bundle();
  }

  SContextFlipMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getStatus() {
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
