package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendIsdbtLayerSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendIsdbtLayerSettings> CREATOR = new Parcelable.Creator<FrontendIsdbtLayerSettings>() { // from class: android.hardware.tv.tuner.FrontendIsdbtLayerSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtLayerSettings createFromParcel(Parcel parcel) {
            FrontendIsdbtLayerSettings frontendIsdbtLayerSettings = new FrontendIsdbtLayerSettings();
            frontendIsdbtLayerSettings.readFromParcel(parcel);
            return frontendIsdbtLayerSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtLayerSettings[] newArray(int i) {
            return new FrontendIsdbtLayerSettings[i];
        }
    };
    public int modulation = 0;
    public int coderate = 0;
    public int timeInterleave = 0;
    public int numOfSegment = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.coderate);
        parcel.writeInt(this.timeInterleave);
        parcel.writeInt(this.numOfSegment);
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
                this.modulation = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.coderate = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.timeInterleave = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.numOfSegment = parcel.readInt();
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
