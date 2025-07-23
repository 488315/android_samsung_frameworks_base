package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiVendorCommandListener extends IInterface {

    public static class Default implements IHdmiVendorCommandListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiVendorCommandListener
        public void onControlStateChanged(boolean z, int i) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiVendorCommandListener
        public void onReceived(int i, int i2, byte[] bArr, boolean z) throws RemoteException {
        }
    }

    void onControlStateChanged(boolean z, int i) throws RemoteException;

    void onReceived(int i, int i2, byte[] bArr, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiVendorCommandListener {
        public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiVendorCommandListener";
        static final int TRANSACTION_onControlStateChanged = 2;
        static final int TRANSACTION_onReceived = 1;

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

        public static IHdmiVendorCommandListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiVendorCommandListener)) {
                return (IHdmiVendorCommandListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onReceived";
            }
            if (i != 2) {
                return null;
            }
            return "onControlStateChanged";
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
                byte[] createByteArray = parcel.createByteArray();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onReceived(readInt, readInt2, createByteArray, readBoolean);
            } else if (i == 2) {
                boolean readBoolean2 = parcel.readBoolean();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onControlStateChanged(readBoolean2, readInt3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IHdmiVendorCommandListener {
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

            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onReceived(int i, int i2, byte[] bArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onControlStateChanged(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
