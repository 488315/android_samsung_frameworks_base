package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendAtscSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendAtscSettings> CREATOR = new Parcelable.Creator<FrontendAtscSettings>() { // from class: android.hardware.tv.tuner.FrontendAtscSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtscSettings createFromParcel(Parcel parcel) {
            FrontendAtscSettings frontendAtscSettings = new FrontendAtscSettings();
            frontendAtscSettings.readFromParcel(parcel);
            return frontendAtscSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtscSettings[] newArray(int i) {
            return new FrontendAtscSettings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int modulation = 0;

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
        parcel.writeLong(this.frequency);
        parcel.writeLong(this.endFrequency);
        parcel.writeInt(this.inversion);
        parcel.writeInt(this.modulation);
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
                this.frequency = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.endFrequency = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.inversion = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.modulation = parcel.readInt();
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
