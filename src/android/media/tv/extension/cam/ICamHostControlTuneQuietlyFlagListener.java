package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamHostControlTuneQuietlyFlagListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener";

    public static class Default implements ICamHostControlTuneQuietlyFlagListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener
        public void onHcTuneQuietlyFlagChanged(String str, int i) throws RemoteException {
        }
    }

    void onHcTuneQuietlyFlagChanged(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamHostControlTuneQuietlyFlagListener {
        static final int TRANSACTION_onHcTuneQuietlyFlagChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener");
        }

        public static ICamHostControlTuneQuietlyFlagListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamHostControlTuneQuietlyFlagListener)) {
                return (ICamHostControlTuneQuietlyFlagListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onHcTuneQuietlyFlagChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHcTuneQuietlyFlagChanged(readString, readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICamHostControlTuneQuietlyFlagListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener";
            }

            @Override // android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener
            public void onHcTuneQuietlyFlagChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
