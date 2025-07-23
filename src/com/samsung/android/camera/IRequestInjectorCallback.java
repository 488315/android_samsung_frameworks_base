package com.samsung.android.camera;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRequestInjectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.IRequestInjectorCallback";

    public static class Default implements IRequestInjectorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.IRequestInjectorCallback
        public void onCaptureResult(CameraMetadataNative cameraMetadataNative, String str, String str2, int i, long j) throws RemoteException {
        }
    }

    void onCaptureResult(CameraMetadataNative cameraMetadataNative, String str, String str2, int i, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestInjectorCallback {
        static final int TRANSACTION_onCaptureResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRequestInjectorCallback.DESCRIPTOR);
        }

        public static IRequestInjectorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRequestInjectorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRequestInjectorCallback)) {
                return (IRequestInjectorCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCaptureResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRequestInjectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestInjectorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onCaptureResult(cameraMetadataNative, readString, readString2, readInt, readLong);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRequestInjectorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestInjectorCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.IRequestInjectorCallback
            public void onCaptureResult(CameraMetadataNative cameraMetadataNative, String str, String str2, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRequestInjectorCallback.DESCRIPTOR);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
