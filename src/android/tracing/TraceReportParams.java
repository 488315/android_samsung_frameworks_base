package android.tracing;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class TraceReportParams implements Parcelable {
    public static final Parcelable.Creator<TraceReportParams> CREATOR = new Parcelable.Creator<TraceReportParams>() { // from class: android.tracing.TraceReportParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TraceReportParams createFromParcel(Parcel parcel) {
            TraceReportParams traceReportParams = new TraceReportParams();
            traceReportParams.readFromParcel(parcel);
            return traceReportParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TraceReportParams[] newArray(int i) {
            return new TraceReportParams[i];
        }
    };
    public ParcelFileDescriptor fd;
    public String reporterClassName;
    public String reporterPackageName;
    public long uuidLsb = 0;
    public long uuidMsb = 0;
    public boolean usePipeForTesting = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.reporterPackageName);
        parcel.writeString(this.reporterClassName);
        parcel.writeTypedObject(this.fd, i);
        parcel.writeLong(this.uuidLsb);
        parcel.writeLong(this.uuidMsb);
        parcel.writeBoolean(this.usePipeForTesting);
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
                this.reporterPackageName = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.reporterClassName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.fd = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.uuidLsb = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.uuidMsb = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.usePipeForTesting = parcel.readBoolean();
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
        return describeContents(this.fd);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
