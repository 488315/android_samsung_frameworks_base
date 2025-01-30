package android.net;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class TrafficTimeStatsParcel implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator() { // from class: android.net.TrafficTimeStatsParcel.1
        @Override // android.os.Parcelable.Creator
        public TrafficTimeStatsParcel createFromParcel(Parcel parcel) {
          TrafficTimeStatsParcel trafficTimeStatsParcel = new TrafficTimeStatsParcel();
          trafficTimeStatsParcel.readFromParcel(parcel);
          return trafficTimeStatsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public TrafficTimeStatsParcel[] newArray(int i) {
          return new TrafficTimeStatsParcel[i];
        }
      };
  public long accumulatedWakeTime = 0;
  public long maxTxInterPacketTime = 0;
  public long minTxInterPacketTime = 0;
  public long maxRxInterPacketTime2 = 0;
  public long maxRxInterPacketTime = 0;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeLong(this.accumulatedWakeTime);
    parcel.writeLong(this.maxTxInterPacketTime);
    parcel.writeLong(this.minTxInterPacketTime);
    parcel.writeLong(this.maxRxInterPacketTime2);
    parcel.writeLong(this.maxRxInterPacketTime);
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
        this.accumulatedWakeTime = parcel.readLong();
        if (parcel.dataPosition() - dataPosition < readInt) {
          this.maxTxInterPacketTime = parcel.readLong();
          if (parcel.dataPosition() - dataPosition < readInt) {
            this.minTxInterPacketTime = parcel.readLong();
            if (parcel.dataPosition() - dataPosition < readInt) {
              this.maxRxInterPacketTime2 = parcel.readLong();
              if (parcel.dataPosition() - dataPosition < readInt) {
                this.maxRxInterPacketTime = parcel.readLong();
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
