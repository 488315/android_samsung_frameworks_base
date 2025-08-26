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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.containerPath);
        parcel.writeLong(this.containerOffset);
        parcel.writeLong(this.methodOffset);
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
                this.containerPath = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.containerOffset = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.methodOffset = parcel.readLong();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
