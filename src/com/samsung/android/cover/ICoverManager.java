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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICoverManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICoverManager)) {
                return (ICoverManager) iInterfaceQueryLocalInterface;
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerCallback(strongBinder, componentName);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerCallback(strongBinder2, componentName2, i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterCallback = unregisterCallback(strongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterCallback);
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
                    boolean zIsCoverManagerDisabled = isCoverManagerDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCoverManagerDisabled);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableCoverManager(z, strongBinder4, string);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(version);
                    return true;
                case 9:
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDataToCover(i4, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    sendPowerKeyToCover();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i5 = parcel.readInt();
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerNfcTouchListenerCallback(i5, strongBinder5, componentName3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterNfcTouchListenerCallback = unregisterNfcTouchListenerCallback(strongBinder6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterNfcTouchListenerCallback);
                    return true;
                case 13:
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDataToNfcLedCover(i6, bArrCreateByteArray2);
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
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableLcdOffByCover = disableLcdOffByCover(strongBinder7, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableLcdOffByCover);
                    return true;
                case 18:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableLcdOffByCover = enableLcdOffByCover(strongBinder8, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableLcdOffByCover);
                    return true;
                case 19:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestCoverAuthentication = requestCoverAuthentication(strongBinder9, componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestCoverAuthentication);
                    return true;
                case 20:
                    boolean z2 = parcel.readBoolean();
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean fotaInProgress = setFotaInProgress(z2, strongBinder10, componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fotaInProgress);
                    return true;
                case 21:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iOnCoverAppCovered = onCoverAppCovered(z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOnCoverAppCovered);
                    return true;
                case 22:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerCallbackForExternal(strongBinder11, componentName8, i7);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterCallbackForExternal = unregisterCallbackForExternal(strongBinder12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterCallbackForExternal);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public CoverState getCoverState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CoverState) parcelObtain2.readTypedObject(CoverState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean getCoverSwitchState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean isCoverManagerDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void disableCoverManager(boolean z, IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public int getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendDataToCover(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendPowerKeyToCover() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendDataToNfcLedCover(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void addLedNotification(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void removeLedNotification(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void sendSystemEvent(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public int onCoverAppCovered(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public boolean unregisterCallbackForExternal(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManager
            public CoverState getCoverStateForExternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManager.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CoverState) parcelObtain2.readTypedObject(CoverState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
