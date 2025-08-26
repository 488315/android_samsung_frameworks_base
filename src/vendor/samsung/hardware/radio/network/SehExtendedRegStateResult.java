package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehExtendedRegStateResult implements Parcelable {
    public static final Parcelable.Creator<SehExtendedRegStateResult> CREATOR = new Parcelable.Creator<SehExtendedRegStateResult>() { // from class: vendor.samsung.hardware.radio.network.SehExtendedRegStateResult.1
        @Override // android.os.Parcelable.Creator
        public SehExtendedRegStateResult createFromParcel(Parcel parcel) {
            SehExtendedRegStateResult sehExtendedRegStateResult = new SehExtendedRegStateResult();
            sehExtendedRegStateResult.readFromParcel(parcel);
            return sehExtendedRegStateResult;
        }

        @Override // android.os.Parcelable.Creator
        public SehExtendedRegStateResult[] newArray(int i) {
            return new SehExtendedRegStateResult[i];
        }
    };
    public int unprocessedDataRegState;
    public int unprocessedVoiceRegState;
    public boolean isValid = false;
    public int snapshotStatus = 0;
    public int unprocessedDataRat = 0;
    public int mobileOptionalRat = 0;
    public int imsEmergencyCallBarring = 0;
    public boolean isPsOnlyReg = false;

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
        parcel.writeBoolean(this.isValid);
        parcel.writeInt(this.snapshotStatus);
        parcel.writeInt(this.unprocessedDataRegState);
        parcel.writeInt(this.unprocessedDataRat);
        parcel.writeInt(this.mobileOptionalRat);
        parcel.writeInt(this.imsEmergencyCallBarring);
        parcel.writeInt(this.unprocessedVoiceRegState);
        parcel.writeBoolean(this.isPsOnlyReg);
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
                this.isValid = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.snapshotStatus = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.unprocessedDataRegState = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.unprocessedDataRat = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.mobileOptionalRat = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.imsEmergencyCallBarring = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.unprocessedVoiceRegState = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.isPsOnlyReg = parcel.readBoolean();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("isValid: " + this.isValid);
        stringJoiner.add("snapshotStatus: " + this.snapshotStatus);
        stringJoiner.add("unprocessedDataRegState: " + SehRegState$$.toString(this.unprocessedDataRegState));
        stringJoiner.add("unprocessedDataRat: " + this.unprocessedDataRat);
        stringJoiner.add("mobileOptionalRat: " + this.mobileOptionalRat);
        stringJoiner.add("imsEmergencyCallBarring: " + this.imsEmergencyCallBarring);
        stringJoiner.add("unprocessedVoiceRegState: " + SehRegState$$.toString(this.unprocessedVoiceRegState));
        stringJoiner.add("isPsOnlyReg: " + this.isPsOnlyReg);
        return "SehExtendedRegStateResult" + stringJoiner.toString();
    }
}
