package android.gsi;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class MappedImage implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.gsi.MappedImage.1
        @Override // android.os.Parcelable.Creator
        public MappedImage createFromParcel(Parcel parcel) {
          MappedImage mappedImage = new MappedImage();
          mappedImage.readFromParcel(parcel);
          return mappedImage;
        }

        @Override // android.os.Parcelable.Creator
        public MappedImage[] newArray(int i) {
          return new MappedImage[i];
        }
      };
  public String path;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeString(this.path);
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
        this.path = parcel.readString();
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
