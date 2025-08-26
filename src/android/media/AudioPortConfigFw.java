package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioPortConfigFw implements Parcelable {
    public static final Parcelable.Creator<AudioPortConfigFw> CREATOR = new Parcelable.Creator<AudioPortConfigFw>() { // from class: android.media.AudioPortConfigFw.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortConfigFw createFromParcel(Parcel parcel) {
            AudioPortConfigFw audioPortConfigFw = new AudioPortConfigFw();
            audioPortConfigFw.readFromParcel(parcel);
            return audioPortConfigFw;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortConfigFw[] newArray(int i) {
            return new AudioPortConfigFw[i];
        }
    };
    public android.media.audio.common.AudioPortConfig hal;
    public AudioPortConfigSys sys;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.hal, i);
        parcel.writeTypedObject(this.sys, i);
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
                this.hal = (android.media.audio.common.AudioPortConfig) parcel.readTypedObject(android.media.audio.common.AudioPortConfig.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sys = (AudioPortConfigSys) parcel.readTypedObject(AudioPortConfigSys.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sys) | describeContents(this.hal);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
