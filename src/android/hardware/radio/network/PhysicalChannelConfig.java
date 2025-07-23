package android.hardware.radio.network;

import android.hardware.radio.RadioTechnology$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhysicalChannelConfig implements Parcelable {
    public static final Parcelable.Creator<PhysicalChannelConfig> CREATOR = new Parcelable.Creator<PhysicalChannelConfig>() { // from class: android.hardware.radio.network.PhysicalChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfig createFromParcel(Parcel parcel) {
            PhysicalChannelConfig physicalChannelConfig = new PhysicalChannelConfig();
            physicalChannelConfig.readFromParcel(parcel);
            return physicalChannelConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfig[] newArray(int i) {
            return new PhysicalChannelConfig[i];
        }
    };
    public PhysicalChannelConfigBand band;
    public int[] contextIds;
    public int status = 0;
    public int rat = 0;
    public int downlinkChannelNumber = 0;
    public int uplinkChannelNumber = 0;
    public int cellBandwidthDownlinkKhz = 0;
    public int cellBandwidthUplinkKhz = 0;
    public int physicalCellId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.downlinkChannelNumber);
        parcel.writeInt(this.uplinkChannelNumber);
        parcel.writeInt(this.cellBandwidthDownlinkKhz);
        parcel.writeInt(this.cellBandwidthUplinkKhz);
        parcel.writeIntArray(this.contextIds);
        parcel.writeInt(this.physicalCellId);
        parcel.writeTypedObject(this.band, i);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.rat = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.downlinkChannelNumber = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.uplinkChannelNumber = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.cellBandwidthDownlinkKhz = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.cellBandwidthUplinkKhz = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.contextIds = parcel.createIntArray();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.physicalCellId = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.band = (PhysicalChannelConfigBand) parcel.readTypedObject(PhysicalChannelConfigBand.CREATOR);
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
        stringJoiner.add("status: " + CellConnectionStatus$$.toString(this.status));
        stringJoiner.add("rat: " + RadioTechnology$$.toString(this.rat));
        stringJoiner.add("downlinkChannelNumber: " + this.downlinkChannelNumber);
        stringJoiner.add("uplinkChannelNumber: " + this.uplinkChannelNumber);
        stringJoiner.add("cellBandwidthDownlinkKhz: " + this.cellBandwidthDownlinkKhz);
        stringJoiner.add("cellBandwidthUplinkKhz: " + this.cellBandwidthUplinkKhz);
        stringJoiner.add("contextIds: " + Arrays.toString(this.contextIds));
        stringJoiner.add("physicalCellId: " + this.physicalCellId);
        stringJoiner.add("band: " + Objects.toString(this.band));
        return "PhysicalChannelConfig" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.band);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
