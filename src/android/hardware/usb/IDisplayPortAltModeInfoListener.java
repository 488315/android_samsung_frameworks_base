package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDisplayPortAltModeInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.usb.IDisplayPortAltModeInfoListener";

    public static class Default implements IDisplayPortAltModeInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
        public void onDisplayPortAltModeInfoChanged(String str, DisplayPortAltModeInfo displayPortAltModeInfo) throws RemoteException {
        }
    }

    void onDisplayPortAltModeInfoChanged(String str, DisplayPortAltModeInfo displayPortAltModeInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayPortAltModeInfoListener {
        static final int TRANSACTION_onDisplayPortAltModeInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDisplayPortAltModeInfoListener.DESCRIPTOR);
        }

        public static IDisplayPortAltModeInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplayPortAltModeInfoListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayPortAltModeInfoListener)) {
                return (IDisplayPortAltModeInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDisplayPortAltModeInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayPortAltModeInfoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayPortAltModeInfoListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                DisplayPortAltModeInfo displayPortAltModeInfo = (DisplayPortAltModeInfo) parcel.readTypedObject(DisplayPortAltModeInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDisplayPortAltModeInfoChanged(readString, displayPortAltModeInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDisplayPortAltModeInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayPortAltModeInfoListener.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
            public void onDisplayPortAltModeInfoChanged(String str, DisplayPortAltModeInfo displayPortAltModeInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayPortAltModeInfoListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(displayPortAltModeInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
