package android.net.ipmemorystore;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class NetworkAttributesParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public byte[] assignedV4Address;
    public String cluster;
    public Blob[] dnsAddresses;
    public IPv6ProvisioningLossQuirkParcelable ipv6ProvisioningLossQuirk;
    public long assignedV4AddressExpiry = 0;
    public int mtu = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.ipmemorystore.NetworkAttributesParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            NetworkAttributesParcelable networkAttributesParcelable = new NetworkAttributesParcelable();
            networkAttributesParcelable.readFromParcel(parcel);
            return networkAttributesParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NetworkAttributesParcelable[i];
        }
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ipv6ProvisioningLossQuirk) | describeContents(this.dnsAddresses);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.assignedV4Address = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.assignedV4AddressExpiry = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.cluster = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dnsAddresses = (Blob[]) parcel.createTypedArray(Blob.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.mtu = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ipv6ProvisioningLossQuirk = (IPv6ProvisioningLossQuirkParcelable) parcel.readTypedObject(IPv6ProvisioningLossQuirkParcelable.CREATOR);
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
        stringJoiner.add("assignedV4Address: " + Arrays.toString(this.assignedV4Address));
        stringJoiner.add("assignedV4AddressExpiry: " + this.assignedV4AddressExpiry);
        StringBuilder m = AmFmBandRange$$ExternalSyntheticOutline0.m(AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.dnsAddresses), "mtu: ", DabTableEntry$$ExternalSyntheticOutline0.m(this.cluster, "dnsAddresses: ", new StringBuilder("cluster: "), stringJoiner), stringJoiner), this.mtu, stringJoiner, "ipv6ProvisioningLossQuirk: ");
        m.append(Objects.toString(this.ipv6ProvisioningLossQuirk));
        stringJoiner.add(m.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, new StringBuilder("NetworkAttributesParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.assignedV4Address);
        parcel.writeLong(this.assignedV4AddressExpiry);
        parcel.writeString(this.cluster);
        parcel.writeTypedArray(this.dnsAddresses, i);
        parcel.writeInt(this.mtu);
        parcel.writeTypedObject(this.ipv6ProvisioningLossQuirk, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
