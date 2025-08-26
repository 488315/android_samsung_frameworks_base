package android.hardware.tv.tuner;

import android.hardware.tv.tuner.IFrontendCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFrontend extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IFrontend".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    void close() throws RemoteException;

    int[] getFrontendStatusReadiness(int[] iArr) throws RemoteException;

    String getHardwareInfo() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    FrontendStatus[] getStatus(int[] iArr) throws RemoteException;

    int linkCiCam(int i) throws RemoteException;

    void removeOutputPid(int i) throws RemoteException;

    void scan(FrontendSettings frontendSettings, int i) throws RemoteException;

    void setCallback(IFrontendCallback iFrontendCallback) throws RemoteException;

    void setLnb(int i) throws RemoteException;

    void stopScan() throws RemoteException;

    void stopTune() throws RemoteException;

    void tune(FrontendSettings frontendSettings) throws RemoteException;

    void unlinkCiCam(int i) throws RemoteException;

    public static class Default implements IFrontend {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int[] getFrontendStatusReadiness(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public String getHardwareInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public FrontendStatus[] getStatus(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int linkCiCam(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void removeOutputPid(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void scan(FrontendSettings frontendSettings, int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void setCallback(IFrontendCallback iFrontendCallback) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void setLnb(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void stopScan() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void stopTune() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void tune(FrontendSettings frontendSettings) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void unlinkCiCam(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IFrontend {
        static final int TRANSACTION_close = 4;
        static final int TRANSACTION_getFrontendStatusReadiness = 13;
        static final int TRANSACTION_getHardwareInfo = 11;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStatus = 7;
        static final int TRANSACTION_linkCiCam = 9;
        static final int TRANSACTION_removeOutputPid = 12;
        static final int TRANSACTION_scan = 5;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setLnb = 8;
        static final int TRANSACTION_stopScan = 6;
        static final int TRANSACTION_stopTune = 3;
        static final int TRANSACTION_tune = 2;
        static final int TRANSACTION_unlinkCiCam = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFrontend asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFrontend)) {
                return (IFrontend) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    IFrontendCallback iFrontendCallbackAsInterface = IFrontendCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(iFrontendCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    FrontendSettings frontendSettings = (FrontendSettings) parcel.readTypedObject(FrontendSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(frontendSettings);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    stopTune();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    FrontendSettings frontendSettings2 = (FrontendSettings) parcel.readTypedObject(FrontendSettings.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scan(frontendSettings2, i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopScan();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    FrontendStatus[] status = getStatus(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(status, 1);
                    return true;
                case 8:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLnb(i4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iLinkCiCam = linkCiCam(i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLinkCiCam);
                    return true;
                case 10:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlinkCiCam(i6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(hardwareInfo);
                    return true;
                case 12:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOutputPid(i7);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] frontendStatusReadiness = getFrontendStatusReadiness(iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(frontendStatusReadiness);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFrontend {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void setCallback(IFrontendCallback iFrontendCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFrontendCallback);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void tune(FrontendSettings frontendSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(frontendSettings, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method tune is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopTune() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stopTune is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void scan(FrontendSettings frontendSettings, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(frontendSettings, 0);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method scan is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stopScan is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public FrontendStatus[] getStatus(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getStatus is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (FrontendStatus[]) parcelObtain2.createTypedArray(FrontendStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void setLnb(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setLnb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int linkCiCam(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method linkCiCam is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void unlinkCiCam(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unlinkCiCam is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public String getHardwareInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void removeOutputPid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method removeOutputPid is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int[] getFrontendStatusReadiness(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getFrontendStatusReadiness is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
