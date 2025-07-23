package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class Request implements Parcelable {
    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.hardware.camera2.extension.Request.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Request createFromParcel(Parcel parcel) {
            Request request = new Request();
            request.readFromParcel(parcel);
            return request;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Request[] newArray(int i) {
            return new Request[i];
        }
    };
    public CameraMetadataNative parameters;
    public List<OutputConfigId> targetOutputConfigIds;
    public int templateId = 0;
    public int requestId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedList(this.targetOutputConfigIds, i);
        parcel.writeTypedObject(this.parameters, i);
        parcel.writeInt(this.templateId);
        parcel.writeInt(this.requestId);
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
                this.targetOutputConfigIds = parcel.createTypedArrayList(OutputConfigId.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.parameters = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.templateId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.requestId = parcel.readInt();
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
        return describeContents(this.parameters) | describeContents(this.targetOutputConfigIds);
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
