package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSsReleaseComplete implements Parcelable {
    public static final Parcelable.Creator<SehSsReleaseComplete> CREATOR = new Parcelable.Creator<SehSsReleaseComplete>() { // from class: vendor.samsung.hardware.radio.sim.SehSsReleaseComplete.1
        @Override // android.os.Parcelable.Creator
        public SehSsReleaseComplete createFromParcel(Parcel parcel) {
            SehSsReleaseComplete sehSsReleaseComplete = new SehSsReleaseComplete();
            sehSsReleaseComplete.readFromParcel(parcel);
            return sehSsReleaseComplete;
        }

        @Override // android.os.Parcelable.Creator
        public SehSsReleaseComplete[] newArray(int i) {
            return new SehSsReleaseComplete[i];
        }
    };
    public String data;
    public int size = 0;
    public int dataLen = 0;
    public int params = 0;
    public int status = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.size);
        parcel.writeInt(this.dataLen);
        parcel.writeInt(this.params);
        parcel.writeInt(this.status);
        parcel.writeString(this.data);
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
                this.size = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dataLen = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.params = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.data = parcel.readString();
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
}
