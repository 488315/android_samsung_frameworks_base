package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class UnsafeStateException extends IllegalStateException implements Parcelable {
    public static final Parcelable.Creator<UnsafeStateException> CREATOR = new Parcelable.Creator<UnsafeStateException>() { // from class: android.app.admin.UnsafeStateException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnsafeStateException createFromParcel(Parcel parcel) {
            return new UnsafeStateException(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnsafeStateException[] newArray(int i) {
            return new UnsafeStateException[i];
        }
    };
    private final int mOperation;
    private final int mReason;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnsafeStateException(int i, int i2) {
        Preconditions.checkArgument(DevicePolicyManager.isValidOperationSafetyReason(i2), "invalid reason %d", Integer.valueOf(i2));
        this.mOperation = i;
        this.mReason = i2;
    }

    public int getOperation() {
        return this.mOperation;
    }

    public List<Integer> getReasons() {
        return Arrays.asList(Integer.valueOf(this.mReason));
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return DevicePolicyManager.operationSafetyReasonToString(this.mReason);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOperation);
        parcel.writeInt(this.mReason);
    }
}
