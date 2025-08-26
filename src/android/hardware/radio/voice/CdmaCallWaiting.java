package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaCallWaiting implements Parcelable {
    public static final Parcelable.Creator<CdmaCallWaiting> CREATOR = new Parcelable.Creator<CdmaCallWaiting>() { // from class: android.hardware.radio.voice.CdmaCallWaiting.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaCallWaiting createFromParcel(Parcel parcel) {
            CdmaCallWaiting cdmaCallWaiting = new CdmaCallWaiting();
            cdmaCallWaiting.readFromParcel(parcel);
            return cdmaCallWaiting;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaCallWaiting[] newArray(int i) {
            return new CdmaCallWaiting[i];
        }
    };

    @Deprecated
    public static final int NUMBER_PLAN_DATA = 3;

    @Deprecated
    public static final int NUMBER_PLAN_ISDN = 1;

    @Deprecated
    public static final int NUMBER_PLAN_NATIONAL = 8;

    @Deprecated
    public static final int NUMBER_PLAN_PRIVATE = 9;

    @Deprecated
    public static final int NUMBER_PLAN_TELEX = 4;

    @Deprecated
    public static final int NUMBER_PLAN_UNKNOWN = 0;

    @Deprecated
    public static final int NUMBER_PRESENTATION_ALLOWED = 0;

    @Deprecated
    public static final int NUMBER_PRESENTATION_RESTRICTED = 1;

    @Deprecated
    public static final int NUMBER_PRESENTATION_UNKNOWN = 2;

    @Deprecated
    public static final int NUMBER_TYPE_INTERNATIONAL = 1;

    @Deprecated
    public static final int NUMBER_TYPE_NATIONAL = 2;

    @Deprecated
    public static final int NUMBER_TYPE_NETWORK_SPECIFIC = 3;

    @Deprecated
    public static final int NUMBER_TYPE_SUBSCRIBER = 4;

    @Deprecated
    public static final int NUMBER_TYPE_UNKNOWN = 0;

    @Deprecated
    public String name;

    @Deprecated
    public String number;

    @Deprecated
    public CdmaSignalInfoRecord signalInfoRecord;

    @Deprecated
    public int numberPresentation = 0;

    @Deprecated
    public int numberType = 0;

    @Deprecated
    public int numberPlan = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.number);
        parcel.writeInt(this.numberPresentation);
        parcel.writeString(this.name);
        parcel.writeTypedObject(this.signalInfoRecord, i);
        parcel.writeInt(this.numberType);
        parcel.writeInt(this.numberPlan);
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
                this.number = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.numberPresentation = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.name = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.signalInfoRecord = (CdmaSignalInfoRecord) parcel.readTypedObject(CdmaSignalInfoRecord.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.numberType = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.numberPlan = parcel.readInt();
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
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("numberPresentation: " + this.numberPresentation);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("signalInfoRecord: " + Objects.toString(this.signalInfoRecord));
        stringJoiner.add("numberType: " + this.numberType);
        stringJoiner.add("numberPlan: " + this.numberPlan);
        return "CdmaCallWaiting" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalInfoRecord);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
