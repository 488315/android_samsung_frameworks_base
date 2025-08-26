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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDynamicFeatureManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDynamicFeatureManager)) {
                return (IDynamicFeatureManager) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                String[] strArrCreateStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                SemDynamicFeature.Properties properties = getProperties(string, strArrCreateStringArray);
                parcel2.writeNoException();
                parcel2.writeTypedObject(properties, 1);
            } else if (i == 2) {
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zSendAbTestResult = sendAbTestResult(string2, string3, string4);
                parcel2.writeNoException();
                parcel2.writeBoolean(zSendAbTestResult);
            } else if (i == 3) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int endpoint = setEndpoint(i3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemDynamicFeature.Properties) parcelObtain2.readTypedObject(SemDynamicFeature.Properties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public boolean sendAbTestResult(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
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

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public int setEndpoint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public String getVid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
