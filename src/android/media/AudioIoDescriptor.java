package android.media;

import android.media.audio.common.AudioChannelLayout;
import android.media.audio.common.AudioFormatDescription;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioIoDescriptor implements Parcelable {
    public static final Parcelable.Creator<AudioIoDescriptor> CREATOR = new Parcelable.Creator<AudioIoDescriptor>() { // from class: android.media.AudioIoDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioIoDescriptor createFromParcel(Parcel parcel) {
            AudioIoDescriptor audioIoDescriptor = new AudioIoDescriptor();
            audioIoDescriptor.readFromParcel(parcel);
            return audioIoDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioIoDescriptor[] newArray(int i) {
            return new AudioIoDescriptor[i];
        }
    };
    public AudioChannelLayout channelMask;
    public AudioFormatDescription format;
    public AudioPatchFw patch;
    public int ioHandle = 0;
    public boolean isInput = false;
    public int samplingRate = 0;
    public long frameCount = 0;
    public long frameCountHAL = 0;
    public int latency = 0;
    public int portId = 0;
    public boolean mDsEnable = false;
    public int mDsProfile = 0;
    public int mDsDevice = 0;
    public boolean mDsInfoChanged = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.ioHandle);
        parcel.writeTypedObject(this.patch, i);
        parcel.writeBoolean(this.isInput);
        parcel.writeInt(this.samplingRate);
        parcel.writeTypedObject(this.format, i);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeLong(this.frameCount);
        parcel.writeLong(this.frameCountHAL);
        parcel.writeInt(this.latency);
        parcel.writeInt(this.portId);
        parcel.writeBoolean(this.mDsEnable);
        parcel.writeInt(this.mDsProfile);
        parcel.writeInt(this.mDsDevice);
        parcel.writeBoolean(this.mDsInfoChanged);
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
                this.ioHandle = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.patch = (AudioPatchFw) parcel.readTypedObject(AudioPatchFw.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isInput = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.samplingRate = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.format = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.frameCount = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.frameCountHAL = parcel.readLong();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.latency = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.portId = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.mDsEnable = parcel.readBoolean();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.mDsProfile = parcel.readInt();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.mDsDevice = parcel.readInt();
                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                    this.mDsInfoChanged = parcel.readBoolean();
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
        return describeContents(this.channelMask) | describeContents(this.patch) | describeContents(this.format);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
