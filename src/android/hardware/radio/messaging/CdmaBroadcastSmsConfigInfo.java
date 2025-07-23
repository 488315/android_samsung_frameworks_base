package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaBroadcastSmsConfigInfo implements Parcelable {
    public static final Parcelable.Creator<CdmaBroadcastSmsConfigInfo> CREATOR = new Parcelable.Creator<CdmaBroadcastSmsConfigInfo>() { // from class: android.hardware.radio.messaging.CdmaBroadcastSmsConfigInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaBroadcastSmsConfigInfo createFromParcel(Parcel parcel) {
            CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = new CdmaBroadcastSmsConfigInfo();
            cdmaBroadcastSmsConfigInfo.readFromParcel(parcel);
            return cdmaBroadcastSmsConfigInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaBroadcastSmsConfigInfo[] newArray(int i) {
            return new CdmaBroadcastSmsConfigInfo[i];
        }
    };

    @Deprecated
    public int serviceCategory = 0;

    @Deprecated
    public int language = 0;

    @Deprecated
    public boolean selected = false;

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
        parcel.writeInt(this.serviceCategory);
        parcel.writeInt(this.language);
        parcel.writeBoolean(this.selected);
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
                this.serviceCategory = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.language = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.selected = parcel.readBoolean();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("serviceCategory: " + this.serviceCategory);
        stringJoiner.add("language: " + this.language);
        stringJoiner.add("selected: " + this.selected);
        return "CdmaBroadcastSmsConfigInfo" + stringJoiner.toString();
    }
}
