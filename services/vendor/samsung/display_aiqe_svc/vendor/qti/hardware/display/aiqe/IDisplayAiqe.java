package vendor.qti.hardware.display.aiqe;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDisplayAiqe extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$qti$hardware$display$aiqe$IDisplayAiqe".replace('$', '.');
    public static final String HASH = "1862f2ce86c1703b98e994b5d200c2b58dd8661b";
    public static final int VERSION = 3;

    void enableCopr(int i, boolean z) throws RemoteException;

    int[] getCoprStats(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void setABCMode(int i, String str) throws RemoteException;

    void setABCReconfig(int i) throws RemoteException;

    void setABCState(int i, int i2) throws RemoteException;

    void setMdnieMode(String str) throws RemoteException;

    void setSsrcMode(int i, String str) throws RemoteException;

    public static class Default implements IDisplayAiqe {
        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void setSsrcMode(int disp_id, String mode_name) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void enableCopr(int disp_id, boolean enable) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public int[] getCoprStats(int disp_id) throws RemoteException {
            return null;
        }

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void setABCState(int dispId, int enable) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void setABCReconfig(int dispId) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void setABCMode(int dispId, String mode_name) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public void setMdnieMode(String mode_name) throws RemoteException {}

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDisplayAiqe {
        static final int TRANSACTION_enableCopr = 2;
        static final int TRANSACTION_getCoprStats = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setABCMode = 6;
        static final int TRANSACTION_setABCReconfig = 5;
        static final int TRANSACTION_setABCState = 4;
        static final int TRANSACTION_setMdnieMode = 7;
        static final int TRANSACTION_setSsrcMode = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayAiqe asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDisplayAiqe)) {
                return (IDisplayAiqe) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= TRANSACTION_setSsrcMode && code <= TRANSACTION_getInterfaceVersion) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == TRANSACTION_getInterfaceVersion) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == TRANSACTION_getInterfaceHash) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case TRANSACTION_setSsrcMode /* 1 */:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    setSsrcMode(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_enableCopr /* 2 */:
                    int _arg02 = data.readInt();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableCopr(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result = getCoprStats(_arg03);
                    reply.writeNoException();
                    reply.writeIntArray(_result);
                    return true;
                case TRANSACTION_setABCState /* 4 */:
                    int _arg04 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setABCState(_arg04, _arg13);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setABCReconfig /* 5 */:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    setABCReconfig(_arg05);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setABCMode /* 6 */:
                    int _arg06 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    setABCMode(_arg06, _arg14);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setMdnieMode /* 7 */:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    setMdnieMode(_arg07);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDisplayAiqe {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void setSsrcMode(int disp_id, String mode_name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(disp_id);
                    _data.writeString(mode_name);
                    boolean _status =
                            this.mRemote.transact(Stub.TRANSACTION_setSsrcMode, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setSsrcMode is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void enableCopr(int disp_id, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(disp_id);
                    _data.writeBoolean(enable);
                    boolean _status =
                            this.mRemote.transact(Stub.TRANSACTION_enableCopr, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method enableCopr is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public int[] getCoprStats(int disp_id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(disp_id);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getCoprStats is unimplemented.");
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void setABCState(int dispId, int enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(dispId);
                    _data.writeInt(enable);
                    boolean _status =
                            this.mRemote.transact(Stub.TRANSACTION_setABCState, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setABCState is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void setABCReconfig(int dispId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(dispId);
                    boolean _status =
                            this.mRemote.transact(
                                    Stub.TRANSACTION_setABCReconfig, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setABCReconfig is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void setABCMode(int dispId, String mode_name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(dispId);
                    _data.writeString(mode_name);
                    boolean _status =
                            this.mRemote.transact(Stub.TRANSACTION_setABCMode, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setABCMode is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public void setMdnieMode(String mode_name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(mode_name);
                    boolean _status =
                            this.mRemote.transact(Stub.TRANSACTION_setMdnieMode, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setMdnieMode is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.qti.hardware.display.aiqe.IDisplayAiqe
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, data, reply, 0);
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
    }
}
