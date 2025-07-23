package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class TrackSecondaryOutputInfo implements Parcelable {
    public static final Parcelable.Creator<TrackSecondaryOutputInfo> CREATOR = new Parcelable.Creator<TrackSecondaryOutputInfo>() { // from class: android.media.TrackSecondaryOutputInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackSecondaryOutputInfo createFromParcel(Parcel parcel) {
            TrackSecondaryOutputInfo trackSecondaryOutputInfo = new TrackSecondaryOutputInfo();
            trackSecondaryOutputInfo.readFromParcel(parcel);
            return trackSecondaryOutputInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackSecondaryOutputInfo[] newArray(int i) {
            return new TrackSecondaryOutputInfo[i];
        }
    };
    public int portId = 0;
    public int[] secondaryOutputIds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.portId);
        parcel.writeIntArray(this.secondaryOutputIds);
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
                this.portId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.secondaryOutputIds = parcel.createIntArray();
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
