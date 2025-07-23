package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class EarfcnRange implements Parcelable {
    public static final Parcelable.Creator<EarfcnRange> CREATOR = new Parcelable.Creator<EarfcnRange>() { // from class: android.telephony.satellite.EarfcnRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EarfcnRange createFromParcel(Parcel parcel) {
            return new EarfcnRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EarfcnRange[] newArray(int i) {
            return new EarfcnRange[i];
        }
    };
    private int mEndEarfcn;
    private int mStartEarfcn;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private EarfcnRange(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStartEarfcn);
        parcel.writeInt(this.mEndEarfcn);
    }

    private void readFromParcel(Parcel parcel) {
        this.mStartEarfcn = parcel.readInt();
        this.mEndEarfcn = parcel.readInt();
    }

    public EarfcnRange(int i, int i2) {
        this.mStartEarfcn = i;
        this.mEndEarfcn = i2;
    }

    public String toString() {
        return "startEarfcn: " + this.mStartEarfcn + ", endEarfcn: " + this.mEndEarfcn;
    }

    public int getStartEarfcn() {
        return this.mStartEarfcn;
    }

    public int getEndEarfcn() {
        return this.mEndEarfcn;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EarfcnRange) {
            EarfcnRange earfcnRange = (EarfcnRange) obj;
            if (earfcnRange.mStartEarfcn == this.mStartEarfcn && earfcnRange.mEndEarfcn == this.mEndEarfcn) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStartEarfcn), Integer.valueOf(this.mEndEarfcn));
    }
}
