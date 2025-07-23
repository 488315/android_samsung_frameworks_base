package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehImsCall implements Parcelable {
    public static final Parcelable.Creator<SehImsCall> CREATOR = new Parcelable.Creator<SehImsCall>() { // from class: vendor.samsung.hardware.radio.network.SehImsCall.1
        @Override // android.os.Parcelable.Creator
        public SehImsCall createFromParcel(Parcel parcel) {
            SehImsCall sehImsCall = new SehImsCall();
            sehImsCall.readFromParcel(parcel);
            return sehImsCall;
        }

        @Override // android.os.Parcelable.Creator
        public SehImsCall[] newArray(int i) {
            return new SehImsCall[i];
        }
    };
    public String number;
    public int state = 0;
    public int type = 0;
    public int isMt = 0;
    public int isMpty = 0;

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
        parcel.writeInt(this.state);
        parcel.writeInt(this.type);
        parcel.writeInt(this.isMt);
        parcel.writeInt(this.isMpty);
        parcel.writeString(this.number);
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
                this.state = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isMt = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isMpty = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.number = parcel.readString();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("isMt: " + this.isMt);
        stringJoiner.add("isMpty: " + this.isMpty);
        stringJoiner.add("number: " + Objects.toString(this.number));
        return "SehImsCall" + stringJoiner.toString();
    }
}
