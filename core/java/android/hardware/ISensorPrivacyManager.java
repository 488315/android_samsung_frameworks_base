package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes.dex */
public interface ISensorPrivacyManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.ISensorPrivacyManager";

    void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener)
            throws RemoteException;

    void addToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener)
            throws RemoteException;

    List<String> getCameraPrivacyAllowlist() throws RemoteException;

    int getToggleSensorPrivacyState(int i, int i2) throws RemoteException;

    boolean isCameraPrivacyEnabled(String str) throws RemoteException;

    boolean isCombinedToggleSensorPrivacyEnabled(int i) throws RemoteException;

    boolean isSensorPrivacyEnabled() throws RemoteException;

    boolean isToggleSensorPrivacyEnabled(int i, int i2) throws RemoteException;

    void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener)
            throws RemoteException;

    void removeToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener)
            throws RemoteException;

    boolean requiresAuthentication() throws RemoteException;

    void setCameraPrivacyAllowlist(List<String> list) throws RemoteException;

    void setSensorPrivacy(boolean z) throws RemoteException;

    void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws RemoteException;

    void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z)
            throws RemoteException;

    void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(
            int i, int i2, int i3, boolean z, int i4) throws RemoteException;

    void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws RemoteException;

    void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4)
            throws RemoteException;

    void showSensorUseDialog(int i) throws RemoteException;

    boolean supportsSensorToggle(int i, int i2) throws RemoteException;

    void suppressToggleSensorPrivacyReminders(int i, int i2, IBinder iBinder, boolean z)
            throws RemoteException;

    public static class Default implements ISensorPrivacyManager {
        @Override // android.hardware.ISensorPrivacyManager
        public boolean supportsSensorToggle(int toggleType, int sensor) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void addSensorPrivacyListener(ISensorPrivacyListener listener)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void addToggleSensorPrivacyListener(ISensorPrivacyListener listener)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void removeSensorPrivacyListener(ISensorPrivacyListener listener)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void removeToggleSensorPrivacyListener(ISensorPrivacyListener listener)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isSensorPrivacyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCombinedToggleSensorPrivacyEnabled(int sensor) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isToggleSensorPrivacyEnabled(int toggleType, int sensor)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setSensorPrivacy(boolean enable) throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacy(int userId, int source, int sensor, boolean enable)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyForProfileGroup(
                int userId, int source, int sensor, boolean enable) throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(
                int userId, int source, int sensor, boolean enable, int displayId)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public List<String> getCameraPrivacyAllowlist() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public int getToggleSensorPrivacyState(int toggleType, int sensor) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyState(int userId, int source, int sensor, int state)
                throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyStateForProfileGroup(
                int userId, int source, int sensor, int state) throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCameraPrivacyEnabled(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setCameraPrivacyAllowlist(List<String> allowlist) throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public void suppressToggleSensorPrivacyReminders(
                int userId, int sensor, IBinder token, boolean suppress) throws RemoteException {}

        @Override // android.hardware.ISensorPrivacyManager
        public boolean requiresAuthentication() throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void showSensorUseDialog(int sensor) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISensorPrivacyManager {
        static final int TRANSACTION_addSensorPrivacyListener = 2;
        static final int TRANSACTION_addToggleSensorPrivacyListener = 3;
        static final int TRANSACTION_getCameraPrivacyAllowlist = 13;
        static final int TRANSACTION_getToggleSensorPrivacyState = 14;
        static final int TRANSACTION_isCameraPrivacyEnabled = 17;
        static final int TRANSACTION_isCombinedToggleSensorPrivacyEnabled = 7;
        static final int TRANSACTION_isSensorPrivacyEnabled = 6;
        static final int TRANSACTION_isToggleSensorPrivacyEnabled = 8;
        static final int TRANSACTION_removeSensorPrivacyListener = 4;
        static final int TRANSACTION_removeToggleSensorPrivacyListener = 5;
        static final int TRANSACTION_requiresAuthentication = 20;
        static final int TRANSACTION_setCameraPrivacyAllowlist = 18;
        static final int TRANSACTION_setSensorPrivacy = 9;
        static final int TRANSACTION_setToggleSensorPrivacy = 10;
        static final int TRANSACTION_setToggleSensorPrivacyForProfileGroup = 11;
        static final int TRANSACTION_setToggleSensorPrivacyForProfileGroupWithConfirmPopup = 12;
        static final int TRANSACTION_setToggleSensorPrivacyState = 15;
        static final int TRANSACTION_setToggleSensorPrivacyStateForProfileGroup = 16;
        static final int TRANSACTION_showSensorUseDialog = 21;
        static final int TRANSACTION_supportsSensorToggle = 1;
        static final int TRANSACTION_suppressToggleSensorPrivacyReminders = 19;

        public Stub() {
            attachInterface(this, ISensorPrivacyManager.DESCRIPTOR);
        }

        public static ISensorPrivacyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISensorPrivacyManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISensorPrivacyManager)) {
                return (ISensorPrivacyManager) iin;
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
                    return "supportsSensorToggle";
                case 2:
                    return "addSensorPrivacyListener";
                case 3:
                    return "addToggleSensorPrivacyListener";
                case 4:
                    return "removeSensorPrivacyListener";
                case 5:
                    return "removeToggleSensorPrivacyListener";
                case 6:
                    return "isSensorPrivacyEnabled";
                case 7:
                    return "isCombinedToggleSensorPrivacyEnabled";
                case 8:
                    return "isToggleSensorPrivacyEnabled";
                case 9:
                    return "setSensorPrivacy";
                case 10:
                    return "setToggleSensorPrivacy";
                case 11:
                    return "setToggleSensorPrivacyForProfileGroup";
                case 12:
                    return "setToggleSensorPrivacyForProfileGroupWithConfirmPopup";
                case 13:
                    return "getCameraPrivacyAllowlist";
                case 14:
                    return "getToggleSensorPrivacyState";
                case 15:
                    return "setToggleSensorPrivacyState";
                case 16:
                    return "setToggleSensorPrivacyStateForProfileGroup";
                case 17:
                    return "isCameraPrivacyEnabled";
                case 18:
                    return "setCameraPrivacyAllowlist";
                case 19:
                    return "suppressToggleSensorPrivacyReminders";
                case 20:
                    return "requiresAuthentication";
                case 21:
                    return "showSensorUseDialog";
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
                data.enforceInterface(ISensorPrivacyManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISensorPrivacyManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = supportsSensorToggle(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    ISensorPrivacyListener _arg02 =
                            ISensorPrivacyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addSensorPrivacyListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    ISensorPrivacyListener _arg03 =
                            ISensorPrivacyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addToggleSensorPrivacyListener(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    ISensorPrivacyListener _arg04 =
                            ISensorPrivacyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeSensorPrivacyListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    ISensorPrivacyListener _arg05 =
                            ISensorPrivacyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeToggleSensorPrivacyListener(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    boolean _result2 = isSensorPrivacyEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 7:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isCombinedToggleSensorPrivacyEnabled(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = isToggleSensorPrivacyEnabled(_arg07, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSensorPrivacy(_arg08);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg09 = data.readInt();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    boolean _arg3 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setToggleSensorPrivacy(_arg09, _arg13, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg22 = data.readInt();
                    boolean _arg32 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroup(_arg010, _arg14, _arg22, _arg32);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    int _arg15 = data.readInt();
                    int _arg23 = data.readInt();
                    boolean _arg33 = data.readBoolean();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroupWithConfirmPopup(
                            _arg011, _arg15, _arg23, _arg33, _arg4);
                    reply.writeNoException();
                    return true;
                case 13:
                    List<String> _result5 = getCameraPrivacyAllowlist();
                    reply.writeNoException();
                    reply.writeStringList(_result5);
                    return true;
                case 14:
                    int _arg012 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result6 = getToggleSensorPrivacyState(_arg012, _arg16);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 15:
                    int _arg013 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg24 = data.readInt();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    setToggleSensorPrivacyState(_arg013, _arg17, _arg24, _arg34);
                    reply.writeNoException();
                    return true;
                case 16:
                    int _arg014 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg25 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    setToggleSensorPrivacyStateForProfileGroup(_arg014, _arg18, _arg25, _arg35);
                    reply.writeNoException();
                    return true;
                case 17:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result7 = isCameraPrivacyEnabled(_arg015);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 18:
                    List<String> _arg016 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setCameraPrivacyAllowlist(_arg016);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg017 = data.readInt();
                    int _arg19 = data.readInt();
                    IBinder _arg26 = data.readStrongBinder();
                    boolean _arg36 = data.readBoolean();
                    data.enforceNoDataAvail();
                    suppressToggleSensorPrivacyReminders(_arg017, _arg19, _arg26, _arg36);
                    reply.writeNoException();
                    return true;
                case 20:
                    boolean _result8 = requiresAuthentication();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 21:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    showSensorUseDialog(_arg018);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISensorPrivacyManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensorPrivacyManager.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean supportsSensorToggle(int toggleType, int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(toggleType);
                    _data.writeInt(sensor);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addSensorPrivacyListener(ISensorPrivacyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addToggleSensorPrivacyListener(ISensorPrivacyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeSensorPrivacyListener(ISensorPrivacyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeToggleSensorPrivacyListener(ISensorPrivacyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isSensorPrivacyEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCombinedToggleSensorPrivacyEnabled(int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(sensor);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isToggleSensorPrivacyEnabled(int toggleType, int sensor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(toggleType);
                    _data.writeInt(sensor);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setSensorPrivacy(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacy(int userId, int source, int sensor, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(source);
                    _data.writeInt(sensor);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroup(
                    int userId, int source, int sensor, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(source);
                    _data.writeInt(sensor);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(
                    int userId, int source, int sensor, boolean enable, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(source);
                    _data.writeInt(sensor);
                    _data.writeBoolean(enable);
                    _data.writeInt(displayId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public List<String> getCameraPrivacyAllowlist() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public int getToggleSensorPrivacyState(int toggleType, int sensor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(toggleType);
                    _data.writeInt(sensor);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyState(int userId, int source, int sensor, int state)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(source);
                    _data.writeInt(sensor);
                    _data.writeInt(state);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyStateForProfileGroup(
                    int userId, int source, int sensor, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(source);
                    _data.writeInt(sensor);
                    _data.writeInt(state);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCameraPrivacyEnabled(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setCameraPrivacyAllowlist(List<String> allowlist) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeStringList(allowlist);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void suppressToggleSensorPrivacyReminders(
                    int userId, int sensor, IBinder token, boolean suppress)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(sensor);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(suppress);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean requiresAuthentication() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void showSensorUseDialog(int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    _data.writeInt(sensor);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }
    }
}
