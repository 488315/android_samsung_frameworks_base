package com.android.internal.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVoiceInteractionSessionListener extends IInterface {

    public static class Default implements IVoiceInteractionSessionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onSetUiHints(Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onVoiceSessionHidden() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onVoiceSessionShown() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onVoiceSessionWindowVisibilityChanged(boolean z) throws RemoteException {
        }
    }

    void onSetUiHints(Bundle bundle) throws RemoteException;

    void onVoiceSessionHidden() throws RemoteException;

    void onVoiceSessionShown() throws RemoteException;

    void onVoiceSessionWindowVisibilityChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionSessionListener {
        public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSessionListener";
        static final int TRANSACTION_onSetUiHints = 4;
        static final int TRANSACTION_onVoiceSessionHidden = 2;
        static final int TRANSACTION_onVoiceSessionShown = 1;
        static final int TRANSACTION_onVoiceSessionWindowVisibilityChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionSessionListener)) {
                return (IVoiceInteractionSessionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onVoiceSessionShown";
            }
            if (i == 2) {
                return "onVoiceSessionHidden";
            }
            if (i == 3) {
                return "onVoiceSessionWindowVisibilityChanged";
            }
            if (i != 4) {
                return null;
            }
            return "onSetUiHints";
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
                onVoiceSessionShown();
            } else if (i == 2) {
                onVoiceSessionHidden();
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onVoiceSessionWindowVisibilityChanged(readBoolean);
            } else if (i == 4) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onSetUiHints(bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVoiceInteractionSessionListener {
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

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onVoiceSessionShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onVoiceSessionHidden() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onVoiceSessionWindowVisibilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onSetUiHints(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
