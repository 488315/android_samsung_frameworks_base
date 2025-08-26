package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICameraServiceProxy extends IInterface {

    public static class Default implements ICameraServiceProxy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ICameraServiceProxy
        public int getAutoframingOverride(String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraServiceProxy
        public int getRotateAndCropOverride(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraServiceProxy
        public boolean isCameraDisabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraServiceProxy
        public void notifyCameraState(CameraSessionStats cameraSessionStats) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceProxy
        public void notifyFeatureCombinationStats(CameraFeatureCombinationStats cameraFeatureCombinationStats) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceProxy
        public void notifyWatchdog(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceProxy
        public void pingForUserUpdate() throws RemoteException {
        }
    }

    int getAutoframingOverride(String str) throws RemoteException;

    int getRotateAndCropOverride(String str, int i, int i2) throws RemoteException;

    boolean isCameraDisabled(int i) throws RemoteException;

    void notifyCameraState(CameraSessionStats cameraSessionStats) throws RemoteException;

    void notifyFeatureCombinationStats(CameraFeatureCombinationStats cameraFeatureCombinationStats) throws RemoteException;

    void notifyWatchdog(int i, boolean z) throws RemoteException;

    void pingForUserUpdate() throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraServiceProxy {
        public static final String DESCRIPTOR = "android.hardware.ICameraServiceProxy";
        static final int TRANSACTION_getAutoframingOverride = 5;
        static final int TRANSACTION_getRotateAndCropOverride = 4;
        static final int TRANSACTION_isCameraDisabled = 6;
        static final int TRANSACTION_notifyCameraState = 2;
        static final int TRANSACTION_notifyFeatureCombinationStats = 3;
        static final int TRANSACTION_notifyWatchdog = 7;
        static final int TRANSACTION_pingForUserUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraServiceProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICameraServiceProxy)) {
                return (ICameraServiceProxy) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pingForUserUpdate";
                case 2:
                    return "notifyCameraState";
                case 3:
                    return "notifyFeatureCombinationStats";
                case 4:
                    return "getRotateAndCropOverride";
                case 5:
                    return "getAutoframingOverride";
                case 6:
                    return "isCameraDisabled";
                case 7:
                    return "notifyWatchdog";
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
                    pingForUserUpdate();
                    return true;
                case 2:
                    CameraSessionStats cameraSessionStats = (CameraSessionStats) parcel.readTypedObject(CameraSessionStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCameraState(cameraSessionStats);
                    return true;
                case 3:
                    CameraFeatureCombinationStats cameraFeatureCombinationStats = (CameraFeatureCombinationStats) parcel.readTypedObject(CameraFeatureCombinationStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFeatureCombinationStats(cameraFeatureCombinationStats);
                    return true;
                case 4:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rotateAndCropOverride = getRotateAndCropOverride(string, i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(rotateAndCropOverride);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoframingOverride = getAutoframingOverride(string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoframingOverride);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCameraDisabled = isCameraDisabled(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraDisabled);
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyWatchdog(i6, z);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraServiceProxy {
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

            @Override // android.hardware.ICameraServiceProxy
            public void pingForUserUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public void notifyCameraState(CameraSessionStats cameraSessionStats) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cameraSessionStats, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public void notifyFeatureCombinationStats(CameraFeatureCombinationStats cameraFeatureCombinationStats) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cameraFeatureCombinationStats, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public int getRotateAndCropOverride(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public int getAutoframingOverride(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public boolean isCameraDisabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public void notifyWatchdog(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
