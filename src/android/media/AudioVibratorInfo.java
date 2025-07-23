package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioVibratorInfo implements Parcelable {
    public static final Parcelable.Creator<AudioVibratorInfo> CREATOR = new Parcelable.Creator<AudioVibratorInfo>() { // from class: android.media.AudioVibratorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVibratorInfo createFromParcel(Parcel parcel) {
            AudioVibratorInfo audioVibratorInfo = new AudioVibratorInfo();
            audioVibratorInfo.readFromParcel(parcel);
            return audioVibratorInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVibratorInfo[] newArray(int i) {
            return new AudioVibratorInfo[i];
        }
    };
    public int id = 0;
    public float resonantFrequency = 0.0f;
    public float qFactor = 0.0f;
    public float maxAmplitude = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeFloat(this.resonantFrequency);
        parcel.writeFloat(this.qFactor);
        parcel.writeFloat(this.maxAmplitude);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.resonantFrequency = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.qFactor = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxAmplitude = parcel.readFloat();
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
