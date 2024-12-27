package com.samsung.android.displayaiqe;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDisplayAiqeManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.displayaiqe.IDisplayAiqeManager";

    String getContentMode() throws RemoteException;

    int getCoprValue() throws RemoteException;

    boolean getDisplayService() throws RemoteException;

    boolean setBlueLightFilterMode(boolean z, int i) throws RemoteException;

    boolean setByPassMode(boolean z) throws RemoteException;

    boolean setContentMode(int i) throws RemoteException;

    boolean setEnvironmentAdaptiveDisplayLevel(int i) throws RemoteException;

    boolean setEnvironmentAdaptiveDisplayMode(int i) throws RemoteException;

    boolean setExtraDimMode(int i) throws RemoteException;

    boolean setHighBrightnessMode(int i) throws RemoteException;

    boolean setHighDynamicRangeMode(boolean z) throws RemoteException;

    boolean setNaturalMode(String str) throws RemoteException;

    boolean setScreenMode(int i) throws RemoteException;

    boolean setVividnessMode(int i) throws RemoteException;

    boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    public static class Default implements IDisplayAiqeManager {
        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setByPassMode(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setExtraDimMode(int level) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setHighDynamicRangeMode(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setScreenMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setBlueLightFilterMode(boolean enable, int level) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setContentMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public String getContentMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setVividnessMode(int index) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setWhiteBalanceMode(int m_r, int m_g, int m_b, int s_r, int s_g, int s_b) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setEnvironmentAdaptiveDisplayMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setEnvironmentAdaptiveDisplayLevel(int level) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setHighBrightnessMode(int index) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setNaturalMode(String mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean getDisplayService() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public int getCoprValue() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDisplayAiqeManager {
        static final int TRANSACTION_getContentMode = 7;
        static final int TRANSACTION_getCoprValue = 15;
        static final int TRANSACTION_getDisplayService = 14;
        static final int TRANSACTION_setBlueLightFilterMode = 5;
        static final int TRANSACTION_setByPassMode = 1;
        static final int TRANSACTION_setContentMode = 6;
        static final int TRANSACTION_setEnvironmentAdaptiveDisplayLevel = 11;
        static final int TRANSACTION_setEnvironmentAdaptiveDisplayMode = 10;
        static final int TRANSACTION_setExtraDimMode = 2;
        static final int TRANSACTION_setHighBrightnessMode = 12;
        static final int TRANSACTION_setHighDynamicRangeMode = 3;
        static final int TRANSACTION_setNaturalMode = 13;
        static final int TRANSACTION_setScreenMode = 4;
        static final int TRANSACTION_setVividnessMode = 8;
        static final int TRANSACTION_setWhiteBalanceMode = 9;

        public Stub() {
            attachInterface(this, IDisplayAiqeManager.DESCRIPTOR);
        }

        public static IDisplayAiqeManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDisplayAiqeManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IDisplayAiqeManager)) {
                return (IDisplayAiqeManager) iin;
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
                    return "setByPassMode";
                case 2:
                    return "setExtraDimMode";
                case 3:
                    return "setHighDynamicRangeMode";
                case 4:
                    return "setScreenMode";
                case 5:
                    return "setBlueLightFilterMode";
                case 6:
                    return "setContentMode";
                case 7:
                    return "getContentMode";
                case 8:
                    return "setVividnessMode";
                case 9:
                    return "setWhiteBalanceMode";
                case 10:
                    return "setEnvironmentAdaptiveDisplayMode";
                case 11:
                    return "setEnvironmentAdaptiveDisplayLevel";
                case 12:
                    return "setHighBrightnessMode";
                case 13:
                    return "setNaturalMode";
                case 14:
                    return "getDisplayService";
                case 15:
                    return "getCoprValue";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDisplayAiqeManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDisplayAiqeManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result = setByPassMode(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = setExtraDimMode(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result3 = setHighDynamicRangeMode(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = setScreenMode(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    boolean _arg05 = data.readBoolean();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = setBlueLightFilterMode(_arg05, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = setContentMode(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _result7 = getContentMode();
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = setVividnessMode(_arg07);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    int _arg08 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = setWhiteBalanceMode(_arg08, _arg12, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 10:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = setEnvironmentAdaptiveDisplayMode(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = setEnvironmentAdaptiveDisplayLevel(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = setHighBrightnessMode(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = setNaturalMode(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 14:
                    boolean _result14 = getDisplayService();
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 15:
                    int _result15 = getCoprValue();
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDisplayAiqeManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayAiqeManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setByPassMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setExtraDimMode(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setHighDynamicRangeMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setScreenMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setBlueLightFilterMode(boolean enable, int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(level);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setContentMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public String getContentMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setVividnessMode(int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(index);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setWhiteBalanceMode(int m_r, int m_g, int m_b, int s_r, int s_g, int s_b) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(m_r);
                    _data.writeInt(m_g);
                    _data.writeInt(m_b);
                    _data.writeInt(s_r);
                    _data.writeInt(s_g);
                    _data.writeInt(s_b);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setEnvironmentAdaptiveDisplayMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setEnvironmentAdaptiveDisplayLevel(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setHighBrightnessMode(int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeInt(index);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setNaturalMode(String mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    _data.writeString(mode);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean getDisplayService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public int getCoprValue() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
