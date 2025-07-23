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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierConfigLoader)) {
                return (ICarrierConfigLoader) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configForSubId = getConfigForSubId(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubId, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configForSubIdWithFeature = getConfigForSubIdWithFeature(readInt2, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubIdWithFeature, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    overrideConfig(readInt3, persistableBundle, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyConfigChangedForSubId(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateConfigForPhoneId(readInt5, readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String defaultCarrierServicePackageName = getDefaultCarrierServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(defaultCarrierServicePackageName);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    PersistableBundle configSubsetForSubIdWithFeature = getConfigSubsetForSubIdWithFeature(readInt6, readString5, readString6, createStringArray);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public PersistableBundle getConfigForSubIdWithFeature(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void notifyConfigChangedForSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void updateConfigForPhoneId(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public String getDefaultCarrierServicePackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public PersistableBundle getConfigSubsetForSubIdWithFeature(int i, String str, String str2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
