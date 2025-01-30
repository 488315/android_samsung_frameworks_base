package android.telephony.satellite;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AntennaPosition implements Parcelable {
  public static final Parcelable.Creator<AntennaPosition> CREATOR =
      new Parcelable.Creator<
          AntennaPosition>() { // from class: android.telephony.satellite.AntennaPosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaPosition createFromParcel(Parcel in) {
          return new AntennaPosition(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaPosition[] newArray(int size) {
          return new AntennaPosition[size];
        }
      };
  private AntennaDirection mAntennaDirection;
  private int mSuggestedHoldPosition;

  public AntennaPosition(AntennaDirection antennaDirection, int suggestedHoldPosition) {
    this.mAntennaDirection = antennaDirection;
    this.mSuggestedHoldPosition = suggestedHoldPosition;
  }

  private AntennaPosition(Parcel in) {
    readFromParcel(in);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeParcelable(this.mAntennaDirection, flags);
    out.writeInt(this.mSuggestedHoldPosition);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AntennaPosition that = (AntennaPosition) o;
    if (Objects.equals(this.mAntennaDirection, that.mAntennaDirection)
        && this.mSuggestedHoldPosition == that.mSuggestedHoldPosition) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(this.mAntennaDirection, Integer.valueOf(this.mSuggestedHoldPosition));
  }

  public String toString() {
    return "antennaDirection:"
        + this.mAntennaDirection
        + ",suggestedHoldPosition:"
        + this.mSuggestedHoldPosition;
  }

  public AntennaDirection getAntennaDirection() {
    return this.mAntennaDirection;
  }

  public int getSuggestedHoldPosition() {
    return this.mSuggestedHoldPosition;
  }

  private void readFromParcel(Parcel in) {
    this.mAntennaDirection =
        (AntennaDirection)
            in.readParcelable(AntennaDirection.class.getClassLoader(), AntennaDirection.class);
    this.mSuggestedHoldPosition = in.readInt();
  }
}
