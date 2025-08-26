package android.media;

import android.media.audio.common.AudioConfigBase;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GetOutputForAttrResponse implements Parcelable {
    public static final Parcelable.Creator<GetOutputForAttrResponse> CREATOR = new Parcelable.Creator<GetOutputForAttrResponse>() { // from class: android.media.GetOutputForAttrResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetOutputForAttrResponse createFromParcel(Parcel parcel) {
            GetOutputForAttrResponse getOutputForAttrResponse = new GetOutputForAttrResponse();
            getOutputForAttrResponse.readFromParcel(parcel);
            return getOutputForAttrResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetOutputForAttrResponse[] newArray(int i) {
            return new GetOutputForAttrResponse[i];
        }
    };
    public android.media.audio.common.AudioAttributes attr;
    public AudioConfigBase configBase;
    public int[] secondaryOutputs;
    public int[] selectedDeviceIds;
    public int stream;
    public int output = 0;
    public int portId = 0;
    public boolean isSpatialized = false;
    public boolean isBitPerfect = false;
    public float volume = 0.0f;
    public boolean muted = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.output);
        parcel.writeInt(this.stream);
        parcel.writeIntArray(this.selectedDeviceIds);
        parcel.writeInt(this.portId);
        parcel.writeIntArray(this.secondaryOutputs);
        parcel.writeBoolean(this.isSpatialized);
        parcel.writeTypedObject(this.configBase, i);
        parcel.writeBoolean(this.isBitPerfect);
        parcel.writeTypedObject(this.attr, i);
        parcel.writeFloat(this.volume);
        parcel.writeBoolean(this.muted);
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
                this.output = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.stream = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.selectedDeviceIds = parcel.createIntArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.portId = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.secondaryOutputs = parcel.createIntArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.isSpatialized = parcel.readBoolean();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.configBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.isBitPerfect = parcel.readBoolean();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.attr = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.volume = parcel.readFloat();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.muted = parcel.readBoolean();
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
        return describeContents(this.attr) | describeContents(this.configBase);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
