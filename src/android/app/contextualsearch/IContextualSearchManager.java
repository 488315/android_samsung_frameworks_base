package android.app.contextualsearch;

import android.app.contextualsearch.IContextualSearchCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IContextualSearchManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.contextualsearch.IContextualSearchManager";

    public static class Default implements IContextualSearchManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.contextualsearch.IContextualSearchManager
        public void getContextualSearchState(IBinder iBinder, IContextualSearchCallback iContextualSearchCallback) throws RemoteException {
        }

        @Override // android.app.contextualsearch.IContextualSearchManager
        public void startContextualSearch(int i) throws RemoteException {
        }

        @Override // android.app.contextualsearch.IContextualSearchManager
        public void startContextualSearchForForegroundApp() throws RemoteException {
        }
    }

    void getContextualSearchState(IBinder iBinder, IContextualSearchCallback iContextualSearchCallback) throws RemoteException;

    void startContextualSearch(int i) throws RemoteException;

    void startContextualSearchForForegroundApp() throws RemoteException;

    public static abstract class Stub extends Binder implements IContextualSearchManager {
        static final int TRANSACTION_getContextualSearchState = 3;
        static final int TRANSACTION_startContextualSearch = 2;
        static final int TRANSACTION_startContextualSearchForForegroundApp = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IContextualSearchManager.DESCRIPTOR);
        }

        public static IContextualSearchManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContextualSearchManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextualSearchManager)) {
                return (IContextualSearchManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startContextualSearchForForegroundApp";
            }
            if (i == 2) {
                return "startContextualSearch";
            }
            if (i != 3) {
                return null;
            }
            return "getContextualSearchState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContextualSearchManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContextualSearchManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                startContextualSearchForForegroundApp();
                parcel2.writeNoException();
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                startContextualSearch(i3);
            } else if (i == 3) {
                IBinder strongBinder = parcel.readStrongBinder();
                IContextualSearchCallback iContextualSearchCallbackAsInterface = IContextualSearchCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getContextualSearchState(strongBinder, iContextualSearchCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContextualSearchManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextualSearchManager.DESCRIPTOR;
            }

            @Override // android.app.contextualsearch.IContextualSearchManager
            public void startContextualSearchForForegroundApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IContextualSearchManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.contextualsearch.IContextualSearchManager
            public void startContextualSearch(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContextualSearchManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.contextualsearch.IContextualSearchManager
            public void getContextualSearchState(IBinder iBinder, IContextualSearchCallback iContextualSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContextualSearchManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iContextualSearchCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
