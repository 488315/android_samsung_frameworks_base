package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDragAndDropClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDragAndDropClient";

    public static class Default implements IDragAndDropClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public Rect getHiddenDropTargetArea() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public boolean getInitialDropTargetVisible() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onConnected(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onDisconnected() throws RemoteException {
        }
    }

    Rect getHiddenDropTargetArea() throws RemoteException;

    boolean getInitialDropTargetVisible() throws RemoteException;

    void onConnected(IBinder iBinder, int i) throws RemoteException;

    void onDisconnected() throws RemoteException;

    public static abstract class Stub extends Binder implements IDragAndDropClient {
        static final int TRANSACTION_getHiddenDropTargetArea = 4;
        static final int TRANSACTION_getInitialDropTargetVisible = 3;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDragAndDropClient.DESCRIPTOR);
        }

        public static IDragAndDropClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDragAndDropClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDragAndDropClient)) {
                return (IDragAndDropClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnected";
            }
            if (i == 2) {
                return "onDisconnected";
            }
            if (i == 3) {
                return "getInitialDropTargetVisible";
            }
            if (i != 4) {
                return null;
            }
            return "getHiddenDropTargetArea";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDragAndDropClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDragAndDropClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onConnected(strongBinder, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                onDisconnected();
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean initialDropTargetVisible = getInitialDropTargetVisible();
                parcel2.writeNoException();
                parcel2.writeBoolean(initialDropTargetVisible);
            } else if (i == 4) {
                Rect hiddenDropTargetArea = getHiddenDropTargetArea();
                parcel2.writeNoException();
                parcel2.writeTypedObject(hiddenDropTargetArea, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDragAndDropClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDragAndDropClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onConnected(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public boolean getInitialDropTargetVisible() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public Rect getHiddenDropTargetArea() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
