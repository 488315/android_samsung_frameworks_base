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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.coderate);
        parcel.writeInt(this.timeInterleave);
        parcel.writeInt(this.numOfSegment);
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
                this.modulation = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.coderate = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.timeInterleave = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.numOfSegment = parcel.readInt();
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
