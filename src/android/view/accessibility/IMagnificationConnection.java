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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMagnificationConnection.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMagnificationConnection)) {
                return (IMagnificationConnection) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    float f3 = parcel.readFloat();
                    float f4 = parcel.readFloat();
                    float f5 = parcel.readFloat();
                    IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallbackAsInterface = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableWindowMagnification(i3, f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallbackAsInterface);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    float f6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setScaleForWindowMagnification(i4, f6);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallbackAsInterface2 = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableWindowMagnification(i5, iRemoteMagnificationAnimationCallbackAsInterface2);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    float f7 = parcel.readFloat();
                    float f8 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifier(i6, f7, f8);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    secSetCursorVisible(i7, z);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    float f9 = parcel.readFloat();
                    float f10 = parcel.readFloat();
                    IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallbackAsInterface3 = IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifierToPosition(i8, f9, f10, iRemoteMagnificationAnimationCallbackAsInterface3);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showMagnificationButton(i9, i10);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationButton(i11);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationSettingsPanel(i12);
                    return true;
                case 10:
                    IMagnificationConnectionCallback iMagnificationConnectionCallbackAsInterface = IMagnificationConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setConnectionCallback(iMagnificationConnectionCallbackAsInterface);
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    float f11 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onUserMagnificationScaleChanged(i13, i14, f11);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFullscreenMagnificationActivationChanged(i15, z2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    parcelObtain.writeFloat(f5);
                    parcelObtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setScaleForWindowMagnification(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifier(int i, float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void secSetCursorVisible(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifierToPosition(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void showMagnificationButton(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationButton(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationSettingsPanel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMagnificationConnectionCallback);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onUserMagnificationScaleChanged(int i, int i2, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onFullscreenMagnificationActivationChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMagnificationConnection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
