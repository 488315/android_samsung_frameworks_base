package android.media.tv.tunerresourcemanager;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class TunerCiCamRequest implements Parcelable {
    public static final Parcelable.Creator<TunerCiCamRequest> CREATOR = new Parcelable.Creator<TunerCiCamRequest>() { // from class: android.media.tv.tunerresourcemanager.TunerCiCamRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerCiCamRequest createFromParcel(Parcel parcel) {
            TunerCiCamRequest tunerCiCamRequest = new TunerCiCamRequest();
            tunerCiCamRequest.readFromParcel(parcel);
            return tunerCiCamRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerCiCamRequest[] newArray(int i) {
            return new TunerCiCamRequest[i];
        }
    };
    public int clientId = 0;
    public int ciCamId = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.clientId);
        parcel.writeInt(this.ciCamId);
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
                    this.ciCamId = parcel.readInt();
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
