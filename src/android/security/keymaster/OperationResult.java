package android.security.keymaster;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class OperationResult implements Parcelable {
    public static final Parcelable.Creator<OperationResult> CREATOR = new Parcelable.Creator<OperationResult>() { // from class: android.security.keymaster.OperationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationResult createFromParcel(Parcel parcel) {
            return new OperationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationResult[] newArray(int i) {
            return new OperationResult[i];
        }
    };
    public final int inputConsumed;
    public final long operationHandle;
    public final KeymasterArguments outParams;
    public final byte[] output;
    public final int resultCode;
    public final IBinder token;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OperationResult(int i, IBinder iBinder, long j, int i2, byte[] bArr, KeymasterArguments keymasterArguments) {
        this.resultCode = i;
        this.token = iBinder;
        this.operationHandle = j;
        this.inputConsumed = i2;
        this.output = bArr;
        this.outParams = keymasterArguments;
    }

    public OperationResult(int i) {
        this(i, null, 0L, 0, null, null);
    }

    protected OperationResult(Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.operationHandle = parcel.readLong();
        this.inputConsumed = parcel.readInt();
        this.output = parcel.createByteArray();
        this.outParams = KeymasterArguments.CREATOR.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.resultCode);
        parcel.writeStrongBinder(this.token);
        parcel.writeLong(this.operationHandle);
        parcel.writeInt(this.inputConsumed);
        parcel.writeByteArray(this.output);
        this.outParams.writeToParcel(parcel, i);
    }
}
