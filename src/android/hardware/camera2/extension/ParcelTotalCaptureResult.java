package android.hardware.camera2.extension;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ParcelTotalCaptureResult implements Parcelable {
    public static final Parcelable.Creator<ParcelTotalCaptureResult> CREATOR = new Parcelable.Creator<ParcelTotalCaptureResult>() { // from class: android.hardware.camera2.extension.ParcelTotalCaptureResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelTotalCaptureResult createFromParcel(Parcel parcel) {
            ParcelTotalCaptureResult parcelTotalCaptureResult = new ParcelTotalCaptureResult();
            parcelTotalCaptureResult.readFromParcel(parcel);
            return parcelTotalCaptureResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelTotalCaptureResult[] newArray(int i) {
            return new ParcelTotalCaptureResult[i];
        }
    };
    public String logicalCameraId;
    public CaptureRequest parent;
    public List<ParcelCaptureResult> partials;
    public List<PhysicalCaptureResultInfo> physicalResult;
    public CameraMetadataNative results;
    public int sequenceId = 0;
    public long frameNumber = 0;
    public int sessionId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.logicalCameraId);
        parcel.writeTypedObject(this.results, i);
        parcel.writeTypedObject(this.parent, i);
        parcel.writeInt(this.sequenceId);
        parcel.writeLong(this.frameNumber);
        parcel.writeTypedList(this.partials, i);
        parcel.writeInt(this.sessionId);
        parcel.writeTypedList(this.physicalResult, i);
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
                this.logicalCameraId = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.results = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.parent = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.sequenceId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.frameNumber = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.partials = parcel.createTypedArrayList(ParcelCaptureResult.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.sessionId = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.physicalResult = parcel.createTypedArrayList(PhysicalCaptureResultInfo.CREATOR);
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
        return describeContents(this.physicalResult) | describeContents(this.results) | describeContents(this.parent) | describeContents(this.partials);
    }

    private int describeContents(Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
