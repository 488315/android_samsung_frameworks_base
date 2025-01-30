package android.hardware.broadcastradio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes.dex */
public class Announcement implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.hardware.broadcastradio.Announcement.1
        @Override // android.os.Parcelable.Creator
        public Announcement createFromParcel(Parcel parcel) {
          Announcement announcement = new Announcement();
          announcement.readFromParcel(parcel);
          return announcement;
        }

        @Override // android.os.Parcelable.Creator
        public Announcement[] newArray(int i) {
          return new Announcement[i];
        }
      };
  public ProgramSelector selector;
  public byte type = 0;
  public VendorKeyValue[] vendorInfo;

  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeTypedObject(this.selector, i);
    parcel.writeByte(this.type);
    parcel.writeTypedArray(this.vendorInfo, i);
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
        this.selector = (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.type = parcel.readByte();
          if (parcel.dataPosition() - dataPosition < readInt) {
            this.vendorInfo = (VendorKeyValue[]) parcel.createTypedArray(VendorKeyValue.CREATOR);
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
    stringJoiner.add("selector: " + Objects.toString(this.selector));
    stringJoiner.add("type: " + AnnouncementType$$.toString(this.type));
    stringJoiner.add("vendorInfo: " + Arrays.toString(this.vendorInfo));
    return "android.hardware.broadcastradio.Announcement" + stringJoiner.toString();
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !(obj instanceof Announcement)) {
      return false;
    }
    Announcement announcement = (Announcement) obj;
    return Objects.deepEquals(this.selector, announcement.selector)
        && Objects.deepEquals(Byte.valueOf(this.type), Byte.valueOf(announcement.type))
        && Objects.deepEquals(this.vendorInfo, announcement.vendorInfo);
  }

  public int hashCode() {
    return Arrays.deepHashCode(
        Arrays.asList(this.selector, Byte.valueOf(this.type), this.vendorInfo).toArray());
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return describeContents(this.vendorInfo) | describeContents(this.selector) | 0;
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
