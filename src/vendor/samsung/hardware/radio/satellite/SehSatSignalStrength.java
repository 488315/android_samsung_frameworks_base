package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatSignalStrength implements Parcelable {
    public static final Parcelable.Creator<SehSatSignalStrength> CREATOR = new Parcelable.Creator<SehSatSignalStrength>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatSignalStrength.1
        @Override // android.os.Parcelable.Creator
        public SehSatSignalStrength createFromParcel(Parcel parcel) {
            SehSatSignalStrength sehSatSignalStrength = new SehSatSignalStrength();
            sehSatSignalStrength.readFromParcel(parcel);
            return sehSatSignalStrength;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatSignalStrength[] newArray(int i) {
            return new SehSatSignalStrength[i];
        }
    };
    public int rssi = 0;
    public int snr = 0;
    public int tx_target = 0;
    public int tx_pdet = 0;
    public int vdet = 0;
    public int satId = 0;
    public int ssRsrq = 0;
    public int ssRsrp = 0;
    public int ssSinr = 0;

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
        parcel.writeInt(this.rssi);
        parcel.writeInt(this.snr);
        parcel.writeInt(this.tx_target);
        parcel.writeInt(this.tx_pdet);
        parcel.writeInt(this.vdet);
        parcel.writeInt(this.satId);
        parcel.writeInt(this.ssRsrq);
        parcel.writeInt(this.ssRsrp);
        parcel.writeInt(this.ssSinr);
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
                this.rssi = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.snr = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.tx_target = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.tx_pdet = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.vdet = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.satId = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.ssRsrq = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.ssRsrp = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.ssSinr = parcel.readInt();
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
        stringJoiner.add("rssi: " + this.rssi);
        stringJoiner.add("snr: " + this.snr);
        stringJoiner.add("tx_target: " + this.tx_target);
        stringJoiner.add("tx_pdet: " + this.tx_pdet);
        stringJoiner.add("vdet: " + this.vdet);
        stringJoiner.add("satId: " + this.satId);
        stringJoiner.add("ssRsrq: " + this.ssRsrq);
        stringJoiner.add("ssRsrp: " + this.ssRsrp);
        stringJoiner.add("ssSinr: " + this.ssSinr);
        return "SehSatSignalStrength" + stringJoiner.toString();
    }
}
