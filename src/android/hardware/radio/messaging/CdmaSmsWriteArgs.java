package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSmsWriteArgs implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsWriteArgs> CREATOR = new Parcelable.Creator<CdmaSmsWriteArgs>() { // from class: android.hardware.radio.messaging.CdmaSmsWriteArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsWriteArgs createFromParcel(Parcel parcel) {
            CdmaSmsWriteArgs cdmaSmsWriteArgs = new CdmaSmsWriteArgs();
            cdmaSmsWriteArgs.readFromParcel(parcel);
            return cdmaSmsWriteArgs;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsWriteArgs[] newArray(int i) {
            return new CdmaSmsWriteArgs[i];
        }
    };

    @Deprecated
    public static final int STATUS_REC_READ = 1;

    @Deprecated
    public static final int STATUS_REC_UNREAD = 0;

    @Deprecated
    public static final int STATUS_STO_SENT = 3;

    @Deprecated
    public static final int STATUS_STO_UNSENT = 2;

    @Deprecated
    public CdmaSmsMessage message;

    @Deprecated
    public int status = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeTypedObject(this.message, i);
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
                    this.message = (CdmaSmsMessage) parcel.readTypedObject(CdmaSmsMessage.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("message: " + Objects.toString(this.message));
        return "CdmaSmsWriteArgs" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.message);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
