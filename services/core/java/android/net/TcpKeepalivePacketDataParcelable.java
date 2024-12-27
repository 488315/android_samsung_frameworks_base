package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.StringJoiner;

public class TcpKeepalivePacketDataParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public byte[] dstAddress;
    public byte[] srcAddress;
    public int srcPort = 0;
    public int dstPort = 0;
    public int seq = 0;
    public int ack = 0;
    public int rcvWnd = 0;
    public int rcvWndScale = 0;
    public int tos = 0;
    public int ttl = 0;

    /* renamed from: android.net.TcpKeepalivePacketDataParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable =
                    new TcpKeepalivePacketDataParcelable();
            tcpKeepalivePacketDataParcelable.readFromParcel(parcel);
            return tcpKeepalivePacketDataParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TcpKeepalivePacketDataParcelable[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.srcAddress = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.srcPort = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.dstAddress = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dstPort = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.seq = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ack = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.rcvWnd = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.rcvWndScale = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.tos = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    this.ttl = parcel.readInt();
                                                    if (dataPosition
                                                            > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException(
                                                                "Overflow in the size of"
                                                                    + " parcelable");
                                                    }
                                                    parcel.setDataPosition(dataPosition + readInt);
                                                    return;
                                                }
                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException(
                                                            "Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException(
                                                        "Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
        stringJoiner.add("srcAddress: " + Arrays.toString(this.srcAddress));
        StringBuilder m =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("srcPort: "), this.srcPort, stringJoiner, "dstAddress: ");
        m.append(Arrays.toString(this.dstAddress));
        stringJoiner.add(m.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                                AmFmBandRange$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "dstPort:"
                                                                                            + " "),
                                                                                this.dstPort,
                                                                                stringJoiner,
                                                                                "seq: "),
                                                                this.seq,
                                                                stringJoiner,
                                                                "ack: "),
                                                        this.ack,
                                                        stringJoiner,
                                                        "rcvWnd: "),
                                                this.rcvWnd,
                                                stringJoiner,
                                                "rcvWndScale: "),
                                        this.rcvWndScale,
                                        stringJoiner,
                                        "tos: "),
                                this.tos,
                                stringJoiner,
                                "ttl: "),
                        this.ttl,
                        stringJoiner,
                        "TcpKeepalivePacketDataParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.srcAddress);
        parcel.writeInt(this.srcPort);
        parcel.writeByteArray(this.dstAddress);
        parcel.writeInt(this.dstPort);
        parcel.writeInt(this.seq);
        parcel.writeInt(this.ack);
        parcel.writeInt(this.rcvWnd);
        parcel.writeInt(this.rcvWndScale);
        parcel.writeInt(this.tos);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.ttl, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
