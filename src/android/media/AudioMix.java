package android.media;

import android.media.audio.common.AudioConfig;
import android.media.audio.common.AudioDevice;
import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioMix implements Parcelable {
    public static final Parcelable.Creator<AudioMix> CREATOR = new Parcelable.Creator<AudioMix>() { // from class: android.media.AudioMix.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMix createFromParcel(Parcel parcel) {
            AudioMix audioMix = new AudioMix();
            audioMix.readFromParcel(parcel);
            return audioMix;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMix[] newArray(int i) {
            return new AudioMix[i];
        }
    };
    public AudioMixMatchCriterion[] criteria;
    public AudioDevice device;
    public AudioConfig format;
    public IBinder mToken;
    public int mixType;
    public int routeFlags = 0;
    public int cbFlags = 0;
    public boolean allowPrivilegedMediaPlaybackCapture = false;
    public boolean voiceCommunicationCaptureAllowed = false;
    public int mVirtualDeviceId = 0;
    public int mixFlags = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.criteria, i);
        parcel.writeInt(this.mixType);
        parcel.writeTypedObject(this.format, i);
        parcel.writeInt(this.routeFlags);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.cbFlags);
        parcel.writeBoolean(this.allowPrivilegedMediaPlaybackCapture);
        parcel.writeBoolean(this.voiceCommunicationCaptureAllowed);
        parcel.writeStrongBinder(this.mToken);
        parcel.writeInt(this.mVirtualDeviceId);
        parcel.writeInt(this.mixFlags);
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
                this.criteria = (AudioMixMatchCriterion[]) parcel.createTypedArray(AudioMixMatchCriterion.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mixType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.format = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.routeFlags = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.device = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.cbFlags = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.allowPrivilegedMediaPlaybackCapture = parcel.readBoolean();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.voiceCommunicationCaptureAllowed = parcel.readBoolean();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.mToken = parcel.readStrongBinder();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.mVirtualDeviceId = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.mixFlags = parcel.readInt();
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
        return describeContents(this.device) | describeContents(this.criteria) | describeContents(this.format);
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
