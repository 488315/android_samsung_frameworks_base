package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehCbConfigArgs implements Parcelable {
    public static final Parcelable.Creator<SehCbConfigArgs> CREATOR = new Parcelable.Creator<SehCbConfigArgs>() { // from class: vendor.samsung.hardware.radio.messaging.SehCbConfigArgs.1
        @Override // android.os.Parcelable.Creator
        public SehCbConfigArgs createFromParcel(Parcel parcel) {
            SehCbConfigArgs sehCbConfigArgs = new SehCbConfigArgs();
            sehCbConfigArgs.readFromParcel(parcel);
            return sehCbConfigArgs;
        }

        @Override // android.os.Parcelable.Creator
        public SehCbConfigArgs[] newArray(int i) {
            return new SehCbConfigArgs[i];
        }
    };
    public String msgIDs;
    public int enabled = 0;
    public int selectedId = 0;
    public int msgIdMaxCount = 0;
    public int msgIdCount = 0;

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
        parcel.writeInt(this.enabled);
        parcel.writeInt(this.selectedId);
        parcel.writeInt(this.msgIdMaxCount);
        parcel.writeInt(this.msgIdCount);
        parcel.writeString(this.msgIDs);
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
                this.enabled = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.selectedId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.msgIdMaxCount = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.msgIdCount = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.msgIDs = parcel.readString();
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
