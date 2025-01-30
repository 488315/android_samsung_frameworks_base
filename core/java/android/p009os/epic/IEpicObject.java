package android.p009os.epic;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IEpicObject extends IInterface {
    public static final String DESCRIPTOR = "android.os.epic.IEpicObject";

    boolean acquire_lock() throws RemoteException;

    boolean acquire_lock_conditional(String str) throws RemoteException;

    boolean acquire_lock_option(int i, int i2) throws RemoteException;

    boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) throws RemoteException;

    boolean hint_release(String str) throws RemoteException;

    boolean perf_hint(String str) throws RemoteException;

    boolean release_lock() throws RemoteException;

    boolean release_lock_conditional(String str) throws RemoteException;

    public static class Default implements IEpicObject {
        @Override // android.p009os.epic.IEpicObject
        public boolean acquire_lock() throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean release_lock() throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean acquire_lock_option(int value, int usec) throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean acquire_lock_option_multi(int[] value_list, int[] usec_list) throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean acquire_lock_conditional(String condition) throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean release_lock_conditional(String condition) throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean perf_hint(String name) throws RemoteException {
            return false;
        }

        @Override // android.p009os.epic.IEpicObject
        public boolean hint_release(String name) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEpicObject {
        static final int TRANSACTION_acquire_lock = 1;
        static final int TRANSACTION_acquire_lock_conditional = 5;
        static final int TRANSACTION_acquire_lock_option = 3;
        static final int TRANSACTION_acquire_lock_option_multi = 4;
        static final int TRANSACTION_hint_release = 8;
        static final int TRANSACTION_perf_hint = 7;
        static final int TRANSACTION_release_lock = 2;
        static final int TRANSACTION_release_lock_conditional = 6;

        public Stub() {
            attachInterface(this, IEpicObject.DESCRIPTOR);
        }

        public static IEpicObject asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEpicObject.DESCRIPTOR);
            if (iin != null && (iin instanceof IEpicObject)) {
                return (IEpicObject) iin;
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
                    return "acquire_lock";
                case 2:
                    return "release_lock";
                case 3:
                    return "acquire_lock_option";
                case 4:
                    return "acquire_lock_option_multi";
                case 5:
                    return "acquire_lock_conditional";
                case 6:
                    return "release_lock_conditional";
                case 7:
                    return "perf_hint";
                case 8:
                    return "hint_release";
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
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IEpicObject.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IEpicObject.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _result = acquire_lock();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            boolean _result2 = release_lock();
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 3:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result3 = acquire_lock_option(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            int[] _arg02 = data.createIntArray();
                            int[] _arg12 = data.createIntArray();
                            data.enforceNoDataAvail();
                            boolean _result4 = acquire_lock_option_multi(_arg02, _arg12);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 5:
                            String _arg03 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result5 = acquire_lock_conditional(_arg03);
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 6:
                            String _arg04 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result6 = release_lock_conditional(_arg04);
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 7:
                            String _arg05 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result7 = perf_hint(_arg05);
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 8:
                            String _arg06 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result8 = hint_release(_arg06);
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IEpicObject {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEpicObject.DESCRIPTOR;
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean acquire_lock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean release_lock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean acquire_lock_option(int value, int usec) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeInt(value);
                    _data.writeInt(usec);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean acquire_lock_option_multi(int[] value_list, int[] usec_list) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeIntArray(value_list);
                    _data.writeIntArray(usec_list);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean acquire_lock_conditional(String condition) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeString(condition);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean release_lock_conditional(String condition) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeString(condition);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean perf_hint(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.epic.IEpicObject
            public boolean hint_release(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
