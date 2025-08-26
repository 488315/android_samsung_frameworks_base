package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IKeyguardStateCallback extends IInterface {

    public static class Default implements IKeyguardStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.policy.IKeyguardStateCallback
        public void onInputRestrictedStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardStateCallback
        public void onShowingStateChanged(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardStateCallback
        public void onSimSecureStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardStateCallback
        public void onTrustedChanged(boolean z) throws RemoteException {
        }
    }

    void onInputRestrictedStateChanged(boolean z) throws RemoteException;

    void onShowingStateChanged(boolean z, int i) throws RemoteException;

    void onSimSecureStateChanged(boolean z) throws RemoteException;

    void onTrustedChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyguardStateCallback {
        public static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardStateCallback";
        static final int TRANSACTION_onInputRestrictedStateChanged = 3;
        static final int TRANSACTION_onShowingStateChanged = 1;
        static final int TRANSACTION_onSimSecureStateChanged = 2;
        static final int TRANSACTION_onTrustedChanged = 4;

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

        public static IKeyguardStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyguardStateCallback)) {
                return (IKeyguardStateCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onShowingStateChanged";
            }
            if (i == 2) {
                return "onSimSecureStateChanged";
            }
            if (i == 3) {
                return "onInputRestrictedStateChanged";
            }
            if (i != 4) {
                return null;
            }
            return "onTrustedChanged";
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
                boolean z = parcel.readBoolean();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onShowingStateChanged(z, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSimSecureStateChanged(z2);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onInputRestrictedStateChanged(z3);
                parcel2.writeNoException();
            } else if (i == 4) {
                boolean z4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTrustedChanged(z4);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IKeyguardStateCallback {
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

            @Override // com.android.internal.policy.IKeyguardStateCallback
            public void onShowingStateChanged(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardStateCallback
            public void onSimSecureStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardStateCallback
            public void onInputRestrictedStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardStateCallback
            public void onTrustedChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
