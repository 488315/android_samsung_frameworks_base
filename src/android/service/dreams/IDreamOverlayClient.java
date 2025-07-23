package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlayCallback;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public interface IDreamOverlayClient extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayClient";

    public static class Default implements IDreamOverlayClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void comeToFront() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void endDream() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void onWakeRequested() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void startDream(WindowManager.LayoutParams layoutParams, IDreamOverlayCallback iDreamOverlayCallback, String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void wakeUp() throws RemoteException {
        }
    }

    void comeToFront() throws RemoteException;

    void endDream() throws RemoteException;

    void onWakeRequested() throws RemoteException;

    void startDream(WindowManager.LayoutParams layoutParams, IDreamOverlayCallback iDreamOverlayCallback, String str, boolean z, boolean z2) throws RemoteException;

    void wakeUp() throws RemoteException;

    public static abstract class Stub extends Binder implements IDreamOverlayClient {
        static final int TRANSACTION_comeToFront = 5;
        static final int TRANSACTION_endDream = 3;
        static final int TRANSACTION_onWakeRequested = 4;
        static final int TRANSACTION_startDream = 1;
        static final int TRANSACTION_wakeUp = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IDreamOverlayClient.DESCRIPTOR);
        }

        public static IDreamOverlayClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDreamOverlayClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDreamOverlayClient)) {
                return (IDreamOverlayClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startDream";
            }
            if (i == 2) {
                return "wakeUp";
            }
            if (i == 3) {
                return "endDream";
            }
            if (i == 4) {
                return "onWakeRequested";
            }
            if (i != 5) {
                return null;
            }
            return "comeToFront";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDreamOverlayClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDreamOverlayClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
                IDreamOverlayCallback asInterface = IDreamOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                startDream(layoutParams, asInterface, readString, readBoolean, readBoolean2);
                parcel2.writeNoException();
            } else if (i == 2) {
                wakeUp();
                parcel2.writeNoException();
            } else if (i == 3) {
                endDream();
                parcel2.writeNoException();
            } else if (i == 4) {
                onWakeRequested();
                parcel2.writeNoException();
            } else if (i == 5) {
                comeToFront();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDreamOverlayClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlayClient.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void startDream(WindowManager.LayoutParams layoutParams, IDreamOverlayCallback iDreamOverlayCallback, String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDreamOverlayClient.DESCRIPTOR);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeStrongInterface(iDreamOverlayCallback);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void wakeUp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void endDream() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void onWakeRequested() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void comeToFront() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
