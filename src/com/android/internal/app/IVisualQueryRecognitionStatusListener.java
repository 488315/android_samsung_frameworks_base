package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVisualQueryRecognitionStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVisualQueryRecognitionStatusListener";

    public static class Default implements IVisualQueryRecognitionStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStartPerceiving() throws RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
        public void onStopPerceiving() throws RemoteException {
        }
    }

    void onStartPerceiving() throws RemoteException;

    void onStopPerceiving() throws RemoteException;

    public static abstract class Stub extends Binder implements IVisualQueryRecognitionStatusListener {
        static final int TRANSACTION_onStartPerceiving = 1;
        static final int TRANSACTION_onStopPerceiving = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IVisualQueryRecognitionStatusListener.DESCRIPTOR);
        }

        public static IVisualQueryRecognitionStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVisualQueryRecognitionStatusListener)) {
                return (IVisualQueryRecognitionStatusListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStartPerceiving";
            }
            if (i != 2) {
                return null;
            }
            return "onStopPerceiving";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onStartPerceiving();
            } else if (i == 2) {
                onStopPerceiving();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVisualQueryRecognitionStatusListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryRecognitionStatusListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStartPerceiving() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryRecognitionStatusListener
            public void onStopPerceiving() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryRecognitionStatusListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
