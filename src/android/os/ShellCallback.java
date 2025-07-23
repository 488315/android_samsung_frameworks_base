package android.os;

import android.os.Parcelable;
import android.util.Log;
import com.android.internal.os.IShellCallback;

/* loaded from: classes3.dex */
public class ShellCallback implements Parcelable {
    public static final Parcelable.Creator<ShellCallback> CREATOR = new Parcelable.Creator<ShellCallback>() { // from class: android.os.ShellCallback.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShellCallback createFromParcel(Parcel parcel) {
            return new ShellCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShellCallback[] newArray(int i) {
            return new ShellCallback[i];
        }
    };
    static final boolean DEBUG = false;
    static final String TAG = "ShellCallback";
    final boolean mLocal = true;
    IShellCallback mShellCallback;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelFileDescriptor onOpenFile(String str, String str2, String str3) {
        return null;
    }

    class MyShellCallback extends IShellCallback.Stub {
        MyShellCallback() {
        }

        @Override // com.android.internal.os.IShellCallback
        public ParcelFileDescriptor openFile(String str, String str2, String str3) {
            return ShellCallback.this.onOpenFile(str, str2, str3);
        }
    }

    public ShellCallback() {
    }

    public ParcelFileDescriptor openFile(String str, String str2, String str3) {
        if (this.mLocal) {
            return onOpenFile(str, str2, str3);
        }
        IShellCallback iShellCallback = this.mShellCallback;
        if (iShellCallback == null) {
            return null;
        }
        try {
            return iShellCallback.openFile(str, str2, str3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failure opening " + str, e);
            return null;
        }
    }

    public static void writeToParcel(ShellCallback shellCallback, Parcel parcel) {
        if (shellCallback == null) {
            parcel.writeStrongBinder(null);
        } else {
            shellCallback.writeToParcel(parcel, 0);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.mShellCallback == null) {
                this.mShellCallback = new MyShellCallback();
            }
            parcel.writeStrongBinder(this.mShellCallback.asBinder());
        }
    }

    public IBinder getShellCallbackBinder() {
        return this.mShellCallback.asBinder();
    }

    ShellCallback(Parcel parcel) {
        IShellCallback asInterface = IShellCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mShellCallback = asInterface;
        if (asInterface != null) {
            Binder.allowBlocking(asInterface.asBinder());
        }
    }
}
