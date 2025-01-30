package android.net.resolv.aidl;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes.dex */
public class DnsHealthEventParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.net.resolv.aidl.DnsHealthEventParcel.1
        @Override // android.os.Parcelable.Creator
        public DnsHealthEventParcel createFromParcel(Parcel parcel) {
            DnsHealthEventParcel dnsHealthEventParcel = new DnsHealthEventParcel();
            dnsHealthEventParcel.readFromParcel(parcel);
            return dnsHealthEventParcel;
        }

        @Override // android.os.Parcelable.Creator
        public DnsHealthEventParcel[] newArray(int i) {
            return new DnsHealthEventParcel[i];
        }
    };
    public int[] successRttMicros;
    public int netId = 0;
    public int healthResult = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeInt(this.healthResult);
        parcel.writeIntArray(this.successRttMicros);
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
                this.netId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.healthResult = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.successRttMicros = parcel.createIntArray();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt);
                        return;
                    }
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        stringJoiner.add("netId: " + this.netId);
        stringJoiner.add("healthResult: " + this.healthResult);
        stringJoiner.add("successRttMicros: " + Arrays.toString(this.successRttMicros));
        return "android.net.resolv.aidl.DnsHealthEventParcel" + stringJoiner.toString();
    }
}
