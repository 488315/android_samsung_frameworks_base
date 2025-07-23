package android.os;

import android.annotation.SystemApi;
import android.os.IRemoteCallback;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class RemoteCallback implements Parcelable {
    public static final Parcelable.Creator<RemoteCallback> CREATOR = new Parcelable.Creator<RemoteCallback>() { // from class: android.os.RemoteCallback.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteCallback[] newArray(int i) {
            return new RemoteCallback[i];
        }
    };
    private final IRemoteCallback mCallback;
    private final Handler mHandler;
    private final OnResultListener mListener;

    public interface OnResultListener {
        void onResult(Bundle bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteCallback(OnResultListener onResultListener) {
        this(onResultListener, null);
    }

    public RemoteCallback(OnResultListener onResultListener, Handler handler) {
        if (onResultListener == null) {
            throw new NullPointerException("listener cannot be null");
        }
        this.mListener = onResultListener;
        this.mHandler = handler;
        this.mCallback = new IRemoteCallback.Stub() { // from class: android.os.RemoteCallback.1
            @Override // android.os.IRemoteCallback
            public void sendResult(Bundle bundle) {
                RemoteCallback.this.sendResult(bundle);
            }
        };
    }

    RemoteCallback(Parcel parcel) {
        this.mListener = null;
        this.mHandler = null;
        this.mCallback = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
    }

    public void sendResult(final Bundle bundle) {
        OnResultListener onResultListener = this.mListener;
        if (onResultListener != null) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.os.RemoteCallback.2
                    @Override // java.lang.Runnable
                    public void run() {
                        RemoteCallback.this.mListener.onResult(bundle);
                    }
                });
                return;
            } else {
                onResultListener.onResult(bundle);
                return;
            }
        }
        try {
            this.mCallback.sendResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public IRemoteCallback getInterface() {
        return this.mCallback;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mCallback.asBinder());
    }
}
