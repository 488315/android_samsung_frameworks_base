package android.hardware.biometrics.face;

import android.hardware.biometrics.common.OperationContext;
import android.hardware.common.NativeHandle;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Surface;

/* loaded from: classes2.dex */
public class FaceEnrollOptions implements Parcelable {
    public static final Parcelable.Creator<FaceEnrollOptions> CREATOR = new Parcelable.Creator<FaceEnrollOptions>() { // from class: android.hardware.biometrics.face.FaceEnrollOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollOptions createFromParcel(Parcel parcel) {
            FaceEnrollOptions faceEnrollOptions = new FaceEnrollOptions();
            faceEnrollOptions.readFromParcel(parcel);
            return faceEnrollOptions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollOptions[] newArray(int i) {
            return new FaceEnrollOptions[i];
        }
    };
    public OperationContext context;
    public byte enrollmentType;
    public byte[] features;
    public HardwareAuthToken hardwareAuthToken;

    @Deprecated
    public NativeHandle nativeHandlePreview;
    public Surface surfacePreview;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.hardwareAuthToken, i);
        parcel.writeByte(this.enrollmentType);
        parcel.writeByteArray(this.features);
        parcel.writeTypedObject(this.nativeHandlePreview, i);
        parcel.writeTypedObject(this.surfacePreview, i);
        parcel.writeTypedObject(this.context, i);
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
                this.hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.enrollmentType = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.features = parcel.createByteArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.nativeHandlePreview = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.surfacePreview = (Surface) parcel.readTypedObject(Surface.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.context = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
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
        return describeContents(this.context) | describeContents(this.hardwareAuthToken) | describeContents(this.nativeHandlePreview) | describeContents(this.surfacePreview);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
