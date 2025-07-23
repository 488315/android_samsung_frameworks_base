package android.media;

import android.media.audio.common.MicrophoneDynamicInfo;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MicrophoneInfoFw implements Parcelable {
    public static final Parcelable.Creator<MicrophoneInfoFw> CREATOR = new Parcelable.Creator<MicrophoneInfoFw>() { // from class: android.media.MicrophoneInfoFw.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneInfoFw createFromParcel(Parcel parcel) {
            MicrophoneInfoFw microphoneInfoFw = new MicrophoneInfoFw();
            microphoneInfoFw.readFromParcel(parcel);
            return microphoneInfoFw;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneInfoFw[] newArray(int i) {
            return new MicrophoneInfoFw[i];
        }
    };
    public MicrophoneDynamicInfo dynamic;
    public android.media.audio.common.MicrophoneInfo info;
    public int portId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.info, i);
        parcel.writeTypedObject(this.dynamic, i);
        parcel.writeInt(this.portId);
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
                this.info = (android.media.audio.common.MicrophoneInfo) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.dynamic = (MicrophoneDynamicInfo) parcel.readTypedObject(MicrophoneDynamicInfo.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.portId = parcel.readInt();
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
        return describeContents(this.dynamic) | describeContents(this.info);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
