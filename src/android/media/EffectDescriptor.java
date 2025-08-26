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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeTypedObject(this.uuid, i);
        parcel.writeInt(this.apiVersion);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.cpuLoad);
        parcel.writeInt(this.memoryUsage);
        parcel.writeString(this.name);
        parcel.writeString(this.implementor);
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
                this.type = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uuid = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.apiVersion = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.flags = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.cpuLoad = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.memoryUsage = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.name = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.implementor = parcel.readString();
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
        return describeContents(this.uuid) | describeContents(this.type);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
