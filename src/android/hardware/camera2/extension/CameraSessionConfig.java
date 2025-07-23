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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedList(this.outputConfigs, i);
        parcel.writeTypedObject(this.sessionParameter, i);
        parcel.writeInt(this.sessionTemplateId);
        parcel.writeInt(this.sessionType);
        parcel.writeInt(this.colorSpace);
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
                this.outputConfigs = parcel.createTypedArrayList(CameraOutputConfig.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sessionParameter = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sessionTemplateId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.sessionType = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.colorSpace = parcel.readInt();
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
        return describeContents(this.sessionParameter) | describeContents(this.outputConfigs);
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
