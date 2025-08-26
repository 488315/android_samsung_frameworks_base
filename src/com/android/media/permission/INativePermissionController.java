package com.android.media.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface INativePermissionController extends IInterface {
    public static final String DESCRIPTOR = "com.android.media.permission.INativePermissionController";

    public static class Default implements INativePermissionController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.media.permission.INativePermissionController
        public void populatePackagesForUids(List<UidPackageState> list) throws RemoteException {
        }

        @Override // com.android.media.permission.INativePermissionController
        public void populatePermissionState(byte b, int[] iArr) throws RemoteException {
        }

        @Override // com.android.media.permission.INativePermissionController
        public void updatePackagesForUid(UidPackageState uidPackageState) throws RemoteException {
        }
    }

    void populatePackagesForUids(List<UidPackageState> list) throws RemoteException;

    void populatePermissionState(byte b, int[] iArr) throws RemoteException;

    void updatePackagesForUid(UidPackageState uidPackageState) throws RemoteException;

    public static abstract class Stub extends Binder implements INativePermissionController {
        static final int TRANSACTION_populatePackagesForUids = 1;
        static final int TRANSACTION_populatePermissionState = 3;
        static final int TRANSACTION_updatePackagesForUid = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, INativePermissionController.DESCRIPTOR);
        }

        public static INativePermissionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INativePermissionController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INativePermissionController)) {
                return (INativePermissionController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INativePermissionController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INativePermissionController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(UidPackageState.CREATOR);
                parcel.enforceNoDataAvail();
                populatePackagesForUids(arrayListCreateTypedArrayList);
                parcel2.writeNoException();
            } else if (i == 2) {
                UidPackageState uidPackageState = (UidPackageState) parcel.readTypedObject(UidPackageState.CREATOR);
                parcel.enforceNoDataAvail();
                updatePackagesForUid(uidPackageState);
                parcel2.writeNoException();
            } else if (i == 3) {
                byte b = parcel.readByte();
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                populatePermissionState(b, iArrCreateIntArray);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INativePermissionController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INativePermissionController.DESCRIPTOR;
            }

            @Override // com.android.media.permission.INativePermissionController
            public void populatePackagesForUids(List<UidPackageState> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.media.permission.INativePermissionController
            public void updatePackagesForUid(UidPackageState uidPackageState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uidPackageState, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.media.permission.INativePermissionController
            public void populatePermissionState(byte b, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INativePermissionController.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
