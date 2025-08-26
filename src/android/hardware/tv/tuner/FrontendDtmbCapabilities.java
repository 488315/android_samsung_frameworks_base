package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDtmbCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendDtmbCapabilities> CREATOR = new Parcelable.Creator<FrontendDtmbCapabilities>() { // from class: android.hardware.tv.tuner.FrontendDtmbCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDtmbCapabilities createFromParcel(Parcel parcel) {
            FrontendDtmbCapabilities frontendDtmbCapabilities = new FrontendDtmbCapabilities();
            frontendDtmbCapabilities.readFromParcel(parcel);
            return frontendDtmbCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDtmbCapabilities[] newArray(int i) {
            return new FrontendDtmbCapabilities[i];
        }
    };
    public int transmissionModeCap = 0;
    public int bandwidthCap = 0;
    public int modulationCap = 0;
    public int codeRateCap = 0;
    public int guardIntervalCap = 0;
    public int interleaveModeCap = 0;

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
        parcel.writeInt(this.transmissionModeCap);
        parcel.writeInt(this.bandwidthCap);
        parcel.writeInt(this.modulationCap);
        parcel.writeInt(this.codeRateCap);
        parcel.writeInt(this.guardIntervalCap);
        parcel.writeInt(this.interleaveModeCap);
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
                this.transmissionModeCap = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bandwidthCap = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.modulationCap = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.codeRateCap = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.guardIntervalCap = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.interleaveModeCap = parcel.readInt();
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
