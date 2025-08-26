package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IProxyService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.proxy.IProxyService";

    public static class Default implements IProxyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public void deregisterAgent(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException {
            return null;
        }
    }

    void deregisterAgent(String str) throws RemoteException;

    boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException;

    boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException;

    Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyService {
        static final int TRANSACTION_deregisterAgent = 3;
        static final int TRANSACTION_registerAgentByAction = 1;
        static final int TRANSACTION_registerAgentByMetadata = 2;
        static final int TRANSACTION_relay = 4;
        static final int TRANSACTION_relayAsync = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IProxyService.DESCRIPTOR);
        }

        public static IProxyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProxyService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProxyService)) {
                return (IProxyService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerAgentByAction";
            }
            if (i == 2) {
                return "registerAgentByMetadata";
            }
            if (i == 3) {
                return "deregisterAgent";
            }
            if (i == 4) {
                return "relay";
            }
            if (i != 5) {
                return null;
            }
            return "relayAsync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProxyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProxyService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zRegisterAgentByAction = registerAgentByAction(string, i3, string2, string3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRegisterAgentByAction);
            } else if (i == 2) {
                String string4 = parcel.readString();
                int i4 = parcel.readInt();
                String string5 = parcel.readString();
                String string6 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zRegisterAgentByMetadata = registerAgentByMetadata(string4, i4, string5, string6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRegisterAgentByMetadata);
            } else if (i == 3) {
                String string7 = parcel.readString();
                parcel.enforceNoDataAvail();
                deregisterAgent(string7);
                parcel2.writeNoException();
            } else if (i == 4) {
                String string8 = parcel.readString();
                String string9 = parcel.readString();
                String string10 = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle bundleRelay = relay(string8, string9, string10, bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(bundleRelay, 1);
            } else if (i == 5) {
                String string11 = parcel.readString();
                String string12 = parcel.readString();
                String string13 = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle bundleRelayAsync = relayAsync(string11, string12, string13, bundle2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(bundleRelayAsync, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProxyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public void deregisterAgent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
