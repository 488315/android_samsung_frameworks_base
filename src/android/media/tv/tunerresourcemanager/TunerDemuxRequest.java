package android.media.tv.tunerresourcemanager;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class TunerDemuxRequest implements Parcelable {
    public static final Parcelable.Creator<TunerDemuxRequest> CREATOR = new Parcelable.Creator<TunerDemuxRequest>() { // from class: android.media.tv.tunerresourcemanager.TunerDemuxRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerDemuxRequest createFromParcel(Parcel parcel) {
            TunerDemuxRequest tunerDemuxRequest = new TunerDemuxRequest();
            tunerDemuxRequest.readFromParcel(parcel);
            return tunerDemuxRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerDemuxRequest[] newArray(int i) {
            return new TunerDemuxRequest[i];
        }
    };
    public int clientId = 0;
    public int desiredFilterTypes = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.clientId);
        parcel.writeInt(this.desiredFilterTypes);
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
                this.clientId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.desiredFilterTypes = parcel.readInt();
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
}
