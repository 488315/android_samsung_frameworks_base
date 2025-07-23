package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISendMgmtFrameEvent extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.ISendMgmtFrameEvent";
    public static final int SEND_MGMT_FRAME_ERROR_ALREADY_STARTED = 5;
    public static final int SEND_MGMT_FRAME_ERROR_MCS_UNSUPPORTED = 2;
    public static final int SEND_MGMT_FRAME_ERROR_NO_ACK = 3;
    public static final int SEND_MGMT_FRAME_ERROR_TIMEOUT = 4;
    public static final int SEND_MGMT_FRAME_ERROR_UNKNOWN = 1;

    public static class Default implements ISendMgmtFrameEvent {
        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnAck(int i) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnFailure(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void OnAck(int i) throws RemoteException;

    void OnFailure(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISendMgmtFrameEvent {
        static final int TRANSACTION_OnAck = 1;
        static final int TRANSACTION_OnFailure = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISendMgmtFrameEvent.DESCRIPTOR);
        }

        public static ISendMgmtFrameEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISendMgmtFrameEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISendMgmtFrameEvent)) {
                return (ISendMgmtFrameEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "OnAck";
            }
            if (i != 2) {
                return null;
            }
            return "OnFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISendMgmtFrameEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISendMgmtFrameEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                OnAck(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                OnFailure(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISendMgmtFrameEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISendMgmtFrameEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
            public void OnAck(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISendMgmtFrameEvent.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
            public void OnFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISendMgmtFrameEvent.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
