package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SuppSvcNotification implements Parcelable {
    public static final Parcelable.Creator<SuppSvcNotification> CREATOR = new Parcelable.Creator<SuppSvcNotification>() { // from class: android.hardware.radio.network.SuppSvcNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuppSvcNotification createFromParcel(Parcel parcel) {
            SuppSvcNotification suppSvcNotification = new SuppSvcNotification();
            suppSvcNotification.readFromParcel(parcel);
            return suppSvcNotification;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuppSvcNotification[] newArray(int i) {
            return new SuppSvcNotification[i];
        }
    };
    public String number;
    public boolean isMT = false;
    public int code = 0;
    public int index = 0;
    public int type = 0;

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
        parcel.writeBoolean(this.isMT);
        parcel.writeInt(this.code);
        parcel.writeInt(this.index);
        parcel.writeInt(this.type);
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
                this.isMT = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.code = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.index = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.type = parcel.readInt();
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
        stringJoiner.add("isMT: " + this.isMT);
        stringJoiner.add("code: " + this.code);
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("number: " + Objects.toString(this.number));
        return "SuppSvcNotification" + stringJoiner.toString();
    }
}
