package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiControlStatusChangeListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlStatusChangeListener";

    public static class Default implements IHdmiControlStatusChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
        public void onStatusChange(int i, boolean z) throws RemoteException {
        }
    }

    void onStatusChange(int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiControlStatusChangeListener {
        static final int TRANSACTION_onStatusChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IHdmiControlStatusChangeListener.DESCRIPTOR);
        }

        public static IHdmiControlStatusChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IHdmiControlStatusChangeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiControlStatusChangeListener)) {
                return (IHdmiControlStatusChangeListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStatusChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IHdmiControlStatusChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHdmiControlStatusChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onStatusChange(readInt, readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHdmiControlStatusChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiControlStatusChangeListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
            public void onStatusChange(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IHdmiControlStatusChangeListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
