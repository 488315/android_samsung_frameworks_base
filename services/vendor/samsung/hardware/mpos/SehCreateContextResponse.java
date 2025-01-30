package vendor.samsung.hardware.mpos;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SehCreateContextResponse implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: vendor.samsung.hardware.mpos.SehCreateContextResponse.1
        @Override // android.os.Parcelable.Creator
        public SehCreateContextResponse createFromParcel(Parcel parcel) {
            SehCreateContextResponse sehCreateContextResponse = new SehCreateContextResponse();
            sehCreateContextResponse.readFromParcel(parcel);
            return sehCreateContextResponse;
        }

        @Override // android.os.Parcelable.Creator
        public SehCreateContextResponse[] newArray(int i) {
            return new SehCreateContextResponse[i];
        }
    };
    public int status = 0;
    public int sessionHandle = 0;
    public int maxSendMsgSize = 0;
    public int maxRecvMsgSize = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.sessionHandle);
        parcel.writeInt(this.maxSendMsgSize);
        parcel.writeInt(this.maxRecvMsgSize);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sessionHandle = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxSendMsgSize = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxRecvMsgSize = parcel.readInt();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                            parcel.setDataPosition(dataPosition + readInt);
                            return;
                        }
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
