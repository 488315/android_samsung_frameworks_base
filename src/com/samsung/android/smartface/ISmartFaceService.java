package com.samsung.android.smartface;

import android.graphics.rendererpolicy.ScpmApiContract;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.smartface.ISmartFaceClient;

/* loaded from: classes6.dex */
public interface ISmartFaceService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.smartface.ISmartFaceService";

    public static class Default implements ISmartFaceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public int getSupportedServices() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public boolean register(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public void registerAsync(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException {
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public void setValue(ISmartFaceClient iSmartFaceClient, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public void unregister(ISmartFaceClient iSmartFaceClient) throws RemoteException {
        }

        @Override // com.samsung.android.smartface.ISmartFaceService
        public void unregisterAsync(ISmartFaceClient iSmartFaceClient) throws RemoteException {
        }
    }

    int getSupportedServices() throws RemoteException;

    boolean register(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException;

    void registerAsync(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException;

    void setValue(ISmartFaceClient iSmartFaceClient, String str, String str2) throws RemoteException;

    void unregister(ISmartFaceClient iSmartFaceClient) throws RemoteException;

    void unregisterAsync(ISmartFaceClient iSmartFaceClient) throws RemoteException;

    public static abstract class Stub extends Binder implements ISmartFaceService {
        static final int TRANSACTION_getSupportedServices = 6;
        static final int TRANSACTION_register = 1;
        static final int TRANSACTION_registerAsync = 3;
        static final int TRANSACTION_setValue = 5;
        static final int TRANSACTION_unregister = 2;
        static final int TRANSACTION_unregisterAsync = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISmartFaceService.DESCRIPTOR);
        }

        public static ISmartFaceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISmartFaceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISmartFaceService)) {
                return (ISmartFaceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return ScpmApiContract.Method.REGISTER;
                case 2:
                    return ScpmApiContract.Method.UNREGISTER;
                case 3:
                    return "registerAsync";
                case 4:
                    return "unregisterAsync";
                case 5:
                    return "setValue";
                case 6:
                    return "getSupportedServices";
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
                parcel.enforceInterface(ISmartFaceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmartFaceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ISmartFaceClient iSmartFaceClientAsInterface = ISmartFaceClient.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRegister = register(iSmartFaceClientAsInterface, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegister);
                    return true;
                case 2:
                    ISmartFaceClient iSmartFaceClientAsInterface2 = ISmartFaceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregister(iSmartFaceClientAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISmartFaceClient iSmartFaceClientAsInterface3 = ISmartFaceClient.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerAsync(iSmartFaceClientAsInterface3, i4);
                    return true;
                case 4:
                    ISmartFaceClient iSmartFaceClientAsInterface4 = ISmartFaceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAsync(iSmartFaceClientAsInterface4);
                    return true;
                case 5:
                    ISmartFaceClient iSmartFaceClientAsInterface5 = ISmartFaceClient.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setValue(iSmartFaceClientAsInterface5, string, string2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int supportedServices = getSupportedServices();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedServices);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISmartFaceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartFaceService.DESCRIPTOR;
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public boolean register(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSmartFaceClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public void unregister(ISmartFaceClient iSmartFaceClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSmartFaceClient);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public void registerAsync(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSmartFaceClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public void unregisterAsync(ISmartFaceClient iSmartFaceClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSmartFaceClient);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public void setValue(ISmartFaceClient iSmartFaceClient, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSmartFaceClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.smartface.ISmartFaceService
            public int getSupportedServices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
