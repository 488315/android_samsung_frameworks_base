package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MediaResourceParcel implements Parcelable {
    public static final Parcelable.Creator<MediaResourceParcel> CREATOR = new Parcelable.Creator<MediaResourceParcel>() { // from class: android.media.MediaResourceParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceParcel createFromParcel(Parcel parcel) {
            MediaResourceParcel mediaResourceParcel = new MediaResourceParcel();
            mediaResourceParcel.readFromParcel(parcel);
            return mediaResourceParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceParcel[] newArray(int i) {
            return new MediaResourceParcel[i];
        }
    };
    public byte[] id;
    public int type = 0;
    public int subType = 0;
    public long value = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeInt(this.subType);
        parcel.writeByteArray(this.id);
        parcel.writeLong(this.value);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.subType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.id = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.value = parcel.readLong();
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
