package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;

/* loaded from: classes4.dex */
public interface IMagnificationConnection extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IMagnificationConnection";

    public static class Default implements IMagnificationConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifier(int i, float f, float f2) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifierToPosition(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void onFullscreenMagnificationActivationChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void onUserMagnificationScaleChanged(int i, int i2, float f) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationButton(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationSettingsPanel(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void secSetCursorVisible(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void setScaleForWindowMagnification(int i, float f) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void showMagnificationButton(int i, int i2) throws RemoteException {
        }
    }

    void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException;

    void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException;

    void moveWindowMagnifier(int i, float f, float f2) throws RemoteException;

    void moveWindowMagnifierToPosition(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException;

    void onFullscreenMagnificationActivationChanged(int i, boolean z) throws RemoteException;

    void onUserMagnificationScaleChanged(int i, int i2, float f) throws RemoteException;

    void removeMagnificationButton(int i) throws RemoteException;

    void removeMagnificationSettingsPanel(int i) throws RemoteException;

    void secSetCursorVisible(int i, boolean z) throws RemoteException;

    void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback) throws RemoteException;

    void setScaleForWindowMagnification(int i, float f) throws RemoteException;

    void showMagnificationButton(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMagnificationConnection {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IMagnificationConnection.DESCRIPTOR);
        }

        public static IMagnificationConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMagnificationConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMagnificationConnection)) {
                return (IMagnificationConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMagnificationConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMagnificationConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    float readFloat5 = parcel.readFloat();
                    IRemoteMagnificationAnimationCallback asInterface = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableWindowMagnification(readInt, readFloat, readFloat2, readFloat3, readFloat4, readFloat5, asInterface);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    float readFloat6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setScaleForWindowMagnification(readInt2, readFloat6);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    IRemoteMagnificationAnimationCallback asInterface2 = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableWindowMagnification(readInt3, asInterface2);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    float readFloat7 = parcel.readFloat();
                    float readFloat8 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifier(readInt4, readFloat7, readFloat8);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    secSetCursorVisible(readInt5, readBoolean);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    float readFloat9 = parcel.readFloat();
                    float readFloat10 = parcel.readFloat();
                    IRemoteMagnificationAnimationCallback asInterface3 = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifierToPosition(readInt6, readFloat9, readFloat10, asInterface3);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showMagnificationButton(readInt7, readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationButton(readInt9);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationSettingsPanel(readInt10);
                    return true;
                case 10:
                    IMagnificationConnectionCallback asInterface4 = IMagnificationConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setConnectionCallback(asInterface4);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    float readFloat11 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onUserMagnificationScaleChanged(readInt11, readInt12, readFloat11);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFullscreenMagnificationActivationChanged(readInt13, readBoolean2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMagnificationConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMagnificationConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeFloat(f5);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setScaleForWindowMagnification(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifier(int i, float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void secSetCursorVisible(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifierToPosition(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void showMagnificationButton(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationButton(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationSettingsPanel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeStrongInterface(iMagnificationConnectionCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onUserMagnificationScaleChanged(int i, int i2, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onFullscreenMagnificationActivationChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
