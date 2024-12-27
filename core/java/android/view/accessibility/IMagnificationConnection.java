package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMagnificationConnection extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IMagnificationConnection";

    void disableWindowMagnification(
            int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback)
            throws RemoteException;

    void enableWindowMagnification(
            int i,
            float f,
            float f2,
            float f3,
            float f4,
            float f5,
            IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback)
            throws RemoteException;

    void moveWindowMagnifier(int i, float f, float f2) throws RemoteException;

    void moveWindowMagnifierToPosition(
            int i,
            float f,
            float f2,
            IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback)
            throws RemoteException;

    void onFullscreenMagnificationActivationChanged(int i, boolean z) throws RemoteException;

    void onUserMagnificationScaleChanged(int i, int i2, float f) throws RemoteException;

    void removeMagnificationButton(int i) throws RemoteException;

    void removeMagnificationSettingsPanel(int i) throws RemoteException;

    void secSetCursorVisible(int i, boolean z) throws RemoteException;

    void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback)
            throws RemoteException;

    void setScaleForWindowMagnification(int i, float f) throws RemoteException;

    void showMagnificationButton(int i, int i2) throws RemoteException;

    public static class Default implements IMagnificationConnection {
        @Override // android.view.accessibility.IMagnificationConnection
        public void enableWindowMagnification(
                int displayId,
                float scale,
                float centerX,
                float centerY,
                float magnificationFrameOffsetRatioX,
                float magnificationFrameOffsetRatioY,
                IRemoteMagnificationAnimationCallback callback)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void setScaleForWindowMagnification(int displayId, float scale)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void disableWindowMagnification(
                int displayId, IRemoteMagnificationAnimationCallback callback)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifier(int displayId, float offsetX, float offsetY)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void secSetCursorVisible(int displayId, boolean visible) throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifierToPosition(
                int displayId,
                float positionX,
                float positionY,
                IRemoteMagnificationAnimationCallback callback)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void showMagnificationButton(int displayId, int magnificationMode)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationButton(int displayId) throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationSettingsPanel(int displayId) throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void setConnectionCallback(IMagnificationConnectionCallback callback)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void onUserMagnificationScaleChanged(int userId, int displayId, float scale)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnection
        public void onFullscreenMagnificationActivationChanged(int displayId, boolean activated)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMagnificationConnection {
        static final int TRANSACTION_disableWindowMagnification = 3;
        static final int TRANSACTION_enableWindowMagnification = 1;
        static final int TRANSACTION_moveWindowMagnifier = 4;
        static final int TRANSACTION_moveWindowMagnifierToPosition = 6;
        static final int TRANSACTION_onFullscreenMagnificationActivationChanged = 12;
        static final int TRANSACTION_onUserMagnificationScaleChanged = 11;
        static final int TRANSACTION_removeMagnificationButton = 8;
        static final int TRANSACTION_removeMagnificationSettingsPanel = 9;
        static final int TRANSACTION_secSetCursorVisible = 5;
        static final int TRANSACTION_setConnectionCallback = 10;
        static final int TRANSACTION_setScaleForWindowMagnification = 2;
        static final int TRANSACTION_showMagnificationButton = 7;

        public Stub() {
            attachInterface(this, IMagnificationConnection.DESCRIPTOR);
        }

        public static IMagnificationConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMagnificationConnection.DESCRIPTOR);
            if (iin != null && (iin instanceof IMagnificationConnection)) {
                return (IMagnificationConnection) iin;
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
                    return "enableWindowMagnification";
                case 2:
                    return "setScaleForWindowMagnification";
                case 3:
                    return "disableWindowMagnification";
                case 4:
                    return "moveWindowMagnifier";
                case 5:
                    return "secSetCursorVisible";
                case 6:
                    return "moveWindowMagnifierToPosition";
                case 7:
                    return "showMagnificationButton";
                case 8:
                    return "removeMagnificationButton";
                case 9:
                    return "removeMagnificationSettingsPanel";
                case 10:
                    return "setConnectionCallback";
                case 11:
                    return "onUserMagnificationScaleChanged";
                case 12:
                    return "onFullscreenMagnificationActivationChanged";
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
                data.enforceInterface(IMagnificationConnection.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMagnificationConnection.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    float _arg1 = data.readFloat();
                    float _arg2 = data.readFloat();
                    float _arg3 = data.readFloat();
                    float _arg4 = data.readFloat();
                    float _arg5 = data.readFloat();
                    IRemoteMagnificationAnimationCallback _arg6 =
                            IRemoteMagnificationAnimationCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    enableWindowMagnification(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    float _arg12 = data.readFloat();
                    data.enforceNoDataAvail();
                    setScaleForWindowMagnification(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    IRemoteMagnificationAnimationCallback _arg13 =
                            IRemoteMagnificationAnimationCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    disableWindowMagnification(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    float _arg14 = data.readFloat();
                    float _arg22 = data.readFloat();
                    data.enforceNoDataAvail();
                    moveWindowMagnifier(_arg04, _arg14, _arg22);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    secSetCursorVisible(_arg05, _arg15);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    float _arg16 = data.readFloat();
                    float _arg23 = data.readFloat();
                    IRemoteMagnificationAnimationCallback _arg32 =
                            IRemoteMagnificationAnimationCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    moveWindowMagnifierToPosition(_arg06, _arg16, _arg23, _arg32);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    showMagnificationButton(_arg07, _arg17);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    removeMagnificationButton(_arg08);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    removeMagnificationSettingsPanel(_arg09);
                    return true;
                case 10:
                    IMagnificationConnectionCallback _arg010 =
                            IMagnificationConnectionCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setConnectionCallback(_arg010);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg18 = data.readInt();
                    float _arg24 = data.readFloat();
                    data.enforceNoDataAvail();
                    onUserMagnificationScaleChanged(_arg011, _arg18, _arg24);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    boolean _arg19 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onFullscreenMagnificationActivationChanged(_arg012, _arg19);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMagnificationConnection {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMagnificationConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void enableWindowMagnification(
                    int displayId,
                    float scale,
                    float centerX,
                    float centerY,
                    float magnificationFrameOffsetRatioX,
                    float magnificationFrameOffsetRatioY,
                    IRemoteMagnificationAnimationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(scale);
                    _data.writeFloat(centerX);
                    _data.writeFloat(centerY);
                    _data.writeFloat(magnificationFrameOffsetRatioX);
                    _data.writeFloat(magnificationFrameOffsetRatioY);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setScaleForWindowMagnification(int displayId, float scale)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(scale);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void disableWindowMagnification(
                    int displayId, IRemoteMagnificationAnimationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifier(int displayId, float offsetX, float offsetY)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(offsetX);
                    _data.writeFloat(offsetY);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void secSetCursorVisible(int displayId, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifierToPosition(
                    int displayId,
                    float positionX,
                    float positionY,
                    IRemoteMagnificationAnimationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(positionX);
                    _data.writeFloat(positionY);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void showMagnificationButton(int displayId, int magnificationMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(magnificationMode);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationButton(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationSettingsPanel(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setConnectionCallback(IMagnificationConnectionCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onUserMagnificationScaleChanged(int userId, int displayId, float scale)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    _data.writeFloat(scale);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onFullscreenMagnificationActivationChanged(int displayId, boolean activated)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(activated);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
