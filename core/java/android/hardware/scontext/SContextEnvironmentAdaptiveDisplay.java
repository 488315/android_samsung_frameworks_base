package android.hardware.scontext;

import android.hardware.gnss.GnssSignalType;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironmentAdaptiveDisplay extends SContextEventContext {
  public static final Parcelable.Creator<SContextEnvironmentAdaptiveDisplay> CREATOR =
      new Parcelable.Creator<
          SContextEnvironmentAdaptiveDisplay>() { // from class:
                                                  // android.hardware.scontext.SContextEnvironmentAdaptiveDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironmentAdaptiveDisplay createFromParcel(Parcel in) {
          return new SContextEnvironmentAdaptiveDisplay(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironmentAdaptiveDisplay[] newArray(int size) {
          return new SContextEnvironmentAdaptiveDisplay[size];
        }
      };
  private Bundle mContext;

  SContextEnvironmentAdaptiveDisplay() {
    this.mContext = new Bundle();
  }

  SContextEnvironmentAdaptiveDisplay(Parcel src) {
    readFromParcel(src);
  }

  public float getRed() {
    return this.mContext.getFloat("R");
  }

  public float getGreen() {
    return this.mContext.getFloat("G");
  }

  public float getBlue() {
    return this.mContext.getFloat(GnssSignalType.CODE_TYPE_B);
  }

  public long getLux() {
    return this.mContext.getLong("Lux");
  }

  public int getCCT() {
    return this.mContext.getInt("CCT");
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
