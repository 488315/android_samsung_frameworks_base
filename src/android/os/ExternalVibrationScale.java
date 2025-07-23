package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ExternalVibrationScale implements Parcelable {
    public static final Parcelable.Creator<ExternalVibrationScale> CREATOR = new Parcelable.Creator<ExternalVibrationScale>() { // from class: android.os.ExternalVibrationScale.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalVibrationScale createFromParcel(Parcel parcel) {
            ExternalVibrationScale externalVibrationScale = new ExternalVibrationScale();
            externalVibrationScale.readFromParcel(parcel);
            return externalVibrationScale;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalVibrationScale[] newArray(int i) {
            return new ExternalVibrationScale[i];
        }
    };
    public int scaleLevel = 0;
    public float scaleFactor = -1.0f;
    public float adaptiveHapticsScale = 1.0f;

    public @interface ScaleLevel {
        public static final int SCALE_HIGH = 1;
        public static final int SCALE_LOW = -1;
        public static final int SCALE_MUTE = -100;
        public static final int SCALE_NONE = 0;
        public static final int SCALE_VERY_HIGH = 2;
        public static final int SCALE_VERY_LOW = -2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.scaleLevel);
        parcel.writeFloat(this.scaleFactor);
        parcel.writeFloat(this.adaptiveHapticsScale);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.scaleLevel = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.scaleFactor = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.adaptiveHapticsScale = parcel.readFloat();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
