package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioVolumeGroup implements Parcelable {
    public static final Parcelable.Creator<AudioVolumeGroup> CREATOR = new Parcelable.Creator<AudioVolumeGroup>() { // from class: android.media.AudioVolumeGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroup createFromParcel(Parcel parcel) {
            AudioVolumeGroup audioVolumeGroup = new AudioVolumeGroup();
            audioVolumeGroup.readFromParcel(parcel);
            return audioVolumeGroup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroup[] newArray(int i) {
            return new AudioVolumeGroup[i];
        }
    };
    public android.media.audio.common.AudioAttributes[] audioAttributes;
    public int groupId = 0;
    public String name;
    public int[] streams;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.groupId);
        parcel.writeString(this.name);
        parcel.writeTypedArray(this.audioAttributes, i);
        parcel.writeIntArray(this.streams);
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
                this.groupId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.audioAttributes = (android.media.audio.common.AudioAttributes[]) parcel.createTypedArray(android.media.audio.common.AudioAttributes.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.streams = parcel.createIntArray();
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
        return describeContents(this.audioAttributes);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
