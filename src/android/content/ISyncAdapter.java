package android.content;

import android.accounts.Account;
import android.content.ISyncAdapterUnsyncableAccountCallback;
import android.content.ISyncContext;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISyncAdapter extends IInterface {

    public static class Default implements ISyncAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.ISyncAdapter
        public void cancelSync(ISyncContext iSyncContext) throws RemoteException {
        }

        @Override // android.content.ISyncAdapter
        public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws RemoteException {
        }

        @Override // android.content.ISyncAdapter
        public void startSync(ISyncContext iSyncContext, String str, Account account, Bundle bundle) throws RemoteException {
        }
    }

    void cancelSync(ISyncContext iSyncContext) throws RemoteException;

    void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws RemoteException;

    void startSync(ISyncContext iSyncContext, String str, Account account, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISyncAdapter {
        public static final String DESCRIPTOR = "android.content.ISyncAdapter";
        static final int TRANSACTION_cancelSync = 3;
        static final int TRANSACTION_onUnsyncableAccount = 1;
        static final int TRANSACTION_startSync = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISyncAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISyncAdapter)) {
                return (ISyncAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onUnsyncableAccount";
            }
            if (i == 2) {
                return "startSync";
            }
            if (i != 3) {
                return null;
            }
            return "cancelSync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISyncAdapterUnsyncableAccountCallback asInterface = ISyncAdapterUnsyncableAccountCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onUnsyncableAccount(asInterface);
            } else if (i == 2) {
                ISyncContext asInterface2 = ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                startSync(asInterface2, readString, account, bundle);
            } else if (i == 3) {
                ISyncContext asInterface3 = ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                cancelSync(asInterface3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISyncAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.ISyncAdapter
            public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncAdapterUnsyncableAccountCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.ISyncAdapter
            public void startSync(ISyncContext iSyncContext, String str, Account account, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    obtain.writeString(str);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.ISyncAdapter
            public void cancelSync(ISyncContext iSyncContext) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
