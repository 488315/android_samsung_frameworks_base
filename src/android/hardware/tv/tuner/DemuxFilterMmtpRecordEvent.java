package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterMmtpRecordEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterMmtpRecordEvent> CREATOR = new Parcelable.Creator<DemuxFilterMmtpRecordEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMmtpRecordEvent createFromParcel(Parcel parcel) {
            DemuxFilterMmtpRecordEvent demuxFilterMmtpRecordEvent = new DemuxFilterMmtpRecordEvent();
            demuxFilterMmtpRecordEvent.readFromParcel(parcel);
            return demuxFilterMmtpRecordEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMmtpRecordEvent[] newArray(int i) {
            return new DemuxFilterMmtpRecordEvent[i];
        }
    };
    public int scHevcIndexMask = 0;
    public long byteNumber = 0;
    public long pts = 0;
    public int mpuSequenceNumber = 0;
    public int firstMbInSlice = 0;
    public int tsIndexMask = 0;

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
        parcel.writeInt(this.scHevcIndexMask);
        parcel.writeLong(this.byteNumber);
        parcel.writeLong(this.pts);
        parcel.writeInt(this.mpuSequenceNumber);
        parcel.writeInt(this.firstMbInSlice);
        parcel.writeInt(this.tsIndexMask);
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
                this.scHevcIndexMask = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.byteNumber = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.pts = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mpuSequenceNumber = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.firstMbInSlice = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.tsIndexMask = parcel.readInt();
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
