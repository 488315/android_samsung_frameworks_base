package android.hardware.display;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IExynosDisplaySolutionManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IExynosDisplaySolutionManager";

    String getColorEnhancementMode() throws RemoteException;

    float[] getRgbGain() throws RemoteException;

    void setColorEnhancementSettingValue(int i) throws RemoteException;

    void setColorTempSettingOn(int i) throws RemoteException;

    void setColorTempSettingValue(int i) throws RemoteException;

    void setDisplayFeature(String str, int i, int i2, String str2) throws RemoteException;

    void setEdgeSharpnessSettingOn(int i) throws RemoteException;

    void setEdgeSharpnessSettingValue(int i) throws RemoteException;

    void setEyeTempSettingOn(int i) throws RemoteException;

    void setEyeTempSettingValue(int i) throws RemoteException;

    void setHsvGainSettingOn(int i) throws RemoteException;

    void setHsvGainSettingValue(int i, int i2, int i3) throws RemoteException;

    void setRgbGain(float f, float f2, float f3) throws RemoteException;

    void setRgbGainSettingOn(int i) throws RemoteException;

    void setRgbGainSettingValue(int i, int i2, int i3) throws RemoteException;

    void setSkinColorSettingOn(int i) throws RemoteException;

    void setWhitePointColorSettingOn(int i) throws RemoteException;

    public static class Default implements IExynosDisplaySolutionManager {
        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setDisplayFeature(String arg0, int arg1, int arg2, String arg3) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public String getColorEnhancementMode() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorEnhancementSettingValue(int value) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorTempSettingValue(int value) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorTempSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEyeTempSettingValue(int value) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEyeTempSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbGainSettingValue(int r, int g, int b) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbGainSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setSkinColorSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setHsvGainSettingValue(int h, int s, int v) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setHsvGainSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setWhitePointColorSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbGain(float r, float g, float b) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEdgeSharpnessSettingValue(int value) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEdgeSharpnessSettingOn(int onoff) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public float[] getRgbGain() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IExynosDisplaySolutionManager {
        static final int TRANSACTION_getColorEnhancementMode = 2;
        static final int TRANSACTION_getRgbGain = 17;
        static final int TRANSACTION_setColorEnhancementSettingValue = 3;
        static final int TRANSACTION_setColorTempSettingOn = 5;
        static final int TRANSACTION_setColorTempSettingValue = 4;
        static final int TRANSACTION_setDisplayFeature = 1;
        static final int TRANSACTION_setEdgeSharpnessSettingOn = 16;
        static final int TRANSACTION_setEdgeSharpnessSettingValue = 15;
        static final int TRANSACTION_setEyeTempSettingOn = 7;
        static final int TRANSACTION_setEyeTempSettingValue = 6;
        static final int TRANSACTION_setHsvGainSettingOn = 12;
        static final int TRANSACTION_setHsvGainSettingValue = 11;
        static final int TRANSACTION_setRgbGain = 14;
        static final int TRANSACTION_setRgbGainSettingOn = 9;
        static final int TRANSACTION_setRgbGainSettingValue = 8;
        static final int TRANSACTION_setSkinColorSettingOn = 10;
        static final int TRANSACTION_setWhitePointColorSettingOn = 13;

        public Stub() {
            attachInterface(this, IExynosDisplaySolutionManager.DESCRIPTOR);
        }

        public static IExynosDisplaySolutionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExynosDisplaySolutionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IExynosDisplaySolutionManager)) {
                return (IExynosDisplaySolutionManager) iin;
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
                    return "setDisplayFeature";
                case 2:
                    return "getColorEnhancementMode";
                case 3:
                    return "setColorEnhancementSettingValue";
                case 4:
                    return "setColorTempSettingValue";
                case 5:
                    return "setColorTempSettingOn";
                case 6:
                    return "setEyeTempSettingValue";
                case 7:
                    return "setEyeTempSettingOn";
                case 8:
                    return "setRgbGainSettingValue";
                case 9:
                    return "setRgbGainSettingOn";
                case 10:
                    return "setSkinColorSettingOn";
                case 11:
                    return "setHsvGainSettingValue";
                case 12:
                    return "setHsvGainSettingOn";
                case 13:
                    return "setWhitePointColorSettingOn";
                case 14:
                    return "setRgbGain";
                case 15:
                    return "setEdgeSharpnessSettingValue";
                case 16:
                    return "setEdgeSharpnessSettingOn";
                case 17:
                    return "getRgbGain";
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
                data.enforceInterface(IExynosDisplaySolutionManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IExynosDisplaySolutionManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            String _arg3 = data.readString();
                            data.enforceNoDataAvail();
                            setDisplayFeature(_arg0, _arg1, _arg2, _arg3);
                            reply.writeNoException();
                            return true;
                        case 2:
                            String _result = getColorEnhancementMode();
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 3:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            setColorEnhancementSettingValue(_arg02);
                            reply.writeNoException();
                            return true;
                        case 4:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            setColorTempSettingValue(_arg03);
                            reply.writeNoException();
                            return true;
                        case 5:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            setColorTempSettingOn(_arg04);
                            reply.writeNoException();
                            return true;
                        case 6:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            setEyeTempSettingValue(_arg05);
                            reply.writeNoException();
                            return true;
                        case 7:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            setEyeTempSettingOn(_arg06);
                            reply.writeNoException();
                            return true;
                        case 8:
                            int _arg07 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            setRgbGainSettingValue(_arg07, _arg12, _arg22);
                            reply.writeNoException();
                            return true;
                        case 9:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            setRgbGainSettingOn(_arg08);
                            reply.writeNoException();
                            return true;
                        case 10:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            setSkinColorSettingOn(_arg09);
                            reply.writeNoException();
                            return true;
                        case 11:
                            int _arg010 = data.readInt();
                            int _arg13 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            setHsvGainSettingValue(_arg010, _arg13, _arg23);
                            reply.writeNoException();
                            return true;
                        case 12:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            setHsvGainSettingOn(_arg011);
                            reply.writeNoException();
                            return true;
                        case 13:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            setWhitePointColorSettingOn(_arg012);
                            reply.writeNoException();
                            return true;
                        case 14:
                            float _arg013 = data.readFloat();
                            float _arg14 = data.readFloat();
                            float _arg24 = data.readFloat();
                            data.enforceNoDataAvail();
                            setRgbGain(_arg013, _arg14, _arg24);
                            reply.writeNoException();
                            return true;
                        case 15:
                            int _arg014 = data.readInt();
                            data.enforceNoDataAvail();
                            setEdgeSharpnessSettingValue(_arg014);
                            reply.writeNoException();
                            return true;
                        case 16:
                            int _arg015 = data.readInt();
                            data.enforceNoDataAvail();
                            setEdgeSharpnessSettingOn(_arg015);
                            reply.writeNoException();
                            return true;
                        case 17:
                            float[] _result2 = getRgbGain();
                            reply.writeNoException();
                            reply.writeFloatArray(_result2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IExynosDisplaySolutionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExynosDisplaySolutionManager.DESCRIPTOR;
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setDisplayFeature(String arg0, int arg1, int arg2, String arg3) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(arg0);
                    _data.writeInt(arg1);
                    _data.writeInt(arg2);
                    _data.writeString(arg3);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public String getColorEnhancementMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorEnhancementSettingValue(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingValue(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingValue(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingValue(int r, int g, int b) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(r);
                    _data.writeInt(g);
                    _data.writeInt(b);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setSkinColorSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingValue(int h, int s, int v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(h);
                    _data.writeInt(s);
                    _data.writeInt(v);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setWhitePointColorSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGain(float r, float g, float b) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeFloat(r);
                    _data.writeFloat(g);
                    _data.writeFloat(b);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingValue(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingOn(int onoff) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(onoff);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public float[] getRgbGain() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
