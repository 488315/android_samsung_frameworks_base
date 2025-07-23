package com.android.internal.view.inline;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControlViewHost;

/* loaded from: classes4.dex */
public interface IInlineContentCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentCallback";

    public static class Default implements IInlineContentCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onClick() throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onContent(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onLongClick() throws RemoteException {
        }
    }

    void onClick() throws RemoteException;

    void onContent(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException;

    void onLongClick() throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineContentCallback {
        static final int TRANSACTION_onClick = 2;
        static final int TRANSACTION_onContent = 1;
        static final int TRANSACTION_onLongClick = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IInlineContentCallback.DESCRIPTOR);
        }

        public static IInlineContentCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInlineContentCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInlineContentCallback)) {
                return (IInlineContentCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onContent";
            }
            if (i == 2) {
                return "onClick";
            }
            if (i != 3) {
                return null;
            }
            return "onLongClick";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineContentCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineContentCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SurfaceControlViewHost.SurfacePackage surfacePackage = (SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onContent(surfacePackage, readInt, readInt2);
            } else if (i == 2) {
                onClick();
            } else if (i == 3) {
                onLongClick();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInlineContentCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineContentCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onContent(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfacePackage, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onClick() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentCallback
            public void onLongClick() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
