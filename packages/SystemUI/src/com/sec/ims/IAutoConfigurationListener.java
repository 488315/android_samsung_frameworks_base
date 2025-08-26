package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAutoConfigurationListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IAutoConfigurationListener";

    void onAutoConfigurationCompleted(boolean z) throws RemoteException;

    void onIidTokenNeeded() throws RemoteException;

    void onMsisdnNumberNeeded() throws RemoteException;

    void onVerificationCodeNeeded() throws RemoteException;

    public class Default implements IAutoConfigurationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onIidTokenNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onMsisdnNumberNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onVerificationCodeNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onAutoConfigurationCompleted(boolean z) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IAutoConfigurationListener {
        static final int TRANSACTION_onAutoConfigurationCompleted = 4;
        static final int TRANSACTION_onIidTokenNeeded = 3;
        static final int TRANSACTION_onMsisdnNumberNeeded = 2;
        static final int TRANSACTION_onVerificationCodeNeeded = 1;

        class Proxy implements IAutoConfigurationListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAutoConfigurationListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onAutoConfigurationCompleted(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onIidTokenNeeded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onMsisdnNumberNeeded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onVerificationCodeNeeded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAutoConfigurationListener.DESCRIPTOR);
        }

        public static IAutoConfigurationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAutoConfigurationListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAutoConfigurationListener)) ? new Proxy(iBinder) : (IAutoConfigurationListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAutoConfigurationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAutoConfigurationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onVerificationCodeNeeded();
                parcel2.writeNoException();
            } else if (i == 2) {
                onMsisdnNumberNeeded();
                parcel2.writeNoException();
            } else if (i == 3) {
                onIidTokenNeeded();
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAutoConfigurationCompleted(z);
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
