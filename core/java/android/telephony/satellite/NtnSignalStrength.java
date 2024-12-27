package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class NtnSignalStrength implements Parcelable {
    public static final Parcelable.Creator<NtnSignalStrength> CREATOR =
            new Parcelable.Creator<
                    NtnSignalStrength>() { // from class:
                                           // android.telephony.satellite.NtnSignalStrength.1
                @Override // android.os.Parcelable.Creator
                public NtnSignalStrength createFromParcel(Parcel in) {
                    return new NtnSignalStrength(in);
                }

                @Override // android.os.Parcelable.Creator
                public NtnSignalStrength[] newArray(int size) {
                    return new NtnSignalStrength[size];
                }
            };
    public static final int NTN_SIGNAL_STRENGTH_GOOD = 3;
    public static final int NTN_SIGNAL_STRENGTH_GREAT = 4;
    public static final int NTN_SIGNAL_STRENGTH_MODERATE = 2;
    public static final int NTN_SIGNAL_STRENGTH_NONE = 0;
    public static final int NTN_SIGNAL_STRENGTH_POOR = 1;
    private int mLevel;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NtnSignalStrengthLevel {}

    public NtnSignalStrength(int level) {
        this.mLevel = level;
    }

    public NtnSignalStrength(NtnSignalStrength source) {
        this.mLevel = source == null ? 0 : source.getLevel();
    }

    private NtnSignalStrength(Parcel in) {
        readFromParcel(in);
    }

    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mLevel);
    }

    private void readFromParcel(Parcel in) {
        this.mLevel = in.readInt();
    }

    public int hashCode() {
        return this.mLevel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NtnSignalStrength that = (NtnSignalStrength) obj;
        if (this.mLevel == that.mLevel) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "NtnSignalStrength{mLevel=" + this.mLevel + '}';
    }
}
