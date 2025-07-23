package android.media;

import android.media.audio.common.AudioUuid;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class EffectDescriptor implements Parcelable {
    public static final Parcelable.Creator<EffectDescriptor> CREATOR = new Parcelable.Creator<EffectDescriptor>() { // from class: android.media.EffectDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EffectDescriptor createFromParcel(Parcel parcel) {
            EffectDescriptor effectDescriptor = new EffectDescriptor();
            effectDescriptor.readFromParcel(parcel);
            return effectDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EffectDescriptor[] newArray(int i) {
            return new EffectDescriptor[i];
        }
    };
    public String implementor;
    public String name;
    public AudioUuid type;
    public AudioUuid uuid;
    public int apiVersion = 0;
    public int flags = 0;
    public int cpuLoad = 0;
    public int memoryUsage = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeTypedObject(this.uuid, i);
        parcel.writeInt(this.apiVersion);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.cpuLoad);
        parcel.writeInt(this.memoryUsage);
        parcel.writeString(this.name);
        parcel.writeString(this.implementor);
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
                this.type = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uuid = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.apiVersion = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.flags = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.cpuLoad = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.memoryUsage = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.name = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.implementor = parcel.readString();
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
        return describeContents(this.uuid) | describeContents(this.type);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
