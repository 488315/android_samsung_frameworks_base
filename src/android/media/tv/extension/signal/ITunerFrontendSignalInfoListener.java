package android.media.tv.extension.signal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITunerFrontendSignalInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.ITunerFrontendSignalInfoListener";

    public static class Default implements ITunerFrontendSignalInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoListener
        public void onFrontendStatusChanged(int i) throws RemoteException {
        }
    }

    void onFrontendStatusChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITunerFrontendSignalInfoListener {
        static final int TRANSACTION_onFrontendStatusChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.ITunerFrontendSignalInfoListener");
        }

        public static ITunerFrontendSignalInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.ITunerFrontendSignalInfoListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITunerFrontendSignalInfoListener)) {
                return (ITunerFrontendSignalInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onFrontendStatusChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.ITunerFrontendSignalInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.ITunerFrontendSignalInfoListener");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFrontendStatusChanged(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITunerFrontendSignalInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.ITunerFrontendSignalInfoListener";
            }

            @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoListener
            public void onFrontendStatusChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.ITunerFrontendSignalInfoListener");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
