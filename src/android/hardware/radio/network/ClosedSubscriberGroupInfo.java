package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ClosedSubscriberGroupInfo implements Parcelable {
    public static final Parcelable.Creator<ClosedSubscriberGroupInfo> CREATOR = new Parcelable.Creator<ClosedSubscriberGroupInfo>() { // from class: android.hardware.radio.network.ClosedSubscriberGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClosedSubscriberGroupInfo createFromParcel(Parcel parcel) {
            ClosedSubscriberGroupInfo closedSubscriberGroupInfo = new ClosedSubscriberGroupInfo();
            closedSubscriberGroupInfo.readFromParcel(parcel);
            return closedSubscriberGroupInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClosedSubscriberGroupInfo[] newArray(int i) {
            return new ClosedSubscriberGroupInfo[i];
        }
    };
    public String homeNodebName;
    public boolean csgIndication = false;
    public int csgIdentity = 0;

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
        parcel.writeBoolean(this.csgIndication);
        parcel.writeString(this.homeNodebName);
        parcel.writeInt(this.csgIdentity);
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
                this.csgIndication = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.homeNodebName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.csgIdentity = parcel.readInt();
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
        stringJoiner.add("csgIndication: " + this.csgIndication);
        stringJoiner.add("homeNodebName: " + Objects.toString(this.homeNodebName));
        stringJoiner.add("csgIdentity: " + this.csgIdentity);
        return "ClosedSubscriberGroupInfo" + stringJoiner.toString();
    }
}
