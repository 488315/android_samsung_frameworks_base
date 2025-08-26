package android.media.tv.extension.signal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IHdmiSignalInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IHdmiSignalInfoListener";

    public static class Default implements IHdmiSignalInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInfoListener
        public void onLowLatencyModeChanged(int i) throws RemoteException {
        }

        @Override // android.media.tv.extension.signal.IHdmiSignalInfoListener
        public void onSignalInfoChanged(String str) throws RemoteException {
        }
    }

    void onLowLatencyModeChanged(int i) throws RemoteException;

    void onSignalInfoChanged(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiSignalInfoListener {
        static final int TRANSACTION_onLowLatencyModeChanged = 2;
        static final int TRANSACTION_onSignalInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IHdmiSignalInfoListener");
        }

        public static IHdmiSignalInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IHdmiSignalInfoListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHdmiSignalInfoListener)) {
                return (IHdmiSignalInfoListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSignalInfoChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onLowLatencyModeChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IHdmiSignalInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IHdmiSignalInfoListener");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onSignalInfoChanged(string);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLowLatencyModeChanged(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IHdmiSignalInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IHdmiSignalInfoListener";
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInfoListener
            public void onSignalInfoChanged(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInfoListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IHdmiSignalInfoListener
            public void onLowLatencyModeChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IHdmiSignalInfoListener");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
