package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSimPhonebookResponse implements Parcelable {
    public static final Parcelable.Creator<SehSimPhonebookResponse> CREATOR = new Parcelable.Creator<SehSimPhonebookResponse>() { // from class: vendor.samsung.hardware.radio.sim.SehSimPhonebookResponse.1
        @Override // android.os.Parcelable.Creator
        public SehSimPhonebookResponse createFromParcel(Parcel parcel) {
            SehSimPhonebookResponse sehSimPhonebookResponse = new SehSimPhonebookResponse();
            sehSimPhonebookResponse.readFromParcel(parcel);
            return sehSimPhonebookResponse;
        }

        @Override // android.os.Parcelable.Creator
        public SehSimPhonebookResponse[] newArray(int i) {
            return new SehSimPhonebookResponse[i];
        }
    };
    public String[] alphaTags;
    public int[] dataTypeAlphas;
    public int[] dataTypeNumbers;
    public int[] lengthAlphas;
    public int[] lengthNumbers;
    public String[] numbers;
    public int recordIndex = 0;
    public int nextIndex = 0;

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
        parcel.writeIntArray(this.lengthAlphas);
        parcel.writeIntArray(this.dataTypeAlphas);
        parcel.writeStringArray(this.alphaTags);
        parcel.writeIntArray(this.lengthNumbers);
        parcel.writeIntArray(this.dataTypeNumbers);
        parcel.writeStringArray(this.numbers);
        parcel.writeInt(this.recordIndex);
        parcel.writeInt(this.nextIndex);
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
                this.lengthAlphas = parcel.createIntArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dataTypeAlphas = parcel.createIntArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.alphaTags = parcel.createStringArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.lengthNumbers = parcel.createIntArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.dataTypeNumbers = parcel.createIntArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.numbers = parcel.createStringArray();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.recordIndex = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.nextIndex = parcel.readInt();
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
}
