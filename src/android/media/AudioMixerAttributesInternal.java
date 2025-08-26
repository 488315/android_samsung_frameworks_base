package android.media;

import android.media.audio.common.AudioConfigBase;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioMixerAttributesInternal implements Parcelable {
    public static final Parcelable.Creator<AudioMixerAttributesInternal> CREATOR = new Parcelable.Creator<AudioMixerAttributesInternal>() { // from class: android.media.AudioMixerAttributesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixerAttributesInternal createFromParcel(Parcel parcel) {
            AudioMixerAttributesInternal audioMixerAttributesInternal = new AudioMixerAttributesInternal();
            audioMixerAttributesInternal.readFromParcel(parcel);
            return audioMixerAttributesInternal;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixerAttributesInternal[] newArray(int i) {
            return new AudioMixerAttributesInternal[i];
        }
    };
    public AudioConfigBase config;
    public int mixerBehavior;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.config, i);
        parcel.writeInt(this.mixerBehavior);
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
                this.config = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mixerBehavior = parcel.readInt();
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
        return describeContents(this.config);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
