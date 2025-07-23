package android.hardware.lights;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class LightState implements Parcelable {
    public static final Parcelable.Creator<LightState> CREATOR = new Parcelable.Creator<LightState>() { // from class: android.hardware.lights.LightState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LightState createFromParcel(Parcel parcel) {
            return new LightState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LightState[] newArray(int i) {
            return new LightState[i];
        }
    };
    private final int mColor;
    private final int mPlayerId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    @Deprecated
    public LightState(int i) {
        this(i, 0);
    }

    public LightState(int i, int i2) {
        this.mColor = i;
        this.mPlayerId = i2;
    }

    public static final class Builder {
        private int mValue = 0;
        private boolean mIsForPlayerId = false;

        public Builder setColor(int i) {
            this.mIsForPlayerId = false;
            this.mValue = i;
            return this;
        }

        public Builder setPlayerId(int i) {
            this.mIsForPlayerId = true;
            this.mValue = i;
            return this;
        }

        public LightState build() {
            if (!this.mIsForPlayerId) {
                return new LightState(this.mValue, 0);
            }
            return new LightState(0, this.mValue);
        }
    }

    private LightState(Parcel parcel) {
        this.mColor = parcel.readInt();
        this.mPlayerId = parcel.readInt();
    }

    public int getColor() {
        return this.mColor;
    }

    public int getPlayerId() {
        return this.mPlayerId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mPlayerId);
    }

    public String toString() {
        return "LightState{Color=0x" + Integer.toHexString(this.mColor) + ", PlayerId=" + this.mPlayerId + "}";
    }
}
