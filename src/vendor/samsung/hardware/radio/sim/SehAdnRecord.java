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
        int dataPosition = parcel.dataPosition();
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
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.name = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.nameDcs = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.nameLength = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.number = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.gsm8bitEmail = parcel.createByteArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.gsm8bitEmailLength = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.anr = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.anrA = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.anrB = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.anrC = parcel.readString();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.sne = parcel.createByteArray();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.sneLength = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.sneDcs = parcel.readInt();
                                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                                }
                                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                                            }
                                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
