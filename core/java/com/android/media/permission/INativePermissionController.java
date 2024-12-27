package com.android.media.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface INativePermissionController extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.media.permission.INativePermissionController";

    void populatePackagesForUids(List<UidPackageState> list) throws RemoteException;

    void populatePermissionState(byte b, int[] iArr) throws RemoteException;

    void updatePackagesForUid(UidPackageState uidPackageState) throws RemoteException;

    public static class Default implements INativePermissionController {
        @Override // com.android.media.permission.INativePermissionController
        public void populatePackagesForUids(List<UidPackageState> initialPackageStates)
                throws RemoteException {}

        @Override // com.android.media.permission.INativePermissionController
        public void updatePackagesForUid(UidPackageState newPackageState) throws RemoteException {}

        @Override // com.android.media.permission.INativePermissionController
        public void populatePermissionState(byte perm, int[] uids) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements INativePermissionController {
        static final int TRANSACTION_populatePackagesForUids = 1;
        static final int TRANSACTION_populatePermissionState = 3;
        static final int TRANSACTION_updatePackagesForUid = 2;

        public Stub() {
            attachInterface(this, INativePermissionController.DESCRIPTOR);
        }

        public static INativePermissionController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INativePermissionController.DESCRIPTOR);
            if (iin != null && (iin instanceof INativePermissionController)) {
                return (INativePermissionController) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(INativePermissionController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(INativePermissionController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<UidPackageState> _arg0 =
                            data.createTypedArrayList(UidPackageState.CREATOR);
                    data.enforceNoDataAvail();
                    populatePackagesForUids(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    UidPackageState _arg02 =
                            (UidPackageState) data.readTypedObject(UidPackageState.CREATOR);
                    data.enforceNoDataAvail();
                    updatePackagesForUid(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    byte _arg03 = data.readByte();
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    populatePermissionState(_arg03, _arg1);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INativePermissionController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INativePermissionController.DESCRIPTOR;
            }

            @Override // com.android.media.permission.INativePermissionController
            public void populatePackagesForUids(List<UidPackageState> initialPackageStates)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    _data.writeTypedList(initialPackageStates, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.media.permission.INativePermissionController
            public void updatePackagesForUid(UidPackageState newPackageState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    _data.writeTypedObject(newPackageState, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.media.permission.INativePermissionController
            public void populatePermissionState(byte perm, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    _data.writeByte(perm);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
