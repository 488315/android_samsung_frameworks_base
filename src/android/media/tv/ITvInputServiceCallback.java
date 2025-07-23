package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvInputServiceCallback extends IInterface {

    public static class Default implements ITvInputServiceCallback {
        @Override // android.media.tv.ITvInputServiceCallback
        public void addHardwareInput(int i, TvInputInfo tvInputInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputServiceCallback
        public void addHdmiInput(int i, TvInputInfo tvInputInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputServiceCallback
        public void removeHardwareInput(String str) throws RemoteException {
        }
    }

    void addHardwareInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

    void addHdmiInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

    void removeHardwareInput(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputServiceCallback {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputServiceCallback";
        static final int TRANSACTION_addHardwareInput = 1;
        static final int TRANSACTION_addHdmiInput = 2;
        static final int TRANSACTION_removeHardwareInput = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputServiceCallback)) {
                return (ITvInputServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addHardwareInput";
            }
            if (i == 2) {
                return "addHdmiInput";
            }
            if (i != 3) {
                return null;
            }
            return "removeHardwareInput";
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
                TvInputInfo tvInputInfo = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                parcel.enforceNoDataAvail();
                addHardwareInput(readInt, tvInputInfo);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                TvInputInfo tvInputInfo2 = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                parcel.enforceNoDataAvail();
                addHdmiInput(readInt2, tvInputInfo2);
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                removeHardwareInput(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvInputServiceCallback {
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

            @Override // android.media.tv.ITvInputServiceCallback
            public void addHardwareInput(int i, TvInputInfo tvInputInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputServiceCallback
            public void addHdmiInput(int i, TvInputInfo tvInputInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputServiceCallback
            public void removeHardwareInput(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
