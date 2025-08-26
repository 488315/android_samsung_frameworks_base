package android.media.tv.tunerresourcemanager;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class TunerFrontendInfo implements Parcelable {
    public static final Parcelable.Creator<TunerFrontendInfo> CREATOR = new Parcelable.Creator<TunerFrontendInfo>() { // from class: android.media.tv.tunerresourcemanager.TunerFrontendInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerFrontendInfo createFromParcel(Parcel parcel) {
            TunerFrontendInfo tunerFrontendInfo = new TunerFrontendInfo();
            tunerFrontendInfo.readFromParcel(parcel);
            return tunerFrontendInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunerFrontendInfo[] newArray(int i) {
            return new TunerFrontendInfo[i];
        }
    };
    public long handle = 0;
    public int type = 0;
    public int exclusiveGroupId = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.handle);
        parcel.writeInt(this.type);
        parcel.writeInt(this.exclusiveGroupId);
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
                this.handle = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.exclusiveGroupId = parcel.readInt();
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
