package android.view.inputmethod;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class InputBinding implements Parcelable {
    public static final Parcelable.Creator<InputBinding> CREATOR = new Parcelable.Creator<InputBinding>() { // from class: android.view.inputmethod.InputBinding.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputBinding createFromParcel(Parcel parcel) {
            return new InputBinding(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputBinding[] newArray(int i) {
            return new InputBinding[i];
        }
    };
    static final String TAG = "InputBinding";
    final InputConnection mConnection;
    final IBinder mConnectionToken;
    final int mPid;
    final int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InputBinding(InputConnection inputConnection, IBinder iBinder, int i, int i2) {
        this.mConnection = inputConnection;
        this.mConnectionToken = iBinder;
        this.mUid = i;
        this.mPid = i2;
    }

    public InputBinding(InputConnection inputConnection, InputBinding inputBinding) {
        this.mConnection = inputConnection;
        this.mConnectionToken = inputBinding.getConnectionToken();
        this.mUid = inputBinding.getUid();
        this.mPid = inputBinding.getPid();
    }

    InputBinding(Parcel parcel) {
        this.mConnection = null;
        this.mConnectionToken = parcel.readStrongBinder();
        this.mUid = parcel.readInt();
        this.mPid = parcel.readInt();
    }

    public InputConnection getConnection() {
        return this.mConnection;
    }

    public IBinder getConnectionToken() {
        return this.mConnectionToken;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getPid() {
        return this.mPid;
    }

    public String toString() {
        return "InputBinding{" + this.mConnectionToken + " / uid " + this.mUid + " / pid " + this.mPid + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mConnectionToken);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mPid);
    }
}
