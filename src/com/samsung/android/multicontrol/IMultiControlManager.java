package com.samsung.android.multicontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IInputFilter;
import com.samsung.android.multicontrol.IInputFilterInstallListener;
import com.samsung.android.multicontrol.IMultiControlDeathChecker;

/* loaded from: classes6.dex */
public interface IMultiControlManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multicontrol.IMultiControlManager";

    public static class Default implements IMultiControlManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void enableTriggerDetection(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void forceHideCursor(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public int getProtocolVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public boolean isAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void observeDesktopMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void resetInputFilter() throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setCursorPosition(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setInteractive(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setMultiControlOutOfFocus(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setProtocolVersion(int i) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void setTriggerThreshold(int i) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IMultiControlManager
        public void stopDeathChecker() throws RemoteException {
        }
    }

    void enableTriggerDetection(boolean z) throws RemoteException;

    void forceHideCursor(boolean z) throws RemoteException;

    int getProtocolVersion() throws RemoteException;

    boolean isAllowed() throws RemoteException;

    void observeDesktopMode(boolean z) throws RemoteException;

    void resetInputFilter() throws RemoteException;

    void setCursorPosition(int i, int i2, int i3) throws RemoteException;

    void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) throws RemoteException;

    void setInteractive(boolean z) throws RemoteException;

    void setMultiControlOutOfFocus(boolean z) throws RemoteException;

    void setProtocolVersion(int i) throws RemoteException;

    void setTriggerThreshold(int i) throws RemoteException;

    void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) throws RemoteException;

    void stopDeathChecker() throws RemoteException;

    public static abstract class Stub extends Binder implements IMultiControlManager {
        static final int TRANSACTION_enableTriggerDetection = 12;
        static final int TRANSACTION_forceHideCursor = 6;
        static final int TRANSACTION_getProtocolVersion = 2;
        static final int TRANSACTION_isAllowed = 1;
        static final int TRANSACTION_observeDesktopMode = 14;
        static final int TRANSACTION_resetInputFilter = 5;
        static final int TRANSACTION_setCursorPosition = 11;
        static final int TRANSACTION_setInputFilter = 4;
        static final int TRANSACTION_setInteractive = 7;
        static final int TRANSACTION_setMultiControlOutOfFocus = 8;
        static final int TRANSACTION_setProtocolVersion = 3;
        static final int TRANSACTION_setTriggerThreshold = 13;
        static final int TRANSACTION_startDeathChecker = 9;
        static final int TRANSACTION_stopDeathChecker = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, IMultiControlManager.DESCRIPTOR);
        }

        public static IMultiControlManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMultiControlManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMultiControlManager)) {
                return (IMultiControlManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 14:
                    return "observeDesktopMode";
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
                parcel.enforceInterface(IMultiControlManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMultiControlManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsAllowed = isAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowed);
                    return true;
                case 2:
                    int protocolVersion = getProtocolVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(protocolVersion);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProtocolVersion(i3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IInputFilter iInputFilterAsInterface = IInputFilter.Stub.asInterface(parcel.readStrongBinder());
                    IInputFilterInstallListener iInputFilterInstallListenerAsInterface = IInputFilterInstallListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInputFilter(iInputFilterAsInterface, iInputFilterInstallListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    resetInputFilter();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceHideCursor(z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInteractive(z2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultiControlOutOfFocus(z3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IMultiControlDeathChecker iMultiControlDeathCheckerAsInterface = IMultiControlDeathChecker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startDeathChecker(iMultiControlDeathCheckerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    stopDeathChecker();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCursorPosition(i4, i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableTriggerDetection(z4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTriggerThreshold(i7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    observeDesktopMode(z5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMultiControlManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMultiControlManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public boolean isAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public int getProtocolVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setProtocolVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setInputFilter(IInputFilter iInputFilter, IInputFilterInstallListener iInputFilterInstallListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputFilter);
                    parcelObtain.writeStrongInterface(iInputFilterInstallListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void resetInputFilter() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void forceHideCursor(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setInteractive(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setMultiControlOutOfFocus(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void startDeathChecker(IMultiControlDeathChecker iMultiControlDeathChecker) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMultiControlDeathChecker);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void stopDeathChecker() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setCursorPosition(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void enableTriggerDetection(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void setTriggerThreshold(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IMultiControlManager
            public void observeDesktopMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiControlManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
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
