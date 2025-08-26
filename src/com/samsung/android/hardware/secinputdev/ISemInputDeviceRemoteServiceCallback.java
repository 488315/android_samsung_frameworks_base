package com.samsung.android.hardware.secinputdev;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemInputDeviceRemoteServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback";

    public static class Default implements ISemInputDeviceRemoteServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
        public void deliveryLastData(int[] iArr, float f) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
        public void deliveryRawdata(int[] iArr) throws RemoteException {
        }
    }

    void deliveryLastData(int[] iArr, float f) throws RemoteException;

    void deliveryRawdata(int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInputDeviceRemoteServiceCallback {
        static final int TRANSACTION_deliveryLastData = 2;
        static final int TRANSACTION_deliveryRawdata = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
        }

        public static ISemInputDeviceRemoteServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemInputDeviceRemoteServiceCallback)) {
                return (ISemInputDeviceRemoteServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "deliveryRawdata";
            }
            if (i != 2) {
                return null;
            }
            return "deliveryLastData";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                deliveryRawdata(iArrCreateIntArray);
            } else if (i == 2) {
                int[] iArrCreateIntArray2 = parcel.createIntArray();
                float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                deliveryLastData(iArrCreateIntArray2, f);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemInputDeviceRemoteServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInputDeviceRemoteServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
            public void deliveryRawdata(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
            public void deliveryLastData(int[] iArr, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
