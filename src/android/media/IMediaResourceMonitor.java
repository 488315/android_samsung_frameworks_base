package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaResourceMonitor extends IInterface {

    public static class Default implements IMediaResourceMonitor {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaResourceMonitor
        public void notifyMediaInfo(int i, MediaMonitorEvent mediaMonitorEvent) throws RemoteException {
        }

        @Override // android.media.IMediaResourceMonitor
        public void notifyResourceGranted(int i, int i2) throws RemoteException {
        }
    }

    void notifyMediaInfo(int i, MediaMonitorEvent mediaMonitorEvent) throws RemoteException;

    void notifyResourceGranted(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaResourceMonitor {
        public static final String DESCRIPTOR = "android.media.IMediaResourceMonitor";
        static final int TRANSACTION_notifyMediaInfo = 2;
        static final int TRANSACTION_notifyResourceGranted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaResourceMonitor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaResourceMonitor)) {
                return (IMediaResourceMonitor) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "notifyResourceGranted";
            }
            if (i != 2) {
                return null;
            }
            return "notifyMediaInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyResourceGranted(readInt, readInt2);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                MediaMonitorEvent mediaMonitorEvent = (MediaMonitorEvent) parcel.readTypedObject(MediaMonitorEvent.CREATOR);
                parcel.enforceNoDataAvail();
                notifyMediaInfo(readInt3, mediaMonitorEvent);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaResourceMonitor {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaResourceMonitor
            public void notifyResourceGranted(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaResourceMonitor
            public void notifyMediaInfo(int i, MediaMonitorEvent mediaMonitorEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaMonitorEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
