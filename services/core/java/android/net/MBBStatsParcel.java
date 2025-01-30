package android.net;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class MBBStatsParcel implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.net.MBBStatsParcel.1
        @Override // android.os.Parcelable.Creator
        public MBBStatsParcel createFromParcel(Parcel parcel) {
          MBBStatsParcel mBBStatsParcel = new MBBStatsParcel();
          mBBStatsParcel.readFromParcel(parcel);
          return mBBStatsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public MBBStatsParcel[] newArray(int i) {
          return new MBBStatsParcel[i];
        }
      };
  public String macAddr;
  public long totalBytes = 0;
  public long totalIngressBytes = 0;
  public long totalEgressBytes = 0;
  public int rtPackets = 0;
  public long rtBytes = 0;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeString(this.macAddr);
    parcel.writeLong(this.totalBytes);
    parcel.writeLong(this.totalIngressBytes);
    parcel.writeLong(this.totalEgressBytes);
    parcel.writeInt(this.rtPackets);
    parcel.writeLong(this.rtBytes);
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
        this.macAddr = parcel.readString();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.totalBytes = parcel.readLong();
          if (parcel.dataPosition() - dataPosition < readInt) {
            this.totalIngressBytes = parcel.readLong();
            if (parcel.dataPosition() - dataPosition < readInt) {
              this.totalEgressBytes = parcel.readLong();
              if (parcel.dataPosition() - dataPosition < readInt) {
                this.rtPackets = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                  this.rtBytes = parcel.readLong();
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
}
