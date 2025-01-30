package android.net.resolv.aidl;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes.dex */
public class Nat64PrefixEventParcel implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.net.resolv.aidl.Nat64PrefixEventParcel.1
        @Override // android.os.Parcelable.Creator
        public Nat64PrefixEventParcel createFromParcel(Parcel parcel) {
          Nat64PrefixEventParcel nat64PrefixEventParcel = new Nat64PrefixEventParcel();
          nat64PrefixEventParcel.readFromParcel(parcel);
          return nat64PrefixEventParcel;
        }

        @Override // android.os.Parcelable.Creator
        public Nat64PrefixEventParcel[] newArray(int i) {
          return new Nat64PrefixEventParcel[i];
        }
      };
  public String prefixAddress;
  public int netId = 0;
  public int prefixOperation = 0;
  public int prefixLength = 0;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeInt(this.netId);
    parcel.writeInt(this.prefixOperation);
    parcel.writeString(this.prefixAddress);
    parcel.writeInt(this.prefixLength);
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
        this.netId = parcel.readInt();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.prefixOperation = parcel.readInt();
          if (parcel.dataPosition() - dataPosition < readInt) {
            this.prefixAddress = parcel.readString();
            if (parcel.dataPosition() - dataPosition < readInt) {
              this.prefixLength = parcel.readInt();
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
        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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

  public String toString() {
    StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
    stringJoiner.add("netId: " + this.netId);
    stringJoiner.add("prefixOperation: " + this.prefixOperation);
    stringJoiner.add("prefixAddress: " + Objects.toString(this.prefixAddress));
    stringJoiner.add("prefixLength: " + this.prefixLength);
    return "android.net.resolv.aidl.Nat64PrefixEventParcel" + stringJoiner.toString();
  }
}
