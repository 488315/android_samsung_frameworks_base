package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamHostControlTuneQuietlyFlag extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag";

    public static class Default implements ICamHostControlTuneQuietlyFlag {
        @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
        public void addHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
        public Bundle getHcTuneQuietlyFlag(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
        public void removeHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException {
        }
    }

    void addHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException;

    Bundle getHcTuneQuietlyFlag(String str) throws RemoteException;

    void removeHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamHostControlTuneQuietlyFlag {
        static final int TRANSACTION_addHcTuneQuietlyFlagListener = 1;
        static final int TRANSACTION_getHcTuneQuietlyFlag = 3;
        static final int TRANSACTION_removeHcTuneQuietlyFlagListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
        }

        public static ICamHostControlTuneQuietlyFlag asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamHostControlTuneQuietlyFlag)) {
                return (ICamHostControlTuneQuietlyFlag) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addHcTuneQuietlyFlagListener";
            }
            if (i == 2) {
                return "removeHcTuneQuietlyFlagListener";
            }
            if (i != 3) {
                return null;
            }
            return "getHcTuneQuietlyFlag";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
                return true;
            }
            if (i == 1) {
                ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListenerAsInterface = ICamHostControlTuneQuietlyFlagListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addHcTuneQuietlyFlagListener(iCamHostControlTuneQuietlyFlagListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListenerAsInterface2 = ICamHostControlTuneQuietlyFlagListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeHcTuneQuietlyFlagListener(iCamHostControlTuneQuietlyFlagListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle hcTuneQuietlyFlag = getHcTuneQuietlyFlag(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(hcTuneQuietlyFlag, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamHostControlTuneQuietlyFlag {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag";
            }

            @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
            public void addHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
                    parcelObtain.writeStrongInterface(iCamHostControlTuneQuietlyFlagListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
            public void removeHcTuneQuietlyFlagListener(ICamHostControlTuneQuietlyFlagListener iCamHostControlTuneQuietlyFlagListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
                    parcelObtain.writeStrongInterface(iCamHostControlTuneQuietlyFlagListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag
            public Bundle getHcTuneQuietlyFlag(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
