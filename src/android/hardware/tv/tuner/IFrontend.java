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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFrontend)) {
                return (IFrontend) queryLocalInterface;
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
                    IFrontendCallback asInterface = IFrontendCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scan(frontendSettings2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopScan();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    FrontendStatus[] status = getStatus(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(status, 1);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLnb(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int linkCiCam = linkCiCam(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(linkCiCam);
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlinkCiCam(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(hardwareInfo);
                    return true;
                case 12:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOutputPid(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] frontendStatusReadiness = getFrontendStatusReadiness(createIntArray2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFrontendCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void tune(FrontendSettings frontendSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(frontendSettings, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method tune is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopTune() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stopTune is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void scan(FrontendSettings frontendSettings, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(frontendSettings, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method scan is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stopScan is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public FrontendStatus[] getStatus(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return (FrontendStatus[]) obtain2.createTypedArray(FrontendStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void setLnb(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setLnb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int linkCiCam(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method linkCiCam is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void unlinkCiCam(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unlinkCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public String getHardwareInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void removeOutputPid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method removeOutputPid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int[] getFrontendStatusReadiness(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrontendStatusReadiness is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
