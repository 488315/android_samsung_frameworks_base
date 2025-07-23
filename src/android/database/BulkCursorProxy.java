package android.database;

import android.database.sqlite.Flags;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: BulkCursorNative.java */
/* loaded from: classes.dex */
final class BulkCursorProxy implements IBulkCursor {
    private Bundle mExtras = null;
    private IBinder mRemote;

    public BulkCursorProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.database.IBulkCursor
    public CursorWindow getWindow(int i) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            obtain.writeInt(i);
            this.mRemote.transact(1, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt() == 1 ? CursorWindow.newFromParcel(obtain2) : null;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void onMove(int i) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            obtain.writeInt(i);
            this.mRemote.transact(4, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void deactivate() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            this.mRemote.transact(2, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void close() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            int i = 1;
            if (!Flags.onewayFinalizerCloseFixed() || !"FinalizerDaemon".equals(Thread.currentThread().getName())) {
                i = 0;
            }
            this.mRemote.transact(7, obtain, obtain2, i);
            if (i == 0) {
                DatabaseUtils.readExceptionFromParcel(obtain2);
            }
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public int requery(IContentObserver iContentObserver) throws RemoteException {
        int i;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            obtain.writeStrongInterface(iContentObserver);
            boolean transact = this.mRemote.transact(3, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            if (transact) {
                int readInt = obtain2.readInt();
                this.mExtras = obtain2.readBundle();
                i = readInt;
            } else {
                i = -1;
            }
            return i;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public Bundle getExtras() throws RemoteException {
        if (this.mExtras == null) {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken(IBulkCursor.descriptor);
                this.mRemote.transact(5, obtain, obtain2, 0);
                DatabaseUtils.readExceptionFromParcel(obtain2);
                this.mExtras = obtain2.readBundle();
            } finally {
                obtain.recycle();
                obtain2.recycle();
            }
        }
        return this.mExtras;
    }

    @Override // android.database.IBulkCursor
    public Bundle respond(Bundle bundle) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IBulkCursor.descriptor);
            obtain.writeBundle(bundle);
            this.mRemote.transact(6, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readBundle();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
