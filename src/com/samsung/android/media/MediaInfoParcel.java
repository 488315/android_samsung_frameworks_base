package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class MediaInfoParcel implements Parcelable {
    public static final Parcelable.Creator<MediaInfoParcel> CREATOR = new Parcelable.Creator<MediaInfoParcel>() { // from class: com.samsung.android.media.MediaInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaInfoParcel createFromParcel(Parcel parcel) {
            MediaInfoParcel mediaInfoParcel = new MediaInfoParcel();
            mediaInfoParcel.readFromParcel(parcel);
            return mediaInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaInfoParcel[] newArray(int i) {
            return new MediaInfoParcel[i];
        }
    };
    public AudioInfoParcel a;
    public int subType;
    public VideoInfoParcel v;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subType);
        parcel.writeTypedObject(this.v, i);
        parcel.writeTypedObject(this.a, i);
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
                this.subType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.v = (VideoInfoParcel) parcel.readTypedObject(VideoInfoParcel.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.a = (AudioInfoParcel) parcel.readTypedObject(AudioInfoParcel.CREATOR);
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
        return describeContents(this.a) | describeContents(this.v);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
