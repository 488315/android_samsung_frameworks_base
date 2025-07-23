package android.os.instrumentation;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class MethodDescriptor implements Parcelable {
    public static final Parcelable.Creator<MethodDescriptor> CREATOR = new Parcelable.Creator<MethodDescriptor>() { // from class: android.os.instrumentation.MethodDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MethodDescriptor createFromParcel(Parcel parcel) {
            MethodDescriptor methodDescriptor = new MethodDescriptor();
            methodDescriptor.readFromParcel(parcel);
            return methodDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MethodDescriptor[] newArray(int i) {
            return new MethodDescriptor[i];
        }
    };
    public String fullyQualifiedClassName;
    public String[] fullyQualifiedParameters;
    public String methodName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.fullyQualifiedClassName);
        parcel.writeString(this.methodName);
        parcel.writeStringArray(this.fullyQualifiedParameters);
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
                this.fullyQualifiedClassName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.methodName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.fullyQualifiedParameters = parcel.createStringArray();
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
        stringJoiner.add("fullyQualifiedClassName: " + Objects.toString(this.fullyQualifiedClassName));
        stringJoiner.add("methodName: " + Objects.toString(this.methodName));
        stringJoiner.add("fullyQualifiedParameters: " + Arrays.toString(this.fullyQualifiedParameters));
        return "MethodDescriptor" + stringJoiner.toString();
    }
}
