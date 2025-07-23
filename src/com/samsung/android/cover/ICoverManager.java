package com.samsung.android.cover;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverManager";

    public static class Default implements ICoverManager {
        @Override // com.samsung.android.cover.ICoverManager
        public void addLedNotification(Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void disableCoverManager(boolean z, IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public CoverState getCoverState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public CoverState getCoverStateForExternal() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean getCoverSwitchState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public int getVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean isCoverManagerDisabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public int onCoverAppCovered(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void registerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void removeLedNotification(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void sendDataToCover(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void sendDataToNfcLedCover(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void sendPowerKeyToCover() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public void sendSystemEvent(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean unregisterCallback(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean unregisterCallbackForExternal(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ICoverManager
        public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) throws RemoteException {
            return false;
        }
    }

    void addLedNotification(Bundle bundle) throws RemoteException;

    void disableCoverManager(boolean z, IBinder iBinder, String str) throws RemoteException;

    boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException;

    boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException;

    CoverState getCoverState() throws RemoteException;

    CoverState getCoverStateForExternal() throws RemoteException;

    boolean getCoverSwitchState() throws RemoteException;

    int getVersion() throws RemoteException;

    boolean isCoverManagerDisabled() throws RemoteException;

    int onCoverAppCovered(boolean z) throws RemoteException;

    void registerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) throws RemoteException;

    void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) throws RemoteException;

    void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) throws RemoteException;

    void removeLedNotification(Bundle bundle) throws RemoteException;

    boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void sendDataToCover(int i, byte[] bArr) throws RemoteException;

    void sendDataToNfcLedCover(int i, byte[] bArr) throws RemoteException;

    void sendPowerKeyToCover() throws RemoteException;

    void sendSystemEvent(Bundle bundle) throws RemoteException;

    boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) throws RemoteException;

    boolean unregisterCallback(IBinder iBinder) throws RemoteException;

    boolean unregisterCallbackForExternal(IBinder iBinder) throws RemoteException;

    boolean unregisterNfcTouchListenerCallback(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements ICoverManager {
        static final int TRANSACTION_addLedNotification = 14;
        static final int TRANSACTION_disableCoverManager = 7;
        static final int TRANSACTION_disableLcdOffByCover = 17;
        static final int TRANSACTION_enableLcdOffByCover = 18;
        static final int TRANSACTION_getCoverState = 4;
        static final int TRANSACTION_getCoverStateForExternal = 24;
        static final int TRANSACTION_getCoverSwitchState = 5;
        static final int TRANSACTION_getVersion = 8;
        static final int TRANSACTION_isCoverManagerDisabled = 6;
        static final int TRANSACTION_onCoverAppCovered = 21;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerListenerCallback = 2;
        static final int TRANSACTION_registerListenerCallbackForExternal = 22;
        static final int TRANSACTION_registerNfcTouchListenerCallback = 11;
        static final int TRANSACTION_removeLedNotification = 15;
        static final int TRANSACTION_requestCoverAuthentication = 19;
        static final int TRANSACTION_sendDataToCover = 9;
        static final int TRANSACTION_sendDataToNfcLedCover = 13;
        static final int TRANSACTION_sendPowerKeyToCover = 10;
        static final int TRANSACTION_sendSystemEvent = 16;
        static final int TRANSACTION_setFotaInProgress = 20;
        static final int TRANSACTION_unregisterCallback = 3;
        static final int TRANSACTION_unregisterCallbackForExternal = 23;
        static final int TRANSACTION_unregisterNfcTouchListenerCallback = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }

        public Stub() {
            attachInterface(this, ICoverManager.DESCRIPTOR);
        }

        public static ICoverManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICoverManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICoverManager)) {
                return (ICoverManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "registerListenerCallback";
                case 3:
                    return "unregisterCallback";
                case 4:
                    return "getCoverState";
                case 5:
                    return "getCoverSwitchState";
                case 6:
                    return "isCoverManagerDisabled";
                case 7:
                    return "disableCoverManager";
                case 8:
                    return "getVersion";
                case 9:
                    return "sendDataToCover";
                case 10:
                    return "sendPowerKeyToCover";
                case 11:
                    return "registerNfcTouchListenerCallback";
                case 12:
                    return "unregisterNfcTouchListenerCallback";
                case 13:
                    return "sendDataToNfcLedCover";
                case 14:
                    return "addLedNotification";
                case 15:
                    return "removeLedNotification";
                case 16:
                    return "sendSystemEvent";
                case 17:
                    return "disableLcdOffByCover";
                case 18:
                    return "enableLcdOffByCover";
                case 19:
                    return "requestCoverAuthentication";
                case 20:
                    return "setFotaInProgress";
                case 21:
                    return "onCoverAppCovered";
                case 22:
                    return "registerListenerCallbackForExternal";
                case 23:
                    return "unregisterCallbackForExternal";
                case 24:
                    return "getCoverStateForExternal";
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
                parcel.enforceInterface(ICoverManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICoverManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerCallback(readStrongBinder, componentName);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerCallback(readStrongBinder2, componentName2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallback = unregisterCallback(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallback);
                    return true;
                case 4:
                    CoverState coverState = getCoverState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(coverState, 1);
                    return true;
                case 5:
                    boolean coverSwitchState = getCoverSwitchState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(coverSwitchState);
                    return true;
                case 6:
                    boolean isCoverManagerDisabled = isCoverManagerDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCoverManagerDisabled);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableCoverManager(readBoolean, readStrongBinder4, readString);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(version);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDataToCover(readInt2, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    sendPowerKeyToCover();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerNfcTouchListenerCallback(readInt3, readStrongBinder5, componentName3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean unregisterNfcTouchListenerCallback = unregisterNfcTouchListenerCallback(readStrongBinder6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterNfcTouchListenerCallback);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDataToNfcLedCover(readInt4, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addLedNotification(bundle);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeLedNotification(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSystemEvent(bundle3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableLcdOffByCover = disableLcdOffByCover(readStrongBinder7, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableLcdOffByCover);
                    return true;
                case 18:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableLcdOffByCover = enableLcdOffByCover(readStrongBinder8, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableLcdOffByCover);
                    return true;
                case 19:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestCoverAuthentication = requestCoverAuthentication(readStrongBinder9, componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCoverAuthentication);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean fotaInProgress = setFotaInProgress(readBoolean2, readStrongBinder10, componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fotaInProgress);
                    return true;
                case 21:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int onCoverAppCovered = onCoverAppCovered(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(onCoverAppCovered);
                    return true;
                case 22:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerCallbackForExternal(readStrongBinder11, componentName8, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallbackForExternal = unregisterCallbackForExternal(readStrongBinder12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallbackForExternal);
                    return true;
                case 24:
                    CoverState coverStateForExternal = getCoverStateForExternal();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(coverStateForExternal, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICoverManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public CoverState getCoverState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CoverState) obtain2.readTypedObject(CoverState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean getCoverSwitchState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean isCoverManagerDisabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void disableCoverManager(boolean z, IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public int getVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendDataToCover(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendPowerKeyToCover() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendDataToNfcLedCover(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void addLedNotification(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void removeLedNotification(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendSystemEvent(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public int onCoverAppCovered(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterCallbackForExternal(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public CoverState getCoverStateForExternal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CoverState) obtain2.readTypedObject(CoverState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
