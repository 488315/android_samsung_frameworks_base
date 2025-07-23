package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class NrSignalStrength implements Parcelable {
    public static final Parcelable.Creator<NrSignalStrength> CREATOR = new Parcelable.Creator<NrSignalStrength>() { // from class: android.hardware.radio.network.NrSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrSignalStrength createFromParcel(Parcel parcel) {
            NrSignalStrength nrSignalStrength = new NrSignalStrength();
            nrSignalStrength.readFromParcel(parcel);
            return nrSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrSignalStrength[] newArray(int i) {
            return new NrSignalStrength[i];
        }
    };
    public byte[] csiCqiReport;
    public int ssRsrp = 0;
    public int ssRsrq = 0;
    public int ssSinr = 0;
    public int csiRsrp = 0;
    public int csiRsrq = 0;
    public int csiSinr = 0;
    public int csiCqiTableIndex = 0;
    public int timingAdvance = Integer.MAX_VALUE;

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
        parcel.writeInt(this.ssRsrp);
        parcel.writeInt(this.ssRsrq);
        parcel.writeInt(this.ssSinr);
        parcel.writeInt(this.csiRsrp);
        parcel.writeInt(this.csiRsrq);
        parcel.writeInt(this.csiSinr);
        parcel.writeInt(this.csiCqiTableIndex);
        parcel.writeByteArray(this.csiCqiReport);
        parcel.writeInt(this.timingAdvance);
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
                this.ssRsrp = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.ssRsrq = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.ssSinr = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.csiRsrp = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.csiRsrq = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.csiSinr = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.csiCqiTableIndex = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.csiCqiReport = parcel.createByteArray();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.timingAdvance = parcel.readInt();
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
        stringJoiner.add("ssRsrp: " + this.ssRsrp);
        stringJoiner.add("ssRsrq: " + this.ssRsrq);
        stringJoiner.add("ssSinr: " + this.ssSinr);
        stringJoiner.add("csiRsrp: " + this.csiRsrp);
        stringJoiner.add("csiRsrq: " + this.csiRsrq);
        stringJoiner.add("csiSinr: " + this.csiSinr);
        stringJoiner.add("csiCqiTableIndex: " + this.csiCqiTableIndex);
        stringJoiner.add("csiCqiReport: " + Arrays.toString(this.csiCqiReport));
        stringJoiner.add("timingAdvance: " + this.timingAdvance);
        return "NrSignalStrength" + stringJoiner.toString();
    }
}
