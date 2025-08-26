package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehPreferredNetworkInfo implements Parcelable {
    public static final Parcelable.Creator<SehPreferredNetworkInfo> CREATOR = new Parcelable.Creator<SehPreferredNetworkInfo>() { // from class: vendor.samsung.hardware.radio.network.SehPreferredNetworkInfo.1
        @Override // android.os.Parcelable.Creator
        public SehPreferredNetworkInfo createFromParcel(Parcel parcel) {
            SehPreferredNetworkInfo sehPreferredNetworkInfo = new SehPreferredNetworkInfo();
            sehPreferredNetworkInfo.readFromParcel(parcel);
            return sehPreferredNetworkInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehPreferredNetworkInfo[] newArray(int i) {
            return new SehPreferredNetworkInfo[i];
        }
    };
    public String oper;
    public String plmn;
    public int index = 0;
    public int gsmAct = 0;
    public int gsmCompactAct = 0;
    public int utranAct = 0;
    public int mode = 0;

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
        parcel.writeInt(this.index);
        parcel.writeString(this.oper);
        parcel.writeString(this.plmn);
        parcel.writeInt(this.gsmAct);
        parcel.writeInt(this.gsmCompactAct);
        parcel.writeInt(this.utranAct);
        parcel.writeInt(this.mode);
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
                this.index = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.oper = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.plmn = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.gsmAct = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gsmCompactAct = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.utranAct = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.mode = parcel.readInt();
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
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("oper: " + Objects.toString(this.oper));
        stringJoiner.add("plmn: " + Objects.toString(this.plmn));
        stringJoiner.add("gsmAct: " + this.gsmAct);
        stringJoiner.add("gsmCompactAct: " + this.gsmCompactAct);
        stringJoiner.add("utranAct: " + this.utranAct);
        stringJoiner.add("mode: " + this.mode);
        return "SehPreferredNetworkInfo" + stringJoiner.toString();
    }
}
