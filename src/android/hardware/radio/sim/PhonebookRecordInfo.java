package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhonebookRecordInfo implements Parcelable {
    public static final Parcelable.Creator<PhonebookRecordInfo> CREATOR = new Parcelable.Creator<PhonebookRecordInfo>() { // from class: android.hardware.radio.sim.PhonebookRecordInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookRecordInfo createFromParcel(Parcel parcel) {
            PhonebookRecordInfo phonebookRecordInfo = new PhonebookRecordInfo();
            phonebookRecordInfo.readFromParcel(parcel);
            return phonebookRecordInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookRecordInfo[] newArray(int i) {
            return new PhonebookRecordInfo[i];
        }
    };
    public String[] additionalNumbers;
    public String[] emails;
    public String name;
    public String number;
    public int recordId = 0;

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
        parcel.writeInt(this.recordId);
        parcel.writeString(this.name);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.emails);
        parcel.writeStringArray(this.additionalNumbers);
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
                this.recordId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.number = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.emails = parcel.createStringArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.additionalNumbers = parcel.createStringArray();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("recordId: " + this.recordId);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("emails: " + Arrays.toString(this.emails));
        stringJoiner.add("additionalNumbers: " + Arrays.toString(this.additionalNumbers));
        return "PhonebookRecordInfo" + stringJoiner.toString();
    }
}
