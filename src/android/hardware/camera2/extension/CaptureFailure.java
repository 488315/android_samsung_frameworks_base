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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.request, i);
        parcel.writeInt(this.reason);
        parcel.writeBoolean(this.dropped);
        parcel.writeInt(this.sequenceId);
        parcel.writeLong(this.frameNumber);
        parcel.writeString(this.errorPhysicalCameraId);
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
                this.request = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.reason = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.dropped = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sequenceId = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.frameNumber = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.errorPhysicalCameraId = parcel.readString();
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
        return describeContents(this.request);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
