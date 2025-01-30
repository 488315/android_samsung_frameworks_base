package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironment extends SContextEventContext {
  public static final Parcelable.Creator<SContextEnvironment> CREATOR =
      new Parcelable.Creator<
          SContextEnvironment>() { // from class: android.hardware.scontext.SContextEnvironment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironment createFromParcel(Parcel in) {
          return new SContextEnvironment(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironment[] newArray(int size) {
          return new SContextEnvironment[size];
        }
      };
  private Bundle mContext;

  SContextEnvironment() {
    this.mContext = new Bundle();
  }

  SContextEnvironment(Parcel src) {
    readFromParcel(src);
  }

  public int getSensorType() {
    return this.mContext.getInt("EnvSensorType");
  }

  public double[] getData(int index) {
    if (this.mContext.getInt("EnvSensorType") == 1) {
      return getTemperatureHumidityData(index);
    }
    return null;
  }

  private double[] getTemperatureHumidityData(int index) {
    if (index == 0) {
      return this.mContext.getDoubleArray("Temperature");
    }
    if (index == 1) {
      return this.mContext.getDoubleArray("Humidity");
    }
    return null;
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
