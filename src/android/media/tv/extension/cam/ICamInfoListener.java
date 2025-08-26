package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamInfoListener";

    public static class Default implements ICamInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamInfoListener
        public void onCamInfoChanged(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.ICamInfoListener
        public void onNewTypeCamInsert(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.ICamInfoListener
        public void onSlotInfoChanged(int i, Bundle bundle) throws RemoteException {
        }
    }

    void onCamInfoChanged(int i, Bundle bundle) throws RemoteException;

    void onNewTypeCamInsert(int i, Bundle bundle) throws RemoteException;

    void onSlotInfoChanged(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamInfoListener {
        static final int TRANSACTION_onCamInfoChanged = 1;
        static final int TRANSACTION_onNewTypeCamInsert = 3;
        static final int TRANSACTION_onSlotInfoChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamInfoListener");
        }

        public static ICamInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamInfoListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamInfoListener)) {
                return (ICamInfoListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCamInfoChanged";
            }
            if (i == 2) {
                return "onSlotInfoChanged";
            }
            if (i != 3) {
                return null;
            }
            return "onNewTypeCamInsert";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamInfoListener");
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onCamInfoChanged(i3, bundle);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onSlotInfoChanged(i4, bundle2);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onNewTypeCamInsert(i5, bundle3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamInfoListener";
            }

            @Override // android.media.tv.extension.cam.ICamInfoListener
            public void onCamInfoChanged(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamInfoListener");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamInfoListener
            public void onSlotInfoChanged(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamInfoListener");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamInfoListener
            public void onNewTypeCamInsert(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamInfoListener");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
