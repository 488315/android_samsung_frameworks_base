package android.app.time;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITimeZoneDetectorListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.time.ITimeZoneDetectorListener";

    public static class Default implements ITimeZoneDetectorListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.time.ITimeZoneDetectorListener
        public void onChange() throws RemoteException {
        }
    }

    void onChange() throws RemoteException;

    public static abstract class Stub extends Binder implements ITimeZoneDetectorListener {
        static final int TRANSACTION_onChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITimeZoneDetectorListener.DESCRIPTOR);
        }

        public static ITimeZoneDetectorListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITimeZoneDetectorListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITimeZoneDetectorListener)) {
                return (ITimeZoneDetectorListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITimeZoneDetectorListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITimeZoneDetectorListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onChange();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITimeZoneDetectorListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneDetectorListener.DESCRIPTOR;
            }

            @Override // android.app.time.ITimeZoneDetectorListener
            public void onChange() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITimeZoneDetectorListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
