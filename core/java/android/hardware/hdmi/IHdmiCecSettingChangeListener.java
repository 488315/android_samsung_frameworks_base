package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHdmiCecSettingChangeListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiCecSettingChangeListener";

    void onChange(String str) throws RemoteException;

    public static class Default implements IHdmiCecSettingChangeListener {
        @Override // android.hardware.hdmi.IHdmiCecSettingChangeListener
        public void onChange(String setting) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IHdmiCecSettingChangeListener {
        static final int TRANSACTION_onChange = 1;

        public Stub() {
            attachInterface(this, IHdmiCecSettingChangeListener.DESCRIPTOR);
        }

        public static IHdmiCecSettingChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHdmiCecSettingChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IHdmiCecSettingChangeListener)) {
                return (IHdmiCecSettingChangeListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onChange";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IHdmiCecSettingChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHdmiCecSettingChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onChange(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHdmiCecSettingChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiCecSettingChangeListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiCecSettingChangeListener
            public void onChange(String setting) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHdmiCecSettingChangeListener.DESCRIPTOR);
                    _data.writeString(setting);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
