package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class AntennaPosition implements Parcelable {
    public static final Parcelable.Creator<AntennaPosition> CREATOR = new Parcelable.Creator<AntennaPosition>() { // from class: android.telephony.satellite.AntennaPosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaPosition createFromParcel(Parcel parcel) {
            return new AntennaPosition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaPosition[] newArray(int i) {
            return new AntennaPosition[i];
        }
    };
    private AntennaDirection mAntennaDirection;
    private int mSuggestedHoldPosition;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AntennaPosition(AntennaDirection antennaDirection, int i) {
        this.mAntennaDirection = antennaDirection;
        this.mSuggestedHoldPosition = i;
    }

    private AntennaPosition(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mAntennaDirection, i);
        parcel.writeInt(this.mSuggestedHoldPosition);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AntennaPosition antennaPosition = (AntennaPosition) obj;
            if (Objects.equals(this.mAntennaDirection, antennaPosition.mAntennaDirection) && this.mSuggestedHoldPosition == antennaPosition.mSuggestedHoldPosition) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mAntennaDirection, Integer.valueOf(this.mSuggestedHoldPosition));
    }

    public String toString() {
        return "antennaDirection:" + this.mAntennaDirection + ",suggestedHoldPosition:" + this.mSuggestedHoldPosition;
    }

    public AntennaDirection getAntennaDirection() {
        return this.mAntennaDirection;
    }

    public int getSuggestedHoldPosition() {
        return this.mSuggestedHoldPosition;
    }

    private void readFromParcel(Parcel parcel) {
        this.mAntennaDirection = (AntennaDirection) parcel.readParcelable(AntennaDirection.class.getClassLoader(), AntennaDirection.class);
        this.mSuggestedHoldPosition = parcel.readInt();
    }
}
