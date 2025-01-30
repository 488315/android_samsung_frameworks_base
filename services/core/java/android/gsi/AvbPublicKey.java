package android.gsi;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AvbPublicKey implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.gsi.AvbPublicKey.1
        @Override // android.os.Parcelable.Creator
        public AvbPublicKey createFromParcel(Parcel parcel) {
          AvbPublicKey avbPublicKey = new AvbPublicKey();
          avbPublicKey.readFromParcel(parcel);
          return avbPublicKey;
        }

        @Override // android.os.Parcelable.Creator
        public AvbPublicKey[] newArray(int i) {
          return new AvbPublicKey[i];
        }
      };
  public byte[] bytes;
  public byte[] sha1;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeByteArray(this.bytes);
    parcel.writeByteArray(this.sha1);
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
        this.bytes = parcel.createByteArray();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.sha1 = parcel.createByteArray();
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
}
