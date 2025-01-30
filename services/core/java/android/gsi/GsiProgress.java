package android.gsi;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class GsiProgress implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.gsi.GsiProgress.1
        @Override // android.os.Parcelable.Creator
        public GsiProgress createFromParcel(Parcel parcel) {
            GsiProgress gsiProgress = new GsiProgress();
            gsiProgress.readFromParcel(parcel);
            return gsiProgress;
        }

        @Override // android.os.Parcelable.Creator
        public GsiProgress[] newArray(int i) {
            return new GsiProgress[i];
        }
    };
    public String step;
    public int status = 0;
    public long bytes_processed = 0;
    public long total_bytes = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.step);
        parcel.writeInt(this.status);
        parcel.writeLong(this.bytes_processed);
        parcel.writeLong(this.total_bytes);
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
                this.step = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.status = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bytes_processed = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.total_bytes = parcel.readLong();
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
}
