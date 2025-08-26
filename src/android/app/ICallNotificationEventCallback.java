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

    public static class Default implements ICallNotificationEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationPosted(String str, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationRemoved(String str, UserHandle userHandle) throws RemoteException {
        }
    }

    void onCallNotificationPosted(String str, UserHandle userHandle) throws RemoteException;

    void onCallNotificationRemoved(String str, UserHandle userHandle) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallNotificationEventCallback {
        static final int TRANSACTION_onCallNotificationPosted = 1;
        static final int TRANSACTION_onCallNotificationRemoved = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICallNotificationEventCallback.DESCRIPTOR);
        }

        public static ICallNotificationEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallNotificationEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallNotificationEventCallback)) {
                return (ICallNotificationEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCallNotificationPosted";
            }
            if (i != 2) {
                return null;
            }
            return "onCallNotificationRemoved";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallNotificationEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallNotificationEventCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                parcel.enforceNoDataAvail();
                onCallNotificationPosted(string, userHandle);
            } else if (i == 2) {
                String string2 = parcel.readString();
                UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                parcel.enforceNoDataAvail();
                onCallNotificationRemoved(string2, userHandle2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallNotificationEventCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallNotificationEventCallback.DESCRIPTOR;
            }

            @Override // android.app.ICallNotificationEventCallback
            public void onCallNotificationPosted(String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallNotificationEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ICallNotificationEventCallback
            public void onCallNotificationRemoved(String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallNotificationEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
