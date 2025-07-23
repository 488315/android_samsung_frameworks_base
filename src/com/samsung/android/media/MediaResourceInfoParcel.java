package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class MediaResourceInfoParcel implements Parcelable {
    public static final Parcelable.Creator<MediaResourceInfoParcel> CREATOR = new Parcelable.Creator<MediaResourceInfoParcel>() { // from class: com.samsung.android.media.MediaResourceInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceInfoParcel createFromParcel(Parcel parcel) {
            MediaResourceInfoParcel mediaResourceInfoParcel = new MediaResourceInfoParcel();
            mediaResourceInfoParcel.readFromParcel(parcel);
            return mediaResourceInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceInfoParcel[] newArray(int i) {
            return new MediaResourceInfoParcel[i];
        }
    };
    public ResourceInfoParcel[] resourceInfos;
    public int status = 0;
    public int totalSize = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.totalSize);
        parcel.writeTypedArray(this.resourceInfos, i);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.totalSize = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.resourceInfos = (ResourceInfoParcel[]) parcel.createTypedArray(ResourceInfoParcel.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.resourceInfos);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
