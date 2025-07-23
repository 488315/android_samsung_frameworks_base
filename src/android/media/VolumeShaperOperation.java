package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class VolumeShaperOperation implements Parcelable {
    public static final Parcelable.Creator<VolumeShaperOperation> CREATOR = new Parcelable.Creator<VolumeShaperOperation>() { // from class: android.media.VolumeShaperOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeShaperOperation createFromParcel(Parcel parcel) {
            VolumeShaperOperation volumeShaperOperation = new VolumeShaperOperation();
            volumeShaperOperation.readFromParcel(parcel);
            return volumeShaperOperation;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeShaperOperation[] newArray(int i) {
            return new VolumeShaperOperation[i];
        }
    };
    public int flags = 0;
    public int replaceId = 0;
    public float xOffset = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.replaceId);
        parcel.writeFloat(this.xOffset);
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
                this.flags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.replaceId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.xOffset = parcel.readFloat();
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
