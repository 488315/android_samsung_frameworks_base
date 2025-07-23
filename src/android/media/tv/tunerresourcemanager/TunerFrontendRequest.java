package android.media.tv.tunerresourcemanager;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class TunerFrontendRequest implements Parcelable {
    public static final Parcelable.Creator<TunerFrontendRequest> CREATOR = new Parcelable.Creator<TunerFrontendRequest>() { // from class: android.media.tv.tunerresourcemanager.TunerFrontendRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerFrontendRequest createFromParcel(Parcel parcel) {
            TunerFrontendRequest tunerFrontendRequest = new TunerFrontendRequest();
            tunerFrontendRequest.readFromParcel(parcel);
            return tunerFrontendRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerFrontendRequest[] newArray(int i) {
            return new TunerFrontendRequest[i];
        }
    };
    public static final int DEFAULT_DESIRED_ID = -1;
    public int clientId = 0;
    public int frontendType = 0;
    public int desiredId = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.clientId);
        parcel.writeInt(this.frontendType);
        parcel.writeInt(this.desiredId);
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
                    this.frontendType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.desiredId = parcel.readInt();
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
