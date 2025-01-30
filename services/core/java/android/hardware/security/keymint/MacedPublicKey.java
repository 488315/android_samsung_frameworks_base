package android.hardware.security.keymint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class MacedPublicKey implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.hardware.security.keymint.MacedPublicKey.1
        @Override // android.os.Parcelable.Creator
        public MacedPublicKey createFromParcel(Parcel parcel) {
          MacedPublicKey macedPublicKey = new MacedPublicKey();
          macedPublicKey.readFromParcel(parcel);
          return macedPublicKey;
        }

        @Override // android.os.Parcelable.Creator
        public MacedPublicKey[] newArray(int i) {
          return new MacedPublicKey[i];
        }
      };
  public byte[] macedKey;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeByteArray(this.macedKey);
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
      if (parcel.dataPosition() - dataPosition >= readInt) {
        if (dataPosition > Integer.MAX_VALUE - readInt) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
      } else {
        this.macedKey = parcel.createByteArray();
        if (dataPosition > Integer.MAX_VALUE - readInt) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
      }
    } catch (Throwable th) {
      if (dataPosition > Integer.MAX_VALUE - readInt) {
        throw new BadParcelableException("Overflow in the size of parcelable");
      }
      parcel.setDataPosition(dataPosition + readInt);
      throw th;
    }
  }
}
