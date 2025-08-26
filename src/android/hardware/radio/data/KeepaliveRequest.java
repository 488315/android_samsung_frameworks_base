package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class KeepaliveRequest implements Parcelable {
    public static final Parcelable.Creator<KeepaliveRequest> CREATOR = new Parcelable.Creator<KeepaliveRequest>() { // from class: android.hardware.radio.data.KeepaliveRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeepaliveRequest createFromParcel(Parcel parcel) {
            KeepaliveRequest keepaliveRequest = new KeepaliveRequest();
            keepaliveRequest.readFromParcel(parcel);
            return keepaliveRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeepaliveRequest[] newArray(int i) {
            return new KeepaliveRequest[i];
        }
    };
    public static final int TYPE_NATT_IPV4 = 0;
    public static final int TYPE_NATT_IPV6 = 1;
    public byte[] destinationAddress;
    public byte[] sourceAddress;
    public int type = 0;
    public int sourcePort = 0;
    public int destinationPort = 0;
    public int maxKeepaliveIntervalMillis = 0;
    public int cid = 0;

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
        parcel.writeInt(this.type);
        parcel.writeByteArray(this.sourceAddress);
        parcel.writeInt(this.sourcePort);
        parcel.writeByteArray(this.destinationAddress);
        parcel.writeInt(this.destinationPort);
        parcel.writeInt(this.maxKeepaliveIntervalMillis);
        parcel.writeInt(this.cid);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sourceAddress = parcel.createByteArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sourcePort = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.destinationAddress = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.destinationPort = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.maxKeepaliveIntervalMillis = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.cid = parcel.readInt();
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("sourceAddress: " + Arrays.toString(this.sourceAddress));
        stringJoiner.add("sourcePort: " + this.sourcePort);
        stringJoiner.add("destinationAddress: " + Arrays.toString(this.destinationAddress));
        stringJoiner.add("destinationPort: " + this.destinationPort);
        stringJoiner.add("maxKeepaliveIntervalMillis: " + this.maxKeepaliveIntervalMillis);
        stringJoiner.add("cid: " + this.cid);
        return "KeepaliveRequest" + stringJoiner.toString();
    }
}
