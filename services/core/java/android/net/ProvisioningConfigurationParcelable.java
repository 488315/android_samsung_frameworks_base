package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.net.apf.ApfCapabilities;
import android.net.networkstack.aidl.dhcp.DhcpOption;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class ProvisioningConfigurationParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public ApfCapabilities apfCapabilities;
    public String displayName;
    public InitialConfigurationParcelable initialConfig;
    public Layer2InformationParcelable layer2Info;
    public Network network;
    public List options;
    public ScanResultInfoParcelable scanResultInfo;
    public StaticIpConfiguration staticIpConfig;

    @Deprecated public boolean enableIPv4 = false;

    @Deprecated public boolean enableIPv6 = false;
    public boolean usingMultinetworkPolicyTracker = false;
    public boolean usingIpReachabilityMonitor = false;
    public int requestedPreDhcpActionMs = 0;
    public int provisioningTimeoutMs = 0;
    public int ipv6AddrGenMode = 0;
    public boolean enablePreconnection = false;
    public int ipv4ProvisioningMode = 0;
    public int ipv6ProvisioningMode = 0;
    public boolean uniqueEui64AddressesOnly = false;
    public int creatorUid = 0;
    public int hostnameSetting = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.ProvisioningConfigurationParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ProvisioningConfigurationParcelable provisioningConfigurationParcelable =
                    new ProvisioningConfigurationParcelable();
            provisioningConfigurationParcelable.readFromParcel(parcel);
            return provisioningConfigurationParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ProvisioningConfigurationParcelable[i];
        }
    }

    private int describeContents(Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Collection)) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        Iterator it = ((Collection) obj).iterator();
        while (it.hasNext()) {
            i |= describeContents(it.next());
        }
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.options)
                | describeContents(this.initialConfig)
                | describeContents(this.staticIpConfig)
                | describeContents(this.apfCapabilities)
                | describeContents(this.network)
                | describeContents(this.scanResultInfo)
                | describeContents(this.layer2Info);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.enableIPv4 = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.enableIPv6 = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.usingMultinetworkPolicyTracker = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.usingIpReachabilityMonitor = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.requestedPreDhcpActionMs = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.initialConfig =
                                            (InitialConfigurationParcelable)
                                                    parcel.readTypedObject(
                                                            InitialConfigurationParcelable.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.staticIpConfig =
                                                (StaticIpConfiguration)
                                                        parcel.readTypedObject(
                                                                StaticIpConfiguration.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.apfCapabilities =
                                                    (ApfCapabilities)
                                                            parcel.readTypedObject(
                                                                    ApfCapabilities.CREATOR);
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.provisioningTimeoutMs = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    this.ipv6AddrGenMode = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition
                                                            < readInt) {
                                                        this.network =
                                                                (Network)
                                                                        parcel.readTypedObject(
                                                                                Network.CREATOR);
                                                        if (parcel.dataPosition() - dataPosition
                                                                < readInt) {
                                                            this.displayName = parcel.readString();
                                                            if (parcel.dataPosition() - dataPosition
                                                                    < readInt) {
                                                                this.enablePreconnection =
                                                                        parcel.readBoolean();
                                                                if (parcel.dataPosition()
                                                                                - dataPosition
                                                                        < readInt) {
                                                                    this.scanResultInfo =
                                                                            (ScanResultInfoParcelable)
                                                                                    parcel
                                                                                            .readTypedObject(
                                                                                                    ScanResultInfoParcelable
                                                                                                            .CREATOR);
                                                                    if (parcel.dataPosition()
                                                                                    - dataPosition
                                                                            < readInt) {
                                                                        this.layer2Info =
                                                                                (Layer2InformationParcelable)
                                                                                        parcel
                                                                                                .readTypedObject(
                                                                                                        Layer2InformationParcelable
                                                                                                                .CREATOR);
                                                                        if (parcel.dataPosition()
                                                                                        - dataPosition
                                                                                < readInt) {
                                                                            this.options =
                                                                                    parcel
                                                                                            .createTypedArrayList(
                                                                                                    DhcpOption
                                                                                                            .CREATOR);
                                                                            if (parcel
                                                                                                    .dataPosition()
                                                                                            - dataPosition
                                                                                    < readInt) {
                                                                                this
                                                                                                .ipv4ProvisioningMode =
                                                                                        parcel
                                                                                                .readInt();
                                                                                if (parcel
                                                                                                        .dataPosition()
                                                                                                - dataPosition
                                                                                        < readInt) {
                                                                                    this
                                                                                                    .ipv6ProvisioningMode =
                                                                                            parcel
                                                                                                    .readInt();
                                                                                    if (parcel
                                                                                                            .dataPosition()
                                                                                                    - dataPosition
                                                                                            < readInt) {
                                                                                        this
                                                                                                        .uniqueEui64AddressesOnly =
                                                                                                parcel
                                                                                                        .readBoolean();
                                                                                        if (parcel
                                                                                                                .dataPosition()
                                                                                                        - dataPosition
                                                                                                < readInt) {
                                                                                            this
                                                                                                            .creatorUid =
                                                                                                    parcel
                                                                                                            .readInt();
                                                                                            if (parcel
                                                                                                                    .dataPosition()
                                                                                                            - dataPosition
                                                                                                    < readInt) {
                                                                                                this
                                                                                                                .hostnameSetting =
                                                                                                        parcel
                                                                                                                .readInt();
                                                                                                if (dataPosition
                                                                                                        > Integer
                                                                                                                        .MAX_VALUE
                                                                                                                - readInt) {
                                                                                                    throw new BadParcelableException(
                                                                                                            "Overflow"
                                                                                                                + " in the"
                                                                                                                + " size"
                                                                                                                + " of parcelable");
                                                                                                }
                                                                                                parcel
                                                                                                        .setDataPosition(
                                                                                                                dataPosition
                                                                                                                        + readInt);
                                                                                                return;
                                                                                            }
                                                                                            if (dataPosition
                                                                                                    > Integer
                                                                                                                    .MAX_VALUE
                                                                                                            - readInt) {
                                                                                                throw new BadParcelableException(
                                                                                                        "Overflow"
                                                                                                            + " in the"
                                                                                                            + " size"
                                                                                                            + " of parcelable");
                                                                                            }
                                                                                        } else if (dataPosition
                                                                                                > Integer
                                                                                                                .MAX_VALUE
                                                                                                        - readInt) {
                                                                                            throw new BadParcelableException(
                                                                                                    "Overflow"
                                                                                                        + " in the"
                                                                                                        + " size"
                                                                                                        + " of parcelable");
                                                                                        }
                                                                                    } else if (dataPosition
                                                                                            > Integer
                                                                                                            .MAX_VALUE
                                                                                                    - readInt) {
                                                                                        throw new BadParcelableException(
                                                                                                "Overflow"
                                                                                                    + " in the"
                                                                                                    + " size"
                                                                                                    + " of parcelable");
                                                                                    }
                                                                                } else if (dataPosition
                                                                                        > Integer
                                                                                                        .MAX_VALUE
                                                                                                - readInt) {
                                                                                    throw new BadParcelableException(
                                                                                            "Overflow"
                                                                                                + " in the"
                                                                                                + " size"
                                                                                                + " of parcelable");
                                                                                }
                                                                            } else if (dataPosition
                                                                                    > Integer
                                                                                                    .MAX_VALUE
                                                                                            - readInt) {
                                                                                throw new BadParcelableException(
                                                                                        "Overflow"
                                                                                            + " in the"
                                                                                            + " size"
                                                                                            + " of parcelable");
                                                                            }
                                                                        } else if (dataPosition
                                                                                > Integer.MAX_VALUE
                                                                                        - readInt) {
                                                                            throw new BadParcelableException(
                                                                                    "Overflow in"
                                                                                        + " the size"
                                                                                        + " of parcelable");
                                                                        }
                                                                    } else if (dataPosition
                                                                            > Integer.MAX_VALUE
                                                                                    - readInt) {
                                                                        throw new BadParcelableException(
                                                                                "Overflow in the"
                                                                                    + " size of"
                                                                                    + " parcelable");
                                                                    }
                                                                } else if (dataPosition
                                                                        > Integer.MAX_VALUE
                                                                                - readInt) {
                                                                    throw new BadParcelableException(
                                                                            "Overflow in the size"
                                                                                + " of parcelable");
                                                                }
                                                            } else if (dataPosition
                                                                    > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException(
                                                                        "Overflow in the size of"
                                                                            + " parcelable");
                                                            }
                                                        } else if (dataPosition
                                                                > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException(
                                                                    "Overflow in the size of"
                                                                        + " parcelable");
                                                        }
                                                    } else if (dataPosition
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
        stringJoiner.add("enableIPv4: " + this.enableIPv4);
        stringJoiner.add("enableIPv6: " + this.enableIPv6);
        stringJoiner.add("usingMultinetworkPolicyTracker: " + this.usingMultinetworkPolicyTracker);
        stringJoiner.add("usingIpReachabilityMonitor: " + this.usingIpReachabilityMonitor);
        StringBuilder m =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("requestedPreDhcpActionMs: "),
                        this.requestedPreDhcpActionMs,
                        stringJoiner,
                        "initialConfig: ");
        m.append(Objects.toString(this.initialConfig));
        stringJoiner.add(m.toString());
        stringJoiner.add("staticIpConfig: " + Objects.toString(this.staticIpConfig));
        stringJoiner.add("apfCapabilities: " + Objects.toString(this.apfCapabilities));
        StringBuilder m2 =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                new StringBuilder("provisioningTimeoutMs: "),
                                this.provisioningTimeoutMs,
                                stringJoiner,
                                "ipv6AddrGenMode: "),
                        this.ipv6AddrGenMode,
                        stringJoiner,
                        "network: ");
        m2.append(Objects.toString(this.network));
        stringJoiner.add(m2.toString());
        StringBuilder m3 =
                DabTableEntry$$ExternalSyntheticOutline0.m(
                        this.displayName,
                        "enablePreconnection: ",
                        new StringBuilder("displayName: "),
                        stringJoiner);
        m3.append(this.enablePreconnection);
        stringJoiner.add(m3.toString());
        stringJoiner.add("scanResultInfo: " + Objects.toString(this.scanResultInfo));
        stringJoiner.add("layer2Info: " + Objects.toString(this.layer2Info));
        stringJoiner.add("options: " + Objects.toString(this.options));
        StringBuilder m4 =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                new StringBuilder("ipv4ProvisioningMode: "),
                                this.ipv4ProvisioningMode,
                                stringJoiner,
                                "ipv6ProvisioningMode: "),
                        this.ipv6ProvisioningMode,
                        stringJoiner,
                        "uniqueEui64AddressesOnly: ");
        m4.append(this.uniqueEui64AddressesOnly);
        stringJoiner.add(m4.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                new StringBuilder("creatorUid: "),
                                this.creatorUid,
                                stringJoiner,
                                "hostnameSetting: "),
                        this.hostnameSetting,
                        stringJoiner,
                        "ProvisioningConfigurationParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.enableIPv4);
        parcel.writeBoolean(this.enableIPv6);
        parcel.writeBoolean(this.usingMultinetworkPolicyTracker);
        parcel.writeBoolean(this.usingIpReachabilityMonitor);
        parcel.writeInt(this.requestedPreDhcpActionMs);
        parcel.writeTypedObject(this.initialConfig, i);
        parcel.writeTypedObject(this.staticIpConfig, i);
        parcel.writeTypedObject(this.apfCapabilities, i);
        parcel.writeInt(this.provisioningTimeoutMs);
        parcel.writeInt(this.ipv6AddrGenMode);
        parcel.writeTypedObject(this.network, i);
        parcel.writeString(this.displayName);
        parcel.writeBoolean(this.enablePreconnection);
        parcel.writeTypedObject(this.scanResultInfo, i);
        parcel.writeTypedObject(this.layer2Info, i);
        List list = this.options;
        if (list == null) {
            parcel.writeInt(-1);
        } else {
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeTypedObject((Parcelable) list.get(i2), i);
            }
        }
        parcel.writeInt(this.ipv4ProvisioningMode);
        parcel.writeInt(this.ipv6ProvisioningMode);
        parcel.writeBoolean(this.uniqueEui64AddressesOnly);
        parcel.writeInt(this.creatorUid);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.hostnameSetting, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
