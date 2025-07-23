package android.media;

import android.media.audio.common.AudioConfigBase;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GetInputForAttrResponse implements Parcelable {
    public static final Parcelable.Creator<GetInputForAttrResponse> CREATOR = new Parcelable.Creator<GetInputForAttrResponse>() { // from class: android.media.GetInputForAttrResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetInputForAttrResponse createFromParcel(Parcel parcel) {
            GetInputForAttrResponse getInputForAttrResponse = new GetInputForAttrResponse();
            getInputForAttrResponse.readFromParcel(parcel);
            return getInputForAttrResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetInputForAttrResponse[] newArray(int i) {
            return new GetInputForAttrResponse[i];
        }
    };
    public AudioConfigBase config;
    public int source;
    public int input = 0;
    public int selectedDeviceId = 0;
    public int portId = 0;
    public int virtualDeviceId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.input);
        parcel.writeInt(this.selectedDeviceId);
        parcel.writeInt(this.portId);
        parcel.writeInt(this.virtualDeviceId);
        parcel.writeTypedObject(this.config, i);
        parcel.writeInt(this.source);
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
                this.input = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.selectedDeviceId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.portId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.virtualDeviceId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.config = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.source = parcel.readInt();
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
        return describeContents(this.config);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
