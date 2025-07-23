package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LinkCapacityEstimate implements Parcelable {
    public static final Parcelable.Creator<LinkCapacityEstimate> CREATOR = new Parcelable.Creator<LinkCapacityEstimate>() { // from class: android.hardware.radio.network.LinkCapacityEstimate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkCapacityEstimate createFromParcel(Parcel parcel) {
            LinkCapacityEstimate linkCapacityEstimate = new LinkCapacityEstimate();
            linkCapacityEstimate.readFromParcel(parcel);
            return linkCapacityEstimate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkCapacityEstimate[] newArray(int i) {
            return new LinkCapacityEstimate[i];
        }
    };
    public int downlinkCapacityKbps = 0;
    public int uplinkCapacityKbps = 0;
    public int secondaryDownlinkCapacityKbps = 0;
    public int secondaryUplinkCapacityKbps = 0;

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
        parcel.writeInt(this.downlinkCapacityKbps);
        parcel.writeInt(this.uplinkCapacityKbps);
        parcel.writeInt(this.secondaryDownlinkCapacityKbps);
        parcel.writeInt(this.secondaryUplinkCapacityKbps);
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
                this.downlinkCapacityKbps = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uplinkCapacityKbps = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.secondaryDownlinkCapacityKbps = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.secondaryUplinkCapacityKbps = parcel.readInt();
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
        stringJoiner.add("downlinkCapacityKbps: " + this.downlinkCapacityKbps);
        stringJoiner.add("uplinkCapacityKbps: " + this.uplinkCapacityKbps);
        stringJoiner.add("secondaryDownlinkCapacityKbps: " + this.secondaryDownlinkCapacityKbps);
        stringJoiner.add("secondaryUplinkCapacityKbps: " + this.secondaryUplinkCapacityKbps);
        return "LinkCapacityEstimate" + stringJoiner.toString();
    }
}
