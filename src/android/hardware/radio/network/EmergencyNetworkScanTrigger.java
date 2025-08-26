package android.hardware.radio.network;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EmergencyNetworkScanTrigger implements Parcelable {
    public static final Parcelable.Creator<EmergencyNetworkScanTrigger> CREATOR = new Parcelable.Creator<EmergencyNetworkScanTrigger>() { // from class: android.hardware.radio.network.EmergencyNetworkScanTrigger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger createFromParcel(Parcel parcel) {
            EmergencyNetworkScanTrigger emergencyNetworkScanTrigger = new EmergencyNetworkScanTrigger();
            emergencyNetworkScanTrigger.readFromParcel(parcel);
            return emergencyNetworkScanTrigger;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger[] newArray(int i) {
            return new EmergencyNetworkScanTrigger[i];
        }
    };
    public int[] accessNetwork;
    public int scanType = 0;

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
        parcel.writeIntArray(this.accessNetwork);
        parcel.writeInt(this.scanType);
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
                this.accessNetwork = parcel.createIntArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.scanType = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("accessNetwork: " + AccessNetwork$$.arrayToString(this.accessNetwork));
        stringJoiner.add("scanType: " + EmergencyScanType$$.toString(this.scanType));
        return "EmergencyNetworkScanTrigger" + stringJoiner.toString();
    }
}
