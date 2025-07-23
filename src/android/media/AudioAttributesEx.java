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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.attributes, i);
        parcel.writeInt(this.streamType);
        parcel.writeInt(this.groupId);
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
                this.attributes = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.streamType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.groupId = parcel.readInt();
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
        return describeContents(this.attributes);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
