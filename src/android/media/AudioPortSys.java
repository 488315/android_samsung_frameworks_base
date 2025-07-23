package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioPortSys implements Parcelable {
    public static final Parcelable.Creator<AudioPortSys> CREATOR = new Parcelable.Creator<AudioPortSys>() { // from class: android.media.AudioPortSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortSys createFromParcel(Parcel parcel) {
            AudioPortSys audioPortSys = new AudioPortSys();
            audioPortSys.readFromParcel(parcel);
            return audioPortSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortSys[] newArray(int i) {
            return new AudioPortSys[i];
        }
    };
    public AudioPortConfigFw activeConfig;
    public AudioPortExtSys ext;
    public AudioGainSys[] gains;
    public AudioProfileSys[] profiles;
    public int role;
    public int type;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.role);
        parcel.writeInt(this.type);
        parcel.writeTypedArray(this.profiles, i);
        parcel.writeTypedArray(this.gains, i);
        parcel.writeTypedObject(this.activeConfig, i);
        parcel.writeTypedObject(this.ext, i);
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
                this.role = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.profiles = (AudioProfileSys[]) parcel.createTypedArray(AudioProfileSys.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.gains = (AudioGainSys[]) parcel.createTypedArray(AudioGainSys.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.activeConfig = (AudioPortConfigFw) parcel.readTypedObject(AudioPortConfigFw.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ext = (AudioPortExtSys) parcel.readTypedObject(AudioPortExtSys.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ext) | describeContents(this.profiles) | describeContents(this.gains) | describeContents(this.activeConfig);
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
