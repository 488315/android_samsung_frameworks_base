package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class UidStatsParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int key = 0;
    public int uid = 0;
    public int sport = 0;
    public int dport = 0;
    public int ipVersion = 0;
    public int ipv4Addr = 0;
    public int ipv6Addr1 = 0;
    public int ipv6Addr2 = 0;
    public int ipv6Addr3 = 0;
    public int ipv6Addr4 = 0;
    public int ipv4sAddr = 0;
    public int ipv6sAddr1 = 0;
    public int ipv6sAddr2 = 0;
    public int ipv6sAddr3 = 0;
    public int ipv6sAddr4 = 0;
    public int tcpPackets = 0;
    public int udpPackets = 0;
    public int rxPackets = 0;
    public int txPackets = 0;
    public long rxBytes = 0;
    public long txBytes = 0;
    public long firstTxTime = 0;
    public int maxTxPacketSize = 0;
    public int minTxPacketSize = 0;
    public long maxTxInterPacketTime = 0;
    public long minTxInterPacketTime = 0;
    public long latestTxTime = 0;
    public int maxRxPacketSize = 0;
    public int minRxPacketSize = 0;
    public long maxRxInterPacketTime2 = 0;
    public long maxRxInterPacketTime = 0;
    public long latestRxTime = 0;
    public long cliMacAddr = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.UidStatsParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            UidStatsParcel uidStatsParcel = new UidStatsParcel();
            uidStatsParcel.readFromParcel(parcel);
            return uidStatsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new UidStatsParcel[i];
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
                this.key = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sport = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dport = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.ipVersion = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ipv4Addr = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.ipv6Addr1 = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.ipv6Addr2 = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.ipv6Addr3 = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    this.ipv6Addr4 = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition
                                                            < readInt) {
                                                        this.ipv4sAddr = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition
                                                                < readInt) {
                                                            this.ipv6sAddr1 = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition
                                                                    < readInt) {
                                                                this.ipv6sAddr2 = parcel.readInt();
                                                                if (parcel.dataPosition()
                                                                                - dataPosition
                                                                        < readInt) {
                                                                    this.ipv6sAddr3 =
                                                                            parcel.readInt();
                                                                    if (parcel.dataPosition()
                                                                                    - dataPosition
                                                                            < readInt) {
                                                                        this.ipv6sAddr4 =
                                                                                parcel.readInt();
                                                                        if (parcel.dataPosition()
                                                                                        - dataPosition
                                                                                < readInt) {
                                                                            this.tcpPackets =
                                                                                    parcel
                                                                                            .readInt();
                                                                            if (parcel
                                                                                                    .dataPosition()
                                                                                            - dataPosition
                                                                                    < readInt) {
                                                                                this.udpPackets =
                                                                                        parcel
                                                                                                .readInt();
                                                                                if (parcel
                                                                                                        .dataPosition()
                                                                                                - dataPosition
                                                                                        < readInt) {
                                                                                    this.rxPackets =
                                                                                            parcel
                                                                                                    .readInt();
                                                                                    if (parcel
                                                                                                            .dataPosition()
                                                                                                    - dataPosition
                                                                                            < readInt) {
                                                                                        this
                                                                                                        .txPackets =
                                                                                                parcel
                                                                                                        .readInt();
                                                                                        if (parcel
                                                                                                                .dataPosition()
                                                                                                        - dataPosition
                                                                                                < readInt) {
                                                                                            this
                                                                                                            .rxBytes =
                                                                                                    parcel
                                                                                                            .readLong();
                                                                                            if (parcel
                                                                                                                    .dataPosition()
                                                                                                            - dataPosition
                                                                                                    < readInt) {
                                                                                                this
                                                                                                                .txBytes =
                                                                                                        parcel
                                                                                                                .readLong();
                                                                                                if (parcel
                                                                                                                        .dataPosition()
                                                                                                                - dataPosition
                                                                                                        < readInt) {
                                                                                                    this
                                                                                                                    .firstTxTime =
                                                                                                            parcel
                                                                                                                    .readLong();
                                                                                                    if (parcel
                                                                                                                            .dataPosition()
                                                                                                                    - dataPosition
                                                                                                            < readInt) {
                                                                                                        this
                                                                                                                        .maxTxPacketSize =
                                                                                                                parcel
                                                                                                                        .readInt();
                                                                                                        if (parcel
                                                                                                                                .dataPosition()
                                                                                                                        - dataPosition
                                                                                                                < readInt) {
                                                                                                            this
                                                                                                                            .minTxPacketSize =
                                                                                                                    parcel
                                                                                                                            .readInt();
                                                                                                            if (parcel
                                                                                                                                    .dataPosition()
                                                                                                                            - dataPosition
                                                                                                                    < readInt) {
                                                                                                                this
                                                                                                                                .maxTxInterPacketTime =
                                                                                                                        parcel
                                                                                                                                .readLong();
                                                                                                                if (parcel
                                                                                                                                        .dataPosition()
                                                                                                                                - dataPosition
                                                                                                                        < readInt) {
                                                                                                                    this
                                                                                                                                    .minTxInterPacketTime =
                                                                                                                            parcel
                                                                                                                                    .readLong();
                                                                                                                    if (parcel
                                                                                                                                            .dataPosition()
                                                                                                                                    - dataPosition
                                                                                                                            < readInt) {
                                                                                                                        this
                                                                                                                                        .latestTxTime =
                                                                                                                                parcel
                                                                                                                                        .readLong();
                                                                                                                        if (parcel
                                                                                                                                                .dataPosition()
                                                                                                                                        - dataPosition
                                                                                                                                < readInt) {
                                                                                                                            this
                                                                                                                                            .maxRxPacketSize =
                                                                                                                                    parcel
                                                                                                                                            .readInt();
                                                                                                                            if (parcel
                                                                                                                                                    .dataPosition()
                                                                                                                                            - dataPosition
                                                                                                                                    < readInt) {
                                                                                                                                this
                                                                                                                                                .minRxPacketSize =
                                                                                                                                        parcel
                                                                                                                                                .readInt();
                                                                                                                                if (parcel
                                                                                                                                                        .dataPosition()
                                                                                                                                                - dataPosition
                                                                                                                                        < readInt) {
                                                                                                                                    this
                                                                                                                                                    .maxRxInterPacketTime2 =
                                                                                                                                            parcel
                                                                                                                                                    .readLong();
                                                                                                                                    if (parcel
                                                                                                                                                            .dataPosition()
                                                                                                                                                    - dataPosition
                                                                                                                                            < readInt) {
                                                                                                                                        this
                                                                                                                                                        .maxRxInterPacketTime =
                                                                                                                                                parcel
                                                                                                                                                        .readLong();
                                                                                                                                        if (parcel
                                                                                                                                                                .dataPosition()
                                                                                                                                                        - dataPosition
                                                                                                                                                < readInt) {
                                                                                                                                            this
                                                                                                                                                            .latestRxTime =
                                                                                                                                                    parcel
                                                                                                                                                            .readLong();
                                                                                                                                            if (parcel
                                                                                                                                                                    .dataPosition()
                                                                                                                                                            - dataPosition
                                                                                                                                                    < readInt) {
                                                                                                                                                this
                                                                                                                                                                .cliMacAddr =
                                                                                                                                                        parcel
                                                                                                                                                                .readLong();
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

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.key);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.sport);
        parcel.writeInt(this.dport);
        parcel.writeInt(this.ipVersion);
        parcel.writeInt(this.ipv4Addr);
        parcel.writeInt(this.ipv6Addr1);
        parcel.writeInt(this.ipv6Addr2);
        parcel.writeInt(this.ipv6Addr3);
        parcel.writeInt(this.ipv6Addr4);
        parcel.writeInt(this.ipv4sAddr);
        parcel.writeInt(this.ipv6sAddr1);
        parcel.writeInt(this.ipv6sAddr2);
        parcel.writeInt(this.ipv6sAddr3);
        parcel.writeInt(this.ipv6sAddr4);
        parcel.writeInt(this.tcpPackets);
        parcel.writeInt(this.udpPackets);
        parcel.writeInt(this.rxPackets);
        parcel.writeInt(this.txPackets);
        parcel.writeLong(this.rxBytes);
        parcel.writeLong(this.txBytes);
        parcel.writeLong(this.firstTxTime);
        parcel.writeInt(this.maxTxPacketSize);
        parcel.writeInt(this.minTxPacketSize);
        parcel.writeLong(this.maxTxInterPacketTime);
        parcel.writeLong(this.minTxInterPacketTime);
        parcel.writeLong(this.latestTxTime);
        parcel.writeInt(this.maxRxPacketSize);
        parcel.writeInt(this.minRxPacketSize);
        parcel.writeLong(this.maxRxInterPacketTime2);
        parcel.writeLong(this.maxRxInterPacketTime);
        parcel.writeLong(this.latestRxTime);
        parcel.writeLong(this.cliMacAddr);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
