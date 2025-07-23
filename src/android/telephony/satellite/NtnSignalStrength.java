package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class NtnSignalStrength implements Parcelable {
    public static final Parcelable.Creator<NtnSignalStrength> CREATOR = new Parcelable.Creator<NtnSignalStrength>() { // from class: android.telephony.satellite.NtnSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NtnSignalStrength createFromParcel(Parcel parcel) {
            return new NtnSignalStrength(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NtnSignalStrength[] newArray(int i) {
            return new NtnSignalStrength[i];
        }
    };
    public static final int NTN_SIGNAL_STRENGTH_GOOD = 3;
    public static final int NTN_SIGNAL_STRENGTH_GREAT = 4;
    public static final int NTN_SIGNAL_STRENGTH_MODERATE = 2;
    public static final int NTN_SIGNAL_STRENGTH_NONE = 0;
    public static final int NTN_SIGNAL_STRENGTH_POOR = 1;
    private int mLevel;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NtnSignalStrengthLevel {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NtnSignalStrength(int i) {
        this.mLevel = i;
    }

    public NtnSignalStrength(NtnSignalStrength ntnSignalStrength) {
        this.mLevel = ntnSignalStrength == null ? 0 : ntnSignalStrength.getLevel();
    }

    private NtnSignalStrength(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLevel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mLevel = parcel.readInt();
    }

    public int hashCode() {
        return this.mLevel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mLevel == ((NtnSignalStrength) obj).mLevel;
    }

    public String toString() {
        return "NtnSignalStrength{mLevel=" + this.mLevel + '}';
    }
}
