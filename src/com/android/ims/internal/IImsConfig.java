package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.ImsConfigListener;

/* loaded from: classes5.dex */
public interface IImsConfig extends IInterface {

    public static class Default implements IImsConfig {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getFeatureValue(int i, int i2, ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsConfig
        public String getProvisionedStringValue(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsConfig
        public int getProvisionedValue(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsConfig
        public boolean getVolteProvisioned() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setFeatureValue(int i, int i2, int i3, ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsConfig
        public int setProvisionedStringValue(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsConfig
        public int setProvisionedValue(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setVideoQuality(int i, ImsConfigListener imsConfigListener) throws RemoteException {
        }
    }

    void getFeatureValue(int i, int i2, ImsConfigListener imsConfigListener) throws RemoteException;

    String getProvisionedStringValue(int i) throws RemoteException;

    int getProvisionedValue(int i) throws RemoteException;

    void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException;

    boolean getVolteProvisioned() throws RemoteException;

    void setFeatureValue(int i, int i2, int i3, ImsConfigListener imsConfigListener) throws RemoteException;

    int setProvisionedStringValue(int i, String str) throws RemoteException;

    int setProvisionedValue(int i, int i2) throws RemoteException;

    void setVideoQuality(int i, ImsConfigListener imsConfigListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsConfig {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsConfig";
        static final int TRANSACTION_getFeatureValue = 5;
        static final int TRANSACTION_getProvisionedStringValue = 2;
        static final int TRANSACTION_getProvisionedValue = 1;
        static final int TRANSACTION_getVideoQuality = 8;
        static final int TRANSACTION_getVolteProvisioned = 7;
        static final int TRANSACTION_setFeatureValue = 6;
        static final int TRANSACTION_setProvisionedStringValue = 4;
        static final int TRANSACTION_setProvisionedValue = 3;
        static final int TRANSACTION_setVideoQuality = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsConfig asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsConfig)) {
                return (IImsConfig) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getProvisionedValue";
                case 2:
                    return "getProvisionedStringValue";
                case 3:
                    return "setProvisionedValue";
                case 4:
                    return "setProvisionedStringValue";
                case 5:
                    return "getFeatureValue";
                case 6:
                    return "setFeatureValue";
                case 7:
                    return "getVolteProvisioned";
                case 8:
                    return "getVideoQuality";
                case 9:
                    return "setVideoQuality";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int provisionedValue = getProvisionedValue(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionedValue);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String provisionedStringValue = getProvisionedStringValue(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(provisionedStringValue);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int provisionedValue2 = setProvisionedValue(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionedValue2);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int provisionedStringValue2 = setProvisionedStringValue(readInt5, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionedStringValue2);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    ImsConfigListener asInterface = ImsConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFeatureValue(readInt6, readInt7, asInterface);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    ImsConfigListener asInterface2 = ImsConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setFeatureValue(readInt8, readInt9, readInt10, asInterface2);
                    return true;
                case 7:
                    boolean volteProvisioned = getVolteProvisioned();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volteProvisioned);
                    return true;
                case 8:
                    ImsConfigListener asInterface3 = ImsConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getVideoQuality(asInterface3);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    ImsConfigListener asInterface4 = ImsConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setVideoQuality(readInt11, asInterface4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsConfig {
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

            @Override // com.android.ims.internal.IImsConfig
            public int getProvisionedValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public String getProvisionedStringValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public int setProvisionedValue(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public int setProvisionedStringValue(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public void getFeatureValue(int i, int i2, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(imsConfigListener);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public void setFeatureValue(int i, int i2, int i3, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(imsConfigListener);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public boolean getVolteProvisioned() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(imsConfigListener);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsConfig
            public void setVideoQuality(int i, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(imsConfigListener);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
