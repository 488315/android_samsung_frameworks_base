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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.index);
        parcel.writeInt(this.endStatus);
        parcel.writeInt(this.ccCause);
        parcel.writeInt(this.callType);
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
                this.index = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.endStatus = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.ccCause = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.callType = parcel.readInt();
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
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("endStatus: " + this.endStatus);
        stringJoiner.add("ccCause: " + this.ccCause);
        stringJoiner.add("callType: " + this.callType);
        return "SehSatCallEndReason" + stringJoiner.toString();
    }
}
