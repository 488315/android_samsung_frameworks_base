package android.hardware.camera2.extension;

import android.hardware.camera2.CaptureRequest;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CaptureFailure implements Parcelable {
    public static final Parcelable.Creator<CaptureFailure> CREATOR = new Parcelable.Creator<CaptureFailure>() { // from class: android.hardware.camera2.extension.CaptureFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureFailure createFromParcel(Parcel parcel) {
            CaptureFailure captureFailure = new CaptureFailure();
            captureFailure.readFromParcel(parcel);
            return captureFailure;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureFailure[] newArray(int i) {
            return new CaptureFailure[i];
        }
    };
    public String errorPhysicalCameraId;
    public CaptureRequest request;
    public int reason = 0;
    public boolean dropped = false;
    public int sequenceId = 0;
    public long frameNumber = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.request, i);
        parcel.writeInt(this.reason);
        parcel.writeBoolean(this.dropped);
        parcel.writeInt(this.sequenceId);
        parcel.writeLong(this.frameNumber);
        parcel.writeString(this.errorPhysicalCameraId);
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
                this.request = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.reason = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.dropped = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.sequenceId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.frameNumber = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.errorPhysicalCameraId = parcel.readString();
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
        return describeContents(this.request);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
