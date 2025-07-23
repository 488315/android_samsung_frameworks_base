package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiHotplugEventListener extends IInterface {

    public static class Default implements IHdmiHotplugEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiHotplugEventListener
        public void onReceived(HdmiHotplugEvent hdmiHotplugEvent) throws RemoteException {
        }
    }

    void onReceived(HdmiHotplugEvent hdmiHotplugEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiHotplugEventListener {
        public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiHotplugEventListener";
        static final int TRANSACTION_onReceived = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiHotplugEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiHotplugEventListener)) {
                return (IHdmiHotplugEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onReceived";
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
                HdmiHotplugEvent hdmiHotplugEvent = (HdmiHotplugEvent) parcel.readTypedObject(HdmiHotplugEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onReceived(hdmiHotplugEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHdmiHotplugEventListener {
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

            @Override // android.hardware.hdmi.IHdmiHotplugEventListener
            public void onReceived(HdmiHotplugEvent hdmiHotplugEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiHotplugEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
