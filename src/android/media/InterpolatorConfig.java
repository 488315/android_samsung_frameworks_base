package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class InterpolatorConfig implements Parcelable {
    public static final Parcelable.Creator<InterpolatorConfig> CREATOR = new Parcelable.Creator<InterpolatorConfig>() { // from class: android.media.InterpolatorConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InterpolatorConfig createFromParcel(Parcel parcel) {
            InterpolatorConfig interpolatorConfig = new InterpolatorConfig();
            interpolatorConfig.readFromParcel(parcel);
            return interpolatorConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InterpolatorConfig[] newArray(int i) {
            return new InterpolatorConfig[i];
        }
    };
    public float[] xy;
    public int type = 2;
    public float firstSlope = 0.0f;
    public float lastSlope = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeFloat(this.firstSlope);
        parcel.writeFloat(this.lastSlope);
        parcel.writeFloatArray(this.xy);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.type = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.firstSlope = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.lastSlope = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.xy = parcel.createFloatArray();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
