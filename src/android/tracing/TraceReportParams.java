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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.reporterPackageName);
        parcel.writeString(this.reporterClassName);
        parcel.writeTypedObject(this.fd, i);
        parcel.writeLong(this.uuidLsb);
        parcel.writeLong(this.uuidMsb);
        parcel.writeBoolean(this.usePipeForTesting);
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
                this.reporterPackageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.reporterClassName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.fd = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.uuidLsb = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.uuidMsb = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.usePipeForTesting = parcel.readBoolean();
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
        return describeContents(this.fd);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
