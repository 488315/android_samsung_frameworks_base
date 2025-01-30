package android.os;


/* loaded from: classes3.dex */
public class PowerSaveState implements Parcelable {
  public static final Parcelable.Creator<PowerSaveState> CREATOR =
      new Parcelable.Creator<PowerSaveState>() { // from class: android.os.PowerSaveState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerSaveState createFromParcel(Parcel source) {
          return new PowerSaveState(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerSaveState[] newArray(int size) {
          return new PowerSaveState[size];
        }
      };
  public final boolean batterySaverEnabled;
  public final float brightnessFactor;
  public final boolean globalBatterySaverEnabled;
  public final int locationMode;
  public final int soundTriggerMode;

  public PowerSaveState(Builder builder) {
    this.batterySaverEnabled = builder.mBatterySaverEnabled;
    this.locationMode = builder.mLocationMode;
    this.soundTriggerMode = builder.mSoundTriggerMode;
    this.brightnessFactor = builder.mBrightnessFactor;
    this.globalBatterySaverEnabled = builder.mGlobalBatterySaverEnabled;
  }

  public PowerSaveState(Parcel in) {
    this.batterySaverEnabled = in.readByte() != 0;
    this.globalBatterySaverEnabled = in.readByte() != 0;
    this.locationMode = in.readInt();
    this.soundTriggerMode = in.readInt();
    this.brightnessFactor = in.readFloat();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeByte(this.batterySaverEnabled ? (byte) 1 : (byte) 0);
    parcel.writeByte(this.globalBatterySaverEnabled ? (byte) 1 : (byte) 0);
    parcel.writeInt(this.locationMode);
    parcel.writeInt(this.soundTriggerMode);
    parcel.writeFloat(this.brightnessFactor);
  }

  public static final class Builder {
    private boolean mBatterySaverEnabled = false;
    private boolean mGlobalBatterySaverEnabled = false;
    private int mLocationMode = 0;
    private int mSoundTriggerMode = 0;
    private float mBrightnessFactor = 0.5f;

    public Builder setBatterySaverEnabled(boolean enabled) {
      this.mBatterySaverEnabled = enabled;
      return this;
    }

    public Builder setGlobalBatterySaverEnabled(boolean enabled) {
      this.mGlobalBatterySaverEnabled = enabled;
      return this;
    }

    public Builder setSoundTriggerMode(int mode) {
      this.mSoundTriggerMode = mode;
      return this;
    }

    public Builder setLocationMode(int mode) {
      this.mLocationMode = mode;
      return this;
    }

    public Builder setBrightnessFactor(float factor) {
      this.mBrightnessFactor = factor;
      return this;
    }

    public PowerSaveState build() {
      return new PowerSaveState(this);
    }
  }
}
