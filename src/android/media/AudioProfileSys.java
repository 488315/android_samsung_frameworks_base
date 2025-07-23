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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isDynamicFormat);
        parcel.writeBoolean(this.isDynamicChannels);
        parcel.writeBoolean(this.isDynamicRate);
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
                this.isDynamicFormat = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isDynamicChannels = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isDynamicRate = parcel.readBoolean();
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
}
