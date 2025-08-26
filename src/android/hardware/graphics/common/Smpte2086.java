package android.hardware.graphics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class Smpte2086 implements Parcelable {
    public static final Parcelable.Creator<Smpte2086> CREATOR = new Parcelable.Creator<Smpte2086>() { // from class: android.hardware.graphics.common.Smpte2086.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Smpte2086 createFromParcel(Parcel parcel) {
            Smpte2086 smpte2086 = new Smpte2086();
            smpte2086.readFromParcel(parcel);
            return smpte2086;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Smpte2086[] newArray(int i) {
            return new Smpte2086[i];
        }
    };
    public float maxLuminance = 0.0f;
    public float minLuminance = 0.0f;
    public XyColor primaryBlue;
    public XyColor primaryGreen;
    public XyColor primaryRed;
    public XyColor whitePoint;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.primaryRed, i);
        parcel.writeTypedObject(this.primaryGreen, i);
        parcel.writeTypedObject(this.primaryBlue, i);
        parcel.writeTypedObject(this.whitePoint, i);
        parcel.writeFloat(this.maxLuminance);
        parcel.writeFloat(this.minLuminance);
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
                this.primaryRed = (XyColor) parcel.readTypedObject(XyColor.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.primaryGreen = (XyColor) parcel.readTypedObject(XyColor.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.primaryBlue = (XyColor) parcel.readTypedObject(XyColor.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.whitePoint = (XyColor) parcel.readTypedObject(XyColor.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxLuminance = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.minLuminance = parcel.readFloat();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.whitePoint) | describeContents(this.primaryRed) | describeContents(this.primaryGreen) | describeContents(this.primaryBlue);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
