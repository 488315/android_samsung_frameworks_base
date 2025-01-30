package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAirMotion extends SContextEventContext {
  public static final Parcelable.Creator<SContextAirMotion> CREATOR =
      new Parcelable.Creator<
          SContextAirMotion>() { // from class: android.hardware.scontext.SContextAirMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAirMotion createFromParcel(Parcel in) {
          return new SContextAirMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAirMotion[] newArray(int size) {
          return new SContextAirMotion[size];
        }
      };
  private Bundle mContext;

  SContextAirMotion() {
    this.mContext = new Bundle();
  }

  SContextAirMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getDirection() {
    return this.mContext.getInt("Direction");
  }

  public int getAngle() {
    return this.mContext.getInt("Angle");
  }

  public int getSpeed() {
    return this.mContext.getInt("Speed");
  }

  @Override // android.hardware.scontext.SContextEventContext,
            // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle();
  }
}
