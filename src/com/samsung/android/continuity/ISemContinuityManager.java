package com.samsung.android.continuity;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.continuity.ISemContinuitySimpleListener;

/* loaded from: classes6.dex */
public interface ISemContinuityManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.continuity.ISemContinuityManager";

    public static class Default implements ISemContinuityManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void cancelDownload(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void clearLocalClip(int i) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public int getNearbyDeviceCount(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void setLocalClip(Bundle bundle, int i) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void unregisterContinuityCopyListener(int i) throws RemoteException {
        }
    }

    void cancelDownload(String str, int i) throws RemoteException;

    void clearLocalClip(int i) throws RemoteException;

    int getNearbyDeviceCount(int i, int i2) throws RemoteException;

    void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException;

    boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException;

    void setLocalClip(Bundle bundle, int i) throws RemoteException;

    void unregisterContinuityCopyListener(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemContinuityManager {
        static final int TRANSACTION_cancelDownload = 7;
        static final int TRANSACTION_clearLocalClip = 3;
        static final int TRANSACTION_getNearbyDeviceCount = 1;
        static final int TRANSACTION_registerContinuityCopyListener = 4;
        static final int TRANSACTION_requestDownload = 6;
        static final int TRANSACTION_setLocalClip = 2;
        static final int TRANSACTION_unregisterContinuityCopyListener = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ISemContinuityManager.DESCRIPTOR);
        }

        public static ISemContinuityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemContinuityManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemContinuityManager)) {
                return (ISemContinuityManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getNearbyDeviceCount";
                case 2:
                    return "setLocalClip";
                case 3:
                    return "clearLocalClip";
                case 4:
                    return "registerContinuityCopyListener";
                case 5:
                    return "unregisterContinuityCopyListener";
                case 6:
                    return "requestDownload";
                case 7:
                    return "cancelDownload";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemContinuityManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemContinuityManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyDeviceCount = getNearbyDeviceCount(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyDeviceCount);
                    return true;
                case 2:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLocalClip(bundle, i5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearLocalClip(i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ISemContinuitySimpleListener iSemContinuitySimpleListenerAsInterface = ISemContinuitySimpleListener.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerContinuityCopyListener(iSemContinuitySimpleListenerAsInterface, i7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterContinuityCopyListener(i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string = parcel.readString();
                    ISemContinuitySimpleListener iSemContinuitySimpleListenerAsInterface2 = ISemContinuitySimpleListener.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestDownload = requestDownload(string, iSemContinuitySimpleListenerAsInterface2, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestDownload);
                    return true;
                case 7:
                    String string2 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelDownload(string2, i10);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemContinuityManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContinuityManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public int getNearbyDeviceCount(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void setLocalClip(Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void clearLocalClip(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemContinuitySimpleListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void unregisterContinuityCopyListener(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSemContinuitySimpleListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void cancelDownload(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
