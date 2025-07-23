package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendAnalogSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendAnalogSettings> CREATOR = new Parcelable.Creator<FrontendAnalogSettings>() { // from class: android.hardware.tv.tuner.FrontendAnalogSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAnalogSettings createFromParcel(Parcel parcel) {
            FrontendAnalogSettings frontendAnalogSettings = new FrontendAnalogSettings();
            frontendAnalogSettings.readFromParcel(parcel);
            return frontendAnalogSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAnalogSettings[] newArray(int i) {
            return new FrontendAnalogSettings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int type = 0;
    public int aftFlag = 0;
    public int sifStandard = 0;

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
        parcel.writeInt(this.type);
        parcel.writeInt(this.aftFlag);
        parcel.writeInt(this.sifStandard);
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
                            this.type = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.aftFlag = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.sifStandard = parcel.readInt();
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
