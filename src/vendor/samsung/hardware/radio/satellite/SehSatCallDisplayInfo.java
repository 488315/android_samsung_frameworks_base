package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatCallDisplayInfo implements Parcelable {
    public static final Parcelable.Creator<SehSatCallDisplayInfo> CREATOR = new Parcelable.Creator<SehSatCallDisplayInfo>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatCallDisplayInfo.1
        @Override // android.os.Parcelable.Creator
        public SehSatCallDisplayInfo createFromParcel(Parcel parcel) {
            SehSatCallDisplayInfo sehSatCallDisplayInfo = new SehSatCallDisplayInfo();
            sehSatCallDisplayInfo.readFromParcel(parcel);
            return sehSatCallDisplayInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatCallDisplayInfo[] newArray(int i) {
            return new SehSatCallDisplayInfo[i];
        }
    };
    public String alpha;
    public String number;
    public String subAddr;
    public int type = 0;
    public int saType = 0;
    public int cli = 0;

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
        parcel.writeString(this.number);
        parcel.writeInt(this.type);
        parcel.writeString(this.subAddr);
        parcel.writeInt(this.saType);
        parcel.writeString(this.alpha);
        parcel.writeInt(this.cli);
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
                this.number = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.subAddr = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.saType = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.alpha = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.cli = parcel.readInt();
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
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("subAddr: " + Objects.toString(this.subAddr));
        stringJoiner.add("saType: " + this.saType);
        stringJoiner.add("alpha: " + Objects.toString(this.alpha));
        stringJoiner.add("cli: " + this.cli);
        return "SehSatCallDisplayInfo" + stringJoiner.toString();
    }
}
