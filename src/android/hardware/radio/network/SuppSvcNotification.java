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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isMT);
        parcel.writeInt(this.code);
        parcel.writeInt(this.index);
        parcel.writeInt(this.type);
        parcel.writeString(this.number);
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
                this.isMT = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.code = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.index = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.type = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.number = parcel.readString();
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
        stringJoiner.add("isMT: " + this.isMT);
        stringJoiner.add("code: " + this.code);
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("number: " + Objects.toString(this.number));
        return "SuppSvcNotification" + stringJoiner.toString();
    }
}
