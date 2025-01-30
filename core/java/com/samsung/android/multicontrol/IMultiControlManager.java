package com.samsung.android.multicontrol;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.IInputFilter;
import com.samsung.android.multicontrol.IInputFilterInstallListener;
import com.samsung.android.multicontrol.IMultiControlDeathChecker;

/* loaded from: classes5.dex */
public interface IMultiControlManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multicontrol.IMultiControlManager";

    void enableTriggerDetection(boolean z) throws RemoteException;

    void forceHideCursor(boolean z) throws RemoteException;

    int getProtocolVersion() throws RemoteException;

    boolean isAllowed() throws RemoteException;

    void resetInputFilter() throws RemoteException;

    void setCursorPosition(int i, int i2, int i3) throws RemoteException;

    void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) throws RemoteException;

    void setInteractive(boolean z) throws RemoteException;

    void setMultiControlOutOfFocus(boolean z) throws RemoteException;

    void setProtocolVersion(int i) throws RemoteException;

    void setTriggerThreshold(int i) throws RemoteException;

    void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) throws RemoteException;

    void stopDeathChecker() throws RemoteException;

    public static class Default implements IMultiControlManager {
        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public boolean isAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public int getProtocolVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setProtocolVersion(int version) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setInputFilter(IInputFilter filter, IInputFilterInstallListener listener) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void resetInputFilter() throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void forceHideCursor(boolean hide) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setInteractive(boolean interactive) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setMultiControlOutOfFocus(boolean outOfFocus) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void startDeathChecker(IMultiControlDeathChecker checker) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void stopDeathChecker() throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setCursorPosition(int x, int y, int displayId) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void enableTriggerDetection(boolean enable) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setTriggerThreshold(int threshold) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMultiControlManager {
        static final int TRANSACTION_enableTriggerDetection = 12;
        static final int TRANSACTION_forceHideCursor = 6;
        static final int TRANSACTION_getProtocolVersion = 2;
        static final int TRANSACTION_isAllowed = 1;
        static final int TRANSACTION_resetInputFilter = 5;
        static final int TRANSACTION_setCursorPosition = 11;
        static final int TRANSACTION_setInputFilter = 4;
        static final int TRANSACTION_setInteractive = 7;
        static final int TRANSACTION_setMultiControlOutOfFocus = 8;
        static final int TRANSACTION_setProtocolVersion = 3;
        static final int TRANSACTION_setTriggerThreshold = 13;
        static final int TRANSACTION_startDeathChecker = 9;
        static final int TRANSACTION_stopDeathChecker = 10;

        public Stub() {
            attachInterface(this, IMultiControlManager.DESCRIPTOR);
        }

        public static IMultiControlManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMultiControlManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IMultiControlManager)) {
                return (IMultiControlManager) iin;
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
                    return "isAllowed";
                case 2:
                    return "getProtocolVersion";
                case 3:
                    return "setProtocolVersion";
                case 4:
                    return "setInputFilter";
                case 5:
                    return "resetInputFilter";
                case 6:
                    return "forceHideCursor";
                case 7:
                    return "setInteractive";
                case 8:
                    return "setMultiControlOutOfFocus";
                case 9:
                    return "startDeathChecker";
                case 10:
                    return "stopDeathChecker";
                case 11:
                    return "setCursorPosition";
                case 12:
                    return "enableTriggerDetection";
                case 13:
                    return "setTriggerThreshold";
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
                data.enforceInterface(IMultiControlManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IMultiControlManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _result = isAllowed();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            int _result2 = getProtocolVersion();
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        case 3:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            setProtocolVersion(_arg0);
                            reply.writeNoException();
                            return true;
                        case 4:
                            IInputFilter _arg02 = IInputFilter.Stub.asInterface(data.readStrongBinder());
                            IInputFilterInstallListener _arg1 = IInputFilterInstallListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setInputFilter(_arg02, _arg1);
                            reply.writeNoException();
                            return true;
                        case 5:
                            resetInputFilter();
                            reply.writeNoException();
                            return true;
                        case 6:
                            boolean _arg03 = data.readBoolean();
                            data.enforceNoDataAvail();
                            forceHideCursor(_arg03);
                            reply.writeNoException();
                            return true;
                        case 7:
                            boolean _arg04 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setInteractive(_arg04);
                            reply.writeNoException();
                            return true;
                        case 8:
                            boolean _arg05 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setMultiControlOutOfFocus(_arg05);
                            reply.writeNoException();
                            return true;
                        case 9:
                            IMultiControlDeathChecker _arg06 = IMultiControlDeathChecker.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            startDeathChecker(_arg06);
                            reply.writeNoException();
                            return true;
                        case 10:
                            stopDeathChecker();
                            reply.writeNoException();
                            return true;
                        case 11:
                            int _arg07 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            setCursorPosition(_arg07, _arg12, _arg2);
                            reply.writeNoException();
                            return true;
                        case 12:
                            boolean _arg08 = data.readBoolean();
                            data.enforceNoDataAvail();
                            enableTriggerDetection(_arg08);
                            reply.writeNoException();
                            return true;
                        case 13:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            setTriggerThreshold(_arg09);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IMultiControlManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMultiControlManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public boolean isAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public int getProtocolVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setProtocolVersion(int version) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeInt(version);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setInputFilter(IInputFilter filter, IInputFilterInstallListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeStrongInterface(filter);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void resetInputFilter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void forceHideCursor(boolean hide) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeBoolean(hide);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setInteractive(boolean interactive) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeBoolean(interactive);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setMultiControlOutOfFocus(boolean outOfFocus) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeBoolean(outOfFocus);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void startDeathChecker(IMultiControlDeathChecker checker) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeStrongInterface(checker);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void stopDeathChecker() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setCursorPosition(int x, int y, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(displayId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void enableTriggerDetection(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setTriggerThreshold(int threshold) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    _data.writeInt(threshold);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
