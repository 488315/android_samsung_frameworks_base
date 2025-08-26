package android.hardware.radio.network;

import android.hardware.radio.RadioTechnology$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RegStateResult implements Parcelable {
    public static final Parcelable.Creator<RegStateResult> CREATOR = new Parcelable.Creator<RegStateResult>() { // from class: android.hardware.radio.network.RegStateResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegStateResult createFromParcel(Parcel parcel) {
            RegStateResult regStateResult = new RegStateResult();
            regStateResult.readFromParcel(parcel);
            return regStateResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegStateResult[] newArray(int i) {
            return new RegStateResult[i];
        }
    };
    public AccessTechnologySpecificInfo accessTechnologySpecificInfo;
    public CellIdentity cellIdentity;
    public String registeredPlmn;
    public int regState = 0;
    public int rat = 0;
    public int reasonForDenial = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.reasonForDenial);
        parcel.writeTypedObject(this.cellIdentity, i);
        parcel.writeString(this.registeredPlmn);
        parcel.writeTypedObject(this.accessTechnologySpecificInfo, i);
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
                this.regState = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.rat = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.reasonForDenial = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.registeredPlmn = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.accessTechnologySpecificInfo = (AccessTechnologySpecificInfo) parcel.readTypedObject(AccessTechnologySpecificInfo.CREATOR);
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("regState: " + RegState$$.toString(this.regState));
        stringJoiner.add("rat: " + RadioTechnology$$.toString(this.rat));
        stringJoiner.add("reasonForDenial: " + RegistrationFailCause$$.toString(this.reasonForDenial));
        stringJoiner.add("cellIdentity: " + Objects.toString(this.cellIdentity));
        stringJoiner.add("registeredPlmn: " + Objects.toString(this.registeredPlmn));
        stringJoiner.add("accessTechnologySpecificInfo: " + Objects.toString(this.accessTechnologySpecificInfo));
        return "RegStateResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.accessTechnologySpecificInfo) | describeContents(this.cellIdentity);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
