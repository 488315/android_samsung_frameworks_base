package android.security.securekeygeneration;

import android.hardware.security.keymint.Certificate;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SecureKeyInfo implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.security.securekeygeneration.SecureKeyInfo.1
        @Override // android.os.Parcelable.Creator
        public SecureKeyInfo createFromParcel(Parcel parcel) {
          SecureKeyInfo secureKeyInfo = new SecureKeyInfo();
          secureKeyInfo.readFromParcel(parcel);
          return secureKeyInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SecureKeyInfo[] newArray(int i) {
          return new SecureKeyInfo[i];
        }
      };
  public Certificate[] attestedCertificates;
  public byte[] blob;

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeByteArray(this.blob);
    parcel.writeTypedArray(this.attestedCertificates, i);
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
        this.blob = parcel.createByteArray();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.attestedCertificates = (Certificate[]) parcel.createTypedArray(Certificate.CREATOR);
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
    return describeContents(this.attestedCertificates) | 0;
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
