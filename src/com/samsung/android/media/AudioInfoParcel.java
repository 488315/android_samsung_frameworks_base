package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class AudioInfoParcel implements Parcelable {
    public static final Parcelable.Creator<AudioInfoParcel> CREATOR = new Parcelable.Creator<AudioInfoParcel>() { // from class: com.samsung.android.media.AudioInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioInfoParcel createFromParcel(Parcel parcel) {
            AudioInfoParcel audioInfoParcel = new AudioInfoParcel();
            audioInfoParcel.readFromParcel(parcel);
            return audioInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioInfoParcel[] newArray(int i) {
            return new AudioInfoParcel[i];
        }
    };
    public int bitRate = 0;
    public int sampleRate = 0;
    public int numChannel = 0;
    public boolean isOffload = false;
    public boolean isEncoder = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.bitRate);
        parcel.writeInt(this.sampleRate);
        parcel.writeInt(this.numChannel);
        parcel.writeBoolean(this.isOffload);
        parcel.writeBoolean(this.isEncoder);
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
                this.bitRate = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sampleRate = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.numChannel = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isOffload = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.isEncoder = parcel.readBoolean();
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
