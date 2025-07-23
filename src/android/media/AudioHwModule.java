package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioHwModule implements Parcelable {
    public static final Parcelable.Creator<AudioHwModule> CREATOR = new Parcelable.Creator<AudioHwModule>() { // from class: android.media.AudioHwModule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHwModule createFromParcel(Parcel parcel) {
            AudioHwModule audioHwModule = new AudioHwModule();
            audioHwModule.readFromParcel(parcel);
            return audioHwModule;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHwModule[] newArray(int i) {
            return new AudioHwModule[i];
        }
    };
    public int handle = 0;
    public String name;
    public android.media.audio.common.AudioPort[] ports;
    public AudioRoute[] routes;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.handle);
        parcel.writeString(this.name);
        parcel.writeTypedArray(this.ports, i);
        parcel.writeTypedArray(this.routes, i);
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
                this.handle = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.ports = (android.media.audio.common.AudioPort[]) parcel.createTypedArray(android.media.audio.common.AudioPort.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.routes = (AudioRoute[]) parcel.createTypedArray(AudioRoute.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.routes) | describeContents(this.ports);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
