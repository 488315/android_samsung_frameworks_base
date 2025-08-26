package com.android.internal.telephony;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICarrierConfigLoader extends IInterface {

    public static class Default implements ICarrierConfigLoader {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public PersistableBundle getConfigForSubId(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public PersistableBundle getConfigForSubIdWithFeature(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public PersistableBundle getConfigSubsetForSubIdWithFeature(int i, String str, String str2, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public String getDefaultCarrierServicePackageName() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void notifyConfigChangedForSubId(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void updateConfigForPhoneId(int i, String str) throws RemoteException {
        }
    }

    @Deprecated
    PersistableBundle getConfigForSubId(int i, String str) throws RemoteException;

    PersistableBundle getConfigForSubIdWithFeature(int i, String str, String str2) throws RemoteException;

    PersistableBundle getConfigSubsetForSubIdWithFeature(int i, String str, String str2, String[] strArr) throws RemoteException;

    String getDefaultCarrierServicePackageName() throws RemoteException;

    void notifyConfigChangedForSubId(int i) throws RemoteException;

    void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    void updateConfigForPhoneId(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierConfigLoader {
        public static final String DESCRIPTOR = "com.android.internal.telephony.ICarrierConfigLoader";
        static final int TRANSACTION_getConfigForSubId = 1;
        static final int TRANSACTION_getConfigForSubIdWithFeature = 2;
        static final int TRANSACTION_getConfigSubsetForSubIdWithFeature = 7;
        static final int TRANSACTION_getDefaultCarrierServicePackageName = 6;
        static final int TRANSACTION_notifyConfigChangedForSubId = 4;
        static final int TRANSACTION_overrideConfig = 3;
        static final int TRANSACTION_updateConfigForPhoneId = 5;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ICarrierConfigLoader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICarrierConfigLoader)) {
                return (ICarrierConfigLoader) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getConfigForSubId";
                case 2:
                    return "getConfigForSubIdWithFeature";
                case 3:
                    return "overrideConfig";
                case 4:
                    return "notifyConfigChangedForSubId";
                case 5:
                    return "updateConfigForPhoneId";
                case 6:
                    return "getDefaultCarrierServicePackageName";
                case 7:
                    return "getConfigSubsetForSubIdWithFeature";
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
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configForSubId = getConfigForSubId(i3, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubId, 1);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configForSubIdWithFeature = getConfigForSubIdWithFeature(i4, string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubIdWithFeature, 1);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    overrideConfig(i5, persistableBundle, z);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyConfigChangedForSubId(i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateConfigForPhoneId(i7, string4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String defaultCarrierServicePackageName = getDefaultCarrierServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(defaultCarrierServicePackageName);
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configSubsetForSubIdWithFeature = getConfigSubsetForSubIdWithFeature(i8, string5, string6, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configSubsetForSubIdWithFeature, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICarrierConfigLoader {
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

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public PersistableBundle getConfigForSubId(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public PersistableBundle getConfigForSubIdWithFeature(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void notifyConfigChangedForSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void updateConfigForPhoneId(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public String getDefaultCarrierServicePackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public PersistableBundle getConfigSubsetForSubIdWithFeature(int i, String str, String str2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void overrideConfig_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        protected void updateConfigForPhoneId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        protected void getDefaultCarrierServicePackageName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE, getCallingPid(), getCallingUid());
        }
    }
}
