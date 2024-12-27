package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes.dex */
public interface ICallNotificationEventCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ICallNotificationEventCallback";

    void onCallNotificationPosted(String str, UserHandle userHandle) throws RemoteException;

    void onCallNotificationRemoved(String str, UserHandle userHandle) throws RemoteException;

    public static class Default implements ICallNotificationEventCallback {
        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationPosted(String packageName, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationRemoved(String packageName, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICallNotificationEventCallback {
        static final int TRANSACTION_onCallNotificationPosted = 1;
        static final int TRANSACTION_onCallNotificationRemoved = 2;

        public Stub() {
            attachInterface(this, ICallNotificationEventCallback.DESCRIPTOR);
        }

        public static ICallNotificationEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICallNotificationEventCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICallNotificationEventCallback)) {
                return (ICallNotificationEventCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCallNotificationPosted";
                case 2:
                    return "onCallNotificationRemoved";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICallNotificationEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICallNotificationEventCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    UserHandle _arg1 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    onCallNotificationPosted(_arg0, _arg1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    UserHandle _arg12 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    onCallNotificationRemoved(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICallNotificationEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallNotificationEventCallback.DESCRIPTOR;
            }

            @Override // android.app.ICallNotificationEventCallback
            public void onCallNotificationPosted(String packageName, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallNotificationEventCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ICallNotificationEventCallback
            public void onCallNotificationRemoved(String packageName, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallNotificationEventCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
