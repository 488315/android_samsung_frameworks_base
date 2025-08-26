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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDesktopMode.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDesktopMode)) {
                return (IDesktopMode) iInterfaceQueryLocalInterface;
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
                    boolean zIsDesktopDockConnected = isDesktopDockConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDesktopDockConnected);
                    return true;
                case 2:
                    boolean zIsDesktopMode = isDesktopMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDesktopMode);
                    return true;
                case 3:
                    SemDesktopModeState desktopModeState = getDesktopModeState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(desktopModeState, 1);
                    return true;
                case 4:
                    IDesktopModeListener iDesktopModeListenerAsInterface = IDesktopModeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterDesktopModeListener = registerDesktopModeListener(iDesktopModeListenerAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterDesktopModeListener);
                    return true;
                case 5:
                    IDesktopModeBlocker iDesktopModeBlockerAsInterface = IDesktopModeBlocker.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterBlocker = registerBlocker(iDesktopModeBlockerAsInterface, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterBlocker);
                    return true;
                case 6:
                    IDesktopModeListener iDesktopModeListenerAsInterface2 = IDesktopModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterDesktopModeListener = unregisterDesktopModeListener(iDesktopModeListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterDesktopModeListener);
                    return true;
                case 7:
                    IDesktopModeBlocker iDesktopModeBlockerAsInterface2 = IDesktopModeBlocker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterBlocker = unregisterBlocker(iDesktopModeBlockerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterBlocker);
                    return true;
                case 8:
                    boolean zIsDeviceConnected = isDeviceConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceConnected);
                    return true;
                case 9:
                    boolean zIsAllowed = isAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowed);
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleUpdateDesktopMode(z);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    Bundle desktopModeKillPolicy = getDesktopModeKillPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(desktopModeKillPolicy, 1);
                    return true;
                case 12:
                    IDesktopModeLauncher iDesktopModeLauncherAsInterface = IDesktopModeLauncher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDesktopLauncher(iDesktopModeLauncherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleSendMessage = sendMessage(bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSendMessage, 1);
                    return true;
                case 14:
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onSecuredAppLaunched(strongBinder, string3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isDesktopMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public SemDesktopModeState getDesktopModeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemDesktopModeState) parcelObtain2.readTypedObject(SemDesktopModeState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDesktopModeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDesktopModeBlocker);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDesktopModeListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDesktopModeBlocker);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isDeviceConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public boolean isAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void scheduleUpdateDesktopMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public Bundle getDesktopModeKillPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDesktopModeLauncher);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public Bundle sendMessage(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopMode
            public void onSecuredAppLaunched(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
