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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.enabled);
        parcel.writeInt(this.selectedId);
        parcel.writeInt(this.msgIdMaxCount);
        parcel.writeInt(this.msgIdCount);
        parcel.writeString(this.msgIDs);
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
                this.enabled = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.selectedId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.msgIdMaxCount = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.msgIdCount = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.msgIDs = parcel.readString();
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
}
