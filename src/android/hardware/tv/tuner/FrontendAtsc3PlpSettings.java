package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendAtsc3PlpSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendAtsc3PlpSettings> CREATOR = new Parcelable.Creator<FrontendAtsc3PlpSettings>() { // from class: android.hardware.tv.tuner.FrontendAtsc3PlpSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3PlpSettings createFromParcel(Parcel parcel) {
            FrontendAtsc3PlpSettings frontendAtsc3PlpSettings = new FrontendAtsc3PlpSettings();
            frontendAtsc3PlpSettings.readFromParcel(parcel);
            return frontendAtsc3PlpSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3PlpSettings[] newArray(int i) {
            return new FrontendAtsc3PlpSettings[i];
        }
    };
    public int plpId = 0;
    public int modulation = 0;
    public int interleaveMode = 0;
    public int codeRate = 0;
    public int fec = 0;

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
        parcel.writeInt(this.plpId);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.interleaveMode);
        parcel.writeInt(this.codeRate);
        parcel.writeInt(this.fec);
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
                this.plpId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.modulation = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.interleaveMode = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.codeRate = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.fec = parcel.readInt();
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
