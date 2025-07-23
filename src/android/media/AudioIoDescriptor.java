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
        int dataPosition = parcel.dataPosition();
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
                this.ioHandle = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.patch = (AudioPatchFw) parcel.readTypedObject(AudioPatchFw.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isInput = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.samplingRate = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.format = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.frameCount = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.frameCountHAL = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.latency = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.portId = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.mDsEnable = parcel.readBoolean();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.mDsProfile = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.mDsDevice = parcel.readInt();
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.mDsInfoChanged = parcel.readBoolean();
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
        return describeContents(this.channelMask) | describeContents(this.patch) | describeContents(this.format);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
