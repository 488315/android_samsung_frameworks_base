package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.telephony.ims.RcsClientConfiguration;

/* loaded from: classes4.dex */
public interface IImsConfig extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfig";

    void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void addRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    int getConfigInt(int i) throws RemoteException;

    String getConfigString(int i) throws RemoteException;

    String getRcsClientConfiguration(int i) throws RemoteException;

    void notifyIntImsConfigChanged(int i, int i2) throws RemoteException;

    void notifyProvisionedIntValueChanged(int i, int i2) throws RemoteException;

    void notifyProvisionedStringValueChanged(int i, String str) throws RemoteException;

    void notifyRcsAutoConfigurationErrorReceived(int i, String str) throws RemoteException;

    void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws RemoteException;

    void notifyRcsAutoConfigurationRemoved() throws RemoteException;

    void notifyRcsPreConfigurationReceived(byte[] bArr) throws RemoteException;

    void notifyStringImsConfigChanged(int i, String str) throws RemoteException;

    void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void removeRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    int setConfigInt(int i, int i2) throws RemoteException;

    int setConfigString(int i, String str) throws RemoteException;

    void setRcsClientConfiguration(RcsClientConfiguration rcsClientConfiguration)
            throws RemoteException;

    void triggerRcsReconfiguration() throws RemoteException;

    void updateImsCarrierConfigs(PersistableBundle persistableBundle) throws RemoteException;

    public static class Default implements IImsConfig {
        @Override // android.telephony.ims.aidl.IImsConfig
        public void addImsConfigCallback(IImsConfigCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeImsConfigCallback(IImsConfigCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public int getConfigInt(int item) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getConfigString(int item) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigInt(int item, int value) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigString(int item, String value) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void updateImsCarrierConfigs(PersistableBundle bundle) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationReceived(byte[] config, boolean isCompressed)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationRemoved() throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationErrorReceived(int errorCode, String errorString)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsPreConfigurationReceived(byte[] configXml) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedIntValueChanged(int item, int value) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedStringValueChanged(int item, String value)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addRcsConfigCallback(IRcsConfigCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeRcsConfigCallback(IRcsConfigCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void triggerRcsReconfiguration() throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getRcsClientConfiguration(int item) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void setRcsClientConfiguration(RcsClientConfiguration rcc) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyIntImsConfigChanged(int item, int value) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyStringImsConfigChanged(int item, String value) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IImsConfig {
        static final int TRANSACTION_addImsConfigCallback = 1;
        static final int TRANSACTION_addRcsConfigCallback = 14;
        static final int TRANSACTION_getConfigInt = 3;
        static final int TRANSACTION_getConfigString = 4;
        static final int TRANSACTION_getRcsClientConfiguration = 17;
        static final int TRANSACTION_notifyIntImsConfigChanged = 19;
        static final int TRANSACTION_notifyProvisionedIntValueChanged = 12;
        static final int TRANSACTION_notifyProvisionedStringValueChanged = 13;
        static final int TRANSACTION_notifyRcsAutoConfigurationErrorReceived = 10;
        static final int TRANSACTION_notifyRcsAutoConfigurationReceived = 8;
        static final int TRANSACTION_notifyRcsAutoConfigurationRemoved = 9;
        static final int TRANSACTION_notifyRcsPreConfigurationReceived = 11;
        static final int TRANSACTION_notifyStringImsConfigChanged = 20;
        static final int TRANSACTION_removeImsConfigCallback = 2;
        static final int TRANSACTION_removeRcsConfigCallback = 15;
        static final int TRANSACTION_setConfigInt = 5;
        static final int TRANSACTION_setConfigString = 6;
        static final int TRANSACTION_setRcsClientConfiguration = 18;
        static final int TRANSACTION_triggerRcsReconfiguration = 16;
        static final int TRANSACTION_updateImsCarrierConfigs = 7;

        public Stub() {
            attachInterface(this, IImsConfig.DESCRIPTOR);
        }

        public static IImsConfig asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsConfig.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsConfig)) {
                return (IImsConfig) iin;
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
                    return "addImsConfigCallback";
                case 2:
                    return "removeImsConfigCallback";
                case 3:
                    return "getConfigInt";
                case 4:
                    return "getConfigString";
                case 5:
                    return "setConfigInt";
                case 6:
                    return "setConfigString";
                case 7:
                    return "updateImsCarrierConfigs";
                case 8:
                    return "notifyRcsAutoConfigurationReceived";
                case 9:
                    return "notifyRcsAutoConfigurationRemoved";
                case 10:
                    return "notifyRcsAutoConfigurationErrorReceived";
                case 11:
                    return "notifyRcsPreConfigurationReceived";
                case 12:
                    return "notifyProvisionedIntValueChanged";
                case 13:
                    return "notifyProvisionedStringValueChanged";
                case 14:
                    return "addRcsConfigCallback";
                case 15:
                    return "removeRcsConfigCallback";
                case 16:
                    return "triggerRcsReconfiguration";
                case 17:
                    return "getRcsClientConfiguration";
                case 18:
                    return "setRcsClientConfiguration";
                case 19:
                    return "notifyIntImsConfigChanged";
                case 20:
                    return "notifyStringImsConfigChanged";
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
                data.enforceInterface(IImsConfig.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImsConfig.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IImsConfigCallback _arg0 =
                            IImsConfigCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addImsConfigCallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    IImsConfigCallback _arg02 =
                            IImsConfigCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeImsConfigCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getConfigInt(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result2 = getConfigString(_arg04);
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 = setConfigInt(_arg05, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = setConfigString(_arg06, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 7:
                    PersistableBundle _arg07 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    updateImsCarrierConfigs(_arg07);
                    reply.writeNoException();
                    return true;
                case 8:
                    byte[] _arg08 = data.createByteArray();
                    boolean _arg13 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyRcsAutoConfigurationReceived(_arg08, _arg13);
                    reply.writeNoException();
                    return true;
                case 9:
                    notifyRcsAutoConfigurationRemoved();
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg09 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    notifyRcsAutoConfigurationErrorReceived(_arg09, _arg14);
                    reply.writeNoException();
                    return true;
                case 11:
                    byte[] _arg010 = data.createByteArray();
                    data.enforceNoDataAvail();
                    notifyRcsPreConfigurationReceived(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyProvisionedIntValueChanged(_arg011, _arg15);
                    reply.writeNoException();
                    return true;
                case 13:
                    int _arg012 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    notifyProvisionedStringValueChanged(_arg012, _arg16);
                    reply.writeNoException();
                    return true;
                case 14:
                    IRcsConfigCallback _arg013 =
                            IRcsConfigCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addRcsConfigCallback(_arg013);
                    reply.writeNoException();
                    return true;
                case 15:
                    IRcsConfigCallback _arg014 =
                            IRcsConfigCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeRcsConfigCallback(_arg014);
                    reply.writeNoException();
                    return true;
                case 16:
                    triggerRcsReconfiguration();
                    reply.writeNoException();
                    return true;
                case 17:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result5 = getRcsClientConfiguration(_arg015);
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                case 18:
                    RcsClientConfiguration _arg016 =
                            (RcsClientConfiguration)
                                    data.readTypedObject(RcsClientConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    setRcsClientConfiguration(_arg016);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg017 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyIntImsConfigChanged(_arg017, _arg17);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg018 = data.readInt();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    notifyStringImsConfigChanged(_arg018, _arg18);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsConfig {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsConfig.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addImsConfigCallback(IImsConfigCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeImsConfigCallback(IImsConfigCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int getConfigInt(int item) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public String getConfigString(int item) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigInt(int item, int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeInt(value);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigString(int item, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeString(value);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void updateImsCarrierConfigs(PersistableBundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationReceived(byte[] config, boolean isCompressed)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeByteArray(config);
                    _data.writeBoolean(isCompressed);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationRemoved() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationErrorReceived(int errorCode, String errorString)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorString);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsPreConfigurationReceived(byte[] configXml) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeByteArray(configXml);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyProvisionedIntValueChanged(int item, int value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeInt(value);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyProvisionedStringValueChanged(int item, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeString(value);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addRcsConfigCallback(IRcsConfigCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeRcsConfigCallback(IRcsConfigCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void triggerRcsReconfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public String getRcsClientConfiguration(int item) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void setRcsClientConfiguration(RcsClientConfiguration rcc)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeTypedObject(rcc, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyIntImsConfigChanged(int item, int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeInt(value);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyStringImsConfigChanged(int item, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeString(value);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }
    }
}
