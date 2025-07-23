package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiCecSettingChangeListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiCecSettingChangeListener";

    public static class Default implements IHdmiCecSettingChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiCecSettingChangeListener
        public void onChange(String str) throws RemoteException {
        }
    }

    void onChange(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiCecSettingChangeListener {
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
            attachInterface(this, IHdmiCecSettingChangeListener.DESCRIPTOR);
        }

        public static IHdmiCecSettingChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IHdmiCecSettingChangeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCecSettingChangeListener)) {
                return (IHdmiCecSettingChangeListener) queryLocalInterface;
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
                parcel.enforceInterface(IHdmiCecSettingChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHdmiCecSettingChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onChange(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHdmiCecSettingChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiCecSettingChangeListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiCecSettingChangeListener
            public void onChange(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IHdmiCecSettingChangeListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
