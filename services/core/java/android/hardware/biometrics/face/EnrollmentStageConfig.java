package android.hardware.biometrics.face;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class EnrollmentStageConfig implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable
          .Creator() { // from class: android.hardware.biometrics.face.EnrollmentStageConfig.1
        @Override // android.os.Parcelable.Creator
        public EnrollmentStageConfig createFromParcel(Parcel parcel) {
          EnrollmentStageConfig enrollmentStageConfig = new EnrollmentStageConfig();
          enrollmentStageConfig.readFromParcel(parcel);
          return enrollmentStageConfig;
        }

        @Override // android.os.Parcelable.Creator
        public EnrollmentStageConfig[] newArray(int i) {
          return new EnrollmentStageConfig[i];
        }
      };
  public List cells;
  public byte stage = 0;

  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeByte(this.stage);
    parcel.writeTypedList(this.cells, i);
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
        this.stage = parcel.readByte();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.cells = parcel.createTypedArrayList(Cell.CREATOR);
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
    return describeContents(this.cells) | 0;
  }

  public final int describeContents(Object obj) {
    int i = 0;
    if (obj == null) {
      return 0;
    }
    if (obj instanceof Collection) {
      Iterator it = ((Collection) obj).iterator();
      while (it.hasNext()) {
        i |= describeContents(it.next());
      }
      return i;
    }
    if (obj instanceof Parcelable) {
      return ((Parcelable) obj).describeContents();
    }
    return 0;
  }
}
