package com.sec.ims.ss;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsUtEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ss.IImsUtEventListener";

    void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr) throws RemoteException;

    void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr) throws RemoteException;

    void onUtConfigurationCallWaitingQueried(int i, boolean z) throws RemoteException;

    void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationUpdated(int i) throws RemoteException;

    public abstract class Stub extends Binder implements IImsUtEventListener {
        static final int TRANSACTION_onUtConfigurationCallBarringQueried = 7;
        static final int TRANSACTION_onUtConfigurationCallForwardQueried = 6;
        static final int TRANSACTION_onUtConfigurationCallWaitingQueried = 5;
        static final int TRANSACTION_onUtConfigurationQueried = 3;
        static final int TRANSACTION_onUtConfigurationQueryFailed = 2;
        static final int TRANSACTION_onUtConfigurationUpdateFailed = 1;
        static final int TRANSACTION_onUtConfigurationUpdated = 4;

        class Proxy implements IImsUtEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsUtEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallWaitingQueried(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationUpdated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsUtEventListener.DESCRIPTOR);
        }

        public static IImsUtEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsUtEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsUtEventListener)) ? new Proxy(iBinder) : (IImsUtEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsUtEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsUtEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationUpdateFailed(i3, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationQueryFailed(i4, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationQueried(i5, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUtConfigurationUpdated(i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallWaitingQueried(i7, z);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallForwardQueried(i8, bundleArr);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    Bundle[] bundleArr2 = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallBarringQueried(i9, bundleArr2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IImsUtEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationUpdated(int i) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallWaitingQueried(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException {
        }
    }
}
