package com.android.internal.view.inline;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.view.inline.IInlineContentCallback;

/* loaded from: classes4.dex */
public interface IInlineContentProvider extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentProvider";

    public static class Default implements IInlineContentProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void onSurfacePackageReleased() throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void provideContent(int i, int i2, IInlineContentCallback iInlineContentCallback) throws RemoteException {
        }

        @Override // com.android.internal.view.inline.IInlineContentProvider
        public void requestSurfacePackage() throws RemoteException {
        }
    }

    void onSurfacePackageReleased() throws RemoteException;

    void provideContent(int i, int i2, IInlineContentCallback iInlineContentCallback) throws RemoteException;

    void requestSurfacePackage() throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineContentProvider {
        static final int TRANSACTION_onSurfacePackageReleased = 3;
        static final int TRANSACTION_provideContent = 1;
        static final int TRANSACTION_requestSurfacePackage = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IInlineContentProvider.DESCRIPTOR);
        }

        public static IInlineContentProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInlineContentProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInlineContentProvider)) {
                return (IInlineContentProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "provideContent";
            }
            if (i == 2) {
                return "requestSurfacePackage";
            }
            if (i != 3) {
                return null;
            }
            return "onSurfacePackageReleased";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineContentProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineContentProvider.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                IInlineContentCallback asInterface = IInlineContentCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                provideContent(readInt, readInt2, asInterface);
            } else if (i == 2) {
                requestSurfacePackage();
            } else if (i == 3) {
                onSurfacePackageReleased();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInlineContentProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineContentProvider.DESCRIPTOR;
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void provideContent(int i, int i2, IInlineContentCallback iInlineContentCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInlineContentCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void requestSurfacePackage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.inline.IInlineContentProvider
            public void onSurfacePackageReleased() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
