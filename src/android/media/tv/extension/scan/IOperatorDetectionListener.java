package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOperatorDetectionListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IOperatorDetectionListener";

    public static class Default implements IOperatorDetectionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IOperatorDetectionListener
        public void onDetectOperatorDetectionList(Bundle[] bundleArr) throws RemoteException {
        }
    }

    void onDetectOperatorDetectionList(Bundle[] bundleArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IOperatorDetectionListener {
        static final int TRANSACTION_onDetectOperatorDetectionList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IOperatorDetectionListener");
        }

        public static IOperatorDetectionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IOperatorDetectionListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOperatorDetectionListener)) {
                return (IOperatorDetectionListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDetectOperatorDetectionList";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IOperatorDetectionListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IOperatorDetectionListener");
                return true;
            }
            if (i == 1) {
                Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onDetectOperatorDetectionList(bundleArr);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOperatorDetectionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IOperatorDetectionListener";
            }

            @Override // android.media.tv.extension.scan.IOperatorDetectionListener
            public void onDetectOperatorDetectionList(Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IOperatorDetectionListener");
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
