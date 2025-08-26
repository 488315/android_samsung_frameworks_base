package android.hardware.camera2.extension;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ParcelCaptureResult implements Parcelable {
    public static final Parcelable.Creator<ParcelCaptureResult> CREATOR = new Parcelable.Creator<ParcelCaptureResult>() { // from class: android.hardware.camera2.extension.ParcelCaptureResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelCaptureResult createFromParcel(Parcel parcel) {
            ParcelCaptureResult parcelCaptureResult = new ParcelCaptureResult();
            parcelCaptureResult.readFromParcel(parcel);
            return parcelCaptureResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelCaptureResult[] newArray(int i) {
            return new ParcelCaptureResult[i];
        }
    };
    public String cameraId;
    public CaptureRequest parent;
    public CameraMetadataNative results;
    public int sequenceId = 0;
    public long frameNumber = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.cameraId);
        parcel.writeTypedObject(this.results, i);
        parcel.writeTypedObject(this.parent, i);
        parcel.writeInt(this.sequenceId);
        parcel.writeLong(this.frameNumber);
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
                this.cameraId = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.results = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.parent = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sequenceId = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.frameNumber = parcel.readLong();
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
        return describeContents(this.parent) | describeContents(this.results);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
