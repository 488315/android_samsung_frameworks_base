package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class MediaResourceNotifyInfoParcel implements Parcelable {
    public static final Parcelable.Creator<MediaResourceNotifyInfoParcel> CREATOR = new Parcelable.Creator<MediaResourceNotifyInfoParcel>() { // from class: com.samsung.android.media.MediaResourceNotifyInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceNotifyInfoParcel createFromParcel(Parcel parcel) {
            MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel = new MediaResourceNotifyInfoParcel();
            mediaResourceNotifyInfoParcel.readFromParcel(parcel);
            return mediaResourceNotifyInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaResourceNotifyInfoParcel[] newArray(int i) {
            return new MediaResourceNotifyInfoParcel[i];
        }
    };
    public MediaResourceInfoParcel mediaResourceInfo;
    public int pid = -1;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pid);
        parcel.writeTypedObject(this.mediaResourceInfo, i);
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
                this.pid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mediaResourceInfo = (MediaResourceInfoParcel) parcel.readTypedObject(MediaResourceInfoParcel.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.mediaResourceInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
