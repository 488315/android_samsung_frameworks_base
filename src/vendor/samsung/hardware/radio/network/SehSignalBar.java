package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSignalBar implements Parcelable {
    public static final Parcelable.Creator<SehSignalBar> CREATOR = new Parcelable.Creator<SehSignalBar>() { // from class: vendor.samsung.hardware.radio.network.SehSignalBar.1
        @Override // android.os.Parcelable.Creator
        public SehSignalBar createFromParcel(Parcel parcel) {
            SehSignalBar sehSignalBar = new SehSignalBar();
            sehSignalBar.readFromParcel(parcel);
            return sehSignalBar;
        }

        @Override // android.os.Parcelable.Creator
        public SehSignalBar[] newArray(int i) {
            return new SehSignalBar[i];
        }
    };
    public int cdmaLevel;
    public int evdoLevel;
    public int gsmLevel;
    public int lteLevel;
    public int nrLevel;
    public int tdscdmaLevel;
    public int wcdmaLevel;

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
        parcel.writeInt(this.cdmaLevel);
        parcel.writeInt(this.evdoLevel);
        parcel.writeInt(this.gsmLevel);
        parcel.writeInt(this.wcdmaLevel);
        parcel.writeInt(this.tdscdmaLevel);
        parcel.writeInt(this.lteLevel);
        parcel.writeInt(this.nrLevel);
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
                this.cdmaLevel = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.evdoLevel = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.gsmLevel = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.wcdmaLevel = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tdscdmaLevel = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.lteLevel = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.nrLevel = parcel.readInt();
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
        stringJoiner.add("cdmaLevel: " + SehSignalLevel$$.toString(this.cdmaLevel));
        stringJoiner.add("evdoLevel: " + SehSignalLevel$$.toString(this.evdoLevel));
        stringJoiner.add("gsmLevel: " + SehSignalLevel$$.toString(this.gsmLevel));
        stringJoiner.add("wcdmaLevel: " + SehSignalLevel$$.toString(this.wcdmaLevel));
        stringJoiner.add("tdscdmaLevel: " + SehSignalLevel$$.toString(this.tdscdmaLevel));
        stringJoiner.add("lteLevel: " + SehSignalLevel$$.toString(this.lteLevel));
        stringJoiner.add("nrLevel: " + SehSignalLevel$$.toString(this.nrLevel));
        return "SehSignalBar" + stringJoiner.toString();
    }
}
