package android.accessibilityservice;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class MagnificationConfig implements Parcelable {
    public static final Parcelable.Creator<MagnificationConfig> CREATOR = new Parcelable.Creator<MagnificationConfig>() { // from class: android.accessibilityservice.MagnificationConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MagnificationConfig createFromParcel(Parcel parcel) {
            return new MagnificationConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MagnificationConfig[] newArray(int i) {
            return new MagnificationConfig[i];
        }
    };
    public static final int MAGNIFICATION_MODE_DEFAULT = 0;
    public static final int MAGNIFICATION_MODE_FULLSCREEN = 1;
    public static final int MAGNIFICATION_MODE_WINDOW = 2;
    private boolean mActivated;
    private float mCenterX;
    private float mCenterY;
    private int mMode;
    private float mScale;

    @Retention(RetentionPolicy.SOURCE)
    @interface MagnificationMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MagnificationConfig() {
        this.mMode = 0;
        this.mActivated = false;
        this.mScale = Float.NaN;
        this.mCenterX = Float.NaN;
        this.mCenterY = Float.NaN;
    }

    private MagnificationConfig(Parcel parcel) {
        this.mMode = 0;
        this.mActivated = false;
        this.mScale = Float.NaN;
        this.mCenterX = Float.NaN;
        this.mCenterY = Float.NaN;
        this.mMode = parcel.readInt();
        this.mActivated = parcel.readBoolean();
        this.mScale = parcel.readFloat();
        this.mCenterX = parcel.readFloat();
        this.mCenterY = parcel.readFloat();
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean isActivated() {
        return this.mActivated;
    }

    public float getScale() {
        return this.mScale;
    }

    public float getCenterX() {
        return this.mCenterX;
    }

    public float getCenterY() {
        return this.mCenterY;
    }

    public String toString() {
        return "MagnificationConfig[mode: " + getMode() + ", activated: " + isActivated() + ", scale: " + getScale() + ", centerX: " + getCenterX() + ", centerY: " + getCenterY() + "] ";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMode);
        parcel.writeBoolean(this.mActivated);
        parcel.writeFloat(this.mScale);
        parcel.writeFloat(this.mCenterX);
        parcel.writeFloat(this.mCenterY);
    }

    public static final class Builder {
        private int mMode = 0;
        private boolean mActivated = true;
        private float mScale = Float.NaN;
        private float mCenterX = Float.NaN;
        private float mCenterY = Float.NaN;

        public Builder setMode(int i) {
            this.mMode = i;
            return this;
        }

        public Builder setActivated(boolean z) {
            this.mActivated = z;
            return this;
        }

        public Builder setScale(float f) {
            this.mScale = f;
            return this;
        }

        public Builder setCenterX(float f) {
            this.mCenterX = f;
            return this;
        }

        public Builder setCenterY(float f) {
            this.mCenterY = f;
            return this;
        }

        public MagnificationConfig build() {
            MagnificationConfig magnificationConfig = new MagnificationConfig();
            magnificationConfig.mMode = this.mMode;
            magnificationConfig.mActivated = this.mActivated;
            magnificationConfig.mScale = this.mScale;
            magnificationConfig.mCenterX = this.mCenterX;
            magnificationConfig.mCenterY = this.mCenterY;
            return magnificationConfig;
        }
    }
}
