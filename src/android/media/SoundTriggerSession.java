package android.media;

import android.media.audio.common.AudioDeviceDescription;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SoundTriggerSession implements Parcelable {
    public static final Parcelable.Creator<SoundTriggerSession> CREATOR = new Parcelable.Creator<SoundTriggerSession>() { // from class: android.media.SoundTriggerSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerSession createFromParcel(Parcel parcel) {
            SoundTriggerSession soundTriggerSession = new SoundTriggerSession();
            soundTriggerSession.readFromParcel(parcel);
            return soundTriggerSession;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerSession[] newArray(int i) {
            return new SoundTriggerSession[i];
        }
    };
    public AudioDeviceDescription device;
    public int session = 0;
    public int ioHandle = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.session);
        parcel.writeInt(this.ioHandle);
        parcel.writeTypedObject(this.device, i);
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
                this.session = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.ioHandle = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.device = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
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
        return describeContents(this.device);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
