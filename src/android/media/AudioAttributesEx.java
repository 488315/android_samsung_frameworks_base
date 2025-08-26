package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioAttributesEx implements Parcelable {
    public static final Parcelable.Creator<AudioAttributesEx> CREATOR = new Parcelable.Creator<AudioAttributesEx>() { // from class: android.media.AudioAttributesEx.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioAttributesEx createFromParcel(Parcel parcel) {
            AudioAttributesEx audioAttributesEx = new AudioAttributesEx();
            audioAttributesEx.readFromParcel(parcel);
            return audioAttributesEx;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioAttributesEx[] newArray(int i) {
            return new AudioAttributesEx[i];
        }
    };
    public android.media.audio.common.AudioAttributes attributes;
    public int groupId = 0;
    public int streamType;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.attributes, i);
        parcel.writeInt(this.streamType);
        parcel.writeInt(this.groupId);
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
                this.attributes = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.streamType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.groupId = parcel.readInt();
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
        return describeContents(this.attributes);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
