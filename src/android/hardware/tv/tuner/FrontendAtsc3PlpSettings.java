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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.plpId);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.interleaveMode);
        parcel.writeInt(this.codeRate);
        parcel.writeInt(this.fec);
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
                this.plpId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.modulation = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.interleaveMode = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.codeRate = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.fec = parcel.readInt();
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
