package android.hardware.tv.tuner;

import android.hardware.tv.tuner.IDemux;
import android.hardware.tv.tuner.IDescrambler;
import android.hardware.tv.tuner.IFrontend;
import android.hardware.tv.tuner.ILnb;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITuner extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$ITuner".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    DemuxCapabilities getDemuxCaps() throws RemoteException;

    int[] getDemuxIds() throws RemoteException;

    DemuxInfo getDemuxInfo(int i) throws RemoteException;

    int[] getFrontendIds() throws RemoteException;

    FrontendInfo getFrontendInfo(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int[] getLnbIds() throws RemoteException;

    int getMaxNumberOfFrontends(int i) throws RemoteException;

    boolean isLnaSupported() throws RemoteException;

    IDemux openDemux(int[] iArr) throws RemoteException;

    IDemux openDemuxById(int i) throws RemoteException;

    IDescrambler openDescrambler() throws RemoteException;

    IFrontend openFrontendById(int i) throws RemoteException;

    ILnb openLnbById(int i) throws RemoteException;

    ILnb openLnbByName(String str, int[] iArr) throws RemoteException;

    void setLna(boolean z) throws RemoteException;

    void setMaxNumberOfFrontends(int i, int i2) throws RemoteException;

    public static class Default implements ITuner {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public DemuxCapabilities getDemuxCaps() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int[] getDemuxIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public DemuxInfo getDemuxInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int[] getFrontendIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public FrontendInfo getFrontendInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int[] getLnbIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int getMaxNumberOfFrontends(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public boolean isLnaSupported() throws RemoteException {
            return false;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public IDemux openDemux(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public IDemux openDemuxById(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public IDescrambler openDescrambler() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public IFrontend openFrontendById(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public ILnb openLnbById(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public ILnb openLnbByName(String str, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public void setLna(boolean z) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.ITuner
        public void setMaxNumberOfFrontends(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.ITuner
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ITuner {
        static final int TRANSACTION_getDemuxCaps = 4;
        static final int TRANSACTION_getDemuxIds = 14;
        static final int TRANSACTION_getDemuxInfo = 16;
        static final int TRANSACTION_getFrontendIds = 1;
        static final int TRANSACTION_getFrontendInfo = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLnbIds = 7;
        static final int TRANSACTION_getMaxNumberOfFrontends = 12;
        static final int TRANSACTION_isLnaSupported = 13;
        static final int TRANSACTION_openDemux = 3;
        static final int TRANSACTION_openDemuxById = 15;
        static final int TRANSACTION_openDescrambler = 5;
        static final int TRANSACTION_openFrontendById = 2;
        static final int TRANSACTION_openLnbById = 8;
        static final int TRANSACTION_openLnbByName = 9;
        static final int TRANSACTION_setLna = 10;
        static final int TRANSACTION_setMaxNumberOfFrontends = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ITuner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITuner)) {
                return (ITuner) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int[] iArr;
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
                    int[] frontendIds = getFrontendIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(frontendIds);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IFrontend openFrontendById = openFrontendById(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openFrontendById);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    if (readInt2 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt2);
                    }
                    iArr = readInt2 >= 0 ? new int[readInt2] : null;
                    parcel.enforceNoDataAvail();
                    IDemux openDemux = openDemux(iArr);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDemux);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 4:
                    DemuxCapabilities demuxCaps = getDemuxCaps();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(demuxCaps, 1);
                    return true;
                case 5:
                    IDescrambler openDescrambler = openDescrambler();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDescrambler);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FrontendInfo frontendInfo = getFrontendInfo(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(frontendInfo, 1);
                    return true;
                case 7:
                    int[] lnbIds = getLnbIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(lnbIds);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ILnb openLnbById = openLnbById(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLnbById);
                    return true;
                case 9:
                    String readString = parcel.readString();
                    int readInt5 = parcel.readInt();
                    if (readInt5 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt5);
                    }
                    iArr = readInt5 >= 0 ? new int[readInt5] : null;
                    parcel.enforceNoDataAvail();
                    ILnb openLnbByName = openLnbByName(readString, iArr);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLnbByName);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLna(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxNumberOfFrontends(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxNumberOfFrontends = getMaxNumberOfFrontends(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumberOfFrontends);
                    return true;
                case 13:
                    boolean isLnaSupported = isLnaSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLnaSupported);
                    return true;
                case 14:
                    int[] demuxIds = getDemuxIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(demuxIds);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IDemux openDemuxById = openDemuxById(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDemuxById);
                    return true;
                case 16:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DemuxInfo demuxInfo = getDemuxInfo(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(demuxInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITuner {
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

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getFrontendIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrontendIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public IFrontend openFrontendById(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openFrontendById is unimplemented.");
                    }
                    obtain2.readException();
                    return IFrontend.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public IDemux openDemux(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(iArr.length);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openDemux is unimplemented.");
                    }
                    obtain2.readException();
                    IDemux asInterface = IDemux.Stub.asInterface(obtain2.readStrongBinder());
                    obtain2.readIntArray(iArr);
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public DemuxCapabilities getDemuxCaps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDemuxCaps is unimplemented.");
                    }
                    obtain2.readException();
                    return (DemuxCapabilities) obtain2.readTypedObject(DemuxCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public IDescrambler openDescrambler() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openDescrambler is unimplemented.");
                    }
                    obtain2.readException();
                    return IDescrambler.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public FrontendInfo getFrontendInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrontendInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (FrontendInfo) obtain2.readTypedObject(FrontendInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getLnbIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getLnbIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public ILnb openLnbById(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openLnbById is unimplemented.");
                    }
                    obtain2.readException();
                    return ILnb.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public ILnb openLnbByName(String str, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(iArr.length);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openLnbByName is unimplemented.");
                    }
                    obtain2.readException();
                    ILnb asInterface = ILnb.Stub.asInterface(obtain2.readStrongBinder());
                    obtain2.readIntArray(iArr);
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public void setLna(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setLna is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public void setMaxNumberOfFrontends(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setMaxNumberOfFrontends is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int getMaxNumberOfFrontends(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getMaxNumberOfFrontends is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public boolean isLnaSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isLnaSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getDemuxIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDemuxIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public IDemux openDemuxById(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openDemuxById is unimplemented.");
                    }
                    obtain2.readException();
                    return IDemux.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public DemuxInfo getDemuxInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDemuxInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (DemuxInfo) obtain2.readTypedObject(DemuxInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
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

            @Override // android.hardware.tv.tuner.ITuner
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
