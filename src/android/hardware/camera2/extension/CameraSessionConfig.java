package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class CameraSessionConfig implements Parcelable {
    public static final Parcelable.Creator<CameraSessionConfig> CREATOR = new Parcelable.Creator<CameraSessionConfig>() { // from class: android.hardware.camera2.extension.CameraSessionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraSessionConfig createFromParcel(Parcel parcel) {
            CameraSessionConfig cameraSessionConfig = new CameraSessionConfig();
            cameraSessionConfig.readFromParcel(parcel);
            return cameraSessionConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraSessionConfig[] newArray(int i) {
            return new CameraSessionConfig[i];
        }
    };
    public List<CameraOutputConfig> outputConfigs;
    public CameraMetadataNative sessionParameter;
    public int sessionTemplateId = 0;
    public int sessionType = 0;
    public int colorSpace = -1;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedList(this.outputConfigs, i);
        parcel.writeTypedObject(this.sessionParameter, i);
        parcel.writeInt(this.sessionTemplateId);
        parcel.writeInt(this.sessionType);
        parcel.writeInt(this.colorSpace);
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
                this.outputConfigs = parcel.createTypedArrayList(CameraOutputConfig.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sessionParameter = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sessionTemplateId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sessionType = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.colorSpace = parcel.readInt();
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
        return describeContents(this.sessionParameter) | describeContents(this.outputConfigs);
    }

    private int describeContents(Object obj) {
        int iDescribeContents = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                iDescribeContents |= describeContents(it.next());
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
