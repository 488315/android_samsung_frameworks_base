package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import com.samsung.android.desktopmode.IDesktopModeLauncher;
import com.samsung.android.desktopmode.IDesktopModeListener;

/* loaded from: classes6.dex */
public interface IDesktopMode extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopMode";

    public static class Default implements IDesktopMode {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public Bundle getDesktopModeKillPolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public SemDesktopModeState getDesktopModeState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean isAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean isDesktopDockConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean isDesktopMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean isDeviceConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public void onSecuredAppLaunched(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public void scheduleUpdateDesktopMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public Bundle sendMessage(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.desktopmode.IDesktopMode
        public boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) throws RemoteException {
            return false;
        }
    }

    Bundle getDesktopModeKillPolicy() throws RemoteException;

    SemDesktopModeState getDesktopModeState() throws RemoteException;

    boolean isAllowed() throws RemoteException;

    boolean isDesktopDockConnected() throws RemoteException;

    boolean isDesktopMode() throws RemoteException;

    boolean isDeviceConnected() throws RemoteException;

    void onSecuredAppLaunched(IBinder iBinder, String str) throws RemoteException;

    boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) throws RemoteException;

    void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) throws RemoteException;

    boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str) throws RemoteException;

    void scheduleUpdateDesktopMode(boolean z) throws RemoteException;

    Bundle sendMessage(Bundle bundle) throws RemoteException;

    boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) throws RemoteException;

    boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IDesktopMode {
        static final int TRANSACTION_getDesktopModeKillPolicy = 11;
        static final int TRANSACTION_getDesktopModeState = 3;
        static final int TRANSACTION_isAllowed = 9;
        static final int TRANSACTION_isDesktopDockConnected = 1;
        static final int TRANSACTION_isDesktopMode = 2;
        static final int TRANSACTION_isDeviceConnected = 8;
        static final int TRANSACTION_onSecuredAppLaunched = 14;
        static final int TRANSACTION_registerBlocker = 5;
        static final int TRANSACTION_registerDesktopLauncher = 12;
        static final int TRANSACTION_registerDesktopModeListener = 4;
        static final int TRANSACTION_scheduleUpdateDesktopMode = 10;
        static final int TRANSACTION_sendMessage = 13;
        static final int TRANSACTION_unregisterBlocker = 7;
        static final int TRANSACTION_unregisterDesktopModeListener = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, IDesktopMode.DESCRIPTOR);
        }

        public static IDesktopMode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDesktopMode.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDesktopMode)) {
                return (IDesktopMode) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isDesktopDockConnected";
                case 2:
                    return "isDesktopMode";
                case 3:
                    return "getDesktopModeState";
                case 4:
                    return "registerDesktopModeListener";
                case 5:
                    return "registerBlocker";
                case 6:
                    return "unregisterDesktopModeListener";
                case 7:
                    return "unregisterBlocker";
                case 8:
                    return "isDeviceConnected";
                case 9:
                    return "isAllowed";
                case 10:
                    return "scheduleUpdateDesktopMode";
                case 11:
                    return "getDesktopModeKillPolicy";
                case 12:
                    return "registerDesktopLauncher";
                case 13:
                    return "sendMessage";
                case 14:
                    return "onSecuredAppLaunched";
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
                parcel.enforceInterface(IDesktopMode.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopMode.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isDesktopDockConnected = isDesktopDockConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopDockConnected);
                    return true;
                case 2:
                    boolean isDesktopMode = isDesktopMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopMode);
                    return true;
                case 3:
                    SemDesktopModeState desktopModeState = getDesktopModeState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(desktopModeState, 1);
                    return true;
                case 4:
                    IDesktopModeListener asInterface = IDesktopModeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerDesktopModeListener = registerDesktopModeListener(asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerDesktopModeListener);
                    return true;
                case 5:
                    IDesktopModeBlocker asInterface2 = IDesktopModeBlocker.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerBlocker = registerBlocker(asInterface2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerBlocker);
                    return true;
                case 6:
                    IDesktopModeListener asInterface3 = IDesktopModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterDesktopModeListener = unregisterDesktopModeListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterDesktopModeListener);
                    return true;
                case 7:
                    IDesktopModeBlocker asInterface4 = IDesktopModeBlocker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterBlocker = unregisterBlocker(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterBlocker);
                    return true;
                case 8:
                    boolean isDeviceConnected = isDeviceConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceConnected);
                    return true;
                case 9:
                    boolean isAllowed = isAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllowed);
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleUpdateDesktopMode(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    Bundle desktopModeKillPolicy = getDesktopModeKillPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(desktopModeKillPolicy, 1);
                    return true;
                case 12:
                    IDesktopModeLauncher asInterface5 = IDesktopModeLauncher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDesktopLauncher(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle sendMessage = sendMessage(bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sendMessage, 1);
                    return true;
                case 14:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onSecuredAppLaunched(readStrongBinder, readString3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDesktopMode {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopMode.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isDesktopDockConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isDesktopMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public SemDesktopModeState getDesktopModeState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemDesktopModeState) obtain2.readTypedObject(SemDesktopModeState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopModeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopModeBlocker);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopModeListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopModeBlocker);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isDeviceConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void scheduleUpdateDesktopMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public Bundle getDesktopModeKillPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iDesktopModeLauncher);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public Bundle sendMessage(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void onSecuredAppLaunched(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
