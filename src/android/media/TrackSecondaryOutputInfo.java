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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.portId);
        parcel.writeIntArray(this.secondaryOutputIds);
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
                this.portId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.secondaryOutputIds = parcel.createIntArray();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
