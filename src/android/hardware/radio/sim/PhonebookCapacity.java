package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhonebookCapacity implements Parcelable {
    public static final Parcelable.Creator<PhonebookCapacity> CREATOR = new Parcelable.Creator<PhonebookCapacity>() { // from class: android.hardware.radio.sim.PhonebookCapacity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookCapacity createFromParcel(Parcel parcel) {
            PhonebookCapacity phonebookCapacity = new PhonebookCapacity();
            phonebookCapacity.readFromParcel(parcel);
            return phonebookCapacity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookCapacity[] newArray(int i) {
            return new PhonebookCapacity[i];
        }
    };
    public int maxAdnRecords = 0;
    public int usedAdnRecords = 0;
    public int maxEmailRecords = 0;
    public int usedEmailRecords = 0;
    public int maxAdditionalNumberRecords = 0;
    public int usedAdditionalNumberRecords = 0;
    public int maxNameLen = 0;
    public int maxNumberLen = 0;
    public int maxEmailLen = 0;
    public int maxAdditionalNumberLen = 0;

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
        parcel.writeInt(this.maxAdnRecords);
        parcel.writeInt(this.usedAdnRecords);
        parcel.writeInt(this.maxEmailRecords);
        parcel.writeInt(this.usedEmailRecords);
        parcel.writeInt(this.maxAdditionalNumberRecords);
        parcel.writeInt(this.usedAdditionalNumberRecords);
        parcel.writeInt(this.maxNameLen);
        parcel.writeInt(this.maxNumberLen);
        parcel.writeInt(this.maxEmailLen);
        parcel.writeInt(this.maxAdditionalNumberLen);
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
                this.maxAdnRecords = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.usedAdnRecords = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxEmailRecords = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.usedEmailRecords = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxAdditionalNumberRecords = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.usedAdditionalNumberRecords = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.maxNameLen = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.maxNumberLen = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.maxEmailLen = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.maxAdditionalNumberLen = parcel.readInt();
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
        stringJoiner.add("maxAdnRecords: " + this.maxAdnRecords);
        stringJoiner.add("usedAdnRecords: " + this.usedAdnRecords);
        stringJoiner.add("maxEmailRecords: " + this.maxEmailRecords);
        stringJoiner.add("usedEmailRecords: " + this.usedEmailRecords);
        stringJoiner.add("maxAdditionalNumberRecords: " + this.maxAdditionalNumberRecords);
        stringJoiner.add("usedAdditionalNumberRecords: " + this.usedAdditionalNumberRecords);
        stringJoiner.add("maxNameLen: " + this.maxNameLen);
        stringJoiner.add("maxNumberLen: " + this.maxNumberLen);
        stringJoiner.add("maxEmailLen: " + this.maxEmailLen);
        stringJoiner.add("maxAdditionalNumberLen: " + this.maxAdditionalNumberLen);
        return "PhonebookCapacity" + stringJoiner.toString();
    }
}
