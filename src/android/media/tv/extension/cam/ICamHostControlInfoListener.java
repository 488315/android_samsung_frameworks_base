package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamHostControlInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamHostControlInfoListener";

    public static class Default implements ICamHostControlInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlInfoListener
        public void onCamHostControlInfoChanged(String str, int i) throws RemoteException {
        }
    }

    void onCamHostControlInfoChanged(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamHostControlInfoListener {
        static final int TRANSACTION_onCamHostControlInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamHostControlInfoListener");
        }

        public static ICamHostControlInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamHostControlInfoListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamHostControlInfoListener)) {
                return (ICamHostControlInfoListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCamHostControlInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamHostControlInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamHostControlInfoListener");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCamHostControlInfoChanged(string, i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICamHostControlInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamHostControlInfoListener";
            }

            @Override // android.media.tv.extension.cam.ICamHostControlInfoListener
            public void onCamHostControlInfoChanged(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlInfoListener");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
