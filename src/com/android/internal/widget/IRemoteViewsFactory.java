package com.android.internal.widget;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes6.dex */
public interface IRemoteViewsFactory extends IInterface {

    public static class Default implements IRemoteViewsFactory {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public int getCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public long getItemId(int i) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews getLoadingView() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews getViewAt(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public int getViewTypeCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public boolean hasStableIds() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public boolean isCreated() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDataSetChanged() throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDataSetChangedAsync() throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDestroy(Intent intent) throws RemoteException {
        }
    }

    int getCount() throws RemoteException;

    long getItemId(int i) throws RemoteException;

    RemoteViews getLoadingView() throws RemoteException;

    RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i, int i2) throws RemoteException;

    RemoteViews getViewAt(int i) throws RemoteException;

    int getViewTypeCount() throws RemoteException;

    boolean hasStableIds() throws RemoteException;

    boolean isCreated() throws RemoteException;

    void onDataSetChanged() throws RemoteException;

    void onDataSetChangedAsync() throws RemoteException;

    void onDestroy(Intent intent) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteViewsFactory {
        public static final String DESCRIPTOR = "com.android.internal.widget.IRemoteViewsFactory";
        static final int TRANSACTION_getCount = 4;
        static final int TRANSACTION_getItemId = 8;
        static final int TRANSACTION_getLoadingView = 6;
        static final int TRANSACTION_getRemoteCollectionItems = 11;
        static final int TRANSACTION_getViewAt = 5;
        static final int TRANSACTION_getViewTypeCount = 7;
        static final int TRANSACTION_hasStableIds = 9;
        static final int TRANSACTION_isCreated = 10;
        static final int TRANSACTION_onDataSetChanged = 1;
        static final int TRANSACTION_onDataSetChangedAsync = 2;
        static final int TRANSACTION_onDestroy = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteViewsFactory asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteViewsFactory)) {
                return (IRemoteViewsFactory) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDataSetChanged";
                case 2:
                    return "onDataSetChangedAsync";
                case 3:
                    return "onDestroy";
                case 4:
                    return "getCount";
                case 5:
                    return "getViewAt";
                case 6:
                    return "getLoadingView";
                case 7:
                    return "getViewTypeCount";
                case 8:
                    return "getItemId";
                case 9:
                    return "hasStableIds";
                case 10:
                    return "isCreated";
                case 11:
                    return "getRemoteCollectionItems";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    onDataSetChanged();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onDataSetChangedAsync();
                    return true;
                case 3:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroy(intent);
                    return true;
                case 4:
                    int count = getCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(count);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews viewAt = getViewAt(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(viewAt, 1);
                    return true;
                case 6:
                    RemoteViews loadingView = getLoadingView();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(loadingView, 1);
                    return true;
                case 7:
                    int viewTypeCount = getViewTypeCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(viewTypeCount);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long itemId = getItemId(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeLong(itemId);
                    return true;
                case 9:
                    boolean hasStableIds = hasStableIds();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasStableIds);
                    return true;
                case 10:
                    boolean isCreated = isCreated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCreated);
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews.RemoteCollectionItems remoteCollectionItems = getRemoteCollectionItems(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteCollectionItems, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteViewsFactory {
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

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDataSetChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDataSetChangedAsync() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public void onDestroy(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public int getCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews getViewAt(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteViews) obtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews getLoadingView() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteViews) obtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public int getViewTypeCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public long getItemId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public boolean hasStableIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public boolean isCreated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteViewsFactory
            public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteViews.RemoteCollectionItems) obtain2.readTypedObject(RemoteViews.RemoteCollectionItems.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
