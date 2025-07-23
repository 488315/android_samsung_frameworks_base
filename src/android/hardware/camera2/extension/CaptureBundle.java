package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CaptureBundle implements Parcelable {
    public static final Parcelable.Creator<CaptureBundle> CREATOR = new Parcelable.Creator<CaptureBundle>() { // from class: android.hardware.camera2.extension.CaptureBundle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureBundle createFromParcel(Parcel parcel) {
            CaptureBundle captureBundle = new CaptureBundle();
            captureBundle.readFromParcel(parcel);
            return captureBundle;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureBundle[] newArray(int i) {
            return new CaptureBundle[i];
        }
    };
    public ParcelImage captureImage;
    public CameraMetadataNative captureResult;
    public int stage = 0;
    public int sequenceId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.stage);
        parcel.writeInt(this.sequenceId);
        parcel.writeTypedObject(this.captureResult, i);
        parcel.writeTypedObject(this.captureImage, i);
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
                this.stage = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sequenceId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.captureResult = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.captureImage = (ParcelImage) parcel.readTypedObject(ParcelImage.CREATOR);
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
        return describeContents(this.captureImage) | describeContents(this.captureResult);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
