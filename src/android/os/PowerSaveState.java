package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class PowerSaveState implements Parcelable {
    public static final Parcelable.Creator<PowerSaveState> CREATOR = new Parcelable.Creator<PowerSaveState>() { // from class: android.os.PowerSaveState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerSaveState createFromParcel(Parcel parcel) {
            return new PowerSaveState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerSaveState[] newArray(int i) {
            return new PowerSaveState[i];
        }
    };
    public final boolean batterySaverEnabled;
    public final float brightnessFactor;
    public final boolean globalBatterySaverEnabled;
    public final int locationMode;
    public final int soundTriggerMode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PowerSaveState(Builder builder) {
        this.batterySaverEnabled = builder.mBatterySaverEnabled;
        this.locationMode = builder.mLocationMode;
        this.soundTriggerMode = builder.mSoundTriggerMode;
        this.brightnessFactor = builder.mBrightnessFactor;
        this.globalBatterySaverEnabled = builder.mGlobalBatterySaverEnabled;
    }

    public PowerSaveState(Parcel parcel) {
        this.batterySaverEnabled = parcel.readByte() != 0;
        this.globalBatterySaverEnabled = parcel.readByte() != 0;
        this.locationMode = parcel.readInt();
        this.soundTriggerMode = parcel.readInt();
        this.brightnessFactor = parcel.readFloat();
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

        public Builder setBatterySaverEnabled(boolean z) {
            this.mBatterySaverEnabled = z;
            return this;
        }

        public Builder setGlobalBatterySaverEnabled(boolean z) {
            this.mGlobalBatterySaverEnabled = z;
            return this;
        }

        public Builder setSoundTriggerMode(int i) {
            this.mSoundTriggerMode = i;
            return this;
        }

        public Builder setLocationMode(int i) {
            this.mLocationMode = i;
            return this;
        }

        public Builder setBrightnessFactor(float f) {
            this.mBrightnessFactor = f;
            return this;
        }

        public PowerSaveState build() {
            return new PowerSaveState(this);
        }
    }
}
