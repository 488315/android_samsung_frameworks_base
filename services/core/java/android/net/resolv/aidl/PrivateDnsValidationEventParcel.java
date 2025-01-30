package android.net.resolv.aidl;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes.dex */
public class PrivateDnsValidationEventParcel implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable
          .Creator() { // from class: android.net.resolv.aidl.PrivateDnsValidationEventParcel.1
        @Override // android.os.Parcelable.Creator
        public PrivateDnsValidationEventParcel createFromParcel(Parcel parcel) {
          PrivateDnsValidationEventParcel privateDnsValidationEventParcel =
              new PrivateDnsValidationEventParcel();
          privateDnsValidationEventParcel.readFromParcel(parcel);
          return privateDnsValidationEventParcel;
        }

        @Override // android.os.Parcelable.Creator
        public PrivateDnsValidationEventParcel[] newArray(int i) {
          return new PrivateDnsValidationEventParcel[i];
        }
      };
  public String hostname;
  public String ipAddress;
  public int netId = 0;
  public int validation = 0;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeInt(this.netId);
    parcel.writeString(this.ipAddress);
    parcel.writeString(this.hostname);
    parcel.writeInt(this.validation);
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
          this.ipAddress = parcel.readString();
          if (parcel.dataPosition() - dataPosition < readInt) {
            this.hostname = parcel.readString();
            if (parcel.dataPosition() - dataPosition < readInt) {
              this.validation = parcel.readInt();
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
    stringJoiner.add("ipAddress: " + Objects.toString(this.ipAddress));
    stringJoiner.add("hostname: " + Objects.toString(this.hostname));
    stringJoiner.add("validation: " + this.validation);
    return "android.net.resolv.aidl.PrivateDnsValidationEventParcel" + stringJoiner.toString();
  }
}
