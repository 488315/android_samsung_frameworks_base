package android.app;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes.dex */
public final class GameModeConfiguration implements Parcelable {
    public static final Parcelable.Creator<GameModeConfiguration> CREATOR = new Parcelable.Creator<GameModeConfiguration>() { // from class: android.app.GameModeConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameModeConfiguration createFromParcel(Parcel parcel) {
            return new GameModeConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameModeConfiguration[] newArray(int i) {
            return new GameModeConfiguration[i];
        }
    };
    public static final int FPS_OVERRIDE_NONE = 0;
    private final int mFpsOverride;
    private final float mScalingFactor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public static final class Builder {
        private int mFpsOverride;
        private float mScalingFactor;

        public Builder() {
        }

        public Builder(GameModeConfiguration gameModeConfiguration) {
            this.mFpsOverride = gameModeConfiguration.mFpsOverride;
            this.mScalingFactor = gameModeConfiguration.mScalingFactor;
        }

        public Builder setScalingFactor(float f) {
            double d = f;
            Preconditions.checkArgument(d >= 0.1d && d <= 1.0d, "Scaling factor should fall between 0.1 and 1.0 (inclusive)");
            this.mScalingFactor = f;
            return this;
        }

        public Builder setFpsOverride(int i) {
            Preconditions.checkArgument(i >= 0, "FPS override should be non-negative");
            this.mFpsOverride = i;
            return this;
        }

        public GameModeConfiguration build() {
            return new GameModeConfiguration(this.mScalingFactor, this.mFpsOverride);
        }
    }

    GameModeConfiguration(float f, int i) {
        this.mScalingFactor = f;
        this.mFpsOverride = i;
    }

    GameModeConfiguration(Parcel parcel) {
        this.mScalingFactor = parcel.readFloat();
        this.mFpsOverride = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mScalingFactor);
        parcel.writeInt(this.mFpsOverride);
    }

    public float getScalingFactor() {
        return this.mScalingFactor;
    }

    public int getFpsOverride() {
        return this.mFpsOverride;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GameModeConfiguration)) {
            return false;
        }
        GameModeConfiguration gameModeConfiguration = (GameModeConfiguration) obj;
        return gameModeConfiguration.mFpsOverride == this.mFpsOverride && gameModeConfiguration.mScalingFactor == this.mScalingFactor;
    }

    public int hashCode() {
        return ((217 + this.mFpsOverride) * 31) + Float.floatToIntBits(this.mScalingFactor);
    }
}
