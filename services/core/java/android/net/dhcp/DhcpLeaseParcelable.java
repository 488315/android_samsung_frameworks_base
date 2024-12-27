package android.net.dhcp;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class DhcpLeaseParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public byte[] clientId;
    public String hostname;
    public byte[] hwAddr;
    public int netAddr = 0;
    public int prefixLength = 0;
    public long expTime = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.dhcp.DhcpLeaseParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DhcpLeaseParcelable dhcpLeaseParcelable = new DhcpLeaseParcelable();
            dhcpLeaseParcelable.readFromParcel(parcel);
            return dhcpLeaseParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DhcpLeaseParcelable[i];
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
                this.clientId = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.hwAddr = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.netAddr = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.prefixLength = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.expTime = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.hostname = parcel.readString();
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
        stringJoiner.add("clientId: " + Arrays.toString(this.clientId));
        stringJoiner.add("hwAddr: " + Arrays.toString(this.hwAddr));
        StringBuilder m = AmFmBandRange$$ExternalSyntheticOutline0.m(AmFmBandRange$$ExternalSyntheticOutline0.m(new StringBuilder("netAddr: "), this.netAddr, stringJoiner, "prefixLength: "), this.prefixLength, stringJoiner, "expTime: ");
        m.append(this.expTime);
        stringJoiner.add(m.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, DabTableEntry$$ExternalSyntheticOutline0.m(this.hostname, "DhcpLeaseParcelable", new StringBuilder("hostname: "), stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.clientId);
        parcel.writeByteArray(this.hwAddr);
        parcel.writeInt(this.netAddr);
        parcel.writeInt(this.prefixLength);
        parcel.writeLong(this.expTime);
        parcel.writeString(this.hostname);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
