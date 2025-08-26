package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDspHotwordDetectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IDspHotwordDetectionCallback";

    public static class Default implements IDspHotwordDetectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IDspHotwordDetectionCallback
        public void onDetected(HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
        }

        @Override // android.service.voice.IDspHotwordDetectionCallback
        public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
        }
    }

    void onDetected(HotwordDetectedResult hotwordDetectedResult) throws RemoteException;

    void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IDspHotwordDetectionCallback {
        static final int TRANSACTION_onDetected = 1;
        static final int TRANSACTION_onRejected = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDspHotwordDetectionCallback.DESCRIPTOR);
        }

        public static IDspHotwordDetectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDspHotwordDetectionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDspHotwordDetectionCallback)) {
                return (IDspHotwordDetectionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDetected";
            }
            if (i != 2) {
                return null;
            }
            return "onRejected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDspHotwordDetectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDspHotwordDetectionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                HotwordDetectedResult hotwordDetectedResult = (HotwordDetectedResult) parcel.readTypedObject(HotwordDetectedResult.CREATOR);
                parcel.enforceNoDataAvail();
                onDetected(hotwordDetectedResult);
            } else if (i == 2) {
                HotwordRejectedResult hotwordRejectedResult = (HotwordRejectedResult) parcel.readTypedObject(HotwordRejectedResult.CREATOR);
                parcel.enforceNoDataAvail();
                onRejected(hotwordRejectedResult);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDspHotwordDetectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDspHotwordDetectionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IDspHotwordDetectionCallback
            public void onDetected(HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDspHotwordDetectionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.IDspHotwordDetectionCallback
            public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDspHotwordDetectionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordRejectedResult, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
