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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.itemId);
        parcel.writeInt(this.downloadId);
        parcel.writeInt(this.mpuSequenceNumber);
        parcel.writeInt(this.itemFragmentIndex);
        parcel.writeInt(this.lastItemFragmentIndex);
        parcel.writeInt(this.dataLength);
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
                this.itemId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.downloadId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mpuSequenceNumber = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.itemFragmentIndex = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.lastItemFragmentIndex = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.dataLength = parcel.readInt();
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
}
