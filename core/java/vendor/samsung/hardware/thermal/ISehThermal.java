package vendor.samsung.hardware.thermal;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import vendor.samsung.hardware.thermal.ISehThermalChangedCallback;

/* loaded from: classes6.dex */
public interface ISehThermal extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$thermal$ISehThermal".replace('$', '.');
    public static final String HASH = "179442d052a6252d8efb5d74ab634019e2e640bb";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getScenarioHint() throws RemoteException;

    SehTemperature[] getTemperatures() throws RemoteException;

    SehTemperature[] getTemperaturesWithType(int i) throws RemoteException;

    void registerThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    void setScenarioHint(int i) throws RemoteException;

    void unregisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    public static class Default implements ISehThermal {
        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public SehTemperature[] getTemperatures() throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public SehTemperature[] getTemperaturesWithType(int type) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void registerThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void unregisterThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void setScenarioHint(int hint) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public int getScenarioHint() throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISehThermal {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getScenarioHint = 6;
        static final int TRANSACTION_getTemperatures = 1;
        static final int TRANSACTION_getTemperaturesWithType = 2;
        static final int TRANSACTION_registerThermalChangedCallback = 3;
        static final int TRANSACTION_setScenarioHint = 5;
        static final int TRANSACTION_unregisterThermalChangedCallback = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehThermal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehThermal)) {
                return (ISehThermal) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getTemperatures";
                case 2:
                    return "getTemperaturesWithType";
                case 3:
                    return "registerThermalChangedCallback";
                case 4:
                    return "unregisterThermalChangedCallback";
                case 5:
                    return "setScenarioHint";
                case 6:
                    return "getScenarioHint";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            switch (code) {
                case 16777214:
                    reply.writeNoException();
                    reply.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    reply.writeNoException();
                    reply.writeInt(getInterfaceVersion());
                    return true;
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(descriptor);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            SehTemperature[] _result = getTemperatures();
                            reply.writeNoException();
                            reply.writeTypedArray(_result, 1);
                            return true;
                        case 2:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            SehTemperature[] _result2 = getTemperaturesWithType(_arg0);
                            reply.writeNoException();
                            reply.writeTypedArray(_result2, 1);
                            return true;
                        case 3:
                            ISehThermalChangedCallback _arg02 = ISehThermalChangedCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerThermalChangedCallback(_arg02);
                            return true;
                        case 4:
                            ISehThermalChangedCallback _arg03 = ISehThermalChangedCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            unregisterThermalChangedCallback(_arg03);
                            return true;
                        case 5:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            setScenarioHint(_arg04);
                            return true;
                        case 6:
                            int _result3 = getScenarioHint();
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISehThermal {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public SehTemperature[] getTemperatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getTemperatures is unimplemented.");
                    }
                    _reply.readException();
                    SehTemperature[] _result = (SehTemperature[]) _reply.createTypedArray(SehTemperature.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public SehTemperature[] getTemperaturesWithType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getTemperaturesWithType is unimplemented.");
                    }
                    _reply.readException();
                    SehTemperature[] _result = (SehTemperature[]) _reply.createTypedArray(SehTemperature.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void registerThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method registerThermalChangedCallback is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void unregisterThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method unregisterThermalChangedCallback is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void setScenarioHint(int hint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(hint);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setScenarioHint is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public int getScenarioHint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getScenarioHint is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
