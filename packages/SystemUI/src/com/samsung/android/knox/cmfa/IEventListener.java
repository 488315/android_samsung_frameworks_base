package com.samsung.android.knox.cmfa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.IEventListener";

    void onFail(String str) throws RemoteException;

    void onStateUpdate(boolean z, String str) throws RemoteException;

    void onSuccess() throws RemoteException;

    public class Default implements IEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public void onSuccess() throws RemoteException {
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public void onFail(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public void onStateUpdate(boolean z, String str) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IEventListener {
        public static final int TRANSACTION_onFail = 2;
        public static final int TRANSACTION_onStateUpdate = 3;
        public static final int TRANSACTION_onSuccess = 1;

        class Proxy implements IEventListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public void onFail(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public void onStateUpdate(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public void onSuccess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEventListener.DESCRIPTOR);
        }

        public static IEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEventListener)) ? new Proxy(iBinder) : (IEventListener) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i == 2) {
                return "onFail";
            }
            if (i != 3) {
                return null;
            }
            return "onStateUpdate";
        }

        public int getMaxTransactionId() {
            return 2;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
                parcel2.writeNoException();
            } else if (i == 2) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onFail(string);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean z = parcel.readBoolean();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onStateUpdate(z, string2);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
