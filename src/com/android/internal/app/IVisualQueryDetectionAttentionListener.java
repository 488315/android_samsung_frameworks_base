package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.voice.VisualQueryAttentionResult;

/* loaded from: classes5.dex */
public interface IVisualQueryDetectionAttentionListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVisualQueryDetectionAttentionListener";

    public static class Default implements IVisualQueryDetectionAttentionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionLost(int i) throws RemoteException {
        }
    }

    void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException;

    void onAttentionLost(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVisualQueryDetectionAttentionListener {
        static final int TRANSACTION_onAttentionGained = 1;
        static final int TRANSACTION_onAttentionLost = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IVisualQueryDetectionAttentionListener.DESCRIPTOR);
        }

        public static IVisualQueryDetectionAttentionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVisualQueryDetectionAttentionListener)) {
                return (IVisualQueryDetectionAttentionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAttentionGained";
            }
            if (i != 2) {
                return null;
            }
            return "onAttentionLost";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                VisualQueryAttentionResult visualQueryAttentionResult = (VisualQueryAttentionResult) parcel.readTypedObject(VisualQueryAttentionResult.CREATOR);
                parcel.enforceNoDataAvail();
                onAttentionGained(visualQueryAttentionResult);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAttentionLost(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVisualQueryDetectionAttentionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryDetectionAttentionListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryAttentionResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionLost(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
