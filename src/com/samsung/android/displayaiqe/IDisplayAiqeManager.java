package com.samsung.android.displayaiqe;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDisplayAiqeManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.displayaiqe.IDisplayAiqeManager";

    public static class Default implements IDisplayAiqeManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public String getContentMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public int getCoprValue() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean getDisplayService() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public String getPanelName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setABCMode(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setABCReconfig(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setABCState(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setBlueLightFilterMode(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setByPassMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setContentMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setEnvironmentAdaptiveDisplayLevel(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setEnvironmentAdaptiveDisplayMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setExtraDimMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setHighBrightnessMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setHighDynamicRangeMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setInternalDimmingFrame(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setLinearSkinMode(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setMdnieMode(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setNaturalMode(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setScreenMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setVividnessMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
        public boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
            return false;
        }
    }

    String getContentMode() throws RemoteException;

    int getCoprValue() throws RemoteException;

    boolean getDisplayService() throws RemoteException;

    String getPanelName(int i) throws RemoteException;

    boolean setABCMode(int i, String str) throws RemoteException;

    boolean setABCReconfig(int i) throws RemoteException;

    boolean setABCState(int i, int i2) throws RemoteException;

    boolean setBlueLightFilterMode(boolean z, int i) throws RemoteException;

    boolean setByPassMode(boolean z) throws RemoteException;

    boolean setContentMode(int i) throws RemoteException;

    boolean setEnvironmentAdaptiveDisplayLevel(int i) throws RemoteException;

    boolean setEnvironmentAdaptiveDisplayMode(int i) throws RemoteException;

    boolean setExtraDimMode(int i) throws RemoteException;

    boolean setHighBrightnessMode(int i) throws RemoteException;

    boolean setHighDynamicRangeMode(boolean z) throws RemoteException;

    boolean setInternalDimmingFrame(int i) throws RemoteException;

    boolean setLinearSkinMode(String str) throws RemoteException;

    boolean setMdnieMode(String str) throws RemoteException;

    boolean setNaturalMode(String str) throws RemoteException;

    boolean setScreenMode(int i) throws RemoteException;

    boolean setVividnessMode(int i) throws RemoteException;

    boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayAiqeManager {
        static final int TRANSACTION_getContentMode = 8;
        static final int TRANSACTION_getCoprValue = 18;
        static final int TRANSACTION_getDisplayService = 17;
        static final int TRANSACTION_getPanelName = 22;
        static final int TRANSACTION_setABCMode = 19;
        static final int TRANSACTION_setABCReconfig = 21;
        static final int TRANSACTION_setABCState = 20;
        static final int TRANSACTION_setBlueLightFilterMode = 6;
        static final int TRANSACTION_setByPassMode = 1;
        static final int TRANSACTION_setContentMode = 7;
        static final int TRANSACTION_setEnvironmentAdaptiveDisplayLevel = 12;
        static final int TRANSACTION_setEnvironmentAdaptiveDisplayMode = 11;
        static final int TRANSACTION_setExtraDimMode = 3;
        static final int TRANSACTION_setHighBrightnessMode = 13;
        static final int TRANSACTION_setHighDynamicRangeMode = 4;
        static final int TRANSACTION_setInternalDimmingFrame = 16;
        static final int TRANSACTION_setLinearSkinMode = 15;
        static final int TRANSACTION_setMdnieMode = 2;
        static final int TRANSACTION_setNaturalMode = 14;
        static final int TRANSACTION_setScreenMode = 5;
        static final int TRANSACTION_setVividnessMode = 9;
        static final int TRANSACTION_setWhiteBalanceMode = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, IDisplayAiqeManager.DESCRIPTOR);
        }

        public static IDisplayAiqeManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplayAiqeManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayAiqeManager)) {
                return (IDisplayAiqeManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setByPassMode";
                case 2:
                    return "setMdnieMode";
                case 3:
                    return "setExtraDimMode";
                case 4:
                    return "setHighDynamicRangeMode";
                case 5:
                    return "setScreenMode";
                case 6:
                    return "setBlueLightFilterMode";
                case 7:
                    return "setContentMode";
                case 8:
                    return "getContentMode";
                case 9:
                    return "setVividnessMode";
                case 10:
                    return "setWhiteBalanceMode";
                case 11:
                    return "setEnvironmentAdaptiveDisplayMode";
                case 12:
                    return "setEnvironmentAdaptiveDisplayLevel";
                case 13:
                    return "setHighBrightnessMode";
                case 14:
                    return "setNaturalMode";
                case 15:
                    return "setLinearSkinMode";
                case 16:
                    return "setInternalDimmingFrame";
                case 17:
                    return "getDisplayService";
                case 18:
                    return "getCoprValue";
                case 19:
                    return "setABCMode";
                case 20:
                    return "setABCState";
                case 21:
                    return "setABCReconfig";
                case 22:
                    return "getPanelName";
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
                parcel.enforceInterface(IDisplayAiqeManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayAiqeManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean byPassMode = setByPassMode(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(byPassMode);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean mdnieMode = setMdnieMode(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mdnieMode);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean extraDimMode = setExtraDimMode(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(extraDimMode);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean highDynamicRangeMode = setHighDynamicRangeMode(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highDynamicRangeMode);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean screenMode = setScreenMode(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenMode);
                    return true;
                case 6:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blueLightFilterMode = setBlueLightFilterMode(readBoolean3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blueLightFilterMode);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean contentMode = setContentMode(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentMode);
                    return true;
                case 8:
                    String contentMode2 = getContentMode();
                    parcel2.writeNoException();
                    parcel2.writeString(contentMode2);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean vividnessMode = setVividnessMode(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(vividnessMode);
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean whiteBalanceMode = setWhiteBalanceMode(readInt6, readInt7, readInt8, readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(whiteBalanceMode);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean environmentAdaptiveDisplayMode = setEnvironmentAdaptiveDisplayMode(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(environmentAdaptiveDisplayMode);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean environmentAdaptiveDisplayLevel = setEnvironmentAdaptiveDisplayLevel(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(environmentAdaptiveDisplayLevel);
                    return true;
                case 13:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean highBrightnessMode = setHighBrightnessMode(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highBrightnessMode);
                    return true;
                case 14:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean naturalMode = setNaturalMode(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(naturalMode);
                    return true;
                case 15:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean linearSkinMode = setLinearSkinMode(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(linearSkinMode);
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean internalDimmingFrame = setInternalDimmingFrame(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(internalDimmingFrame);
                    return true;
                case 17:
                    boolean displayService = getDisplayService();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(displayService);
                    return true;
                case 18:
                    int coprValue = getCoprValue();
                    parcel2.writeNoException();
                    parcel2.writeInt(coprValue);
                    return true;
                case 19:
                    int readInt16 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean aBCMode = setABCMode(readInt16, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(aBCMode);
                    return true;
                case 20:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean aBCState = setABCState(readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(aBCState);
                    return true;
                case 21:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean aBCReconfig = setABCReconfig(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(aBCReconfig);
                    return true;
                case 22:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String panelName = getPanelName(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(panelName);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDisplayAiqeManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayAiqeManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setByPassMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setMdnieMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setExtraDimMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setHighDynamicRangeMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setScreenMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setBlueLightFilterMode(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setContentMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public String getContentMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setVividnessMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setEnvironmentAdaptiveDisplayMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setEnvironmentAdaptiveDisplayLevel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setHighBrightnessMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setNaturalMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setLinearSkinMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setInternalDimmingFrame(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean getDisplayService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public int getCoprValue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setABCMode(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setABCState(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public boolean setABCReconfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displayaiqe.IDisplayAiqeManager
            public String getPanelName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAiqeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
