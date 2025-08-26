package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatCallEndReason implements Parcelable {
    public static final Parcelable.Creator<SehSatCallEndReason> CREATOR = new Parcelable.Creator<SehSatCallEndReason>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatCallEndReason.1
        @Override // android.os.Parcelable.Creator
        public SehSatCallEndReason createFromParcel(Parcel parcel) {
            SehSatCallEndReason sehSatCallEndReason = new SehSatCallEndReason();
            sehSatCallEndReason.readFromParcel(parcel);
            return sehSatCallEndReason;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatCallEndReason[] newArray(int i) {
            return new SehSatCallEndReason[i];
        }
    };
    public int index = 0;
    public int endStatus = 0;
    public int ccCause = 0;
    public int callType = 0;

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
        parcel.writeInt(this.endStatus);
        parcel.writeInt(this.ccCause);
        parcel.writeInt(this.callType);
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
                    this.endStatus = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.ccCause = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.callType = parcel.readInt();
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
        stringJoiner.add("endStatus: " + this.endStatus);
        stringJoiner.add("ccCause: " + this.ccCause);
        stringJoiner.add("callType: " + this.callType);
        return "SehSatCallEndReason" + stringJoiner.toString();
    }
}
