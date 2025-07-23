package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterDownloadEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterDownloadEvent> CREATOR = new Parcelable.Creator<DemuxFilterDownloadEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterDownloadEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterDownloadEvent createFromParcel(Parcel parcel) {
            DemuxFilterDownloadEvent demuxFilterDownloadEvent = new DemuxFilterDownloadEvent();
            demuxFilterDownloadEvent.readFromParcel(parcel);
            return demuxFilterDownloadEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterDownloadEvent[] newArray(int i) {
            return new DemuxFilterDownloadEvent[i];
        }
    };
    public int itemId = 0;
    public int downloadId = 0;
    public int mpuSequenceNumber = 0;
    public int itemFragmentIndex = 0;
    public int lastItemFragmentIndex = 0;
    public int dataLength = 0;

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
        parcel.writeInt(this.itemId);
        parcel.writeInt(this.downloadId);
        parcel.writeInt(this.mpuSequenceNumber);
        parcel.writeInt(this.itemFragmentIndex);
        parcel.writeInt(this.lastItemFragmentIndex);
        parcel.writeInt(this.dataLength);
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
                this.itemId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.downloadId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mpuSequenceNumber = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.itemFragmentIndex = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.lastItemFragmentIndex = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.dataLength = parcel.readInt();
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
