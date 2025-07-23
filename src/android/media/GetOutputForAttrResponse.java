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
        int dataPosition = parcel.dataPosition();
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
                this.output = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.stream = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.selectedDeviceIds = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.portId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.secondaryOutputs = parcel.createIntArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.isSpatialized = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.configBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.isBitPerfect = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.attr = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.volume = parcel.readFloat();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.muted = parcel.readBoolean();
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
        return describeContents(this.attr) | describeContents(this.configBase);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
