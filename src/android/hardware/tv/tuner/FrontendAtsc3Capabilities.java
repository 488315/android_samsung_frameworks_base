package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendAtsc3Capabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendAtsc3Capabilities> CREATOR = new Parcelable.Creator<FrontendAtsc3Capabilities>() { // from class: android.hardware.tv.tuner.FrontendAtsc3Capabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3Capabilities createFromParcel(Parcel parcel) {
            FrontendAtsc3Capabilities frontendAtsc3Capabilities = new FrontendAtsc3Capabilities();
            frontendAtsc3Capabilities.readFromParcel(parcel);
            return frontendAtsc3Capabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3Capabilities[] newArray(int i) {
            return new FrontendAtsc3Capabilities[i];
        }
    };
    public int bandwidthCap = 0;
    public int modulationCap = 0;
    public int timeInterleaveModeCap = 0;
    public int codeRateCap = 0;
    public int fecCap = 0;
    public byte demodOutputFormatCap = 0;

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
        parcel.writeInt(this.bandwidthCap);
        parcel.writeInt(this.modulationCap);
        parcel.writeInt(this.timeInterleaveModeCap);
        parcel.writeInt(this.codeRateCap);
        parcel.writeInt(this.fecCap);
        parcel.writeByte(this.demodOutputFormatCap);
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
                this.bandwidthCap = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.modulationCap = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.timeInterleaveModeCap = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.codeRateCap = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.fecCap = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.demodOutputFormatCap = parcel.readByte();
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
