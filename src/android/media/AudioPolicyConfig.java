package android.media;

import android.media.audio.common.AudioHalEngineConfig;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioPolicyConfig implements Parcelable {
    public static final Parcelable.Creator<AudioPolicyConfig> CREATOR = new Parcelable.Creator<AudioPolicyConfig>() { // from class: android.media.AudioPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyConfig createFromParcel(Parcel parcel) {
            AudioPolicyConfig audioPolicyConfig = new AudioPolicyConfig();
            audioPolicyConfig.readFromParcel(parcel);
            return audioPolicyConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyConfig[] newArray(int i) {
            return new AudioPolicyConfig[i];
        }
    };
    public AudioHalEngineConfig engineConfig;
    public AudioHwModule[] modules;
    public int[] supportedModes;
    public SurroundSoundConfig surroundSoundConfig;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.modules, i);
        parcel.writeIntArray(this.supportedModes);
        parcel.writeTypedObject(this.surroundSoundConfig, i);
        parcel.writeTypedObject(this.engineConfig, i);
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
                this.modules = (AudioHwModule[]) parcel.createTypedArray(AudioHwModule.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.supportedModes = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.surroundSoundConfig = (SurroundSoundConfig) parcel.readTypedObject(SurroundSoundConfig.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.engineConfig = (AudioHalEngineConfig) parcel.readTypedObject(AudioHalEngineConfig.CREATOR);
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
        return describeContents(this.engineConfig) | describeContents(this.modules) | describeContents(this.surroundSoundConfig);
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
