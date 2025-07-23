package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class RecordSettings implements Parcelable {
    public static final Parcelable.Creator<RecordSettings> CREATOR = new Parcelable.Creator<RecordSettings>() { // from class: android.hardware.tv.tuner.RecordSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordSettings createFromParcel(Parcel parcel) {
            RecordSettings recordSettings = new RecordSettings();
            recordSettings.readFromParcel(parcel);
            return recordSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordSettings[] newArray(int i) {
            return new RecordSettings[i];
        }
    };
    public int statusMask = 0;
    public long lowThreshold = 0;
    public long highThreshold = 0;
    public int dataFormat = 4;
    public long packetSize = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.statusMask);
        parcel.writeLong(this.lowThreshold);
        parcel.writeLong(this.highThreshold);
        parcel.writeInt(this.dataFormat);
        parcel.writeLong(this.packetSize);
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
                this.statusMask = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.lowThreshold = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.highThreshold = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dataFormat = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.packetSize = parcel.readLong();
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
