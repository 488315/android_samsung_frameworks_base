package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class QosFilter implements Parcelable {
    public static final Parcelable.Creator<QosFilter> CREATOR = new Parcelable.Creator<QosFilter>() { // from class: android.hardware.radio.data.QosFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilter createFromParcel(Parcel parcel) {
            QosFilter qosFilter = new QosFilter();
            qosFilter.readFromParcel(parcel);
            return qosFilter;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilter[] newArray(int i) {
            return new QosFilter[i];
        }
    };
    public static final byte DIRECTION_BIDIRECTIONAL = 2;
    public static final byte DIRECTION_DOWNLINK = 0;
    public static final byte DIRECTION_UPLINK = 1;
    public static final byte PROTOCOL_AH = 51;
    public static final byte PROTOCOL_ESP = 50;
    public static final byte PROTOCOL_TCP = 6;
    public static final byte PROTOCOL_UDP = 17;
    public static final byte PROTOCOL_UNSPECIFIED = -1;
    public QosFilterIpv6FlowLabel flowLabel;
    public String[] localAddresses;
    public PortRange localPort;
    public String[] remoteAddresses;
    public PortRange remotePort;
    public QosFilterIpsecSpi spi;
    public QosFilterTypeOfService tos;
    public byte protocol = 0;
    public byte direction = 0;
    public int precedence = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStringArray(this.localAddresses);
        parcel.writeStringArray(this.remoteAddresses);
        parcel.writeTypedObject(this.localPort, i);
        parcel.writeTypedObject(this.remotePort, i);
        parcel.writeByte(this.protocol);
        parcel.writeTypedObject(this.tos, i);
        parcel.writeTypedObject(this.flowLabel, i);
        parcel.writeTypedObject(this.spi, i);
        parcel.writeByte(this.direction);
        parcel.writeInt(this.precedence);
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
                this.localAddresses = parcel.createStringArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.remoteAddresses = parcel.createStringArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.localPort = (PortRange) parcel.readTypedObject(PortRange.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.remotePort = (PortRange) parcel.readTypedObject(PortRange.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.protocol = parcel.readByte();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.tos = (QosFilterTypeOfService) parcel.readTypedObject(QosFilterTypeOfService.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.flowLabel = (QosFilterIpv6FlowLabel) parcel.readTypedObject(QosFilterIpv6FlowLabel.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.spi = (QosFilterIpsecSpi) parcel.readTypedObject(QosFilterIpsecSpi.CREATOR);
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.direction = parcel.readByte();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.precedence = parcel.readInt();
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
        stringJoiner.add("localAddresses: " + Arrays.toString(this.localAddresses));
        stringJoiner.add("remoteAddresses: " + Arrays.toString(this.remoteAddresses));
        stringJoiner.add("localPort: " + Objects.toString(this.localPort));
        stringJoiner.add("remotePort: " + Objects.toString(this.remotePort));
        stringJoiner.add("protocol: " + ((int) this.protocol));
        stringJoiner.add("tos: " + Objects.toString(this.tos));
        stringJoiner.add("flowLabel: " + Objects.toString(this.flowLabel));
        stringJoiner.add("spi: " + Objects.toString(this.spi));
        stringJoiner.add("direction: " + ((int) this.direction));
        stringJoiner.add("precedence: " + this.precedence);
        return "QosFilter" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.spi) | describeContents(this.localPort) | describeContents(this.remotePort) | describeContents(this.tos) | describeContents(this.flowLabel);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
