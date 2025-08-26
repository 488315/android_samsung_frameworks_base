package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCsgInfo implements Parcelable {
    public static final Parcelable.Creator<SehCsgInfo> CREATOR = new Parcelable.Creator<SehCsgInfo>() { // from class: vendor.samsung.hardware.radio.network.SehCsgInfo.1
        @Override // android.os.Parcelable.Creator
        public SehCsgInfo createFromParcel(Parcel parcel) {
            SehCsgInfo sehCsgInfo = new SehCsgInfo();
            sehCsgInfo.readFromParcel(parcel);
            return sehCsgInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehCsgInfo[] newArray(int i) {
            return new SehCsgInfo[i];
        }
    };
    public String name;
    public String plmn;
    public int csgId = 0;
    public int rat = 0;
    public int category = 0;
    public int signalStrength = 0;

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
        parcel.writeInt(this.csgId);
        parcel.writeString(this.name);
        parcel.writeString(this.plmn);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.category);
        parcel.writeInt(this.signalStrength);
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
                this.csgId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.plmn = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.rat = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.category = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.signalStrength = parcel.readInt();
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
        stringJoiner.add("csgId: " + this.csgId);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("plmn: " + Objects.toString(this.plmn));
        stringJoiner.add("rat: " + this.rat);
        stringJoiner.add("category: " + this.category);
        stringJoiner.add("signalStrength: " + this.signalStrength);
        return "SehCsgInfo" + stringJoiner.toString();
    }
}
