package android.os.instrumentation;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class ExecutableMethodFileOffsets implements Parcelable {
    public static final Parcelable.Creator<ExecutableMethodFileOffsets> CREATOR = new Parcelable.Creator<ExecutableMethodFileOffsets>() { // from class: android.os.instrumentation.ExecutableMethodFileOffsets.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecutableMethodFileOffsets createFromParcel(Parcel parcel) {
            ExecutableMethodFileOffsets executableMethodFileOffsets = new ExecutableMethodFileOffsets();
            executableMethodFileOffsets.readFromParcel(parcel);
            return executableMethodFileOffsets;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecutableMethodFileOffsets[] newArray(int i) {
            return new ExecutableMethodFileOffsets[i];
        }
    };
    public String containerPath;
    public long containerOffset = 0;
    public long methodOffset = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.containerPath);
        parcel.writeLong(this.containerOffset);
        parcel.writeLong(this.methodOffset);
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
                this.containerPath = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.containerOffset = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.methodOffset = parcel.readLong();
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
        stringJoiner.add("containerPath: " + Objects.toString(this.containerPath));
        stringJoiner.add("containerOffset: " + this.containerOffset);
        stringJoiner.add("methodOffset: " + this.methodOffset);
        return "ExecutableMethodFileOffsets" + stringJoiner.toString();
    }
}
