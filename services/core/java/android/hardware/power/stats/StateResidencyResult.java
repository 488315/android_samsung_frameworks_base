package android.hardware.power.stats;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class StateResidencyResult implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.hardware.power.stats.StateResidencyResult.1
        @Override // android.os.Parcelable.Creator
        public StateResidencyResult createFromParcel(Parcel parcel) {
          StateResidencyResult stateResidencyResult = new StateResidencyResult();
          stateResidencyResult.readFromParcel(parcel);
          return stateResidencyResult;
        }

        @Override // android.os.Parcelable.Creator
        public StateResidencyResult[] newArray(int i) {
          return new StateResidencyResult[i];
        }
      };

  /* renamed from: id */
  public int f11id = 0;
  public StateResidency[] stateResidencyData;

  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeInt(this.f11id);
    parcel.writeTypedArray(this.stateResidencyData, i);
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
        this.f11id = parcel.readInt();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.stateResidencyData =
              (StateResidency[]) parcel.createTypedArray(StateResidency.CREATOR);
          if (dataPosition > Integer.MAX_VALUE - readInt) {
            throw new BadParcelableException("Overflow in the size of parcelable");
          }
          parcel.setDataPosition(dataPosition + readInt);
          return;
        }
        if (dataPosition > Integer.MAX_VALUE - readInt) {
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

  @Override // android.os.Parcelable
  public int describeContents() {
    return describeContents(this.stateResidencyData) | 0;
  }

  public final int describeContents(Object obj) {
    if (obj == null) {
      return 0;
    }
    if (obj instanceof Object[]) {
      int i = 0;
      for (Object obj2 : (Object[]) obj) {
        i |= describeContents(obj2);
      }
      return i;
    }
    if (obj instanceof Parcelable) {
      return ((Parcelable) obj).describeContents();
    }
    return 0;
  }
}
