package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioExtraMetaData implements Parcelable {
    public static final Parcelable.Creator<AudioExtraMetaData> CREATOR = new Parcelable.Creator<AudioExtraMetaData>() { // from class: android.hardware.tv.tuner.AudioExtraMetaData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioExtraMetaData createFromParcel(Parcel parcel) {
            AudioExtraMetaData audioExtraMetaData = new AudioExtraMetaData();
            audioExtraMetaData.readFromParcel(parcel);
            return audioExtraMetaData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioExtraMetaData[] newArray(int i) {
            return new AudioExtraMetaData[i];
        }
    };
    public byte adFade = 0;
    public byte adPan = 0;
    public char versionTextTag = 0;
    public byte adGainCenter = 0;
    public byte adGainFront = 0;
    public byte adGainSurround = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.adFade);
        parcel.writeByte(this.adPan);
        parcel.writeInt(this.versionTextTag);
        parcel.writeByte(this.adGainCenter);
        parcel.writeByte(this.adGainFront);
        parcel.writeByte(this.adGainSurround);
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
                this.adFade = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.adPan = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.versionTextTag = (char) parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.adGainCenter = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.adGainFront = parcel.readByte();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.adGainSurround = parcel.readByte();
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
