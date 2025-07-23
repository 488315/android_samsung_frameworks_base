package com.samsung.android.provider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.provider.SemDynamicFeature;

/* loaded from: classes6.dex */
public interface IDynamicFeatureManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.provider.IDynamicFeatureManager";

    public static class Default implements IDynamicFeatureManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public SemDynamicFeature.Properties getProperties(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public String getVid() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public boolean sendAbTestResult(String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public int setEndpoint(int i) throws RemoteException {
            return 0;
        }
    }

    SemDynamicFeature.Properties getProperties(String str, String[] strArr) throws RemoteException;

    String getVid() throws RemoteException;

    boolean sendAbTestResult(String str, String str2, String str3) throws RemoteException;

    int setEndpoint(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDynamicFeatureManager {
        static final int TRANSACTION_getProperties = 1;
        static final int TRANSACTION_getVid = 4;
        static final int TRANSACTION_sendAbTestResult = 2;
        static final int TRANSACTION_setEndpoint = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDynamicFeatureManager.DESCRIPTOR);
        }

        public static IDynamicFeatureManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDynamicFeatureManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDynamicFeatureManager)) {
                return (IDynamicFeatureManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getProperties";
            }
            if (i == 2) {
                return "sendAbTestResult";
            }
            if (i == 3) {
                return "setEndpoint";
            }
            if (i != 4) {
                return null;
            }
            return "getVid";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDynamicFeatureManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDynamicFeatureManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                SemDynamicFeature.Properties properties = getProperties(readString, createStringArray);
                parcel2.writeNoException();
                parcel2.writeTypedObject(properties, 1);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean sendAbTestResult = sendAbTestResult(readString2, readString3, readString4);
                parcel2.writeNoException();
                parcel2.writeBoolean(sendAbTestResult);
            } else if (i == 3) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int endpoint = setEndpoint(readInt);
                parcel2.writeNoException();
                parcel2.writeInt(endpoint);
            } else if (i == 4) {
                String vid = getVid();
                parcel2.writeNoException();
                parcel2.writeString(vid);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDynamicFeatureManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDynamicFeatureManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public SemDynamicFeature.Properties getProperties(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemDynamicFeature.Properties) obtain2.readTypedObject(SemDynamicFeature.Properties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public boolean sendAbTestResult(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public int setEndpoint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public String getVid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
