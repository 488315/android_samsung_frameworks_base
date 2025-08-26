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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.info, i);
        parcel.writeTypedObject(this.dynamic, i);
        parcel.writeInt(this.portId);
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
                this.info = (android.media.audio.common.MicrophoneInfo) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dynamic = (MicrophoneDynamicInfo) parcel.readTypedObject(MicrophoneDynamicInfo.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.portId = parcel.readInt();
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
        return describeContents(this.dynamic) | describeContents(this.info);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
