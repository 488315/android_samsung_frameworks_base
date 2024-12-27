package android.net.dhcp;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class DhcpServingParamsParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int[] defaultRouters;
    public int[] dnsServers;
    public int[] excludedAddrs;
    public int serverAddr = 0;
    public int serverAddrPrefixLength = 0;
    public long dhcpLeaseTimeSecs = 0;
    public int linkMtu = 0;
    public boolean metered = false;
    public int singleClientAddr = 0;
    public boolean changePrefixOnDecline = false;
    public int leasesSubnetPrefixLength = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.dhcp.DhcpServingParamsParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DhcpServingParamsParcel dhcpServingParamsParcel = new DhcpServingParamsParcel();
            dhcpServingParamsParcel.readFromParcel(parcel);
            return dhcpServingParamsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DhcpServingParamsParcel[i];
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
                this.serverAddr = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.serverAddrPrefixLength = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.defaultRouters = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dnsServers = parcel.createIntArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.excludedAddrs = parcel.createIntArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.dhcpLeaseTimeSecs = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.linkMtu = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.metered = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.singleClientAddr = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    this.changePrefixOnDecline =
                                                            parcel.readBoolean();
                                                    if (parcel.dataPosition() - dataPosition
                                                            < readInt) {
                                                        this.leasesSubnetPrefixLength =
                                                                parcel.readInt();
                                                        if (dataPosition
                                                                > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException(
                                                                    "Overflow in the size of"
                                                                        + " parcelable");
                                                        }
                                                        parcel.setDataPosition(
                                                                dataPosition + readInt);
                                                        return;
                                                    }
                                                    if (dataPosition
                                                            > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException(
                                                                "Overflow in the size of"
                                                                    + " parcelable");
                                                    }
                                                } else if (dataPosition
                                                        > Integer.MAX_VALUE - readInt) {
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
        StringBuilder m =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                new StringBuilder("serverAddr: "),
                                this.serverAddr,
                                stringJoiner,
                                "serverAddrPrefixLength: "),
                        this.serverAddrPrefixLength,
                        stringJoiner,
                        "defaultRouters: ");
        m.append(Arrays.toString(this.defaultRouters));
        stringJoiner.add(m.toString());
        stringJoiner.add("dnsServers: " + Arrays.toString(this.dnsServers));
        stringJoiner.add("excludedAddrs: " + Arrays.toString(this.excludedAddrs));
        stringJoiner.add("dhcpLeaseTimeSecs: " + this.dhcpLeaseTimeSecs);
        StringBuilder m2 =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("linkMtu: "), this.linkMtu, stringJoiner, "metered: ");
        m2.append(this.metered);
        stringJoiner.add(m2.toString());
        StringBuilder m3 =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("singleClientAddr: "),
                        this.singleClientAddr,
                        stringJoiner,
                        "changePrefixOnDecline: ");
        m3.append(this.changePrefixOnDecline);
        stringJoiner.add(m3.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("leasesSubnetPrefixLength: "),
                        this.leasesSubnetPrefixLength,
                        stringJoiner,
                        "DhcpServingParamsParcel"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serverAddr);
        parcel.writeInt(this.serverAddrPrefixLength);
        parcel.writeIntArray(this.defaultRouters);
        parcel.writeIntArray(this.dnsServers);
        parcel.writeIntArray(this.excludedAddrs);
        parcel.writeLong(this.dhcpLeaseTimeSecs);
        parcel.writeInt(this.linkMtu);
        parcel.writeBoolean(this.metered);
        parcel.writeInt(this.singleClientAddr);
        parcel.writeBoolean(this.changePrefixOnDecline);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.leasesSubnetPrefixLength, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
