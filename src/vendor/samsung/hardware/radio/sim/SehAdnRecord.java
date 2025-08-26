package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehAdnRecord implements Parcelable {
    public static final Parcelable.Creator<SehAdnRecord> CREATOR = new Parcelable.Creator<SehAdnRecord>() { // from class: vendor.samsung.hardware.radio.sim.SehAdnRecord.1
        @Override // android.os.Parcelable.Creator
        public SehAdnRecord createFromParcel(Parcel parcel) {
            SehAdnRecord sehAdnRecord = new SehAdnRecord();
            sehAdnRecord.readFromParcel(parcel);
            return sehAdnRecord;
        }

        @Override // android.os.Parcelable.Creator
        public SehAdnRecord[] newArray(int i) {
            return new SehAdnRecord[i];
        }
    };
    public String anr;
    public String anrA;
    public String anrB;
    public String anrC;
    public byte[] gsm8bitEmail;
    public byte[] name;
    public String number;
    public byte[] sne;
    public int nameDcs = 0;
    public int nameLength = 0;
    public int gsm8bitEmailLength = 0;
    public int sneLength = 0;
    public int sneDcs = 0;

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
        parcel.writeByteArray(this.name);
        parcel.writeInt(this.nameDcs);
        parcel.writeInt(this.nameLength);
        parcel.writeString(this.number);
        parcel.writeByteArray(this.gsm8bitEmail);
        parcel.writeInt(this.gsm8bitEmailLength);
        parcel.writeString(this.anr);
        parcel.writeString(this.anrA);
        parcel.writeString(this.anrB);
        parcel.writeString(this.anrC);
        parcel.writeByteArray(this.sne);
        parcel.writeInt(this.sneLength);
        parcel.writeInt(this.sneDcs);
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
                this.name = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.nameDcs = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.nameLength = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.number = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gsm8bitEmail = parcel.createByteArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.gsm8bitEmailLength = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.anr = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.anrA = parcel.readString();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.anrB = parcel.readString();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.anrC = parcel.readString();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.sne = parcel.createByteArray();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.sneLength = parcel.readInt();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.sneDcs = parcel.readInt();
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
