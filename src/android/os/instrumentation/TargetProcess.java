package android.os.instrumentation;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class TargetProcess implements Parcelable {
    public static final Parcelable.Creator<TargetProcess> CREATOR = new Parcelable.Creator<TargetProcess>() { // from class: android.os.instrumentation.TargetProcess.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TargetProcess createFromParcel(Parcel parcel) {
            TargetProcess targetProcess = new TargetProcess();
            targetProcess.readFromParcel(parcel);
            return targetProcess;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TargetProcess[] newArray(int i) {
            return new TargetProcess[i];
        }
    };
    public String processName;
    public int uid = 0;
    public int pid = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeString(this.processName);
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
                this.uid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.pid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.processName = parcel.readString();
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
        stringJoiner.add("uid: " + this.uid);
        stringJoiner.add("pid: " + this.pid);
        stringJoiner.add("processName: " + Objects.toString(this.processName));
        return "TargetProcess" + stringJoiner.toString();
    }
}
