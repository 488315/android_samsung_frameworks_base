package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SelectUiccSub implements Parcelable {
    public static final int ACT_STATUS_ACTIVATE = 1;
    public static final int ACT_STATUS_DEACTIVATE = 0;
    public static final Parcelable.Creator<SelectUiccSub> CREATOR = new Parcelable.Creator<SelectUiccSub>() { // from class: android.hardware.radio.sim.SelectUiccSub.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectUiccSub createFromParcel(Parcel parcel) {
            SelectUiccSub selectUiccSub = new SelectUiccSub();
            selectUiccSub.readFromParcel(parcel);
            return selectUiccSub;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectUiccSub[] newArray(int i) {
            return new SelectUiccSub[i];
        }
    };
    public static final int SUBSCRIPTION_TYPE_1 = 0;
    public static final int SUBSCRIPTION_TYPE_2 = 1;
    public static final int SUBSCRIPTION_TYPE_3 = 2;
    public int slot = 0;
    public int appIndex = 0;
    public int subType = 0;
    public int actStatus = 0;

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
        parcel.writeInt(this.slot);
        parcel.writeInt(this.appIndex);
        parcel.writeInt(this.subType);
        parcel.writeInt(this.actStatus);
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
                this.slot = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.appIndex = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.subType = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.actStatus = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("slot: " + this.slot);
        stringJoiner.add("appIndex: " + this.appIndex);
        stringJoiner.add("subType: " + this.subType);
        stringJoiner.add("actStatus: " + this.actStatus);
        return "SelectUiccSub" + stringJoiner.toString();
    }
}
