package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioProfileSys implements Parcelable {
    public static final Parcelable.Creator<AudioProfileSys> CREATOR = new Parcelable.Creator<AudioProfileSys>() { // from class: android.media.AudioProfileSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfileSys createFromParcel(Parcel parcel) {
            AudioProfileSys audioProfileSys = new AudioProfileSys();
            audioProfileSys.readFromParcel(parcel);
            return audioProfileSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfileSys[] newArray(int i) {
            return new AudioProfileSys[i];
        }
    };
    public boolean isDynamicFormat = false;
    public boolean isDynamicChannels = false;
    public boolean isDynamicRate = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isDynamicFormat);
        parcel.writeBoolean(this.isDynamicChannels);
        parcel.writeBoolean(this.isDynamicRate);
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
                this.isDynamicFormat = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isDynamicChannels = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isDynamicRate = parcel.readBoolean();
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
}
