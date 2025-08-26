package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LteSignalStrength implements Parcelable {
    public static final Parcelable.Creator<LteSignalStrength> CREATOR = new Parcelable.Creator<LteSignalStrength>() { // from class: android.hardware.radio.network.LteSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteSignalStrength createFromParcel(Parcel parcel) {
            LteSignalStrength lteSignalStrength = new LteSignalStrength();
            lteSignalStrength.readFromParcel(parcel);
            return lteSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteSignalStrength[] newArray(int i) {
            return new LteSignalStrength[i];
        }
    };
    public int signalStrength = 0;
    public int rsrp = 0;
    public int rsrq = 0;
    public int rssnr = 0;
    public int cqi = 0;
    public int timingAdvance = 0;
    public int cqiTableIndex = 0;

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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.signalStrength);
        parcel.writeInt(this.rsrp);
        parcel.writeInt(this.rsrq);
        parcel.writeInt(this.rssnr);
        parcel.writeInt(this.cqi);
        parcel.writeInt(this.timingAdvance);
        parcel.writeInt(this.cqiTableIndex);
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
                this.signalStrength = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.rsrp = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.rsrq = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.rssnr = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.cqi = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.timingAdvance = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.cqiTableIndex = parcel.readInt();
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
        stringJoiner.add("signalStrength: " + this.signalStrength);
        stringJoiner.add("rsrp: " + this.rsrp);
        stringJoiner.add("rsrq: " + this.rsrq);
        stringJoiner.add("rssnr: " + this.rssnr);
        stringJoiner.add("cqi: " + this.cqi);
        stringJoiner.add("timingAdvance: " + this.timingAdvance);
        stringJoiner.add("cqiTableIndex: " + this.cqiTableIndex);
        return "LteSignalStrength" + stringJoiner.toString();
    }
}
