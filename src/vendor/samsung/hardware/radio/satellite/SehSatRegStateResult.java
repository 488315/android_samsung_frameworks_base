package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatRegStateResult implements Parcelable {
    public static final Parcelable.Creator<SehSatRegStateResult> CREATOR = new Parcelable.Creator<SehSatRegStateResult>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatRegStateResult.1
        @Override // android.os.Parcelable.Creator
        public SehSatRegStateResult createFromParcel(Parcel parcel) {
            SehSatRegStateResult sehSatRegStateResult = new SehSatRegStateResult();
            sehSatRegStateResult.readFromParcel(parcel);
            return sehSatRegStateResult;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatRegStateResult[] newArray(int i) {
            return new SehSatRegStateResult[i];
        }
    };
    public int state;
    public int mode = 0;
    public int lac = 0;
    public int ci = 0;
    public int arfcn = 0;
    public int beam_id = 0;
    public int bm_long = 0;
    public int bm_lat = 0;
    public int reject_cause = 0;

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
        parcel.writeInt(this.mode);
        parcel.writeInt(this.state);
        parcel.writeInt(this.lac);
        parcel.writeInt(this.ci);
        parcel.writeInt(this.arfcn);
        parcel.writeInt(this.beam_id);
        parcel.writeInt(this.bm_long);
        parcel.writeInt(this.bm_lat);
        parcel.writeInt(this.reject_cause);
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
                this.mode = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.state = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.lac = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.ci = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.arfcn = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.beam_id = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.bm_long = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.bm_lat = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.reject_cause = parcel.readInt();
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
        stringJoiner.add("mode: " + this.mode);
        stringJoiner.add("state: " + SehSatRegState$$.toString(this.state));
        stringJoiner.add("lac: " + this.lac);
        stringJoiner.add("ci: " + this.ci);
        stringJoiner.add("arfcn: " + this.arfcn);
        stringJoiner.add("beam_id: " + this.beam_id);
        stringJoiner.add("bm_long: " + this.bm_long);
        stringJoiner.add("bm_lat: " + this.bm_lat);
        stringJoiner.add("reject_cause: " + this.reject_cause);
        return "SehSatRegStateResult" + stringJoiner.toString();
    }
}
