package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ILoudnessCodecUpdatesDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.ILoudnessCodecUpdatesDispatcher";

    public static class Default implements ILoudnessCodecUpdatesDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(int i, PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    void dispatchLoudnessCodecParameterChange(int i, PersistableBundle persistableBundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ILoudnessCodecUpdatesDispatcher {
        static final int TRANSACTION_dispatchLoudnessCodecParameterChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
        }

        public static ILoudnessCodecUpdatesDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILoudnessCodecUpdatesDispatcher)) {
                return (ILoudnessCodecUpdatesDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchLoudnessCodecParameterChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                dispatchLoudnessCodecParameterChange(readInt, persistableBundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ILoudnessCodecUpdatesDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILoudnessCodecUpdatesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ILoudnessCodecUpdatesDispatcher
            public void dispatchLoudnessCodecParameterChange(int i, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
