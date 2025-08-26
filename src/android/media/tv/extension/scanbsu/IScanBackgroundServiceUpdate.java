package android.media.tv.extension.scanbsu;

import android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanBackgroundServiceUpdate extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate";

    public static class Default implements IScanBackgroundServiceUpdate {
        @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate
        public void addBackgroundServiceUpdateListener(String str, IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate
        public void removeBackgroundServiceUpdateListener(IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException {
        }
    }

    void addBackgroundServiceUpdateListener(String str, IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException;

    void removeBackgroundServiceUpdateListener(IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IScanBackgroundServiceUpdate {
        static final int TRANSACTION_addBackgroundServiceUpdateListener = 1;
        static final int TRANSACTION_removeBackgroundServiceUpdateListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
        }

        public static IScanBackgroundServiceUpdate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IScanBackgroundServiceUpdate)) {
                return (IScanBackgroundServiceUpdate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addBackgroundServiceUpdateListener";
            }
            if (i != 2) {
                return null;
            }
            return "removeBackgroundServiceUpdateListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListenerAsInterface = IScanBackgroundServiceUpdateListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addBackgroundServiceUpdateListener(string, iScanBackgroundServiceUpdateListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListenerAsInterface2 = IScanBackgroundServiceUpdateListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeBackgroundServiceUpdateListener(iScanBackgroundServiceUpdateListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScanBackgroundServiceUpdate {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate";
            }

            @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate
            public void addBackgroundServiceUpdateListener(String str, IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iScanBackgroundServiceUpdateListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate
            public void removeBackgroundServiceUpdateListener(IScanBackgroundServiceUpdateListener iScanBackgroundServiceUpdateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate");
                    parcelObtain.writeStrongInterface(iScanBackgroundServiceUpdateListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
