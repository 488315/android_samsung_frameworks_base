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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.role);
        parcel.writeInt(this.type);
        parcel.writeTypedArray(this.profiles, i);
        parcel.writeTypedArray(this.gains, i);
        parcel.writeTypedObject(this.activeConfig, i);
        parcel.writeTypedObject(this.ext, i);
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
                this.role = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.profiles = (AudioProfileSys[]) parcel.createTypedArray(AudioProfileSys.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.gains = (AudioGainSys[]) parcel.createTypedArray(AudioGainSys.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.activeConfig = (AudioPortConfigFw) parcel.readTypedObject(AudioPortConfigFw.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.ext = (AudioPortExtSys) parcel.readTypedObject(AudioPortExtSys.CREATOR);
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
        return describeContents(this.ext) | describeContents(this.profiles) | describeContents(this.gains) | describeContents(this.activeConfig);
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
