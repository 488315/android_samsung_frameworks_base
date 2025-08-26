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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subType);
        parcel.writeTypedObject(this.v, i);
        parcel.writeTypedObject(this.a, i);
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
                this.subType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.v = (VideoInfoParcel) parcel.readTypedObject(VideoInfoParcel.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.a = (AudioInfoParcel) parcel.readTypedObject(AudioInfoParcel.CREATOR);
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
