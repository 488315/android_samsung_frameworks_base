package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface ICarrierPrivilegesCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.ICarrierPrivilegesCallback";

    void onCarrierPrivilegesChanged(List<String> list, int[] iArr) throws RemoteException;

    void onCarrierServiceChanged(String str, int i) throws RemoteException;

    public static class Default implements ICarrierPrivilegesCallback {
        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierPrivilegesChanged(
                List<String> privilegedPackageNames, int[] privilegedUids) throws RemoteException {}

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierServiceChanged(String carrierServicePackageName, int carrierServiceUid)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICarrierPrivilegesCallback {
        static final int TRANSACTION_onCarrierPrivilegesChanged = 1;
        static final int TRANSACTION_onCarrierServiceChanged = 2;

        public Stub() {
            attachInterface(this, ICarrierPrivilegesCallback.DESCRIPTOR);
        }

        public static ICarrierPrivilegesCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICarrierPrivilegesCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICarrierPrivilegesCallback)) {
                return (ICarrierPrivilegesCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCarrierPrivilegesChanged";
                case 2:
                    return "onCarrierServiceChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICarrierPrivilegesCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICarrierPrivilegesCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<String> _arg0 = data.createStringArrayList();
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    onCarrierPrivilegesChanged(_arg0, _arg1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onCarrierServiceChanged(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICarrierPrivilegesCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICarrierPrivilegesCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierPrivilegesChanged(
                    List<String> privilegedPackageNames, int[] privilegedUids)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
                    _data.writeStringList(privilegedPackageNames);
                    _data.writeIntArray(privilegedUids);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierServiceChanged(
                    String carrierServicePackageName, int carrierServiceUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
                    _data.writeString(carrierServicePackageName);
                    _data.writeInt(carrierServiceUid);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
