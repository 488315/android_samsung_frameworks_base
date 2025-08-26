package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterTsRecordEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterTsRecordEvent> CREATOR = new Parcelable.Creator<DemuxFilterTsRecordEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterTsRecordEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTsRecordEvent createFromParcel(Parcel parcel) {
            DemuxFilterTsRecordEvent demuxFilterTsRecordEvent = new DemuxFilterTsRecordEvent();
            demuxFilterTsRecordEvent.readFromParcel(parcel);
            return demuxFilterTsRecordEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTsRecordEvent[] newArray(int i) {
            return new DemuxFilterTsRecordEvent[i];
        }
    };
    public DemuxPid pid;
    public DemuxFilterScIndexMask scIndexMask;
    public int tsIndexMask = 0;
    public long byteNumber = 0;
    public long pts = 0;
    public int firstMbInSlice = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.pid, i);
        parcel.writeInt(this.tsIndexMask);
        parcel.writeTypedObject(this.scIndexMask, i);
        parcel.writeLong(this.byteNumber);
        parcel.writeLong(this.pts);
        parcel.writeInt(this.firstMbInSlice);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.pid = (DemuxPid) parcel.readTypedObject(DemuxPid.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.tsIndexMask = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.scIndexMask = (DemuxFilterScIndexMask) parcel.readTypedObject(DemuxFilterScIndexMask.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.byteNumber = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.pts = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.firstMbInSlice = parcel.readInt();
                                    if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.scIndexMask) | describeContents(this.pid);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
